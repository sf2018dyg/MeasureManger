package com.soonfor.measuremanager.home.othertask.fragment.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.home.othertask.model.bean.OtherTaskMainBean;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.AttachDto;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.DisposeBean;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.FileBean;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.VoiceBean;
import com.soonfor.measuremanager.home.othertask.presenter.DisposeDetailPresenter;
import com.soonfor.measuremanager.home.othertask.view.detail.IDisposeDetailView;
import com.soonfor.evaluate.bean.ReturnVisitBean;
import com.soonfor.evaluate.view.EvaluateReturnVisitView;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.ImageVoiceView;
import com.soonfor.repository.tools.ActivityUtils;
import com.soonfor.updateutil.ShowPicActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：DC-DingYG on 2018-10-16 19:23
 * 邮箱：dingyg012655@126.com
 */
public class DisposeDetailFragment extends BaseFragment<DisposeDetailPresenter> implements IDisposeDetailView {

    Unbinder unbinder;
    Activity mActivity;

    @BindView(R.id.nSrollView)
    NestedScrollView svfData;
    @BindView(R.id.llfNoOthertaskData)
    LinearLayout llfNoData;

    @BindView(R.id.tvfExecuteResultT)
    TextView tvfExecuteResultT;
    @BindView(R.id.tvfExecuteResult)
    TextView tvfExecuteResult;
    @BindView(R.id.tvfExecuteDescT)
    TextView tvfExecuteDescT;
    @BindView(R.id.tvfExecuteDesc)
    TextView tvfExecuteDesc;
    @BindView(R.id.tvfExecuteTimeT)
    TextView tvfExecuteTimeT;
    @BindView(R.id.tvfExecuteTime)
    TextView tvfExecuteTime;
    @BindView(R.id.llfEmail)
    LinearLayout llfEmail;
    @BindView(R.id.tvfEmailInfo)
    TextView tvfEmailInfo;

    //人工回访
    @BindView(R.id.llfTotalP)
    LinearLayout llfTotalPoints;
    @BindView(R.id.tvfTotalPoints)
    TextView tvfTotalPoints;
    @BindView(R.id.detail_ErvView)
    EvaluateReturnVisitView erVisitView;

    @BindView(R.id.util_view)
    ImageVoiceView viewUtil;

    private String location;//定位
    private ArrayList<VoiceBean> voices = new ArrayList<>();//语音
    private ArrayList<String> images = new ArrayList<>();//图片
    private ArrayList<FileBean> files = new ArrayList<>();//附件
    private OtherTaskMainBean taskBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mActivity = getActivity();

