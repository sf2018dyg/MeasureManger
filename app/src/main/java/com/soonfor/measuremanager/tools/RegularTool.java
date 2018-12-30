package com.soonfor.measuremanager.tools;

/**
 * Created by Administrator on 2018-02-22.
 */

public class RegularTool {
    /*数字与字母的混合正则表达式*/
    public static boolean isPwd(String txt) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < txt.length(); i++) {
            if (Character.isDigit(txt.charAt(i))) {     //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(txt.charAt(i))) {   //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        return isDigit & isLetter;
    }

    /**
     * 是否包含中文
     */
    public static boolean isIncludeChinese(String str){
        boolean flag = false;
        for (int i = 0; i < str.length(); i++) {
            flag = str.substring(i,i+1).matches("[\\u4e00-\\u9fa5]+");
            if(flag)
                return flag;
        }
        return flag;

    }
}
