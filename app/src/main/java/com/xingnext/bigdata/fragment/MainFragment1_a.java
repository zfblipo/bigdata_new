package com.xingnext.bigdata.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lipo.utils.DisplayUtil;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.adapter.MyFragmentPagerAdapter;
import com.xingnext.bigdata.factory.TitleHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */
public class MainFragment1_a extends BaseFragment {

    private View mainView;
    private int dp20;

    private LayoutInflater inflater;
    private DisplayMetrics metrics;
    private int line_width;

    private int seq_temp;

    private TitleHelper titleHelper;
    private TextView[] textViews = new TextView[2];
    private View main1_a_line,maina_status;
    private ViewPager main1_a_frame_pager;

    private List<Fragment> fragments;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mainView = inflater.inflate(R.layout.main_fragment1_a, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dp20 = DisplayUtil.dip2px(getActivity(), 20);
        fragments = new ArrayList<Fragment>();

        inflater = LayoutInflater.from(getActivity());
        metrics = getResources().getDisplayMetrics();

        initView();
        initLine();
        initPager();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    private void initView() {
        titleHelper = new TitleHelper(mContext, R.id.main1_a_top, getResources().getString(R.string.app_name));
        titleHelper.dismissBack();
        textViews[0] = (TextView) mainView.findViewById(R.id.main1_a_plan);
        textViews[1] = (TextView) mainView.findViewById(R.id.main1_a_history);

        textViews[0].setOnClickListener(onclick);
        textViews[1].setOnClickListener(onclick);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            maina_status = mainView.findViewById(R.id.maina_status);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,55);
            maina_status.setLayoutParams(params);
        }
    }

    private void initLine() {
        main1_a_line = mainView.findViewById(R.id.main1_a_line);
        line_width = metrics.widthPixels / 2;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) main1_a_line.getLayoutParams();
        params.width = line_width - DisplayUtil.dip2px(getActivity(), 40);
        main1_a_line.setLayoutParams(params);
        main1_a_line.setTranslationX(dp20);
    }

    private void initPager() {
        main1_a_frame_pager = (ViewPager) mainView.findViewById(R.id.main1_a_frame_pager);
        main1_a_frame_pager.setOffscreenPageLimit(1);

        GameExpandFragment fragment1 = new GameExpandFragment();
        fragments.add(fragment1);

        MainFragment1a listFragment = new MainFragment1a();
        Bundle bundle = new Bundle();
        bundle.putInt("start_temp", 1);
        listFragment.setArguments(bundle);
        fragments.add(listFragment);


        main1_a_frame_pager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager(), fragments));

        main1_a_frame_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int widthX = line_width * position + positionOffsetPixels / 2;
                main1_a_line.setTranslationX(widthX + dp20);
            }

            @Override
            public void onPageSelected(int position) {
                changeLabel(position);
                seq_temp = position;
            }

            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void changeLabel(int position) {
        textViews[seq_temp].setTextColor(getResources().getColor(R.color.main_text3));
        textViews[position].setTextColor(getResources().getColor(R.color.main_orange));
    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main1_a_plan:
                    main1_a_frame_pager.setCurrentItem(0);
                    break;
                case R.id.main1_a_history:
                    main1_a_frame_pager.setCurrentItem(1);
                    break;
            }
        }
    };

}
