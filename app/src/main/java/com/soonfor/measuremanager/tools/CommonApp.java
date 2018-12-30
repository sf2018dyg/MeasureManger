package com.soonfor.measuremanager.tools;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;


public class CommonApp extends Application {

	private static List<Activity> activityList = new LinkedList<Activity>();
	private static CommonApp instance;

	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public static CommonApp getInstance() {
		if (null == instance) {
			instance = new CommonApp();
		}
		return instance;

	}


	public void addActivity(Activity activity) {
		if(!activityList.contains(activity)){
			activityList.add(activity);
		}
	}
	
	public void removeActivity(Activity activity) {
		if(activityList.contains(activity)){
			activityList.remove(activity);
		}
	}
	

	public static void exit() {
		finishAllActivity();	
		System.exit(0);
	}
	
	
	public static void finishAllActivity() {
		for (Activity activity : activityList) {
			if(activity != null){
				activity.finish();
			}
		}
	}
}
