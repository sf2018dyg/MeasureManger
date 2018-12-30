package com.soonfor.measuremanager.home.homepage.activity;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.gallerlib.ScreenUtils;
import com.pkmmte.view.CircularImageView;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.adpter.CommentListAdapter;
import com.soonfor.measuremanager.afflatus.bean.CommentBean;
import com.soonfor.measuremanager.afflatus.bean.CommentDetailBean;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.homepage.model.bean.topPostsEntity;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/26 13:57
 * 邮箱：suibozhu@139.com
 * 热帖详情
 */
public class DesignReTieDetailActivity extends BaseActivity {

    Context mContext;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rlvcomment)
    RecyclerView rlvcomment;
    List<CommentBean> commentBeans;
    List<CommentDetailBean> commentDetailBeans;
    CommentListAdapter commentListAdapter;
    GridLayoutManager manager;
    @BindView(R.id.designerName)
    TextView designerName;
    @BindView(R.id.postDate)
    TextView postDate;
    @BindView(R.id.ivhead)
    CircularImageView ivhead;
    @BindView(R.id.txttitle)
    TextView txttitle;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_design_re_tie_detail;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);// 关闭title
        mContext = DesignReTieDetailActivity.this;
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(listener);

        topPostsEntity te = (topPostsEntity) getIntent().getSerializableExtra("topPostsEntity");
        if(te!=null) {
            txttitle.setText(CommonUtils.formatStr(te.getTitle()));
            designerName.setText(CommonUtils.formatStr(te.getAuthor()));
            postDate.setText(CommonUtils.formatStr(te.getPostDate()));
            ImageUtil.loadImage(mContext, te.getAuthorImg(), ivhead);
        }

        //评论留言
        manager = new GridLayoutManager(mContext, 1);
        rlvcomment.setLayoutManager(manager);
        commentBeans = new ArrayList<CommentBean>();
        commentListAdapter = new CommentListAdapter(mContext, commentBeans,null,null);
        rlvcomment.setAdapter(commentListAdapter);
        rlvcomment.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动

        //设置数据源
        commentListAdapter.notifyDataSetChanged(getCommentBeans());

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)rlvcomment.getLayoutParams();
        layoutParams.height = getTotleHeight();
        rlvcomment.setLayoutParams(layoutParams);
    }

    private List<CommentBean> getCommentBeans() {
        commentBeans = new ArrayList<CommentBean>();
//        commentBeans.add(new CommentBean("http://img5.imgtn.bdimg.com/it/u=2870880441,2164714698&fm=11&gp=0.jpg", "Ada", "6楼", "01.22 12:00", "太漂亮了！收藏收藏！学习学习", getCommentDetailBeans()));
//        commentBeans.add(new CommentBean("http://img4.imgtn.bdimg.com/it/u=3407329645,609445419&fm=11&gp=0.jpg", "FuYuiOnG", "5楼", "01.21 10:13", "大家可以多分享", getCommentDetailBeans2()));
//        commentBeans.add(new CommentBean("http://img5.imgtn.bdimg.com/it/u=2870880441,2164714698&fm=11&gp=0.jpg", "Ada", "4楼", "01.22 12:00", "太漂亮了！收藏收藏！学习学习", getCommentDetailBeans()));
//        commentBeans.add(new CommentBean("http://img4.imgtn.bdimg.com/it/u=3407329645,609445419&fm=11&gp=0.jpg", "FuYuiOnG", "3楼", "01.21 10:13", "大家可以多分享", getCommentDetailBeans2()));
//        commentBeans.add(new CommentBean("http://img5.imgtn.bdimg.com/it/u=2870880441,2164714698&fm=11&gp=0.jpg", "Ada", "2楼", "01.22 12:00", "太漂亮了！收藏收藏！学习学习", getCommentDetailBeans()));
//        commentBeans.add(new CommentBean("http://img4.imgtn.bdimg.com/it/u=3407329645,609445419&fm=11&gp=0.jpg", "FuYuiOnG", "1楼", "01.21 10:13", "大家可以多分享", getCommentDetailBeans2()));
        return commentBeans;
    }

    private List<CommentDetailBean> getCommentDetailBeans() {
        commentDetailBeans = new ArrayList<CommentDetailBean>();
        //commentDetailBeans.add(new CommentDetailBean("FuYuiOnG", "@Ada", "有帮助就好", true));
        return commentDetailBeans;
    }

    private List<CommentDetailBean> getCommentDetailBeans2() {
        commentDetailBeans = new ArrayList<CommentDetailBean>();
        //commentDetailBeans.add(new CommentDetailBean("Aero", "@FuYuiOnG", "大师球!", true));
        return commentDetailBeans;
    }

    private int getTotleHeight(){
        int height = ScreenUtils.dip2px(mContext,commentBeans.size() * 86);
        /*for(int i=0;i<commentBeans.size();i++){
            height += ScreenUtils.dip2px(mContext,commentBeans.get(i).getCommentDetailBeans().size() * 48);
        }*/
        return height;
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
