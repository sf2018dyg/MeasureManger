package com.soonfor.measuremanager.home.lofting.activity.immloft.detail.mj_sdk;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkComponentEntity;
import com.soonfor.measuremanager.home.lofting.presenter.immloft.EditLfMemberPresenter;
import com.soonfor.measuremanager.home.lofting.view.immloft.IEditLfMemberView;
import com.soonfor.measuremanager.tools.Tokens;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by DingYg on 2018-02-10.
 * 展示清单item
 */

public class ShowLfMemberActivity extends BaseActivity<EditLfMemberPresenter> implements IEditLfMemberView {
    Activity mActivity;
    @BindView(R.id.tvfTitle)
    TextView tvfTitle;
    @BindView(R.id.etfActWith)
    TextView tvfActWith;
    @BindView(R.id.tvfWithSize)
    TextView tvfdiff_w;
    @BindView(R.id.etfActHeight)
    TextView tvfActHeight;
    @BindView(R.id.tvfheightSize)
    TextView tvfdiff_h;
    @BindView(R.id.etfActDeep)
    TextView tvfActDeep;
    @BindView(R.id.tvfdeepSize)
    TextView tvfdiff_d;
    @BindView(R.id.tvfRemark)
    TextView tvfRemark;
    @BindView(R.id.llfReturn)
    LinearLayout llfReturn;

    int position;
    MarkComponentEntity componentEntity;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_lofting_detail_loftinfo_member;
    }

    @Override
    protected void initPresenter() {
        presenter = new EditLfMemberPresenter(this);
    }

    @Override
    protected void initViews() {
        mActivity = ShowLfMemberActivity.this;
        componentEntity = getIntent().getParcelableExtra(Tokens.Lofing.SKIP_ENTER_SHOWMEMBER_SIZE_STRIGN);
        if(componentEntity!=null){
            tvfTitle.setText(componentEntity.getName() + "放样");
            tvfActWith.setText(componentEntity.getActWidth()+"");
            tvfActHeight.setText(componentEntity.getActHeight()+"");
            tvfActDeep.setText(componentEntity.getActDeep()+"");
            tvfdiff_w.setText(componentEntity.getDiffersize_w()+"");
            tvfdiff_h.setText(componentEntity.getDiffersize_h()+"");
            tvfdiff_d.setText(componentEntity.getDiffersize_d()+"");
            tvfRemark.setText(componentEntity.getRemark());
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }

    @OnClick({R.id.llfReturn,})
    void clickListenergy(View v) {
        switch (v.getId()) {
            case R.id.llfReturn:
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }

    @Override
    public void showDownLoading() {

    }

    @Override
    public void hideDownLoading() {

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
