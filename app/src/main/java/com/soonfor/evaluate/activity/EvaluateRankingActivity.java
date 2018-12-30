package com.soonfor.evaluate.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.evaluate.adapter.EvaluateRankAdapter;
import com.soonfor.evaluate.bean.EvaluateRankingBean;
import com.soonfor.evaluate.presenter.EvaluateRankingPresenter;
import com.soonfor.evaluate.view.EvaluateRankView;
import com.soonfor.measuremanager.view.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：DC-DingYG on 2018-10-19 14:25
 * 邮箱：dingyg012655@126.com
 */
public class EvaluateRankingActivity extends BaseActivity<EvaluateRankingPresenter> {

    @BindView(R.id.tvfMyRanking)
    TextView tvfMyRanking;
    @BindView(R.id.my_avatar)
    RoundImageView myfAvatar;
    @BindView(R.id.tvfClientGood)
    TextView tvfClientGood;
    @BindView(R.id.tvfRevistV)
    TextView tvfReVistValue;

    @BindView(R.id.viewEvaluateRankView)
    EvaluateRankView evaluateRankView;
    @BindView(R.id.imgfLineAboveList)
    ImageView imgfLineAboveList;
    @BindView(R.id.rvfRankingList)
    RecyclerView rvfRankingList;
    private LinearLayoutManager rankManager;
    private EvaluateRankAdapter erAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_evaluation_ranking;
    }

    @Override
    protected void initPresenter() {
        presenter = new EvaluateRankingPresenter(this);
    }

    @Override
    protected void initViews() {
        rankManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvfRankingList.setLayoutManager(rankManager);
        erAdapter = new EvaluateRankAdapter(this);
        rvfRankingList.setAdapter(erAdapter);

    }

    @Override
    protected void updateViews(boolean isRefresh) {
        RefreshData(false);
    }

    @Override
    public void RefreshData(boolean isRefresh) {
        super.RefreshData(isRefresh);
        presenter.getEvaluateRanking(EvaluateRankingActivity.this);
    }

    public void showViewByData(boolean isSuccess, List<EvaluateRankingBean> erBeanList){
        imgfLineAboveList.setVisibility(View.VISIBLE);
        if(isSuccess){
            if(erBeanList.size()>0) {
                imgfLineAboveList.setVisibility(View.GONE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(erBeanList.size()>3){
                            List<EvaluateRankingBean> resultList = new ArrayList<>();
                            for(int i=0; i< erBeanList.size(); i++){
                                if(i>2){
                                    resultList.add(erBeanList.get(i));
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    erAdapter.notifyDataSetChanged(resultList);
                                }
                            });
                        }
                    }
                }).start();
                evaluateRankView.initEvaluateRankingView(EvaluateRankingActivity.this, erBeanList, setMainView);
            }
//            else {
//            showNoDataHint("请求出错"); }
        }
//        else {
//            showNoDataHint("请求出错");
//        }
    }
    private EvaluateRankView.SetMainView setMainView = new EvaluateRankView.SetMainView() {
        @Override
        public void callback(EvaluateRankingBean currtBean) {
            if(currtBean!=null) {
                imageLoading(currtBean.getFheadpic(), myfAvatar, R.drawable.user2);
                tvfMyRanking.setText(currtBean.getRankSort());
                tvfClientGood.setText(currtBean.getGoodRank() + "%");
                tvfReVistValue.setText(currtBean.getFeedbackAvg() + "分");
            }
        }
    };
}
