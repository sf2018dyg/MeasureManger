package com.soonfor.measuremanager.home.othertask.adapter;

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
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.bases.HeadBean;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.FileBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.repository.tools.FileUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-DingYG on 2018-11-12 15:47
 * 邮箱：dingyg012655@126.com
 */
public class FileAdapter extends BaseAdapter<FileAdapter.ViewHolder, FileBean> {

    private List<FileBean> list;
    static Context mContext;

    public FileAdapter(Context context, List<FileBean> list) {
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
                                Request.openFileByUrl(mContext, list.get(pos).getAttachUrl() + "/" + list.get(pos).getAttachName(), new AsyncUtils.AsyncCallback() {
                                    @Override
                                    public void success(int requestCode, JSONObject object) {
                                        // file 即为文件数据，文件保存在指定目录
                                        try {
                                            HeadBean headBean = JsonUtils.getHeadBean(object.toString());
                                            if (headBean != null) {
                                                if (headBean.getMsgcode() == 0 || headBean.isSuccess())
                                                    FileUtils.openFile(mContext, headBean.getData());
                                                else
                                                    MyToast.showFailToast(mContext, "文件无法打开");
                                            } else {
                                                MyToast.showFailToast(mContext, "文件无法打开");
                                            }
                                        } catch (Exception e) {
                                            NLogger.e("打开文件报错：" + e.getMessage() + "");
                                        }
                                    }

                                    @Override
                                    public void fail(int requestCode, int statusCode, String errorMsg) {
                                        MyToast.showFailToast(mContext, "文件无法打开");
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
            tvfFileName.setText(gpBean.getAttachName());
//            double size = gpBean.getFileSize() / 1024;
//            if (size > 0.00) {
//                tvfFileSize.setText(String.format("%.2f", size));
//            }
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