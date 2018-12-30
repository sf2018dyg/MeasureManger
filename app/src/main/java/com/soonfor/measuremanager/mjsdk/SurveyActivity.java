package com.soonfor.measuremanager.mjsdk;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.android.tu.loadingdialog.LoadingDailog;
import com.jiamm.bluetooth.MeasureDevice;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiLookDetailBean;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.repository.tools.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jesse.nativelogger.NLogger;
import cn.jiamm.lib.JMMView;
import cn.jiamm.lib.JMMViewBaseActivity;
import cn.jiamm.lib.MJSdk;

public class SurveyActivity extends JMMViewBaseActivity {

    public LoadingDailog mLoadingDialog;
    protected String actionName = "加载中..";
    private final int RESET_SURFACEVIEW_BACKGROUND = 10;
    private final int SHOW_MESSAGE = 11;
    private final int ERRORMSG = 999;

    private SurveyActivity pThis;
    private LoadHouseListener mLoadHouseListener;
    private boolean bHouseClosed = false;
    private int optionType;
    LiangChiLookDetailBean lcbean;
    private String orderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        /**
         * 修改人：DC-ZhuSuiBo on 2018/7/3 0003 10:48
         * 邮箱：suibozhu@139.com
         * 修改目的：从主界面搬过来
         */
        if(!MJSdk.getInstance().IsInited()) {
            //第一步：初始化环境
            MJSdk.getInstance().InitEnv(this);

            //第二步：初始化配置
            MJReqBean.SdkAppConfig config = new MJReqBean.SdkAppConfig();
            config.packageName = getPackageName();
            config.apiUrl = "http://api.jiamm.cn/test";
            //String storagePath = Environment.getExternalStorageDirectory().getPath() + "/jmm/";
            String storagePath = getFilesDir().getPath() + "/test/";
            config.storagePath = storagePath;
            //先获取时间戳，再调用generateSign生成签名信息
            long tm = System.currentTimeMillis();
            config.timeStamp = String.valueOf(tm);
            config.generateSign();
            MJSdk.getInstance().Execute(config.getString());
           // NLogger.w("demo", "config ret:" + sret);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_view);

        if (savedInstanceState != null) {
            if(savedInstanceState.containsKey("orderNo"))
                orderNo = savedInstanceState.getString("orderNo");
            if(savedInstanceState.containsKey("LiangChiLookDetailBean"))
                lcbean = savedInstanceState.getParcelable("LiangChiLookDetailBean");
        }

        pThis = this;
        try {
            //绘图视图初始化
            m_JMMView = (JMMView) findViewById(R.id.m_JiaMMView);
            m_JMMView.setActivity(this);
        }catch (Exception e){
            NLogger.e("美家绘图视图初始化失败:\n","引起原因:" + e.getCause() + "\n异常信息:" + e.getMessage());
            MyToast.showFailToast(SurveyActivity.this, "初始化美家绘图视图失败, 请查看日志");
            finish();
        }

        optionType = getIntent().getIntExtra("optionType", 0);
        lcbean = (LiangChiLookDetailBean) getIntent().getParcelableExtra("LiangChiLookDetailBean");
        orderNo = getIntent().getStringExtra("orderNo");

        NLogger.w("getOrderNo", orderNo + "--订单号");
        NLogger.w("getContractNo", lcbean.getContractNo() + "--合同号");
        NLogger.w("getNew_contractNo", lcbean.getNew_contractNo() + "--复测合同号");
        NLogger.w("getTemplateContractNo", lcbean.getTemplateContractNo() + "--模版合同号");
        NLogger.w("isNewhouse", lcbean.isNewhouse() + "--是否新建合同号");
        NLogger.w("isFuChiHouse", lcbean.isFuChiHouse() + "--是否复尺合同号");

//        //显示打印日志
//        String strlog = "合同号日志信息:\n";
//        strlog += orderNo + "--订单号\n";
//        strlog += lcbean.getContractNo() + "--合同号\n";
//        strlog += lcbean.getNew_contractNo() + "--复测合同号\n";
//        strlog += lcbean.getTemplateContractNo() + "--模版合同号\n";
//        strlog += lcbean.isNewhouse() + "--是否新建合同号\n";
//        strlog += lcbean.isFuChiHouse() + "--是否复尺合同号\n";
//        MyToast.showToast(SurveyActivity.this,strlog);

        //回调消息设置
        mLoadHouseListener = new LoadHouseListener();
        MJSdk.getInstance().regMessageListener(mLoadHouseListener);

        if (mLoadingDialog == null) {
            LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                    .setMessage(actionName)
                    .setCancelable(true)
                    .setCancelOutside(true);
            mLoadingDialog = loadBuilder.create();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("orderNo", orderNo);
        outState.putParcelable("LiangChiLookDetailBean", lcbean);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(m_JMMView!=null) {
            m_JMMView.onResume();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(m_JMMView!=null) {
            m_JMMView.onWindowFocusChanged(hasFocus);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(m_JMMView!=null) {
            m_JMMView.onPause();
        }
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        if (!bHouseClosed) {
            JSONObject jreq = new JSONObject();
            try {
                jreq.put("ns", "house");
                jreq.put("cmd", "close_house");

                final String sreq = jreq.toString();
                m_JMMView.runOnGLThread(new Runnable() {
                    @Override
                    public void run() {
                        String sret = MJSdk.getInstance().Execute(sreq);
                        //NLogger.d("demo", "ret:" + sret);
                        mHandler.sendEmptyMessage(RESET_SURFACEVIEW_BACKGROUND);
                    }
                });
                return;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        super.finish();
        MJSdk.getInstance().unregMessageListener(mLoadHouseListener);
        MeasureDevice.getInstance().setActivity(null);
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
                    switch (optionType) {
                        case 0:
                            if (lcbean.isNewhouse()) {
                                createNewHouse();
                            } else {
                                if (lcbean.isFuChiHouse()) {
                                    remeasureHouse();
                                } else {
                                    openHouse();
                                }
                            }
                            break;
                        case 1:
                            if (lcbean.isFuChiHouse()) {
                                openPreviewSurvey(lcbean.getNew_contractNo());
                            } else {
                                openPreviewSurvey(lcbean.getContractNo());
                            }
                            break;
                        case 2:
                            if (lcbean.isFuChiHouse()) {
                                openPreview3D(lcbean.getNew_contractNo());
                            } else {
                                openPreview3D(lcbean.getContractNo());
                            }
                            break;
                    }
                } else if (cmd.equals("back_home")) {
                    bHouseClosed = true;
                    finish();
                } else if (cmd.equals("complete_house")) {
                    bHouseClosed = true;
                    JSONObject jparams = jobj.optJSONObject("params");
                    int beddingRooms = jparams.optInt("beddingRooms");
                    int livingRooms = jparams.optInt("livingRooms");
                    int washingRooms = jparams.optInt("washingRooms");

                    finish();
                } else if (cmd.equals("house_empty")) {
                    Bundle b = new Bundle();
                    b.putString("notify", "房屋数据未加载完成");
                    Message msg = mHandler.obtainMessage(SHOW_MESSAGE);
                    msg.setData(b);
                    mHandler.sendMessage(msg);
                } else if (cmd.equals("create_house")) {
                    dialogHandler.sendEmptyMessage(1);
                    /*返回状态说明，0代表新建正常，1代表模板量房合同号不存在，2代表新建量房合同号已存在*/
                    if (jobj.getString("errorCode").equals("0")) {
                        //保存状态到本地 req.data.contractNo
                        Hawk.put(jobj.getString("errorMessage"), true);
                    } else if (jobj.getString("errorCode").equals("2")) {
                        openHouse();
                        Hawk.put(jobj.getString("errorMessage"), true);
                    } else {
                        openHouse();
                        Hawk.put(jobj.getString("errorMessage"), false);
                    }
                    /*if(ret.contains("该合同号已经存在，不能用于新建")){
                        openHouse();
                    }*/
                   // NLogger.d("demo", "create ret:" + jobj);
                    mHandler.sendEmptyMessage(RESET_SURFACEVIEW_BACKGROUND);
                } else if (cmd.equals("remeasure_house")) {
                    dialogHandler.sendEmptyMessage(1);
                    /*返回状态说明，0代表复测正常，3代表初测量房合同号不存在，4代表复测量房合同号已存在*/
                    if (jobj.getString("errorCode").equals("3") || jobj.getString("errorCode").equals("4")) {
                        openRemeasureHouse();
                    }
                    //NLogger.d("demo", "remeasure ret:" + jobj);
                    mHandler.sendEmptyMessage(RESET_SURFACEVIEW_BACKGROUND);
                } else if (cmd.equals("open_house")) {
                    dialogHandler.sendEmptyMessage(1);
                    /*返回状态说明，0代表打开正常，5代表打开量房合同号不存在*/
                    if (jobj.getString("errorCode").equals("5")) {
                        Message msg = new Message();
                        msg.what = ERRORMSG;
                        msg.obj = jobj.getString("errorMessage");
                        mHandler.sendMessage(msg);
                        //finish();

                        /**打印错误消息*
                        Bundle b = new Bundle();
                        b.putString("notify", "openHouse ret:" + jobj);
                        msg = mHandler.obtainMessage(SHOW_MESSAGE);
                        msg.setData(b);
                        mHandler.sendMessage(msg);*/
                    }
                   // NLogger.d("demo", "openHouse ret:" + jobj);
                    mHandler.sendEmptyMessage(RESET_SURFACEVIEW_BACKGROUND);
                } else if (cmd.equals("preview_survey")) {
                    dialogHandler.sendEmptyMessage(1);
                    /*返回状态说明，0代表预览平面图正常，6代表预览量房合同号不存在*/
                    if (jobj.getString("errorCode").equals("6")) {
                        Message msg = new Message();
                        msg.what = ERRORMSG;
                        msg.obj = lcbean.isFuChiHouse()?"请先复尺":"请先量尺";//jobj.getString("errorMessage");
                        mHandler.sendMessage(msg);
                        //finish();

                        /**打印错误消息*
                        Bundle b = new Bundle();
                        b.putString("notify", "openPreviewSurvey ret:" + jobj);
                        msg = mHandler.obtainMessage(SHOW_MESSAGE);
                        msg.setData(b);
                        mHandler.sendMessage(msg);*/
                    }
                   // NLogger.d("demo", "openPreviewSurvey ret:" + jobj);
                    mHandler.sendEmptyMessage(RESET_SURFACEVIEW_BACKGROUND);
                } else if (cmd.equals("preview_3d")) {
                    dialogHandler.sendEmptyMessage(1);
                    /*返回状态说明，0代表预览3D正常，7代表预览3D合同号不存在*/
                    if (jobj.getString("errorCode").equals("7")) {
                        Message msg = new Message();
                        msg.what = ERRORMSG;
                        msg.obj = lcbean.isFuChiHouse()?"请先复尺":"请先量尺";//jobj.getString("errorMessage");
                        mHandler.sendMessage(msg);
                        //finish();

                       /* *//**打印错误消息**//*
                        Bundle b = new Bundle();
                        b.putString("notify", "openPreview3D ret:" + jobj);
                        msg = mHandler.obtainMessage(SHOW_MESSAGE);
                        msg.setData(b);
                        mHandler.sendMessage(msg);*/
                    }
                   // NLogger.d("demo", "openPreview3D ret:" + jobj);
                    mHandler.sendEmptyMessage(RESET_SURFACEVIEW_BACKGROUND);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //创建房屋
    private void createNewHouse() {
        dialogHandler.sendEmptyMessage(0);
        final MJReqBean.SdkCreateHouse req = new MJReqBean.SdkCreateHouse();
        req.data.village = lcbean.getVillage();
        req.data.buildingNo = lcbean.getBuildingNo();
        req.data.contractNo = lcbean.getContractNo();
        req.data.employeeNo = lcbean.getEmployeeNo();
        req.data.company = lcbean.getCompany();
        req.data.templateContractNo = lcbean.getTemplateContractNo();
        req.data.taskNo = lcbean.getTaskNo();

        final String renderInfo = req.getString();
        m_JMMView.runOnGLThread(new Runnable() {
            public void run() {
                try {
                    String ret = MJSdk.getInstance().Execute(renderInfo);
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }
        });
        return;
    }

    //打开房屋
    private void openHouse() {
        dialogHandler.sendEmptyMessage(0);
        final MJReqBean.SdkOpenHouse req = new MJReqBean.SdkOpenHouse();
        req._id.contractNo = lcbean.getContractNo();
        //req.id.contractNo = "137";

        final String renderInfo = req.getString();
        m_JMMView.runOnGLThread(new Runnable() {
            public void run() {
                try {
                    String ret = MJSdk.getInstance().Execute(renderInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return;
    }

    //打开复尺的房屋
    private void openRemeasureHouse() {
        dialogHandler.sendEmptyMessage(0);
        final MJReqBean.SdkOpenHouse req = new MJReqBean.SdkOpenHouse();
        req._id.contractNo = lcbean.getNew_contractNo();
        //req.id.contractNo = "137";

        final String renderInfo = req.getString();
        m_JMMView.runOnGLThread(new Runnable() {
            public void run() {
                try {
                    String ret = MJSdk.getInstance().Execute(renderInfo);
                  //  NLogger.d("demo", "openRemeasure ret:" + ret);
                    JSONObject o = new JSONObject(ret);
                    if (o.getString("errorCode").equals("-1")) {
                        Message msg = new Message();
                        msg.what = ERRORMSG;
                        msg.obj = o.getString("errorMessage");
                        mHandler.sendMessage(msg);
                        finish();
                    }
                    mHandler.sendEmptyMessage(RESET_SURFACEVIEW_BACKGROUND);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return;
    }

 /*   //完成量房
    private void completeHouse() {
        final MJReqBean.SdkCompleteHouse req = new MJReqBean.SdkCompleteHouse();
        req.contractNo = lcbean.getNew_contractNo();

        final String renderInfo = req.getString();
        m_JMMView.runOnGLThread(new Runnable() {
            public void run() {
                String ret = MJSdk.getInstance().Execute(renderInfo);
                NLogger.d("demo", "complete ret:" + ret);
                mHandler.sendEmptyMessage(RESET_SURFACEVIEW_BACKGROUND);
            }
        });
        return;
    }*/

    //复测新建量房
    private void remeasureHouse() {
        dialogHandler.sendEmptyMessage(0);
        final MJReqBean.SdkRemeasureHouse req = new MJReqBean.SdkRemeasureHouse();
        req.data.village = lcbean.getVillage();
        req.data.buildingNo = lcbean.getBuildingNo();
        req.data.copy_contractNo = lcbean.getContractNo();
        req.data.new_contractNo = lcbean.getNew_contractNo();
        req.data.employeeNo = lcbean.getEmployeeNo();
        req.data.company = lcbean.getCompany();
        req.data.taskNo = lcbean.getTaskNo();

        final String renderInfo = req.getString();
        m_JMMView.runOnGLThread(new Runnable() {
            public void run() {
                try {
                    String ret = MJSdk.getInstance().Execute(renderInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return;
    }

    //打开平面图
    private void openPreviewSurvey(String contractNo) {
        dialogHandler.sendEmptyMessage(0);
        final MJReqBean.SdkPreviewSurvey req = new MJReqBean.SdkPreviewSurvey();
        req._id.contractNo = contractNo;
        //req.id.contractNo = "137";

        final String renderInfo = req.getString();
        m_JMMView.runOnGLThread(new Runnable() {
            public void run() {
                try {
                    String ret = MJSdk.getInstance().Execute(renderInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return;
    }

    //打开3d模型图
    private void openPreview3D(String contractNo) {
        dialogHandler.sendEmptyMessage(0);
        final MJReqBean.SdkPreview3D req = new MJReqBean.SdkPreview3D();
        req._id.contractNo = contractNo;
        //req.id.contractNo = "137";

        final String renderInfo = req.getString();
        m_JMMView.runOnGLThread(new Runnable() {
            public void run() {
                try {
                    String ret = MJSdk.getInstance().Execute(renderInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return;
    }

   /* //打开放样主页
    private void openLoftingPage() {
        final MJReqBean.SdkLoftingSurvey req = new MJReqBean.SdkLoftingSurvey();
        req._id.contractNo = lcbean.getContractNo();
        MJReqBean.SdkLoftingSurvey.DataInfo info1 = req.new DataInfo();
        info1.number = "00A";
        info1.status = "yes";
        req.data.add(info1);
        MJReqBean.SdkLoftingSurvey.DataInfo info2 = req.new DataInfo();
        info2.number = "00B";
        info2.status = "no";
        req.data.add(info2);

        final String renderInfo = req.getString();
        m_JMMView.runOnGLThread(new Runnable() {
            public void run() {
                String ret = MJSdk.getInstance().Execute(renderInfo);
                NLogger.d("demo", "openLoftingPage ret:" + ret);
                mHandler.sendEmptyMessage(RESET_SURFACEVIEW_BACKGROUND);
            }
        });
        return;
    }*/

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            int id = msg.what;
            switch (id) {
                case RESET_SURFACEVIEW_BACKGROUND: {
                    m_JMMView.resetSurfaceViewBackground();
                }
                break;
                case SHOW_MESSAGE: {
                    Bundle b = msg.getData();
                    if (b != null) {
                        String txt = b.getString("notify");
                        if (txt != null && !txt.isEmpty())
                        //MyToast.showToast(pThis, txt + "");
                        //Toast.makeText(pThis, txt, Toast.LENGTH_SHORT).show();
                        /**
                         * 冯文斌要求修改 2018-04-19 
                         */
                            if (lcbean != null) {
                                if (lcbean.isFuChiHouse()) {
                                    MyToast.showToast(pThis, "请先复尺，否则无法查看");
                                } else {
                                    MyToast.showToast(pThis, "请先量尺，否则无法查看");
                                }
                            }
                    }
                }
                break;
                case ERRORMSG:
                    MyToast.showFailToast(SurveyActivity.this, msg.obj + "");
                    break;
            }
        }

        ;
    };


    private Handler dialogHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    showLoading();
                    break;
                case 1:
                    closeLoadingDialog();
                    break;
            }
        }
    };

    public void showLoading(){
        if(mLoadingDialog!=null && !mLoadingDialog.isShowing() && ActivityUtils.isRunning(SurveyActivity.this)){
            mLoadingDialog.show();
        }
    }
    public void closeLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()&& ActivityUtils.isRunning(SurveyActivity.this)) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 500);
    }
}
