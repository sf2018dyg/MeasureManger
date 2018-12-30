package com.soonfor.repository.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.soonfor.repository.tools.dialog.CustomDialog;
import com.soonfor.repository.ui.RepApp;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 作者：DC-DingYG on 2018-04-27 9:03
 * 邮箱：dingyg012655@126.com
 */
public class ComTools {

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    //**tostring
    public static String ToString(String string) {
        if (string == null || string.equals("null")) {
            return "";
        } else {
            return string;
        }
    }

    /**
     * 是否包含中文
     */
    public static boolean isIncludeChinese(String str) {
        boolean flag = false;
        for (int i = 0; i < str.length(); i++) {
            flag = str.substring(i, i + 1).matches("[\\u4e00-\\u9fa5]+");
            if (flag)
                return flag;
        }
        return flag;
    }

    static Dialog dialog = null;

    public static void ToSetPermissions(final Activity context, int stringId) {
        dialog = CustomDialog.createCancleDialog(context, "权限申请,点确定设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //getAppDetailSettingIntent(mContext);
                Intent intent = new Intent("/");
                ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.RunningServices");
                intent.setComponent(cm);
                intent.setAction("android.intent.action.VIEW");
                context.startActivity(intent);
            }
        });
        dialog.show();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取VersionName
     *
     * @param context
     * @return pi.versionName
     */
    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi.versionName;
    }

    //此情况适合map不为空的情况
    public static boolean getBoolFromMap(Map<String, Boolean> vMap, String key) {
        try {
            if (vMap.containsKey(key)) {
                return vMap.get(key);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    //此情况适合map不为空的情况
    public static String getStringFromArray(String[] strings, int strIndex) {
        try {
            if (strings != null && strings.length > strIndex) {
                return strings[strIndex];
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

//    /**
//     * The encode formate of data scanned by scnner;
//     *
//     * @param date the source data
//     * @return the encode formate;
//     * @throws IOException decode exception;
//     */
//    public static String charsetName(byte[] date) throws IOException {
//        Charset charset = null;
//        InputStream is = new ByteArrayInputStream(date);
//        CodepageDetectorProxy detectorProxy = CodepageDetectorProxy.getInstance();
//        detectorProxy.add(new ParsingDetector(false));
//        detectorProxy.add(JChardetFacade.getInstance());
//        detectorProxy.add(ASCIIDetector.getInstance());
//        detectorProxy.add(UnicodeDetector.getInstance());
//        charset = detectorProxy.detectCodepage(is, date.length);
//        if (charset != null) {
//            return charset.name();
//        } else {
//            return "utf-8";
//        }
//    }

    /**
     * 返回表格标题和key
     **/
    public static Map<Integer, String[]> getKeyAndTitle(JSONObject jo, int start) {
        try {
            Map<Integer, String[]> sarrList = new HashMap<>();
            Iterator iterator = jo.keys();
            String[] tKey = new String[jo.names().length() - start];
            String[] tValue = new String[jo.names().length() - start];
            int index = 0;
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = jo.getString(key);
                if (index >= start && index <= tKey.length) {
                    tKey[index - start] = key;
                    tValue[index - start] = value;
                }
                index++;
            }
            sarrList.put(0, tKey);
            sarrList.put(1, tValue);
            return sarrList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过key获取数值
     */
    public static String[] getValuesByKeys(JSONObject jo, String keys[]) {
        try {
            if (keys == null || keys.length == 0) {
                return null;
            } else {
                String[] value = new String[keys.length];
                for (int i = 0; i < keys.length; i++) {
                    value[i] = ComTools.ToString(jo.getString(keys[i]));
                }
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setTextWithDraw(Activity context, TextView textView, String text, int imgRes){
        textView.setText(text);
        Drawable drawable = context.getResources().getDrawable(imgRes);
        if(RepApp.dm.density > 2.0){
            drawable.setBounds(0, 0, 40, 40);
        }else {
            drawable.setBounds(0, 0, 20, 20);
        }
        textView.setCompoundDrawables(drawable, null, null, null);
    }
}
