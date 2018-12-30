package com.soonfor.repository.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.soonfor.repository.R;

import com.soonfor.repository.adapter.knowledge.KnowledgeListAdapter;
import com.soonfor.repository.adapter.knowledge.SearchTitleListAdapter;
import com.soonfor.repository.base.RepBaseActivity;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.model.knowledge.SearchTitleBean;
import com.soonfor.repository.presenter.seekhelp.SearchPresenter;
import com.soonfor.repository.tools.FileUtils;
import com.soonfor.repository.tools.MyToast;
import com.soonfor.repository.tools.Tokens;
import com.soonfor.repository.view.seekhelp.ISearchView;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;

/**
 * 作者：DC-DingYG on 2018-06-20 13:45
 * 邮箱：dingyg012655@126.com
 */
public class SearchActivity extends RepBaseActivity<SearchPresenter> implements ISearchView, View.OnClickListener {

    private SearchPresenter presenter;
    EditText etfTitle;
    RecyclerView rcfView;
    GridLayoutManager manager;
    List<SearchTitleBean> titleBeans;
    SearchTitleListAdapter titleAdpter;
    TextView tvfSearch;
    KnowledgeListAdapter klAdapter;
    ArrayList<KnowledgeBean> beans = new ArrayList<>();
    RepPageTurn page = null;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        finishRefresh();
    }
    private void findViewById(){
        etfTitle = this.findViewById(R.id.etfTitle);
        rcfView = this.findViewById(R.id.rcfView);
        tvfSearch = this.findViewById(R.id.tvfSearch);
        tvfSearch.setOnClickListener(this);
    }
    @Override
    protected SearchPresenter initPresenter() {
        QMUIStatusBarHelper.translucent(this);// 沉浸式状态栏
        findViewById();
        presenter = new SearchPresenter(this);
        return presenter;
    }

    @Override
    protected void initViews() {
        QMUIStatusBarHelper.translucent(this);// 沉浸式状态栏
        manager = new GridLayoutManager(this, 1);
        rcfView.setLayoutManager(manager);
        titleBeans = new ArrayList<>();
        titleAdpter = new SearchTitleListAdapter(this, titleBeans);
        rcfView.setAdapter(titleAdpter);
        beans = new ArrayList<>();
        klAdapter = new KnowledgeListAdapter(this, beans, listener, 1);
        mEmptyLayout.show("请输入关键词搜索", "");
        etfTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mEmptyLayout.show(true);
                    presenter.SearchKnowLedge(SearchActivity.this, etfTitle.getText().toString().trim(), 1, 10);
                }
                return false;
            }
        });
        etfTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0) {
                    presenter.SearchTitle(SearchActivity.this, s.toString().trim().toString());
                }else{
                    beans.clear();
                    page = null;
                    titleAdpter = new SearchTitleListAdapter(SearchActivity.this, null);
                    rcfView.setAdapter(titleAdpter);
                    mEmptyLayout.show("请输入关键词搜索", "");
                }
            }
        });
    }

    @Override
    protected void loadmoredata() {
        super.loadmoredata();
        if(rcfView.getAdapter() instanceof  KnowledgeListAdapter){
            if (page != null) {
                if (page.getPageNo() >= page.getNextPage()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MyToast.showToast(SearchActivity.this, "已是最后一页");
                        }
                    }, 1500);
                    finishRefresh();
                } else {
                    presenter.SearchKnowLedge(SearchActivity.this, etfTitle.getText().toString().trim(), page.getNextPage(), 10);
                }
            } else {
                finishRefresh();
            }
        }else {
            finishRefresh();
        }
    }

    @Override
    public void showSearchTitle(boolean isSuccess, List<SearchTitleBean> bean) {
        if (isSuccess) {
            mEmptyLayout.hide();
            titleAdpter = new SearchTitleListAdapter(this, bean);
            rcfView.setAdapter(titleAdpter);
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            KnowledgeBean klBean = (KnowledgeBean) v.getTag(R.id.item_object);
            int i = v.getId();
            if (i == R.id.llfLike) {
                int position = (int) v.getTag(R.id.knowledge_list_key);
                presenter.like(SearchActivity.this, position, klBean.getId());

            } else if (i == R.id.llfShare) {
                PermissionsUtil.requestPermission(SearchActivity.this, new PermissionListener() {
                            @Override
                            public void permissionGranted(@NonNull String[] permissions) {
                                new FileUtils.downloadImageAndShareAsyncTask(SearchActivity.this).execute("欢迎光临数夫家具软件", "分享", "http://www.fdatacraft.com", "");
                            }

                            @Override
                            public void permissionDenied(@NonNull String[] permissions) {
                            }
                        }, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);

            }
        }
    };


    @Override
    public void showSearchKnowLedge(boolean isSuccess, RepPageTurn pageTurn, ArrayList<KnowledgeBean> list) {
        if (isSuccess) {
            if (pageTurn == null || pageTurn.getPageNo() < 0) {//错误
                //showNoDataHint(msg);
                return;
            } else if (pageTurn.getPageNo() == 1) {//刷新
                page = pageTurn;
                beans = list;
            } else {//加载更多
                page = pageTurn;
                beans.addAll(list);
            }
            klAdapter = new KnowledgeListAdapter(this, beans, listener, 1);
            rcfView.setAdapter(klAdapter);
            rcfView.scrollToPosition((pageTurn.getPageNo() - 1) * 10);
            if(page.getPageNo()<=1 &&(beans==null || beans.size()==0)) {
                mSwipeRefresh.setVisibility(View.GONE);
                showNetError("搜索结果为空");
            }else {
                mSwipeRefresh.setVisibility(View.VISIBLE);
                mEmptyLayout.hide();
            }
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etfTitle, InputMethodManager.SHOW_FORCED);
            imm.hideSoftInputFromWindow(etfTitle.getWindowToken(), 0); //强制隐藏键盘
        }else{
            if(page==null || (page.getPageNo()<=1 &&(beans==null || beans.size()==0))) {
                mSwipeRefresh.setVisibility(View.GONE);
                showNetError("");
            }
        }
    }

    /**
     * 请求点赞/取消点赞接口后的操作
     *
     * @param isSuccess
     * @param position
     */
    @Override
    public void setAfterLike(boolean isSuccess, int position) {
        if (isSuccess) {
            if (beans.get(position).getIsLike() == 0) {//点赞成功
                beans.get(position).setIsLike(1);
                int likeCount = Integer.parseInt(beans.get(position).getLikeCount()) + 1;
                beans.get(position).setLikeCount(likeCount + "");
            } else {//取消点赞成功
                beans.get(position).setIsLike(0);
                int likeCount = Integer.parseInt(beans.get(position).getLikeCount()) - 1;
                if (likeCount >= 0) {
                    beans.get(position).setLikeCount(likeCount + "");
                }
            }
            klAdapter.notifyDataSetChanged(beans);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case Tokens.Knowledge.CLICK_TO_DETAIL:
                    presenter.SearchKnowLedge(SearchActivity.this, etfTitle.getText().toString(), 1, 10);
                    tvfSearch.setText("取消");
                    break;
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvfSearch) {
            if (tvfSearch.getText().toString().equals("搜索")) {
                presenter.SearchKnowLedge(SearchActivity.this, etfTitle.getText().toString(), 1, 10);
                tvfSearch.setText("取消");
            } else if (tvfSearch.getText().toString().equals("取消")) {
                klAdapter.notifyDataSetChanged();
                tvfSearch.setText("搜索");
                if(etfTitle.getText().toString().trim().length()>0){
                    presenter.SearchTitle(SearchActivity.this, etfTitle.getText().toString().trim());
                }
            }
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etfTitle, InputMethodManager.SHOW_FORCED);
            imm.hideSoftInputFromWindow(etfTitle.getWindowToken(), 0); //强制隐藏键盘
        }
    }
}
