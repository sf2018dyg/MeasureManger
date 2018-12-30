package com.dynamicpicpro.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-06-15.
 */

public class PhotoModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private String originalPath;
    private boolean isChecked;

    public PhotoModel(String originalPath, boolean isChecked) {
        super();
        this.originalPath = originalPath;
        this.isChecked = isChecked;
    }

    public PhotoModel(String originalPath) {
        this.originalPath = originalPath;
    }

    public PhotoModel() {
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

}