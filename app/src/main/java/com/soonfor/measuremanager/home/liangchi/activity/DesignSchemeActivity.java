package com.soonfor.measuremanager.home.liangchi.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.lofting.adapter.detail.DesignPlanListAdapter;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Design.DesignPlanBean;
import com.soonfor.measuremanager.home.lofting.model.bean.taskinfo.TaskCompleteInfoBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.BindView;
import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/8 0008 14:23
 * 邮箱：suibozhu@139.com
 * 设计方案
 */
public class DesignSchemeActivity extends BaseActivity implements AsyncUtils.AsyncCallback {

    Context mContext;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvfDesignPlan)
    RecyclerView mRecyclerView;
    private DesignPlanListAdapter dpAdapter;
    //private List<DesignPlanBean> dpList;
    private DesignPlanBean dplb;
    private GridLayoutManager manager;
    String taskNo;
    String orderNo;
    @BindView(R.id.llftxterror)
    LinearLayout llftxterror;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_fuchi_design_scheme;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);// 关闭title
        mContext = DesignSchemeActivity.this;
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(listener);

        Bundle bundle = getIntent().getBundleExtra("EntityBundle");
        taskNo = bundle.getString("taskNo", "");
        orderNo = bundle.getString("orderNo", "");

        manager = new GridLayoutManager(mContext,1);
        mRecyclerView.setLayoutManager(manager);
        dpAdapter = new DesignPlanListAdapter(mContext, dplb);
        mRecyclerView.setAdapter(dpAdapter);

        getDatas(taskNo,orderNo);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    public void getDatas(String taskNo, String orderNo) {
        Request.getTaskCompleteInfo(DesignSchemeActivity.this, DesignSchemeActivity.this, taskNo, "design", orderNo);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode){
            case Request.GET_TASK_COMPLETE_INFO:
                JsonUtils.analysisJsonHead( object.toString(), new JsonUtils.OperateData() {                    @Override                    public void doingInFail(String msg) {                        showNoDataHint(msg);                    }
                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            NLogger.w("测量清单返回json:" + data);
                            TaskCompleteInfoBean tcBean = null;
                            Gson gson = new Gson();
                            try{
                                tcBean = gson.fromJson(data, TaskCompleteInfoBean.class);
                            }catch (Exception e){}
                            if(tcBean!=null && tcBean.getDesignInfos()!=null){
                                isHaveDatas(1);
                                //开始组装
                                dplb = tcBean.getDesignInfos();
                                dpAdapter.notifyDataSetChanged(dplb);
                            }else{
                                isHaveDatas(0);
                            }
                        }catch (Exception e){
                            showMsg(e.toString());
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {

    }

    private void isHaveDatas(int type){
        switch (type){
            case 0:
                mRecyclerView.setVisibility(View.GONE);
                llftxterror.setVisibility(View.VISIBLE);
                break;
            case 1:
                mRecyclerView.setVisibility(View.VISIBLE);
                llftxterror.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 更新视图
     */
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
