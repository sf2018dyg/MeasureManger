package com.soonfor.measuremanager.home.order.presenter;

import com.google.gson.Gson;
import com.soonfor.measuremanager.home.order.OrderDetailActivity;
import com.soonfor.measuremanager.home.order.bean.OrderDetailBean;
import com.soonfor.measuremanager.home.order.bean.ProcessBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.other.bean.DataBean;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.apache.http.Header;
import org.json.JSONObject;


/**
 * Created by Administrator on 2018/1/20.
 */

public class OrderDetailPresenter implements IOrderDetailPresenter, AsyncUtils.AsyncCallback {

    private OrderDetailActivity view;

    public OrderDetailPresenter(OrderDetailActivity view) {
        this.view = view;
    }

    /**
     *  获取订单时间线
     * @param orderNo
     */
    public void getTimeLine(String orderNo){
        view.mLoadingDialog.show();
        Request.getTimeLine(view, this, orderNo);
    }
    @Override
    public void getOrderDetail(String orderNo) {
        Request.getOrderDetail(view, this, orderNo);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        Gson gson = new Gson();
        switch (requestCode){
            case Request.GET_ORDER_TIMELINE:
                ProcessBean processBean = gson.fromJson(object.toString(), ProcessBean.class);
               if(processBean!=null && processBean.getMsgcode()==0){
                   if(processBean.getData()!=null && processBean.getData().getList()!=null && processBean.getData().getList().size()>0){
                       ProcessBean.DataBean.ListBean listBean = processBean.getData().getList().get(0);
                       if(listBean!=null && listBean.getCustomerStage()!=null)
                           view.initStepView(listBean.getCustomerStage().getSubProcess());
                   }
               }
                break;
            case Request.ORDER_DETAIL:
                OrderDetailBean bean = gson.fromJson(object.toString(), OrderDetailBean.class);
                if (bean != null && bean.getMsgcode() == 0) {
                    view.showOrderDetail(bean);
                }
                view.mLoadingDialog.dismiss();
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        view.mLoadingDialog.dismiss();
    }
}
