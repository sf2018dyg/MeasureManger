package com.soonfor.measuremanager.tools;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.home.login.activity.CustomScanAct;

import java.security.Permissions;

/**
 * Created by Administrator on 2018/1/11 0011.
 */

public class QrCodeDialog implements View.OnClickListener {
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private Activity context;
    private View dialogView;

    public QrCodeDialog(Activity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.view_dialog_qr_code, null);
        ViewTools.setButtonListener(dialogView,R.id.camera,this);
        ViewTools.setButtonListener(dialogView,R.id.choose_from_album,this);
        ViewTools.setButtonListener(dialogView,R.id.cancel,this);
        dialog = new AlertDialog.Builder(context,R.style.my_style_dialog).create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
    }

    /**
     * 显示对话框
     */
    public void show() {
        dialog.show();
        dialog.getWindow().setContentView(dialogView);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); //设置宽度
        dialog.getWindow().setAttributes(lp);
    }

    public void setGravityBottom(){
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    /**
     * 隐藏对话框
     */
    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 判断对话框是否显示
     */
    public boolean isShowing() {
        if (dialog != null) {
            return dialog.isShowing();
        }
        return false;
    }

    /**
     * 设置对话框点击对话框以外的范围是否可以关闭对话框
     */
    public void setCancelable(boolean bool) {
        dialog.setCancelable(bool);
    }

    /**
     * 相册
     */
    public void pictureSelect() {
        PermissionsUtil.requestPermission(context, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                //启动相册
                PictureSelector.create(context)
                        .openGallery(PictureMimeType.ofImage())
                        .isCamera(false)
                        .imageSpanCount(3)
                        .compress(true)
                       // .compressGrade(Luban.THIRD_GEAR)
                        .selectionMode(PictureConfig.SINGLE)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {

            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    /**
     * 二维码扫描
     */
    private void scan() {
        PermissionsUtil.requestPermission(context, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(context);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                        .setPrompt("对齐QR码/条码框内进行扫描")//写那句提示的话
                        .setOrientationLocked(false)//扫描方向固定
                        .setCaptureActivity(CustomScanAct.class) // 设置自定义的activity是CustomActivity
                        .initiateScan(); // 初始化扫描
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {

            }
        }, Manifest.permission.CAMERA);

    }

    @Override
    public void onClick(View v) {
        dialog.dismiss();
        switch (v.getId()){
            case R.id.camera:
                scan();//进入二维码扫描界面
                break;
            case R.id.choose_from_album:
                pictureSelect();//打开相册
                break;
            case R.id.cancel:
                break;
        }
    }
}