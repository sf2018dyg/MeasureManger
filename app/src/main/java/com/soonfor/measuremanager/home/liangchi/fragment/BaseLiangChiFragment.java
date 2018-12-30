package com.soonfor.measuremanager.home.liangchi.fragment;

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

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.liangchi.activity.ConfirmationReservationActivity;
import com.soonfor.measuremanager.home.liangchi.activity.LiangChiDetailActivity;
import com.soonfor.measuremanager.home.liangchi.activity.LiangChiImmediatelyActivity;
import com.soonfor.measuremanager.home.liangchi.activity.LiangChiMainActivity;
import com.soonfor.measuremanager.home.liangchi.adapter.LiangChiListAdapter;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.liangchibean.liangchiHeadBean;
import com.soonfor.measuremanager.home.liangchi.presenter.LiangChiBasePresenter;
import com.soonfor.measuremanager.home.liangchi.view.ILiangchiBaseView;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.view.dialog.normal.NormalDialog;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 15:32
 * 邮箱：suibozhu@139.com
 */
public abstract class BaseLiangChiFragment extends BaseFragment<LiangChiBasePresenter> implements ILiangchiBaseView {

    Unbinder unbinder;
    LiangChiMainActivity mActivity;
    @BindView(R.id.recyView)
    RecyclerView mRecyclerView;//1, mRecyclerView2, mRecyclerView3, mRecyclerView4;
    private LiangChiListAdapter goAdapter;//1, goAdapter2, goAdapter3, goAdapter4;
    private List<LiangChiBean> gobList = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    public static SmartRefreshLayout refreshLayout1, refreshLayout2, refreshLayout3, refreshLayout4;
    int totlePage = 0, totleNum = 0, pageNo = 1, pageSize = 10;

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (LiangChiMainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        goAdapter = new LiangChiListAdapter(mActivity, mActivity, gobList, listener);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(goAdapter);

        return rootView;
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_graborder;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {

    }

    @Override
    protected void initPresenter() {
        presenter = new LiangChiBasePresenter(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        /*if (needConnect()) {
            RefreshData(true);
        }*/
//        if (LiangChiMainActivity.currPosition > 0) {
//            LiangChiMainActivity.currPosition = 0;
//            RefreshData(true);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        RefreshData(true);
    }

    @Override
    public void RefreshData(boolean isRefresh) {
        super.RefreshData(isRefresh);
        DataTools.pageMap.put(gettype(), new PageTurnBean(0, 1, 10));
        presenter.getData(gettype(), 1, isRefresh);
    }

    @Override
    protected void loadmoredata() {
        if (DataTools.pageMap.containsKey(gettype()) && DataTools.pageMap.get(gettype()).getPageCount() > DataTools.pageMap.get(gettype()).getPageNo()) {
            presenter.getData(gettype(), DataTools.pageMap.get(gettype()).getPageNo() + 1, false);
        } else {
            finishRefresh();
      /*      new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MyToast.showToast(mActivity, "已是最后一页了！");
                }
            }, 1000);*/
        }
    }

    /**
     * 接收成功刷新界面
     */
    public void refreshAfterAccept(int position) {
        DataTools.ListMapLiangChi.get(0).remove(position);
        if (DataTools.ListMapLiangChi.get(0)==null || DataTools.ListMapLiangChi.get(0).size() == 0) {
            showNoDataHint("暂无数据！");
        } else {
            goAdapter.notifyDataSetChanged(DataTools.ListMapLiangChi.get(0));
        }
    }

    /**
     * 拒收成功刷新界面
     */
    public void refreshAfterReject(int position) {
        DataTools.ListMapLiangChi.get(0).remove(position);
        if (DataTools.ListMapLiangChi.get(0)==null || DataTools.ListMapLiangChi.get(0).size() == 0) {
            showNoDataHint("暂无数据！");
        } else {
            goAdapter.notifyDataSetChanged(DataTools.ListMapLiangChi.get(0));
        }
    }

    /**
     * 更新视图
     */
    public void setListView(int type, PageTurnBean pt, liangchiHeadBean lhb) {
        closeLoadingDialog();
        List<LiangChiBean> totalList = new ArrayList<>();
        if (DataTools.ListMapLiangChi.containsKey(type) && DataTools.ListMapLiangChi.get(type) != null) {
            if (pt != null && pt.getPageNo() > 1) {
                totalList = DataTools.ListMapLiangChi.get(type);
            }
        }
        totalList.addAll(lhb.getList());
        DataTools.ListMapLiangChi.put(type, totalList);
        DataTools.pageMap.put(type, pt);
        if (totalList.size() > 0) {
            showDataToView(null);
        } else {
            showNoDataHint("暂无数据！");
        }
    }

    NormalDialog ndialog;
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CustomDialog dialog = CustomDialog.getInstance();
            Bundle b = new Bundle();
            switch (v.getId()) {
                case R.id.llfItem:
                    //mActivity.showMsg("点击了item");
                    int posi = (int) v.getTag();
                    LiangChiBean lb = DataTools.ListMapLiangChi.get(gettype()).get(posi);
                    try {
                        if (lb != null) {
                            b.putParcelable("LiangChiBean", lb);
                            b.putInt("LC_POSITION", posi);
                            b.putInt("LiangChiStatus", gettype() + 1);
                        }
                        mActivity.startNewAct(mActivity, LiangChiDetailActivity.class, b);
                        //startNewAct(LiangChiDetailActivity.class, b);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.tvName:
                    MyToast.showToast(mActivity, "画像！");
                    break;
                case R.id.jushou:
                    final String taskno = (String) v.getTag();
                    ndialog = dialog.getInputDialog(mActivity, "拒收量尺", "" +
                                    "请填写您的拒绝原因!",
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick(View view) {
                                    EditText et = (EditText) view.getTag();
                                    presenter.rejectTask(Integer.parseInt(taskno.split("@")[0]), taskno.split("@")[1], et.getText().toString());
                                    ndialog.dismiss();
                                }
                            }, 0);
                    break;
                case R.id.jieshou:
                    final String taskno1 = (String) v.getTag();
                    ndialog = dialog.getNormalDialog(mActivity, "温馨提示", "亲接受后，尽快与客户再次确认上门测量日期，任务预期未处理，将会对您的信誉造成影响",
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick(View view) {
                                    presenter.acceptTask(Integer.parseInt(taskno1.split("@")[0]), taskno1.split("@")[1]);
                                    ndialog.dismiss();
                                }
                            });
                    break;
                case R.id.querenyuyue:
                    posi = (int) v.getTag();
                    lb = DataTools.ListMapLiangChi.get(gettype()).get(posi);
                    if (lb != null) {
                        b.putParcelable("LiangChiBean", lb);
                        b.putInt("LC_POSITION", posi);
                    }
                    mActivity.startNewAct(mActivity, ConfirmationReservationActivity.class, b);
                    //mActivity.startNewAct(ConfirmationReservationActivity.class, b);
                    break;
                case R.id.lijiliangchi:
                    b = new Bundle();
                    lb = (LiangChiBean) v.getTag();
                    if (lb != null) {
                        b.putParcelable("LiangChiBean", lb);
                        mActivity.startNewAct(mActivity, LiangChiImmediatelyActivity.class, b);
                        //mActivity.startNewAct(LiangChiImmediatelyActivity.class, b);
                    }
                    break;
            }
        }
    };

    @Override
    public void showDataToView(String returnJson) {
        super.showDataToView(returnJson);
        goAdapter.notifyDataSetChanged(DataTools.ListMapLiangChi.get(gettype()));
    }

    /**
     * 显示错误信息
     *
     * @param msg
     */
    public void showError(String msg) {
        MyToast.showToast(mActivity, msg);
        closeLoadingDialog();
    }


    @Override
    public void showLoadingDialog() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    public void showTip(String msg) {
        MyToast.showToast(getContext(), msg);
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
