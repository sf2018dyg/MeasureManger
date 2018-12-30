package com.soonfor.repository.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 作者：DC-DingYG on 2018-04-27 9:03
 * 邮箱：dingyg012655@126.com
 */
public class NetTools {

    /**
     * 获取指定网页内容
     *
     * @param url
     * @return
     */
    public static String getUrlContent(String url) {
        String sResult = "";
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);// 设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);// 不缓存
            conn.connect();
            InputStream is = conn.getInputStream();// 获得的数据流
            BufferedReader br = new BufferedReader(new InputStreamReader(is));// 获得输入流的包装�?
            String str = null;//
            StringBuffer sb = new StringBuffer();// 字符串数据的拼接
            while ((str = br.readLine()) != null) {// 做判断是不是读完�?
                sb.append(str);// 若没读完，则拼接
            }
            is.close();
            br.close();
            conn.disconnect();
            sResult = sb.toString();
        } catch (Exception e) {
            sResult = "";
        }
        return sResult;
    }

    /**
     * 判断网络是否断开
     */
    public static boolean isConnectInternet(Context context) {

        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

        if (networkInfo != null) { // 注意，这个判断一定要的哦，要不然会出错

            return networkInfo.isAvailable();
        }
        return false;
    }
}
