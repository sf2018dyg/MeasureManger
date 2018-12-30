package com.soonfor.measuremanager.tools;

import android.content.Context;
import android.support.v4.content.ContextCompat;


import java.util.List;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.view.stepview.HorizontalStepView;
import com.soonfor.measuremanager.view.stepview.bean.StepBean;

/**
 * Created by ljc on 2018/1/12.
 */

public class StepViewUtils {
    public static void initCommonStep(HorizontalStepView stepView, List<StepBean> stepBeans, Context context) {
        stepView
                .setStepViewTexts(stepBeans)//总步骤
                .setTextSize(12)//set textSize

                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(context, R.color.text_prompt))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(context, R.color.text_prompt))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(context, R.color.text))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(context, R.color.text))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(context, R.drawable.flow_finish))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(context, R.drawable.flow_fail))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(context, R.drawable.flow_waiting));//设置StepsViewIndicator AttentionIcon
    }
}
