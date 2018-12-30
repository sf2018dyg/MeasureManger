package com.soonfor.evaluate.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.soonfor.measuremanager.R;
import com.soonfor.evaluate.adapter.RetrunVisitAdapter;
import com.soonfor.evaluate.bean.ReturnVisitBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：DC-DingYG on 2018-10-17 13:57
 * 邮箱：dingyg012655@126.com
 *
 */
public class EvaluateReturnVisitView extends LinearLayout {
    private Context mContext;
    private boolean isCanEdit;//是否可编辑
    @BindView(R.id.rvfQuestionnaire)
    RecyclerView mRecyclerView;
    private LinearLayoutManager manager;
    private RetrunVisitAdapter rvistitAdapter;
    private List<ReturnVisitBean> visitList;
    private TotalPoints tp;
    public EvaluateReturnVisitView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final View view = View.inflate(context, R.layout.view_evaluattome_returnvisit, this);
        ButterKnife.bind(this, view);
    }

    //初始化
    public void initEvaluateReturnVisitView(final Context mContext, boolean isCanEdit,
                                            final List<ReturnVisitBean> visitBeanList, TotalPoints tp) {
        this.mContext = mContext;
        this.isCanEdit = isCanEdit;
        this.tp = tp;
        manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvistitAdapter = new RetrunVisitAdapter(mContext, isCanEdit, new RetrunVisitAdapter.RSoroceListerner() {
            @Override
            public void getTotalPoints(boolean isRefreshRecyvlerView) {
                refreshView(rvistitAdapter.getRvlist(),isRefreshRecyvlerView);
            }
        });
        // 设置布局管理器
        mRecyclerView.setLayoutManager(manager);
        // 设置adapter
        mRecyclerView.setAdapter(rvistitAdapter);
        mRecyclerView.setSaveEnabled(false);
        refreshView(visitBeanList, true);
    }

    public void refreshView(List<ReturnVisitBean> visitBeanList, boolean isNeedRefresh){
        int Total_Scores = 0;
        if (visitBeanList != null && visitBeanList.size()>0) {
            for(int i=0; i< visitBeanList.size(); i++){
                Total_Scores += visitBeanList.get(i).getFactpoint();
            }
            if(isNeedRefresh) {
                rvistitAdapter.notifyDataSetChanged(visitBeanList);
            }
        }
        if(tp!=null) {
            tp.setTotalPoints(Total_Scores, visitBeanList);
        }
    }

    public interface TotalPoints{
        void setTotalPoints(int scores, List<ReturnVisitBean> result);
    }
}
