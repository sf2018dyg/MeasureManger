package com.soonfor.repository.adapter.knowledge;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.repository.R;
import com.soonfor.repository.adapter.knowledge.staff.ReceiverMsgListAdapter;
import com.soonfor.repository.base.RepBaseAdapter;
import com.soonfor.repository.model.knowledge.StaffMsg;

import java.util.List;

/**
 * 作者：DC-DingYG on 2018-08-23 10:50
 * 邮箱：dingyg012655@126.com
 */
public class OnlineStaffAdapter extends RepBaseAdapter<OnlineStaffAdapter.ViewHolder, StaffMsg> {

    private List<StaffMsg> mMsgList;
    private Activity activity;
    public OnlineStaffAdapter(Activity context, List<StaffMsg> msgList) {
        super(context);
        activity = context;
        mMsgList = msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_staff_msg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //onBindViewHolder()用于对RecyclerView子项的数据进行赋值，会在每个子项被滚动到屏幕内的时候执行
        StaffMsg msg = mMsgList.get(position);
        //增加对消息类的判断，如果这条消息是收到的，显示左边布局，是发出的，显示右边布局
        if (msg.getType() == StaffMsg.TYPE_RECEIVED) {
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.leftMsg.setText(msg.getContent());
            if(msg.getKbList()==null || msg.getKbList().size()==0){
                holder.mRecyclerView.setVisibility(View.GONE);
            }else {
                holder.mRecyclerView.setVisibility(View.VISIBLE);
                LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
                holder.mRecyclerView.setLayoutManager(layoutManager);
                ReceiverMsgListAdapter rmAdapter = new ReceiverMsgListAdapter(activity, msg.getKbList());
                holder.mRecyclerView.setAdapter(rmAdapter);
            }

        } else if (msg.getType() == StaffMsg.TYPE_SENT) {
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList==null?0:mMsgList.size();
    }

    @Override
    public void notifyDataSetChanged(List<StaffMsg> dataList) {
        mMsgList = dataList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        RecyclerView mRecyclerView;


        public ViewHolder(View view) {
            super(view);
            leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            leftMsg = (TextView) view.findViewById(R.id.left_msg);
            rightMsg = (TextView) view.findViewById(R.id.right_msg);
            mRecyclerView = view.findViewById(R.id.msgRecyclerView);
        }
    }
}
