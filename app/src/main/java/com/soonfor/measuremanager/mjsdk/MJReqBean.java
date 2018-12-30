package com.soonfor.measuremanager.mjsdk;

import com.google.gson.Gson;
import com.jiamm.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class MJReqBean {

    public static class SdkBaseReq {
        protected String ns;
        protected String cmd;

        public String getString() {
            Gson gson = new Gson();
            String reqInfo = gson.toJson(this);
            return reqInfo;
        }
    }

    public static class SdkAppConfig extends SdkBaseReq {
        public String apiUrl;
        public String storagePath;
        public String packageName;
        public String timeStamp;

        private String appKey = "PG411N3KFH";
        private String appSecret = "8QAU6hknCIyaErduK1Bc5WO7PzhlwM";
        private String sign;

        public SdkAppConfig() {
            ns = "user";
            cmd = "config";
        }

        public void generateSign() {
            String md5Str = appKey + appSecret + timeStamp;
            String sBigSign = CommonUtil.MD5(md5Str);
            sign = sBigSign.toLowerCase();
        }
    }

    public static class SdkChangeHouseEditState extends SdkBaseReq {
        public String contractNo;
        public boolean editState;

        public SdkChangeHouseEditState() {
            ns = "house";
            cmd = "change_house_edit_state";
        }
    }

    public static class SdkUpdateHouseLayout extends SdkBaseReq {
        public String contractNo;
        public String beddingRooms;
        public String livingRooms;
        public String washingRooms;

        public SdkUpdateHouseLayout() {
            ns = "house";
            cmd = "updata_house_layout";
        }
    }

    public static class SdkCreateHouse extends SdkBaseReq {

        public class CreateHouseInfo {
            public String village;//小区
            public String buildingNo;//楼栋号
            public String contractNo;//合同号
            public String employeeNo;//员工编号
            public String company;//所属公司
            public String templateContractNo;//模版合同号
            public String taskNo; //任务号
        }

        public CreateHouseInfo data;

        public SdkCreateHouse() {
            ns = "house";
            cmd = "create_house";
            data = new CreateHouseInfo();
        }
    }

    public static class SdkRemeasureHouse extends SdkBaseReq {

        public class RemeasureHouseInfo {
            public String village;
            public String buildingNo;
            public String copy_contractNo;//初测合同号
            public String new_contractNo;//复测合同号
            public String employeeNo;
            public String company;
            public String taskNo;//任务号
        }

        public RemeasureHouseInfo data;

        public SdkRemeasureHouse() {
            ns = "house";
            cmd = "remeasure_house";
            data = new RemeasureHouseInfo();
        }
    }

    public static class SdkOpenHouse extends SdkBaseReq {
        public class HouseIdInfo {
            public String contractNo;//合同号
        }

        public HouseIdInfo _id;
        public String view;

        public SdkOpenHouse() {
            ns = "house";
            cmd = "open_house";
            _id = new HouseIdInfo();
            view = "survey_2d";
        }
    }

    public static class SdkPreviewSurvey extends SdkBaseReq {
        public class HouseIdInfo {
            public String contractNo;
        }

        public HouseIdInfo _id;
        public String view;

        public SdkPreviewSurvey() {
            ns = "house";
            cmd = "preview_survey";
            _id = new HouseIdInfo();
            view = "preview_2d";
        }
    }

    public static class SdkPreview3D extends SdkBaseReq {
        public class HouseIdInfo {
            public String contractNo;
        }

        public HouseIdInfo _id;
        public String view;

        public SdkPreview3D() {
            ns = "house";
            cmd = "preview_3d";
            _id = new HouseIdInfo();
            view = "preview_3d";
        }
    }

    public static class SdkCloseHouse extends SdkBaseReq {
        public String id;

        public SdkCloseHouse() {
            cmd = "close_house";
            ns = "house";
        }
    }

    public static class SdkDeleteHouse extends SdkBaseReq {
        public String contractNo;

        public SdkDeleteHouse() {
            cmd = "delete_house";
            ns = "house";
        }
    }

    public static class SdkCompleteHouse extends SdkBaseReq {
        public String contractNo;

        public SdkCompleteHouse() {
            cmd = "complete_house_measure";
            ns = "house";
        }
    }

    public static class SdkMsgInfo {
        public String cmd;
        public int errorCode;
        public String errorMessage;
        public String result;
    }

    /**
     * 放样
     */
    public static class SdkLoftingSurvey extends SdkBaseReq {
        public class HouseIdInfo {
            public String contractNo;//合同号
        }

        public class DataInfo {
            public String number;//墙面编号
            public String proxyNumber;//对sdk系统的墙面编号的代理
            public String remoteLoftPicUrl;//放样图片网络地址
            public String status;//放样状态
        }

        public HouseIdInfo _id;
        public List<DataInfo> data;
        public String view;

        public SdkLoftingSurvey() {
            ns = "house";
            cmd = "lofting_home";
            _id = new HouseIdInfo();
            view = "lofting_home";
            data = new ArrayList<DataInfo>();
        }
    }
    //更新放样主场景
    public static class SdkLoftingRefresh extends SdkBaseReq{

        public class HouseIdInfo {
            public String contractNo;//合同号
        }
        public class DataInfo {
            public String number;//墙面编号
            public String proxyNumber;//对sdk系统的墙面编号的代理
            public String remoteLoftPicUrl;//放样图片网络地址
            public String status;//放样状态
        }

        public HouseIdInfo _id;
        public List<DataInfo> data;

        public SdkLoftingRefresh() {
            ns = "house";
            cmd = "lofting_home_updata";
            _id = new HouseIdInfo();
            data = new ArrayList<DataInfo>();
        }
    }
    //返回放样主场景
    public static class SdkLoftingChange extends SdkBaseReq {

        public SdkLoftingChange() {
            ns = "native";
            cmd = "lofting_home_changed";
        }
    }

    /**
     * 户型清单
     */
    public static class SdkHouseTypeList extends SdkBaseReq {

        public String contractNo;

        public String getContractNo() {
            return contractNo;
        }

        public void setContractNo(String contractNo) {
            this.contractNo = contractNo;
        }

        public SdkHouseTypeList() {
            ns = "native";
            cmd = "all_lofting_list";
            contractNo = getContractNo();
        }
    }

    //获取量房完成状态
    public static class SdkGetMeasureMentStatus extends SdkBaseReq {

        public class HouseIdInfo {
            public String contractNo;//合同号
        }

        public HouseIdInfo _id;

        public SdkGetMeasureMentStatus() {
            ns = "house";
            cmd = "measurement_status";
            _id = new HouseIdInfo();
        }
    }
}
