package com.soonfor.repository.ui.activity.knowledge;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.soonfor.repository.R;

import com.soonfor.repository.adapter.knowledge.CommentListAdapter;
import com.soonfor.repository.adapter.knowledge.FilelListAdapter;
import com.soonfor.repository.base.RepBaseActivity;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.CommentBean;
import com.soonfor.repository.model.knowledge.FileBean;
import com.soonfor.repository.model.knowledge.KUserInfo;
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.model.knowledge.KnowledgeDetailBean;
import com.soonfor.repository.model.knowledge.MyAddedKnowLedgeBean;
import com.soonfor.repository.model.knowledge.ReplyBean;
import com.soonfor.repository.presenter.knowledge.KnowledgeDetailPresenter;
import com.soonfor.repository.tools.CircleImageView;
import com.soonfor.repository.tools.ComTools;
import com.soonfor.repository.tools.DataTools;
import com.soonfor.repository.tools.DateTools;
import com.soonfor.repository.tools.FileUtils;
import com.soonfor.repository.tools.MyToast;
import com.soonfor.repository.ui.RepApp;
import com.soonfor.repository.ui.activity.RepositoryMainActivity;
import com.soonfor.repository.ui.activity.SearchActivity;
import com.soonfor.repository.ui.activity.personal.MyAddedKnowLedgeActivity;
import com.soonfor.repository.ui.activity.personal.MyFavoriteActivity;
import com.soonfor.repository.ui.activity.staff.OnlineStaffActivity;
import com.soonfor.repository.ui.fragment.KnowledgeFragment;
import com.soonfor.repository.view.knowledge.IKnowledgeDetailView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;

/**
 * 作者：DC-DingYG on 2018-07-20 11:01
 * 邮箱：dingyg012655@126.com
 */
