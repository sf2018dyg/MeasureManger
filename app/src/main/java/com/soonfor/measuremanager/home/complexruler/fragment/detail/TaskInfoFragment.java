package com.soonfor.measuremanager.home.complexruler.fragment.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.soonfor.measuremanager.home.complexruler.activity.ConfirmationReservationActivity;
import com.soonfor.measuremanager.home.complexruler.presenter.detail.FuChiDetailBasePresenter;
import com.soonfor.measuremanager.home.complexruler.view.detail.IFuChiDetailBaseView;
import com.soonfor.measuremanager.home.grab.activity.GrabPondActivity;
import com.soonfor.measuremanager.home.grab.presenter.GrabComPresenter;
import com.soonfor.measuremanager.home.grab.presenter.IGrabComView;
import com.soonfor.measuremanager.home.liangchi.activity.LiangChiImmediatelyActivity;
import com.soonfor.measuremanager.home.liangchi.adapter.detail.ProcessAdpter;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.TaskProgress;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.beanTotal;
import com.soonfor.measuremanager.other.bean.DataBean;
import com.soonfor.measuremanager.tools.AppCache;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.DensityUtils;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.ViewTools;
import com.soonfor.measuremanager.view.dialog.normal.NormalDialog;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;
import com.soonfor.repository.tools.ActivityUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 15:32
 * 邮箱：suibozhu@139.com
 * 任务信息
 */
