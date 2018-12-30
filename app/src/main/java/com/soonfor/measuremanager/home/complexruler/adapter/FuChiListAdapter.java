package com.soonfor.measuremanager.home.complexruler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.complexruler.activity.FuChiMainActivity;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiBean;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.DensityUtils;
import com.soonfor.measuremanager.tools.ViewTools;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 16:11
 * 邮箱：suibozhu@139.com
 */

/**
 * 修改人：DC-ZhuSuiBo on 2018/2/7 8:32
 * 邮箱：suibozhu@139.com
 */
public class FuChiListAdapter extends BaseAdapter<FuChiListAdapter.ViewHolder, LiangChiBean> {

    private List<LiangChiBean> list;
    private static FuChiMainActivity mainActivity;
    private static int tabIndex;
    static View.OnClickListener listener;

    public FuChiListAdapter(Context context, FuChiMainActivity mainActivity, List<LiangChiBean> list, int tabIndex,View.OnClickListener listener) {
        super(context);
        this.mainActivity = mainActivity;
        this.list = list;
        this.tabIndex = tabIndex;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_fuchi, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
        holder.llfItem.setOnClickListener(listener);
        holder.llfItem.setTag(list.get(position));
        holder.jieshou.setTag(position + "@" + list.get(position).getTaskNo());
        holder.jushou.setTag(position + "@" + list.get(position).getTaskNo());
        holder.querenyuyue.setTag(list.get(position));
        holder.lijiliangchi.setTag(list.get(position));
    }

