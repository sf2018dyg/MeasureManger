package com.soonfor.measuremanager.home.lofting.view.immloft;

import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkWallEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.WallPath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DingYg on 2018-03-19.
 */

public interface IComCheckIsLoftedView {

    //只取数据
    void setGetData(boolean isSuccess, ArrayList<MarkWallEntity> markWallEntityList);
    //处理数据完成后调用
    void finishDispose(boolean isLoft, ArrayList<MarkWallEntity> markWallEntityList);
    //提交成功后调用
    void finishSumbit(boolean isSuboted, String msg);
    //获取所有墙面图片地址
    void setGetWallPaths(boolean isSuccess, ArrayList<WallPath> wallPaths);

    void showLoading();

    void hideLoading();
}
