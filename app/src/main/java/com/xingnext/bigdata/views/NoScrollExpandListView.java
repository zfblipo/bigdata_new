package com.xingnext.bigdata.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by lipo on 2017/4/19.
 */
public class NoScrollExpandListView extends ExpandableListView {

    public NoScrollExpandListView(Context context) {
        super(context);
    }

    public NoScrollExpandListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollExpandListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
