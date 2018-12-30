package com.soonfor.measuremanager.home.liangchi.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jiamm.bluetooth.MeasureDevice;
import com.lzy.ninegrid.ImageInfo;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.home.liangchi.adapter.LiangChiLookDetailAdpter;
import com.soonfor.measuremanager.home.liangchi.model.bean.ApartmentLayoutBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiImmeaditelyBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiLookDetailBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.measureHead;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.mjsdk.MJReqBean;
import com.soonfor.measuremanager.mjsdk.SdkCallbackCode;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.PreferenceUtil;
import com.soonfor.measuremanager.tools.TimeUtils;
import com.soonfor.measuremanager.view.RoundJiaoImageView;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;
import com.soonfor.measuremanager.view.previewImage.ImageVAty;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jesse.nativelogger.NLogger;
import cn.jiamm.lib.MJSdk;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/8 14:44
 * 邮箱：suibozhu@139.com
 * 查看量尺 --> 量尺详情
 */
public class LiangChiLookDetailActivity extends BaseActivity implements AsyncUtils.AsyncCallback {

    Context mContext;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvfsubmit)
    TextView tvfsubmit;
    @BindView(R.id.imgpath)
    ImageView imgpath;
    @BindView(R.id.tvfcustom)
    TextView tvfcustom;
    @BindView(R.id.tvfTaskType)
    TextView tvfTaskType;
    @BindView(R.id.tvfadress)
    TextView tvfadress;
    @BindView(R.id.tvfloulevel)
    TextView tvfloulevel;
    @BindView(R.id.tvfmodifytime)
    TextView tvfmodifytime;
    @BindView(R.id.tvfselectModel)
    TextView tvfselectModel;
    @BindView(R.id.glControl)
    RecyclerView glControl;
    GridLayoutManager manager;
    LiangChiLookDetailAdpter adpter;
    List<LiangChiLookDetailBean> beans;
    LiangChiImmeaditelyBean upBean;
    @BindView(R.id.txthead)
    TextView txthead;
    String taskTypeStr = "";  //Measure    Remeasure
    List<ImageInfo> datas;
    List<measureHead> measureHeads;
    boolean mobanIsReady = false;
    boolean celiangIsReady = false;
    boolean isNew = false;
    boolean isFuChi = false;
    boolean isMeasureFinish = false;
    boolean isNeedToFinish = false;
    boolean isSelectedModel = false;
    String tasktypeStr = "";
    ArrayList<String> picList = new ArrayList<>();
    private String completeHouseStr = "-1";
    private LoadHouseListener mLoadHouseListener;
