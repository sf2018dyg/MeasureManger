package com.soonfor.repository.http.httptools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.orhanobut.hawk.Hawk;
import com.soonfor.repository.base.RepHeadBean;
import com.soonfor.repository.tools.DataTools;
import com.soonfor.repository.tools.RepJsonUtils;
import com.soonfor.repository.tools.MyToast;
import com.soonfor.repository.tools.NetTools;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import cn.jesse.nativelogger.NLogger;


/**
 * 作者：DC-DingYG on 2018-04-27 9:06
 * 邮箱：dingyg012655@126.com
 */
public class RepAsyncUtils {
    private static AsyncCallback callback = null;

    public void setAsyncListerner(AsyncCallback callback) {
        this.callback = callback;
    }

    public interface AsyncCallback {
        void success(int requestCode, String data);

        void fail(int requestCode, int statusCode, String msg);
    }
    public interface AsyncCallback2 {
        void success(String type, int requestCode, String data);

        void fail(String type, int requestCode, int statusCode, String msg);
    }

    public static void downLoadFile(String url, BinaryHttpResponseHandler handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, handler);
    }

    public static void saveImg(String url, BinaryHttpResponseHandler handler) {
        RepAsyncUtils.downLoadFile(url, handler);
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
        } catch (PackageManager.NameNotFoundException e) {
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
     * 将UUID加入头部
     *
     * @param context
     * @param client
     */
    public static void setHeader(Context context, AsyncHttpClient client) {
        String uuid = Hawk.get(DataTools.UUID, "");
        if (!uuid.equals(""))
            client.addHeader("Authorization", "Bearer " + uuid);
    }

    /**
     * Get请求
     *
     * @param context
     * @param fullUrl
     * @param requestCode
     * @param callback
     */
    public static void get(final Context context, final String fullUrl, final int requestCode,
                           final AsyncCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
        client.setTimeout(20 * 1000);
        setHeader(context, client);
        client.get(Hawk.get(DataTools.ServerAdr) + fullUrl, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                try {
                    RepHeadBean bean = RepJsonUtils.getHeadBean(result);
                    if (bean != null) {
                        if (bean.isSuccess()) {
                            //保存账号和密码
                            callback.success(requestCode, bean.getData());
                        } else {
                            callback.fail(requestCode, -1, bean.getFaileMsg());
                            NLogger.e(fullUrl, result);
                        }
                    } else {
                        callback.fail(requestCode, -1, "请求出错，结果为空");
                        NLogger.e(fullUrl, result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    NLogger.e(fullUrl, e.getMessage());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                if (!NetTools.isConnectInternet(context)) {
                    String emsg = "您的WLAN和移动网络均未连接！";
                    callback.fail(requestCode, i, emsg);
                    NLogger.e(fullUrl, emsg);
                } else {
                    NLogger.e("请求失败状态吗", i + "");
                    String Errmsg = "请求出错";
                    if (bytes != null) {
                        String result = new String(bytes);
                        NLogger.e("失败", result + "");
                        JSONObject object = null;
                        try {
                            object = new JSONObject(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (object != null) {
                            try {
                                JSONArray err_ja = object.getJSONArray("errors");
                                JSONObject err_jo = err_ja.getJSONObject(0);
                                Errmsg = err_jo.getString("defaultMessage");
                            } catch (Exception e) {
                                e.printStackTrace();
                                Errmsg = object.toString();
                            }
                        }
                    } else if (throwable != null) {
                        Errmsg = throwable.toString();
                    }
                    callback.fail(requestCode, i, Errmsg);
                    NLogger.e(fullUrl, Errmsg);
                }
            }
        });
    }
    /**
     * Get请求
     *
     * @param context
     * @param fullUrl
     * @param requestCode
     * @param callback
     */
    public static void fget(final Context context, final String fullUrl, final int requestCode,
                            final AsyncCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
        client.setTimeout(20 * 1000);
        setHeader(context, client);
        client.get(fullUrl, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                try {
                    if(result!=null && !result.equals("")){
                        //保存账号和密码
                        callback.success(requestCode,result);
                    } else {
                        callback.fail(requestCode, -1, result);
                        NLogger.e(fullUrl, result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    NLogger.e(fullUrl, e.getMessage());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                if (!NetTools.isConnectInternet(context)) {
                    String emsg = "您的WLAN和移动网络均未连接！";
                    callback.fail(requestCode, i, emsg);
                    NLogger.e(fullUrl, emsg);
                } else {
                    NLogger.e("请求失败状态吗", i + "");
                    String Errmsg = "请求出错";
                    if (bytes != null) {
                        String result = new String(bytes);
                        NLogger.e("失败", result + "");
                        JSONObject object = null;
                        try {
                            object = new JSONObject(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (object != null) {
                            try {
                                JSONArray err_ja = object.getJSONArray("errors");
                                JSONObject err_jo = err_ja.getJSONObject(0);
                                Errmsg = err_jo.getString("defaultMessage");
                            } catch (Exception e) {
                                e.printStackTrace();
                                Errmsg = object.toString();
                            }
                        }
                    } else if (throwable != null) {
                        Errmsg = throwable.toString();
                    }
                    callback.fail(requestCode, i, Errmsg);
                    NLogger.e(fullUrl, Errmsg);
                }
            }
        });
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
    public static void post(final Context context, final String url, String jsonParams, final int requestCode, final AsyncCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
        client.setTimeout(20 * 1000);
        setHeader(context, client);
        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(jsonParams.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            String fullUrl = "";
            if(url.startsWith("http")){
                fullUrl = url;
            }else {
                fullUrl = Hawk.get(DataTools.ServerAdr) + url;
            }
            client.post(context, fullUrl, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String result = new String(bytes);
                    try {
                        RepHeadBean bean = RepJsonUtils.getHeadBean(result);
                        if (bean != null) {
                            if (bean.getMsgCode() == 710001) {//bean.getMsgCode() == 700100 ||
                                Hawk.put(DataTools.UUID, "");
                                MyToast.showFailToast(context, bean.getFaileMsg());
                                NLogger.e(url, result);
                                //取消自动登录，并调至登录界面
//                                PreferenceUtil.commitBoolean(RepUserInfo.ISAUTOLOGIN, false);
//                                CommonApp.finishAllActivity();
//                                context.startActivity(new Intent(context, LoginActivity.class));
                            } else if (bean.getMsgCode() == 0) {
                                callback.success(requestCode, bean.getData());
                            } else {
                                callback.fail(requestCode, -1, bean.getFaileMsg());
                                NLogger.e(url, result);
                            }
                        } else {
                            callback.fail(requestCode, -1, "请求出错，结果为空");
                            NLogger.e(url, result);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    if (!NetTools.isConnectInternet(context)) {
                        String emsg = "您的WLAN和移动网络均未连接！";
                        callback.fail(requestCode, i, emsg);
                        NLogger.e(url, emsg);
                    } else {
                        String Errmsg = "请求出错";
                        if (bytes != null) {
                            String result = new String(bytes);
                            NLogger.e("失败", result + "");
                            JSONObject object = null;
                            try {
                                object = new JSONObject(result);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (object != null) {
                                try {
                                    JSONArray err_ja = object.getJSONArray("errors");
                                    JSONObject err_jo = err_ja.getJSONObject(0);
                                    Errmsg = err_jo.getString("defaultMessage");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Errmsg = object.toString();
                                }
                            }
                        } else if (throwable != null) {
                            Errmsg = throwable.toString();
                        }
                        callback.fail(requestCode, i, Errmsg);
                        NLogger.e(url, Errmsg);
                    }

                }
            });
        } catch (Exception e) {
            e.fillInStackTrace();
            NLogger.e(url, e.getMessage());
        }
    }
    /**
     * 上传文件到crm
     *
     * @param context
     * @param fileType //上传的文件类型
     * @param url
     * @param params
     * @param requestCode
     * @param callback
     */
    public static void uploadFileToCrm(final Context context, final String fileType, final String url, RequestParams params, final int requestCode, final RepAsyncUtils.AsyncCallback2 callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
        client.setTimeout(30*1000);
        setHeader(context, client);

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                try {
                    RepHeadBean bean = RepJsonUtils.getHeadBean(result);
                    if (bean != null) {
                        if (bean.getMsgCode() == 710001) {//bean.getMsgCode() == 700100 ||
                            Hawk.put(DataTools.UUID, "");
                            MyToast.showFailToast(context, bean.getFaileMsg());
                            NLogger.e(url, result);
                            //取消自动登录，并调至登录界面
//                                PreferenceUtil.commitBoolean(RepUserInfo.ISAUTOLOGIN, false);
//                                CommonApp.finishAllActivity();
//                                context.startActivity(new Intent(context, LoginActivity.class));
                        } else if (bean.getMsgCode() == 0) {
                            callback.success(fileType, requestCode, bean.getData());
                        } else {
                            callback.fail(fileType, requestCode, -1, bean.getFaileMsg());
                            NLogger.e(url, result);
                        }
                    } else {
                        callback.fail(fileType, requestCode, -1, "上传失败");
                        NLogger.e(url, result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    NLogger.e(url, e.getMessage());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                if (!NetTools.isConnectInternet(context)) {
                    String emsg = "您的WLAN和移动网络均未连接！";
                    callback.fail(fileType, requestCode, i, emsg);
                    NLogger.e(url, emsg);
                } else {
                    String Errmsg = "请求出错";
                    if (bytes != null) {
                        String result = new String(bytes);
                        NLogger.e("失败", result + "");
                        JSONObject object = null;
                        try {
                            object = new JSONObject(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (object != null) {
                            try {
                                JSONArray err_ja = object.getJSONArray("errors");
                                JSONObject err_jo = err_ja.getJSONObject(0);
                                Errmsg = err_jo.getString("defaultMessage");
                            } catch (Exception e) {
                                e.printStackTrace();
                                Errmsg = object.toString();
                            }
                        }
                    } else if (throwable != null) {
                        Errmsg = throwable.toString();
                    }
                    callback.fail(fileType, requestCode, i, "图片上传失败");
                    NLogger.e(url, Errmsg);
                }

            }
        });
    }
}
