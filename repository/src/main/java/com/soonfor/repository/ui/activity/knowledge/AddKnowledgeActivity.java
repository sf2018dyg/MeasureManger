package com.soonfor.repository.ui.activity.knowledge;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dingyg.edittextwithclear.EditTextWithClear;
import com.dingyg.richeditor.RichEditUtils;
import com.dingyg.richeditor.RichTextEditor;
import com.dingyg.richeditor.Tools;
import com.dingyg.richeditor.utils.ImageUtils;
import com.dingyg.richeditor.utils.MyGlideEngine;
import com.dingyg.richeditor.utils.SDCardUtil;
import com.dingyg.videocompressorr.VideoCompress;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.orhanobut.hawk.Hawk;
import com.soonfor.repository.R;
import com.soonfor.repository.base.RepBaseActivity;
import com.soonfor.repository.model.knowledge.CategoryBean;
import com.soonfor.repository.presenter.knowledge.AddKnowledgePresenter;
import com.soonfor.repository.tools.ComTools;
import com.soonfor.repository.tools.DataTools;
import com.soonfor.repository.tools.MyToast;
import com.soonfor.repository.tools.Tokens;
import com.soonfor.repository.tools.dialog.CustomDialog;
import com.soonfor.repository.tools.popupwindow.SelectAddClassifyPopupWindow;
import com.soonfor.repository.view.knowledge.IAddKnowledgeView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.jzvd.JZVideoPlayer;

/**
 * 作者：DC-DingYG on 2018-07-31 8:40
 * 邮箱：dingyg012655@126.com
 */
public class AddKnowledgeActivity extends RepBaseActivity<AddKnowledgePresenter> implements IAddKnowledgeView {

    private AddKnowledgeActivity mActivity;
    ImageView imgfOpen;//展示二级菜单
    TextView tvfClassify;
    TextView tvfTitile;
    RelativeLayout rlfTablayout;
    EditTextWithClear llfEditTitle;
    EditText etfTilte;
    RichTextEditor editor;//图文混编器
    ImageView imgfCover;
    ImageView imgfCamera, imgfPhoto, imgfAccessory;
    RelativeLayout rlfBottom;
    Button btfSend;
    private RichEditUtils richEditUtils;
    //private boolean isRichFocus = false;//光标焦点是否在内容编辑器上


