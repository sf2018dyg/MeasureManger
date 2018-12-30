package com.soonfor.evaluate.bean;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.AttachDto;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DateTool;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-DingYG on 2018-10-19 8:34
 * 邮箱：dingyg012655@126.com
 */
public class Evaluate_IToCustomersDetailBean {
    /**
     * ---整体好评结果1:好评；2：中评;3:差评	allhighappraiseresult	number	@mock=1
     * ---标签星级	appResSortItemDtos|2	array<object>
     * ------得分值	fpoint	number	@mock=$order(3,2)
     * ------标签标题	fsorttitle	string	@mock=$order('xingwei','xing111wei')
     * ------id	fapprresultitemid	number	@mock=$order(19,20)
     * ------标签星级描述	fsortstardesc	string	@mock=$order('yuhjhk','yuhjhk')
     * ------标签星级标题	fsortstartitle
     * ------父id	fid	number	@mock=$order(3,3)
     * ---0.客户评价，1.评价客户	type	number	@mock=0
     * ---评价内容填写	ifsetappraisecontent	number	@mock=0
     * ---回复时间	freplydate
     * ---评价内容结果填写	appraisecontent	string	@mock=252522555555
     * ---图片附件信息	attachDtos	array<object>
     * ------图片名称	attachDesc	string	@mock=52522
     * ------url	attachUrl	string	@mock=525252
     * ------附件ID	attachId	string	@mock=7
     * ------0.评价图，1.评价结果回复图	attachType	string	@mock=1
     * ---评价时间	evalTime	number	@mock=1541656720730
     * ---完成状态0.未完成，1.已完成	status	number	@mock=0
     * ---回复	reply
     * ---id	id	string	@mock=3
     * ---图片上传	ifuploadImg	number	@mock=1
     * ---标签星级评价	ifstarlvappraise	number
     *
     * @return
     */
    private String id;
    private int type;//0.客户评价，1.评价客户
    private int status;//完成状态0.未完成，1.已完成
    private String reply;//回访
    private String freplydate;
    private int ifuploadimg;//图片上传(是否)
    private int ifstarlvappraise;//标签星级评价
    private int ifsetappraisecontent;//是否需要评价内容填写
    private int ifallhighappraise;//是否需要整体评价
    private String evalTime;
    private int allhighappraiseresult; //* ---整体好评结果1:好评；2：中评;3:差评		number	@mock=1
    private String appraisecontent;//整体评价内容
    private List<AppResSortItemDto> appResSortItemDtos;//---标签星级
    private List<AttachDto> attachDtos;//图片附件信息  attachType: 0.评价图，1.评价结果回复图
    private boolean cusEvaCheck;//是否已经评价客户


    public int getAllhighappraiseresult() {
        return allhighappraiseresult;
    }

    public List<AppResSortItemDto> getAppResSortItemDtos() {
        return appResSortItemDtos==null?new ArrayList<>():appResSortItemDtos;
    }

    public int getType() {
        return type;
    }

    public int getFfsetappraisecontent() {
        return ifsetappraisecontent;
    }

    public String getFreplydate() {
        return CommonUtils.formatStr(freplydate);
    }

    public String getAppraisecontent() {
        return CommonUtils.formatStr(appraisecontent);
    }

    public List<AttachDto> getAttachDtos() {
        return attachDtos==null?new ArrayList<>():attachDtos;
    }

    public String getEvalTime() {
        return DateTool.getTimeTimestamp(evalTime, null);
    }

    public int getStatus() {
        return status;
    }

    public String getReply() {
        return CommonUtils.formatStr(reply);
    }

    public String getId() {
        return CommonUtils.formatStr(id);
    }

    public int getIfuploadImg() {
        return ifuploadimg;
    }

    public int getIfstarlvappraise() {
        return ifstarlvappraise;
    }

    public int getIfuploadimg() {
        return ifuploadimg;
    }

    public int getIfallhighappraise() {
        return ifallhighappraise;
    }

    public boolean isCusEvaCheck() {
        return cusEvaCheck;
    }
}
