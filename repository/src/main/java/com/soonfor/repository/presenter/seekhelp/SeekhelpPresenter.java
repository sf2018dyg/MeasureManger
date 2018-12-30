package com.soonfor.repository.presenter.seekhelp;

import android.content.Context;

import com.soonfor.repository.base.RepBasePresenter;
import com.soonfor.repository.http.api.RepRequest;
import com.soonfor.repository.http.httptools.RepAsyncUtils;
import com.soonfor.repository.view.seekhelp.ISeekhelpView;


/**
 * 作者：DC-DingYG on 2018-06-20 11:07
 * 邮箱：dingyg012655@126.com
 */
public class SeekhelpPresenter extends RepBasePresenter<ISeekhelpView> implements RepAsyncUtils.AsyncCallback {
    private ISeekhelpView view;

    public SeekhelpPresenter(ISeekhelpView view) {
        this.view = view;
    }

    public void getPersonalInfo(Context mContext, boolean isRefresh) {
//        if (!isRefresh) {
//            view.showLoadingDialog();
//        }
        RepRequest.Seekhelp.getSeekhelpList(mContext, this);

    }

    @Override
    public void success(int requestCode, String data) {
        switch (requestCode) {
            case RepRequest.Seekhelp.GET_TOPKNOWLEDGE:
                view.showDataToView(data);
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String msg) {
        switch (requestCode) {
            case RepRequest.Seekhelp.GET_TOPKNOWLEDGE:
                view.showNetError(msg);
                break;
        }
    }

}
