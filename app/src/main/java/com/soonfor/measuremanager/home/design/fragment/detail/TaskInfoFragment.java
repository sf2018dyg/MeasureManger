package com.soonfor.measuremanager.home.design.fragment.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.home.design.activity.DesignDetailActivity;
import com.soonfor.measuremanager.home.design.activity.DesignMainActivity;
import com.soonfor.measuremanager.home.design.model.DesignItemBean;
import com.soonfor.measuremanager.home.design.presenter.detail.TaskInfoPresenter;
import com.soonfor.measuremanager.home.grab.activity.GrabPondActivity;
import com.soonfor.measuremanager.home.grab.presenter.GrabComPresenter;
import com.soonfor.measuremanager.home.grab.presenter.IGrabComView;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.CustomerInfo;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.TaskProgress;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.beanTotal;
import com.soonfor.measuremanager.home.lofting.adapter.detail.TaskProgressAdapter;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.TaskProgressBean;
import com.soonfor.measuremanager.home.lofting.view.detail.ITaskInfoView;
import com.soonfor.measuremanager.tools.AppCache;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018-02-01.
 * 任务信息
 */
public class TaskInfoFragment extends BaseFragment<TaskInfoPresenter> implements ITaskInfoView {

    Unbinder unbinder;
    Activity mActivity;
    @BindView(R.id.rvhead)
    RecyclerView mRecyclerView;
    @BindView(R.id.tvfNoProgress)
    TextView tvfNoProgress;

    @BindView(R.id.tvfCustomerName)
    TextView tvfCustomerName;
    @BindView(R.id.tvfCustomerPhone)
    TextView tvfCustomerPhone;
    @BindView(R.id.tvfCustomerAddress)
    TextView tvfCustomerAddress;
    @BindView(R.id.tvfTaskStatus)
    TextView tvfTaskStatus;
    @BindView(R.id.tvfworkpoints)
    TextView tvfworkpoints;
    @BindView(R.id.tvfCustomerAsk)
    TextView tvfCustomerAsk;
    @BindView(R.id.tvfFaGtTime)
    TextView tvfFaGtTime;
    @BindView(R.id.tvfRemarkDesc)
    TextView tvfRemarkDesc;


    @BindView(R.id.bottom)
    LinearLayout llfBottom;
    @BindView(R.id.rlfReject)
    RelativeLayout rlfReject;
    @BindView(R.id.rlfAccept)
    RelativeLayout rlfAccept;
    @BindView(R.id.btn_imediaterobbing)
    RelativeLayout rlfLijiQiang;//立即抢单
    @BindView(R.id.btn_sure)
    RelativeLayout rlfSure;//确认预约

