package com.xingnext.bigdata.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.xingnext.bigdata.utils.MyStatic;

/**
 * Created by lipo on 2016/8/14.
 */
public class MyMainScrollView extends ScrollView {

    public MyMainScrollView(Context context) {
        super(context);
        initView(context);
    }

    public MyMainScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {

    }

    private float rawY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                rawY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                if (getScrollY() == 0 && ev.getRawY() - rawY > 0) {
                    return false;
                } else if (getChildAt(0).getMeasuredHeight() <= getScrollY() + getHeight() && ev.getRawY() - rawY < 0) {
                    return false;
                } else if (MyStatic.mainf_status == 1) {
                    return false;
                }
                rawY = ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                break;

        }

        return super.onInterceptTouchEvent(ev);
    }


}
