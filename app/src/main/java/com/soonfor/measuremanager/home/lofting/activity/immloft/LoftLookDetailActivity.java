package com.soonfor.measuremanager.home.lofting.activity.immloft;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.lofting.adapter.immloft.LoftLookDetailAdpter;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.LoftItemBean;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.LoftLookDetailBean;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkWallEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.WallPath;
import com.soonfor.measuremanager.home.lofting.presenter.LoftLookDetailPresenter;
import com.soonfor.measuremanager.home.lofting.presenter.immloft.ComCheckIsLoftedPresenter;
import com.soonfor.measuremanager.home.lofting.view.ILoftLookDetailView;
import com.soonfor.measuremanager.home.lofting.view.immloft.IComCheckIsLoftedView;
import com.soonfor.measuremanager.mjsdk.SdkCallbackCode;
import com.soonfor.measuremanager.tools.CustomDialog;
import com.soonfor.measuremanager.tools.DataTools;
import com.soonfor.measuremanager.tools.DateTool;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.dialog.normal.NormalDialog;
import com.soonfor.measuremanager.view.dialog.normal.OnBtnClickL;
import com.soonfor.measuremanager.view.previewImage.ImageVAty;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by DingYg on 2018-03-06.
 * 放样详情
 */

public class LoftLookDetailActivity extends BaseActivity<LoftLookDetailPresenter> implements ILoftLookDetailView, IComCheckIsLoftedView {

