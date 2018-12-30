package com.soonfor.repository.presenter.staff;

import com.google.gson.Gson;
import com.soonfor.repository.base.RepBasePresenter;
import com.soonfor.repository.http.httptools.RepAsyncUtils;
import com.soonfor.repository.view.staff.IArtificialStaffView;

/**
 * 作者：DC-DingYG on 2018-06-20 13:46
 * 邮箱：dingyg012655@126.com
 * 人工客服
 */
public class ArtificialStaffPresenter extends RepBasePresenter<IArtificialStaffView> implements RepAsyncUtils.AsyncCallback {

    private IArtificialStaffView view;

    public ArtificialStaffPresenter(IArtificialStaffView view) {
        this.view = view;
    }

    @Override
    public void success(int requestCode, String data) {
        Gson gson = new Gson();

    }

    @Override
    public void fail(int requestCode, int statusCode, String msg) {

    }
}
