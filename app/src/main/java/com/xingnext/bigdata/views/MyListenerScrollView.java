package com.xingnext.bigdata.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/4/5.
 */
public class MyListenerScrollView extends ScrollView {

    private OnScrollListener onScrollListener;

    public MyListenerScrollView(Context context) {
        super(context);
    }

    public MyListenerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListenerScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
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
