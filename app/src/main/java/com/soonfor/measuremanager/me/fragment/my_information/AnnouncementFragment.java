package com.soonfor.measuremanager.me.fragment.my_information;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.me.activity.my_information.MyInformationActivity;
import com.soonfor.measuremanager.me.adapter.my_information.AnnouncementListAdapter;
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
 * 我的消息——通告
 */

public class AnnouncementFragment extends BaseFragment<MyInformation_FragmentPresenter> implements IMyInform_FragmentView {

    Unbinder unbinder;
    Activity mActivity;
    @BindView(R.id.reyclerview)
    RecyclerView mRecyclerView;
    private AnnouncementListAdapter annAdapter;
    private List<AnnouncementBean> annList = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.llftxterror)
    LinearLayout llftxterror;

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
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
        annAdapter = new AnnouncementListAdapter(mActivity, annList);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(annAdapter);
        return rootView;
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_me_myinformation_announcement;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        presenter.getAnnList();
    }

    @Override
    protected void initPresenter() {
        presenter = new MyInformation_FragmentPresenter(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private void isHaveDatas(int type){
        switch (type){
            case 0:
                mRecyclerView.setVisibility(View.GONE);
                llftxterror.setVisibility(View.VISIBLE);
                break;
            case 1:
                mRecyclerView.setVisibility(View.VISIBLE);
                llftxterror.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showInformList(List<InformBean> informBeans, String msg) {

    }

    @Override
    public void showAnnList(List<AnnouncementBean> annBeans, String msg) {
        if(annBeans==null && annBeans.size()==0){
            showNoDataHint(msg);
            MyInformationActivity.showRedProint(false);
        }else{
            showDataToView(null);
            MyInformationActivity.showRedProint(true);
            annAdapter.notifyDataSetChanged(annBeans);
        }
    }
}
