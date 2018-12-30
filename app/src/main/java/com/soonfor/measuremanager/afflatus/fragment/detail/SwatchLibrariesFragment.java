package com.soonfor.measuremanager.afflatus.fragment.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.adpter.TintplateListAdapter;
import com.soonfor.measuremanager.afflatus.bean.TintplateBean;
import com.soonfor.measuremanager.afflatus.fragment.drawlayout.TintplateFragment;
import com.soonfor.measuremanager.afflatus.presenter.SwatchLibrariesBasePresenter;
import com.soonfor.measuremanager.afflatus.view.ISwatchLibrariesBaseView;
import com.soonfor.measuremanager.bases.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/20 15:37
 * 邮箱：suibozhu@139.com
 * 色板库
 */

public class SwatchLibrariesFragment extends BaseFragment<SwatchLibrariesBasePresenter> implements ISwatchLibrariesBaseView {

    Unbinder unbinder;
    @BindView(R.id.rlvseban)
    RecyclerView rlvseban;
    GridLayoutManager manager;
    TintplateListAdapter adapter;
    List<TintplateBean> list;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.drawlayout)
    DrawerLayout dl;
    String bid = "";

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        manager = new GridLayoutManager(getContext(), 2);
        list = new ArrayList<TintplateBean>();
        adapter = new TintplateListAdapter(getContext(), list);
        rlvseban.setLayoutManager(manager);
        rlvseban.setAdapter(adapter);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView text, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.getData("0", edtSearch.getText().toString(), bid, true);
                }
                return false;
            }
        });

        return rootView;
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_swatchlibraries;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        mEmptyLayout.show(true);
    }

    @Override
    protected void initPresenter() {
        presenter = new SwatchLibrariesBasePresenter(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getData("0", edtSearch.getText().toString(), bid, isRefresh);
    }

    /**
     * 更新视图
     *
     * @param returnStr
     */
    public void setListView(String returnStr) {
        mEmptyLayout.hide();
        Gson gson = new Gson();
        List<TintplateBean> list = gson.fromJson(returnStr, new TypeToken<List<TintplateBean>>() {
        }.getType());
        if (list != null) {
            this.list = list;
            if(list.size()==0){
                mEmptyLayout.show("没有相关数据","");
            }
            adapter.notifyDataSetChanged(list);
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().removeStickyEvent(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TintplateFragment.MessageEvent event) {
        if (event != null) {
            //过滤数据 todo...
            bid = event.getBid();
            if(!bid.equals("")) {
                if(bid.equals("-1")){
                    bid = "";
                }
                presenter.getData("0", edtSearch.getText().toString(), bid, true);
            }

            dl.closeDrawer(Gravity.RIGHT);
        }
    };

    @OnClick({R.id.imgfilter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgfilter:
                if (dl != null) {
                    dl.openDrawer(Gravity.RIGHT);
                }
                break;
        }
    }

    @Override
    public void RefreshData(boolean isRefresh) {
        super.RefreshData(isRefresh);
        presenter.getData("0", edtSearch.getText().toString(), bid, true);
        mSwipeRefresh.finishRefresh();
    }

    @Override
    protected void loadmoredata() {
        super.loadmoredata();
        mSwipeRefresh.finishLoadmore();
    }

        /*private void setDatas() {
        list.add(new TintplateBean("http://img3.imgtn.bdimg.com/it/u=1671601595,1711746094&fm=200&gp=0.jpg", "松木", "木板", "黑檀，柿属植物，俗称风车木别名：乌木、黑紫檀大乔木，高达45m，直径达2.5m；板根大；材表常见小沟槽本属15种黑檀木心边材区别明显，边材白色至浅红褐色；心材黑色及不规则黑色心材木材有光泽、无特殊气味"));
        list.add(new TintplateBean("http://img4.imgtn.bdimg.com/it/u=768989474,774515258&fm=200&gp=0.jpg", "木板", "木板", "黑檀，柿属植物，俗称风车木别名：乌木、黑紫檀大乔木，高达45m，直径达2.5m；板根大；材表常见小沟槽本属15种黑檀木心边材区别明显，边材白色至浅红褐色；心材黑色及不规则黑色心材木材有光泽、无特殊气味"));
        list.add(new TintplateBean("http://img5.imgtn.bdimg.com/it/u=1475911366,1731987655&fm=27&gp=0.jpg", "石头", "墙壁", "黑檀，柿属植物，俗称风车木别名：乌木、黑紫檀大乔木，高达45m，直径达2.5m；板根大；材表常见小沟槽本属15种黑檀木心边材区别明显，边材白色至浅红褐色；心材黑色及不规则黑色心材木材有光泽、无特殊气味"));
        list.add(new TintplateBean("http://img2.imgtn.bdimg.com/it/u=1567099786,4231137032&fm=27&gp=0.jpg", "木板", "木板", "黑檀，柿属植物，俗称风车木别名：乌木、黑紫檀大乔木，高达45m，直径达2.5m；板根大；材表常见小沟槽本属15种黑檀木心边材区别明显，边材白色至浅红褐色；心材黑色及不规则黑色心材木材有光泽、无特殊气味"));
        list.add(new TintplateBean("http://img0.imgtn.bdimg.com/it/u=3157860052,3054561222&fm=200&gp=0.jpg", "松木", "墙壁", "黑檀，柿属植物，俗称风车木别名：乌木、黑紫檀大乔木，高达45m，直径达2.5m；板根大；材表常见小沟槽本属15种黑檀木心边材区别明显，边材白色至浅红褐色；心材黑色及不规则黑色心材木材有光泽、无特殊气味"));
        list.add(new TintplateBean("http://img5.imgtn.bdimg.com/it/u=2140467470,2516998645&fm=27&gp=0.jpg", "石头", "木板", "黑檀，柿属植物，俗称风车木别名：乌木、黑紫檀大乔木，高达45m，直径达2.5m；板根大；材表常见小沟槽本属15种黑檀木心边材区别明显，边材白色至浅红褐色；心材黑色及不规则黑色心材木材有光泽、无特殊气味"));
        me_evaluate_ranking.notifyDataSetChanged(list);
    }*/
}
