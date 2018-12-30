package com.soonfor.evaluate.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.AttachDto;
import com.soonfor.evaluate.bean.Evaluate_IToCustomersBean;
import com.soonfor.evaluate.bean.Evaluate_IToCustomersDetailBean;
import com.soonfor.evaluate.presenter.Evaluate_IToCustomersDetailPresenter;
import com.soonfor.evaluate.view.EvaluateLevlView;
import com.soonfor.measuremanager.other.adapter.PhotoAdapter;
import com.soonfor.measuremanager.other.adapter.layoutmanager.FullyGridLayoutManager;
import com.soonfor.updateutil.ShowPicActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：DC-DingYG on 2018-10-19 9:20
 * 邮箱：dingyg012655@126.com
 * 我对客户的评价
 */

public class Evaluate_IToCustomersDetailActivity extends BaseActivity<Evaluate_IToCustomersDetailPresenter> {

    private Activity mActivity;

    @BindView(R.id.llfAllEval)
    LinearLayout llfAllEval;
    @BindView(R.id.tvfAllEval)
    TextView tvfAllEval;
    @BindView(R.id.llfEvalLevl)
    LinearLayout llfEvalLevl;
    @BindView(R.id.viewfEvalLevl)
    EvaluateLevlView viewfEvalLevl;
    @BindView(R.id.llfEvalTime)
    LinearLayout llfEvalTime;
    @BindView(R.id.tvfEvalTime)
    TextView tvfEvalTime;
    @BindView(R.id.llfEvalContent)
    LinearLayout llfEvalContent;
    @BindView(R.id.tvfEvalContent)
    TextView tvfEvalContent;
    @BindView(R.id.rvfIEvalPics)
    RecyclerView rvfIEvalPics;
    private GridLayoutManager manager;
    private PhotoAdapter adapter;
    private Evaluate_IToCustomersBean eBean;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_evaluate_i_to_clients_detail;
    }

    @Override
    protected void initPresenter() {
        presenter = new Evaluate_IToCustomersDetailPresenter(this);
    }

    @Override
    protected void initViews() {
        mActivity = Evaluate_IToCustomersDetailActivity.this;
        mSwipeRefresh.setEnableLoadmore(false);
        eBean = (Evaluate_IToCustomersBean) getIntent().getSerializableExtra("ITOCLIENT_ITEM");
        if (eBean != null) {
            if (eBean.getEvalstatus().equals("0")) {//待评价
                mSwipeRefresh.setEnableRefresh(false);
                showNoDataHint("尚未评价, 暂无评价详情");
            } else {//已评价
                mSwipeRefresh.autoRefresh();
            }
        } else {
            finish();
        }
    }

    @Override
    public void RefreshData(boolean isRefresh) {
        super.RefreshData(isRefresh);
        if (!eBean.getId().equals("")) presenter.getDetail(mActivity, eBean.getId());
    }

    String allEvalDesc = "";

    //为界面设数据
    public void showViewByData(Evaluate_IToCustomersDetailBean detailBean, String msg) {
        if (detailBean != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (detailBean.getIfallhighappraise() == 1) {
                                allEvalDesc = "";
                                switch (detailBean.getAllhighappraiseresult()) {
                                    case 1:
                                        allEvalDesc = "好评";
                                        break;
                                    case 2:
                                        allEvalDesc = "中评";
                                        break;
                                    case 3:
                                        allEvalDesc = "差评";
                                        break;
                                }
                                llfAllEval.setVisibility(View.VISIBLE);
                                tvfAllEval.setText(allEvalDesc);
                            }
                            llfEvalTime.setVisibility(View.VISIBLE);
                            tvfEvalTime.setText(detailBean.getEvalTime());
                            if (detailBean.getAppResSortItemDtos().size() > 0 && detailBean.getIfstarlvappraise() == 1) {
                                llfEvalLevl.setVisibility(View.VISIBLE);
                                viewfEvalLevl.initEvaluateLevlList(mActivity, detailBean.getAppResSortItemDtos());
                            }
                        }
                    });
                }
            }).start();
            ArrayList<String> evalPics = new ArrayList<>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (detailBean.getAttachDtos().size() > 0) {
                        List<AttachDto> attachDtos = detailBean.getAttachDtos();
                        for (int i = 0; i < attachDtos.size(); i++) {
                            // if (attachDtos.get(i).getAttachType().equals("0")) {
                            //0.评价图，1.评价结果回复图
                            evalPics.add(attachDtos.get(i).getAttachUrl());
                            // }
                        }
                    }
                    if (evalPics.size() > 0 && detailBean.getIfuploadImg() == 1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rvfIEvalPics.setVisibility(View.VISIBLE);
                                manager = new FullyGridLayoutManager(mActivity, 3);
                                rvfIEvalPics.setLayoutManager(manager);
                                adapter = new PhotoAdapter(mActivity, evalPics, PhotoAdapter.TYPE_URL, false);
                                adapter.setListener(new PhotoAdapter.onItemClick() {
                                    @Override
                                    public void itemClick(View view, ArrayList<String> beans, int adapterPosition) {
                                        Bundle bundle1 = new Bundle();
                                        bundle1.putStringArrayList("piclists", evalPics);
                                        bundle1.putInt("position", adapterPosition);
                                        Intent intent = new Intent(mActivity, ShowPicActivity.class);
                                        intent.putExtras(bundle1);
                                        mActivity.startActivity(intent);
                                    }

                                    @Override
                                    public void deleteClick(View view, ArrayList<String> beans, int adapterPosition) {
                                    }
                                });
                                rvfIEvalPics.setAdapter(adapter);
                            }
                        });
                    }
                }
            }).start();
            if (detailBean.getFfsetappraisecontent() == 1) {
                llfEvalContent.setVisibility(View.VISIBLE);
                tvfEvalContent.setText(detailBean.getAppraisecontent());
            }
            showDataToView(null);
        }else {
            if(msg!=null && !msg.equals(""))
                showNoDataHint(msg);
            else
                showNoDataHint("详情为空");
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }
}
