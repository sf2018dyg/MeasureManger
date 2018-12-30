package com.soonfor.measuremanager.home.lofting.activity.immloft.detail.mj_sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.lofting.adapter.immloft.LoftListFirstAdapter;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkComponentEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkWallEntity;
import com.soonfor.measuremanager.home.lofting.presenter.immloft.ComCheckIsLoftedPresenter;
import com.soonfor.measuremanager.home.lofting.presenter.immloft.LoftingListPresenter;
import com.soonfor.measuremanager.home.lofting.view.immloft.ILoftingListView;
import com.soonfor.measuremanager.mjsdk.SdkLoftingActivity;
import com.soonfor.measuremanager.tools.Tokens;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by DingYg on 2018-03-13.
 * 从sdk放样界面打开的第一个放样清单界面
 */

public class LoftListFirstActivity extends BaseActivity<LoftingListPresenter> implements ILoftingListView {

    Activity mActivity;
    @BindView(R.id.rvfVlist)
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private LoftListFirstAdapter llfAdapter;
    private ArrayList<MarkWallEntity> lfInfoList;
    String taskno = "";
    String sContractNo = "";

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_loftlist_frist;
    }

    @Override
    protected void initPresenter() {
        presenter = new LoftingListPresenter(this);
    }

    @Override
    protected void initViews() {
        mActivity = LoftListFirstActivity.this;
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString(Tokens.Lofing.DETAILSKIPIMMELOFT_TITLE, "放样清单");
        ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText(title + "-户型清单");
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InFinish();
            }
        });
        taskno = bundle.getString("fy_taskNo");
        sContractNo = bundle.getString("fy_contractNo");
        lfInfoList = bundle.getParcelableArrayList("LIST_DATA");
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        llfAdapter = new LoftListFirstAdapter(mActivity, taskno, sContractNo, lfInfoList);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(llfAdapter);
        //获取清单数据
        if(lfInfoList!=null && lfInfoList.size()>0){
            setListdata();
        }else{
            presenter.getData(1, taskno, sContractNo, "");
        }
    }


    @Override
    protected void updateViews(boolean isRefresh) {
    }

    public void setListdata() {
        if (lfInfoList == null || lfInfoList.size() == 0) {
            showNoDataHint("户型放样清单为空");
        } else {
            showDataToView(null);
        }
        llfAdapter.notifyDataSetChanged(lfInfoList);
        closeLoadingDialog();
    }
    /**
     * 解析放样清单数据
     */
    public void setListdata(String data){
        if(data!=null){
            lfInfoList = new ArrayList<>();
            try {
                JSONArray ja = new JSONArray(data);
                if (ja != null && ja.length() > 0) {
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        MarkWallEntity bean = new Gson().fromJson(jo.toString(), MarkWallEntity.class);
                        bean.setShowing(true);
                        lfInfoList.add(bean);
                    }
                }
            } catch (Exception e) {}
            setListdata();
        }else {
            showNoDataHint("户型放样清单为空");
            closeLoadingDialog();
        }
    }

    @Override
    public void showLoadingDialog() {
        if(mLoadingDialog!=null && !mLoadingDialog.isShowing())
            mLoadingDialog.show();
    }

    @Override
    public void closeLoadingDialog() {
        if(mLoadingDialog!=null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        InFinish();
    }
    private void InFinish(){
        Intent intent = new Intent(mActivity, SdkLoftingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("MARKLISTBEAN_LIST", lfInfoList);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Activity.RESULT_OK && data!=null){
            if(requestCode==Tokens.Lofing.REQUEST_CODE_FIRST_TO_SECOEND){
                presenter.getData(1, taskno, sContractNo, "");
            }
        }
    }
}