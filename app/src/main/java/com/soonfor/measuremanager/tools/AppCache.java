package com.soonfor.measuremanager.tools;

import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.taskbeans.TaskProgress;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.TaskProgressBean;
import com.soonfor.measuremanager.home.homepage.model.bean.OptionTypeBean;
import com.soonfor.measuremanager.other.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2018/1/9.
 */

public class AppCache {
    /**
     * app 角色
     * 1是导购员
     * 2是店长
     */
    public static int APP_ROLE;

    public static final String APP_ROLE_KEY = "app_role";

    public static List<OptionTypeBean> typeBeanList;

    public static DataBean processBean;


    /**
     * 根据processNo获取流程名称
     *
     * @param processNo
     * @return
     */
    public static String getProcessName(String processNo) {
        if(processBean ==null){
            return null;
        }
        List<DataBean.ListBean> beans = processBean.getList();
        for (DataBean.ListBean bean : beans) {
            if (bean.getCode().equals(processNo)) {
                return bean.getName();
            }
        }
        return null;
    }

    /**
     * 根据processNo获取流程名称
     *
     * @param processName
     * @return
     */
    public static String getProcessNo(String processName) {
        if(processBean ==null){
            return null;
        }
        List<DataBean.ListBean> beans = processBean.getList();
        for (DataBean.ListBean bean : beans) {
            if (bean.getName().equals(processName)) {
                return bean.getCode();
            }
        }
        return null;
    }
    /**
     * 根据processNo获取子流程对象列表
     */
    public static List<DataBean.ListBean> getProcessLineList(String mainProcessNo){
        if(processBean ==null){
            return new ArrayList<>();
        }
        List<DataBean.ListBean> beans = processBean.getList();
        for (DataBean.ListBean bean : beans) {
            if (bean.getCode().equals(mainProcessNo)) {
                return bean.getItems();
            }
        }
        return new ArrayList<>();
    }


    /**
     * 根据processNo获取子流程名称
     *
     * @param processNo
     * @return
     */
    public static String getSubProcessName(String processNo) {
        if(processBean ==null){
            return null;
        }
        List<DataBean.ListBean> beans = processBean.getList();
        for (DataBean.ListBean bean : beans) {
            if (bean.getItems() != null) {
                for (DataBean.ListBean subBean : bean.getItems()) {
                    if (subBean.getCode().equals(processNo)) {
                        return subBean.getName();
                    }
                }
            }
//            return null;
        }
        return null;
    }

    /**
     * 根据subProcessNo获取子流程中的流程
     *
     * @param subProcessNo
     * @return
     */
    public static List<String> getProcessLineNames(String subProcessNo) {
        try {
            List<DataBean.ListBean> beans = processBean.getList();
            for (DataBean.ListBean bean : beans) {
                if (bean.getItems() != null) {
                    for (DataBean.ListBean subBean : bean.getItems()) {
                        if (subBean.getCode().equals(subProcessNo)) {
                            List<String> processLine = new ArrayList<>();
                            for (int i = 0; i < bean.getItems().size(); i++) {
                                processLine.add(bean.getItems().get(i).getName());
                            }
                            return processLine;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取进度对象集合List<TaskProgressBean>
     */
    public static List<TaskProgressBean> getProgressListData(TaskProgress progress, String mainProcessNo) {
        List<TaskProgressBean> progressBeans = new ArrayList<>();
        int subIndex = 0;
        List<DataBean.ListBean> tpList = null;
        if(progress==null){
            tpList = getProcessLineList(mainProcessNo);
            if(tpList!=null && tpList.size()>0) {
                for (int j = 0; j < tpList.size(); j++) {
                    TaskProgressBean taskProgressBean = new TaskProgressBean();
                    int index = tpList.get(j).getIndex();
                    taskProgressBean.setIndex(index);
                    taskProgressBean.setSpName(tpList.get(j).getName());
                    taskProgressBean.setStatus("-1");
                    progressBeans.add(taskProgressBean);
                }
            }
        }else{
            tpList = getProcessLineList(progress.getMainProcess());
            if(tpList!=null && tpList.size()>0) {
                for (int i = 0; i < tpList.size(); i++) {
                    int index = tpList.get(i).getIndex();
                    if (tpList.get(i).getCode().equals(progress.getSubProcess())) {
                        subIndex = index;
                    }
                }
                for (int j = 0; j < tpList.size(); j++) {
                    TaskProgressBean taskProgressBean = new TaskProgressBean();
                    int index = tpList.get(j).getIndex();
                    taskProgressBean.setIndex(index);
                    taskProgressBean.setSpName(tpList.get(j).getName());
                    if (index == subIndex) {
                        taskProgressBean.setStatus(progress.getStatus());
                    } else if (index < subIndex) {
                        taskProgressBean.setStatus("1");
                    } else if (index > subIndex) {
                        taskProgressBean.setStatus("-1");
                    }
                    progressBeans.add(taskProgressBean);
                }
            }
        }
        return progressBeans;
    }

    static {
        if (APP_ROLE == 0) {
            APP_ROLE = PreferenceUtil.getInt(APP_ROLE_KEY, 0);
        }
    }


    public static String getOptionName(String decription) {
        if (typeBeanList != null) {
            for (OptionTypeBean bean : typeBeanList) {
                if (bean.getDescription().equals(decription)) {
                    return bean.getName();
                }
            }
        }
        return null;
    }
}
