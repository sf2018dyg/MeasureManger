package com.soonfor.measuremanager.me.activity;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.soonfor.repository.tools.ActivityUtils;
import com.soonfor.updateutil.CustomDialog;
import com.soonfor.updateutil.IntranetUpdateManager;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.tools.APKVersionUtils;
import com.soonfor.measuremanager.tools.MyToast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-02-22.
 * 关于我们
 */

public class AboutUsActivity extends BaseActivity {

    private IntranetUpdateManager intUpdateManager;
    @BindView(R.id.tvfVersion)
    TextView tvfVersion;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_aboutus;
    }

    @Override
    protected void initPresenter() {

    }
    @Override
    protected void initViews() {
        tvfVersion.setText(" V"+ APKVersionUtils.getVersionName(this));
    }
    @Override
    protected void updateViews(boolean isRefresh) {}

    @OnClick(R.id.llfCheck)
    void check(){
        //        String url[] = new String[]{Hawk.get(SoonforApplication.ServerAdr, "") + "/sfapi/update/getVersions?path=Logfiles&fileName=versionInfo.txt",
//                Hawk.get(SoonforApplication.ServerAdr, "") + "/sfapi/update/downloadFile?path=Logfiles&fileName="};
        String url[] = new String[]{"http://61.145.173.8:8084/MobileGroup/Update/LFB/version.txt", "http://61.145.173.8:8084/MobileGroup/Update/LFB/"};
        //内网 WarehousetManager.apk
        intUpdateManager = new IntranetUpdateManager(this,
                url,"com.soonfor.measuremanager.myfileprovider");
        new Thread(checkSelfUpdateInt).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntranetUpdateManager.INSTALL_PACKAGES_REQUESTCODE) {
            intUpdateManager.checkIsAndroid();
        }
    }
    Dialog dialog;
    //内网更新
    Runnable checkSelfUpdateInt = new Runnable() {
        @Override
        public void run() {
            // TODO 这里写上传逻辑
            try {
                int localVerCode = intUpdateManager.getLocalVerCode();
                int serverVerCode = intUpdateManager.getServerVerCode();
                // 如果服务器版本高于本地版本则提示更新
                if (serverVerCode > localVerCode) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (ActivityUtils.isRunning(AboutUsActivity.this)) {
                                if (System.currentTimeMillis() - SoonforApplication.lastUpdateTime > 0) {
                                    SoonforApplication.lastUpdateTime = System.currentTimeMillis();
                                    dialog = CustomDialog.createUpdateDialog(AboutUsActivity.this,
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                    dialog.setCancelable(true);
                                                    intUpdateManager.showDownloadDialog();
                                                }
                                            });
                                    dialog.show();
                                }
                            }
                        }
                    });
                }else {
                    MyToast.showToast(AboutUsActivity.this,"当前版本已是最新");
                }
            } catch (Exception e) {
            }
        }
    };
}
