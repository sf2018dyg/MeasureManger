package com.soonfor.evaluate.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.evaluate.bean.ReturnVisitBean;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-10-18 15:17
 * 邮箱：dingyg012655@126.com
 */
public class RetrunVisitAdapter extends BaseAdapter<RetrunVisitAdapter.ViewHolder, ReturnVisitBean> {

    private List<ReturnVisitBean> rvlist;
    private boolean isCanEdit;//是否可编辑
    private RSoroceListerner rsListener;//反写总分

    private int inputId = -1;//正在输入的项的id

    public List<ReturnVisitBean> getRvlist() {
        return rvlist;
    }

    public void setRvlist(List<ReturnVisitBean> rvlist) {
        this.rvlist = rvlist;
    }

    public RetrunVisitAdapter(Context context, boolean isCanEdit, RSoroceListerner rsListener) {
        super(context);
        this.isCanEdit = isCanEdit;
        this.rsListener = rsListener;
    }

    @Override
    public void notifyDataSetChanged(List dataList) {
        rvlist = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_returnvisit, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(context, position);
    }


    @Override
    public int getItemCount() {
        return rvlist == null ? 0 : rvlist.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvfSeq;//序号
        TextView tvfVTitle;//题目
        TextView tvfTscores;//本地得分
        RecyclerView mChildRecyclerView;
        EditText etfMyAnswer;
        public ViewHolder(View itemView) {
            super(itemView);
            this.tvfSeq = itemView.findViewById(R.id.tvfSeq);
            this.tvfVTitle = itemView.findViewById(R.id.tvfVTitle);
            this.tvfTscores = itemView.findViewById(R.id.tvfTscores);
            this.mChildRecyclerView = itemView.findViewById(R.id.mChildRecyclerView);
            this.etfMyAnswer = itemView.findViewById(R.id.etfMyAnswer);
        }

        public void setData(Context mContext, int position) {
            ReturnVisitBean bean = rvlist.get(position);
            String content = bean.getFrvisitprjdesc();
            switch (Integer.parseInt(bean.getFtype())) {
                case 1:
                    content = content + "（是非题）";
                    etfMyAnswer.setVisibility(View.GONE);
                    mChildRecyclerView.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    content = content + "（单选题）";
                    etfMyAnswer.setVisibility(View.GONE);
                    mChildRecyclerView.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    content = content + "（多选题）";
                    etfMyAnswer.setVisibility(View.GONE);
                    mChildRecyclerView.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    content = content + "（问答题）";
                    mChildRecyclerView.setVisibility(View.GONE);
                    etfMyAnswer.setVisibility(View.VISIBLE);
                    etfMyAnswer.removeTextChangedListener(textWatcher);
                    String defaultText = bean.getFanswerdesc();
                    etfMyAnswer.setText(defaultText);
                    if(isCanEdit){
                        etfMyAnswer.setEnabled(true);
                        if(defaultText.length()>0){
                            etfMyAnswer.setSelection(defaultText.length());
                        }else {
                            etfMyAnswer.setHint("请在此处答题");
                        }
                        etfMyAnswer.setTag(R.id.child_position, position);
                        etfMyAnswer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if(hasFocus) {
                                    int pos = (int) v.getTag(R.id.child_position);
                                    if (pos < rvlist.size()) {
                                        inputId = pos;
                                    }
                                }else {
                                    //notifyItemChanged(inputId);
                                }
                            }
                        });
                        etfMyAnswer.addTextChangedListener(textWatcher);

                    }else {
                        etfMyAnswer.setEnabled(false);
                    }
                    break;

            }
            SpannableStringBuilder spannableString = new SpannableStringBuilder(content);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#c7c7c7"));
            if (content.contains("（"))
                spannableString.setSpan(colorSpan, content.indexOf("（"), content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            else
                spannableString.setSpan(colorSpan, content.length(), content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvfVTitle.setText(spannableString);
            tvfSeq.setText((position + 1) + ".");
            tvfTscores.setText(bean.getFactpoint() + "");
            if(bean.getResultItemDto().size()>0) {
                GridLayoutManager manager = new GridLayoutManager(mContext, 1);
                RetrunVisitAnswerAdapter answerAdapter;
                if (bean.getFtype().equals("3")) {//多选
                    answerAdapter = new RetrunVisitAnswerAdapter(mContext, true, position, new RetrunVisitAnswerAdapter.ScoresListenner() {
                        @Override
                        public void getScores(List<ReturnVisitBean.Answer> answers, int pos) {
                            rvlist.get(pos).setResultItemDto(answers);
                            int itemPoints = 0;
                            for(int a = 0; a<answers.size(); a++){
                                if(answers.get(a).getFifchecked()==1){
                                    itemPoints = itemPoints + Integer.parseInt(answers.get(a).getFpoint());
                                }
                            }
                            rvlist.get(pos).setFactpoint(itemPoints);
                            //检查答题得出分数
                            rsListener.getTotalPoints(true);
                            notifyItemChanged(pos);
                        }
                    }, isCanEdit);
                } else {
                    answerAdapter = new RetrunVisitAnswerAdapter(mContext, false, position, new RetrunVisitAnswerAdapter.ScoresListenner() {
                        @Override
                        public void getScores(List<ReturnVisitBean.Answer> answers, int pos) {
                            rvlist.get(pos).setResultItemDto(answers);
                            int itemPoints = 0;
                            for(int a = 0; a<answers.size(); a++){
                                if(answers.get(a).getFifchecked()==1){
                                    itemPoints = itemPoints + Integer.parseInt(answers.get(a).getFpoint());
                                }
                            }
                            rvlist.get(pos).setFactpoint(itemPoints);
                            //检查答题得出分数
                            rsListener.getTotalPoints(true);
                            notifyItemChanged(pos);
                        }
                    }, isCanEdit);
                }
                mChildRecyclerView.setLayoutManager(manager);
                mChildRecyclerView.setAdapter(answerAdapter);
                mChildRecyclerView.setSaveEnabled(false);
                answerAdapter.notifyDataSetChanged(bean.getResultItemDto());
            }
        }
    }

    public interface RSoroceListerner {
        void getTotalPoints(boolean isRefreshRecyvlerView);
    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(inputId>=0) {
                if (s.toString() != null && s.toString().trim().length() > 0) {
                    rvlist.get(inputId).setFanswerdesc(s.toString().trim());
                    rvlist.get(inputId).setFactpoint(rvlist.get(inputId).getFpoint());
                    //检查答题得出分数
                    rsListener.getTotalPoints(false);
                } else {
                    rvlist.get(inputId).setFanswerdesc("");
                    rvlist.get(inputId).setFactpoint(0);
                    //检查答题得出分数
                    rsListener.getTotalPoints(false);
                }
            }
        }
    };
}
