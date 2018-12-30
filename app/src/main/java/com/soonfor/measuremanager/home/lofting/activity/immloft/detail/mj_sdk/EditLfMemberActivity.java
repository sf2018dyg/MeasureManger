package com.soonfor.measuremanager.home.lofting.activity.immloft.detail.mj_sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkComponentEntity;
import com.soonfor.measuremanager.home.lofting.presenter.immloft.EditLfMemberPresenter;
import com.soonfor.measuremanager.home.lofting.view.immloft.IEditLfMemberView;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.Tokens;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by DingYg on 2018-02-10.
 * 编辑清单Item
 */

public class EditLfMemberActivity extends BaseActivity<EditLfMemberPresenter> implements IEditLfMemberView {

    Activity mActivity;
    @BindView(R.id.imgfTranslucence)
    ImageView imgfTranslucence;
    @BindView(R.id.tvfLfMemberTitle)
    TextView tvfTitle;
    @BindView(R.id.etfWith)
    EditText etfWith;
    @BindView(R.id.etfheight)
    EditText etfHight;
    @BindView(R.id.etfdeep)
    EditText etfDeep;
    @BindView(R.id.etfMark)
    EditText etfMark;
    @BindView(R.id.tvfWithSize)
    TextView tvfWSize;
    @BindView(R.id.tvfheightSize)
    TextView tvfHSize;
    @BindView(R.id.tvfdeepSize)
    TextView tvfDSize;
    @BindView(R.id.rlfCancle)
    RelativeLayout rlfCancle;
    @BindView(R.id.rlfSure)
    RelativeLayout rlfSure;
    @BindView(R.id.llfBottom)
    LinearLayout llfBottom;
    int position;
    MarkComponentEntity lfMember;
    int int_with, int_height, int_deep;


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_lofting_editlfmember;
    }

    @Override
    protected void initPresenter() {
        presenter = new EditLfMemberPresenter(this);
    }

    @Override
    protected void initViews() {
        mActivity = EditLfMemberActivity.this;
        Bundle bundle = mActivity.getIntent().getExtras();
        position = bundle.getInt(Tokens.Lofing.SKIP_ENTER_EDIT_SIZE_POSITION, 0);
        lfMember = bundle.getParcelable(Tokens.Lofing.SKIP_ENTER_EDIT_SIZE_STRIGN);
        if (lfMember != null) {
            tvfTitle.setText(lfMember.getName() + "放样尺寸");
            if (lfMember.getEditState() == 2 || lfMember.getEditState() == 3) {//已编辑
                int_with = lfMember.getActWidth();
                etfWith.setText(int_with + "");
                etfWith.setSelection((int_with + "").length());
                tvfWSize.setText(lfMember.getDiffersize_w() + "");

                int_height = lfMember.getActHeight();
                etfHight.setText(int_height + "");
                etfHight.setSelection((int_height + "").length());
                tvfHSize.setText(lfMember.getDiffersize_h() + "");

                int_deep = lfMember.getActDeep();
                etfDeep.setText(int_deep + "");
                etfDeep.setSelection((int_deep + "").length());
                tvfDSize.setText(lfMember.getDiffersize_d() + "");
            }
            if (!lfMember.getRemark().equals("")) {
                etfMark.setText(lfMember.getRemark());
                etfMark.setSelection(lfMember.getRemark().length());
            }
            etfWith.addTextChangedListener(withWatcher);
            etfHight.addTextChangedListener(heightWatcher);
            etfDeep.addTextChangedListener(deepWatcher);
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }

    /**
     * 提交放样数据成功
     */
    public void RefreshAfterSetSuccess() {
        Intent intent = new Intent(mActivity, LoftListSecondActivity.class);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @OnClick({R.id.rlfCancle, R.id.rlfSure, R.id.imgfTranslucence})
    void clickListenergy(View v) {
        switch (v.getId()) {
            case R.id.rlfCancle:
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.rlfSure:
                String remark = etfMark.getText().toString();
                String act_w = etfWith.getText().toString();
                String act_h = etfHight.getText().toString();
                String act_d = etfDeep.getText().toString();
                boolean flag = false;
                if (!act_w.equals("")) {
                    flag = true;
                } else {
                    act_w = "0";
                }
                if (!act_h.equals("")) {
                    flag = true;
                } else {
                    act_h = "0";
                }
                if (!act_d.equals("")) {
                    flag = true;
                } else {
                    act_d = "0";
                }
                if (remark != null && !remark.trim().equals("")) {
                    flag = true;
                }
                if (flag) {
                    int size[] = {Integer.parseInt(act_w), Integer.parseInt(act_h), Integer.parseInt(act_d)};
                    presenter.saveLoftItemData(lfMember.getObjId(), size, remark.trim());
                } else {
                    MyToast.showToast(mActivity, "未做任何改动，无需提交！");
                }
                break;
            case R.id.imgfTranslucence:
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }


    /**
     * 动态计算相差-宽(绝对值)
     */
    private TextWatcher withWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString() != null && !s.toString().trim().equals("")) {
                try {
                    int_with = Integer.parseInt(s.toString().trim());
                    int Tu_With = lfMember.getWidth();//图纸放样尺寸
                    tvfWSize.setText("" + (int_with - Tu_With));
                } catch (Exception ee) {
                    if (ee.toString().contains("NumberFormatException")) {
                        MyToast.showToast(mActivity, "输入值超出范围");
                    }
                }
            } else {
                tvfWSize.setText("");
            }
        }
    };
    /**
     * 动态计算相差-高(绝对值)
     */
    private TextWatcher heightWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString() != null && !s.toString().trim().equals("")) {
                try {
                    int_height = Integer.parseInt(s.toString().trim());
                    int Tu_Height = lfMember.getHeight();//图纸放样尺寸
                    tvfHSize.setText("" + (int_height - Tu_Height));
                } catch (Exception ee) {
                    if (ee.toString().contains("NumberFormatException")) {
                        MyToast.showToast(mActivity, "输入值超出范围");
                    }
                }
            } else {
                tvfHSize.setText("");
            }
        }
    };
    /**
     * 动态计算相差-厚(绝对值)
     */
    private TextWatcher deepWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString() != null && !s.toString().trim().equals("")) {
                try {
                    int_deep = Integer.parseInt(s.toString().trim());
                    int Tu_Deep = lfMember.getDeep();//图纸放样尺寸
                    tvfDSize.setText("" + (int_deep - Tu_Deep));
                } catch (Exception ee) {
                    if (ee.toString().contains("NumberFormatException")) {
                        MyToast.showToast(mActivity, "输入值超出范围");
                    }
                }
            } else {
                tvfDSize.setText("");
            }
        }
    };

    @Override
    public void showDownLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void hideDownLoading() {
        mLoadingDialog.dismiss();
    }
}
