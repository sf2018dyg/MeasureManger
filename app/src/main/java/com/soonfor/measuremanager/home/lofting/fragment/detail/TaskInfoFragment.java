package com.soonfor.measuremanager.home.lofting.fragment.detail;

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
import com.soonfor.measuremanager.home.grab.activity.GrabPondActivity;
import com.soonfor.measuremanager.home.grab.presenter.GrabComPresenter;
import com.soonfor.measuremanager.home.grab.presenter.IGrabComView;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.CustomerInfo;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.StaffInfo;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.TaskProgress;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.beanTotal;
import com.soonfor.measuremanager.home.lofting.activity.ConfimAppointActivity;
import com.soonfor.measuremanager.home.lofting.activity.LoftingMainActivity;
import com.soonfor.measuremanager.home.lofting.activity.immloft.ImmediateLoftingActivity;
import com.soonfor.measuremanager.home.lofting.adapter.detail.TaskProgressAdapter;
import com.soonfor.measuremanager.home.lofting.model.bean.LoftingMainBean;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.TaskProgressBean;
import com.soonfor.measuremanager.home.lofting.presenter.detail.TaskInfoPresenter;
import com.soonfor.measuremanager.home.lofting.view.detail.ITaskInfoView;
import com.soonfor.measuremanager.tools.AppCache;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.DataTools;
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
 * Created by DingYg on 2018-02-01.
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
    @BindView(R.id.tvMeasurerT)
    TextView tvfMeasureTitle;
    @BindView(R.id.tvfMeasurer)
    TextView tvfMeasurer;
    @BindView(R.id.tvfCustomerAsk)
    TextView tvfCustomerAsk;
    @BindView(R.id.tvfYuyueTime)
    TextView tvfFyYuyueTime;
    @BindView(R.id.tvfYuyueDesc)
    TextView tvfYuyueDesc;

    @BindView(R.id.llfDaiLofting)
    LinearLayout llfDaiLofting;//待放样和放样完成时显示
    @BindView(R.id.tvfSureTimeT)
    TextView tvfSureTimeT;
    @BindView(R.id.tvfSureLoftTime)
    TextView tvfSureLoftTime;
    @BindView(R.id.tvfSureLoftDes)
    TextView tvfSureLoftDes;
    @BindView(R.id.tvfLoftedTime)
    TextView tvfLoftedTime;

    @BindView(R.id.bottom)
    LinearLayout llfBottom;
    @BindView(R.id.rlfReject)
    RelativeLayout rlfReject;
    @BindView(R.id.rlfAccept)
    RelativeLayout rlfAccept;
    @BindView(R.id.btn_imediaterobbing)
    RelativeLayout rlfLijiQiang;//立即抢单
    @BindView(R.id.btn_yuyue)
    RelativeLayout rlfYuyue;//确认预约
    @BindView(R.id.btn_immediatelyFy)
    RelativeLayout rlfLijiFy;//立即放样
    @BindView(R.id.tvfMeasurerPhone)
    TextView tvfMeasurerPhone;
    @BindView(R.id.llfFyedDate)
    LinearLayout llfFyedDate;

    CustomDialog ddialog;
    NormalDialog ndialog;
    String customer;
    boolean isShowllfDaiLofting = false;
    LoftingMainBean taskinfo = null;
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
        return R.layout.fragment_lofting_detail_taskinfo;
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
        taskinfo = bundle.getParcelable(Tokens.Lofing.ITEMSKIPDETAIL_ITEM);
        switch (type) {
            case -1://抢任务
                tvfCustomerPhone.setVisibility(View.GONE);
                llfBottom.setVisibility(View.VISIBLE);
                rlfReject.setVisibility(View.GONE);
                rlfAccept.setVisibility(View.GONE);
                rlfYuyue.setVisibility(View.GONE);
                rlfLijiFy.setVisibility(View.GONE);
                rlfLijiQiang.setVisibility(View.VISIBLE);
                llfDaiLofting.setVisibility(View.GONE);
                llfFyedDate.setVisibility(View.GONE);
                //isShowllfDaiLofting = false;
                break;
            case 1://待接收
                tvfCustomerPhone.setVisibility(View.VISIBLE);
                llfBottom.setVisibility(View.VISIBLE);
                rlfReject.setVisibility(View.VISIBLE);
                rlfAccept.setVisibility(View.VISIBLE);
                rlfYuyue.setVisibility(View.GONE);
                rlfLijiFy.setVisibility(View.GONE);
                rlfLijiQiang.setVisibility(View.GONE);
                llfDaiLofting.setVisibility(View.GONE);
                llfFyedDate.setVisibility(View.GONE);
                //isShowllfDaiLofting = false;
                break;
            case 2://待确认预约
                tvfCustomerPhone.setVisibility(View.VISIBLE);
                llfBottom.setVisibility(View.VISIBLE);
                rlfReject.setVisibility(View.GONE);
                rlfAccept.setVisibility(View.GONE);
                rlfYuyue.setVisibility(View.VISIBLE);
                rlfLijiFy.setVisibility(View.GONE);
                rlfLijiQiang.setVisibility(View.GONE);
                llfDaiLofting.setVisibility(View.GONE);
                llfFyedDate.setVisibility(View.GONE);
                //isShowllfDaiLofting = false;
                break;
            case 3://待放样
                tvfCustomerPhone.setVisibility(View.VISIBLE);
                llfBottom.setVisibility(View.VISIBLE);
                rlfReject.setVisibility(View.GONE);
                rlfAccept.setVisibility(View.GONE);
                rlfYuyue.setVisibility(View.GONE);
                rlfLijiFy.setVisibility(View.VISIBLE);
                rlfLijiQiang.setVisibility(View.GONE);
                llfDaiLofting.setVisibility(View.VISIBLE);
                llfFyedDate.setVisibility(View.GONE);
                //isShowllfDaiLofting = true;
                break;
            case 4://放样完成
                tvfCustomerPhone.setVisibility(View.VISIBLE);
                llfBottom.setVisibility(View.GONE);
                llfDaiLofting.setVisibility(View.VISIBLE);
                llfFyedDate.setVisibility(View.VISIBLE);
                // isShowllfDaiLofting = true;
                break;
        }
        if (taskinfo != null) {
            presenter.getData(type, new String[]{taskinfo.getTaskNo(), taskinfo.getTaskType(), taskinfo.getOrderNo()}, true);
            setListView(type, null);
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
    CustomerInfo cusInfo;
    StaffInfo staInfot;
    TaskProgress taskProgress = null;

    public void setListView(int type, beanTotal b) {
        closeLoadingDialog();
        cusInfo = null;
        staInfot = null;
        if (b != null) {
            cusInfo = b.getCustomerInfo();
            staInfot = b.getStaffInfo();
            taskProgress = b.getTaskProgress();
            tvfCustomerAsk.setText(CommonUtils.formatStr(b.getCustomerNeeds()));
            tvfYuyueDesc.setText(CommonUtils.formatStr(b.getDescription()));
            if (type == 3 || type == 4) {
                tvfSureLoftDes.setText(CommonUtils.formatStr(b.getConfirmDesc()));
            }
            tvfTaskStatus.setText(CommonUtils.formatStr(b.getTaskStatus()));
            tvfworkpoints.setText(CommonUtils.formatStr(b.getWorkPoints()));
            if (taskinfo.getAppointDate() != 0) {
                tvfFyYuyueTime.setText(DateTool.getTimeTimestamp(b.getDispatchTime(),null));
            }
            if (type == 3 || type == 4) {
                tvfSureTimeT.setText(b.getExecutionTimeType());
                tvfSureLoftTime.setText(DateTool.getTimeTimestamp(b.getExecutionTime(), null));
            }
            if (type == 4) {
                tvfLoftedTime.setText(DateTool.getTimeTimestamp(b.getFinishTime(), null));
            }
        } else {
//            tvfTaskStatus.setText(taskinfo.getStatusDesc());
//            tvfworkpoints.setText(taskinfo.getWorkPoints());
            if (taskinfo.getAppointDate() != 0) {
                tvfFyYuyueTime.setText(DateTool.getTimeTimestamp(taskinfo.getAppointDate(),null));
            }
            if (type == 3 || type == 4) {
                tvfSureTimeT.setText("确认上门时间");
                tvfSureLoftTime.setText(DateTool.getTimeTimestamp(taskinfo.getConfirmDate(), null));
            }
            if (type == 4) {
               tvfLoftedTime.setText(DateTool.getTimeTimestamp(taskinfo.getFinishDate(), null));
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * 客户
                         */
                        if (cusInfo != null) {
                            CommonUtils.setSex(tvfCustomerName, cusInfo.getName(), taskinfo.getCustomerSex());
                            tvfCustomerPhone.setText(CommonUtils.formatStr(cusInfo.getPhone()));
                            tvfCustomerAddress.setText(CommonUtils.formatStr(cusInfo.getAddress()));
                        } else {
                            CommonUtils.setSex(tvfCustomerName, taskinfo.getCustomerName(), taskinfo.getCustomerSex());
                            tvfCustomerPhone.setText(CommonUtils.formatStr(taskinfo.getCustomerPhone()));
                            tvfCustomerAddress.setText(CommonUtils.formatStr(taskinfo.getAddress()));
                        }
                        /**
                         * 测量员/导购员
                         */
                        if (staInfot != null) {
                            tvfMeasureTitle.setText(CommonUtils.formatStr(staInfot.getJobType()));
                            tvfMeasurer.setText(CommonUtils.formatStr(staInfot.getName()));
                            tvfMeasurerPhone.setText(CommonUtils.formatStr(staInfot.getPhone()));
                        }
                        if (b != null) {
                            DataTools.loftDetailInfos.put(taskinfo.getTaskNo(), b);
                        }
                    }
                });
            }
        }).start();
        /**
         * 进度
         */
        if (AppCache.processBean == null) {
            presenter.getOptionInMain();
        } else setThisPargress();
    }

    /**
     * 获取当前进度
     */
    public void setThisPargress() {
        if (taskProgress != null)
            presenter.getPargress(taskProgress);
    }


    /**
     * 接收成功刷新界面
     */
    public void refreshAfterAccept() {
        LoftingMainActivity.ItemPosition = ItemPosition;
        mActivity.finish();
    }

    /**
     * 拒收成功刷新界面
     */
    public void refreshAfterReject() {
        LoftingMainActivity.ItemPosition = ItemPosition;
        mActivity.finish();
    }

    /**
     * 设置进度=
     */
    public void setProgressImage(List<TaskProgressBean> tpBeanList) {
        if (tpBeanList != null && tpBeanList.size() > 0) {
            tvfNoProgress.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            //设置布局管理器
            GridLayoutManager manager = new GridLayoutManager(getActivity(), tpBeanList.size());
            mRecyclerView.setLayoutManager(manager);
            //设置适配器
            tprogressAdapter = new TaskProgressAdapter(getContext(), tpBeanList);
            mRecyclerView.setAdapter(tprogressAdapter);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            tvfNoProgress.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.tvfCustomerName, R.id.tvfCustomerPhone, R.id.rlfReject, R.id.rlfAccept,
            R.id.btn_imediaterobbing, R.id.btn_yuyue, R.id.btn_immediatelyFy, R.id.tvfMeasurerPhone})
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
            case R.id.tvfMeasurerPhone:
                if (tvfMeasurerPhone.getText() != null) {
                    final String tel = tvfMeasurerPhone.getText().toString();
                    if (!tel.equals("")) {
                        ndialog = dialog.getNormalDialog(getContext(), "温馨提示", "是否拨打电话" + tel + "?",
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick(View view) {
                                        ndialog.dismiss();
                                        call(mActivity, tel);
                                    }
                                });
                    } else {
                        MyToast.showFailToast(mActivity, "电话号码不能为空");
                    }
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
                String hintText = "亲抢单完成后，尽快与客户再次确认放样日期，任务预期未处理，将会对您的信誉造成影响";
                ndialog = CustomDialog.getInstance().getNormalDialog(getActivity(), "温馨提示", hintText,
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick(View view) {
                                ndialog.dismiss();
                                if (taskinfo != null)
                                    postToGrabTask(getActivity());
                            }
                        });
                ndialog.show();
                break;
            case R.id.btn_yuyue:
                Bundle bundle0 = new Bundle();
                if (taskinfo != null) {
                    bundle0.putParcelable(Tokens.Lofing.SKIP_TO_CONFIMAPPOINT, taskinfo);
                }
                bundle0.putInt("POSITION", ItemPosition);
                startNewAct(ConfimAppointActivity.class, bundle0);
                break;
            case R.id.btn_immediatelyFy:
                Bundle bundle = new Bundle();
                bundle.putString(Tokens.Lofing.DETAILSKIPIMMELOFT_TITLE, customer + "-放样");
                bundle.putParcelable(Tokens.Lofing.DETAILSKIPIMMELOFT_TASKINFO, taskinfo);
                bundle.putInt("POSITION", ItemPosition);
                startNewAct(ImmediateLoftingActivity.class, bundle);
                break;
        }
    }

    @Override
    public void showLoadingDialog() {
        if (ActivityUtils.isRunning(getActivity())) {
            if (mLoadingDialog != null && !mLoadingDialog.isShowing())
                mLoadingDialog.show();
        }
    }


    @Override
    public void closeLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //抢单
    private void postToGrabTask(Activity mc) {
        new GrabComPresenter(getActivity(), new IGrabComView() {
            @Override
            public void refreshAfterGrabTask(boolean isSuccess, String msg) {
                if (isSuccess) {
                    MyToast.showSuccessToast(mc, "抢单成功");
                    GrabPondActivity.ItemPosition = ItemPosition;
                    mc.finish();
                } else {
                    MyToast.showFailToast(mc, "抢单失败");
                }
                closeLoadingDialog();
            }
        }).grabTask(taskinfo.getTaskNo(), taskinfo.getTaskType());
    }
}
