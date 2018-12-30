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
import com.soonfor.measuremanager.afflatus.adpter.SparePartsListAdapter;
import com.soonfor.measuremanager.afflatus.bean.SparePartsBean;
import com.soonfor.measuremanager.afflatus.fragment.drawlayout.PartsFragment;
import com.soonfor.measuremanager.afflatus.presenter.SparePartsLibraryPresenter;
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
 * 作者：DC-ZhuSuiBo on 2018/2/20 15:34
 * 邮箱：suibozhu@139.com
 * 配件库
 */

public class SparePartsLibraryFragment extends BaseFragment<SparePartsLibraryPresenter> implements ISwatchLibrariesBaseView {

    Unbinder unbinder;
    @BindView(R.id.rlvpeijian)
    RecyclerView rlvpeijian;
    GridLayoutManager manager;
    List<SparePartsBean> list;
    SparePartsListAdapter adapter;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.drawlayout)
    DrawerLayout dl;
    String brandname="", pid="";

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
        rlvpeijian.setLayoutManager(manager);
        list = new ArrayList<SparePartsBean>();
        adapter = new SparePartsListAdapter(getContext(), list);
        rlvpeijian.setAdapter(adapter);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView text, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.getData("0", edtSearch.getText().toString(), brandname, pid, true);
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
        return R.layout.fragment_sparepartslibrary;
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
        presenter = new SparePartsLibraryPresenter(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getData("0", edtSearch.getText().toString(), brandname, pid, isRefresh);
    }

    /**
     * 更新视图
     *
     * @param returnStr
     */
    public void setListView(String returnStr) {
        mEmptyLayout.hide();
        Gson gson = new Gson();
        List<SparePartsBean> list = gson.fromJson(returnStr, new TypeToken<List<SparePartsBean>>() {
        }.getType());
        if (list != null) {
            this.list = list;
            if (list.size() == 0) {
                mEmptyLayout.show("没有相关数据", "");
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
    public void onMessageEvent(PartsFragment.MessageEvent event) {
        if (event != null) {
            //过滤数据 todo...
            if(event!=null){
                brandname = event.getBrandname();
                pid = event.getPid();
                presenter.getData("0", edtSearch.getText().toString(), brandname, pid, true);
            }

            dl.closeDrawer(Gravity.RIGHT);
        }
    }

    ;

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
        presenter.getData("0", edtSearch.getText().toString(), brandname, pid, true);
        mSwipeRefresh.finishRefresh();
    }

    @Override
    protected void loadmoredata() {
        super.loadmoredata();
        mSwipeRefresh.finishLoadmore();
    }

    /*    private void setDatas(){
        list.add(new SparePartsBean("http://img5.imgtn.bdimg.com/it/u=3634010358,85990829&fm=27&gp=0.jpg","http://img1.imgtn.bdimg.com/it/u=614084213,2952339602&fm=27&gp=0.jpg","美的厨具美的厨具美的厨具美的厨具","家电>厨卫家电","Midea/美的","720mm*420mm*220mm"));
        list.add(new SparePartsBean("http://img5.imgtn.bdimg.com/it/u=3634010358,85990829&fm=27&gp=0.jpg","http://img3.imgtn.bdimg.com/it/u=1381778704,2471411285&fm=200&gp=0.jpg","美的厨具美的厨具美的厨具美的厨具","家电>厨卫家电","Midea/美的","720mm*420mm*220mm"));
        list.add(new SparePartsBean("http://img5.imgtn.bdimg.com/it/u=3634010358,85990829&fm=27&gp=0.jpg","http://img5.imgtn.bdimg.com/it/u=142957858,374005579&fm=27&gp=0.jpg","美的厨具美的厨具美的厨具美的厨具","家电>厨卫家电","Midea/美的","720mm*420mm*220mm"));
        list.add(new SparePartsBean("http://img5.imgtn.bdimg.com/it/u=3634010358,85990829&fm=27&gp=0.jpg","http://img3.imgtn.bdimg.com/it/u=3616506531,616597344&fm=27&gp=0.jpg","美的厨具美的厨具美的厨具美的厨具","家电>厨卫家电","Midea/美的","720mm*420mm*220mm"));
        list.add(new SparePartsBean("http://img5.imgtn.bdimg.com/it/u=3634010358,85990829&fm=27&gp=0.jpg","http://img1.imgtn.bdimg.com/it/u=941901338,3331681403&fm=27&gp=0.jpg","美的厨具美的厨具美的厨具美的厨具","家电>厨卫家电","Midea/美的","720mm*420mm*220mm"));
        list.add(new SparePartsBean("http://img5.imgtn.bdimg.com/it/u=3634010358,85990829&fm=27&gp=0.jpg","http://img3.imgtn.bdimg.com/it/u=275984851,2478135566&fm=200&gp=0.jpg","美的厨具美的厨具美的厨具美的厨具","家电>厨卫家电","Midea/美的","720mm*420mm*220mm"));
        me_evaluate_ranking.notifyDataSetChanged(list);
    }*/
}
