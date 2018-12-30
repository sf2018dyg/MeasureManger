package com.soonfor.repository.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 作者：DC-DingYG on 2018-06-27 14:28
 * 邮箱：dingyg012655@126.com
 */
public class DateTools {
    /**
     * 获取格式时间
     * @param time
     * @return
     */
    public static String getTimestamp(long time, String format) {
        SimpleDateFormat sdr = new SimpleDateFormat(format, Locale.getDefault());
        return sdr.format(new Date(time));
    }
    /**
     * 截取时分秒
     *
     * @param date
     */
    public static String commonFormat2(String date) {
        int index = date.indexOf(" ");
        return date.substring(0, index);
    }
    public static String commonFormat3(String date) {
        int index = date.lastIndexOf(":");
        return date.substring(0, index);
    }
}
