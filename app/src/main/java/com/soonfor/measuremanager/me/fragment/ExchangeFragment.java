package com.soonfor.measuremanager.me.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.me.activity.PrizeRuleActivity;
import com.soonfor.measuremanager.me.adapter.points.LoftteryAdapter;
import com.soonfor.measuremanager.me.bean.WorkPointsBean;
import com.soonfor.measuremanager.me.presenter.exchange.ExchangePresenter;
import com.soonfor.measuremanager.me.presenter.exchange.IExchangeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/10 0010.
 * 兑换
 */

public class ExchangeFragment extends BaseFragment<ExchangePresenter> implements IExchangeView {

    Unbinder unbinder;
    @BindView(R.id.linear_exchange)
    LinearLayout linearExchange;
    @BindView(R.id.rvfLottery)
    RecyclerView rvfLottery;//抽奖专区
    @BindView(R.id.rvfExpiry)
    RecyclerView rlfExpiry;//兑奖专区
    private WorkPointsBean bean;
    private LoftteryAdapter ltAdapter1, ltAdapter2;
    private LinearLayoutManager mLayoutManager1, mLayoutManager2;
    public static ExchangeFragment newInstance() {
        Bundle args = new Bundle();
//        args.putInt("index", index);
        ExchangeFragment fragment = new ExchangeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ltAdapter1 = new LoftteryAdapter(getActivity(),0, listener);
        // 设置布局管理器
        rvfLottery.setLayoutManager(mLayoutManager1);
        // 设置adapter
        rvfLottery.setAdapter(ltAdapter1);
        mLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ltAdapter2 = new LoftteryAdapter(getActivity(),0, listener);
        // 设置布局管理器
        rlfExpiry.setLayoutManager(mLayoutManager2);
        // 设置adapter
        rlfExpiry.setAdapter(ltAdapter2);
        return rootView;
    }
    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_work_points_exchange;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {

    }

    /**
     * 初始化presenter
     */
    @Override
    protected void initPresenter() {
        presenter = new ExchangePresenter(this);
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
    public void setData(WorkPointsBean bean) {
        this.bean = bean;
        if(bean!=null){
            linearExchange.setVisibility(View.VISIBLE);
            if(bean.getLotteryArea()!=null && bean.getLotteryArea().size()>0)ltAdapter1.notifyDataSetChanged(bean.getLotteryArea());
            if(bean.getExchangeArea()!=null && bean.getExchangeArea().size()>0)ltAdapter2.notifyDataSetChanged(bean.getExchangeArea());
        }

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.id.tag_first);
            Bundle bundle = new Bundle();
            bundle.putString("prizeId",bean.getLotteryArea().get(position).getId());
            startNewAct(PrizeRuleActivity.class);
        }
    };



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
