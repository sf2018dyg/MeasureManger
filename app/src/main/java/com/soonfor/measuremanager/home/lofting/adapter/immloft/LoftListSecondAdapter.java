package com.soonfor.measuremanager.home.lofting.adapter.immloft;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.lofting.activity.immloft.detail.mj_sdk.EditLfMemberActivity;
import com.soonfor.measuremanager.home.lofting.activity.immloft.detail.mj_sdk.LoftListSecondActivity;
import com.soonfor.measuremanager.home.lofting.activity.immloft.detail.mj_sdk.ShowLfMemberActivity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkComponentEntity;
import com.soonfor.measuremanager.tools.NoDoubleClickUtils;
import com.soonfor.measuremanager.tools.Tokens;

import java.util.List;

/**
 * Created by DingYg on 2018-02-09.
 */

public class LoftListSecondAdapter extends BaseAdapter<LoftListSecondAdapter.ViewHolder, MarkComponentEntity> implements View.OnClickListener {

    private List<MarkComponentEntity> clist;
    private String isMark;
    public LoftListSecondAdapter(Context context,List<MarkComponentEntity> list) {
        super(context);
        this.isMark = "1";
        this.clist = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_lofting_imm_list_item_child, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.rlfEdit.setTag(position);
        holder.rlfEdit.setOnClickListener(this);
        holder.setData(clist.get(position));
    }

    @Override
    public int getItemCount() {
        return clist == null ? 0 : clist.size();
    }

    @Override
    public void notifyDataSetChanged(List<MarkComponentEntity> dataList) {
        clist = dataList;
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged(List<MarkComponentEntity> dataList, String isMark) {
        clist = dataList;
        this.isMark = isMark;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlfEdit:
                int positon = (int) v.getTag();
                if(!NoDoubleClickUtils.isDoubleClick()) {
                    LoftListSecondActivity.CusPosition = positon;
                    Intent intent=new Intent();
                    if(isMark.equals("1")){
                        intent.setClass(context, ShowLfMemberActivity.class);
                        intent.putExtra(Tokens.Lofing.SKIP_ENTER_SHOWMEMBER_SIZE_STRIGN, clist.get(positon));
                        ((Activity) context).startActivity(intent);
                    }else {
                        intent.setClass(context,EditLfMemberActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt(Tokens.Lofing.SKIP_ENTER_EDIT_SIZE_POSITION, positon);
                        bundle.putParcelable(Tokens.Lofing.SKIP_ENTER_EDIT_SIZE_STRIGN, clist.get(positon));
                        bundle.putParcelable(Tokens.Lofing.SKIP_ENTER_EDIT_SIZE_STRIGN_CHILD, clist.get(positon));
                        intent.putExtras(bundle);
                        ((Activity) context).startActivityForResult(intent, Tokens.Lofing.REQUEST_CODE_EDIT_SIZE);
                    }

                    ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                break;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvfMember;
        public TextView tvfMemberSize;
        public ImageView imgfStatus;
        public RelativeLayout rlfEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvfMember = itemView.findViewById(R.id.tvfLfMember);
            this.tvfMemberSize = itemView.findViewById(R.id.tvfLfMemberSize);
            this.imgfStatus = itemView.findViewById(R.id.imgfState);
            this.rlfEdit = itemView.findViewById(R.id.rlfEdit);
        }
        public void setData(MarkComponentEntity component){
            if(component!=null){
                tvfMember.setText(component.getName());
                tvfMemberSize.setText("宽"+component.getWidth() + "*高"+component.getHeight()
                        + "*厚" +component.getDeep());
                switch (component.getEditState()){
                    case 1://未编辑
                        imgfStatus.setImageResource(R.mipmap.icn_state03);
                        break;
                    case 2://编辑后发现尺寸有差别
                        imgfStatus.setImageResource(R.mipmap.icn_state02);
                        break;
                    case 3://编辑成功
                        imgfStatus.setImageResource(R.mipmap.icn_state01);
                        break;
                }
            }
        }
    }
}