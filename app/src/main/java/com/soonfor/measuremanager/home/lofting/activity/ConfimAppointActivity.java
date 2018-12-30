package com.soonfor.measuremanager.home.lofting.activity;

import android.app.Activity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.CustomerInfo;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.StaffInfo;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.beanTotal;
import com.soonfor.measuremanager.home.lofting.model.bean.LoftingMainBean;
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
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.Timepicker.wheel.DateWheel_Noprevious_Dialog;
import com.soonfor.measuremanager.view.dialog.normal.NormalDialog;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by DingYg on 2018-02-05.
 * 确认预约
 */
public class ConfimAppointActivity extends BaseActivity implements AsyncUtils.AsyncCallback {

    private Activity mActivity;
    @BindView(R.id.body)
    ScrollView scrollView;
    @BindView(R.id.customtxt)
    TextView customtxt;
    @BindView(R.id.tvfCustomerPhone)
    TextView tvfCustomerPhone;
    @BindView(R.id.tvfDaoGou)
    TextView tvfMeasurer;
    @BindView(R.id.tvfDaoGouPhone)
    TextView tvfMeasurerPhone;
    @BindView(R.id.tvfBuilding)
    TextView tvfBuilding;
    @BindView(R.id.tvfCustAddress)
    TextView tvfCustomerAddress;
    @BindView(R.id.appointmentTime)
    TextView tvfAppointmentTime;
    @BindView(R.id.appointmentDesc)
    TextView tvfYuyueDesc;
    @BindView(R.id.tvfConfirmTime)
    TextView tvfConfirmTime;
    @BindView(R.id.tvfTaskDesc)
    TextView tvfTaskDesc;
    CustomDialog cdialog;
    LoftingMainBean task;
    private int ItemPosition;

    @Override
    protected void initPresenter() {
        scrollView.scrollTo(0, 0);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_lofting_confirmation_reservation;
    }

