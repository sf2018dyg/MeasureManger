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
import com.soonfor.measuremanager.home.lofting.presenter.detail.LiangChiInfoPresenter;
import com.soonfor.measuremanager.home.lofting.view.detail.ILiangchiInfoView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DingYg on 2018-02-01.
 * 量尺信息
 */

public class LiangchiInfoFragment extends BaseFragment<LiangChiInfoPresenter> implements ILiangchiInfoView {

    Unbinder unbinder;
    @BindView(R.id.houseType)
    BaseHouseTypeListView houseTypeView;
    @BindView(R.id.lclistError)
    TextView lclistError;
    String orderNo;
    String lcTaskNo;//复尺任务号
    List<String> housePics = null;
    private int picIndex = 0;//户型图的位置
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
        return R.layout.fragment_liangchi_measurement;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        orderNo = getArguments().getString("orderNo", "");
        //通过订单号取一下量尺任务号
        presenter.getLiangchiTaskNoByOrderNo(orderNo);
    }

    /**
     * 获取量尺任务号成功后请求数据
     */
    public void postDataWithLcTaskNo(String newTaskNo) {
        lcTaskNo = newTaskNo;
        presenter.getData(newTaskNo, "measure", orderNo);
    }

    @Override
    protected void initPresenter() {
        presenter = new LiangChiInfoPresenter(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
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
                showNoDataHint("量尺清单为空");
            }
        } catch (Exception e) {
            showNoDataHint("请求出错啦");
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        if(SoonforApplication.pathIndex_liangchi>=0){
            refreshData(SoonforApplication.pathIndex_liangchi);
        }
        SoonforApplication.pathIndex_liangchi = -1;
    }
    //刷新数据
    private void refreshData(int position){
        houseTypeView.initHoustTypeListView(getActivity(), "量尺清单",mrList, position);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}