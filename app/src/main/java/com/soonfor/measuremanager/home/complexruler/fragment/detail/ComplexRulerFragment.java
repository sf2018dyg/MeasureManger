package com.soonfor.measuremanager.home.complexruler.fragment.detail;

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
import com.soonfor.measuremanager.home.complexruler.presenter.detail.FuChiComplexRulerBasePresenter;
import com.soonfor.measuremanager.home.complexruler.view.detail.IFuChiComplexRulerBaseView;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.MeasureResultEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.taskinfo.TaskCompleteInfoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/31 11:37
 * 邮箱：suibozhu@139.com
 * 复尺信息
 */

public class ComplexRulerFragment extends BaseFragment<FuChiComplexRulerBasePresenter> implements IFuChiComplexRulerBaseView {

    Unbinder unbinder;
    @BindView(R.id.houseType)
    BaseHouseTypeListView houseTypeView;
    String orderNo;
    String taskNo;//复尺任务号
    @BindView(R.id.lclistError)
    TextView lclistError;
    ArrayList<String> housePics = null;
    int LiangChiStatus = 1;
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
        Bundle bundle = getArguments();
        orderNo = bundle.getString("orderNo", "");
        taskNo = bundle.getString("taskNo", "");
        LiangChiStatus = bundle.getInt("LiangChiStatus",1);
        presenter.getTaskCompleteInfo(taskNo, "remeasure", orderNo);
    }

    @Override
    protected void initPresenter() {
        presenter = new FuChiComplexRulerBasePresenter(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }


    /**
     * 更新视图
     */
    @Override
    public void showDataToView(String returnJson) {
        super.showDataToView(returnJson);
        NLogger.w("测量清单返回json:" + returnJson);
        TaskCompleteInfoBean tcBean = null;
        Gson gson = new Gson();
        try {
            tcBean = gson.fromJson(returnJson, TaskCompleteInfoBean.class);
        } catch (Exception e) {
        }
        if (tcBean != null && tcBean.getMeasureInfo() != null && tcBean.getMeasureInfo().size()>0) {
            mrList = tcBean.getMeasureInfo();
            refreshData(0);
        } else {
            if(LiangChiStatus == 4){
                showNoDataHint("暂无复尺信息");
            }else{
                showNoDataHint("尚未复尺，暂无复尺信息");
            }
        }
    }
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
        houseTypeView.initHoustTypeListView(getActivity(), "复尺清单",mrList, position);
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