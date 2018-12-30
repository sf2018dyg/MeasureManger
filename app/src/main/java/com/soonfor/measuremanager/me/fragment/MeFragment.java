package com.soonfor.measuremanager.me.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.me.activity.MyFavoriteActivity;
import com.soonfor.measuremanager.me.activity.SalesTargetActivity;
import com.soonfor.evaluate.activity.EvaluateRankingActivity;
import com.soonfor.evaluate.activity.Evaluate_CustomersToMeActivity;
import com.soonfor.evaluate.activity.Evaluate_IToCustomersActivity;
import com.soonfor.measuremanager.me.bean.MeMineBean;
import com.soonfor.measuremanager.me.bean.SaleTargetBean;
import com.soonfor.evaluate.bean.EvaluateRankingBean;
import com.soonfor.evaluate.bean.EvaluateViewBean;
import com.soonfor.measuremanager.me.presenter.MePresenter;
import com.soonfor.evaluate.view.ClientEvalToMeView;
import com.soonfor.measuremanager.me.presenter.IMeView;
import com.soonfor.measuremanager.me.view.AchievementRateView;
import com.soonfor.measuremanager.me.view.MeEvaluateRankView;
import com.soonfor.measuremanager.me.view.MeInfoView;
import com.soonfor.measuremanager.tools.PreferenceUtil;
import com.soonfor.measuremanager.tools.Tokens;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ljc on 2018/1/11.
 */

public class MeFragment extends BaseFragment<MePresenter> implements IMeView {
    Unbinder unbinder;
    @BindView(R.id.viewFMineInfo)
    MeInfoView viewFMineInfo;//个人信息
    @BindView(R.id.viewfAchievementRate)
    AchievementRateView viewfAchievementRate;//个人和门店目标业绩达成率
    @BindView(R.id.view_ClientEvalToMe)
    ClientEvalToMeView viewCETMe;//客户对我的评价
    @BindView(R.id.viewEvaluateRankView)
    MeEvaluateRankView evaluateRankView;//评价排行榜
    @BindView(R.id.tvfEvalNum)
    TextView tvfEvalNum;//我对客户的评价

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            //presenter.getData(false);
            presenter.getMaineInfo();
        }
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initPresenter() {
        presenter = new MePresenter(this);
    }

    @Override
    protected void initViews() {
        viewFMineInfo.initMineInfoVeiw(getActivity(), null);
    }

    @Override
    protected void updateViews(boolean isRefresh) {}
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.viewfAchievementRate, R.id.llfToMyCollect, /*R.id.llfToMyMessage, R.id.llfToMyCase, R.id.llfToMyWork,R.id.llfToMyAttention, */
            R.id.llfToRepository, R.id.llfEvaluateToMe, R.id.llfClientRanking, R.id.llfToEvaluation_i_to_clients})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.viewfAchievementRate://当月目标
               // startNewAct(SalesTargetActivity.class);
                break;
//            case R.id.llfToMyMessage:
//                startNewAct(MyInformationActivity.class);
//                break;
//            case R.id.llfToMyCase:
//                startNewAct(MycaseActivity.class);
//                break;
//            case R.id.llfToMyWork:
//                break;
            case R.id.llfToMyCollect:
                startNewAct(MyFavoriteActivity.class);
                break;
//            case R.id.llfToMyAttention:
//                startNewAct(MyAttentionActivity.class);
//                break;
            case R.id.llfToRepository:
                presenter.autoToRepositoryModuel();
                break;
            case R.id.llfEvaluateToMe:
                startNewAct(Evaluate_CustomersToMeActivity.class);
                break;
            case R.id.llfClientRanking:
                startNewAct(EvaluateRankingActivity.class);
                break;
            case R.id.llfToEvaluation_i_to_clients:
                startNewAct(Evaluate_IToCustomersActivity.class);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void showLoadingDialog() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    /**
     * 设置个人信息
     *
     * @param bean
     */
    @Override
    public void setMine(boolean isSuccess, MeMineBean bean) {
        if (isSuccess) {
            PreferenceUtil.commitPersonalInfo(Tokens.Mine.SP_PERSONALINFO, bean);
            viewFMineInfo.initMineInfoVeiw(getActivity(), bean);
        } else {
            PreferenceUtil.commitPersonalInfo(Tokens.Mine.SP_PERSONALINFO, null);
        }
    }

    /**
     * 设置当月目标
     *
     * @param bean
     */
    @Override
    public void setAchievementRate(SaleTargetBean bean) {
        viewfAchievementRate.initRateView(getActivity(), bean);
    }
    ArrayList<EvaluateRankingBean> rankingBeans;
    //更新评价信息
    @Override
    public void setEvaluateInfoToView(EvaluateViewBean bean) {
        if (bean != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewCETMe != null) viewCETMe.initEvalToMeView(getActivity(), bean);
                    if (evaluateRankView != null) {
                        rankingBeans = bean.getRankingDtos();
                        evaluateRankView.initEvaluateRankingView(getActivity(), rankingBeans, null);
                    }
                    tvfEvalNum.setText("待评价数：" + bean.getEval());
                }
            });

        }
    }
}
