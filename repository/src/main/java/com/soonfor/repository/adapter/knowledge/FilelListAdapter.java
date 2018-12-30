package com.soonfor.repository.adapter.knowledge;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.soonfor.repository.R;
import com.soonfor.repository.base.RepBaseAdapter;
import com.soonfor.repository.http.api.RepRequest;
import com.soonfor.repository.http.httptools.RepAsyncUtils;
import com.soonfor.repository.model.knowledge.FileBean;
import com.soonfor.repository.tools.FileUtils;
import com.soonfor.repository.tools.MyToast;

import java.util.List;

import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-DingYG on 2018/7/20 13:50
 * 邮箱：dingyg012655@126.com
 * 回复评论的回复
 */
public class FilelListAdapter extends RepBaseAdapter<FilelListAdapter.ViewHolder, FileBean> {

    private List<FileBean> list;
    static Context mContext;

    public FilelListAdapter(Context context, List<FileBean> list) {
        super(context);
        mContext = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adpter_filelist, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
        holder.rlfFileItem.setTag(R.id.file_position, position);
        holder.rlfFileItem.setTag(R.id.file_viewholder, holder);
        holder.rlfFileItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PermissionsUtil.requestPermission(mContext, new PermissionListener() {
                            @Override
                            public void permissionGranted(@NonNull String[] permission) {
                                int pos = (int) v.getTag(R.id.file_position);
                                RepRequest.openFileByUrl(mContext, list.get(pos).getFileUrl() + "/" + list.get(pos).getFileName(), new RepAsyncUtils.AsyncCallback() {
                                    @Override
                                    public void success(int requestCode, String data) {
                                        // file 即为文件数据，文件保存在指定目录
                                        try {
                                            if (data != null) {
                                                FileUtils.openFile(mContext, data);
                                            } else {
                                                MyToast.showFailToast(mContext, "请求文件失败");
                                            }
                                        } catch (Exception e) {
                                            NLogger.e(e.getMessage() + "");
                                        }
                                    }

                                    @Override
                                    public void fail(int requestCode, int statusCode, String msg) {

                                    }
                                });
                            }

                            @Override
                            public void permissionDenied(@NonNull String[] permission) {

                            }
                        },
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);

            }
        });

    }

    @Override
    public void notifyDataSetChanged(List<FileBean> dataList) {
        this.list = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgfFile;
        TextView tvfFileName;
        TextView tvfFileSize;
        RelativeLayout rlfFileItem;

        public ViewHolder(View itemView) {
            super(itemView);
            imgfFile = itemView.findViewById(R.id.imgfFile);
            tvfFileName = itemView.findViewById(R.id.tvfFileName);
            tvfFileSize = itemView.findViewById(R.id.tvfFileSize);
            rlfFileItem = itemView.findViewById(R.id.rlfFileItem);
        }

        public void setData(FileBean gpBean) {
            tvfFileName.setText(gpBean.getFileName());
            double size = gpBean.getFileSize() / 1024;
            if (size > 0.00) {
                tvfFileSize.setText(String.format("%.2f", size));
            }
            String format = gpBean.getFileFormat();
            if (!format.equals("")) {
                if (format.equals("doc")) {
                    imgfFile.setImageResource(R.mipmap.doc);
                } else if (format.equals("docx")) {
                    imgfFile.setImageResource(R.mipmap.docx);
                } else if (format.equals("txt")) {
                    imgfFile.setImageResource(R.mipmap.txt);
                } else if (format.equals("gif")) {
                    imgfFile.setImageResource(R.mipmap.gif);
                } else if (format.equals("jpg")) {
                    imgfFile.setImageResource(R.mipmap.jpg);
                } else if (format.equals("png")) {
                    imgfFile.setImageResource(R.mipmap.png);
                } else if (format.equals("pdf")) {
                    imgfFile.setImageResource(R.mipmap.pdf);
                } else if (format.equals("cad")) {
                    imgfFile.setImageResource(R.mipmap.cad);
                } else if (format.equals("xls")) {
                    imgfFile.setImageResource(R.mipmap.xls);
                } else if (format.equals("xlsx")) {
                    imgfFile.setImageResource(R.mipmap.xlsx);
                } else if (format.equals("rar")) {
                    imgfFile.setImageResource(R.mipmap.rar);
                } else if (format.equals("zip")) {
                    imgfFile.setImageResource(R.mipmap.zip);
                } else if (format.equals("tiff")) {
                    imgfFile.setImageResource(R.mipmap.tiff);
                } else if (format.equals("exe")) {
                    imgfFile.setImageResource(R.mipmap.exe);
                } else if (format.equals("dmg")) {
                    imgfFile.setImageResource(R.mipmap.dmg);
                }
            }
        }
    }
}
