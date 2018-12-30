package com.soonfor.evaluate.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.soonfor.measuremanager.R;
import com.soonfor.evaluate.adapter.EvaluateLevlEditAdapter;
import com.soonfor.evaluate.adapter.EvaluateLevlShowAdapter;
import com.soonfor.evaluate.bean.AppResSortItemDto;
import com.soonfor.evaluate.bean.EvaluateTemplateBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: DC-DingYG on 2018-11-15 14:18
 * e-mail: dingyg012655@126.com
 */
public class EvaluateLevlView extends LinearLayout {

    private Context mContext;
    @BindView(R.id.rvfEvalLevls)
    RecyclerView rvfEvalLevls;

    private LinearLayoutManager showManager;
    private EvaluateLevlShowAdapter showLevlAdapter;

    private LinearLayoutManager editManager;
    private EvaluateLevlEditAdapter editLevlAdapter;

    public EvaluateLevlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.view_evaluatelevl, this);
        ButterKnife.bind(this, view);
    }

    /**
     *  editable
     * @param mContext
     * @param templateItemDtos
     */
    public void initEvaluateLevlList(final Activity mContext, List<EvaluateTemplateBean.TemplateItemDto> templateItemDtos,
                                     EvaluateLevlEditAdapter.DescListenner descListenner){
        this.mContext =mContext;
        editManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvfEvalLevls.setLayoutManager(editManager);
        editLevlAdapter = new EvaluateLevlEditAdapter(mContext, templateItemDtos, descListenner);
        rvfEvalLevls.setAdapter(editLevlAdapter);
    }

    /**
     *  not editable
     * @param mContext
     * @param AppResSortItemDtos
     */
    public void initEvaluateLevlList(final Activity mContext, List<AppResSortItemDto> AppResSortItemDtos){
        this.mContext =mContext;
        showManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvfEvalLevls.setLayoutManager(showManager);
        showLevlAdapter = new EvaluateLevlShowAdapter(mContext, AppResSortItemDtos);
        rvfEvalLevls.setAdapter(showLevlAdapter);
    }

}