    private CategoryBean[] fcbs = new CategoryBean[2];//選中的一、二級菜單
    public static final int REQUEST_CODE_CHOOSE = 23;//定义请求码常量

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_addknowledge;
    }

    @Override
    protected void finishByBack() {
        super.finishByBack();
        InFinish();
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private void findViewById() {
        imgfOpen = this.findViewById(R.id.imgfState);
        imgfOpen.setOnClickListener(listener);
        tvfClassify = this.findViewById(R.id.tvfClassify);
        tvfTitile = this.findViewById(R.id.tvfTitile);
        rlfTablayout = this.findViewById(R.id.rlfTablayout);
        rlfTablayout.setOnClickListener(listener);
        llfEditTitle = this.findViewById(R.id.ewfTitle);
        editor = this.findViewById(R.id.richEditor);
        imgfCover = this.findViewById(R.id.imgfCover);
        rlfBottom = this.findViewById(R.id.rlfBottom);
        imgfCamera = this.findViewById(R.id.imgfCamera);
        imgfCamera.setOnClickListener(listener);
        imgfPhoto = this.findViewById(R.id.imgfPhoto);
        imgfPhoto.setOnClickListener(listener);
        btfSend = this.findViewById(R.id.btfSave);
        btfSend.setOnClickListener(listener);
        if (RichTextEditor.framePics == null) {
            RichTextEditor.framePics = new HashMap<>();
        } else if (RichTextEditor.framePics.size() > 0) {
            RichTextEditor.framePics.clear();
        }
    }

    @Override
    protected AddKnowledgePresenter initPresenter() {
        findViewById();
        actionName = "上传中...";
        presenter = new AddKnowledgePresenter(this);
        mActivity = AddKnowledgeActivity.this;
        tvfTitile.setText("添加知识");
        etfTilte = llfEditTitle.getEditText();
        editor.setHint("内容");
        etfTilte.setHint("标题");
        etfTilte.requestFocus();
        etfTilte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().equals("")) {
                    btfSend.setEnabled(false);
                } else if (etfTilte.getText().toString().trim().length() > 0 && fcbs != null && fcbs.length > 1 && fcbs[1] != null && !fcbs[1].getId().equals("")) {
                    btfSend.setEnabled(true);
                }
            }
        });
        return presenter;
    }

    @Override
    protected void initViews() {
        richEditUtils = RichEditUtils.getRichEditUtils(mActivity, editor);
        presenter.getTabTitles(mActivity, false);
    }

    @Override
    public void setGetCategory(boolean isSuccess, String msg) {
        if (isSuccess) {

        } else {
            showNoDataHint(msg);
            MyToast.showFailToast(mActivity, msg);
        }
    }

    @Override
    public void setAddKnowLedge(boolean isSuccess, String msg) {
        btfSend.setEnabled(true);
        if (isSuccess) {
            MyToast.showFailToast(mActivity, "已成功新增知识");
            finish();
        } else {
            showNoDataHint(msg);
            MyToast.showFailToast(mActivity, msg);
        }
    }

    SelectAddClassifyPopupWindow scPopupWindow;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.rlfTablayout || i == R.id.imgfState) {
                if (DataTools.fTypes == null) {
                    return;
                }
                if (scPopupWindow != null) {
                    scPopupWindow.dismiss();
                }
                scPopupWindow = new SelectAddClassifyPopupWindow(mActivity, fcbs, imgfCover, new SelectAddClassifyPopupWindow.refresh() {
                    @Override
                    public void refreshLayout(CategoryBean[] cbs) {
                        fcbs = cbs;
                        if (scPopupWindow != null) {
                            scPopupWindow.dismiss();
                        }
                        tvfClassify.setText(fcbs[1].getName());
                        if (!fcbs[1].getId().equals("")) {
                            if (!etfTilte.getText().toString().trim().equals("")) ;
                            btfSend.setEnabled(true);
                        }
                    }
                });
                scPopupWindow.showPopupWindow(rlfTablayout, mActivity);

            } else if (i == R.id.btfSave) {
                if (fcbs[1] == null || fcbs[1].getId().equals("")) {
                    MyToast.showToast(mActivity, "请选择要增加的知识类型");
                    return;
                }
                String title = etfTilte.getText().toString().trim();
                if (title.equals("")) {
                    MyToast.showToast(mActivity, "请填写标题");
                    return;
                }
                String content = richEditUtils.getEditData();
                if (content.length() == 0) {
                    MyToast.showToast(mActivity, "请填写内容");
                    return;
                }
                btfSend.setEnabled(false);
                presenter.addKonwledge(mActivity, fcbs[1].getId(), title, content, null);

            } else if (i == R.id.imgfCamera) {
                PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permission) {
                        Tools.closeSoftKeyInput(mActivity);//关闭软键盘
//                        if (isRichFocus) {
                        // 打开相机
                        richEditUtils.openCamera(2);
//                        } else {
//                            MyToast.showToast(mActivity, "请先将光标聚焦内容编辑器");
//                        }
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permission) {

                    }
                }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            } else if (i == R.id.imgfPhoto) {
                PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permission) {
                        // 打开系统相册
                        Tools.closeSoftKeyInput(mActivity);//关闭软键盘
//                        if (isRichFocus) {
                        // 打开相册
                        //int count = editor.getChildCount();
                        ImageUtils.callGallery(mActivity, MimeType.ofAll(), 1, REQUEST_CODE_CHOOSE);
//                        } else {
//                            MyToast.showToast(mActivity, "请先将光标聚焦内容编辑器");
//                        }
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permission) {

                    }
                }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);


            } else if (i == R.id.imgfAccessory) {
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RichEditUtils.REQUEST_CODE_CAMERA://拍照
                    break;
                case RichEditUtils.REQUEST_CODE_CAMERA2://摄像
                    showLoadingDialog("视频压缩中...");
                    //根据Uri获取文件的存放路径
                    String loca_videopath = SDCardUtil.getFilePathByUri(mActivity, data.getData());
                    ComprassVideo(loca_videopath);
                    break;
                case REQUEST_CODE_CHOOSE:
                    List<Uri> mSelected = richEditUtils.getUriList(data);
                    if (mSelected != null && mSelected.get(0) != null) {
                        //根据Uri获取文件的存放路径
                        String locapath = SDCardUtil.getFilePathByUri(mActivity, mSelected.get(0));
                        //获取文件格式用于判断是视频还是图片
                        String fmat = SDCardUtil.getFrmatByUri(locapath);
                        String filepath = null;
                        if (fmat.equals("video")) {
                            showLoadingDialog("正在计算视频大小");
                            int during = SDCardUtil.getRingDuring(locapath);
                            closeLoadingDialog();
                            if (during <= 0) {
                                MyToast.showToast(AddKnowledgeActivity.this, "视频无效，请重新选择");
                            } else if (during > 600 * 1000) {
                                MyToast.showToast(AddKnowledgeActivity.this, "视频过长，请重新选择");
                            } else {
                                showLoadingDialog("视频压缩中...");
                                ComprassVideo(locapath);
                            }
                        } else {
                            showLoadingDialog("压缩中...");
                            filepath = richEditUtils.getCompressImageFile("image", mSelected.get(0));//压缩图片
                            closeLoadingDialog();
                            presenter.uploadFile(mActivity, "image", filepath, "图片上传中...");
                        }
                    }
                    break;

            }
        }
    }
    //上传图片或视频
    @Override
    public void setUploadFile(String fileType, boolean isSuccess, String localpath, String msg) {
        try {
            if (isSuccess) {
                JSONObject jo = new JSONObject(msg);
                String domain = Hawk.get(Tokens.SP_UPLOADCNETERPATH, "");
                if (!domain.equals("")) {
                    if (fileType.equals("video")) {
                        //视频的网络路径
                        String videoPath = domain + "/" + ComTools.ToString(jo.getString("url"));
                        //获取第一帧图片后上传
                        String frameLocalPath = SDCardUtil.saveToSdCard(richEditUtils.getLocalVideoBitmap(localpath));
                        presenter.uploadFrameAtPic(mActivity, "image", frameLocalPath, videoPath);
                    } else if (fileType.equals("image")) {
                        String imgpath = domain + "/" + ComTools.ToString(jo.getString("url"));
                        Bitmap bitmap = richEditUtils.getScaledBitmap(localpath, editor.getMeasuredWidth());
                        richEditUtils.insertImagesSync(bitmap, null, imgpath);
                    }
                }
            } else {
                showNoDataHint(msg);
                MyToast.showFailToast(mActivity, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //上传第一帧图片
    @Override
    public void setUploadFrameAtPic(boolean isSuccess, String videoPath, String frameLocalPath, String msg) {
        try {
            String fameAtPath = null;
            if (isSuccess) {
                try {
                    JSONObject jo = new JSONObject(msg);
                    String domain = Hawk.get(Tokens.SP_UPLOADCNETERPATH, "");
                    if (!domain.equals("")) {
                        fameAtPath = domain + "/" + ComTools.ToString(jo.getString("url"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (RichTextEditor.framePics == null) {
                RichTextEditor.framePics = new HashMap<>();
            }
            RichTextEditor.framePics.put(videoPath, fameAtPath);
            richEditUtils.insertImagesSync(null, frameLocalPath, videoPath);

        } catch (Exception e) {
            e.getMessage();
        }
    }


    @Override
    public void onBackPressed() {
        InFinish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    Dialog quitDialog = null;

    private void InFinish() {
        if (editor.getLastIndex() > 1) {
            quitDialog = CustomDialog.createCancleDialog(mActivity, "添加的内容尚未发布，确定要退出界面吗？", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quitDialog.dismiss();
                    editor.removeAllViews();
                    presenter.addHotAndAll();
                    closeLoadingDialog();
                    finish();
                }
            });
            quitDialog.show();
        } else {
            editor.removeAllViews();
            presenter.addHotAndAll();
            closeLoadingDialog();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (RichTextEditor.framePics != null && RichTextEditor.framePics.size() > 0) {
            RichTextEditor.framePics.clear();
        }
        //清空缓存的图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                SDCardUtil.deleteDir(SDCardUtil.APP_NAME, null);

            }
        }).start();
        //清空缓存的压缩视频
        new Thread(new Runnable() {
            @Override
            public void run() {
                SDCardUtil.deleteDir(SDCardUtil.CompressFile, "VID_");
            }
        });
    }

    private void ComprassVideo(String vUrl){
        if(SDCardUtil.getFileSizeMB(vUrl) < 5){//压缩的大小是否在5M以下
            closeLoadingDialog();
            presenter.uploadFile(mActivity, "video", vUrl, "视频上传中...");
        }else {
            final String destPath = SDCardUtil.CompressFile + "VID_" + new SimpleDateFormat("yyyyMMdd_HHmmss", SDCardUtil.getLocale(mActivity)).format(new Date()) + ".mp4";
            VideoCompress.compressVideoLow(vUrl, destPath, new VideoCompress.CompressListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess() {
                    ComprassVideo(destPath);
                }

                @Override
                public void onFail() {
                    closeLoadingDialog();
                    MyToast.showFailToast(mActivity, "视频压缩失败！");
                }

                @Override
                public void onProgress(float percent) {

                }
            });
        }
    }
}
