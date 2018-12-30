package com.soonfor.measuremanager.afflatus.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.bean.cehua.DesignCehuaBean;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.tools.CommonUtils;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/21 10:33
 * 邮箱：suibozhu@139.com
 */
public class DesignDrawLayoutAdpter extends BaseAdapter<DesignDrawLayoutAdpter.ViewHolder, DesignCehuaBean.Datas> {

    private List<DesignCehuaBean.Datas> beans;
    private Context mContext;
    private int selected = -1;
    private boolean[] selecteds;
    private boolean isSingleSelect;
    private String key;

    public DesignDrawLayoutAdpter(Context context, List<DesignCehuaBean.Datas> beans, boolean isSingleSelect) {
        super(context);
        mContext = context;
        this.beans = beans;
        selecteds = new boolean[beans.size()];
        this.isSingleSelect = isSingleSelect;
    }

    @Override
    public void notifyDataSetChanged(List dataList) {
        this.beans = dataList;
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged(String key) {
        this.key = key;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adpter_drawlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setDatas(beans.get(position));
        holder.txt_name.setTag(position);
        if (isSingleSelect) {
            if (selected == position) {
                holder.txt_name.setBackgroundResource(R.drawable.button_jieshou_bg);
                holder.imgjiaobiao.setVisibility(View.VISIBLE);
            } else {
                holder.txt_name.setBackgroundResource(R.drawable.button_jushou_bg);
                holder.imgjiaobiao.setVisibility(View.INVISIBLE);
            }
        } else {
            if (selecteds != null) {
                if (selecteds[position]) {
                    holder.txt_name.setBackgroundResource(R.drawable.button_jieshou_bg);
                    holder.imgjiaobiao.setVisibility(View.VISIBLE);
                } else {
                    holder.txt_name.setBackgroundResource(R.drawable.button_jushou_bg);
                    holder.imgjiaobiao.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        int size = beans == null ? 0 : beans.size();
        return size;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_name;
        public ImageView imgjiaobiao;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            imgjiaobiao = itemView.findViewById(R.id.imgjiaobiao);
            txt_name.setOnClickListener(listener);
        }

        public void setDatas(DesignCehuaBean.Datas bean) {
            if(key.equals("space_material")){//栏目
                txt_name.setText(CommonUtils.formatStr(bean.getName()) + "");
            }else if(key.equals("luminance_material")){//亮点
                txt_name.setText(CommonUtils.formatStr(bean.getName()) + "");
            }else if(key.equals("style_material")){//风格
                txt_name.setText(CommonUtils.formatStr(bean.getName()) + "");
            }else if(key.equals("area_material")){//面积
                txt_name.setText(CommonUtils.formatStr(bean.getRange()) + "");
            }else if(key.equals("household_material")){//户型
                txt_name.setText(CommonUtils.formatStr(bean.getName()) + "");
            }else if(key.equals("price_material")){//价格
                txt_name.setText(CommonUtils.formatStr(bean.getRange()) + "");
            }
        }

        public View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.txt_name:
                        if (isSingleSelect) {
                            int posi = (int) txt_name.getTag();
                            selected = posi;
                            notifyDataSetChanged();
                        } else {
                            int posi = (int) txt_name.getTag();
                            if (selecteds[posi]) {
                                selecteds[posi] = false;
                            } else {
                                selecteds[posi] = true;
                            }
                            notifyDataSetChanged();
                        }
                        break;
                }
            }
        };
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public boolean[] getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(boolean[] selecteds) {
        this.selecteds = selecteds;
    }
}
