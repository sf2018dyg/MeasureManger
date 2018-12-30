package com.soonfor.repository.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.soonfor.repository.model.knowledge.CategoryBean;
import com.soonfor.repository.ui.fragment.knowledge.BaseKnowledgeFragment;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-07-19 17:51
 * 邮箱：dingyg012655@126.com
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    public List<BaseKnowledgeFragment> list;
    private List<CategoryBean> titles;

    public FragmentAdapter(FragmentManager fm, List<BaseKnowledgeFragment> list, List<CategoryBean> titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }


    /**
     * 返回显示的Fragment总数
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * 返回要显示的Fragment的某个实例
     */
    @Override
    public BaseKnowledgeFragment getItem(int position) {
        return list.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? "" : titles.get(position).getName();
    }
}