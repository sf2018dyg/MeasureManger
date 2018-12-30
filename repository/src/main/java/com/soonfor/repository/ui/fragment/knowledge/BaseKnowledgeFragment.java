package com.soonfor.repository.ui.fragment.knowledge;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.soonfor.repository.R;

import com.soonfor.repository.adapter.knowledge.KnowledgeListAdapter;
import com.soonfor.repository.base.RepBaseFragment;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.presenter.knowledge.KnowledgePresenter;
import com.soonfor.repository.tools.DataTools;
import com.soonfor.repository.tools.FileUtils;
import com.soonfor.repository.tools.MyToast;
import com.soonfor.repository.ui.activity.knowledge.KnowledgeDetailActivity;
import com.soonfor.repository.ui.fragment.KnowledgeFragment;
import com.soonfor.repository.view.knowledge.IKnowledgeView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 作者：DC-DingYG on 2018-06-22 14:29
 * 邮箱：dingyg012655@126.com
 */
public class BaseKnowledgeFragment extends RepBaseFragment<KnowledgePresenter> implements IKnowledgeView {

    RecyclerView mRecyclerView;
    static KnowledgeListAdapter klAdapter;
    private LinearLayoutManager mLayoutManager;
    public int type;

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void fetchData() {
    }

    @Override
    protected void initPresenter() {
        presenter = new KnowledgePresenter(this);
    }

    @Override
    protected void initViews() {
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = inflater.inflate(R.layout.fragment_knowledge_base, container, false);
        BasefindViewById(rootView);
        mRecyclerView = rootView.findViewById(R.id.rvfBase);
        mActivity = getActivity();
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (KnowledgeDetailActivity.listPositon >= 0 && klAdapter!=null) {
            String key = DataTools.getMapKey(KnowledgeFragment.sType);
            if (key.equals("hot")) {
                klAdapter.notifyDataSetChanged(DataTools.hotList);
            } else {
                klAdapter.notifyDataSetChanged(DataTools.ListKlMap.get(key));
            }
            mRecyclerView.scrollToPosition(KnowledgeDetailActivity.listPositon);
        }
        KnowledgeDetailActivity.listPositon = -1;
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        if (type == 0) {
            presenter.getHotList(mActivity, 1, isRefresh);
        } else {
            presenter.getDatas(mActivity, KnowledgeFragment.sType.getIds(), 1, isRefresh);
        }
    }

