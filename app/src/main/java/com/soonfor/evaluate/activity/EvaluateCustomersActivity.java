package com.soonfor.evaluate.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DoubleUtils;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.othertask.activity.UpdateTaskResultActivity;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.AttachDto;
import com.soonfor.evaluate.adapter.EvaluateLevlEditAdapter;
import com.soonfor.evaluate.bean.AppResSortItemDto;
import com.soonfor.evaluate.bean.EvalCustSaveBean;
import com.soonfor.evaluate.bean.EvaluateTemplateBean;
import com.soonfor.evaluate.bean.RequestTemplateDto;
import com.soonfor.evaluate.bean.UploadPhoto;
import com.soonfor.evaluate.presenter.EvaluateCustomersPresenter;
import com.soonfor.evaluate.view.EvaluateLevlView;
import com.soonfor.evaluate.view.UploadImageView;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：DC-DingYG on 2018-10-19 15:16
 * 邮箱：dingyg012655@126.com
 * 评价客户
 */
public class EvaluateCustomersActivity extends BaseActivity<EvaluateCustomersPresenter> {

    private EvaluateCustomersActivity mActivity;

    @BindView(R.id.llfAllEval)
    LinearLayout llfAllEval;
    @BindView(R.id.rgfEvalAll)
    RadioGroup rgAllEval;
    @BindView(R.id.llfEvaluateLevl)
    LinearLayout llfEvaluateLevl;
    @BindView(R.id.viewfEvalLevl)
    EvaluateLevlView viewfEvalLevl;

    @BindView(R.id.llfEditEvaluateContent)
    LinearLayout llfEditEvaluateContent;
    @BindView(R.id.etfEvalContent)
    EditText etfEvalContent;

    @BindView(R.id.llfUploadPics)
    UploadImageView viewUploadPics;
    @BindView(R.id.tvfSave)
    TextView tvfSave;

    RequestTemplateDto requestTemDto;
    private int allEStatus = 0;
    private EvaluateTemplateBean.TemplateMstDto templateMstDto;//评价模板信息
    private List<EvaluateTemplateBean.TemplateItemDto> resultList;//保存前评价的标签最终数据集

