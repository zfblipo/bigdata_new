package com.xingnext.bigdata.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by lipo on 2017/4/27.
 */

public class MyPullListenerScrollView extends ScrollView {

    private MyListenerScrollView.OnScrollListener onScrollListener;

    public MyPullListenerScrollView(Context context) {
        super(context);
    }

    public MyPullListenerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPullListenerScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollListener(MyListenerScrollView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        t += getHeight()-getPaddingTop()-getPaddingBottom();
        onScrollListener.onListener(l, t, oldl, oldt);
    }

    public interface OnScrollListener{
        public void onListener(int l, int t, int oldl, int oldt);
    }

}