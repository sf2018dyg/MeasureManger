package com.soonfor.measuremanager.home.lofting.activity.immloft;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.lofting.activity.LoftingMainActivity;
import com.soonfor.measuremanager.home.lofting.adapter.immloft.ImmLoftingListAdpter;
import com.soonfor.measuremanager.home.lofting.model.bean.LoftingMainBean;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.LoftItemBean;
import com.soonfor.measuremanager.home.lofting.presenter.immloft.ImmeLoftPresenter;
import com.soonfor.measuremanager.home.lofting.view.immloft.ImmeLoftView;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DingYg on 2018-02-08.
 * 查看放样（点击立即放样进入）
 */

public class ImmediateLoftingActivity extends BaseActivity<ImmeLoftPresenter> implements ImmeLoftView {

    ImmediateLoftingActivity mContext;
    @BindView(R.id.tvfcustom)
    TextView tvfcustom;
    @BindView(R.id.tvfadress)
    TextView tvfadress;
    @BindView(R.id.tvfstatus)
    TextView tvfstatus;
    @BindView(R.id.recyView)
    RecyclerView recyView;
    TextView tvsubmit;
    LinearLayoutManager manager;
    ImmLoftingListAdpter adapter;
    List<LoftItemBean> ltBeans;
    LoftingMainBean task = null;
    boolean isCanSubmit = false;
    private int listItemPosition = -1;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_immediatelylofting;
    }

    @Override
    protected void initPresenter() {
        mContext = ImmediateLoftingActivity.this;
        presenter = new ImmeLoftPresenter(this);
    }

    @Override
    protected void initViews() {
        tvsubmit = toolbar.findViewById(R.id.tvsubmit);
        Bundle bundle = getIntent().getExtras();
        manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        // 设置布局管理器
        adapter = new ImmLoftingListAdpter(mContext, ltBeans);
        recyView.setLayoutManager(manager);
        recyView.setAdapter(adapter);
        listItemPosition = bundle.getInt("POSITION", -1);
        task = bundle.getParcelable(Tokens.Lofing.DETAILSKIPIMMELOFT_TASKINFO);
        if (task != null) {
            /**
             * 设置信息到界面
             */
            if (!task.getBuilding().equals("")) {
                tvfcustom.setText(task.getBuilding() + "-" + task.getCustomerName());
            } else {
                tvfcustom.setText(task.getCustomerName());
            }
            tvfadress.setText(task.getAddress());
            tvfstatus.setText(task.getStatusDesc());
        }
    }

    Dialog finalConfirmDialog = null;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isCanSubmit) {
                finalConfirmDialog = CustomDialog.getDoubleNormalDialog(mContext, "温馨提示", "确认提交即视为正式更改放样状态，更改成功后将无法撤销，确定提交吗?", new OnBtnClickL() {
                    @Override
                    public void onBtnClick(View view) {
                        //未完成
                        finalConfirmDialog.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick(View view) {
                        //完成
                        finalConfirmDialog.dismiss();
                        presenter.setFinishTask(task.getTaskNo());
                    }
                });
            } else {
                showMsg("尚有户型未完成放样,无法提交任务");
            }
        }
    };

    @Override
    protected void updateViews(boolean isRefresh) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * 请求数据列表
         */
        presenter.getData(task, true);
    }

    public void setListdata(List<LoftItemBean> listdata) {
        adapter.notifyDataSetChanged(listdata);
        closeLoadingDialog();
        if (listdata != null && listdata.size() > 0) {
            isCanSubmit = true;
            for (int i = 0; i < listdata.size(); i++) {
                if (!listdata.get(i).getFstatus().equals("2")) {
                    isCanSubmit = false;
                    break;
                }
            }
            if (isCanSubmit) {
                tvsubmit.setVisibility(View.VISIBLE);
                tvsubmit.setOnClickListener(listener);
            } else {
                tvsubmit.setVisibility(View.GONE);
            }
        }
    }


    /**
     * 更新视图
     */
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingDialog() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void closeLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 放样户型整体提交后
     */
    public void finish_task(){
        LoftingMainActivity.ItemPosition = listItemPosition;
        finish();
    }
}
