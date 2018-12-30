package com.soonfor.evaluate.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.evaluate.bean.EvaluateTemplateBean;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 作者：DC-DingYG on 2018-10-18 15:17
 * 邮箱：dingyg012655@126.com
 * 评价模板
 */
public class EvaluateLevlEditAdapter extends BaseAdapter<EvaluateLevlEditAdapter.ViewHolder, EvaluateTemplateBean.TemplateItemDto> {

    private List<EvaluateTemplateBean.TemplateItemDto> mlist;
    private DescListenner descListenner;

    public List<EvaluateTemplateBean.TemplateItemDto> getMlist() {
        return mlist;
    }
    private long toughTime = 0;

    public EvaluateLevlEditAdapter(Context context, List<EvaluateTemplateBean.TemplateItemDto> list, DescListenner descListenner) {
        super(context);
        this.mlist = list;
        this.descListenner = descListenner;
    }

    @Override
    public void notifyDataSetChanged(List dataList) {
        mlist = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_evaluate_levl_edit, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position);
        holder.mrBar.setTag(R.id.item_id, position);
        holder.mrBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean isUserClick) {
                if(isUserClick){//是不是用户点击
                    int pos = (int) ratingBar.getTag(R.id.item_id);
                    EvaluateTemplateBean.TemplateItemDto itemDto = mlist.get(pos);
                    int r = (int) (rating + 0.5f); // 四舍五入
                    ratingBar.setRating(r);
                    mlist.get(pos).setfItemStep(r);
                    switch (r) {
                        case 0:
                            mlist.get(pos).setfItemValue(0);
                            mlist.get(pos).setfItemTilte("");
                            mlist.get(pos).setfItemDesc("");
                            break;
                        case 1:
                            mlist.get(pos).setfItemValue(itemDto.getOnevalue());
                            mlist.get(pos).setfItemTilte(itemDto.getOnetitle());
                            mlist.get(pos).setfItemDesc(itemDto.getOnedesc());
                            break;
                        case 2:
                            mlist.get(pos).setfItemValue(itemDto.getTwovalue());
                            mlist.get(pos).setfItemTilte(itemDto.getTwotitle());
                            mlist.get(pos).setfItemDesc(itemDto.getTwodesc());
                            break;
                        case 3:
                            mlist.get(pos).setfItemValue(itemDto.getThreevalue());
                            mlist.get(pos).setfItemTilte(itemDto.getThreetitle());
                            mlist.get(pos).setfItemDesc(itemDto.getThreedesc());
                            break;
                        case 4:
                            mlist.get(pos).setfItemValue(itemDto.getFourvalue());
                            mlist.get(pos).setfItemTilte(itemDto.getFourtitle());
                            mlist.get(pos).setfItemDesc(itemDto.getFourdesc());
                            break;
                        case 5:
                            mlist.get(pos).setfItemValue(itemDto.getFivevalue());
                            mlist.get(pos).setfItemTilte(itemDto.getFivetitle());
                            mlist.get(pos).setfItemDesc(itemDto.getFivedesc());
                            break;
                    }
                    specialUpdate(pos);
                    descListenner.setDesc(mlist);
                }
            }
        });
    }

    private void specialUpdate(int pos) {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                notifyItemChanged(pos);
            }
        };
        handler.post(r);
    }


    @Override
    public int getItemCount() {
        return mlist == null ? 0 : mlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvfEvalObj;//评价的项目
        MaterialRatingBar mrBar;
        TextView tvfAttitudePoint;
        TextView tvfAttitudeDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvfEvalObj = itemView.findViewById(R.id.tvfEvalObj);
            this.mrBar = itemView.findViewById(R.id.mRatingBar_Attitude);
            this.tvfAttitudePoint = itemView.findViewById(R.id.tvfAttitudePoint);
            this.tvfAttitudeDesc = itemView.findViewById(R.id.tvfAttitudeDesc);
        }

        public void setData(int position) {
            EvaluateTemplateBean.TemplateItemDto bean = mlist.get(position);
            tvfEvalObj.setText(bean.getTitle().trim());
            mrBar.setNumStars(5);
            mrBar.setRating(bean.getfItemStep());
            switch (bean.getfItemStep()) {
                case 0:
                    tvfAttitudePoint.setText("");
                    tvfAttitudeDesc.setText("");
                    break;
                case 1:
                    tvfAttitudePoint.setText(bean.getOnevalue() + "分");
                    tvfAttitudeDesc.setText(bean.getOnedesc());
                    break;
                case 2:
                    tvfAttitudePoint.setText(bean.getTwovalue() + "分");
                    tvfAttitudeDesc.setText(bean.getTwodesc());
                    break;
                case 3:
                    tvfAttitudePoint.setText(bean.getThreevalue() + "分");
                    tvfAttitudeDesc.setText(bean.getThreedesc());
                    break;
                case 4:
                    tvfAttitudePoint.setText(bean.getFourvalue() + "分");
                    tvfAttitudeDesc.setText(bean.getFourdesc());
                    break;
                case 5:
                    tvfAttitudePoint.setText(bean.getFivevalue() + "分");
                    tvfAttitudeDesc.setText(bean.getFivedesc());
                    break;
            }
        }
    }

    public interface DescListenner {
        void setDesc(List<EvaluateTemplateBean.TemplateItemDto> mList);
    }
}