    @Override
    protected void loadmoredata() {
        super.loadmoredata();
        if (type == 0) {
            if(DataTools.hotPage!=null) {
                if (DataTools.hotPage.getPageNo() >= DataTools.hotPage.getNextPage()) {
                    finishRefresh();
                    if (DataTools.hotPage.getPageNo() > 1) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                MyToast.showToast(mActivity, "已是最后一页");
                            }
                        }, 1500);
                    }
                } else {
                    presenter.getHotList(mActivity, DataTools.hotPage.getNextPage(), false);
                }
            }
        } else {
            String key = DataTools.getMapKey(KnowledgeFragment.sType);
            if(DataTools.pageKlMap!=null) {
                if (DataTools.pageKlMap.containsKey(key)) {
                    RepPageTurn pageTurn = DataTools.pageKlMap.get(key);
                    if (pageTurn.getPageNo() >= pageTurn.getNextPage()) {
                        finishRefresh();
                        if (pageTurn.getPageNo() > 1)
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    MyToast.showToast(mActivity, "已是最后一页");
                                }
                            }, 1500);
                    } else {
                        presenter.getDatas(mActivity, KnowledgeFragment.sType.getIds(), pageTurn.getNextPage(), false);
                    }
                }
            }
        }
    }

    @Override
    public void showNoDataHint(String msg) {
        super.showNoDataHint(msg);
        if (type == 0) {
            if (DataTools.hotPage.getPageNo() == 1)
                showNetError(msg);
        }else {
            String key = DataTools.getMapKey(KnowledgeFragment.sType);
            if (DataTools.pageKlMap.containsKey(key)) {
                if (DataTools.pageKlMap.get(key).getPageNo() <= 1)
                    showNetError(msg);
            }
//        if (msg != null && !msg.equals("")) {
//            MyToast.showFailToast(mActivity, msg);
//        }
        }
    }

    @Override
    public void showNetError(String msg) {
        super.showNetError(msg);
        mRecyclerView.setVisibility(View.GONE);
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public void setGetCategory(boolean isSuccess, String msg) {

    }

    @Override
    public void setGetHotList(boolean isSuccess, RepPageTurn pageTurn, ArrayList<KnowledgeBean> hotBeans, String msg) {
        mRecyclerView.setVisibility(View.VISIBLE);
        if (isSuccess) {
            if (DataTools.hotList == null) {
                DataTools.hotList = new ArrayList<>();
            }
            if (pageTurn.getPageNo() < 0) {//错误
                showNoDataHint(msg);
                return;
            } else if (pageTurn.getPageNo() == 1) {//刷新
                DataTools.hotPage = pageTurn;
                DataTools.hotList = hotBeans;
            } else {//加载更多
                DataTools.hotPage = pageTurn;
                DataTools.hotList.addAll(hotBeans);
            }
        }
        setListView(isSuccess, DataTools.hotList, msg);
    }

    @Override
    public void setGetHnowledwList(boolean isSuccess, RepPageTurn pageTurn, ArrayList<KnowledgeBean> kBeans, String msg) {
        mRecyclerView.setVisibility(View.VISIBLE);
        if (isSuccess) {
            if (pageTurn.getPageNo() < 1) {//错误
                showNoDataHint(msg);
                return;
            } else {
                if (DataTools.hotList == null) {
                    DataTools.hotList = new ArrayList<>();
                }
                if (DataTools.pageKlMap == null) {
                    DataTools.pageKlMap = new HashMap<>();
                }
                if (DataTools.ListKlMap == null) {
                    DataTools.ListKlMap = new HashMap<>();
                }
                String key = DataTools.getMapKey(KnowledgeFragment.sType);
                DataTools.pageKlMap.put(key, pageTurn);
                if (pageTurn.getPageNo() == 1) {//刷新
                    if (pageTurn.getPageNo() == 1) {
                        DataTools.ListKlMap.put(key, kBeans);
                    }
                    setListView(true, kBeans, msg);
                } else {//加载更多
                    if (DataTools.ListKlMap.containsKey(key)) {
                        ArrayList<KnowledgeBean> totalList = DataTools.ListKlMap.get(key);
                        totalList.addAll(kBeans);
                        DataTools.ListKlMap.put(key, totalList);
                        setListView(true, totalList, msg);
                    } else {
                        DataTools.ListKlMap.put(key, kBeans);
                        setListView(true, kBeans, msg);
                    }
                }
            }
        } else {
            setListView(false, kBeans, msg);
        }

    }

    public void setListView(boolean isSuccess, ArrayList<KnowledgeBean> beans, String msg) {
        if (isSuccess) {
            showDataToView(null);
            klAdapter = new KnowledgeListAdapter(mActivity, beans, listener, 0);
            mRecyclerView.setAdapter(klAdapter);
        } else {
            showNoDataHint(msg);
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            KnowledgeBean klBean = (KnowledgeBean) v.getTag(R.id.item_object);
            int i = v.getId();
            if (i == R.id.llfLike) {
                int position = (int) v.getTag(R.id.knowledge_list_key);
                presenter.like(mActivity, position, klBean.getId());

            } else if (i == R.id.llfShare) {
                PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
                            @Override
                            public void permissionGranted(@NonNull String[] permissions) {
                                new FileUtils.downloadImageAndShareAsyncTask(mActivity).execute("欢迎光临数夫家具软件", "分享", "http://www.fdatacraft.com", "");
                            }

                            @Override
                            public void permissionDenied(@NonNull String[] permissions) {
                            }
                        }, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);

            }
        }
    };

    /**
     * 请求点赞/取消点赞接口后的操作
     *
     * @param isSuccess
     * @param position
     */
    @Override
    public void setAfterLike(boolean isSuccess, int position) {
        if (isSuccess) {
            String key = DataTools.getMapKey(KnowledgeFragment.sType);
            if (key.equals("hot")) {
                if (DataTools.hotList.get(position).getIsLike() == 0) {//点赞成功
                    DataTools.hotList.get(position).setIsLike(1);
                    int likeCount = Integer.parseInt(DataTools.hotList.get(position).getLikeCount()) + 1;
                    DataTools.hotList.get(position).setLikeCount(likeCount + "");
                } else {//取消点赞成功
                    DataTools.hotList.get(position).setIsLike(0);
                    int likeCount = Integer.parseInt(DataTools.hotList.get(position).getLikeCount()) - 1;
                    if (likeCount >= 0) {
                        DataTools.hotList.get(position).setLikeCount(likeCount + "");
                    }
                }
                klAdapter.notifyDataSetChanged(DataTools.hotList);
            } else {
                if (DataTools.ListKlMap.get(key).get(position).getIsLike() == 0) {
                    DataTools.ListKlMap.get(key).get(position).setIsLike(1);
                    int likeCount = Integer.parseInt(DataTools.ListKlMap.get(key).get(position).getLikeCount()) + 1;
                    DataTools.ListKlMap.get(key).get(position).setLikeCount(likeCount + "");
                } else {
                    DataTools.ListKlMap.get(key).get(position).setIsLike(0);
                    int likeCount = Integer.parseInt(DataTools.ListKlMap.get(key).get(position).getLikeCount()) - 1;
                    if (likeCount >= 0) {
                        DataTools.ListKlMap.get(key).get(position).setLikeCount(likeCount + "");
                    }
                }
                klAdapter.notifyDataSetChanged(DataTools.ListKlMap.get(key));
            }
            klAdapter.MoveToPosition(mLayoutManager, mRecyclerView, position);
        }
    }
}