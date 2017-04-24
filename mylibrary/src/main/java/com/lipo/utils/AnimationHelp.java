package com.lipo.utils;


import com.lipo.views.ToastView;

import android.content.Context;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

public class AnimationHelp {
	private Animation am;

	public AnimationHelp(View view, Context context) {

		ToastView.setToasd(context, "开始摇晃");

		LinearInterpolator lin = new LinearInterpolator();
		am = new RotateAnimation(0, 45, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

		
		am.setDuration(50);

		
		am.setRepeatMode(Animation.REVERSE);
		am.setRepeatCount(20);
		am.setInterpolator(lin);

		am.setAnimationListener(new Animation.AnimationListener() {
			@Override  
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				am.cancel();
				am.reset();
			}
		});

		
//		view.setAnimation(am);
		view.startAnimation(am);

//		am.startNow();
	}
	
	public void cancel(){
		am.cancel();
		am.reset();
	}

}
