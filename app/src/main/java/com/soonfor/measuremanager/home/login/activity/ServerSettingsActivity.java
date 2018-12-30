package com.soonfor.measuremanager.home.login.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.telecom.PhoneAccountHandle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.tools.BitmapUtils;
import com.soonfor.measuremanager.tools.LogTools;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.QrCodeDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/11 0011.
 */

public class ServerSettingsActivity extends BaseActivity {
    ServerSettingsActivity mContext = this;
    @BindView(R.id.tv_sj_server_address)
    EditText tvServerAddress_sj;//数据服务器
    @BindView(R.id.ibt_sj_scan)
    ImageButton ibtScan;
//    @BindView(R.id.tv_mcode)
//    EditText tv_mcode;
    @BindView(R.id.bt_save)
    Button btSave;

    private QrCodeDialog mQrCodeDialog;
    private String actionType = "";

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_server_settings;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InFinish();
            }
        });
        mQrCodeDialog = new QrCodeDialog(this);
        mQrCodeDialog.setGravityBottom();
        tvServerAddress_sj.setText(Hawk.get(SoonforApplication.ServerAdr, ""));
        //tv_mcode.setText(Hawk.get(SoonforApplication.MerchantCode, ""));
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

    @OnClick({R.id.ibt_sj_scan, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibt_sj_scan:
                actionType = "sj_scan";
                mQrCodeDialog.show();
                break;
            case R.id.bt_save:
                if(tvServerAddress_sj.getText().toString()==null
                        || tvServerAddress_sj.getText().toString().trim().equals("")){
                    MyToast.showToast(mContext, "请输入数据服务器地址！");
                    return;
                }
                Hawk.put(SoonforApplication.MerchantCode, "mt");
//                if(tv_mcode.getText().toString()==null || tv_mcode.getText().toString().trim().equals("")){
//                    MyToast.showToast(mContext, "请输入服务号！");
//                    return;
//                }else {
//                    Hawk.put(SoonforApplication.MerchantCode, tv_mcode.getText().toString().toLowerCase().trim());
//                }
                String edtInput_sj = tvServerAddress_sj.getText().toString().trim().toLowerCase();
                if (!edtInput_sj.equals("") && edtInput_sj.startsWith("http://")) {
                    edtInput_sj = edtInput_sj.replace("http://", "");
                }
                //判断url是否符合标准
                if (Patterns.WEB_URL.matcher(edtInput_sj).matches()) {
                    //符合标准
                    for(int i=0; i<5; i++){
                        if(edtInput_sj.endsWith("/")) edtInput_sj = edtInput_sj.substring(0,edtInput_sj.length()-1);
                        else break;
                    }
                    Hawk.put(SoonforApplication.ServerAdr, edtInput_sj);
                    finish();
                } else{
                    //不符合标准
                    MyToast.showToast(mContext, "请输入正确的服务器地址！");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            Bitmap bitmap;
            if (data != null) {
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
                    if (actionType.equals("sj_scan")) {
                        tvServerAddress_sj.setText(result.toString());
                    }
                    LogTools.showLog(this, result.toString());
                } catch (NotFoundException e) {
                    e.printStackTrace();
                } catch (ChecksumException e) {
                    e.printStackTrace();
                } catch (FormatException e) {
                    e.printStackTrace();
                }
            }
        } else {
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (intentResult != null) {
                if (intentResult.getContents() == null) {
                } else {
                    // scanResult 为获取到的字符串
                    String scanResult = intentResult.getContents();
                    if (actionType.equals("sj_scan")) {
                        tvServerAddress_sj.setText(scanResult);
                    }
                    LogTools.showLog(this, scanResult);
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        InFinish();
    }

    private void InFinish() {
        if (tvServerAddress_sj.getText().toString().trim().length() == 0) {
            MyToast.showToast(mContext, "请设置服务器地址！");
            return;
        } else {
            finish();
        }
    }
}
