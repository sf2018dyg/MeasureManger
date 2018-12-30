package com.soonfor.measuremanager.home.othertask.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.othertask.fragment.detail.OtherTaskInfoFragment;
import com.soonfor.measuremanager.home.othertask.model.bean.OtherTaskMainBean;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.AttachDto;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.UpdateTaskResultBean;
import com.soonfor.measuremanager.home.othertask.presenter.updatetaskresult.IUpdateTaskResultPresenter;
import com.soonfor.measuremanager.home.othertask.presenter.updatetaskresult.UpdateTaskResultPresenter;
import com.soonfor.evaluate.activity.EvaluateCustomersActivity;
import com.soonfor.evaluate.bean.RequestTemplateDto;
import com.soonfor.evaluate.bean.ReturnVisitBean;
import com.soonfor.evaluate.view.EvaluateReturnVisitView;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.NoDoubleClickUtils;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.PopupWindowFactory;
import com.soonfor.measuremanager.view.Utiliew;
import com.soonfor.measuremanager.view.dialog.normal.NormalDialog;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;
import com.soonfor.repository.tools.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：DC-DingYG on 2018-10-16 20:47
 * 邮箱：dingyg012655@126.com
 */
public class UpdateTaskResultActivity extends BaseActivity<IUpdateTaskResultPresenter> {

    private final int REQUEST_CODE_UPTR = 1013;
    static UpdateTaskResultActivity sActivity;
    private UpdateTaskResultActivity mActivity;
    @BindView(R.id.tv_save_and_edit)
    TextView tv_save_and_edit;
    @BindView(R.id.tv_save)
    TextView tvfSave;
    @BindView(R.id.rb_finish)
    RadioButton rbFinish;
    @BindView(R.id.rb_unfinish)
    RadioButton rbUnfinish;
    //    @BindView(R.id.rb_cancel)
//    RadioButton rbCancel;
    @BindView(R.id.rg)
    RadioGroup rg;
    //    @BindView(R.id.etfInputSj)
//    EditText etfInputSj;
    @BindView(R.id.llfMail)
    LinearLayout llfMail;//邮寄信息
    @BindView(R.id.et_maildesc)
    EditText etfMaildesc;
    @BindView(R.id.tvfReturnVisitDescT)
    TextView tvfReturnVisitDescT;

    @BindView(R.id.et_desc)
    EditText etfDesc;
    @BindView(R.id.util_view)
    Utiliew utilView;
    @BindView(R.id.root)
    RelativeLayout root;

    //回访
    @BindView(R.id.erVisitView)
    EvaluateReturnVisitView erVisitView;
    @BindView(R.id.rlfTotalScores)
    RelativeLayout rlfTotalScores;
    @BindView(R.id.tvfTotalPoints)
    TextView tvfTotalPoints;
    private List<ReturnVisitBean> rvb_List;//回访数据

