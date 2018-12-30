package com.soonfor.measuremanager.home.othertask.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.othertask.activity.OtherTaskDetailActivity;
import com.soonfor.measuremanager.home.othertask.activity.OtherTaskMainActivity;
import com.soonfor.measuremanager.home.othertask.activity.UpdateTaskResultActivity;
import com.soonfor.measuremanager.home.othertask.adapter.OtherTaskMainAdapter;
import com.soonfor.measuremanager.home.othertask.model.bean.OtherTaskMainBean;
import com.soonfor.measuremanager.home.othertask.presenter.OtherTaskMainPresenter;
import com.soonfor.measuremanager.home.othertask.view.IOtherTaskMainView;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.dialog.normal.NormalDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DingYg on 2018-01-29.
 */

public abstract class BaseOtherTaskMainFragment extends BaseFragment<OtherTaskMainPresenter> implements IOtherTaskMainView {

    Unbinder unbinder;
    Activity mActivity;
    @BindView(R.id.recyView)
    RecyclerView mRecyclerView;
    private OtherTaskMainAdapter goAdapter;
    private LinearLayoutManager mLayoutManager;
    CustomDialog ddialog;
    NormalDialog ndialog;
    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        ddialog = CustomDialog.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        goAdapter = new OtherTaskMainAdapter(mActivity, gettype(),adapterlistener);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(goAdapter);

        return rootView;
    }

    /**
     * 绑定布局文件
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_graborder;
    }
    @Override
    protected void initPresenter() {
        presenter = new OtherTaskMainPresenter(this);
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {}

    @Override
    protected void updateViews(boolean isRefresh) {
       mSwipeRefresh.autoRefresh();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
         if (isVisibleToUser) {
            if(gettype()==3){
                RefreshData(false);
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if(OtherTaskMainActivity.ItemPosition>=0){
            RefreshData(false);
            OtherTaskMainActivity.ItemPosition = -1;
        }
    }

    private View.OnClickListener adapterlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int position = (int) v.getTag();
            final OtherTaskMainBean taskMainBean = DataTools.ListOtherTaskMap.get(gettype()).get(position);
            switch (v.getId()) {
                case R.id.llfItem:
                    Intent intent = new Intent(mActivity, OtherTaskDetailActivity.class);
                    intent.putExtra(Tokens.OtherTask.OT_ITEMSKIPDETAIL_STATUS, gettype());
                    intent.putExtra("POSITION", position);
                    intent.putExtra(Tokens.OtherTask.OT_ITEMSKIPDETAIL_ITEM, taskMainBean);
                    mActivity.startActivity(intent);
                    break;
                case R.id.tvName:
                    break;
                case R.id.btnLookDisDetail:
                    Bundle bundle0 = new Bundle();
                    bundle0.putInt("POSITION", position);
                    bundle0.putInt("TABINDEX", 1);
                    bundle0.putInt(Tokens.OtherTask.OT_ITEMSKIPDETAIL_STATUS, gettype());
                    bundle0.putSerializable(Tokens.OtherTask.OT_ITEMSKIPDETAIL_ITEM, taskMainBean);
                    Intent intent0 = new Intent(mActivity, OtherTaskDetailActivity.class);
                    intent0.putExtras(bundle0);
                    mActivity.startActivity(intent0);
                    break;
                case R.id.btnUpdateTaskResult:
                    Intent intent1 = new Intent(mActivity, UpdateTaskResultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("VIEWTYPE", 1);
                    bundle.putInt("POSITION", position);
                    bundle.putSerializable(Tokens.OtherTask.OT_ITEMSKIPDETAIL_ITEM, taskMainBean);
                    intent1.putExtras(bundle);
                    mActivity.startActivity(intent1);
                    break;
            }
        }
    };


    @Override
    public void RefreshData(boolean isNeedProgressDialog) {
        super.RefreshData(isNeedProgressDialog);
        if(llfdata!=null) {
            DataTools.pageOtherTaskMap.put(gettype(), new PageTurnBean(0, 1, 10));
            llfdata.setVisibility(View.GONE);
            presenter.getData(gettype(), 1, isNeedProgressDialog);
        }
    }

    @Override
    protected void loadmoredata() {
        if(DataTools.pageOtherTaskMap.containsKey(gettype()) && DataTools.pageOtherTaskMap.get(gettype()).getPageCount()>DataTools.pageOtherTaskMap.get(gettype()).getPageNo()){
            presenter.getData(gettype(),DataTools.pageOtherTaskMap.get(gettype()).getPageNo()+1,false);
        }else {
            finishRefresh();
           new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MyToast.showToast(mActivity, "已是最后一页了！");
                }
            },800);
        }
    }
    /**
     * 更新视图
     * @param type
     */
    public void setListView(int type, PageTurnBean pt, List<OtherTaskMainBean> lfBeans) {
        closeLoadingDialog();
        List<OtherTaskMainBean> totalList = new ArrayList<>();
        if(DataTools.ListOtherTaskMap.containsKey(type) && DataTools.ListOtherTaskMap.get(type) != null){
            if(pt!=null && pt.getPageNo() > 1){
                totalList = DataTools.ListOtherTaskMap.get(type);
            }
        }
        totalList.addAll(lfBeans);
        DataTools.ListOtherTaskMap.put(type,totalList);
        DataTools.pageOtherTaskMap.put(type,pt);
        if(totalList.size()>0){
            showDataToView(null);
        }else{
            showNoDataHint("暂无数据");
        }
    }

    @Override
    public void showDataToView(String returnJson) {
        super.showDataToView(returnJson);
        goAdapter.notifyDataSetChanged(DataTools.ListOtherTaskMap.get(gettype()));
        if(DataTools.pageOtherTaskMap.containsKey(gettype()) && DataTools.pageOtherTaskMap.get(gettype())!=null){
            int currentPage =  DataTools.pageOtherTaskMap.get(gettype()).getPageNo();
            if(currentPage > 0){
                goAdapter.MoveToPosition(mLayoutManager,mRecyclerView,(currentPage-1) * 10);
            }
        }
    }
    /**
     * 接收成功刷新界面
     */
    public void refreshAfterAccept(int position){
        DataTools.ListOtherTaskMap.get(gettype()).remove(position);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goAdapter.notifyDataSetChanged(DataTools.ListOtherTaskMap.get(gettype()));
                if(DataTools.ListOtherTaskMap.get(gettype())==null || DataTools.ListOtherTaskMap.get(gettype()).size()==0){
                    showNoDataHint("暂无数据");
                }
            }
        },1500);
    }
    /**
     * 拒收成功刷新界面
     */
    public void refreshAfterReject(int position){
        DataTools.ListOtherTaskMap.get(gettype()).remove(position);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goAdapter.notifyDataSetChanged(DataTools.ListOtherTaskMap.get(gettype()));
                if(DataTools.ListOtherTaskMap.get(gettype())==null || DataTools.ListOtherTaskMap.get(gettype()).size()==0){
                    showNoDataHint("暂无数据");
                }
            }
        },1500);
    }
    /**
     * 显示错误信息
     * @param msg
     */
    public void showError(String msg){
        MyToast.showToast(mActivity,msg);
        closeLoadingDialog();
    }

    @Override
    public void showLoadingDialog() {mLoadingDialog.show();}
    @Override
    public void closeLoadingDialog() {
        mLoadingDialog.dismiss();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract boolean needConnect();

    /**
     * gettype回去接口对应的type 0.取待领取的任务, 1.已领取待联系的任务, 2.已领取待上门的任务, 3.已延期的任务 4.全部
     */
    protected abstract int gettype();
}
