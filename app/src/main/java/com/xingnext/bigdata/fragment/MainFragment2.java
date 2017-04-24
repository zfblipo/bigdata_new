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
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.adapter.MyFragmentPagerAdapter;
import com.xingnext.bigdata.beans.NoticeInfo;
import com.xingnext.bigdata.factory.TitleHelper;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lipo on 2017/3/13.
 */
public class MainFragment2 extends BaseFragment {

    private View mainView;
    private View mainb_top,mainb_ads,mainb_status;
    private TextView mainb_ads_content;
    
    private TitleHelper titleHelper;

	private DisplayMetrics metrics;
	private ViewPager mainb_pager;
	private View mainb_line;

	private TextView[] textViews = new TextView[3];
	private int seq_temp;

	private List<Fragment> fragments;

	private ViewFlipper mainb_ads_fipper;
	private LayoutInflater inflater;
	private MyHttpConn httpConn;
	private Gson gson;
	private List<NoticeInfo> noticeInfos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		this.inflater = inflater;
		return mainView = inflater.inflate(R.layout.main_fragment2,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
		metrics = getResources().getDisplayMetrics();
		fragments = new ArrayList<Fragment>();

		httpConn = new MyHttpConn(mContext,false);
		gson = new Gson();
		noticeInfos = new ArrayList<NoticeInfo>();

        initView();
		initLine();
		initPager();
		getData();
    }
    
    private void initView(){
    	mainb_top = mainView.findViewById(R.id.mainb_top);
		mainb_ads_fipper = (ViewFlipper) mainView.findViewById(R.id.mainb_ads_fipper);
    	mainb_ads = mainView.findViewById(R.id.mainb_ads);
    	mainb_ads_content = (TextView) mainView.findViewById(R.id.mainb_ads_content);
    	
    	titleHelper = new TitleHelper(mContext, R.id.mainb_top,"红单推荐");
    	titleHelper.dismissBack();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			mainb_status = mainView.findViewById(R.id.mainb_status);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,55);
			mainb_status.setLayoutParams(params);
		}

		textViews[0] = (TextView) mainView.findViewById(R.id.mainb_all);
		textViews[1] = (TextView) mainView.findViewById(R.id.mainb_single);
		textViews[2] = (TextView) mainView.findViewById(R.id.mainb_double);

		textViews[0].setOnClickListener(onclick);
		textViews[1].setOnClickListener(onclick);
		textViews[2].setOnClickListener(onclick);

		mainb_ads_fipper.setInAnimation(AnimationUtils.loadAnimation(mContext,
				R.anim.in_bottomtop));//设置View进入屏幕时候使用的动画
		mainb_ads_fipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,
				R.anim.out_bottomtop));  //设置View退出屏幕时候使用的动画
		mainb_ads_fipper.setFlipInterval(2000);//设置自动切换的间隔时间
    }

	private void initLine() {
		mainb_line = mainView.findViewById(R.id.mainb_line);
		mainb_line.post(new Runnable() {
			@Override
			public void run() {
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mainb_line.getLayoutParams();
				params.width = metrics.widthPixels / 3;
				mainb_line.setLayoutParams(params);
			}
		});
	}

	private void initPager() {
		mainb_pager = (ViewPager) mainView.findViewById(R.id.mainb_pager);
		mainb_pager.setOffscreenPageLimit(2);
        for (int i = 0; i < 3; i++) {
            MainFragment2a listFragment = new MainFragment2a();
            Bundle bundle = new Bundle();
            bundle.putInt("start_temp", i);
            listFragment.setArguments(bundle);
            fragments.add(listFragment);
        }

		mainb_pager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager(), fragments));

		mainb_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				int widthX = metrics.widthPixels / 3 * position + positionOffsetPixels / 3;
				mainb_line.setTranslationX(widthX);
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
		textViews[seq_temp].setTextColor(getResources().getColor(R.color.main_text9));
		textViews[position].setTextColor(getResources().getColor(R.color.main_color));
	}

	private View.OnClickListener onclick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.mainb_all:
					mainb_pager.setCurrentItem(0);
					break;
				case R.id.mainb_single:
					mainb_pager.setCurrentItem(1);
					break;
				case R.id.mainb_double:
					mainb_pager.setCurrentItem(2);
					break;
			}
		}
	};

	private void getData(){

		String url = MyUrl.noticeList;
		Map<String, String> params = new HashMap<String, String>();

		httpConn.httpGet(url, params, new MyHttpConn.OnCallBack() {
			@Override
			public void Success(JSONObject json) {
				JSONArray data = json.optJSONArray("data");
				int lent = 0;
				if(data!=null){
					lent = data.length();
					for (int i = 0; i < lent; i++) {
						NoticeInfo info = gson.fromJson(data.optString(i),NoticeInfo.class);
						if(info!=null){
							TextView textView = (TextView) inflater.inflate(R.layout.cell_ads_text,null);
							textView.setText(info.getTitle());
							mainb_ads_fipper.addView(textView);
							noticeInfos.add(info);
						}
					}
				}

				if(noticeInfos.size()>0){
					if(!mainb_ads_fipper.isFlipping()){
						mainb_ads_fipper.startFlipping();
					}
				}
			}

			@Override
			public void onError(int code, String msg) {
			}
		});
	}

}
