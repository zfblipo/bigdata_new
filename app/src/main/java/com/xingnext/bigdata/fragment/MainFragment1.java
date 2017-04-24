package com.xingnext.bigdata.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lipo.utils.DisplayUtil;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.adapter.ImagePagerNewAdapter;
import com.xingnext.bigdata.adapter.MyFragmentPagerAdapter;
import com.xingnext.bigdata.beans.ImageInfo;
import com.xingnext.bigdata.beans.PrizeInfo;
import com.xingnext.bigdata.factory.TitleHelper;
import com.xingnext.bigdata.utils.FixedSpeedScroller;
import com.xingnext.bigdata.views.MyMainScrollView;
import com.xingnext.bigdata.views.MyPagerIndicator;
import com.xingnext.bigdata.views.PullToRefreshMyScrollView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lipo on 2017/3/13.
 */
public class MainFragment1 extends BaseFragment {

    private Handler handler = new Handler();
    private int timeInterval = 4000;
    public static int seq_temp;
    private int dp20;

    private LayoutInflater inflater;
    private DisplayMetrics metrics;
    private int line_width;

    private View mainView;
    private TitleHelper titleHelper;

    private ViewPager maina_pager,maina_frame_pager;
    private MyPagerIndicator maina_indicator;
    private View maina_frame,lin_mainta_a1,lin_mainta_a2,lin_mainta_a3,lin_mainta_a4;
    private ViewFlipper main1_viewfli;
    private TextView maina_plan,maina_history;
    private View maina_line;
    private ImageButton main_imgbtn_cell;
    private PullToRefreshMyScrollView main1_pull;
    private MyMainScrollView refreshablescroView;
    private View contentView;

    private ImagePagerNewAdapter imagePagerNewAdapter;
    private List<ImageInfo> imageInfos;

    private List<PrizeInfo> prizeInfos;
    private Timer timerRun;

    private TextView[] textViews = new TextView[2];

    private List<Fragment> fragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mainView = inflater.inflate(R.layout.main_fragment1,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageInfos = new ArrayList<ImageInfo>();
        prizeInfos = new ArrayList<PrizeInfo>();
        fragments = new ArrayList<Fragment>();
        dp20 = DisplayUtil.dip2px(getActivity(),20);

        inflater = LayoutInflater.from(getActivity());
        metrics = getResources().getDisplayMetrics();


        initView();
        initAdsView();
        initViewFlipper();

        initSeq();
        initLine();
        initPager();

        contentView.post(new Runnable() {
            @Override
            public void run() {
                toTop();
                initPageHeight();
            }
        });

    }

    public void onResume() {
        super.onResume();
        handler.postDelayed(pagerTask, timeInterval);
    }

    private void initView(){
        titleHelper = new TitleHelper(mContext,R.id.main1_top,"竞彩大数据");
        titleHelper.dismissBack();

        contentView = getActivity().getWindow().findViewById(Window.ID_ANDROID_CONTENT);

        maina_frame_pager = (ViewPager) findViewById(R.id.maina_frame_pager);
        lin_mainta_a1 = findViewById(R.id.lin_mainta_a1);
        lin_mainta_a2 = findViewById(R.id.lin_mainta_a2);
        lin_mainta_a3 = findViewById(R.id.lin_mainta_a3);
        lin_mainta_a4 = findViewById(R.id.lin_mainta_a4);
        maina_plan = (TextView) findViewById(R.id.maina_plan);
        maina_history = (TextView) findViewById(R.id.maina_history);
        maina_line = findViewById(R.id.maina_line);
        main_imgbtn_cell = (ImageButton) findViewById(R.id.main_imgbtn_cell);
    }

