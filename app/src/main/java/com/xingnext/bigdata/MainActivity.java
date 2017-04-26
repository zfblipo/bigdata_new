package com.xingnext.bigdata;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xingnext.bigdata.beans.GameTypeInfo;
import com.xingnext.bigdata.beans.VersionInfo;
import com.xingnext.bigdata.factory.APPUploadHelper;
import com.xingnext.bigdata.fragment.MainFragment1_two;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyImageLoader;
import com.xingnext.bigdata.utils.MyStatic;
import com.xingnext.bigdata.utils.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseFragmentActivity {

    private Intent intent;
    private FragmentManager manager;
    private int temp = 0;
    private int start_temp = 0;

    public Fragment[] fragments;
    private ImageView[] images;
    private TextView[] texts;
    private int[] imageResouse0;
    private int[] imageResouse1;
    private SharedPreferences preferences;
    private MyHttpConn httpConn;
    private Gson gson;

    private ImageView main_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        manager = getSupportFragmentManager();
        preferences = getSharedPreferences("xing_big_user_info",
                Context.MODE_PRIVATE);

        httpConn = new MyHttpConn(mContext);
        gson = new Gson();

        initFragment();

        getStartup();
    }

    private void initFragment() {
        main_image = (ImageView) findViewById(R.id.main_image);
        fragments = new Fragment[]{manager.findFragmentById(R.id.main_fragment1)
                , manager.findFragmentById(R.id.main_fragment2)
                , manager.findFragmentById(R.id.main_fragment3)
                , manager.findFragmentById(R.id.main_fragment4)};

        images = new ImageView[]{(ImageView) findViewById(R.id.image1)
                , (ImageView) findViewById(R.id.image2)
                , (ImageView) findViewById(R.id.image3)
                , (ImageView) findViewById(R.id.image4)};

        texts = new TextView[]{(TextView) findViewById(R.id.text1)
                , (TextView) findViewById(R.id.text2)
                , (TextView) findViewById(R.id.text3)
                , (TextView) findViewById(R.id.text4)};

        imageResouse0 = new int[]{R.mipmap.tabbar_icon_normal_1
                , R.mipmap.tabbar_icon_normal_2
                , R.mipmap.tabbar_icon_normal_3
                , R.mipmap.tabbar_icon_normal_4};

        imageResouse1 = new int[]{R.mipmap.tabbar_icon_press_1
                , R.mipmap.tabbar_icon_press_2
                , R.mipmap.tabbar_icon_press_3
                , R.mipmap.tabbar_icon_press_4};

        FragmentTransaction transaction = manager.beginTransaction();
        for (int i = 1; i < 4; i++) {
            transaction.hide(fragments[i]);
        }
        transaction.show(fragments[0]);
        transaction.commit();
    }

    public void changeLayout(int position) {
        if (position != temp) {
            manager.beginTransaction().hide(fragments[temp]).show(fragments[position]).commit();
            images[temp].setImageResource(imageResouse0[temp]);
            images[position].setImageResource(imageResouse1[position]);
            texts[temp].setTextColor(getResources().getColor(R.color.tab_text_normal));
            texts[position].setTextColor(getResources().getColor(R.color.tab_text_press));
            temp = position;
        }
    }

    public void LayoutOnclickMethod(View layout) {
        switch (layout.getId()) {
            case R.id.layout1:
                changeLayout(0);
                break;
            case R.id.layout2:
                dismissView();
                changeLayout(1);
                break;
            case R.id.layout3:
                dismissView();
//                changeLayout(2);
                if(MyStatic.userData.access_token == null||"".equals(MyStatic.userData.access_token)){
                    startIntent(LoginActivity.class);
                }else{
                    startIntent(ProfitWebActivity.class);
                }
                break;
            case R.id.layout4:
                dismissView();
                changeLayout(3);
                break;
        }
    }

    private void dismissView(){
        ((MainFragment1_two)fragments[0]).dismissLabel();
    }

    private void getStartup() {
        String url = MyUrl.getVersion;
        Map<String, String> params = new HashMap<String, String>();
        httpConn.httpGet(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                JSONObject data = json.optJSONObject("data");
                VersionInfo info = VersionInfo.fromJson(data);

                if (info != null && MyStatic.isFristUpgrade) {
                    if (MyStatic.version_code < info.version_code) {
//								if ("1".equals(info.getForceUpgrade())) {
//									forceUploadDialog(context, info);
//								} else {
                        APPUploadHelper.uploadDialog(mContext, info);
//								}
                    } else {

                    }
                }

                JSONObject screen = data.optJSONObject("screen");
                if (screen != null) {
                    String pic = screen.optString("pic");
                    String link = screen.optString("link");
                    if (pic != null&&!pic.equals(MyStatic.startImage)) {
                        MyStatic.startImage = pic;
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("start_image",pic);
                        editor.commit();
                        MyImageLoader.loaderEmpty(mContext,main_image,MyStatic.startImage);
                    }
                }

                MyStatic.gameTypeInfos.clear();
                JSONArray game_type = data.optJSONArray("game_type");
                if(game_type!=null){
                    int lent = game_type.length();
                    for (int i = 0; i < lent; i++) {
                        String gameJson = game_type.optString(i);
                        GameTypeInfo gameTypeInfo = gson.fromJson(gameJson, GameTypeInfo.class);
                        if(i == 0){
                            gameTypeInfo.setChoiced(true);
                        }else{
                            gameTypeInfo.setChoiced(false);
                        }
                        MyStatic.gameTypeInfos.add(gameTypeInfo);
                    }
                }
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }


}
