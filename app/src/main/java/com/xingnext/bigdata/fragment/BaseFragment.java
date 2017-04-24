package com.xingnext.bigdata.fragment;

import com.xingnext.bigdata.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by lipo on 2017/3/13.
 */
public class BaseFragment extends Fragment {

    public Activity mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

    }
    
    public void startIntent(Class<?> intentClass){
		Intent intentBase = new Intent();
		intentBase.setClass(mContext, intentClass);
		startActivity(intentBase);
		mContext.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	public void startIntent(Intent intent,Class<?> intentClass){
		intent.setClass(mContext, intentClass);
		startActivity(intent);
		mContext.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	public void startIntentForResult(Intent intent,Class<?> intentClass,int requestCode){
		intent.setClass(mContext, intentClass);
		startActivityForResult(intent, requestCode);
		mContext.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	public void finishActivity(){
		mContext.finish();
		mContext.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
}
