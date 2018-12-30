package com.soonfor.evaluate.bean;

import com.soonfor.measuremanager.tools.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-DingYG on 2018-10-22 10:24
 * 邮箱：dingyg012655@126.com
 */
public class EvaluateTemplateBean {
    private TemplateMstDto tempMstDto;
    private List<TemplateItemDto> tempItemDtos;

    public TemplateMstDto getTempMstDto() {
        return tempMstDto;
    }

    public void setTempMstDto(TemplateMstDto tempMstDto) {
        this.tempMstDto = tempMstDto;
    }

    public List<TemplateItemDto> getTempItemDtos() {
        return tempItemDtos==null?new ArrayList<>():tempItemDtos;
    }

    public void setTempItemDtos(List<TemplateItemDto> tempItemDtos) {
        this.tempItemDtos = tempItemDtos;
    }

    /**
     * 评价模板
     */
    public static class TemplateMstDto{
        private int mtype;//0平台 1商家
        private int type;//0客户评价 1评价客户

        private String appraisetempcode;//模板编号
        private String appraisetempid;//评价模板id
        private String appraisetempname;//模板名称
        private int ifallhighappraise;//整体好评评价(0:隐藏 1:显示)
        private int ifsetappraisecontent;//评价内容填写（0:隐藏 1:显示）
        private int ifstarlvappraise;//标签星级评价 勾选时，可选评价标签
        private int ifuploadimg;//图片上传(0:隐藏 1:显示)
        private String remark;//模板说明

        public String getAppraisetempcode() {
            return appraisetempcode==null?"":appraisetempcode;
        }

        public void setAppraisetempcode(String appraisetempcode) {
            this.appraisetempcode = appraisetempcode;
        }

        public String getAppraisetempid() {
            return appraisetempid==null?"":appraisetempid;
        }

        public void setAppraisetempid(String appraisetempid) {
            this.appraisetempid = appraisetempid;
        }

        public String getAppraisetempname() {
            return appraisetempname==null?"":appraisetempname;
        }

        public void setAppraisetempname(String appraisetempname) {
            this.appraisetempname = appraisetempname;
        }

        public int getIfallhighappraise() {
            return ifallhighappraise;
        }

        public void setIfallhighappraise(int ifallhighappraise) {
            this.ifallhighappraise = ifallhighappraise;
        }

        public int getIfsetappraisecontent() {
            return ifsetappraisecontent;
        }

        public void setIfsetappraisecontent(int ifsetappraisecontent) {
            this.ifsetappraisecontent = ifsetappraisecontent;
        }

        public int getIfstarlvappraise() {
            return ifstarlvappraise;
        }

        public void setIfstarlvappraise(int ifstarlvappraise) {
            this.ifstarlvappraise = ifstarlvappraise;
        }

        public int getIfuploadimg() {
            return ifuploadimg;
        }

        public void setIfuploadimg(int ifuploadimg) {
            this.ifuploadimg = ifuploadimg;
        }

        public int getMtype() {
            return mtype;
        }

        public void setMtype(int mtype) {
            this.mtype = mtype;
        }

