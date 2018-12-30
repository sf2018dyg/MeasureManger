package com.soonfor.measuremanager.me.fragment.my_information;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.me.activity.my_information.MyInformationActivity;
import com.soonfor.measuremanager.me.adapter.my_information.InformListAdapter;
import com.soonfor.measuremanager.me.bean.AnnouncementBean;
import com.soonfor.measuremanager.me.bean.InformBean;
import com.soonfor.measuremanager.me.presenter.my_information.IMyInform_FragmentView;
import com.soonfor.measuremanager.me.presenter.my_information.MyInformation_FragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018-02-26.
 * 我的消息——通知
 */

public class InformFragment extends BaseFragment<MyInformation_FragmentPresenter> implements IMyInform_FragmentView{

    Unbinder unbinder;
    @BindView(R.id.rvfInform)
    RecyclerView mRecyclerView;
    private InformListAdapter listAdapter;
    private List<InformBean> informList = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_me_myinformation_inform;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initPresenter() {

        presenter = new MyInformation_FragmentPresenter(this);
    }
    @Override
    protected void initViews() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        listAdapter = new InformListAdapter(getActivity(), informList);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(listAdapter);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getInformList();
    }

    public void showInformList(List<InformBean> informBeans, String msg){
        if(informBeans==null && informBeans.size()==0){
            showNoDataHint(msg);
            MyInformationActivity.showRedProint(false);
        }else{
            showDataToView(null);
            MyInformationActivity.showRedProint(true);
            listAdapter.notifyDataSetChanged(informBeans);
        }
    }

    @Override
    public void showAnnList(List<AnnouncementBean> annBeans, String msg) {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
