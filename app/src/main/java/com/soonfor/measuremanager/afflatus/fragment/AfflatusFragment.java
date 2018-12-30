package com.soonfor.measuremanager.afflatus.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.fragment.detail.DesignCaseFragment;
import com.soonfor.measuremanager.afflatus.fragment.detail.SparePartsLibraryFragment;
import com.soonfor.measuremanager.afflatus.fragment.detail.SwatchLibrariesFragment;
import com.soonfor.measuremanager.afflatus.fragment.drawlayout.DesignDrawLayoutFragment;
import com.soonfor.measuremanager.afflatus.fragment.drawlayout.PartsFragment;
import com.soonfor.measuremanager.afflatus.fragment.drawlayout.TintplateFragment;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.activity.my_information.MyInformationActivity;
import com.soonfor.measuremanager.me.fragment.FragmentAdapter;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import q.rorbin.badgeview.QBadgeView;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/20 14:43
 * 邮箱：suibozhu@139.com
 * 灵感
 */

public class AfflatusFragment extends Fragment implements AsyncUtils.AsyncCallback {

    Unbinder unbinder;
    @BindView(R.id.rlnewsmsg)
    RelativeLayout rlnewsmsg;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private String[] titles;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;
    private FragmentAdapter adapter;
    DesignCaseFragment dcFragment;
    SwatchLibrariesFragment slFragment;
    SparePartsLibraryFragment splFragment;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            //消息上方的小红点
            new QBadgeView(getContext()).bindTarget(rlnewsmsg).setBadgeNumber(-1).setBadgePadding(3, true).setGravityOffset(1, 1, true);

            titles = new String[]{"案例库", "色板库", "配件库"};
            mTitles = new ArrayList<>();
            for (int i = 0; i < titles.length; i++) {
                mTitles.add(titles[i]);
            }
            mFragments = new ArrayList<>();
            dcFragment = new DesignCaseFragment();
            slFragment = new SwatchLibrariesFragment();
            splFragment = new SparePartsLibraryFragment();

            mFragments.add(dcFragment);
            mFragments.add(slFragment);
            mFragments.add(splFragment);

            adapter = new FragmentAdapter(getFragmentManager(), mFragments, mTitles);
            viewPager.setAdapter(adapter);//给ViewPager设置适配器
            tablayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
            viewPager.setCurrentItem(0);
            designFragment();
            viewPager.setOffscreenPageLimit(0);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                            designFragment();
                            break;
                        case 1:
                            tintplateFragment();
                            break;
                        case 2:
                            partsFragment();
                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_afflatus, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void designFragment() {
        try {
            getFragmentManager().beginTransaction()
                    .replace(R.id.new_menu_anli_frame, new DesignDrawLayoutFragment(getContext()))
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tintplateFragment() {
        try {
            getFragmentManager().beginTransaction()
                    .replace(R.id.new_menu_seban_frame, new TintplateFragment(getContext()))
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void partsFragment() {
        try {
            getFragmentManager().beginTransaction()
                    .replace(R.id.new_menu_peijian_frame, new PartsFragment(getContext()))
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rlnewsmsg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlnewsmsg:
                new Intent(getActivity(), MyInformationActivity.class);
                break;
        }
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode) {
//            case Request.GET_OPTION_TYPE:
//
//                Gson gson = new Gson();
//                LogTools.showLog(getContext(), object.toString());
//                BaseResultBean<ListBean<OptionTypeBean>> resultBean2 =
//                        gson.fromJson(object.toString(), new TypeToken<BaseResultBean<ListBean<OptionTypeBean>>>() {
//                        }.getType());
//
//                AppCache.typeBeanList = resultBean2.getData().getList();
//                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {

    }

}
