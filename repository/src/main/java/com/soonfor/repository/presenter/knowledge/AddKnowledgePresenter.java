package com.soonfor.repository.presenter.knowledge;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.repository.base.RepBasePresenter;
import com.soonfor.repository.http.api.RepRequest;
import com.soonfor.repository.http.httptools.RepAsyncUtils;
import com.soonfor.repository.model.knowledge.CategoryBean;
import com.soonfor.repository.model.knowledge.FileBean;
import com.soonfor.repository.tools.DataTools;
import com.soonfor.repository.view.knowledge.IAddKnowledgeView;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：DC-DingYG on 2018-07-31 8:41
 * 邮箱：dingyg012655@126.com
 */
public class AddKnowledgePresenter extends RepBasePresenter<IAddKnowledgeView> implements RepAsyncUtils.AsyncCallback, RepAsyncUtils.AsyncCallback2 {

    private IAddKnowledgeView view;

    public AddKnowledgePresenter(IAddKnowledgeView view) {
        this.view = view;
    }

    public boolean isUploadFrame;//是否上传第一帧图片
    public String localPath;//图片上传前的本地路径
    public String videoPath;//上传的视频网络路径

    /**
     * 获取分类类型
     */
    public void getTabTitles(Context context, boolean isRefresh) {
        if (DataTools.fTypes != null && DataTools.fTypes.size() > 0 && DataTools.sTypes != null && DataTools.sTypes.size() > 0) {
            deleteHotAndAll();
        } else {
            if (!isRefresh) {
                view.showLoadingDialog();
            }
            RepRequest.Knowledge.getCategoryList(context, this);
        }
    }

    /**
     * //上传视频或图片
     * @param mContext
     * @param fileType
     * @param localPath 待上传的视频或图片本地路径
     * @param actonName 进度条提示语
     */
    public void uploadFile(Context mContext, String fileType, String localPath, String actonName) {
        this.isUploadFrame = false;
        this.localPath = localPath;
        view.showLoadingDialog(actonName);
        RepRequest.uploadFileToCrm(mContext, fileType, new File(localPath), this);
    }


    /**
     * //上传视频的第一帧图
     * @param mContext
     * @param fileType
     * @param frameLocalPath 待上传的第一帧图片本地路径
     * @param videoPath 已上传的视频网络路径
     */
    public void uploadFrameAtPic(Context mContext, String fileType, String frameLocalPath, String videoPath) {
        this.isUploadFrame = true;
        this.localPath = frameLocalPath;
        this.videoPath = videoPath;
        RepRequest.uploadFileToCrm(mContext, fileType, new File(localPath), this);
    }

    /**
     * /knowledge/appSave
     * 新增知识
     */
    public void addKonwledge(Context cxt, String categoryId, String title, String content, ArrayList<FileBean> fileList) {
        view.showLoadingDialog("发布中...");
        RepRequest.Knowledge.AddKnowLedge(cxt, categoryId, title, content, fileList, this);
    }

    //为一、级分类分别加上“热门”和“全部”项
    public void addHotAndAll() {
        CategoryBean hotBean = new CategoryBean("hot", "热门");
        ArrayList<CategoryBean> child = new ArrayList<>();
        child.add(new CategoryBean("hot", "热门", "hot", "热门"));
        hotBean.setChildren(child);
        DataTools.fTypes.add(0, hotBean);
        for (Map.Entry<String, List<CategoryBean>> entry : DataTools.sTypes.entrySet()) {
            List<CategoryBean> value = entry.getValue();
            CategoryBean hb = new CategoryBean("all", "全部", entry.getKey(), value.get(0).getParentName());
            value.add(0, hb);
            DataTools.sTypes.put(entry.getKey(), value);
        }
        DataTools.sTypes.put("hot", child);
    }

    //去掉一、二级分类的“热门”和“全部”项
    public void deleteHotAndAll() {
        for (int i = 0; i < DataTools.fTypes.size(); i++) {
            if (DataTools.fTypes.get(i).getId().equals("hot")) {
                DataTools.fTypes.remove(i);
            }
        }
        if (DataTools.sTypes.containsKey("hot")) {
            DataTools.sTypes.remove("hot");
        }
        for (Map.Entry<String, List<CategoryBean>> entry : DataTools.sTypes.entrySet()) {
            List<CategoryBean> value = entry.getValue();
            for (int i = 0; i < value.size(); i++) {
                if (value.get(i).getId().equals("all")) {
                    value.remove(i);
                }
            }
            DataTools.sTypes.put(entry.getKey(), value);
        }
    }

    @Override
    public void success(int requestCode, String data) {
        Gson gson = new Gson();
        try {
            switch (requestCode) {
                case RepRequest.Knowledge.GET_CATEGORY_LIST:
                    JSONArray ja = new JSONArray(data);
                    if (ja != null && ja.length() > 0) {
                        if (DataTools.fTypes != null) {
                            DataTools.fTypes.clear();
                        } else {
                            DataTools.fTypes = new ArrayList<>();
                        }
                        if (DataTools.sTypes != null) {
                            DataTools.sTypes.clear();
                        } else {
                            DataTools.sTypes = new HashMap<>();
                        }
                        for (int i = 0; i < ja.length(); i++) {
                            CategoryBean categoryBean = gson.fromJson(ja.getJSONObject(i).toString(), new TypeToken<CategoryBean>() {
                            }.getType());
                            categoryBean.setParentNameToChild();
                            DataTools.fTypes.add(categoryBean);
                            DataTools.sTypes.put(categoryBean.getId(), categoryBean.getChildren());
                        }
                        view.closeLoadingDialog();
                        view.setGetCategory(true, null);
                    } else {
                        view.setGetCategory(false, "请求分类数据出错");
                    }
                    break;
                case RepRequest.Knowledge.AddKNOWLEDGE:
                    view.closeLoadingDialog();
                    view.setAddKnowLedge(true, data);
                    break;
            }
        } catch (Exception e) {
            view.closeLoadingDialog();
        }

    }

    @Override
    public void fail(int requestCode, int statusCode, String msg) {
        view.closeLoadingDialog();
        switch (requestCode) {
            case RepRequest.Knowledge.GET_CATEGORY_LIST:
                view.setGetCategory(false, "请求分类数据出错：" + msg);
                break;
            case RepRequest.Knowledge.AddKNOWLEDGE:
                view.setAddKnowLedge(false, msg);
                break;

        }
    }

    @Override
    public void success(String type, int requestCode, String data) {
        if (isUploadFrame) {//上传第一帧图的成功返回
            view.closeLoadingDialog();
            view.setUploadFrameAtPic(true, videoPath, localPath, data);
        }else{
            if(!type.equals("video")){
                view.closeLoadingDialog();
            }
            view.setUploadFile(type, true, localPath, data);
        }
    }

    @Override
    public void fail(String type, int requestCode, int statusCode, String msg) {
        view.closeLoadingDialog();
        if (isUploadFrame) //上传第一帧图的失败返回
            view.setUploadFrameAtPic(false, videoPath, localPath, msg);
        else{
            if(type.equals("video")){
                view.setUploadFile(type, false, localPath, "视频上传失败");
            }else {
                view.setUploadFile(type, false, localPath, msg);
            }
        }
    }
}
