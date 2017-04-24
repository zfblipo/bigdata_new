package com.xingnext.bigdata.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lipo.utils.DisplayUtil;
import com.xingnext.bigdata.R;

import java.util.ArrayList;
import java.util.List;

public class MyPagerIndicator extends LinearLayout {

	private List<ImageView> imageList;
	private Context context;
	private int imageSize;
	private int imageMar;

	public MyPagerIndicator(Context context) {
		super(context);
		init(context);
	}

	public MyPagerIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyPagerIndicator(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
		imageSize = DisplayUtil.dip2px(context, 7);
		imageMar = DisplayUtil.dip2px(context, 5);
	}

	public void setPager(ViewPager pagers, final int currtent) {
		imageList = new ArrayList<ImageView>();
		if (currtent <= 0) {
			return;
		}
		this.removeAllViews();
		for (int i = 0; i < currtent; i++) {
			ImageView imageview = new ImageView(context);
			LayoutParams params = new LayoutParams(imageSize, imageSize);
			params.setMargins(0, 0, imageMar, 0);
			imageview.setLayoutParams(params);
			if (i == 0) {
				imageview.setImageResource(R.drawable.pager_dot);
			} else {
				imageview.setImageResource(R.drawable.pager_ring);
			}

			imageList.add(imageview);
			this.addView(imageview);
		}


		pagers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				int positions = position % currtent;
				for (int i = 0; i < currtent; i++) {
					ImageView image = imageList.get(i);
					if (i == positions) {
						image.setImageResource(R.drawable.pager_dot);
					} else {
						image.setImageResource(R.drawable.pager_ring);
					}
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});



	}

}
