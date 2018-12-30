package com.soonfor.measuremanager.home.lofting.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.lofting.activity.ConfimAppointActivity;
import com.soonfor.measuremanager.home.lofting.activity.LoftingDetailActivity;
import com.soonfor.measuremanager.home.lofting.activity.LoftingMainActivity;
import com.soonfor.measuremanager.home.lofting.activity.immloft.ImmediateLoftingActivity;
import com.soonfor.measuremanager.home.lofting.adapter.LoftingMainAdapter;
import com.soonfor.measuremanager.home.lofting.model.bean.LoftingMainBean;
import com.soonfor.measuremanager.home.lofting.presenter.LoftingMainPresenter;
import com.soonfor.measuremanager.home.lofting.view.ILoftingMainView;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.dialog.normal.NormalDialog;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DingYg on 2018-01-29.
 */

public abstract class BaseLoftingMainFragment extends BaseFragment<LoftingMainPresenter> implements ILoftingMainView {

    Unbinder unbinder;
    Activity mActivity;
    @BindView(R.id.recyView)
    RecyclerView mRecyclerView;
    private LoftingMainAdapter goAdapter;
    private LinearLayoutManager mLayoutManager;
    CustomDialog ddialog;
    NormalDialog ndialog;
    public static int fragmentPositon = 0;// 第一次进入的是哪个Fragment
    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        ddialog = CustomDialog.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        goAdapter = new LoftingMainAdapter(mActivity, gettype(),adapterlistener);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(goAdapter);

