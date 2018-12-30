package com.soonfor.evaluate.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.evaluate.bean.UploadPhoto;
import com.soonfor.evaluate.view.UploadImageView;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.List;


/**
 * Created by DingYg on 2018-02-09.
 */

public class ImagesAdapter extends BaseAdapter<ImagesAdapter.ViewHolder, UploadPhoto> {

    private List<UploadPhoto> beans;
    private View.OnClickListener imgListener;
    private GridLayoutManager manager;
    private RecyclerView recyclerView;

    public List<UploadPhoto> getBeans() {
        return beans;
    }

    public void setBeans(List<UploadPhoto> beans) {
        this.beans = beans;
    }

    public ImagesAdapter(Context context, List<UploadPhoto> imgList, GridLayoutManager manager,
                         RecyclerView recyclerView, View.OnClickListener imgListener) {
        super(context);
        this.beans = imgList;
        this.manager = manager;
        this.recyclerView = recyclerView;
        this.imgListener = imgListener;
    }

    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImagesAdapter.ViewHolder(mInflater.inflate(R.layout.adapter_lofting_imm_addimages, parent, false));
    }

    @Override
    public void onBindViewHolder(final ImagesAdapter.ViewHolder holder, int position) {
        String path = beans.get(position).getLocalMedia().getCompressPath();
        holder.imgfItem.setTag(R.id.item_id, position);
        holder.imgfItem.setOnClickListener(imgListener);
        if (path.toLowerCase().contains("http") || path.toLowerCase().contains("https")) {
            //加载网络图片
            String localPath = beans.get(position).getLocalUrl();
            if(!localPath.equals("")){
                Picasso.with(context).load(new File(localPath)).into(holder.imgfItem);
            }else {
                ImageUtil.loadImage(context, path, holder.imgfItem);
            }
            holder.imgfDelete.setVisibility(View.VISIBLE);
            holder.imgfDelete.setTag(R.id.item_id_d, position);
            holder.imgfDelete.setOnClickListener(imgListener);
        } else {
            holder.tvfProgress.setVisibility(View.VISIBLE);
            UploadImage(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return beans == null ? 0 : beans.size();
    }


    @Override
    public void notifyDataSetChanged(List<UploadPhoto> dataList) {
        beans = dataList;
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements Serializable {

        //public RelativeLayout rlfFirstItem;
        public FrameLayout flImage;
        public ImageView imgfItem;
        public TextView tvfProgress;
        public ImageView imgfDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            // rlfFirstItem = itemView.findViewById(R.id.rlfFirstItem);
            flImage = itemView.findViewById(R.id.flImage);
            imgfItem = itemView.findViewById(R.id.imgfItem);
            tvfProgress = itemView.findViewById(R.id.tvfProgress);
            imgfDelete = itemView.findViewById(R.id.imgfDelete);
        }
    }

    /**
     * 重新上传成功后刷新
     *
     * @param position
     * @param manager
     * @param mRecyclerView
     */
    public void RefreshItemImage(int position, GridLayoutManager manager, RecyclerView mRecyclerView) {
        if (manager == null || mRecyclerView == null) {
            return;
        }
        int firstItemPosition = manager.findFirstVisibleItemPosition();
        if (position - firstItemPosition >= 0) {
            //得到要更新的item的view
            View view = mRecyclerView.getChildAt(position - firstItemPosition + 1);
            if (null != mRecyclerView.getChildViewHolder(view)) {
                ViewHolder vh = (ViewHolder) mRecyclerView.getChildViewHolder(view);
                vh.tvfProgress.setVisibility(View.GONE);
                vh.imgfDelete.setVisibility(View.VISIBLE);
            }
        }
    }

    private void refreshFileItem(int position, final ViewHolder vh) {
        if (beans.size() > position) {
            vh.tvfProgress.setText("上传失败\n点击\n重新上传");
            vh.tvfProgress.setEnabled(true);
            vh.tvfProgress.setTag(R.id.item_id_p, position);
            vh.tvfProgress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = (int) v.getTag(R.id.item_id_p);
                    UploadImage(vh, index);
                }
            });
            vh.imgfDelete.setVisibility(View.VISIBLE);
            vh.imgfDelete.setTag(R.id.item_id_d, beans.get(position).getId());
            vh.imgfDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String _id = (String) v.getTag(R.id.item_id);
                    for (int i = 0; i < UploadImageView.imgList.size(); i++) {
                        if (UploadImageView.imgList.get(i).getId().equals(_id)) {
                            UploadImageView.imgList.remove(i);
                            break;
                        }
                    }
                    notifyDataSetChanged(UploadImageView.imgList);
                }
            });
        }
    }

    private void UploadImage(ViewHolder vh, int position) {
        UploadHandler upHandler = new UploadHandler();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        bundle.putSerializable("VIEWHOLDER", vh);
        Message msg = new Message();
        //msg.arg1 = type;
        msg.setData(bundle);
        upHandler.handleMessage(msg);
    }

    class UploadHandler extends Handler {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            try {
                Bundle bundle = msg.getData();
                final int index = bundle.getInt("POSITION");
                final ViewHolder VH = (ViewHolder) bundle.getSerializable("VIEWHOLDER");
                final String localPath = beans.get(index).getLocalMedia().getCompressPath();
                final RequestParams params = new RequestParams();
                params.addBodyParameter("imgFile", new File(localPath));
                params.addBodyParameter("dir", "image");
                ImageUtil.getFjAddress(context, 1, new ImageUtil.fjInterface() {
                    @Override
                    public void setAddress(String address) {
                        if (address != null && !address.equals("")) {
                            String uploadUrl = "http://" + address + UserInfo.UPLOAD_FILE_TO_CRM;
                            HttpUtils httpUtils = new HttpUtils();
                            httpUtils.configTimeout(15 * 1000);
                            httpUtils.configCurrentHttpCacheExpiry(0);
                            httpUtils.send(HttpRequest.HttpMethod.POST, uploadUrl, params,
                                    new RequestCallBack<String>() {
                                        @Override
                                        public void onStart() {
                                            VH.imgfDelete.setVisibility(View.GONE);
                                            VH.tvfProgress.setEnabled(false);
                                        }

                                        @Override
                                        public void onLoading(long total, long current, boolean isUploading) {
                                            //这里可以增加loading，或进度条开始
                                            try {
                                                int upProgress = (int) ((current / total) * 100);
                                                if (upProgress >=0 && upProgress <= 100) {
                                                    VH.tvfProgress.setText(upProgress + "%");
                                                }
                                            } catch (Exception ee) {
                                            }
                                        }

                                        @Override
                                        public void onFailure(HttpException arg0, String arg1) {
                                            //请求失败，可做异常提示处理和loading、进度条消失
                                            refreshFileItem(index, VH);
                                        }

                                        @Override
                                        public void onSuccess(ResponseInfo<String> arg0) {
                                            //请求成功，做相应处理和loading、进度条消失
                                            try {
                                                if (arg0.result != null) {
                                                    JSONObject jo = new JSONObject(arg0.result);
                                                    if (jo.getString("error").equals("0")) {
                                                        String picPaht = CommonUtils.formatStr(jo.getString("filepath"));
                                                        UploadImageView.imgList.get(index).setUrl(picPaht);
                                                        UploadImageView.imgList.get(index).getLocalMedia().setCompressPath("http://" + Hawk.get(SoonforApplication.ServerAdr_fj, "") + UserInfo.DOWNLOAD_FILE + picPaht);
                                                        UploadImageView.imgList.get(index).getLocalMedia().setPath("http://" + Hawk.get(SoonforApplication.ServerAdr_fj, "") + UserInfo.DOWNLOAD_FILE + picPaht);
                                                        VH.tvfProgress.setVisibility(View.GONE);
                                                        VH.imgfDelete.setVisibility(View.VISIBLE);
                                                        notifyItemChanged(index);
                                                    } else {
                                                        refreshFileItem(index, VH);
                                                    }
                                                } else {
                                                    refreshFileItem(index, VH);
                                                }
                                            } catch (Exception ee) {
                                                refreshFileItem(index, VH);
                                            }
                                        }
                                    });
                        }
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
