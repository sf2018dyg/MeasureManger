package com.soonfor.measuremanager.me.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.me.adapter.MyGrowthListAdapter;
import com.soonfor.measuremanager.me.bean.MyGrowthBean;
import com.soonfor.measuremanager.me.bean.MyGrowthItemBean;
import com.soonfor.measuremanager.me.presenter.mygrowth.IMyGrowthView;
import com.soonfor.measuremanager.me.presenter.mygrowth.MyGrowthPresenter;
import com.soonfor.measuremanager.view.LineChartView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2018/1/18 0018.
 * 成长值
 */

public class MyGrowthActivity extends BaseActivity<MyGrowthPresenter> implements IMyGrowthView {

    @BindView(R.id.lineChartView)
    LineChartView lineChartView;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvGrowth)
    TextView tvGrowth;
    private MyGrowthListAdapter adapter;

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_growth;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.line)));
        List<LineChartView.Data> dataList = new ArrayList<>();
        LineChartView.Data data3 = new LineChartView.Data(0);
        dataList.add(data3);
        LineChartView.Data data = new LineChartView.Data(200);
        dataList.add(data);
        LineChartView.Data data1 = new LineChartView.Data(2000);
        dataList.add(data1);
        LineChartView.Data data5 = new LineChartView.Data(1000);
        dataList.add(data5);
        LineChartView.Data data2 = new LineChartView.Data(3600);
        dataList.add(data2);
        LineChartView.Data data4 = new LineChartView.Data(3600);
        dataList.add(data4);
        lineChartView.setData(dataList);
        lineChartView.setRulerYSpace(10);
        lineChartView.setStepSpace(50);
        lineChartView.playAnim();
    }

    /**
     * 初始化presenter
     */
    @Override
    protected void initPresenter() {
        presenter = new MyGrowthPresenter(this);
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
    public void setData(MyGrowthBean growthBean) {
        tvGrowth.setText(growthBean.getData().getCurrentValue() + "");
    }

    @Override
    public void setList(MyGrowthItemBean itemBean) {
        adapter = new MyGrowthListAdapter(this,itemBean);
        recyclerView.setAdapter(adapter);
    }
}
