package com.soonfor.measuremanager.me.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.customportrait.headBean;
import com.soonfor.measuremanager.home.lofting.activity.immloft.detail.mj_sdk.LoftListSecondActivity;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.bean.MeMineBean;
import com.soonfor.measuremanager.tools.BitmapUtils;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.EncodingUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.LogTools;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.PreferenceUtil;
import com.soonfor.measuremanager.tools.QrCodeDialog;
import com.soonfor.measuremanager.tools.Tokens;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 二维码名片
 * Created by Administrator on 2018/1/9 0009.
 */

public class QrCodeActivity extends BaseActivity implements AsyncUtils.AsyncCallback {

    @BindView(R.id.avatar)
    ImageView imgfAvatar;
    @BindView(R.id.tv_nick_name)
    TextView tvfName;
    @BindView(R.id.area)
    TextView tvfArea;
    @BindView(R.id.img_qr_code)
    ImageView imgfQrCode;
    MeMineBean mBean;
    private String avatarUrl;//请求下来的二维码地址

    private QrCodeDialog mQrCodeDialog;

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_qrcode;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        avatarUrl = "";
        mBean = PreferenceUtil.getPersonalInfo(Tokens.Mine.SP_PERSONALINFO);
        if (mBean != null) {
            imageLoading(mBean.getAvatar(), imgfAvatar, R.drawable.avatar_default);
            tvfName.setText(mBean.getName());
            tvfArea.setText(mBean.getStoreName());
            /**
             * 获取二维码名片
             */
            if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
                mLoadingDialog.show();
            }
            Request.Me.getQRcode(QrCodeActivity.this, this, PreferenceUtil.getCurrentUserBean().getUserId());

