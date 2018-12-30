package com.soonfor.measuremanager.home.lofting.fragment.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.bases.BaseHouseTypeListView;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.MeasureResultEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.taskinfo.TaskCompleteInfoBean;
import com.soonfor.measuremanager.home.lofting.presenter.detail.FuchiInfoPresenter;
import com.soonfor.measuremanager.home.lofting.view.detail.IFuchiInfoView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DingYg on 2018-02-01.
 * 复尺信息
 */

public class FuchiInfoFragment extends BaseFragment<FuchiInfoPresenter> implements IFuchiInfoView {

    Unbinder unbinder;
    @BindView(R.id.houseType)
    BaseHouseTypeListView houseTypeView;
    @BindView(R.id.lclistError)
    TextView lclistError;
    String orderNo;
    String fcTaskNo;//复尺任务号
    List<MeasureResultEntity> mrList = null;

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_fuchi_complexruler;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        orderNo = getArguments().getString("orderNo", "");
        //复尺任务号为空,则通过订单号取一下量尺任务号
        presenter.getFuchiTaskNoByOrderNo(getActivity(), 1, orderNo);

    }

    @Override
    protected void initPresenter() {
        presenter = new FuchiInfoPresenter(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {}

    @Override
    public void onResume() {
        super.onResume();
        if(SoonforApplication.pathIndex_fuchi>=0){
            refreshData(SoonforApplication.pathIndex_fuchi);
        }
        SoonforApplication.pathIndex_fuchi = -1;
    }
    //刷新数据
    private void refreshData(int position){
        houseTypeView.initHoustTypeListView(getActivity(), "测量清单",mrList, position);
    }
    /**
     * 获取复尺任务号成功后请求数据
     */
    public void postDataWithFcTaskNo(boolean isFuchi, String newTaskNo) {
        fcTaskNo = newTaskNo;
        MeasureResultEntity mBean = null;
        if (isFuchi) {//有复尺
            presenter.getData(1, newTaskNo, "remeasure", orderNo, "");
        } else {//没有复尺，只有量尺
            presenter.getData(1, newTaskNo, "measure", orderNo, "");
        }
    }

    /**
     * 更新视图
     */
    @Override
    public void showDataToView(String returnJson) {
        super.showDataToView(returnJson);
        TaskCompleteInfoBean tcBean = null;
        Gson gson = new Gson();
        try {
            tcBean = gson.fromJson(returnJson, TaskCompleteInfoBean.class);
            if (tcBean != null && tcBean.getMeasureInfo() != null && tcBean.getMeasureInfo().size()>0) {
                mrList = tcBean.getMeasureInfo();
                refreshData(0);
            } else {
                showNoDataHint("测量清单为空");
            }
        } catch (Exception e) {
            showNoDataHint("请求出错啦");
        }
    }

    @Override
    public void showLoadingDialog() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void closeLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}