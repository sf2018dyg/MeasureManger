package com.soonfor.measuremanager.home.order;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.order.adapter.ExpandListViewAdapter;
import com.soonfor.measuremanager.home.order.bean.OrderDetailBean;
import com.soonfor.measuremanager.home.order.presenter.OrderDetailPresenter;
import com.soonfor.measuremanager.tools.AppCache;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.StepViewUtils;
import com.soonfor.measuremanager.view.NestedExpandableListView;
import com.soonfor.measuremanager.view.stepview.HorizontalStepView;
import com.soonfor.measuremanager.view.stepview.bean.StepBean;
import com.soonfor.repository.tools.DateTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ljc on 2018/1/11.
 * 销货订单详情
 */

public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> {

    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_file)
    TextView tvFile;
    @BindView(R.id.llfCustomerPhone)
    LinearLayout llfCustomerPhone;
    @BindView(R.id.llfStaffPhone)
    LinearLayout llfStaffPhone;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.step_view)
    HorizontalStepView stepView;
    @BindView(R.id.tv_dagou_name)
    TextView tvDagouName;
    @BindView(R.id.tv_dagou_phone)
    TextView tvDagouPhone;
    @BindView(R.id.tv_emergent)
    TextView tvEmergent;
    @BindView(R.id.tv_banggou_name)
    TextView tvBanggouName;
    @BindView(R.id.tv_banggou_phone)
    TextView tvBanggouPhone;
    @BindView(R.id.tv_install_time)
    TextView tvInstallTime;
    @BindView(R.id.tv_serie_profile)
    TextView tvSerieProfile;
    @BindView(R.id.tv_beizhu)
    TextView tvBeizhu;
    @BindView(R.id.ll_show_more)
    LinearLayout llShowMore;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.elv_procut)
    NestedExpandableListView elvProcut;
    @BindView(R.id.tv_need_money)
    TextView tvNeedMoney;
    @BindView(R.id.already_money)
    TextView alreadyMoney;
    @BindView(R.id.tv_weishou_money)
    TextView tvWeishouMoney;
    @BindView(R.id.tv_status_money)
    TextView tvStatusMoney;
    @BindView(R.id.tv_status_money2)
    TextView tvStatusMoney2;
    @BindView(R.id.tv_status_money3)
    TextView tvStatusMoney3;
    @BindView(R.id.tv_status_money4)
    TextView tvStatusMoney4;
    @BindView(R.id.tv_status_money5)
    TextView tvStatusMoney5;
    @BindView(R.id.tv_status_money6)
    TextView tvStatusMoney6;
    @BindView(R.id.tv_need_money2)
    TextView tvNeedMoney2;
    @BindView(R.id.sv_view)
    ScrollView sv_view;
    private String orderNo;
    private int status;
    private boolean isGrab;

    public static void start(String orderNo, Context context, int status, boolean isGrab) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("orderNo", orderNo);
        intent.putExtra("status", status);
        intent.putExtra("ISGRAB", isGrab);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initViews() {
        orderNo = getIntent().getStringExtra("orderNo");
        status = getIntent().getIntExtra("status", 0);
        isGrab = getIntent().getBooleanExtra("ISGRAB", false);
        if(isGrab){
            llfCustomerPhone.setVisibility(View.GONE);
            llfStaffPhone.setVisibility(View.GONE);
        }else {
            llfCustomerPhone.setVisibility(View.VISIBLE);
            llfStaffPhone.setVisibility(View.VISIBLE);
        }
        if(orderNo!=null && !orderNo.equals(""))
            presenter.getTimeLine(orderNo);
    }

    @Override
    protected void initPresenter() {
        presenter = new OrderDetailPresenter(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getOrderDetail(orderNo);
    }


    @OnClick({R.id.ll_more, R.id.tv_phone_number})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_more:
                if (llShowMore.getVisibility() == View.VISIBLE) {
                    llShowMore.setVisibility(View.GONE);
                    ivArrow.setBackgroundResource(R.drawable.arrow_more);
                } else {
                    llShowMore.setVisibility(View.VISIBLE);
                    ivArrow.setBackgroundResource(R.drawable.arrow_more_up);
                }
                break;
            case R.id.tv_phone_number:
                CommonUtils.callPhone(bean.getData().getCustomerPhone(), this);
                break;
            case R.id.tv_banggou_phone:
                CommonUtils.callPhone(bean.getData().getStaffPhone(), this);
                break;
            case R.id.tv_dagou_phone:
                CommonUtils.callPhone(bean.getData().getGuidePhone(), this);
                break;
        }
    }

    private OrderDetailBean bean;
    public void initStepView(String subProcess) {
        if (subProcess != null) {
            List<String> lines = AppCache.getProcessLineNames(subProcess);
            if(lines!=null) {
                try {
                    lines = lines.subList(0, 4);
                    List<StepBean> stepBeans = new ArrayList<>();
                    int index = -1;
                    for (int i = 0; i < lines.size(); i++) {
                        StepBean stepBean = new StepBean();
                        stepBean.setName(lines.get(i));
                        stepBean.setState(1);
                        if (AppCache.getSubProcessName(subProcess).equals(lines.get(i))) {
                            index = i;
                            stepBean.setState(status != 2 ?
                                    status : -1);
                        }
                        if (index != -1 && i > index) {
                            stepBean.setState(2);
                        }
                        stepBeans.add(stepBean);
                    }
                    StepViewUtils.initCommonStep(stepView, stepBeans, this);
                }catch (Exception e){
                    stepView.setVisibility(View.GONE);
                }
            }else {
                stepView.setVisibility(View.GONE);
            }
        } else {
            stepView.setVisibility(View.GONE);
        }
    }


    /**
     * 展示界面
     *
     * @param bean
     */
    public void showOrderDetail(OrderDetailBean bean) {
        this.bean = bean;
        tvOrderId.setText(orderNo + "");
//        tvStatus.setText(bean.getData().getStatus().isEmpty() ? "" : bean.getData().getStatus());
        tvName.setText(bean.getData().getCustomerName().isEmpty() ? "" : bean.getData().getCustomerName());
        tvPhoneNumber.setText(bean.getData().getCustomerPhone().isEmpty() ? "" : bean.getData().getCustomerPhone());
        tvLocation.setText(bean.getData().getCustomerAddress().isEmpty() ? "" : bean.getData().getCustomerAddress());
        tvDagouName.setText(bean.getData().getGuideName().isEmpty() ? "" : bean.getData().getGuideName());
        tvDagouPhone.setText(bean.getData().getGuidePhone().isEmpty() ? "" : bean.getData().getGuidePhone());
        tvEmergent.setText(bean.getData().getEmgType().isEmpty() ? "" : bean.getData().getEmgType());
        tvBanggouName.setText(bean.getData().getStaffName().isEmpty() ? "" : bean.getData().getStaffName());

        tvBanggouPhone.setText(bean.getData().getStaffPhone().isEmpty() ? "" : bean.getData().getStaffPhone());
        if (!bean.getData().getInstallDate().isEmpty())
            tvInstallTime.setText(DateTools.commonFormat2(bean.getData().getInstallDate()));

        tvSerieProfile.setText(bean.getData().getActivityPackage().isEmpty() ? "" : bean.getData().getActivityPackage());
        tvBeizhu.setText(bean.getData().getRemark().isEmpty() ? "" : bean.getData().getRemark());

        ExpandListViewAdapter adapter = null;
        if (bean.getData().getBatches() != null && bean.getData().getBatches().size() != 0) {
            adapter = new ExpandListViewAdapter(this, bean.getData().getBatches());

            int width = getWindowManager().getDefaultDisplay().getWidth();
            elvProcut.setIndicatorBounds(width - 100, width - 10);
            elvProcut.setAdapter(adapter);
            elvProcut.expandGroup(0);
        }
        tvNeedMoney.setText("￥" + bean.getData().getTotalReceivable());
        alreadyMoney.setText("￥" + bean.getData().getTotalReceived());
        tvWeishouMoney.setText("￥" + bean.getData().getTotalUnbundled());

        tvStatusMoney.setText("￥" + bean.getData().getFunds().get(0).getAmount());
        tvStatusMoney2.setText("￥" + bean.getData().getFunds().get(1).getAmount());
        tvStatusMoney3.setText("￥" + bean.getData().getFunds().get(2).getAmount());
        tvStatusMoney4.setText("￥" + bean.getData().getFunds().get(3).getAmount());
        tvStatusMoney5.setText("￥" + bean.getData().getFunds().get(4).getAmount());
        tvStatusMoney6.setText("￥" + bean.getData().getFunds().get(5).getAmount());

        tvNeedMoney2.setText("￥" + bean.getData().getTotalReceivable());
//        sv_view.scrollTo(0,0);
    }
}
