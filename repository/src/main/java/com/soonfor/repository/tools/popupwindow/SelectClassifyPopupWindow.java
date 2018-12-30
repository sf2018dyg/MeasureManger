package com.soonfor.repository.tools.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;


import com.soonfor.repository.R;
import com.soonfor.repository.adapter.knowledge.FirstLevlClassifyAdapter;
import com.soonfor.repository.tools.ComTools;
import com.soonfor.repository.tools.DataTools;
import com.soonfor.repository.ui.RepApp;
import com.soonfor.repository.ui.fragment.KnowledgeFragment;


/**
 * Created by DC-DingYG on 2016/12/31 0031.
 */

public class SelectClassifyPopupWindow extends PopupWindow {
    private Activity mActivity;
    private View mMenuView;
    private LinearLayoutManager mLayoutManager1;
    private RecyclerView rvfFirstLevl;
    private RecyclerView rvfSecondLevl;
    private FirstLevlClassifyAdapter flcAdapter;

    private ImageView imgfColor;
    private ImageView imgfCover;

    public SelectClassifyPopupWindow(final Activity context, ImageView imgfCover, refresh refreshInterface) {
        mActivity = context;
        this.imgfCover = imgfCover;
        if(RepApp.dm == null){
            RepApp.dm = context.getResources().getDisplayMetrics();}

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.view_classify, null);
        rvfFirstLevl = (RecyclerView) mMenuView.findViewById(R.id.rvfFirstLevl);
        rvfSecondLevl = (RecyclerView) mMenuView.findViewById(R.id.rvfSecondLevl);
        imgfColor = (ImageView) mMenuView.findViewById(R.id.imgfColor);
        mLayoutManager1 = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);

        flcAdapter = new FirstLevlClassifyAdapter(mActivity, DataTools.fTypes, rvfSecondLevl, refreshInterface);
        // 设置布局管理器
        rvfFirstLevl.setLayoutManager(mLayoutManager1);
        // 设置adapter
        rvfFirstLevl.setAdapter(flcAdapter);

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体的背景
        ColorDrawable dw = new ColorDrawable(mActivity.getResources().getColor(R.color.white));
        this.setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(RepApp.dm.widthPixels);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(RepApp.dm.heightPixels - ComTools.dip2px(context,200));
        //设置SelectPicPopupWindow弹出窗体动画效果
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//        mMenuView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int height = mMenuView.findViewById(R.id.lvfChidlItem).getTop();
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    dismiss();
//                }
//                return true;
//            }
//        });
        imgfColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent, Context context) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent,0, ComTools.dip2px(context,-44));
            this.setAnimationStyle(android.R.style.Animation_Dialog);
            imgfCover.setVisibility(View.VISIBLE);
        } else {
            this.dismiss();
        }
    }
    @Override
    public void dismiss() {
        super.dismiss();
        KnowledgeFragment.isSelecFirstLevlByClick = false;
        imgfCover.setVisibility(View.GONE);
    }
    public interface refresh{
        void refreshLayout();
    }
}
