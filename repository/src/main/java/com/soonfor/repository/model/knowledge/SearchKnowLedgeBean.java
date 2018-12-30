package com.soonfor.repository.model.knowledge;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/7/31 0031 13:56
 * 邮箱：suibozhu@139.com
 * 类用途：{"pageTurn":{"firstPage":0,"pageCount":0,"pageNo":0,"nextPage":0,"prevPage":0,"pageSize":0,"rowCount":0},"list":[]}
 */
public class SearchKnowLedgeBean implements Serializable{

    private PageTurn pageTurn;
    private List<ListBean> list;

    public class PageTurn{
        private int prevPage;
        private int rowCount;
        private int pageSize;
        private int pageNo;
        private int pageCount;
        private int nextPage;
        private int firstPage;

        public int getPrevPage() {
            return prevPage;
        }

        public void setPrevPage(int prevPage) {
            this.prevPage = prevPage;
        }

        public int getRowCount() {
            return rowCount;
        }

        public void setRowCount(int rowCount) {
            this.rowCount = rowCount;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public int getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(int firstPage) {
            this.firstPage = firstPage;
        }
    }

    public class ListBean{
        private String title;
        private int isLike;
        private UserInfo userInfo;
        private int likeCount;
        private int commentCount;
        private String id;
        private String summary;
        private int publishTime;
        private String video;
        private List<String> picList;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIsLike() {
            return isLike;
        }

        public void setIsLike(int isLike) {
            this.isLike = isLike;
        }

        public UserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public int getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(int publishTime) {
            this.publishTime = publishTime;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public List<String> getPicList() {
            return picList;
        }

        public void setPicList(List<String> picList) {
            this.picList = picList;
        }
    }

    public class UserInfo{
        private String id;
        private String nickName;
        private String avatar;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public PageTurn getPageTurn() {
        return pageTurn;
    }

    public void setPageTurn(PageTurn pageTurn) {
        this.pageTurn = pageTurn;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }
}
