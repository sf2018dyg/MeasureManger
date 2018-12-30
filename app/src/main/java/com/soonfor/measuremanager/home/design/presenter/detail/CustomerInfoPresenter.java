package com.soonfor.measuremanager.home.design.presenter.detail;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.home.design.fragment.detail.CustomerInfoFragment;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.CustomerInfo;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.beanTotal;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.other.bean.DataBean;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2018-01-31.
 */

public class CustomerInfoPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private CustomerInfoFragment bgFragment;
    private Gson gson;


    //获取详细信息
    public void getData(String task[], boolean isRefresh) {
        if (isRefresh)
            this.bgFragment.showLoadingDialog();
        Request.getProgressTaskInfo(bgFragment.getContext(), this, task[0], task[1], task[2]);
    }


    public CustomerInfoPresenter(CustomerInfoFragment bgFragment) {
        this.bgFragment = bgFragment;
    }

    /**
     * 获取选项类型
     */
    public void getAllTypes(Context context, String optionType, int actionType) {
        Request.getOption(context, this, optionType, actionType);
    }

    /**
     * 获取客户信息
     */
    public void getCustomerInfo(Context context, String customerId) {
        Request.getCustomerInfo(context, this, customerId);
    }

    /**
     * 获取意向产品
     *
     * @param orderNo
     */
    public void getIntentionProducts(String orderNo) {
        this.bgFragment.showLoadingDialog();
        //Request.getIntentionProducts(bgFragment.getContext(), this, orderNo);
        Request.getPreOrder(bgFragment.getContext(), this, orderNo);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        HeadBean headBean = JsonUtils.getHeadBean(object.toString());
        switch (requestCode) {
            case Request.GET_PROGRESS_TASK_INFO:
                if (headBean != null && (headBean.getMsgcode() == 0 || headBean.isSuccess())) {
                    beanTotal tBean = gson.fromJson(headBean.getData(), beanTotal.class);
                    if (tBean != null) {
                        bgFragment.setDesignerView(tBean);
                    } else {
                        bgFragment.closeLoadingDialog();
                        MyToast.showFailToast(bgFragment.getContext(), headBean.getFaileMsg());
                    }
                } else {
                    MyToast.showFailToast(bgFragment.getContext(), headBean.getFaileMsg());
                    bgFragment.setDesignerView(null);
                }
                break;
            case Request.DESIGN_GETALLTYPES_GoodsClass:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        getAllTypes(bgFragment.getActivity(), "DoorType", Request.DESIGN_GETALLTYPES_Door);
                        bgFragment.setGetAllTypes(Request.DESIGN_GETALLTYPES_GoodsClass, false);

                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            DataBean dataBean = new Gson().fromJson(data, new TypeToken<DataBean>() {
                            }.getType());
                            if (dataBean != null) {
                                DataTools.goodstypesBean = dataBean;
                            }
                        } catch (Exception e) {
                        }
                        bgFragment.setGetAllTypes(Request.DESIGN_GETALLTYPES_GoodsClass, true);
                    }
                });
                break;
            case Request.DESIGN_GETALLTYPES_Door:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        bgFragment.setGetAllTypes(Request.DESIGN_GETALLTYPES_Door, false);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            DataBean dataBean = new Gson().fromJson(data, new TypeToken<DataBean>() {
                            }.getType());
                            if (dataBean != null) {
                                DataTools.doortypesBean = dataBean;
                                bgFragment.setGetAllTypes(Request.DESIGN_GETALLTYPES_Door, true);
                            }
                        } catch (Exception e) {
                        }
                    }
                });
                break;
            case Request.GET_CUSTOM_MESSAGE://获取客户信息
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        bgFragment.setCustomerInfo(null);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            CustomerInfo customerInfo = new Gson().fromJson(data, new TypeToken<CustomerInfo>() {
                            }.getType());
                            if (customerInfo != null) {
                                bgFragment.setCustomerInfo(customerInfo);
                            }
                        } catch (Exception e) {
                            e.fillInStackTrace();
                        }
                    }
                });
                break;

            case Request.GET_INTENTION_PRODUCTS:
                bgFragment.setIntentionalListView(object.toString());
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        bgFragment.closeLoadingDialog();
        switch (requestCode) {
            case Request.GET_PROGRESS_TASK_INFO:
                break;
            case Request.DESIGN_GETALLTYPES_GoodsClass:
                getAllTypes(bgFragment.getActivity(), "DoorType", Request.DESIGN_GETALLTYPES_Door);
                bgFragment.setGetAllTypes(Request.DESIGN_GETALLTYPES_GoodsClass, false);
                break;
            case Request.DESIGN_GETALLTYPES_Door:
                bgFragment.setGetAllTypes(Request.DESIGN_GETALLTYPES_Door, false);
                break;
            case Request.GET_INTENTION_PRODUCTS:

                break;
            case Request.GET_CUSTOM_MESSAGE://获取客户信息
                bgFragment.setCustomerInfo(null);
                break;
        }
    }
}
