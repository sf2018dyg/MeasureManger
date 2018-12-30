package com.soonfor.measuremanager.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.home.login.bean.CurrentUserBean;
import com.soonfor.measuremanager.me.bean.MeMineBean;

import java.util.Locale;

import cn.jesse.nativelogger.NLogger;

/**
 * Description：SharedPreferences的管理类
 */
public class PreferenceUtil {

    private static SharedPreferences mSharedPreferences = null;
    private static Editor mEditor = null;

    public static void init(Context context) {
        if (null == mSharedPreferences) {
            mSharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public static String isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("en")) {
            language = "english";
        } else if (language.endsWith("zh")) {
            language = "china";
        }else if (language.endsWith("fr")) {
            language = "french";
        }else if (language.endsWith("es")) {
            language = "spanish";
        }else if (language.endsWith("pt")) {
            language = "portuguese";
        } else
            language = "english";
        return language;
    }

    public static String isLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("en")) {
            language = "en";
        } else if (language.endsWith("zh")) {
            language = "zh";
        } else if (language.endsWith("fr")) {
            language = "fr";
        } else if (language.endsWith("es")) {
            language = "es";
        } else if (language.endsWith("pt")) {
            language = "pt";
        } else {
            language = "en";
        }
        return language;
    }

    public static void removeKey(String key) {
        mEditor = mSharedPreferences.edit();
        mEditor.remove(key);
        mEditor.commit();
    }

    public static void removeAll() {
        mEditor = mSharedPreferences.edit();
        mEditor.clear();
        mEditor.commit();
    }

    public static void commitString(String key, String value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public static String getString(String key, String faillValue) {
        return mSharedPreferences.getString(key, faillValue);
    }

    public static void commitInt(String key, int value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public static int getInt(String key, int failValue) {
        return mSharedPreferences.getInt(key, failValue);
    }


    public static void commitBoolean(String key, boolean value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public static Boolean getBoolean(String key, boolean failValue) {
        return mSharedPreferences.getBoolean(key, failValue);
    }

    public static void switchLanguage(Context context, String language) {
        // 设置应用语言类型
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (language.equals("en")) {
            config.locale = Locale.ENGLISH;
        } else if (language.equals("zh")) {
            config.locale = Locale.SIMPLIFIED_CHINESE;
        } else if (language.equals("fr")) {
            config.locale = Locale.FRENCH;
        }else if (language.equals("es")){
            config.locale = new Locale("es");
        }else if (language.equals("pt")){
            config.locale = new Locale("pt");
        }
        resources.updateConfiguration(config, dm);
        // 保存设置语言的类型
        PreferenceUtil.commitString("language", language);
    }

    /**
     * 保存个人资料MeMineBean
     */
    public static void commitPersonalInfo(String key, MeMineBean mineBean){
        synchronized (mSharedPreferences) {
            Gson gson = new Gson();
            //转换成json数据，再保存
            String strJson = gson.toJson(mineBean);
            mEditor = mSharedPreferences.edit();
            mEditor.putString(key, strJson);
            mEditor.commit();
        }
    }
    /**
     * 取出个人资料MeMineBean
     */
    public static MeMineBean getPersonalInfo(String key){
        synchronized (mSharedPreferences) {
            String strJson = mSharedPreferences.getString(key, null);
            if (null == strJson) {
                return null;
            }
            Gson gson = new Gson();
            MeMineBean bean = gson.fromJson(strJson, new TypeToken<MeMineBean>() {
            }.getType());
            return bean;
        }
    }

    /**
     * 保存当前用户信息
     */
    public static void commitCurrentUserBean(CurrentUserBean userBean){
        synchronized (mSharedPreferences) {
            Gson gson = new Gson();
            //转换成json数据，再保存
            String strJson = gson.toJson(userBean);
            mEditor = mSharedPreferences.edit();
            mEditor.putString(Tokens.SP_USERBEAN, strJson);
            mEditor.commit();
        }
    }
    /**
     * 取出当前用户信息
     */
    public static CurrentUserBean getCurrentUserBean(){
        synchronized (mSharedPreferences) {
            String strJson = mSharedPreferences.getString(Tokens.SP_USERBEAN, null);
            if (null == strJson) {
                return null;
            }
            Gson gson = new Gson();
            CurrentUserBean bean = gson.fromJson(strJson, new TypeToken<CurrentUserBean>() {
            }.getType());
            return bean;
        }
    }
}
