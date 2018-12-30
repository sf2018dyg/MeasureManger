package com.soonfor.measuremanager.tools;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.ninegrid.ImageInfo;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.bases.ErrorBean;
import com.soonfor.measuremanager.http.api.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import cn.jesse.nativelogger.NLogger;


/**
 * Created by ljc on 2018/1/16.
 */

public class CommonUtils {
    @SuppressLint("MissingPermission")
    public static void callPhone(String number, Context context) {
        if (!TextUtils.isEmpty(number)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                MyToast.showToast(context, "请打开电话权限");
                return;
            }
            context.startActivity(intent);
        }
    }
    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断是否内网
     * 10.0.0.0~10.255.255.255
     * 172.16.0.0~172.31.255.255
     * 192.168.0.0~192.168.255.255
     **/
    public static boolean isInner(String ip) {
        String reg = "(10|172|192)\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})";
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(ip);
        return matcher.find();
    }

    public static String formatSecond(int second) {
        int result = second / 60;
        if (result > 0) {
            return String.format("%d'%d\'", result, second % 60);
        } else {
            return second + "\"";
        }
    }


    public static String getServiceAdr() {
        String address = Hawk.get(SoonforApplication.ServerAdr, "");
        if (address == null) {
            return "";
        } else if (!address.toLowerCase().startsWith("http://")) {
            return "http://" + address;
        } else {
            return address;
        }
    }

    public static String formatStr(String string) {
        if (string == null || string.equals("null")) {
            return "";
        } else {
            return string;
        }
    }

    public static String formatNum(String string) {
        if (string == null || string.trim().equals("null") || string.trim().equals("")) {
            return "0";
        } else {
            return string;
        }
    }
    /**
     * 获取百分比
     */
    public static String formatPercentage(String string){
        if (string == null || string.equals("null") || string.equals("")) {
            return "0.0";
        }else {
            float fs = 0;
            try {
                fs = Float.parseFloat(string);
            }catch (Exception e){}
            if(fs == 0){
                return "0.0";
            }else if(fs<1){
                fs = fs*100;
                java.text.DecimalFormat df = new java.text.DecimalFormat("#.0");
                try {
                    return df.format(fs);
                }catch (Exception e){
                    return "0.0";
                }
            }
            return "0.0";
        }
    }
    /**
     * 保留一位小数
     * @param string
     * @return
     */
    public static String formatAdecimal(String string) {
        if (string == null || string.equals("null") || string.equals("")) {
            return "0.0";
        } else {
            double sd = 0;
            try {
                sd = Double.parseDouble(string);
            }catch (Exception e){}
            if(sd==0){
                return "0.0";
            }else {
                java.text.DecimalFormat df = new java.text.DecimalFormat("#.0");
                try {
                    return df.format(sd);
                }catch (Exception e){
                    return string;
                }
            }
        }
    }
    /**
     * 保留两位小数
     * @param string
     * @return
     */
    public static String formatTwodecimal(String string) {
        if (string == null || string.equals("null") || string.equals("")) {
            return "0.00";
        } else {
            double sd = 0;
            try {
                sd = Double.parseDouble(string);
            }catch (Exception e){}
            if(sd==0){
                return "0.00";
            }else {
                java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                try {
                    return df.format(sd);
                }catch (Exception e){
                    return string;
                }
            }
        }
    }

    public static void setSex(TextView textView, String name, String sex) {
        if (textView != null) {
            if (sex == null || sex.equals("") || sex.equals("0")) {
                textView.setText(formatStr(name));
            } else {
                if (sex.equals("0")) {
                    sex = "不确定";
                } else if (sex.equals("1")) {
                    sex = "先生";
                } else if (sex.equals("2")) {
                    sex = "女士";
                }
                textView.setText(formatStr(name) + "(" + sex + ")");
            }
        }
    }

    public static String statusToChinese(boolean isFuChi, int statu) {
        switch (statu) {
            case 1:
                return isFuChi ? "待接收" : "待接收";
            case 2:
                return isFuChi ? "待确认上门" : "待确认上门";
            case 3:
                return isFuChi ? "待复尺" : "待量尺";
            case 4:
                return isFuChi ? "复尺完成" : "量尺完成";
        }
        return "";
    }

    /**
     * 返回是量尺还是复尺
     *
     * @param taskType 0: 其他 1：量尺 2：复尺
     **/
    public static int backLcORFc(String taskType) {
        if (taskType.equals("measure")) {
            return 1;
        } else if (taskType.equals("remeasure")) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * 获取户型图片对象清单
     *
     * @param unitPics
     */
    public static List<ImageInfo> getImageInfos(List<String> unitPics) {
        List<ImageInfo> imageList = new ArrayList<>();
        if (unitPics == null || unitPics.size() == 0) {
            imageList.add(new ImageInfo());
        } else {
            for (int i = 0; i < unitPics.size(); i++) {
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setThumbnailUrl(unitPics.get(i));
                imageInfo.setBigImageUrl(unitPics.get(i));
                imageList.add(imageInfo);
            }
        }
        return imageList;
    }

    /**
     * 分享功能
     * <p>
     * 上下文
     *
     * @param context       上下文
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param imgFile       图片流文件，不分享图片则传null
     */
    public static void shareMsg(Context context, String activityTitle, String msgTitle, String msgText,
                                File imgFile) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgFile == null || imgFile.equals("")) {
            intent.setType("text/*"); // 纯文本
        } else {
            if (imgFile.exists() && imgFile.isFile()) {
                intent.setType("image/*");
                Uri u = Uri.fromFile(imgFile);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, activityTitle);//标题
        intent.putExtra("Kdescription", msgTitle + "\n" + msgText); // 作用于发朋友圈，对好友不会有影响
        intent.putExtra(Intent.EXTRA_TEXT, msgTitle + "\n" + msgText);//附带说明
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //拷贝分享内容到粘贴板
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(msgText);
        MyToast.showToast(context, "已拷贝分享网址到粘贴板,去粘贴分享吧！");

        context.startActivity(Intent.createChooser(intent, activityTitle));
    }


    /**
     * 根据录音文件网络路径或本地文件 获取时长
     *
     * @param mUri
     * @param isLocal
     * @return
     */
    public static long getRingDuring(String mUri, boolean isLocal) {
        String duration = null;
        if(!isLocal) {
            mUri = "http://" + Hawk.get(SoonforApplication.ServerAdr_fj) + UserInfo.DOWNLOAD_FILE + mUri;
        }
        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();

        try {
            if (mUri != null) {
                HashMap<String, String> headers = null;
                if (headers == null) {
                    headers = new HashMap<String, String>();
                    headers.put("User-Agent",
                            "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                }
                mmr.setDataSource(mUri, headers);
            }

            duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mmr.release();
        }
        if (duration == null || duration.equals("")) {
            return 0;
        }
        return Long.parseLong(duration);
    }

    public static void pictureSelect(final Activity context, final int tiple, final int maxSelNum, final List<LocalMedia> lastMedias) {
        PermissionsUtil.requestPermission(context, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                //启动相册并拍照
                PictureSelector.create(context)
                        .openGallery(PictureMimeType.ofImage())
                        .isCamera(true)
                        .imageSpanCount(3)
                        .compress(true)
                        .maxSelectNum(maxSelNum)
                        .isCamera(true)
                        //.compressGrade(Luban.THIRD_GEAR)
                        .selectionMode(tiple)
                        .selectionMedia(lastMedias)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {

            }
        }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }
    public static void LogErrorMsg(String title, String errorText){
        NLogger.e(title, errorText);
    }
}