    private OtherTaskMainBean taskBean;
    static int List_Index;
    private boolean isAddAttach = false;
    private int viewType = 0;//从哪个界面

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_update_task_result;
    }

    /**
     * 更新后是否跳转到用户画像页面
     */
    private int buttonType = 0;

    @Override
    protected void initViews() {
        sActivity = UpdateTaskResultActivity.this;
        mActivity = UpdateTaskResultActivity.this;
        Bundle bundle = getIntent().getExtras();
        viewType = bundle.getInt("VIEWTYPE", 0);
        List_Index = bundle.getInt("POSITION", -1);
        taskBean = (OtherTaskMainBean) bundle.getSerializable(Tokens.OtherTask.OT_ITEMSKIPDETAIL_ITEM);
        if (taskBean != null) {
            setViewByDetailInfo(taskBean);
            requestPermissions();
            initLocation();
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (rbFinish.getId() == i) {
                        result = "1";
                        if (rbFinish.getText().toString().equals("已邮寄")) {
                            llfMail.setVisibility(View.VISIBLE);
                            erVisitView.setVisibility(View.GONE);
                            rlfTotalScores.setVisibility(View.GONE);
                        }else {
                            llfMail.setVisibility(View.GONE);
                            if(taskBean.getTaskType().equals("5")) {//回访任务
                                erVisitView.setVisibility(View.VISIBLE);
                                rlfTotalScores.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    if (rbUnfinish.getId() == i) {
                        result = "0";
                        llfMail.setVisibility(View.GONE);
                        erVisitView.setVisibility(View.GONE);
                        rlfTotalScores.setVisibility(View.GONE);
                    }
                }
            });
            utilView.setListener(new Utiliew.OnClickListner() {
                @Override
                public void onPictureClick(int maxSelNum, List<LocalMedia> lastMedias) {
                    CommonUtils.pictureSelect(mActivity, PictureConfig.MULTIPLE, maxSelNum, lastMedias);
                }

                @Override
                public boolean onVoiceClick(PopupWindowFactory popupWindow) {
                    popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
                    return isVoiceGrant;
                }

                @Override
                public void onLocationClick() {
                    requestLocationPermissions();
                    isAddAttach = true;
                }

                @Override
                public void onPostAfter(List<AttachDto> attaches) {
                    save(attaches);
                }

                @Override
                public boolean onPostBefore() {
                    if(result.equals("")){
                        showTrop("请选择执行结果");
                        return false;
                    }
                    exdesc = etfDesc.getText().toString();
                    if (TextUtils.isEmpty(exdesc)/* || TextUtils.isEmpty(opppdesc)*/) {
                        showTrop("执行情况描述不能为空");
                        return false;
                    }
                    if (rbFinish.getText().toString().equals("已邮寄")) {
                        maildesc = etfMaildesc.getText().toString();
                        if (TextUtils.isEmpty(maildesc)) {
                            showTrop("邮寄信息不能为空");
                            return false;
                        }
                    }
                    mActivity.mLoadingDialog.setCancelable(false);
                    mActivity.mLoadingDialog.show();
                    return true;
                }
            });
        } else {
            finish();
        }
    }

    @Override
    protected void initPresenter() {
        presenter = new UpdateTaskResultPresenter(this);
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InFinish();
            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    //根据详情信息设置界面信息
    public void setViewByDetailInfo(OtherTaskMainBean taskBean) {
        if (taskBean == null) {
            return;
        }
        if (taskBean.getTaskType().equals("4")) {//主动追踪任务
            rlfTotalScores.setVisibility(View.GONE);
            erVisitView.setVisibility(View.GONE);
            if (taskBean.getExectype() == 2) {
                rbFinish.setText("已邮寄");
                rbUnfinish.setText("无需邮寄");
            } else {
                rbFinish.setText("已完成");
                rbUnfinish.setText("未能完成");
            }
        } else if (taskBean.getTaskType().equals("5")) {//人工回访任务
            tvfReturnVisitDescT.setText("回访情况描述");
            presenter.getQuestionnaireTemplate(taskBean.getFserviceprjid());
        }
    }

    public int getButtonType() {
        return buttonType;
    }

    private String result = "", exdesc = "", maildesc = "";//opppdesc = "",

    @OnClick({R.id.tv_save_and_edit, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_save_and_edit:
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    tv_save_and_edit.setEnabled(false);
                    buttonType = 1;
                    utilView.postFile();
                }
                break;
            case R.id.tv_save:
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    tvfSave.setEnabled(false);
                    buttonType = 2;
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                                mLoadingDialog.dismiss();
                            }
                        }
                    }, 60 * 1000);
                    utilView.postFile();
                }
                break;
        }
    }

    private void save(List<AttachDto> attaches) {
        UpdateTaskResultBean bean = new UpdateTaskResultBean();
        bean.setTaskId(taskBean.getTaskId());
        bean.setTaskType(taskBean.getTaskType());
        if (taskBean.getExectype() == 2) {//邮寄方式
            bean.setResult("1");
            bean.setMailstatus(result);
            bean.setMailDesc(maildesc);
        } else {//其它执行方式
            bean.setResult(result);
            bean.setMailstatus("");
            bean.setMailDesc("");
            if(taskBean.getTaskType().equals("5") && result.equals("1")){//只有已完成才会有回访数据
                if(rvb_List!=null && rvb_List.size()>0){
                    for (int i=0; i< rvb_List.size(); i++){
                        rvb_List.get(i).setTaskId(taskBean.getTaskId());
                        rvb_List.get(i).setItems();
                    }
                }
                bean.setRtnVisitParmList(rvb_List);
            }else {
                bean.setRtnVisitParmList(new ArrayList<>());
            }
        }
        bean.setDescription(exdesc);
        // bean.setOpportunitydesc("");
        if (attaches != null) {
            List<AttachDto> attachInfosBeans = new ArrayList<>();
            for (int i = 0; i < attaches.size(); i++) {
                AttachDto attachInfoBean = new AttachDto();
                attachInfoBean.setAttachDesc(attaches.get(i).getAttachDesc());
                attachInfoBean.setAttachId(attaches.get(i).getAttachId());
                attachInfoBean.setAttachUrl(attaches.get(i).getAttachUrl());
                attachInfoBean.setAttachType(attaches.get(i).getAttachType());
                attachInfoBean.setLocation(attaches.get(i).getLocation());
                attachInfosBeans.add(attachInfoBean);
            }
            bean.setAttachDtos(attachInfosBeans);
        }
        presenter.updateTaskResult(bean);
    }


    public static final int VOICE_REQUEST_CODE = 1001;

    public static final int LOCATION_REQUEST_CODE = 1002;

    /**
     * 开启扫描之前判断权限是否打开
     */
    private void requestPermissions() {
        //判断是否开启摄像头权限
        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)
                ) {
//            StartListener();
            isVoiceGrant = true;
            //判断是否开启语音权限
        } else {
            //请求获取摄像头权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, VOICE_REQUEST_CODE);
        }
    }

    /**
     * 开启定位权限
     */
    private void requestLocationPermissions() {
        //判断是否定位权限
        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                ) {
            startLocation();
        } else {
            //请求获取摄像头权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }

    }

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    utilView.showLocation(location.getPoiName());
                } else {
                    utilView.showLocation("定位失败");
                }
            } else {
                utilView.showLocation("定位失败");
            }
        }
    };

    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void startLocation() {
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    private boolean isVoiceGrant;

    /**
     * 请求权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == VOICE_REQUEST_CODE) {
            if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                isVoiceGrant = true;
            } else {
                isVoiceGrant = false;
            }
        }

        if (requestCode == LOCATION_REQUEST_CODE) {
            if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                // startLocation();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            if (data != null) {
                List<LocalMedia> medias = PictureSelector.obtainMultipleResult(data);
                if (medias != null && medias.size() != 0) {
                    utilView.initRecycler(medias);
                }
            }
            isAddAttach = true;
        } else if (requestCode == REQUEST_CODE_UPTR) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                if(viewType==2)FinishActivity();
                else finish();
            }
        }
    }

    /**
     * 跳转到客户画像编辑
     */
    public void goToProfile() {
        OtherTaskMainActivity.ItemPosition = List_Index;
        EditCustomerProfileActivity.start(taskBean.getCustomerId(), taskBean.getCustomerName(), mActivity);
    }

    /**
     * 跳转到评价客户
     */
    public void geToEvaluateCustomer(String fproId, String isUseEval) {
        if (isUseEval.toLowerCase().equals("true")) {
            if (fproId.equals("")) {
                showTrop("项目id为空，无法评价");
                return;
            }
            EvaluateCustomersActivity.start(mActivity, "UpdateTaskResultActivity", convertToEvalCustSaveBean(fproId), REQUEST_CODE_UPTR);
        } else {
            showTrop("任务结果已更新");
            OtherTaskMainActivity.ItemPosition = List_Index;
            FinishActivity();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        InFinish();
    }

    /**
     * 销毁定位
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    //处理人工回访界面
    public void setGetReturnVisit(boolean isSuccess, List<ReturnVisitBean> rvbeans) {
        if (isSuccess && rvbeans.size() > 0) {
            rvb_List = rvbeans;
            erVisitView.initEvaluateReturnVisitView(mActivity, true, rvbeans, new EvaluateReturnVisitView.TotalPoints() {
                @Override
                public void setTotalPoints(int scores, List<ReturnVisitBean> rvbs) {
                    tvfTotalPoints.setVisibility(View.VISIBLE);
                    tvfTotalPoints.setText(scores + "");
                    rvb_List = rvbs;
                }
            });
        }
    }

    /**
     * 转为上传所需的EvalCustSaveBean对象
     */
    public RequestTemplateDto convertToEvalCustSaveBean(String fproId) {
        if (taskBean != null) {
            RequestTemplateDto result = new RequestTemplateDto();
            result.setId("");
            result.setFserviceprjid(fproId);
            result.setTaskId(taskBean.getTaskId());
            result.setTaskType(taskBean.getTaskType());
            return result;
        } else return null;
    }

    NormalDialog quitDialog;

    private void InFinish() {
        if ((etfDesc != null && !etfDesc.getText().toString().trim().equals(""))
                || (etfMaildesc != null && !etfMaildesc.getText().toString().trim().equals(""))
                || isAddAttach) {
            quitDialog = CustomDialog.getInstance().getNormalDialog(mActivity, "温馨提示", "你填写的内容尚未保存，确认要离开吗?",
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick(View view) {
                            quitDialog.dismiss();
                            OtherTaskMainActivity.ItemPosition = List_Index;
                            finish();
                        }
                    });
        } else {
            OtherTaskMainActivity.ItemPosition = List_Index;
            finish();
        }
    }
    public void showTrop(String msg){
        MyToast.showToast(mActivity, msg);
        tv_save_and_edit.setEnabled(true);
        tvfSave.setEnabled(true);
    }
    public static void FinishActivity(){
        if(sActivity!=null && ActivityUtils.isRunning(sActivity)){
            OtherTaskInfoFragment.isUpdatedResult = true;
            OtherTaskMainActivity.ItemPosition = List_Index;
            sActivity.finish();
        }
    }
}
