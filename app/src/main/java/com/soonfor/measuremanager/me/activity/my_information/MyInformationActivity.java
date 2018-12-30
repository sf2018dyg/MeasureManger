package com.soonfor.measuremanager.me.activity.my_information;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;
import com.soonfor.measuremanager.me.fragment.my_information.AnnouncementFragment;
import com.soonfor.measuremanager.me.fragment.my_information.InformFragment;
import com.soonfor.measuremanager.me.presenter.my_information.IMyInformationView;
import com.soonfor.measuremanager.me.presenter.my_information.MyInformationPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-02-26.
 * 我的消息
 */

public class MyInformationActivity extends BaseActivity<MyInformationPresenter> implements IMyInformationView {

    private Activity mActivity;
    private InformFragment informFragment;
    private AnnouncementFragment annFragment;
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    @BindView(R.id.tvfInform)
    TextView tvfInform;
    static ImageView imgfInform;
    @BindView(R.id.tvfAnnouncement)
    TextView tvfAnnouncement;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_information;
    }

    @Override
    protected void initPresenter() {
        presenter = new MyInformationPresenter(this);
        mActivity = MyInformationActivity.this;
        imgfInform = (ImageView) findViewById(R.id.imgfInform);
    }

    @Override
    protected void initViews() {
        mFragments = new ArrayList<>();
        informFragment = new InformFragment();
        annFragment = new AnnouncementFragment();

        mFragments.add(informFragment);
        mFragments.add(annFragment);

        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        tvfInform.setEnabled(false);
                        tvfAnnouncement.setEnabled(true);
                        break;
                    case 1:
                        tvfInform.setEnabled(true);
                        tvfAnnouncement.setEnabled(false);
                        break;
                }
            }
            @Override public void onPageScrollStateChanged(int state) {}
        });
    }
    @OnClick({R.id.tvfInform, R.id.tvfAnnouncement})
    void clickListener(View view){
        switch (view.getId()){
            case R.id.tvfInform:
                tvfInform.setEnabled(false);
                tvfAnnouncement.setEnabled(true);
                viewPager.setCurrentItem(0);
                break;
            case R.id.tvfAnnouncement:
                tvfInform.setEnabled(true);
                tvfAnnouncement.setEnabled(false);
                viewPager.setCurrentItem(1,false);
                break;
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    /**
     * 根据是否有列表数据决定是否显示通知右上角的红点
     */
    public static void showRedProint(boolean isShow){
        if(isShow){
            imgfInform.setVisibility(View.VISIBLE);
        }else{
            imgfInform.setVisibility(View.INVISIBLE);
        }
    }
}
