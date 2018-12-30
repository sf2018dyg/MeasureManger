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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.adpter.DesignDrawLayoutAdpter;
import com.soonfor.measuremanager.afflatus.bean.cehua.DesignCehuaBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;

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
 * 作者：DC-ZhuSuiBo on 2018/2/21 14:21
 * 邮箱：suibozhu@139.com
 */
@SuppressLint("ValidFragment")
public class DesignDrawLayoutFragment extends Fragment implements AsyncUtils.AsyncCallback {

    Unbinder unbinder;
    Context mContext;
    View v;
    GridLayoutManager manager1, manager2, manager3, manager4, manager5, manager6;
    DesignDrawLayoutAdpter adpter1, adpter2, adpter3, adpter4, adpter5, adpter6;
    List<DesignCehuaBean.Datas> lt1, lt2, lt3, lt4, lt5, lt6;
    @BindView(R.id.rvl_fengge)
    RecyclerView rvl_fengge;
    @BindView(R.id.rvl_huxing)
    RecyclerView rvl_huxing;
    @BindView(R.id.rvl_mianji)
    RecyclerView rvl_mianji;
    @BindView(R.id.llffenge)
    RelativeLayout llffenge;
    boolean isClose1, isClose2, isClose3, isClose4, isClose5, isClose6 = false;
    @BindView(R.id.rvl_lanmu)
    RecyclerView rvl_lanmu;
    @BindView(R.id.rvl_liangdian)
    RecyclerView rvl_liangdian;
    @BindView(R.id.rvl_jiawei)
    RecyclerView rvl_jiawei;
    @BindView(R.id.imgArrLanmu)
    ImageView imgArrLanmu;
    @BindView(R.id.imgArrLiangdian)
    ImageView imgArrLiangdian;
    @BindView(R.id.imgArrFengge)
    ImageView imgArrFengge;
    @BindView(R.id.imgArrMainji)
    ImageView imgArrMainji;
    @BindView(R.id.imgArrHuxing)
    ImageView imgArrHuxing;
    @BindView(R.id.imgArrJiawei)
    ImageView imgArrJiawei;
    List<DesignCehuaBean> list;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.view_design_drawlayout, null);
        unbinder = ButterKnife.bind(this, v);
        /**初始化界面**/
        initDrawLayoutView();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Request.getCaseDataList(getContext(), this);

        /**初始化数据**/
        /*setChangjing();
        setFengge();
        setHuxing();
        setMianji();*/
    }

    @OnClick({R.id.btn_chongzhi, R.id.btn_queding, R.id.imgArrLanmu, R.id.imgArrLiangdian, R.id.imgArrFengge, R.id.imgArrMainji, R.id.imgArrHuxing, R.id.imgArrJiawei})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_chongzhi:
                adpter1.setSelected(-1);
                adpter2.setSelected(-1);
                adpter3.setSelected(-1);
                adpter4.setSelected(-1);
                adpter5.setSelected(-1);
                adpter6.setSelected(-1);
                adpter1.notifyDataSetChanged(lt1);
                adpter1.setSelecteds(new boolean[lt1.size()]);
                adpter2.notifyDataSetChanged(lt2);
                adpter2.setSelecteds(new boolean[lt2.size()]);
                adpter3.notifyDataSetChanged(lt3);
                adpter3.setSelecteds(new boolean[lt3.size()]);
                adpter4.notifyDataSetChanged(lt4);
                adpter4.setSelecteds(new boolean[lt4.size()]);
                adpter5.notifyDataSetChanged(lt5);
                adpter5.setSelecteds(new boolean[lt5.size()]);
                adpter6.notifyDataSetChanged(lt6);
                adpter6.setSelecteds(new boolean[lt6.size()]);
                break;
            case R.id.btn_queding:
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setLt1(lt1);
                messageEvent.setLt2(lt2);
                messageEvent.setLt3(lt3);
                messageEvent.setLt4(lt4);
                messageEvent.setLt5(lt5);
                messageEvent.setLt6(lt6);
                messageEvent.setAdpter1(adpter1);
                messageEvent.setAdpter2(adpter2);
                messageEvent.setAdpter3(adpter3);
                messageEvent.setAdpter4(adpter4);
                messageEvent.setAdpter5(adpter5);
                messageEvent.setAdpter6(adpter6);
                EventBus.getDefault().post(messageEvent);
                break;
            case R.id.imgArrLanmu:
                if (isClose1) {
                    isClose1 = false;
                    rvl_lanmu.setVisibility(View.VISIBLE);
                    imgArrLanmu.setImageResource(R.mipmap.icn_down_gray);
                } else {
                    isClose1 = true;
                    rvl_lanmu.setVisibility(View.GONE);
                    imgArrLanmu.setImageResource(R.mipmap.icn_up_gray);
                }
                break;
            case R.id.imgArrLiangdian:
                if (isClose2) {
                    isClose2 = false;
                    rvl_liangdian.setVisibility(View.VISIBLE);
                    imgArrLiangdian.setImageResource(R.mipmap.icn_down_gray);
                } else {
                    isClose2 = true;
                    rvl_liangdian.setVisibility(View.GONE);
                    imgArrLiangdian.setImageResource(R.mipmap.icn_up_gray);
                }
                break;
            case R.id.imgArrFengge:
                if (isClose3) {
                    isClose3 = false;
                    rvl_fengge.setVisibility(View.VISIBLE);
                    imgArrFengge.setImageResource(R.mipmap.icn_down_gray);
                } else {
                    isClose3 = true;
                    rvl_fengge.setVisibility(View.GONE);
                    imgArrFengge.setImageResource(R.mipmap.icn_up_gray);
                }
                break;
            case R.id.imgArrMainji:
                if (isClose4) {
                    isClose4 = false;
                    rvl_mianji.setVisibility(View.VISIBLE);
                    imgArrMainji.setImageResource(R.mipmap.icn_down_gray);
                } else {
                    isClose4 = true;
                    rvl_mianji.setVisibility(View.GONE);
                    imgArrMainji.setImageResource(R.mipmap.icn_up_gray);
                }
                break;
            case R.id.imgArrHuxing:
                if (isClose5) {
                    isClose5 = false;
                    rvl_huxing.setVisibility(View.VISIBLE);
                    imgArrHuxing.setImageResource(R.mipmap.icn_down_gray);
                } else {
                    isClose5 = true;
                    rvl_huxing.setVisibility(View.GONE);
                    imgArrHuxing.setImageResource(R.mipmap.icn_up_gray);
                }
                break;
            case R.id.imgArrJiawei:
                if (isClose6) {
                    isClose6 = false;
                    rvl_jiawei.setVisibility(View.VISIBLE);
                    imgArrJiawei.setImageResource(R.mipmap.icn_down_gray);
                } else {
                    isClose6 = true;
                    rvl_jiawei.setVisibility(View.GONE);
                    imgArrJiawei.setImageResource(R.mipmap.icn_up_gray);
                }
                break;
        }
    }

    public DesignDrawLayoutFragment(Context context) {
        mContext = context;
    }

    private void initDrawLayoutView() {
        manager1 = new GridLayoutManager(getContext(), 3);
        manager2 = new GridLayoutManager(getContext(), 3);
        manager3 = new GridLayoutManager(getContext(), 3);
        manager4 = new GridLayoutManager(getContext(), 3);
        manager5 = new GridLayoutManager(getContext(), 3);
        manager6 = new GridLayoutManager(getContext(), 3);
        lt1 = new ArrayList<>();
        lt2 = new ArrayList<>();
        lt3 = new ArrayList<>();
        lt4 = new ArrayList<>();
        lt5 = new ArrayList<>();
        lt6 = new ArrayList<>();

        adpter1 = new DesignDrawLayoutAdpter(getContext(), lt1, true);
        rvl_lanmu.setLayoutManager(manager1);
        rvl_lanmu.setAdapter(adpter1);
        rvl_lanmu.setNestedScrollingEnabled(false);

        adpter2 = new DesignDrawLayoutAdpter(getContext(), lt2, false);
        rvl_liangdian.setLayoutManager(manager2);
        rvl_liangdian.setAdapter(adpter2);
        rvl_liangdian.setNestedScrollingEnabled(false);

        adpter3 = new DesignDrawLayoutAdpter(getContext(), lt3, false);
        rvl_fengge.setLayoutManager(manager3);
        rvl_fengge.setAdapter(adpter3);
        rvl_fengge.setNestedScrollingEnabled(false);

        adpter4 = new DesignDrawLayoutAdpter(getContext(), lt4, false);
        rvl_mianji.setLayoutManager(manager4);
        rvl_mianji.setAdapter(adpter4);
        rvl_mianji.setNestedScrollingEnabled(false);

        adpter5 = new DesignDrawLayoutAdpter(getContext(), lt5, false);
        rvl_huxing.setLayoutManager(manager5);
        rvl_huxing.setAdapter(adpter5);
        rvl_huxing.setNestedScrollingEnabled(false);

        adpter6 = new DesignDrawLayoutAdpter(getContext(), lt6, false);
        rvl_jiawei.setLayoutManager(manager6);
        rvl_jiawei.setAdapter(adpter6);
        rvl_jiawei.setNestedScrollingEnabled(false);
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
            case Request.GETCASEDATALIST:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                       // NLogger.e("请求失败: " + msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            NLogger.w(data);
                            list = gson.fromJson(data, new TypeToken<List<DesignCehuaBean>>() {
                            }.getType());
                            if (list != null && list.size() > 0) {
                                for (DesignCehuaBean db : list) {
                                    if (db.getKey().equals("space_material")) {
                                        lt1 = new ArrayList<>();
                                        for (DesignCehuaBean.Datas d : db.getData()) {
                                            lt1.add(d);
                                        }
                                        adpter1.notifyDataSetChanged("space_material");
                                        adpter1.notifyDataSetChanged(lt1);
                                        adpter1.setSelecteds(new boolean[lt1.size()]);
                                    }

                                    if (db.getKey().equals("luminance_material")) {
                                        lt2 = new ArrayList<>();
                                        for (DesignCehuaBean.Datas d : db.getData()) {
                                            lt2.add(d);
                                        }
                                        adpter2.notifyDataSetChanged("luminance_material");
                                        adpter2.notifyDataSetChanged(lt2);
                                        adpter2.setSelecteds(new boolean[lt2.size()]);
                                    }

                                    if (db.getKey().equals("style_material")) {
                                        lt3 = new ArrayList<>();
                                        for (DesignCehuaBean.Datas d : db.getData()) {
                                            lt3.add(d);
                                        }
                                        adpter3.notifyDataSetChanged("style_material");
                                        adpter3.notifyDataSetChanged(lt3);
                                        adpter3.setSelecteds(new boolean[lt3.size()]);
                                    }

                                    if (db.getKey().equals("area_material")) {
                                        lt4 = new ArrayList<>();
                                        for (DesignCehuaBean.Datas d : db.getData()) {
                                            lt4.add(d);
                                        }
                                        adpter4.notifyDataSetChanged("area_material");
                                        adpter4.notifyDataSetChanged(lt4);
                                        adpter4.setSelecteds(new boolean[lt4.size()]);
                                    }

                                    if (db.getKey().equals("household_material")) {
                                        lt5 = new ArrayList<>();
                                        for (DesignCehuaBean.Datas d : db.getData()) {
                                            lt5.add(d);
                                        }
                                        adpter5.notifyDataSetChanged("household_material");
                                        adpter5.notifyDataSetChanged(lt5);
                                        adpter5.setSelecteds(new boolean[lt5.size()]);
                                    }

                                    if (db.getKey().equals("price_material")) {
                                        lt6 = new ArrayList<>();
                                        for (DesignCehuaBean.Datas d : db.getData()) {
                                            lt6.add(d);
                                        }
                                        adpter6.notifyDataSetChanged("price_material");
                                        adpter6.notifyDataSetChanged(lt6);
                                        adpter6.setSelecteds(new boolean[lt6.size()]);
                                    }
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                });
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {}

    public static class MessageEvent {
        private List<DesignCehuaBean.Datas> lt1, lt2, lt3, lt4, lt5, lt6;
        private DesignDrawLayoutAdpter adpter1, adpter2, adpter3, adpter4, adpter5, adpter6;

        public List<DesignCehuaBean.Datas> getLt1() {
            return lt1;
        }

        public void setLt1(List<DesignCehuaBean.Datas> lt1) {
            this.lt1 = lt1;
        }

        public List<DesignCehuaBean.Datas> getLt2() {
            return lt2;
        }

        public void setLt2(List<DesignCehuaBean.Datas> lt2) {
            this.lt2 = lt2;
        }

        public List<DesignCehuaBean.Datas> getLt3() {
            return lt3;
        }

        public void setLt3(List<DesignCehuaBean.Datas> lt3) {
            this.lt3 = lt3;
        }

        public List<DesignCehuaBean.Datas> getLt4() {
            return lt4;
        }

        public void setLt4(List<DesignCehuaBean.Datas> lt4) {
            this.lt4 = lt4;
        }

        public List<DesignCehuaBean.Datas> getLt5() {
            return lt5;
        }

        public void setLt5(List<DesignCehuaBean.Datas> lt5) {
            this.lt5 = lt5;
        }

        public List<DesignCehuaBean.Datas> getLt6() {
            return lt6;
        }

        public void setLt6(List<DesignCehuaBean.Datas> lt6) {
            this.lt6 = lt6;
        }

        public DesignDrawLayoutAdpter getAdpter1() {
            return adpter1;
        }

        public void setAdpter1(DesignDrawLayoutAdpter adpter1) {
            this.adpter1 = adpter1;
        }

        public DesignDrawLayoutAdpter getAdpter2() {
            return adpter2;
        }

        public void setAdpter2(DesignDrawLayoutAdpter adpter2) {
            this.adpter2 = adpter2;
        }

        public DesignDrawLayoutAdpter getAdpter3() {
            return adpter3;
        }

        public void setAdpter3(DesignDrawLayoutAdpter adpter3) {
            this.adpter3 = adpter3;
        }

        public DesignDrawLayoutAdpter getAdpter4() {
            return adpter4;
        }

        public void setAdpter4(DesignDrawLayoutAdpter adpter4) {
            this.adpter4 = adpter4;
        }

        public DesignDrawLayoutAdpter getAdpter5() {
            return adpter5;
        }

        public void setAdpter5(DesignDrawLayoutAdpter adpter5) {
            this.adpter5 = adpter5;
        }

        public DesignDrawLayoutAdpter getAdpter6() {
            return adpter6;
        }

        public void setAdpter6(DesignDrawLayoutAdpter adpter6) {
            this.adpter6 = adpter6;
        }
    }

}
