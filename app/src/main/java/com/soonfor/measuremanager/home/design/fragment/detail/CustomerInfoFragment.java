package com.soonfor.measuremanager.home.design.fragment.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.home.design.activity.DesignDetailActivity;
import com.soonfor.measuremanager.home.design.model.DesignItemBean;
import com.soonfor.measuremanager.home.design.presenter.detail.CustomerInfoPresenter;
import com.soonfor.measuremanager.home.design.view.detail.ICustomerInfoView;
import com.soonfor.measuremanager.home.grab.model.bean.GrabOrderBean;
import com.soonfor.measuremanager.home.liangchi.adapter.detail.IntentionalAdpter;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.IntentionalEntity;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.CustomerInfo;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.StaffInfo;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.beanTotal;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.other.bean.DataBean;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.Tokens;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018-02-01.
 * 客户信息
 */

public class CustomerInfoFragment extends BaseFragment<CustomerInfoPresenter> implements ICustomerInfoView {

    Unbinder unbinder;
    Activity mActivity;
    @BindView(R.id.tvfCustomer)
    TextView tvfCustomer;
    @BindView(R.id.tvfCustomerPhone)
    TextView tvfCustomerPhone;
    @BindView(R.id.tvMeasurerT)
    TextView tvfMeasureT;
    @BindView(R.id.tvfMeasurer)
    TextView tvfMeasurer;
    @BindView(R.id.tvfMeasurerPhone)
    TextView tvfMeasurerPhone;
    @BindView(R.id.tvfHouseType)
    TextView tvfHouseType;
    @BindView(R.id.tvfArea)
    TextView tvfArea;
    @BindView(R.id.tvfPredictFitmentDate)
    TextView tvfPredictFitmentDate;
    @BindView(R.id.tvfGtRequirements)
    TextView tvfGtRequirements;
    //    @BindView(R.id.recyView)
//    RecyclerView mRecyclerView;
    @BindView(R.id.tvfSerize)
    TextView tvfSerize;
    @BindView(R.id.tvfStyle)
    TextView tvfStyle;
    List<IntentionalEntity> datas;
//    private GridLayoutManager manager;
//    IntentionalAdpter adpter;
    DesignItemBean task;
    CustomerInfo customerInfo = null;//客户信息

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_design_customerinfo;
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        datas = new ArrayList<>();
//        manager = new GridLayoutManager(getContext(), 1);
//        adpter = new IntentionalAdpter(getContext(), datas);
//        mRecyclerView.setLayoutManager(manager);
//        mRecyclerView.setAdapter(adpter);
        return rootView;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        task = getArguments().getParcelable(Tokens.Design.ITEMSKIPDETAIL_ITEM);
        if (DesignDetailActivity.sTaskInfo != null) {
            setDesignerView(DesignDetailActivity.sTaskInfo);
        } else {
            presenter.getData(new String[]{task.getTaskNo(), task.getTaskType(), task.getOrderNo()}, true);
        }
        presenter.getCustomerInfo(mActivity, task.getCustomerId());
        //presenter.getIntentionProducts(task.getTaskNo());

    }

    @Override
    protected void initPresenter() {
        presenter = new CustomerInfoPresenter(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }

    @Override
    public void showDataToView(String returnJson) {
        super.showDataToView(returnJson);
    }

    /**
     * 展示设计师信息
     *
     * @param b
     */
    public void setDesignerView(beanTotal b) {
        CustomerInfo cusInfo = null;
        StaffInfo staInfot = null;
        if (b != null) {
            DesignDetailActivity.sTaskInfo = b;
            cusInfo = b.getCustomerInfo();
            staInfot = b.getStaffInfo();
        }
        /**
         * 导购员或设计师
         */
        if (staInfot != null) {
            tvfMeasureT.setText(staInfot.getJobType());
            tvfMeasurer.setText(staInfot.getName());
            tvfMeasurerPhone.setText(staInfot.getPhone());
        }
        /**
         * 客户
         */
        if (cusInfo != null) {
            CommonUtils.setSex(this.tvfCustomer, cusInfo.getName(), task.getCustomerSex());
            tvfCustomerPhone.setText(CommonUtils.formatStr(cusInfo.getPhone()));
        } else {
            CommonUtils.setSex(this.tvfCustomer, task.getCustomerName(), task.getCustomerSex());
            tvfCustomerPhone.setText(CommonUtils.formatStr(task.getCustomerPhone()));
        }
    }

    /**
     * 请求客户信息后根据code获取对应信息并显示
     *
     * @param
     */
    public void setCustomerInfo(CustomerInfo custInfo) {
        this.customerInfo = custInfo;
        if (customerInfo != null) {
            String estimDate = customerInfo.getEstimateDate();
            if (!estimDate.equals("")) {
                String hh = estimDate.substring(estimDate.indexOf(" ") + 1, estimDate.indexOf(" ") + 3);
                if (hh.equals("00")) {
                    hh = estimDate.substring(0, estimDate.indexOf(" "));
                    tvfPredictFitmentDate.setText(hh);
                } else {
                    tvfPredictFitmentDate.setText(estimDate);
                }
            }
            tvfArea.setText(customerInfo.getHouseSize());
            tvfGtRequirements.setText(customerInfo.getNeeds());
            //更新户型
            if (DataTools.doortypesBean != null) {
                setHustType(customerInfo.getDoorType());
            } else {
                presenter.getAllTypes(mActivity, "DoorType", Request.DESIGN_GETALLTYPES_Door);
            }
            //更新意向产品
            if (DataTools.goodstypesBean != null) {
                showIntenPros(customerInfo.getIntentionProduct());
            } else {
                presenter.getAllTypes(mActivity, "GoodsClassType", Request.DESIGN_GETALLTYPES_GoodsClass);
            }
        }
    }

    /**
     * 获取选项类型后处理
     */
    public void setGetAllTypes(int actionType, boolean isSuccess) {
        switch (actionType) {
            case Request.DESIGN_GETALLTYPES_GoodsClass://请求商品列表
                if (isSuccess) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showIntenPros(customerInfo.getIntentionProduct());
                        }
                    });
                }
                break;
            case Request.DESIGN_GETALLTYPES_Door://请求门户列表
                if (isSuccess) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setHustType(customerInfo.getDoorType());
                        }
                    });
                }
                break;
        }
    }


    /**
     * 更新意向产品视图
     *
     * @param returnStr
     */
    public void setIntentionalListView(String returnStr) {
        closeLoadingDialog();
        HeadBean headBean = JsonUtils.getHeadBean(returnStr);
//        if (headBean != null && (headBean.getMsgcode() == 0 || headBean.isSuccess())) {
//            if (!headBean.getData().equals("[]")) {
//                datas = new Gson().fromJson(returnStr, new TypeToken<List<IntentionalEntity>>() {
//                }.getType());
//            }
//        }
//        adpter.notifyDataSetChanged(datas);
        closeLoadingDialog();
    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void closeLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //展示户型数据
    private void setHustType(String doorType) {
        if (!doorType.equals("")) {
            if (DataTools.doortypesBean != null) {
                List<DataBean.ListBean> list = DataTools.doortypesBean.getList();
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getCode().equals(doorType)) {
                            tvfHouseType.setText(list.get(i).getName());
                            break;
                        }
                    }

                }
            }
        }
    }

    //获取意向数据
    private void showIntenPros(CustomerInfo.IntentionProduct product) {
        if (product != null) {
            List<String> serizeCodes = product.getSeries();
            String serizeName = "";
            List<String> styleCodes = product.getStyles();
            String styleName = "";
            if (DataTools.goodstypesBean != null) {
                List<DataBean.ListBean> list = DataTools.goodstypesBean.getList();
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getName().equals("系列")) {
                            List<DataBean.ListBean> serizes = list.get(i).getItems();
                            for (int s = 0; s < serizeCodes.size(); s++) {
                                for (int j = 0; j < serizes.size(); j++) {
                                    if (serizeCodes.get(s).equals(serizes.get(j).getCode())) {
                                        if(s==0){
                                            serizeName = serizes.get(j).getName();
                                        }else {
                                            serizeName += "，" + serizes.get(j).getName();
                                        }
                                        break;
                                    }
                                }
                            }
                        } else if (list.get(i).getName().equals("款式")) {
                            List<DataBean.ListBean> styles = list.get(i).getItems();
                            for (int s = 0; s < styleCodes.size(); s++) {
                                for (int j = 0; j < styles.size(); j++) {
                                    if (styleCodes.get(s).equals(styles.get(j).getCode())) {
                                        if(s==0){
                                            styleName = styles.get(j).getName();
                                        }else {
                                            styleName += "，" + styles.get(j).getName();
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            tvfSerize.setText(serizeName);
            tvfStyle.setText(styleName);
        }
    }
}