    private String LastActivity = "";//上一个View

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_evaluateclients;
    }

    @Override
    protected void initPresenter() {
        mActivity = EvaluateCustomersActivity.this;
        presenter = new EvaluateCustomersPresenter(mActivity);
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InFinish();
            }
        });
    }

    @Override
    protected void initViews() {
        requestTemDto = (RequestTemplateDto) getIntent().getSerializableExtra("EVALREQUEST_DTO");
        LastActivity = getIntent().getStringExtra("LASTVIEW");
        mSwipeRefresh.setEnableLoadmore(false);
        mSwipeRefresh.autoRefresh();
    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }

    @Override
    public void RefreshData(boolean isRefresh) {
        super.RefreshData(isRefresh);
        if (requestTemDto != null)
            presenter.getEvaluateTemplateBean(mActivity, requestTemDto.getFserviceprjid());
    }

    public void showViewByData(boolean isSuccess, EvaluateTemplateBean templateBean) {
        if (!isSuccess) {
            showNoDataHint(null);
            return;
        }
        showDataToView(null);
        //评价模板
        resultList = templateBean.getTempItemDtos();
        boolean isNull = false;
        if (resultList != null && resultList.size() > 0) {
            llfEvaluateLevl.setVisibility(View.VISIBLE);
            new Thread(evalLevlRunnable).start();
        } else {
            isNull = true;
            llfEvaluateLevl.setVisibility(View.GONE);
        }
        //整体评价及输入内容
        templateMstDto = templateBean.getTempMstDto();
        if (templateMstDto != null) {
            tvfSave.setVisibility(View.VISIBLE);
            setView(templateMstDto);
        } else if (isNull) {
            tvfSave.setVisibility(View.GONE);
            showNoDataHint(null);
        }
    }


    public void refreshAfterSave(boolean isSuccess, String msg) {
        closeLoadingDialog();
        if (isSuccess) {
            Intent intent = new Intent();
            if (!LastActivity.equals("")) {
                if (LastActivity.equals("Evaluate_CustomersToMeDetailActivity")) {
                    intent.setClass(mActivity, Evaluate_CustomersToMeDetailActivity.class);
                } else if (LastActivity.equals("UpdateTaskResultActivity")) {
                    intent.setClass(mActivity, UpdateTaskResultActivity.class);
                } else if (LastActivity.equals("UnEvaluate_IToCustomersFragment")) {
                    intent.setClass(mActivity, Evaluate_IToCustomersActivity.class);
                }
                mActivity.setResult(Activity.RESULT_OK, intent);
            }
            finish();
        } else {
            MyToast.showFailToast(mActivity, msg);
        }
    }

    public static void start(Context context, String viewName, RequestTemplateDto dto, int requestCode) {
        Intent intent = new Intent(context, EvaluateCustomersActivity.class);
        intent.putExtra("LASTVIEW", viewName);
        intent.putExtra("EVALREQUEST_DTO", dto);
        ((Activity)context).startActivityForResult(intent, requestCode);
    }

    private void setView(EvaluateTemplateBean.TemplateMstDto templateMstDto) {
        if (templateMstDto.getIfallhighappraise() == 1) {
            llfAllEval.setVisibility(View.VISIBLE);
            rgAllEval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // 获取选中的RadioButton的id
                    int id = group.getCheckedRadioButtonId();
                    // 通过id实例化选中的这个RadioButton
                    RadioButton choise = (RadioButton) findViewById(id);
                    // 获取这个RadioButton的text内容
                    String butText = choise.getText().toString();
                    if (butText.equals("好评"))
                        allEStatus = 1;
                    else if (butText.equals("中评"))
                        allEStatus = 2;
                    else if (butText.equals("差评"))
                        allEStatus = 3;
                }
            });
        } else {
            llfAllEval.setVisibility(View.GONE);
        }
        if (templateMstDto.getIfsetappraisecontent() == 1) llfEditEvaluateContent.setVisibility(View.VISIBLE);
        else llfEditEvaluateContent.setVisibility(View.GONE);
        if (templateMstDto.getIfuploadimg() == 1) {
            viewUploadPics.setVisibility(View.VISIBLE);
            viewUploadPics.initUploadImgView(mActivity);
        } else {
            viewUploadPics.setVisibility(View.GONE);
        }
    }

    private Runnable evalLevlRunnable = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    viewfEvalLevl.initEvaluateLevlList(mActivity, resultList, descListenner);
                }
            });
        }
    };
    private EvaluateLevlEditAdapter.DescListenner descListenner = new EvaluateLevlEditAdapter.DescListenner() {
        @Override
        public void setDesc(List<EvaluateTemplateBean.TemplateItemDto> mList) {
            resultList = mList;
        }
    };

    @OnClick({R.id.tvfSave})
    void clickListener() {
        if (!DoubleUtils.isFastDoubleClick() && templateMstDto != null) {
            if(allEStatus==0 && (resultList==null||resultList.size()==0)){
                MyToast.showToast(mActivity, "请评价后再保存！");
                return;
            }
            showLoadingDialog();
            EvalCustSaveBean saveBean = convertToEvalCustSaveBean(templateMstDto);
            List<AttachDto> attachDtos = new ArrayList<>();
            if (viewUploadPics.getImgList().size() > 0) {
                List<UploadPhoto> photos = viewUploadPics.getImgList();
                boolean isUploaded = true;//是否都上传成功
                for (int i = 0; i < photos.size(); i++) {
                    String url = photos.get(i).getLocalMedia().getCompressPath();
                    if (url == null || !url.startsWith("http")) {
                        isUploaded = false;
                        break;
                    } else {
                        AttachDto attachDto = new AttachDto();
                        attachDto.setAttachUrl(photos.get(i).getUrl());
                        if (url.contains("\\")) {
                            attachDto.setAttachDesc(url.substring(url.lastIndexOf("\\")+1, url.length()));
                        }
                        attachDtos.add(attachDto);
                    }
                }
                if (!isUploaded) {
                    attachDtos.clear();
                    MyToast.showToast(mActivity, "尚有图片未上传成功，请稍候或重新上传");
                    closeLoadingDialog();
                    return;
                }
                saveBean.setAttachDtos(attachDtos);
                saveData(saveBean);
            } else {
                saveBean.setAttachDtos(new ArrayList<>());
                saveData(saveBean);
            }
        }
    }

    private void saveData(EvalCustSaveBean saveBean) {
        saveBean.setTaskId(requestTemDto.getTaskId());
        saveBean.setTaskType(requestTemDto.getTaskType());
        saveBean.setAllhighappraiseresult(allEStatus);
        saveBean.setAppraisecontent(etfEvalContent.getText().toString().trim());
        List<AppResSortItemDto> resSortItemDtos = new ArrayList<>();
        if (resultList != null && resultList.size() > 0) {
            for (int i = 0; i < resultList.size(); i++) {
                AppResSortItemDto sortItemDto = new AppResSortItemDto();
                EvaluateTemplateBean.TemplateItemDto temDto = resultList.get(i);
                sortItemDto.setFsorttitle(temDto.getTitle());
                sortItemDto.setFsortstartitle(temDto.getfItemTilte());
                sortItemDto.setFpoint(temDto.getfItemValue());
                sortItemDto.setFsortstardesc(temDto.getfItemDesc());
                resSortItemDtos.add(sortItemDto);
            }
        }
        saveBean.setAppResSortItemDtos(resSortItemDtos);
        presenter.saveEvalCustomer(mActivity, saveBean);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> medias = PictureSelector.obtainMultipleResult(data);
                    if (medias != null && medias.size() > 0) {
                        List<UploadPhoto> photos = new ArrayList<>();
                        for (int i = 0; i < medias.size(); i++) {
                            UploadPhoto photo = new UploadPhoto();
                            photo.setLocalUrl(medias.get(i).getCompressPath());
                            photo.setLocalMedia(medias.get(i));
                            photos.add(photo);
                        }
                        viewUploadPics.refreshView(photos);
                    }
                    break;
            }
        }
    }

    Dialog quitConfirmDialog = null;

    private void InFinish() {
        quitConfirmDialog = CustomDialog.getNormalDialog(mActivity, "温馨提示", "尚未保存评价，确认要离开吗？",
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick(View view) {
                        quitConfirmDialog.dismiss();
                        finish();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        InFinish();
    }

    /**
     * 转为上传所需的EvalCustSaveBean对象
     */
    public EvalCustSaveBean convertToEvalCustSaveBean(EvaluateTemplateBean.TemplateMstDto mstDto) {
        if (mstDto != null) {
            Gson gson = new Gson();
            return gson.fromJson(gson.toJson(mstDto), EvalCustSaveBean.class);
        } else return null;
    }
}
