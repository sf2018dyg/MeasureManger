package com.soonfor.measuremanager.home.lofting.fragment.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.home.lofting.adapter.detail.MarkInfoTabAdapter;
import com.soonfor.measuremanager.home.lofting.adapter.detail.MarkInfoWallAdpter;
import com.soonfor.measuremanager.home.lofting.model.bean.LoftingMainBean;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Mark.MarkDrawing;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Mark.MarkResultEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.immloft.mj_sdk.MarkWallEntity;
import com.soonfor.measuremanager.home.lofting.presenter.detail.LoftingInfoPresenter;
import com.soonfor.measuremanager.home.lofting.view.detail.ILoftingInfoView;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.RoundJiaoImageView;
import com.soonfor.measuremanager.view.previewImage.ImageVAty;
import com.soonfor.measuremanager.view.stepview.HorizontalStepView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DingYg on 2018-02-01.
 * 放样信息
 */

public class LoftingInfoFragment extends BaseFragment<LoftingInfoPresenter> implements ILoftingInfoView {

    Unbinder unbinder;
    Activity mActivity;
    @BindView(R.id.tvfWallName)
    TextView tvfWallName;//墙面名称
    @BindView(R.id.imgpath)
    RoundJiaoImageView imgpath;//墙面放样图
    @BindView(R.id.rlfLast)
    RelativeLayout rlfLast;
    @BindView(R.id.rlfNext)
    RelativeLayout rlfNext;
    @BindView(R.id.mRV_houseType)
    RecyclerView mRV_houseType;//展示户型横向列表
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;//展示墙面数据
    @BindView(R.id.lclistError)
    TextView lclistError;//无清单数据时显示
    private int picIndex = 0;
    private MarkInfoWallAdpter wallAdapter;
    private MarkInfoTabAdapter tabAdapter;
    private LinearLayoutManager manager1;
    private GridLayoutManager manager2;
    private MarkResultEntity result;
    List<MarkResultEntity> resultEntities;
    List<MarkDrawing> drawingList;//放样图
    List<MarkWallEntity> wallList;//墙面信息集合

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_lofting_detail_loftinfo;
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    protected void initPresenter() {
        presenter = new LoftingInfoPresenter(this);
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {
        manager1 = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        manager2 = new GridLayoutManager(mActivity, 1);
        tabAdapter = new MarkInfoTabAdapter(mActivity, null, presenter);
        mRV_houseType.setLayoutManager(manager1);
        mRV_houseType.setAdapter(tabAdapter);
        wallAdapter = new MarkInfoWallAdpter(mActivity, wallList);
        mRecyclerView.setLayoutManager(manager2);
        mRecyclerView.setAdapter(wallAdapter);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        LoftingMainBean task = getArguments().getParcelable(Tokens.Lofing.ITEMSKIPDETAIL_ITEM);
        if (task != null) {
            //1-待接收/2-待上门确认/3-待放样/4-放样完成）
            switch (task.getStatus()) {
                case 1:
                case 2:
                    showNoDataHint("尚未放样，暂无放样清单");
                    break;
                case 3:
                case 4:
                    presenter.getData(task.getTaskNo(), "mark", task.getOrderNo());
                    break;
            }
        }
    }

    @Override
    public void showDataToView(String returnJson) {
        super.showDataToView(returnJson);
        try {
            String markinfos = new JSONObject(returnJson).getString("markInfos");
            if (markinfos != null && !markinfos.equals("")) {
                resultEntities = new ArrayList<>();
                JSONArray ja = new JSONArray(markinfos);
                if (ja != null && ja.length() > 0) {
                    for (int i = 0; i < ja.length(); i++) {
                        MarkResultEntity resultEntity = new Gson().fromJson(ja.getJSONObject(i).toString(), MarkResultEntity.class);
                        if (i == 0) {
                            resultEntity.setShowing(false);
                        } else {
                            resultEntity.setShowing(true);
                        }
                        resultEntities.add(resultEntity);
                    }
                }
                if (resultEntities.size() > 0) {
                    tabAdapter.notifyDataSetChanged(resultEntities);
                    initFragment(resultEntities.get(0));
                } else {
                    showNoDataHint("未获取到放样信息");
                }
            } else {
                showNoDataHint("请求出错啦");
            }
        } catch (Exception e) {
        }
    }


    // Actionbar
    public void initFragment(final MarkResultEntity result) {
        picIndex = 0;
        if (result != null) {
            this.result = result;
            //初始化放样图
            drawingList = result.getMarkDrawing();
            if (drawingList.size() == 1) {
                setImageViewVisibility(0, drawingList, true);
            } else {
                setImageViewVisibility(0, drawingList, false);
                rlfLast.setTag(R.id.lastpic_key, 0);
                rlfNext.setTag(R.id.nextpic_key, 0);
                rlfLast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int positon = (int) v.getTag(R.id.lastpic_key);
                        refreshView(positon - 1);
                    }
                });
                rlfNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int positon = (int) v.getTag(R.id.nextpic_key);
                        refreshView(positon + 1);
                    }
                });
            }
            imgpath.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("ISBASEHOUSETYPE", true);
                    intent.putExtra("position", picIndex);
                    intent.putStringArrayListExtra("images", getAllPathList());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setClass(mActivity, ImageVAty.class);
                    mActivity.startActivity(intent);
                }
            });
            //初始化清单
            wallList = result.getRooms();
            if (wallList != null && wallList.size() > 0) {
                mRecyclerView.setVisibility(View.VISIBLE);
                lclistError.setVisibility(View.GONE);
                wallAdapter.notifyDataSetChanged(wallList);
            } else {
                mRecyclerView.setVisibility(View.GONE);
                lclistError.setVisibility(View.VISIBLE);
            }
        } else {
            drawingList = new ArrayList<>();
            wallList = new ArrayList<>();
            showNoDataHint("此户型无放样信息");
        }

    }

    /**
     * 更新放样图
     */
    private void refreshView(int position) {
        picIndex = position;
        rlfLast.setTag(R.id.lastpic_key, picIndex);
        rlfNext.setTag(R.id.nextpic_key, picIndex);
        setImageViewVisibility(picIndex, drawingList,false);
        if (drawingList != null && drawingList.size() > picIndex) {
            //图片名称
            tvfWallName.setText(drawingList.get(picIndex).getName());
            //放样图
            List<String> housePics = new ArrayList<>();
            housePics.add(drawingList.get(picIndex).getImg());
            refreshPics(picIndex, housePics);
        }

    }

    /**
     * 更新户型图
     */
    private void refreshPics(int position, List<String> housePics) {
        if (housePics != null && position < housePics.size()) {
            picIndex = position;
            try {
                if (housePics != null && housePics.size() > 0) {
                    if (housePics.get(position) == null || housePics.get(position).equals("")) {
                        imgpath.setImageResource(R.mipmap.zanuw);
                    } else {
                        Picasso.with(getContext()).load(housePics.get(position)).error(R.mipmap.zanuw).into(imgpath);
                    }
                }
            } catch (Exception ee) {
            }
        }
    }

    /**
     * 更新翻页按钮
     *
     * @param position
     * @param isSinglePic
     */
    private void setImageViewVisibility(int position, List<MarkDrawing> drawings, boolean isSinglePic) {
        if (isSinglePic) {
            tvfWallName.setText(drawings.get(0).getName());
            imageLoading(drawings.get(0).getImg(), imgpath, R.mipmap.zanuw);
            rlfLast.setVisibility(View.INVISIBLE);
            rlfNext.setVisibility(View.INVISIBLE);
        } else {
            tvfWallName.setText(drawings.get(position).getName());
            imageLoading(drawings.get(position).getImg(), imgpath, R.mipmap.zanuw);
            rlfLast.setVisibility(View.VISIBLE);
            rlfNext.setVisibility(View.VISIBLE);
            if (position == 0) {
                rlfLast.setVisibility(View.INVISIBLE);
                rlfNext.setVisibility(View.VISIBLE);
            } else if (position == drawings.size() - 1) {
                rlfLast.setVisibility(View.VISIBLE);
                rlfNext.setVisibility(View.INVISIBLE);
            } else {
                rlfLast.setVisibility(View.VISIBLE);
                rlfNext.setVisibility(View.VISIBLE);
            }
        }
    }

    private ArrayList<String> getAllPathList() {
        if (result == null || result.getMarkDrawing() == null || result.getMarkDrawing().size() == 0) {
            return null;
        }
        ArrayList<String> pathList = new ArrayList<>();
        for (int i = 0; i < result.getMarkDrawing().size(); i++) {
            pathList.add(result.getMarkDrawing().get(i).getImg());
        }
        return pathList;
    }

    @Override
    public void showLoadingDialog() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
