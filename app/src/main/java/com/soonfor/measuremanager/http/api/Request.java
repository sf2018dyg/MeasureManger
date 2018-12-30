package com.soonfor.measuremanager.http.api;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.home.login.bean.CurrentUserBean;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.http.httptools.AsyncUtils.AsyncCallback;
import com.soonfor.measuremanager.other.bean.SaveCustomerBean;
import com.soonfor.measuremanager.tools.PreferenceUtil;
import com.soonfor.repository.tools.DataTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import cn.jesse.nativelogger.NLogger;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

/**
 * 修改人：DC-ZhuSuiBo on 2018/2/28 15:37
 * 邮箱：suibozhu@139.com
 */
public class Request {
    public static final int GET_KEY = 2001;
    public static final int GET_PERSON = 2002;
    public static final int LOGIN = 2003;
    public static final int COMMON = 2000;
    public static final int GET_CURRENT_USER = 2044;
    public static final int GET_MINE = 2004;
    public static final int GET_SALE_TARGET = 2005;
    public static final int GET_RANKING = 2006;
    public static final int CHANGE_PASSWORD = 2007;
    public static final int GET_WORK_POINTS = 2008;
    public static final int GET_WORK_POINT_DETAILS = 2009;
    public static final int GET_PRIZE_RULE = 2022;
    public static final int EXECUTE_LOTTERY = 2023;
    public static final int GET_TOTAL_PERFORMANCE = 2024;
    public static final int GET_MONTH_PERFORMANCE = 2025;
    public static final int GET_HISTORY_PERFORMANCE = 2026;
    public static final int GET_MY_RANKING = 2027;
    public static final int GET_SHORTCUT_LIST = 2028;
    public static final int UPDATE_SHORTCUT = 2029;
    public static final int GET_OPTION_TYPE = 2004;
    public static final int GET_OPTION = 2005;
    public static final int SAVE_CUSTOM = 2006;
    public static final int SAVE_PORTRAITS = 2050;
    public static final int GET_TASK_DETAIL = 2010;
    public static final int GET_SEA_CUSTOM = 2011;
    public static final int GET_CUSTOM_PORTRAITS = 2012;
    public static final int SAVE_COMMUNICATE = 2013;
    public static final int SAVE_COMMUNICATE_2 = 2014;
    public static final int GET_CUSTOM_TASK = 2015;
    public static final int GET_CUSTOM_MESSAGE = 2016;
    public static final int SHARE_CUSTOM = 2017;
    public static final int GET_FOLLOW_RECORD = 2018;
    public static final int GET_ORDERS = 2019;
    public static final int GET_ORDER_TIMELINE = 2020;
    public static final int GET_MYCUSTOM = 2021;
    public static final int SET_DISPATCH = 2030;
    public static final int ORDER_DETAIL = 2031;
    public static final int ORDER_RECEIVE_DETAIL = 2032;
    public static final int EXCEPTION = 2033;
    public static final int RECEIVE_MONEY = 2034;
    public static final int PRE_ORDER = 2035;
    public static final int WAIT_TASK = 2036;
    public static final int UPLOAD_CONTRACT = 2037;
    public static final int GRAB_CUSTOMER = 2038;
    public static final int UPDATE_INFO = 1999;
    public static final int GET_MY_GROWTH_VALUE = 1998;
    public static final int GET_GROWTH_ITEM = 1997;
    public static final int GET_USER_RULES = 1996;
    public static final int POST_FILE = 2039;
    public static final int GET_STAFFS = 2040;
    public static final int UPDATE_TASK_RESULT = 2041;
    public static final int GET_TASK_POINT = 2042;
    public static final int GET_DELIVERY_INFO = 2043;
    public static final int GET_NEWS_COUNT = 3000;
    public static final int GET_CAROUSEL_PICTURE = 3001;
    public static final int GET_TASK_OVERVIEW = 3002;
    public static final int GET_GRAB_TASK = 3003;
    public static final int GET_DESIGNER_TOPN = 3004;
    public static final int GET_PROGRESS_TASK_INFO = 3005;
    public static final int GET_MEASURE_TASKS = 3006;
    public static final int GET_REMEASURE_TASKS = 3007;
    public static final int GET_TASK_COMPLETE_INFO = 3008;
    public static final int ACCEPT_TASK = 3009;
    public static final int REJECT_TASK = 3010;
    public static final int CONFIRM_APPOINT = 3011;
    public static final int GET_MEASURE_LIST = 3012;
    public static final int SAVE_NEW_MEASURE = 3013;
    public static final int PAGE_HOUSE_TEMPLATE = 3014;
    public static final int SET_TASK_STATUS = 3015;
    public static final int DELETE_MEASURE = 3016;
    public static final int GET_INTENTION_PRODUCTS = 3017;
    public static final int SET_STANDARD_TEMPLATE = 3018;
    public static final int FINISH_TASK = 3019;
    public static final int GET_MYCASE = 3020;
    public static final int GET_MYFAVROITE_CASE = 3021;
    public static final int GET_MYFAVROITE_HOT = 3022;
    public static final int GET_MYATTENTION = 3023;
    public static final int LOGOUT = 2999;
    public static final int UPLOADPIC = 2998;
    public static final int GETFJURI = 29999;

    public static final int NEEDCAPTCHA = 1505;

    public static final int DESIGN_GETALLTYPES_GoodsClass = 1506;
    public static final int DESIGN_GETALLTYPES_Door = 1507;

    public static final int BOARDTYPELIST = 1400;
    public static final int BOARDDETAILLIST = 1401;
    public static final int GETINFOBOARDDETAIL = 1402;
    public static final int PARTSORTLIST = 1403;
    public static final int PARTDETAILLIST = 1404;
    public static final int GETINFOPARTDETAIL = 1405;
    public static final int BRANDLIST = 1406;
    public static final int GETCASEDATALIST = 1407;

    public static final int PAGECASE = 1408;
    public static final int GETBYID = 1409;
    public static final int SAVECOLLECTS = 1410;
    public static final int SAVELIKES = 1412;
    public static final int SAVECOMMENT = 1413;
    public static final int SAVECOMMENTREPLY = 1414;
    public static final int PAGECOMMENTLIST = 1415;

    public static final int GET_SYSCODE = 1416;

    public static final int GET_ORDER_WORKPOINT = 1417;

    /**
     * @param cxt          获取服务器公匙
     * @param merchantCode 商户号
     * @param userName     用户名
     * @param callback
     */
    public static void sendGetKey(Context cxt, String merchantCode,
                                  String userName, AsyncCallback callback) {
        RequestParams params = new RequestParams();
        params.put("merchantCode", merchantCode);
        params.put("userName", userName);
        AsyncUtils.post(cxt, UserInfo.GET_KEY, params, GET_KEY, callback);
    }

    /**
     * @param cxt          登录
     * @param merchantCode 企业代号
     * @param userName     用户名
     * @param captcha      验证码
     * @param password     密码
     * @param clientType   客户端类型
     * @param loginType    登录类型
     * @param callback
     */
    public static void sendLogin(Context cxt, String merchantCode, String userName,
                                 String captcha, String password, int clientType, int loginType, AsyncCallback callback) {
        RequestParams params = new RequestParams();
        params.put("merchantCode", merchantCode);
        params.put("userName", userName);
        params.put("captcha", captcha);
        params.put("password", password);
        params.put("clientType", clientType);
        params.put("loginType", loginType);
        AsyncUtils.post(cxt, UserInfo.LOGIN, params, LOGIN, callback);
    }

