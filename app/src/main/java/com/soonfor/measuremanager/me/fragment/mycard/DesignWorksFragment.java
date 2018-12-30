package com.soonfor.measuremanager.me.fragment.mycard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.activity.DesignCaseDetailActivity;
import com.soonfor.measuremanager.afflatus.bean.DesignCaseBean;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.me.adapter.mycase.MycaseListAdapter;
import com.soonfor.measuremanager.me.presenter.mycard.MycardPresenter;
import com.soonfor.measuremanager.me.presenter.myfavorite.IMyFavoriteView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DesignWorksFragment extends BaseFragment<MycardPresenter> implements IMyFavoriteView {
    Unbinder unbinder;

    Activity mActivity;
    @BindView(R.id.recyView)
    RecyclerView mRecyclerView;
    private MycaseListAdapter cbAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<DesignCaseBean> cbList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSwipeRefresh;
    PageTurnBean pageTurnBean;
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_mycard_designworks;
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
        cbAdapter = new MycaseListAdapter(mActivity, cbList);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(cbAdapter);
        cbAdapter.setOnItemClickListener(new MycaseListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int data) {
                DesignCaseBean db = cbList.get(data);
                Intent intent = new Intent(mActivity, DesignCaseDetailActivity.class);
                intent.putExtra("DesignCaseBean", db);
                mActivity.startActivity(intent);
            }
        });
        return rootView;
    }


    @Override
    protected void initPresenter() {
        presenter = new MycardPresenter(this);
    }

    @Override
    protected void initViews() {
        getDesignWorks(1,false);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }
    private void getDesignWorks(int pageNo, boolean isRefresh) {
        presenter.getDesignWorks(this, pageNo, isRefresh);
    }

    @Override
    public void RefreshData(boolean isRefresh) {
        super.RefreshData(isRefresh);
        pageTurnBean = new PageTurnBean(0, 1, 10);
        getDesignWorks(pageTurnBean.getPageNo(), true);
    }

    @Override
    protected void loadmoredata() {
        if (pageTurnBean!=null && pageTurnBean!=null && pageTurnBean.getPageCount() > pageTurnBean.getPageNo()) {
            getDesignWorks(pageTurnBean.getPageNo() + 1, false);
        } else {
            finishRefresh();
          /*  new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MyToast.showToast(mActivity, "已是最后一页了！");
                }
            }, 1000);*/
        }
    }
    @Override
    public void showLoadingDialog() {
        if(mLoadingDialog!=null && !mLoadingDialog.isShowing()){
            mLoadingDialog.show();
        }
    }

    @Override
    public void closeLoadingDialog() {
        closeLoadingDialog();
    }

    @Override
    public void setListView(String returnStr) {
//        RepHeadBean headBean = RepJsonUtils.getHeadBean(returnStr);
//        List<DesignCaseBean> cbList = new ArrayList<>();
//        String msg = "暂无案例";
//        if (headBean != null) {
//            if (headBean.getMsgcode() == 0) {
//                if (headBean.getData() != null) {
//                    try {
//                        JSONArray ja = new JSONArray(headBean.getData());
//                        if (ja != null && ja.length() > 0) {
//                            for(int i=0; i<ja.length(); i++) {
//                                DesignCaseBean caseBean = new Gson().fromJson(ja.get(i).toString(), DesignCaseBean.class);
//                                cbList.add(caseBean);
//                            }
//                        }
//                    } catch (Exception e) {
//                        msg = e.toString();
//                    }
//                } else {
//                    msg = headBean.getMsg();
//                }
//            } else {
//                msg = headBean.getFaileMsg();
//            }
//        }
//        if (cbList != null && cbList.size() > 0) {
//            showDataToView(null);
//        } else {
//            showNoDataHint("暂无案例");
//            if(!msg.equals("暂无案例")){
//                MyToast.showFailToast(mActivity, msg);
//            }
//        }
        /**
         *假数据
         */
        cbList = new ArrayList<>();
//        cbList.add(new DesignCaseBean("FuGUIOn", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2579211415,1545415720&fm=27&gp=0.jpg", "18-05-06", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1130331467,3007611445&fm=27&gp=0.jpg", "装横预算多点！！！！！装横预算多点！！！！！", "成都天语小区", "单身公寓、欧洲风","主要起居室结合客厅、书桌与工作空间，沙发后方的大桌兼具用餐及办公两种用途，几种动线，把更多面积留给卧室与横浴", "200", "12", "56"));
//        cbList.add(new DesignCaseBean("梁天志", "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1030400957,1891723070&fm=11&gp=0.jpg", "18-05-06", "http://img5.imgtn.bdimg.com/it/u=4247704318,2577748025&fm=27&gp=0.jpg", "装横预算多点！！！！！装横预算多点！！！！！", "成都天语小区", "单身公寓、欧洲风","主要起居室结合客厅、书桌与工作空间，沙发后方的大桌兼具用餐及办公两种用途，几种动线，把更多面积留给卧室与横浴", "150", "111", "33"));
//        cbList.add(new DesignCaseBean("FuGUIOn", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2579211415,1545415720&fm=27&gp=0.jpg", "18-05-06", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1130331467,3007611445&fm=27&gp=0.jpg", "装横预算多点！！！！！装横预算多点！！！！！", "成都天语小区","主要起居室结合客厅、书桌与工作空间，沙发后方的大桌兼具用餐及办公两种用途，几种动线，把更多面积留给卧室与横浴", "单身公寓、欧洲风", "200", "12", "56"));
//        cbList.add(new DesignCaseBean("梁天志", "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1030400957,1891723070&fm=11&gp=0.jpg", "18-05-06", "http://img5.imgtn.bdimg.com/it/u=4247704318,2577748025&fm=27&gp=0.jpg", "装横预算多点！！！！！装横预算多点！！！！！", "成都天语小区", "单身公寓、欧洲风","主要起居室结合客厅、书桌与工作空间，沙发后方的大桌兼具用餐及办公两种用途，几种动线，把更多面积留给卧室与横浴", "150", "111", "33"));
//        cbList.add(new DesignCaseBean("FuGUIOn", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2579211415,1545415720&fm=27&gp=0.jpg", "18-05-06", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1130331467,3007611445&fm=27&gp=0.jpg", "装横预算多点！！！！！装横预算多点！！！！！", "成都天语小区","主要起居室结合客厅、书桌与工作空间，沙发后方的大桌兼具用餐及办公两种用途，几种动线，把更多面积留给卧室与横浴", "单身公寓、欧洲风", "200", "12", "56"));
//        cbList.add(new DesignCaseBean("梁天志", "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1030400957,1891723070&fm=11&gp=0.jpg", "18-05-06", "http://img5.imgtn.bdimg.com/it/u=4247704318,2577748025&fm=27&gp=0.jpg", "装横预算多点！！！！！装横预算多点！！！！！", "成都天语小区", "单身公寓、欧洲风","主要起居室结合客厅、书桌与工作空间，沙发后方的大桌兼具用餐及办公两种用途，几种动线，把更多面积留给卧室与横浴", "150", "111", "33"));
        cbAdapter.notifyDataSetChanged(cbList);
        if (cbList != null && cbList.size() > 0) {
            showDataToView(null);
        } else {
            showNoDataHint("暂无设计作品");
        }
    }
}
