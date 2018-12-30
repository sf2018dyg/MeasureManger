package com.soonfor.measuremanager.upload.adpter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dynamicpicpro.model.UploadGoodsBean;
import com.dynamicpicpro.util.ScreenUnit;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/4/2 0002 13:38
 * 邮箱：suibozhu@139.com
 */
public class GridImgAdapter extends BaseAdapter<GridImgAdapter.ViewHolder, UploadGoodsBean> {

    private Activity mContext;
    List<UploadGoodsBean> img_uri;
    View.OnClickListener selector, preview, delpic;
    int index = 0;

    public GridImgAdapter(Activity context, List<UploadGoodsBean> list, View.OnClickListener selector, View.OnClickListener preview, View.OnClickListener delpic) {
        super(context);
        mContext = context;
        this.img_uri = list;
        this.selector = selector;
        this.preview = preview;
        this.delpic = delpic;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.activity_addstory_img_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(img_uri.get(position), position);
    }

    @Override
    public void notifyDataSetChanged(List<UploadGoodsBean> dataList) {
        this.img_uri = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return img_uri == null ? 0 : img_uri.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView add_IB;
        ImageView delete_IV;
        RelativeLayout fBlank;

        public ViewHolder(View view) {
            super(view);
            add_IB = (ImageView) view.findViewById(R.id.add_IB);
            delete_IV = (ImageView) view.findViewById(R.id.delete_IV);
            fBlank = (RelativeLayout) view.findViewById(R.id.fBlank);

            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory().cacheOnDisc().build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    mContext).defaultDisplayImageOptions(
                    defaultOptions).build();
            ImageLoader.getInstance().init(config);
            ScreenUnit.ScreenMap = ScreenUnit.getScreenSize(mContext, mContext);
            /*WindowManager windowManager = mContext.getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            screen_widthOffset = (display.getWidth() - (3 * ScreenUnit.dip2px(mContext, 2))) / 4;*/
        }

        public void setData(UploadGoodsBean gpBean, final int posit) {
            add_IB.setTag(R.id.imgurls_key, img_uri);
            add_IB.setTag(R.id.imgurls_posi_key, index);
            delete_IV.setTag(R.id.imgurls_key, img_uri);
            delete_IV.setTag(R.id.imgurls_posi_key, index);
            fBlank.setTag(R.id.imgurls_key, img_uri);
            fBlank.setTag(R.id.imgurls_posi_key, index);

            if (img_uri.get(posit) == null) {
                fBlank.setVisibility(View.VISIBLE);
                add_IB.setVisibility(View.GONE);
                delete_IV.setVisibility(View.GONE);
                //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.iv_add_the_pic, holder.add_IB);
                fBlank.setOnClickListener(selector);

            } else {
                fBlank.setVisibility(View.GONE);
                add_IB.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage("file://" + img_uri.get(posit).getUrl(), add_IB);
                delete_IV.setVisibility(View.GONE);
                delete_IV.setOnClickListener(delpic);
                add_IB.setOnClickListener(preview);
            }
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}