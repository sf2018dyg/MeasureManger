package com.soonfor.measuremanager.home.liangchi.presenter.detail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.liangchi.fragment.detail.IntentionalFragment;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.IntentionalEntity;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/31 11:03
 * 邮箱：suibozhu@139.com
 */

public class LiangChiIntentionalBasePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private IntentionalFragment bgFragment;
    private Gson gson;


    public void getData(String type, boolean isRefresh) {
        this.bgFragment.showLoadingDialog();
        String returnStr = null;
        bgFragment.setListView(returnStr);
    }

    public void getIntentionProducts(String orderNo) {
        this.bgFragment.showLoadingDialog();
        Request.getIntentionProducts(bgFragment.getContext(), this, orderNo);
    }

    public void getFjUri(String type) {
        Request.getFjUri(bgFragment.getContext(), type, this);
    }


    public LiangChiIntentionalBasePresenter(IntentionalFragment bgFragment) {
        this.bgFragment = bgFragment;
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        bgFragment.closeLoadingDialog();
        final Gson gson = new Gson();
        switch (requestCode) {
            case Request.GET_INTENTION_PRODUCTS:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        bgFragment.closeLoadingDialog();
                        bgFragment.showMsg(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            if (data.equals("[]")) {
                                //bgFragment.showMsg("没有意向产品数据");
                            } else {
                                List<IntentionalEntity> datas = gson.fromJson(data, new TypeToken<List<IntentionalEntity>>() {
                                }.getType());
                                bgFragment.setDatas(datas);
                            }
                        } catch (Exception e) {
                            bgFragment.showMsg(e.toString());
                        }
                    }
                });
                break;
            case Request.GETFJURI:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        bgFragment.closeLoadingDialog();
                        bgFragment.showMsg(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            bgFragment.updateSetDatas(false, data);
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
