package com.xingnext.bigdata;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.xingnext.bigdata.utils.MyPublic;
import com.xingnext.bigdata.utils.MyStatic;

/**
 * Created by lipo on 2017/3/12.
 */
public class BaseApplication extends Application {

    public static SharedPreferences preferences;
    public static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initImageLoader();
        preferences = getSharedPreferences("xing_big_user_info",
                Context.MODE_PRIVATE);

        MyPublic.getStatusHeight(this);
        getUserData();
        getVersion();
    }

    private void getUserData() {
        MyStatic.userData.user_id = preferences.getString("user_id", "");
        MyStatic.userData.access_token = preferences.getString("access_token", "");
        MyStatic.userData.refresh_token = preferences.getString("refresh_token", "");
        MyStatic.startImage = preferences.getString("start_image", "");
    }

    public static void quitPreferences() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_id", "");
        editor.putString("access_token", "");
        editor.putString("refresh_token", "");
        editor.commit();
    }

    private void getVersion() {
        PackageInfo packageInfo = null;
        PackageManager packageManager = this.getPackageManager();
        try {
            packageInfo = packageManager.getPackageInfo(
                    this.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        MyStatic.version_name = packageInfo.versionName;
        MyStatic.version_code = packageInfo.versionCode;
    }

    private void initImageLoader() {
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPoolSize(5) //线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 1) // 优先级
                .memoryCache(new LruMemoryCache(5 * 1024 * 1024))   //也可以用自己的内存缓存实现
                .memoryCacheSize(4 * 1024 * 1024)   // 设置缓存的最大字节
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URL名称用MD5加密
                .tasksProcessingOrder(QueueProcessingType.FIFO) //先进先出
                .diskCacheSize(20 * 1024 * 1024)  // 设置缓存的最大字节
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDecoder(new BaseImageDecoder(true)) // default
                //  .denyCacheImageMultipleSizesInMemory()   // 缓存显示不同大小的同一张图片
                //.imageDownloader(new BaseImageDownloader(getApplicationContext(), 10 * 1000, 30 * 1000)) //超时时间
                //.writeDebugLogs() // 是否打印log
                .build();
        ImageLoader.getInstance().init(configuration);
    }


}
