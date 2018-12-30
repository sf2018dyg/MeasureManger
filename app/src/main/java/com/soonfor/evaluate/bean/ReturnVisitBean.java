package com.soonfor.evaluate.bean;

import com.soonfor.measuremanager.tools.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-DingYG on 2018-10-18 15:18
 * 邮箱：dingyg012655@126.com
 */
public class ReturnVisitBean {

    /**
     * ---实际得分	factpoint	number	@mock=$order(10,40,0)
     * ---项目代号	frvisitprjcode	string	@mock=$order('1031003','1031004','234')
     * ---是非值	fynval	number	@mock=$order(0,0,0)
     * ---总分分值	fpoint	number	@mock=$order(100,40,444)
     * ---回答内容	fanswerdesc	string	@mock=$order('','啊啊啊啊啊啊啊啊啊啊啊啊啊','')
     * ---选项信息	resultItemDto	array<object>
     * ------	fseldesc	string	@mock=$order('','','','')
     * ------ID	fitemid	number	@mock=$order(20,21,22,23)
     * ------分值	fpoint	number	@mock=$order(0,0,0,0)
     * ------备注	fremark	string	@mock=$order('','','','')
     * ------项目ID	frvisitprjid	number	@mock=$order(21,21,21,21)
     * ------选项值	fselcode	string	@mock=$order('','','','')
     * ------是否选中	fifchecked	number	@mock=$order(0,0,0,0)
     * ---项目分类ID	frvisitprjtypeid	number	@mock=$order(533131639,533131639,1)
     * ---项目描述	frvisitprjdesc	string	@mock=$order('多选型','问答型','234')
     * ---项目ID	frvisitprjid	number	@mock=$order(21,22,26)
     * ---	id
     * ---提问类型 1.是非型，2.单选项，3.多选项，4.问答型	ftype	number	@mock=$order(3,4,3)
     */
    private String id;//
    private String taskId;//任务id
    private String frvisitprjid;//题目id
    private String frvisitprjdesc;//题目描述
    private String frvisitprjtypeid;//项目分类ID
    private String ftype;//题目类型 1.是非型，2.单选项，3.多选项，4.问答型
    private String fanswerdesc;//回答内容
    private int fpoint;//总分值
    private int fynval;//是非值
    private String frvisitprjcode;//项目代号
    private int factpoint;//实际得分
    private List<Answer> resultItemDto;//选项

    private List<Answer> items;//选项，用于提交

    public String getId() {
        return CommonUtils.formatStr(id);
    }

    public String getTaskId() {
        return taskId==null?"":taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFrvisitprjid() {
        return CommonUtils.formatStr(frvisitprjid);
    }

    public String getFrvisitprjdesc() {
        return CommonUtils.formatStr(frvisitprjdesc);
    }

    public String getFrvisitprjtypeid() {
        return CommonUtils.formatStr(frvisitprjtypeid);
    }

    public String getFtype() {
        return CommonUtils.formatNum(ftype);

    }

    public String getFanswerdesc() {
        return CommonUtils.formatStr(fanswerdesc);
    }

    public int getFpoint() {
        return fpoint;
    }

    public int getFynval() {
        return fynval;
    }

    public String getFrvisitprjcode() {
        return CommonUtils.formatStr(frvisitprjcode);
    }

    public int getFactpoint() {
        return factpoint;
    }

    public List<Answer> getResultItemDto() {
        return resultItemDto == null ? new ArrayList<>() : resultItemDto;
    }

    public void setResultItemDto(List<Answer> resultItemDto) {
        this.resultItemDto = resultItemDto;
    }

    /**
     * 答案选项对象
     */
    public static class Answer {
        private String fitemid;//
        public String fseldesc;//答案描述
        public String fpoint;//分值
        public String frvisitprjid;//项目ID
        public String fselcode;//选项值
        public int fifchecked;//是否选中

        public Answer(String fseldesc, String fpoint, int fifchecked) {
            this.fseldesc = fseldesc;
            this.fpoint = fpoint;
            this.fifchecked = fifchecked;
        }

        public String getFseldesc() {
            return CommonUtils.formatStr(fseldesc);
        }

        public String getFpoint() {
            return CommonUtils.formatNum(fpoint);
        }

        public int getFifchecked() {
            return fifchecked;
        }

        public void setFifchecked(int fifchecked) {
            this.fifchecked = fifchecked;
        }

    }

//    public int getT_scores() {
//        if(answerList==null||answerList.size()==0){
//            t_scores= 0;
//        }else {
//            t_scores = 0;
//            for(int i=0; i<correctList.size(); i++){
//                t_scores += correctList.get(0).getScores();
//            }
//        }
//        return t_scores;
//    }


    public void setId(String id) {
        this.id = id;
    }

    public void setFrvisitprjid(String frvisitprjid) {
        this.frvisitprjid = frvisitprjid;
    }

    public void setFrvisitprjdesc(String frvisitprjdesc) {
        this.frvisitprjdesc = frvisitprjdesc;
    }

    public void setFrvisitprjtypeid(String frvisitprjtypeid) {
        this.frvisitprjtypeid = frvisitprjtypeid;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public void setFanswerdesc(String fanswerdesc) {
        this.fanswerdesc = fanswerdesc;
    }

    public void setFpoint(int fpoint) {
        this.fpoint = fpoint;
    }

    public void setFynval(int fynval) {
        this.fynval = fynval;
    }

    public void setFrvisitprjcode(String frvisitprjcode) {
        this.frvisitprjcode = frvisitprjcode;
    }

    public void setFactpoint(int factpoint) {
        this.factpoint = factpoint;
    }

    public List<Answer> getItems() {
        return items;
    }

    public void setItems() {
        this.items = getResultItemDto();
        setResultItemDto(new ArrayList<>());
    }
}
