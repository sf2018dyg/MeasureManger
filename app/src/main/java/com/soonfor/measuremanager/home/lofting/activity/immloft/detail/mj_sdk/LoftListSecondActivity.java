package com.soonfor.measuremanager.home.lofting.activity.immloft.detail.mj_sdk;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureExternalPreviewActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DoubleUtils;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.lofting.adapter.immloft.LoftImagesAdapter;
import com.soonfor.measuremanager.home.lofting.adapter.immloft.LoftListSecondAdapter;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkComponentEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkWallEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkPhoto;
import com.soonfor.measuremanager.home.lofting.presenter.immloft.LoftingListPresenter;
import com.soonfor.measuremanager.home.lofting.view.immloft.ILoftingListView;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.dialog.normal.NormalDialog;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by DingYg on 2018-02-09.
 * 放样清单之立即放样
 */

public class LoftListSecondActivity extends BaseActivity<LoftingListPresenter> implements ILoftingListView {

    LoftListSecondActivity mActivity;
    @BindView(R.id.rvVList)
    RecyclerView recyclerView;//垂直列表
    @BindView(R.id.rlfFirstItem)
    RelativeLayout rlfFirshtItem;
    @BindView(R.id.rvHAddphotos)
    RecyclerView rvfHAddphotos;//横向图片列表
    private TextView tvfSave;
    private GridLayoutManager mLayoutManager_v;
    private LoftListSecondAdapter llistAdapter;
    private List<MarkComponentEntity> memList;//部件对象
    private GridLayoutManager mLayoutManager_h;
    private LoftImagesAdapter imgAdapter;
    public static List<MarkPhoto> imgList;//图片集合
    NormalDialog ndialog;
    String sTaskno;
    String sContractNo;
    MarkWallEntity wallEntity = null;
    String sWallCode = "";
    public static int CusPosition = 0;
    private boolean isAddPicAndIsUpdate = false;//是否有修改图片并提交
    private String isAllComponentsMarked = "";//是否所有部件都已放样
    private boolean isRePostByFromEdit = false;//请求墙面清单是否是在修改了部件放样数据并提交成功了之后

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_loftlist_second;
    }

    @Override
    protected void initViews() {
        mActivity = LoftListSecondActivity.this;
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString(Tokens.Lofing.DETAILSKIPIMMELOFT_TITLE_WALL,
                "墙面放样清单");
        ((TextView) toolbar.findViewById(R.id.tvfTitile)).setText(title);
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InFinish();
            }
        });
        tvfSave = toolbar.findViewById(R.id.tvfSave);
        sTaskno = bundle.getString("fy_taskNo", "");
        sContractNo = bundle.getString("fy_contractNo", "");
        sWallCode = bundle.getString("fy_wallCode","");
        imgList = new ArrayList<>();
        //横向列表
        mLayoutManager_h = new GridLayoutManager(mActivity, 10);
        llistAdapter = new LoftListSecondAdapter(mActivity,  memList);
        imgAdapter = new LoftImagesAdapter(mActivity, imgList, mLayoutManager_h, rvfHAddphotos, imgListener);
        //垂直列表
        mLayoutManager_v = new GridLayoutManager(mActivity, 1);
        // 设置布局管理器
        recyclerView.setLayoutManager(mLayoutManager_v);
        // 设置adapter
        recyclerView.setAdapter(llistAdapter);

        // 设置布局管理器
        rvfHAddphotos.setLayoutManager(mLayoutManager_h);
        // 设置adapter
        rvfHAddphotos.setAdapter(imgAdapter);
        rlfFirshtItem.setOnClickListener(imgListener);
        if(!sWallCode.equals("")) {
            showLoadingDialog();
            presenter.getData(2, sTaskno, sContractNo, sWallCode);
        }
    }
    private void initRecycleView(){
        if(wallEntity !=null) {
            if (wallEntity.getIsMark().equals("1")) {
                tvfSave.setVisibility(View.GONE);
                rlfFirshtItem.setVisibility(View.GONE);
            } else {
                tvfSave.setVisibility(View.VISIBLE);
                tvfSave.setOnClickListener(imgListener);
                rlfFirshtItem.setVisibility(View.VISIBLE);
            }
        }else {
            showNoDataHint(null);
        }
    }

    @Override
    protected void initPresenter() {
        presenter = new LoftingListPresenter(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        if (wallEntity == null) {
            return;
        }
        //获取清单数据
        if (wallEntity.getComponents() == null || wallEntity.getComponents().size() == 0) {
            presenter.getData(2, sTaskno, sContractNo, wallEntity.getWallCode());
        } else {
            imgList = wallEntity.getPhotos();
            memList = wallEntity.getComponents();
            imgAdapter.notifyDataSetChanged(imgList, wallEntity.getIsMark());
            imgAdapter.MoveToPosition(mLayoutManager_h, rvfHAddphotos, imgList.size() - 2);
            llistAdapter.notifyDataSetChanged(memList, wallEntity.getIsMark());
            if (CusPosition > 0) {
                llistAdapter.MoveToPosition(mLayoutManager_h, recyclerView, CusPosition);
            }
        }
    }

    Dialog finalConfirmDialog = null;
    private View.OnClickListener imgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rlfFirstItem:
                    if(wallEntity!=null && wallEntity.getIsMark().equals("")){
                        MyToast.showToast(mActivity, "墙面已放样，故不能新增图片");
                        return;
                    }
                    if(imgList==null){
                        imgList = new ArrayList<>();
                    }else if(imgList.size()>=10){
                        MyToast.showToast(mActivity, "选择的图片数量已达最大值（10）");
                        return;
                    }
                    PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
                                @Override
                                public void permissionGranted(@NonNull String[] permission) {
                                    //启动相册并拍照
                                    PictureSelector.create(mActivity)
                                            .openGallery(PictureMimeType.ofImage())
                                            .isCamera(true)
                                            .imageSpanCount(3)
                                            .maxSelectNum(10-imgList.size())
                                            .compress(true)
                                           // .compressGrade(Luban.CUSTOM_GEAR)
                                            .selectionMode(PictureConfig.MULTIPLE)
                                            .forResult(PictureConfig.CHOOSE_REQUEST);
                                }

                                @Override
                                public void permissionDenied(@NonNull String[] permission) {

                                }
                            }, Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE);
                    break;
                case R.id.imgfItem:
                    int index = (int) v.getTag(R.id.item_id);
                    if (!DoubleUtils.isFastDoubleClick()) {
                        if (imgList != null && imgList.size() > 0) {
                            ArrayList<LocalMedia> localMediaArrayList = new ArrayList<>();
                            for(int i=0; i<imgList.size(); i++){
                                localMediaArrayList.add(imgList.get(i).getLocalMedia());
                            }
                            Intent intent = new Intent(mActivity, PictureExternalPreviewActivity.class);
                            intent.putExtra("previewSelectList", (Serializable) localMediaArrayList);
                            intent.putExtra("position", index);
                            mActivity.startActivity(intent);
                            mActivity.overridePendingTransition(com.luck.picture.lib.R.anim.a5, 0);
                        }
                    }
                    break;
                case R.id.imgfDelete://删除
                    index = (int) v.getTag(R.id.item_id_d);
                    presenter.deletePic(index, imgList.get(index).getId(), false);
                    break;
                case R.id.tvfSave:
                    if(!isAddPicAndIsUpdate && !isRePostByFromEdit){
                        MyToast.showToast(mActivity, "未做任何有效操作，无需提交");
                        return;
                    }
                    //提交
                    isAllComponentsMarked = getComponentsStatus();
                    if (isAllComponentsMarked.equals("1")) {//部件全部已放样
                        finalConfirmDialog = CustomDialog.getDoubleNormalDialog(mActivity,
                                "温馨提示", "所有部件均已放样，提交成功后将正式更改墙面放样状态，确定提交吗?", new OnBtnClickL() {
                            @Override
                            public void onBtnClick(View view) {
                                //未完成
                                finalConfirmDialog.dismiss();
                            }
                        }, new OnBtnClickL() {
                            @Override
                            public void onBtnClick(View view) {
                                //完成
                                finalConfirmDialog.dismiss();
                                submitPics();
                            }
                        });
                    } else {
                        submitPics();
                    }
                    break;
            }
        }
    };


    @Override
    public void showLoadingDialog() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> medias = PictureSelector.obtainMultipleResult(data);
                    if (medias != null && medias.size()> 0) {
                        for(int i=0; i<medias.size(); i++){
                            MarkPhoto photo = new MarkPhoto();
                            photo.setLocalpath(medias.get(i).getCompressPath());
                            photo.setLocalMedia(medias.get(i));
                            imgList.add(photo);
                        }
                        imgAdapter.notifyDataSetChanged(imgList, wallEntity.getIsMark());
                        imgAdapter.MoveToPosition(mLayoutManager_h, rvfHAddphotos, imgList.size() - 1);
                        isAddPicAndIsUpdate = true;
                    }
                    break;
                case Tokens.Lofing.REQUEST_CODE_EDIT_SIZE:
                    if(wallEntity!=null) {
                        showLoadingDialog();
                        isRePostByFromEdit = true;
                        presenter.getData(2, sTaskno, sContractNo, wallEntity.getWallCode());
                    }
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        InFinish();
    }

    public void InFinish() {
        if (isAddPicAndIsUpdate) {//修改了图片或者修改了部件信息并提交成功了
            ndialog = CustomDialog.getNormalDialog(mActivity, "温馨提示", "您新增或修改了放样图片后未提交，确定要退出吗?",
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick(View view) {
                            ndialog.dismiss();
                            finish();
                        }
                    });
            if(ndialog!=null) ndialog.show();
        } else {
            Intent intent = new Intent(mActivity, LoftListFirstActivity.class);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }


    /**
     * 请求数据成功后处理数据
     *
     * @param data
     */
    public void setListdata(boolean isSuccess, String data) {
        if(isSuccess) {
            if (data != null) {
                try {
                    JSONArray ja = new JSONArray(data);
                    if (ja != null && ja.length() > 0) {
                        for(int i=0; i<ja.length(); i++){
                            JSONObject jo = ja.getJSONObject(i);
                            MarkWallEntity wall = new Gson().fromJson(jo.toString(), MarkWallEntity.class);
                            if(wall!=null && wall.getWallCode().equals(sWallCode)){
                                wallEntity = wall;
                                break;
                            }
                        }
                        analysisData(2);
                        if(isRePostByFromEdit){
                            MyToast.showSuccessToast(mActivity, "部件放样数据提交成功");
                        }else {
                            analysisData(1);
                        }
                    }
                } catch (Exception e) {
                }
            } else {
                MyToast.showFailToast(mActivity, "墙面放样清单为空");
            }
        }else {
            MyToast.showFailToast(mActivity, "墙面放样清单请求失败");
        }
        initRecycleView();
    }
    /**
     * @param type 1解析处理图片数据 2.解析处理清单
     */
    private void analysisData(int type) {
        try {
            switch (type) {
                case 1:
                    imgList = wallEntity.getPhotos();
                    showDataToView(null);
                    imgAdapter.notifyDataSetChanged(imgList,wallEntity.getIsMark());
                    imgAdapter.MoveToPosition(mLayoutManager_h, rvfHAddphotos, imgList.size() - 2);
                    break;
                case 2:
                    memList = wallEntity.getComponents();
                    showDataToView(null);
                    if (memList == null || memList.size() == 0) {
                        MyToast.showFailToast(mActivity, "墙面放样清单为空");
                    }
                    llistAdapter.notifyDataSetChanged(memList, wallEntity.getIsMark());
                    if (CusPosition > 0) {
                        llistAdapter.MoveToPosition(mLayoutManager_h, recyclerView, CusPosition);
                    }
                    CusPosition = 0;
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除图片后处理数据
     */
    public void afterDeletePic(int index, String data) {
        imgList.remove(index);
        imgAdapter.notifyDataSetChanged(imgList, wallEntity.getIsMark());
        imgAdapter.MoveToPosition(mLayoutManager_v, rvfHAddphotos, index - 1);
        isAddPicAndIsUpdate = true;
    }
    /**
     * 提交图片
     */
    private void submitPics() {
        if (wallEntity != null) {
            if(imgList!=null && imgList.size()>0) {
                boolean isUploaded = true;//是否都上传成功
                for (int i = 0; i < imgList.size(); i++) {
                    String url = imgList.get(i).getLocalMedia().getCompressPath();
                    if(url==null || !url.startsWith("http")){
                        isUploaded = false;
                        break;
                    }
                }
                if(isUploaded) {
                    boolean flag = false;//是否需要上传图片, 默认不需要
                    for (int i = 0; i < imgList.size(); i++) {
                        if(imgList.get(i).getId().equals("")) {//防止重复上传
                            flag = true;
                            String parms[] = new String[7];
                            parms[0] = imgList.get(i).getUrl();
                            parms[1] = sTaskno;
                            parms[2] = sContractNo;
                            parms[3] = wallEntity.getRoomCode();
                            parms[4] = wallEntity.getRoomName();
                            parms[5] = wallEntity.getWallCode();
                            parms[6] = wallEntity.getWallName();
                            presenter.setMarkPic(parms);
                        }
                    }
                    if(!flag){
                        submitWallStatus();
                    }
                }else {
                    MyToast.showToast(mActivity, "尚有图片未上传成功，请稍候或重新上传");
                }
            }else {
                submitWallStatus();
            }
        }
    }
    /**
     * 提交墙面状态
     */
    private void submitWallStatus() {
        if(isAllComponentsMarked.equals("1")) {//只有全部放样成功时才提交状态
            String parms[] = new String[4];
            parms[0] = sContractNo;
            parms[1] = wallEntity.getRoomCode();
            parms[2] = wallEntity.getWallCode();
            parms[3] = isAllComponentsMarked;
            presenter.setWall_MarkStatus(parms);
        }else {
            afterSetWallMarkStatus(true);
        }
    }
    /**
     * 提交图片后处理数据
     */
    public void afterSetPic(boolean isSuccess, String data) {
        if (isSuccess) {
            MyToast.showToast(mActivity, "放样照片提交成功");
            submitWallStatus();
        } else {
            isAddPicAndIsUpdate = true;
            MyToast.showToast(mActivity, "放样照片提交失败: " + data);
        }
    }

    /**
     * 提交墙面放样状态后处理数据
     */
    public void afterSetWallMarkStatus(boolean isSuccess) {
        if (isSuccess) {
            isAddPicAndIsUpdate = false;
            Intent intent = new Intent(mActivity, LoftListFirstActivity.class);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            isAddPicAndIsUpdate = true;
        }
    }


    /**
     * 循环检测部件信息以检测墙面放样状态
     *
     * @return
     */
    private String getComponentsStatus() {
        if (memList == null || memList.size() == 0) {
            return "0";
        } else {
            String result = "1";
            for (int i = 0; i < memList.size(); i++) {
                if (memList.get(i).getStatus().equals("待放样")) {
                    result = "0";
                    break;
                }
            }
            return result;
        }
    }
}
