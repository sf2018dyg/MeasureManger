package com.soonfor.measuremanager.view.popupwindow.SelectPopupWindow;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.view.popupwindow.SelectPopupWindow.bean.ConditionEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by DC-DingYG on 2016/12/31 0031.
 */

public class Com_SelectPopupWindow extends PopupWindow {
    private Activity mActivity;
    private TextView textView;
    private View mMenuView;
    private ListView lvfPopupItem;
    private MyAdapter myAdapter;
    private int dialogWidth = 0;
    private ImageView imgfCover;
    private DisplayMetrics dm;
    private Resources res;

    public Com_SelectPopupWindow(final Activity context, List<ConditionEntity> sList, int firstIndex,
                                 AdapterView.OnItemClickListener itenListener, ImageView imgfCover) {
        mActivity = context;
        this.imgfCover = imgfCover;
        res = context.getResources();
        dm = res.getDisplayMetrics();
        dialogWidth = Tools.dip2px(context, 100);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.view_popupwindow, null);
        lvfPopupItem = (ListView) mMenuView.findViewById(R.id.lvfChidlItem);
        myAdapter = new MyAdapter(context, firstIndex);
        lvfPopupItem.setAdapter(myAdapter);
        myAdapter.appendData(sList, true);
        lvfPopupItem.setSelection(firstIndex);
        lvfPopupItem.setOnItemClickListener(itenListener);
        //自动算高度
        ViewGroup.LayoutParams params = lvfPopupItem.getLayoutParams();
        params.width = dialogWidth;
        if (myAdapter.getCount() < 5) {
            params.height = Tools.dip2px(context, 41) * sList.size();
            lvfPopupItem.setLayoutParams(params);
        } else {
            params.height = Tools.dip2px(context, 205);//223
            lvfPopupItem.setLayoutParams(params);
        }

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
        //设置SelectPicPopupWindow弹出窗体的背景
        ColorDrawable dw = new ColorDrawable(res.getColor(R.color.white));
        this.setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(params.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(params.WRAP_CONTENT);
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
        imgfCover.setOnClickListener(new View.OnClickListener() {
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
    public void showPopupWindow(TextView parent, Context context, int dp_x, int dp_y) {
        try {
            textView = parent;
            this.showAtLocation(parent, Gravity.BOTTOM | Gravity.START,
                    Tools.dip2px(context, dp_x), Tools.dip2px(context, dp_y));
            //this.showAsDropDown(parent, Tools.dip2px(context,dp_x),  Tools.dip2px(context,dp_y));
            this.setAnimationStyle(R.style.mypopwindow_anim_style);
            textView.setTextColor(Color.parseColor("#333333"));
            imgfCover.setVisibility(View.VISIBLE);
        }catch (Exception e){}
    }

    @Override
    public void dismiss() {
        super.dismiss();
        textView.setTextColor(Color.parseColor("#888888"));
        imgfCover.setVisibility(View.GONE);
    }

    class MyAdapter extends BaseAdapter {
        protected Context mContext;
        protected LayoutInflater mInflater;
        protected List<ConditionEntity> myList = new ArrayList<ConditionEntity>();
        int ItemCode;

        public MyAdapter(Context context, int itemCode) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
            ItemCode = itemCode;
        }

        @Override
        public int getCount() {
            if (myList == null)
                return 0;
            return myList.size();
        }

        @Override
        public ConditionEntity getItem(int position) {
            if (myList == null || myList.size() <= 0)
                return null;
            if (position > myList.size())
                return null;
            return myList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * 刷新数据
         */
        public void appendData(List<ConditionEntity> data, boolean isClearOld) {
            if (data == null) {
                return;
            } else if (isClearOld) {
                myList.clear();
            }
            myList.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ConditionEntity item = myList.get(position);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.view_popup_item, parent, false);
            }
            TextView tvfItemN = (TextView) convertView.findViewById(R.id.tvfItemN);
            tvfItemN.setText(item.getConditionName());
//            if (position == ItemCode) {
//                tvfItemN.setTextColor(Color.parseColor("#eb433a"));
//            } else {
//                tvfItemN.setTextColor(Color.parseColor("#333333"));
//            }
            return convertView;
        }
    }
}
