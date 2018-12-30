package com.soonfor.measuremanager.upload.presenter;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.upload.fragment.UploadFragment;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * 作者：DC-ZhuSuiBo on 2018/4/4 0004 14:24
 * 邮箱：suibozhu@139.com
 * 类用途：
 */

public class CaseBasePresenter implements IBasePresenter, AsyncUtils.AsyncCallback{

    private UploadFragment bgFragment;
    private Gson gson;


    public CaseBasePresenter(UploadFragment bgFragment) {
        this.bgFragment = bgFragment;
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        bgFragment.closeLoadingDialog();
        switch (requestCode) {
//            case Request.GET_OPTION_TYPE:
//
//                Gson gson = new Gson();
//                LogTools.showLog(getContext(), object.toString());
//                BaseResultBean<ListBean<OptionTypeBean>> resultBean2 =
//                        gson.fromJson(object.toString(), new TypeToken<BaseResultBean<ListBean<OptionTypeBean>>>() {
//                        }.getType());
//
//                AppCache.typeBeanList = resultBean2.getData().getList();
//                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        bgFragment.closeLoadingDialog();
    }
}
