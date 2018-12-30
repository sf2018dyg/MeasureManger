package com.soonfor.measuremanager.home.liangchi.model.bean.detail;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/31 14:42
 * 邮箱：suibozhu@139.com
 */
public class IntentionalEntity {

    /*{"fordno":"A201804230002","name":"111010030 测试产品2","goodsCode":"111010030",
    "goodsName":"测试产品2","sizeDesc":"200*200*200MM","texture":"白丝橡木",
    "color":"红橡仿古白2#","fStdUnit":"BAO","thumbnail":"E:\\\\DCCRM\\\\AttachmeQQ截图20180417083806.png",
    "unitPrice":10,"quantity":10}*/

    String goodsCode;
    String goodsName;
    String color;//颜色
    String fStdUnit;//单位
    String fordno;//订单号
    String name;//名称
    String quantity;//数量
    String sizeDesc;//尺寸
    String texture;//材质
    String thumbnail;//缩略图
    String unitPrice;//单价

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getfStdUnit() {
        return fStdUnit;
    }

    public void setfStdUnit(String fStdUnit) {
        this.fStdUnit = fStdUnit;
    }

    public String getFordno() {
        return fordno;
    }

    public void setFordno(String fordno) {
        this.fordno = fordno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSizeDesc() {
        return sizeDesc;
    }

    public void setSizeDesc(String sizeDesc) {
        this.sizeDesc = sizeDesc;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
}
