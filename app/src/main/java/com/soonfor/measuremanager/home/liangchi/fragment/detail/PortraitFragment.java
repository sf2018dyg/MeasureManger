package com.soonfor.measuremanager.home.liangchi.fragment.detail;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.home.liangchi.adapter.PortraitAdpter;
import com.soonfor.measuremanager.home.liangchi.model.bean.customportrait.headBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.customportrait.itemBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.customportrait.savedBean;
import com.soonfor.measuremanager.home.liangchi.presenter.detail.LiangChiPortraitBasePresenter;
import com.soonfor.measuremanager.home.liangchi.view.detail.ILiangchiPortraitBaseView;
import com.soonfor.measuremanager.home.homepage.model.bean.ListBean;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/31 11:37
 * 邮箱：suibozhu@139.com
 * 用户画像
 */

public class PortraitFragment extends BaseFragment<LiangChiPortraitBasePresenter> implements ILiangchiPortraitBaseView, View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.llftxterror)
    LinearLayout llftxterror;
    @BindView(R.id.txterror)
    TextView txterror;
    String customerId;
    @BindView(R.id.dataList)
    RecyclerView dataList;
    GridLayoutManager manager;
    ListBean<headBean> beans;
    PortraitAdpter adpter;
    @BindView(R.id.rlbottom)
    RelativeLayout rlbottom;
    @BindView(R.id.btn_save_portrait)
    RelativeLayout btn_save_portrait;
    List<itemBean> submitBeans;

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        customerId = bundle.getString("customerId", "");

        manager = new GridLayoutManager(getActivity(), 1);
        dataList.setLayoutManager(manager);
        submitBeans = new ArrayList<>();

        btn_save_portrait.setOnClickListener(this);

        return rootView;
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_liangchi_portrait;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {

    }

    @Override
    protected void initPresenter() {
        presenter = new LiangChiPortraitBasePresenter(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        //先获取总的画像
        presenter.getAllOptionCustomerPortraits();
    }

    Dialog dialog = null;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_portrait:
                try {
                    dialog = CustomDialog.getSingleNormalDialog2(getActivity(), "是否确定保存?", "好的", new OnBtnClickL() {
                        @Override
                        public void onBtnClick(View view) {
                            try {
                                String list = "";
                                if (submitBeans.size() > 0) {
                                    JSONArray jr = new JSONArray();
                                    for (int i = 0; i < submitBeans.size(); i++) {
                                        JSONObject jo = new JSONObject();
                                        jo.put("customerId", customerId);
                                        jo.put("portraitCode", submitBeans.get(i).getCode());
                                        jo.put("portraitName", submitBeans.get(i).getName());
                                        jo.put("index", submitBeans.get(i).getIndex());
                                        jr.put(jo);
                                    }
                                    list = jr.toString();
                                    presenter.savePortraits(list);
                                } else {
                                    MyToast.showToast(getActivity(), "至少先选一个画像保存");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void getAllOptionCustomerPortraits(String data) {
        Gson gson = new Gson();
        try {
            beans = gson.fromJson(data, new TypeToken<ListBean<headBean>>() {
            }.getType());
            if (beans != null && beans.getList().size() > 0) {
                isHaveData(1);
                adpter = new PortraitAdpter(getActivity(), beans.getList(), listener);
                dataList.setAdapter(adpter);

                //获取已选的用户画像
                presenter.getCustomerPortraits(customerId);
            } else {
                isHaveData(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCustiomPortraits(String data) {
        Gson gson = new Gson();
        try {
            int main = 0;
            int sub = 0;
            ListBean<savedBean> tmp = gson.fromJson(data, new TypeToken<ListBean<savedBean>>() {
            }.getType());
            for (int i = 0; i < tmp.getList().size(); i++) {
                String name = tmp.getList().get(i).getPortraitName();
                for (headBean hb : beans.getList()) {
                    for (itemBean ib : hb.getItems()) {
                        if (ib.getName().contains(name)) {
                            if (ib.isSelected()) {
                                ib.setSelected(false);
                                submitBeans.remove(ib);
                            } else {
                                ib.setSelected(true);
                                submitBeans.add(ib);
                            }
                        }
                        sub++;
                    }
                    main++;
                }
                adpter.notifyDataSetChanged(beans.getList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePortraits(String data) {
        Gson gson = new Gson();
        try {
            //showMsg(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void isHaveData(int type) {
        switch (type) {
            case 0:
                llftxterror.setVisibility(View.VISIBLE);
                rlbottom.setVisibility(View.GONE);
                //intentionLt.setVisibility(View.GONE);
                break;
            case 1:
                llftxterror.setVisibility(View.GONE);
                rlbottom.setVisibility(View.VISIBLE);
                //intentionLt.setVisibility(View.VISIBLE);
                break;
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.namebg:
                    String name = (String) v.getTag();
                    int main = 0;
                    int sub = 0;
                    List<headBean> tmp = beans.getList();
                    for (headBean hb : tmp) {
                        for (itemBean ib : hb.getItems()) {
                            if (ib.getName().contains(name)) {
                                if (ib.isSelected()) {
                                    ib.setSelected(false);
                                    submitBeans.remove(ib);
                                } else {
                                    ib.setSelected(true);
                                    submitBeans.add(ib);
                                }
                            }
                            sub++;
                        }
                        main++;
                    }
                    beans.setList(tmp);
                    adpter.notifyDataSetChanged(beans.getList());
                    break;
            }
        }
    };

    public void showMsg(String msg) {
        MyToast.showToast(getContext(), msg);
    }

    @Override
    public void showLoadingDialog() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoadingDialog() {
        //mLoadingDialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}