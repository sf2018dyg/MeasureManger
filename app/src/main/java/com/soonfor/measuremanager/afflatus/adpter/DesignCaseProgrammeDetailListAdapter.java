package com.soonfor.measuremanager.afflatus.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.bean.ProgrammeDetail;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/20 16:56
 * 邮箱：suibozhu@139.com
 * 方案中间那个各个房间的图
 */
public class DesignCaseProgrammeDetailListAdapter extends BaseAdapter<DesignCaseProgrammeDetailListAdapter.ViewHolder, ProgrammeDetail> {

    private List<ProgrammeDetail> list;
    static Context mContext;

    public DesignCaseProgrammeDetailListAdapter(Context context, List<ProgrammeDetail> list) {
        super(context);
        mContext = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adpter_programmedetail, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public void notifyDataSetChanged(List<ProgrammeDetail> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgview;
        private TextView txtlean;

        public ViewHolder(View itemView) {
            super(itemView);
            imgview = itemView.findViewById(R.id.imgview);
            txtlean = itemView.findViewById(R.id.txtlean);
        }

        public void setData(ProgrammeDetail gpBean) {
            ImageUtil.loadCaselibImage(mContext, gpBean.getImgpath(),imgview);
            txtlean.setText(CommonUtils.formatStr(gpBean.getTypename()));
        }

    }
}
