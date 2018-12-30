package com.soonfor.measuremanager.home.liangchi.presenter.detail;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.liangchi.fragment.detail.PortraitFragment;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/31 11:03
 * 邮箱：suibozhu@139.com
 */

public class LiangChiPortraitBasePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private PortraitFragment bgFragment;
    private Gson gson;

    public void getAllOptionCustomerPortraits() {
        AsyncUtils.get(bgFragment.getActivity(), UserInfo.GET_OPTION + "?optionType=CustPortraitType", Request.GET_OPTION, this);
    }

    public void getCustomerPortraits(String customerId) {
        Request.getCustomerPortraits(bgFragment.getActivity(), this, customerId);
    }

    public void savePortraits(String list) {
        Request.savePortrait(bgFragment.getActivity(), this, list);
    }


    public LiangChiPortraitBasePresenter(PortraitFragment bgFragment) {
        this.bgFragment = bgFragment;
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        bgFragment.closeLoadingDialog();
        switch (requestCode) {
            case Request.GET_CUSTOM_PORTRAITS:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {

                        bgFragment.closeLoadingDialog();
                        bgFragment.showMsg(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            bgFragment.getCustiomPortraits(data);
                        } catch (Exception e) {
                            bgFragment.showMsg(e.toString());
                        }
                    }
                });
                break;
            case Request.GET_OPTION:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        bgFragment.closeLoadingDialog();
                        bgFragment.showMsg(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            bgFragment.getAllOptionCustomerPortraits(data);
                        } catch (Exception e) {
                            bgFragment.showMsg(e.toString());
                        }
                    }
                });
                break;
            case Request.SAVE_PORTRAITS:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        bgFragment.closeLoadingDialog();
                        bgFragment.showMsg(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            bgFragment.savePortraits(data);
                        } catch (Exception e) {
                            bgFragment.showMsg(e.toString());
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        bgFragment.closeLoadingDialog();
    }
}
