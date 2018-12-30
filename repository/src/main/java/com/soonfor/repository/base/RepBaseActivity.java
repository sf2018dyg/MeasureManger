package com.soonfor.repository.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.soonfor.repository.R;

import com.soonfor.repository.tools.SwipeRefreshHelper;


/**
 * 作者：DC-DingYG on 2018-05-03 9:24
 * 邮箱：dingyg012655@126.com
 */
public abstract class RepBaseActivity<T extends RepBasePresenter> extends FragmentActivity implements IRepBaseView {

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

    @Nullable
    protected Toolbar toolbar;

    /**
     * 控制器
     */
    public T presenter;

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @LayoutRes
    protected abstract int attachLayoutRes();

    /**
     * 更新视图控件
     */
    protected abstract void updateViews(boolean isRefresh);


    /**
     * 初始化presenter
     */
    protected abstract T initPresenter();


    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    public LoadingDailog mLoadingDialog;
    protected String actionName = "加载中...";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);// 沉浸式状态栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(attachLayoutRes());
        if (mLoadingDialog == null) {
            LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                    .setMessage(actionName)
                    .setCancelable(true)
                    .setCancelOutside(true);
            mLoadingDialog = loadBuilder.create();
        }
        BasefindViewById();
        try {
            toolbar.findViewById(R.id.ivfLeft).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishByBack();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        initPresenter();
        initViews();
        initSwipeRefresh();
    }

    private void BasefindViewById() {
        mEmptyLayout = this.findViewById(R.id.empty_layout);
        mSwipeRefresh = this.findViewById(R.id.swipe_refresh);
        toolbar = this.findViewById(R.id.toolbar);
    }

    protected void initToolbar(Toolbar toolbar, String title, int imageRes, View.OnClickListener listener) {
        ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText(title);
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(listener);
        ImageView imgfRight = toolbar.findViewById(R.id.imgfRight);
        imgfRight.setImageResource(imageRes);
        imgfRight.setVisibility(View.VISIBLE);
        imgfRight.setOnClickListener(listener);
    }

    protected void finishByBack() {
    }

    ;

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
            finish();
        }
    }

    public void startNewAct(Class<?> targetActClass, Bundle data) {
        Intent intent = new Intent();
        intent.setClass(this, targetActClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
    }

    public void startNewAct(Class<?> targetActClass, Bundle data, boolean isfinsished) {
        Intent intent = new Intent();
        intent.setClass(this, targetActClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
        if (isfinsished) {
            finish();
        }
    }

    public void startNewAct(Class<?> targetActClass, Bundle data, int resultCode) {
        Intent intent = new Intent();
        intent.setClass(this, targetActClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivityForResult(intent, resultCode);
    }


    /**
     * -----------------------------    点击空白处隐藏软键盘   --------------------------------------------------------------------------
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }


    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    protected void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 初始化下拉刷新
     */
    private void initSwipeRefresh() {
        if (mSwipeRefresh != null) {
            //设置 Header 为 Material风格
            //mSwipeRefresh.setRefreshHeader(new MaterialHeader(getContext()).setShowBezierWave(false).setColorSchemeColors(getResources().getColor(R.color.blue)));
            SwipeRefreshHelper.init(mSwipeRefresh, new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    updateViews(true);
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

    @Override
    public void finishRefresh() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.finishRefresh();
            mSwipeRefresh.finishLoadmore();
        }
        closeLoadingDialog();
    }

    /**
     * 请求有数据时刷新界面
     */
    @Override
    public void showDataToView(String returnJson) {
        if (mEmptyLayout != null) {
            mEmptyLayout.setVisibility(View.GONE);
        }
        finishRefresh();
        closeLoadingDialog();
    }

    /**
     * 请求无数据时显示错误提示
     */
    @Override
    public void showNoDataHint(String msg) {
        finishRefresh();
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
            SwipeRefreshHelper.enableRefresh(mSwipeRefresh, true);
            SwipeRefreshHelper.controlRefresh(mSwipeRefresh, false);
        }
    }

    @Override
    public void showNetError(String msg) {
        finishRefresh();
        hideLoading();
        closeLoadingDialog();
        if (mEmptyLayout != null) {
            mEmptyLayout.setVisibility(View.VISIBLE);
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
    }

    @Override
    public void showLoadingDialog() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void showLoadingDialog(String actionName) {
        this.actionName = actionName;
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                    .setMessage(actionName)
                    .setCancelable(true)
                    .setCancelOutside(false);
            mLoadingDialog = loadBuilder.create();
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