//    boolean isNeedToReflash = false;
    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_liang_chi_look_detail;
    }

    @Override
    protected void initViews() {
        mContext = LiangChiLookDetailActivity.this;
        manager = new GridLayoutManager(mContext, 3);
        glControl.setLayoutManager(manager);

        upBean = getIntent().getParcelableExtra("LiangChiImmeaditelyBean");
        if (upBean != null) {
            ImageUtil.loadImage(LiangChiLookDetailActivity.this, upBean.getFimgpath(), imgpath);
            if (picList != null) {
                picList.clear();
            } else {
                picList = new ArrayList<>();
            }
            picList.add(upBean.getFimgpath());
            String loupan = CommonUtils.formatStr(upBean.getCustomBulid()).equals("")?"":(CommonUtils.formatStr(upBean.getCustomBulid())+ "-");
            tvfcustom.setText(loupan + CommonUtils.formatStr(upBean.getFcname()));
            tvfadress.setText(CommonUtils.formatStr(upBean.getCustomAddress()));
            tvfloulevel.setText(CommonUtils.formatStr(upBean.getFmeafloor()));
            tvfmodifytime.setText(DateTool.getTimeTimestamp(upBean.getFcdate(), "yyyy-MM-dd HH:mm:ss") + "");
            taskTypeStr = upBean.getTaskTypeValue();

            int type = CommonUtils.backLcORFc(taskTypeStr);
            switch (type) {
                case 1:
                    txthead.setText("量尺详情");
                    isFuChi = false;
                    break;
                case 2:
                    txthead.setText("复尺详情");
                    isFuChi = true;
                    //复尺的时候要拿回原来测量的合同号
                    Request.Loft.getRemeasureTasknoByOrderNo(LiangChiLookDetailActivity.this, upBean.getOrderNo(), LiangChiLookDetailActivity.this, 100);
                    break;
            }

            isNew = isFuChi ? false : upBean.getNews();
            tasktypeStr = CommonUtils.formatStr(getStatuValue(upBean.getFstatus()));
            tvfTaskType.setText(tasktypeStr);
            if (tasktypeStr.contains("尺完成")) {
                isNew = false;
            }
        }

        datas = new ArrayList<>();
        measureHeads = new ArrayList<>();
        adpter = new LiangChiLookDetailAdpter(mContext, LiangChiLookDetailActivity.this, initIcons(isNew, upBean.getFobsplanid(), upBean.getTaskNo()), taskTypeStr, upBean.getOrderNo(), CommonUtils.formatStr(upBean.getFcname()));
        adpter.setHouseName(CommonUtils.formatStr(upBean.getFmeafloor()));
        glControl.setAdapter(adpter);

        //mLoadingDialog.show();
        // RepRequest.getTaskCompleteInfo(mContext, this, upBean.getTaskNo(), upBean.getTaskTypeValue(), upBean.getOrderNo());

        //回调消息设置
        mLoadHouseListener = new LoadHouseListener();
        MJSdk.getInstance().regMessageListener(mLoadHouseListener);

        //isNeedToReflash = true;
    }

    /**
     * 0 不能 1 能
     * **/
    private void isEnableMoban(int selected){
        switch (selected){
            case 0:
                tvfselectModel.setBackgroundResource(R.drawable.btn_gray);
                tvfselectModel.setTextColor(Color.parseColor("#cccccc"));
                tvfselectModel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyToast.showToast(LiangChiLookDetailActivity.this, "合同号已新建,不能选择模版");
                    }
                });
                break;
            case 1:
                tvfselectModel.setBackgroundResource(R.drawable.btn_blue);
                tvfselectModel.setTextColor(Color.parseColor("#1198fb"));
                tvfselectModel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle b = new Bundle();
                        startNewAct(LiangChiLookDetailActivity.this, ApartmentLayoutActivity.class, b, 7777);
                    }
                });
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (isNeedToReflash) {
//            isNeedToReflash = false;
            String hetonghao = "";
            if (isFuChi) {
                celiangIsReady = true;
                if (beans != null) {
                    for (int i = 0; i < beans.size(); i++) {
                        beans.get(i).setNewhouse(false);
                    }
                }
                adpter.notifyDataSetChanged(beans);
            } else {
                hetonghao = upBean.getTaskNo() + "-" + upBean.getFobsplanid();
                if (Hawk.get(hetonghao)) {
                    celiangIsReady = true;
                    isEnableMoban(0);
                    if (beans != null) {
                        for (int i = 0; i < beans.size(); i++) {
                            beans.get(i).setNewhouse(false);
                        }
                    }
                    adpter.notifyDataSetChanged(beans);
                } else {
                    celiangIsReady = false;
                    isEnableMoban(1);
                    if (beans != null) {
                        for (int i = 0; i < beans.size(); i++) {
                            beans.get(i).setNewhouse(true);

                            if (tasktypeStr.contains("量尺中") || tasktypeStr.contains("尺完成")) {
                                beans.get(i).setNewhouse(false);
                            }

                        }
                    }
                    adpter.notifyDataSetChanged(beans);
                }
            }

            /**
             * 修改人：DC-ZhuSuiBo on 2018/4/27 0027 9:47
             * 邮箱：suibozhu@139.com
             * 修改目的：强制纠正
             */
            if (tasktypeStr.contains("量尺中") || tasktypeStr.contains("尺完成")) {
                isEnableMoban(0);
            } else {
                if (beans.get(0).isNewhouse()) {
                    isEnableMoban(1);
                } else {
                    isEnableMoban(0);
                }
            }
        /**
         * dingyg修改 再次onCreate此界面，只会得到什么都未改变之前的数据，
         * 这也是选择的模块后户型图不刷新的原因
         */
//        }else{
//            Intent intent = getIntent();
//            finish();
//            startActivity(intent);
//        }
    }

    private List<LiangChiLookDetailBean> initIcons(boolean isNew, String contractNo, String taskNo) {
        beans = new ArrayList<LiangChiLookDetailBean>();
        String tmpStr = "";
        if (isFuChi) {
            tmpStr = "开始复尺";
        } else {
            tmpStr = "开始测量";
        }

        beans.add(new LiangChiLookDetailBean(tmpStr, R.mipmap.icn_liangchi01, isMeasureFinish ? false : true, isNew, isFuChi, taskNo + "-" + contractNo, taskNo, "", upBean.getCustomBulid(), PreferenceUtil.getCurrentUserBean().getSalesCode(), "", "", "", upBean.getFimgpath()));
        beans.add(new LiangChiLookDetailBean("查看平面图", R.mipmap.icn_liangchi02, true, isNew, isFuChi, taskNo + "-" + contractNo, taskNo, "", upBean.getCustomBulid(), PreferenceUtil.getCurrentUserBean().getSalesCode(), "", "", "", upBean.getFimgpath()));
        //beans.add(new LiangChiLookDetailBean("查看立面图", R.mipmap.icn_liangchi03, isNew, contractNo));
        beans.add(new LiangChiLookDetailBean("查看3D模型", R.mipmap.icn_liangchi04, true, isNew, isFuChi, taskNo + "-" + contractNo, taskNo, "", upBean.getCustomBulid(), PreferenceUtil.getCurrentUserBean().getSalesCode(), "", "", "", upBean.getFimgpath()));
        //beans.add(new LiangChiLookDetailBean("查看平面布局", R.mipmap.icn_liangchi05, isNew, contractNo));
        beans.add(new LiangChiLookDetailBean("查看测量清单", R.mipmap.icn_liangchi06, true, isNew, isFuChi, taskNo + "-" + contractNo, taskNo, "", upBean.getCustomBulid(), PreferenceUtil.getCurrentUserBean().getSalesCode(), "", "", "", upBean.getFimgpath()));
        beans.add(new LiangChiLookDetailBean("设计方案", R.mipmap.icn_liangchi07, true, isNew, isFuChi, taskNo + "-" + contractNo, taskNo, "", upBean.getCustomBulid(), PreferenceUtil.getCurrentUserBean().getSalesCode(), "", "", "", upBean.getFimgpath()));
        //beans.add(new LiangChiLookDetailBean("现场照片", R.mipmap.icn_liangchi08, true, isNew, isFuChi, taskNo + "-" + contractNo, taskNo, "", upBean.getCustomBulid(), PreferenceUtil.getCurrentUserBean().getSalesCode(), "", "", ""));
        beans.add(new LiangChiLookDetailBean("分享", R.mipmap.icn_liangchi09, true, isNew, isFuChi, taskNo + "-" + contractNo, taskNo, "", upBean.getCustomBulid(), PreferenceUtil.getCurrentUserBean().getSalesCode(), "", "", "", upBean.getFimgpath()));
        return beans;
    }

    Dialog dialog = null;

    @OnClick({R.id.imgBack, R.id.tvfsubmit})//R.id.tvfselectModel
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                inFinish(false);
                break;
            case R.id.tvfsubmit:
                if (taskTypeStr.equals("measure")) {
                    if (celiangIsReady) {//mobanIsReady
                        if (mobanIsReady) {
                            submit(false, "是否测量已经完成?");
                        } else {
                            dialog = CustomDialog.ShowSubmitSaveModel(LiangChiLookDetailActivity.this, "是否将" + tvfcustom.getText() + tvfloulevel.getText() + "设为模版?", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    submit(true, "是否测量已经完成?");
                                    dialog.dismiss();
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    submit(false, "是否测量已经完成?");
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    } else {
                        showTip("请先进行测量");
                    }
                } else if (taskTypeStr.equals("remeasure")) {//复尺
                    if (celiangIsReady) {
                        //复尺提交前得先验证是否量尺完成接口
                        //if (getMeasurementStatus()) {
                        submit(false, "是否复尺已经完成?");
                        //} else {
                        // showTip("复尺状态未完成,请在复尺完成后提交");
                        //}
                    } else {
                        showTip("请先进行测量");
                    }
                }

                break;
           /* case R.id.tvfselectModel:
                Bundle b = new Bundle();
                startNewAct(LiangChiLookDetailActivity.this, ApartmentLayoutActivity.class, b, 7777);
                //startNewAct(ApartmentLayoutActivity.class, b, 7777);
                break;*/
        }
    }

    Dialog finalConfirmDialog = null;

    private void submit(boolean isSetMoban, String msg) {
        finalConfirmDialog = CustomDialog.getDoubleNormalDialog(mContext, "温馨提示", msg, new OnBtnClickL() {
            @Override
            public void onBtnClick(View view) {
                //未完成
                completeHouseStr = "1";
                completeHouse();
                finalConfirmDialog.dismiss();

            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick(View view) {
                //完成
                completeHouseStr = "2";
                completeHouse();
                finalConfirmDialog.dismiss();
            }
        });
        finalConfirmDialog.show();

        if (isSetMoban) {
            Request.setStandardTemplate(LiangChiLookDetailActivity.this, LiangChiLookDetailActivity.this, upBean.getFobsplanid());
        } else {

        }
        //if (mobanIsReady) {
        //} else {

        // }
    }

    private boolean getMeasurementStatus() {
        try {
            //获取量房完成状态
            MJReqBean.SdkGetMeasureMentStatus req = new MJReqBean.SdkGetMeasureMentStatus();
            req._id.contractNo = upBean.getTaskNo() + "-" + upBean.getFobsplanid();
            final String renderInfo = req.getString();
            String ret = MJSdk.getInstance().Execute(renderInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void completeHouse() {
        try {
            //完成量房
            MJReqBean.SdkCompleteHouse req = new MJReqBean.SdkCompleteHouse();
            req.contractNo = upBean.getTaskNo() + "-" + upBean.getFobsplanid();
            final String renderInfo = req.getString();
            String ret = MJSdk.getInstance().Execute(renderInfo);
            NLogger.w("completeHouse ret:" + ret);
            JSONObject jobj = new JSONObject(ret);
            NLogger.w("demo", "complte ret:" + jobj);
            if (jobj.getString("errorCode").equals("0")) {
                //MyToast.showToast(mContext, "已成功执行完成量房接口");
                setTaskStatus(completeHouseStr, true);
            }else if(jobj.getString("errorCode").equals("9")){
                if(taskTypeStr.equals("remeasure")) {
                    MyToast.showToast(mContext, "请先复测");
                }else if(taskTypeStr.equals("measure")){
                    MyToast.showToast(mContext, "请先量尺");
                }
            } else {
                //MyToast.showToast(mContext, "提交完成量房信息失败");

                /**打印错误消息**/
                Bundle b = new Bundle();
                //JSONObject err = new JSONObject(jobj.getString("errorMessage"));
                b.putString("notify", "提交完成量房信息失败，具体原因：" + jobj.getString("errorMessage"));//err.getString("data"));
                Message msg = mHandler.obtainMessage(SHOW_MESSAGE);
                msg.setData(b);
                mHandler.sendMessage(msg);
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final int SHOW_MESSAGE = 11;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            int id = msg.what;
            switch (id) {
                case SHOW_MESSAGE: {
                    Bundle b = msg.getData();
                    if (b != null) {
                        String txt = b.getString("notify");
                        if (txt != null && !txt.isEmpty())
                            MyToast.showToast(LiangChiLookDetailActivity.this, txt + "");
                    }
                }
                break;
            }
        }
    };

    private void setTaskStatus(String status, boolean isNeedFin) {
        //mLoadingDialog.show();
        isNeedToFinish = isNeedFin;
        Request.setTaskStatus(mContext, this, upBean.getTaskNo(), upBean.getFobsplanid(), status);
    }

    private String getStatuValue(String status) {
        if (status.equals("0")) {//待量尺
            celiangIsReady = false;
            isMeasureFinish = false;
            tvfsubmit.setVisibility(VISIBLE);
            if (isFuChi) {
                tvfselectModel.setVisibility(INVISIBLE);
            } else {
                tvfselectModel.setVisibility(VISIBLE);
            }
            return isFuChi ? "待复尺" : "待量尺";
        } else if (status.equals("1")) {//量尺中
            celiangIsReady = true;
            isMeasureFinish = false;
            tvfsubmit.setVisibility(VISIBLE);
            if (isFuChi) {
                tvfselectModel.setVisibility(INVISIBLE);
            } else {
                tvfselectModel.setVisibility(VISIBLE);
            }
            return isFuChi ? "复尺中" : "量尺中";
        } else if (status.equals("2")) {//量尺完成
            celiangIsReady = true;
            isMeasureFinish = true;
            tvfsubmit.setVisibility(INVISIBLE);
            if (isFuChi) {
                tvfselectModel.setVisibility(INVISIBLE);
            } else {
                tvfselectModel.setVisibility(INVISIBLE);
            }
            return isFuChi ? "复尺完成" : "量尺完成";
        }
        return "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isSelectedModel = false;
        if (resultCode == 7777) {
            ApartmentLayoutBean aBean = (ApartmentLayoutBean) data.getParcelableExtra("ApartmentLayoutBean");
            if (picList != null) {
                picList.clear();
            } else {
                picList = new ArrayList<>();
            }
            picList.add(aBean.getFpics());
            ImageUtil.loadImage(LiangChiLookDetailActivity.this, aBean.getFpics(), imgpath);
            tvfmodifytime.setText(TimeUtils.getCurrentTimeByFormat("yyyy-MM-dd HH:mm:ss"));
            mobanIsReady = true;
            //选了模版更新合同号
            if (beans != null) {
                for (int i = 0; i < beans.size(); i++) {
                    beans.get(i).setTemplateContractNo(aBean.getFobsplanid());
                }
            }
            adpter.notifyDataSetChanged(beans);
            isSelectedModel = true;
        }else if(resultCode == 7778){
            //nothing to do ...
        } else if (resultCode == SdkCallbackCode.CREATE_HOUSE) {

        } else if (resultCode == SdkCallbackCode.OPEN_HOUSE) {

        } else if (resultCode == SdkCallbackCode.PREVIEW_SURVEY) {

        } else if (resultCode == SdkCallbackCode.PREVIEW_3D) {

        } else if (resultCode == 0) { // 美家sdk按了home
           /* if (beans != null) {
                for (int i = 0; i < beans.size(); i++) {
                    beans.get(i).setNewhouse(false);
                }
            }
            adpter.notifyDataSetChanged(beans);
            celiangIsReady = true;*/
           Hawk.put(upBean.getTaskNo() + "-" + upBean.getFobsplanid(),true);
        }
    }


    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @OnClick({R.id.imgpath})
    void priveHouseType(View v) {
        switch (v.getId()) {
            case R.id.imgpath:
                Intent intent = new Intent();
                intent.putExtra("ISBASEHOUSETYPE", false);
                intent.putExtra("position", 0);
                intent.putStringArrayListExtra("images", picList);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(mContext, ImageVAty.class);
                mContext.startActivity(intent);
                break;
        }
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        mLoadingDialog.dismiss();
        Gson gson = new Gson();
        switch (requestCode) {
            case Request.SET_STANDARD_TEMPLATE:
                JsonUtils.analysisJsonHead( object.toString(), new JsonUtils.OperateData() {
                   @Override
                    public void doingInFail(String msg) {
                        showNoDataHint(msg);
                    }
                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            NLogger.w("SET_STANDARD_TEMPLATE", "设置模版接口返回:" + data);
                        } catch (Exception e) {
                            showTip(e.toString());
                        }
                    }
                });
                break;
            /*case RepRequest.GET_TASK_COMPLETE_INFO:
                RepJsonUtils.analysisJsonHead(LiangChiLookDetailActivity.this, object.toString(), new RepJsonUtils.OperateData() {                    @Override                    public void doingInFail(String msg) {                        showNoDataHint(msg);                    }
                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            mainbean result1 = gson.fromJson(data, new TypeToken<mainbean>() { }.getType());
                            try {
                                mainbean mb = result1;
                                if (mb != null) {
                                    //户型图mb.getMeasureInfo().getUnitsPicture()

                                    //实景图
                                    datas = new ArrayList<>();
                                    for (int i = 0; i < mb.getMeasureInfo().getLivePictures().size(); i++) {
                                        ImageInfo info = new ImageInfo();
                                        info.setThumbnailUrl(mb.getMeasureInfo().getLivePictures().get(i));
                                        info.setBigImageUrl(mb.getMeasureInfo().getLivePictures().get(i));
                                        datas.add(info);
                                    }

                                    //测量信息
                                    measureHeads = RepJsonUtils.getMeasureHead(mb);
                                    if (measureHeads.size() > 0) {

                                    }

                                    adpter.setDatas(datas);
                                    adpter.setMeasureHeads(measureHeads);
                                    adpter.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            showTip(e.toString());
                        }
                    }
                });
                break;*/
            case Request.SET_TASK_STATUS:
                JsonUtils.analysisJsonHead( object.toString(), new JsonUtils.OperateData() {                    @Override                    public void doingInFail(String msg) {                        showNoDataHint(msg);                    }
                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            completeHouseStr = "-1";
                            //showTip(data);
                            //finish();
                            if (isNeedToFinish) {
                                inFinish(true);
                            }
                        } catch (Exception e) {
                            showTip(e.toString());
                        }
                    }
                });
                break;
            case 100:
                HeadBean headBean = JsonUtils.getHeadBean(object.toString());
                if (headBean != null && (headBean.getMsgcode() == 0 || headBean.isSuccess())) {
                    String newTaskNo = "";
                    try {
                        JSONObject jo = new JSONObject(headBean.getData());
                        if (jo != null) {
                            //判断量尺是否为空
                            String measureId = CommonUtils.formatStr(jo.getString("measureId"));
                            String remeasureId = CommonUtils.formatStr(jo.getString("remeasureId"));

                            //复尺时更新初测合同号
                            if (beans != null) {
                                for (int i = 0; i < beans.size(); i++) {
                                    beans.get(i).setContractNo(measureId + "-" + upBean.getFobsplanid());
                                    beans.get(i).setNew_contractNo(remeasureId + "-" + upBean.getFobsplanid());
                                    //beans.get(i).setNewhouse(false);
                                }
                            }
                            adpter.notifyDataSetChanged(beans);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        closeLoadingDialog();
    }

    /**
     * 更新视图
     */
    public void showTip(String msg) {
        MyToast.showToast(mContext, msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            inFinish(false);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void inFinish(boolean isNeedToReflash) {
        if(isSelectedModel){
            finalConfirmDialog = CustomDialog.getNormalDialog(mContext, "温馨提示",
                    "您选择的户型模板尚未提交，确定要退出吗？", new OnBtnClickL() {
                @Override
                public void onBtnClick(View view) {
                    Intent intent = new Intent(mContext, LiangChiImmediatelyActivity.class);
                    intent.putExtra("fobsplanid", upBean.getFobsplanid());
                    intent.putExtra("isNeedToReflash", isNeedToReflash);
                    setResult(56000, intent);

                    MJSdk.getInstance().unregMessageListener(mLoadHouseListener);
                    MeasureDevice.getInstance().setActivity(null);

                    finish();
                }
            });
            finalConfirmDialog.show();
            return;
        }
        Intent intent = new Intent(mContext, LiangChiImmediatelyActivity.class);
        intent.putExtra("fobsplanid", upBean.getFobsplanid());
        intent.putExtra("isNeedToReflash", isNeedToReflash);
        setResult(56000, intent);

        MJSdk.getInstance().unregMessageListener(mLoadHouseListener);
        MeasureDevice.getInstance().setActivity(null);

        finish();
    }

    //继承回调类，实现回调函数
    private class LoadHouseListener implements MJSdk.MessageListener {

        @Override
        public void onSdkEvent(String arg0) {
            //SdkMsgInfo ret = GSONUtil.gson.fromJson(arg0, new TypeToken<SdkMsgInfo>() {}.getType());
            JSONObject jobj;
            try {
                jobj = new JSONObject(arg0);
                //System.out.println("meijia sdk 回调" + jobj.toString());
                String cmd = jobj.optString("cmd");
                if (cmd.equals("event_OnInitEnd")) {

                } else if (cmd.equals("back_home")) {

                } else if (cmd.equals("complete_house")) {

                } else if (cmd.equals("house_empty")) {

                } else if (cmd.equals("measurement_status")) {
                    /*返回状态说明，errorCode为-1时，errorMessage返回错误原因，否则返回“OK”。*/
                    if (jobj.getString("errorCode").equals("0")) {
                        //return true;
                    } else {
                        //return false;
                    }
                    NLogger.w("demo", "getMeasurementStatus ret:" + jobj);
                } else if (cmd.equals("complete_house_measure")) {
                    /*if (jobj.getString("errorCode").equals("0")) {
                        //MyToast.showToast(mContext, "已成功执行完成量房接口");
                    } else {
                        MyToast.showToast(mContext, "执行完成量房接口失败");
                    }
                    NLogger.w("demo", "complte ret:" + jobj);*/
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}
