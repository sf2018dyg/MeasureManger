package com.soonfor.measuremanager.me.presenter;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.bean.MeMineBean;
import com.soonfor.measuremanager.me.bean.SaleTargetBean;
import com.soonfor.evaluate.bean.EvaluateViewBean;
import com.soonfor.measuremanager.me.fragment.MeFragment;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.PreferenceUtil;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.repository.model.person.PersonInfo;
import com.soonfor.repository.tools.DataTools;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.jesse.nativelogger.NLogger;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class MePresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private MeFragment meFragment;
    private Gson gson;


    /**
     * 获取网络数据，更新界面
     *
     * @param isRefresh 是否是swiperefresh的
     */
    public void getData(boolean isRefresh) {
        //获取销售目标
        Request.getSaleTarget(meFragment.getContext(), 1, this);
    }

    //获取个人信息
    public void getMaineInfo() {
        //获取个人信息
        Request.getMine(meFragment.getContext(), this);
        //获取客户评价
        Request.Evaluate.getEvaluateView(meFragment.getContext(), this);
    }

    public MePresenter(MeFragment meFragment) {
        this.meFragment = meFragment;
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        meFragment.closeLoadingDialog();
        gson = new Gson();
        HeadBean headBean = JsonUtils.getHeadBean(object.toString());
        try {
            switch (requestCode) {
                case Request.GET_MINE:
                    if (headBean != null && (headBean.getMsgcode() == 0 || headBean.isSuccess())) {
                        MeMineBean mineBean = gson.fromJson(headBean.getData(), MeMineBean.class);
                        if (mineBean != null) {
                            meFragment.setMine(true, mineBean);
                        } else
                            meFragment.setMine(false, null);
                    } else {
                        meFragment.setMine(false, null);
                    }
                    break;
                case Request.GET_SALE_TARGET:
                    //LogTools.showLog(meFragment.getContext(), object.toString());
                    gson = new Gson();
                    if (headBean != null && (headBean.getMsgcode() == 0 || headBean.isSuccess())) {
                        SaleTargetBean saleTargetBean = gson.fromJson(headBean.getData(), SaleTargetBean.class);
                        if (saleTargetBean != null) {
                            meFragment.setAchievementRate(saleTargetBean);
                        } else {
                            // MyToast.showFailToast(meFragment.getContext(), headBean.getFaileMsg());
                        }
                    }
                    break;
                case Request.Evaluate.GET_EVALUATEVIEW:
                    gson = new Gson();
                    if (headBean != null && (headBean.getMsgcode() == 0 || headBean.isSuccess())) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                EvaluateViewBean evalViewBean = gson.fromJson(headBean.getData(), EvaluateViewBean.class);
                                if (evalViewBean != null) {
                                    meFragment.setEvaluateInfoToView(evalViewBean);
                                } else {
                                    // MyToast.showFailToast(meFragment.getContext(), headBean.getFaileMsg());
                                }
                            }
                        }).start();
                    }
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        meFragment.closeLoadingDialog();
        switch (requestCode) {
            case Request.GET_MINE:
                meFragment.setMine(false, null);
                break;
            case Request.GET_SALE_TARGET:
                break;
            case Request.Evaluate.GET_EVALUATEVIEW:
                break;
        }
    }

    /**
     * 跳转知识库
     */
    public void autoToRepositoryModuel() {
        String serverAdr = Hawk.get(SoonforApplication.ServerAdr, "");
        if (!serverAdr.equals("")) {
            if (!serverAdr.toLowerCase().startsWith("http://")) {
                serverAdr = "http://" + serverAdr;
            }
            Hawk.put(DataTools.ServerAdr, serverAdr);
            Hawk.put(DataTools.UUID, PreferenceUtil.getString(UserInfo.UUID, ""));
            final String[] uploadCenterUrl = {Hawk.get(SoonforApplication.ServerAdr_Upload, "")};
            if (uploadCenterUrl[0].equals("")) {
                meFragment.showLoadingDialog();
                Request.getSysCode(meFragment.getActivity(), "uploadCenter", new AsyncUtils.AsyncCallback() {
                    @Override
                    public void success(int requestCode, JSONObject object) {
                        HeadBean headBean = JsonUtils.getHeadBean(object.toString());
                        if (headBean != null && headBean.getMsgcode() == 0) {
                            try {
                                JSONArray jr = new JSONArray(headBean.getData());
                                JSONObject o = new JSONObject(jr.get(0).toString());
                                if (o.getBoolean("success")) {
                                    if (o.getString("code").equals("uploadCenter")) {
                                        uploadCenterUrl[0] = o.getString("value");
                                    }
                                }
                            } catch (Exception e) {
                                NLogger.e("请求上传下载地址：" + e.getMessage());
                            }
                        }
                        if (uploadCenterUrl[0].equals("")) {
                            MyToast.showCaveatToast(meFragment.getActivity(), "请联系管理员配置知识库相关附件地址,谢谢!");
                        } else {
                            Hawk.get(SoonforApplication.ServerAdr_Upload, uploadCenterUrl[0]);
                            skipToRepository(uploadCenterUrl[0]);
                        }
                        meFragment.closeLoadingDialog();
                    }

                    @Override
                    public void fail(int requestCode, int statusCode, String msg) {
                        MyToast.showCaveatToast(meFragment.getActivity(), "请联系管理员配置知识库相关附件地址,谢谢!");
                        meFragment.closeLoadingDialog();
                    }
                });
            } else {
                skipToRepository(uploadCenterUrl[0]);
            }
        }
    }

    //跳转知识库
    private void skipToRepository(String uploadPath) {
        String serverAdr = Hawk.get(SoonforApplication.ServerAdr, "");
        String uuid = PreferenceUtil.getString(UserInfo.UUID, "");
        if (!serverAdr.equals("") && !uuid.equals("")) {
            if (!serverAdr.toLowerCase().startsWith("http://")) {
                serverAdr = "http://" + serverAdr;
            }
            Bundle bd = new Bundle();
            bd.putString("SERVICEADDRESS", serverAdr);
            bd.putString("UUID", uuid);
            bd.putString("DOWNLOAD_ADDRESS", Hawk.get(SoonforApplication.ServerAdr_fj, "") + UserInfo.DOWNLOAD_FILE);
            bd.putString("UPLOADPATH", uploadPath);
            MeMineBean mmBean = PreferenceUtil.getPersonalInfo(Tokens.Mine.SP_PERSONALINFO);
            if (mmBean != null) {
                PersonInfo person = new PersonInfo();
                person.setAvatar(mmBean.getAvatar());
                person.setLevel(mmBean.getLevel());
                person.setName(mmBean.getName());
                person.setPost(mmBean.getPost());
                bd.putParcelable("MINEINFO", person);
            }
            ARouter.getInstance().build("/repository/ui/activity/RepositoryMainActivity")
                    .withBundle("bundle", bd).navigation();
        }
    }
}
