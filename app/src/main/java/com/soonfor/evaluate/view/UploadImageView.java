package com.soonfor.evaluate.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.luck.picture.lib.PictureExternalPreviewActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DoubleUtils;
import com.soonfor.measuremanager.R;
import com.soonfor.evaluate.adapter.ImagesAdapter;
import com.soonfor.evaluate.bean.UploadPhoto;
import com.soonfor.measuremanager.tools.MyToast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：DC-DingYG on 2018-11-16 8:23
 * 邮箱：dingyg012655@126.com
 * 上传图片view
 */
public class UploadImageView extends LinearLayout {

    private Activity mActivity;
    @BindView(R.id.rlfFirstItem)
    RelativeLayout rlfFirshtItem;
    @BindView(R.id.rvHAddphotos)
    RecyclerView rvfHAddphotos;//横向图片列表
    private GridLayoutManager mLayoutManager_h;
    private ImagesAdapter imgAdapter;
    public static List<UploadPhoto> imgList;//图片集合

    public UploadImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.view_uploadimages, this);
        ButterKnife.bind(this, view);
    }
    public void initUploadImgView(Activity mActivity){
        this.mActivity = mActivity;
        if(imgList==null){ imgList = new ArrayList<>();}else if(imgList.size()>0){imgList.clear();}
        mLayoutManager_h = new GridLayoutManager(mActivity, 6);
        // 设置布局管理器
        rvfHAddphotos.setLayoutManager(mLayoutManager_h);
        imgAdapter = new ImagesAdapter(mActivity, imgList, mLayoutManager_h, rvfHAddphotos, imgListener);
        // 设置adapter
        rvfHAddphotos.setAdapter(imgAdapter);
        rlfFirshtItem.setOnClickListener(imgListener);
    }

    private View.OnClickListener imgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rlfFirstItem:
                    if(imgList==null){
                        imgList = new ArrayList<>();
                    }else if(imgList.size()>=10){
                        MyToast.showToast(mActivity, "选择的图片数量已达最大值（6）");
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
                                            .maxSelectNum(6-imgList.size())
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
                    deletePic(index, imgList.get(index).getId(), false);
                    break;
            }
        }
    };
    public void refreshView(List<UploadPhoto> imageList){
        if(imageList.size()>0) imgList.addAll(imageList);
        imgAdapter.notifyDataSetChanged(imgList);
        imgAdapter.MoveToPosition(mLayoutManager_h, rvfHAddphotos, imgList.size() - 1);
    }

    public void deletePic(int delePosition, String picId, boolean isDeleteAll) {
        if(picId.equals("")){//删除未提交
            //ImageUtil.delFile("");
            afterDeletePic(delePosition, null);
        }else {//删除已提交的图片

        }
    }

    public List<UploadPhoto> getImgList() {
        if(imgList == null)imgList = new ArrayList<>();
        return imgList;
    }

    /**
     * 删除图片后处理数据
     */
    public void afterDeletePic(int index, String data) {
        imgList.remove(index);
        imgAdapter.notifyDataSetChanged(imgList);
        imgAdapter.MoveToPosition(mLayoutManager_h, rvfHAddphotos, index - 1);
    }
}
