package com.soonfor.repository.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.repository.R;

import com.soonfor.repository.adapter.SeekhelpListAdapter;
import com.soonfor.repository.base.RepBaseFragment;
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.presenter.seekhelp.SeekhelpPresenter;
import com.soonfor.repository.ui.activity.SearchActivity;
import com.soonfor.repository.ui.activity.staff.OnlineStaffActivity;
import com.soonfor.repository.view.seekhelp.ISeekhelpView;

import java.util.List;


/**
 * 作者：DC-DingYG on 2018-06-20 11:03
 * 邮箱：dingyg012655@126.com
 */

/**
 * 修改人：DC-ZhuSuiBo on 2018/7/31 0031 10:42
 * 邮箱：suibozhu@139.com
 * 修改目的：完善功能
 */
public class SeekhelpFragment extends RepBaseFragment<SeekhelpPresenter>
        implements ISeekhelpView, View.OnClickListener {

    LinearLayout llfSearch;
    LinearLayout llfOnlineService;

    RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private SeekhelpListAdapter slAdapter;

    private void findViewById(View view){
        llfSearch = view.findViewById(R.id.llfSearch);
        llfOnlineService = view.findViewById(R.id.llfOnlineService);
        mRecyclerView = view.findViewById(R.id.rcfView);
    }
    @Override
    protected void initPresenter() {
        presenter = new SeekhelpPresenter(this);
    }

    @Override
    protected void initViews() {
        mLayoutManager = new GridLayoutManager(mActivity, 1);
        slAdapter = new SeekhelpListAdapter(mActivity);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(slAdapter);
        llfSearch.setOnClickListener(this);
        llfOnlineService.setOnClickListener(this);
        updateViews(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_seekhelp, container, false);
        BasefindViewById(rootView);
        findViewById(rootView);
        return rootView;
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        if (presenter != null && mActivity != null) {
            presenter.getPersonalInfo(mActivity, isRefresh);
        }
    }

    @Override
    public void fetchData() {
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.llfSearch) {
            startActivity(new Intent(mActivity, SearchActivity.class));

        } else if (i == R.id.llfOnlineService) {
            startActivity(new Intent(mActivity, OnlineStaffActivity.class));

        }
    }

    /**
     * 修改人：DC-ZhuSuiBo on 2018/7/31 0031 10:32
     * 邮箱：suibozhu@139.com
     * 修改目的：
     */
    @Override
    public void showDataToView(String returnJson) {
        super.showDataToView(returnJson);
        if (mRecyclerView != null && mRecyclerView.getVisibility()!=View.VISIBLE){
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        Gson gson = new Gson();
        final List<KnowledgeBean> kBeans = gson.fromJson(returnJson, new TypeToken<List<KnowledgeBean>>() {
        }.getType());
        if (kBeans.size() > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    slAdapter.notifyDataSetChanged(kBeans);
                }
            },1000);
        }
    }

    @Override
    public void showNetError(String msg) {
        super.showNetError(msg);
        if (mRecyclerView != null)
            mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void loadmoredata() {
        super.loadmoredata();
        finishRefresh();
    }
}
