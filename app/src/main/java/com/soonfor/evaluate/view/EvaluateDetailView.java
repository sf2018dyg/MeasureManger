package com.soonfor.evaluate.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.evaluate.adapter.EvaluateLevlShowAdapter;
import com.soonfor.evaluate.bean.Evaluate_CustomersToMeDetailBean;
import com.soonfor.measuremanager.other.adapter.PhotoAdapter;
import com.soonfor.measuremanager.other.adapter.layoutmanager.FullyGridLayoutManager;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.updateutil.ShowPicActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：DC-DingYG on 2018-10-17 13:57
 * 邮箱：dingyg012655@126.com
 */
public class EvaluateDetailView extends LinearLayout {
    private Context mContext;
    @BindView(R.id.tvfEvaluateTime)
    TextView tvfEvaluateTime;
    @BindView(R.id.tvfOverall)
    TextView tvfOverall;
    @BindView(R.id.mRecyclerEvaluate)
    RecyclerView mRecyclerEvaluate;

    private LinearLayoutManager manager;
    private EvaluateLevlShowAdapter levlAdapter;

    @BindView(R.id.llfEvaluateContent)
    LinearLayout llfEvaluateContent;
    @BindView(R.id.tvfEvaluateContent)
    TextView tvfEvaluateContent;
    @BindView(R.id.rvfEvalPics)
    RecyclerView rvfEvalPics;
    private GridLayoutManager pManager;
    private PhotoAdapter pAdapter;

    public EvaluateDetailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final View view = View.inflate(context, R.layout.view_evaluatetome_detail, this);
        ButterKnife.bind(this, view);
    }


    //初始化
    public void initEvaluateDetailView(final Activity mContext, final Evaluate_CustomersToMeDetailBean detailBean, ArrayList<String> evalPics) {
        this.mContext = mContext;
        //异步更新数据1
        new Thread(new Runnable() {
            @Override
            public void run() {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvfEvaluateTime.setText(DateTool.getTimeTimestamp(detailBean.getEvalTime(), "yyyy-MM-dd HH:mm") + "");
                        if (detailBean.getIfsetappraisecontent() == 1) {
                            llfEvaluateContent.setVisibility(VISIBLE);
                            switch (detailBean.getAllhighappraiseresult()) {
                                case 1:
                                    tvfOverall.setText("好评");
                                    break;
                                case 2:
                                    tvfOverall.setText("中评");
                                    break;
                                case 3:
                                    tvfOverall.setText("差评");
                                    break;
                            }
                            tvfEvaluateContent.setText(detailBean.getAppraisecontent());
                        } else {
                            llfEvaluateContent.setVisibility(GONE);
                        }
                    }
                });
            }
        }).start();
        //异步更新数据1
        new Thread(new Runnable() {
            @Override
            public void run() {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (detailBean.getIfuploadimg() == 1) {
                            rvfEvalPics.setVisibility(VISIBLE);
                            if (evalPics.size() > 0) {
                                pManager = new FullyGridLayoutManager(mContext, 3);
                                pAdapter = new PhotoAdapter(mContext, evalPics, PhotoAdapter.TYPE_URL, false);
                                pAdapter.setListener(new PhotoAdapter.onItemClick() {
                                    @Override
                                    public void itemClick(View view, ArrayList<String> beans, int adapterPosition) {
                                        Bundle bundle1 = new Bundle();
                                        bundle1.putStringArrayList("piclists", evalPics);
                                        bundle1.putInt("position", adapterPosition);
                                        Intent intent = new Intent(mContext, ShowPicActivity.class);
                                        intent.putExtras(bundle1);
                                        mContext.startActivity(intent);
                                    }
                                    @Override
                                    public void deleteClick(View view, ArrayList<String> beans, int adapterPosition) {}
                                });
                                rvfEvalPics.setLayoutManager(manager);
                                rvfEvalPics.setAdapter(pAdapter);
                            }
                        } else {
                            rvfEvalPics.setVisibility(GONE);
                        }
                    }
                });
            }
        }).start();
        manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerEvaluate.setLayoutManager(manager);
        levlAdapter = new EvaluateLevlShowAdapter(mContext, detailBean.getAppResSortItemDtos());
        mRecyclerEvaluate.setAdapter(levlAdapter);
    }
}