        public String getRemark() {
            return remark==null?"":remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    /*
     * 评价标签
     */
    public static class TemplateItemDto{

        private int fItemStep;//选中了几星
        private int fItemValue;//对应的分数
        private String fItemTilte;//星级标题
        private String fItemDesc;//星级描述

        private String appraisesortid;//评价分类id
        private String appraisetempid;//模板id
        private String itemid;//主键ID
        private String remark;//备注
        private String title;//评价标签

        private String fivedesc;//五星描述
        private String fivetitle;//五星
        private int fivevalue;//五星值
        private String fourdesc;//四星描述
        private String fourtitle;//四星
        private int fourvalue;//四星值
        private String threedesc;//三星描述
        private String threetitle;//三星
        private int threevalue;//三星值
        private String twodesc;//二星描述
        private String twotitle;//二星
        private int twovalue;//二星值
        private String onedesc;//一星描述
        private String onetitle;//一星
        private int onevalue;//一星值

        public int getfItemStep() {
            return fItemStep;
        }

        public void setfItemStep(int fItemStep) {
            this.fItemStep = fItemStep;
        }

        public int getfItemValue() {
            return fItemValue;
        }

        public void setfItemValue(int fItemValue) {
            this.fItemValue = fItemValue;
        }

        public String getfItemTilte() {
            return CommonUtils.formatStr(fItemTilte);
        }

        public void setfItemTilte(String fItemTilte) {
            this.fItemTilte = fItemTilte;
        }

        public String getfItemDesc() {
            return CommonUtils.formatStr(fItemDesc);
        }

        public void setfItemDesc(String fItemDesc) {
            this.fItemDesc = fItemDesc;
        }

        public String getAppraisesortid() {
            return appraisesortid==null?"":appraisesortid;
        }

        public void setAppraisesortid(String appraisesortid) {
            this.appraisesortid = appraisesortid;
        }

        public String getAppraisetempid() {
            return appraisetempid==null?"":appraisetempid;
        }

        public void setAppraisetempid(String appraisetempid) {
            this.appraisetempid = appraisetempid;
        }

        public String getItmeid() {
            return itemid==null?"":itemid;
        }

        public void setItmeid(String itemid) {
            this.itemid = itemid;
        }

        public String getRemark() {
            return remark==null?"":remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getTitle() {
            return title==null?"":title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFivedesc() {
            return fivedesc==null?"":fivedesc;
        }

        public void setFivedesc(String fivedesc) {
            this.fivedesc = fivedesc;
        }

        public String getFivetitle() {
            return fivetitle==null?"":fivetitle;
        }

        public void setFivetitle(String fivetitle) {
            this.fivetitle = fivetitle;
        }

        public int getFivevalue() {
            return fivevalue;
        }

        public void setFivevalue(int fivevalue) {
            this.fivevalue = fivevalue;
        }

        public String getFourdesc() {
            return fourdesc==null?"":fourdesc;
        }

        public void setFourdesc(String fourdesc) {
            this.fourdesc = fourdesc;
        }

        public String getFourtitle() {
            return fourtitle==null?"":fourtitle;
        }

        public void setFourtitle(String fourtitle) {
            this.fourtitle = fourtitle;
        }

        public int getFourvalue() {
            return fourvalue;
        }

        public void setFourvalue(int fourvalue) {
            this.fourvalue = fourvalue;
        }

        public String getThreedesc() {
            return threedesc==null?"":threedesc;
        }

        public void setThreedesc(String threedesc) {
            this.threedesc = threedesc;
        }

        public String getThreetitle() {
            return threetitle==null?"":threetitle;
        }

        public void setThreetitle(String threetitle) {
            this.threetitle = threetitle;
        }

        public int getThreevalue() {
            return threevalue;
        }

        public void setThreevalue(int threevalue) {
            this.threevalue = threevalue;
        }

        public String getTwodesc() {
            return twodesc==null?"":twodesc;
        }

        public void setTwodesc(String twodesc) {
            this.twodesc = twodesc;
        }

        public String getTwotitle() {
            return twotitle;
        }

        public void setTwotitle(String twotitle) {
            this.twotitle = twotitle;
        }

        public int getTwovalue() {
            return twovalue;
        }

        public void setTwovalue(int twovalue) {
            this.twovalue = twovalue;
        }

        public String getOnedesc() {
            return onedesc==null?"":onedesc;
        }

        public void setOnedesc(String onedesc) {
            this.onedesc = onedesc;
        }

        public String getOnetitle() {
            return onetitle==null?"":onetitle;
        }

        public void setOnetitle(String onetitle) {
            this.onetitle = onetitle;
        }

        public int getOnevalue() {
            return onevalue;
        }

        public void setOnevalue(int onevalue) {
            this.onevalue = onevalue;
        }
    }

}
