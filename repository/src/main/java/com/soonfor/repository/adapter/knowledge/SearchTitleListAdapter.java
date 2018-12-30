package com.soonfor.repository.adapter.knowledge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soonfor.repository.R;
import com.soonfor.repository.base.RepBaseAdapter;
import com.soonfor.repository.model.knowledge.SearchTitleBean;
import com.soonfor.repository.tools.Tokens;
import com.soonfor.repository.ui.activity.knowledge.KnowledgeDetailActivity;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018/7/20 13:50
 * 邮箱：dingyg012655@126.com
 * 回复评论
 */
public class SearchTitleListAdapter extends RepBaseAdapter<SearchTitleListAdapter.ViewHolder, SearchTitleBean> {

    private List<SearchTitleBean> list;
    private Context mContext;

    public SearchTitleListAdapter(Context context, List<SearchTitleBean> list) {
        super(context);
        mContext = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adpter_searchtitle, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position), position);
        holder.txtname.setTag(position);
    }

    @Override
    public void notifyDataSetChanged(List<SearchTitleBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtname;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtname = itemView.findViewById(R.id.txtname);
            this.txtname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int Posi = (int)v.getTag();
                    Intent intent = new Intent(context, KnowledgeDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ITEM_ID", list.get(Posi).getId());
                    bundle.putParcelable("ITEM_BEAN", list.get(Posi));
                    bundle.putInt("LIST_POSITION", Posi);
                    bundle.putString("LAST_VIEWTYPE", "SearchActivity");
                    intent.putExtras(bundle);
                    ((Activity)context).startActivityForResult(intent, Tokens.Knowledge.CLICK_TO_DETAIL);
                }
            });
        }

        public void setData(SearchTitleBean gpBean, int position) {
            String title = gpBean.getTitle().replace("<em>","<font color='#0599fd'>").replace("</em>","</font>");
            txtname.setText(Html.fromHtml(title));
        }
    }
}
