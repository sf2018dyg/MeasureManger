package com.soonfor.repository.tools;

import android.content.Context;

/**
 * 作者：DC-DingYG on 2018-05-29 18:16
 * 邮箱：dingyg012655@126.com
 */
public class SoundUtil {
    // 上下文
    static Context mContext;
    static SoundUtil soundUtil;

    public SoundUtil(Context context) {
        mContext = context;
    }
    public static SoundUtil getInstense(Context context){
        if(soundUtil==null){
            soundUtil = new SoundUtil(context);
        }
        // 初始化声音
        mContext = context;

        return soundUtil;
    }

}
