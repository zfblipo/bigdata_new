package com.xingnext.bigdata.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xingnext.bigdata.R;

import java.io.File;
import java.io.FileOutputStream;


public class MyImageLoader {

    private static String key = "http://mp.jikegouwu.com/attachment/";
    private static int loadingDrawableId = R.color.main_gray;
    private static int failureDrawableId = R.color.main_gray;
    private static int delayTime = 500;
    private static Animation animation;


    public static void loaderHead(Context context, ImageView imageView, String url){
        animation = AnimationUtils.loadAnimation(context, R.anim.my_alpha_action);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.no_avatar)
                .showImageForEmptyUri(R.mipmap.no_avatar)
                .showImageOnFail(R.mipmap.no_avatar)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .delayBeforeLoading(50)//设置展示前的等待时间
                //EXACTLY_STRETCHED：图片会缩放到目标大小完全相同   EXACTLY :图像将完全按比例缩小的目标大小
                //IN_SAMPLE_INT:图像将被二次采样的整数倍
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .displayer(new FadeInBitmapDisplayer(delayTime))//设置渐显时间
                .build();
        if (url != null && !url.startsWith("http")) {
            url = key + url;
        }
        ImageLoader.getInstance().displayImage(url, imageView, options);
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     */
    public static void loader(Context context, ImageView imageView, String url) {

        animation = AnimationUtils.loadAnimation(context, R.anim.my_alpha_action);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(loadingDrawableId)
                .showImageForEmptyUri(loadingDrawableId)
                .showImageOnFail(failureDrawableId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .delayBeforeLoading(50)//设置展示前的等待时间
                //EXACTLY_STRETCHED：图片会缩放到目标大小完全相同   EXACTLY :图像将完全按比例缩小的目标大小
                //IN_SAMPLE_INT:图像将被二次采样的整数倍
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .displayer(new FadeInBitmapDisplayer(delayTime))//设置渐显时间
                .build();
        if (url != null && !url.startsWith("http")) {
            url = key + url;
        }
        ImageLoader.getInstance().displayImage(url, imageView, options);
    }

    public static void loaderEmpty(Context context, ImageView imageView, String url){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(loadingDrawableId)
//                .showImageForEmptyUri(loadingDrawableId)
//                .showImageOnFail(failureDrawableId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
//                .delayBeforeLoading(50)//设置展示前的等待时间
                //EXACTLY_STRETCHED：图片会缩放到目标大小完全相同   EXACTLY :图像将完全按比例缩小的目标大小
                //IN_SAMPLE_INT:图像将被二次采样的整数倍
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .displayer(new FadeInBitmapDisplayer(1000))//设置渐显时间
                .build();
        if (url != null && !url.startsWith("http")) {
            url = key + url;
        }
        ImageLoader.getInstance().displayImage(url, imageView, options);
    }

    public static void loadImage(String url){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(loadingDrawableId)
//                .showImageForEmptyUri(loadingDrawableId)
//                .showImageOnFail(failureDrawableId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
//                .delayBeforeLoading(50)//设置展示前的等待时间
                //EXACTLY_STRETCHED：图片会缩放到目标大小完全相同   EXACTLY :图像将完全按比例缩小的目标大小
                //IN_SAMPLE_INT:图像将被二次采样的整数倍
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
//                .displayer(new FadeInBitmapDisplayer(300))//设置渐显时间
                .build();
        if (url != null && !url.startsWith("http")) {
            url = key + url;
        }
        ImageLoader.getInstance().loadImageSync(url);
    }

    /**
     * 加载图片，设置宽高
     *
     * @param imageView
     * @param url
     * @param width
     * @param height
     */
    public static void loader(ImageView imageView, String url, int width,
                              int height) {

        if (!url.startsWith("http")) {
            url = key + url;
        }

    }



    public static void cacheBitmap(String file_url, Bitmap bitmap) {
        File file2 = new File(file_url);
        try {
            file2.createNewFile();
            FileOutputStream fos = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }


}
