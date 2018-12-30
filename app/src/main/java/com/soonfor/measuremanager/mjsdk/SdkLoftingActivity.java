package com.soonfor.measuremanager.mjsdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.jiamm.bluetooth.MeasureDevice;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.home.lofting.activity.immloft.LoftLookDetailActivity;
import com.soonfor.measuremanager.home.lofting.activity.immloft.detail.mj_sdk.LoftListFirstActivity;
import com.soonfor.measuremanager.home.lofting.activity.immloft.detail.mj_sdk.LoftListSecondActivity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkWallEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.WallPath;
import com.soonfor.measuremanager.home.lofting.presenter.immloft.ComCheckIsLoftedPresenter;
import com.soonfor.measuremanager.home.lofting.view.immloft.IComCheckIsLoftedView;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.Tokens;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jesse.nativelogger.NLogger;
import cn.jiamm.lib.JMMView;
import cn.jiamm.lib.JMMViewBaseActivity;
import cn.jiamm.lib.MJSdk;

/**
 * Created by Administrator on 2018-03-12.
 */

public class SdkLoftingActivity extends JMMViewBaseActivity {

    private final int RESET_SURFACEVIEW_BACKGROUND = 10;
    private final int SHOW_MESSAGE = 11;
    private final int REQUEST_CODE_WALL = 12;
    private final int REQUEST_CODE_LOFTLIST = 13;

