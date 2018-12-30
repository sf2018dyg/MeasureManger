package com.soonfor.repository.tools;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 作者：DC-DingYG on 2018-07-30 14:38
 * 邮箱：dingyg012655@126.com
 */
public class FileUtils {

    /**
     * 分享功能
     * <p>
     * 上下文
     *
     * @param context       上下文
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param imgFile       图片流文件，不分享图片则传null
     */
    public static void shareMsg(Context context, String activityTitle, String msgTitle, String msgText,
                                File imgFile) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgFile == null || imgFile.equals("")) {
            intent.setType("text/*"); // 纯文本
        } else {
            if (imgFile.exists() && imgFile.isFile()) {
                intent.setType("image/*");
                Uri u = Uri.fromFile(imgFile);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, activityTitle);//标题
        intent.putExtra("Kdescription", msgTitle + "\n" + msgText); // 作用于发朋友圈，对好友不会有影响
        intent.putExtra(Intent.EXTRA_TEXT, msgTitle + "\n" + msgText);//附带说明
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //拷贝分享内容到粘贴板
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(msgText);
        //MyToast.showToast(context,"已拷贝分享网址到粘贴板,去粘贴分享吧！");

        context.startActivity(Intent.createChooser(intent, activityTitle));
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
           // return RepAsyncUtils.getBitMBitmap(sParam[2]);
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
//            if (result == null) {
//                MyToast.showToast(context, "生产分享信息失败！");
//                return;
//            }
            try {
                //此path就是对应文件的缓存路径
               // File f = saveBitmapFile(context, result);
               // shareMsg(context, sParam[0], sParam[1],sParam[2],f);
                shareMsg(context, sParam[0], sParam[1],sParam[2],null);
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
            savePath = "/sdcard/RepositoryShareImage/";
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + "/RepositoryShareImage/";
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
        if(!filePic.exists() || !filePic.isFile()){
            filePic = null;
        }
        return filePic;
    }
    public static void openFile(Context context, String path) {
        if (context == null || path == null)
            return;
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //文件的类型
        String type = "";
        for (int i = 0; i < MATCH_ARRAY.length; i++) {
            //判断文件的格式
            if (path.toString().contains(MATCH_ARRAY[i][0].toString())) {
                type = MATCH_ARRAY[i][1];
                break;
            }
        }
        try {
            //设置intent的data和Type属性
            intent.setDataAndType(Uri.fromFile(new File(path)), type);
            //跳转
            context.startActivity(intent);
        } catch (Exception e) { //当系统没有携带文件打开软件，提示
            MyToast.showToast(context, "无法打开该格式文件!");
            e.printStackTrace();
        }
    }
    //建立一个文件类型与文件后缀名的匹配表
    private static final String[][] MATCH_ARRAY= {
            //{后缀名，MIME类型}
            {".3gp",    "video/3gpp"},
            {".apk",    "application/vnd.android.package-archive"},
            {".asf",    "video/x-ms-asf"},
            {".avi",    "video/x-msvideo"},
            {".bin",    "application/octet-stream"},
            {".bmp",    "image/bmp"},
            {".c",  "text/plain"},
            {".class",  "application/octet-stream"},
            {".conf",   "text/plain"},
            {".cpp",    "text/plain"},
            {".doc",    "application/msword"},
            {".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls",    "application/vnd.ms-excel"},
            {".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe",    "application/octet-stream"},
            {".gif",    "image/gif"},
            {".gtar",   "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h",  "text/plain"},
            {".htm",    "text/html"},
            {".html",   "text/html"},
            {".jar",    "application/java-archive"},
            {".java",   "text/plain"},
            {".jpeg",   "image/jpeg"},
            {".jpg",    "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log",    "text/plain"},
            {".m3u",    "audio/x-mpegurl"},
            {".m4a",    "audio/mp4a-latm"},
            {".m4b",    "audio/mp4a-latm"},
            {".m4p",    "audio/mp4a-latm"},
            {".m4u",    "video/vnd.mpegurl"},
            {".m4v",    "video/x-m4v"},
            {".mov",    "video/quicktime"},
            {".mp2",    "audio/x-mpeg"},
            {".mp3",    "audio/x-mpeg"},
            {".mp4",    "video/mp4"},
            {".mpc",    "application/vnd.mpohun.certificate"},
            {".mpe",    "video/mpeg"},
            {".mpeg",   "video/mpeg"},
            {".mpg",    "video/mpeg"},
            {".mpg4",   "video/mp4"},
            {".mpga",   "audio/mpeg"},
            {".msg",    "application/vnd.ms-outlook"},
            {".ogg",    "audio/ogg"},
            {".pdf",    "application/pdf"},
            {".png",    "image/png"},
            {".pps",    "application/vnd.ms-powerpoint"},
            {".ppt",    "application/vnd.ms-powerpoint"},
            {".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop",   "text/plain"},
            {".rc", "text/plain"},
            {".rmvb",   "audio/x-pn-realaudio"},
            {".rtf",    "application/rtf"},
            {".sh", "text/plain"},
            {".tar",    "application/x-tar"},
            {".tgz",    "application/x-compressed"},
            {".txt",    "text/plain"},
            {".wav",    "audio/x-wav"},
            {".wma",    "audio/x-ms-wma"},
            {".wmv",    "audio/x-ms-wmv"},
            {".wps",    "application/vnd.ms-works"},
            {".xml",    "text/plain"},
            {".z",  "application/x-compress"},
            {".zip",    "application/x-zip-compressed"},
            {"",        "*/*"}
    };

}
