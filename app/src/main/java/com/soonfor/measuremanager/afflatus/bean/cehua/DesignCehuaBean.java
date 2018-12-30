package com.soonfor.measuremanager.afflatus.bean.cehua;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/10/9 0009 10:38
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class DesignCehuaBean {
    private String name;
    private String key;
    private List<Datas> data;

    public class Datas{
        private String id;
        private String name;
        private String range;

        public String getRange() {
            return range;
        }

        public void setRange(String range) {
            this.range = range;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Datas> getData() {
        return data;
    }

    public void setData(List<Datas> data) {
        this.data = data;
    }
}
