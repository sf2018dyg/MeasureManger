package com.soonfor.measuremanager.afflatus.adpter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.gallerlib.ScreenUtils;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.bean.ProgrammeBean;
import com.soonfor.measuremanager.afflatus.bean.ProgrammeDetail;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.tools.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/20 16:56
 * 邮箱：suibozhu@139.com
 * 方案中间那个各个房间的图
 */
public class DesignCaseProgrammeListAdapter extends BaseAdapter<DesignCaseProgrammeListAdapter.ViewHolder, ProgrammeBean> {

    private static List<ProgrammeBean> list;
    static Context mContext;
    private int curTabHeight = 0;

    public DesignCaseProgrammeListAdapter(Context context, List<ProgrammeBean> list) {
        super(context);
        mContext = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adpter_programme, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
        holder.adapter.notifyDataSetChanged(list.get(position).getLtdetail());
    }

    @Override
    public void notifyDataSetChanged(List<ProgrammeBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtfangwei;
        private TextView txtalias;
        private TextView txtdesc;
        private RecyclerView rlvpromme;
        private GridLayoutManager manager;
        private List<ProgrammeDetail> datas;
        private DesignCaseProgrammeDetailListAdapter adapter;

        public ViewHolder(View itemView) {
            super(itemView);
            txtfangwei = itemView.findViewById(R.id.txtfangwei);
            txtalias = itemView.findViewById(R.id.txtalias);
            txtdesc = itemView.findViewById(R.id.txtdesc);
            rlvpromme = itemView.findViewById(R.id.rlvpromme);

            manager = new GridLayoutManager(mContext, 1);
            rlvpromme.setLayoutManager(manager);
            datas = new ArrayList<ProgrammeDetail>();
            adapter = new DesignCaseProgrammeDetailListAdapter(mContext, datas);
            rlvpromme.setAdapter(adapter);

            //重算高度
            ViewGroup.LayoutParams layoutParams = rlvpromme.getLayoutParams();
            layoutParams.height = list.size() * ScreenUtils.dip2px(mContext,232);
            rlvpromme.setLayoutParams(layoutParams);
        }

        public void setData(ProgrammeBean gpBean) {
            txtfangwei.setText(CommonUtils.formatStr(gpBean.getFangwei()));
            txtalias.setText(CommonUtils.formatStr(gpBean.getAlias()));
            txtdesc.setText(CommonUtils.formatStr(gpBean.getDesc()));

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)txtdesc.getLayoutParams();
            setCurTabHeight(layoutParams.height);
        }

    }

    public int getCurTabHeight() {
        return curTabHeight;
    }

    public void setCurTabHeight(int curTabHeight) {
        this.curTabHeight = curTabHeight;
    }
}
