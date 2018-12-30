package com.soonfor.repository.tools;

/**
 * Created by Administrator on 2018-02-02.
 */

public class Tokens {

    public final static String SP_PERSONINFO = "sp_personinfo";//保存用户信息
    public final static String SP_USERBEAN = "sp_userbean";//跳转至Fragment页时状态
    public final static String SP_UPLOADCNETERPATH = "sp_uploadCenterPath";//上传下载附件地址
    public final static String SP_ARTIFICIALSTAFFPATH = "sp_artificialStaffPath";//人工客户html5网址

    /**
     * 知识相关口令
     */
    public class Knowledge{
        public final static int CLICK_TO_DETAIL = 201;//跳转至知识详情页

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
}
