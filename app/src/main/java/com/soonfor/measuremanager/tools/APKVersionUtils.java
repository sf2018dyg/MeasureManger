package com.soonfor.measuremanager.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class APKVersionUtils {
    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    static PackageInfo getVersion(Context mContext) {
        //获取当前版本号
        PackageInfo pi = null;//getPackageName()是你当前类的包名，0代表是获取版本信息
        try {
            pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi;
    }

    /**
     * 获取当前版本号
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext){
        PackageInfo pi = getVersion(mContext);
        if(pi==null){
            return 0;
        }else{
            return pi.versionCode;
        }
    }
    /**
     * 获取当前版本名称
     * @param mContext
     * @return
     */
    public static String getVersionName(Context mContext){
        PackageInfo pi = getVersion(mContext);
        if(pi==null){
            return "1.0";
        }else{
            return pi.versionName;
        }
    }
}
