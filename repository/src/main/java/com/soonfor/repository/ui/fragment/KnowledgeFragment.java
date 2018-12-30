package com.soonfor.repository.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.soonfor.repository.R;

import com.soonfor.repository.adapter.FragmentAdapter;
import com.soonfor.repository.base.RepBaseFragment;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.model.knowledge.CategoryBean;
import com.soonfor.repository.presenter.knowledge.KnowledgePresenter;
import com.soonfor.repository.tools.DataTools;
import com.soonfor.repository.tools.floatactionbutton.DraggableFloatingButton;
import com.soonfor.repository.tools.popupwindow.SelectClassifyPopupWindow;
import com.soonfor.repository.tools.tablayout.NoScrollViewPager;
import com.soonfor.repository.ui.activity.knowledge.AddKnowledgeActivity;
import com.soonfor.repository.ui.fragment.knowledge.BaseKnowledgeFragment;
import com.soonfor.repository.view.knowledge.IKnowledgeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：DC-DingYG on 2018-06-14 15:08
 * 邮箱：dingyg012655@126.com
 */
public class KnowledgeFragment extends RepBaseFragment<KnowledgePresenter>
        implements IKnowledgeView, View.OnClickListener, SelectClassifyPopupWindow.refresh {

    ImageView imgfOpen;//展示二级菜单
    TabLayout tablayout;
    NoScrollViewPager viewPager;
    RelativeLayout rlfTablayout;
    ImageView imgfCover;
    DraggableFloatingButton fab;
    LinearLayout llfData;
    FrameLayout flFragment;

    FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    List<BaseKnowledgeFragment> mFragments;
    List<CategoryBean> mTitles;
    public static CategoryBean fType;//当前一级菜单选中项id
    public static CategoryBean sType;//当前二级菜单选中项
    public static boolean isSelecFirstLevlByClick = false;//（在分类popupwindow中）是否通过点击选中了一级分类

    static SelectClassifyPopupWindow scPopupWindow;
    static FragmentActivity fragmentActivity;

    public static boolean isNeedRfresh = true;
    public boolean isSelectToViewPageChange = false;//viewpager的切换变化是否是通过选择类别popupwindow上的分类条目的

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentActivity = getActivity();
    }

    @Override
    protected void initPresenter() {
        presenter = new KnowledgePresenter(this);
    }

    @Override
    protected void initViews() {
        presenter.getTabTitles(mActivity, true);
        imgfOpen.setOnClickListener(this);
        fab.setOnClickListener(this);
    }
    private void findViewById(View view){
        imgfOpen = view.findViewById(R.id.imgfState);
        tablayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewPager);
        rlfTablayout = view.findViewById(R.id.rlfTablayout);
        imgfCover = view.findViewById(R.id.imgfCover);
        fab = view.findViewById(R.id.fab);
        llfData = view.findViewById(R.id.llfData);
        flFragment = view.findViewById(R.id.flFragment);
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_knowledge, container, false);
        BasefindViewById(rootView);
        findViewById(rootView);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (isSelectToViewPageChange) {
                    if (position == 0) {
                        presenter.getHotList(fragmentActivity, 1, true);
                    } else {
                        presenter.getDatas(fragmentActivity, sType.getIds(), 1, false);
                    }
                    isSelectToViewPageChange = false;
                } else {
                    getCurrId(sType, position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return rootView;
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        if(llfData!=null && llfData.getVisibility() != View.VISIBLE){
            llfData.setVisibility(View.VISIBLE);
        }
        getCurrId(sType, viewPager.getCurrentItem());
    }

    @Override
    public void fetchData() {
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.imgfState) {
            if (DataTools.fTypes == null) {
                //presenter.getTabTitles();
                return;
            }
            if (scPopupWindow != null) {
                scPopupWindow.dismiss();
            }
            scPopupWindow = new SelectClassifyPopupWindow(mActivity, imgfCover, this);
            scPopupWindow.showPopupWindow(rlfTablayout, mActivity);

        } else if (i == R.id.fab) {
            Intent intent = new Intent(mActivity, AddKnowledgeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mActivity.startActivity(intent);

        }
    }

    /**
     * 选择分类后刷新
     */
    @Override
    public void refreshLayout() {
        if (scPopupWindow != null) {
            scPopupWindow.dismiss();
        }
        isSelectToViewPageChange = true;
        initTabLout(false);
    }

    @Override
    public void setGetCategory(boolean isSuccess, String msg) {
        if (isSuccess) {
            initTabLout(true);
        } else {
            showNoDataHint(msg);
        }
    }

    /**
     * @param isFirst 是否首次初始化Tablout
     */
    public void initTabLout(boolean isFirst) {
        int currentIndex = 0;//当前位置
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
        if (DataTools.pageKlMap != null) {
            DataTools.pageKlMap.clear();
        }
        if (DataTools.ListKlMap != null) {
            DataTools.ListKlMap.clear();
        }
        if (fType == null) {
            fType = new CategoryBean("hot", "热门");
        }
        if (sType == null) {
            sType = new CategoryBean("hot", "热门", "hot", "热门");
        }
        if (sType.getId().equals("all") || (sType.getId().equals("hot") && sType.getParentId().equals("hot"))) {//tab为一级菜单
            for (int i = 0; i < DataTools.fTypes.size(); i++) {
                mTitles.add(DataTools.fTypes.get(i));
                BaseKnowledgeFragment bFragment = new BaseKnowledgeFragment();
                bFragment.setType(i);
                mFragments.add(bFragment);
                if (fType.getId().equals(DataTools.fTypes.get(i).getId())) {
                    currentIndex = i;
                }
            }
        } else {
            mTitles.add(new CategoryBean("hot", "热门", sType.getParentId(), sType.getParentName()));
            BaseKnowledgeFragment bFragment0 = new BaseKnowledgeFragment();
            bFragment0.setType(0);
            mFragments.add(bFragment0);

            List<CategoryBean> zList = null;
            if (fType.getId().equals("hot")) {
                zList = DataTools.sTypes.get(sType.getParentId());
                for (int i = 1; i < zList.size(); i++) {
                    mTitles.add(zList.get(i));
                    BaseKnowledgeFragment bFragment = new BaseKnowledgeFragment();
                    bFragment.setType(i);
                    mFragments.add(bFragment);
                }
            } else {
                zList = DataTools.sTypes.get(fType.getId());
                for (int i = 1; i < zList.size(); i++) {
                    mTitles.add(zList.get(i));
                    BaseKnowledgeFragment bFragment = new BaseKnowledgeFragment();
                    bFragment.setType(i);
                    mFragments.add(bFragment);
                    if (sType.getId().equals(zList.get(i).getId())) {
                        currentIndex = i;
                    }
                }
            }
        }
        DataTools.refreshBoolean.clear();
        adapter = new FragmentAdapter(getChildFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        tablayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
        if (currentIndex > 0) {
            viewPager.setCurrentItem(currentIndex);
        } else {
            presenter.getHotList(fragmentActivity, 1, false);
            isSelectToViewPageChange = false;
        }
        if(mTitles==null || mTitles.size()==0){
            showNoDataHint("");
        }
    }

    /**
     * 通过位置获取当前一二级分类
     */
    public void getCurrId(CategoryBean lastType, int position) {
        if (sType == null) {
            sType = new CategoryBean("hot", "热门", "hot", "热门");
        }
        if (lastType.getId().equals("all") || (lastType.getId().equals("hot") && lastType.getParentId().equals("hot"))) {//说明选中前界面显示的是一级分类
            fType = DataTools.fTypes.get(position);
            sType = DataTools.sTypes.get(fType.getId()).get(0);
        } else {
            if (position == 0) {//热门
                fType = new CategoryBean("hot", "热门");
                sType = new CategoryBean("hot", "热门", lastType.getParentId(), lastType.getParentName());
            } else {
                fType = new CategoryBean(lastType.getParentId(), lastType.getParentName());
                List<CategoryBean> cbList = DataTools.sTypes.get(lastType.getParentId());
                if(cbList!=null){
                    if(cbList.size() > position) {
                        sType = cbList.get(position);
                    }else {
                        sType = cbList.get(0);
                    }
                }
            }
        }
        if (position == 0) {
            presenter.getHotList(fragmentActivity, 1, true);
        } else {
            presenter.getDatas(fragmentActivity, sType.getIds(), 1, false);
        }
    }

    @Override
    public void setGetHotList(boolean isSuccess, RepPageTurn pageTurn, ArrayList<KnowledgeBean> hotBeans, String msg) {
        DataTools.hotPage = pageTurn;
        DataTools.hotList = hotBeans;
        showFragmentList(isSuccess, hotBeans, msg);
    }

    @Override
    public void setGetHnowledwList(boolean isSuccess, RepPageTurn pageTurn, ArrayList<KnowledgeBean> kBeans, String msg) {
        if (DataTools.pageKlMap == null) {
            DataTools.pageKlMap = new HashMap<>();
        }
        if (DataTools.ListKlMap == null) {
            DataTools.ListKlMap = new HashMap<>();
        }
        String key = DataTools.getMapKey(sType);
        DataTools.pageKlMap.put(key, pageTurn);
        DataTools.ListKlMap.put(key, kBeans);
        showFragmentList(isSuccess, kBeans, msg);
    }

    @Override
    public void setAfterLike(boolean isSuccess, int position) {}

    @Override
    public void showNoDataHint(String msg) {
        super.showNoDataHint(msg);
//        if(msg!=null && !msg.equals(""))
//            MyToast.showFailToast(mActivity, msg);
        showNetError(msg);
    }
    @Override
    public void showNetError(String msg) {
        super.showNetError(msg);
        if (llfData != null)
            llfData.setVisibility(View.GONE);
    }
    private void showFragmentList(boolean isSuccess, ArrayList<KnowledgeBean> beans, String errmsg) {
        if (flFragment != null && flFragment.getVisibility()!=View.VISIBLE) flFragment.setVisibility(View.VISIBLE);
        Refreshhandler refreshHandler = new Refreshhandler(mActivity);
        Bundle bundle = new Bundle();
        bundle.putBoolean("ISSUCCESS", isSuccess);
        bundle.putParcelableArrayList("KNOWLEDGES", beans);
        bundle.putString("ERRMSG", errmsg);
        Message msg = new Message();
        msg.setData(bundle);
        refreshHandler.sendMessage(msg);
    }

    public class Refreshhandler extends Handler {

        Activity mContext;

        public Refreshhandler(Activity mContext) {
            this.mContext = mContext;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                Bundle bundle = msg.getData();
                final boolean isSuccess = bundle.getBoolean("ISSUCCESS", false);
                final ArrayList<KnowledgeBean> beanList = bundle.getParcelableArrayList("KNOWLEDGES");
                final String errMsg = bundle.getString("ERRMSG", "");
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * 此处截的设置pagecount
                         */
                        mFragments.get(viewPager.getCurrentItem()).setListView(isSuccess, beanList, errMsg);
                    }
                });
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }
    }
}