            //makeQrCode(mBean.getName(), mBean.getAvatar());
        }
        mQrCodeDialog = new QrCodeDialog(this);
        mQrCodeDialog.setGravityBottom();
    }

    /**
     * 初始化presenter
     */
    @Override
    protected void initPresenter() {
    }

    /**
     * 更新视图控件
     *
     * @param isRefresh
     */
    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @OnClick({R.id.avatar, R.id.bt_upload, R.id.bt_share})
    void preview(View v) {
        switch (v.getId()) {
            case R.id.avatar:
                if (mBean != null) {
                    ImageUtil.previewCrmPics(this, R.drawable.avatar_default, new String[]{mBean.getAvatar()});
                }
                break;
            case R.id.bt_upload:
                //获取二维码后上传
                mQrCodeDialog.show();
                break;
            case R.id.bt_share:
                if (avatarUrl == null || avatarUrl.equals("")) {
                    MyToast.showToast(this, "暂无可分享的二维码图片");
                } else {
                    PermissionsUtil.requestPermission(QrCodeActivity.this, new PermissionListener() {
                                @Override
                                public void permissionGranted(@NonNull String[] permissions) {
                                    // 在这里进行 http request.网络请求相关操作
                                    new ImageUtil.downloadImageAndShareAsyncTask(QrCodeActivity.this).execute("欢迎光临数夫家具软件", mBean.getName()+"为您分享了二维码", "", avatarUrl);
                                }

                                @Override
                                public void permissionDenied(@NonNull String[] permissions) {
                                }
                            }, Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);

                }
                break;
        }
    }

    /**
     * 生成二维码 可以设置Logo
     *
     * @param content      二维码内容
     * @param isNeedUpload 是否需要上传
     */
    public void makeQrCode(String content, boolean isNeedUpload) {
        if (content != null && !content.equals("")) {
            // 位图
            try {
                /**
                 * 参数：1.文本 2 3.二维码的宽高 4.二维码中间的那个logo
                 */
                Bitmap bitmap = EncodingUtils.createQRCode(content, 500, 500, null);
                // bitmap = EncodingUtils.createQRCode(content, 500, 500 , EncodingUtils.returnBitMap(logoUrl));//CheckBox选中就设置Logo
                if (!isNeedUpload) {
                    imgfQrCode.setImageBitmap(bitmap);
                    closeLoadingDialog();
                } else {
                    /**
                     * 上传步骤
                     * 1.保存在本地
                     * 2.上传至crm服务器
                     * 3.将图片路径上传至后台
                     */
//                    if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
//                        mLoadingDialog.show();
//                    }
                    if (bitmap != null) {
                        File file = ImageUtil.saveBitmapFile(QrCodeActivity.this, bitmap);
                        if (file != null) {
                            Request.uploadFileToCrm(QrCodeActivity.this, file, Request.UPLOADPIC,this);
                        } else {
                            closeLoadingDialog();
                        }
                    }

                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                if (!isNeedUpload) MyToast.showToast(this, "二维码生成失败");
                closeLoadingDialog();
            }
        } else {
            if (!isNeedUpload) MyToast.showToast(this, "二维码生成失败");
            closeLoadingDialog();
        }
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode) {
            case Request.Me.GETQRCOARD:
                HeadBean headBean = JsonUtils.getHeadBean(object.toString());
                if (headBean != null && headBean.isSuccess()) {
                    makeQrCode(avatarUrl, false);
                } else {
                    if (!headBean.getFaileMsg().equals(""))
                        MyToast.showToast(this, headBean.getFaileMsg());
                }
                closeLoadingDialog();
                break;
            case Request.UPLOADPIC:
                try {
                    if (object != null) {
                        if (CommonUtils.formatStr(object.getString("error")).equals("0")) {//上传文件到crmc成功
                            String picName = CommonUtils.formatStr(object.getString("title"));
                            String picUrl = "http://" + Hawk.get(SoonforApplication.ServerAdr_fj, "") + UserInfo.DOWNLOAD_FILE + CommonUtils.formatStr(object.getString("filepath"));
                            String fileSize = CommonUtils.formatStr(object.getString("filesize"));
                            Request.Me.uploadQrCode(QrCodeActivity.this, QrCodeActivity.this, picName, picUrl, fileSize);
                        } else {
                            MyToast.showFailToast(QrCodeActivity.this, "二维码上传失败");
                            closeLoadingDialog();
                        }
                    } else {
                        MyToast.showFailToast(QrCodeActivity.this, "二维码上传失败");
                        closeLoadingDialog();
                    }
                } catch (Exception ee) {
                    MyToast.showFailToast(QrCodeActivity.this, "二维码上传失败");
                    closeLoadingDialog();
                }
                break;
            case Request.Me.UPDATEQRCOARD:
                headBean = JsonUtils.getHeadBean(object.toString());
                if (headBean != null && headBean.isSuccess()) {//上传二维码成功
                    //avatarUrl = "http://" + Hawk.get(SoonforApplication.ServerAdr_fj, "") + RepUserInfo.DOWNLOAD_FILE + headBean.getData();
                    avatarUrl = headBean.getData();
                    makeQrCode(avatarUrl, false);
                } else {
                    if (!headBean.getFaileMsg().equals(""))
                        MyToast.showToast(this, headBean.getFaileMsg());
                }
                closeLoadingDialog();
                break;

        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        switch (requestCode) {
            case Request.Me.GETQRCOARD:
                closeLoadingDialog();
                MyToast.showFailToast(QrCodeActivity.this, "请求二维码失败");
                break;
            case Request.UPLOADPIC:
                closeLoadingDialog();
                MyToast.showFailToast(QrCodeActivity.this, "二维码上传失败");
                break;
            case Request.Me.UPDATEQRCOARD://上传二维码失败
                closeLoadingDialog();
                MyToast.showFailToast(QrCodeActivity.this, "二维码上传失败");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            Bitmap bitmap;
            if (data != null) {
                if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
                    mLoadingDialog.show();
                }
                String picPath = PictureSelector.obtainMultipleResult(data).get(0).getCompressPath();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                bitmap = BitmapFactory.decodeFile(picPath, options);
                // 在这里我用到的 getSmallerBitmap 非常重要，下面就要说到
                bitmap = BitmapUtils.getSmallerBitmap(bitmap);
                // 获取bitmap的宽高，像素矩阵
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int[] pixels = new int[width * height];
                bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                // 最新的库中，RGBLuminanceSource 的构造器参数不只是bitmap了
                RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
                Reader reader = new MultiFormatReader();
                Result result = null;
                // 尝试解析此bitmap，！！注意！！ 这个部分一定写到外层的try之中，因为只有在bitmap获取到之后才能解析写外部可能会有异步的问题（开始解析时bitmap为空）
                try {
                    result = reader.decode(binaryBitmap);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                    result = null;
                } catch (ChecksumException e) {
                    e.printStackTrace();
                    result = null;
                } catch (FormatException e) {
                    e.printStackTrace();
                    result = null;
                }
                //生产二维码并上传
                if (result == null) {
                    MyToast.showToast(QrCodeActivity.this, "非合法二维码");
                    closeLoadingDialog();
                } else {
                    makeQrCode(result.toString(), true);
                }
            }
        } else

        {
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (intentResult != null) {
                if (intentResult.getContents() == null) {
                } else {
                    // scanResult 为获取到的字符串
                    String scanResult = intentResult.getContents();
                    //生产二维码并上传
                    makeQrCode(scanResult.toString(), true);
                }
            } else {
                closeLoadingDialog();
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
//        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
//            if (data != null) {
//                List<LocalMedia> medias = PictureSelector.obtainMultipleResult(data);
//                if (medias != null && medias.size() != 0) {
//                    if(mLoadingDialog!=null && !mLoadingDialog.isShowing())
//                        RepRequest.uploadFileToCrm(QrCodeActivity.this, new File(medias.get(0).getCompressPath()), this);
//                }
//            }
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageUtil.deleteDir("ShareImage");
    }
}
