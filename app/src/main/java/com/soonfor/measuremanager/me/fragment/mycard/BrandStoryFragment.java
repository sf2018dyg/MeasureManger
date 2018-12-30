package com.soonfor.measuremanager.me.fragment.mycard;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.me.adapter.BrandStoryAdapter;
import com.soonfor.measuremanager.me.presenter.mycard.MycardPresenter;
import com.soonfor.measuremanager.me.presenter.myfavorite.IMyFavoriteView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BrandStoryFragment extends BaseFragment<MycardPresenter> implements IMyFavoriteView {

    Unbinder unbinder;
    Activity mActivity;
    @BindView(R.id.recyView)
    RecyclerView mRecyclerView;
    private BrandStoryAdapter bsAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<String> sList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSwipeRefresh;
    PageTurnBean pageTurnBean;
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_mycard_brandstroy;
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
        bsAdapter = new BrandStoryAdapter(mActivity);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(bsAdapter);
        return rootView;
    }


    @Override
    protected void initPresenter() {
        presenter = new MycardPresenter(this);
    }

    @Override
    protected void initViews() {
        presenter.getBrandStory(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

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
//        List<String> cbList = new ArrayList<>();
//        String msg = "暂无品牌故事";
//        if (headBean != null) {
//            if (headBean.getMsgcode() == 0) {
//                if (headBean.getData() != null) {
//                    try {
//                        JSONArray ja = new JSONArray(headBean.getData());
//                        if (ja != null && ja.length() > 0) {
//                            for(int i=0; i<ja.length(); i++) {
//                                String caseBean = ja.get(i).toString();
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
//            showNoDataHint("暂无品牌故事");
//            if(!msg.equals("暂无品牌故事")){
//                MyToast.showFailToast(mActivity, msg);
//            }
//        }
        sList = new ArrayList<>();
        bsAdapter.notifyDataSetChanged(new ArrayList());
        if (sList != null && sList.size() > 0) {
            showDataToView(null);
        } else {
            showNoDataHint("暂无品牌故事");
        }
    }
}