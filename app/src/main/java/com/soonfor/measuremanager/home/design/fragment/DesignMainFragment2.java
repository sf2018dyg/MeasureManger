package com.soonfor.measuremanager.home.design.fragment;

/**
 * Created by Administrator on 2018-01-29.
 */

public class DesignMainFragment2 extends BaseDesignMainFragment{

    /**
     * 在activity Create时需不需要联网请求 返回true需要 反之不需要
     */
    @Override
    protected boolean needConnect() {
        return false;
    }

    /**
     * gettype回去接口对应的type 0.取待领取的任务, 1.已领取待联系的任务, 2.已领取待上门的任务, 3.已延期的任务 4.全部
     */
    @Override
    protected int gettype() {
        return 2;
    }// s
}