public class KnowledgeDetailActivity extends RepBaseActivity<KnowledgeDetailPresenter>
        implements IKnowledgeDetailView, View.OnClickListener {

    KnowledgeDetailActivity mActivity;
    GridLayoutManager manager1;
    GridLayoutManager manager2;
    ScrollView scrollView;
    TextView tvfTitle;
    CircleImageView imgfHead;
    TextView tvfUserName;
    TextView tvfPubTime;
    WebView webView;
    LinearLayout llfFj;
    RecyclerView fjRecyclerView;

    LinearLayout llfComment;
    RelativeLayout collectionVolume;
    RelativeLayout thumbsupVolume;
    RecyclerView rlvcomment;
    TextView tvfRead;
    TextView tvfCommot;
    TextView tvfThumb;
    TextView tvfCollect;
    RelativeLayout rlfBottom;
    RelativeLayout rlfCommotVolume;

    private static EditText edtComment;
    public static List<CommentBean> commentBeans = new ArrayList<>();
    public static KnowledgeDetailBean kdBean = null;

    CommentListAdapter commentListAdapter;
    public static String _id = "";
    String shareUrl = "";
    List<FileBean> fileList;
    FilelListAdapter filelListAdapter;
    //RepPageTurn pageTurn;//页码
    public static int listPositon = -1;//进入知识库的列表的位置
    public static int comment_Index = -1;//评论的位置
    public static int commentdetail_Index = -1;//评论回复的位置
    String last_viewType = "";//跳转到此界面的上一个界面 名称 0：回退上一个界面需要判断刷新 1:回退到上一个界面不需要刷新
    String auditStatus = "";//为“我添加的客户页面”特制
    private long publishTime;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_knowledgedetail;
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getDetailData(this, _id, isRefresh);
    }

    @Override
    protected KnowledgeDetailPresenter initPresenter() {
        QMUIStatusBarHelper.translucent(this);// 沉浸式状态栏
        findViewById();
        mActivity = KnowledgeDetailActivity.this;
        presenter = new KnowledgeDetailPresenter(this);
        return presenter;
    }
    private void findViewById() {
        scrollView = this.findViewById(R.id.scrollView);
        tvfTitle = this.findViewById(R.id.tvfTitle);
        imgfHead = this.findViewById(R.id.imgfCustomerHead);
        tvfUserName = this.findViewById(R.id.tvfUserName);
        tvfPubTime = this.findViewById(R.id.tvfPublishTime);
        webView = this.findViewById(R.id.webView);
        llfFj = this.findViewById(R.id.llfFj);
        fjRecyclerView = this.findViewById(R.id.fjRecyclerView);
        llfComment = this.findViewById(R.id.llfComment);
        collectionVolume = this.findViewById(R.id.collectionVolume);
        thumbsupVolume = this.findViewById(R.id.thumbsupVolume);
        rlvcomment = this.findViewById(R.id.rlvcomment);
        tvfRead = this.findViewById(R.id.readingVolumetxt);
        tvfCommot = this.findViewById(R.id.commotVolumetxt);
        tvfThumb = this.findViewById(R.id.thumbsupVolumetxt);
        tvfCollect = this.findViewById(R.id.collectionVolumetxt);
        rlfBottom = this.findViewById(R.id.rlfBottom);
    }
    @Override
    protected void initViews() {
        initToolbar(toolbar, "知识详情", R.mipmap.more, this);
        Bundle bundle = getIntent().getExtras();
        _id = bundle.getString("ITEM_ID");
        listPositon = bundle.getInt("LIST_POSITION");
        last_viewType = bundle.getString("LAST_VIEWTYPE");
        if(last_viewType.contains("MyAddedKnowLedgeActivity")){//从我添加的知识跳转而来
            auditStatus = bundle.getString("AUDITSTATUS", "");
            MyAddedKnowLedgeBean addedKnowLedgeBean = bundle.getParcelable("ITEM_BEAN");
            if(addedKnowLedgeBean!=null){
                publishTime = addedKnowLedgeBean.getCreateTime();
                initUserInfo(addedKnowLedgeBean.getTitle(), addedKnowLedgeBean.getUserInfo());
            }
        }else {
            auditStatus = "";
            KnowledgeBean knowledgeBean = null;
            try {
                knowledgeBean = bundle.getParcelable("ITEM_BEAN");
            }catch (Exception e){}
            if(knowledgeBean!=null){
                publishTime = knowledgeBean.getPublishTime();
                initUserInfo(knowledgeBean.getTitle(), knowledgeBean.getUserInfo());
            }
        }
        mSwipeRefresh.setEnableLoadmore(false);
        updateViews(false);
        rlfCommotVolume = findViewById(R.id.commotVolume);
        rlfCommotVolume.setOnClickListener(this);
        edtComment = findViewById(R.id.edtComment);
        edtComment.setKeyListener(null);
        if(auditStatus.equals("")||auditStatus.equals("审核通过")){
            rlfBottom.setVisibility(View.VISIBLE);
            edtComment.setOnClickListener(this);
            collectionVolume.setOnClickListener(this);
            thumbsupVolume.setOnClickListener(this);
        }else {
            rlfBottom.setVisibility(View.GONE);
            toolbar.findViewById(R.id.imgfRight).setVisibility(View.GONE);
        }
    }
    private void initUserInfo(String title, KUserInfo userInfo){
        if (userInfo != null) {
            tvfTitle.setText(Html.fromHtml(title));
            tvfUserName.setText(userInfo.getNickName());
            if (userInfo.getAvatar().equals("")) {
                imgfHead.setImageResource(R.mipmap.avatar_default);
            } else {
                String imageUrl = userInfo.getAvatar();
                if (!imageUrl.contains("http")) {
                    imageUrl = RepApp.DOWNLOAD_FILE + imageUrl;
                }
                Picasso.with(this).load(imageUrl)
                        .placeholder(R.mipmap.avatar_default)
                        .error(R.mipmap.avatar_default)
                        .into(imgfHead);
            }
        }
        if(publishTime>0) {
            tvfPubTime.setText(DateTools.getTimestamp(publishTime, "yyyy年MM月dd日"));
        }
    }

    @Override
    public void showDetailData(KnowledgeDetailBean detailBean) {
        finishRefresh();
        if (detailBean != null) {
            if(detailBean.getPublishTime()>0){
                publishTime = detailBean.getPublishTime();
            }
            initUserInfo(detailBean.getTitle(), detailBean.getUserInfo());
            shareUrl = detailBean.getContent();
            boolean flag = false;
            if(shareUrl.contains("<img src=")){
                shareUrl = shareUrl.replace("<img src=","<img width=100% height=auto src=");
                flag = true;
            }
            if(shareUrl.contains("<video src=")){
                shareUrl = shareUrl.replace("<video src=","<video width=100% height=auto src=");
                flag = true;
            }
            if(!flag){
                shareUrl = "<p style=\"text-indent:0em;margin:8px auto 10px auto;\"><font style=\"font-size:14.000000;color:#333333\">"+ shareUrl;
            }else {
                shareUrl = shareUrl.replace("#000000","#333333").replace("font-size:17.","font-size:14.");
            }
            WebSettings settings = webView.getSettings();//获取WebSettings对象
            settings .setJavaScriptEnabled(true);
            webView.setWebViewClient(new MyWebViewClient());
            // 支持js
            webView.addJavascriptInterface(new MJavascriptInterface(this), "videolistener");
            webView.loadDataWithBaseURL(null,shareUrl,"text/html; charset=UTF-8",null,null);
            kdBean = detailBean;
            new DataHandler().sendEmptyMessage(0);
            new refreshNums().sendEmptyMessage(0);
            showDataToView(null);
            scrollView.setVisibility(View.VISIBLE);
        }
        presenter.getCommentList(mActivity, _id, "1", false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //增加浏览数量
                presenter.addViewCount(mActivity, _id);
            }
        }, 2000);
    }

    @Override
    public void setGetComments(RepPageTurn currentpT, ArrayList<CommentBean> commentList, boolean isRepost) {
        commentBeans = commentList;
//        if (currentpT.getPageNo() <= 1) {//刷新
//            //pageTurn = currentpT;
//            commentBeans = commentList;
//        } else {//加载更多
//            if (isRepost && commentList != null) {
//                for (int i = 0; i < commentBeans.size(); i++) {
//                    for (int j = 0; j < commentList.size(); )
//                        if (commentList.get(i).getId().equals(commentList.get(j).getId())) {
//                            commentBeans.get(i).setReplyList(commentList.get(j).getReplyList());
//                        }
//                }
//            } else {
//               // pageTurn = currentpT;
//                commentBeans.addAll(commentList);
//            }
//        }
        finishRefresh();
        if(commentBeans.size()>0){
            llfComment.setVisibility(View.VISIBLE);
        }
        //评论留言
        if(commentListAdapter==null) {
            manager2 = new GridLayoutManager(mActivity, 1);
            rlvcomment.setLayoutManager(manager2);
            commentListAdapter = new CommentListAdapter(mActivity, commentBeans, listListener, detailListener, auditStatus);
            rlvcomment.setAdapter(commentListAdapter);
            rlvcomment.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
        }
        commentListAdapter.notifyDataSetChanged(commentBeans);
//        if(isRepost){
//            if(commentListAdapter.getItemCount() > comment_Index+1){
//                commentListAdapter.MoveToPosition(manager2, rlvcomment, comment_Index+1);
//            }else {
//                commentListAdapter.MoveToPosition(manager2, rlvcomment, comment_Index);
//            }
//        }else if (pageTurn.getPageNo() > 1)
//            commentListAdapter.MoveToPosition(manager2, rlvcomment, pageTurn.getPageNo() * 10);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ivfLeft) {
            InFinish();

        } else if (i == R.id.imgfRight) {
            PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
                        @Override
                        public void permissionGranted(@NonNull String[] permissions) {
                            new FileUtils.downloadImageAndShareAsyncTask(mActivity).execute("欢迎光临数夫家具软件", "分享", "http://www.fdatacraft.com", shareUrl);
                        }

                        @Override
                        public void permissionDenied(@NonNull String[] permissions) {
                        }
                    }, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

        } else if (i == R.id.thumbsupVolume) {
            presenter.like(mActivity, _id, listPositon);

        } else if (i == R.id.collectionVolume) {
            presenter.collect(mActivity, _id, listPositon);

        } else if (i == R.id.edtComment) {
            String text = "";
            try {
                text = edtComment.getText().toString().trim();
            } catch (Exception e) {
            }
            Intent intent = new Intent(mActivity, EditToSendActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("BUTTON_TYPE", 3);
            bundle.putString("DEFAULTSTRING", text);
            bundle.putParcelable("KNOWLEDGEDETAIL", kdBean);
            intent.putExtras(bundle);
            startActivityForResult(intent, DataTools.Knowledge.CLICK_TO_DETAIL);
        }else if(i == R.id.commotVolume){
        if(scrollView.getVisibility() == View.VISIBLE){
            scrollView.scrollTo(0, webView.getBottom());
        }
    }
    }

    @Override
    public void showNoDataHint(String msg) {
        super.showNoDataHint(msg);
        if (msg != null && !msg.equals(""))
            MyToast.showFailToast(mActivity, msg);
    }

    @Override
    public void showNetError(String msg) {
        super.showNetError(msg);
        if (scrollView != null) {
            scrollView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void loadmoredata() {
//        super.loadmoredata();
//        if (pageTurn == null) {
//            finishRefresh();
//            return;
//        }
//        if (pageTurn.getPageNo() >= pageTurn.getNextPage()) {
//            finishRefresh();
////            if(pageTurn.getPageNo() >= 1) {
////                new Handler().postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        MyToast.showToast(mActivity, "没有更多评论了");
////                    }
////                },1500);
////            }
//        } else {
//            presenter.getCommentList(mActivity, _id, pageTurn.getNextPage() + "", false);
//        }
    }

    private View.OnClickListener listListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            comment_Index = (int) v.getTag(R.id.position);
            CommentBean commentBean = (CommentBean) v.getTag(R.id.item_object);
            Intent intent = new Intent(mActivity, EditToSendActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("BUTTON_TYPE", 1);
            bundle.putParcelable("COMMENT_BEAN", commentBean);
            intent.putExtras(bundle);
            startActivityForResult(intent, DataTools.Knowledge.CLICK_TO_DETAIL);

        }
    };
    private View.OnClickListener detailListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            commentdetail_Index = (int) v.getTag(R.id.child_position);
            ReplyBean replyBean = (ReplyBean) v.getTag(R.id.item_chile_object);
            Intent intent = new Intent(mActivity, EditToSendActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("BUTTON_TYPE", 2);
            bundle.putParcelable("REPLAYBEAN", replyBean);
            intent.putExtras(bundle);
            startActivityForResult(intent, DataTools.Knowledge.CLICK_TO_DETAIL);
        }
    };

    public static void setTextToEdit(String text) {
        if (text != null && !text.equals("")) {
            edtComment.setText(text);
            edtComment.setSelection(text.length());
        } else {
            edtComment.setText("");
        }
    }


    //点赞/取消点赞后
    @Override
    public void setAfterLike(boolean isSuccess, int position) {
        if (isSuccess) {
            String key = DataTools.getMapKey(KnowledgeFragment.sType);
            if (key.equals("hot")) {
                if (kdBean.getIsLike() == 0) {//点赞
                    int likecout = Integer.parseInt(kdBean.getLikeCount()) + 1;
                    ComTools.setTextWithDraw(mActivity, tvfThumb, likecout + "", R.mipmap.support2);
                    DataTools.hotList.get(position).setLikeCount(likecout + "");
                    DataTools.hotList.get(position).setIsLike(1);
                    kdBean.setIsLike(1);
                    kdBean.setLikeCount(likecout+"");
                } else {//取消点赞
                    int likecout = Integer.parseInt(kdBean.getLikeCount()) - 1;
                    if (likecout >= 0) {
                        ComTools.setTextWithDraw(mActivity, tvfThumb, likecout + "", R.mipmap.support);
                        DataTools.hotList.get(position).setLikeCount(likecout + "");
                        DataTools.hotList.get(position).setIsLike(0);
                        kdBean.setIsLike(0);
                        kdBean.setLikeCount(likecout+"");
                    }
                }
            } else {
                if (kdBean.getIsLike() == 0) {//点赞
                    int likecout = Integer.parseInt(kdBean.getLikeCount()) + 1;
                    ComTools.setTextWithDraw(mActivity, tvfThumb, likecout + "", R.mipmap.support2);
                    DataTools.ListKlMap.get(key).get(position).setLikeCount(likecout + "");
                    DataTools.ListKlMap.get(key).get(position).setIsLike(1);
                    kdBean.setIsLike(1);
                    kdBean.setLikeCount(likecout+"");
                } else {//取消点赞
                    int likecout = Integer.parseInt(kdBean.getLikeCount()) - 1;
                    if (likecout >= 0) {
                        ComTools.setTextWithDraw(mActivity, tvfThumb, likecout + "", R.mipmap.support);
                        DataTools.ListKlMap.get(key).get(position).setLikeCount(likecout + "");
                        DataTools.ListKlMap.get(key).get(position).setIsLike(0);
                        kdBean.setIsLike(0);
                        kdBean.setLikeCount(likecout+"");
                    }
                }
            }
        }
    }

    //收藏/取消收藏后
    @Override
    public void setAfterCollect(boolean isSuccess, int positon) {
        if (isSuccess) {
            if (kdBean.getIsCollect() == 0) {
                int collectCount = Integer.parseInt(kdBean.getCollectCount()) + 1;
                kdBean.setIsCollect(1);
                kdBean.setCollectCount(collectCount+"");
                ComTools.setTextWithDraw(mActivity, tvfCollect, collectCount + "", R.mipmap.collect2);
            } else {
                int collectCount = Integer.parseInt(kdBean.getCollectCount()) - 1;
                if(collectCount>=0) {
                    kdBean.setIsCollect(0);
                    kdBean.setCollectCount(collectCount+"");
                    ComTools.setTextWithDraw(mActivity, tvfCollect, collectCount + "", R.mipmap.collect);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case DataTools.Knowledge.CLICK_TO_DETAIL:
                    new refreshNums().sendEmptyMessage(0);
                    int buttonType = data.getIntExtra("BUTTON_TYPE", 0);
                    switch (buttonType) {
                        case 1:
                        case 2://回复后重新获取评论数据
                            presenter.getCommentList(mActivity, _id, "1", true);
                            break;
                        case 3://发表评论后重新获取评论数据
                            presenter.getCommentList(mActivity, _id, "1", false);
                            break;
                        default:
                            new refreshNums().sendEmptyMessage(0);
                            break;
                    }
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        InFinish();
    }

    private void InFinish() {
        Intent intent = new Intent();
        if(last_viewType.contains("BaseKnowledgeFragment")){
            intent.setClass(mActivity, RepositoryMainActivity.class);
        }else if(last_viewType.contains("SearchActivity")){
            intent.setClass(mActivity, SearchActivity.class);
        }else if(last_viewType.contains("MyFavoriteActivity")){
            intent.setClass(mActivity, MyFavoriteActivity.class);
        }else if(last_viewType.contains("MyAddedKnowLedgeActivity")){
            intent.setClass(mActivity, MyAddedKnowLedgeActivity.class);
        }else if(last_viewType.contains("OnlineStaffActivity")){
            intent.setClass(mActivity, OnlineStaffActivity.class);
        }else {
            finish();
        }
        intent.putExtra("POSITION", listPositon);
        mActivity.setResult(Activity.RESULT_OK, intent);//Result_OK 表示结果响应成功
        mActivity.finish();
    }

    /**
     * 属性附件列表
     */
    public class DataHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //中间那段
            manager1 = new GridLayoutManager(mActivity, 1);
            fjRecyclerView.setLayoutManager(manager1);
            fileList = kdBean.getFileList();
            if (fileList != null && fileList.size() > 0) {
                llfFj.setVisibility(View.VISIBLE);
                //刷新附件列表
                filelListAdapter = new FilelListAdapter(mActivity, fileList);
                fjRecyclerView.setAdapter(filelListAdapter);
                fjRecyclerView.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
            }else {
                llfFj.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 刷新底部数据
     */
    class refreshNums extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(kdBean!=null) {
                ComTools.setTextWithDraw(mActivity, tvfRead, kdBean.getViewCount(), R.mipmap.read);
                ComTools.setTextWithDraw(mActivity, tvfCommot, kdBean.getCommentCount(), R.mipmap.comment);
                if (kdBean.getIsLike() == 0) {
                    ComTools.setTextWithDraw(mActivity, tvfThumb, kdBean.getLikeCount(), R.mipmap.support);
                } else {
                    ComTools.setTextWithDraw(mActivity, tvfThumb, kdBean.getLikeCount(), R.mipmap.support2);
                }
                if (kdBean.getIsCollect() == 0) {
                    ComTools.setTextWithDraw(mActivity, tvfCollect, kdBean.getCollectCount(), R.mipmap.collect);
                } else {
                    ComTools.setTextWithDraw(mActivity, tvfCollect, kdBean.getCollectCount(), R.mipmap.collect2);
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    /**
     * 注入js的两个核心类和接口
     */
    public class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            addImageClickListener(view);//待网页加载完全后设置图片点击的监听方法
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
        }

        private void addImageClickListener(WebView webView) {
            webView.loadUrl("javascript:(function(){ "
                    + "var objs = document.getElementsByTagName(\"video\");"
                    + " var array=new Array(); " + " for(var j=0;j<objs.length;j++){ "
                    + "array[j]=objs[j].src;" + " }  "
                    + "for(var i=0;i<objs.length;i++){"
                    +"objs[i].i=i;"
                    + "objs[i].onclick=function(){  " +
                    "window.videolistener.playVideo(this.poster,this.src,this.i);" + "}  " + "}    })()");
        }
    }
    public class MJavascriptInterface {
        private Context context;

        public MJavascriptInterface(Context context) {
            this.context = context;
        }

        //播放视频
        @android.webkit.JavascriptInterface
        public void playVideo(String fameAtPath, String videoPath, int position) {
            Intent mIntent = new Intent(context, PlayVideoActivity.class);
            mIntent.putExtra("FAMEATPATH", fameAtPath);
            mIntent.putExtra("VIDEOPATH", videoPath);
            context.startActivity(mIntent);
        }
    }
}
