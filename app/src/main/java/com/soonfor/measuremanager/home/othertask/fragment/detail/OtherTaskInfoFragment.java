package com.soonfor.measuremanager.home.othertask.fragment.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.home.grab.activity.GrabPondActivity;
import com.soonfor.measuremanager.home.grab.presenter.GrabComPresenter;
import com.soonfor.measuremanager.home.grab.presenter.IGrabComView;
import com.soonfor.measuremanager.home.lofting.adapter.detail.TaskProgressAdapter;
import com.soonfor.measuremanager.home.order.OrderDetailActivity;
import com.soonfor.measuremanager.home.order.PreOrderActivity;
import com.soonfor.measuremanager.home.othertask.activity.UpdateTaskResultActivity;
import com.soonfor.measuremanager.home.othertask.model.bean.OtherTaskMainBean;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.OtherTaskDetailBean;
import com.soonfor.measuremanager.home.othertask.presenter.OtherTaskInfoPresenter;
import com.soonfor.measuremanager.home.othertask.view.detail.IOtherTaskInfoView;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.DensityUtils;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.tools.ViewTools;
import com.soonfor.measuremanager.view.dialog.normal.NormalDialog;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;
import com.soonfor.repository.tools.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DingYg on 2018-02-01.
 * 任务信息
 */
public class OtherTaskInfoFragment extends BaseFragment<OtherTaskInfoPresenter> implements IOtherTaskInfoView {

    Unbinder unbinder;
    Activity mActivity;

    @BindView(R.id.tvfStutas)
    TextView tvfTaskStutas;
    @BindView(R.id.tvfPublishTime)
    TextView tvfPublishTime;
    @BindView(R.id.tvfPoints)
    TextView tvfWorkPoints;
    @BindView(R.id.tvfCustomerName)
    TextView tvfCustomerName;
    @BindView(R.id.tvfCustomerPhone)
    TextView tvfCustomerPhone;
    @BindView(R.id.tvfCustomerAddress)
    TextView tvfCustomerAddress;

    @BindView(R.id.tvfExecutePay)
    TextView tvfExecutePay;
    @BindView(R.id.tvfTaskNo)
    TextView tvfTaskNo;
    @BindView(R.id.tvfExecuteTime)
    TextView tvfExecuteTime;

    @BindView(R.id.llfTaskDesc)
    LinearLayout llfTaskDesc;
    @BindView(R.id.tvfTaskDesc)
    TextView tvfTaskDesc;
    @BindView(R.id.rlfOrder)
    RelativeLayout rlfOrder;
    @BindView(R.id.llfMindle)
    LinearLayout llfOrderTo;
    @BindView(R.id.imgfNext)
    ImageView imgfNext;
    @BindView(R.id.tvfOrderNo)
    TextView tvfOrderNo;
    @BindView(R.id.tvfOrderType)
    TextView tvfOrderType;
    @BindView(R.id.tvfButton)
    TextView tvfButton;

    //回访
    @BindView(R.id.llfReturnVisitContent)
    LinearLayout llfVisitContent;
    @BindView(R.id.tvfVisitContent)
            TextView tvfVisitContent;

    CustomDialog ddialog;
    NormalDialog ndialog;
    OtherTaskMainBean taskinfo = null;
    OtherTaskDetailBean detailBean = null;
    public static boolean isUpdatedResult = false;//任务更新结果是否已经保存
    private int ItemPosition;
    int type = 0;//是否抢单

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mActivity = getActivity();

