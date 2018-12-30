package com.soonfor.measuremanager.tools;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationManagerCompat;


/**
 * Created by Administrator on 2017/10/26 0026.
 */

public class NotificationUtil {
    private static Context mContext;
    private static Vibrator mVibrator;  //声明一个振动器对象
    private static NotificationManager notificationManager;

    public static void playSound() {
        mVibrator = (Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification_alarm);//声音
        Ringtone r = RingtoneManager.getRingtone(mContext, notification);
        r.play();
        mVibrator.vibrate(new long[]{0, 1000, 0, 0}, -1);
    }

    private static void sendNotification(Context context, Intent intent, String title, String contentText, int notify_Id, int Icon) {
        mContext = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = null;
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notify_Id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            Notification.Builder builder = new Notification.Builder(context)
                    .setAutoCancel(true)  //设置点击通知后自动取消通知
                    .setContentTitle(title)  //通知标题
                    .setContentText(contentText)  //通知第二行的内容
                    .setContentIntent(pendingIntent)  //点击通知后，发送指定的PendingIntent
                    .setWhen(System.currentTimeMillis())// 设置通知来到的时间
                    .setSmallIcon(Icon);  //通知图标，必须设置否则通知不显示
            notification = builder.getNotification();
        } else {
            notification = new Notification.Builder(context)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentText(contentText)
                    .setSmallIcon(Icon)
                    .setWhen(System.currentTimeMillis())// 设置通知来到的时间
                    .setContentIntent(pendingIntent)
                    .build();
        }
        notificationManager.notify(notify_Id, notification);
        playSound();
    }

    /**
     * 判断通知权限是否开启
     * @param context
     * @return
     */
    public static boolean isOpenNotification(Context context){
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        return manager.areNotificationsEnabled();
    }

    /**
     * 跳转到应用详情
     * @param context
     */
    public static void openAppDetail(Context context){
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(localIntent);
    }
}