    private void initAdsView(){
        maina_pager = (ViewPager) findViewById(R.id.maina_pager);
        maina_indicator = (MyPagerIndicator) findViewById(R.id.maina_indicator);

        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Interpolator sInterpolator = new AccelerateDecelerateInterpolator();
            FixedSpeedScroller scroller = new FixedSpeedScroller(
                    maina_pager.getContext(), sInterpolator);
            scroller.setFixedDuration(500);
            mScroller.set(maina_pager, scroller);
        } catch (Exception e) {

        }
        maina_pager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler.removeCallbacks(pagerTask);
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.postDelayed(pagerTask, timeInterval);
                        break;
                }
                return false;
            }
        });

        fillImagePager();
    }

    private void fillImagePager(){
        int lent = 4;
        for (int i = 0; i < lent; i++) {
            imageInfos.add(new ImageInfo("http://img0.imgtn.bdimg.com/it/u=3611779475,973764155&fm=23&gp=0.jpg"));
        }
        
        imagePagerNewAdapter = new ImagePagerNewAdapter(getActivity(),imageInfos,1);
        maina_pager.setAdapter(imagePagerNewAdapter);

        maina_pager.setCurrentItem(lent * 50);
        maina_indicator.setPager(maina_pager, lent);
    }


    //初始化上下轮播的滚动条
    private void initViewFlipper() {
        main1_viewfli=(ViewFlipper)mainView.findViewById(R.id.main1_viewfli);
        prizeInfos = new ArrayList<PrizeInfo>();

        timerRun = new Timer();
        timerRun.schedule(new TimerTask() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        moveTonext();
                    }
                });

            }
        }, 0, 10000);

        fillViewFlipper();

    }

    private void fillViewFlipper(){
        for (int i = 0; i < 3; i++) {
            View view = inflater.inflate(R.layout.item_viewflipper,null);
            main1_viewfli.addView(view);
        }
        main1_viewfli.startFlipping();
    }

    private void moveTonext() {
        main1_viewfli.setInAnimation(getActivity(), R.anim.in_bottomtop);
        main1_viewfli.setOutAnimation(getActivity(), R.anim.out_bottomtop);
        main1_viewfli.showNext();
    }


    private int current;
    private Runnable pagerTask = new Runnable() {
        @Override
        public void run() {
            current = maina_pager.getCurrentItem();
            current++;
            maina_pager.setCurrentItem(current);
            handler.postDelayed(pagerTask, timeInterval);
        }
    };

    private void initSeq() {
        main1_pull = (PullToRefreshMyScrollView) mainView.findViewById(R.id.main1_pull);
        maina_frame = mainView.findViewById(R.id.maina_frame);
        main1_pull.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        refreshablescroView = main1_pull.getRefreshableView();
//        maina_frame = mainView.findViewById(R.id.maina_frame);
        textViews[0] = (TextView) mainView.findViewById(R.id.maina_plan);
        textViews[1] = (TextView) mainView.findViewById(R.id.maina_history);
        for (int i = 0; i < 2; i++) {
            textViews[i].setOnClickListener(onclick);
        }

        main1_pull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<MyMainScrollView>() {

            public void onPullDownToRefresh(PullToRefreshBase<MyMainScrollView> refreshView) {
                main1_pull.onRefreshComplete();
            }

            public void onPullUpToRefresh(PullToRefreshBase<MyMainScrollView> refreshView) {

            }
        });

    }

    private void initLine() {
        maina_line = mainView.findViewById(R.id.maina_line);
        line_width = metrics.widthPixels / 2;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) maina_line.getLayoutParams();
        params.width = line_width-DisplayUtil.dip2px(getActivity(),40);
        maina_line.setLayoutParams(params);
        maina_line.setTranslationX(dp20);
    }

    //添加三个相同的fragment
    private void initPager() {
        maina_frame_pager = (ViewPager) mainView.findViewById(R.id.maina_frame_pager);
        maina_frame_pager.setOffscreenPageLimit(1);
        for (int i = 0; i < 2; i++) {
            MainFragment1a listFragment = new MainFragment1a();
            Bundle bundle = new Bundle();
            bundle.putInt("start_temp", i);
            listFragment.setArguments(bundle);
            fragments.add(listFragment);
        }

        maina_frame_pager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager(), fragments));

        maina_frame_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int widthX = line_width * position + positionOffsetPixels / 2;
                maina_line.setTranslationX(widthX+dp20);
            }

            @Override
            public void onPageSelected(int position) {
                toTop();
                changeLabel(position);
//                ((MainFragment1a)fragments.get(seq_temp)).scrollToTop();
                seq_temp = position;
            }

            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initPageHeight() {
        int contentHeight = contentView.getMeasuredHeight();
        ViewGroup.LayoutParams params = maina_frame_pager.getLayoutParams();
        params.height = contentHeight - DisplayUtil.dip2px(getActivity(), 130);
        maina_frame_pager.setLayoutParams(params);
    }

    public void toTop() {
        maina_frame.setFocusable(true);
        maina_frame.setFocusableInTouchMode(true);
        maina_frame.requestFocus();
    }

    private void changeLabel(int position) {
        textViews[seq_temp].setTextColor(getResources().getColor(R.color.main_text3));
        textViews[position].setTextColor(getResources().getColor(R.color.main_orange));
    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public View findViewById(int id){
        return  mainView.findViewById(id);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(pagerTask);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        timerRun.cancel();
    }

}
