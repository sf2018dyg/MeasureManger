package com.soonfor.measuremanager.afflatus.activity;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.hawk.Hawk;
import com.pkmmte.view.CircularImageView;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.adpter.CommentListAdapter;
import com.soonfor.measuremanager.afflatus.adpter.DesignCaseProgrammeListAdapter;
import com.soonfor.measuremanager.afflatus.bean.CommentBean;
import com.soonfor.measuremanager.afflatus.bean.CommentDetailBean;
import com.soonfor.measuremanager.afflatus.bean.DesignCaseBean;
import com.soonfor.measuremanager.afflatus.bean.ProgrammeBean;
import com.soonfor.measuremanager.afflatus.bean.ProgrammeDetail;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.DensityUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.ViewTools;
import com.soonfor.measuremanager.view.SoftKeyBoardListener;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/22 11:06
 * 邮箱：suibozhu@139.com
 * 案例详情
 */
public class DesignCaseDetailActivity extends BaseActivity implements AsyncUtils.AsyncCallback {

    Context mContext;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.imgShare)
    ImageView imgShare;
    @BindView(R.id.txttitle)
    TextView txttitle;
    @BindView(R.id.imgpath)
    ImageView imgpath;
    @BindView(R.id.ivhead)
    CircularImageView ivhead;
    @BindView(R.id.designerName)
    TextView designerName;
    @BindView(R.id.postDate)
    TextView postDate;
    @BindView(R.id.txtdesc)
    TextView txtdesc;
    @BindView(R.id.txtchangjing)
    TextView txtchangjing;
    @BindView(R.id.txtfengge)
    TextView txtfengge;
    @BindView(R.id.txtloupan)
    TextView txtloupan;
    @BindView(R.id.txtfujian)
    TextView txtfujian;
    @BindView(R.id.txthuxing)
    TextView txthuxing;
    @BindView(R.id.txtjiawei)
    TextView txtjiawei;
    /* @BindView(R.id.rlvLocschemes)
     RecyclerView rlvLocScheme;*/
    GridLayoutManager manager1, manager2;
    DesignCaseProgrammeListAdapter adapter;
    List<ProgrammeBean> programmeList;
    List<ProgrammeDetail> programmeDetailList;
    @BindView(R.id.rlpanorama)
    RelativeLayout rlpanorama;
    @BindView(R.id.readingVolume)
    RelativeLayout readingVolume;
    @BindView(R.id.collectionVolume)
    RelativeLayout collectionVolume;
    @BindView(R.id.thumbsupVolume)
    RelativeLayout thumbsupVolume;
    @BindView(R.id.shareVolume)
    RelativeLayout shareVolume;
    @BindView(R.id.rlvcomment)
    RecyclerView rlvcomment;
    List<CommentBean> commentBeans;
    List<CommentDetailBean> commentDetailBeans;
    CommentListAdapter commentListAdapter;
    @BindView(R.id.txtDescHtml)
    TextView txtDescHtml;
    @BindView(R.id.readingVolumetxt)
    TextView readingVolumetxt;
    @BindView(R.id.collectionVolumetxt)
    TextView collectionVolumetxt;
    @BindView(R.id.thumbsupVolumetxt)
    TextView thumbsupVolumetxt;
    @BindView(R.id.shareVolumetxt)
    TextView shareVolumetxt;
    @BindView(R.id.edtHuifuComment)
    EditText edtHuifuComment;
    @BindView(R.id.relvJustLook)
    RelativeLayout relvJustLook;
    @BindView(R.id.relvhuifu)
    RelativeLayout relvhuifu;
    @BindView(R.id.edtComment)
    TextView edtComment;
    boolean huifuNomal = true;
    DesignCaseBean db;
    int flagLike;//是否收藏(1:是,0:否)

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_design_case_detail;
    }

    @Override
    protected void initViews() {
        try {
            mContext = DesignCaseDetailActivity.this;
            db = (DesignCaseBean) getIntent().getSerializableExtra("DesignCaseBean");
            if (db != null) {
                ImageUtil.loadCaselibImage(mContext, db.getSurfacePlotUrl(), imgpath);
//                txttitle.setText(CommonUtils.formatStr(db.getTitle()));
//                designerName.setText(CommonUtils.formatStr(db.getCreator()));
//                try {
//                    postDate.setText(DateTool.longToString(db.getCreateTime(), "yyyy-MM-dd"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                txtdesc.setText(CommonUtils.formatStr(db.getRemark()));
//                txtchangjing.setText(CommonUtils.formatStr(db.getSpaceName()));
//                txtfengge.setText(CommonUtils.formatStr(db.getStyleName()));
//                txtloupan.setText(CommonUtils.formatStr(db.getBuildName()));
//                txthuxing.setText(CommonUtils.formatStr(db.getHouseName()) + " " + CommonUtils.formatStr(db.getAreaName()));
//                txtjiawei.setText(CommonUtils.formatStr(db.getPriceName()));
//                //txtfujian.setText(CommonUtils.formatStr(db.getContent()));
//                txtDescHtml.setText(Html.fromHtml(CommonUtils.formatStr(db.getContent())));
//
//                readingVolumetxt.setText(CommonUtils.formatStr(db.getViews()));
//                collectionVolumetxt.setText(CommonUtils.formatStr(db.getCollects()));
//                thumbsupVolumetxt.setText(CommonUtils.formatStr(db.getComments()));
//                shareVolumetxt.setText("0");
            }
        /*//中间那段
        manager1 = new GridLayoutManager(mContext, 1);
        //rlvLocScheme.setLayoutManager(manager1);
        programmeList = new ArrayList<ProgrammeBean>();
        me_evaluate_ranking = new DesignCaseProgrammeListAdapter(mContext, programmeList);
        //rlvLocScheme.setAdapter(me_evaluate_ranking);
        //rlvLocScheme.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动

        //设置数据源
        me_evaluate_ranking.notifyDataSetChanged(getProgrammeDatas());*/

            //ViewGroup.LayoutParams layoutParams = rlvLocScheme.getLayoutParams();
            //layoutParams.height = getTotleHeight();
            //rlvLocScheme.setLayoutParams(layoutParams);

            imgBack.setFocusable(true);
            imgBack.setFocusableInTouchMode(true);
            imgBack.requestFocus();

            //评论留言
            manager2 = new GridLayoutManager(mContext, 1);
            rlvcomment.setLayoutManager(manager2);
            commentListAdapter = new CommentListAdapter(mContext, commentBeans, huifuListener, huifuRepeatListener);
            rlvcomment.setAdapter(commentListAdapter);
            rlvcomment.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
            //再从接口取一次案例详情
            Request.getById(this, db.getId(), this);
            //获取评论列表
            Request.pageCommentList(this, db.getId(), this);

            SoftKeyBoardListener.setListener(DesignCaseDetailActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
                @Override
                public void keyBoardShow(int height) {
                    //Toast.makeText(DesignCaseDetailActivity.this, "键盘显示 高度" + height, Toast.LENGTH_SHORT).show();
                    ExtendedMode(true);
                }

                @Override
                public void keyBoardHide(int height) {
                    //Toast.makeText(DesignCaseDetailActivity.this, "键盘隐藏 高度" + height, Toast.LENGTH_SHORT).show();
                    ExtendedMode(false);
                }
            });
        }catch (Exception e){
            e.getMessage();
            NLogger.e("案例库错误：" + e.getMessage());
        }
    }

    @OnClick({R.id.imgBack, R.id.imgShare, R.id.txtfujian, R.id.rlpanorama, R.id.readingVolume, R.id.collectionVolume, R.id.thumbsupVolume, R.id.shareVolume, R.id.edtComment, R.id.btn_huifu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgShare:
                MyToast.showToast(mContext, "分享..");
                break;
            case R.id.txtfujian:
                MyToast.showToast(mContext, "我是方案..");
                break;
            case R.id.rlpanorama:
                MyToast.showToast(mContext, "全景..");
                break;
            case R.id.readingVolume:
                //MyToast.showToast(mContext, "阅读量..");
                break;
            case R.id.collectionVolume:
                //MyToast.showToast(mContext, "收藏量..");
                if (flagLike == 0) {
                    Request.saveCollects(DesignCaseDetailActivity.this, db.getId(), 1, DesignCaseDetailActivity.this);
                } else if (flagLike == 1) {
                    Request.saveCollects(DesignCaseDetailActivity.this, db.getId(), 0, DesignCaseDetailActivity.this);
                }
                break;
            case R.id.thumbsupVolume:
                Request.saveLikes(DesignCaseDetailActivity.this, db.getId(), DesignCaseDetailActivity.this);
                break;
            case R.id.shareVolume:
                MyToast.showToast(mContext, "分享量..");
                break;
            case R.id.edtComment:
                ExtendedMode(true);
                break;
            case R.id.btn_huifu:
                //保存回复
                if (huifuNomal) {//普通的回复
                    if (db != null) {
                        Request.saveComment(DesignCaseDetailActivity.this, db.getId(), edtHuifuComment.getText().toString().trim(), DesignCaseDetailActivity.this);
                    }
                } else {//针对某人的回复
                    if (tmpBean != null && tmpRepBean == null) {
                        Request.saveCommentReply(DesignCaseDetailActivity.this, tmpBean.getId(), tmpBean.getCreator(), edtHuifuComment.getText().toString().trim(), DesignCaseDetailActivity.this);
                    } else if (tmpBean == null && tmpRepBean != null) {
                        Request.saveCommentReply(DesignCaseDetailActivity.this, tmpRepBean.getBigID(), tmpRepBean.getReplyUserId(), edtHuifuComment.getText().toString().trim(), DesignCaseDetailActivity.this);
                    }
                }
                break;
        }
    }


    @Override
    protected void updateViews(boolean isRefresh) {

    }

    /**
     * 更新视图
     */
    public void showMsg(String msg) {
        MyToast.showToast(mContext, msg);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        Gson gson = new Gson();
        switch (requestCode) {
            case Request.GETBYID:
                JsonUtils.analysisJsonHead( object.toString(), new JsonUtils.OperateData() {                    @Override                    public void doingInFail(String msg) {                        showNoDataHint(msg);                    }
                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            JSONObject o = new JSONObject(data);
                            txttitle.setText(o.getString("title"));
                            designerName.setText(o.getString("creator"));
                            try {
                                postDate.setText(DateTool.getTimeTimestamp(o.getString("createTime"), "yyyy-MM-dd"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            txtdesc.setText(CommonUtils.formatStr(o.getString("remark")));
                            txtchangjing.setText(CommonUtils.formatStr(o.getString("spaceName")));
                            txtfengge.setText(CommonUtils.formatStr(o.getString("styleName")));
                            txtloupan.setText(CommonUtils.formatStr(o.getString("buildName")));
                            txthuxing.setText(CommonUtils.formatStr(o.getString("houseName")) + " " + CommonUtils.formatStr(o.getString("areaName")));
                            txtjiawei.setText(CommonUtils.formatStr(o.getString("priceName")));
                            //txtfujian.setText(CommonUtils.formatStr(db.getContent()));
                            txtDescHtml.setText(Html.fromHtml(CommonUtils.formatStr(o.getString("content"))));

                            readingVolumetxt.setText(CommonUtils.formatStr(o.getString("views")));
                            collectionVolumetxt.setText(CommonUtils.formatStr(o.getString("collects")));
                            thumbsupVolumetxt.setText(CommonUtils.formatStr(o.getString("likes")));
                            shareVolumetxt.setText("0");

                            flagLike = o.getInt("flagLike");//是否收藏

                            if (flagLike == 1) {
                                ViewTools.returnDrawable(mContext, collectionVolumetxt, 1, R.mipmap.collect2, new int[]{0, 0, DensityUtils.dp2px(mContext, 13), DensityUtils.dp2px(mContext, 13)});
                            } else if (flagLike == 0) {
                                ViewTools.returnDrawable(mContext, collectionVolumetxt, 1, R.mipmap.icn_shouchang, new int[]{0, 0, DensityUtils.dp2px(mContext, 13), DensityUtils.dp2px(mContext, 13)});
                            }
                        } catch (Exception e) {
                            showMsg(e.toString());
                        }
                    }
                });
                break;
            case Request.PAGECOMMENTLIST:
                JsonUtils.analysisJsonHead( object.toString(), new JsonUtils.OperateData() {                    @Override                    public void doingInFail(String msg) {                        showNoDataHint(msg);                    }
                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            JSONObject o = new JSONObject(data);
//                            PageTurnBean pageTurnBean = gson.fromJson(o.getString("pageTurn"), new TypeToken<PageTurnBean>() {
//                            }.getType());
                            commentBeans = gson.fromJson(o.getString("list"), new TypeToken<List<CommentBean>>() {
                            }.getType());
                            if (commentBeans != null) {
                                //设置数据源
                                commentListAdapter.notifyDataSetChanged(commentBeans);
                            }
                        } catch (Exception e) {
                            showMsg(e.toString());
                        }
                    }
                });
                break;
            case Request.SAVECOMMENT:
                //获取评论列表
                Request.pageCommentList(this, db.getId(), this);
                ExtendedMode(false);
                edtComment.setText("不写点什么吗?");
                edtComment.setTextColor(getResources().getColor(R.color.gray));
                edtHuifuComment.setText("");
                break;
            case Request.SAVECOMMENTREPLY:
                //获取评论列表
                Request.pageCommentList(this, db.getId(), this);
                ExtendedMode(false);
                edtComment.setText("不写点什么吗?");
                edtComment.setTextColor(getResources().getColor(R.color.gray));
                edtHuifuComment.setText("");
                break;
            case Request.SAVECOLLECTS:
                //Request.getById(this, db.getId(), this);
                if (flagLike == 0) {
                    int count = Integer.parseInt(CommonUtils.formatStr(db.getCollects()));
                    count++;
                    db.setCollects(count + "");
                    ViewTools.returnDrawable(mContext, collectionVolumetxt, 1, R.mipmap.collect2, new int[]{0, 0, DensityUtils.dp2px(mContext, 13), DensityUtils.dp2px(mContext, 13)});
                    flagLike = 1;
                } else if (flagLike == 1) {
                    int count = Integer.parseInt(CommonUtils.formatStr(db.getCollects()));
                    count--;
                    db.setCollects(count < 0 ? "0" : (count + ""));
                    ViewTools.returnDrawable(mContext, collectionVolumetxt, 1, R.mipmap.icn_shouchang, new int[]{0, 0, DensityUtils.dp2px(mContext, 13), DensityUtils.dp2px(mContext, 13)});
                    flagLike = 0;
                }
                collectionVolumetxt.setText(CommonUtils.formatStr(db.getCollects()));
                break;
            case Request.SAVELIKES:
                //Request.getById(this, db.getId(), this);
                int count = Integer.parseInt(CommonUtils.formatStr(db.getLikes()));
                count++;
                db.setLikes(count + "");
                thumbsupVolumetxt.setText(count + "");
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
    }

    private void ExtendedMode(boolean extended) {
        if (extended) {
            edtHuifuComment.setVisibility(View.VISIBLE);
            relvJustLook.setVisibility(View.GONE);
            relvhuifu.setVisibility(View.VISIBLE);
            String tmp = edtComment.getText().toString().trim();
            if (!tmp.equals("")) {
                if (!tmp.equals("不写点什么吗?")) {
                    edtHuifuComment.setText(tmp);
                }
            } else {
                edtHuifuComment.setText("");
            }
        } else {
            edtHuifuComment.setVisibility(View.GONE);
            relvJustLook.setVisibility(View.VISIBLE);
            relvhuifu.setVisibility(View.GONE);
            String tmp = edtHuifuComment.getText().toString().trim();
            if (!tmp.equals("")) {
                edtComment.setText(tmp);
            } else {
                edtComment.setText("不写点什么吗?");
                edtComment.setTextColor(getResources().getColor(R.color.gray));
            }
        }
    }

    CommentBean tmpBean = null;
    public View.OnClickListener huifuListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txtclickhuifu:
                    ExtendedMode(true);
                    huifuNomal = false;
                    tmpBean = (CommentBean) v.getTag();
                    break;
            }
        }
    };

    CommentDetailBean tmpRepBean = null;
    String tmpID = "";
    public View.OnClickListener huifuRepeatListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txtclickhuifu:
                    ExtendedMode(true);
                    huifuNomal = false;
                    tmpRepBean = (CommentDetailBean) v.getTag();
                    break;
            }
        }
    };
}
