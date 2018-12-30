package com.soonfor.measuremanager.home.homepage.model.bean;

import java.io.Serializable;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 9:12
 * 邮箱：suibozhu@139.com
 */
public class topPostsEntity implements Serializable{
    String title;
    String imgpath;
    String author;
    String authorImg;
    String postDate;
    String readingVolume;
    String area;

    public topPostsEntity(String title, String imgpath, String author,String authorImg,String postDate, String readingVolume) {
        this.title = title;
        this.imgpath = imgpath;
        this.author = author;
        this.readingVolume = readingVolume;
        this.authorImg = authorImg;
        this.postDate = postDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getReadingVolume() {
        return readingVolume;
    }

    public void setReadingVolume(String readingVolume) {
        this.readingVolume = readingVolume;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
