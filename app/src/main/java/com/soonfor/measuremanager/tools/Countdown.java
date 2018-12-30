package com.soonfor.measuremanager.tools;

import android.app.Activity;
import android.os.Handler;
import android.text.Html;
import android.widget.Button;


public class Countdown {
	private int settingTime;
	private int time;// 倒计时的整个时间数
	private Handler mHandler = new Handler();// 全局handler
	private Button messageBtn;
	private Activity activity;

	public Countdown(int settingTime, Button messageBtn, Activity activity) {
		super();
		this.settingTime = settingTime;
		this.messageBtn = messageBtn;
		this.activity = activity;
		time = settingTime;
	}

	public void setCountDown() {
		new Thread(new ClassCut()).start();// 开启倒计时
	}

	class ClassCut implements Runnable {// 倒计时逻辑子线程

		@Override
		public void run() {
			while (time > 0) {// 整个倒计时执行的循环
				time--;
				mHandler.post(new Runnable() {// 通过它在UI主线程中修改显示的剩余时间

					@Override
					public void run() {
						// TODO Auto-generated method stub
						String htmlStr = "<font color='#ff0000' size='29px'>" + time + "s</font>" + "<font color='#999999' size='29px'>重新获取</font>";
						messageBtn.setText(Html.fromHtml(htmlStr));
						messageBtn.setEnabled(false);
					}

				});
				try {
					Thread.sleep(1000);// 线程休眠一秒钟 这个就是倒计时的间隔时间
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// 下面是倒计时结束逻辑

			mHandler.post(new Runnable() {

				@Override
				public void run() {
					messageBtn.setEnabled(true);
					messageBtn.setText(Html.fromHtml("<font color='#ff0000' size='29px'>重新获取</font>"));
				}
			});
			time = settingTime;
		}
	}
}
