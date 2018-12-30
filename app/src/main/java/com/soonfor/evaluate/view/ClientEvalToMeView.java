package com.soonfor.evaluate.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.evaluate.bean.EvaluateViewBean;
import com.soonfor.measuremanager.view.RingProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：DC-DingYG on 2018-10-17 13:57
 * 邮箱：dingyg012655@126.com
 */
public class ClientEvalToMeView extends LinearLayout {
    private Context mContext;
    @BindView(R.id.tv_good_evaluation_amount)
    TextView tvfGood;//
    @BindView(R.id.tv_general_evaluation_amount)
    TextView tvfMedium;//
    @BindView(R.id.tv_bad_evaluation_amount)
    TextView tvfBad;//
    @BindView(R.id.rPGood)
    RingProgressView rpGood;
    @BindView(R.id.tvfGoodValue)
    TextView tvfGoodValue;
    @BindView(R.id.rPReVisit)
    RingProgressView rpReVisit;
    @BindView(R.id.tvfReVisitValue)
    TextView tvfReVisitValue;

    public ClientEvalToMeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.me_evaluation_layout, this);
        ButterKnife.bind(this, view);
    }

    //初始化
    public void initEvalToMeView(final Activity mContext, final EvaluateViewBean bean) {
        this.mContext = mContext;
        new Thread(new Runnable() {
            @Override
            public void run() {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvfGood.setText(bean.getGood());
                        tvfMedium.setText(bean.getMedium());
                        tvfBad.setText(bean.getBad());
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    rpGood.setCurrentProgress(Float.parseFloat(bean.getGoodRank()));
                                    rpGood.refreshDrawableState();
                                }catch (Exception e){
                                    e.fillInStackTrace();
                                }
                            }
                        },1000);
                        tvfGoodValue.setText(bean.getGoodRank() + "%");
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    rpReVisit.setCurrentProgress(Float.parseFloat(bean.getFeedbackAvg()));
                                    rpGood.refreshDrawableState();
                                }catch (Exception e){
                                    e.fillInStackTrace();
                                }
                            }
                        },1000);
                        tvfReVisitValue.setText(bean.getFeedbackAvg());
                    }
                });
            }
        }).start();
    }
}
