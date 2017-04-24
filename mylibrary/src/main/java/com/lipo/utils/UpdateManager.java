package com.lipo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.lipo.mylibrary.R;
import com.lipo.service.UpdateService;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

/**
 * @author coolszy
 * @date 2012-4-26
 * @blog http://blog.92coding.com
 */

public class UpdateManager {

	/**
	 * 属�??
	 */
	private String urlStr;
	private String content;
	private String versionName;
	private String filesize;
	private int forced;

	/* 下载�? */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	/* 保存解析的XML信息 */
	// HashMap<String, String> mHashMap;
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数�? */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;

	private Context mContext;
	/* 更新进度�? */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位�?
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context) {
		this.mContext = context;
	}

	/**
	 * �?测软件更�?
	 */
	// public void checkUpdate() {
	// if (isUpdate()) {
	// // 显示提示对话�?
	// showNoticeDialog();
	// } else {
	// Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG)
	// .show();
	// }
	// }

	/**
	 * �?查软件是否有更新版本
	 * 
	 * @return
	 */
	// private boolean isUpdate() {
	// // 获取当前软件版本
	// int versionCode = getVersionCode(mContext);
	// // 把version.xml放到网络上，然后获取文件信息
	// InputStream inStream = ParseXmlService.class.getClassLoader()
	// .getResourceAsStream("version.xml");
	// // 解析XML文件�? 由于XML文件比较小，因此使用DOM方式进行解析
	// ParseXmlService service = new ParseXmlService();
	// try {
	// mHashMap = service.parseXml(inStream);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// if (null != mHashMap) {
	// int serviceCode = Integer.valueOf(mHashMap.get("version"));
	// // 版本判断
	// if (serviceCode > versionCode) {
	// return true;
	// }
	// }
	// return false;
	// }

	/**
	 * 获取软件版本�?
	 * 
	 * @param context
	 * @return
	 */
	private int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo("com.szy.update", 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 显示软件更新对话�?
	 */
	public void showNoticeDialog() {
		// 构�?�对话框
		Builder builder = new Builder(mContext);
		builder.setTitle(mContext.getResources().getString(R.string.soft_update_name) + versionName + "版本更新");
		builder.setMessage(content);
		// 更新
		builder.setPositiveButton(R.string.soft_update_updatebtn, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 显示下载对话�?
				showDownloadDialog();
			}
		});
		// 稍后更新
		builder.setNegativeButton(R.string.soft_update_later, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (forced == 0) {
					dialog.dismiss();

				} else if (forced == 1) {
					
				}
			}
		});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	/**
	 * 显示软件下载对话�?
	 */
	private void showDownloadDialog() {
		// 构�?�软件下载对话框
		// AlertDialog.Builder builder = new Builder(mContext);
		// builder.setTitle(R.string.soft_updating);
		// // 给下载对话框增加进度�?
		// final LayoutInflater inflater = LayoutInflater.from(mContext);
		// View v = inflater.inflate(R.layout.softupdate_progress, null);
		// mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		// builder.setView(v);
		// // 取消更新
		// builder.setNegativeButton(R.string.soft_update_cancel,
		// new OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		//
		// dialog.dismiss();
		// // 设置取消状�??
		// cancelUpdate = true;
		//
		// }
		// });
		// mDownloadDialog = builder.create();
		// mDownloadDialog.show();
		//
		// NotificationManager updateNotificationManager = (NotificationManager)
		// mContext
		// .getSystemService(Context.NOTIFICATION_SERVICE);
		// Notification updateNotification = new Notification();
		// // 设置通知栏显示内�?
		// updateNotification.icon = R.drawable.launcher;
		// updateNotification.tickerText = "即刻便利下载";
		// updateNotification.
		//
		// // 发出通知
		// updateNotificationManager.notify(0, updateNotification);

		// 现在文件
		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk() {
		// 启动新线程下载软�?
		// new downloadApkThread().start();
		Intent intent = new Intent(mContext, UpdateService.class);
		intent.putExtra("Key_App_Name", "蛋管�?");
		intent.putExtra("Key_Down_Url", urlStr);
		mContext.startService(intent);
	}

	private File pathFile = null;

	/**
	 * 下载文件线程
	 * 
	 * @author coolszy
	 * @date 2012-4-26
	 * @blog http://blog.92coding.com
	 */
	private class downloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					// 获得存储卡的路径
					// String sdpath = Environment.getExternalStorageDirectory()
					// + "/";
					// mSavePath = sdpath + "xiangbianli";
					pathFile = Environment.getExternalStorageDirectory();
				} else {
					pathFile = Environment.getDownloadCacheDirectory();

				}
				if (pathFile == null || !pathFile.exists()) {
					pathFile = new File("/storage/sdcard0");
				}
				URL url = new URL(urlStr);
				// 创建连接
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.connect();
				// 获取文件大小
				int length = conn.getContentLength();
				// 创建输入�?
				InputStream is = conn.getInputStream();

				// File file = new File(pathFile);
				// // 判断文件目录是否存在
				// if (!file.exists()) {
				// pathFile.mkdir();
				// }
				File apkFile = new File(pathFile, "danguanjia");
				FileOutputStream fos = new FileOutputStream(apkFile);
				int count = 0;
				// 缓存
				byte buf[] = new byte[1024];
				// 写入到文件中
				do {
					int numread = is.read(buf);
					count += numread;
					// 计算进度条位�?
					progress = (int) (((float) count / length) * 100);
					// 更新进度
					mHandler.sendEmptyMessage(DOWNLOAD);
					if (numread <= 0) {
						// 下载完成
						mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
						break;
					}
					// 写入文件
					fos.write(buf, 0, numread);
				} while (!cancelUpdate);// 点击取消就停止下�?.
				fos.close();
				is.close();

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 取消下载对话框显�?
			mDownloadDialog.dismiss();
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk() {
		File apkfile = new File(pathFile, "jikebianli");
		if (!apkfile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		mContext.startActivity(i);
		pathFile.delete();
	}

	public String getUrlStr() {
		return urlStr;
	}

	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public int getForced() {
		return forced;
	}

	public void setForced(int forced) {
		this.forced = forced;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

}
