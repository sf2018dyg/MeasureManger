package com.soonfor.updateutil;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.tools.ImageUtil;

import java.util.ArrayList;

/**
 * 类用途：缩放图片
 */
public class ShowPicActivity extends AppCompatActivity {

    private ViewPager mPager;
    private ArrayList<String> pics;
    private TextView txt_pageno;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpic);
        txt_pageno = (TextView) findViewById(R.id.txt_pageno);
        pics = getIntent().getStringArrayListExtra("piclists");
        position = getIntent().getIntExtra("position",0);

       /* if (pics != null) {
            txt_pageno.setText("0/" + pics.size());
        } else {
            txt_pageno.setText("0/0");
        }*/

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return pics.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                //txt_pageno.setText(position+1 + "/" + pics.size());

                PhotoView view = new PhotoView(ShowPicActivity.this);
                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ImageUtil.loadImage(ShowPicActivity.this, pics.get(position), view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        mPager.setCurrentItem(position);
    }
}