    @Override
    public void notifyDataSetChanged(List<LiangChiBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llfbtnbarright;
        RelativeLayout llfbtnbar;
        TextView tvfType;//任务类型
        TextView tvfName;//客户姓名和性别
        TextView tvfBuilding;//楼盘
        TextView tvfAppointTime;//预约时间
        TextView tvfAddressT;//地址标题（当设计时为客户需求）
        TextView tvAddress;//地址
        TextView tvfWorkpoints;//工分
        LinearLayout llfItem;
        LinearLayout qrsmsj;
        LinearLayout lcwcsj;
        Button jushou;
        Button jieshou;
        Button querenyuyue;
        Button lijiliangchi;
        TextView tvftypes;
        TextView tvfconfirmtime;
        TextView tvfliangchifinishtime;

        public ViewHolder(View itemView) {
            super(itemView);
            this.llfbtnbarright = itemView.findViewById(R.id.llfbtnbarright);
            this.llfbtnbar = itemView.findViewById(R.id.llfbtnbar);
            this.tvfType = itemView.findViewById(R.id.tvfTaskType);
            this.tvfName = itemView.findViewById(R.id.tvName);
            this.tvfBuilding = itemView.findViewById(R.id.tvfBuilding);
            this.tvfAppointTime = itemView.findViewById(R.id.tvfYYDate);
            this.tvfAddressT = itemView.findViewById(R.id.tvfAddressT);
            this.tvAddress = itemView.findViewById(R.id.tvfAddress);
            this.tvfWorkpoints = itemView.findViewById(R.id.tvfWorkpoints);
            this.llfItem = itemView.findViewById(R.id.llfItem);
            qrsmsj = itemView.findViewById(R.id.qrsmsj);
            lcwcsj = itemView.findViewById(R.id.lcwcsj);
            jushou = itemView.findViewById(R.id.jushou);
            jieshou = itemView.findViewById(R.id.jieshou);
            querenyuyue = itemView.findViewById(R.id.querenyuyue);
            lijiliangchi = itemView.findViewById(R.id.lijiliangchi);
            tvftypes = itemView.findViewById(R.id.tvftypes);
            tvfconfirmtime = itemView.findViewById(R.id.tvfconfirmtime);
            tvfliangchifinishtime = itemView.findViewById(R.id.tvfliangchifinishtime);
            ViewTools.returnDrawable(mainActivity, this.tvfName, 3, R.mipmap.btn_huaxiang, new int[]{0, 0, DensityUtils.dp2px(mainActivity, 18), DensityUtils.dp2px(mainActivity, 18)});

            jushou.setOnClickListener(listener);
            jieshou.setOnClickListener(listener);
            querenyuyue.setOnClickListener(listener);
            lijiliangchi.setOnClickListener(listener);
            tvfName.setOnClickListener(listener);
        }

        public void setData(LiangChiBean gpBean) {
            this.tvfType.setText("复尺");
            this.tvfType.setBackgroundResource(R.drawable.bg_red);
            this.tvfAddressT.setText("地址：");
            switch (gpBean.getStatus()) {
                case 1:
                    qrsmsj.setVisibility(View.GONE);
                    lcwcsj.setVisibility(View.GONE);
                    jushou.setVisibility(View.VISIBLE);
                    jieshou.setVisibility(View.VISIBLE);
                    querenyuyue.setVisibility(View.GONE);
                    lijiliangchi.setVisibility(View.GONE);
                    tvftypes.setText("待接收");
                    llfbtnbar.setGravity(Gravity.LEFT);
                    llfbtnbarright.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    qrsmsj.setVisibility(View.GONE);
                    lcwcsj.setVisibility(View.GONE);
                    jushou.setVisibility(View.GONE);
                    jieshou.setVisibility(View.GONE);
                    querenyuyue.setVisibility(View.VISIBLE);
                    lijiliangchi.setVisibility(View.GONE);
                    tvftypes.setText("待上门确认");
                    llfbtnbar.setGravity(Gravity.LEFT);
                    llfbtnbarright.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    qrsmsj.setVisibility(View.VISIBLE);
                    lcwcsj.setVisibility(View.GONE);
                    jushou.setVisibility(View.GONE);
                    jieshou.setVisibility(View.GONE);
                    querenyuyue.setVisibility(View.GONE);
                    lijiliangchi.setVisibility(View.VISIBLE);
                    tvftypes.setText("待复尺");
                    llfbtnbar.setGravity(Gravity.LEFT);
                    llfbtnbarright.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    qrsmsj.setVisibility(View.VISIBLE);
                    lcwcsj.setVisibility(View.VISIBLE);
                    jushou.setVisibility(View.GONE);
                    jieshou.setVisibility(View.GONE);
                    querenyuyue.setVisibility(View.GONE);
                    lijiliangchi.setVisibility(View.GONE);
                    tvftypes.setText("复尺完成");
                    llfbtnbar.setGravity(Gravity.CENTER);
                    llfbtnbarright.setVisibility(View.GONE);
                    break;
            }
            ViewTools.returnDrawable(mainActivity, tvfName, 3, R.mipmap.btn_huaxiang, new int[]{0, 0, DensityUtils.dp2px(mainActivity, 18), DensityUtils.dp2px(mainActivity, 18)});
            CommonUtils.setSex(this.tvfName, CommonUtils.formatStr(gpBean.getCustomerName()), CommonUtils.formatStr(gpBean.getCustomerSex()));
            this.tvfBuilding.setText(CommonUtils.formatStr(gpBean.getBuilding()) + "");
            this.tvfAppointTime.setText(DateTool.getTimeTimestamp(gpBean.getAppointDate(), "MM月dd日-HH:mm") + "");
            this.tvAddress.setText(CommonUtils.formatStr(gpBean.getAddress()) + "");
            this.tvfWorkpoints.setText(CommonUtils.formatStr(gpBean.getWorkPoints()==null?"0":gpBean.getWorkPoints()) + "");
            tvfconfirmtime.setText(DateTool.getTimeTimestamp(gpBean.getConfirmDate(), "MM月dd日-HH:mm") + "");
            tvfliangchifinishtime.setText(DateTool.getTimeTimestamp(gpBean.getFinishDate(), "MM月dd日-HH:mm") + "");
        }
    }

    public static int getTabIndex() {
        return tabIndex;
    }

    public static void setTabIndex(int tabIndex) {
        FuChiListAdapter.tabIndex = tabIndex;
    }
}
