package com.soonfor.repository.ui.activity.personal;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.soonfor.repository.R;

import com.soonfor.repository.adapter.knowledge.MyFavoriteListAdapter;
import com.soonfor.repository.base.RepBaseActivity;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.presenter.personal.FavoritePresenter;
import com.soonfor.repository.tools.FileUtils;
import com.soonfor.repository.tools.MyToast;
import com.soonfor.repository.tools.Tokens;
import com.soonfor.repository.view.personal.IFavoriteView;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/2 0002 11:32
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class MyFavoriteActivity extends RepBaseActivity<FavoritePresenter>
        implements IFavoriteView, View.OnClickListener {

    FavoritePresenter presenter;
    TextView tvfTitile;
    TextView tvfRight;
    RelativeLayout rlfBottom;
    MyFavoriteListAdapter adapter;
    GridLayoutManager manager;
    List<KnowledgeBean> list;
    RecyclerView recyView;
    RepPageTurn pageTurn;//当前页
    CheckBox cb_fullCheck;
    Button bt_delFavorite;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_favorite;
    }

    @Override
    protected FavoritePresenter initPresenter() {
        findViewById();
        presenter = new FavoritePresenter(this);
        return presenter;
    }
    private void findViewById() {
        tvfTitile = this.findViewById(R.id.tvfTitile);
        tvfRight = this.findViewById(R.id.tvfRight);
        tvfRight.setOnClickListener(this);
        rlfBottom = this.findViewById(R.id.rlfBottom);
        recyView = this.findViewById(R.id.recyView);
        cb_fullCheck = this.findViewById(R.id.cb_fullCheck);
        cb_fullCheck.setOnClickListener(this);
        bt_delFavorite = this.findViewById(R.id.bt_delFavorite);
        bt_delFavorite.setOnClickListener(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getFavorite(MyFavoriteActivity.this, 1, 10);
    }

    @Override
    protected void loadmoredata() {
        super.loadmoredata();
        if (pageTurn != null){
            if(pageTurn.getPageNo() >= pageTurn.getNextPage()){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MyToast.showToast(MyFavoriteActivity.this, "已是最后一页");
                    }
                }, 1500);
                finishRefresh();
            } else {
                presenter.getFavorite(MyFavoriteActivity.this, pageTurn.getNextPage(), 10);
            }
        } else {
            finishRefresh();
        }
    }

    @Override
    protected void initViews() {
        tvfTitile.setText("我的收藏");
        tvfRight.setVisibility(View.VISIBLE);
        tvfRight.setText("编辑");
        mEmptyLayout.show(true);

        manager = new GridLayoutManager(this, 1);
        recyView.setLayoutManager(manager);
        list = new ArrayList<>();
        adapter = new MyFavoriteListAdapter(this, list, listener, checkListener);
        recyView.setAdapter(adapter);

        presenter.getFavorite(MyFavoriteActivity.this, 1, 10);
    }

    @Override
    public void setDatas(RepPageTurn pageTurn, List<KnowledgeBean> beans) {
        try {
            if (pageTurn != null) {
                this.pageTurn = pageTurn;
            }
            if (pageTurn.getPageNo() < 0) {//错误
            } else if (pageTurn.getPageNo() == 1) {//刷新
                list = beans;
            } else {//加载更多
                if(list==null){list = new ArrayList<>();}
                list.addAll(beans);
            }
            mEmptyLayout.hide();
            finishRefresh();
            this.list = beans;
            if (beans.size() == 0) {
                mEmptyLayout.show("暂无收藏的知识", "");
            }
            adapter.notifyDataSetChanged(beans);
        }catch (Exception e){}

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            KnowledgeBean klBean = (KnowledgeBean) v.getTag(R.id.item_object);
            int i = v.getId();
            if (i == R.id.llfLike) {
                int position = (int) v.getTag(R.id.knowledge_list_key);
                presenter.like(MyFavoriteActivity.this, position, klBean.getId());

            } else if (i == R.id.llfShare) {
                PermissionsUtil.requestPermission(MyFavoriteActivity.this, new PermissionListener() {
                            @Override
                            public void permissionGranted(@NonNull String[] permissions) {
                                new FileUtils.downloadImageAndShareAsyncTask(MyFavoriteActivity.this).execute("欢迎光临数夫家具软件", "分享", "http://www.fdatacraft.com", "");
                            }

                            @Override
                            public void permissionDenied(@NonNull String[] permissions) {
                            }
                        }, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);

            }
        }
    };

    private View.OnClickListener checkListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckBox cb = (CheckBox) v;
            int curPosi = (int) cb.getTag();
            if (list != null) {
                list.get(curPosi).setEditable(cb.isChecked());
                adapter.notifyDataSetChanged(list);

                boolean bl = false;
                for (KnowledgeBean b : list) {
                    if (!b.isEditable()) {
                        bl = false;
                        break;
                    } else {
                        bl = true;
                    }
                }
                cb_fullCheck.setChecked(bl);

                //统计选了多少个
                cluamCheckNum();
            }
        }
    };

    private void ResetStatu(boolean bl) {
        if (list != null) {
            int i = 0;
            for (KnowledgeBean b : list) {
                b.setEditable(false);
                list.set(i, b);
                i++;
            }
            adapter.notifyDataSetChanged(list, bl);
        }
    }

    /**
     * 请求点赞/取消点赞接口后的操作
     *
     * @param isSuccess
     * @param position
     */
    @Override
    public void setAfterLike(boolean isSuccess, int position) {
        if (isSuccess) {
            if (list.get(position).getIsLike() == 0) {//点赞成功
                list.get(position).setIsLike(1);
                int likeCount = Integer.parseInt(list.get(position).getLikeCount()) + 1;
                list.get(position).setLikeCount(likeCount + "");
            } else {//取消点赞成功
                list.get(position).setIsLike(0);
                int likeCount = Integer.parseInt(list.get(position).getLikeCount()) - 1;
                if (likeCount >= 0) {
                    list.get(position).setLikeCount(likeCount + "");
                }
            }
            adapter.notifyDataSetChanged(list);
            recyView.scrollToPosition(position);
        }
    }

    private void cluamCheckNum() {
        if (list != null) {
            int num = 0;
            for (KnowledgeBean b : list) {
                if (b.isEditable()) {
                    num++;
                }
            }
            if (num > 0) {
                bt_delFavorite.setText("删除(" + num + ")");
            } else {
                bt_delFavorite.setText("删除");
            }
        }
    }

    @Override
    public void afterDel(String data) {
        if(data.equals("success")){
            tvfRight.callOnClick();
            updateViews(true);
            MyToast.showSuccessToast(MyFavoriteActivity.this, "删除成功");
        }else {
            MyToast.showFailToast(MyFavoriteActivity.this, "删除失败");
        }
    }

    @Override
    public void showNetError(String msg) {
        super.showNetError(msg);
        if (recyView != null) {
            recyView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void finishByBack() {
        super.finishByBack();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case Tokens.Knowledge.CLICK_TO_DETAIL:

                    break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvfRight) {
            if (tvfRight.getText().toString().equals("编辑")) {
                tvfRight.setText("取消");
                rlfBottom.setVisibility(View.VISIBLE);
                ResetStatu(true);
            } else if (tvfRight.getText().toString().equals("取消")) {
                tvfRight.setText("编辑");
                rlfBottom.setVisibility(View.GONE);
                ResetStatu(false);
            }
            cb_fullCheck.setChecked(false);

            cluamCheckNum();
        } else if (view.getId() == R.id.cb_fullCheck) {
            if (list != null) {
                int i = 0;
                for (KnowledgeBean b : list) {
                    b.setEditable(cb_fullCheck.isChecked());
                    list.set(i, b);
                    i++;
                }
                adapter.notifyDataSetChanged(list);

                cluamCheckNum();
            }
        } else if (view.getId() == R.id.bt_delFavorite) {
            ArrayList<String> okIds = new ArrayList<>();
            for (KnowledgeBean mb : list) {
                if (mb.isEditable()) {
                    okIds.add(mb.getId());
                }
            }
            if (okIds.size() > 0) {
                presenter.delFavorite(MyFavoriteActivity.this, okIds);
            } else {
                Toast.makeText(MyFavoriteActivity.this, "先选几个再点我", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
