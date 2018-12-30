package com.soonfor.measuremanager.http.api;

/**
 * Created by ljc on 2018/1/15.
 */

public class CustomerInfo {

    /**
     * 获取数据漏斗
     */
    public static final String GET_AMALYSIS =  "/sfapi/Customers/GetSaleAnalysisList";


    /**
     * 保存用户
     */
    public static final String SAVE_CUSTOMER =  "/sfapi/Customers/SaveCustomer";


    /**
     * 用户任务详情
     */
    public static final String GET_TASK_DETAIL =  "/sfapi/Tasks/GetTaskInfo";


    /**
     * 获取公海客户
     */
    public static final String GET_SEA_CUSTOM =  "/sfapi/Customers/GetSeaCustomers";


    /**
     * 保存用户跟进记录
     */
    public static final String SAVE_COMMUNICATE =  "/sfapi/Customers/SaveCommunicate";

    /**
     * 获取客户任务
     */
    public static final String GET_CUSTOM_TASK =  "/sfapi/Customers/GetCustomerTasks";


    /**
     * 获取客户信息
     */
    public static final String GET_CUSTOM_MESSAGE =  "/sfapi/Customers/GetCustomerInfo";


    /**
     * 获取客户画像
     */
    public static final String GET_CUSTOM_PORTRAITS =  "/sfapi/Customers/GetCustomerPortraits";

    /**
     * 保存用户画像
     * **/
    public static final String SAVE_PORTRAITS = "/sfapi/Customers/SavePortraits";


    /**
     * 分享客户
     */
    public static final String SHARE_CUSTOM = "/sfapi/Customers/ShareCustomer";


    /**
     * 获取客户跟进记录
     */
    public static final String GET_FOLLOW_RECORD = "/sfapi/Customers/GetCommunicates";

    /**
     * 获取客户订单
     */
    public static final String GET_ORDERS = "/sfapi/Customers/GetOrders";


    /**
     * 获取客户订单时间线
     */
    public static final String GET_ORDER_TIMELINE = "/sfapi/Customers/GetOrderTimelines";


    /**
     * 获取我的客户
     */
    public static final String GET_MYCUSTOM = "/sfapi/Customers/GetCustomerList";


    /**
     * 派工
     */
    public static final String SET_DISPATCH = "/sfapi/Tasks/SetDispatch";

    /**
     * 销货订单详情
     */
    public static final String ORDER_DETAIL = "/storejps/Orders/GetSaleOrder";


    /**
     * 收货订单详情
     */
    public static final String ORDER_RECEIVE_DETAIL = "/sfapi/Orders/GetReceiptDetails";


    /**
     * 异常
     */
    public static final String EXCEPTION =  "/sfapi/Orders/GetAbnormalDeclareDetails";

    /**
     * 收款记录
     */
    public static final String RECEIVE_MONEY =  "/sfapi/Orders/GetCollectMoney";

    /**
     * 预订单详情
     */
    public static final String PRE_ORDER =  "/storejps/Orders/GetPreorderInfo";

    /**
     * 代办任务
     */
    public static final String WAIT_TASK =  "/sfapi/Users/GetUserTasks";

    /**
     * 上传合同
     */
    public static final String UPLOAD_CONTRACT =  "/sfapi/Customers/UploadContract";


    /**
     * 抢客
     */
    public static final String GRAB_CUSTOMER =  "/sfapi/Customers/GrabCustomer";

    /**
     * 添加跟进(普通)任务
     */
    public static final String SAVE_TASK =  "/sfapi/Tasks/SaveTask";
    /**
     * 更新任务结果
     */
    public static final String UPDATE_TASK_RESULT =  "/storejps/evatask/updateTaskResult";

    /**
     * 获取任务工分
     */
    public static final String GET_TASK_POINT =  "/sfapi/Orders/GetPrcessWorkPoints";


    /**
     * 获取出货信息
     */
    public static final String GET_DELEVERY_INFO =  "/sfapi/Orders/GetDeliveryInfo";

    //SP存储名
    public static final String SP_NAME = "Soonfor";

    /**
     * 其它任务
     */
    public static final String GET_OTHERTASKLIST = "/storejps/evatask/getOtherTasks";
    //主动追踪任务详情
    public static final String GET_ACTIVEFOLLOWIN_TASKDETAIL = "/storejps/evatask/getTraceTaskDetial";
    //回访任务详情
    public static final String GET_RETURNVISIT_TASKDETAIL = "/storejps/evatask/getVisitTaskDetial";
    //其它任务处理详情
    public static final String GET_TASKRESULT = "/storejps/evatask/getTaskResult";
    //人工回访的调查问卷模板（填写）
    public static final String GET_QUESTIONNAIRETEMPLATE = "/storejps/evaluate/getRtnVisitTemple";
    //调查问卷结果（查看）
    public static final String GET_QUESTIONNAIRERESULT  = "/storejps/evaluate/getRtnVisitResultDto";

    //评价概览
    public static String GET_EVALUATEVIEW = "/storejps/evaluate/getEvaluateView";

    //获取 客户对我的评价列表
    public static final String GET_EVALUATEToMeLIST = "/storejps/evaluate/getSalesEvaluate";
    //获取 客户对我的评价详情
    public static final String GET_EVALUATETOMEDETAILINFO = "/storejps/evaluate/getSalesEvaluateDetail";
    // 获取 我对客户评价列表
    public static final String GET_ITOCUSEVALUATE = "/storejps/evaluate/getCusEvaluate";
    // 获取 我对客户评价详情
    public static final String GET_ITOCUSEVALUATEDETAIL = "/storejps/evaluate/getCusEvaluateDetail";
    //获取评价模板
    public static final String GET_EVALUATETEMP = "/storejps/evaluate/getEvaluateTemp";

    //评价客户
    public static String POST_EVALUATECUS = "/storejps/evaluate/evaluateCus";

    //回复说明
    public static String POST_REPLAYTOTHAT = "/storejps/evaluate/evaluateFeedback";
    // 评价排行榜
    public static String POST_EVALUATERANKING = "/storejps/evaluate/evaluateRanking";


}