public class TaskInfoFragment extends BaseFragment<FuChiDetailBasePresenter> implements IFuChiDetailBaseView, View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.bottom)
    LinearLayout bottom;
    @BindView(R.id.btn_imediaterobbing)
    RelativeLayout btn_imediaterobbing;
    NormalDialog ndialog;
    @BindView(R.id.btn_yuyue)
    RelativeLayout btn_yuyue;
    @BindView(R.id.btn_immediatelyfuchi)
    RelativeLayout btn_immediatelyfuchi;
    @BindView(R.id.btn_jushou)
    RelativeLayout btn_jushou;
    @BindView(R.id.btn_jieshou)
    RelativeLayout btn_jieshou;
    @BindView(R.id.llfqrsmsj)
    LinearLayout llfqrsmsj;
    @BindView(R.id.llfqrsmsjline)
    View llfqrsmsjline;
    @BindView(R.id.llfqrsmms)
    LinearLayout llfqrsmms;
    @BindView(R.id.llfqrsmmsline)
    View llfqrsmmsline;
    @BindView(R.id.llflcwcsj)
    LinearLayout llflcwcsj;
    @BindView(R.id.llflcwcsjline)
    View llflcwcsjline;
    @BindView(R.id.txt1)
    TextView customname;
    @BindView(R.id.txt2)
    TextView customAdress;
    @BindView(R.id.tvfSurveyor)
    TextView tvfSurveyor;
    @BindView(R.id.customPhone)
    TextView customPhone;
    @BindView(R.id.tvfrwzt)
    TextView tvfrwzt;
    LiangChiBean lb;
    @BindView(R.id.txtgongfen)
    TextView txtgongfen;
    @BindView(R.id.txtmeasu)
    TextView txtmeasu;
    @BindView(R.id.txtkehuxuqiu)
    TextView txtkehuxuqiu;
    @BindView(R.id.txtyuyueliangchishijian)
    TextView txtyuyueliangchishijian;
    @BindView(R.id.txtyuyuemiaoshu)
    TextView txtyuyuemiaoshu;
    String taskNo;
    String orderNo;
    @BindView(R.id.txtquerenshangmensj)
    TextView txtquerenshangmensj;
    @BindView(R.id.txtquerenshangmenms)
    TextView txtquerenshangmenms;
    @BindView(R.id.txtliangfinshsj)
    TextView txtliangfinshsj;
    @BindView(R.id.rvlProcessView)
    RecyclerView rvlProcessView;
    ProcessAdpter pAdpter;
    @BindView(R.id.txtProcessError)
    TextView txtProcessError;

    private int itemPosition;

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        btn_jieshou.setOnClickListener(this);
        btn_jushou.setOnClickListener(this);
        btn_imediaterobbing.setOnClickListener(this);
        btn_yuyue.setOnClickListener(this);
        btn_immediatelyfuchi.setOnClickListener(this);
        customPhone.setOnClickListener(this);
        tvfSurveyor.setOnClickListener(this);
        customname.setOnClickListener(this);

        Bundle bundle = getArguments();
        int type = bundle.getInt("type");
        int isGrabIn = bundle.getInt("isGrabIn");
        itemPosition = bundle.getInt("POSITION", -1);
        lb = bundle.getParcelable("LiangChiBean");
        taskNo = lb == null ? "" : lb.getTaskNo();
        orderNo = lb == null ? "" : lb.getOrderNo();
        //

        switch (type) {
            case 1:
                if (isGrabIn == 1) {
                    customPhone.setVisibility(View.GONE);
                    btn_jushou.setVisibility(View.GONE);
                    btn_jieshou.setVisibility(View.GONE);
                    btn_imediaterobbing.setVisibility(View.VISIBLE);
                } else {
                    customPhone.setVisibility(View.VISIBLE);
                    btn_jushou.setVisibility(View.VISIBLE);
                    btn_jieshou.setVisibility(View.VISIBLE);
                    btn_imediaterobbing.setVisibility(View.GONE);
                }
                btn_yuyue.setVisibility(View.GONE);
                btn_immediatelyfuchi.setVisibility(View.GONE);
                bottom.setWeightSum(2);
                bottom.setVisibility(View.VISIBLE);
                llfqrsmsj.setVisibility(View.GONE);
                llfqrsmsjline.setVisibility(View.GONE);
                llfqrsmms.setVisibility(View.GONE);
                llfqrsmmsline.setVisibility(View.GONE);
                llflcwcsj.setVisibility(View.GONE);
                llflcwcsjline.setVisibility(View.GONE);
                break;
            case 2:
                customPhone.setVisibility(View.VISIBLE);
                btn_jushou.setVisibility(View.GONE);
                btn_jieshou.setVisibility(View.GONE);
                btn_imediaterobbing.setVisibility(View.GONE);
                btn_yuyue.setVisibility(View.VISIBLE);
                btn_immediatelyfuchi.setVisibility(View.GONE);
                bottom.setWeightSum(1);
                bottom.setVisibility(View.VISIBLE);
                llfqrsmsj.setVisibility(View.GONE);
                llfqrsmsjline.setVisibility(View.GONE);
                llfqrsmms.setVisibility(View.GONE);
                llfqrsmmsline.setVisibility(View.GONE);
                llflcwcsj.setVisibility(View.GONE);
                llflcwcsjline.setVisibility(View.GONE);
                break;
            case 3:
                customPhone.setVisibility(View.VISIBLE);
                btn_jushou.setVisibility(View.GONE);
                btn_jieshou.setVisibility(View.GONE);
                btn_imediaterobbing.setVisibility(View.GONE);
                btn_yuyue.setVisibility(View.GONE);
                btn_immediatelyfuchi.setVisibility(View.VISIBLE);
                bottom.setWeightSum(1);
                bottom.setVisibility(View.VISIBLE);
                llfqrsmsj.setVisibility(View.VISIBLE);
                llfqrsmsjline.setVisibility(View.VISIBLE);
                llfqrsmms.setVisibility(View.VISIBLE);
                llfqrsmmsline.setVisibility(View.VISIBLE);
                llflcwcsj.setVisibility(View.GONE);
                llflcwcsjline.setVisibility(View.GONE);
                break;
            case 4:
                customPhone.setVisibility(View.VISIBLE);
                btn_jushou.setVisibility(View.GONE);
                btn_jieshou.setVisibility(View.GONE);
                btn_imediaterobbing.setVisibility(View.GONE);
                btn_yuyue.setVisibility(View.GONE);
                btn_immediatelyfuchi.setVisibility(View.GONE);
                bottom.setWeightSum(0);
                bottom.setVisibility(View.GONE);
                llfqrsmsj.setVisibility(View.VISIBLE);
                llfqrsmsjline.setVisibility(View.VISIBLE);
                llfqrsmms.setVisibility(View.VISIBLE);
                llfqrsmmsline.setVisibility(View.VISIBLE);
                llflcwcsj.setVisibility(View.VISIBLE);
                llflcwcsjline.setVisibility(View.VISIBLE);
                break;
        }

        ViewTools.returnDrawable(getContext(), customname, 3, R.mipmap.btn_huaxiang, new int[]{0, 0, DensityUtils.dp2px(getContext(), 18), DensityUtils.dp2px(getContext(), 18)});

        return rootView;
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_fuchi_detail;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {

    }

    @Override
    protected void initPresenter() {
        presenter = new FuChiDetailBasePresenter(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getProgressTaskInfo(taskNo, orderNo);
    }

    /**
     * 更新视图
     *
     * @param returnStr
     */
    public void setListView(String returnStr) {
        closeLoadingDialog();
    }

    @Override
    public void onClick(View v) {
        CustomDialog dialog = CustomDialog.getInstance();
        switch (v.getId()) {
            case R.id.txt1:
                MyToast.showToast(getContext(), "自我画像！");
                break;
            case R.id.btn_jushou:
                ndialog = dialog.getInputDialog(getActivity(), "拒收量尺", "" +
                                "请填写您的拒绝原因!",
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick(View view) {
                                EditText et = (EditText) view.getTag();
                                presenter.rejectTask(taskNo, et.getText().toString());
                                ndialog.dismiss();
                            }
                        }, 0);
                break;
            case R.id.btn_jieshou:
                ndialog = dialog.getNormalDialog(getActivity(), "温馨提示", "亲接受后，尽快与客户再次确认上门测量日期，任务预期未处理，将会对您的信誉造成影响",
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick(View view) {
                                presenter.acceptTask(taskNo);
                                ndialog.dismiss();
                            }
                        });
                break;
            case R.id.btn_imediaterobbing:
                String hintText = "亲抢单完成后，尽快与客户再次确认上门复尺日期，任务预期未处理，将会对您的信誉造成影响";
                ndialog = CustomDialog.getInstance().getNormalDialog(getActivity(), "温馨提示", hintText,
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick(View view) {
                                ndialog.dismiss();
                                if (lb != null)
                                    postToGrabTask(getActivity());
                            }
                        });
                ndialog.show();
                break;
            case R.id.btn_yuyue:
                Bundle b = new Bundle();
                b.putParcelable("LiangChiBean", lb);
                //打开保存
                startNewAct(getContext(), ConfirmationReservationActivity.class, b);
                //startNewAct(ConfirmationReservationActivity.class, b);
                break;
            case R.id.btn_immediatelyfuchi:
                b = new Bundle();
                if (lb != null) {
                    b.putParcelable("LiangChiBean", lb);
                    startNewAct(getContext(), LiangChiImmediatelyActivity.class, b, 6666);
                    //startNewAct(LiangChiImmediatelyActivity.class, b);
                }
                break;
            case R.id.customPhone:
                ndialog = dialog.getNormalDialog(getContext(), "温馨提示", "是否拨打电话? " + customPhone.getText(),
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick(View view) {
                                call(getContext(), customPhone.getText().toString());
                                ndialog.dismiss();
                            }
                        });
                break;
            case R.id.tvfSurveyor:
                ndialog = dialog.getNormalDialog(getContext(), "温馨提示", "是否拨打电话? " + tvfSurveyor.getText(),
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick(View view) {
                                call(getContext(), customPhone.getText().toString());
                                ndialog.dismiss();
                            }
                        });
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 6666:
                String result = data.getStringExtra("requ");
                if (result.equals("success")) {
                    bottom.setVisibility(View.GONE);
                } else if (result.equals("notsuccess")) {
                    bottom.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    TaskProgress taskProgress = null;

    @Override
    public void getProgressTaskInfo(beanTotal b) {
        customname.setText(CommonUtils.formatStr(b.getCustomerInfo().getName()) + "");
        customPhone.setText(CommonUtils.formatStr(b.getCustomerInfo().getPhone()) + "");
        customAdress.setText(CommonUtils.formatStr(b.getCustomerInfo().getAddress()) + "");
        tvfrwzt.setText(CommonUtils.formatStr(b.getTaskStatus()) + "");
        txtgongfen.setText(CommonUtils.formatStr(b.getWorkPoints()) + "");
        txtmeasu.setText(CommonUtils.formatStr(b.getStaffInfo().getName()) + "");
        tvfSurveyor.setText(CommonUtils.formatStr(b.getStaffInfo().getPhone()) + "");
        //txtkehuxuqiu.setText(CommonUtils.formatStr(b.getCustomerNeeds()) + "");
        txtyuyueliangchishijian.setText(b.getExecutionTime() == null ? "" : DateTool.getTimeTimestamp(b.getExecutionTime(), "MM月dd日-HH:mm") + "");
        txtyuyuemiaoshu.setText(CommonUtils.formatStr(b.getDescription()) + "");//(CommonUtils.formatStr(b.getDescription()) + "");
        txtquerenshangmensj.setText(DateTool.getTimeTimestamp(lb.getConfirmDate(), "MM月dd日-HH:mm") + "");//确认上门时间
        txtquerenshangmenms.setText(CommonUtils.formatStr(b.getConfirmDesc()) + "");//确认上门描述
        txtliangfinshsj.setText(DateTool.getTimeTimestamp(lb.getFinishDate(), "MM月dd日-HH:mm") + "");//量尺完成时间

        //更新实体
        lb.setCustomerName(customname.getText().toString());
        lb.setCustomerPhone(customPhone.getText().toString());
        lb.setAddress(customAdress.getText().toString());
        lb.setAppointDate(b.getExecutionTime());
        if (b.getTaskProgress() != null) {
            taskProgress = b.getTaskProgress();
            if (AppCache.processBean != null) {
                setProgress();
            }else {
                presenter.getAllOrderProcess();
            }
        } else {
            showTip("进度返回为null");
            isHaveProcess(0);
        }
    }

    //设置进度
    public void setProgress() {
        for (DataBean.ListBean bean : AppCache.processBean.getList()) {
            if (taskProgress.getMainProcess().equals(bean.getCode())) {
                List<DataBean.ListBean> bdetail = bean.getItems();
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                rvlProcessView.setLayoutManager(manager);
                pAdpter = new ProcessAdpter(getContext(), bdetail, taskProgress.getSubProcess(), taskProgress.getStatus());
                rvlProcessView.setAdapter(pAdpter);
                isHaveProcess(1);
            }
        }
    }

    private void isHaveProcess(int type) {
        switch (type) {
            case 0:
                rvlProcessView.setVisibility(View.GONE);
                txtProcessError.setVisibility(View.VISIBLE);
                break;
            case 1:
                rvlProcessView.setVisibility(View.VISIBLE);
                txtProcessError.setVisibility(View.GONE);
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
    public void showTip(String msg) {
        MyToast.showToast(getContext(), msg);
    }

    public void afterSuccessHideInterface(boolean isSuccess) {
        try {
            if (isSuccess) {
                bottom.setWeightSum(0);
                bottom.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            NLogger.e("复尺" + e.getMessage());
        }
    }

    /**
     * 显示错误信息
     *
     * @param msg
     */
    public void showError(String msg) {
        //MyToast.showToast(getContext(), msg);
        NLogger.e("获取复尺任务详情异常:" + msg);
        closeLoadingDialog();
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
                    GrabPondActivity.ItemPosition = itemPosition;
                    mc.finish();
                } else {
                    MyToast.showFailToast(mc, "抢单失败");
                }
                closeLoadingDialog();
            }
        }).grabTask(taskNo, lb.getTaskType());
    }
}
