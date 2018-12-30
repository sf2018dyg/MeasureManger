package com.soonfor.measuremanager.home.homepage.activity;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.homepage.adapter.topPostsAdpter;
import com.soonfor.measuremanager.home.homepage.model.bean.topPostsEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DesignReTieActivity extends BaseActivity {

    Context mContext;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rlvlist)
    RecyclerView rlvlist;
    GridLayoutManager manager;
    private topPostsAdpter tpostAdpter;
    List<topPostsEntity> tpostBeans;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_design_re_tie;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);// 关闭title
        mContext = DesignReTieActivity.this;
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(listener);

        tpostBeans = new ArrayList<>();
        tpostBeans.add(new topPostsEntity("租房不等于就是依照自己喜欢的弄不等于就是依照自己喜欢的弄不等于就是依照自己喜欢的弄不等于就是依照自己喜欢的弄不等于就是依", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517881562&di=adb12129f5d15a6d4fd2724fb696e9dc&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dpixel_huitu%252C0%252C0%252C294%252C40%2Fsign%3D15e580c1ac86c9171c0e5a79a04515a3%2F80cb39dbb6fd52665039582da018972bd40736fc.jpg", "冯小宝","http://img1.imgtn.bdimg.com/it/u=1417434314,1309927943&fm=11&gp=0.jpg","3小时前", "200"));
        tpostBeans.add(new topPostsEntity("租房不等于就是依照自己喜欢的弄不等于就是依照自己喜欢的弄不等于就是依照自己喜欢的弄不等于就是依照自己喜欢的弄不等于就是依", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517881562&di=adb12129f5d15a6d4fd2724fb696e9dc&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dpixel_huitu%252C0%252C0%252C294%252C40%2Fsign%3D15e580c1ac86c9171c0e5a79a04515a3%2F80cb39dbb6fd52665039582da018972bd40736fc.jpg", "唐小兵","http://img1.imgtn.bdimg.com/it/u=2772751555,2790696784&fm=11&gp=0.jpg","2小时前", "200"));
        tpostBeans.add(new topPostsEntity("租房不等于就是依照自己喜欢的弄不等于就是依照自己喜欢的弄不等于就是依照自己喜欢的弄不等于就是依照自己喜欢的弄不等于就是依", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517881562&di=adb12129f5d15a6d4fd2724fb696e9dc&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dpixel_huitu%252C0%252C0%252C294%252C40%2Fsign%3D15e580c1ac86c9171c0e5a79a04515a3%2F80cb39dbb6fd52665039582da018972bd40736fc.jpg", "凌小婷","http://img5.imgtn.bdimg.com/it/u=3394015260,3420228543&fm=11&gp=0.jpg","1小时前", "200"));
        manager = new GridLayoutManager(mContext, 1);
        tpostAdpter = new topPostsAdpter(mContext, tpostBeans);
        rlvlist.setLayoutManager(manager);
        rlvlist.setAdapter(tpostAdpter);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
