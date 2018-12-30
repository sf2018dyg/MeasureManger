package com.soonfor.measuremanager.bases;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.tools.CommonApp;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.PreferenceUtil;
import com.soonfor.measuremanager.tools.SwipeRefreshHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by Administrator on 2018/1/5 0005.
 */

public abstract class BaseActivity<T extends IBasePresenter> extends AppCompatActivity implements IBaseView {

    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    /**
     * 把 EmptyLayout 放在基类统一处理，@Nullable 表明 View 可以为 null，详细可看 ButterKnife
     */
    @Nullable
    @BindView(R.id.empty_layout)
    protected QMUIEmptyView mEmptyLayout;
    /**
     * 刷新控件，注意，资源的ID一定要一样
     */
    @Nullable
    @BindView(R.id.swipe_refresh)
    protected SmartRefreshLayout mSwipeRefresh;

    /**
     * 资源的ID一定要一样
     */
    @Nullable
    @BindView(R.id.llftxterror)
    protected LinearLayout llfNoData; //错误的信息
    /**
     * 资源的ID一定要一样
     */
    @Nullable
    @BindView(R.id.llfdata)
    protected LinearLayout llfdata; //有数据时
    /**
     * 资源的ID一定要一样
     */
    @Nullable
    @BindView(R.id.txterror)
    protected TextView tvfMsg;

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
     * 初始化presenter
     */
    protected abstract void initPresenter();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    public LoadingDailog mLoadingDialog;
    protected String actionName = "加载中...";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        CommonApp.getInstance().addActivity(this);
        setContentView(attachLayoutRes());
        PreferenceUtil.init(this);
        if (mLoadingDialog == null) {
            LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                    .setMessage(actionName)
                    .setCancelable(true)
                    .setCancelOutside(false);
            mLoadingDialog = loadBuilder.create();
        }
        ButterKnife.bind(this);

        //初始化状态栏的假沉浸式
        StatusBarCompat.translucentStatusBar(this);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));

        //StatusBarUtil.setColor(this, getResources().getColor(R.color.black));
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);// 关闭title
            toolbar.findViewById(R.id.ivfLeft).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        initPresenter();
        initSwipeRefresh();
        initViews();
        updateViews(false);
    }

    /* * 头部处理
     *
     * @param isShowBack
     * @param title
     */
    public void setHead(boolean isShowBack, String title) {
        if (isShowBack) {
            ImageButton ibt_back = (ImageButton) findViewById(R.id.ibt_back);
            ibt_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        TextView tv_title = (TextView) findViewById(R.id.title);
        tv_title.setText(title);
    }

    /**
     * 加载图片或头像
     *
     * @param imageUrl 图片地址
     * @param view     图片控件
     */
    public void imageLoading(String imageUrl, ImageView view, int defaultResId) {
        try {
            ImageUtil.loadPicByGlide(this, view, imageUrl, defaultResId, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * 修改人：DC-ZhuSuiBo on 2018/3/20 0020 18:03
     * 邮箱：suibozhu@139.com
     */
    public void startNewAct(Activity activity, Class<?> targetActClass, Bundle data) {
        Intent intent = new Intent(activity, targetActClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
    }

    /**
     * 修改人：DC-ZhuSuiBo on 2018/3/20 0020 18:03
     * 邮箱：suibozhu@139.com
     */
    public void startNewAct(Activity activity, Class<?> targetActClass, Bundle data, int resultCode) {
        Intent intent = new Intent(activity, targetActClass);
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
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
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
    public void showNetError() {
        if (mEmptyLayout != null) {
            mEmptyLayout.show(false, getResources().getString(R.string.emptyView_mode_desc_fail_title),
                    getResources().getString(R.string.emptyView_mode_desc_fail_desc),
                    getResources().getString(R.string.emptyView_mode_desc_retry), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateViews(false);
                        }
                    });
            SwipeRefreshHelper.enableRefresh(mSwipeRefresh, false);
        }
    }


    /**
     * 初始化下拉刷新
     */
    private void initSwipeRefresh() {
        if (mSwipeRefresh != null) {
            //设置 Header 为 Material风格
            // mSwipeRefresh.setRefreshHeader(new MaterialHeader(getBaseContext()).setShowBezierWave(false).setColorSchemeColors(getResources().getColor(R.color.blue)));
            SwipeRefreshHelper.init(mSwipeRefresh, new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    RefreshData(true);
                }
            });
            mSwipeRefresh.setOnLoadmoreListener(new OnLoadmoreListener() {
                @Override
                public void onLoadmore(RefreshLayout refreshlayout) {
                    loadmoredata();
                }
            });
            //设置回弹时间
            mSwipeRefresh.setReboundDuration(0);
        }
    }

    protected void loadmoredata() {
    }

    /**
     * 更新视图控件
     */
    protected abstract void updateViews(boolean isRefresh);

    @Override
    public void finishRefresh() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.finishRefresh();
            mSwipeRefresh.finishLoadmore();
        }
    }

    /**
     * 请求有数据时刷新界面
     */
    @Override
    public void showDataToView(String returnJson) {
        finishRefresh();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (llfdata != null) {
                    llfdata.setVisibility(View.VISIBLE);
                }
                if (llfNoData != null) {
                    llfNoData.setVisibility(View.GONE);
                }
            }
        }, 500);
    }

    /**
     * 请求无数据时显示错误提示
     */
    @Override
    public void showNoDataHint(String msg) {
        finishRefresh();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (llfdata != null) {
                    llfdata.setVisibility(View.GONE);
                }
                if (llfNoData != null) {
                    llfNoData.setVisibility(View.VISIBLE);
                }
                if (tvfMsg != null) {
                    tvfMsg.setText(msg);
                }
            }
        }, 500);
    }

    /**
     * 刷新数据
     */
    @Override
    public void RefreshData(boolean isRefresh) {}

    public void call(Context mActivity, String number) {
        if (number.equals("")) {
            MyToast.showFailToast(mActivity, "电话号码不能为空！");
        } else if (CommonUtils.isPhoneLegal(number)) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            MyToast.showFailToast(mActivity, "非可用电话号码，不可拨打！");
        }
    }

    public void showFailText(Context context, Throwable throwable, JSONObject errorResponse) {
        if (errorResponse != null) {
            ErrorBean errorBean = null;
            try {
                errorBean = new Gson().fromJson(errorResponse.toString(), ErrorBean.class);
            } catch (Exception e) {
            }
            if (errorBean == null) {
                MyToast.showFailToast(context, "服务器请求失败：" + throwable.getMessage());
            } else {
                if (errorBean.getMessage() == null)
                    MyToast.showFailToast(context, "服务器请求失败：" + errorBean.getError());
                else
                    MyToast.showFailToast(context, errorBean.getError() + ": " + errorBean.getMessage());
            }
        } else {
            if (throwable.getMessage().contains("time out")) {
                MyToast.showFailToast(context, "请求超时！");
            } else {
                MyToast.showFailToast(context, "服务器请求失败：" + throwable.getMessage());
            }
        }
    }

    public void showLoadingDialog(){
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }
    public void closeLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }
}
