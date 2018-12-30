package com.soonfor.measuremanager.me.fragment.my_favorite;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.homepage.adapter.topPostsAdpter;
import com.soonfor.measuremanager.home.homepage.model.bean.topPostsEntity;
import com.soonfor.measuremanager.me.presenter.myfavorite.IMyFavoriteView;
import com.soonfor.measuremanager.me.presenter.myfavorite.MyFavoritePresenter;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.view.search.SearchView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class DesignHotpostFragment extends BaseFragment<MyFavoritePresenter> implements IMyFavoriteView{//, SearchView.SearchViewListener{

    Unbinder unbinder;
//    @BindView(R.id.main_search_layout)
//    SearchView mSearchView;
    Activity mActivity;
    @BindView(R.id.recyView)
    SwipeMenuRecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private topPostsAdpter tpostAdpter;
    private List<topPostsEntity> tpostBeans;
    //String InputText = "";
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSwipeRefresh;
    PageTurnBean pageTurnBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        addSlidingMenu();
        tpostBeans = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        tpostAdpter = new topPostsAdpter(mActivity, tpostBeans);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(tpostAdpter);
        //mSearchView.setSearchViewListener(this);
        return rootView;
    }
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_mine_myfavorite;
    }

    @Override
    protected void initPresenter() {
        presenter = new MyFavoritePresenter(this, mActivity);
    }

    @Override
    protected void initViews() {
        mSwipeRefresh.autoRefresh();
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

//    @Override
//    public void onSetHint(EditText etfInput) {
//        etfInput.setHint("请输入关键字搜索");
//    }
//
//    @Override
//    public void onSearch(String text) {
//        if (text == null || text.equals("")) {
//            MyToast.showToast(mActivity, "搜索内容不能为空！");
//        } else {
//            getHotpostList(1, true, text);
//        }
//    }
//
//    @Override
//    public void onChaged(String text) {
//        if (text == null || text.equals("")) {
//            getHotpostList(1, true, "");
//        }
//    }

    @Override
    public void RefreshData(boolean isRefresh) {
        super.RefreshData(isRefresh);
        pageTurnBean = new PageTurnBean(0, 1, 10);
        presenter.getFavroiteDatas(2, pageTurnBean.getPageNo(), 10,"", false);
    }

    @Override
    protected void loadmoredata() {
        if (pageTurnBean!=null && pageTurnBean.getPageCount() > pageTurnBean.getPageNo()) {
            presenter.getFavroiteDatas(2, pageTurnBean.getPageNo(), 10,"", false);
        } else {
            finishRefresh();
            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MyToast.showToast(mActivity, "已是最后一页了！");
                }
            }, 1000);*/
        }
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
    public void setListView(String returnStr) {
        if(returnStr==null){
            showNoDataHint("暂无热贴");
            return;
        }
        String msg = "暂无热贴";
        HeadBean headBean = JsonUtils.getHeadBean(returnStr);
        List<topPostsEntity> newList = new ArrayList<>();
        if (headBean != null) {
            if (headBean.getMsgcode() == 0) {
                if (headBean.getData() != null) {
                    Gson gson = new Gson();
                    try {
                        JSONObject jobject = new JSONObject(headBean.getData());
                        PageTurnBean pageBean = gson.fromJson(jobject.getString("pageTurn"), PageTurnBean.class);
                        if(pageBean!=null) pageTurnBean = pageBean;
                        JSONArray ja = jobject.getJSONArray("list");
                        if (ja != null && ja.length() > 0) {
                            for(int i=0; i<ja.length(); i++) {
                                topPostsEntity caseBean = new Gson().fromJson(ja.get(i).toString(), topPostsEntity.class);
                                newList.add(caseBean);
                            }
                        }
                    } catch (Exception e) {
                        msg = e.toString();
                    }
                } else {
                    msg = headBean.getMsg();
                }
            } else {
                msg = headBean.getFaileMsg();
            }
        }
        tpostBeans.addAll(newList);
        if (tpostBeans.size() > 0) {
            showDataToView(null);
        } else {
            showNoDataHint("暂无热贴");
            if(!msg.equals("暂无热贴")){
                MyToast.showFailToast(mActivity, msg);
            }
        }
        tpostAdpter.notifyDataSetChanged(tpostBeans);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    /**
     * 添加侧滑菜单
     */
    private void addSlidingMenu() {
        // 设置监听器。创建菜单
        mRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mActivity);
                // 各种文字和图标属性设置。
                deleteItem.setBackground(R.drawable.view_swipdelete_2);
                deleteItem.setText("删除");
                deleteItem.setTextSize(15);
                deleteItem.setTextColor(Color.parseColor("#ffffff"));
                deleteItem.setWidth(120);
                deleteItem.setHeight(MATCH_PARENT);
                swipeRightMenu.addMenuItem(deleteItem); // 在Item右侧添加一个菜单。
                // 注意：哪边不想要菜单，那么不要添加即可。
            }
        });
        // 菜单点击监听。
        mRecyclerView.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();
                //int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                final int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                //int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                tpostBeans.remove(adapterPosition);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tpostAdpter.notifyDataSetChanged(tpostBeans);
                        if(tpostBeans.size()>0) {
                            if (adapterPosition >= 0) {
                                tpostAdpter.MoveToPosition(mLayoutManager, mRecyclerView, adapterPosition);
                            }
                        }else {
                            showNoDataHint("暂无热帖");
                        }
                    }
                },500);
            }
        });
        mRecyclerView.setLongPressDragEnabled(false); // 拖拽排序，默认关闭。
        mRecyclerView.setItemViewSwipeEnabled(false); // 侧滑删除，默认关闭。
    }

}
