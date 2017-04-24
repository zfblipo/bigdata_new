package com.xingnext.bigdata;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xingnext.bigdata.adapter.MyFragmentPagerAdapter;
import com.xingnext.bigdata.beans.UserInfo;
import com.xingnext.bigdata.factory.TitleHelper;
import com.xingnext.bigdata.fragment.AccountFragment;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyUrl;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAccountActivity extends BaseFragmentActivity {

    private Resources resources;
    private int positionTemp;

    private TitleHelper titleHelper;
    private TextView my_account_money, my_account_buy;
    private ViewPager my_account_pager;
    private TextView[] textViews = new TextView[3];

    private List<Fragment> fragments;

    private MyHttpConn httpConn;
    private Gson gson;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        positionTemp = 0;
        resources = getResources();

        fragments = new ArrayList<Fragment>();

        initView();
        initPager();

        httpConn = new MyHttpConn(mContext,false);
        gson = new Gson();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void initView() {
        titleHelper = new TitleHelper(mContext,R.id.my_account_top,"账户明细");
        my_account_money = (TextView) findViewById(R.id.my_account_money);
        my_account_buy = (TextView) findViewById(R.id.my_account_buy);
        textViews[0] = (TextView) findViewById(R.id.my_account_all);
        textViews[1] = (TextView) findViewById(R.id.my_account_come);
        textViews[2] = (TextView) findViewById(R.id.my_account_go);

        textViews[0].setOnClickListener(onclick);
        textViews[1].setOnClickListener(onclick);
        textViews[2].setOnClickListener(onclick);

        my_account_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent(RechareWebActivity.class);
            }
        });

    }

    private void initPager() {
        my_account_pager = (ViewPager) findViewById(R.id.my_account_pager);
        my_account_pager.setOffscreenPageLimit(2);
        my_account_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != positionTemp) {
                    textViews[position].setTextColor(resources.getColor(R.color.white));
                    textViews[position].setTextSize(18);
                    textViews[positionTemp].setTextColor(resources.getColor(R.color.main_red_pink));
                    textViews[positionTemp].setTextSize(15);

                    positionTemp = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < 3; i++) {
            AccountFragment fragment = new AccountFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("start_temp",i);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        my_account_pager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments));

    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.my_account_all:
                    if (positionTemp != 0) {
                        my_account_pager.setCurrentItem(0);
                    }

                    break;
                case R.id.my_account_come:
                    if (positionTemp != 1) {
                        my_account_pager.setCurrentItem(1);
                    }
                    break;
                case R.id.my_account_go:
                    if (positionTemp != 2) {
                        my_account_pager.setCurrentItem(2);
                    }
                    break;
            }
        }
    };

    private void getData(){
        String url = MyUrl.userUrl;
        Map<String, String> params = new HashMap<String, String>();
        httpConn.isToShowDialog(false);
        httpConn.httpPost(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                String data = json.optString("data");
                userInfo = gson.fromJson(data,UserInfo.class);
                my_account_money.setText(userInfo.getMoney());
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

}
