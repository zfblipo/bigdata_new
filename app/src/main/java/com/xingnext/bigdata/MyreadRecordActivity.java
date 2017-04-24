package com.xingnext.bigdata;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.xingnext.bigdata.adapter.MyFragmentPagerAdapter;
import com.xingnext.bigdata.factory.TitleHelper;
import com.xingnext.bigdata.fragment.RecordGuessFragmentTwo;
import com.xingnext.bigdata.fragment.RecordRecomFragment;

import java.util.ArrayList;
import java.util.List;

public class MyreadRecordActivity extends BaseFragmentActivity {

    private Resources resources;

    private TitleHelper titleHelper;
    private ViewPager myread_record_pager;
    private List<Fragment> fragments;

    private int positionTemp;
    private TextView[] textViews = new TextView[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myread_record);

        fragments = new ArrayList<Fragment>();
        positionTemp = 0;
        resources = getResources();

        initView();
        initPager();

    }

    private void initView() {
        titleHelper = new TitleHelper(this, R.id.myread_record_top, "订阅记录");

        textViews[0] = (TextView) findViewById(R.id.myread_record_guess);
        textViews[1] = (TextView) findViewById(R.id.myread_record_recomm);

        textViews[0].setOnClickListener(onclick);
        textViews[1].setOnClickListener(onclick);
    }

    private void initPager() {
        myread_record_pager = (ViewPager) findViewById(R.id.myread_record_pager);

        RecordGuessFragmentTwo fragment1 = new RecordGuessFragmentTwo();
        RecordRecomFragment fragment2 = new RecordRecomFragment();

        fragments.add(fragment1);
        fragments.add(fragment2);

        myread_record_pager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments));

        myread_record_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    }


    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.myread_record_guess:
                    if (positionTemp != 0) {
                        myread_record_pager.setCurrentItem(0);
                    }

                    break;
                case R.id.myread_record_recomm:
                    if (positionTemp != 1) {
                        myread_record_pager.setCurrentItem(1);
                    }
                    break;
            }
        }
    };


}
