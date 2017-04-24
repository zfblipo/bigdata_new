package com.xingnext.bigdata.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xingnext.bigdata.R;
import com.xingnext.bigdata.beans.ImageInfo;

import java.util.List;


/**
 * Created by lipo on 2016/8/22.
 */
public class ImagePagerNewAdapter extends PagerAdapter {
    private List<ImageInfo> infoList;
    private int temp;//0不循环，1循环
    private Activity context;
    private int size;
    private LayoutInflater inflater;

    public ImagePagerNewAdapter(Activity context, List<ImageInfo> infoList, int temp) {
        this.context = context;
        this.infoList = infoList;
        this.temp = temp;
        this.size = infoList.size();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        //  Auto-generated method stub
        if (temp == 0) {
            return size;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        //  Auto-generated method stub
        return arg0 == arg1;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

// 需要调节如果页面不是web的页面
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView view = (ImageView) inflater.inflate(R.layout.cell_image,
                null);
//        MyImageLoader.loader(context,view,infoList.get(position % size).thumb);
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        return view;
    }
}
