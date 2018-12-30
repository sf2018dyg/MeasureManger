package com.soonfor.repository.http.api;

/**
 * 作者：DC-DingYG on 2018-05-02 15:41
 * 邮箱：dingyg012655@126.com
 */
public class RepUserInfo {

    public static class Knowledge{
        public static String GET_CATEGORY = "/knowledge/category/list";//获取分类列表
        public static String GET_HOTLIST = "/knowledge/pageHotList";
        public static String GET_KNOWLEDGELIST = "/knowledge/searchPage";//获取知识列表
        public static String SEARCHTITLE = "/knowledge/searchTitle";//检索-标题
        public static String SEARCHKNOWLEDGE = "/knowledge/searchKnowledge";//搜索知识结果
        public static String GET_KNOWLEDGEDETAIL = "/knowledge/getKnowledge";//获取知识详情
        public static String ADDVIEWCOUNT = "/knowledge/addViewCount";//增加浏览数量
        public static String GET_COMMENTLIST = "/knowledge/comment/pageCommentList";//获取评论列表
        public static String SAVE_COMMENTREPLY = "/knowledge/comment/saveCommentReply";//保存评论回复
        public static String SAVE_COMMENT = "/knowledge/comment/saveComment";//保存评论
        public static String COLLECT = "/knowledge/collect";//(取消)收藏
        public static String LIKE = "/knowledge/like";//（取消）点赞
        public static String ADDKNOWLEDGE = "/knowledge/appSave";
        public static String GETSYSCODE = "/sfapi/Options/GetSysCode";//获取系统参数
    }
    public static class Seekhelp{
        public static String GET_SEARCHTITLE = "/knowledge/searchTitle";//检索-标题
        public static String GET_TOPKNOWLEDGE = "/knowledge/getTopKnowledge";//获取浏览量前25名的求助知识列表

    }
    public static class Personal{
        public static String GET_PERSONALINFO = "/sfapi/Users/GetMine";//获取个人资料信息
        public static String CANCELCOLLECT = "/knowledge/cancelCollect";//取消收藏
        public static String CANCELLIKE = "/knowledge/cancelLike";//取消点赞
        public static String COLLECT  = "/knowledge/collect";//收藏
        public static String LIKE  = "/knowledge/like";//点赞

        public static String GET_MYCOLLECTIONS = "/knowledge/user/collectionPage";//获取我的收藏
        public static String DELETECOLLECTIONS = "/knowledge/user/deleteCollections";//批量删除收藏
        public static String GET_MYINCREASEDKNOWLEDGELIST = "/knowledge/user/myKnowledgePage";//我添加的知识
    }
    public static String UPLOAD_FILE_TO_CRM = "/upc/upload"; /*** 上传文件到crm服务器*/

}
