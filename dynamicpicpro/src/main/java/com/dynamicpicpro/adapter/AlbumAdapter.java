package com.dynamicpicpro.adapter;

/**
 * 作者：DC-ZhuSuiBo on 2018/4/2 0002 10:41
 * 邮箱：suibozhu@139.com
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.dynamicpicpro.model.AlbumModel;
import com.dynamicpicpro.view.AlbumItem;

import java.util.ArrayList;

public class AlbumAdapter extends com.dynamicpicpro.adapter.MBaseAdapter<AlbumModel> {
    public AlbumAdapter(Context context, ArrayList<AlbumModel> models) {
        super(context, models);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlbumItem albumItem = null;
        if (convertView == null) {
            albumItem = new AlbumItem(context);
            convertView = albumItem;
        } else
            albumItem = (AlbumItem) convertView;
        albumItem.update(models.get(position));
        return convertView;
    }

}