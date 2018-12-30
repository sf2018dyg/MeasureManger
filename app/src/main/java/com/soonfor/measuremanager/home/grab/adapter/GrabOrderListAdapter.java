package com.soonfor.measuremanager.home.grab.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.complexruler.activity.FuChiDetailActivity;
import com.soonfor.measuremanager.home.design.activity.DesignDetailActivity;
import com.soonfor.measuremanager.home.design.model.DesignItemBean;
import com.soonfor.measuremanager.home.grab.model.bean.GrabOrderBean;
import com.soonfor.measuremanager.home.liangchi.activity.LiangChiDetailActivity;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiBean;
import com.soonfor.measuremanager.home.lofting.activity.LoftingDetailActivity;
import com.soonfor.measuremanager.home.lofting.model.bean.LoftingMainBean;
import com.soonfor.measuremanager.home.othertask.activity.OtherTaskDetailActivity;
import com.soonfor.measuremanager.home.othertask.model.bean.OtherTaskMainBean;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.dialog.normal.NormalDialog;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2018-01-30.
 */

/**
 * 修改人：DC-ZhuSuiBo on 2018/2/2 14:39
 * 邮箱：suibozhu@139.com
 */
public class GrabOrderListAdapter extends BaseAdapter<GrabOrderListAdapter.ViewHolder, GrabOrderBean> implements View.OnClickListener {

    private List<GrabOrderBean> list;

    Activity activity;
    private View.OnClickListener grabListener;

