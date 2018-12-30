package com.soonfor.measuremanager.home.liangchi.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.bases.BaseHouseTypeListView;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.MeasureResultEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.taskinfo.TaskCompleteInfoBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/9 13:46
 * 邮箱：suibozhu@139.com
 * <p>
 * 测量清单
 */
public class SurveyListActivity extends BaseActivity implements AsyncUtils.AsyncCallback {

    Context mContext;
    String taskNo;
    String orderNo;
    String taskType;
    @BindView(R.id.llftxterror)
    LinearLayout llftxterror; //错误的信息
    @BindView(R.id.llfdata)
    LinearLayout llfdata; //有数据时

    String fimpath;
    String contractNo;
    @BindView(R.id.houseType)
    BaseHouseTypeListView houseTypeView;
    @BindView(R.id.lclistError)
    TextView lclistError;
    boolean isFuChi;
    List<MeasureResultEntity> mrList = null;

    String houseName;//户型名称

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_surveylist;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);// 关闭title
        mContext = SurveyListActivity.this;
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(listener);

        Bundle bundle = getIntent().getBundleExtra("EntityBundle");
        taskNo = bundle.getString("taskNo", "");
        taskType = bundle.getString("taskType", "");
        orderNo = bundle.getString("orderNo", "");
        fimpath = bundle.getString("fimpath", "");
        contractNo = bundle.getString("contractNo", "");
        isFuChi = bundle.getBoolean("isFuChi", false);
        houseName = bundle.getString("HouseName", "");
        if (!contractNo.equals("")) {
            contractNo = contractNo.split("-")[1];
        }

        //户型图 从上面传过来
        // ImageUtil.loadImage(mContext, fimpath, imgpath);

        showLoadingDialog();
        Request.getTaskCompleteInfoWithContractNo(SurveyListActivity.this, SurveyListActivity.this, taskNo, taskType, orderNo, contractNo);
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

    private void showTip(String msg) {
        MyToast.showToast(mContext, msg);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        Gson gson = new Gson();
        switch (requestCode) {
            case Request.GET_TASK_COMPLETE_INFO:
                closeLoadingDialog();
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        showNoDataHint(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            NLogger.w("量尺详情中查看测量清单返回:" + data);
                            TaskCompleteInfoBean tcBean = null;
                            Gson gson = new Gson();
                            try {
                                tcBean = gson.fromJson(data, TaskCompleteInfoBean.class);
                            } catch (Exception e) {
                            }
                            if (tcBean != null && tcBean.getMeasureInfo() != null && tcBean.getMeasureInfo().size() > 0) {
                                showDataToView(null);
                                List<MeasureResultEntity> resultEntities;
                                if (tcBean.getMeasureInfo().size() > 1) {
                                    resultEntities = DataTools.getHtList(tcBean.getMeasureInfo(), houseName);
                                } else {
                                    resultEntities = tcBean.getMeasureInfo();
                                }
                                if (isFuChi) {
                                    houseTypeView.initHoustTypeListView(mContext, "复尺清单", resultEntities, 0);
                                } else {
                                    houseTypeView.initHoustTypeListView(mContext, "量尺清单", resultEntities, 0);
                                }
                            } else {
                                if (isFuChi) {
                                    showNoDataHint("暂无复尺清单");
                                } else {
                                    showNoDataHint("暂无量尺清单");
                                }
                                closeLoadingDialog();
                            }
                        } catch (Exception e) {
                            showTip(e.toString());
                            showNoDataHint("请求出错啦");
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        closeLoadingDialog();
        showNoDataHint("请求出错啦");
    }

    public void showLoadingDialog() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    public void closeLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

}
