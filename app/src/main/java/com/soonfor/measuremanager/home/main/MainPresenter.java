package com.soonfor.measuremanager.home.main;

import android.app.Fragment;

import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.afflatus.fragment.AfflatusFragment;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.billboard.bean.BillBoardBean;
import com.soonfor.measuremanager.billboard.fragment.WindCloudsBillboardFragment;
import com.soonfor.measuremanager.home.homepage.fragment.HomePageFragment;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.fragment.MeFragment;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.view.tablayout.bean.TabItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/30.
 */

public class MainPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private MainActivity view;

    public MainPresenter(MainActivity view) {
        this.view = view;
    }

    public void getData(boolean isRefresh) {
        Request.getFjUri(view, "attachEx", this);
        Request.getSysCode(view, "uploadCenter", this);
    }

    String fjUrl = "";

    /**
     * dingyg新增获取上传下载服务器接口 2018-03-24
     */
    @Override
    public void success(int requestCode, JSONObject object) {
        HeadBean headBean = JsonUtils.getHeadBean(object.toString());
        switch (requestCode) {
            case Request.GETFJURI:
                if (headBean != null && (headBean.getMsgcode() == 0 || headBean.isSuccess())) {
                    if (headBean.getData() != null) {
                        try {
                            JSONObject jo = new JSONObject(headBean.getData());
                            fjUrl = CommonUtils.formatStr(jo.getString("uri"));
                            if (fjUrl.contains("http://")) {
                                fjUrl = fjUrl.replace("http://", "");
                            }
                            Hawk.put(SoonforApplication.ServerAdr_fj, fjUrl);
                        } catch (Exception e) {
                        }
                    }
                }
                break;
            case Request.GET_SYSCODE:
                fjUrl = "";
                if (headBean != null && (headBean.getMsgcode() == 0 || headBean.isSuccess())) {
                    if (headBean.getData() != null) {
                        try {
//                            JSONObject jo = new JSONObject(headBean.getData());
//                            String uri = CommonUtils.formatStr(jo.getString("uri"));
                            JSONArray ja = new JSONArray(headBean.getData());
                            if (ja != null && ja.length() > 0) {
                                JSONObject ja_item = ja.getJSONObject(0);
                                fjUrl = CommonUtils.formatStr(ja_item.getString("value"));
                            }
                        } catch (Exception e) {
                        }
                    }
                }
                if (!fjUrl.equals("")) {
                    if (fjUrl.contains("http://")) {
                        fjUrl = fjUrl.replace("http://", "");
                    }
                    Hawk.put(SoonforApplication.ServerAdr_Upload, fjUrl);
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {

    }


    public ArrayList<TabItem> getTabItems(HomePageFragment homepageFragment, AfflatusFragment afflatusFragment,
                    WindCloudsBillboardFragment billBoardFragment, MeFragment meFragment) {
        ArrayList<TabItem> tabs = new ArrayList<TabItem>();
        tabs.add(new TabItem(R.mipmap.iocn_home, R.string.main_home, homepageFragment));
        tabs.add(new TabItem(R.mipmap.icon_cof, R.string.main_cof, afflatusFragment));
        //tabs.add(new TabItem(R.mipmap.icon_upload,R.string.main_upload, UploadFragment.class));
        tabs.add(new TabItem(R.mipmap.icon_list, R.string.main_list, billBoardFragment));
        tabs.add(new TabItem(R.mipmap.icon_my, R.string.main_mine, meFragment));
        return tabs;
    }
}
