package com.soonfor.measuremanager.me.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.me.adapter.MyAttentionAdapter;
import com.soonfor.measuremanager.me.bean.PersonDataBean;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MyAttentionActivity extends BaseActivity<MyFavoritePresenter> implements IMyFavoriteView, SearchView.SearchViewListener {

    @BindView(R.id.main_search_layout)
    SearchView mSearchView;
    Activity mActivity;
    @BindView(R.id.recyView)
    SwipeMenuRecyclerView mRecyclerView;
    private MyAttentionAdapter maAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<PersonDataBean> pdList;
    String InputText = "";
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSwipeRefresh;
    PageTurnBean pageTurnBean;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_mine_myattention;
    }

    @Override
    protected void initPresenter() {
        mActivity = MyAttentionActivity.this;
        presenter = new MyFavoritePresenter(this, mActivity);
    }

    @Override
    protected void initViews() {
        mSearchView.setSearchViewListener(this);
        addSlidingMenu();
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        maAdapter = new MyAttentionAdapter(mActivity,pdList);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(maAdapter);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        getAttentionList(1, false, "");
    }

    @Override
    public void onSetHint(EditText etfInput) {
        etfInput.setHint("请输入关键字搜索");
    }

    @Override
    public void onSearch(String text) {
        if (text == null || text.equals("")) {
            MyToast.showToast(mActivity, "搜索内容不能为空！");
        } else {
            InputText = text;
            getAttentionList(1, true, InputText);
        }
    }

    @Override
    public void onChaged(String text) {
        if (text == null || text.equals("")) {
            getAttentionList(1, true, "");
        }
    }

    private void getAttentionList(int pageNo, boolean isRefresh, String seaText) {
        InputText = seaText;
        if(pageNo==1){
            pdList = new ArrayList<>();
        }
        presenter.getAttentionDatas(pageNo, seaText, isRefresh);
    }

    @Override
    public void RefreshData(boolean isRefresh) {
        super.RefreshData(isRefresh);
        pageTurnBean = new PageTurnBean(0, 1, 10);
        getAttentionList(pageTurnBean.getPageNo(), true, "");
    }

    @Override
    protected void loadmoredata() {
        if (pageTurnBean!=null && pageTurnBean.getPageCount() > pageTurnBean.getPageNo()) {
            getAttentionList(pageTurnBean.getPageNo() + 1, false, "");
        } else {
            finishRefresh();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MyToast.showToast(mActivity, "已是最后一页了！");
                }
            }, 1000);
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
        HeadBean headBean = JsonUtils.getHeadBean(returnStr);
        List<PersonDataBean> newList = new ArrayList<>();
        String msg = "暂无关注";
        if (headBean != null) {
            if (headBean.getMsgcode() == 0) {
                if (headBean.getData() != null) {
                    try {
                        JSONArray ja = new JSONArray(headBean.getData());
                        if (ja != null && ja.length() > 0) {
                            for(int i=0; i<ja.length(); i++) {
                                PersonDataBean pdBean = new Gson().fromJson(ja.get(i).toString(), PersonDataBean.class);
                                /**
                                 * 本地过滤 记得删除
                                 */
                                if(!InputText.equals("")){
                                    if(pdBean.getNickName().toLowerCase().contains(InputText.toLowerCase()))
                                        newList.add(pdBean);
                                }else{
                                    newList.add(pdBean);
                                }

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

        pdList.addAll(newList);
        if (pdList.size() > 0) {
            showDataToView(null);
        } else {
            showNoDataHint("暂无关注");
            if(!msg.equals("暂无关注")){
                MyToast.showFailToast(mActivity, msg);
            }
        }
        maAdapter.notifyDataSetChanged(pdList);
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
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                final int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                pdList.remove(adapterPosition);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        maAdapter.notifyDataSetChanged(pdList);
                        if(adapterPosition>=0) {
                            maAdapter.MoveToPosition(mLayoutManager, mRecyclerView, adapterPosition);
                        }
                    }
                },500);
            }
        });
        mRecyclerView.setLongPressDragEnabled(true); // 拖拽排序，默认关闭。
        mRecyclerView.setItemViewSwipeEnabled(false); // 侧滑删除，默认关闭。
    }
}
