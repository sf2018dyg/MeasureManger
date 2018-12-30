package com.soonfor.measuremanager.me.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.me.adapter.PerformanceAdapter;
import com.soonfor.measuremanager.me.bean.PerformanceBean;
import com.soonfor.measuremanager.me.presenter.historyperformance.HistoryPerformancePresenter;
import com.soonfor.measuremanager.me.presenter.historyperformance.IHistoryPerformanceView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2018/1/10 0010.
 * 历史绩效
 */

public class HistoryPerformanceActivity extends BaseActivity<HistoryPerformancePresenter> implements IHistoryPerformanceView {

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

    }

    /**
     * 初始化presenter
     */
    @Override
    protected void initPresenter() {
        presenter = new HistoryPerformancePresenter(this,listView);
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
    public void setListData(PerformanceBean.DataBean bean) {
        adapter = new PerformanceAdapter(this, bean);
        listView.setAdapter(adapter);
    }
}
