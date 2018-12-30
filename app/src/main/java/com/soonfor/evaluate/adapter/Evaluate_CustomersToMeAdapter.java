package com.soonfor.evaluate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.evaluate.bean.Evaluate_CustomersToMeBean;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.DateTool;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-10-17 18:38
 * 邮箱：dingyg012655@126.com
 */
public class Evaluate_CustomersToMeAdapter extends BaseAdapter<Evaluate_CustomersToMeAdapter.ViewHolder, Evaluate_CustomersToMeBean> {

    private List<Evaluate_CustomersToMeBean> list;
    private int type;//那个Fragment
    View.OnClickListener listener;

    public Evaluate_CustomersToMeAdapter(Context context, int type, View.OnClickListener listener) {
        super(context);
        this.type = type;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_evaluate_clients_to_me, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
        if(type==0){
            holder.llfItem.setBackgroundResource(R.drawable.custom_select_item_bg_press);
        }else {
            holder.llfItem.setBackgroundResource(R.drawable.custom_select_item_bg);
        }
        holder.llfItem.setTag(position);
        holder.tvfServicesAvailable.setTag(position);
        holder.llfItem.setOnClickListener(listener);
        holder.tvfServicesAvailable.setOnClickListener(listener);
    }

    @Override
    public void notifyDataSetChanged(List<Evaluate_CustomersToMeBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvfName;//客户姓名和性别
        TextView tvfBuilding;//楼盘地址
        ImageView imgfFNewNotify;//是否为新评价
        TextView tvfServicesAvailable;//服务项目
        TextView tvfFinishTime;//完成时间
        TextView tvfClientEvaluate;//客户评价
        LinearLayout llfCustEval;
        RelativeLayout rlfNext;
        LinearLayout llfVisit;
        TextView tvfReturnVisit;//人工回访
        TextView tvfEvaluateDesc;//客户评价等级
        TextView tvfReturnVisitScores;//回访得分

        LinearLayout llfItem;//item整体


        public ViewHolder(View itemView) {
            super(itemView);
            this.tvfName = itemView.findViewById(R.id.tvName);
            this.tvfBuilding = itemView.findViewById(R.id.tvfBuilding);
            this.imgfFNewNotify = itemView.findViewById(R.id.imgfFNewNotify);
            this.tvfServicesAvailable = itemView.findViewById(R.id.tvfServicesAvailable);
            this.tvfFinishTime = itemView.findViewById(R.id.tvfFinishTime);
            this.tvfClientEvaluate = itemView.findViewById(R.id.tvfClientEvaluate);
            llfCustEval = itemView.findViewById(R.id.llfCustEval);
            llfVisit = itemView.findViewById(R.id.llfVisit);
            this.tvfReturnVisit = itemView.findViewById(R.id.tvfReturnVisit);
            this.tvfEvaluateDesc = itemView.findViewById(R.id.tvfEvaluateDesc);
            this.tvfReturnVisitScores = itemView.findViewById(R.id.tvfReturnVisitScores);
            this.llfItem = itemView.findViewById(R.id.llfItem);
            this.rlfNext = itemView.findViewById(R.id.rlfNext);
        }

        public void setData(Evaluate_CustomersToMeBean bean) {
            CommonUtils.setSex(this.tvfName, bean.getCustomername(), bean.getCustomersex());
            this.tvfBuilding.setText(bean.getBuilding());
            this.tvfServicesAvailable.setText(bean.getFserviceprjname());
            this.tvfFinishTime.setText(DateTool.getTimeTimestamp(bean.getFinishdate(), "yyyy-MM-dd HH:mm"));
            if(type==0){
                rlfNext.setVisibility(View.INVISIBLE);
            }else {
                rlfNext.setVisibility(View.VISIBLE);
            }
            if(bean.getFifuse()==1) {
                llfCustEval.setVisibility(View.VISIBLE);
                if (bean.getEvalstatus().equals("1")) {
                    this.tvfClientEvaluate.setText("已评价");
                    if(bean.getIfallhighappraise()==1) {
                        this.tvfEvaluateDesc.setText("整体评价：" + bean.getEval());
                    }
                } else {
                    this.tvfClientEvaluate.setText("未评价");
                    this.tvfEvaluateDesc.setText("");
                }
            }else {
                llfCustEval.setVisibility(View.GONE);
            }
            if (bean.getFifrtnvisit() == 1) {//已启用人工回访
                llfVisit.setVisibility(View.VISIBLE);
                if (bean.getFeedbackstatus().equals("1")) {
                    tvfReturnVisit.setText("已回访");
                    this.tvfReturnVisitScores.setText("回访得分：" + bean.getVissorce());
                } else {
                    tvfReturnVisit.setText("待回访");
                    this.tvfReturnVisitScores.setText("");
                }
            } else {
                llfVisit.setVisibility(View.GONE);
            }
        }
    }
}