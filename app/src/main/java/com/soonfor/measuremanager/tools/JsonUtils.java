package com.soonfor.measuremanager.tools;

import android.content.Context;

import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.measureChild;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.measureHead;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskcompletebeans.mainbean;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskcompletebeans.sizes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-02-20.
 */

public class JsonUtils {

    /**
     * 解析json数据的头部
     *
     * @return
     */
    public static HeadBean getHeadBean(String jsonStr) {
        HeadBean bean = new HeadBean();
        if (jsonStr != null && !jsonStr.equals("")) {
            try {
                JSONObject json = new JSONObject(jsonStr);
                bean.setMsgcode(json.getInt("msgcode"));
                bean.setMsg(json.getString("msg"));
                bean.setData(json.getString("data"));
                bean.setSuccess(json.getBoolean("success"));
            } catch (Exception e) {
                if(e!=null){
                    if(e.getMessage().equals("No value for success")){

                    }else{
                        e.printStackTrace();
                    }
                }
            }
        }
        return bean;
    }


    /**
     * 处理解析出的json数据的头部
     *
     * @return 用于任何类
     */
    public static void analysisJsonHead(String jsonStr, OperateData operateDataInfance) {
        HeadBean bean = getHeadBean(jsonStr);
        if (bean!=null) {
            if(bean.getMsgcode() == 0) {
                if (!bean.getData().equals("")) operateDataInfance.doingInSuccess(bean.getData());
                else operateDataInfance.doingInFail("数据为空");
            }else{
                operateDataInfance.doingInFail(bean.getFaileMsg());
            }
        } else {
            operateDataInfance.doingInFail("数据为空");
        }
    }

    /**
     * 处理解析出的json数据的页码相关部分
     *
     * @return
     */
    public static PageTurnBean getPageBean(String jsonStr) {
        PageTurnBean bean = new PageTurnBean();
        if (jsonStr != null && !jsonStr.equals("")) {
            try {
                JSONObject json = new JSONObject(jsonStr);
                bean.setRowCount(json.getInt("rowCount"));
                bean.setPageNo(json.getInt("pageNo"));
                bean.setPageSize(json.getInt("pageSize"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    public interface OperateData {
        void doingInSuccess(String data);
        void doingInFail(String msg);
    }

    ;

    /**
     * 量尺/复尺获取测量信息 方法
     */
    public static List<measureHead> getMeasureHead(mainbean mb) {
        List<measureHead> measureHeads = new ArrayList<measureHead>();
        if(mb.getMeasureInfo().getRooms()!=null){
            for (int j = 0; j < mb.getMeasureInfo().getRooms().size(); j++) {
                //给child
                List<measureChild> measureChilds = new ArrayList<>();
                for (int k = 0; k < mb.getMeasureInfo().getRooms().get(j).getComponents().size(); k++) {
                    String sizeComp = "";
                    int index = 1;
                    for (sizes size : mb.getMeasureInfo().getRooms().get(j).getComponents().get(k).getSizes()) {
                        sizeComp += size.getName() + "  " + size.getValue() + "          ";
                        if (index < mb.getMeasureInfo().getRooms().get(j).getComponents().get(k).getSizes().size()) {
                            if (index % 2 == 0) {
                                sizeComp = sizeComp + "\n";
                            }
                        }
                        index++;
                    }
                    measureChilds.add(new measureChild(mb.getMeasureInfo().getRooms().get(j).getComponents().get(k).getName(), sizeComp));
                }
                //给head
                measureHeads.add(new measureHead(mb.getMeasureInfo().getRooms().get(j).getName(), measureChilds));
            }
        }
        return measureHeads;
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
