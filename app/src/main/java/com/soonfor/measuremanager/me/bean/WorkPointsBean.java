package com.soonfor.measuremanager.me.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class WorkPointsBean {

    /**
     * totalPoints : 256
     * signDays : 0
     * lotteryArea : [{"name":"家用电风扇","id":"b2d2d643-fb87-4863-96b0-09cc4fd14855","description":"每天只能抽取一次","workPoints":20,"thumbnail":"http://tb.xxx.com/xxss.jpg","rule":null},{"name":"海尔双开门冰箱","id":"87bf1710-8b88-4a2a-8a11-2b1b222e73ab","description":"每天只能抽取一次","workPoints":20,"thumbnail":"http://tb.xxx.com/xxss.jpg","rule":null}]
     * exchangeArea : [{"name":"七彩虹晴雨伞","id":"0b141c0a-c726-4c1d-a256-cb58fa21af43","description":"兑换后不可退换货","workPoints":100,"thumbnail":"http://tb.xxx.com/xxss.jpg","rule":null}]
     */

    private int totalPoints;
    private int signDays;
    private List<LotteryAreaBean> lotteryArea;
    private List<LotteryAreaBean> exchangeArea;

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getSignDays() {
        return signDays;
    }

    public void setSignDays(int signDays) {
        this.signDays = signDays;
    }

    public List<LotteryAreaBean> getLotteryArea() {
        return lotteryArea;
    }

    public void setLotteryArea(List<LotteryAreaBean> lotteryArea) {
        this.lotteryArea = lotteryArea;
    }

    public List<LotteryAreaBean> getExchangeArea() {
        return exchangeArea;
    }

    public void setExchangeArea(List<LotteryAreaBean> exchangeArea) {
        this.exchangeArea = exchangeArea;
    }

    public static class LotteryAreaBean {
        /**
         * name : 家用电风扇
         * id : b2d2d643-fb87-4863-96b0-09cc4fd14855
         * description : 每天只能抽取一次
         * workPoints : 20
         * thumbnail : http://tb.xxx.com/xxss.jpg
         * rule : null
         */

        private String name;
        private String id;
        private String description;
        private int workPoints;
        private String thumbnail;
        private Object rule;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getWorkPoints() {
            return workPoints;
        }

        public void setWorkPoints(int workPoints) {
            this.workPoints = workPoints;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public Object getRule() {
            return rule;
        }

        public void setRule(Object rule) {
            this.rule = rule;
        }
    }

}
