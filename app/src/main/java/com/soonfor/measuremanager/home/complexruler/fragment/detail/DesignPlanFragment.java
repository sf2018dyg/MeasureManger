package com.soonfor.measuremanager.home.complexruler.fragment.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.home.complexruler.presenter.detail.FuChiDesignPlanPresenter;
import com.soonfor.measuremanager.home.lofting.adapter.detail.DesignPlanListAdapter;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Design.DesignPlanBean;
import com.soonfor.measuremanager.home.lofting.model.bean.taskinfo.TaskCompleteInfoBean;
import com.soonfor.measuremanager.home.lofting.view.detail.IDesignPlanView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/2 9:09
 * 邮箱：suibozhu@139.com
 * 设计方案
 */

public class DesignPlanFragment extends BaseFragment<FuChiDesignPlanPresenter> implements IDesignPlanView {

    Unbinder unbinder;
    Activity mActivity;
    @BindView(R.id.rvfDesignPlan)
    RecyclerView mRecyclerView;
    private DesignPlanListAdapter dpAdapter;
    //private List<DesignPlanBean> dpList = new ArrayList<>();
    private DesignPlanBean dplb;
    private RecyclerView.LayoutManager mLayoutManager;
    String taskNo;
    String orderNo;
    @BindView(R.id.llftxterror)
    LinearLayout llftxterror;

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        dpAdapter = new DesignPlanListAdapter(mActivity, dplb);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(dpAdapter);
        return rootView;
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_designplan;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        Bundle bundle = getArguments();
        taskNo = bundle.getString("taskNo","");
        orderNo = bundle.getString("orderNo","");
        presenter.getDatas(taskNo,orderNo);
    }

    @Override
    protected void initPresenter() {
        presenter = new FuChiDesignPlanPresenter(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    /**
     * 更新视图
     *
     * @param returnStr
     */
    public void setListView(String returnStr) {
        try {
            NLogger.w("设计方案返回json:" + returnStr);
            TaskCompleteInfoBean tcBean = null;
            Gson gson = new Gson();
            try{
                tcBean = gson.fromJson(returnStr, TaskCompleteInfoBean.class);
            }catch (Exception e){}
            if(tcBean!=null && tcBean.getDesignInfos()!=null){
                isHaveDatas(1);
                //开始组装
                dplb = tcBean.getDesignInfos();
                dpAdapter.notifyDataSetChanged(dplb);
            }else{
                isHaveDatas(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void isHaveDatas(int type){
        switch (type){
            case 0:
                mRecyclerView.setVisibility(View.GONE);
                llftxterror.setVisibility(View.VISIBLE);
                break;
            case 1:
                mRecyclerView.setVisibility(View.VISIBLE);
                llftxterror.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void showLoadingDialog() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
