package com.soonfor.measuremanager.home.liangchi.fragment.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.home.liangchi.adapter.detail.IntentionalAdpter;
import com.soonfor.measuremanager.home.liangchi.model.bean.detail.IntentionalEntity;
import com.soonfor.measuremanager.home.liangchi.presenter.detail.LiangChiIntentionalBasePresenter;
import com.soonfor.measuremanager.home.liangchi.view.detail.ILiangchiIntentionalBaseView;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/31 11:37
 * 邮箱：suibozhu@139.com
 * 意向产品
 */

public class IntentionalFragment extends BaseFragment<LiangChiIntentionalBasePresenter> implements ILiangchiIntentionalBaseView {

    Unbinder unbinder;
    @BindView(R.id.intentionLt)
    RecyclerView intentionLt;
    List<IntentionalEntity> datas;
    private GridLayoutManager manager;
    IntentionalAdpter adpter;
    @BindView(R.id.llftxterror)
    LinearLayout llftxterror; //错误的信息
    String orderNo;

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        Bundle bundle = getArguments();
        orderNo = bundle.getString("orderNo", "");

        datas = new ArrayList<>();
        manager = new GridLayoutManager(getContext(), 1);
        adpter = new IntentionalAdpter(getContext(), datas);
        intentionLt.setLayoutManager(manager);
        intentionLt.setAdapter(adpter);
        isHaveData(0);

        return rootView;
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_liangchi_intentional;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        presenter.getIntentionProducts(orderNo);
    }

    @Override
    protected void initPresenter() {
        presenter = new LiangChiIntentionalBasePresenter(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {


        //datas.add(new IntentionalEntity("304515485 YM151门","2000*1000*150","胡桃色","胡桃色规格规格","200.00","pcs","1","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517881562&di=adb12129f5d15a6d4fd2724fb696e9dc&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dpixel_huitu%252C0%252C0%252C294%252C40%2Fsign%3D15e580c1ac86c9171c0e5a79a04515a3%2F80cb39dbb6fd52665039582da018972bd40736fc.jpg"));
        // datas.add(new IntentionalEntity("304515485 YM151门","2000*1000*150","胡桃色","胡桃色规格规格","200.00","pcs","1","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517881562&di=adb12129f5d15a6d4fd2724fb696e9dc&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dpixel_huitu%252C0%252C0%252C294%252C40%2Fsign%3D15e580c1ac86c9171c0e5a79a04515a3%2F80cb39dbb6fd52665039582da018972bd40736fc.jpg"));
        //datas.add(new IntentionalEntity("304515485 YM151门","2000*1000*150","胡桃色","胡桃色规格规格","200.00","pcs","1","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517881562&di=adb12129f5d15a6d4fd2724fb696e9dc&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dpixel_huitu%252C0%252C0%252C294%252C40%2Fsign%3D15e580c1ac86c9171c0e5a79a04515a3%2F80cb39dbb6fd52665039582da018972bd40736fc.jpg"));
        //adpter.notifyDataSetChanged(datas);
        //presenter.getData("0", isRefresh);
    }

    public void setDatas(List<IntentionalEntity> datas) {
        //adpter.notifyDataSetChanged(datas);
        this.datas = datas;
        isHaveData(1);
        String serverip = Hawk.get(SoonforApplication.ServerAdr);
        String ourOrin = "";

        String ip = serverip.split(":")[0];
        if (CommonUtils.isInner(ip)) {//内网
            ourOrin = "attachIn";
        } else {//外网
            ourOrin = "attachEx";
        }
        if(Hawk.get(SoonforApplication.ServerAdr_fj, "").equals("")) {
            presenter.getFjUri(ourOrin);
        }else {
            updateSetDatas(true, "");
        }
    }

    public void updateSetDatas(boolean isHave, String realServIP) {
        try {
            String fjSer = "";
            if(isHave){
                fjSer = Hawk.get(SoonforApplication.ServerAdr_fj, "");
            }else {
                JSONObject o = new JSONObject(realServIP);
                fjSer = o.getString("uri");
            }
            List<IntentionalEntity> tmplt = new ArrayList<>();
            if (datas != null) {
                for (int i = 0; i < datas.size(); i++) {
                    IntentionalEntity ie = datas.get(i);
                    String tmp = ie.getThumbnail();
                    ie.setThumbnail(fjSer + UserInfo.DOWNLOAD_FILE + tmp);
                    tmplt.add(ie);
                }
                this.datas = tmplt;
                adpter.notifyDataSetChanged(datas);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void isHaveData(int type) {
        switch (type) {
            case 0:
                llftxterror.setVisibility(View.VISIBLE);
                intentionLt.setVisibility(View.GONE);
                break;
            case 1:
                llftxterror.setVisibility(View.GONE);
                intentionLt.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 更新视图
     *
     * @param returnStr
     */
    public void setListView(String returnStr) {
        closeLoadingDialog();
    }

    public void showMsg(String msg) {
        MyToast.showToast(getContext(), msg);
    }

    @Override
    public void showLoadingDialog() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}