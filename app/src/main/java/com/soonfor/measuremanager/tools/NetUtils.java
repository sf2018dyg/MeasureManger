package com.soonfor.measuremanager.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2018-03-15.
 */

public class NetUtils {

    public static boolean getNetworkIsAvailable(Context context){
        //此处写网络检测的代码
        //<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
        ConnectivityManager conManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        if (networkInfo != null ){
            return networkInfo.isAvailable();
        }
        return false ;
    }
}
