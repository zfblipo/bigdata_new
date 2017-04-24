package com.lipo.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

public class JudgeNetwork {

	private boolean k_isWifi, k_isMobile;// 是否为WiFi，是否为移动
	private ConnectivityManager connectivityManager_KBaseActivity;
	private State kState = null;

	public JudgeNetwork(Context context) {
		connectivityManager_KBaseActivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	public boolean isInternet() {
		isWifi();
		is3G();
		if (!k_isWifi && !k_isMobile) {
			return false;
		}
		return true;

	}

	private boolean isWifi() {
		kState = connectivityManager_KBaseActivity.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).getState();
		if (kState == State.CONNECTED) {
			k_isWifi = true;
		} else {
			k_isWifi = false;
		}
		return k_isWifi;
	}

	private boolean is3G() {
		kState = connectivityManager_KBaseActivity.getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE).getState();
		if (kState == State.CONNECTED) {
			k_isMobile = true;
		} else {
			k_isMobile = false;
		}
		return k_isMobile;
	}
	
	/**
	 * 打开网络设置
	 * @param context
	 */
	public void openNetset(Context context){
        //判断手机系统的版  即API大于10 就是3.0或以上版
		Intent intent = null;
        if(android.os.Build.VERSION.SDK_INT>10){
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        }else{
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        context.startActivity(intent);

	}
}
