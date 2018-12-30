package com.soonfor.repository.ui.activity.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.soonfor.repository.R;

import com.soonfor.repository.adapter.knowledge.MyAddedKnowLedgeListAdapter;
import com.soonfor.repository.base.RepBaseActivity;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.MyAddedKnowLedgeBean;
import com.soonfor.repository.presenter.personal.MyAddedKnowLedgePresenter;
import com.soonfor.repository.tools.MyToast;
import com.soonfor.repository.tools.Tokens;
import com.soonfor.repository.view.personal.IMyAddedKnowLedgeView;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/3 0003 10:46
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class MyAddedKnowLedgeActivity extends RepBaseActivity<MyAddedKnowLedgePresenter> implements IMyAddedKnowLedgeView {

    MyAddedKnowLedgePresenter presenter;
    TextView tvfTitile;
    MyAddedKnowLedgeListAdapter adapter;
    GridLayoutManager manager;
    public static List<MyAddedKnowLedgeBean> list;
    RecyclerView recyView;
    RepPageTurn pageTurn;//当前页
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_added_know_ledge;
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getMyAddedKnowLedge(this, 1, 10);
    }
    private void findViewById() {
        tvfTitile = this.findViewById(R.id.tvfTitile);
        recyView = this.findViewById(R.id.recyView);
    }
    @Override
    protected MyAddedKnowLedgePresenter initPresenter() {
        findViewById();
        presenter = new MyAddedKnowLedgePresenter(this);
        return presenter;
    }

    @Override
    protected void initViews() {
        mEmptyLayout.show(true);
        tvfTitile.setText("我添加的知识");
        manager = new GridLayoutManager(this, 1);
        recyView.setLayoutManager(manager);
        list = new ArrayList<>();
        adapter = new MyAddedKnowLedgeListAdapter(this, list);
        recyView.setAdapter(adapter);
        updateViews(false);
    }

    @Override
    public void setDatas(RepPageTurn pageTurn, List<MyAddedKnowLedgeBean> beans) {
        try {
            if (pageTurn != null) {
                this.pageTurn = pageTurn;
            }
            if (pageTurn.getPageNo() < 0) {//错误
            } else if (pageTurn.getPageNo() == 1) {//刷新
                list = beans;
            } else {//加载更多
                if(list==null){list = new ArrayList<>();}
                list.addAll(beans);
            }
            mEmptyLayout.hide();
            finishRefresh();
            if (list != null && list.size() > 0) {
                //tvfTitile.setText("我添加的知识 (共" + list.size() + "条)");
                adapter.notifyDataSetChanged(beans);
            } else {
                tvfTitile.setText("我添加的知识");
                if(pageTurn.getPageNo() == 1)
                    mEmptyLayout.show("暂无添加的知识", "");
            }
        }catch (Exception e){}
    }

    @Override
    public void showNetError(String msg) {
        super.showNetError(msg);
        if(recyView!=null){
            recyView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void loadmoredata() {
        super.loadmoredata();
        if (pageTurn != null){
           if(pageTurn.getPageNo() >= pageTurn.getNextPage()){
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       MyToast.showToast(MyAddedKnowLedgeActivity.this, "已是最后一页");
                   }
               }, 1500);
               finishRefresh();
           } else {
               presenter.getMyAddedKnowLedge(this, pageTurn.getNextPage(), 10);
           }
        } else {
            finishRefresh();
        }
    }

    @Override
    protected void finishByBack() {
        super.finishByBack();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case Tokens.Knowledge.CLICK_TO_DETAIL:

                    break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

}