    public GrabOrderListAdapter(Activity context, List<GrabOrderBean> list, View.OnClickListener grabListener) {
        super(context);
        activity = context;
        this.list = list;
        this.grabListener = grabListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_graborder, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GrabOrderBean gpBean = list.get(position);
        holder.setData(gpBean);
        setViewByStatus(gpBean, holder);
        holder.llfItem.setTag(position);
        holder.btnInstantlyGrab.setTag(position);
        holder.rlfDesignPhoto.setTag(position);
        holder.llfItem.setOnClickListener(this);
        holder.btnInstantlyGrab.setOnClickListener(grabListener);
        holder.rlfDesignPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llfItem:
                int pos = (int) v.getTag();
                GrabOrderBean b = list.get(pos);
                Bundle bundle = new Bundle();
                Intent i;
                if (!b.getTaskType().equals("")) {
                    switch (Integer.parseInt(b.getTaskType())) {
                        case 0://量尺任务
                            bundle.putInt("isGrabIn", 1); //1 是 其它： 不是 默认 -1
                            LiangChiBean lb = convertToLcbean(b);
                            if (lb != null) {
                                bundle.putParcelable("LiangChiBean", lb);
                            }
                            bundle.putInt("POSITION", pos);
                            i = new Intent(activity, LiangChiDetailActivity.class);
                            i.putExtras(bundle);
                            activity.startActivity(i);
                            break;
                        case 1://复尺任务
                            bundle.putInt("isGrabIn", 1); //1 是 其它： 不是 默认 -1
                            LiangChiBean lbf = convertToLcbean(b);
                            if (lbf != null) {
                                bundle.putParcelable("LiangChiBean", lbf);
                            }
                            bundle.putInt("POSITION", pos);
                            i = new Intent(activity, FuChiDetailActivity.class);
                            i.putExtras(bundle);
                            activity.startActivity(i);
                            break;
                        case 2://放样任务
                            bundle.putInt(Tokens.Lofing.ITEMSKIPDETAIL_STATUS, -1);
                            LoftingMainBean lmb = convertToFybean(b);
                            bundle.putInt("POSITION", pos);
                            bundle.putParcelable(Tokens.Lofing.ITEMSKIPDETAIL_ITEM, lmb);
                            i = new Intent(activity, LoftingDetailActivity.class);
                            i.putExtras(bundle);
                            activity.startActivity(i);
                            break;
                        case 3://设计任务
                            bundle.putInt(Tokens.Lofing.ITEMSKIPDETAIL_STATUS, -1);
                            bundle.putParcelable(Tokens.Design.ITEMSKIPDETAIL_ITEM, convertToDesignbean(b));
                            i = new Intent(activity, DesignDetailActivity.class);
                            i.putExtras(bundle);
                            activity.startActivity(i);
                            break;
                        default://其它任务（主动追踪任务、回访任务）
                            bundle.putInt(Tokens.OtherTask.OT_ITEMSKIPDETAIL_STATUS, -1);
                            bundle.putSerializable(Tokens.OtherTask.OT_ITEMSKIPDETAIL_ITEM, convertToOtherTaskbean(b));
                            i = new Intent(activity, OtherTaskDetailActivity.class);
                            i.putExtras(bundle);
                            activity.startActivity(i);
                            break;
                    }
                }
                break;
        }
    }

    @Override
    public void notifyDataSetChanged(List<GrabOrderBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvfTitle;//任务标题
        //TextView tvftypes;//任务类型
        TextView tvfName;//客户姓名和性别
        TextView tvfBuilding;//楼盘
        TextView tvfTiemTitle;//时间标题
        TextView tvfExecuteTime;//预约/执行时间
        TextView tvfWorkpoints;//工分
        LinearLayout llfItem;
        RelativeLayout rlfDesignPhoto;
        Button btnInstantlyGrab;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvfTitle = itemView.findViewById(R.id.tvfTitle);
            //this.tvftypes = itemView.findViewById(R.id.tvftypes);
            this.tvfName = itemView.findViewById(R.id.tvName);
            this.tvfBuilding = itemView.findViewById(R.id.tvfBuilding);
            this.tvfTiemTitle = itemView.findViewById(R.id.tvfTiemTitle);
            this.tvfExecuteTime = itemView.findViewById(R.id.tvfExecuteDate);
            this.tvfWorkpoints = itemView.findViewById(R.id.tvfWorkpoints);
            this.llfItem = itemView.findViewById(R.id.llfItem);
            this.rlfDesignPhoto = itemView.findViewById(R.id.rlfChangePhoto);
            this.btnInstantlyGrab = itemView.findViewById(R.id.btnInstantlyGrab);
        }

        public void setData(GrabOrderBean gpBean) {
            tvfTitle.setText(gpBean.getTitle());
            CommonUtils.setSex(tvfName, gpBean.getCustomerName(), gpBean.getCustomerSex());
            tvfBuilding.setText(gpBean.getBuilding());
            this.tvfName.setText(CommonUtils.formatStr(gpBean.getCustomerName()));
            this.tvfBuilding.setText(CommonUtils.formatStr(gpBean.getBuilding()));
            this.tvfWorkpoints.setText(CommonUtils.formatStr(gpBean.getWorkPoints()));
        }
    }

    /**
     * 转为量尺、复尺任务对象
     *
     * @param goBean
     * @return
     */
    public LiangChiBean convertToLcbean(GrabOrderBean goBean) {
        if (goBean != null) {
            Gson gson = new Gson();
            LiangChiBean liangChiBean = gson.fromJson(gson.toJson(goBean), LiangChiBean.class);
            liangChiBean.setStatus("1");
            return liangChiBean;
        } else {
            return null;
        }
    }

    /**
     * 转为放样任务对象
     */
    public LoftingMainBean convertToFybean(GrabOrderBean goBean) {
        if (goBean != null) {
            Gson gson = new Gson();
            LoftingMainBean loftingMainBean = gson.fromJson(gson.toJson(goBean), LoftingMainBean.class);
            loftingMainBean.setStatus("1");
            return loftingMainBean;
        } else {
            return null;
        }
    }

    /**
     * 转为设计任务对象
     */
    public DesignItemBean convertToDesignbean(GrabOrderBean goBean) {
        if (goBean != null) {
            Gson gson = new Gson();
            return gson.fromJson(gson.toJson(goBean), DesignItemBean.class);
        } else return null;
    }

    /**
     * 转为其它对象
     */
    public OtherTaskMainBean convertToOtherTaskbean(GrabOrderBean goBean) {
        if (goBean != null) {
            Gson gson = new Gson();
            OtherTaskMainBean result = gson.fromJson(gson.toJson(goBean), OtherTaskMainBean.class);
            result.setTaskId(goBean.getTaskNo());
            result.setTaskNo("");
            return result;
        } else return null;
    }

    private void setViewByStatus(GrabOrderBean gpBean, ViewHolder vh) {
        if (!gpBean.getTaskType().equals("")) {
            switch (Integer.parseInt(gpBean.getTaskType())) {
                case 0://量尺
                    vh.tvfTiemTitle.setText("预约量尺时间：");
                    vh.tvfExecuteTime.setText(gpBean.getAppointDate());
                    break;
                case 1://复尺
                    vh.tvfTiemTitle.setText("预约复尺时间：");
                    vh.tvfExecuteTime.setText(gpBean.getAppointDate());
                    break;
                case 2://放样
                    vh.tvfTiemTitle.setText("预约放样时间：");
                    vh.tvfExecuteTime.setText(gpBean.getAppointDate());
                    break;
                case 3://设计
                    vh.tvfTiemTitle.setText("预约沟通时间：");
                    vh.tvfExecuteTime.setText(gpBean.getConfirmDate());
                    break;
                default://其它任务
                    vh.tvfTiemTitle.setText("执行时间：");
                    vh.tvfExecuteTime.setText(gpBean.getExecDate());
                    break;
            }
        }
    }

}