    @Override
    protected void initViews() {
        mActivity = ConfimAppointActivity.this;
        cdialog = CustomDialog.getInstance();
        ItemPosition = getIntent().getExtras().getInt("POSITION", -1);
        task = getIntent().getExtras().getParcelable(Tokens.Lofing.SKIP_TO_CONFIMAPPOINT);
        if (task != null) {
            //设置详细信息
            if (!DataTools.loftDetailInfos.containsKey(task.getTaskNo()) || DataTools.loftDetailInfos.get(task.getTaskNo()) == null) {
                //获取详细信息
                Request.getProgressTaskInfo(mActivity, this, task.getTaskNo(), task.getTaskType(), task.getOrderNo());
                setListView(null);
            } else {
                setListView(DataTools.loftDetailInfos.get(task.getTaskNo()));
            }
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }

    /**
     * 更新视图
     *
     * @param b
     */
    public void setListView(beanTotal b) {

        CustomerInfo cusInfo = null;
        StaffInfo staInfot = null;
        if (b != null) {
            cusInfo = b.getCustomerInfo();
            staInfot = b.getStaffInfo();
            if (!CommonUtils.formatStr(b.getExecutionTime()).equals("")) {
                tvfAppointmentTime.setText(DateTool.getTimeTimestamp(b.getExecutionTime(),null));
            }
            //tvfAppointmentTimeT.setText(CommonUtils.formatStr(b.getExecutionTimeType()));
            tvfYuyueDesc.setText(CommonUtils.formatStr(b.getDescription()));
        }

        /**
         * 客户
         */
        customtxt.setText(task.getBuilding() + "-" + task.getCustomerName());
        tvfBuilding.setText(task.getBuilding());
        if (cusInfo != null) {
            tvfCustomerPhone.setText(CommonUtils.formatStr(cusInfo.getPhone()));
            tvfCustomerAddress.setText(CommonUtils.formatStr(cusInfo.getAddress()));
        } else {
            tvfCustomerPhone.setText(CommonUtils.formatStr(task.getCustomerPhone()));
            tvfCustomerAddress.setText(CommonUtils.formatStr(task.getAddress()));
        }

        /**
         * 测量员/导购员
         */
        if (staInfot != null) {
            //tvfMeasureTitle.setText(CommonUtils.formatStr(staInfot.getJobType()));
            tvfMeasurer.setText(CommonUtils.formatStr(staInfot.getName()));
            tvfMeasurerPhone.setText(CommonUtils.formatStr(staInfot.getPhone()));
        }
        if (b != null) {
            DataTools.loftDetailInfos.put(task.getTaskNo(), b);
        }
    }

    NormalDialog ndialog;

    @OnClick({R.id.llfSelectTime, R.id.tvfCustomerPhone, R.id.tvfDaoGouPhone, R.id.btn_save})
    void clickListener(View v) {
        switch (v.getId()) {
            case R.id.llfSelectTime:
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    DateWheel_Noprevious_Dialog dnDialog = new DateWheel_Noprevious_Dialog(mActivity, new DateWheel_Noprevious_Dialog.DateChooseInterface() {
                        @Override
                        public void getDateTime(String time) {
                            tvfConfirmTime.setText(time);
                        }
                    });
                    dnDialog.setDefault(null);
                    dnDialog.showDateChooseDialog();
                }
                break;
            case R.id.tvfCustomerPhone:
                if (tvfCustomerPhone.getText() != null) {
                    final String tel = tvfCustomerPhone.getText().toString();
                    if (!tel.equals("")) {
                        if (!NoDoubleClickUtils.isDoubleClick()) {
                            ndialog = cdialog.getNormalDialog(mActivity, "温馨提示", "是否拨打电话" + tel + "?",
                                    new OnBtnClickL() {
                                        @Override
                                        public void onBtnClick(View view) {
                                            ndialog.dismiss();
                                            call(mActivity, tel);
                                        }
                                    });
                        }
                    } else {
                        MyToast.showFailToast(mActivity, "电话号码不能为空");
                    }
                }
                break;
            case R.id.tvfDaoGouPhone:
                if (tvfMeasurerPhone.getText() != null) {
                    final String tel = tvfMeasurerPhone.getText().toString();
                    if (!tel.equals("")) {
                        if (!NoDoubleClickUtils.isDoubleClick()) {
                            ndialog = cdialog.getNormalDialog(mActivity, "温馨提示", "是否拨打电话" + tel + "?",
                                    new OnBtnClickL() {
                                        @Override
                                        public void onBtnClick(View view) {
                                            ndialog.dismiss();
                                            call(mActivity, tel);
                                        }
                                    });
                        }
                    } else {
                        MyToast.showFailToast(mActivity, "电话号码不能为空");
                    }
                }
                break;
            case R.id.btn_save:
                if (tvfConfirmTime.getText().toString().equals("请选择(必填)")) {
                    MyToast.showToast(mActivity, "请选择预约时间");
                    return;
                }
                if (tvfTaskDesc.getText().toString().trim().equals("")) {
                    MyToast.showToast(mActivity, "请输入任务描述");
                    return;
                }
                if (task != null) {
                    Request.confirmAppoint(mActivity, this, task.getTaskNo(),
                            PreferenceUtil.getCurrentUserBean().getSalesId(),
                            tvfTaskDesc.getText().toString(), tvfConfirmTime.getText().toString());
                }
                break;
        }
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        hideLoading();
        closeLoadingDialog();
        HeadBean headBean = JsonUtils.getHeadBean(object.toString());
        try {
            switch (requestCode) {
                case Request.GET_PROGRESS_TASK_INFO:
                    Gson gson = new Gson();
                    if (headBean != null && (headBean.getMsgcode() == 0 || headBean.isSuccess())) {
                        beanTotal tBean = gson.fromJson(headBean.getData(), beanTotal.class);
                        if (tBean != null) {
                            setListView(tBean);
                        } else
                            MyToast.showFailToast(mActivity, headBean.getFaileMsg());
                    } else {
                        MyToast.showFailToast(mActivity, headBean.getFaileMsg());
                    }
                    break;
                case Request.CONFIRM_APPOINT:
                    if (headBean != null) {
                        if (headBean.getMsgcode() == 0 || headBean.isSuccess()) {
                            LoftingMainActivity.ItemPosition = ItemPosition;
                            LoftingDetailActivity.FinishLdActivity();
                            MyToast.showFailToast(mActivity, "预约信息保存成功");
                            finish();
                        } else
                            MyToast.showFailToast(mActivity, headBean.getFaileMsg());
                    } else
                        MyToast.showFailToast(mActivity, headBean.getFaileMsg());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        closeLoadingDialog();
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }
}
