package com.dingyg.richeditor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import com.dingyg.richeditor.utils.ImageUtils;
import com.dingyg.richeditor.utils.MyGlideEngine;
import com.dingyg.richeditor.utils.SDCardUtil;
import com.dingyg.richeditor.utils.StringUtils;
import com.soonfor.repository.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.HashMap;
import java.util.List;

import cn.jesse.nativelogger.NLogger;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：DC-DingYG on 2018-08-01 10:47
 * 邮箱：dingyg012655@126.com
 */
public class RichEditUtils {

    public static final int REQUEST_CODE_CHOOSE = 23;//定义请求码常量
    public static final int REQUEST_CODE_CAMERA = 24;//拍照
    public static final int REQUEST_CODE_CAMERA2 = 22;//摄像
    private ProgressDialog loadingDialog;
    private ProgressDialog insertDialog;

    private Subscription subsLoading;
    private Subscription subsInsert;
    private int screenWidth;
    private int screenHeight;
    private RichTextEditor et_new_content;
    private String myContent;

    private static RichEditUtils instance;
    private Activity mContent;

    private RichEditUtils(Activity mContent, RichTextEditor richTextEditor) {
        init(mContent, richTextEditor);
    }

    public static RichEditUtils getRichEditUtils(Activity mContent, RichTextEditor richTextEditor) {
        return new RichEditUtils(mContent, richTextEditor);
    }

    private void init(final Activity mContent, RichTextEditor richTextEditor) {
        this.mContent = mContent;
        this.et_new_content = richTextEditor;
        screenWidth = Tools.getScreenWidth(mContent);
        screenHeight = Tools.getScreenHeight(mContent);
        loadingDialog = new ProgressDialog(mContent);
        loadingDialog.setMessage("数据加载中...");
        loadingDialog.setCanceledOnTouchOutside(false);
        insertDialog = new ProgressDialog(mContent);
        insertDialog.setMessage("正在插入图片...");
        insertDialog.setCanceledOnTouchOutside(false);
        // 图片删除事件
        et_new_content.setOnRtImageDeleteListener(new RichTextEditor.OnRtImageDeleteListener() {

            @Override
            public void onRtImageDelete(String imagePath) {
                if (!TextUtils.isEmpty(imagePath)) {
                    boolean isOK = SDCardUtil.deleteFile(imagePath);
                    if (isOK) {
                        Toast.makeText(mContent, "删除成功：" + imagePath, Toast.LENGTH_SHORT);
                    }
                }
            }
        });
        // 图片点击事件
        et_new_content.setOnRtImageClickListener(new RichTextEditor.OnRtImageClickListener() {
            @Override
            public void onRtImageClick(String imagePath) {
                myContent = getEditData();
                if (!TextUtils.isEmpty(myContent)) {
                    List<String> imageList = StringUtils.getTextFromHtml(myContent, true);
                }
            }
        });
    }


    /**
     * 打开相机拍照或视频
     */
    public void openCamera(int type) {
        try {
            switch (type) {
                case 1://拍照
                    Intent intent1 = getTakePickIntent();
                    mContent.startActivityForResult(intent1, REQUEST_CODE_CAMERA);
                    break;
                case 2://摄像
                    Intent intent2 = getTakePickIntent();
                    mContent.startActivityForResult(intent2, REQUEST_CODE_CAMERA2);
                    break;
            }
        } catch (ActivityNotFoundException e) {
        }
    }


    public static List<Uri> getUriList(Intent data) {
        return Matisse.obtainResult(data);
    }



    /**
     * 压缩图片
     */
    public String getCompressImageFile(String fmat, Uri imageUri) {
        final String Path = SDCardUtil.getFilePathFromUri(mContent, fmat, imageUri);
        if(SDCardUtil.getFileSizeKB(Path)<=100){
            return Path;
        }else {
            String imagePath = "";
            for(int i=0; i<100; i++) {
                try {
                    //Log.e(TAG, "###path=" + imagePath);
                    Bitmap bitmap = ImageUtils.getSmallBitmap(Path, screenWidth, screenHeight);//压缩图片
                    //bitmap = BitmapFactory.decodeFile(imagePath);
                    imagePath = SDCardUtil.saveToSdCard(bitmap);
                    if(SDCardUtil.getFileSizeKB(Path) <=100){
                        break;
                    }
                } catch (Exception e) {
                    imagePath = Path;
                    break;
                }
            }
            return imagePath;
        }
    }

