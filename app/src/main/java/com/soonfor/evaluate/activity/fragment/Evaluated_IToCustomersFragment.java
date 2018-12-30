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

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.othertask.activity.OtherTaskDetailActivity;
import com.soonfor.measuremanager.home.othertask.model.bean.OtherTaskMainBean;
import com.soonfor.evaluate.activity.Evaluate_IToCustomersDetailActivity;
import com.soonfor.evaluate.adapter.Evaluate_IToCustomersAdapter;
import com.soonfor.evaluate.bean.Evaluate_IToCustomersBean;
import com.soonfor.evaluate.presenter.Evaluate_IToCustomersPresenter;
import com.soonfor.evaluate.presenter.IEvaluate_IToCustomersView;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.Tokens;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：DC-ZhuSuiBo on 2018/10/17 0017 14:00
 * 邮箱：suibozhu@139.com
 * 类用途：我对客户的评价 - 已评价
 */
public class Evaluated_IToCustomersFragment extends
        BaseFragment<Evaluate_IToCustomersPresenter> implements IEvaluate_IToCustomersView {

    Unbinder unbinder;
    Activity mActivity;
    @BindView(R.id.recyView)
    SwipeMenuRecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Evaluate_IToCustomersAdapter etmAdpter;
    private List<Evaluate_IToCustomersBean> etmList;
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
        etmAdpter = new Evaluate_IToCustomersAdapter(mActivity, 1, itemListener, 0);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(etmAdpter);
        return rootView;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_mine_evaluate;
    }

    @Override
    protected void initPresenter() {
        presenter = new Evaluate_IToCustomersPresenter(this, mActivity);
    }

    @Override
    protected void initViews() {
        mSwipeRefresh.autoRefresh();
    }

    @Override
    protected void updateViews(boolean isRefresh) {}

    @Override
    public void RefreshData(boolean isRefresh) {
        super.RefreshData(isRefresh);
        pageTurnBean = new PageTurnBean(0, 1, 10);
        presenter.getEvaluatedList(1, 1);
    }

    @Override
    protected void loadmoredata() {
        if (pageTurnBean != null && pageTurnBean.getPageCount() > pageTurnBean.getPageNo()) {
            presenter.getEvaluatedList(1, pageTurnBean.getPageNo()+1);
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
    private View.OnClickListener itemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            switch (v.getId()){
                case R.id.tvfServicesAvailable:
                    Bundle bundle = new Bundle();
                    Evaluate_IToCustomersBean ectBean = etmList.get(pos);
                    Intent i;
                    if (!ectBean.getTasktype().equals("")) {
                        if (Integer.parseInt(ectBean.getTasktype())==4) {
                            bundle.putInt(Tokens.OtherTask.OT_ITEMSKIPDETAIL_STATUS, 3);
                            bundle.putSerializable(Tokens.OtherTask.OT_ITEMSKIPDETAIL_ITEM, presenter.convertToOtherTaskbean(ectBean));
                            i = new Intent(mActivity, OtherTaskDetailActivity.class);
                            i.putExtras(bundle);
                            mActivity.startActivity(i);
                        }
                    }
                    break;
                case R.id.llfItem:
                    Intent intent2 = new Intent(mActivity, Evaluate_IToCustomersDetailActivity.class);
                    intent2.putExtra("ITOCLIENT_ITEM", etmList.get(pos));
                    mActivity.startActivity(intent2);
                    break;
            }
        }
    };
    //显示已评价
    public void showYList(boolean isSuccess, PageTurnBean pt, List<Evaluate_IToCustomersBean> beans, String msg) {
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
                    showNoDataHint("暂无评价");
                }
                etmAdpter.notifyDataSetChanged(etmList);
            }
        }else {
            if(pageTurnBean.getPageCount()==1) showNoDataHint(msg);
        }
    }

    @Override
    public void showLoadingDialog() {
        if(mLoadingDialog!=null && !mLoadingDialog.isShowing())
            mLoadingDialog.show();
    }

    @Override
    public void closeLoadingDialog() {
        if(mLoadingDialog!=null && mLoadingDialog.isShowing())
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
    public OtherTaskMainBean convertToOtherTaskbean(Evaluate_IToCustomersBean goBean) {
        if (goBean != null) {
            OtherTaskMainBean result = new OtherTaskMainBean();
            result.setTaskId(goBean.getTaskno());
            result.setTaskType(goBean.getTasktype());
            result.setCustomerId(goBean.getCustomerid());
            result.setCustomerSex(goBean.getCustomersex());
            result.setCustomerName(goBean.getCustomername());
            result.setCustomerPhone("");
            result.setExectype(-1);
            return result;
        } else return null;
    }
}
