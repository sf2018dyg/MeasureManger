package com.soonfor.measuremanager.afflatus.fragment.drawlayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.adpter.TintplateDrawLayoutAdpter;
import com.soonfor.measuremanager.afflatus.bean.cehua.TintplateCehuaBean;
import com.soonfor.measuremanager.bases.PageTurnBean;
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
 * 作者：DC-ZhuSuiBo on 2018/2/21 15:35
 * 邮箱：suibozhu@139.com
 */
@SuppressLint("ValidFragment")
public class TintplateFragment extends Fragment implements AsyncUtils.AsyncCallback {
    Unbinder unbinder;
    Context mContext;
    View v;
    DrawerLayout dl;
    GridLayoutManager manager;
    List<TintplateCehuaBean> lt;
    @BindView(R.id.rvl_sebanleixing)
    RecyclerView rvl_sebanleixing;
    @BindView(R.id.btn_fanhui)
    RelativeLayout btn_fanhui;
    @BindView(R.id.btn_queding)
    RelativeLayout btn_queding;
    TintplateDrawLayoutAdpter adpter;

    public TintplateFragment(Context context) {
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.view_tintplate_drawlayout, null);
        unbinder = ButterKnife.bind(this, v);
        /**初始化界面**/
        initView();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /**初始化数据**/
        initdatas();
    }

    private void initView() {
        manager = new GridLayoutManager(mContext, 1);
        rvl_sebanleixing.setLayoutManager(manager);
        lt = new ArrayList<TintplateCehuaBean>();
        adpter = new TintplateDrawLayoutAdpter(mContext, lt);
        rvl_sebanleixing.setAdapter(adpter);
    }

    private void initdatas() {
        lt = new ArrayList<TintplateCehuaBean>();
        TintplateCehuaBean t = new TintplateCehuaBean();
        t.setId("-1");
        t.setName("全部");
        lt.add(t);
        /*lt = new ArrayList<String>();
        lt.add("全部");
        lt.add("木板");
        lt.add("油漆");
        lt.add("五金");
        lt.add("塑料");
        lt.add("面料");
        lt.add("玻璃");
        lt.add("木板");
        lt.add("油漆");
        lt.add("五金");
        lt.add("塑料");
        lt.add("面料");
        lt.add("玻璃");
        adpter.notifyDataSetChanged(lt);*/
        Request.getBoardTypeList(mContext, 1, 5000, this);
    }

    @OnClick({R.id.btn_fanhui, R.id.btn_queding})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_fanhui:
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setBid("");
                EventBus.getDefault().post(messageEvent);
                break;
            case R.id.btn_queding:
                messageEvent = new MessageEvent();
                messageEvent.setBid(lt.get(adpter.getSelected()).getId());
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
            case Request.BOARDTYPELIST:
                //{"id":"ea7995607e214deba4cdb75b7efc0e93","name":"五金","sort":90,"remark":"五金","status":1,"merchantCode":"mt","creator":"mt_1008611","createTime":1527670700000,"updater":"mt_admin","updateTime":1529400864000}
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {}

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            JSONObject o = new JSONObject(data);
                            PageTurnBean pageTurnBean = gson.fromJson(o.getString("pageTurn"), new TypeToken<PageTurnBean>() {
                            }.getType());
                            lt.addAll(gson.fromJson(o.getString("list"), new TypeToken<List<TintplateCehuaBean>>() {
                            }.getType()));
                            if (lt != null) {
                                adpter.notifyDataSetChanged(lt);
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

    public static class MessageEvent {
        private String bid;

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }
    }

}
