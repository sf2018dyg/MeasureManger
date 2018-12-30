package com.soonfor.repository.ui.activity.staff;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dingyg.richeditor.utils.ImageUtils;
import com.orhanobut.hawk.Hawk;
import com.soonfor.repository.R;
import com.soonfor.repository.http.httptools.RepAsyncUtils;
import com.soonfor.repository.tools.DataTools;
import com.soonfor.repository.tools.Tokens;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-08-22 11:22
 * 邮箱：dingyg012655@126.com
 */
public class ArtificialStaffActivity extends Activity implements RepAsyncUtils.AsyncCallback {

    WebView webView;
    TextView tvfTitle;
    ImageView imgfLeft;
    private final int REQUEST_CODE = 1001;
    private ValueCallback<Uri> mUploadCallbackBelow;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artificialstaff);
        initViews();
    }

    private void findViewById() {
        webView = this.findViewById(R.id.webView);
        tvfTitle = this.findViewById(R.id.tvfTitile);
        imgfLeft = this.findViewById(R.id.ivfLeft);
        imgfLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @SuppressLint("JavascriptInterface")
    protected void initViews() {
        findViewById();
        tvfTitle.setText("人工客服");
        WebSettings ws = webView.getSettings();
        // 设置WebView属性，能够执行JavaScript脚本
        ws.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        ws.setSupportZoom(true);
        // 设置出现缩放工具
        ws.setBuiltInZoomControls(false);
        // 为图片添加放大缩小功能
        ws.setUseWideViewPort(true);
        webView.setInitialScale(70);   //100代表不缩放

        webView.setWebViewClient(new MyWebViewClient());//限制在webview中打开网页，不用默认浏览器
        webView.setWebChromeClient(new WebChromeClient() {
            /**
             * 8(Android 2.2) <= API <= 10(Android 2.3)回调此方法
             */
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                //Log.e("WangJ", "运行方法 openFileChooser-1");
                // (2)该方法回调时说明版本API < 21，此时将结果赋值给 mUploadCallbackBelow，使之 != null
                mUploadCallbackBelow = uploadMsg;
                ImageUtils.callGallery(ArtificialStaffActivity.this, MimeType.ofImage(), 1, REQUEST_CODE);
            }

            /**
             * 11(Android 3.0) <= API <= 15(Android 4.0.3)回调此方法
             */
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                openFileChooser(uploadMsg);
            }

            /**
             * 16(Android 4.1.2) <= API <= 20(Android 4.4W.2)回调此方法
             */
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooser(uploadMsg);
            }

            /**
             * API >= 21(Android 5.0.1)回调此方法
             */
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                // (1)该方法回调时说明版本API >= 21，此时将结果赋值给 mUploadCallbackAboveL，使之 != null
                mUploadCallbackAboveL = filePathCallback;
                ImageUtils.callGallery(ArtificialStaffActivity.this, MimeType.ofImage(), 9, REQUEST_CODE);
                return true;
            }
        });
        webView.loadUrl(Hawk.get(Tokens.SP_ARTIFICIALSTAFFPATH, "") + "/im/layIm.html");
    }

    @Override
    public void success(int requestCode, String data) {
        /**
         * <!DOCTYPE html>
         <html lang="en">
         <head>
         <meta charset="UTF-8">
         <title>LayIM</title>
         <link rel="stylesheet" type="text/css" href="css/main.css">
         <script type="text/javascript" src="js/main.js"></script>
         <script type="text/javascript" src="js/jquery.min.js"></script>
         </head>
         <body>
         <div id="container">
         <div class="header">
         <span id="serspn" style="float: left;"></span>
         <span style="float: right;"></span>

         </div>
         <ul class="content">
         </ul>

         <div class="footer">
         <div class="file">图册<input accept="image/*" type="file" id="uploadIMG" title=" "/></div>
         <input id="text" type="text" placeholder="请输入...">
         <span id="btn">发送</span>
         </div>
         <!-- <input id="uuid" type="hidden" value="152cea4d-65a2-4e95-af51-bd9c929ebb8d"> -->
         <input id="ip" type="hidden" value="192.168.5.77:18888">
         <input id="uploadUrl" type="hidden" value="http://192.168.5.77:62506//upc/upload?type=1">
         <input id="imgUrl" type="hidden" value="http://192.168.5.77/">
         </div>

         </body>
         </html>
         */
        // webView.loadDataWithBaseURL("http://192.168.1.236:4222/im/layIm.html",null,"text/html; charset=UTF-8",null,null);
    }

    @Override
    public void fail(int requestCode, int statusCode, String msg) {
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            imgReset();
        }
    }

    private void imgReset() {
        //设置图片的宽度为自适应屏幕宽度
        String uuid = Hawk.get(DataTools.UUID, "");
        webView.loadUrl("javascript:initUUID(\"" + uuid + "\")");
        String famtString = "javascript:var meta = document.createElement('meta');" +
                " meta.setAttribute('name', 'viewport');" +
                " meta.setAttribute('content', 'width=device-width');" +
                " document.getElementsByTagName('head')[0].appendChild(meta);";
        webView.loadUrl(famtString);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // 经过上边(1)、(2)两个赋值操作，此处即可根据其值是否为空来决定采用哪种处理方法
            if (mUploadCallbackBelow != null) {
                chooseBelow(resultCode, data);
            } else if (mUploadCallbackAboveL != null) {
                chooseAbove(resultCode, data);
            } else {
                Toast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Android API < 21(Android 5.0)版本的回调处理
     *
     * @param resultCode 选取文件或拍照的返回码
     * @param data       选取文件或拍照的返回结果
     */
    private void chooseBelow(int resultCode, Intent data) {
        try {
            if (RESULT_OK == resultCode) {
                //updatePhotos();
                if (data != null) {
                    // 这里是针对文件路径处理
//                // 指定拍照存储位置的方式调起相机
//                String filePath = Environment.getExternalStorageDirectory() + File.separator
//                        + Environment.DIRECTORY_PICTURES + File.separator;
//                String fileName = "IMG_" + DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
//                imageUri = Uri.fromFile(new File(filePath + fileName));
                    List<Uri> uriList = Matisse.obtainResult(data);
                    Uri uri;
                    if (uriList != null && uriList.size() > 0) {
                        uri = uriList.get(0);
                        mUploadCallbackBelow.onReceiveValue(uri);
                    } else {
                        mUploadCallbackBelow.onReceiveValue(null);
                    }
                } else {
                    // 以指定图像存储路径的方式调起相机，成功后返回data为空
                    mUploadCallbackBelow.onReceiveValue(imageUri);
                }
            } else {
                mUploadCallbackBelow.onReceiveValue(null);
            }
            mUploadCallbackBelow = null;
        } catch (Exception e) {
        }
    }

    /**
     * Android API >= 21(Android 5.0) 版本的回调处理
     *
     * @param resultCode 选取文件或拍照的返回码
     * @param data       选取文件或拍照的返回结果
     */
    private void chooseAbove(int resultCode, Intent data) {
        try {
            if (RESULT_OK == resultCode) {
                //updatePhotos();
                if (data != null) {
                    // 这里是针对从文件中选图片的处理
                    Uri[] results;
                    List<Uri> uriList = Matisse.obtainResult(data);
                    if (uriList != null && uriList.size() > 0) {
                        results = new Uri[uriList.size()];
                        for (int i = 0; i < uriList.size(); i++) {
                            results[i] = uriList.get(i);
                        }
                        mUploadCallbackAboveL.onReceiveValue(results);
                    } else {
                        mUploadCallbackAboveL.onReceiveValue(null);
                    }
                } else {
                    Log.e("WangJ", "自定义结果：" + imageUri.toString());
                    mUploadCallbackAboveL.onReceiveValue(new Uri[]{imageUri});
                }
            } else {
                mUploadCallbackAboveL.onReceiveValue(null);
            }
            mUploadCallbackAboveL = null;
        } catch (Exception e) {
        }
    }

    private void updatePhotos() {
        // 该广播即使多发（即选取照片成功时也发送）也没有关系，只是唤醒系统刷新媒体文件
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(imageUri);
        sendBroadcast(intent);
    }

}
