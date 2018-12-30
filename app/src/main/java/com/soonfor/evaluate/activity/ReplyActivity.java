package com.soonfor.evaluate.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DoubleUtils;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.AttachDto;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.evaluate.bean.UploadPhoto;
import com.soonfor.evaluate.view.UploadImageView;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：DC-DingYG on 2018-11-14 19:50
 * 邮箱：dingyg012655@126.com
 */
public class ReplyActivity extends BaseActivity {

    private final int REQUEST_CODE_REPLAY = 1014;
    private Activity mActivity;
    @BindView(R.id.etfEvalContent)
    EditText etfReplay;
    @BindView(R.id.llfUploadPics)
    UploadImageView viewUploadPics;

    private String evalId;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_replay;
    }

    @Override
    protected void initPresenter() {
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              InFinish();
            }
        });
    }

    @Override
    protected void initViews() {
        mActivity = ReplyActivity.this;
        evalId = getIntent().getStringExtra("EVALID");
        String content = getIntent().getStringExtra("STRCONTENT");
        if(content!=null){
            etfReplay.setText(content);
            etfReplay.setSelection(content.length());
        }
        viewUploadPics.initUploadImgView(mActivity);
        List<String> pics = getIntent().getStringArrayListExtra("IMAGEPATHS");
        List<UploadPhoto> photos = new ArrayList<>();
        if(pics!=null && pics.size()>0){
            for(int i=0; i<pics.size(); i++){
                UploadPhoto uphoto = new UploadPhoto();
                LocalMedia lm = new LocalMedia();
                lm.setCompressPath(pics.get(i));
                lm.setPath(pics.get(i));
                uphoto.setLocalMedia(lm);
                uphoto.setUrl(pics.get(i));
                photos.add(uphoto);
            }
        }
        viewUploadPics.refreshView(photos);
    }

    @Override
    protected void updateViews(boolean isRefresh) {}

    @OnClick({R.id.tvfSave})
    void thisClickListener(){
        if (!DoubleUtils.isFastDoubleClick()) {
            showLoadingDialog();
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
                        String uploadUrl = photos.get(i).getUrl();
                        AttachDto attachDto = new AttachDto();
                        attachDto.setAttachUrl(uploadUrl);
                        if (uploadUrl.contains("\\")) {
                            attachDto.setAttachDesc(uploadUrl.substring(uploadUrl.lastIndexOf("\\") + 1, uploadUrl.length()));
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
            }
            saveData(attachDtos);
        }
    }
    private void saveData(List<AttachDto> attachDtos){
        String Scontent = etfReplay.getText().toString().trim();
        if(Scontent.equals("") && attachDtos.size()==0){
            MyToast.showToast(mActivity, "请编辑内容后再提交");
            return;
        }
        JSONObject jo = new JSONObject();
        try {
            jo.put("id", evalId);
            jo.put("content", Scontent);
            JSONArray attachArray = new JSONArray(new Gson().toJson(attachDtos));
            jo.put("attachDtos", attachArray);
        }catch (Exception e){}
        Request.Evaluate.replyToThat(mActivity, REQUEST_CODE_REPLAY, jo.toString(), new AsyncUtils.AsyncCallback() {
            @Override
            public void success(int requestCode, JSONObject object) {
                closeLoadingDialog();
                finish();
            }

            @Override
            public void fail(int requestCode, int statusCode, String errorMsg) {
                closeLoadingDialog();
                MyToast.showFailToast(mActivity, "回复不成功");
            }
        });
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
                            photo.setLocalMedia(medias.get(i));
                            photo.setLocalUrl(medias.get(i).getCompressPath());
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
        if(!etfReplay.getText().toString().trim().equals("") || (viewUploadPics!=null && viewUploadPics.getImgList().size()==0)) {
            quitConfirmDialog = CustomDialog.getNormalDialog(mActivity, "温馨提示", "编辑的内容未提交，确认要离开吗",
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick(View view) {
                            quitConfirmDialog.dismiss();
                            finish();
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        InFinish();
    }
}
