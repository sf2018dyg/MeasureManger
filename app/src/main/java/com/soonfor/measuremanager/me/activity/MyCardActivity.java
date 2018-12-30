package com.soonfor.measuremanager.me.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.me.bean.MeMineBean;
import com.soonfor.measuremanager.me.bean.MyCardBean;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;
import com.soonfor.measuremanager.me.fragment.my_favorite.DesignCaseFragment;
import com.soonfor.measuremanager.me.fragment.my_favorite.DesignHotpostFragment;
import com.soonfor.measuremanager.me.fragment.mycard.BrandStoryFragment;
import com.soonfor.measuremanager.me.fragment.mycard.DesignWorksFragment;
import com.soonfor.measuremanager.me.presenter.mycard.IMycardView;
import com.soonfor.measuremanager.me.presenter.mycard.MycardPresenter;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.NoScrollViewPager;
import com.soonfor.measuremanager.view.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCardActivity extends BaseActivity<MycardPresenter> implements IMycardView {

    private MyCardActivity mActivity;
    @BindView(R.id.imgpath)
    ImageView imgpath;
    @BindView(R.id.tvfName)
    TextView tvfName;
    @BindView(R.id.tvfCity)
    TextView tvfCity;//城市区域
    @BindView(R.id.tv_storeName)
    TextView tvStoreName;//门店
    @BindView(R.id.tvfPopularity)
    TextView tvfPopularity;//人气
    @BindView(R.id.tvfPerformance)
    TextView tvfPerformance;//业绩
    @BindView(R.id.tvfFens)
    TextView tvfFens;//粉丝
    @BindView(R.id.tvfFavourite)
    TextView tvfFavourite;//收藏
    @BindView(R.id.avatar)
    RoundImageView imgfAvatar;

    @BindView(R.id.tvfDesignJy)
    TextView tvfDesignJy;//设计经验
    @BindView(R.id.tvfGoodStyle)
    TextView tvfGoodStyle;//设计风格
    @BindView(R.id.tvfDesignLy)
    TextView tvfDesignLy;//设计体验
    private String[] titles = new String[]{"设计作品", "品牌故事"};
    private DesignWorksFragment bgFrament1;
    private BrandStoryFragment bgFrament2;
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;


    private MeMineBean mmBean;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_mycard;
    }

    @Override
    protected void initPresenter() {
        mActivity = MyCardActivity.this;
        presenter = new MycardPresenter(this);
    }

    @Override
    protected void initViews() {
        imgpath.setFocusable(true);
        imgpath.setFocusableInTouchMode(true);
        imgpath.requestFocus();
        mmBean = getIntent().getParcelableExtra(Tokens.Mine.SKIP_TO_MYCARD);
        if(mmBean!=null){
            tvfName.setText(mmBean.getName());
            tvStoreName.setText(mmBean.getStoreName());
            tvfPerformance.setText(mmBean.getPerformance() + "");
            imageLoading(mmBean.getAvatar(), imgfAvatar, R.drawable.avatar_default);
        }
        /**
         * 请求我的名片信息
         */
        presenter.getCardDatas(this);

        mTitles = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mTitles.add(titles[i]);
        }
        mFragments = new ArrayList<>();
        bgFrament1 = new DesignWorksFragment();
        bgFrament2 = new BrandStoryFragment();

        mFragments.add(bgFrament1);
        mFragments.add(bgFrament2);

        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        tablayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
        viewPager.setCurrentItem(0);
        viewPager.setScanScroll(false);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }
    @OnClick({R.id.imgBack, R.id.imgShare})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgShare:
                CommonUtils.shareMsg(MyCardActivity.this,"分享", "欢迎光临数夫家具软件","http://music.163.com/#/song/515143305/?userid=335532549", null);
                break;
        }
    }
    public void setDataToView(MyCardBean mcBean){
        tvfPopularity.setText(mcBean.getPopularity());
       // tvfPerformance.setText(mcBean.getPerformance());
        tvfFens.setText(mcBean.getFens());
        tvfFavourite.setText(mcBean.getFavourite());
        tvfDesignJy.setText(mcBean.getDesign_experience());
        tvfGoodStyle.setText(mcBean.getGoodstyles());
        tvfDesignLy.setText(mcBean.getDesign_idea());
        imageLoading("http://img5.imgtn.bdimg.com/it/u=4247704318,2577748025&fm=27&gp=0.jpg", imgpath, R.mipmap.zanuw);
    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void closeLoadingDialog() {

    }
}
