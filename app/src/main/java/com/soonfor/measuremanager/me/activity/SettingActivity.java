package com.soonfor.measuremanager.me.activity;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.github.dfqin.grantor.PermissionsUtil;
import com.luck.picture.lib.tools.DoubleUtils;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.home.login.activity.LoginActivity;
import com.soonfor.measuremanager.home.main.MainActivity;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.bean.MeMineBean;
import com.soonfor.measuremanager.tools.APKVersionUtils;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyDataCleanManager;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.PreferenceUtil;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.RoundImageView;
import com.soonfor.measuremanager.view.dialog.normal.NormalDialog;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-02-22.
 * 设置
 */

public class SettingActivity extends BaseActivity implements AsyncUtils.AsyncCallback {

    private Activity mActivity;
    private MeMineBean mmBean;
    CustomDialog ddialog;
    NormalDialog ndialog;
    @BindView(R.id.img_head)
    RoundImageView imgfHead;
    @BindView(R.id.tvfSName)
    TextView tvfSName;
    @BindView(R.id.tvfAccount)
    TextView tvfAccount;
    @BindView(R.id.tvfVersion)
    TextView tvfVersion;
    @BindView(R.id.tvfCache)
    TextView tvfCache;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_setting;
    }

    @Override
    protected void initPresenter() {
        mActivity = SettingActivity.this;
        ddialog = CustomDialog.getInstance();
    }

    @Override
    protected void initViews() {
        tvfAccount.setText(PreferenceUtil.getString(UserInfo.USERNAME, ""));
        tvfVersion.setText("V"+ APKVersionUtils.getVersionName(mActivity));
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mmBean = PreferenceUtil.getPersonalInfo(Tokens.Mine.SP_PERSONALINFO);
            if (mmBean != null) {
                imageLoading(mmBean.getAvatar(), imgfHead, R.drawable.avatar_default);
                tvfSName.setText(mmBean.getName() + "");
            }
            String CacheSize = MyDataCleanManager.getTotalCacheSize(mActivity);
            if (CacheSize == null || CacheSize.equals("") || CacheSize.contains("0.00MB")) {
                CacheSize = "0 KB";
            }
            tvfCache.setText(CacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.img_head, R.id.llfPersonalData, R.id.llfModifyPassword, R.id.llfVersionUpdating,
            R.id.llfClearCache, R.id.llfAboutUs, R.id.rlfExit})
    void llClickListener(View view) {
        switch (view.getId()) {
            case R.id.img_head:
                if(!DoubleUtils.isFastDoubleClick()) {
                    ArrayList<String> headList = new ArrayList<>();
                    if(mmBean!=null) {
                        ImageUtil.previewCrmPics(mActivity, R.drawable.avatar_default, new String[]{mmBean.getAvatar()});
                    }
                }
                break;
            case R.id.llfPersonalData:
                startNewAct(PersonalDataActivity.class);
                break;
            case R.id.llfModifyPassword:
                startNewAct(ModifyPawActivity.class);
                break;
            case R.id.llfVersionUpdating:
                MyToast.showToast(mActivity, "当前已是最新版");
                break;
            case R.id.llfClearCache:
                ndialog = ddialog.getNormalDialog(mActivity, "温馨提示", "此操作将会清除App运行过程中产生的缓存数据，确认要清除缓存吗?",
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick(View view) {
                                ndialog.dismiss();
                                if(mLoadingDialog!=null && !mLoadingDialog.isShowing()){
                                    mLoadingDialog.show();
                                }
                                MyDataCleanManager.clearAllCache(mActivity);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            String CacheSize = MyDataCleanManager.getTotalCacheSize(mActivity);
                                            if (CacheSize.contains("0.00")) {
                                                CacheSize = "0 KB";
                                            }
                                            tvfCache.setText(CacheSize);
                                            closeLoadingDialog();
                                            MyToast.showToast(mActivity, "清理完成!");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, 1000);
                            }
                        });
                ndialog.show();
                break;
            case R.id.llfAboutUs:
                startNewAct(AboutUsActivity.class);
                break;
            case R.id.rlfExit:
                if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
                    mLoadingDialog.show();
                }
                if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
                PreferenceUtil.commitBoolean(UserInfo.ISREMEMBER, false);
                MainActivity.FinishMainActivity();
                startNewAct(LoginActivity.class);
                finish();
                //RepRequest.sendLogout(mActivity, PreferenceUtil.getString(RepUserInfo.USERNAME, ""), this);

        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode) {
            case Request.LOGOUT:
                if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
                HeadBean headBean1 = JsonUtils.getHeadBean(object.toString());
                if (headBean1 != null) {
                    if ((headBean1.getMsgcode() == 0 || headBean1.isSuccess())) {
                        startNewAct(LoginActivity.class);
                        finish();
                        MainActivity.FinishMainActivity();
                    }else
                        MyToast.showFailToast(mActivity, "登出不成功："+headBean1.getFaileMsg());
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

}
