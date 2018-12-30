package com.soonfor.repository.view.knowledge;

/**
 * 作者：DC-DingYG on 2018-07-27 9:47
 * 邮箱：dingyg012655@126.com
 */
public interface ISendCommentView{
    /**
     * 显示加载动画
     */
    void showLoadingDialog();
    /**
     * 隐藏加载
     */
    void closeLoadingDialog();

    /*
     * 回复评论后操作
     */
    void afterSaveReplay(boolean isSuccess, String data);
    /*
     * 发表评论后操作
     */
    void afterSaveComment(boolean isSuccess, String data);
    void setAfterLike(boolean isSuccess, int position);//点赞/取消点赞后
    void setAfterCollect(boolean isSuccess, int positon);//收藏/取消收藏后
}
