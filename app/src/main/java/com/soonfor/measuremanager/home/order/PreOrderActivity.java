package com.soonfor.measuremanager.home.order;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.OptionsPickerView;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.order.adapter.PreOrderAdapter;
import com.soonfor.measuremanager.home.order.bean.PreOrderBean;
import com.soonfor.measuremanager.home.order.bean.StaffBean;
import com.soonfor.measuremanager.home.order.presenter.PreOrderPresenter;
import com.soonfor.measuremanager.other.adapter.PhotoAdapter;
import com.soonfor.measuremanager.other.adapter.layoutmanager.FullyLinearLayoutManager;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.repository.tools.DateTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ljc on 2018/1/9.
 * 预订单详情
 */

public class PreOrderActivity extends BaseActivity<PreOrderPresenter> {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.llfCustomerPhone)
    LinearLayout llfCustomerPhone;
    @BindView(R.id.llfStaffPhone)
    LinearLayout llfStaffPhone;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.ll_daogou)
    LinearLayout llDaogou;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_from)
    TextView tvFrom;
    @BindView(R.id.tv_daogou_name)
    TextView tvDaogouName;
    @BindView(R.id.tvChoiceName)
    TextView tvChoiceName;
    @BindView(R.id.tv_customer_address)
    TextView tvCustomerAddress;
    @BindView(R.id.tv_customer_require_time)
    TextView tvCustomerRequireTime;
    @BindView(R.id.iv_door_type)
    RecyclerView ivDoorType;
    @BindView(R.id.tv_require)
    TextView tvRequire;