        return rootView;
    }

    /**
     * 绑定布局文件
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_graborder;
    }
    @Override
    protected void initPresenter() {
        presenter = new LoftingMainPresenter(this);
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {}

    @Override
    protected void updateViews(boolean isRefresh) {
        if(needConnect()) {
            mSwipeRefresh.autoRefresh();
        }else if(LoftingMainActivity.fragmentPositon!=0){
            mSwipeRefresh.autoRefresh();
            LoftingMainActivity.fragmentPositon = 0;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(LoftingMainActivity.ItemPosition>=0){
            RefreshData(true);
        }
    }

    private View.OnClickListener adapterlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int position = (int) v.getTag();
            final LoftingMainBean lfBean = DataTools.ListMap.get(gettype()).get(position);
            switch (v.getId()) {
                case R.id.llfItem:
                    Intent intent = new Intent(mActivity, LoftingDetailActivity.class);
                    intent.putExtra(Tokens.Lofing.ITEMSKIPDETAIL_STATUS, gettype());
                    intent.putExtra("POSITION", position);
                    intent.putExtra(Tokens.Lofing.ITEMSKIPDETAIL_ITEM, lfBean);
                    mActivity.startActivity(intent);
                    break;
                case R.id.tvName:
//                    ndialog = ddialog.getInputDialog(mActivity, "设计头像", "" +
//                                    "请为您的设计起个名字",
//                            new OnBtnClickL() {
//                                @Override
//                                public void onBtnClick(View view) {
//                                    EditText et = (EditText) view.getTag();
//                                    ndialog.dismiss();
//                                    MyToast.showSuccessToast(mActivity, et.getText().toString());
//                                }
//                            }, position);
                    break;
                case R.id.jushou:
                    ndialog = ddialog.getInputDialog(mActivity, "拒收放样", "" +
                                    "请填写您的拒收原因",
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick(View view) {
                                    EditText et = (EditText) view.getTag();
                                    ndialog.dismiss();
                                    presenter.rejectTask(position, lfBean.getTaskNo(),et.getText().toString());
                                }
                            }, position);
                    break;
                case R.id.jieshou:
                    ndialog = ddialog.getNormalDialog(mActivity, "温馨提示", "亲接收后，尽快与客户确认再次上门测量日期，任务预期未处理，将会对您的信誉造成影响",
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick(View view) {
                                    ndialog.dismiss();
                                    presenter.acceptTask(position, lfBean.getTaskNo());
                                }
                            });
                    break;
                case R.id.querenyuyue:
                    Bundle bundle0 = new Bundle();
                    bundle0.putInt("POSITION", position);
                    bundle0.putParcelable(Tokens.Lofing.SKIP_TO_CONFIMAPPOINT, lfBean);
                    Intent intent0 = new Intent(mActivity, ConfimAppointActivity.class);
                    intent0.putExtras(bundle0);
                    mActivity.startActivity(intent0);
                    break;
                case R.id.lijiFy:
                    Intent intent1 = new Intent(mActivity, ImmediateLoftingActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Tokens.Lofing.DETAILSKIPIMMELOFT_TITLE, lfBean.getBuilding() + lfBean.getCustomerName() + "-放样");
                    bundle.putParcelable(Tokens.Lofing.DETAILSKIPIMMELOFT_TASKINFO, lfBean);
                    bundle.putInt("POSITION", position);
                    intent1.putExtras(bundle);
                    mActivity.startActivity(intent1);
                    break;
            }
        }
    };
   

    @Override
    public void RefreshData(boolean isNeedProgressDialog) {
        super.RefreshData(isNeedProgressDialog);
        DataTools.pageMap.put(gettype(), new PageTurnBean(0, 1, 10));
        presenter.getData(gettype(), 1, isNeedProgressDialog);
    }

    @Override
    protected void loadmoredata() {
        if(DataTools.pageMap.containsKey(gettype()) && DataTools.pageMap.get(gettype()).getPageCount()>DataTools.pageMap.get(gettype()).getPageNo()){
            presenter.getData(gettype(),DataTools.pageMap.get(gettype()).getPageNo()+1,false);
        }else {
            finishRefresh();
           /* new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MyToast.showToast(mActivity, "已是最后一页了！");
                }
            },1000);*/
        }
    }
    /**
     * 更新视图
     * @param type
     */
    public void setListView(int type, PageTurnBean pt, List<LoftingMainBean> lfBeans) {
        closeLoadingDialog();
        List<LoftingMainBean> totalList = new ArrayList<>();
        if(DataTools.ListMap.containsKey(type) && DataTools.ListMap.get(type) != null){
            if(pt!=null && pt.getPageNo() > 1){
                totalList = DataTools.ListMap.get(type);
            }
        }
        totalList.addAll(lfBeans);
        DataTools.ListMap.put(type,totalList);
        DataTools.pageMap.put(type,pt);
        if(totalList.size()>0){
            showDataToView(null);
        }else{
            showNoDataHint("暂无数据");
        }
    }

    @Override
    public void showDataToView(String returnJson) {
        super.showDataToView(returnJson);
        goAdapter.notifyDataSetChanged(DataTools.ListMap.get(gettype()));
        if(DataTools.pageMap.containsKey(gettype()) && DataTools.pageMap.get(gettype())!=null){
            int currentPage =  DataTools.pageMap.get(gettype()).getPageNo();
            if(currentPage > 0){
                goAdapter.MoveToPosition(mLayoutManager,mRecyclerView,(currentPage-1) * 10);
            }
        }
    }
    /**
     * 接收成功刷新界面
     */
    public void refreshAfterAccept(int position){
        DataTools.ListMap.get(gettype()).remove(position);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goAdapter.notifyDataSetChanged(DataTools.ListMap.get(gettype()));
                if(DataTools.ListMap.get(gettype())==null || DataTools.ListMap.get(gettype()).size()==0){
                    showNoDataHint("暂无数据");
                }
            }
        },1500);
    }
    /**
     * 拒收成功刷新界面
     */
    public void refreshAfterReject(int position){
        DataTools.ListMap.get(gettype()).remove(position);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goAdapter.notifyDataSetChanged(DataTools.ListMap.get(gettype()));
                if(DataTools.ListMap.get(gettype())==null || DataTools.ListMap.get(gettype()).size()==0){
                    showNoDataHint("暂无数据");
                }
            }
        },1500);
    }
    /**
     * 显示错误信息
     * @param msg
     */
    public void showError(String msg){
        MyToast.showToast(mActivity,msg);
        closeLoadingDialog();
    }

    @Override
    public void showLoadingDialog() {mLoadingDialog.show();}
    @Override
    public void closeLoadingDialog() {
        mLoadingDialog.dismiss();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract boolean needConnect();

    /**
     * gettype回去接口对应的type 0.取待领取的任务, 1.已领取待联系的任务, 2.已领取待上门的任务, 3.已延期的任务 4.全部
     */
    protected abstract int gettype();
}
