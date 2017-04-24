package com.lipo.views;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.lipo.mylibrary.R;

public class MyProgreeDialog extends AlertDialog {

	private ImageView dialog_image;
	private Context context;
	private Animation animation;

	public MyProgreeDialog(Context context) {
		super(context, R.style.my_dialog);
		this.context = context;
	}

	protected MyProgreeDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_view);
		initView();
	}

	private void initView() {
		dialog_image = (ImageView) findViewById(R.id.dialog_image);

		animation = AnimationUtils.loadAnimation(context,
				R.anim.loading_animation);
		dialog_image.startAnimation(animation);
	}

}
