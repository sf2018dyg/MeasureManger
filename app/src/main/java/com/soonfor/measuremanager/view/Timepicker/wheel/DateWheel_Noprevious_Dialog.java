package com.soonfor.measuremanager.view.Timepicker.wheel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.view.Timepicker.wheel.adapters.AbstractWheelTextAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by DingYG on 2017-10-17.
 */

public class DateWheel_Noprevious_Dialog extends Dialog implements View.OnClickListener {
    //控件
    private WheelView mYearWheelView;
    private WheelView mMonthWheelView;
    private WheelView mDayWheelView;
    private WheelView mHourWheelView;
    private WheelView mMinuteWheelView;
    private CalendarTextAdapter mYearAdapter;
    private CalendarTextAdapter mMonthAdapter;
    private CalendarTextAdapter mDayAdapter;
    private CalendarTextAdapter mHourAdapter;
    private CalendarTextAdapter mMinuteAdapter;
    private TextView mTitleTextView;
    private Button mSureButton;
    private Dialog mDialog;
    private Button mCancleDialog;

    //变量
    private ArrayList<String> arry_year = new ArrayList<String>();
    private ArrayList<String> arry_month = new ArrayList<String>();
    private ArrayList<String> arry_day = new ArrayList<String>();
    private ArrayList<String> arry_hour = new ArrayList<String>();
    private ArrayList<String> arry_minute = new ArrayList<String>();

    private int nowMonthId = 0;
    private int nowDayId = 0;
    private int nowHourId = 0;
    private int nowMinuteId = 0;
    private int nowYearId = 0;
    private String mYearStr;
    private String mMonthStr;
    private String mDayStr;
    private String mHourStr;
    private String mMinuteStr;
    private boolean mBlnTimePickerGone = false;//时间选择是否显示

    //常量
    private final int MAX_TEXT_SIZE = 18;
    private final int MIN_TEXT_SIZE = 18;

    private Context mContext;
    private DateChooseInterface dateChooseInterface;

    private int monthWheel = 0;//月份的当前滚轮数
    private int dayWheel = 0;//天数的当前滚轮数
    private int hourWheel = 0;//小时的当前滚轮数
    private int minWheel = 0;//分钟的当前滚轮数

    private int[] defaultTime = new int[5];

    public DateWheel_Noprevious_Dialog(Context context, DateChooseInterface dateChooseInterface) {
        super(context);
        this.mContext = context;
        this.dateChooseInterface = dateChooseInterface;
        mDialog = new Dialog(context, R.style.my_style_dialog);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_dialog_date_choose, null);
        mDialog.setContentView(view);
        mYearWheelView = (WheelView) view.findViewById(R.id.year_wv);
        mMonthWheelView = (WheelView) view.findViewById(R.id.month_wv);
        mDayWheelView = (WheelView) view.findViewById(R.id.day_wv);
        mHourWheelView = (WheelView) view.findViewById(R.id.hour_wv);
        mMinuteWheelView = (WheelView) view.findViewById(R.id.minute_wv);
        mTitleTextView = (TextView) view.findViewById(R.id.title_tv);
        mSureButton = (Button) view.findViewById(R.id.btn_pos);
        mCancleDialog = (Button) view.findViewById(R.id.btn_neg);

