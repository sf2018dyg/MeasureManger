package com.soonfor.measuremanager.home.lofting.activity.immloft.detail;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.bases.BaseHouseTypeListView;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.MeasureResultEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.taskinfo.TaskCompleteInfoBean;
import com.soonfor.measuremanager.home.lofting.presenter.detail.FuchiInfoPresenter;
import com.soonfor.measuremanager.home.lofting.view.detail.IFuchiInfoView;
import com.soonfor.measuremanager.tools.DataTools;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DingYg on 2018-02-11.
 * 复尺清单
 */

public class LookSurveyListActivity extends BaseActivity<FuchiInfoPresenter> implements IFuchiInfoView {

    private Activity mActivity;
    @BindView(R.id.houseType)
    BaseHouseTypeListView houseTypeView;
    @BindView(R.id.lclistError)
    TextView lclistError;
    String orderNo;
    String fcTaskNo;//复尺任务号
    String contractNo;//合同号
    String houseName;//户型名称
    List<String> housePics = null;
    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_loft_fucilist;
    }

    @Override
    protected void initPresenter() {
        mActivity = LookSurveyListActivity.this;
        presenter = new FuchiInfoPresenter(this);
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        Bundle bundle = getIntent().getExtras();
        fcTaskNo = bundle.getString("FcTaskNo", "");
        orderNo = bundle.getString("orderNo", "");
        contractNo = bundle.getString("contractNo", "");
        houseName = bundle.getString("HouseName","");
        if (!contractNo.equals("") && contractNo.contains("-")) {
            contractNo = contractNo.split("-")[1];
        }
        showLoadingDialog();
        if (fcTaskNo.equals("")) {//复尺任务号为空,则通过订单号取一下
            presenter.getFuchiTaskNoByOrderNo(mActivity, 2, orderNo);
        } else {
            presenter.getData(2, fcTaskNo, "remeasure", orderNo, contractNo);
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }


    /**
     * 获取复尺任务号成功后请求数据
     */
    public void postDataWithFcTaskNo(boolean isFuchi, String newTaskNo) {
        fcTaskNo = newTaskNo;
        if (isFuchi) {//有复尺
            presenter.getData(2, newTaskNo, "remeasure", orderNo, contractNo);
        } else {//没有复尺，只有量尺
            presenter.getData(2, newTaskNo, "measure", orderNo, contractNo);
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
                List<MeasureResultEntity> resultEntities;
                if(tcBean.getMeasureInfo().size()>1){
                    resultEntities = DataTools.getHtList(tcBean.getMeasureInfo(),houseName);
                }else{
                    resultEntities = tcBean.getMeasureInfo();
                }
                houseTypeView.initHoustTypeListView(mActivity,"复尺清单",resultEntities, 0);
            } else {
                showNoDataHint("测量清单为空");
            }
            closeLoadingDialog();
        } catch (Exception e) {
            e.printStackTrace();
            closeLoadingDialog();
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

}
