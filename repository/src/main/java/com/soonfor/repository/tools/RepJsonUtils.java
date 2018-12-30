package com.soonfor.repository.tools;

import com.soonfor.repository.base.RepHeadBean;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 作者：DC-DingYG on 2018-04-27 9:03
 * 邮箱：dingyg012655@126.com
 */
public class RepJsonUtils {

    /**
     * 解析json数据的头部
     *
     * @return
     */
    public static RepHeadBean getHeadBean(String jsonStr) {
        RepHeadBean bean = new RepHeadBean();
        if (jsonStr != null && !jsonStr.equals("")) {
            try {
                JSONObject json = new JSONObject(jsonStr);
                bean.setMsgCode(json.getInt("msgcode"));
                bean.setMsg(json.getString("msg"));
                bean.setData(json.getString("data"));
            } catch (Exception e) {
                if(e!=null){
                    if(e.getMessage().equals("No value for success")){
                    }else{
                        e.printStackTrace();
                    }
                    bean.setMsg(e.getMessage());
                }
            }
        }
        return bean;
    }

//    /**
//     * 处理解析出的json数据的头部
//     * 用于BaseActivity的子类
//     *
//     * @return
//     */
//    public static void analysisJsonHead(RepBaseFragment mContext, String jsonStr, OperateData operateDataInfance) {
//        RepHeadBean User = getHeadBean(jsonStr);
//        if (User!=null &&  User.getMsgcode() == 0) {
//            if(User.getData()!=null) operateDataInfance.doingInSuccess(User.getData());
//            else operateDataInfance.doingInSuccess(User.getData());
//        } else {
//            mContext.showNoDataHint(User.getFaileMsg());
//            //MyToast.showToast(mContext.getContext(), User.getFaileMsg());
//        }
//    }
//
//    /**
//     * 处理解析出的json数据的头部
//     *
//     * @return 用于BaseActivity的子类
//     */
//    public static void analysisJsonHead(RepBaseActivity mContext, String jsonStr, OperateData operateDataInfance) {
//        RepHeadBean User = getHeadBean(jsonStr);
//        if (User!=null && User.getMsgcode() == 0) {
////            if(User.getData()!=null) operateDataInfance.doingInSuccess(User.getData());
////            else mContext.showNoDataHint("暂无数据");
//            operateDataInfance.doingInSuccess(User.getData());
//        } else {
//            mContext.showNoDataHint(User.getFaileMsg());
//            MyToast.showToast(mContext, User.getFaileMsg());
//        }
//    }

//    /**
//     * 处理解析出的json数据的头部
//     *
//     * @return 用于任何类
//     */
//    public static void analysisJsonHead(IBaseView mContext, String jsonStr, OperateData operateDataInfance) {
//        RepHeadBean bean = getHeadBean(jsonStr);
//        if (bean!=null && bean.isSuccess()) {
//            if(bean.getData()!=null) operateDataInfance.doingInSuccess(bean.getData());
//        } else {
//            mContext.showNoDataHint("请求出错，" + bean.getFaileMsg());
//        }
//    }


    public interface OperateData {
        void doingInSuccess(String data);
    }

    /**
     * 返回表格标题和key
     **/
    public static Map<Integer, String[]> getKeyAndTitle(Map<String,String> sMap) {
        Map<Integer, String[]> result = new HashMap<>();
        if(sMap!=null && sMap.size()>0){
            String[] keys = new String[sMap.size()];
            String[] values = new String[sMap.size()];
            int index = 0;
            for (Iterator i = sMap.keySet().iterator(); i.hasNext();) {
                String obj = (String) i.next();
                keys[index] = obj;
                values[index] = sMap.get(obj);
                index++;
            }
            result.put(0, keys);
            result.put(1, values);
        }
        return result;
    }
    /**
     * 返回表格标题和key
     **/
    public static String getValue( Map<Integer, String[]> sMap, int position, int index) {
        if(sMap.containsKey(position)){
            if(index<sMap.get(position).length){
                return sMap.get(position)[index];
            }else {
                return "";
            }
        }
        return "";
    }
}
