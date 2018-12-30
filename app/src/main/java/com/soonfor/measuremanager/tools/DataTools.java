package com.soonfor.measuremanager.tools;

import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.design.model.DesignItemBean;
import com.soonfor.measuremanager.home.grab.model.bean.GrabOrderBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.beanTotal;
import com.soonfor.measuremanager.home.lofting.model.bean.LoftingMainBean;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.MeasureResultEntity;
import com.soonfor.measuremanager.home.othertask.model.bean.OtherTaskMainBean;
import com.soonfor.measuremanager.other.bean.DataBean;
import com.soonfor.repository.model.knowledge.SearchKnowLedgeBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-03-03.
 */

public class DataTools {

    public static String getString(String str){
        String result = "";
        if(str != null && !str.equals("null")){
            result = str;
        }
        return result;
    }
    public static Map<Integer,PageTurnBean> pageMapGrab = new HashMap<>();//抢单页码对象集合
    public static Map<Integer, List<GrabOrderBean>> ListMapGrab = new HashMap<>();//待抢数据集合

    public static Map<Integer,PageTurnBean> pageMap = new HashMap<>();//页码对象集合
    public static Map<Integer,List<LoftingMainBean>> ListMap = new HashMap<>();//放样数据集合

    public static Map<Integer,PageTurnBean> pageDesignMap = new HashMap<>();//设计页码对象集合
    public static Map<Integer,List<DesignItemBean>> ListDesignMap = new HashMap<>();//设计数据集合

    public static Map<Integer, PageTurnBean> pageOtherTaskMap = new HashMap<>();//其他任务页码对象集合
    public static Map<Integer, List<OtherTaskMainBean>> ListOtherTaskMap = new HashMap<>();//其他任务数据集合

    /**
     * 修改人：DC-ZhuSuiBo on 2018/3/6 0006 8:38
     * 邮箱：suibozhu@139.com
     */
    public static Map<Integer,List<LiangChiBean>> ListMapLiangChi = new HashMap<>();//数据集合
    public static Map<Integer,List<LiangChiBean>> ListMapFuChi = new HashMap<>();//数据集合


//    /**
//     * 获取抢单对应code
//     * @param taskType
//     * @return
//     */
//    public static int gettaskTypeCode(String taskType) {
//        if (taskType.toLowerCase().equals("measure")) {//量尺
//            return 1;
//        } else if (taskType.toLowerCase().equals("design")) {//设计
//            return 2;
//        } else if (taskType.toLowerCase().equals("remeasure")) {//复尺
//            return 3;
//        } else if (taskType.toLowerCase().equals("mark")) {//放样
//            return 4;
//        }
//        return 0;
//    }
    /**
     * 量尺详情临时保存
     */
    public static Map<String,beanTotal> liangchiDetailInfos = new HashMap<>();
    /**
     * 设计模块的临时保存
     */
    public static DataBean goodstypesBean;//意向产品
    public static DataBean doortypesBean;//户型

    /**
     * 复尺详情的临时保存
     * **/
    public static Map<String,beanTotal> fuchiDetailInfos = new HashMap<>();

    /**
     * 放样详情临时保存
     */
    public static Map<String,beanTotal> loftDetailInfos = new HashMap<>();


    /**
     * 获取当前户型的测量清单
     */
    public static List<MeasureResultEntity> getHtList(List<MeasureResultEntity> resultEntities, String houseName){
        List<MeasureResultEntity> resultList = new ArrayList<>();
        for(int i=0; i<resultEntities.size(); i++){
            if(resultEntities.get(i).getUnitName().equals(houseName)){
                resultList.add(resultEntities.get(i));
            }
        }
        return resultList;
    }
   public static class Loft{
       public static int statusCode = 0;//用于显示的放样状态（未提交前）
       public static boolean isEnterSdk = false;//是否进入过sdk开始放样
       public static String loftListJson;//从sdk界面回来时的清单数据
   }

    // 获取精确的小数（去多余的0）
    public static String getExactStrNum(String str) {
        if (str == null || str.equals("")) {
            return "";
        } else {
            String bigD = (new BigDecimal(str).stripTrailingZeros())
                    .toPlainString();
            // 如果是0.00000则不能去多余的0
            if (bigD.contains(".")) {
                String tou = bigD.substring(0, bigD.indexOf("."));
                if (tou.equals("0") && bigD.charAt(bigD.length() - 1) == '0') {
                    bigD = "0";
                }
            }
            return bigD;
        }
    }
    /*
      以千位符分割金额的形式显示
      */
    public static String getMoney(String s) {
        if (s != null) {
            if(s.contains(".")){
                String text = s.substring(0, s.indexOf("."));
                String dx = array(text);
                StringBuffer buffer = new StringBuffer();
                buffer.append(dx);
                for (int index = 0; index< buffer.length(); index++) {
                    if(index == 3 || index == 7 || index == 11 || index == 15){
                        buffer.insert(index, ',');
                    }
                }
                return array(buffer.toString()) + s.substring(s.indexOf("."), s.length());
            }else {
                String dx = array(s);
                StringBuffer buffer = new StringBuffer();
                buffer.append(dx);
                for (int index = 0; index< buffer.length(); index++) {
                    if(index == 3 || index == 7 || index == 11 || index == 15){
                        buffer.insert(index, ',');
                    }
                }
                return array(buffer.toString());
            }
        }
        return "";
    }
    //1. 使用数组循环
    public static String array(String s){
        int length=s.length();
        char[] array=s.toCharArray();
        for(int i=0;i<length/2;i++){
            array[i]=s.charAt(length-1-i);
            array[length-1-i]=s.charAt(i);
        }
        return new String(array);
    }
}