    /**
     * 异步方式插入图片
     *
     * @param imagePath
     */
    public void insertImagesSync(final Bitmap bitmap, final String fameAtPath, final String imagePath) {
        insertDialog.show();
        subsInsert = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    et_new_content.measure(0, 0);
                    subscriber.onNext(imagePath);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        if (insertDialog != null && insertDialog.isShowing()) {
                            insertDialog.dismiss();
                        }
                        if (fameAtPath == null) {
                            Toast.makeText(mContent, "图片插入成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContent, "视频插入成功", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (insertDialog != null && insertDialog.isShowing()) {
                            insertDialog.dismiss();
                        }
                        if (fameAtPath == null) {
                            Toast.makeText(mContent, "图片插入失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContent, "视频插入失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNext(String imagePath) {
                        et_new_content.insertImage(bitmap, fameAtPath, imagePath);
                    }
                });
    }

    /**
     * 根据view的宽度，动态缩放bitmap尺寸
     *
     * @param width view的宽度
     */
    public Bitmap getScaledBitmap(String filePath, int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int sampleSize = options.outWidth > width ? options.outWidth / width
                + 1 : 1;
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        return BitmapFactory.decodeFile(filePath, options);
    }


    /**
     * 显示数据
     */
    protected void showEditData(Subscriber<? super String> subscriber, String html) {
        try {
            List<String> textList = StringUtils.cutStringByImgTag(html);
            for (int i = 0; i < textList.size(); i++) {
                String text = textList.get(i);
                subscriber.onNext(text);
            }
            subscriber.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
            subscriber.onError(e);
        }
    }

    /**
     * 负责处理编辑数据提交等事宜，请自行实现
     */
    public String getEditData() {
        List<RichTextEditor.EditData> editList = et_new_content.buildEditData();
        StringBuffer content = new StringBuffer();
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null && !itemData.inputStr.equals("")) {
                content.append("<p style=\"text-indent:0em;margin:8px auto 10px auto;\"><font style=\"font-size:14.000000;color:#333333\">" + itemData.inputStr + "</p>");
            } else if (itemData.imagePath != null) {
                if (itemData.imagePath.contains(".mp4")) {
                    content.append("<video width=100% height=auto src=\"").append(itemData.imagePath + "\"")
                            .append(" poster=\"" + itemData.fameAtPath + "\"" + " controls autoplay=\"false\"")
                            .append("></video>")
                            .append("<p style=\"text-indent:0em;margin:2px auto 0px auto;\"></p>");
                } else {
                    content.append("<img width=100% height=auto src=\"")
                            .append(itemData.imagePath + "\"")
                            .append("></img>")
                            .append("<p style=\"text-indent:0em;margin:2px auto 0px auto;\"></p>");
                }
            }
        }
        return content.toString();
    }


    public Intent getTakePickIntent() {
        Intent intent = new Intent();
        try {
            intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
        } catch (Exception e) {
        }
        return intent;
    }

    /**
     * 获取本地视频的第一帧
     *
     * @param localPath
     * @return
     */
    public static Bitmap getLocalVideoBitmap(String localPath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据文件路径获取缩略图
            retriever.setDataSource(localPath);
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;//SDCardUtil.saveToSdCard(bitmap);
    }

    /**
     * 服务器返回url，通过url去获取视频的第一帧
     * Android 原生给我们提供了一个MediaMetadataRetriever类
     * 提供了获取url视频第一帧的方法,返回Bitmap对象
     *
     * @param videoUrl
     * @return
     */
    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;
        if(videoUrl==null || videoUrl.equals("")){
            return null;
        }

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            NLogger.e("视频不完整或被损坏！");
        } finally {
            retriever.release();
        }
        return bitmap;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//                Bitmap bitmap = null;
//                try {
//                    HashMap<String, String> params = new HashMap<>();
//                    params.put("Accept-Encoding", "gzip, deflate, sdch");
//                    params.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//                    mediaMetadataRetriever.setDataSource(mediaPlayer.getCurrentURI().toString(), params);
//                    // 获取图片
//                    bitmap = mediaMetadataRetriever.getFrameAtTime(currentPlayPosition, MediaMetadataRetriever.OPTION_NEXT_SYNC);
//                    //将图片保持到相册中
//                    saveBitmapToGallery(bitmap);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    mediaMetadataRetriever.release();
//                    if (bitmap != null) {
//                        bitmap.recycle();
//                    }
//                }
//            }
//        }).start();
    }
}
