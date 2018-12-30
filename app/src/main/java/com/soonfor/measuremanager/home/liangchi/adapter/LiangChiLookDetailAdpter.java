package com.soonfor.measuremanager.home.liangchi.adapter;

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
import com.soonfor.measuremanager.home.liangchi.activity.LiangChiLookDetailActivity;
import com.soonfor.measuremanager.home.liangchi.activity.SurveyListActivity;
import com.soonfor.measuremanager.home.liangchi.model.bean.LiangChiLookDetailBean;
import com.soonfor.measuremanager.mjsdk.SdkCallbackCode;
import com.soonfor.measuremanager.mjsdk.SurveyActivity;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.view.showBroadView;

import java.util.List;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/30 9:12
 * 邮箱：suibozhu@139.com
 */
public class LiangChiLookDetailAdpter extends BaseAdapter<LiangChiLookDetailAdpter.ViewHolder, LiangChiLookDetailBean> {

    private static List<LiangChiLookDetailBean> beans;
    private static Context mContext;
    static LiangChiLookDetailActivity mActivity;
    String orderNo;
    String taskType;
    private String customName;//客户姓名
    private String HouseName;//户型名称

    public LiangChiLookDetailAdpter(Context context, LiangChiLookDetailActivity mactivity, List<LiangChiLookDetailBean> beans, String taskType, String orderNo, String customName) {
        super(context);
        mContext = context;
        mActivity = mactivity;
        this.beans = beans;
        this.taskType = taskType;
        this.orderNo = orderNo;
        this.customName = customName;
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
        if (beans.get(position).isActivat()) {
            holder.llfanniu.setBackgroundResource(R.drawable.bg_round_corner);
        } else {
            holder.llfanniu.setBackgroundResource(R.drawable.bg_round_corner2);
        }
    }

    public String getHouseName() {
        return HouseName;
    }

    public void setHouseName(String houseName) {
        HouseName = houseName;
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

        public void setData(LiangChiLookDetailBean bean) {
            anniuicon.setBackgroundResource(bean.getIcon());
            anniutxt.setText(CommonUtils.formatStr(bean.getName()));
        }

        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posi = (int) v.getTag();
                Bundle b = new Bundle();
                switch (posi) {
                    case 0:
                        if (beans.get(posi).isActivat()) {
                            b.putParcelable("LiangChiLookDetailBean", beans.get(posi));
                            b.putString("orderNo",orderNo);
                            b.putInt("optionType", 0);
                            mActivity.startNewAct(mActivity, SurveyActivity.class, b, beans.get(posi).isNewhouse() ? SdkCallbackCode.CREATE_HOUSE : SdkCallbackCode.OPEN_HOUSE);
                            //mActivity.startNewAct(SurveyActivity.class, b, beans.get(posi).isNewhouse() ? SdkCallbackCode.CREATE_HOUSE : SdkCallbackCode.OPEN_HOUSE);
                        } else {
                            MyToast.showToast(mContext, "测量已完成,无法继续测量");
                        }
                        break;
                    case 1:
                        if (!beans.get(posi).isNewhouse()) {
                            b.putParcelable("LiangChiLookDetailBean", beans.get(posi));
                            b.putString("orderNo",orderNo);
                            b.putInt("optionType", 1);
                            mActivity.startNewAct(mActivity, SurveyActivity.class, b, SdkCallbackCode.PREVIEW_SURVEY);
                            //mActivity.startNewAct(SurveyActivity.class, b, SdkCallbackCode.PREVIEW_SURVEY);
                        } else {
                            MyToast.showToast(mContext, "请先测量，否则无法查看");
                        }
                        break;
                   /* case 2:
                        b.putBoolean("newhouse", beans.get(posi).isNewhouse());
                        b.putString("contractNo", beans.get(posi).getOrderNo());
                        mActivity.startNewAct(SurveyActivity.class, b, beans.get(posi).isNewhouse() ? SdkCallbackCode.CREATE_HOUSE : SdkCallbackCode.OPEN_HOUSE);
                        break;*/
                    case 2:
                        if (!beans.get(posi).isNewhouse()) {
                            b.putParcelable("LiangChiLookDetailBean", beans.get(posi));
                            b.putString("orderNo",orderNo);
                            b.putInt("optionType", 2);
                            mActivity.startNewAct(mActivity, SurveyActivity.class, b, SdkCallbackCode.PREVIEW_3D);
                            //mActivity.startNewAct(SurveyActivity.class, b, SdkCallbackCode.PREVIEW_3D);
                        } else {
                            MyToast.showToast(mContext, "请先测量，否则无法查看");
                        }
                        break;
                    /*case 4:
                        b.putBoolean("newhouse", beans.get(posi).isNewhouse());
                        b.putString("contractNo", beans.get(posi).getOrderNo());
                        mActivity.startNewAct(SurveyActivity.class, b, beans.get(posi).isNewhouse() ? SdkCallbackCode.CREATE_HOUSE : SdkCallbackCode.OPEN_HOUSE);
                        break;*/
                    case 3:
                        Bundle EntityBundle = new Bundle();
                        EntityBundle.putString("taskNo", beans.get(posi).getTaskNo());
                        EntityBundle.putString("taskType", taskType);
                        EntityBundle.putString("orderNo", orderNo);
                        EntityBundle.putString("fimpath",beans.get(posi).getFimgpath());
                        EntityBundle.putString("contractNo",beans.get(posi).getContractNo());
                        EntityBundle.putBoolean("isFuChi",beans.get(posi).isFuChiHouse());
                        EntityBundle.putString("HouseName",getHouseName());
                        Intent intent = new Intent(mContext, SurveyListActivity.class);
                        intent.putExtra("EntityBundle", EntityBundle);
                        mActivity.startActivity(intent);
                        break;
                    case 4:
                        EntityBundle = new Bundle();
                        EntityBundle.putString("taskNo", beans.get(posi).getTaskNo());
                        EntityBundle.putString("orderNo", orderNo);
                        intent = new Intent(mContext, DesignSchemeActivity.class);
                        intent.putExtra("EntityBundle", EntityBundle);
                        mActivity.startActivity(intent);
                        break;
                    /*case 5:
                        Hawk.put("ImageInfo", datas);
                        mActivity.startNewAct(ScenePictureActivity.class);
                        break;*/
                    case 5:
                        if (beans.get(posi).getFimgpath() != null && !beans.get(posi).getFimgpath().equals("")) {
                            // TODO
                            // 在这里进行 http request.网络请求相关操作
                            PermissionsUtil.requestPermission(context, new PermissionListener() {
                                        @Override
                                        public void permissionGranted(@NonNull String[] permissions) {
                                            new ImageUtil.downloadImageAndShareAsyncTask(context).execute("欢迎光临数夫家具软件", customName + "为您分享了一张设计测量图", "http://www.fdatacraft.com", beans.get(posi).getFimgpath());
                                        }

                                        @Override
                                        public void permissionDenied(@NonNull String[] permissions) {
                                        }
                                    }, Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

                        } else {
                            MyToast.showToast(context, "暂无可分享的测量图");
                        }
                         break;
                }
            }
        };
    }
}