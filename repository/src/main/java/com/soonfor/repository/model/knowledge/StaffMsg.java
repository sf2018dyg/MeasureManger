package com.soonfor.repository.model.knowledge;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-DingYG on 2018-08-23 10:42
 * 邮箱：dingyg012655@126.com
 */
public class StaffMsg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    public static final int TYPE_CONTEX = 2;
    private int type;//消息类型
    private String content;//消息内容
    private List<KnowledgeBean> kbList;

    public StaffMsg(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content==null?"":content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<KnowledgeBean> getKbList() {
        return kbList;
    }

    public void setKbList(List<KnowledgeBean> kbList) {
        this.kbList = kbList;
    }
}
