package com.soonfor.measuremanager.home.othertask.presenter.editcustomerprofile;

import com.google.gson.Gson;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.home.othertask.activity.EditCustomerProfileActivity;
import com.soonfor.measuremanager.home.othertask.fragment.editcustomerprofile.AddCustomerHuaXiangFragment;
import com.soonfor.measuremanager.home.othertask.fragment.editcustomerprofile.CheckInHuaXiangFragment;
import com.soonfor.measuremanager.home.othertask.model.bean.CustomerProfileBean;
import com.soonfor.measuremanager.home.othertask.model.bean.CustomerSelfProfileBean;
import com.soonfor.measuremanager.home.homepage.model.bean.BaseResultBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.LogTools;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONObject;
/**
 * Created by ljc on 2018/1/18.
 */

/**
 * 修改人：DC-ZhuSuiBo on 2018/8/29 0029 18:59
 * 邮箱：suibozhu@139.com
 * 修改目的：
 */
public class EditCustomerProfilePresenter implements IEditCustomerProfilePresenter, AsyncUtils.AsyncCallback {

    private String customerId;
    private EditCustomerProfileActivity view;
    private CheckInHuaXiangFragment viewF;
    private AddCustomerHuaXiangFragment viewC;

    public EditCustomerProfilePresenter(EditCustomerProfileActivity view, String customerId) {
        this.view = view;
        this.customerId = customerId;
    }

    public EditCustomerProfilePresenter(CheckInHuaXiangFragment viewF, String customerId) {
        this.viewF = viewF;
        this.customerId = customerId;
    }

    public EditCustomerProfilePresenter(AddCustomerHuaXiangFragment viewC, String customerId) {
        this.viewC = viewC;
        this.customerId = customerId;
    }

    public void getData(boolean isRefresh) {
        if (view != null) {
            Request.getCustomProfile(view, this);
        } else if (viewF != null) {
            Request.getCustomProfile(viewF.getContext(), this);
        } else {
            Request.getCustomProfile(viewC.getContext(), this);
        }
    }

    @Override
    public void saveProfile(String json) {
        if (view != null) {
            Request.savePortrait(view, this, json);
        } else if (viewF != null) {
            Request.savePortrait(viewF.getContext(), this, json);
        } else {
            Request.savePortrait(viewC.getContext(), this, json);
        }
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode) {
            case Request.GET_OPTION:
                Gson gson = new Gson();
                CustomerProfileBean bean = gson.fromJson(object.toString(), CustomerProfileBean.class);
                if (view != null) {
                    view.showProfiles(bean.getData().getList().get(0).getItems(), bean.getData().getList().get(1).getItems(),
                            bean.getData().getList().get(2).getItems());
                    Request.getCustomerPortraits(view, this, customerId);
                } else if(viewF!=null){
                    viewF.showProfiles(bean.getData().getList().get(0).getItems(), bean.getData().getList().get(1).getItems(),
                            bean.getData().getList().get(2).getItems());
                    //新增的就不要请求了
                    //Request.getSingleCustomerProfie(viewF.getContext(), this, customerId);
                }else{
                    viewC.showProfiles(bean.getData().getList().get(0).getItems(), bean.getData().getList().get(1).getItems(),
                            bean.getData().getList().get(2).getItems());
                    //Request.getSingleCustomerProfie(viewC.getContext(), this, customerId);
                }
                break;
            case Request.SAVE_PORTRAITS:
                gson = new Gson();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInSuccess(String data) {
                        MyToast.showSuccessToast(view,"保存成功");
                        view.notifyData(null);
                    }

                    @Override
                    public void doingInFail(String msg) {
                        MyToast.showSuccessToast(view,"保存失败：" + msg);
                    }
                });
//                CustomerSelfProfileBean selfProfileBean = gson.fromJson(object.toString(), CustomerSelfProfileBean.class);
//                if (view != null) {
//                   view.notifyData(selfProfileBean);
//                } else if(viewF!=null){
//                    viewF.notifyData(selfProfileBean);
//                } else {
//                    viewC.notifyData(selfProfileBean);
//                }
                break;
            default:
                gson = new Gson();
                BaseResultBean baseResultBean = gson.fromJson(object.toString(), BaseResultBean.class);
                if (baseResultBean.getMsgcode() == 0) {
                    if (view != null) {
                        MyToast.showToast(view, "保存成功");
                        view.finish();
                    } else {
                        MyToast.showToast(viewF.getContext(), "保存成功");
                    }
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        if(requestCode==Request.SAVE_PORTRAITS){
            MyToast.showSuccessToast(view,"保存失败：" + errorMsg);
        }
    }
}
