package com.xingnext.bigdata.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingnext.bigdata.R;
import com.xingnext.bigdata.adapter.MyFragmentPagerAdapter;
import com.xingnext.bigdata.adapter.TextChoiceAdapter;
import com.xingnext.bigdata.beans.GameTypeInfo;
import com.xingnext.bigdata.factory.TitleHelper;
import com.xingnext.bigdata.utils.MyStatic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lipo on 2017/4/23.
 */
public class MainFragment1_two extends BaseFragment {

    private View mainView;

    private TitleHelper titleHelper;
    private View maina_two_status;

    private View[] views = new View[3];
    private View[] icons = new View[3];

    private TextView maina_two_item1_text;
    private ImageView maina_two_item1_next;

    private ViewPager maina_two_pager;
    private View maina_two_remark;
    private GridView maina_two_label;

    private TextChoiceAdapter textChoiceAdapter;

    private int typePosition = 0;
    private int tempPosition = 0;
    private boolean isShowType = false;

    private List<Fragment> fragments;
    private MainFragment1_twoa twoaFragment1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mainView = inflater.inflate(R.layout.main_fragment1_two, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragments = new ArrayList<Fragment>();

        initView();
        initPager();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    private void initView() {
        titleHelper = new TitleHelper(mContext, R.id.main1_two_top, getResources().getString(R.string.app_name));
        titleHelper.dismissBack();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            maina_two_status = mainView.findViewById(R.id.maina_two_status);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 55);
            maina_two_status.setLayoutParams(params);
        }

        views[0] = findViewById(R.id.maina_two_item1);
        views[1] = findViewById(R.id.maina_two_item2);
        views[2] = findViewById(R.id.maina_two_item3);

        icons[0] = findViewById(R.id.maina_two_item1_icon);
        icons[1] = findViewById(R.id.maina_two_item2_icon);
        icons[2] = findViewById(R.id.maina_two_item3_icon);

        maina_two_item1_text = (TextView) findViewById(R.id.maina_two_item1_text);
        maina_two_item1_next = (ImageView) findViewById(R.id.maina_two_item1_next);
        maina_two_remark = findViewById(R.id.maina_two_remark);
        maina_two_label = (GridView) findViewById(R.id.maina_two_label);

        textChoiceAdapter = new TextChoiceAdapter(mContext, MyStatic.gameTypeInfos);
        maina_two_label.setAdapter(textChoiceAdapter);


        maina_two_label.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != typePosition) {
                    GameTypeInfo gameTypeInfo = MyStatic.gameTypeInfos.get(position);
                    gameTypeInfo.setChoiced(true);
                    MyStatic.gameTypeInfos.get(typePosition).setChoiced(false);
                    maina_two_item1_text.setText(gameTypeInfo.getTitle());
                    MainFragment1_twoa.type_id = gameTypeInfo.getId();
                    typePosition = position;
                    dismissLabel();
                    textChoiceAdapter.notifyDataSetChanged();

                    twoaFragment1.refreshSn();

                }
            }
        });

        views[0].setOnClickListener(onclick);
        views[1].setOnClickListener(onclick);
        views[2].setOnClickListener(onclick);
        maina_two_remark.setOnClickListener(onclick);
    }

    private void initPager() {
        maina_two_pager = (ViewPager) findViewById(R.id.maina_two_pager);
        maina_two_pager.setOffscreenPageLimit(2);
        maina_two_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                icons[tempPosition].setVisibility(View.GONE);
                icons[position].setVisibility(View.VISIBLE);
                tempPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        twoaFragment1 = new MainFragment1_twoa();
        MainFragment1_twob twoaFragment2 = new MainFragment1_twob();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("start_temp", 1);
        twoaFragment2.setArguments(bundle2);

        MainFragment1_twob twoaFragment3 = new MainFragment1_twob();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("start_temp", 2);
        twoaFragment3.setArguments(bundle3);

        fragments.add(twoaFragment1);
        fragments.add(twoaFragment2);
        fragments.add(twoaFragment3);

        maina_two_pager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager(), fragments));
    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.maina_two_item1:
                    if(tempPosition == 0){
                        if(isShowType){
                            dismissLabel();
                        }else{
                            showLabel();
                        }

                    }else{
                        dismissLabel();
                        maina_two_item1_next.setImageResource(R.mipmap.up_icon);
                        maina_two_pager.setCurrentItem(0);
                    }
                    break;
                case R.id.maina_two_item2:
                    if(tempPosition != 1){
                        maina_two_pager.setCurrentItem(1);
                    }
                    dismissLabel();
                    break;
                case R.id.maina_two_item3:
                    if(tempPosition != 2){
                        maina_two_pager.setCurrentItem(2);
                    }
                    dismissLabel();
                    break;
                case R.id.maina_two_remark:
                    dismissLabel();
                    break;
            }
        }
    };


    private void showLabel(){
        if(!isShowType){
            isShowType = true;
            maina_two_remark.setVisibility(View.VISIBLE);
            maina_two_label.setVisibility(View.VISIBLE);
            maina_two_item1_next.setImageResource(R.mipmap.down_icon);
        }
    }

    private void dismissLabel(){
        if(isShowType){
            isShowType = false;
            maina_two_remark.setVisibility(View.GONE);
            maina_two_label.setVisibility(View.GONE);
            maina_two_item1_next.setImageResource(R.mipmap.up_icon);
        }
    }

    private View findViewById(int id) {
        return mainView.findViewById(id);
    }

}
