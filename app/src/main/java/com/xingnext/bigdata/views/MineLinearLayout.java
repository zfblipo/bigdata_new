package com.xingnext.bigdata.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.lipo.utils.DisplayUtil;
import com.xingnext.bigdata.utils.MyStatic;

/**
 * Created by lipo on 2016/7/26.
 */
public class MineLinearLayout extends LinearLayout {

	private Context context;
	private int dp200;

	public MineLinearLayout(Context context) {
		super(context);
		init(context);
	}

	public MineLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MineLinearLayout(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
		dp200 = DisplayUtil.dip2px(context, 200);
	}

	private float rawY;
	private float rawX;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {

		case MotionEvent.ACTION_DOWN:
			rawY = ev.getRawY();
			rawX = ev.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			float nowY = ev.getRawY() - rawY;
			float nowX = ev.getRawX() - rawX;
			if (MyStatic.mine_stattus == 0 && nowY < 0
					&& Math.abs(nowY) > Math.abs(nowX) && Math.abs(nowY) > 0) {
				return true;
			}
			rawY = ev.getRawY();
			rawX = ev.getRawX();
			break;

		}

		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			rawY = event.getRawY();
			rawX = event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			float nowY = event.getRawY() - rawY;
			float nowX = event.getRawX() - rawX;

			if (MyStatic.mine_stattus == 0 && Math.abs(nowY) > Math.abs(nowX)
					&& Math.abs(nowY) > 10) {
				int paddTop = getPaddingTop() + (int) nowY;
				if (paddTop < 0) {
					paddTop = 0;
					MyStatic.mine_stattus = 1;
				} else if (paddTop > dp200) {
					paddTop = dp200;
				}
				setPadding(0, paddTop, 0, 0);

				rawY = event.getRawY();
				rawX = event.getRawX();
				return true;
			}

			break;
		case MotionEvent.ACTION_UP:
			if (getPaddingTop() < (dp200 * 0.4)) {
				scrollLinear();
			} else {
				scrollStart();
			}

			break;
		}

		return super.onTouchEvent(event);
	}

	private void scrollLinear() {

		ValueAnimator animator = ValueAnimator.ofInt(getPaddingTop(), 0);
		animator.setDuration(300);
		animator.setInterpolator(new AccelerateInterpolator());
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int value = (Integer) animation.getAnimatedValue();
				setPadding(0, value, 0, 0);
			}
		});
		animator.start();
		MyStatic.mine_stattus = 1;
	}

	public void scrollStart() {
		ValueAnimator animator = ValueAnimator.ofInt(getPaddingTop(), dp200);
		animator.setDuration(200);
		animator.setInterpolator(new AccelerateInterpolator());
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int value = (Integer) animation.getAnimatedValue();
				setPadding(0, value, 0, 0);
			}
		});
		animator.start();
		MyStatic.mine_stattus = 0;
	}

}
