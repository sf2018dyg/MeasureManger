package com.dynamicpicpro.util;

/**
 * 作者：DC-ZhuSuiBo on 2018/4/2 0002 10:48
 * 邮箱：suibozhu@139.com
 */

public class StringUtils {

    public static boolean isEmpty(CharSequence cs) {
        return (cs == null) || (cs.length() == 0);
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }



    /**
     * 判断字符串是否为空
     * @param text
     * @return true null false !null
     */
    public static boolean isNull(CharSequence text) {
        if (text == null || "".equals(text.toString().trim()) || "null".equals(text))
            return true;
        return false;
    }
}