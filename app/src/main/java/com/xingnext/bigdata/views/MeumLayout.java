package com.xingnext.bigdata.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.lipo.utils.DisplayUtil;
import com.xingnext.bigdata.utils.MyStatic;

/**
 * Created by lipo on 2016/7/17.
 */
public class MeumLayout extends LinearLayout {

    private DisplayMetrics metrics;
    private int dpWidth;
    private int menuWidth;
    private int dp5;
    private boolean isScrollEnable;
    private MarginLayoutParams params;

    public MeumLayout(Context context) {
        super(context);
        initView(context);
    }

    public MeumLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MeumLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        isScrollEnable = false;
        metrics = context.getResources().getDisplayMetrics();
        dpWidth = DisplayUtil.dip2px(context, 10);
        dp5 = DisplayUtil.dip2px(context, 5);
        menuWidth = metrics.widthPixels * 4 / 5 - dp5;
        params = (MarginLayoutParams) this.getLayoutParams();
    }

    private float rawX, rawY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (MyStatic.meun_status == 1) {
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (ev.getRawX() < dpWidth) {
                    isScrollEnable = true;
                    rawX = ev.getRawX();
                    rawY = ev.getRawY();
                } else {
                    isScrollEnable = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float mathX = Math.abs(rawX - ev.getRawX());
                float mathY = Math.abs(rawY - ev.getRawY());
                if (isScrollEnable && mathX > 5 && mathX > mathY) {
                    return true;
                } else {
                    rawX = ev.getRawX();
                    rawY = ev.getRawY();
                    return false;
                }
            case MotionEvent.ACTION_UP:

                break;

        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (MyStatic.meun_status == 1) {
                    scrollToStart();
                    return true;
                }

                if (event.getRawX() < dpWidth) {
                    isScrollEnable = true;
                    rawX = event.getRawX();
                    rawY = event.getRawY();
                } else {
                    isScrollEnable = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:

                if (MyStatic.meun_status == 0 && isScrollEnable) {
                    float moveX = event.getRawX() - rawX;
                    setTranslationX(getTranslationX() + moveX / 1.5f);
                    if (getTranslationX() < 0) {
                        setTranslationX(0);
                    } else if (getTranslationX() > menuWidth) {
                        setTranslationX(menuWidth);
                    }
                    rawX = event.getRawX();
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (MyStatic.meun_status == 0) {
                    if (getTranslationX() > menuWidth / 3) {
                        scrollToEnd();
                        MyStatic.meun_status = 1;
                    } else {
                        MyStatic.meun_status = 0;
                        ObjectAnimator animator = ObjectAnimator.ofFloat(MeumLayout.this, "translationX", getTranslationX(), 0);
                        animator.setDuration(16);
                        animator.setInterpolator(new AccelerateInterpolator());
                        animator.start();
                    }
                } else {
//                    scrollToStart();
                    MyStatic.meun_status = 0;
                }
                break;
        }

//        return true;
        return super.onTouchEvent(event);
    }

    public void scrollToEnd() {
        scrollLinear(menuWidth);
    }

    public void scrollToStart() {
        scrollLinear(0);
    }

    private void scrollLinear(int arg0) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(MeumLayout.this, "translationX", getTranslationX(), arg0);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();

    }


//    @Override
//    public void setOnClickListener(OnClickListener l) {
//        super.setOnClickListener(l);
//
//        if (MyStatic.meun_status == 1){
//            scrollToStart();
//            MyStatic.meun_status = 0;
//        }
//    }
}
