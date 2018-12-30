package com.soonfor.measuremanager.home.lofting.presenter.immloft;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.lofting.activity.immloft.detail.mj_sdk.LoftListFirstActivity;
import com.soonfor.measuremanager.home.lofting.activity.immloft.detail.mj_sdk.LoftListSecondActivity;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by DingYg on 2018-01-29.
 */

public class LoftingListPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private Gson gson;
    private LoftListFirstActivity firstActivity;
    private LoftListSecondActivity secondActivity;
    private int type;


    /**
     * 第一、二个界面获取数据
     */
    public void getData(int type, String taskno, String contractno, String wallcode) {
        this.type = type;
        switch (type) {
            case 1://获取全部
                Request.Loft.getLoftingList(firstActivity, 1, taskno, contractno, "", this);
                break;
            case 2:
                Request.Loft.getLoftingList(secondActivity, 2, taskno, contractno, wallcode, this);
                break;
        }
    }


    /**
     * 删除图片
     */
    private int delePosition;
    private boolean isDeleteAll = false;//是否删除全部

    public void deletePic(int delePosition, String picId, boolean isDeleteAll) {
        this.isDeleteAll = isDeleteAll;
        this.delePosition = delePosition;
        if (picId.equals("")) {//删除未提交
            //ImageUtil.delFile("");
            secondActivity.afterDeletePic(delePosition, null);
        } else {//删除已提交的图片
            Request.deletePic(secondActivity, this, picId);
        }
    }

    /**
     * 提交放样图片
     */
    public void setMarkPic(String[] params) {
        Request.setMarkPic(secondActivity, this, params);
    }

    /**
     * 提交墙面放样状态
     */
    public void setWall_MarkStatus(String[] params) {
        Request.setWallMarkStatus(secondActivity, this, params);
    }


    public LoftingListPresenter(LoftListFirstActivity llActivity) {
        this.firstActivity = llActivity;
    }

    public LoftingListPresenter(LoftListSecondActivity llActivity) {
        this.secondActivity = llActivity;
    }

    int setMarkPicNum = 0;//成功提交的放样图片
    boolean isFail = false;//是否提交失败，哪怕一次也算失败

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode) {
            case Request.Loft.GET_LOFTING_LIST_1://获取房间所有墙墙面放样信息
                firstActivity.closeLoadingDialog();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        firstActivity.showNoDataHint(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        firstActivity.setListdata(data);
                    }
                });
                //firstActivity.setListdata(DataTools.getJiashuju());
                break;
            case Request.Loft.GET_LOFTING_LIST_2://获取单墙墙面放样信息
                secondActivity.closeLoadingDialog();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        MyToast.showFailToast(secondActivity, msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        secondActivity.setListdata(true, data);
                    }
                });
                //secondActivity.setListdata(true, DataTools.getJiashuju());
                break;
            case Request.Loft.DETELE_LOFT_PIC://删除放样图片
                if (!isDeleteAll) {
                    secondActivity.closeLoadingDialog();
                    JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                        @Override
                        public void doingInFail(String msg) {
                            MyToast.showFailToast(secondActivity, msg);
                        }

                        @Override
                        public void doingInSuccess(String data) {
                            secondActivity.afterDeletePic(delePosition, data);
                        }
                    });
                }
                break;
            case Request.Loft.SET_MARK_PICTURE://提交放样图片
                if (!isFail) {
                    HeadBean headBean = JsonUtils.getHeadBean(object.toString());
                    if (headBean != null && (headBean.getMsgcode() == 0 || headBean.isSuccess())) {
                        setMarkPicNum += 1;
                        if (setMarkPicNum == LoftListSecondActivity.imgList.size()) {
                            secondActivity.closeLoadingDialog();
                            secondActivity.afterSetPic(true, headBean.getData());
                        }
                    } else {
                        isFail = true;
                        secondActivity.closeLoadingDialog();
                        secondActivity.afterSetPic(false, headBean.getFaileMsg());
                    }
                }
                break;
            case Request.Loft.SET_WALL_MARKSTAUTS://提交墙面状态成功
                secondActivity.afterSetWallMarkStatus(true);
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        switch (requestCode) {
            case Request.Loft.GET_LOFTING_LIST_1:
                firstActivity.closeLoadingDialog();
                break;
            case Request.Loft.GET_LOFTING_LIST_2:
                secondActivity.setListdata(false, null);
                secondActivity.closeLoadingDialog();
                break;
            case Request.Loft.DETELE_LOFT_PIC:
                if (!isDeleteAll) {
                    secondActivity.closeLoadingDialog();
                    MyToast.showFailToast(secondActivity, "删除服务器图片失败");
                }
                break;
            case Request.Loft.SET_MARK_PICTURE:
                isFail = true;
                secondActivity.closeLoadingDialog();
                secondActivity.afterSetPic(false, errorMsg);
                break;
            case Request.Loft.SET_WALL_MARKSTAUTS://提交墙面状态不成功
                secondActivity.afterSetWallMarkStatus(false);
                break;
        }
    }

}
