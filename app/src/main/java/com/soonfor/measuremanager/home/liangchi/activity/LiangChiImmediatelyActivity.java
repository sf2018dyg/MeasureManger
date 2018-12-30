package com.soonfor.measuremanager.home.liangchi.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiamm.bluetooth.MeasureDevice;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.liangchi.adapter.LiangChiImmediatelyListAdapter;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiBean;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiImmeaditelyBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.mjsdk.MJReqBean;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.NoDoubleClickUtils;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jesse.nativelogger.NLogger;
import cn.jiamm.lib.MJSdk;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/8 9:19
 * 邮箱：suibozhu@139.com
 * 立即量尺
 */
public class LiangChiImmediatelyActivity extends BaseActivity implements AsyncUtils.AsyncCallback {

    LiangChiImmediatelyActivity mContext;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvfcustom)
    TextView tvfcustom;
    @BindView(R.id.tvfadress)
    TextView tvfadress;
    @BindView(R.id.tvfstatus)
    TextView tvfstatus;
    LiangChiBean lb;
    @BindView(R.id.recyView)
    SwipeMenuRecyclerView recyView;
    GridLayoutManager manager;
    LiangChiImmediatelyListAdapter adapter;
    List<LiangChiImmeaditelyBean> ltBeans;
    @BindView(R.id.tvfTaskType)
    TextView tvfTaskType;
    @BindView(R.id.tvfTitile)
    TextView tvfTitile;
    boolean isFuChi = false;
    String tmpTaskNo = "";
    String tmpFobsplanid = "";
    @BindView(R.id.llftxterror)
    LinearLayout llftxterror;
    @BindView(R.id.tvsubmit)
    TextView tvsubmit;
    boolean isCanSubmit = false;
    boolean isNeedToReflash = false;

    private LoadHouseListener mLoadHouseListener;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_liang_chi_immediately;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);// 关闭title
        mContext = LiangChiImmediatelyActivity.this;
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(listener);
        manager = new GridLayoutManager(mContext, 1);
        recyView.setLayoutManager(manager);

        lb = getIntent().getParcelableExtra("LiangChiBean");
        if (lb != null) {
            String loupan = CommonUtils.formatStr(lb.getBuilding()).equals("")?"":(CommonUtils.formatStr(lb.getBuilding())+ "-");
            tvfcustom.setText(loupan + CommonUtils.formatStr(lb.getCustomerName()) + "");
            tvfadress.setText(CommonUtils.formatStr(lb.getAddress()) + "");

            int type = CommonUtils.backLcORFc(lb.getTaskType());
            switch (type) {
                case 1:
                    isFuChi = false;
                    break;
                case 2:
                    isFuChi = true;
                    break;
            }
            if (isFuChi) {
                tvfTaskType.setVisibility(View.INVISIBLE);
                //tvfTaskType.setText("新建复尺");
                tvfTitile.setText("查看复尺");
            } else {
                tvfTaskType.setVisibility(View.VISIBLE);
                tvfTaskType.setText("新建量尺");
                tvfTitile.setText("查看量尺");
                //addSlidingMenu();//添加侧滑菜单
            }

            tvfstatus.setText(CommonUtils.statusToChinese(isFuChi, lb.getStatus()) + "");
        }

        ltBeans = new ArrayList<LiangChiImmeaditelyBean>();
        adapter = new LiangChiImmediatelyListAdapter(LiangChiImmediatelyActivity.this, LiangChiImmediatelyActivity.this, ltBeans, isFuChi,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) v.getTag(R.id.item_id);
                        dialog1 = CustomDialog.getSingleNormalDialog2(LiangChiImmediatelyActivity.this, "是否确定删除该楼层?", "删除", new OnBtnClickL() {
                            @Override
                            public void onBtnClick(View view) {
                                tmpTaskNo = ltBeans.get(pos).getTaskNo();
                                tmpFobsplanid = ltBeans.get(pos).getFobsplanid();
                                //调用删除楼层的WEB接口
                                mLoadingDialog.show();
                                Request.deleteMeasure(mContext, LiangChiImmediatelyActivity.this, tmpFobsplanid);
                                dialog1.dismiss();
                            }
                        });
                    }
                });
        recyView.setAdapter(adapter);
        isHaveData(0);

        //回调消息设置
        mLoadHouseListener = new LoadHouseListener();
        MJSdk.getInstance().regMessageListener(mLoadHouseListener);

        isNeedToReflash = true;
    }

    private void isHaveData(int type) {
        switch (type) {
            case 0:
                llftxterror.setVisibility(View.VISIBLE);
                recyView.setVisibility(View.GONE);
                break;
            case 1:
                llftxterror.setVisibility(View.GONE);
                recyView.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 添加侧滑菜单
     */
    private void addSlidingMenu() {
        // 设置监听器。创建菜单
        recyView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
                // 各种文字和图标属性设置。
                deleteItem.setBackground(R.drawable.view_swipdelete);
                deleteItem.setText("删除");
                deleteItem.setTextSize(12);
                deleteItem.setTextColor(Color.parseColor("#ffffff"));
                deleteItem.setWidth(120);
                deleteItem.setHeight(MATCH_PARENT);

                swipeRightMenu.addMenuItem(deleteItem); // 在Item右侧添加一个菜单。
                // 注意：哪边不想要菜单，那么不要添加即可。
            }
        });
        // 菜单点击监听。
        recyView.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                final int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

                dialog1 = CustomDialog.getSingleNormalDialog2(LiangChiImmediatelyActivity.this, "是否确定删除该楼层?", "删除", new OnBtnClickL() {
                    @Override
                    public void onBtnClick(View view) {
                        tmpTaskNo = ltBeans.get(adapterPosition).getTaskNo();
                        tmpFobsplanid = ltBeans.get(adapterPosition).getFobsplanid();
                        //调用删除楼层的WEB接口
                        mLoadingDialog.show();
                        Request.deleteMeasure(mContext, LiangChiImmediatelyActivity.this, tmpFobsplanid);
                        dialog1.dismiss();
                    }
                });
            }
        });
        recyView.setLongPressDragEnabled(false); // 拖拽排序，默认关闭。
        recyView.setItemViewSwipeEnabled(false); // 侧滑删除，默认关闭。
    }

    private void deleteHouseSDK(String taskNo, String contractno) {
        try {
            MJReqBean.SdkDeleteHouse req = new MJReqBean.SdkDeleteHouse();
            req.contractNo = taskNo + "-" + contractno;
            final String renderInfo = req.getString();
            String ret = MJSdk.getInstance().Execute(renderInfo);
            /*返回状态说明，errorCode为-1时，errorMessage返回错误原因，否则返回“OK”。*/
            JSONObject jo = new JSONObject(ret.toString());
            if (jo.getString("errorCode").equals("0")) {
                //MyToast.showToast(mContext, "已成功执行删除量房接口");
                //删除本地的map
                Hawk.delete(tmpTaskNo + "-" + tmpFobsplanid);
            } else {
                MyToast.showToast(mContext, "执行删除量房接口失败");
            }
            tmpTaskNo = "";
            tmpFobsplanid = "";
            NLogger.w("demo", "deletehouse ret:" + ret);
            mLoadingDialog.show();
            Request.getMeasureList(mContext, LiangChiImmediatelyActivity.this, lb.getTaskNo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedToReflash) {
            isNeedToReflash = false;
            if (lb != null) {
                mLoadingDialog.show();
                Request.getMeasureList(mContext, this, lb.getTaskNo());
            }
        } else {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    Dialog dialog = null;
    Dialog dialog1 = null;
    Dialog finalConfirmDialog = null;

    @OnClick({R.id.tvfTaskType, R.id.tvsubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvfTaskType:
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    String title = "新建量尺-请输入楼层,如果客户房型有多层,请按楼层依次测量";
                    String loucengname = "量尺楼层";
                    String loucenghint = "请输入量尺楼层名称";
                    String loupan = CommonUtils.formatStr(lb.getBuilding()).equals("")?"":(CommonUtils.formatStr(lb.getBuilding())+ "-");
                    dialog = CustomDialog.ShowLiangChiDialog(LiangChiImmediatelyActivity.this, title, loucengname, loucenghint,loupan  + CommonUtils.formatStr(lb.getCustomerName()), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String backStr = (String) v.getTag();
                            if (backStr != null) {
                                if (backStr.equals("")) {
                                    showMsg("楼层名称不能为空");
                                } else {
                                    mLoadingDialog.show();
                                    Request.saveNewMeasure(mContext, LiangChiImmediatelyActivity.this, backStr, lb == null ? "" : lb.getTaskNo());
                                }
                            } else {
                                showMsg("楼层名称不能为空");
                            }
                        }
                    });
                    dialog.show();
                }
                break;
            case R.id.tvsubmit://RepRequest.setTaskStatus(mContext, this, upBean.getTaskNo(), upBean.getFobsplanid(), status);
                if (isCanSubmit) {
                    finalConfirmDialog = CustomDialog.getDoubleNormalDialog(mContext, "温馨提示", "是否确定提交?提交后操作无法撤销.", new OnBtnClickL() {
                        @Override
                        public void onBtnClick(View view) {
                            //未完成
                            //setTaskStatus("1");
                            finalConfirmDialog.dismiss();

                        }
                    }, new OnBtnClickL() {
                        @Override
                        public void onBtnClick(View view) {
                            //完成
                            setFinishTask(lb.getTaskNo());
                            finalConfirmDialog.dismiss();
                        }
                    });
                } else {
                    showMsg("尚有户型未测量完成,无法提交完成");
                }
                break;
        }
    }

    private void setFinishTask(String taskNo) {
        Request.finishTask(mContext, this, taskNo);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            MJSdk.getInstance().unregMessageListener(mLoadHouseListener);
            MeasureDevice.getInstance().setActivity(null);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 56000) {
            String fobsplanid = data.getStringExtra("fobsplanid");
            isNeedToReflash = data.getBooleanExtra("isNeedToReflash", false);

            /*//临时方法
            LiangChiImmeaditelyBean lb = null;
            int index;
            for (index = 0; index < ltBeans.size(); index++) {
                lb = ltBeans.get(index);
                if (fobsplanid.equals(lb.getFobsplanid())) {
                    lb.setNews(false);
                    break;
                }
            }
            ltBeans.set(index, lb);
            me_evaluate_ranking.notifyDataSetChanged(ltBeans);*/
        } else {
            isNeedToReflash = true;
        }
    }

    /**
     * 更新视图
     */
    public void showMsg(String msg) {
        MyToast.showToast(mContext, msg);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        final Gson gson = new Gson();
        mLoadingDialog.dismiss();
        switch (requestCode) {
            case Request.GET_MEASURE_LIST:
                JsonUtils.analysisJsonHead( object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        showNoDataHint(msg);
                        adapter.notifyDataSetChanged(ltBeans, isFuChi);
                        isHaveData(0);
                    }
                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            NLogger.w("获取测量合同号清单:" + data);
                            if (data == null || data.equals("") || data.equals("null")) {
                                ltBeans = new ArrayList<LiangChiImmeaditelyBean>();
                                adapter.notifyDataSetChanged(ltBeans, isFuChi);
                                isHaveData(0);
                            } else {
                                JSONArray jr = new JSONArray(data);
                                ltBeans = new ArrayList<LiangChiImmeaditelyBean>();
                                for (int i = 0; i < jr.length(); i++) {
                                    String ss = jr.get(i).toString();
                                    LiangChiImmeaditelyBean bean = gson.fromJson(ss, new TypeToken<LiangChiImmeaditelyBean>() {
                                    }.getType());
                                    if (bean.getFstatus().equals("0")) {
                                        bean.setNews(true);
                                    } else {
                                        bean.setNews(false);
                                    }
                                    bean.setTaskTypeValue(lb.getTaskType());
                                    bean.setCustomBulid(lb.getBuilding());
                                    bean.setCustomAddress(lb.getAddress());
                                    bean.setTaskNo(lb.getTaskNo());
                                    bean.setOrderNo(lb.getOrderNo());
                                    ltBeans.add(bean);

                                    //把新的合同号加存到本地map
                                    String key = bean.getTaskNo() + "-" + bean.getFobsplanid();
                                    if (!Hawk.contains(key)) {
                                        Hawk.put(key, false);
                                    }
                                }
                                adapter.notifyDataSetChanged(ltBeans, isFuChi);
                                isHaveData(1);
                            }

                            //检查任务状态,外面不刷新我自己刷
                            if (ltBeans.size() > 0) {
                                for (LiangChiImmeaditelyBean bean : ltBeans) {
                                    if (bean.getFstatus().equals("2")) {
                                        isCanSubmit = true;
                                        tvfstatus.setText(isFuChi ? "复尺完成" : "量尺完成");
                                    } else {
                                        isCanSubmit = false;
                                        tvfstatus.setText(isFuChi ? "复尺中" : "量尺中");
                                        break;
                                    }
                                }
                            } else {
                                isCanSubmit = false;
                                tvfstatus.setText(isFuChi ? "待复尺" : "待量尺");
                            }
                        } catch (Exception e) {
                            showMsg(e.toString());
                        }
                    }
                });
                break;
            case Request.SAVE_NEW_MEASURE:
                JsonUtils.analysisJsonHead( object.toString(), new JsonUtils.OperateData() {                    @Override                    public void doingInFail(String msg) {                        showNoDataHint(msg);                    }
                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            //showMsg(data);
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                            if (lb != null) {
                                mLoadingDialog.show();
                                Request.getMeasureList(mContext, LiangChiImmediatelyActivity.this, lb.getTaskNo());
                            }
                            //addToLtBeans("", true, "");
                        } catch (Exception e) {
                            showMsg(e.toString());
                        }
                    }
                });
                break;
            case Request.DELETE_MEASURE:
                JsonUtils.analysisJsonHead( object.toString(), new JsonUtils.OperateData() {                    @Override                    public void doingInFail(String msg) {                        showNoDataHint(msg);                    }
                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            //showMsg(data);
                            //调用删除楼层的sdk接口
                            deleteHouseSDK(tmpTaskNo, tmpFobsplanid);
                        } catch (Exception e) {
                            showMsg(e.toString());
                        }
                    }
                });
                break;
            case Request.FINISH_TASK:
                JsonUtils.analysisJsonHead( object.toString(), new JsonUtils.OperateData() {                    @Override                    public void doingInFail(String msg) {                        showNoDataHint(msg);                    }
                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            Intent intent = new Intent();
                            if (data.contains("success")) {
                                showMsg("提交成功");
                                intent.putExtra("requ", "success");
                            } else {
                                intent.putExtra("requ", "notsuccess");
                                showMsg(data);
                            }
                            setResult(6666, intent);
                            finish();
                        } catch (Exception e) {
                            showMsg(e.toString());
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        closeLoadingDialog();
        showMsg("请求错误代码:" + requestCode);
    }

    //继承回调类，实现回调函数
    private class LoadHouseListener implements MJSdk.MessageListener {

        @Override
        public void onSdkEvent(String arg0) {
            //SdkMsgInfo ret = GSONUtil.gson.fromJson(arg0, new TypeToken<SdkMsgInfo>() {}.getType());
            JSONObject jobj;
            try {
                jobj = new JSONObject(arg0);
                //System.out.println("meijia sdk 回调" + jobj.toString());
                String cmd = jobj.optString("cmd");
                if (cmd.equals("event_OnInitEnd")) {

                } else if (cmd.equals("back_home")) {

                } else if (cmd.equals("complete_house")) {

                } else if (cmd.equals("house_empty")) {

                } else if (cmd.equals("delete_house")) {

                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }
}
