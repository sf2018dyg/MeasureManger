package com.soonfor.evaluate.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.AttachDto;
import com.soonfor.evaluate.bean.Evaluate_CustomersToMeBean;
import com.soonfor.evaluate.bean.Evaluate_CustomersToMeDetailBean;
import com.soonfor.evaluate.bean.RequestTemplateDto;
import com.soonfor.evaluate.bean.ReturnVisitBean;
import com.soonfor.evaluate.presenter.Evaluate_CustomerToMeDetailPresenter;
import com.soonfor.evaluate.view.EvaluateDetailView;
import com.soonfor.evaluate.view.EvaluateReturnVisitView;
import com.soonfor.measuremanager.other.adapter.PhotoAdapter;
import com.soonfor.measuremanager.other.adapter.layoutmanager.FullyGridLayoutManager;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.NoDoubleClickUtils;
import com.soonfor.updateutil.ShowPicActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-DingYG on 2018-10-17 19:47
 * 邮箱：dingyg012655@126.com
 */
public class Evaluate_CustomersToMeDetailActivity extends BaseActivity<Evaluate_CustomerToMeDetailPresenter>{

    private Activity mActivity;
    private final int REQUEST_CODE_CTM = 1011;

    @BindView(R.id.tvfLeftTilte)
    TextView tvfLeftTilte;
    @BindView(R.id.tvfRightTilte)
    TextView tvfRightTilte;
    @BindView(R.id.imgfLeftVernier)
    ImageView imgfLeftVernier;
    @BindView(R.id.imgfRightVernier)
    ImageView imgfRightVernier;

    @BindView(R.id.edView)
    EvaluateDetailView edView;

    @BindView(R.id.llfTotalP)
    LinearLayout llfTotalPoints;
    @BindView(R.id.tvfTotalPoints)
    TextView tvfTotalPoints;
    @BindView(R.id.ervView)
    EvaluateReturnVisitView ervView;

    @BindView(R.id.tvfToEvaluateClient)
    TextView tvfToEvaluateClient;

    @BindView(R.id.tvfReplyTime)
    TextView tvfReplyTime;
    @BindView(R.id.tvfReplyContent)
    TextView tvfReplyContent;
    @BindView(R.id.rvfReplyPics)
    RecyclerView rvfReplyPics;

