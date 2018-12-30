package com.soonfor.measuremanager.afflatus.fragment.drawlayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.adpter.PartsListAdapter;
import com.soonfor.measuremanager.afflatus.bean.cehua.SparePartsPinpaiBean;
import com.soonfor.measuremanager.afflatus.bean.partsBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.view.FlycoTabLayout_Lib.CommonTabLayout;
import com.soonfor.measuremanager.view.FlycoTabLayout_Lib.entity.TabEntity;
import com.soonfor.measuremanager.view.FlycoTabLayout_Lib.listener.CustomTabEntity;
import com.soonfor.measuremanager.view.FlycoTabLayout_Lib.listener.OnTabSelectListener;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/21 15:36
 * 邮箱：suibozhu@139.com
 */
@SuppressLint("ValidFragment")
public class PartsFragment extends Fragment implements AsyncUtils.AsyncCallback {
    Unbinder unbinder;
    Context mContext;
    View v;
    @BindView(R.id.tl_1)
    CommonTabLayout tl_1;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    List<SparePartsPinpaiBean> pinpaiBeans;
    private String[] mTitles;// = {"品牌", "门锁", "厨房电器", "厨房配件", "卫浴", "厨房电器", "厨房配件", "卫浴"};
    @BindView(R.id.rvListAnli)
    RecyclerView rvListAnli;
    GridLayoutManager manager;
    List<partsBean> pinPaiDatas;
    List<partsBean> datas;
    PartsListAdapter adapter;
    String brandname = "品牌";

    public PartsFragment(Context context) {
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.view_spareparts_drawlayout, null);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Request.getPartSortList(getContext(), this);
    }

    private void initMainHead() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], R.mipmap.icon_quanjing, R.mipmap.icon_quanjing));
        }
        tl_1.setTabData(mTabEntities);
        tl_1.setCurrentTab(0);
        tl_1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (mTitles != null) {
                    adapter.setSelected(-1);
                    brandname = mTitles[position];
                    if (brandname.equals("品牌")) {//固定的
                        adapter.notifyDataSetChanged(pinPaiDatas);
                    } else {
                        for (SparePartsPinpaiBean bean : pinpaiBeans) {
                            if (bean.getName().equals(brandname)) {
                                setDatas(bean.getChildren());
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void initMainBody() {
        manager = new GridLayoutManager(getContext(), 3);
        rvListAnli.setLayoutManager(manager);
        datas = new ArrayList<partsBean>();
        adapter = new PartsListAdapter(getContext(), datas);
        rvListAnli.setAdapter(adapter);
    }

    private void setDatas(List<SparePartsPinpaiBean.Children> beans) {
        datas = new ArrayList<>();
        for (SparePartsPinpaiBean.Children c : beans) {
            datas.add(new partsBean(c.getId(), c.getPid(), c.getName(), ""));
        }
        adapter.notifyDataSetChanged(datas);
    }

    @OnClick({R.id.btn_chongzhi, R.id.btn_queding})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_chongzhi:
                tl_1.setCurrentTab(0);
                adapter.setSelected(-1);
                brandname = "品牌";
                adapter.notifyDataSetChanged(pinPaiDatas);
                break;
            case R.id.btn_queding:
                MessageEvent messageEvent = new MessageEvent();
                if (brandname.equals("品牌")) {//固定的
                    if (adapter.getSelected() == -1) {
                        messageEvent.setBrandname("");
                        messageEvent.setPid("");
                    } else {
                        messageEvent.setBrandname(pinPaiDatas.get(adapter.getSelected()).getName());
                        messageEvent.setPid("");
                    }
                } else {
                    if (adapter.getSelected() == -1) {
                        messageEvent.setBrandname("");
                        messageEvent.setPid("");
                    } else {
                        messageEvent.setBrandname("");
                        messageEvent.setPid(datas.get(adapter.getSelected()).getId());
                    }
                }
                EventBus.getDefault().post(messageEvent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbinder.unbind();
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        Gson gson = new Gson();
        switch (requestCode) {
            case Request.PARTSORTLIST:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {}

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            pinpaiBeans = gson.fromJson(data, new TypeToken<List<SparePartsPinpaiBean>>() {
                            }.getType());
                            if (pinpaiBeans != null && pinpaiBeans.size() > 0) {
                                mTitles = new String[pinpaiBeans.size() + 1];
                                mTitles[0] = "品牌";
                                int i = 1;
                                for (SparePartsPinpaiBean sp : pinpaiBeans) {
                                    mTitles[i] = sp.getName();
                                    i++;
                                }
                            }
                            /**初始化界面**/
                            initMainHead();
                            initMainBody();

                            Request.getBrandList(getContext(), PartsFragment.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case Request.BRANDLIST:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {}

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            List<BrandBeans> beans = gson.fromJson(data, new TypeToken<List<BrandBeans>>() {
                            }.getType());

                            if (tl_1.getCurrentTab() == 0) {
                                pinPaiDatas = new ArrayList<>();
                                for (BrandBeans c : beans) {
                                    pinPaiDatas.add(new partsBean(c.getId(), c.getBrandcode(), c.getBrandname(), ""));
                                }
                                adapter.notifyDataSetChanged(pinPaiDatas);
                            } else {
                                datas = new ArrayList<>();
                                for (BrandBeans c : beans) {
                                    datas.add(new partsBean(c.getId(), c.getBrandcode(), c.getBrandname(), ""));
                                }
                                adapter.notifyDataSetChanged(datas);
                            }

                        } catch (Exception e) {

                        }
                    }
                });
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
    }

    class BrandBeans {
        private String id;
        private String brandcode;
        private String brandname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBrandcode() {
            return brandcode;
        }

        public void setBrandcode(String brandcode) {
            this.brandcode = brandcode;
        }

        public String getBrandname() {
            return brandname;
        }

        public void setBrandname(String brandname) {
            this.brandname = brandname;
        }
    }

    public static class MessageEvent {
        private String brandname;
        private String pid;

        public String getBrandname() {
            return brandname;
        }

        public void setBrandname(String brandname) {
            this.brandname = brandname;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }
    }
}
