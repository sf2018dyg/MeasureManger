package com.dingyg.richeditor.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;

import com.soonfor.repository.tools.ActivityUtils;
import com.soonfor.repository.tools.dialog.CustomDialog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class SDCardUtil {
    public static String SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    public static String APP_NAME = "XRichText";
    public static String CompressFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator;//压缩视频的路径
    public static String UploadVideos = "RepositoryVideos";

    /**
     * 检查是否存在SDCard
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获得文章图片保存路径
     */
    public static String getFileDir(String fileDir) {
        String imageCacheUrl = SDCardRoot + fileDir + File.separator;
        File file = new File(imageCacheUrl);
        if (!file.exists())
            file.mkdirs();  //如果不存在则创建
        return imageCacheUrl;
    }

    /**
     * 图片保存到SD卡
     *
     * @param bitmap
     * @return
     */
    public static String saveToSdCard(Bitmap bitmap) {
        String imageUrl = getFileDir(APP_NAME) + "IMG_" + System.currentTimeMillis() + ".jpg";
        File file = new File(imageUrl);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    /**
     * 下载视频保存到SD卡
     *
     * @param svurl
     * @return
     */
    public static String saveVideoToSdCard(final String svurl) {
        final String vidUrl = CompressFile + "UVID_" + System.currentTimeMillis() + ".mp4";
        // apk文件下载线程
        boolean isOver = false;
        try {
            URL url = new URL(svurl);
            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            File file = new File(CompressFile);
            if (!file.exists()) {
                file.mkdir();
            }
            File videoFile = new File(vidUrl);
            FileOutputStream out = new FileOutputStream(videoFile);
            int count = 0;
            int readnum = 0;
            byte[] buffer = new byte[1024 * 10];
            do {
                readnum = is.read(buffer);
                count += readnum;
                if (readnum <= 0) {
                    // 下载结束
                    isOver = true;
                    break;
                }
                out.write(buffer, 0, readnum);
            } while (!isOver);

            is.close();
            out.close();
        } catch (Exception e) {
            Looper.prepare();  // 此处获取到当前线程的Looper，并且prepare()
            Looper.loop();
        }
        if (isOver) {
            return vidUrl;
        } else {
            return null;
        }
    }

    /**
     * 根据Uri获取图片文件的格式
     */
    public static String getFrmatByUri(String path) {
        if (path != null && !path.equals("") && path.contains(".")) {
            path = path.substring(path.lastIndexOf("."), path.length()).toLowerCase();
        } else {
            return "";
        }
        if (path.equals(".jpeg") || path.equals(".jpg") || path.equals(".png")
                || path.equals(".bmp") || path.equals(".webp") || path.equals(".gif")) {
            return "image";
        } else if (path.equals(".mp4") || path.equals(".mpg") || path.equals(".mkv") || path.equals(".avi")
                || path.equals(".mov") || path.equals(".webm") || path.equals(".ts") || path.equals(".m4v")
                || path.equals(".3g2") || path.equals(".3gpp2") || path.equals(".3gpp") || path.equals(".3gpp")
                || path.equals(".mpeg")) {
            return "video";
        }
        return "";
    }

    /**
     * 根据Uri获取图片/视频文件的绝对路径
     */
    public static String getFilePathByUri(Activity context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 根据Uri获取真实的文件路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getFilePathFromUri(Context context, String fmat, Uri uri) {
        if (uri == null) return null;

        ContentResolver resolver = context.getContentResolver();
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            ParcelFileDescriptor pfd = resolver.openFileDescriptor(uri, "r");
            if (pfd == null) {
                return null;
            }
            FileDescriptor fd = pfd.getFileDescriptor();
            input = new FileInputStream(fd);


            File outputDir = context.getCacheDir();
            File outputFile = File.createTempFile(fmat, "tmp", outputDir);
            String tempFilename = outputFile.getAbsolutePath();
            output = new FileOutputStream(tempFilename);

            int read;
            byte[] bytes = new byte[4096];
            while ((read = input.read(bytes)) != -1) {
                output.write(bytes, 0, read);
            }

            return new File(tempFilename).getAbsolutePath();
        } catch (Exception ignored) {

            ignored.getStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (Throwable t) {
                // Do nothing
            }
        }
        return null;
    }

    /**
     * 删除文件
     **/
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        boolean isOk = false;
        if (file.isFile() && file.exists())
            isOk = file.delete(); // 删除文件
        return isOk;
    }

    //删除文件夹和文件夹里面包含条件condtion的文件 condtion为空时删除全部
    public static void deleteDir(String dirStr, String condtion) {
        try {
            File dir = new File(SDCardRoot + dirStr + "/");
            if (dir == null || !dir.exists() || !dir.isDirectory()) {
                return;
            }
            if (condtion == null) {
                for (File file : dir.listFiles()) {
                    if (file.isFile())
                        file.delete(); // 删除所有文件
                    else if (file.isDirectory())
                        deleteDir(file.getAbsolutePath(), null); // 递规的方式删除文件夹
                }
            }else {
                for (File file : dir.listFiles()) {
                    if (file.isFile()) {
                        String absolutePath = file.getAbsolutePath();
                        String filename = file.getAbsolutePath().substring(absolutePath.lastIndexOf("/")+1, absolutePath.length());
                        if(filename.startsWith(condtion)) {
                            file.delete(); // 删除所有文件
                        }
                    }else if (file.isDirectory())
                        deleteDir(file.getAbsolutePath(), condtion); // 递规的方式删除文件夹
                }
            }
            dir.delete();// 删除目录本身
        } catch (Exception e) {
        }
    }

    //文件拷贝
    //要复制的目录下的所有非子目录(文件夹)文件拷贝
    public static int CopySdcardFile(String fromFile, String toFile) {

        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 0;

        } catch (Exception ex) {
            return -1;
        }
    }

    /**
     * 获取视频的时长
     */
    public static int getRingDuring(String url) {
        //本地视频
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            return mediaPlayer.getDuration();
        } catch (Exception e) {
            return 0;
        }

        //网络视频
//        String duration=null;
//        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();
//
//        try {
//            if (mUri != null) {
//                HashMap<String, String> headers = null;
//                if (headers == null) {
//                    headers = new HashMap<String, String>();
//                    headers.put("User-Agent",
//                            "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
//                }
//                mmr.setDataSource(mUri, headers);
//            }
//
//            duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            mmr.release();
//        }
//        if(duration==null || duration.equals("")){
//            return 0;
//        }
//        return Long.parseLong(duration);
    }

    public static Locale getLocale(Context context) {
        Configuration config = context.getResources().getConfiguration();
        Locale sysLocale = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = getSystemLocale(config);
        } else {
            sysLocale = getSystemLocaleLegacy(config);
        }

        return sysLocale;
    }

    @SuppressWarnings("deprecation")
    public static Locale getSystemLocaleLegacy(Configuration config) {
        return config.locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSystemLocale(Configuration config) {
        return config.getLocales().get(0);
    }

    /*
    获取文件大小
    */
    public static double getFileSizeMB(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return 0.00;
        } else {
            long size = f.length();
            return (double) ((size / 1024f) / 1024f);
        }
    }
    public static double getFileSizeKB(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return 0.00;
        } else {
            long size = f.length();
            return (double) (size / 1024f);
        }
    }
}
