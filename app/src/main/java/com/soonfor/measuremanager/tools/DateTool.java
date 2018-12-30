package com.soonfor.measuremanager.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class DateTool {

    /**
     * 输入时间字符串，返回想要的时间格式类型时间
     *
     * @param time 时间戳
     * @param type 时间格式
     * @return
     */
    public static String getTimeTimestamp(String time, String type) {
        if(time==null || time.equals("")){
            return "";
        }
        if(type==null)type = "yyyy-MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
        try {
            long lt = new Long(time);
            Date date = new Date(lt);
            return simpleDateFormat.format(date);
        }catch (Exception e){
            if(time.contains(".")){
                return time.substring(0, time.indexOf("."));
            }
            return time;
        }
    }
    /**
     * 输入时间戳，返回想要的时间格式类型时间
     *
     * @param time 时间戳
     * @param type 时间格式
     * @return
     */
    public static String getTimeTimestamp(long time, String type) {
        if(time==0){
            return "";
        }
        if(type==null){
            SimpleDateFormat sdr = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
            return sdr.format(new Date(time));
        }else {
            SimpleDateFormat sdr = new SimpleDateFormat(type, Locale.getDefault());
            return sdr.format(new Date(time));
        }
    }


    /**
     * 获取当前时间并根据你输入的格式返回需要的格式类型时间
     *
     * @param format 输入你想要的格式，例如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getTodayDateTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(new Date());
    }

    public static String getDateDay(String timeStr){
        String time = null;
        //用于当月中的每天判断
        long today = Long.valueOf(getTodayDateTime("yyyyMMdd"));
        long times = Long.valueOf(getTimeTimestamp(timeStr,"yyyyMMdd"));
        //每月的判断
        long month = Long.valueOf(getTodayDateTime("yyyyMM"));
        long months = Long.valueOf(getTimeTimestamp(timeStr,"yyyyMM"));
        if (month == months){
            if (today == times){
                time = "今日";
            }else if (today - times == 1){
                time = "昨天";
            }else if (today - times > 1){
                time = getTimeTimestamp(timeStr,"MM-dd");
            }
        }else{
            time = getTimeTimestamp(timeStr,"yyyy") + "年" + getTimeTimestamp(timeStr,"MM") + "月";
        }
        return time;
    }



    /*
     * 毫秒转化时分秒毫秒
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "'");
        }
        if (second > 0) {
            sb.append(second + "''");
        }
        return sb.toString();
    }
//    private static String getWeek(long timeStamp) {
//        int mydate = 0;
//        String week = null;
//        Calendar cd = Calendar.getInstance();
//        cd.setTime(new Date(timeStamp));
//        mydate = cd.get(Calendar.DAY_OF_WEEK);
//        // 获取指定日期转换成星期几
//        if (mydate == 1) {
//            week = "周日";
//        } else if (mydate == 2) {
//            week = "周一";
//        } else if (mydate == 3) {
//            week = "周二";
//        } else if (mydate == 4) {
//            week = "周三";
//        } else if (mydate == 5) {
//            week = "周四";
//        } else if (mydate == 6) {
//            week = "周五";
//        } else if (mydate == 7) {
//            week = "周六";
//        }
//        return week;
//    }
//
//    /**
//     * 输入日期如（2014年06月14日16时09分00秒）返回（星期数）
//     *
//     * @param time
//     * @return
//     */
//    public String week(String time) {
//        Date date = null;
//        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
//        int mydate = 0;
//        String week = null;
//        try {
//            date = sdr.parse(time);
//            Calendar cd = Calendar.getInstance();
//            cd.setTime(date);
//            mydate = cd.get(Calendar.DAY_OF_WEEK);
//            // 获取指定日期转换成星期几
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        if (mydate == 1) {
//            week = "星期日";
//        } else if (mydate == 2) {
//            week = "星期一";
//        } else if (mydate == 3) {
//            week = "星期二";
//        } else if (mydate == 4) {
//            week = "星期三";
//        } else if (mydate == 5) {
//            week = "星期四";
//        } else if (mydate == 6) {
//            week = "星期五";
//        } else if (mydate == 7) {
//            week = "星期六";
//        }
//        return week;
//    }
}
