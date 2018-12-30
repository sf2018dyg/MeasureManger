package com.soonfor.evaluate.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.evaluate.bean.ReturnVisitBean;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-10-18 15:17
 * 邮箱：dingyg012655@126.com
 */
public class RetrunVisitAnswerAdapter extends BaseAdapter<RetrunVisitAnswerAdapter.ViewHolder, ReturnVisitBean.Answer> {

    private List<ReturnVisitBean.Answer> list;
    private boolean isCheckMult;//是否可多选
    private ScoresListenner scoresListenner;
    private int Pposition;
    private boolean isCanEdit = true;//是否能编辑

    public RetrunVisitAnswerAdapter(Context context, boolean isCheckMult, int Ppostion, ScoresListenner scoresListenner, boolean isCanEdit) {
        super(context);
        this.isCheckMult = isCheckMult;
        this.Pposition = Ppostion;
        this.scoresListenner = scoresListenner;
        this.isCanEdit = isCanEdit;
    }

    @Override
    public void notifyDataSetChanged(List dataList) {
        list = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_returnvisit_answer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(context, position);
        holder.llfAnswer.setTag(position);
        if(isCanEdit) {
            holder.llfAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (holder.imgfCheck.isSelected()) {
                        list.get(pos).setFifchecked(0);
                    } else {
                        if (!isCheckMult) {//单选
                            for (int i = 0; i < list.size(); i++) {
                                if (i == pos) {
                                    list.get(i).setFifchecked(1);
                                } else {
                                    list.get(i).setFifchecked(0);
                                }
                            }
                        } else {
                            list.get(pos).setFifchecked(1);
                        }
                    }
                    scoresListenner.getScores(list, Pposition);
                }

            });
        }
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llfAnswer;
        ImageView imgfCheck;
        TextView tvfAnswer;

        public ViewHolder(View itemView) {
            super(itemView);
            this.llfAnswer = itemView.findViewById(R.id.llfAnswer);
            this.imgfCheck = itemView.findViewById(R.id.imgfCheck);
            this.tvfAnswer = itemView.findViewById(R.id.tvfAnswer);
            this.imgfCheck.setBackground(context.getDrawable(R.drawable.check_select));
        }

        public void setData(Context mContext, int position) {
            ReturnVisitBean.Answer bean = list.get(position);
            tvfAnswer.setText(bean.getFseldesc());
            imgfCheck.setSelected(bean.getFifchecked()==1?true:false);
        }
    }
    public interface ScoresListenner{
        void getScores(List<ReturnVisitBean.Answer> answers, int position);
    }
}
