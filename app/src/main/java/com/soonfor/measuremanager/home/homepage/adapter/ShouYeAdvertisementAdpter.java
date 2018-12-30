package com.soonfor.measuremanager.home.homepage.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;

import com.orhanobut.hawk.Hawk;
import com.soonfor.gallerlib.GallerAdapter;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.view.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/27 11:36
 * 邮箱：suibozhu@139.com
 */

public class ShouYeAdvertisementAdpter extends GallerAdapter {

    Context mContext;
    List<String> pics;

    public ShouYeAdvertisementAdpter(Context context,List<String> pics){
        mContext = context;
        this.pics = pics;
    }

    public void notifyDataSetChanged(List<String> lt){
        pics = lt;
        notifyDataSetChanged();
    }

    @Override
    public int getGallerSize() {
        return pics.size();
    }

    @Override
    public View getItemView(int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_image, null);
        RoundedImageView img = (RoundedImageView) v.findViewById(R.id.img);
        ImageUtil.loadImageWithCache(mContext, pics.get(position-1), img, R.mipmap.zanuw, true);
        return v;
    }
}

/*

public class ShouYeAdvertisementAdpter extends RepBaseAdapter {

    Context mContext;
    List<String> pics;

    public ShouYeAdvertisementAdpter(Context context,List<String> pics){
        mContext = context;
        this.pics = pics;
    }

    @Override
    public int getCount() {
        return pics.size();
    }

    @Override
    public Object getItem(int position) {
        return pics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHoler holer = null;
        if(v == null){
            v = LayoutInflater.from(mContext).inflate(R.layout.item_image, null);
            holer = new ViewHoler(v);
            v.setTag(holer);
        }else {
            holer = (ViewHoler) v.getTag();
        }

        holer.setDatas(position);
        return v;
    }

    class ViewHoler{
        RoundedImageView img;
        public ViewHoler(View v){
            img = (RoundedImageView)v.findViewById(R.id.img);
        }

        public void setDatas(int posi){
            Picasso.with(mContext)
                    .load(pics.get(posi))
                    .placeholder(R.mipmap.zanuw)
                    .error(R.mipmap.zanuw)
                    .into(img);
        }
    }

}*/
