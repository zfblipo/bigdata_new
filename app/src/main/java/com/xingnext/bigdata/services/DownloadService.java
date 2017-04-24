package com.xingnext.bigdata.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.lipo.utils.NetWork;
import com.lipo.views.ToastView;
import com.xingnext.bigdata.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

public class DownloadService extends Service {

	private final int notification_id = 2;
	private NotificationManager manger;
	private int progress;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String url = intent.getStringExtra("download_url");
		manger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		downloadApk(url);
		return super.onStartCommand(intent, flags, startId);
	}

	private void downloadApk(String url) {
		// apk包路径
		String destFileDir = getExternalFilesDir("xitong").getPath();

		if (NetWork.isNetworkConnected(this)) {

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Accept", "application/json");

			OkHttpUtils//
					.get()//
					.headers(headers).url(url)//
					.build()//
					.execute(new FileCallBack(destFileDir, "xingnext_shaobing.apk")//
							{

								@Override
								public void onError(Call arg0, Exception arg1,
										int arg2) {

								}

								@Override
								public void onResponse(File arg0, int arg1) {
									// 通过Intent安装APK文件
									Intent intentB = new Intent();
									intentB.setAction(Intent.ACTION_VIEW);
									intentB.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									intentB.setDataAndType(
											Uri.parse("file://"
													+ arg0.getAbsolutePath()),
											"application/vnd.android.package-archive");
									startActivity(intentB);
									stopSelf();
								}

								@Override
								public void inProgress(float progress,
										long total, int id) {
									super.inProgress(progress, total, id);
									DownloadService.this.progress = (int) (progress * 100);
									
								}

								@Override
								public void onBefore(Request request, int id) {					
									super.onBefore(request, id);
									thread.start();
								}

							});

		} else {
			ToastView.setToasd(this, "没有网络");
		}
	}

	private void sendNotification() {

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setSmallIcon(R.mipmap.ic_launcher);
		builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
				R.mipmap.ic_launcher));
		// 禁止用户点击删除按钮删除
		builder.setAutoCancel(false);
		// 禁止滑动删除
		builder.setOngoing(true);
		// 取消右上角的时间显示
		builder.setShowWhen(false);
		builder.setContentTitle("下载中..." + progress + "%");
		builder.setProgress(100, progress, false);
		// builder.setContentInfo(progress+"%");
		builder.setOngoing(true);
		builder.setShowWhen(false);
		Intent intent = new Intent(this, DownloadService.class);
		intent.putExtra("command", 1);
		Notification notification = builder.build();
		manger.notify(notification_id, notification);
	}

	private Thread thread = new Thread(){
		public void run() {
			while (progress<100) {
				sendNotification();
				try {
					sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	};
	
	@Override
	public void onDestroy() {
		progress = 100;
		manger.cancel(notification_id);
		super.onDestroy();
	}
	
}
