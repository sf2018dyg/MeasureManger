package com.soonfor.measuremanager.me.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.me.adapter.PointsDetailsAdapter;
import com.soonfor.measuremanager.me.bean.PointsDetailsBean;
import com.soonfor.measuremanager.me.presenter.pointsget.IPointsGetView;
import com.soonfor.measuremanager.me.presenter.pointsget.PointsGetPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/10 0010.
 */

public class WorkPointsGetFragment extends BaseFragment<PointsGetPresenter> implements IPointsGetView {

    @BindView(R.id.listview)
    ListView listview;
    Unbinder unbinder;
    private PointsDetailsAdapter adapter;
    private PointsDetailsBean.DataBean beanList = new PointsDetailsBean.DataBean();

    public static WorkPointsGetFragment newInstance() {
        return new WorkPointsGetFragment();
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_points_get;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        adapter = new PointsDetailsAdapter(getContext(),beanList);
        listview.setAdapter(adapter);
    }

    /**
     * 初始化presenter
     */
    @Override
    protected void initPresenter() {
        presenter = new PointsGetPresenter(this);
    }

    /**
     * 更新视图控件
     *
     * @param isRefresh
     */
    @Override
    protected void updateViews(boolean isRefresh) {

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
    public void setData(PointsDetailsBean.DataBean bean) {
        beanList = bean;
        adapter.changeList(beanList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
