package com.soonfor.measuremanager.home.lofting.adapter.immloft;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseAdapter;
import com.soonfor.measuremanager.home.liangchi.activity.DesignSchemeActivity;
import com.soonfor.measuremanager.home.lofting.activity.immloft.LoftLookDetailActivity;
import com.soonfor.measuremanager.home.lofting.activity.immloft.detail.LookSurveyListActivity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.LoftLookDetailBean;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkWallEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.WallPath;
import com.soonfor.measuremanager.mjsdk.SdkCallbackCode;
import com.soonfor.measuremanager.mjsdk.SdkLoftingActivity;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.MyToast;

import java.util.ArrayList;
import java.util.List;

public class LoftLookDetailAdpter extends BaseAdapter<LoftLookDetailAdpter.ViewHolder, LoftLookDetailBean> {

    private static List<LoftLookDetailBean> beans;
    private LoftLookDetailActivity mActivity;
    private String taskNo;
    private String title;//传入的标题
    private ArrayList<MarkWallEntity> lfInfoList;
    private ArrayList<WallPath> wallPaths;
    private String HouseName;//户型名称
    private String customName;//客户姓名
    private String imapth;//户型图路径

    public LoftLookDetailAdpter(Context context, LoftLookDetailActivity mactivity, List<LoftLookDetailBean> beans,
                                String taskNo, ArrayList<MarkWallEntity> lfInfoList, ArrayList<WallPath> wallPaths, String customName, String imapth) {
        super(context);
        mActivity = mactivity;
        this.beans = beans;
        this.taskNo = taskNo;
        this.lfInfoList = lfInfoList;
        this.wallPaths = wallPaths;
        this.customName = customName;
        this.imapth = imapth;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHouseName() {
        return HouseName;
    }

    public void setHouseName(String houseName) {
        HouseName = houseName;
    }

    public ArrayList<WallPath> getWallPaths() {
        return wallPaths;
    }

    public void setWallPaths(ArrayList<WallPath> wallPaths) {
        this.wallPaths = wallPaths;
    }

    @Override
    public void notifyDataSetChanged(List dataList) {
        this.beans = dataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.view_lookdetail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(beans.get(position));
        holder.llfanniu.setTag(position);
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llfanniu;
        public ImageView anniuicon;
        public TextView anniutxt;


        public ViewHolder(View itemView) {
            super(itemView);
            llfanniu = itemView.findViewById(R.id.llfanniu);
            anniuicon = itemView.findViewById(R.id.anniuicon);
            anniutxt = itemView.findViewById(R.id.anniutxt);

            llfanniu.setOnClickListener(listener);
        }

        public void setData(LoftLookDetailBean bean) {
            anniuicon.setBackgroundResource(bean.getIcon());
            anniutxt.setText(bean.getName());
        }

        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posi = (int) v.getTag();
                Bundle b = new Bundle();
                switch (posi) {
                    case 0:
                        if (beans.get(posi).getFobsplanid() == null || beans.get(posi).getFobsplanid().equals("")) {
                            MyToast.showToast(mActivity, "合同号为空，不能进行放样");
                        }else {
                            b.putParcelableArrayList("LIST_DATA", lfInfoList);
                            b.putParcelableArrayList("LOFT_WALLPATHS", wallPaths);
                            b.putString("fy_taskNo", taskNo);
                            b.putString("fy_contractNo", beans.get(posi).getFobsplanid());
                            b.putInt("fy_optionType", 0);
                            b.putString("TITLETEXT", getTitle());
                            mActivity.startNewAct(SdkLoftingActivity.class, b, SdkCallbackCode.OPEN_LOFTING);
                            DataTools.Loft.isEnterSdk = true;
                        }
                        break;
                    case 1:
                        if (beans.get(posi).getFobsplanid() == null || beans.get(posi).getFobsplanid().equals("")) {
                            MyToast.showToast(mActivity, "合同号为空，不能查看平面图");
                        } else {
                            b.putString("fy_taskNo", taskNo);
                            b.putString("fy_contractNo", beans.get(posi).getFobsplanid());
                            b.putInt("fy_optionType", 1);
                            mActivity.startNewAct(SdkLoftingActivity.class, b, SdkCallbackCode.PREVIEW_SURVEY);
                        }
                        break;
                    case 2:
                        if (beans.get(posi).getFobsplanid() == null || beans.get(posi).getFobsplanid().equals("")) {
                                MyToast.showToast(mActivity, "合同号为空，不能查看3D图");
                        } else {
                            b.putString("fy_taskNo", taskNo);
                            b.putString("fy_contractNo", beans.get(posi).getFobsplanid());
                            b.putInt("fy_optionType", 2);
                            mActivity.startNewAct(SdkLoftingActivity.class, b, SdkCallbackCode.PREVIEW_3D);
                        }
                        break;
                    case 3:
                        b.putString("FcTaskNo", beans.get(posi).getFcTaskNo());
                        b.putString("orderNo", beans.get(posi).getOrderNo());
                        b.putString("contractNo", beans.get(posi).getFobsplanid());
                        b.putString("HouseName", getHouseName());
                        mActivity.startNewAct(LookSurveyListActivity.class, b);
                        break;
                    case 4:
                        Bundle dBundle= new Bundle();
                        dBundle.putString("taskNo", beans.get(posi).getFcTaskNo());
                        dBundle.putString("orderNo", beans.get(posi).getOrderNo());
                        Intent intent_d = new Intent(mActivity, DesignSchemeActivity.class);
                        intent_d.putExtra("EntityBundle", dBundle);
                        mActivity.startActivity(intent_d);
                        break;
                    case 5:
                        if (imapth != null && !imapth.equals("")) {
                            // TODO
                            // 在这里进行 http request.网络请求相关操作
                            PermissionsUtil.requestPermission(context, new PermissionListener() {
                                        @Override
                                        public void permissionGranted(@NonNull String[] permissions) {
                                            new ImageUtil.downloadImageAndShareAsyncTask(context).execute("欢迎光临数夫家具软件", customName + "为您分享了一张设计放样图", "http://www.fdatacraft.com", imapth);
                                        }

                                        @Override
                                        public void permissionDenied(@NonNull String[] permissions) {
                                        }
                                    }, Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

                        } else {
                            MyToast.showToast(context, "暂无可分享的放样图");
                        }
                        break;
                }
            }
        };
    }

}