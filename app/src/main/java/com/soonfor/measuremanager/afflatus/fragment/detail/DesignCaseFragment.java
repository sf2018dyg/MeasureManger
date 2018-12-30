package com.soonfor.measuremanager.afflatus.fragment.detail;

import android.content.Intent;
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
import com.soonfor.measuremanager.afflatus.activity.DesignCaseDetailActivity;
import com.soonfor.measuremanager.afflatus.adpter.DesignCaseListAdapter;
import com.soonfor.measuremanager.afflatus.bean.DesignCaseBean;
import com.soonfor.measuremanager.afflatus.bean.postBean.PostCaseBean;
import com.soonfor.measuremanager.afflatus.fragment.drawlayout.DesignDrawLayoutFragment;
import com.soonfor.measuremanager.afflatus.presenter.DesignCaseBasePresenter;
import com.soonfor.measuremanager.afflatus.view.IDesignCaseBaseView;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.view.FlycoTabLayout_Lib.CommonTabLayout;
import com.soonfor.measuremanager.view.FlycoTabLayout_Lib.entity.TabEntity;
import com.soonfor.measuremanager.view.FlycoTabLayout_Lib.listener.CustomTabEntity;
import com.soonfor.measuremanager.view.FlycoTabLayout_Lib.listener.OnTabSelectListener;

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
 * 作者：DC-ZhuSuiBo on 2018/2/20 15:48
 * 邮箱：suibozhu@139.com
 * 案例库
 */

public class DesignCaseFragment extends BaseFragment<DesignCaseBasePresenter> implements IDesignCaseBaseView {

    Unbinder unbinder;
    @BindView(R.id.tl_1)
    CommonTabLayout tl_1;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"最新", "人气", "点赞", "收藏"};
    @BindView(R.id.rvListAnli)
    RecyclerView rvListAnli;
    GridLayoutManager manager;
    DesignCaseListAdapter adapter;
    List<DesignCaseBean> datas;
    PostCaseBean post;
    String postType = "0";
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.drawlayout)
    DrawerLayout dl;

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

        initMainHead();
        initMainBody();

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView text, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    post.setKeyword(edtSearch.getText().toString());
                    presenter.getData(postType, post, true);
                }
                return false;
            }
        });

        return rootView;
    }


    private void initMainHead() {
        if(mTabEntities==null){
            mTabEntities = new ArrayList<>();
        }else if(mTabEntities.size()>0){
            mTabEntities.clear();
        }
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], R.mipmap.icon_quanjing, R.mipmap.icon_quanjing));
        }
        tl_1.setTabData(mTabEntities);
        tl_1.setCurrentTab(0);
        tl_1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        postType = "0";
                        presenter.getData(postType, post, true);
                        break;
                    case 1:
                        postType = "1";
                        presenter.getData(postType, post, true);
                        break;
                    case 2:
                        postType = "2";
                        presenter.getData(postType, post, true);
                        break;
                    case 3:
                        postType = "3";
                        presenter.getData(postType, post, true);
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void initMainBody() {
        manager = new GridLayoutManager(getContext(), 1);
        rvListAnli.setLayoutManager(manager);
        datas = new ArrayList<DesignCaseBean>();
        adapter = new DesignCaseListAdapter(getContext(), datas, listener);
        rvListAnli.setAdapter(adapter);
    }


    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_designcase;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        post = new PostCaseBean();
        mEmptyLayout.show(true);
    }

    @Override
    protected void initPresenter() {
        presenter = new DesignCaseBasePresenter(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getData(postType, post, isRefresh);
    }

    /**
     * 更新视图
     *
     * @param returnStr
     */
    public void setListView(String returnStr) {
        mEmptyLayout.hide();
        Gson gson = new Gson();
        List<DesignCaseBean> list = gson.fromJson(returnStr, new TypeToken<List<DesignCaseBean>>() {
        }.getType());
        if (list != null) {
            datas = list;
            if (datas.size() == 0) {
                mEmptyLayout.show("没有相关数据", "");
            }
            adapter.notifyDataSetChanged(datas);
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
    public void onMessageEvent(DesignDrawLayoutFragment.MessageEvent event) {
        if (event != null) {
            //过滤数据 todo...
            //栏目的数组
            post.setSceneId(event.getAdpter1().getSelected() + "");
            //组建亮点的数组
            List<String> ldlt = new ArrayList<>();
            for (int i = 0; i < event.getAdpter2().getSelecteds().length; i++) {
                if (event.getAdpter2().getSelecteds()[i]) {
                    ldlt.add(event.getLt2().get(i).getId());
                }
            }
            post.setMeritsIdList(ldlt);
            //组建风格的数组
            List<String> fglt = new ArrayList<>();
            for (int i = 0; i < event.getAdpter3().getSelecteds().length; i++) {
                if (event.getAdpter3().getSelecteds()[i]) {
                    fglt.add(event.getLt3().get(i).getId());
                }
            }
            post.setStyleIdList(fglt);
            //组建面积的数组
            List<String> mjlt = new ArrayList<>();
            for (int i = 0; i < event.getAdpter4().getSelecteds().length; i++) {
                if (event.getAdpter4().getSelecteds()[i]) {
                    mjlt.add(event.getLt4().get(i).getId());
                }
            }
            post.setAreaIdList(mjlt);
            //组建户型的数组
            List<String> hxlt = new ArrayList<>();
            for (int i = 0; i < event.getAdpter5().getSelecteds().length; i++) {
                if (event.getAdpter5().getSelecteds()[i]) {
                    hxlt.add(event.getLt5().get(i).getId());
                }
            }
            post.setHuxingIdList(hxlt);
            //组建价格的数组
            List<String> jglt = new ArrayList<>();
            for (int i = 0; i < event.getAdpter6().getSelecteds().length; i++) {
                if (event.getAdpter6().getSelecteds()[i]) {
                    jglt.add(event.getLt6().get(i).getId());
                }
            }
            post.setPriceIdList(jglt);

            presenter.getData(postType, post, true);

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
        presenter.getData(postType, post, isRefresh);
        mSwipeRefresh.finishRefresh();
    }

    @Override
    protected void loadmoredata() {
        super.loadmoredata();
        mSwipeRefresh.finishLoadmore();
    }

    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rlfitem:
                    DesignCaseBean db = (DesignCaseBean) v.getTag();
                    Intent intent = new Intent(getContext(), DesignCaseDetailActivity.class);
                    intent.putExtra("DesignCaseBean", db);
                    startActivity(intent);
                    break;
                case R.id.readingVolumetxt:
                    MyToast.showToast(getContext(), "阅读量..");
                    break;
                case R.id.replyVolumetxt:
                    MyToast.showToast(getContext(), "回复数量..");
                    break;
                case R.id.thumbsupVolumetxt:
                    DesignCaseBean bean = (DesignCaseBean)v.getTag();
                    if(bean!=null){
                        presenter.saveLike(bean.getId());
                    }
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        RefreshData(true);
    }
}
