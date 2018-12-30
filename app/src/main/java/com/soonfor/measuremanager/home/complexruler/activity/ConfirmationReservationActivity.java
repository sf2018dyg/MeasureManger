package com.soonfor.measuremanager.home.complexruler.activity;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.CustomerInfo;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.StaffInfo;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.beanTotal;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.NoDoubleClickUtils;
import com.soonfor.measuremanager.tools.PreferenceUtil;
import com.soonfor.measuremanager.view.Timepicker.wheel.DateWheel_Noprevious_Dialog;
import com.soonfor.measuremanager.view.dialog.normal.NormalDialog;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/19 9:15
 * 邮箱：suibozhu@139.com
 * 确认预约
 */
public class ConfirmationReservationActivity extends BaseActivity implements View.OnClickListener, AsyncUtils.AsyncCallback {

    Activity mActivity;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.customtxt)
    TextView customtxt;
    @BindView(R.id.appointmentTime)
    TextView appointmentTime;
    @BindView(R.id.customPhone)
    TextView customPhone;
    @BindView(R.id.shoppingGuide)
    TextView shoppingGuide;
    NormalDialog ndialog;
    @BindView(R.id.btn_save)
    RelativeLayout btn_save;
    LiangChiBean lb;
    @BindView(R.id.subscribeDescribe)
    TextView subscribeDescribe;
    @BindView(R.id.txtadress)
    TextView txtadress;
    @BindView(R.id.txtbuliding)
    TextView txtbuliding;
    @BindView(R.id.txtAppointDate)
    TextView txtAppointDate;
    @BindView(R.id.txtAppointDesc)
    TextView txtAppointDesc;
    @BindView(R.id.tvfDaoGou)
    TextView tvfDaoGou;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_fuchi_confirmation_reservation;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);// 关闭title
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(listener);
        mActivity = ConfirmationReservationActivity.this;

        lb = getIntent().getParcelableExtra("LiangChiBean");
        //设置详细信息
        if(!DataTools.fuchiDetailInfos.containsKey(lb.getTaskNo()) || DataTools.fuchiDetailInfos.get(lb.getTaskNo())==null){
            //获取详细信息
            mLoadingDialog.show();
            Request.getProgressTaskInfo(mActivity, this, lb.getTaskNo(), lb.getTaskType(), lb.getOrderNo());
            setListView(null);
        }else{
            setListView(DataTools.fuchiDetailInfos.get(lb.getTaskNo()));
        }


       /* customtxt.setText(CommonUtils.formatStr(lb.getBuilding()) + "-" + CommonUtils.formatStr(lb.getCustomerName()));
        customPhone.setText(CommonUtils.formatStr(lb.getCustomerPhone()));
        txtbuliding.setText(CommonUtils.formatStr(lb.getBuilding()));
        txtadress.setText(CommonUtils.formatStr(lb.getAddress()));
        txtAppointDate.setText(DateTool.getTimeTimestamp(lb.getAppointDate(), "MM月dd日-HH:mm") + "");
        txtAppointDesc.setText("");*/

        appointmentTime.setOnClickListener(this);
        customPhone.setOnClickListener(this);
        shoppingGuide.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    /**
     * 更新视图
     *
     * @param b
     */
    public void setListView(beanTotal b) {
        CustomerInfo cusInfo = null;
        StaffInfo staInfot = null;
        if(b!=null){
            cusInfo = b.getCustomerInfo();
            staInfot = b.getStaffInfo();
            if(!CommonUtils.formatStr(b.getExecutionTime()).equals("")){
                txtAppointDate.setText(DateTool.getTimeTimestamp(b.getExecutionTime(), null));
            }
            //tvfAppointmentTimeT.setText(CommonUtils.formatStr(b.getExecutionTimeType()));
            txtAppointDesc.setText(CommonUtils.formatStr(b.getDescription()));
        }

        /**
         * 客户
         */
        String loupan = CommonUtils.formatStr(lb.getBuilding()).equals("")?"":(CommonUtils.formatStr(lb.getBuilding())+ "-");
        customtxt.setText(loupan + CommonUtils.formatStr(lb.getCustomerName()));
        txtbuliding.setText(CommonUtils.formatStr(lb.getBuilding()));
        if(cusInfo!=null){
            customPhone.setText(CommonUtils.formatStr(cusInfo.getPhone()));
            txtadress.setText(CommonUtils.formatStr(cusInfo.getAddress()));
        }else{
            customPhone.setText(CommonUtils.formatStr(lb.getCustomerPhone()));
            txtadress.setText(CommonUtils.formatStr(lb.getAddress()));
        }

        /**
         * 测量员/导购员
         */
        if(staInfot!=null){
            //tvfMeasureTitle.setText(CommonUtils.formatStr(staInfot.getJobType()));
            tvfDaoGou.setText(CommonUtils.formatStr(staInfot.getName()));
            shoppingGuide.setText(CommonUtils.formatStr(staInfot.getPhone()));
        }
        if(b!=null){
            DataTools.fuchiDetailInfos.put(lb.getTaskNo(),b);
        }

    }

    @Override
    public void onClick(View v) {
        CustomDialog cdialog = CustomDialog.getInstance();
        switch (v.getId()) {
            case R.id.appointmentTime:
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    DateWheel_Noprevious_Dialog dialog = new DateWheel_Noprevious_Dialog(ConfirmationReservationActivity.this, new DateWheel_Noprevious_Dialog.DateChooseInterface() {
                        @Override
                        public void getDateTime(String time) {
                            appointmentTime.setText(time + "");
                        }
                    });
                    dialog.setDefault(null);
                    dialog.showDateChooseDialog();
                }
                break;
            case R.id.customPhone:
                ndialog = cdialog.getNormalDialog(ConfirmationReservationActivity.this, "温馨提示", "是否拨打电话? " + customPhone.getText(),
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick(View view) {
                                call(ConfirmationReservationActivity.this, customPhone.getText().toString());
                                ndialog.dismiss();
                            }
                        });
                break;
            case R.id.shoppingGuide:
                ndialog = cdialog.getNormalDialog(ConfirmationReservationActivity.this, "温馨提示", "是否拨打电话? " + shoppingGuide.getText(),
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick(View view) {
                                call(ConfirmationReservationActivity.this, customPhone.getText().toString());
                                ndialog.dismiss();
                            }
                        });
                break;
            case R.id.btn_save:
                if (appointmentTime.getText().toString().equals("请选择(必填)")) {
                    showTip("请选择预约时间");
                } else {
                    if (subscribeDescribe.getText().toString().equals("")) {
                        showTip("请输入任务描述");
                    } else {
                        mLoadingDialog.show();
                        Request.confirmAppoint(ConfirmationReservationActivity.this, this, lb.getTaskNo(), PreferenceUtil.getCurrentUserBean().getSalesId(), subscribeDescribe.getText().toString(), appointmentTime.getText().toString());
                    }
                }
                break;
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    public void success(int requestCode, JSONObject object) {
        mLoadingDialog.dismiss();
        Gson gson = new Gson();
        HeadBean headBean = JsonUtils.getHeadBean(object.toString());
        switch (requestCode) {
            case Request.GET_PROGRESS_TASK_INFO:
                if (headBean != null && (headBean.getMsgcode()==0 || headBean.isSuccess())) {
                    beanTotal tBean = gson.fromJson(headBean.getData(), beanTotal.class);
                    if (tBean != null) {
                        setListView(tBean);
                    } else
                        MyToast.showFailToast(mActivity, headBean.getFaileMsg());
                }else {
                    MyToast.showFailToast(mActivity, headBean.getFaileMsg());
                }
                break;
            case Request.CONFIRM_APPOINT:
                if (headBean != null) {
                    if (headBean.getMsgcode() == 0 || headBean.isSuccess()) {
                        MyToast.showFailToast(mActivity, "预约信息保存保存成功！");
                        finish();
                        FuChiDetailActivity.FinishFcDetialActivity();
                    } else
                        MyToast.showFailToast(mActivity, headBean.getFaileMsg());
                } else
                    MyToast.showFailToast(mActivity, headBean.getFaileMsg());
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
         closeLoadingDialog();
    }

    public void showTip(String msg) {
        MyToast.showToast(ConfirmationReservationActivity.this, msg);
    }
}
