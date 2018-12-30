package com.soonfor.measuremanager.bases;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.home.lofting.adapter.detail.MeasureInfoRoomAdapter;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.MeasureResultEntity;
import com.soonfor.measuremanager.home.lofting.model.bean.detail.Measure.MeasureRoomEntity;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.view.RoundJiaoImageView;
import com.soonfor.measuremanager.view.floatactionbutton.DraggableFloatingButton;
import com.soonfor.measuremanager.view.previewImage.ImageVAty;


import java.util.ArrayList;
import java.util.List;

public class BaseHouseTypeListView extends LinearLayout {

    private Context mContext;
    private LinearLayout llfTop;
    private TextView tvfHouseTypeName;//户型名称
    private RoundJiaoImageView imgpath;//户型图
    private TextView tvfMeasureName;//测量类型（是量尺还是复尺）
    private RecyclerView mRecyclerView;
    private TextView lclistError;//无清单数据时显示
    private DraggableFloatingButton fab_prev, fab_next;
    private int picIndex = 0;
    private List<MeasureResultEntity> resultEntities;
    private String measureType = "测量清单";
    private MeasureInfoRoomAdapter roomAdapter;
    private GridLayoutManager manager;
    List<MeasureRoomEntity> roomList;//房间
    private ScrollView scrollView;

    public BaseHouseTypeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.com_listview_housetype, this);
        llfTop = this.findViewById(R.id.llfTop);
        tvfHouseTypeName = this.findViewById(R.id.tvfHouseTypeName);
        imgpath = this.findViewById(R.id.imgpath);
        tvfMeasureName = this.findViewById(R.id.tvfMeasureName);
        mRecyclerView = this.findViewById(R.id.mRecyclerView);
        lclistError = this.findViewById(R.id.lclistError);
        fab_prev = this.findViewById(R.id.fab_prev);
        fab_next = this.findViewById(R.id.fab_next);
        scrollView = this.findViewById(R.id.scrollView);
    }
    //初始化
    public void initHoustTypeListView(final Context mContext,final String measureType, final List<MeasureResultEntity> resultEntities, int position) {
        this.mContext = mContext;
        this.measureType = measureType;
        manager = new GridLayoutManager(getContext(), 1);
        roomAdapter = new MeasureInfoRoomAdapter(getContext(), roomList, measureType);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(roomAdapter);
        this.resultEntities = resultEntities;
        if (resultEntities.size() == 1) {
            setImageViewVisibility(position, true);
        } else {
            setImageViewVisibility(position, false);
            fab_prev.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = (int) v.getTag(R.id.lastpic_key);
                    if (index - 1 >= 0) {
                        setImageViewVisibility(index - 1, false);
                        refreshView(index - 1);
                    } else {
                        MyToast.showToast(mContext, "已是第一页");
                    }
                }
            });
            fab_next.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = (int) v.getTag(R.id.nextpic_key);
                    if (index + 1 < resultEntities.size()) {
                        setImageViewVisibility(index + 1, false);
                        refreshView(index + 1);
                    } else {
                        MyToast.showToast(mContext, "已是最后一页");
                    }
                }
            });
        }
        imgpath.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("ISBASEHOUSETYPE", measureType);
                intent.putExtra("position", picIndex);
                intent.putStringArrayListExtra("images", getAllPathList());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(mContext, ImageVAty.class);
                mContext.startActivity(intent);
            }
        });
        refreshView(position);
    }

    /**
     * 更新户型图及清单
     */
    private void refreshView(int position) {
        tvfMeasureName.setText(measureType);
        picIndex = position;
        fab_prev.setTag(R.id.lastpic_key, picIndex);
        fab_next.setTag(R.id.nextpic_key, picIndex);
        refreshData(resultEntities.get(picIndex));
        llfTop.setFocusable(true);
        llfTop.setFocusableInTouchMode(true);
        llfTop.requestFocus();
    }

    public void refreshData(MeasureResultEntity resultEntity) {
        try {
            if (resultEntity != null) {
                //户型名称
                tvfHouseTypeName.setText(resultEntity.getUnitName());
                //户型图
                if (resultEntity.getUnitsPicture() != null) {
                    refreshPics(resultEntity.getUnitsPicture());
                }

                //房间信息
                if (resultEntity.getRooms() != null) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    lclistError.setVisibility(View.GONE);
                    roomList = resultEntity.getRooms();
                    if (roomList.size() > 0) {
                        roomAdapter.notifyDataSetChanged(roomList);
                    } else {
                        mRecyclerView.setVisibility(View.GONE);
                    }
                } else {
                    mRecyclerView.setVisibility(View.GONE);
                    lclistError.setVisibility(View.VISIBLE);
                }
            }
//            else {
//                showNoDataHint("请求出错，未获取到数据");
//            }
        } catch (Exception e) {
            MyToast.showToast(mContext, e.toString());
        }
        if (scrollView != null) {
            scrollView.scrollTo(0, 0);
        }
    }

    /**
     * 更新户型图
     */
    private void refreshPics(String housePic) {
        try {
            ImageUtil.loadImage(mContext,housePic,imgpath);
        } catch (Exception ee) {
        }

    }

    /**
     * 更新翻页按钮
     *
     * @param position
     * @param isSinglePic
     */
    private void setImageViewVisibility(int position, boolean isSinglePic) {
        if (isSinglePic) {
            fab_prev.setVisibility(GONE);
            fab_next.setVisibility(GONE);
        } else {
            fab_prev.setVisibility(VISIBLE);
            fab_next.setVisibility(VISIBLE);
            if (position == 0) {
                fab_prev.setVisibility(GONE);
                fab_next.setVisibility(VISIBLE);
            } else if (position == resultEntities.size() - 1) {
                fab_next.setVisibility(GONE);
                fab_prev.setVisibility(VISIBLE);
            } else {
                fab_prev.setVisibility(VISIBLE);
                fab_next.setVisibility(VISIBLE);
            }
        }
    }

    private ArrayList<String> getAllPathList() {
//        if(resultEntities==null || resultEntities.size()==0){
//            return null;
//        }
        ArrayList<String> pathList = new ArrayList<>();
        for (int i = 0; i < resultEntities.size(); i++) {
            pathList.add(resultEntities.get(i).getUnitsPicture());
        }
        return pathList;
    }

    public interface Back_FromImageVAty {
        void backListener(int position);
    }

    ;
}