    /**
     * 获取附件服务器地址
     *
     * @param cxt
     * @param type     内网还是外网
     * @param callback
     */
    public static void getFjUri(Context cxt, String type, AsyncCallback callback) {
        AsyncUtils.get(cxt, UserInfo.GETFJURI + type, GETFJURI, callback);
    }

    /**
     * @param cxt      登出
     * @param userName 用户名
     * @param callback
     */
    public static void sendLogout(Context cxt, String userName, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("userName", "userName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(cxt, UserInfo.LOGOUT, jo.toString(), LOGOUT, callback);
    }

    /**
     * 获取当前登录用户
     **/
    public static void getCurrentUser(Context context, AsyncCallback callback) {
        AsyncUtils.get(context, UserInfo.GET_CURRENT_USER, GET_CURRENT_USER, callback);
    }

    /**
     * @param context  我的个人资料
     * @param userId   用户id
     * @param callback
     */
    public static void getPerson(Context context, String userId, AsyncCallback callback) {
        AsyncUtils.get(context, UserInfo.GET_PERSON/*+ "&userId=" + userId*/, GET_PERSON, callback);
    }

    /**
     * @param context  获取个人信息
     * @param callback
     */
    public static void getMine(Context context, AsyncCallback callback) {
        AsyncUtils.get(context, UserInfo.GET_MINE, GET_MINE, callback);
    }

    /**
     * @param context    获取销售目标
     * @param periodType 周期类型(1-月度,2-季度,3-年度)
     * @param callback
     */
    public static void getSaleTarget(Context context, int periodType, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        try {
            CurrentUserBean cUserBean = PreferenceUtil.getCurrentUserBean();
            jo.put("userId", cUserBean.getUserId());
            jo.put("periodType", periodType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_SALE_TARGET, jo.toString(), GET_SALE_TARGET, callback);
    }

    /**
     * @param context  获取我的排行榜
     * @param numbers  前后用户数，例如：该值为1则为前1位和后1位，以此类推
     * @param callback
     */
    public static void getMyRanking(Context context, int numbers, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("numbers", numbers);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_MY_RANKING, jo.toString(), GET_MY_RANKING, callback);
    }

    /**
     * @param context    获取(销售额、好评数、确图数)等排行榜
     * @param periodType 周期类型（1月度  2季度  3年度）
     * @param rankType   排行榜类型（1店内 2全国）
     * @param pageNo     当前分页页码（从1开始）
     * @param pageSize   分页大小（每页记录数 默认10）
     * @param callback
     */
    public static void getRanking(Context context, int periodType, int rankType,
                                  int pageNo, int pageSize, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("periodType", periodType);
            jo.put("rankType", rankType);
            jo.put("pageNo", pageNo);
            jo.put("pageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_RANKING, jo.toString(), GET_RANKING, callback);
    }

    /**
     * @param context  修改密码
     * @param oldpaw   旧密码
     * @param newpaw   新密码
     * @param callback
     */
    public static void changePassword(Context context, String oldpaw, String newpaw, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("fPwd_Old", oldpaw);
            jo.put("fPwd_New", newpaw);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.CHANGE_PASSWORD, jo.toString(), CHANGE_PASSWORD, callback);
    }

    /**
     * @param context  工分中心-获取工分信息
     * @param callback
     */
    public static void getWorkPoints(Context context, AsyncCallback callback) {
        AsyncUtils.get(context, UserInfo.GET_WORK_POINTS, GET_WORK_POINTS, callback);
    }

    /**
     * @param context  获取工分明细
     * @param callback
     */
    public static void getWorkPointsDetails(Context context, AsyncCallback callback) {
        AsyncUtils.get(context, UserInfo.GET_WORK_POINT_DETAILS,
                GET_WORK_POINT_DETAILS, callback);
    }

    /**
     * @param context  获取奖品及规则
     * @param callback
     */
    public static void getPrizeRule(Context context, String prizeId, AsyncCallback callback) {
        AsyncUtils.get(context, UserInfo.GET_PRIZE_RULE +
                "?prizeId=" + prizeId, GET_PRIZE_RULE, callback);
    }

    /**
     * @param context  执行抽奖
     * @param prizeId
     * @param callback
     */
    public static void ExecuteLottery(Context context, String prizeId, AsyncCallback callback) {
        AsyncUtils.get(context, UserInfo.EXECUTE_LOTTERY +
                "?prizeId=" + prizeId, EXECUTE_LOTTERY, callback);
    }


    /**
     * @param context  获取绩效总额
     * @param callback
     */
    public static void getTotalPerformance(Context context, AsyncCallback callback) {
        AsyncUtils.post(context, UserInfo.GET_TOTAL_PERFORMANCE, "", GET_TOTAL_PERFORMANCE, callback);
    }

    /**
     * @param context  获取每月绩效明细
     * @param pageNo   当前分页页码(从1开始)
     * @param callback
     */
    public static void getMonthPerformance(Context context, int pageNo, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageNo ", pageNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_MONTH_PERFORMANCE, jsonObject.toString(), GET_MONTH_PERFORMANCE, callback);
    }

    /**
     * @param context   获取历史绩效
     * @param monthType 月度（形式：201712），默认为当月
     * @param pageNo    当前分页页码(从1开始)
     * @param callback
     */
    public static void getHistoryPerformance(Context context, String monthType, int pageNo, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("monthType ", monthType);
            jsonObject.put("pageNo ", pageNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_HISTORY_PERFORMANCE, jsonObject.toString(), GET_HISTORY_PERFORMANCE, callback);
    }

    /**
     * @param context  获取功能列表
     * @param visible  显示状态 (默认为：空[返回所有数据]，0：返回不显示，1：返回显示）
     * @param callback
     */
    public static void getShortcutList(Context context, int visible, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("visible ", visible);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_SHORTCUT_LIST, jsonObject.toString(), GET_SHORTCUT_LIST, callback);
    }

    /**
     * @param context      更新快捷功能
     * @param code         快捷功能编码
     * @param name         快捷功能名称
     * @param index        快捷功能排序
     * @param command      执行命令
     * @param visible      显示状态(0:不显示，1:显示）
     * @param visibleIndex 显示排序
     * @param callback
     */
    public static void updateShortcut(Context context, String code, String name, int index, String command, int visible, int visibleIndex, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
            jsonObject.put("name", name);
            jsonObject.put("index", index);
            jsonObject.put("command", command);
            jsonObject.put("visible", visible);
            jsonObject.put("visibleIndex", visibleIndex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.UPDATE_SHORTCUT, jsonObject.toString(), UPDATE_SHORTCUT, callback);
    }


    /**
     * 获取任务详情
     *
     * @param taskNo 任务单号
     */
    public static void getTaskDetail(Context context, String taskNo, AsyncCallback callback) {
        AsyncUtils.get(context, CustomerInfo.GET_TASK_DETAIL + "?taskNo=" + taskNo, GET_TASK_DETAIL, callback);
    }


