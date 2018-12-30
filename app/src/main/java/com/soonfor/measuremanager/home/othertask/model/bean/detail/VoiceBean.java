package com.soonfor.measuremanager.home.othertask.model.bean.detail;

/**
 * 作者：DC-DingYG on 2018-08-29 14:15
 * 邮箱：dingyg012655@126.com
 */
public class VoiceBean {
    private String path;//文件路径
    private long duration;//时长

    public VoiceBean(String path, long duration) {
        this.path = path;
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
