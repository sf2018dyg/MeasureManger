package com.soonfor.measuremanager.afflatus.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.afflatus.bean.postBean.PostCaseBean;
import com.soonfor.measuremanager.afflatus.fragment.detail.DesignCaseFragment;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.login.activity.LoginActivity;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/20 15:44
 * 邮箱：suibozhu@139.com
 */

public class DesignCaseBasePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private DesignCaseFragment bgFragment;
    private Gson gson;

    public void getData(String type, PostCaseBean post, boolean isRefresh) {
        //this.bgFragment.showLoadingDialog();
        String sortColumn = "a.create_time";
        if (type.equals("0")) {
            sortColumn = "a.create_time";
        } else if (type.equals("1")) {
            sortColumn = "b.intents";
        } else if (type.equals("2")) {
            sortColumn = "b.likes";
        } else if (type.equals("3")) {
            sortColumn = "b.collects";
        }

        Request.pageCase(bgFragment.getContext(), post.getMeritsIdList(), sortColumn, post.getSceneId().equals("-1") ? "" : post.getSceneId(), post.getKeyword(), post.getStyleIdList(), post.getAreaIdList(), post.getPageNo(), post.getPageSize(), post.getHuxingIdList(), post.getPriceIdList(), post.getSortOrder(), this);
        //bgFragment.setListView(returnStr);
    }

    public void saveLike(String id) {
        Request.saveLikes(bgFragment.getContext(), id, this);
    }

    public DesignCaseBasePresenter(DesignCaseFragment bgFragment) {
        this.bgFragment = bgFragment;
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        bgFragment.closeLoadingDialog();
        Gson gson = new Gson();
        switch (requestCode) {
            case Request.PAGECASE:
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
            case Request.SAVELIKES:
                bgFragment.RefreshData(true);
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        bgFragment.closeLoadingDialog();
    }
}