//    @BindView(R.id.image_voice_view)
//    ImageVoiceView imageVoiceView;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.tv_door_type)
    TextView tvDoorType;
    @BindView(R.id.tv_phone_number1)
    TextView tv_phone_number1;
    @BindView(R.id.imageMore)
    ImageView imageMore;
    @BindView(R.id.ll_intent_product)
    LinearLayout llIntentProduct;

    private PreOrderAdapter adapter;

    private FullyLinearLayoutManager manager;

    private String userId, navManName;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_pre_order;
    }

    /**
     * 0是查看 1是接收
     */
    private int type = -1;

    @Override
    protected void initViews() {
        type = getIntent().getIntExtra("type", -1);
    }

    /**
     * 展示人员名单
     */
    private void showStaffOptions() {
        if (beans == null || beans.size() == 0) {
            return;
        }
        final ArrayList<String> list = new ArrayList<>();
        for (StaffBean.DataBean.ListBean bean : beans) {
            list.add(bean.getName());
        }
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this,
                new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        tvDaogouName.setText(list.get(options1));
                        tvChoiceName.setVisibility(View.GONE);
                        userId = beans.get(options1).getId()+"";
                        navManName = beans.get(options1).getName()+"";
                    }
                }).build();
        pvOptions.setPicker(list);
        pvOptions.show();
    }

    public String getUserId() {
        return userId;
    }
    public String getName() {
        return navManName;
    }

    private String orderNo;
    private String orderId;
    private boolean isGrab;

    private List<StaffBean.DataBean.ListBean> beans;

    public void setBeans(List<StaffBean.DataBean.ListBean> beans) {
        this.beans = beans;
    }


    @Override
    protected void initPresenter() {
        presenter = new PreOrderPresenter(this);
        orderNo = getIntent().getStringExtra("orderNo");
        orderId = getIntent().getStringExtra("orderId");
        isGrab = getIntent().getBooleanExtra("ISGRAB", false);
        if(isGrab){
            llfCustomerPhone.setVisibility(View.GONE);
            llfStaffPhone.setVisibility(View.GONE);
        }else {
            llfCustomerPhone.setVisibility(View.VISIBLE);
            llfStaffPhone.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getPreOrder(orderNo);
      //  presenter.getData(isRefresh);
    }


    @OnClick({R.id.ll_more, R.id.ll_tel,
            R.id.ll_daogou, R.id.tv_phone_number})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_phone_number:
                CommonUtils.callPhone(bean.getData().getCustomerPhone(), this);
                break;
            case R.id.ll_tel:
                CommonUtils.callPhone(bean.getData().getTel(), this);
                break;
            case R.id.ll_more:
                if (llContainer.getVisibility() == View.VISIBLE) {
                    llContainer.setVisibility(View.GONE);
                    imageMore.setBackgroundResource(R.drawable.arrow_more);
                } else {
                    llContainer.setVisibility(View.VISIBLE);
                    imageMore.setBackgroundResource(R.drawable.arrow_more_up);
                }
                break;
            case R.id.ll_daogou:
                showStaffOptions();
                break;
        }
    }

    private PreOrderBean bean;

    public void showPreOrder(final PreOrderBean bean) {
        this.bean = bean;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (bean.getData().getAttaches() != null &&
//                        bean.getData().getAttaches().size() != 0) {
//                    final ArrayList<String> images = new ArrayList<>();
//                    final ArrayList<VoiceBean> voices = new ArrayList<>();
//                    String loaction = null;
//
//                    for (PreOrderBean.DataBean.AttachesBean infosBean : bean.getData().getAttaches()) {
//                        if (infosBean.getAttachType() == 1) {
//                            voices.add(new VoiceBean(infosBean.getAttachUrl(),0));
//                        }
//                        if (infosBean.getAttachType() == 0) {
//                            images.add(infosBean.getAttachUrl());
//                        }
//                        if (infosBean.getAttachType() == 4) {
//                            loaction=infosBean.getLocation()+"";
//                        }
//                    }
//
//                    if (images.size() != 0) {
//                        imageVoiceView.setImages(images);
//                        imageVoiceView.setListener(new ImageVoiceView.OnClickListener() {
//                            @Override
//                            public void onImageClick(int position) {
//                                ARouter.getInstance().build("/ui/ShowPicActivity")
//                                        .withStringArrayList("piclists", images)
//                                        .withInt("position", position)
//                                        .navigation(PreOrderActivity.this);
//
//                            }
//                        });
//                    }
//
//                    if (voices.size() > 0) {
//                        imageVoiceView.setVoice(voices);
//                    }
//                    if (loaction != null) {
//                        imageVoiceView.setLocation(loaction);
//                    }
//                }
//            }
//        }).start();
        tvFrom.setText(bean.getData().getSourceMethod());
        tvTime.setText(DateTools.commonFormat3(bean.getData().getSourceTime()));
        tvName.setText(bean.getData().getCustomerName());
        tvNumber.setText(bean.getData().getOrderNo() + "");
        tv_phone_number1.setText(bean.getData().getTel());
        tvPhoneNumber.setText(bean.getData().getCustomerPhone());

        tvCustomerAddress.setText(TextUtils.isEmpty(bean.getData().getCustomerAddress()) ? ""
                : bean.getData().getCustomerAddress());
        if (!TextUtils.isEmpty(bean.getData().getFitmentDate())) {
            tvCustomerRequireTime.setText(DateFormat.format("MM-dd", Long.parseLong(bean.getData().getFitmentDate())));
        } else {
            tvCustomerRequireTime.setText("");
        }

        tvDoorType.setText(TextUtils.isEmpty(bean.getData().getDoorType()) ? ""
                : bean.getData().getDoorType());

        if (bean.getData().getDoorPicture() != null && bean.getData().getDoorPicture().size() != 0) {
            PhotoAdapter adapter = new PhotoAdapter(this,
                    bean.getData().getDoorPicture(), PhotoAdapter.TYPE_URL);

            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);

            ivDoorType.setLayoutManager(manager);
            ivDoorType.setAdapter(adapter);
        }

        tvRequire.setText(TextUtils.isEmpty(bean.getData().getCommunicateNeeds()) ? ""
                : bean.getData().getCommunicateNeeds());
        tvRemark.setText(TextUtils.isEmpty(bean.getData().getRemark()) ? ""
                : bean.getData().getRemark());

        manager = new FullyLinearLayoutManager(this);

        adapter = new PreOrderAdapter(this, bean.getData().getIntentionProducts());

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        if (type != 0)
           // presenter.setGetOrderWorkpoint(bean.getData().getFordid());
//        presenter.setGetOrderDefaultScore(bean.getData().getReceivedAmt());

        if (bean.getData().getIntentionProducts() != null && bean.getData().getIntentionProducts().size() != 0) {
            llIntentProduct.setVisibility(View.VISIBLE);
        } else {
            llIntentProduct.setVisibility(View.GONE);
        }
    }

    public static void start(String orderNo, int type, Activity context, String orderId, boolean isGrab) {
        Intent intent = new Intent(context, PreOrderActivity.class);
        intent.putExtra("orderNo", orderNo);
        intent.putExtra("type", type);
        intent.putExtra("orderId", orderId);
        intent.putExtra("ISGRAB", isGrab);
        context.startActivityForResult(intent, 0);
    }
}


