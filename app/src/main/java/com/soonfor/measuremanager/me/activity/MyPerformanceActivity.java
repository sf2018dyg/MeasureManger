package com.soonfor.measuremanager.me.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.me.adapter.PerformanceAdapter;
import com.soonfor.measuremanager.me.bean.MyPerformanceBean;
import com.soonfor.measuremanager.me.bean.PerformanceBean;
import com.soonfor.measuremanager.me.presenter.performance.IPerformanceView;
import com.soonfor.measuremanager.me.presenter.performance.PerformancePresenter;
import com.soonfor.measuremanager.tools.Tokens;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的绩效
 * Created by Administrator on 2018/1/10 0010.
 */

public class MyPerformanceActivity extends BaseActivity<PerformancePresenter> implements IPerformanceView {

    @BindView(R.id.tv_performance_amount_month)
    TextView tvPerformanceAmountMonth;
    @BindView(R.id.line_performance)
    LinearLayout linePerformance;
    @BindView(R.id.tv_performance_amount)
    TextView tvPerformanceAmount;
    @BindView(R.id.line)
    LinearLayout line;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.linearPerformanceAmount)
    LinearLayout linearPerformanceAmount;

    private PerformanceAdapter adapter;

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_performance;
    }

    /**
     * 初始化presenter
     */
    @Override
    protected void initPresenter() {
        presenter = new PerformancePresenter(this);
    }
    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        int jxValue = getIntent().getExtras().getInt(Tokens.Mine.SKIP_TO_JX_ACTIVITY, 0);
        tvPerformanceAmountMonth.setText(jxValue + "");
        tvPerformanceAmount.setText(jxValue + "元");
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void setData(MyPerformanceBean bean) {
        tvPerformanceAmountMonth.setText(bean.getData().getMonthAmount() + "");
        tvPerformanceAmount.setText(bean.getData().getTotalAmount() + "元");
    }

    @Override
    public void setListData(PerformanceBean.DataBean bean) {
        adapter = new PerformanceAdapter(this, bean);
        listview.setAdapter(adapter);
    }

    @OnClick(R.id.linearPerformanceAmount)
    public void onViewClicked() {
        startNewAct(HistoryPerformanceActivity.class);
    }
}