    /**
     * 获取公海客户
     *
     * @param query 查询参数
     */
    public static void getSeaCustom(Context context, String query, AsyncCallback callback) {
        if (query != null) {
            JSONObject jo = new JSONObject();
            try {
                jo.put("queryInfo ", query);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AsyncUtils.post(context, CustomerInfo.GET_SEA_CUSTOM, jo.toString(), GET_SEA_CUSTOM, callback);
        }
        AsyncUtils.post(context, CustomerInfo.GET_SEA_CUSTOM, "", GET_SEA_CUSTOM, callback);
    }


    /**
     * 获取客户信息
     */
    public static void getCustomerInfo(Context context, AsyncCallback callback, String customerId) {
        AsyncUtils.get(context, CustomerInfo.GET_CUSTOM_MESSAGE + "?customerId=" + customerId, GET_CUSTOM_MESSAGE, callback);
    }


    /**
     * 获取用户画像
     */
    public static void getCustomerPortraits(Context context, AsyncCallback callback, String customerId) {
        AsyncUtils.get(context, CustomerInfo.GET_CUSTOM_PORTRAITS + "?customerId=" + customerId, GET_CUSTOM_PORTRAITS, callback);
    }

    /**
     * 保存用户画像
     **/
    public static void savePortrait(Context context, AsyncCallback callback, String list) {
        AsyncUtils.post(context, CustomerInfo.SAVE_PORTRAITS, list, SAVE_PORTRAITS, callback);
    }
    /**
     * 获取用户画像选项
     */
    public static void getCustomProfile(Context context, AsyncCallback callback) {

        AsyncUtils.get(context, UserInfo.GET_OPTION + "?optionType=CustPortraitType", GET_OPTION, callback);
    }

    /**
     * 分享客户
     */
    public static void shareCustom(Context context, AsyncCallback callback, String json) {
        AsyncUtils.post(context, CustomerInfo.SHARE_CUSTOM, json, SHARE_CUSTOM, callback);
    }


    /**
     * 获取跟进记录
     */
    public static void getFollowRecord(Context context, AsyncCallback callback, String json) {
        AsyncUtils.post(context, CustomerInfo.GET_FOLLOW_RECORD, json, GET_FOLLOW_RECORD, callback);
    }


    /**
     * 获取客户订单
     */
    public static void getOrders(Context context, AsyncCallback callback, String userid, int pageNo, int pageSize) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("customerId", userid);
            jo.put("pageNo", pageNo);
            jo.put("pageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, CustomerInfo.GET_ORDERS, jo.toString(), GET_ORDERS, callback);
    }


    /**
     * 获取订单时间线
     */
    public static void getTimeLine(Context context, AsyncCallback callback, String orderNo) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("id", orderNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, CustomerInfo.GET_ORDER_TIMELINE, jo.toString(), GET_ORDER_TIMELINE, callback);
    }

    /**
     * 获取我的客户
     */
    public static void getMyCustomer(Context context, AsyncCallback callback, String queryInfo, String userId
            , String custType, int pageNo, int pageSize) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("queryInfo", queryInfo);
            jo.put("userId", userId);
            jo.put("custType", custType);
            jo.put("pageNo", pageNo);
            jo.put("pageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, CustomerInfo.GET_MYCUSTOM, jo.toString(), GET_MYCUSTOM, callback);
    }

    /**
     * @param context  保存客户
     * @param callback
     */
    public static void saveCustomer(Context context, AsyncCallback callback, SaveCustomerBean bean) {
        RequestParams params = new RequestParams();

        Gson gson = new Gson();
        String json = gson.toJson(bean, SaveCustomerBean.class);
        params.put("input", json);

        AsyncUtils.post(context, CustomerInfo.SAVE_CUSTOMER, params, SAVE_CUSTOM, callback);
    }

    /**
     * @param context  获取选项类型
     * @param callback
     */
    public static void getOptionType(Context context, AsyncCallback callback) {
        AsyncUtils.get(context, UserInfo.GET_OPTION_TYPE, GET_OPTION_TYPE, callback);
    }


    /**
     * @param context  销货订单详情
     * @param callback
     */
    public static void getOrderDetail(Activity context, AsyncCallback callback, String orderNo) {
        AsyncUtils.get(context, CustomerInfo.ORDER_DETAIL + "?orderNo=" + orderNo, ORDER_DETAIL, callback);
    }

    /**
     * @param context  获取异常
     * @param callback
     */
    public static void getException(Context context, AsyncCallback callback, String orderNo) {
        AsyncUtils.get(context, CustomerInfo.EXCEPTION + "?orderNo=" + orderNo, EXCEPTION, callback);
    }

    /**
     * @param context  获取收款记录
     * @param callback
     */
    public static void getReceiveMoney(Context context, AsyncCallback callback, String orderNo) {
        AsyncUtils.get(context, CustomerInfo.RECEIVE_MONEY + "?orderNo=" + orderNo, RECEIVE_MONEY, callback);
    }


    /**
     * @param context  获取预订单
     * @param callback
     */
    public static void getPreOrder(Context context, AsyncCallback callback, String orderNo) {
        AsyncUtils.get(context, CustomerInfo.PRE_ORDER + "?orderNo=" + orderNo, PRE_ORDER, callback);
    }

    /**
     * @param context  代办任务
     * @param callback
     */
    public static void getWaitTask(Context context, AsyncCallback callback, String json) {
        AsyncUtils.post(context, CustomerInfo.WAIT_TASK, json, WAIT_TASK, callback);
    }

    /**
     * @param context  上传合同
     * @param callback
     */
    public static void uploadContract(Context context, AsyncCallback callback, String json) {
        AsyncUtils.post(context, CustomerInfo.UPLOAD_CONTRACT, json, UPLOAD_CONTRACT, callback);
    }


    /**
     * @param context  获取任务详情
     * @param callback
     */
    public static void getTaskDetail(Context context, AsyncCallback callback, String json) {
        AsyncUtils.post(context, CustomerInfo.GET_TASK_DETAIL, json, GET_TASK_DETAIL, callback);
    }


    /**
     * @param context  添加任务（跟进、普通）
     * @param callback
     */
    public static void saveTask(Context context, AsyncCallback callback, String json) {
        AsyncUtils.post(context, CustomerInfo.SAVE_TASK, json, GRAB_CUSTOMER, callback);
    }

    /**
     * @param context  获取选项类型
     * @param callback
     */
    public static void getOption(Context context, AsyncCallback callback, String option, int requestCode) {
        AsyncUtils.get(context, UserInfo.GET_OPTION + "?optionType=" + option, requestCode, callback);
    }


    /**
     * 获取各类人员
     *
     * @param context
     * @param callback
     * @param type
     */
    public static void getStaffs(Context context, AsyncCallback callback, int type) {
        AsyncUtils.get(context, UserInfo.GET_STAFFS + "?staffType=" + type, GET_STAFFS, callback);
    }

    /**
     * @param context
     * @param infoType 要修改的信息类型（1-性别/2-出生年月/3-电话/4-头像）
     * @param newValue 修改信息的新值 例如，infoType=1时,newValue='男'或者infoType=2时,newValue='2011-12-12'
     * @param callback
     */
    public static void upDateInfo(Context context, int infoType, String newValue, AsyncCallback callback) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("infoType", infoType);
            jo.put("newValue", newValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NLogger.e("修改", jo.toString());
        AsyncUtils.post(context, UserInfo.UPDATE_INFO, jo.toString(), UPDATE_INFO, callback);
    }

