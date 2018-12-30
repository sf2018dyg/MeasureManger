package com.soonfor.evaluate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.evaluate.activity.EvaluateCustomersActivity;
import com.soonfor.evaluate.bean.Evaluate_IToCustomersBean;
import com.soonfor.evaluate.bean.RequestTemplateDto;
import com.soonfor.measuremanager.tools.CommonUtils;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-10-19 8:41
 * 邮箱：dingyg012655@126.com
 */
public class Evaluate_IToCustomersAdapter extends BaseAdapter<Evaluate_IToCustomersAdapter.ViewHolder, Evaluate_IToCustomersBean> {

    private List<Evaluate_IToCustomersBean> list;
    private int type;//那个Fragment
    View.OnClickListener listener;
    private int requestCode;

    public Evaluate_IToCustomersAdapter(Context context, int type, View.OnClickListener listener, int requestCode) {
        super(context);
        this.type = type;
        this.listener = listener;
        this.requestCode = requestCode;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_evaluate_i_to_clients, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Evaluate_IToCustomersBean bean = list.get(position);
        holder.setData(bean);
        if(type==0){
            holder.llfItem.setBackgroundResource(R.drawable.custom_select_item_bg_press);
        }else {
            holder.llfItem.setBackgroundResource(R.drawable.custom_select_item_bg);
        }
        holder.llfItem.setTag(position);
        holder.tvfServicesAvailable.setTag(position);
        holder.llfItem.setOnClickListener(listener);
        holder.tvfServicesAvailable.setOnClickListener(listener);
        if(type==0) {
            holder.btnfEvaluate.setTag(bean);
            holder.btnfEvaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Evaluate_IToCustomersBean itembean = (Evaluate_IToCustomersBean) v.getTag();
                    EvaluateCustomersActivity.start(context, "UnEvaluate_IToCustomersFragment", convertToEvalCustSaveBean(itembean), requestCode);
                }
            });
        }
    }

    @Override
    public void notifyDataSetChanged(List<Evaluate_IToCustomersBean> dataList) {
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
        TextView tvfServicesAvailable;//服务项目
        TextView tvfFinishTime;//完成时间
        TextView tvfIEvaluateDesc;//评论登记
        Button btnfEvaluate;//评价
        RelativeLayout rlfNext;
        LinearLayout llfItem;//item整体


        public ViewHolder(View itemView) {
            super(itemView);
            this.tvfName = itemView.findViewById(R.id.tvName);
            this.tvfBuilding = itemView.findViewById(R.id.tvfBuilding);
            this.tvfServicesAvailable = itemView.findViewById(R.id.tvfServicesAvailable);
            this.tvfFinishTime = itemView.findViewById(R.id.tvfFinishTime);
            this.tvfIEvaluateDesc = itemView.findViewById(R.id.tvfIEvaluateDesc);
            this.btnfEvaluate = itemView.findViewById(R.id.btnfEvaluate);
            this.llfItem = itemView.findViewById(R.id.llfItem);
            this.rlfNext = itemView.findViewById(R.id.rlfNext);
        }

        public void setData(Evaluate_IToCustomersBean bean) {
            CommonUtils.setSex(this.tvfName, bean.getCustomername(), bean.getCustomersex());
            this.tvfBuilding.setText(bean.getBuilding());
            this.tvfServicesAvailable.setText(bean.getFserviceprjname());
            this.tvfFinishTime.setText(bean.getFinishdate());
            switch (type) {
                case 0:
                    rlfNext.setVisibility(View.INVISIBLE);
                    tvfIEvaluateDesc.setVisibility(View.GONE);
                    btnfEvaluate.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    rlfNext.setVisibility(View.VISIBLE);
                    btnfEvaluate.setVisibility(View.GONE);
                    tvfIEvaluateDesc.setVisibility(View.VISIBLE);
                    tvfIEvaluateDesc.setText("整体评价：" + bean.getEval());
                    break;
            }
        }
    }
    /**
     * 转为上传所需的EvalCustSaveBean对象
     */
    public RequestTemplateDto convertToEvalCustSaveBean(Evaluate_IToCustomersBean itembean) {
        if (itembean != null) {
            RequestTemplateDto result = new RequestTemplateDto();
            result.setTaskId(itembean.getId());
            result.setFserviceprjid(itembean.getFserviceprjid());
            result.setTaskId(itembean.getTaskno());
            result.setTaskType(itembean.getTasktype());
            return result;
        } else return null;
    }
}
