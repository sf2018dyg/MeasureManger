package com.soonfor.repository.model.knowledge;

import android.os.Parcel;
import android.os.Parcelable;

import com.soonfor.repository.tools.DataTools;

import java.util.ArrayList;

/**
 * 作者：DC-DingYG on 2018-06-22 16:47
 * 邮箱：dingyg012655@126.com
 * 知识类型对象
 */
public class CategoryBean {
    /*
      "id":"8f8f0e3ac26049ef86296352b6e107f4",
                "merchantCode":null,
                "name":"销售话术",
                "parentId":"28d2219fad76460cbbdec87ba74e51fc",
                "sort":3,
                "creator":null,
                "createTime":null,
                "updater":null,
                "updateTime":null,
                "children":[
             */
    private String id;
    private String merchantCode;
    private String name;
    private String parentId;
    private String parentName;
    private String sort;
    private String creator;
    private String createTime;
    private String updater;
    private String updateTime;
    private ArrayList<CategoryBean> children;

    public CategoryBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryBean(String id, String name, String parentId, String parentName) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.parentName = parentName;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public String getMerchantCode() {
        return merchantCode == null ? "" : merchantCode;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public String getParentId() {
        return parentId == null ? "" : parentId;
    }

    public String getParentName() {
        return parentName == null ? "" : parentName;
    }

    public String getSort() {
        return sort == null ? "" : sort;
    }

    public String getCreator() {
        return creator == null ? "" : creator;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public String getUpdater() {
        return updater == null ? "" : updater;
    }

    public String getUpdateTime() {
        return updateTime == null ? "" : updateTime;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public ArrayList<CategoryBean> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<CategoryBean> children) {
        this.children = children;
    }

    public void setParentNameToChild() {
        if (children != null && children.size() > 0) {
            ArrayList<CategoryBean> newChildren = new ArrayList<>();
            newChildren.add(new CategoryBean("all", "全部", getId(), getName()));
            for(int p=0; p<children.size(); p++){
                CategoryBean child = children.get(p);
                child.setParentName(getName());
                newChildren.add(child);
            }
            setChildren(newChildren);
        }
    }

    public ArrayList<String> getIds() {
        ArrayList<String> ids = new ArrayList<>();
        try {
            ArrayList<CategoryBean> childs = null;
            if (getId().equals("all")) {
                int index = -1;
                try {
                    for (int i = 0; i < DataTools.fTypes.size(); i++) {
                        if (DataTools.fTypes.get(i).getId().equals(getParentId())) {
                            index = i;
                            break;
                        }
                    }
                } catch (Exception e) {
                }
                if (index > 0) {
                    childs = DataTools.fTypes.get(index).getChildren();
                }
            } else {
                childs = getChildren();
            }
            if (childs != null && childs.size() > 0) {
                ids.clear();
                for (int c = 1; c < childs.size(); c++) {
                    ids.add(childs.get(c).getId());
                }
            } else {
                ids.clear();
                ids.add(getId());
            }
        } catch (Exception e) {}
        return ids;
    }
}
