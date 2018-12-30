package com.soonfor.measuremanager.me.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.me.bean.SaleTargetBean;
import com.soonfor.measuremanager.me.presenter.salestarget.ISalesTargetView;
import com.soonfor.measuremanager.me.presenter.salestarget.SalesTargetPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/10 0010.
 */

public class SalesTargetFragment extends BaseFragment<SalesTargetPresenter> implements ISalesTargetView {

    @BindView(R.id.tv_personal_target_complete_schedule)
    TextView tvPersonalTargetCompleteSchedule;
    @BindView(R.id.progressbar_personal_target_complete_schedule)
    ProgressBar progressbarPersonalTargetCompleteSchedule;
    @BindView(R.id.start_amount_personal_target_complete_schedule)
    TextView startAmountPersonalTargetCompleteSchedule;
    @BindView(R.id.end_amount_personal_target_complete_schedule)
    TextView endAmountPersonalTargetCompleteSchedule;
    @BindView(R.id.tv_personal_excellent_target_complete_schedule)
    TextView tvPersonalExcellentTargetCompleteSchedule;
    @BindView(R.id.progressbar_personal_excellent_target_complete_schedule)
    ProgressBar progressbarPersonalExcellentTargetCompleteSchedule;
    @BindView(R.id.start_amount_personal_excellent_target_complete_schedule)
    TextView startAmountPersonalExcellentTargetCompleteSchedule;
    @BindView(R.id.end_amount_personal_excellent_target_complete_schedule)
    TextView endAmountPersonalExcellentTargetCompleteSchedule;
    @BindView(R.id.tv_store_target_complete_schedule)
    TextView tvStoreTargetCompleteSchedule;
    @BindView(R.id.progressbar_store_target_complete_schedule)
    ProgressBar progressbarStoreTargetCompleteSchedule;
    @BindView(R.id.start_amount_store_target_complete_schedule)
    TextView startAmountStoreTargetCompleteSchedule;
    @BindView(R.id.end_amount_store_target_complete_schedule)
    TextView endAmountStoreTargetCompleteSchedule;
    @BindView(R.id.tv_store_excellent_target_complete_schedule)
    TextView tvStoreExcellentTargetCompleteSchedule;
    @BindView(R.id.progressbar_store_excellent_target_complete_schedule)
    ProgressBar progressbarStoreExcellentTargetCompleteSchedule;
    @BindView(R.id.start_amount_store_excellent_target_complete_schedule)
    TextView startAmountStoreExcellentTargetCompleteSchedule;
    @BindView(R.id.end_amount_store_excellent_target_complete_schedule)
    TextView endAmountStoreExcellentTargetCompleteSchedule;
    Unbinder unbinder;

    public static SalesTargetFragment newInstance(int index) {
        Bundle args = new Bundle();
        args.putInt("index", index);
        SalesTargetFragment fragment = new SalesTargetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_sales_target;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {

    }

    /**
     * 初始化presenter
     */
    @Override
    protected void initPresenter() {
        presenter = new SalesTargetPresenter(this,getActivity().getIntent().getIntExtra("index",0));
    }

    /**
     * 更新视图控件
     *
     * @param isRefresh
     */
    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setData(SaleTargetBean bean) {
        //个人基本目标
        tvPersonalTargetCompleteSchedule.setText((int)(bean.getPersonalTarget().getBaseRate() * 100) + "%");
        progressbarPersonalTargetCompleteSchedule.setProgress((int) (bean.getPersonalTarget().getBaseRate()*100));
        startAmountPersonalTargetCompleteSchedule.setText(getString(R.string.yuan) + bean.getPersonalTarget().getBaseActual());
        endAmountPersonalTargetCompleteSchedule.setText(getString(R.string.yuan) + bean.getPersonalTarget().getBaseTarget());
        //个人优秀目标
        tvPersonalExcellentTargetCompleteSchedule.setText((int)(bean.getPersonalTarget().getExcellentRate() * 100) + "%");
        startAmountPersonalExcellentTargetCompleteSchedule.setText(getString(R.string.yuan) + bean.getPersonalTarget().getExcellentActual());
        endAmountPersonalExcellentTargetCompleteSchedule.setText(getString(R.string.yuan) + bean.getPersonalTarget().getExcellentTarget());
        progressbarPersonalExcellentTargetCompleteSchedule.setProgress((int) (bean.getPersonalTarget().getExcellentRate()*100));
        //门店基本目标
        tvStoreTargetCompleteSchedule.setText((int)(bean.getStoreTarget().getBaseRate() * 100) + "%");
        progressbarStoreTargetCompleteSchedule.setProgress((int) (bean.getStoreTarget().getBaseRate()*100));
        startAmountStoreTargetCompleteSchedule.setText(getString(R.string.yuan) + bean.getStoreTarget().getBaseActual());
        endAmountStoreTargetCompleteSchedule.setText(getString(R.string.yuan) + bean.getStoreTarget().getBaseTarget());
        //门店优秀目标
        tvStoreExcellentTargetCompleteSchedule.setText((int)(bean.getStoreTarget().getExcellentRate() * 100) + "%");
        startAmountStoreExcellentTargetCompleteSchedule.setText(getString(R.string.yuan) + bean.getStoreTarget().getExcellentActual());
        endAmountStoreExcellentTargetCompleteSchedule.setText(getString(R.string.yuan) + bean.getStoreTarget().getExcellentTarget());
        progressbarStoreExcellentTargetCompleteSchedule.setProgress((int) (bean.getStoreTarget().getExcellentRate()*100));
    }
}
