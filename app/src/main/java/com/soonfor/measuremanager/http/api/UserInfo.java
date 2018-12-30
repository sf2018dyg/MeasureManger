package com.soonfor.measuremanager.http.api;

import java.math.BigDecimal;

import cn.jesse.nativelogger.NLogger;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class UserInfo {
    /**
     * SP存储名
     */
    public static String UUID = "uuid";
    public static String PASSWORD = "password";
    public static String USERNAME = "userName";
    public static String ISREMEMBER = "isRemember";
    /**
     * 接口
     */
    public static String GET_KEY = "/auth/sso/publicKey";
    /**
     * 获取服务器公匙
     */
    public static String LOGIN = "/auth/sso/login";
    /***登录*/
    public static String GET_CURRENT_USER = "/sfapi/Users/GetCurrentUser";
    /**
     * 获取当前登录用户的实体，包含了username和userid
     **/
    public static String LOGOUT = "/auth/sso/logout";
    /*** 退出登录*/
    public static String GETFJURI = "/sfapi/Systems/GetUri?type=";
    /*** 获取附件服务器地址*/
    public static String UPLOAD_FILE_TO_CRM = "/javascript/kindeditor/asp.net/appupload_json.ashx";
    /*** 上传文件到crm服务器*/
    public static String DOWNLOAD_FILE = "/javascript/kindeditor/asp.net/appfile_download.ashx?filepath=";

    /**
     * 获取新版本的版本信息
     */
    public static final String UPDATE_GETVERSIONINFO = "/sfapi/update/getVersions?path=Logfiles&fileName=versionInfo.json";
    /**
     * 下载app的url
     */
    public static final String UPDATE_UPLPADAPP = "/sfapi/update/downloadFile?path=Logfiles&fileName=";

    public static String GET_OPTION = "/sfapi/Options/GetListByType";//根据类型获取选项
    public static String GET_OPTION_TYPE = "/sfapi/Options/GetAllOptionTypes";//获取所有选项列表

    /**
     * 首页相关
     */
    public static String GET_MY_RANKING = "/sfapi/Users/GetMyRanking"; /** 获取我的排行榜*/

    /**
     * 量尺、复尺
     * 修改人：DC-ZhuSuiBo on 2018/2/28 15:49
     * 邮箱：suibozhu@139.com
     */
    public static String GET_NEWS_COUNT = "/storejps/News/GetNewsCount";
    public static String GET_CAROUSEL_PICTURE = "/sfapi/Home/GetCarouselPicture";//"/storejps/home/getCarouselPicture";//演示接口 "/sfapi/Home/GetCarouselPicture";
    public static String GET_TASK_OVERVIEW = "/storejps/home/getTaskOverview";
    public static String GET_GRAB_TASK = "/storejps/home/getGrabTaskCount";
    public static String GET_DESIGNER_TOPN = "/sfapi/Home/GetDesignerTopN";//"/storejps/home/getDesignerPerformanceGoals";//演示用接口"/sfapi/Home/GetDesignerTopN";
    public static String GET_PROGRESS_TASK_INFO = "/storejps/tasks/getProgressTaskInfo";
    public static String GET_MEASURE_TASKS = "/storejps/tasks/getMeasureTasks";
    public static String GET_REMEASURE_TASKS = "/storejps/tasks/getRemeasureTasks";
    public static String GET_TASK_COMPLETE_INFO = "/storejps/tasks/getTaskCompleteInfo";
    public static String ACCEPT_TASK = "/storejps/tasks/acceptTask";
    public static String REJECT_TASK = "/storejps/tasks/rejectTask";
    public static String CONFIRM_APPOINT = "/storejps/tasks/confirmAppoint";
    public static String GET_MEASURE_LIST = "/storejps/tasks/getMeasureList";
    public static String SAVE_NEW_MEASURE = "/storejps/tasks/saveNewMeasure";
    public static String PAGE_HOUSE_TEMPLATE = "/storejps/tasks/pageHouseTemplate";
    public static String SET_TASK_STATUS = "/storejps/tasks/setTaskStatus";
    public static String DELETE_MEASURE = "/storejps/tasks/deleteMeasure";
    public static String GET_INTENTION_PRODUCTS = "/storejps/Orders/GetIntentionProducts";
    public static String SET_STANDARD_TEMPLATE = "/storejps/tasks/setStandardTemplate";//设置为标准模板
    public static String FINISH_TASK = "/storejps/tasks/finishTask";//完成任务

    /**
     * 设计
     */
    public static String GET_DESIGN_TASK_LIST = "/storejps/tasks/getDesignTasks";//获取放样客户任务列表
    public static String SURE_DESIGNPAGER = "/storejps/tasks/confirmDesign";

    /**
     * 抢单
     */
    public static String GET_GRABORDER_TASK_LIST = "/storejps/evatask/getGrabTasks";//待抢任务列表
    public static String POST_GRAB_TASK = "/storejps/evatask/grabTask";//抢单
    /**
     * 放样
     */
    public static String GET_LOFTING_TASK_LIST = "/storejps/tasks/getMarkTasks";//获取放样客户任务列表
    public static String GET_LOFTING_DETAIL_LIST = "/storejps/tasks/getMeasureList";//获取某客户的放样列表
    public static String GET_LOFTING_LOFTPATH_LIST = "/storejps/tasks/getMarkImg";//获取某客户的放样列表
    public static String GET_REMEASURE_TASKNO_BY_LOGT = "/storejps/tasks/getOrderTaskIds";//根据订单号获取复尺/量尺任务号
    public static String GET_LOFTING_LIST = "/storejps/tasks/getMarkList";//获取放样清单
    public static String SET_LOFT_DATA = "/storejps/tasks/setMarkData";

    public static String DELETE_LOFT_PIC = "/storejps/tasks/deleteMarkPicture";//删除放样图片
    public static String SET_LOFT_PIC = "/storejps/tasks/setMarkPicture";//提交放样图片
    public static String SET_WALLMARKSTAUTS = "/storejps/tasks/setWallMarkStatus";//提交墙面放样状态

    /**
     * “我的”相关接口
     */
    public static String GET_MINE = "/sfapi/Users/GetMine";
    /**
     * 获取个人信息
     */
    public static String GET_PERSON = "/sfapi/Users/GetPerson";//获取个人资料
    /**
     * 获取销售目标
     */
    public static String GET_SALE_TARGET = "/sfapi/Targets/GetSaleTarget";
    /**
     * 更新用户信息
     */
    public static String UPDATE_INFO = "/sfapi/Users/UpdateInfo";

    /*** 获取二维码*/
    public static String GET_QRCOARD = "/sfapi/Users/GetQRCard";
    /*** 上传二维码*/
    public static String UPLOAD_QRCARD = "/sfapi/Users/UploadQRCard";

    public static String CHANGE_PASSWORD = "";

    /**
     * 我的关注
     */
    public static String GET_MYCARD = "";//我的名片信息
    public static String GET_MYCASE = "";
    /**
     * 我的案例
     */
    public static String GET_BRANDSTORY = "";/**品牌故事*/
    /**
     * 工分中心
     */
    public static String GET_WORK_POINTS = "/sfapi/Users/GetWorkPoints";//获取工分信息
    public static String GET_WORK_POINT_DETAILS = "/sfapi/Users/GetWorkPointDetails";//获取工分明细
    public static String GET_PRIZE_RULE = "/sfapi/Users/GetPrizeRule";//获取奖品及规则
    public static String EXECUTE_LOTTERY = "/sfapi/Users/ExecuteLottery";//执行抽奖
    /**
     * 我的绩效
     */
    public static String GET_TOTAL_PERFORMANCE = "/sfapi/Users/GetTotalPerformance";//获取绩效总额
    public static String GET_MONTH_PERFORMANCE = "/sfapi/Users/GetMonthPerformance";//获取每月绩效明细
    public static String GET_HISTORY_PERFORMANCE = "/sfapi/Users/GetHistoryPerformance";//获取历史绩效
    /**
     * 成长值
     */
    public static String GET_MY_GROWTH_VALUE = "/sfapi/Users/GetMyGrowthValue";//获取我的成长值
    public static String GET_GROWTH_ITEM = "/sfapi/Users/GetGrowthItem";//获取成长明细（分页）

    /**
     * 获取(销售额、好评数、确图数)等排行榜
     */
    public static String GET_RANKING = "/sfapi/Users/GetRanking";

    /**
     * 获取功能列表
     */
    public static String GET_SHORTCUT_LIST = "/sfapi/Shortcuts/GetShortcutList";

    /**
     * 更新快捷功能
     */
    public static String UPDATE_SHORTCUT = "/sfapi/Shortcuts/UpdateShortcut";
    /**
     * 获取各类人员
     */
    public static String GET_STAFFS = "/sfapi/Users/GetStaffs";

    /**
     * 获取用户相关规则（攻略）
     */
    public static String GET_USER_RULES = "/sfapi/Users/GetUserRules";

    /**
     * 提供精确的除法运算方法div
     *
     * @param value1 被除数
     * @param value2 除数
     * @param scale  精确范围
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static double div(double value1, double value2, int scale) {
        //如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            NLogger.e("算法错误", "scale不能小于0");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        //默认保留两位会有错误，这里设置保留小数点后4位
        double result = 0.0;
        try {
            result = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }catch (Exception e){}
        return result;
    }

    /**
     * 灵感
     **/
    public static String BOARDTYPELIST = "/case/board/boardTypeList";//POST色板类别列表
    public static String BOARDDETAILLIST = "/case/board/boardDetailList";//POST色板列表
    public static String GETINFOBOARDDETAIL = "/case/board/getInfoBoardDetail";//POST色板详情
    public static String PARTSORTLIST = "/case/part/partSortList";//POST配件类别列表
    public static String PARTDETAILLIST = "/case/part/partDetailList";//POST配件列表
    public static String GETINFOPARTDETAIL = "/case/part/getInfoPartDetail";//POST配件详情
    public static String BRANDLIST = "/case/part/brandList";//POST配件品牌列表
    public static String GETCASEDATALIST = "/case/data/list";//获取案例基础数据(筛选侧滑界面数据)

    /**案例**/
    public static String PAGECASE = "/case/detail/pageCase";//更多方案列表
    public static String GETBYID = "/case/detail/getById";//获取案例详情
    public static String SAVECOLLECTS = "/case/detail/saveCollects";//案例添加收藏/取消收藏
    public static String COLLECTLIST_CASE = "/case/detail/collectList";// 我的收藏 案例列表
    public static String COLLECTLIST_HOT = "";// 我的收藏 设计热帖列表
    public static String SAVELIKES = "/case/detail/saveLikes";//添加点赞
    public static String SAVECOMMENT = "/case/comment/saveComment";// POST保存案例评论
    public static String SAVECOMMENTREPLY = "/case/comment/saveCommentReply";//POST保存案例评论回复
    public static String PAGECOMMENTLIST = "/case/comment/pageCommentList";// POST获取案例评论列表

    public static String GETSYSCODE = "/sfapi/Options/GetSysCode";//获取系统参数

    /**
     * 获取订单工分
     */
    public static String GET_ORDER_WORKPOINT = "/storejps/Orders/getOrderWorkPoints";

}
