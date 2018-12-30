package com.soonfor.repository.view.knowledge;

import com.soonfor.repository.base.IRepBaseView;

/**
 * 作者：DC-DingYG on 2018-07-31 8:42
 * 邮箱：dingyg012655@126.com
 */
public interface IAddKnowledgeView extends IRepBaseView {
    void setGetCategory(boolean isSuccess, String msg);//设置分类标题
    void setAddKnowLedge(boolean isSuccess, String msg);
    void setUploadFile(String fileType, boolean isSuccess, String localpath, String msg);//图片/视频上传
    void setUploadFrameAtPic(boolean isSuccess, String videoPath, String framePath, String msg);//上传第一帧图返回
}