        mSureButton.setOnClickListener(this);
        mCancleDialog.setOnClickListener(this);
    }

    private void initData() {
        Calendar nowCalendar = Calendar.getInstance();
        initTime(nowCalendar);
        initListener();
    }

    /**
     * 初始化年、月、日、时、分
     */
    private void initTime(Calendar nowCalendar) {
        setTime(0, nowCalendar);
        mYearAdapter = new CalendarTextAdapter(mContext, arry_year, nowYearId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mYearWheelView.setVisibleItems(3);
        mYearWheelView.setViewAdapter(mYearAdapter);
        mYearWheelView.setCurrentItem(nowYearId);
        mYearStr = arry_year.get(nowYearId) + "";

        setTime(1, nowCalendar);
        mMonthAdapter = new CalendarTextAdapter(mContext, arry_month, nowMonthId, MAX_TEXT_SIZE, MAX_TEXT_SIZE);
        mMonthWheelView.setVisibleItems(3);
        mMonthWheelView.setViewAdapter(mMonthAdapter);
        mMonthWheelView.setCurrentItem(nowMonthId);
        mMonthStr = arry_month.get(nowMonthId) + "";

        setTime(2, nowCalendar);
        mDayAdapter = new CalendarTextAdapter(mContext, arry_day, nowDayId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mDayWheelView.setVisibleItems(3);
        mDayWheelView.setViewAdapter(mDayAdapter);
        mDayWheelView.setCurrentItem(nowDayId);
        mDayStr = arry_day.get(nowDayId);

        setTime(3, nowCalendar);
        mHourAdapter = new CalendarTextAdapter(mContext, arry_hour, nowHourId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mHourWheelView.setVisibleItems(3);
        mHourWheelView.setViewAdapter(mHourAdapter);
        mHourWheelView.setCurrentItem(nowHourId);
        mHourStr = arry_hour.get(nowHourId);

        setTime(4, nowCalendar);
        mMinuteAdapter = new CalendarTextAdapter(mContext, arry_minute, nowMinuteId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mMinuteWheelView.setVisibleItems(3);
        mMinuteWheelView.setViewAdapter(mMinuteAdapter);
        mMinuteWheelView.setCurrentItem(nowMinuteId);
        mMinuteStr = arry_minute.get(nowMinuteId);
    }

    //分别设置月、日、时、分
    private void setTime(int type, Calendar nowCalendar) {
        int nowyear = nowCalendar.get(Calendar.YEAR);
        int nowMonth = 0, nowday = 0;
        switch (type) {
            case 0:
                arry_year.clear();
                for (int i = 0; i <= 99; i++) {
                    int year = nowyear + i;
                    arry_year.add(year + "年");
                }
                if(mYearStr!=null && !mYearStr.equals("")){
                    int curryear = Integer.parseInt(mYearStr.replace("年", "").trim());
                    setFirstWheel(0,curryear);
                }else{
                    nowYearId = arry_year.size() - 1;
                    mYearStr = arry_year.get(nowYearId) + "";
                }
                break;
            case 1:
                arry_month.clear();
                nowMonth = nowCalendar.get(Calendar.MONTH) + 1;
                boolean isThisYear = true;
                if (isThisTime(1, nowyear, 0, 0, 0, 0)) {
                    isThisYear = true;
                    for (int i = nowMonth; i <= 12; i++) {
                        arry_month.add(i + "月");
                    }
                } else {
                    isThisYear = false;
                    for (int i = 1; i <= 12; i++) {
                        arry_month.add(i + "月");
                    }
                }
                if (mMonthStr != null && !mMonthStr.equals("")) {
                    int currMonth = Integer.parseInt(mMonthStr.replace("月", "").trim());
                    if (isThisYear && nowMonth > currMonth) {
                        nowMonthId = 0;
                        mMonthStr = arry_month.get(nowMonthId) + "";
                        mMonthWheelView.setCurrentItem(nowMonthId);
                    }else{
                        setFirstWheel(1,currMonth);
                    }
                } else {
                    nowMonthId = arry_month.size() - 1;
                }
                break;
            case 2:
                arry_day.clear();
                if (nowMonth == 0) {
                    nowMonth = nowCalendar.get(Calendar.MONTH) + 1;
                }
                nowday = nowCalendar.get(Calendar.DAY_OF_MONTH);
                int days = getDaysWhitMonth(Integer.parseInt(mYearStr.substring(0, mYearStr.indexOf("年")).trim()), Integer.parseInt(mMonthStr.substring(0, mMonthStr.indexOf("月")).trim()));
                boolean isThisMonth = true;
                if (isThisTime(2, nowyear, nowMonth, 0, 0, 0)) {
                    isThisMonth = true;
                    for (int i = nowday; i <= days; i++) {
                        arry_day.add(i + "日");
                    }
                } else {
                    isThisMonth = false;
                    for (int i = 1; i <= days; i++) {
                        arry_day.add(i + "日");
                    }
                }
                if (mDayStr != null && !mDayStr.equals("")) {
                    int currDay = Integer.parseInt(mDayStr.replace("日", "").trim());
                    if (isThisMonth && nowday > currDay) {
                        nowDayId = 0;
                        mDayStr = arry_day.get(nowDayId) + "";
                        mDayWheelView.setCurrentItem(nowDayId);
                    }else{
                        setFirstWheel(2,currDay);
                    }
                } else {
                    nowDayId = arry_day.size() - 1;
                }
                break;
            case 3:
                arry_hour.clear();
                if (nowMonth == 0) {
                    nowMonth = nowCalendar.get(Calendar.MONTH) + 1;
                }
                if (nowday == 0) {
                    nowday = nowCalendar.get(Calendar.DAY_OF_MONTH);
                }
                int nowHour = nowCalendar.get(Calendar.HOUR_OF_DAY);
                boolean isThisDay = true;
                if (isThisTime(3, nowyear, nowMonth, nowday, 0, 0)) {
                    isThisDay = true;
                    for (int i = nowHour; i <= 23; i++) {
                        String hour = "";
                        if (i < 10) {
                            hour = "0" + i;
                        } else {
                            hour = "" + i;
                        }
                        arry_hour.add(hour);
                    }
                } else {
                    isThisDay = false;
                    for (int i = 0; i <= 23; i++) {
                        String hour = "";
                        if (i < 10) {
                            hour = "0" + i;
                        } else {
                            hour = "" + i;
                        }
                        arry_hour.add(hour);
                    }
                }
                if (mHourStr != null && !mHourStr.equals("")) {
                    int currHour = Integer.parseInt(mHourStr.trim());
                    if (isThisDay && nowHour > currHour) {
                        nowHourId = 0;
                        mHourStr = arry_hour.get(nowHourId);
                        mHourWheelView.setCurrentItem(nowHourId);
                    }else{
                        setFirstWheel(3, currHour);
                    }
                } else {
                    nowHourId = arry_hour.size() - 1;
                }
                break;
            case 4:
                arry_minute.clear();
                if (nowMonth == 0) {
                    nowMonth = nowCalendar.get(Calendar.MONTH) + 1;
                }
                if (nowday == 0) {
                    nowday = nowCalendar.get(Calendar.DAY_OF_MONTH);
                }
                nowHour = nowCalendar.get(Calendar.HOUR_OF_DAY);
                int nowMinite = nowCalendar.get(Calendar.MINUTE);
                boolean isThisHour = true;
                if (isThisTime(4, nowyear, nowMonth, nowday, nowHour, 0)) {
                    isThisHour = true;
                    for (int i = nowMinite; i <= 59; i++) {
                        String min = "";
                        if (i < 10) {
                            min = "0" + i;
                        } else {
                            min = "" + i;
                        }
                        arry_minute.add(min);
                    }
                } else {
                     isThisHour = false;
                    for (int i = 0; i <= 59; i++) {
                        String min = "";
                        if (i < 10) {
                            min = "0" + i;
                        } else {
                            min = "" + i;
                        }
                        arry_minute.add(min);
                    }
                }
                if (mMinuteStr != null && !mMinuteStr.equals("")) {
                    int currMinute = Integer.parseInt(mMinuteStr.trim());
                    if (isThisHour && nowMinite > currMinute) {
                        nowMinuteId = 0;
                        mMinuteStr = arry_minute.get(nowMinuteId);
                        mMinuteWheelView.setCurrentItem(nowMinuteId);
                    }else{
                        setFirstWheel(4, currMinute);
                    }
                } else {
                    nowMinuteId = arry_minute.size() - 1;
                }
                break;
        }
    }

    //判断选择的是否是当前时间
    private boolean isThisTime(int type, int nowyear, int nowmonth, int nowday, int nowhour, int nowmin) {
        boolean flag = false;
        int selYear = Integer.parseInt(mYearStr.substring(0, mYearStr.indexOf("年")).trim());
        int selMonth = 0;
        int selDay = 0;
        switch (type) {
            case 1://年
                if (selYear == nowyear) {
                    flag = true;
                }
                break;
            case 2://月份
                if (selMonth == 0) {
                    selMonth = Integer.parseInt(mMonthStr.substring(0, mMonthStr.indexOf("月")).trim());
                }
                if (selYear == nowyear && selMonth == nowmonth) {
                    flag = true;
                }
                break;
            case 3://天
                if (selMonth == 0) {
                    selMonth = Integer.parseInt(mMonthStr.substring(0, mMonthStr.indexOf("月")).trim());
                }
                if (selDay == 0) {
                    selDay = Integer.parseInt(mDayStr.substring(0, mDayStr.indexOf("日")).trim());
                }
                if (selYear == nowyear && selMonth == nowmonth && selDay == nowday) {
                    flag = true;
                }
                break;
            case 4://小时
                if (selMonth == 0) {
                    selMonth = Integer.parseInt(mMonthStr.substring(0, mMonthStr.indexOf("月")).trim());
                }
                if (selDay == 0) {
                    selDay = Integer.parseInt(mDayStr.substring(0, mDayStr.indexOf("日")).trim());
                }
                if (selYear == nowyear && selMonth == nowmonth && selDay == nowday && Integer.parseInt(mHourStr) == nowhour) {
                    flag = true;
                }
                break;
            case 5://分钟
                if (selMonth == 0) {
                    selMonth = Integer.parseInt(mMonthStr.substring(0, mMonthStr.indexOf("月")).trim());
                }
                if (selDay == 0) {
                    selDay = Integer.parseInt(mDayStr.substring(0, mDayStr.indexOf("日")).trim());
                }
                if (selYear == nowyear && selMonth == nowmonth && selDay == nowday
                        && Integer.parseInt(mHourStr) == nowhour
                        && Integer.parseInt(mMinuteStr) == nowmin) {
                    flag = true;
                }
                break;

        }
        return flag;
    }

    //获取默认的时间在滚轮中的id
    private void setFirstWheel(int type, int int_time) {
        switch (type) {
            case 0://年轮
                if (arry_year != null && arry_year.size() > 0) {
                    for (int i = 0; i < arry_year.size(); i++) {
                        if (int_time == Integer.parseInt(arry_year.get(i).replace("年", "").trim())) {
                            nowYearId = i;
                            mYearStr = arry_year.get(nowYearId) + "";
                            mMinuteWheelView.setCurrentItem(i);
                            break;
                        }
                    }
                }
                break;
            case 1://月轮
                if (arry_month != null && arry_month.size() > 0) {
                    for (int i = 0; i < arry_month.size(); i++) {
                        if (int_time == Integer.parseInt(arry_month.get(i).replace("月", "").trim())) {
                            nowMonthId = i;
                            mMonthStr = arry_month.get(nowMonthId) + "";
                            mMonthWheelView.setCurrentItem(i);
                            break;
                        }
                    }
                }
                break;
            case 2://日轮
                if (arry_day != null && arry_day.size() > 0) {
                    for (int i = 0; i < arry_day.size(); i++) {
                        if (int_time == Integer.parseInt(arry_day.get(i).replace("日", "").trim())) {
                            nowDayId = i;
                            mDayStr = arry_day.get(nowDayId) + "";
                            mDayWheelView.setCurrentItem(i);
                            break;
                        }
                    }
                }
                break;
            case 3://时轮
                if (arry_hour != null && arry_hour.size() > 0) {
                    for (int i = 0; i < arry_hour.size(); i++) {
                        if (int_time == Integer.parseInt(arry_hour.get(i).trim())) {
                            nowHourId = i;
                            mHourStr = arry_hour.get(nowHourId) + "";
                            mHourWheelView.setCurrentItem(i);
                            break;
                        }
                    }
                }
                break;
            case 4://分钟滚轮
                if (arry_minute != null && arry_minute.size() > 0) {
                    for (int i = 0; i < arry_minute.size(); i++) {
                        if (int_time == Integer.parseInt(arry_minute.get(i).trim())) {
                            nowMinuteId = i;
                            mMinuteStr = arry_minute.get(nowMinuteId) + "";
                            mMinuteWheelView.setCurrentItem(i);
                            break;
                        }
                    }
                }
                break;
        }
    }


        /**
         * 初始化滚动监听事件
         */
    private void initListener() {
        //年份*****************************
        mYearWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mYearStr = arry_year.get(wheel.getCurrentItem()) + "";
                setTime(1, Calendar.getInstance());
                mMonthWheelView.setViewAdapter(mMonthAdapter);
                setTime(2, Calendar.getInstance());
                mDayWheelView.setViewAdapter(mDayAdapter);
                setTime(3, Calendar.getInstance());
                mHourWheelView.setViewAdapter(mHourAdapter);
                setTime(4, Calendar.getInstance());
                mMinuteWheelView.setViewAdapter(mMinuteAdapter);
                setDateDialogTitle(strTimeToDateFormat(mYearStr, mMonthStr, mDayStr, mHourStr, mMinuteStr));
            }
        });

        //月份*********************
        mMonthWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mMonthStr = arry_month.get(wheel.getCurrentItem()) + "";
                setTime(2, Calendar.getInstance());
                mDayWheelView.setViewAdapter(mDayAdapter);
                setTime(3, Calendar.getInstance());
                mHourWheelView.setViewAdapter(mHourAdapter);
                setTime(4, Calendar.getInstance());
                mMinuteWheelView.setViewAdapter(mMinuteAdapter);
                if(mDayWheelView.getCurrentItem()>=arry_day.size()){
                    mDayWheelView.setCurrentItem(arry_day.size()-1);
                    if (hourWheel >= arry_hour.size()) {
                        mHourWheelView.setCurrentItem(nowHourId);
                        if (minWheel >= arry_minute.size()) {
                            mMinuteWheelView.setCurrentItem(nowMinuteId);
                        }
                    }
                }
                setDateDialogTitle(strTimeToDateFormat(mYearStr, mMonthStr, mDayStr, mHourStr, mMinuteStr));
            }
        });
        //日期********************
        mDayWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mDayStr = arry_day.get(wheel.getCurrentItem());
                setTime(3, Calendar.getInstance());
                mHourWheelView.setViewAdapter(mHourAdapter);
                setTime(4, Calendar.getInstance());
                mMinuteWheelView.setViewAdapter(mMinuteAdapter);
                setDateDialogTitle(strTimeToDateFormat(mYearStr, mMonthStr, mDayStr, mHourStr, mMinuteStr));
            }
        });

        //小时***********************************
        mHourWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mHourStr = arry_hour.get(wheel.getCurrentItem()) + "";
                setTime(4, Calendar.getInstance());
                mMinuteWheelView.setViewAdapter(mMinuteAdapter);
                setDateDialogTitle(strTimeToDateFormat(mYearStr, mMonthStr, mDayStr, mHourStr, mMinuteStr));
            }
        });

        //分钟********************************************
        mMinuteWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mMinuteStr = arry_minute.get(wheel.getCurrentItem()) + "";
                setDateDialogTitle(strTimeToDateFormat(mYearStr, mMonthStr, mDayStr, mHourStr, mMinuteStr));
            }

        });
    }

    /**
     * 判断是否是闰年
     *
     * @param year
     * @return
     */
    private boolean isRunNian(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }
    public void setDefault(String timeTitle){
        String title = timeTitle;
        if(title==null || title.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            title = sdf.format(Calendar.getInstance().getTime());
        }
        mTitleTextView.setText(title);
        mYearStr = title.substring(0,4)+"年";
        mMonthStr = title.substring(5,7) + "月";
        mDayStr = title.substring(8,10) + "日";
        mHourStr = title.substring(11,13);
        mMinuteStr = title.substring(14,title.length());
        initData();
    }

    public void setDateDialogTitle(String title) {
        mTitleTextView.setText(title);
    }

    private int getDaysWhitMonth(int year, int month) {
        int days = 0;
        boolean isRun = isRunNian(year);
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 2:
                if (isRun) {
                    days = 29;
                } else {
                    days = 28;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
        }
        return days;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pos://确定选择按钮监听
                if (mBlnTimePickerGone) {
                    dateChooseInterface.getDateTime(strTimeToDateFormat(mYearStr, mMonthStr, mDayStr));
                } else {
                    dateChooseInterface.getDateTime(strTimeToDateFormat(mYearStr, mMonthStr, mDayStr, mHourStr, mMinuteStr));
                }
                dismissDialog();
                break;
            case R.id.btn_neg://关闭日期选择对话框
                dismissDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 对话框消失
     */
    private void dismissDialog() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return;
        }
        if (null == mDialog || !mDialog.isShowing() || null == mContext || ((Activity) mContext).isFinishing()) {
            return;
        }
        mDialog.dismiss();
        this.dismiss();
    }

    /**
     * 显示日期选择dialog
     */
    public void showDateChooseDialog() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return;
        }
        if (null == mContext || ((Activity) mContext).isFinishing()) {
            // 界面已被销毁
            return;
        }
        if (null != mDialog) {
            mDialog.show();
            return;
        }
        if (null == mDialog) {
            return;
        }
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
    }

    /**
     * xx年xx月xx日xx时xx分转成yyyy-MM-dd HH:mm
     *
     * @param yearStr
     * @param dayStr
     * @param hourStr
     * @param minuteStr
     * @return
     */
    private String strTimeToDateFormat(String yearStr, String monthStr, String dayStr, String hourStr, String minuteStr) {
        String ymonth = monthStr.replace("月", "-");
        String yday = dayStr.replace("日", " ");
        if (ymonth.length() <= 2) {
            ymonth = "0" + ymonth;
        }
        if (yday.length() <= 2) {
            yday = "0" + yday;
        }
        return yearStr.replace("年", "-") + ymonth + yday
                + hourStr + ":" + minuteStr;
    }

    private String strTimeToDateFormat(String yearStr, String monthStr, String dayStr) {
        String ymonth = monthStr.replace("月", "-");
        String yday = dayStr.replace("日", " ");
        if (ymonth.length() <= 2) {
            ymonth = "0" + ymonth;
        }
        if (yday.length() <= 2) {
            yday = "0" + yday;
        }
        return yearStr.replace("年", "-") + ymonth + yday;
    }

    /**
     * 滚轮的adapter
     */
    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, R.id.tempValue, currentItem, maxsize, minsize);
            this.list = list;
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            String str = list.get(index) + "";
            return str;
        }
    }

    /**
     * 回调选中的时间（默认时间格式"yyyy-MM-dd HH:mm:ss"）
     */
    public interface DateChooseInterface {
        void getDateTime(String time);
    }

}