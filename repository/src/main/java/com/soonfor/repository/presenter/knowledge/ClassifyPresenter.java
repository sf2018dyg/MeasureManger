package com.soonfor.repository.presenter.knowledge;

import com.soonfor.repository.base.RepBasePresenter;
import com.soonfor.repository.http.httptools.RepAsyncUtils;
import com.soonfor.repository.view.knowledge.IClassfyView;

/**
 * 作者：DC-DingYG on 2018-06-23 8:48
 * 邮箱：dingyg012655@126.com
 */
public class ClassifyPresenter extends RepBasePresenter<IClassfyView> implements RepAsyncUtils.AsyncCallback {

    private IClassfyView view;

    public ClassifyPresenter(IClassfyView view) {
        this.view = view;
    }

    /**
     * 获取已经分类
     * @param requestCode
     * @param data
     */
    @Override
    public void success(int requestCode, String data) {

    }

    @Override
    public void fail(int requestCode, int statusCode, String msg) {

    }
}
