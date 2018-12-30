package com.soonfor.measuremanager.home.grab.activity;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.grab.adapter.GrabOrderListAdapter;
import com.soonfor.measuremanager.home.grab.model.bean.GrabOrderBean;
import com.soonfor.measuremanager.home.grab.presenter.GrabBasePresenter;
import com.soonfor.measuremanager.home.grab.presenter.GrabComPresenter;
import com.soonfor.measuremanager.home.grab.presenter.IGrabComView;
import com.soonfor.measuremanager.home.grab.view.IGrabBaseView;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.view.dialog.normal.NormalDialog;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;
import com.soonfor.repository.tools.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：DC-DingYG on 2018-10-25 8:37
 * 邮箱：dingyg012655@126.com
 */
public class GrabPondActivity extends BaseActivity<GrabBasePresenter> implements IGrabBaseView {

    Activity mActivity;
    @BindView(R.id.recyView)
    RecyclerView mRecyclerView;
    private GrabOrderListAdapter goAdapter;
    private List<GrabOrderBean> gobList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private PageTurnBean pageTurnBean;//页码信息
    public static int ItemPosition = -1;//进入详情的位置
    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_graborder;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        mActivity = GrabPondActivity.this;
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        goAdapter = new GrabOrderListAdapter(mActivity, gobList, grabListener);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(goAdapter);
    }

    @Override
    protected void initPresenter() {
        presenter = new GrabBasePresenter(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mSwipeRefresh.autoRefresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ItemPosition>=0){
            RefreshData(true);
        }
    }

    @Override
    public void RefreshData(boolean isRefresh) {
        super.RefreshData(isRefresh);
        pageTurnBean = new PageTurnBean(0, 1, 10);
        presenter.getData(-1, 1, isRefresh);
    }

    @Override
    protected void loadmoredata() {
        if (pageTurnBean!=null && pageTurnBean.getPageCount() > pageTurnBean.getPageNo()) {
            presenter.getData(-1, pageTurnBean.getPageNo()+1, true);
        } else {
            finishRefresh();
          /*  new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MyToast.showToast(mActivity, "已是最后一页了！");
                }
            },1000);*/
        }
    }

    /**
     * 更新视图
     */
    public void setListView(int type, PageTurnBean pt, List<GrabOrderBean> mLists) {
        if (pt != null) {
            if (pt.getPageNo() <= 1) {
                if (gobList == null) gobList = new ArrayList<>();
                else if(gobList.size()>0) gobList.clear();
            }
            if (mLists.size() > 0) {
                gobList.addAll(mLists);
                pageTurnBean = pt;
            }
            if (gobList.size() > 0) {
                showDataToView(null);
            } else {
                showNoDataHint("暂无数据");
            }
        }
    }
    //抢单后刷新
    public void refreshAfterGrabTask() {

    }
    @Override
    public void showDataToView(String returnJson) {
        super.showDataToView(returnJson);
        goAdapter.notifyDataSetChanged(gobList);
        if (pageTurnBean != null) {
            int currentPage = pageTurnBean.getPageNo();
            if (currentPage > 0) {
                goAdapter.MoveToPosition(mLayoutManager, mRecyclerView, (currentPage - 1) * 10);
            }
        }
    }

    @Override
    public void showNoDataHint(String msg) {
        super.showNoDataHint(msg);
        closeLoadingDialog();
    }

    @Override
    public void showLoadingDialog() {
        if (ActivityUtils.isRunning(mActivity)) {
            if(mLoadingDialog!=null && !mLoadingDialog.isShowing())
                mLoadingDialog.show();
        }
    }


    @Override
    public void closeLoadingDialog() {
        if(mLoadingDialog!=null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }
    NormalDialog ndialog;
    /**
     * 抢单
     */
    private View.OnClickListener grabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int finalPositon = (int) v.getTag();
            GrabOrderBean goBean = gobList.get(finalPositon);
            String hintText = "亲抢单完成后，尽快与客户再次确认" + presenter.getHintByStatus(goBean.getTaskType()) + "日期，任务预期未处理，将会对您的信誉造成影响";
            ndialog = CustomDialog.getInstance().getNormalDialog(mActivity, "温馨提示", hintText,
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick(View view) {
                            ndialog.dismiss();
                            showLoadingDialog();
                            new GrabComPresenter(mActivity, new IGrabComView() {
                                @Override
                                public void refreshAfterGrabTask(boolean isSuccess, String msg) {
                                    if(isSuccess){
                                        MyToast.showSuccessToast(mActivity, "抢单成功");
                                        presenter.getData(-1, 1, true);
                                    }else {
                                        MyToast.showFailToast(mActivity, "抢单失败");
                                    }
                                    closeLoadingDialog();
                                }
                            }).grabTask(goBean.getTaskNo(), goBean.getTaskType());
                        }
                    });
            ndialog.show();
        }
    };
}