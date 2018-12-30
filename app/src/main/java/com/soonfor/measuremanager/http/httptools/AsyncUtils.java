package com.soonfor.measuremanager.http.httptools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.bases.ErrorBean;
import com.soonfor.measuremanager.home.login.activity.LoginActivity;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.tools.CommonApp;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.LogTools;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.NetUtils;
import com.soonfor.measuremanager.tools.PreferenceUtil;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import cn.jesse.nativelogger.NLogger;


public class AsyncUtils {

    private static AsyncCallback callback = null;

    public void setAsyncListerner(AsyncCallback callback) {
        this.callback = callback;
    }

    public interface AsyncCallback {
        void success(int requestCode, JSONObject object);

        void fail(int requestCode, int statusCode, String msg);
    }

    public static void downLoadFile(String url, BinaryHttpResponseHandler handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, handler);
    }

    public static void saveImg(String url, BinaryHttpResponseHandler handler) {
        AsyncUtils.downLoadFile(url, handler);
    }

    public static Bitmap getBitMBitmap(String urlpath) {
        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
            // TODO Auto-generated catch block
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        PackageInfo info;
        String versionName = null;
        try {
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            versionName = info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 验证邮箱格式是否正确
     */
    public static boolean emailValidation(String email) {
        String regex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return email.matches(regex);
    }

    /**
     * 上传文件到crm
     *
     * @param context
     * @param url
     * @param params
     * @param requestCode
     * @param callback
     */
    public static void uploadFileToCrm(final Context context, String url, RequestParams params, final int requestCode, final AsyncCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
        client.setConnectTimeout(30 * 1000);
        String fullUrl = "http://" + Hawk.get(SoonforApplication.ServerAdr_fj) + url;
        client.post(fullUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                try {
                    JSONObject object = new JSONObject(result);
                    callback.success(requestCode, object);
//                    if (object.getInt("msgcode") != 0) {
//                        CommonUtils.LogErrorMsg(url + ": " ,object.toString());
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    setFailureMsg(url + "请求出错: ", requestCode, i, e.getMessage(), null, null, callback);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                if (!NetUtils.getNetworkIsAvailable(context)) {
                    MyToast.showToast(context, "您的WLAN和移动网络均未连接！");
                    callback.fail(requestCode, i, "您的WLAN和移动网络均未连接！");
                } else {
                    setFailureMsg(url + "请求出错: ", requestCode, i, null, throwable, bytes, callback);
                }
            }
        });
    }

    /**
     * 不需要验证UUID （比如登录之前的请求等）
     *
     * @param context
     * @param url
     * @param params
     * @param requestCode
     * @param callback
     */
    public static void post(final Context context, String url, RequestParams params, final int requestCode, final AsyncCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
        client.setConnectTimeout(30 * 1000);
        String fullUrl = CommonUtils.getServiceAdr() + url;
        client.post(fullUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("msgcode") == 710001) {
                        if (context instanceof LoginActivity) {
                        } else {
                            MyToast.showFailToast(context, object.getString("msg"));
                            CommonApp.finishAllActivity();
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }
                    } else {
                        callback.success(requestCode, object);
                        if (object.getInt("msgcode") != 0) {
                            CommonUtils.LogErrorMsg(url + ": " ,object.toString());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    setFailureMsg(url + "请求出错: ", requestCode, i, e.getMessage(), null, null, callback);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                if (!NetUtils.getNetworkIsAvailable(context)) {
                    MyToast.showToast(context, "您的WLAN和移动网络均未连接！");
                    callback.fail(requestCode, i, "您的WLAN和移动网络均未连接！");
                } else {
                    setFailureMsg(url + "请求出错: ", requestCode, i, null, throwable, bytes, callback);
                }
            }
        });
    }

    /**
     * 将UUID加入头部
     *
     * @param context
     * @param client
     */
    public static void setHeader(Context context, AsyncHttpClient client) {
        String uuid = PreferenceUtil.getString(UserInfo.UUID, "");
        if (!uuid.equals(""))
            client.addHeader("Authorization", "Bearer " + PreferenceUtil.getString(UserInfo.UUID, ""));
    }

    /**
     * 需要验证（比如请求任务信息）
     *
     * @param context
     * @param url
     * @param jsonParams
     * @param requestCode
     * @param callback
     */
    public static void post(final Context context, String url, String jsonParams, final int requestCode, final AsyncCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
        client.setConnectTimeout(30 * 1000);
        setHeader(context, client);
        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(jsonParams.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String fullUrl = CommonUtils.getServiceAdr() + url;
        client.post(context, fullUrl, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("msgcode") == 710001) {
                        MyToast.showFailToast(context, object.getString("data"));
                        if (context instanceof LoginActivity) {
                        } else {
                            PreferenceUtil.removeKey(UserInfo.UUID);
                            CommonApp.finishAllActivity();
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }
                    } else{
                        callback.success(requestCode, object);
                        if(object.getInt("msgcode") != 0){
                            CommonUtils.LogErrorMsg(url + ": " ,object.toString());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    setFailureMsg(url + "请求出错: ", requestCode, i, e.getMessage(), null, null, callback);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                if (!NetUtils.getNetworkIsAvailable(context)) {
                    MyToast.showToast(context, "您的WLAN和移动网络均未连接！");
                    callback.fail(requestCode, i, "您的WLAN和移动网络均未连接！");
                } else {
                    setFailureMsg(url + "请求出错: ", requestCode, i, null, throwable, bytes, callback);
                }
            }
        });
    }


    /**
     * Get请求
     *
     * @param context
     * @param url
     * @param requestCode
     * @param callback
     */
    public static void get(final Context context, String url, final int requestCode,
                           final AsyncCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
        client.setConnectTimeout(30 * 1000);
        setHeader(context, client);
        String fullUrl = CommonUtils.getServiceAdr() + url;
        client.get(fullUrl, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("msgcode") == 710001) {
                        MyToast.showFailToast(context, object.getString("data"));
                        if (context instanceof LoginActivity) {
                        } else {
                            PreferenceUtil.removeKey(UserInfo.UUID);
                            CommonApp.finishAllActivity();
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }
                    } else {
                        callback.success(requestCode, object);
                        if (object.getInt("msgcode") != 0) {
                            CommonUtils.LogErrorMsg(url + ": " ,object.toString());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    setFailureMsg(url + "请求出错: ", requestCode, i, e.getMessage(), null, null, callback);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                if (!NetUtils.getNetworkIsAvailable(context)) {
                    MyToast.showToast(context, "您的WLAN和移动网络均未连接！");
                    callback.fail(requestCode, i, "您的WLAN和移动网络均未连接！");
                } else {
                    setFailureMsg(url + "请求出错: ", requestCode, i, null, throwable, bytes, callback);
                }
            }
        });
    }

    //没有header
    public static void getNoHeader(final Context context, String url, final int requestCode,
                                   final AsyncCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
        LogTools.showLog(context, "get Request:" + url);
        String fullUrl = CommonUtils.getServiceAdr() + url;
        client.get(fullUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("msgcode") == 710001) {
                        MyToast.showFailToast(context, object.getString("data"));
                        if (context instanceof LoginActivity) {
                        } else {
                            PreferenceUtil.removeKey(UserInfo.UUID);
                            CommonApp.finishAllActivity();
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }
                    } else{
                        callback.success(requestCode, object);
                    if (object.getInt("msgcode") != 0) {
                        CommonUtils.LogErrorMsg(url + ": " ,object.toString());
                    }
                }
                } catch (Exception e) {
                    e.printStackTrace();
                    setFailureMsg(url + "请求出错: ", requestCode, i, e.getMessage(), null, null, callback);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                if (!NetUtils.getNetworkIsAvailable(context)) {
                    MyToast.showToast(context, "您的WLAN和移动网络均未连接！");
                    callback.fail(requestCode, i, "您的WLAN和移动网络均未连接！");
                } else {
                    setFailureMsg( url + "请求失败: ", requestCode, i, null, throwable, bytes, callback);
                }
            }
        });
    }

    static void setFailureMsg(String title, int requestCode, int statusCode, String msg, Throwable throwable,  byte[] bytes, AsyncCallback callback){
        if(msg==null) {
            JSONObject object = null;
            ErrorBean errorBean = null;
            if (bytes != null) {
                String result = new String(bytes);
                try {
                    object = new JSONObject(result);
                    errorBean = new Gson().fromJson(object.toString(), ErrorBean.class);
                    CommonUtils.LogErrorMsg(title, object.toString());
                } catch (Exception e) {
                    errorBean = new ErrorBean();
                    errorBean.setStatus("-1");
                    errorBean.setMessage("网络错误");
                    CommonUtils.LogErrorMsg(title, "网络错误");
                }
            }
            String errMsg = "";
            if (errorBean != null) {
                if (errorBean.getStatus().equals("500"))
                    errMsg = "服务器内部错误（500）";
                 else if(errorBean.getStatus().equals("-1"))
                    errMsg = errorBean.getMessage();
                else
                    errMsg = errorBean.getError() + "（" + errorBean.getStatus() + "）";
            } else {
                if (throwable != null) {
                    errMsg = throwable.getMessage();
                    CommonUtils.LogErrorMsg(title, errMsg);
                }
            }
            if(errMsg==null || errMsg.equals("")){
                errMsg = "请求出错";
            }
            callback.fail(requestCode, statusCode, errMsg);
        }else if(!msg.equals("")){
            callback.fail(requestCode, statusCode, msg);
        }else {
            callback.fail(requestCode, statusCode, "请求出错");
        }
    }
}
