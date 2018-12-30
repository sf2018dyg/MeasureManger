package com.soonfor.measuremanager.me.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.me.adapter.PerformanceAdapter;
import com.soonfor.measuremanager.me.bean.PerformanceBean;
import com.soonfor.measuremanager.me.presenter.monthperformance.IMonthPerformanceView;
import com.soonfor.measuremanager.me.presenter.monthperformance.MonthPerformancePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/19 0019.
 * 当月工分
 */

public class MonthPerformanceActivity extends BaseActivity<MonthPerformancePresenter> implements IMonthPerformanceView {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipe_refresh)
    SmartRefreshLayout swipeRefresh;
    private PerformanceAdapter adapter;

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_history_performance;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        String title = getIntent().getStringExtra("monthType");
        ((TextView)toolbar.findViewById(R.id.tvfTitile)).setText(title);
        setHead(true, getIntent().getStringExtra("monthType"));
    }

    /**
     * 初始化presenter
     */
    @Override
    protected void initPresenter() {
        presenter = new MonthPerformancePresenter(this, getIntent().getStringExtra("monthType"));
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
    public void setListData(PerformanceBean.DataBean bean) {
        adapter = new PerformanceAdapter(this, bean);
        listView.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
