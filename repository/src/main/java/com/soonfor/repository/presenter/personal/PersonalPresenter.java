package com.soonfor.repository.presenter.personal;

import android.content.Context;

import com.google.gson.Gson;
import com.soonfor.repository.base.RepBasePresenter;
import com.soonfor.repository.http.api.RepRequest;
import com.soonfor.repository.http.httptools.RepAsyncUtils;
import com.soonfor.repository.model.person.PersonInfo;
import com.soonfor.repository.view.personal.IPersonalView;

/**
 * 作者：DC-DingYG on 2018-06-15 11:52
 * 邮箱：dingyg012655@126.com
 */
public class PersonalPresenter extends RepBasePresenter<IPersonalView> implements RepAsyncUtils.AsyncCallback {
    private IPersonalView view;
    private static PersonalPresenter ppersenter;
    public static PersonalPresenter getInstense(IPersonalView view){
        if(ppersenter==null){
            ppersenter = new PersonalPresenter(view);
        }
        return ppersenter;

    }
    public PersonalPresenter(IPersonalView view) {
        this.view = view;
    }

    public void getPersonalInfo(Context mContext){
        RepRequest.Personal.getPersonalInfo(mContext,this);
    }

    @Override
    public void success(int requestCode, String data) {
        switch (requestCode){
            case RepRequest.Personal.GET_PERSONALINFO:
                Gson gson = new Gson();
                view.refreshInfo(gson.fromJson(data, PersonInfo.class));
                break;
        }

    }

    @Override
    public void fail(int requestCode, int statusCode, String msg) {
        switch (requestCode){
            case RepRequest.Personal.GET_PERSONALINFO:
                break;
        }
    }
}
