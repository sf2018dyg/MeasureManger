package com.dynamicpicpro;

/**
 * 作者：DC-ZhuSuiBo on 2018/4/2 0002 10:46
 * 邮箱：suibozhu@139.com
 */

import android.os.Bundle;

import com.dynamicpicpro.control.PhotoSelectorDomain;
import com.dynamicpicpro.model.PhotoModel;
import com.dynamicpicpro.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
public class PhotoPreviewActivity extends BasePhotoPreviewActivity implements PhotoSelectorActivity.OnLocalReccentListener {
    private PhotoSelectorDomain photoSelectorDomain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoSelectorDomain = new PhotoSelectorDomain(getApplicationContext());
        init(getIntent().getExtras());
    }

    @SuppressWarnings("unchecked")
    protected void init(Bundle extras) {
        if (extras == null)
            return;
        if (extras.containsKey("photos")) { // 预览图片
            photos = (List<PhotoModel>) extras.getSerializable("photos");
            current = extras.getInt("position", 0);
            updatePercent();
            bindData(false);
        } else if (extras.containsKey("album")) { // 点击图片查看
            String albumName = extras.getString("album"); // 相册
            this.current = extras.getInt("position");
            if (!StringUtils.isNull(albumName) && albumName.equals(PhotoSelectorActivity.RECCENT_PHOTO)) {
                photoSelectorDomain.getReccent(this);
            } else {
                photoSelectorDomain.getAlbum(albumName, this);
            }
        }else if(extras.containsKey("save")){
            List<PhotoModel> pics = (List<PhotoModel>) extras.getSerializable("pics");
            int position = extras.getInt("position");
            List<PhotoModel> photos = new ArrayList<PhotoModel>();
            photos = pics;
            this.photos = photos;
            this.current = position;
            bindData(true);
        }
    }


    @Override
    public void onPhotoLoaded(List<PhotoModel> photos) {
        this.photos = photos;
        updatePercent();
        setIs_Chat(false);
        bindData(false); // 更新界面
    }


}