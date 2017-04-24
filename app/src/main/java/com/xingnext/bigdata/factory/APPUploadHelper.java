package com.xingnext.bigdata.factory;

import android.app.Activity;
import android.content.Intent;

import com.google.gson.Gson;
import com.lipo.views.ToastView;
import com.xingnext.bigdata.beans.VersionInfo;
import com.xingnext.bigdata.services.DownloadService;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyStatic;
import com.xingnext.bigdata.utils.MyUrl;
import com.xingnext.bigdata.views.MyUpgradDialog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class APPUploadHelper {

	public static void getVersion(final Activity context,final boolean isshowToast) {
		MyStatic.isFristUpgrade = false;
		final Gson gson = new Gson();
		String url = MyUrl.getVersion;
		Map<String, String> params = new HashMap<String, String>();
		new MyHttpConn(context, false).httpGet(url, params,
				new MyHttpConn.OnCallBack() {

					@Override
					public void Success(JSONObject json) {
						JSONObject data = json.optJSONObject("data");
						VersionInfo info = VersionInfo.fromJson(data);

						if (info != null) {
							if (MyStatic.version_code<info.version_code) {
//								if ("1".equals(info.getForceUpgrade())) {
//									forceUploadDialog(context, info);
//								} else {
									uploadDialog(context, info);
//								}
							}else{
								if(isshowToast){
									ToastView.setToasd(context, "已经是最新版本");
								}
							}
						}
					}

					@Override
					public void onError(int code, String msg) {

					}
				});
	}

	public static void uploadDialog(final Activity context,
			final VersionInfo info) {
		MyUpgradDialog dialog = new MyUpgradDialog(context, "发现新版本", info.contents) {

			@Override
			public void onClickSure() {
				if(info.url.startsWith("http")){
//					downloadApk(context, info.getUrl());
					sendToService(context, info.url);
				}else{
					ToastView.setToasd(context, "参数有误，等待调试");
				}
			}
		};
		dialog.show();
	}

//	private static void forceUploadDialog(final Activity context,
//			final VersionInfo info) {
//		MySingleUnclickDialog dialog = new MySingleUnclickDialog(context,
//				"发布新版本，强制更新", Html.fromHtml(info.getContent()).toString()) {
//
//			@Override
//			public void onSureClick() {
//				if(info.getUrl().startsWith("http")){
////					downloadApk(context, info.getUrl());
//					sendToService(context, info.getUrl());
//				}else{
//					ToastView.setToasd(context, "参数有误，等待调试");
//				}
//			}
//
//		};
//		dialog.setContentColor(context.getResources().getColor(
//				R.color.main_color));
//		dialog.show();
//	}
	
	private static void sendToService(Activity context, String url) {
		Intent intent = new Intent();
		intent.putExtra("download_url", url);
		intent.setClass(context, DownloadService.class);
		context.startService(intent);
	}

}
