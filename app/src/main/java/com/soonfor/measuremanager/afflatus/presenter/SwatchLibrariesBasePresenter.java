package com.soonfor.measuremanager.afflatus.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.afflatus.fragment.detail.SwatchLibrariesFragment;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/20 15:34
 * 邮箱：suibozhu@139.com
 */

public class SwatchLibrariesBasePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private SwatchLibrariesFragment bgFragment;
    private Gson gson;


    public void getData(String type, String keyword, String bid, boolean isRefresh) {
        //this.bgFragment.showLoadingDialog();
        Request.getBoardDetailList(bgFragment.getContext(), keyword, bid, 1, 9999, 1, this);
        //bgFragment.setListView(returnStr);
    }


    public SwatchLibrariesBasePresenter(SwatchLibrariesFragment bgFragment) {
        this.bgFragment = bgFragment;
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        bgFragment.closeLoadingDialog();
        Gson gson = new Gson();
        switch (requestCode) {
            case Request.BOARDDETAILLIST:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            JSONObject o = new JSONObject(data);
                            PageTurnBean pageTurnBean = gson.fromJson(o.getString("pageTurn"), new TypeToken<PageTurnBean>() {
                            }.getType());
                            bgFragment.setListView(o.getString("list"));
                        } catch (Exception e) {
                            NLogger.e(e.getMessage());
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
