package com.soonfor.repository.ui.activity.knowledge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.soonfor.repository.R;
import com.soonfor.repository.R;

import com.soonfor.repository.model.knowledge.CommentBean;
import com.soonfor.repository.model.knowledge.ReplyBean;
import com.soonfor.repository.presenter.knowledge.SendCommintPresenter;
import com.soonfor.repository.tools.ComTools;
import com.soonfor.repository.tools.DataTools;
import com.soonfor.repository.tools.MyToast;
import com.soonfor.repository.ui.fragment.KnowledgeFragment;
import com.soonfor.repository.view.knowledge.ISendCommentView;


/**
 * 作者：DC-DingYG on 2018-07-26 20:39
 * 邮箱：dingyg012655@126.com
 */
public class EditToSendActivity extends Activity implements ISendCommentView, View.OnClickListener {

    private EditToSendActivity mActivity;
    Button mButton;
    EditText etfSend;
    ImageView image_kong;
    LinearLayout llfComment;
    TextView tvfRead;
    TextView tvfCollect;
    TextView tvfThumb;
    TextView tvfCommot;
    RelativeLayout collectionVolume;
    RelativeLayout thumbsupVolume;
    CommentBean commentBean;
    ReplyBean replyBean;
    String inputS = "";
    int buttonType = 0;
    SendCommintPresenter presenter;
    public LoadingDailog mLoadingDialog;
    protected String actionName = "发送中...";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendcomment);
        if (mLoadingDialog == null) {
            LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                    .setMessage(actionName)
                    .setCancelable(true)
                    .setCancelOutside(true);
            mLoadingDialog = loadBuilder.create();
        }
        findViewById();
        mActivity = EditToSendActivity.this;
        presenter = new SendCommintPresenter(this);
        initViews();
    }
    private void findViewById() {
        mButton = this.findViewById(R.id.mbfSend);
        etfSend = this.findViewById(R.id.etfSend);
        image_kong = this.findViewById(R.id.image_kong);
        llfComment = this.findViewById(R.id.llfComment);
        collectionVolume = this.findViewById(R.id.collectionVolume);
        thumbsupVolume = this.findViewById(R.id.thumbsupVolume);
        tvfRead = this.findViewById(R.id.readingVolumetxt);
        tvfCommot = this.findViewById(R.id.commotVolumetxt);
        tvfThumb = this.findViewById(R.id.thumbsupVolumetxt);
        tvfCollect = this.findViewById(R.id.collectionVolumetxt);

    }

    protected void initViews() {
        Bundle bundle = getIntent().getExtras();
        buttonType = bundle.getInt("BUTTON_TYPE", 0);
        switch (buttonType) {
            case 1://回复评论1
                llfComment.setVisibility(View.GONE);
                mButton.setText("发送");
                commentBean = bundle.getParcelable("COMMENT_BEAN");
                if (commentBean != null) {
                    etfSend.setHint("回复" + commentBean.getUserInfo().getNickName() + "：");
                }
                break;
            case 2://回复评论2
                llfComment.setVisibility(View.GONE);
                mButton.setText("发送");
                replyBean = bundle.getParcelable("REPLAYBEAN");
                if (replyBean != null) {
                    etfSend.setHint("回复" + replyBean.getUser() + "：");
                }
                break;
            case 3://发表评论
                llfComment.setVisibility(View.VISIBLE);
                mButton.setText("发表");
                inputS = bundle.getString("DEFAULTSTRING");
                if (!inputS.equals("")) {
                    etfSend.setText(inputS);
                    etfSend.setSelection(inputS.length());
                    mButton.setEnabled(true);
                } else {
                    etfSend.setHint("不写点什么吗？");
                }
                if (KnowledgeDetailActivity.kdBean != null) {
                    ComTools.setTextWithDraw(mActivity, tvfRead, KnowledgeDetailActivity.kdBean.getViewCount(), R.mipmap.read);
                    ComTools.setTextWithDraw(mActivity, tvfCommot, KnowledgeDetailActivity.kdBean.getCommentCount(), R.mipmap.comment);
                    if (KnowledgeDetailActivity.kdBean.getIsLike() == 1)
                        ComTools.setTextWithDraw(mActivity, tvfThumb, KnowledgeDetailActivity.kdBean.getLikeCount(), R.mipmap.support2);
                    else
                        ComTools.setTextWithDraw(mActivity, tvfThumb, KnowledgeDetailActivity.kdBean.getLikeCount(), R.mipmap.support);
                    if (KnowledgeDetailActivity.kdBean.getIsCollect() == 1)
                        ComTools.setTextWithDraw(mActivity, tvfCollect, KnowledgeDetailActivity.kdBean.getCollectCount(), R.mipmap.collect2);
                    else
                        ComTools.setTextWithDraw(mActivity, tvfCollect, KnowledgeDetailActivity.kdBean.getCollectCount(), R.mipmap.collect);
                }
                break;
        }
        image_kong.setOnClickListener(this);
        collectionVolume.setOnClickListener(this);
        thumbsupVolume.setOnClickListener(this);
        mButton.setOnClickListener(this);
        etfSend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() <= 0) {
                    inputS = "";
                    mButton.setEnabled(false);
                } else {
                    mButton.setEnabled(true);
                    inputS = s.toString().trim();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (buttonType == 3) {
            KnowledgeDetailActivity.setTextToEdit(inputS);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.thumbsupVolume) {
            presenter.like(mActivity, KnowledgeDetailActivity.kdBean.getId());

        } else if (i == R.id.collectionVolume) {
            presenter.collect(mActivity, KnowledgeDetailActivity.kdBean.getId());

        } else if (i == R.id.mbfSend) {
            if (buttonType == 1) {//回复1：回复贴子（带头像）
                if (commentBean != null)
                    presenter.ReplyToComment(mActivity, commentBean.getId(), commentBean.getCreator(), inputS);
            } else if (buttonType == 2) {//回复2：回复回复（不带头像）
                if (replyBean != null)
                    presenter.ReplyToComment(mActivity, replyBean.get_commentid(), replyBean.getUserId(), inputS);
            } else if (buttonType == 3) {//发表
                presenter.saveComment(mActivity, KnowledgeDetailActivity._id, inputS);
            }

        } else if (i == R.id.image_kong) {
            Intent intent = new Intent(mActivity, KnowledgeDetailActivity.class);
            intent.putExtra("BUTTON_TYPE", 5);
            mActivity.setResult(Activity.RESULT_OK, intent);//Result_OK 表示结果响应成功
            mActivity.finish();

        }
    }

    /**
     * 请求回复接口后操作
     *
     * @param isSuccess
     * @param data
     */
    @Override
    public void afterSaveReplay(boolean isSuccess, String data) {
        if (isSuccess) {
            String _id = data;
            MyToast.showSuccessToast(mActivity, "已成功回复！");
            inputS = "";
            Intent intent = new Intent(mActivity, KnowledgeDetailActivity.class);
            intent.putExtra("BUTTON_TYPE", buttonType);
            mActivity.setResult(Activity.RESULT_OK, intent);//Result_OK 表示结果响应成功
            mActivity.finish();
        } else {
            MyToast.showFailToast(mActivity, "保存回复失败: " + data);
        }
    }

    /**
     * 请求发表评论后操作
     *
     * @param isSuccess
     * @param data
     */
    @Override
    public void afterSaveComment(boolean isSuccess, String data) {
        if (isSuccess) {
            MyToast.showSuccessToast(mActivity, "已成功发表评论！");
            inputS = "";
            String key = DataTools.getMapKey(KnowledgeFragment.sType);
            if (key.equals("hot")) {
                int commCount = 1 + Integer.parseInt(DataTools.hotList.get(KnowledgeDetailActivity.listPositon).getCommentCount());
                DataTools.hotList.get(KnowledgeDetailActivity.listPositon).setCommentCount(commCount + "");
                KnowledgeDetailActivity.kdBean.setCommentCount(commCount + "");
            } else {
                int commCount = 1 + Integer.parseInt(DataTools.ListKlMap.get(key).get(KnowledgeDetailActivity.listPositon).getCommentCount());
                DataTools.ListKlMap.get(key).get(KnowledgeDetailActivity.listPositon).setCommentCount(commCount + "");
                KnowledgeDetailActivity.kdBean.setCommentCount(commCount + "");
            }
            Intent intent = new Intent(mActivity, KnowledgeDetailActivity.class);
            intent.putExtra("BUTTON_TYPE", buttonType);
            mActivity.setResult(Activity.RESULT_OK, intent);//Result_OK 表示结果响应成功
            mActivity.finish();
        } else {
            MyToast.showFailToast(mActivity, "发表评论失败: " + data);
        }
    }

    //点赞/取消点赞后
    @Override
    public void setAfterLike(boolean isSuccess, int position) {
        if (isSuccess) {
            String key = DataTools.getMapKey(KnowledgeFragment.sType);
            if (key.equals("hot")) {
                if (KnowledgeDetailActivity.kdBean.getIsLike() == 0) {//点赞
                    int likecout = Integer.parseInt(KnowledgeDetailActivity.kdBean.getLikeCount()) + 1;
                    ComTools.setTextWithDraw(mActivity, tvfThumb, likecout + "", R.mipmap.support2);
                    DataTools.hotList.get(position).setLikeCount(likecout + "");
                    DataTools.hotList.get(position).setIsLike(1);
                    KnowledgeDetailActivity.kdBean.setLikeCount(likecout + "");
                    KnowledgeDetailActivity.kdBean.setIsLike(1);
                } else {//取消点赞
                    int likecout = Integer.parseInt(KnowledgeDetailActivity.kdBean.getLikeCount()) - 1;
                    if (likecout >= 0) {
                        ComTools.setTextWithDraw(mActivity, tvfThumb, likecout + "", R.mipmap.support);
                        DataTools.hotList.get(position).setLikeCount(likecout + "");
                        DataTools.hotList.get(position).setIsLike(0);
                        KnowledgeDetailActivity.kdBean.setLikeCount(likecout + "");
                        KnowledgeDetailActivity.kdBean.setIsLike(0);
                    }
                }
            } else {
                if (KnowledgeDetailActivity.kdBean.getIsLike() == 0) {//点赞
                    int likecout = Integer.parseInt(KnowledgeDetailActivity.kdBean.getLikeCount()) + 1;
                    ComTools.setTextWithDraw(mActivity, tvfThumb, likecout + "", R.mipmap.support2);
                    DataTools.ListKlMap.get(key).get(position).setLikeCount(likecout + "");
                    DataTools.ListKlMap.get(key).get(position).setIsLike(1);
                    KnowledgeDetailActivity.kdBean.setLikeCount(likecout + "");
                    KnowledgeDetailActivity.kdBean.setIsLike(1);
                } else {//取消点赞
                    int likecout = Integer.parseInt(KnowledgeDetailActivity.kdBean.getLikeCount()) - 1;
                    if (likecout >= 0) {
                        ComTools.setTextWithDraw(mActivity, tvfThumb, likecout + "", R.mipmap.support);
                        DataTools.ListKlMap.get(key).get(position).setLikeCount(likecout + "");
                        DataTools.ListKlMap.get(key).get(position).setIsLike(0);
                        KnowledgeDetailActivity.kdBean.setLikeCount(likecout + "");
                        KnowledgeDetailActivity.kdBean.setIsLike(1);
                    }
                }
            }
//            Intent intent = new Intent(mActivity, KnowledgeDetailActivity.class);
//            intent.putExtra("BUTTON_TYPE", 4);
//            mActivity.setResult(Activity.RESULT_OK, intent);//Result_OK 表示结果响应成功
//            mActivity.finish();
        }
    }

    //收藏/取消收藏后
    @Override
    public void setAfterCollect(boolean isSuccess, int positon) {
        if (isSuccess) {
            if (KnowledgeDetailActivity.kdBean.getIsCollect() == 0) {
                int collectCount = Integer.parseInt(KnowledgeDetailActivity.kdBean.getCollectCount()) + 1;
                ComTools.setTextWithDraw(mActivity, tvfCollect, collectCount + "", R.mipmap.collect2);
                KnowledgeDetailActivity.kdBean.setCollectCount(collectCount + "");
                KnowledgeDetailActivity.kdBean.setIsCollect(1);
            } else {
                int collectCount = Integer.parseInt(KnowledgeDetailActivity.kdBean.getCollectCount()) - 1;
                ComTools.setTextWithDraw(mActivity, tvfCollect, collectCount + "", R.mipmap.collect);
                KnowledgeDetailActivity.kdBean.setCollectCount(collectCount + "");
                KnowledgeDetailActivity.kdBean.setIsCollect(0);
            }
//            Intent intent = new Intent(mActivity, KnowledgeDetailActivity.class);
//            intent.putExtra("BUTTON_TYPE", 5);
//            mActivity.setResult(Activity.RESULT_OK, intent);//Result_OK 表示结果响应成功
//            mActivity.finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(mActivity, KnowledgeDetailActivity.class);
        intent.putExtra("BUTTON_TYPE", 5);
        mActivity.setResult(Activity.RESULT_OK, intent);//Result_OK 表示结果响应成功
        mActivity.finish();
    }

    @Override
    public void showLoadingDialog() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void closeLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

}
