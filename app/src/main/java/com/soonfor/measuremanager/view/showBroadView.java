package com.soonfor.measuremanager.view;

import android.app.Activity;

import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.view.shareboard.ShareBoard;
import com.soonfor.measuremanager.view.shareboard.ShareBoardlistener;
import com.soonfor.measuremanager.view.shareboard.SnsPlatform;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.qqmodel.QQ;
import cn.jiguang.share.qqmodel.QZone;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatFavorite;
import cn.jiguang.share.wechat.WechatMoments;
import cn.jiguang.share.weibo.SinaWeibo;
import cn.jiguang.share.weibo.SinaWeiboMessage;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/8 0008 13:54
 * 邮箱：suibozhu@139.com
 * 分享一个连接到各个平台
 */

public class showBroadView {
    private ShareBoard mShareBoard;
    private int mAction = Platform.ACTION_SHARE;
    Activity mActivity;

    /**
     * 例子
     **/
    public static String share_url = "";//http://www.fdatacraft.com/";
    public static String share_text = "";//"数夫家具软件长期专注于家具行业的信息化应用，积累了大量实木、板式、...";
    public static String share_title = "";//" 欢迎光临数夫家具软件";
    public static String share_imageurl = "";//"http://www.fdatacraft.com/Images/Logo_Home.jpg";

    public showBroadView(Activity mActivity, String share_title, String share_text, String share_url, String share_imageurl) {
        this.mActivity = mActivity;
        this.share_title = share_title;
        this.share_text = share_text;
        this.share_url = share_url;
        this.share_imageurl = share_imageurl;

        if (mShareBoard == null) {
            mShareBoard = new ShareBoard(mActivity);
            List<String> platforms = JShareInterface.getPlatformList();
            if (platforms != null) {
                Iterator var2 = platforms.iterator();
                while (var2.hasNext()) {
                    String temp = (String) var2.next();
                    SnsPlatform snsPlatform = createSnsPlatform(temp);
                    mShareBoard.addPlatform(snsPlatform);
                }
            }
            mShareBoard.setShareboardclickCallback(mShareBoardlistener);
        }
        mShareBoard.show();
    }

    private ShareBoardlistener mShareBoardlistener = new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, String platform) {

            switch (mAction) {
                case Platform.ACTION_SHARE:
                    //这里以分享链接为例
                    ShareParams shareParams = new ShareParams();
                    shareParams.setShareType(Platform.SHARE_WEBPAGE);
                    shareParams.setTitle(share_title);
                    shareParams.setText(share_text);
                    shareParams.setShareType(Platform.SHARE_WEBPAGE);
                    shareParams.setUrl(share_url);
                    shareParams.setImagePath(share_imageurl);
                    JShareInterface.share(platform, shareParams, mShareListener);
                    break;
                default:
                    break;
            }
        }
    };

    public static SnsPlatform createSnsPlatform(String platformName) {
        String mShowWord = platformName;
        String mIcon = "";
        String mGrayIcon = "";
        String mKeyword = platformName;
        if (Wechat.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_wechat";
            mGrayIcon = "jiguang_socialize_wechat";
            mShowWord = "jiguang_socialize_text_weixin_key";
        } else if (WechatMoments.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_wxcircle";
            mGrayIcon = "jiguang_socialize_wxcircle";
            mShowWord = "jiguang_socialize_text_weixin_circle_key";

        } else if (WechatFavorite.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_wxfavorite";
            mGrayIcon = "jiguang_socialize_wxfavorite";
            mShowWord = "jiguang_socialize_text_weixin_favorite_key";

        } else if (SinaWeibo.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_sina";
            mGrayIcon = "jiguang_socialize_sina";
            mShowWord = "jiguang_socialize_text_sina_key";
        } else if (SinaWeiboMessage.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_sina";
            mGrayIcon = "jiguang_socialize_sina";
            mShowWord = "jiguang_socialize_text_sina_msg_key";
        } else if (QQ.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_qq";
            mGrayIcon = "jiguang_socialize_qq";
            mShowWord = "jiguang_socialize_text_qq_key";

        } else if (QZone.Name.equals(platformName)) {
            mIcon = "jiguang_socialize_qzone";
            mGrayIcon = "jiguang_socialize_qzone";
            mShowWord = "jiguang_socialize_text_qq_zone_key";
        }
        return ShareBoard.createSnsPlatform(mShowWord, mKeyword, mIcon, mGrayIcon, 0);
    }

    private PlatActionListener mShareListener = new PlatActionListener() {
        @Override
        public void onComplete(Platform platform, int action, HashMap<String, Object> data) {
            MyToast.showToast(mActivity, "分享成功");
        }

        @Override
        public void onError(Platform platform, int action, int errorCode, Throwable error) {
            MyToast.showToast(mActivity, "分享失败:" + error.getMessage() + "---" + errorCode);
        }

        @Override
        public void onCancel(Platform platform, int action) {
            MyToast.showToast(mActivity, "分享取消");
        }
    };
}
