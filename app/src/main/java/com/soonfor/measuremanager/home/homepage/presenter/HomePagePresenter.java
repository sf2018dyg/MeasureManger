package com.soonfor.measuremanager.home.homepage.presenter;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.home.homepage.model.bean.ListBean;
import com.soonfor.measuremanager.home.homepage.model.bean.TaskTypes;
import com.soonfor.measuremanager.home.homepage.model.bean.TaskTypesDetail;
import com.soonfor.measuremanager.home.homepage.model.bean.topAchievementEntity;
import com.soonfor.measuremanager.home.liangchi.model.bean.customportrait.headBean;
import com.soonfor.measuremanager.home.login.activity.LoginActivity;
import com.soonfor.measuremanager.home.login.bean.CurrentUserBean;
import com.soonfor.measuremanager.home.main.MainActivity;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.PreferenceUtil;
import com.soonfor.repository.tools.ActivityUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：DC-DingYG on 2018-11-27 18:29
 * 邮箱：dingyg012655@126.com
 */
public class HomePagePresenter implements AsyncUtils.AsyncCallback {
    private IHomePageView view;
    private MainActivity mContext;

    public HomePagePresenter(IHomePageView view, MainActivity mContext) {
        this.view = view;
        this.mContext = mContext;
    }

    //发起网络请求 先请求当前用户的基本信息
    public void requestNet(Context mContext) {
        //消息
        //Request.getNewsCount(mContext, this, PreferenceUtil.getString(UserInfo.USERNAME, ""));
        //获取任务概览
        Request.getTaskOverview(mContext, this, PreferenceUtil.getCurrentUserBean().getSalesId());
        //获取轮播图片
        Request.getCarouselPicture(mContext, this, "0");
        //获取待抢任务数量
        Request.getGrabTask(mContext, this);
//        //获取设计师业绩
        Request.getDesignerTopn(mContext, this, "10");
    }

    public List<TaskTypes> getDefaultTaskOverview() {
        List<TaskTypes> result = new ArrayList<>();
        List<TaskTypesDetail> taskDetails = new ArrayList<>();
        taskDetails.add(new TaskTypesDetail("待接收", "0"));
        taskDetails.add(new TaskTypesDetail("待上门确认", "0"));
        taskDetails.add(new TaskTypesDetail("待量尺", "0"));
        result.add(new TaskTypes("Measure", taskDetails));

        taskDetails = new ArrayList<>();
        taskDetails.add(new TaskTypesDetail("待接收", "0"));
        taskDetails.add(new TaskTypesDetail("待设计方案", "0"));
        taskDetails.add(new TaskTypesDetail("待调整方案", "0"));
        taskDetails.add(new TaskTypesDetail("待客户确图", "0"));
        taskDetails.add(new TaskTypesDetail("已确图", "0"));
        result.add(new TaskTypes("Design", taskDetails));

        taskDetails = new ArrayList<>();
        taskDetails.add(new TaskTypesDetail("待接收", "0"));
        taskDetails.add(new TaskTypesDetail("待上门确认", "0"));
        taskDetails.add(new TaskTypesDetail("待复尺", "0"));
        result.add(new TaskTypes("Remeasure", taskDetails));

        taskDetails = new ArrayList<>();
        taskDetails.add(new TaskTypesDetail("待接收", "0"));
        taskDetails.add(new TaskTypesDetail("待上门确认", "0"));
        taskDetails.add(new TaskTypesDetail("待放样", "0"));
        result.add(new TaskTypes("Mark", taskDetails));

        taskDetails = new ArrayList<>();
        taskDetails.add(new TaskTypesDetail("进行中", "0"));
        taskDetails.add(new TaskTypesDetail("已逾期", "0"));
        taskDetails.add(new TaskTypesDetail("已完成", "0"));
        result.add(new TaskTypes("Other", taskDetails));
        return result;
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        final Gson gson = new Gson();
        switch (requestCode) {
            case Request.GET_NEWS_COUNT:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        view.showTip(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        int num = 0;
                        try {
                            num = Integer.parseInt(data);
                        } catch (Exception e) {
                            view.showTip(e.toString());
                        }
                        if (num > 0) view.showNoticeNum(num);
                    }
                });
                break;
            case Request.GET_CAROUSEL_PICTURE:
                try {
                    JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                        @Override
                        public void doingInFail(String msg) {
                            view.showTip(msg);
                        }

                        @Override
                        public void doingInSuccess(String data) {
                            int type = 0;
                            List<String> syPicsLt = null;
                            try {
                                if (data.equals("[]")) {
                                    view.showTip("轮播图片数据为空");
                                } else {
                                    type = 1;
                                    ListBean<String> result1 = gson.fromJson(data, new TypeToken<ListBean<String>>() {
                                    }.getType());
                                    ListBean<String> b = result1;
                                    if (b.getList().size() > 0) {
                                        syPicsLt = b.getList();
                                    }
                                }
                            } catch (Exception e) {
                                view.showTip(e.toString());
                            }
                            view.showPosterPics(type, syPicsLt);
                        }
                    });
                } catch (Exception e) {
                }
                break;
            case Request.GET_TASK_OVERVIEW:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        view.showTip(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        ListBean<TaskTypes> result = null;
                        try {
                            result = gson.fromJson(data, new TypeToken<ListBean<TaskTypes>>() {
                            }.getType());
                        } catch (Exception e) {
                            view.showTip(e.toString());
                        }
                        List<TaskTypes> resultList = new ArrayList<>();
                        if (result !=null && result.getList() != null && result.getList().size() > 0) {
                            view.showTaskNum(result.getList());
                        }
                    }
                });
                break;
            case Request.GET_GRAB_TASK:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        view.showTip(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        String grabNum = "0";
                        try {
                            String result1 = gson.fromJson(data, new TypeToken<String>() {
                            }.getType());
                            grabNum = CommonUtils.formatNum(result1);
                        } catch (Exception e) {
                            view.showTip(e.toString());
                        }
                        if (Integer.parseInt(grabNum) > 99) {
                            grabNum = "99+";
                        }
                        view.showGrabNum(grabNum);
                    }
                });

                break;
            case Request.GET_DESIGNER_TOPN:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData()

                {
                    @Override
                    public void doingInFail(String msg) {
                        view.showTip(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        topAchievementEntity result1 = null;
                        try {
                            result1 = gson.fromJson(data, new TypeToken<topAchievementEntity>() {
                            }.getType());
                        } catch (Exception e) {
                            view.showTip(e.toString());
                        }
                        view.show10Tops(result1);
                    }
                });
                break;
            case Request.GET_SYSCODE:

                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        if(requestCode == Request.GET_TASK_OVERVIEW){
            view.showTaskNum(getDefaultTaskOverview());
        }
    }
}