        return rootView;
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_othertaskinfo;
    }

    @Override
    protected void initPresenter() {
        presenter = new OtherTaskInfoPresenter(this);
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        ddialog = CustomDialog.getInstance();
        ViewTools.returnDrawable(mActivity, tvfCustomerName, 3, R.mipmap.btn_huaxiang, new int[]{0, 0, DensityUtils.dp2px(getContext(), 18), DensityUtils.dp2px(getContext(), 18)});
        //获取是否从抢 按钮进入 1 ：是 其它：不是
        Bundle bundle = getArguments();
        type = bundle.getInt(Tokens.OtherTask.OT_ITEMSKIPDETAIL_STATUS, 0);
        ItemPosition = bundle.getInt("POSITION", -1);
        taskinfo = (OtherTaskMainBean) bundle.getSerializable(Tokens.OtherTask.OT_ITEMSKIPDETAIL_ITEM);
        isUpdatedResult = false;
        if (taskinfo != null) {
            setViewByDetailInfo(true, null);
            if (type == -1) {//抢单
                tvfCustomerPhone.setVisibility(View.GONE);
                tvfButton.setVisibility(View.VISIBLE);
                tvfButton.setText("立即抢单");
                tvfButton.setOnClickListener(grabListener);
            } else {
                tvfCustomerPhone.setVisibility(View.VISIBLE);
                if (type == 3) {//已完成
                    tvfButton.setVisibility(View.GONE);
                } else {
                    tvfButton.setVisibility(View.VISIBLE);
                    tvfButton.setText("更新任务结果");
                }
                tvfButton.setOnClickListener(toUpdateTaskViewListener);
            }
            mSwipeRefresh.setEnableLoadmore(false);
            mSwipeRefresh.autoRefresh();
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }

    @Override
    public void RefreshData(boolean isNeedProgressDialog) {
        super.RefreshData(isNeedProgressDialog);
        if (taskinfo != null) {
            if (taskinfo.getTaskType().equals("4")) {//主动追踪任务
                presenter.getTaskDetail(0, taskinfo.getTaskId());
                llfTaskDesc.setVisibility(View.VISIBLE);
                rlfOrder.setVisibility(View.VISIBLE);
            } else if (taskinfo.getTaskType().equals("5")) {//人工回访任务
                presenter.getTaskDetail(1, taskinfo.getTaskId());
                llfTaskDesc.setVisibility(View.GONE);
                rlfOrder.setVisibility(View.GONE);
                llfVisitContent.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isUpdatedResult) {
            isUpdatedResult = false;
            getActivity().finish();
        }
    }

    /**
     * 更新视图
     * 根据详情信息设置界面信息
     */
    public void setViewByDetailInfo(boolean isFirst, OtherTaskDetailBean detail) {
        closeLoadingDialog();
        if (detail == null && isFirst) {
            tvfTaskStutas.setText(taskinfo.getStatusDesc());
            tvfWorkPoints.setText(taskinfo.getWorkPoints());
            CommonUtils.setSex(tvfCustomerName, taskinfo.getCustomerName(), taskinfo.getCustomerSex());
            tvfCustomerAddress.setText(taskinfo.getAddress());
            tvfExecutePay.setText(taskinfo.getExectypeDesc());
            tvfExecuteTime.setText(taskinfo.getExecDate());
        } else {
            finishRefresh();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    detailBean = detail;
                    tvfPublishTime.setText(detail.getPublishTime());
                    tvfCustomerPhone.setText(detail.getMobilePhone());
                    tvfTaskNo.setText(detail.getTaskNo());
                    tvfWorkPoints.setText(detail.getWorkPoints());
                    tvfExecuteTime.setText(detail.getExecDate());
                    if(taskinfo.getTaskType().equals("4")){
                        tvfTaskDesc.setText(detail.getDescription());
                        tvfOrderNo.setText(detail.getOrderNo());
                        String orderType = detail.getOrderTypeDesc();
                        if (!orderType.equals("")) {
                            tvfOrderType.setText("【" + orderType + "】");
                            imgfNext.setVisibility(View.VISIBLE);
                        }
                    }else if(taskinfo.getTaskType().equals("5")){
                        String visitCt = "";
                        if(detail.getVisitDto()!=null){
                            visitCt = "服务项目：" + detail.getVisitDto().getServicePrj()
                                    + "\n"+ detail.getVisitDto().getForiType() + "：" + detail.getVisitDto().getEcecuterName()
                                    + "\n完成时间：" + DateTool.getTimeTimestamp(detail.getVisitDto().getCompleteTime(), null);
                        }
                        tvfVisitContent.setText(visitCt);
                    }
                }
            },500);
        }
    }


    @OnClick({R.id.tvfCustomerName, R.id.tvfCustomerPhone, R.id.llfMindle})
    void clickListener(View view) {
        CustomDialog dialog = CustomDialog.getInstance();
        switch (view.getId()) {
            case R.id.tvfCustomerName:
                //MyToast.showToast(mActivity, "自我画像");
                break;
            case R.id.tvfCustomerPhone:
                if (tvfCustomerPhone.getText() != null) {
                    final String tel = tvfCustomerPhone.getText().toString();
                    if (!tel.equals("")) {
                        ndialog = dialog.getNormalDialog(getContext(), "温馨提示", "是否拨打电话" + tel + "?",
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick(View view) {
                                        ndialog.dismiss();
                                        call(mActivity, tel);
                                    }
                                });
                    } else
                        MyToast.showFailToast(mActivity, "电话号码不能为空");
                }
                break;
            case R.id.llfMindle:
                if (detailBean != null) {
                    if (detailBean.getOrderTypeDesc().startsWith("预")) {
                        PreOrderActivity.start(detailBean.getOrderNo(), Integer.parseInt(detailBean.getOrdType()), mActivity, detailBean.getTaskId(), type==-1?true:false);
                    } else if (detailBean.getOrderTypeDesc().startsWith("销")) {
                        OrderDetailActivity.start(detailBean.getOrderNo(), mActivity, Integer.parseInt(detailBean.getOrdType()), type==-1?true:false);
                    }
                }
                break;
        }
    }

    public void refreshAfterGrabTask() {

    }

    @Override
    public void showLoadingDialog() {
        if (ActivityUtils.isRunning(getActivity())) {
            mLoadingDialog.show();
        }
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

    //立即抢单
    private View.OnClickListener grabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showLoadingDialog();
            new GrabComPresenter(mActivity, new IGrabComView() {
                @Override
                public void refreshAfterGrabTask(boolean isSuccess, String msg) {
                    if (isSuccess) {
                        MyToast.showSuccessToast(mActivity, "抢单成功");
                        GrabPondActivity.ItemPosition = ItemPosition;
                    } else {
                        MyToast.showFailToast(mActivity, "抢单失败");
                    }
                    closeLoadingDialog();
                }
            }).grabTask(taskinfo.getTaskId(), taskinfo.getTaskType());
        }
    };
    //更新任务结果
    private View.OnClickListener toUpdateTaskViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mActivity, UpdateTaskResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("VIEWTYPE", 2);
//            if (detailBean != null) {
//                bundle.putSerializable(Tokens.OtherTask.OT_ITEMSKIPDETAIL_ITEM_DETAIL, detailBean);
//            } else {
            bundle.putSerializable(Tokens.OtherTask.OT_ITEMSKIPDETAIL_ITEM, taskinfo);
//            }
            bundle.putInt("POSITION", ItemPosition);
            intent.putExtras(bundle);
            mActivity.startActivity(intent);
        }
    };
}