    Context mContext;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvfsubmit)
    TextView tvfsubmit;
    @BindView(R.id.imgpath)
    ImageView imgpath;
    @BindView(R.id.tvfcustom)
    TextView tvfcustom;
    @BindView(R.id.tvfTaskType)
    TextView tvfStatus;
    @BindView(R.id.tvfadress)
    TextView tvfadress;
    @BindView(R.id.tvfloulevel)
    TextView tvfloulevel;
    @BindView(R.id.tvfmodifytime)
    TextView tvfmodifytime;
    @BindView(R.id.glControl)
    RecyclerView glControl;
    GridLayoutManager manager;
    LoftLookDetailAdpter adpter;
    List<LoftLookDetailBean> beans;
    LoftItemBean loftBean;
    String fcTaskNo;//复尺任务号
    String sContractNo;//合同号
    private ArrayList<MarkWallEntity> lfInfoList = null;
    private ArrayList<WallPath> wpathList = null;
    CustomDialog ddialog;
    NormalDialog ndialog;
    ComCheckIsLoftedPresenter ccPersenter;
    boolean isNeedReCreate = false;
    private boolean isLofted = false;//是否放样完成

    @Override
    protected void initPresenter() {
        presenter = new LoftLookDetailPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_lofting_look_detail;
    }

    @Override
    protected void initViews() {
        mContext = LoftLookDetailActivity.this;
        ddialog = CustomDialog.getInstance();
        manager = new GridLayoutManager(mContext, 3);
        glControl.setLayoutManager(manager);
        ccPersenter = new ComCheckIsLoftedPresenter(this);
        loftBean = getIntent().getParcelableExtra(Tokens.Lofing.SKIP_IMMT_TO_LOFTDETAIL);
        if (loftBean != null) {
            //获取所有的墙面放样图片清单
            ccPersenter.getWallPaths(mContext,loftBean.getFobsplanid(),"");
            //根据放样任务号获取量尺、复尺任务号
            presenter.getRemeasureTaskNoByOrderNo(loftBean.getForderNo(), loftBean.getTaskNo());
            imageLoading(loftBean.getFimgpath(), imgpath, R.mipmap.zanuw);
            if (!loftBean.getCustomBulid().equals("")) {
                tvfcustom.setText(loftBean.getCustomBulid() + "-" + loftBean.getFcname());
            } else {
                tvfcustom.setText(loftBean.getFcname());
            }
            switch (Integer.parseInt(loftBean.getFstatus())) {
                case 0:
                    DataTools.Loft.statusCode = 0;
                    tvfsubmit.setVisibility(View.VISIBLE);
                    tvfStatus.setText("待放样");
                    tvfStatus.setBackgroundResource(R.color.red);
                    break;
                case 1:
                    DataTools.Loft.statusCode = 1;
                    tvfsubmit.setVisibility(View.VISIBLE);
                    tvfStatus.setText("放样中");
                    tvfStatus.setBackgroundResource(R.color.orange);
                    break;
                case 2:
                    DataTools.Loft.statusCode = 2;
                    tvfsubmit.setVisibility(View.GONE);
                    tvfStatus.setText("放样完成");
                    tvfStatus.setBackgroundResource(R.color.blue);
                    break;
                default:
                    DataTools.Loft.statusCode = -1;
                    tvfsubmit.setVisibility(View.VISIBLE);
                    tvfStatus.setText("状态未知");
                    tvfStatus.setBackgroundResource(R.color.red);
            }
            tvfadress.setText(loftBean.getCustomAddress());
            tvfloulevel.setText(loftBean.getFmeafloor());
            tvfmodifytime.setText(DateTool.getTimeTimestamp(loftBean.getFcdate(), "yyyy-MM-dd HH:mm") + "");
        }
        isNeedReCreate = false;
    }

    //获取户型所有墙面清单
    public void getAllWallList(String fcTaskno) {
        this.fcTaskNo = fcTaskno;
        if (!fcTaskno.equals("")) {
            //获取户型所有墙面清单
            sContractNo = fcTaskNo + "-" + loftBean.getFobsplanid();
            presenter.getFyList(loftBean.getTaskNo(), loftBean.getFobsplanid());
        } else {
            initview();
        }
    }

    //设置网格数据
    public void initview() {
        try {
            if(sContractNo!=null && lfInfoList!=null) {
                closeLoadingDialog();
                adpter = new LoftLookDetailAdpter(mContext, LoftLookDetailActivity.this, initIcons(loftBean.getForderNo(), fcTaskNo, sContractNo),
                        loftBean.getTaskNo(), lfInfoList, wpathList, loftBean.getFcname(), loftBean.getFimgpath());
                adpter.setTitle(tvfcustom.getText().toString());
                adpter.setHouseName(loftBean.getFmeafloor());
                adpter.setWallPaths(wpathList);
                glControl.setAdapter(adpter);
            }
        }catch (Exception e){}

    }

    private List<LoftLookDetailBean> initIcons(String orderNo, String fcTaskNo, String Fobsplanid) {
        beans = new ArrayList<LoftLookDetailBean>();
        beans.add(new LoftLookDetailBean("开始放样", R.mipmap.icn_liangchi01, orderNo, fcTaskNo, Fobsplanid));
        beans.add(new LoftLookDetailBean("查看平面图", R.mipmap.icn_liangchi02, orderNo, fcTaskNo, Fobsplanid));
        //beans.add(new LiangChiLookDetailBean("查看立面图", R.mipmap.icn_liangchi03, isNew, orderNo));
        beans.add(new LoftLookDetailBean("查看3D模型", R.mipmap.icn_liangchi04, orderNo, fcTaskNo, Fobsplanid));
        //beans.add(new LiangChiLookDetailBean("查看平面布局", R.mipmap.icn_liangchi05, isNew, orderNo));
        beans.add(new LoftLookDetailBean("查看测量清单", R.mipmap.icn_liangchi06, orderNo, fcTaskNo, Fobsplanid));
        beans.add(new LoftLookDetailBean("设计方案", R.mipmap.icn_liangchi07, orderNo, fcTaskNo, Fobsplanid));
        //beans.add(new LoftLookDetailBean("现场照片", R.mipmap.icn_liangchi08, orderNo, Fobsplanid));
        beans.add(new LoftLookDetailBean("分享", R.mipmap.icn_liangchi09, orderNo, fcTaskNo, Fobsplanid));

        return beans;
    }

    /**
     * 解析放样清单数据
     */
    public void setListdata(String data) {
        if (data != null) {
            lfInfoList = new ArrayList<>();
            try {
                JSONArray ja = new JSONArray(data);
                if (ja != null && ja.length() > 0) {
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        MarkWallEntity bean = new Gson().fromJson(jo.toString(), MarkWallEntity.class);
                        bean.setShowing(false);
                        lfInfoList.add(bean);
                    }
                }
            } catch (Exception e) {
            }
        }
        initview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedReCreate) {
            isNeedReCreate = false;
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } else {
            if (DataTools.Loft.isEnterSdk && DataTools.Loft.statusCode == 1) {
                tvfStatus.setText("放样中");
                tvfStatus.setBackgroundResource(R.color.orange);
            }
        }
    }

    @OnClick({R.id.imgBack, R.id.tvfsubmit, R.id.imgpath})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                inFinish();
                break;
            case R.id.tvfsubmit:
                if (DataTools.Loft.isEnterSdk) {//进入过放样界面
                    if (DataTools.Loft.statusCode == 0)
                        MyToast.showToast(mContext, "尚未开始放样或放样数据未保存，不可提交放样状态！");
                    else if (ccPersenter.IsLofted(lfInfoList))
                        allIsLofted();
                    else
                        MyToast.showFailToast(mContext, "请将墙面全部放样完成后，再点击提交！");
                } else {
                    if (ccPersenter.IsLofted(lfInfoList))
                        allIsLofted();
                    else if (DataTools.Loft.statusCode == 2) {
                        MyToast.showToast(mContext, "已全部放样完成，无需提交放样状态！");
                    } else
                        ccPersenter.getWalls(mContext, loftBean.getTaskNo(), loftBean.getFobsplanid(), 2);
                }
                break;
            case R.id.imgpath:
                ArrayList<String> pathList = new ArrayList<>();
                pathList.add(loftBean.getFimgpath());
                Intent intent = new Intent();
                intent.putExtra("ISBASEHOUSETYPE", false);
                intent.putExtra("position", 0);
                intent.putStringArrayListExtra("images", pathList);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(mContext, ImageVAty.class);
                mContext.startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SdkCallbackCode.OPEN_LOFTING) {
            isNeedReCreate = false;
            if (resultCode == Activity.RESULT_OK && data != null) {
                ArrayList<MarkWallEntity> wallEntities = data.getParcelableArrayListExtra("NEW_WALLLIST");
                if (wallEntities != null && wallEntities.size() > 0) {
                    lfInfoList = wallEntities;
                    initview();
                }
            }
        } else if (resultCode == SdkCallbackCode.PREVIEW_SURVEY) {

        } else if (resultCode == SdkCallbackCode.PREVIEW_3D) {

        } else if (resultCode == 0) {
            adpter.notifyDataSetChanged(beans);
        }


    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    /**
     * 更新视图
     */
    public void showMsg(String msg) {
        MyToast.showToast(mContext, msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            inFinish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void inFinish() {
        finish();
    }

    @Override
    public void showLoadingDialog() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void closeLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void setGetData(boolean isSuccess, ArrayList<MarkWallEntity> markWallEntityList) {

    }

    @Override
    public void finishDispose(boolean isLofted, ArrayList<MarkWallEntity> markWallEntities) {
        if (markWallEntities != null && markWallEntities.size() > 0)
            lfInfoList = markWallEntities;
        if (isLofted) {//已全部放样完成
            allIsLofted();
        } else {
            MyToast.showFailToast(mContext, "请将墙面全部放样完成后，再点击提交！");
        }
    }

    @Override
    public void finishSumbit(boolean isSuboted, String msg) {
        if (isSuboted) {//提交成功
            finish();
        } else {
            MyToast.showFailToast(mContext, msg);
        }
    }

    @Override
    public void setGetWallPaths(boolean isSuccess, ArrayList<WallPath> wallPaths) {
        if(isSuccess){
            this.wpathList = wallPaths;
        }else {
            this.wpathList = null;
        }
        if(adpter!=null) {
            closeLoadingDialog();
            adpter.setWallPaths(wallPaths);
            adpter.notifyDataSetChanged();
        }else {
            initview();
        }
    }

    public void allIsLofted() {
        ndialog = ddialog.getNormalDialog_Red(mContext, "提示", "确认即视为正式放样完成，请谨慎确认是否提交?",
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick(View view) {
                        ndialog.dismiss();
                        //提交接口
                        ccPersenter.setTaskStatus(mContext, "2", loftBean.getTaskNo(), loftBean.getFobsplanid());
                    }
                });
        ndialog.show();
        isNeedReCreate = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageUtil.deleteDir("ShareImage");
    }
}
