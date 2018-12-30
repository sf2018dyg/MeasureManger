package com.soonfor.measuremanager.me.presenter;

import com.soonfor.measuremanager.bases.IBaseView;
import com.soonfor.measuremanager.me.bean.MeMineBean;
import com.soonfor.measuremanager.me.bean.SaleTargetBean;
import com.soonfor.evaluate.bean.EvaluateViewBean;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public interface IMeView extends IBaseView {
    void showLoadingDialog();

    void closeLoadingDialog();

    void setMine(boolean isSuccess, MeMineBean bean);

    void setAchievementRate(SaleTargetBean bean);

    void setEvaluateInfoToView(EvaluateViewBean bean);
}
