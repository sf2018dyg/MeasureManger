package com.soonfor.measuremanager.bases;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.PreferenceUtil;
import com.soonfor.measuremanager.tools.SwipeRefreshHelper;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public abstract class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView {

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
    @BindView(R.id.refreshLayout)
    public SmartRefreshLayout mSwipeRefresh;

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
    TextView tvfMsg;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(attachLayoutRes(), container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PreferenceUtil.init(getContext());
        if (mLoadingDialog == null) {
            LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(getContext())
                    .setMessage("加载中...")
                    .setCancelable(true)
                    .setCancelOutside(false);
            mLoadingDialog = loadBuilder.create();
        }
        ButterKnife.bind(getActivity());
        initPresenter();
        initViews();
        initSwipeRefresh();
        updateViews(false);
    }

    /**
     * 加载图片或头像
     * @param imageUrl 图片地址
     * @param view     图片控件
     */
    public void imageLoading(String imageUrl, ImageView view, int defaultResId) {
        try {
            ImageUtil.loadPicByGlide(getActivity(), view, imageUrl, defaultResId, false);
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

    /**
     * 修改人：DC-ZhuSuiBo on 2018/3/20 0020 18:03
     * 邮箱：suibozhu@139.com
     */
    public void startNewAct(Context activity, Class<?> targetActClass, Bundle data) {
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
    public void startNewAct(Context activity, Class<?> targetActClass, Bundle data, int resultCode) {
        Intent intent = new Intent(activity, targetActClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivityForResult(intent, resultCode);
    }

    @Override
    public void showLoading() {
        SwipeRefreshHelper.enableRefresh(mSwipeRefresh, false);
    }

    @Override
    public void hideLoading() {
        SwipeRefreshHelper.enableRefresh(mSwipeRefresh, true);
        SwipeRefreshHelper.controlRefresh(mSwipeRefresh, false);
    }

    /**
     * 初始化下拉刷新
     */
    public void initSwipeRefresh() {
        if (mSwipeRefresh != null) {
            //设置 Header 为 Material风格
            SwipeRefreshHelper.init(mSwipeRefresh, new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    RefreshData(false);
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

    ;

    /**
     * 更新视图控件
     */
    protected abstract void updateViews(boolean isRefresh);


    @Override
    public void showNetError() {
        SwipeRefreshHelper.enableRefresh(mSwipeRefresh, false);
    }

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
                try {
                    llfNoData.setVisibility(View.GONE);
                    llfdata.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },500);

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
        },500);

    }

    /**
     * 刷新数据
     */
    @Override
    public void RefreshData(boolean isNeedProgressDialog) {
    }


    public void call(Context mActivity, String number) {
        if(number.equals("")){
            MyToast.showFailToast(mActivity, "电话号码不能为空！");
        }else if (CommonUtils.isPhoneLegal(number)) {
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
    public void closeLoadingDialog(){
        if(mLoadingDialog!=null && mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
        }
    }
}
