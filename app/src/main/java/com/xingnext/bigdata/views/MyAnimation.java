package com.xingnext.bigdata.views;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created：晴天 on 2016/11/7 0007 15:07
 * 描述：
 * qq:425116228
 */
public class MyAnimation {


    /**
     * 先放大再缩小的动画
     * @param view
     * @param ZOOM_MAX
     * @param ZOOM_MIN
     */

    public static void playMaxToMinAnimation(final View view, final float ZOOM_MAX, final float ZOOM_MIN, final boolean isloop){
        /**
         * 放大动画
         */
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new ScaleAnimation(ZOOM_MIN, ZOOM_MAX, ZOOM_MIN, ZOOM_MAX, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f));
        animationSet.addAnimation(new AlphaAnimation(1.0f, 0.8f));
//        animationSet.setStartOffset(100);//执行前的等待时间
        animationSet.setDuration(300);
        animationSet.setRepeatCount(10000);//循环次数
        animationSet.setInterpolator(new AccelerateInterpolator());
        animationSet.setFillAfter(true);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                /**
                 * 缩小动画
                 */
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(new ScaleAnimation(ZOOM_MAX, ZOOM_MIN, ZOOM_MAX,ZOOM_MIN,
                        Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f));
                animationSet.addAnimation(new AlphaAnimation(0.8f, 1.0f));
                animationSet.setDuration(300);
                /** 常用方法 */
                //animation.setRepeatCount(int repeatCount);//设置重复次数
                //animation.setFillAfter(boolean);//动画执行完后是否停留在执行完的状态
                animationSet.setRepeatCount(10000);
                animationSet.setInterpolator(new DecelerateInterpolator());
                animationSet.setFillAfter(false);
                // 实现心跳的View
                view.startAnimation(animationSet);

                if (isloop){
                    view.postDelayed(new Runnable(){

                        @Override
                        public void run(){
                            playMaxToMinAnimation(view,ZOOM_MAX,ZOOM_MIN,true);
                        }
                    }, 1500);
                }
            }
        });
        // 实现心跳的View
        view.startAnimation(animationSet);
    }




    /**
     * 跳动-跳起动画.
     *
     * @param view the view
     * @param offsetY the offset y
     */
    private void playJumpAnimation(final View view,final float offsetY) {
        float originalY = 0;
        float finalY = - offsetY;
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new TranslateAnimation(0, 0, originalY,finalY));
        animationSet.setDuration(300);
        animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animationSet.setFillAfter(true);

        animationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                playLandAnimation(view,offsetY);
            }
        });

        view.startAnimation(animationSet);
    }



    /**
     * 跳动-落下动画.
     *
     * @param view the view
     * @param offsetY the offset y
     */
    private void playLandAnimation(final View view,final float offsetY) {
        float originalY =  - offsetY;
        float finalY = 0;
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new TranslateAnimation(0, 0, originalY,finalY));
        animationSet.setDuration(200);
        animationSet.setInterpolator(new AccelerateInterpolator());
        animationSet.setFillAfter(true);

        animationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //两秒后再调
                view.postDelayed(new Runnable(){

                    @Override
                    public void run(){
                        playJumpAnimation(view,offsetY);
                    }
                }, 2000);
            }
        });

        view.startAnimation(animationSet);
    }



    /**
     * 旋转动画
     * @param v
     * @param durationMillis
     * @param repeatCount  Animation.INFINITE
     * @param repeatMode  Animation.RESTART
     */
    public static void playRotateAnimation(View v,long durationMillis,int repeatCount,int repeatMode) {

        //创建AnimationSet对象
        AnimationSet animationSet = new AnimationSet(true);
        //创建RotateAnimation对象
        RotateAnimation rotateAnimation = new RotateAnimation(0f,+360f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
        //设置动画持续
        rotateAnimation.setDuration(durationMillis);
        rotateAnimation.setRepeatCount(repeatCount);
        //Animation.RESTART
        rotateAnimation.setRepeatMode(repeatMode);
        //动画插入器
        rotateAnimation.setInterpolator(v.getContext(), android.R.anim.decelerate_interpolator);
        //添加到AnimationSet
        animationSet.addAnimation(rotateAnimation);

        //开始动画
        v.startAnimation(animationSet);
    }

}
