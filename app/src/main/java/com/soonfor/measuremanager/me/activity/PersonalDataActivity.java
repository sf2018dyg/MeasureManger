package com.soonfor.measuremanager.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DoubleUtils;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.customportrait.headBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.bean.MeMineBean;
import com.soonfor.measuremanager.me.bean.PersonDataBean;
import com.soonfor.measuremanager.me.presenter.setting.person.IPersonView;
import com.soonfor.measuremanager.me.presenter.setting.person.PersonPresenter;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.PictureDialog;
import com.soonfor.measuremanager.tools.PreferenceUtil;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.RoundImageView;
import com.soonfor.measuremanager.view.dialog.normal.NormalDialog;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/9 0009.
 * 个人资料
 */

public class PersonalDataActivity extends BaseActivity<PersonPresenter> implements IPersonView {

    private Activity mActivity;
    @BindView(R.id.avatar)
    RoundImageView avatar;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv_name)
    TextView tvPName;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_shop)
    TextView tvShop;
    @BindView(R.id.tv_design_experience)
    TextView tvDesignExperience;
    @BindView(R.id.tv_BeGoodAt_style)
    TextView tvGoodStyle;
    @BindView(R.id.tv_design_idea)
    TextView tvfDesignIdea;

    CustomDialog ddialog;
    NormalDialog ndialog;
    MeMineBean mBean;
    PersonDataBean pdBean;
    String headImage;//头像

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_personaldata;
    }

    /**
     * 初始化presenter
     */
    @Override
    protected void initPresenter() {
        presenter = new PersonPresenter(this);
        mActivity = PersonalDataActivity.this;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        ddialog = CustomDialog.getInstance();
        mBean = PreferenceUtil.getPersonalInfo(Tokens.Mine.SP_PERSONALINFO);
        presenter.getData(true);
        if (mBean != null) {
            showView(1);
        }
    }

    /**
     * 更新视图控件
     *
     * @param isRefresh
     */
    @Override
    protected void updateViews(boolean isRefresh) {
    }


    @OnClick({R.id.relative_avatar, R.id.avatar, R.id.relative_name, R.id.relative_phone_number, R.id.relative_qr_code,
            R.id.relative_shop, R.id.relative_design_experience, R.id.relative_BeGoodAt_style})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_avatar:
                new PictureDialog(PersonalDataActivity.this).show();
                break;
            case R.id.avatar:
                if (!DoubleUtils.isFastDoubleClick()) {
                    if (headImage != null && !headImage.equals("")) {
                        ImageUtil.previewCrmPics(mActivity, R.drawable.avatar_default, new String[]{headImage});
                    } else if (pdBean.getAvatar() != null && !pdBean.getAvatar().equals("")) {
                        ImageUtil.previewCrmPics(mActivity, R.drawable.avatar_default, new String[]{pdBean.getAvatar()});
                    }
                }
                break;
            case R.id.relative_name:
                ModifyAndPost(-1, "名称");
                break;
            case R.id.relative_phone_number:
                ModifyAndPost(3, "电话");
                break;
            case R.id.relative_qr_code:
                startNewAct(QrCodeActivity.class);
                break;
            case R.id.relative_shop:
                ModifyAndPost(-1, "门店");
                break;
            case R.id.relative_design_experience:
                ModifyAndPost(-1, "设计经验");
                break;
            case R.id.relative_BeGoodAt_style:
                ModifyAndPost(-1, "擅长风格");
                break;
        }
    }

    @Override
    public void showLoadingDialog() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void setPersonData(PersonDataBean bean) {
        try {
            pdBean = bean;
            if(mBean==null){
                showView(2);
            }
            tvPhoneNumber.setText(bean.getPhone());
            tvDesignExperience.setText(bean.getDesign_experience());
            tvGoodStyle.setText(bean.getGoodstyles());
            tvfDesignIdea.setText(bean.getDesign_idea());
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    private void showView(int type) {
        if(type==1){
            imageLoading(mBean.getAvatar(), avatar, R.drawable.avatar_default);
            headImage = mBean.getAvatar();
            tvPosition.setText(mBean.getPost());
            tvPName.setText(mBean.getName());
            tvShop.setText(mBean.getStoreName());
        }else if(type==2) {
            imageLoading(pdBean.getAvatar(), avatar, R.drawable.avatar_default);
            headImage = pdBean.getAvatar();
            tvPosition.setText(pdBean.getPost());
            tvPName.setText(pdBean.getNickName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    if (data != null) {
                        List<LocalMedia> medias = PictureSelector.obtainMultipleResult(data);
                        if (medias != null && medias.size() > 0) {
                            //更新头像
                            presenter.uploadHeadImage(medias.get(0));
                        }
                    }
                    break;
            }
            //tvNickName.setText(data.getStringExtra("nickName"));
        }
    }

    /**
     * 修改并请求后台
     */
    private void ModifyAndPost(final int type, String hintText) {
        int intputType = InputType.TYPE_CLASS_TEXT;
        if (hintText.equals("电话")) {
            intputType = InputType.TYPE_CLASS_PHONE;
        } else {
            intputType = InputType.TYPE_CLASS_TEXT;
        }
        ndialog = ddialog.getInputDialog(mActivity, "修改" + hintText, "请填写您要修改的" + hintText,
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick(View view) {
                        EditText et = (EditText) view.getTag();
                        ndialog.dismiss();
                        if (et != null && et.getText() != null && !et.getText().toString().trim().equals("")) {
                            if (type > 0) {
                                presenter.updateInfo(type, et.getText().toString().trim(), true);
                            }
                        }
                    }
                }, 0, intputType);
    }

    /**
     * 修改成功后刷新界面
     */
    public void refreshAfterModifed(int infoType, String newValue) {
        switch (infoType) {
            case 1://性别

                break;
            case 2://出生年月
                break;
            case 3://电话
                tvPhoneNumber.setText(newValue);
                break;
            case 4://头像
                headImage = newValue;
                imageLoading(newValue, avatar, R.drawable.avatar_default);
                if (mBean != null) {
                    mBean.setAvatar(newValue);
                    PreferenceUtil.commitPersonalInfo(Tokens.Mine.SP_PERSONALINFO, mBean);
                }
                break;
        }
    }
}
