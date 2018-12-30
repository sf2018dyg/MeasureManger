package com.soonfor.measuremanager.me.presenter.mycard;

import com.soonfor.measuremanager.bases.IBasePresenter;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.activity.MyCardActivity;
import com.soonfor.measuremanager.me.bean.MyCardBean;
import com.soonfor.measuremanager.me.fragment.mycard.BrandStoryFragment;
import com.soonfor.measuremanager.me.fragment.mycard.DesignWorksFragment;
import com.soonfor.measuremanager.tools.PreferenceUtil;

import org.apache.http.Header;
import org.json.JSONObject;

public class MycardPresenter implements IBasePresenter, AsyncUtils.AsyncCallback {

    private MyCardActivity mcActivity;
    private DesignWorksFragment dwFragment;
    private BrandStoryFragment bsFragment;

    public MycardPresenter(MyCardActivity mcActivity) {
        this.mcActivity = mcActivity;
    }
    public MycardPresenter(DesignWorksFragment dwFragment) {
        this.dwFragment = dwFragment;
    }
    public MycardPresenter(BrandStoryFragment bsFragment) {
        this.bsFragment = bsFragment;
    }

    /**
     * 获取我的名片信息
     * @param mcActivity
     */
    public void getCardDatas(MyCardActivity mcActivity){
        //mcActivity.showLoadingDialog();
        Request.Me.getMyCard(mcActivity, this, PreferenceUtil.getString(UserInfo.USERNAME, ""));
        mcActivity.setDataToView(getMyCardInfo());
    }

    /**
     * 请求设计作品
     */
    public void getDesignWorks(DesignWorksFragment dwFragment, int pageIndex, boolean isRefresh){
        //        mcActivity.showLoadingDialog();
//        Request.Me.getMyCases(mcActivity, this, PreferenceUtil.getString(UserInfo.USERNAME, ""), inputText);
        dwFragment.setListView(null);
    }

    /**
     * 获取品牌故事
     */
    public void getBrandStory(BrandStoryFragment bsFragment){
        bsFragment.setListView(null);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode){
            case Request.Me.GET_MYCARD:
                mcActivity.closeLoadingDialog();
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        switch (requestCode){
            case Request.Me.GET_MYCARD:
                mcActivity.closeLoadingDialog();
                break;
        }
    }

    /**
     * 获取我的名片假数据
     */
    public MyCardBean getMyCardInfo(){
        MyCardBean mcBean = new MyCardBean();
        mcBean.setDesign_experience("10年");
        mcBean.setGoodstyles("简欧风格 现代简约");
        mcBean.setDesign_idea("我觉得设计应该以生活为原点，美学为半径。我希望为业主设计的家既有令人一见倾心的魅力，也有足以寄托朝夕相伴。");
        mcBean.setPopularity("4235");
        mcBean.setPerformance("12000");
        mcBean.setFens("120");
        mcBean.setFavourite("102");
        return mcBean;
    }
}
