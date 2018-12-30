package com.soonfor.measuremanager.view.previewImage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.soonfor.measuremanager.R;

import java.util.List;

/**
 * Created by Soonfor on 2017/5/27.
 */

public class ImageViewPagerAdapter extends FragmentStatePagerAdapter{

    List<String> mDatas;
    int defaultImageId;

    public ImageViewPagerAdapter(FragmentManager fm, List<String> data) {
        super(fm);
        mDatas = data;
        this.defaultImageId = R.mipmap.zanuw;
    }
    public ImageViewPagerAdapter(FragmentManager fm, List<String> data, int defaultImageId) {
        super(fm);
        mDatas = data;
        this.defaultImageId = defaultImageId;
    }

    @Override
    public Fragment getItem(int position) {
        String url = mDatas.get(position);
        Fragment fragment = ImageFragment.newInstance(url,defaultImageId);
        return fragment;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }
}
