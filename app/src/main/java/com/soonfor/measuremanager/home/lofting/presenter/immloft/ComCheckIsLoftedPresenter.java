package com.soonfor.measuremanager.home.lofting.presenter.immloft;

import android.content.Context;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.customportrait.headBean;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkComponentEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkWallEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.WallPath;
import com.soonfor.measuremanager.home.lofting.view.immloft.IComCheckIsLoftedView;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DingYg on 2018-03-19.
 */

public class ComCheckIsLoftedPresenter implements AsyncUtils.AsyncCallback {

    IComCheckIsLoftedView cView;
    int actionType = 0;

    public ComCheckIsLoftedPresenter(IComCheckIsLoftedView cView) {
        this.cView = cView;
    }
    /**
     * 获取当前户型所有墙面的放样图片网络路径
     */
    public void getWallPaths(Context context, String sContractNo, String wallcode){
        Request.Loft.getWallPaths(context, sContractNo, wallcode, this);
    }
    /**
     * 请求墙面详细数据,用于判断是否放样
     *
     * @param mContext
     * @param taskno
     * @param contractno
     * @param actionType 0 只请求数据 1 判断是否开始放样 2 判断是否放样完成
     */
    public void getWalls(Context mContext, String taskno, String contractno, int actionType) {
        this.actionType = actionType;
        cView.showLoading();
        Request.Loft.getLoftingList(mContext, 1, taskno, contractno, "", this);
    }

    /**
     * 提交
     */
    public void setTaskStatus(Context mContext, String status, String taskNo, String fobsolainid) {
        cView.showLoading();
        Request.setTaskStatus(mContext, this, taskNo, fobsolainid, status);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode) {
            case Request.Loft.GET_LOFTING_LIST_1:
                cView.hideLoading();
                boolean flag = false;
                ArrayList<MarkWallEntity> markWallEntityList = new ArrayList<>();
                HeadBean bean = JsonUtils.getHeadBean(object.toString());
                if (bean != null && bean.getMsgcode() == 0) {
                    try {
                        JSONArray jsonArray = new JSONArray(bean.getData());
                        if (jsonArray != null && jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                MarkWallEntity markWallEntity = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), MarkWallEntity.class);
                                markWallEntityList.add(markWallEntity);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                switch (actionType) {
                    case 0:
                        if (markWallEntityList.size() > 0) {
                            cView.setGetData(true, markWallEntityList);
                        } else {
                            cView.setGetData(false, null);
                        }
                        break;
                    case 1:
                        flag = IsLofting(markWallEntityList);
                        if (flag) {
                            cView.finishDispose(true, markWallEntityList);
                        } else {
                            cView.finishDispose(false, markWallEntityList);
                        }
                        break;
                    case 2:
                        flag = IsLofted(markWallEntityList);
                        if (flag) {
                            cView.finishDispose(true, markWallEntityList);
                        } else {
                            cView.finishDispose(false, markWallEntityList);
                        }
                        break;
                }
                break;
            case Request.SET_TASK_STATUS:
                cView.hideLoading();
                HeadBean headBean2 = JsonUtils.getHeadBean(object.toString());
                if (headBean2 != null && headBean2.getMsgcode() == 0) {
                    cView.finishSumbit(true, headBean2.getData());
                } else {
                    cView.finishSumbit(false, headBean2.getFaileMsg());
                }
                break;
            case Request.Loft.GET_LOFTING_WALLPATHS:
                cView.hideLoading();
                HeadBean hb = JsonUtils.getHeadBean(object.toString());
                ArrayList<WallPath> wPaths = new ArrayList<>();
                if (hb != null && (hb.getMsgcode() == 0 || hb.isSuccess())) {
                    try {
                        JSONArray ja = new JSONArray(hb.getData());
                        if (ja != null && ja.length() > 0) {
                            for (int i = 0; i < ja.length(); i++) {
                                WallPath wp = new Gson().fromJson(ja.getJSONObject(i).toString(), WallPath.class);
                                wPaths.add(wp);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                cView.setGetWallPaths(true, wPaths);
                break;

        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        switch (requestCode) {
            case Request.Loft.GET_LOFTING_LIST_1:
                cView.hideLoading();
                if (actionType == 0) {
                    cView.setGetData(false, null);
                } else {
                    cView.finishDispose(false, null);
                }
                break;
            case Request.SET_TASK_STATUS:
                cView.hideLoading();
                cView.finishSumbit(false, "提交不成功");
                break;
            case Request.Loft.GET_LOFTING_WALLPATHS:
                cView.hideLoading();
                cView.setGetWallPaths(false, null);
                break;
        }
    }

    /**
     * 是否全部放样完成
     *
     * @return
     */
    public boolean IsLofted(ArrayList<MarkWallEntity> markWallEntityList) {
        if (markWallEntityList == null || markWallEntityList.size() == 0) {
            return false;
        }
        boolean isLofted = true;//默认已全部放样完成
        boolean isFail = false;//是否请求出错 默认错误

        try {
            isFail = true;
            for (int i = 0; i < markWallEntityList.size(); i++) {
                if (!markWallEntityList.get(i).getIsMark().equals("1")) {
                    isLofted = false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isLofted & isFail;
    }

    /**
     * 是否未放样
     */
    public boolean IsLofting(ArrayList<MarkWallEntity> markWallEntityList) {
        if (markWallEntityList == null || markWallEntityList.size() == 0) {
            return false;
        }
        boolean isLofting = false;//默认未开始放样
        boolean isFile = false;//是否请求出错 默认错误
        try {
            isFile = true;
            for (int i = 0; i < markWallEntityList.size(); i++) {
                if (markWallEntityList.get(i).getIsMark().equals("1")) {
                    isLofting = true;
                    break;
                }
            }
        } catch (Exception e) {
        }
        return isLofting & isFile;
    }
}