    /**
     * @param context  获取我的成长值
     * @param callback
     */
    public static void getMygrowthValue(Context context, AsyncCallback callback) {
        AsyncUtils.get(context, UserInfo.GET_MY_GROWTH_VALUE, GET_MY_GROWTH_VALUE, callback);
    }

    /**
     * @param context  获取成长明细（分页）
     * @param callback
     * @param pageNo   当前分页页码(从1开始)
     * @param pageSize 分页大小——每页记录数，默认 10
     */
    public static void getGrowthItem(Context context, AsyncCallback callback, int pageNo, int pageSize) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("pageNo", pageNo);
            jo.put("pageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_GROWTH_ITEM, jo.toString(), GET_GROWTH_ITEM, callback);
    }

    /**
     * @param context  获取用户相关规则（攻略）
     * @param callback
     */
    public static void getUserRules(Context context, AsyncCallback callback) {
        AsyncUtils.get(context, UserInfo.GET_USER_RULES, GET_USER_RULES, callback);
    }

    /**
     * 获取新消息数
     **/
    public static void getNewsCount(Context context, AsyncCallback callback, String userId) {
        AsyncUtils.get(context, UserInfo.GET_NEWS_COUNT + "?userId=" + userId, GET_NEWS_COUNT, callback);
    }

    /*
     * 获取轮播图片
     * */
    public static void getCarouselPicture(Context context, AsyncCallback callback, String id) {
        /*JSONObject jo = new JSONObject();
        try {
            jo.put("id", id);
        } catch (Exception e0) {
            e0.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_CAROUSEL_PICTURE, jo.toString(), GET_CAROUSEL_PICTURE, callback);*/

        /**
         * 假数据用来演示
         * **/
        AsyncUtils.get(context, UserInfo.GET_CAROUSEL_PICTURE + "?id=" + id, GET_CAROUSEL_PICTURE, callback);
    }

