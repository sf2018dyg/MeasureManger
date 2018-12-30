package com.soonfor.measuremanager.home.lofting.presenter.immloft;

import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.lofting.activity.immloft.detail.mj_sdk.EditLfMemberActivity;
import com.soonfor.measuremanager.home.lofting.activity.immloft.detail.mj_sdk.ShowLfMemberActivity;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by DingYg on 2018-02-10.
 */

public class EditLfMemberPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private EditLfMemberActivity efActivity;
    private ShowLfMemberActivity slfActivity;

    public EditLfMemberPresenter(EditLfMemberActivity efActivity) {
        this.efActivity = efActivity;
    }

    public EditLfMemberPresenter(ShowLfMemberActivity slfActivity) {
        this.slfActivity = slfActivity;
    }

    public void saveLoftItemData(String objid, int[] actSize, String remark) {
        efActivity.showDownLoading();
        Request.Loft.setMarkData(efActivity.getBaseContext(), objid, actSize, remark, this);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode) {
            case Request.Loft.SET_LOFT_DATA://提交放样数据成功
                efActivity.hideDownLoading();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        efActivity.hideDownLoading();
                        MyToast.showFailToast(efActivity, "部件放样数据提交失败:" + msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        efActivity.RefreshAfterSetSuccess();
                    }
                });
                break;
        }

    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        switch (requestCode) {
            case Request.Loft.SET_LOFT_DATA://提交放样数据失败
                efActivity.hideDownLoading();
                MyToast.showFailToast(efActivity, "部件放样数据提交失败");
                break;
        }

    }
}