    CustomDialog ddialog;
    NormalDialog ndialog;
    String customer;
    boolean isShowllfDaiLofting = false;
    DesignItemBean taskinfo = null;
    private LinearLayoutManager manager;
    private TaskProgressAdapter tprogressAdapter;
    private int ItemPosition;

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
        return R.layout.fragment_design_detail_taskinfo;
    }

    @Override
    protected void initPresenter() {
        presenter = new TaskInfoPresenter(this);
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
        int type = bundle.getInt(Tokens.Lofing.ITEMSKIPDETAIL_STATUS, 0);
        ItemPosition = bundle.getInt("POSITION", -1);
        taskinfo = bundle.getParcelable(Tokens.Design.ITEMSKIPDETAIL_ITEM);
        switch (type) {
            case -1://抢任务
                tvfCustomerPhone.setVisibility(View.GONE);
                llfBottom.setVisibility(View.VISIBLE);
                rlfReject.setVisibility(View.GONE);
                rlfAccept.setVisibility(View.GONE);
                rlfSure.setVisibility(View.GONE);
                rlfLijiQiang.setVisibility(View.VISIBLE);
                break;
            case 1://待接收
                tvfCustomerPhone.setVisibility(View.VISIBLE);
                llfBottom.setVisibility(View.VISIBLE);
                rlfReject.setVisibility(View.VISIBLE);
                rlfAccept.setVisibility(View.VISIBLE);
                rlfSure.setVisibility(View.GONE);
                rlfLijiQiang.setVisibility(View.GONE);
                break;
            case 2://待设计
                tvfCustomerPhone.setVisibility(View.VISIBLE);
                llfBottom.setVisibility(View.GONE);
                rlfReject.setVisibility(View.GONE);
                rlfAccept.setVisibility(View.GONE);
                rlfSure.setVisibility(View.GONE);
                rlfLijiQiang.setVisibility(View.GONE);
                //isShowllfDaiLofting = false;
                break;
            case 3://待调整
                tvfCustomerPhone.setVisibility(View.VISIBLE);
                llfBottom.setVisibility(View.GONE);
                rlfReject.setVisibility(View.GONE);
                rlfAccept.setVisibility(View.GONE);
                rlfSure.setVisibility(View.GONE);
                rlfLijiQiang.setVisibility(View.GONE);
                break;
            case 4://待确图
                /**
                 * 修改人：DC-ZhuSuiBo on 2018/8/12 0012 14:27
                 * 邮箱：suibozhu@139.com
                 * 修改目的： bug 873 说去掉
                 */
                tvfCustomerPhone.setVisibility(View.VISIBLE);
                llfBottom.setVisibility(View.GONE);
                rlfReject.setVisibility(View.GONE);
                rlfAccept.setVisibility(View.GONE);
                rlfSure.setVisibility(View.VISIBLE);
                rlfLijiQiang.setVisibility(View.GONE);
                break;
            case 5://已确图
                tvfCustomerPhone.setVisibility(View.VISIBLE);
                llfBottom.setVisibility(View.GONE);
                rlfReject.setVisibility(View.GONE);
                rlfAccept.setVisibility(View.GONE);
                rlfSure.setVisibility(View.GONE);
                rlfLijiQiang.setVisibility(View.GONE);
                break;
        }
        if (taskinfo != null) {
            if (DesignDetailActivity.sTaskInfo == null) {
                presenter.getData(type, new String[]{taskinfo.getTaskNo(), taskinfo.getTaskType(), taskinfo.getOrderNo()}, true);
            }else{
                setListView(type, DesignDetailActivity.sTaskInfo);
            }
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }

    TaskProgress taskProgress = null;
    /**
     * 更新视图
     *
     * @param b
     */
    CustomerInfo cusInfo;
    public void setListView(int type, beanTotal b) {
        //StaffInfo staInfot = null;
        cusInfo = null;
        if (b != null) {
            DesignDetailActivity.sTaskInfo = b;
            cusInfo = b.getCustomerInfo();
            //staInfot = b.getStaffInfo();
            taskProgress = b.getTaskProgress();
            tvfCustomerAsk.setText(CommonUtils.formatStr(b.getCustomerNeeds()));
            tvfTaskStatus.setText(CommonUtils.formatStr(b.getTaskStatus()));
            tvfworkpoints.setText(CommonUtils.formatStr(b.getWorkPoints()));
            if (tvfFaGtTime.getText().toString().equals("")) {
                tvfFaGtTime.setText(DateTool.getTimeTimestamp(b.getExecutionTime(),null));
            }
            tvfRemarkDesc.setText(CommonUtils.formatStr(b.getDescription()));
        }
//        else {
//            int taskStatus = taskinfo.getStatus();
//            switch (taskStatus) {
//                case 1:
//                    tvfTaskStatus.setText("待接收");
//                    break;
//                case 2:
//                    tvfTaskStatus.setText("待设计");
//                    break;
//                case 3:
//                    tvfTaskStatus.setText("待调整");
//                    break;
//                case 4:
//                    tvfTaskStatus.setText("待确图");
//                    break;
//                case 5:
//                    tvfTaskStatus.setText("已确图");
//                    break;
//            }
//            tvfFaGtTime.setText(DateTool.getTimeTimestamp(taskinfo.getCommunicateDate(), "MM月dd日-HH:mm") + "");
//        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 客户
                 */
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (cusInfo != null) {
                            CommonUtils.setSex(tvfCustomerName, cusInfo.getName(), taskinfo.getCustomerSex());
                            tvfCustomerPhone.setText(CommonUtils.formatStr(cusInfo.getPhone()));
                            tvfCustomerAddress.setText(CommonUtils.formatStr(cusInfo.getAddress()));
                        } else {
                            CommonUtils.setSex(tvfCustomerName, taskinfo.getCustomerName(), taskinfo.getCustomerSex());
                            tvfCustomerPhone.setText(CommonUtils.formatStr(taskinfo.getCustomerPhone()));
                            tvfCustomerAddress.setText(CommonUtils.formatStr(taskinfo.getAddress()));
                        }
                    }
                });
            }
        }).start();
        /**
         * 进度
         */
        if(AppCache.processBean==null){
            presenter.getOptionInMain();
        }else setThisPargress();
    }

    /**
     * 获取当前进度
     */
    public void setThisPargress(){
        if(taskProgress!=null)
            presenter.getPargress(taskProgress);
    }


    /**
     * 接收成功刷新界面
     */
    public void refreshAfterAccept() {
        DesignMainActivity.ItemPosition = ItemPosition;
        mActivity.finish();
    }

    /**
     * 拒收成功刷新界面
     */
    public void refreshAfterReject() {
        DesignMainActivity.ItemPosition = ItemPosition;
        mActivity.finish();
    }
    /**
     * 确图成功后刷新界面
     */
    public void refreshAfterSure(){
        DesignMainActivity.ItemPosition = ItemPosition;
        mActivity.finish();
    }

    /**
     * 设置进度=
     */
    public void setProgressImage(List<TaskProgressBean> tpBeanList) {
        if(tpBeanList!=null && tpBeanList.size()>0) {
            tvfNoProgress.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            //设置布局管理器
            GridLayoutManager manager = new GridLayoutManager(getActivity(), tpBeanList.size());
            mRecyclerView.setLayoutManager(manager);
            //设置适配器
            tprogressAdapter = new TaskProgressAdapter(getContext(), tpBeanList);
            mRecyclerView.setAdapter(tprogressAdapter);
        }else {
            mRecyclerView.setVisibility(View.GONE);
            tvfNoProgress.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.tvfCustomerName, R.id.tvfCustomerPhone, R.id.rlfReject, R.id.rlfAccept,
            R.id.btn_imediaterobbing, R.id.btn_sure})
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
            case R.id.rlfReject:
                ndialog = ddialog.getInputDialog(mActivity, "拒收放样", "" +
                                "请填写您的拒收原因",
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick(View view) {
                                EditText et = (EditText) view.getTag();
                                ndialog.dismiss();
                                presenter.rejectTask(taskinfo.getTaskNo(), et.getText().toString());
                            }
                        }, 0);
                break;
            case R.id.rlfAccept:
                ndialog = ddialog.getNormalDialog(mActivity, "温馨提示", "亲接收后，尽快与客户确认再次上门测量日期，任务预期未处理，将会对您的信誉造成影响",
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick(View view) {
                                ndialog.dismiss();
                                presenter.acceptTask(taskinfo.getTaskNo());
                            }
                        });
                break;
            case R.id.btn_imediaterobbing:
                String hintText = "亲抢单完成后，尽快与客户再次确认上门测量日期，任务预期未处理，将会对您的信誉造成影响";
                ndialog = CustomDialog.getInstance().getNormalDialog(getActivity(), "温馨提示", hintText,
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick(View view) {
                                ndialog.dismiss();
                                if(taskinfo!=null)
                                    postToGrabTask(getActivity());
                            }
                        });
                ndialog.show();
                break;
            case R.id.btn_sure:
                ndialog = ddialog.getNormalDialog(mActivity, "温馨提示", "亲确图后，将不可再更改",
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick(View view) {
                                ndialog.dismiss();
                                presenter.acceptTask(taskinfo.getTaskNo());
                            }
                        });
                break;
        }
    }

    @Override
    public void showLoadingDialog() {
        if (ActivityUtils.isRunning(getActivity())) {
            if(mLoadingDialog!=null && !mLoadingDialog.isShowing())
                mLoadingDialog.show();
        }
    }


    @Override
    public void closeLoadingDialog() {
        if(mLoadingDialog!=null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    //抢单
    private void postToGrabTask(Activity mc){
        new GrabComPresenter(getActivity(), new IGrabComView() {
            @Override
            public void refreshAfterGrabTask(boolean isSuccess, String msg) {
                if(isSuccess){
                    MyToast.showSuccessToast(mc, "抢单成功");
                    GrabPondActivity.ItemPosition = ItemPosition;
                    mc.finish();
                }else {
                    MyToast.showFailToast(mc, "抢单失败");
                }
                closeLoadingDialog();
            }
        }).grabTask(taskinfo.getTaskNo(), taskinfo.getTaskType());
    }
}