    @BindView(R.id.rlfReply)
    RelativeLayout rlfReplay;
    private GridLayoutManager manager;
    private PhotoAdapter adapter;
    private int ItemPositon;
    private Evaluate_CustomersToMeBean ectBean;
    public List<ReturnVisitBean> rvbList;//人工回访问题集合
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_evaluate_clients_to_me_detail;
    }

    @Override
    protected void initPresenter() {
        if(rvbList==null)rvbList = new ArrayList<>();
        else if(rvbList.size()>0)rvbList.clear();
        mActivity = Evaluate_CustomersToMeDetailActivity.this;
        presenter = new Evaluate_CustomerToMeDetailPresenter(this);
    }

    @Override
    protected void initViews() {
        ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText("客户对我的评价详情");
        ItemPositon = getIntent().getIntExtra("List_Position", -1);
        ectBean = (Evaluate_CustomersToMeBean) getIntent().getSerializableExtra("Evaluate_CustomersToMeBean");
        if(ectBean!=null && !ectBean.getId().equals("")){
            presenter.getData(false, ectBean.getId(), ectBean.getVistTaskId());
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(ectBean!=null && !ectBean.getId().equals("")){
            presenter.getData(false, ectBean.getId(), ectBean.getVistTaskId());
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {}
    ArrayList<String> replayPics;
    //处理详情信息
    public void setGetDetail(boolean isSuccess, Evaluate_CustomersToMeDetailBean detailBean){
        ArrayList<String> evalPics = new ArrayList<>();
        replayPics = new ArrayList<>();
        if(detailBean!=null) {
            if(!detailBean.isCusEvaCheck() && ectBean.getFappcstifuse()==1){
                tvfToEvaluateClient.setVisibility(View.VISIBLE);
            }else {
                tvfToEvaluateClient.setVisibility(View.GONE);
            }
            if(detailBean.getFreplydate().equals("") || detailBean.getFreplydate().equals("0")){
                rlfReplay.setVisibility(View.VISIBLE);
            }else {
                rlfReplay.setVisibility(View.GONE);
            }
            if (detailBean.getAttachDtos().size() > 0) {
                List<AttachDto> attachDtos = detailBean.getAttachDtos();
                for (int i = 0; i < attachDtos.size(); i++) {
                    if (attachDtos.get(i).getAttachType().equals("0")) {
                        //0.评价图，1.评价结果回复图
                        evalPics.add(attachDtos.get(i).getAttachUrl());
                    } else if (attachDtos.get(i).getAttachType().equals("1")) {
                        replayPics.add(attachDtos.get(i).getAttachUrl());
                    }
                }
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvfReplyTime.setText(DateTool.getTimeTimestamp(detailBean.getFreplydate(), "yyyy-MM-dd HH:mm"));
                            tvfReplyContent.setText(detailBean.getReply());
                            if(replayPics.size()>0) {
                                manager = new FullyGridLayoutManager(mActivity, 3);
                                adapter = new PhotoAdapter(mActivity, replayPics, PhotoAdapter.TYPE_URL, false);
                                adapter.setListener(new PhotoAdapter.onItemClick() {
                                    @Override
                                    public void itemClick(View view, ArrayList<String> beans, int adapterPosition) {
                                        Bundle bundle1 = new Bundle();
                                        bundle1.putStringArrayList("piclists", replayPics);
                                        bundle1.putInt("position", adapterPosition);
                                        Intent intent = new Intent(mActivity, ShowPicActivity.class);
                                        intent.putExtras(bundle1);
                                        mActivity.startActivity(intent);
                                    }
                                    @Override
                                    public void deleteClick(View view, ArrayList<String> beans, int adapterPosition) {}
                                });
                                rvfReplyPics.setLayoutManager(manager);
                                rvfReplyPics.setAdapter(adapter);
                            }
                        }
                    });
                }
            }).start();
            edView.initEvaluateDetailView(mActivity, detailBean, evalPics);
        }
    }
    //处理人工回访界面
    public void setGetReturnVisit(boolean isSuccess, List<ReturnVisitBean> rvbeans){
        if (isSuccess && rvbeans.size() > 0) {
            rvbList = rvbeans;
            ervView.initEvaluateReturnVisitView(mActivity, false, rvbList, new EvaluateReturnVisitView.TotalPoints() {
                @Override
                public void setTotalPoints(int scores, List<ReturnVisitBean> result) {
                    tvfTotalPoints.setText(scores + "");
                }
            });
        }
    }
    @OnClick({R.id.rlfYEvaluateToMe, R.id.rlfNEvaluateToMe, R.id.tvfToEvaluateClient, R.id.rlfReply})
    void thisOnClick(View v) {
        switch (v.getId()) {
            case R.id.rlfYEvaluateToMe:
                changeTab(0);
                break;
            case R.id.rlfNEvaluateToMe:
                changeTab(1);
                break;
            case R.id.tvfToEvaluateClient:
                if(!NoDoubleClickUtils.isDoubleClick()) {
                    EvaluateCustomersActivity.start(mActivity, "Evaluate_CustomersToMeDetailActivity", convertToEvalCustSaveBean(ectBean), REQUEST_CODE_CTM);
                }
                break;
            case R.id.rlfReply:
                if(!NoDoubleClickUtils.isDoubleClick()) {
                    Intent intent = new Intent(mActivity, ReplyActivity.class);
                    intent.putExtra("EVALID", ectBean.getId());
//                intent.putExtra("STRCONTENT", tvfReplyContent.getText().toString());
//                intent.putExtra("IMAGEPATHS", replayPics);
                    mActivity.startActivity(intent);
                }
                break;
        }
    }
    private void changeTab(int index){
        try {
            switch (index){
                case 0:
                    if(imgfLeftVernier.getVisibility() != View.VISIBLE){
                        tvfLeftTilte.setTextColor(Color.parseColor("#ed423a"));
                        tvfRightTilte.setTextColor(Color.parseColor("#333333"));
                        imgfLeftVernier.setVisibility(View.VISIBLE);
                        imgfRightVernier.setVisibility(View.INVISIBLE);
                        llfTotalPoints.setVisibility(View.GONE);
                        ervView.setVisibility(View.GONE);
                        edView.setVisibility(View.VISIBLE);
                    }
                    break;
                case 1:
                    if(imgfRightVernier.getVisibility() != View.VISIBLE){
                        tvfRightTilte.setTextColor(Color.parseColor("#ed423a"));
                        tvfLeftTilte.setTextColor(Color.parseColor("#333333"));
                        imgfRightVernier.setVisibility(View.VISIBLE);
                        imgfLeftVernier.setVisibility(View.INVISIBLE);
                        llfTotalPoints.setVisibility(View.VISIBLE);
                        edView.setVisibility(View.GONE);
                        ervView.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }catch (Exception e){
            NLogger.e("切换页签：" + e.getMessage());
        }
    }
    /**
     * 转为上传所需的EvalCustSaveBean对象
     */
    public RequestTemplateDto convertToEvalCustSaveBean(Evaluate_CustomersToMeBean mstDto) {
        if (mstDto != null) {
            RequestTemplateDto result = new RequestTemplateDto();
            result.setId(mstDto.getId());
            result.setTaskId(mstDto.getTaskno());
            result.setTaskType(mstDto.getTasktype());
            result.setFserviceprjid(mstDto.getFserviceprjid());
            return result;
        } else return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_CTM:
                    tvfToEvaluateClient.setVisibility(View.GONE);
                    ectBean.setFappcstifuse(1);
                    break;
            }
        }
    }
}
