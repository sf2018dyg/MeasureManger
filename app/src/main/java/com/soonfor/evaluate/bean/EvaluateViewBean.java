package com.soonfor.evaluate.bean;

import com.soonfor.measuremanager.tools.CommonUtils;

import java.util.ArrayList;

/**
 * 作者：DC-DingYG on 2018-11-19 9:15
 * 邮箱：dingyg012655@126.com
 */
public class EvaluateViewBean {
    private String eval;//待评价数量
    private String goodRank;//客户好评率
    private String rankSort;//排名
    private String feedbackAvg;//回访平均分
    private String bad;//差评
    private String medium;//中评
    private String good;//差评
    private ArrayList<EvaluateRankingBean> rankingDtos;//评价排行

    public String getEval() {
        return CommonUtils.formatStr(eval);
    }

    public String getGoodRank() {
        return CommonUtils.formatPercentage(goodRank);
    }

    public String getRankSort() {
        return CommonUtils.formatStr(rankSort);
    }

    public String getFeedbackAvg() {
        return CommonUtils.formatAdecimal(feedbackAvg);
    }

    public String getBad() {
        return CommonUtils.formatAdecimal(bad);
    }

    public String getMedium() {
        return CommonUtils.formatAdecimal(medium);
    }

    public String getGood() {
        return CommonUtils.formatAdecimal(good);
    }

    public ArrayList<EvaluateRankingBean> getRankingDtos() {
        return rankingDtos==null ? new ArrayList<>() : rankingDtos;
    }
}
