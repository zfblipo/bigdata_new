package com.xingnext.bigdata;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.xingnext.bigdata.utils.MyImageLoader;
import com.xingnext.bigdata.utils.MyStatic;

import java.io.File;

public class StartActivity extends BaseActivity {

    private Handler handler = new Handler();

    private ImageView start_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //控制底部虚拟键盘
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        setContentView(R.layout.activity_start);

        start_image = (ImageView) findViewById(R.id.start_image);

        if(MyStatic.startImage!=null&&!"".equals(MyStatic.startImage)){
            MyImageLoader.loaderEmpty(this,start_image,MyStatic.startImage);
        }

        deleteApk();
        handler.postDelayed(task, 3000);

    }

    Runnable task = new Runnable() {

        @Override
        public void run() {
            Intent intent = new Intent(StartActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_right_in,
                    R.anim.push_right_out);
        }
    };

    private void deleteApk() {
        // apk包路径
        String fileaddr = this.getExternalFilesDir("xitong") + File.separator
                + "xingnext_shaobing.apk";
        File file = new File(fileaddr);
        if (file.exists()) {
            file.delete();
        }
    }

}
