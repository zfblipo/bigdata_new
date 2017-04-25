package com.xingnext.bigdata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by lipo on 2017/3/12.
 */
public class BaseFragmentActivity extends FragmentActivity {

    protected Activity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        MobclickAgent.openActivityDurationTrack(false);
    }


    public void startIntent(Class<?> intentClass){
        Intent intentBase = new Intent();
        intentBase.setClass(this, intentClass);
        startActivity(intentBase);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public void startIntent(Intent intent,Class<?> intentClass){
        intent.setClass(this, intentClass);
        startActivity(intent);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public void startIntentForResult(Intent intent,Class<?> intentClass,int requestCode){
        intent.setClass(this, intentClass);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public void finishActivity(){
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
    }

}
