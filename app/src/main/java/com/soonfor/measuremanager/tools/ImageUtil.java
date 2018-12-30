package com.soonfor.measuremanager.tools;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.dynamicpicpro.util.FileUtils;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.jiamm.utils.CommonUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.view.previewImage.ImageVAty;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ljc on 2018/1/18.
 */

public class ImageUtil {

    /**
     * @param context
     * @param url
     * @param isNoCache 需要缓存
     */
    public static void loadImageWithCache(Context context, String url, ImageView iv, int defaultResId, boolean isNoCache) {//isNoCache决定是否要缓存
        String finalurl = backCrmPicPath(url);
        if(isNoCache) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(defaultResId); //加载成功之前占位图
            requestOptions.error(defaultResId);//加载错误之后的错误图
            requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE); //只缓存最终的图片
            Glide.with(context).load(finalurl).apply(requestOptions).into(iv);
        }else {
            Picasso.with(context).load(finalurl).config(Bitmap.Config.ARGB_4444).placeholder(defaultResId).error(defaultResId).into(iv);
        }
    }

    /**
     * 通过 附件服务器路径 加载图片
     * @param context
     * @param url
     * @param iv
     */
    public static void loadImage(Context context, String url, ImageView iv) {//每次都请求，不要缓存
        ImageUtil.loadPicByGlide(context, iv, url, R.mipmap.zanuw, false);
    }

    /**
     * 通过 uploadcenter 服务器路径 加载图片
     * 案例库获取图片
     *
     * @param context
     * @param url
     * @param iv
     */
    public static void loadCaselibImage(Context context, String url, ImageView iv) {//每次都请求，不要缓存
        ImageUtil.loadPicByGlide(context, iv, url, R.mipmap.zanuw, true);
    }

    /**
     * 获取附件服务器地址（用于上传和下载附件）
     *
     * @param mContext
     * @param netType  数据服务器地址网络类型 0 内网 1 外网
     * @return
     */
    public static void getFjAddress(Context mContext, int netType, fjInterface interf) {
        String fjAddress = Hawk.get(SoonforApplication.ServerAdr_fj, "");
        if (fjAddress.equals("")) {
            String sjaddress = Hawk.get(SoonforApplication.ServerAdr, "");
            String type = "";
            switch (netType) {
                case 0:
                    type = "attachIn";//内网
                    break;
                case 1:
                    type = "attachEx";//外网
                    break;
            }
            String url = "http://" + sjaddress + UserInfo.GETFJURI + type;
            get(mContext, url, interf);
        } else {
            interf.setAddress(fjAddress);
        }
    }

    public interface fjInterface {
        void setAddress(String address);
    }

    static void get(final Context context, String url, final fjInterface Interf) {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
        client.setConnectTimeout(20 * 1000);
        String uuid = PreferenceUtil.getString(UserInfo.UUID, "");
        if (!uuid.equals("")) {
            client.addHeader("Authorization", "Bearer " + PreferenceUtil.getString(UserInfo.UUID, ""));
        }
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("msgcode") != 710001) {
                    } else {
                        boolean isSuccess = false;
                        HeadBean headBean = JsonUtils.getHeadBean(object.toString());
                        if (headBean != null && (headBean.getMsgcode() == 0 || headBean.isSuccess())) {
                            if (headBean.getData() != null) {
                                try {
                                    JSONObject jo = new JSONObject(headBean.getData());
                                    String uri = CommonUtils.formatStr(jo.getString("uri"));
                                    if (!uri.equals("") && uri.contains("http://")) {
                                        uri = uri.replace("http://", "");
                                    }
                                    Hawk.put(SoonforApplication.ServerAdr_fj, uri);
                                    isSuccess = true;
                                    Interf.setAddress(uri);
                                } catch (Exception e) {
                                }
                            }
                        }
                        if (!isSuccess) {
                            Interf.setAddress("");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Interf.setAddress("");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                if (!NetUtils.getNetworkIsAvailable(context)) {
                    MyToast.showToast(context, "与服务器通讯中断！");
                } else {
                    Interf.setAddress("");
                }
            }
        });
    }

    public static void previewCrmPics(Context mActivity, int defaulID, String... strings) {
        ArrayList<String> pathList = new ArrayList<>();
        if (strings != null && strings.length > 0) {
            for (int i = 0; i < strings.length; i++) {
                pathList.add("http://" + Hawk.get(SoonforApplication.ServerAdr_fj, "") + UserInfo.DOWNLOAD_FILE + strings[i]);
            }
            Intent intent = new Intent();
            intent.putExtra("DEFAULTIMAGEID", defaulID);
            intent.putExtra("position", 0);
            intent.putStringArrayListExtra("images", pathList);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setClass(mActivity, ImageVAty.class);
            mActivity.startActivity(intent);
        }
    }

    public static String SDPATH = Environment.getExternalStorageDirectory() + "/";//获取文件夹

    //删除文件
    public static void delFile(String dirStr, String fileName) {
        try {
            File file = new File(SDPATH + dirStr + "/" + fileName);
            if (file.isFile()) {
                file.delete();
            }
            file.exists();
        } catch (Exception e) {
        }
    }

    //删除文件夹和文件夹里面的文件
    public static void deleteDir(String dirStr) {
        try {
            File dir = new File(SDPATH + dirStr + "/");
            if (dir == null || !dir.exists() || !dir.isDirectory())
                return;

            for (File file : dir.listFiles()) {
                if (file.isFile())
                    file.delete(); // 删除所有文件
                else if (file.isDirectory())
                    deleteDir(dirStr); // 递规的方式删除文件夹
            }
            dir.delete();// 删除目录本身
        } catch (Exception e) {
        }
    }

    /**
     * 下载图片并保存到本地，并分享出去
     */
    public static class downloadImageAndShareAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private final Context context;
        String[] sParam;

        public downloadImageAndShareAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            sParam = params;
            return AsyncUtils.getBitMBitmap(sParam[3]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                MyToast.showToast(context, "生产分享信息失败！");
                return;
            }
            try {
                //此path就是对应文件的缓存路径
                File f = saveBitmapFile(context, result);
                CommonUtils.shareMsg(context, sParam[0], sParam[1], sParam[2], f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存Bitmap图片到本地
     *
     * @return void
     */
    public static File saveBitmapFile(Context context, Bitmap bitmap) {
        String savePath;
        File filePic = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            savePath = "/sdcard/ShareImage/";
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + "/ShareImage/";
        }
        try {
//            if (Build.VERSION.SDK_INT >= 24) {
//                filesave = Uri
//            } else {
//                filesave = new File(savePath);
            filePic = new File(savePath + "/" + System.currentTimeMillis() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream out;
            try {
                out = new FileOutputStream(filePic);
                if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                    out.flush();
                    out.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        if (!filePic.exists() || !filePic.isFile()) {
            filePic = null;
        }
        return filePic;
    }

    /**
     * 将图片写入到磁盘
     *
     * @param img      图片数据流
     * @param fileName 文件保存时的名称
     */
    public static boolean writeImageToDisk(byte[] img, String fileName) {
        boolean isSuccessful = false;
        try {
            File file = new File(fileName);
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(img);
            fops.flush();
            fops.close();
            isSuccessful = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccessful;
    }

    /**
     * 根据地址获得数据的字节流
     *
     * @param strUrl 网络连接地址
     * @return
     */
    public static byte[] getImageFromNetByUrl(String strUrl) {
        byte[] btImg = null;
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            btImg = readInputStream(inStream);//得到图片的二进制数据
        } catch (Exception e) {
            e.printStackTrace();
        }
        return btImg;
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    //用glide加载图片
    public static void loadPicByGlide(Context context, ImageView img, String filePath, int defaultResId, boolean isCaseLib) {
        try {
            String finalurl = "";
            if (isCaseLib) {
                finalurl = backUploadCenterPath(filePath);
            } else {
                finalurl = backCrmPicPath(filePath);
            }
            Picasso.with(context).load(finalurl).config(Bitmap.Config.ARGB_4444).placeholder(defaultResId).error(defaultResId).into(img);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //返回Crm的图片路径
    public static String backCrmPicPath(String filePath) {
        try {
            if (filePath == null || filePath.equals("")) {
                return "";
            } else {
                if (!filePath.toLowerCase().startsWith("http")) {
                    String resultPath = "";
                    if (filePath.indexOf(":") == 1) { //filepath是服务器上的物理路径
                        resultPath = UserInfo.DOWNLOAD_FILE + filePath;
                    } else if (!filePath.startsWith("/")) {
                        resultPath = "/" + filePath;
                    } else {
                        resultPath = filePath;
                    }
                    return "http://" + Hawk.get(SoonforApplication.ServerAdr_fj) + resultPath;
                } else {
                    return filePath;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //返回由上传中心uploadcenter上传的图片
    public static String backUploadCenterPath(String filePath) {
        try {
            if (filePath == null || filePath.equals("")) {
                return "";
            } else {
                if (!filePath.toLowerCase().startsWith("http")) {
                    String resultPath = "";
                    if (filePath.indexOf(":") == 1) { //filepath是服务器上的物理路径
                        resultPath = UserInfo.DOWNLOAD_FILE + filePath;
                    } else if (!filePath.startsWith("/")) {
                        resultPath = "/" + filePath;
                    } else {
                        resultPath = filePath;
                    }
                    return "http://" + Hawk.get(SoonforApplication.ServerAdr_Upload) + resultPath;
                } else {
                    return filePath;
                }
                //String filename = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.toString().length());
                // + filename;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
