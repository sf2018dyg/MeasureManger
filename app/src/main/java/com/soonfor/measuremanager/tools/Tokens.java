package com.soonfor.measuremanager.tools;

/**
 * Created by Administrator on 2018-02-02.
 */

public class Tokens {

    public final static String SP_USERBEAN = "sp_userbean";//跳转至Fragment页时状态

    public class Design{
        public final static String SKIPTOFRAGMENT_STATUS = "skipToDesignFragment_status";//跳转至Fragment页时状态
        public final static String ITEMSKIPDETAIL_ITEM = "ItemToDetail_item";//从Item调制详情页的具体Item数据
    }
    /**
     * 放样相关口令
     */
    public class Lofing{
        public final static String SKIPTOOTHERTASK_STATUS = "skipToOtherTask_status";//跳转至其他任务
        public final static String SKIPTOFRAGMENT_STATUS = "skipToFragment_status";//跳转至Fragment页时状态
        public final static String ITEMSKIPDETAIL_ITEM = "itemToDetail_item";//从Item调制详情页的具体Item数据
        public final static String ITEMSKIPDETAIL_STATUS = "itemToDetail_status";//从Item调制详情页 的状态
        public final static String SKIP_TO_CONFIMAPPOINT = "skip_to_confimappoint";//跳到确认预约界面
        public final static String DETAILSKIPIMMELOFT_TITLE = "detailToImmeLoft_title";//从详情页进入立即放样 的标题
        public final static String DETAILSKIPIMMELOFT_TITLE_WALL = "detailToImmeLoft_title_wall";//从详情页进入墙面立即放样 的标题
        public final static String DETAILSKIPIMMELOFT_TASKINFO = "detailToImmeLoft_taskinfo";//从详情页进入立即放样 的任务号与任务类型

        public final static String SKIP_IMMT_TO_LOFTDETAIL = "skip_immt_to_loftdetail";//立即放样界面进入放样详情

        public static final int REQUEST_CODE_FIRST_TO_SECOEND = 11;//从放样清单界面点击Item跳至墙面放样清单界面
        public static final int REQUEST_CODE_EDIT_SIZE = 12;//从墙面放样清单界面点击Item上的编辑图标进入编辑尺寸
        public static final String SKIP_ENTER_EDIT_SIZE_STRIGN = "skip_enter_edit_size";//进入
        public static final String SKIP_ENTER_EDIT_SIZE_STRIGN_CHILD = "skip_enter_edit_size_child";//
        public static final String SKIP_ENTER_EDIT_SIZE_POSITION = "skip_enter_edit_size_position";//进入
        public static final String RETURN_EDIT_SIZE_STRIGN = "return_edit_size";//返回
        public static final String RETURN_EDIT_SIZE_POSITION = "return_edit_size_position";//返回

        public static final String SKIP_ENTER_SHOWMEMBER_SIZE_STRIGN = "skip_enter_showmember_size";//进入
        public static final String SKIP_ENTER_SURVEYLIST = "skip_enter_surveylist";//进入量(复)尺清单


        //sdk相关
        public static final String SKIP_ENTER_HXLIST = "skip_enter_hxlist";//从sdk放样进入户型清单


    }
    /**
     * 我的-相关口令
     */
    public class Mine{
        public final static String SP_PERSONALINFO = "sp_personalinfo";//保存个人资料
        public final static String SKIP_TO_MYCARD = "skip_to_mycard";//跳转名片界面
        public final static String SKIP_TO_ANNOUNCEMENTDETAIL = "skip_to_announcement_detail";//跳转至通告详情界面
        public final static String SKIP_TO_JX_ACTIVITY = "skip_to_jx_activity";//跳转至绩效界面
    }
    /**
     * 其它任务-相关口令
     */
    public class OtherTask{
        public final static String SKIPTOFRAGMENT_STATUS = "skipToOTFragment_status";//跳转至Fragment页时状态
        public final static String OT_ITEMSKIPDETAIL_STATUS = "ot_itemToDetail_status";//从Item调制详情页 的状态
        public final static String OT_ITEMSKIPDETAIL_ITEM = "ot_itemToDetail_item";//从Item调制详情页的具体Item数据
        public final static String OT_ITEMSKIPDETAIL_ITEM_DETAIL = "ot_itemToDetail_itemdetail";//从Item调制详情页的具体Item数据
        public final static String SKIP_TO_UPDATETASKRESULT = "skip_to_updatataskresult";//跳转至任务更新结果页
    }
}