    /**
     * 获取任务概览
     **/
    public static void getTaskOverview(Context context, AsyncCallback callback, String userid) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("userId", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_TASK_OVERVIEW, jo.toString(), GET_TASK_OVERVIEW, callback);
    }

    /**
     * 获取待抢任务数量
     **/
    public static void getGrabTask(Context context, AsyncCallback callback) {
        AsyncUtils.get(context, UserInfo.GET_GRAB_TASK, GET_GRAB_TASK, callback);
    }

    /**
     * 获取前10设计师
     **/
    public static void getDesignerTopn(Context context, AsyncCallback callback, String n) {
        AsyncUtils.get(context, UserInfo.GET_DESIGNER_TOPN, GET_DESIGNER_TOPN, callback);
    }

    public static class Grab {
        public static final int GET_GRABORDER_TASK_LIST = 101;
        public static final int POST_GRAB = 102;

        /**
         * 获取待抢单任务列表
         */
        public static void getGraborderList(Context context, String taskType, int pageNo, int pageSize, AsyncCallback callback) {
            JSONObject jo = new JSONObject();
            try {
                //jo.put("taskType", taskType);
                jo.put("pageNo", pageNo);
                jo.put("pageSize", pageSize);
            } catch (Exception e0) {
                e0.printStackTrace();
            }
            AsyncUtils.post(context, UserInfo.GET_GRABORDER_TASK_LIST, jo.toString(), GET_GRABORDER_TASK_LIST, callback);
        }
        /**
         * 获取待抢单任务列表
         */
        public static void postToGrab(Context context, String taskid, String taskType, AsyncCallback callback) {
            JSONObject jo = new JSONObject();
            try {
                jo.put("taskType", taskType);
                jo.put("taskId", taskid);
            } catch (Exception e0) {
                e0.printStackTrace();
            }
            AsyncUtils.post(context, UserInfo.POST_GRAB_TASK, jo.toString(), POST_GRAB, callback);

        }
    }

    public static class Design {
        public static final int SURE_DESIGNPAGER = 4015;

        /**
         * 获取设计任务列表
         **/
        public static void getDesignTaskList(Context context, int type, String keyWord, int pageNo, int pageSize, AsyncCallback callback) {

            JSONObject jo = new JSONObject();
            try {
                jo.put("status", type);
                jo.put("keyword", keyWord);
                jo.put("pageNo", pageNo);
                jo.put("pageSize", pageSize);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            AsyncUtils.post(context, UserInfo.GET_DESIGN_TASK_LIST, jo.toString(), Loft.GET_LOFTING_TASK_LIST, callback);
        }

        /**
         * 确图
         **/
        public static void sureDesignPager(Context context, String taskno, AsyncCallback callback) {

            JSONObject jo = new JSONObject();
            try {
                jo.put("taskNo", taskno);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AsyncUtils.post(context, UserInfo.SURE_DESIGNPAGER, jo.toString(), SURE_DESIGNPAGER, callback);
        }
    }

    public static class Loft {
        public static final int GET_LOFTING_TASK_LIST = 4001;
        public static final int GET_LOFTING_DETAIL_LIST = 4002;
        public static final int GET_LOFTING_LIST_1 = 4003;
        public static final int GET_LOFTING_LIST_2 = 4004;
        public static final int GET_TASK_DETAIL_LOFT_LIANGCHI = 4005;
        public static final int GET_TASK_DETAIL_LOFT_FUCHI_FRAGMENT = 4006;
        public static final int GET_TASK_DETAIL_LOFT_FUCHI_ACTIVITY = 4007;
        public static final int GET_TASK_DETAIL_LOFT_LOFTING = 4008;
        public static final int SET_LOFT_DATA = 40009;//提交放样数据
        public static final int DETELE_LOFT_PIC = 40010;//删除放样图片
        public static final int SET_MARK_PICTURE = 40011;//提交放样图片
        public static final int SET_WALL_MARKSTAUTS = 40012;//提交墙面放样状态
        public static final int GET_FUCHI_TASKNO_BY_ORDERNO_FRAGMENT = 4013;//根据订单号获取复尺任务号（Fragment）
        public static final int GET_FUCHI_TASKNO_BY_ORDERNO_ACTIVITY = 4014;//根据订单号获取复尺任务号（Activity）
        public static final int GET_LOFTING_WALLPATHS = 4015;// 获取当前户型所有墙面的放样图片网络路径

        /**
         * 获取任务列表
         **/
        public static void getLoftingTaskList(Context context, int type, int pageNo, int pageSize, AsyncCallback callback) {
            JSONObject jo = new JSONObject();
            try {
                jo.put("status", type);
                jo.put("taskType", "mark");
                jo.put("pageNo", pageNo);
                jo.put("pageSize", pageSize);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AsyncUtils.post(context, UserInfo.GET_LOFTING_TASK_LIST, jo.toString(), GET_LOFTING_TASK_LIST, callback);
        }

        /**
         * 获取某客户的放样列表
         */
        public static void getLoftingDetailList(Context context, String taskno, AsyncCallback callback) {
            JSONObject jo = new JSONObject();
            try {
                jo.put("taskNo", taskno);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AsyncUtils.post(context, UserInfo.GET_LOFTING_DETAIL_LIST, jo.toString(), GET_LOFTING_DETAIL_LIST, callback);
        }

        /**
         * 获取当前户型所有墙面的放样图片网络路径
         */
        public static void getWallPaths(Context context, String sContractNo, String wallcode, AsyncCallback callback) {
            JSONObject jo = new JSONObject();
            try {
                jo.put("contractNo", sContractNo);
                jo.put("wallCode", wallcode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AsyncUtils.post(context, UserInfo.GET_LOFTING_LOFTPATH_LIST, jo.toString(), GET_LOFTING_WALLPATHS, callback);
        }

        /**
         * 根据放样任务号获取量尺、复尺任务号
         */
        public static void getRemeasureTasknoByOrderNo(Context context, String taskno, AsyncCallback callback, int REQUESTCODE) {
            JSONObject jo = new JSONObject();
            try {
                jo.put("orderNo", taskno);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AsyncUtils.post(context, UserInfo.GET_REMEASURE_TASKNO_BY_LOGT, jo.toString(), REQUESTCODE, callback);
        }

        /**
         * 获取放样清单
         */
        public static void getLoftingList(Context context, int type, String taskno, String contractno, String wallcode, AsyncCallback callback) {
            JSONObject jo = new JSONObject();
            try {
                jo.put("taskNo", taskno);
                jo.put("contractNo", contractno);
                jo.put("wallCode", wallcode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (type == 1) {
                AsyncUtils.post(context, UserInfo.GET_LOFTING_LIST, jo.toString(), GET_LOFTING_LIST_1, callback);
            } else if (type == 2) {
                AsyncUtils.post(context, UserInfo.GET_LOFTING_LIST, jo.toString(), GET_LOFTING_LIST_2, callback);
            }
        }

        /**
         * 提交放样数据
         */
        public static void setMarkData(Context context, String objid, int[] actSize, String remark, AsyncCallback callback) {
            JSONObject jo = new JSONObject();
            try {
                jo.put("objId", objid);
                jo.put("actWidth", actSize[0]);
                jo.put("actHeight", actSize[1]);
                jo.put("actDeep", actSize[2]);
                jo.put("remark", remark);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AsyncUtils.post(context, UserInfo.SET_LOFT_DATA, jo.toString(), SET_LOFT_DATA, callback);

        }

        /**
         * 量尺、复尺、放样信息
         */
        public static void getTaskDetailInfo(Context context, AsyncCallback callback, String taskNo, String taskType, String orderNo, int RESULTCODE) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("taskNo", taskNo);
                jsonObject.put("taskType", taskType);
                jsonObject.put("orderNo", orderNo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AsyncUtils.post(context, UserInfo.GET_TASK_COMPLETE_INFO, jsonObject.toString(), RESULTCODE, callback);
        }

    }

    public static class Me {

        public static final int GETQRCOARD = 3024;
        public static final int UPDATEQRCOARD = 3025;
        public static final int GET_MYCARD = 3026;
        public static final int GET_BRANDSTORY = 3027;

        /**
         * 获取我的名片
         */
        public static void getMyCard(Context context, AsyncCallback callback, String userName) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", userName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AsyncUtils.post(context, UserInfo.GET_MYCARD, jsonObject.toString(), GET_MYCARD, callback);
        }

        /**
         * 获取我的设计案例
         **/
        public static void getMyCases(Context context, AsyncCallback callback, String userName) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", userName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AsyncUtils.post(context, UserInfo.GET_MYCASE, jsonObject.toString(), GET_MYCASE, callback);
        }

        /**
         * 获取品牌故事
         */
        public static void getBandStory(Context context, AsyncCallback callback, String userName) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", userName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AsyncUtils.post(context, UserInfo.GET_BRANDSTORY, jsonObject.toString(), GET_BRANDSTORY, callback);
        }

        /**
         * 获取我的二维码名片
         */
        public static void getQRcode(Context context, AsyncCallback callback, String userid) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("userId", userid);
            } catch (JSONException e) {
                e.fillInStackTrace();
            }
            AsyncUtils.post(context, UserInfo.GET_QRCOARD, jsonObject.toString(), GETQRCOARD, callback);
        }

        /**
         * 上传二维码
         */
        public static void uploadQrCode(Context context, AsyncCallback callback, String attachName, String attachUrl, String fileSize) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("attachName", attachName);
                jsonObject.put("attachUrl", attachUrl);
                jsonObject.put("fileSize", fileSize);
            } catch (JSONException e) {
                e.fillInStackTrace();
            }
            AsyncUtils.post(context, UserInfo.UPLOAD_QRCARD, jsonObject.toString(), UPDATEQRCOARD, callback);
        }

        /**
         * 获取我的收藏
         */
        public static void getMyFavroite(int type, Context context,  int pageNo, int pageSize, AsyncCallback callback) {
            JSONObject jsonObject = new JSONObject();
            if(type==1) {
                try {
                    jsonObject.put("pageNo", pageNo);//页码
                    jsonObject.put("pageSize", pageSize);//页大小
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AsyncUtils.post(context, UserInfo.COLLECTLIST_CASE, jsonObject.toString(), GET_MYFAVROITE_CASE, callback);
            }else if(type == 2){
                AsyncUtils.post(context, UserInfo.COLLECTLIST_HOT, jsonObject.toString(), GET_MYFAVROITE_HOT, callback);
            }
        }
        /**
         * 获取我的关注
         */
        public static void getMyAttentions(Context context, AsyncCallback callback, String userName, String searchText) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", userName);
                jsonObject.put("search", searchText);
            } catch (JSONException e) {
                e.printStackTrace();
            }
          //  AsyncUtils.post(context, UserInfo.GET_MYATTENTION, jsonObject.toString(), GET_MYATTENTION, callback);
        }
    }

    /**
     * 获取任务详情
     **/
    public static void getProgressTaskInfo(Context context, AsyncCallback callback, String taskNo, String taskType, String orderNo) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("taskNo", taskNo);
            jsonObject.put("taskType", taskType);
            jsonObject.put("orderNo", orderNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_PROGRESS_TASK_INFO, jsonObject.toString(), GET_PROGRESS_TASK_INFO, callback);
    }

    /**
     * 查看量尺/设计/复尺/放样/送货/安装任务完成结果
     **/
    public static void getTaskCompleteInfo(Context context, AsyncCallback callback, String taskNo, String taskType, String orderNo) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("taskNo", taskNo);
            jsonObject.put("taskType", taskType);
            jsonObject.put("orderNo", orderNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_TASK_COMPLETE_INFO, jsonObject.toString(), GET_TASK_COMPLETE_INFO, callback);
    }

    /**
     * 获取量尺任务（测量App）
     **/
    public static void getMeasureTasks(Context context, AsyncCallback callback, int type, int pageNo, int pageSize) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", type);
            jsonObject.put("taskType", "measure");
            jsonObject.put("pageNo", pageNo);
            jsonObject.put("pageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_MEASURE_TASKS, jsonObject.toString(), GET_MEASURE_TASKS, callback);
    }

    /**
     * 获取复尺任务（测量App）
     **/
    public static void getRemeasureTasks(Context context, AsyncCallback callback, int type, int pageNo, int pageSize) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", type);
            jsonObject.put("taskType", "remeasure");
            jsonObject.put("pageNo", pageNo);
            jsonObject.put("pageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_REMEASURE_TASKS, jsonObject.toString(), GET_REMEASURE_TASKS, callback);
    }


    /**
     * 查看量尺/设计/复尺/放样/送货/安装任务完成结果
     **/
    public static void getTaskCompleteInfoWithContractNo(Context context, AsyncCallback callback, String taskNo, String taskType, String orderNo, String contractNo) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("taskNo", taskNo);
            jsonObject.put("contractNo", contractNo);
            jsonObject.put("taskType", taskType);
            jsonObject.put("orderNo", orderNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_TASK_COMPLETE_INFO, jsonObject.toString(), GET_TASK_COMPLETE_INFO, callback);
    }

    /**
     * 测量任务的接收
     **/
    public static void acceptTask(Context context, AsyncCallback callback, String taskPersonId, String taskType, String taskNo) {
        JSONObject jsonObject = new JSONObject();
        try {
            //jsonObject.put("taskPersonId", PreferenceUtil.getCurrentUserBean().getSalesId());
            jsonObject.put("taskType", taskType);
            jsonObject.put("taskNo", taskNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.ACCEPT_TASK, jsonObject.toString(), ACCEPT_TASK, callback);
    }

    /**
     * 测量任务的拒收
     **/
    public static void rejectTask(Context context, AsyncCallback callback, String taskPersonId, String taskType, String taskNo, String taskDescribe) {
        JSONObject jsonObject = new JSONObject();
        try {
            //jsonObject.put("taskPersonId", PreferenceUtil.getCurrentUserBean().getSalesId());
            jsonObject.put("taskType", taskType);
            jsonObject.put("taskNo", taskNo);
            jsonObject.put("taskDescribe", taskDescribe);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.REJECT_TASK, jsonObject.toString(), REJECT_TASK, callback);
    }

    /**
     * 量尺、复尺的确认预约
     **/
    public static void confirmAppoint(Context context, AsyncCallback callback, String taskNo, String taskPersonId, String subscribeDescribe, String subscribeDate) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("taskNo", taskNo);
            jsonObject.put("taskPersonId", taskPersonId);
            jsonObject.put("subscribeDescribe", subscribeDescribe);
            jsonObject.put("subscribeDate", subscribeDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.CONFIRM_APPOINT, jsonObject.toString(), CONFIRM_APPOINT, callback);
    }

    /**
     * 查看量尺/立即量尺
     **/
    public static void getMeasureList(Context context, AsyncCallback callback, String taskNo) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("taskNo", taskNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_MEASURE_LIST, jsonObject.toString(), GET_MEASURE_LIST, callback);
    }

    /**
     * 查看量尺/新建量尺
     */
    public static void saveNewMeasure(Context context, AsyncCallback callback, String floorName, String taskNo) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("taskNo", taskNo);
            jsonObject.put("floorName", floorName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.SAVE_NEW_MEASURE, jsonObject.toString(), SAVE_NEW_MEASURE, callback);
    }

    /**
     * 获取户型模版列表
     **/
    public static void pageHouseTemplate(Context context, AsyncCallback callback, String pageNo, String pageSize, String name) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageNo", pageNo);
            jsonObject.put("pageSize", pageSize);
            jsonObject.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.PAGE_HOUSE_TEMPLATE, jsonObject.toString(), PAGE_HOUSE_TEMPLATE, callback);
    }

    /**
     * 设置任务状态
     * status   1是测量中，2是完成测量
     **/
    public static void setTaskStatus(Context context, AsyncCallback callback, String taskNo, String contractNo, String status) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("taskNo", taskNo);
            jsonObject.put("contractNo", contractNo);
            jsonObject.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.SET_TASK_STATUS, jsonObject.toString(), SET_TASK_STATUS, callback);
    }

    /**
     * 删除楼层
     **/
    public static void deleteMeasure(Context context, AsyncCallback callback, String contractNo) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contractNo", contractNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.DELETE_MEASURE, jsonObject.toString(), DELETE_MEASURE, callback);
    }

    /**
     * 获取意向产品
     **/
    public static void getIntentionProducts(Context context, AsyncCallback callback, String orderNo) {
        AsyncUtils.get(context, UserInfo.GET_INTENTION_PRODUCTS + "?orderNo=" + orderNo, GET_INTENTION_PRODUCTS, callback);
    }

    /**
     * 删除图片
     */
    public static void deletePic(Context context, AsyncCallback callback, String picId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", picId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.DELETE_LOFT_PIC, jsonObject.toString(), Loft.DETELE_LOFT_PIC, callback);
    }

    /**
     * 提交放样图片地址
     */
    public static void setMarkPic(Context context, AsyncCallback callback, String parms[]) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("url", parms[0]);
            jsonObject.put("taskNo", parms[1]);
            jsonObject.put("contractNo", parms[2]);
            jsonObject.put("roomCode", parms[3]);
            jsonObject.put("roomName", parms[4]);
            jsonObject.put("wallCode", parms[5]);
            jsonObject.put("wallName", parms[6]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.SET_LOFT_PIC, jsonObject.toString(), Loft.SET_MARK_PICTURE, callback);
    }

    /**
     * 提交墙面放样状态
     */
    public static void setWallMarkStatus(Context context, AsyncCallback callback, String parms[]) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contractNo", parms[0]);
            jsonObject.put("roomCode", parms[1]);
            jsonObject.put("wallCode", parms[2]);
            jsonObject.put("status", parms[3]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.SET_WALLMARKSTAUTS, jsonObject.toString(), Loft.SET_WALL_MARKSTAUTS, callback);
    }

    /**
     * 上传文件
     */
    public static void uploadFileToCrm(Context context, File imgfile, int requestCode, AsyncCallback callback) {
        RequestParams params = new RequestParams();
        try {
            params.put("imgFile", imgfile);
            params.put("dir", "image");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        AsyncUtils.uploadFileToCrm(context, UserInfo.UPLOAD_FILE_TO_CRM, params, requestCode, callback);
    }

    /**
     * 设置标准模版
     **/
    public static void setStandardTemplate(Context context, AsyncCallback callback, String contractNo) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contractNo", contractNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.SET_STANDARD_TEMPLATE, jsonObject.toString(), SET_STANDARD_TEMPLATE, callback);
    }

    /**
     * 完成任务
     **/
    public static void finishTask(Context context, AsyncCallback callback, String taskNo) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("taskNo", taskNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.FINISH_TASK, jsonObject.toString(), FINISH_TASK, callback);
    }

    /**
     * 请求是否需要图形验证码
     **/
    public static void getNeedCaptcha(Context context, AsyncCallback callback) {
        AsyncUtils.getNoHeader(context, "/needCaptcha", NEEDCAPTCHA, callback);
    }

    /**
     * POST色板类别列表
     **/
    public static void getBoardTypeList(Context context, int pageNo, int pageSize, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageNo", pageNo);
            jsonObject.put("pageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.BOARDTYPELIST, jsonObject.toString(), BOARDTYPELIST, callback);
    }

    /**
     * POST色板列表
     * 条件搜索-色板名称 keyword string @mock=A
     * pageSize number @mock=10
     * pageNo number @mock=1
     * 0下架，1上架 *status number @mock=1
     **/
    public static void getBoardDetailList(Context context, String keyword, String bid, int pageNo, int pageSize, int status, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("keyword", keyword);
            jsonObject.put("bid", bid);//色板类别id
            jsonObject.put("pageNo", pageNo);
            jsonObject.put("pageSize", pageSize);
            jsonObject.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.BOARDDETAILLIST, jsonObject.toString(), BOARDDETAILLIST, callback);
    }

    /**
     * POST色板详情
     **/
    public static void getInfoBoardDetail(Context context, String id, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GETINFOBOARDDETAIL, jsonObject.toString(), GETINFOBOARDDETAIL, callback);
    }

    /**
     * POST配件类别列表
     **/
    public static void getPartSortList(Context context, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        AsyncUtils.post(context, UserInfo.PARTSORTLIST, jsonObject.toString(), PARTSORTLIST, callback);
    }

    /**
     * POST配件列表条件搜索-名称 keyword string @mock=房顶
     * pageSize number @mock=10
     * pageNo number @mock=1
     * 1上架，0下架 *status number @mock=1
     **/
    public static void getPartDetailList(Context context, String keyword, String brandname, String pid, int pageNo, int pageSize, int status, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("keyword", keyword);
            jsonObject.put("brandname", brandname);
            jsonObject.put("pid", pid);
            jsonObject.put("pageNo", pageNo);
            jsonObject.put("pageSize", pageSize);
            jsonObject.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.PARTDETAILLIST, jsonObject.toString(), PARTDETAILLIST, callback);
    }

    /**
     * POST配件详情
     */
    public static void getInfoPartDetail(Context context, String id, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GETINFOPARTDETAIL, jsonObject.toString(), GETINFOPARTDETAIL, callback);
    }

    /**
     * POST配件品牌列表
     **/
    public static void getBrandList(Context context, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        AsyncUtils.post(context, UserInfo.BRANDLIST, jsonObject.toString(), BRANDLIST, callback);
    }

    /**
     * 获取案例基础数据(筛选侧滑界面数据)
     **/
    public static void getCaseDataList(Context context, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        AsyncUtils.post(context, UserInfo.GETCASEDATALIST, jsonObject.toString(), GETCASEDATALIST, callback);
    }

    /**
     * 更多方案列表
     **/
    public static void pageCase(Context context, List<String> meritsIdList, String sortColumn, String sceneId, String keyword, List<String> styleIdList, List<String> areaIdList, int pageNo, int pageSize, List<String> huxingIdList, List<String> priceIdList, int sortOrder, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        Gson gson = new Gson();
        String str = "";
        try {
            jsonObject.put("sortColumn", sortColumn);//排序字段(最新：a.create_time, 意向数 ：b.intents, 最赞：b.likes, 收藏数:b.collects)
            //jsonObject.put("cstid", cstid);//客户id
            jsonObject.put("sceneId", sceneId);//栏目id(获取全部传空字符串)
            jsonObject.put("keyword", keyword);//关键字
            jsonObject.put("pageNo", pageNo);//页码
            jsonObject.put("pageSize", pageSize);//页大小
            jsonObject.put("sortOrder", sortOrder);//排序(0:升序，1:降序)

            str = jsonObject.toString();

            String tmp1 = "[";
            for (int i = 0; i < meritsIdList.size(); i++) {
                tmp1 += "\"" + meritsIdList.get(i) + "\"";
                if (i != meritsIdList.size() - 1) {
                    tmp1 += ",";
                }
            }
            tmp1 += "]";
            str = str.substring(0, str.length() - 1) + ",\"meritsIdList\":" + tmp1;

            String tmp2 = "[";
            for (int i = 0; i < styleIdList.size(); i++) {
                tmp2 += "\"" + styleIdList.get(i) + "\"";
                if (i != styleIdList.size() - 1) {
                    tmp2 += ",";
                }
            }
            tmp2 += "]";
            str += ",\"styleIdList\":" + tmp2;

            String tmp3 = "[";
            for (int i = 0; i < areaIdList.size(); i++) {
                tmp3 += "\"" + areaIdList.get(i) + "\"";
                if (i != areaIdList.size() - 1) {
                    tmp3 += ",";
                }
            }
            tmp3 += "]";
            str += ",\"areaIdList\":" + tmp3;

            String tmp4 = "[";
            for (int i = 0; i < huxingIdList.size(); i++) {
                tmp4 += "\"" + huxingIdList.get(i) + "\"";
                if (i != huxingIdList.size() - 1) {
                    tmp4 += ",";
                }
            }
            tmp4 += "]";
            str += ",\"huxingIdList\":" + tmp4;

            String tmp5 = "[";
            for (int i = 0; i < priceIdList.size(); i++) {
                tmp5 += "\"" + priceIdList.get(i) + "\"";
                if (i != priceIdList.size() - 1) {
                    tmp5 += ",";
                }
            }
            tmp5 += "]";
            str += ",\"priceIdList\":" + tmp5 + "}";

            NLogger.w("最终提交参数: " + str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.PAGECASE, str, PAGECASE, callback);
    }

    /**
     * 获取案例详情
     **/
    public static void getById(Context context, String id, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);//案例id
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GETBYID, jsonObject.toString(), GETBYID, callback);
    }

    /**
     * 收藏/取消收藏
     **/
    public static void saveCollects(Context context, String id, int status, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);//案例id
            jsonObject.put("status", status);//1:收藏，0:取消收藏
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.SAVECOLLECTS, jsonObject.toString(), SAVECOLLECTS, callback);
    }

    /**
     * 添加点赞
     **/
    public static void saveLikes(Context context, String id, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);//案例id
            jsonObject.put("status",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.SAVELIKES, jsonObject.toString(), SAVELIKES, callback);
    }

    /**
     * POST保存案例评论
     **/
    public static void saveComment(Context context, String caseId, String content, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("caseId", caseId);//案例id
            jsonObject.put("content", content);//内容
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.SAVECOMMENT, jsonObject.toString(), SAVECOMMENT, callback);
    }

    /**
     * POST保存案例评论回复
     **/
    public static void saveCommentReply(Context context, String commentId, String replyUserId, String content, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("commentId", commentId);//评论id
            jsonObject.put("replyUserId", replyUserId);//回复对象id
            jsonObject.put("content", content);//内容
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.SAVECOMMENTREPLY, jsonObject.toString(), SAVECOMMENTREPLY, callback);
    }

    /**
     * POST获取案例评论列表
     **/
    public static void pageCommentList(Context context, String caseId, AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("caseId", caseId);//案例id
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.PAGECOMMENTLIST, jsonObject.toString(), PAGECOMMENTLIST, callback);
    }

    public static void getSysCode(Context cxt, String code, AsyncUtils.AsyncCallback callback) {
        AsyncUtils.get(cxt, UserInfo.GETSYSCODE + "?code=" + code, GET_SYSCODE, callback);
    }

    /**
     * 其它任务
     */
    public static class OtherTask{
        public static final int GET_OTHERTASKLIST = 1523;
        public static final int GET_OTHERTASK_DETAIL = 1524;
        public static final int GET_OTHERTASK_RESULT = 1525;
        public static final int GET_QUESTIONNAIRE_TEMPLATE = 1526;//调查问卷模板
        //获取其它任务列表
        public static void getOtherTaskList(Context context, String json, AsyncCallback callback){
            AsyncUtils.post(context, CustomerInfo.GET_OTHERTASKLIST, json, GET_OTHERTASKLIST,callback);
        }
        //获取任务详情
        public static void getOtherTaskDetailInfo(int taskType, Context context, String json, AsyncCallback callback){
            String url = "";
            if(taskType==0){//主动追踪任务
                url = CustomerInfo.GET_ACTIVEFOLLOWIN_TASKDETAIL;
            }else if(taskType==1){//人工回访任务
                url = CustomerInfo.GET_RETURNVISIT_TASKDETAIL;
            }
            AsyncUtils.post(context, url, json, GET_OTHERTASK_DETAIL, callback);
        }
        //获取处理详情
        public static void getOtherTaskResult(Context context, String taskid, AsyncCallback callback){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("taskId", taskid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AsyncUtils.post(context, CustomerInfo.GET_TASKRESULT, jsonObject.toString(), GET_OTHERTASK_RESULT, callback);
        }
        //获取调查问卷模板
        public static void getQuestionnaireTemplate(Context context, String fproid, AsyncCallback callback){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", fproid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AsyncUtils.post(context, CustomerInfo.GET_QUESTIONNAIRETEMPLATE, jsonObject.toString(), GET_QUESTIONNAIRE_TEMPLATE, callback);
        }
        //更新任务结果
        public static void updateTaskResult(Context context, AsyncCallback callback, String json) {
            AsyncUtils.post(context, CustomerInfo.UPDATE_TASK_RESULT, json, UPDATE_TASK_RESULT, callback);
        }

    }


    /**
     * 评价相关接口及口令
     */
    public static class Evaluate{
        public static final int GET_EVALUATEVIEW = 1527;
        public static final int GET_EVALUATETOMELIST_Y = 1528;
        public static final int GET_EVALUATETOMELIST_N = 1529;
        public static final int GET_EVALUATE_ITOCUSTOMER_Y = 1530;
        public static final int GET_EVALUATE_ITOCUSTOMER_N = 1531;
        public static final int GET_EVALUATETOMEDETAILINFO = 1532;
        public static final int GET_EVALUATE_ITOCUSTOMERINFO = 1533;
        public static final int POST_GETEVALUATETEMP = 1534;
        public static final int POST_SAVEEVALCUSTOMER = 1535;
        public static final int GET_EVALUATERANKING = 1536;
        public static final int GET_QUESTIONNAIRE_RESULT = 1537;

        /**
         * 获取评价概览
         * @param context
         * @param callback
         */
        public static void getEvaluateView(Context context, AsyncCallback callback){
            JSONObject jo = new JSONObject();
            try {
                jo.put("", "");
            }catch (Exception e){}
            AsyncUtils.post(context, CustomerInfo.GET_EVALUATEVIEW, jo.toString(), GET_EVALUATEVIEW, callback);
        }
        //获取评价排行榜
        public static void getEvaluateRanking(Context cxt, AsyncCallback callback){
            JSONObject jo = new JSONObject();
            try {
                jo.put("","");
            }catch (Exception e){}
            AsyncUtils.post(cxt, CustomerInfo.POST_EVALUATERANKING, jo.toString(), GET_EVALUATERANKING, callback);
        }
        //获取客户对我的评价列表
        public static void getEvaluateToMeList(Context context, int pageno, int type, AsyncCallback callback){
            JSONObject jo = new JSONObject();
            try {
                jo.put("pageNo", pageno);
                jo.put("status", type +"");
            }catch (Exception e){}
            if(type==0){
                AsyncUtils.post(context, CustomerInfo.GET_EVALUATEToMeLIST, jo.toString(), GET_EVALUATETOMELIST_N, callback);
            }else if(type==1){
                AsyncUtils.post(context, CustomerInfo.GET_EVALUATEToMeLIST, jo.toString(), GET_EVALUATETOMELIST_Y, callback);
            }
        }
        //获取客户对我的评价详情
        public static void getEvaluateToMeDetailInfo(Context context, String _id, AsyncCallback callback){
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", _id);
            }catch (Exception e){}
            AsyncUtils.post(context, CustomerInfo.GET_EVALUATETOMEDETAILINFO, jo.toString(), GET_EVALUATETOMEDETAILINFO, callback);
        }
        //获取我对客户的评价列表
        public static void getIToCustomerList(Context context, int type, int pageno, AsyncCallback callback){
            JSONObject jo = new JSONObject();
            try {
                jo.put("pageNo", pageno);
                jo.put("status", type+"");
            }catch (Exception e){}
            if(type==0){
                AsyncUtils.post(context, CustomerInfo.GET_ITOCUSEVALUATE, jo.toString(), GET_EVALUATE_ITOCUSTOMER_N, callback);
            }else if(type==1){
                AsyncUtils.post(context, CustomerInfo.GET_ITOCUSEVALUATE, jo.toString(), GET_EVALUATE_ITOCUSTOMER_Y, callback);
            }
        }
        //获取我对客户的评价详情
        public static void getIToCustomerDetailInfo(Context context, String _id, AsyncCallback callback){
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", _id);
            }catch (Exception e){}
            AsyncUtils.post(context, CustomerInfo.GET_ITOCUSEVALUATEDETAIL, jo.toString(), GET_EVALUATE_ITOCUSTOMERINFO, callback);
        }
        //获取人工回访问题列表
        public static void getReturnVisitList(Context context, String taskid, AsyncCallback callback){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("taskId", taskid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AsyncUtils.post(context, CustomerInfo.GET_QUESTIONNAIRERESULT, jsonObject.toString(), GET_QUESTIONNAIRE_RESULT, callback);
        }

        /**
         * 获取评价模板
         */
        public static void getEvaluateTemplate(Context cxt, int requestcode, String _id, AsyncCallback callback){
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", _id);
            }catch (Exception e){}
            AsyncUtils.post(cxt, CustomerInfo.GET_EVALUATETEMP, jo.toString(), requestcode, callback);
        }
        /**
         * 评价客户（保存）
         */
        public static void saveEvaluateCustomer(Context cxt, int requestcode, String jsonparams, AsyncCallback callback){
            AsyncUtils.post(cxt, CustomerInfo.POST_EVALUATECUS, jsonparams, requestcode, callback);
        }
        /**
         * 回复说明
         */
        public static void replyToThat(Context cxt, int requestcode, String jsonparams, AsyncCallback callback){
            AsyncUtils.post(cxt, CustomerInfo.POST_REPLAYTOTHAT, jsonparams, requestcode, callback);
        }
    }

    /**
     * 打开网络文件
     */
    public static final int OPEN_FILE_BY_URL = 11;

    public static void openFileByUrl(Context context, String imagPath, AsyncUtils.AsyncCallback callback) {
        AsyncUtils.get(context, imagPath, OPEN_FILE_BY_URL, callback);
    }

    /**
     * @param context  获取订单工分
     * @param callback
     * @param fordid   订单ID
     */
    public static void setGetOrderWorkpoint(Context context, AsyncCallback callback, int fordid) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("fordid", fordid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncUtils.post(context, UserInfo.GET_ORDER_WORKPOINT, jo.toString(), GET_ORDER_WORKPOINT, callback);
    }
}
