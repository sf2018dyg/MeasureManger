package com.soonfor.measuremanager.me.presenter.setting.person;

import com.google.gson.Gson;
import com.luck.picture.lib.entity.LocalMedia;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.activity.PersonalDataActivity;
import com.soonfor.measuremanager.me.bean.PersonDataBean;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.PreferenceUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cn.jesse.nativelogger.NLogger;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class PersonPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {
    private PersonalDataActivity mContext;
    private Gson gson;
    private int infoType;
    private String modfyText = "";//更改后的内容

    /**
     * 获取网络数据，更新界面
     *
     * @param isRefresh 是否是swiperefresh的
     */
    public void getData(boolean isRefresh) {
        if (isRefresh) {
            mContext.showLoadingDialog();
        }
        Request.getPerson(mContext, PreferenceUtil.getCurrentUserBean().getUserId(), this);
    }

    public PersonPresenter(PersonalDataActivity personalInformationActivity) {
        this.mContext = personalInformationActivity;
    }

    /*
      更改头像步骤
      1.上传头像图片至crm，得到响应图片路径
      2.将图片路径上传至后台
      3.第2步返回成功后在界面显示头像，并调接口删除crm中原头像
    */
    public void uploadHeadImage(LocalMedia localMedia) {
        mContext.showLoadingDialog();
        String picpath = localMedia.getCompressPath();
        if(picpath==null || picpath.equals("")){
            picpath = localMedia.getPath();
        }
        if(picpath!=null && !picpath.equals("")){
            Request.uploadFileToCrm(mContext, new File(localMedia.getCompressPath()), Request.UPLOADPIC, this);
        }
    }

    ;

    public void updateInfo(int infoType, String newValue, boolean isNeedLoading) {
        this.infoType = infoType;
        this.modfyText = newValue;
        if (isNeedLoading)
            mContext.showLoadingDialog();
        Request.upDateInfo(mContext, infoType, newValue, this);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        // LogTools.showLog(mContext,object.toString());
        mContext.closeLoadingDialog();
        switch (requestCode) {
            case Request.GET_PERSON:
                gson = new Gson();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            PersonDataBean bean = gson.fromJson(data, PersonDataBean.class);
                            mContext.setPersonData(bean);
                        } catch (Exception e) {
                        }
                    }
                });
                break;
            case Request.UPDATE_INFO:
                NLogger.e("修改", object.toString());
                try {
                    if (object.getBoolean("success")) {
                        MyToast.showSuccessToast(mContext, object.getString("data"));
                        mContext.refreshAfterModifed(infoType, modfyText);
                    } else
                        MyToast.showCaveatToast(mContext, object.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Request.UPLOADPIC:
                try {
                    String error = CommonUtils.formatStr(object.getString("error"));
                    if (error.equals("0")) {
                        String filepath = CommonUtils.formatStr(object.getString("filepath"));
                        updateInfo(4, filepath, false);
                    } else {
                        MyToast.showFailToast(mContext, "上传不成功！");
                    }
                } catch (Exception e) {
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        mContext.closeLoadingDialog();
    }
}
