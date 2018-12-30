package com.soonfor.evaluate.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.complexruler.activity.FuChiDetailActivity;
import com.soonfor.measuremanager.home.design.activity.DesignDetailActivity;
import com.soonfor.measuremanager.home.liangchi.activity.LiangChiDetailActivity;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiBean;
import com.soonfor.measuremanager.home.lofting.activity.LoftingDetailActivity;
import com.soonfor.measuremanager.home.lofting.model.bean.LoftingMainBean;
import com.soonfor.measuremanager.home.othertask.activity.OtherTaskDetailActivity;
import com.soonfor.measuremanager.home.othertask.model.bean.OtherTaskMainBean;
import com.soonfor.evaluate.adapter.Evaluate_CustomersToMeAdapter;
import com.soonfor.evaluate.bean.Evaluate_CustomersToMeBean;
import com.soonfor.evaluate.presenter.Evaluate_CustomersToMePresenter;
import com.soonfor.evaluate.presenter.IEvaluate_CustomerToMeView;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.Tokens;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：DC-DingYG on 2018-10-17 15:32
 * 邮箱：dingyg012655@126.com
 */
public class UnEvaluate_CustomersToMeFragment extends BaseFragment<Evaluate_CustomersToMePresenter> implements IEvaluate_CustomerToMeView {

    Unbinder unbinder;
    Activity mActivity;
    @BindView(R.id.recyView)
    SwipeMenuRecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Evaluate_CustomersToMeAdapter etmAdpter;
    private List<Evaluate_CustomersToMeBean> etmList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSwipeRefresh;
    PageTurnBean pageTurnBean;

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
        etmList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        etmAdpter = new Evaluate_CustomersToMeAdapter(mActivity, 0, itemListener);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(etmAdpter);
        return rootView;
    }

    private View.OnClickListener itemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            switch (v.getId()) {
                case R.id.tvfServicesAvailable:
                    Bundle bundle = new Bundle();
                    Evaluate_CustomersToMeBean ectBean = etmList.get(pos);
                    Intent i;
                    if (!ectBean.getTasktype().equals("")) {
                        switch (Integer.parseInt(ectBean.getTasktype())) {
                            case 0://量尺任务
                                LiangChiBean lb = presenter.convertToLcbean(ectBean);
                                if (lb != null) {
                                    bundle.putParcelable("LiangChiBean", lb);
                                }
                                bundle.putInt("POSITION", pos);
                                i = new Intent(mActivity, LiangChiDetailActivity.class);
                                i.putExtras(bundle);
                                mActivity.startActivity(i);
                                break;
                            case 1://复尺任务
                                LiangChiBean lbf = presenter.convertToLcbean(ectBean);
                                if (lbf != null) {
                                    bundle.putParcelable("LiangChiBean", lbf);
                                }
                                bundle.putInt("POSITION", pos);
                                i = new Intent(mActivity, FuChiDetailActivity.class);
                                i.putExtras(bundle);
                                mActivity.startActivity(i);
                                break;
                            case 2://放样任务
                                bundle.putInt(Tokens.Lofing.ITEMSKIPDETAIL_STATUS, 4);
                                LoftingMainBean lmb = presenter.convertToFybean(ectBean);
                                bundle.putInt("POSITION", pos);
                                bundle.putParcelable(Tokens.Lofing.ITEMSKIPDETAIL_ITEM, lmb);
                                i = new Intent(mActivity, LoftingDetailActivity.class);
                                i.putExtras(bundle);
                                mActivity.startActivity(i);
                                break;
                            case 3://设计任务
                                bundle.putInt(Tokens.Lofing.ITEMSKIPDETAIL_STATUS, -1);
                                bundle.putParcelable(Tokens.Design.ITEMSKIPDETAIL_ITEM, presenter.convertToDesignbean(ectBean));
                                i = new Intent(mActivity, DesignDetailActivity.class);
                                i.putExtras(bundle);
                                mActivity.startActivity(i);
                                break;
                            default://其它任务（主动追踪任务、回访任务）
                                bundle.putInt(Tokens.OtherTask.OT_ITEMSKIPDETAIL_STATUS, 3);
                                bundle.putSerializable(Tokens.OtherTask.OT_ITEMSKIPDETAIL_ITEM, presenter.convertToOtherTaskbean(ectBean));
                                i = new Intent(mActivity, OtherTaskDetailActivity.class);
                                i.putExtras(bundle);
                                mActivity.startActivity(i);
                                break;
                        }
                    }
                    break;
                case R.id.llfItem:
                    break;
            }
        }
    };

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_mine_evaluate;
    }

    @Override
    protected void initPresenter() {
        presenter = new Evaluate_CustomersToMePresenter(this, mActivity);
    }

    @Override
    protected void initViews() {
        mSwipeRefresh.autoRefresh();
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    public void RefreshData(boolean isRefresh) {
        super.RefreshData(isRefresh);
        pageTurnBean = new PageTurnBean(0, 1, 10);
        presenter.getN_EvaluatedList(pageTurnBean.getPageNo());
    }

    @Override
    protected void loadmoredata() {
        if (pageTurnBean != null && pageTurnBean.getPageCount() > pageTurnBean.getPageNo()) {
            presenter.getN_EvaluatedList(pageTurnBean.getPageNo() + 1);
        } else {
            finishRefresh();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MyToast.showToast(mActivity, "已是最后一页了！");
                }
            }, 1000);
        }
    }

    @Override
    public void showYList(boolean isSuccess, PageTurnBean pageBean, List<Evaluate_CustomersToMeBean> beans, String msg) {
    }

    @Override
    public void showNList(boolean isSuccess, PageTurnBean pt, List<Evaluate_CustomersToMeBean> beans, String msg) {
        if (isSuccess) {
            if (pt != null) {
                if (pt.getPageNo() <= 1) {
                    if (etmList == null) etmList = new ArrayList<>();
                    else if (etmList.size() > 0) etmList.clear();
                }
                if (beans.size() > 0) {
                    etmList.addAll(beans);
                    pageTurnBean = pt;
                }
                if (etmList.size() > 0) {
                    showDataToView(null);
                } else {
                    showNoDataHint(msg);
                }
                etmAdpter.notifyDataSetChanged(etmList);
            }
        }else {
            if(pageTurnBean.getPageCount()==1)
                showNoDataHint(msg);
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

    /**
     * 转为其它任务对象
     */
    public OtherTaskMainBean convertToOtherTaskbean(Evaluate_CustomersToMeBean goBean) {
        if (goBean != null) {
            OtherTaskMainBean result = new OtherTaskMainBean();
            result.setTaskId(goBean.getTaskno());
            result.setTaskType(goBean.getTasktype());
            result.setCustomerId(goBean.getCustomerid());
            result.setCustomerSex(goBean.getCustomersex());
            result.setCustomerName(goBean.getCustomername());
            result.setCustomerPhone(goBean.getCustomerphone());
            result.setExectype(-1);
            return result;
        } else return null;
    }

}
