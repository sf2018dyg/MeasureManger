package com.soonfor.measuremanager.view.popupwindow.SelectPopupWindow.bean;

/**
 * Created by Administrator on 2017-12-04.
 */

public class ConditionEntity {
    private int conditionCode;
    private String conditionName;

    public ConditionEntity(int conditionCode, String conditionName) {
        this.conditionCode = conditionCode;
        this.conditionName = conditionName;
    }

    public int getConditionCode() {
        return conditionCode;
    }

    public void setConditionCode(int conditionCode) {
        this.conditionCode = conditionCode;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }
}