    private SdkLoftingActivity pThis;
    private LoadHouseListener mLoadHouseListener;
    private String taskno;//任务号
    private String sContractNo, sHouseId;//合同号，户型id
    private boolean bHouseClosed = false;
    private int optionType;
    private String TitleText = "放样清单";
    private ArrayList<MarkWallEntity> lfInfoList = null;
    private ArrayList<WallPath> wallPaths = null;
    private ComCheckIsLoftedPresenter cPresenter;
    private boolean isLofting = false;//是否是放样
    private String contractNo_To_Post = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        try {
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
//                NLogger.w("demo", "config ret:" + sret);
            }
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lofting_view);
            pThis = this;
            //绘图视图初始化
            m_JMMView = (JMMView) findViewById(R.id.m_JiaMMView);
            m_JMMView.setActivity(this);

            Bundle bundle = getIntent().getExtras();
            lfInfoList = bundle.getParcelableArrayList("LIST_DATA");
            wallPaths = bundle.getParcelableArrayList("LOFT_WALLPATHS");
            taskno = bundle.getString("fy_taskNo", "");
            sContractNo = bundle.getString("fy_contractNo", "");
            if (!sContractNo.equals("") && sContractNo.contains("-")) {
                contractNo_To_Post = sContractNo.split("-")[1];
            }
            optionType = bundle.getInt("fy_optionType", 0);
            if (optionType == 0) {
                TitleText = bundle.getString("TITLETEXT", "放样清单");
            }
            cPresenter = new ComCheckIsLoftedPresenter(iccView);
            //回调消息设置
            mLoadHouseListener = new LoadHouseListener();
            MJSdk.getInstance().regMessageListener(mLoadHouseListener);
        }catch (Exception e){
            e.fillInStackTrace();
        }
    }

    private IComCheckIsLoftedView iccView = new IComCheckIsLoftedView() {

        @Override
        public void setGetData(boolean isSuccess, ArrayList<MarkWallEntity> markWallEntityList) {
            if (isSuccess && markWallEntityList != null) {
                lfInfoList = markWallEntityList;
            }
        }

        @Override
        public void finishDispose(boolean isLofting, ArrayList<MarkWallEntity> markWallEntityList) {
            if (markWallEntityList != null && markWallEntityList.size() > 0)
                lfInfoList = markWallEntityList;
            if (isLofting) {//已开始放样了
                //提交状态值
                cPresenter.setTaskStatus(SdkLoftingActivity.this, "1", taskno, sContractNo);
            } else {
                finish();
            }
        }

        @Override
        public void finishSumbit(boolean isSuboted, String msg) {
            if (isSuboted) {
                DataTools.Loft.statusCode = 1;
            }
            InFinish();
        }

        @Override
        public void setGetWallPaths(boolean isSuccess, ArrayList<WallPath> paths) {
            wallPaths = paths;
            updateLoftingPage();
        }

        @Override
        public void showLoading() {

        }

        @Override
        public void hideLoading() {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        m_JMMView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //refreshLoftingPage();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        m_JMMView.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onPause() {
        super.onPause();
        m_JMMView.onPause();
    }

    private void InFinish() {
        Intent intent = new Intent(SdkLoftingActivity.this, LoftLookDetailActivity.class);
        intent.putParcelableArrayListExtra("NEW_WALLLIST", lfInfoList);
        SdkLoftingActivity.this.setResult(Activity.RESULT_OK, intent);
        SdkLoftingActivity.this.finish();


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
                //NLogger.w("SdkLoftingActivity", "req:" + sreq);
                m_JMMView.runOnGLThread(new Runnable() {
                    @Override
                    public void run() {
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
            JSONObject jobj;
            try {
                jobj = new JSONObject(arg0);
                String cmd = jobj.optString("cmd");
                if (cmd.equals("event_OnInitEnd")) {
                    if (optionType == 0)
                        openLoftingPage();
                    else if (optionType == 1)
                        openPreviewSurvey();
                    else if (optionType == 2)
                        openPreview3D();
                } else if (cmd.equals("back_home")) {
                    bHouseClosed = true;
                    if (isLofting) {
                        isLofting = false;
                        getWallDatas(1);
                    } else {
                        finish();
                    }
                } else if (cmd.equals("complete_house")) {
                    bHouseClosed = true;
                    finish();
                } else if (cmd.equals("house_empty")) {
                    Bundle b = new Bundle();
                    b.putString("notify", "房屋数据未加载完成");
                    Message msg = mHandler.obtainMessage(SHOW_MESSAGE);
                    msg.setData(b);
                    mHandler.sendMessage(msg);
                } else if (cmd.equals("created_house_info")) {
                    JSONObject jparams = jobj.optJSONObject("params");
                    JSONObject jhouse = jparams.optJSONObject("houseInfo");
                    sHouseId = jhouse.optString("_id");
                    NLogger.w("demo", "create house id:" + sHouseId);
                } else if (cmd.equals("all_lofting_list")) {//清单列表
                    /**
                     * 展开清单列表
                     */
                    openHouseTypeList();
                } else if (cmd.equals("single_lofting_list")) {
                    String number = jobj.optString("number");
                    /**
                     * 墙面清单
                     */
                    openWallList(number);
                } else if (cmd.equals("lofting_home_updata")) {
                } else if (cmd.equals("lofting_home_changed")) {
                    //墙面放样后返回主场景
                    updateLoftingPage();
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private String getWallLogtPath(String wallcode) {
        if (wallPaths == null || wallPaths.size() == 0) {
            return "";
        } else {
            for (int i = 0; i < wallPaths.size(); i++) {
                if (wallPaths.get(i).getWallCode().equals(wallcode)) {
                    return wallPaths.get(i).getUrl();
                }
            }
            return "";
        }
    }

    //打开放样主页
    private void openLoftingPage() {
        isLofting = true;
        MJReqBean.SdkLoftingSurvey req = new MJReqBean.SdkLoftingSurvey();
        req._id.contractNo = sContractNo;
        if (lfInfoList != null && lfInfoList.size() > 0) {
            for (int i = 0; i < lfInfoList.size(); i++) {
                MJReqBean.SdkLoftingSurvey.DataInfo info1 = req.new DataInfo();
                info1.number = lfInfoList.get(i).getWallCode();
                info1.proxyNumber = lfInfoList.get(i).getProxyNumber();
                info1.remoteLoftPicUrl = getWallLogtPath(info1.number);
                if (lfInfoList.get(i).getNeedMark().equals("1")) {//需要放样
                    if (lfInfoList.get(i).getIsMark().equals("1")) {//已放样
                        info1.status = "yes";
                    } else {//待放样
                        info1.status = "no";
                    }
                }
//                else{
//                    info1.status = "null";
//                }
                req.data.add(info1);
            }
        }
        final String renderInfo = req.getString();
        m_JMMView.runOnGLThread(new Runnable() {
            public void run() {
                String ret = MJSdk.getInstance().Execute(renderInfo);
                NLogger.w("Lofting", "open ret:" + ret);
                mHandler.sendEmptyMessage(RESET_SURFACEVIEW_BACKGROUND);
            }
        });
        if (wallPaths == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }).start();
            try {
                cPresenter.getWallPaths(SdkLoftingActivity.this, contractNo_To_Post, "");
            } catch (Exception e) {
            }
        }
}

    //更新放样主页
    private void updateLoftingPage() {
        isLofting = true;
        MJReqBean.SdkLoftingRefresh req = new MJReqBean.SdkLoftingRefresh();
        req._id.contractNo = sContractNo;
        if (lfInfoList != null && lfInfoList.size() > 0) {
            for (int i = 0; i < lfInfoList.size(); i++) {
                MJReqBean.SdkLoftingRefresh.DataInfo info1 = req.new DataInfo();
                info1.number = lfInfoList.get(i).getWallCode();
                info1.proxyNumber = lfInfoList.get(i).getProxyNumber();
                info1.remoteLoftPicUrl = getWallLogtPath(info1.number);
                if (lfInfoList.get(i).getIsMark().equals("1")) {
                    info1.status = "yes";
                } else {
                    info1.status = "no";
                }
                req.data.add(info1);
            }
        } else {
            req.data = null;
        }
        final String renderInfo = req.getString();
        m_JMMView.runOnGLThread(new Runnable() {
            public void run() {
                String ret = MJSdk.getInstance().Execute(renderInfo);
                NLogger.w("Lofting", "open ret:" + ret);
                mHandler.sendEmptyMessage(RESET_SURFACEVIEW_BACKGROUND);
            }
        });
    }


    //打开平面图
    private void openPreviewSurvey() {
        isLofting = false;
        MJReqBean.SdkPreviewSurvey req = new MJReqBean.SdkPreviewSurvey();
        req._id.contractNo = sContractNo;
        final String renderInfo = req.getString();
        m_JMMView.runOnGLThread(new Runnable() {
            public void run() {
                String ret = MJSdk.getInstance().Execute(renderInfo);
                NLogger.w("demo", "open ret:" + ret);
                mHandler.sendEmptyMessage(RESET_SURFACEVIEW_BACKGROUND);
            }
        });
    }

    //打开3d模型图
    private void openPreview3D() {
        isLofting = false;
        MJReqBean.SdkPreview3D req = new MJReqBean.SdkPreview3D();
        req._id.contractNo = sContractNo;

        final String renderInfo = req.getString();
        m_JMMView.runOnGLThread(new Runnable() {
            public void run() {
                String ret = MJSdk.getInstance().Execute(renderInfo);
                NLogger.w("demo", "open ret:" + ret);
                mHandler.sendEmptyMessage(RESET_SURFACEVIEW_BACKGROUND);
            }
        });
    }

    //打开户型清单信息
    private void openHouseTypeList() {
        Bundle bundle = new Bundle();
        bundle.putString(Tokens.Lofing.DETAILSKIPIMMELOFT_TITLE, TitleText);
        bundle.putString("fy_taskNo", taskno);
        bundle.putString("fy_contractNo", contractNo_To_Post);
        bundle.putParcelableArrayList("LIST_DATA", lfInfoList);
        Intent intent = new Intent(SdkLoftingActivity.this, LoftListFirstActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE_LOFTLIST);
    }

    //打开墙面清单信息
    private void openWallList(String number) {
        Bundle bundle = new Bundle();
        bundle.putString(Tokens.Lofing.DETAILSKIPIMMELOFT_TITLE_WALL, "放样清单");
        bundle.putString("fy_taskNo", taskno);
        bundle.putString("fy_contractNo", contractNo_To_Post);
        bundle.putString("fy_wallCode", number);//墙面编号
        Intent intent = new Intent(SdkLoftingActivity.this, LoftListSecondActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE_WALL);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int id = msg.what;
            switch (id) {
                case RESET_SURFACEVIEW_BACKGROUND: {
                    try {
                        m_JMMView.resetSurfaceViewBackground();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
                case SHOW_MESSAGE: {
                    Bundle b = msg.getData();
                    if (b != null) {
                        String txt = b.getString("notify");
                        if (txt != null && !txt.isEmpty())
                            Toast.makeText(pThis, txt, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }
    };

    public void getWallDatas(final int actionType) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = m_handler.obtainMessage();
                msg.arg1 = actionType;
                m_handler.sendMessage(msg);
            }
        }).start();
    }

    final Handler m_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //执行AsyncHttpClient的get或post函数
            cPresenter.getWalls(SdkLoftingActivity.this, taskno, contractNo_To_Post, msg.arg1);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_WALL://从墙面放样回来
                    getWallDatas(0);
                    break;
                case REQUEST_CODE_LOFTLIST://从清单列表中回来
                    Bundle bundle = data.getExtras();
                    lfInfoList = bundle.getParcelableArrayList("MARKLISTBEAN_LIST");
                    updateLoftingPage();
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        bHouseClosed = true;
        if (isLofting) {
            isLofting = false;
            getWallDatas(1);
        } else {
            finish();
        }
    }
}
