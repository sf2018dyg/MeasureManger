package com.soonfor.measuremanager.me.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.me.bean.SaleTargetBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: DC-DingYG on 2018-11-15 14:18
 * e-mail: dingyg012655@126.com
 * 当月目标
 */
public class AchievementRateView extends LinearLayout {

    @BindView(R.id.tv_personal_complete_schedule)
    TextView tvPersonalGoalRate;//个人目标达成率
    @BindView(R.id.progressbar_personal_complete_schedule)
    ProgressBar pbPersonalGoalRate;//计划个人目标及优秀目标的进度条
    @BindView(R.id.pbPersonalActual)
    ProgressBar pbPersonalActual;//个人目标实际达成率进度条
    @BindView(R.id.start_personal_goal_rate)
    TextView tvfSJPersonalGoalRate;//实际完成的个人目标值
    @BindView(R.id.tvfPresenMub_value)
    TextView tvfJHPersonalGoalRate;//计划完成的个人目标值
    @BindView(R.id.tv_Goodgoals)
    TextView tvfGoodGoals;//优秀目标
    @BindView(R.id.tvfZhuoYueMub_value)
    TextView tvfZhuoYueMub_value;//卓越目标
    @BindView(R.id.tv_store_complete_schedule)
    TextView tvStoreGoalRate;//门店目标达成率
    @BindView(R.id.progressbar_store_complete_schedule)
    ProgressBar pbStoreGoalRate;//门店目标达成率的进度条
    @BindView(R.id.tvfStoresTargetRate)
    TextView tvfSJStoreGoalRate;//实际完成的门店目标
    @BindView(R.id.tvfStoresTarget_value)
    TextView tvfJHStroeGoalRate;//计划完成的门店目标

    public AchievementRateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.me_base_aims_layout, this);
        ButterKnife.bind(this, view);
    }
    public void initRateView(Activity mContext, SaleTargetBean bean){
        /**
         * 个人目标
         * baseActual : 200000  //实际完成的目标
         * baseTarget : 300000 //计划完成的目标
         * baseRate : 0.66 //完成的比率
         * excellentActual : 300000 //实际完成的卓越目标
         * excellentTarget : 500000 //计划完成的卓越目标
         * excellentRate : 0.6 //卓越目标的完成比率
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SaleTargetBean.PersonalTargetBean personalTarget = bean.getPersonalTarget();
                        tvPersonalGoalRate.setText((int) (personalTarget.getBaseRate() * 100) + "%");
                        tvfJHPersonalGoalRate.setText(mContext.getResources().getString(R.string.yuan) + personalTarget.getBaseTarget());
                        int P_actual = 0;
                        if (personalTarget.getBaseActual() == 0) {
                            P_actual = (int) (personalTarget.getBaseTarget() * personalTarget.getBaseRate());
                        } else {
                            P_actual = personalTarget.getBaseActual();
                        }
                        tvfSJPersonalGoalRate.setText(mContext.getResources().getString(R.string.yuan) + P_actual);
                        tvfGoodGoals.setText("");
                        tvfZhuoYueMub_value.setText(mContext.getResources().getString(R.string.yuan) + personalTarget.getExcellentTarget());
                        double p_plan = 0.00;//RepUserInfo.div(personalTarget.getBaseTarget(), personalTarget.getExcellentTarget(), 2);
                        pbPersonalGoalRate.setProgress((int) (p_plan * 100));
                        pbPersonalGoalRate.setSecondaryProgress((int) (0.76 * 100));
                        double p_actual = 0.00;//崩溃UserInfo.div(P_actual, bean.getPersonalTarget().getExcellentTarget(), 2);
                        pbPersonalActual.setProgress((int) (p_actual * 100));
                        /**
                         * 门店目标
                         */
                        SaleTargetBean.StoreTargetBean storeTarget = bean.getStoreTarget();
                        tvStoreGoalRate.setText((int) (storeTarget.getBaseRate() * 100) + "%");
                        tvfJHStroeGoalRate.setText(mContext.getResources().getString(R.string.yuan) + bean.getStoreTarget().getBaseTarget());
                        int S_actual = 0;
                        if (storeTarget.getBaseActual() == 0) {
                            S_actual = (int) (storeTarget.getBaseTarget() * storeTarget.getBaseTarget());
                        } else {
                            S_actual = storeTarget.getBaseActual();
                        }
                        tvfSJStoreGoalRate.setText(mContext.getResources().getString(R.string.yuan) + S_actual);
                        double store = UserInfo.div(S_actual, bean.getStoreTarget().getBaseTarget(), 2);
                        pbStoreGoalRate.setProgress((int) (store * 100));
                    }
                });
            }
        }).start();

    }
}