        return rootView;
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_disposedetail;
    }

    @Override
    protected void initPresenter() {
        presenter = new DisposeDetailPresenter(this);
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        Bundle bundle = getArguments();
        int type = bundle.getInt(Tokens.OtherTask.OT_ITEMSKIPDETAIL_STATUS, 0);
        if (type == -1) {//抢单
            svfData.setVisibility(View.GONE);
            llfNoData.setVisibility(View.VISIBLE);
        } else {
            llfNoData.setVisibility(View.GONE);
            svfData.setVisibility(View.VISIBLE);
            taskBean = (OtherTaskMainBean) bundle.getSerializable(Tokens.OtherTask.OT_ITEMSKIPDETAIL_ITEM);
            if (taskBean != null && taskBean.getStatus() == 3) {
                if (!taskBean.getTaskId().equals(""))
                    presenter.getOtherTaskResult(mActivity, taskBean.getTaskId());
                if (taskBean.getTaskType().equals("4")) {//主动追踪任务
                    tvfExecuteResultT.setText("执行结果");
                    tvfExecuteTimeT.setText("执行时间");
                    tvfExecuteDescT.setText("执行情况");
                } else if (taskBean.getTaskType().equals("5")) {//回访任务
                    presenter.getQuestionnaire(mActivity, taskBean.getTaskId());
                    tvfExecuteResultT.setVisibility(View.VISIBLE);
                    tvfExecuteResultT.setText("回访结果");
                    tvfExecuteResult.setVisibility(View.VISIBLE);
                    tvfExecuteTimeT.setText("回访时间");
                    tvfExecuteDescT.setText("回访情况");
                }
                viewUtil.setListener(new ImageVoiceView.OnClickListener() {
                    @Override
                    public void onImageClick(int position) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putStringArrayList("piclists", images);
                        bundle1.putInt("position", position);
                        Intent intent = new Intent(mActivity, ShowPicActivity.class);
                        intent.putExtras(bundle1);
                        mActivity.startActivity(intent);
                    }
                });
            } else {
                svfData.setVisibility(View.GONE);
                llfNoData.setVisibility(View.VISIBLE);
            }
        }
    }

    //根据数据显示界面
    public void setDisposeView(boolean isSuccess, DisposeBean dpBean, String Smsg) {
        if (isSuccess && dpBean != null) {
            tvfExecuteTime.setText(dpBean.getExecDate());
            if (taskBean!=null && taskBean.getExectype() == 2) {
                if (dpBean.getMailstatus() == 0) {
                    tvfExecuteResult.setText("无需邮寄");
                } else if (dpBean.getMailstatus() == 1) {
                    llfEmail.setVisibility(View.VISIBLE);
                    tvfExecuteResult.setText("已邮寄");
                    tvfEmailInfo.setText(dpBean.getMailDesc());
                }
            } else {
                tvfExecuteResult.setText(dpBean.getStatusDesc());
            }
            tvfExecuteDesc.setText(dpBean.getNote());
            if (dpBean.getAttachDtos().size() > 0) {
                viewUtil.setVisibility(View.VISIBLE);
                List<AttachDto> attachDtos = dpBean.getAttachDtos();
                images = new ArrayList<>();
                voices = new ArrayList<>();
                files = new ArrayList<>();
                for (AttachDto infosBean : attachDtos) {
                    if (infosBean.getAttachType().equals("0")) {//图片
                        images.add(infosBean.getAttachUrl());
                    } else if (infosBean.getAttachType().equals("1")) {//语音
                        if (infosBean.getAttachDesc().equals("")) {
                            voices.add(new VoiceBean(infosBean.getAttachUrl(), 1));
                        } else {
                            long sl = 0;
                            try {
                                sl = Long.parseLong(infosBean.getAttachDesc());
                            } catch (Exception e) {
                            }
                            voices.add(new VoiceBean(infosBean.getAttachUrl(), sl));
                        }
                    } else if (infosBean.getAttachType().equals("2")) {//视频

                    } else if (infosBean.getAttachType().equals("3")) {//文档
                        files.add(new FileBean(infosBean.getAttachId(), infosBean.getAttachUrl(), infosBean.getAttachDesc()));
                    } else if (infosBean.getAttachType().equals("4")) {//其它（包括定位）
                        location = infosBean.getLocation();
                    }
                }
                //更新图片和语音
                new VocieAndPicHandler().handleMessage(new Message());
                //更新附件
                new DataHandler().handleMessage(new Message());
                if (location != null && !location.equals("")) {
                    viewUtil.setLocation(location);
                }
            }
        }
        closeLoadingDialog();
    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }

    /**
     * 更新图片和语音
     */
    public class VocieAndPicHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (images != null && images.size() > 0) viewUtil.setImages(images);
            if (voices != null && voices.size() > 0) viewUtil.setVoice(voices);
        }
    }

    /**
     * 属性附件列表
     */
    public class DataHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (files != null && files.size() > 0) {
                viewUtil.setFiles(files);
            }
        }
    }

    //处理人工回访界面
    public void setGetReturnVisit(boolean isSuccess, List<ReturnVisitBean> rvbeans) {
        if (isSuccess) {
            llfTotalPoints.setVisibility(View.VISIBLE);
            erVisitView.setVisibility(View.VISIBLE);
            erVisitView.initEvaluateReturnVisitView(mActivity, false, rvbeans, new EvaluateReturnVisitView.TotalPoints() {
                @Override
                public void setTotalPoints(int scores, List<ReturnVisitBean> result) {
                    tvfTotalPoints.setText(scores + "");
                }
            });
        }
    }

    @Override
    public void showLoadingDialog() {
        if (ActivityUtils.isRunning(getActivity())) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void closeLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
