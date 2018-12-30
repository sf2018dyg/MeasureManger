package com.soonfor.repository.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.android.tu.loadingdialog.LoadingDailog;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.soonfor.repository.R;

import com.soonfor.repository.tools.SwipeRefreshHelper;


/**
 * Created by Administrator on 2018/1/16 0016.
 */

public abstract class RepBaseFragment<T extends RepBasePresenter> extends Fragment implements IRepBaseView {

    /**
     * 把 EmptyLayout 放在基类统一处理，@Nullable 表明 View 可以为 null，详细可看 ButterKnife
     */
    @Nullable
    protected QMUIEmptyView mEmptyLayout;
    /**
     * 刷新控件，注意，资源的ID一定要一样
     */
    @Nullable
    protected SmartRefreshLayout mSwipeRefresh;

    /**
     * 控制器
     */
    public T presenter;

    public FragmentActivity mActivity;

    /**
     * 初始化presenter
     */
    protected abstract void initPresenter();

    public LoadingDailog mLoadingDialog;

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;
    //本Fragment需要的View(填充view)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        if (mLoadingDialog == null) {
            LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(getContext())
                    .setMessage("加载中...")
                    .setCancelable(true)
                    .setCancelOutside(true);
            mLoadingDialog = loadBuilder.create();
        }
        initPresenter();
        initViews();
        initSwipeRefresh();
    }

    protected void BasefindViewById(View view) {
        mEmptyLayout = view.findViewById(R.id.empty_layout);
        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    /**
     * startNewActivity Description: 意图，界面跳转
     *
     * @param targetActClass 转入的活动类
     */
    public void startNewAct(Class<?> targetActClass) {
        startNewAct(targetActClass, null);
    }

    public void startNewAct(Class<?> targetActClass, boolean isfinished) {
        startNewAct(targetActClass, null);
        if (isfinished) {
            getActivity().finish();
        }
    }

    public void startNewAct(Class<?> targetActClass, Bundle data) {
        Intent intent = new Intent();
        intent.setClass(getContext(), targetActClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
    }

    public void startNewAct(Class<?> targetActClass, Bundle data, boolean isfinsished) {
        Intent intent = new Intent();
        intent.setClass(getContext(), targetActClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
        if (isfinsished) {
            getActivity().finish();
        }
    }

    public void startNewAct(Class<?> targetActClass, Bundle data, int resultCode) {
        Intent intent = new Intent();
        intent.setClass(getContext(), targetActClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivityForResult(intent, resultCode);
    }

    public void startNewAct(Context activity, Class<?> targetActClass, Bundle data, int resultCode) {
        Intent intent = new Intent(activity, targetActClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivityForResult(intent, resultCode);
    }

    public void startRefresh() {
        SwipeRefreshHelper.enableRefresh(mSwipeRefresh, false);
    }

    public void endRefresh() {
        SwipeRefreshHelper.enableRefresh(mSwipeRefresh, true);
        SwipeRefreshHelper.controlRefresh(mSwipeRefresh, false);
    }

    /**
     * 初始化下拉刷新
     */
    public void initSwipeRefresh() {
        if (mSwipeRefresh == null) {
            mSwipeRefresh = mActivity.findViewById(R.id.swipe_refresh);
        }
        if (mSwipeRefresh != null) {
            //设置 Header 为 Material风格
            SwipeRefreshHelper.init(mSwipeRefresh, new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    updateViews(false);
                }
            });
            mSwipeRefresh.setOnLoadmoreListener(new OnLoadmoreListener() {
                @Override
                public void onLoadmore(RefreshLayout refreshlayout) {
                    loadmoredata();
                }
            });

        }
    }

    protected void loadmoredata() {
    }

    /**
     * 更新视图控件
     */
    protected abstract void updateViews(boolean isRefresh);


    /**
     * 请求有数据时刷新界面
     */
    @Override
    public void showDataToView(String returnJson) {
        finishRefresh();
        hideLoading();
        closeLoadingDialog();
    }

    /**
     * 请求无数据时显示错误提示
     */
    @Override
    public void showNoDataHint(String msg) {
        finishRefresh();
        hideLoading();
        closeLoadingDialog();
    }

    @Override
    public void showLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.show(true);
            SwipeRefreshHelper.enableRefresh(mSwipeRefresh, false);
        }
    }

    @Override
    public void hideLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.hide();
        }
        if (mSwipeRefresh != null) {
            SwipeRefreshHelper.enableRefresh(mSwipeRefresh, true);
            SwipeRefreshHelper.controlRefresh(mSwipeRefresh, false);
        }
    }

    @Override
    public void finishRefresh() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.finishRefresh();
            mSwipeRefresh.finishLoadmore();
        }
    }

    @Override
    public void showNetError(String msg) {
        finishRefresh();
        hideLoading();
        if (mEmptyLayout != null) {
            if (msg == null || msg.equals("")) {
                mEmptyLayout.show(false, "加载失败",
                        "请检测网络是否能正常连接",
                        "点击重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updateViews(false);
                            }
                        });
            } else {
                mEmptyLayout.show(false, "提示",
                        msg,
                        "点击重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updateViews(false);
                            }
                        });
            }
            SwipeRefreshHelper.enableRefresh(mSwipeRefresh, false);
        }
        closeLoadingDialog();
    }

    @Override
    public void showLoadingDialog() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void showLoadingDialog(String actionName) {
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
