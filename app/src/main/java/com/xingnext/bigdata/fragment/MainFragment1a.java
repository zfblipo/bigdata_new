package com.xingnext.bigdata.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xingnext.bigdata.GameDetailActivity;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.adapter.GameAdapter;
import com.xingnext.bigdata.adapter.GameHistoryAdapter;
import com.xingnext.bigdata.beans.GameInfo;
import com.xingnext.bigdata.factory.PullListViewHelperNew;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyStatic;
import com.xingnext.bigdata.utils.MyUrl;
import com.xingnext.bigdata.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lipo on 2017/3/15.
 */
public class MainFragment1a extends BaseFragment {

	private int start_temp;
	private View mainView;
	private boolean isVisibleToUser;
	private boolean isCreated = false;
	private boolean isFrist = true;

	private PullListViewHelperNew pullHelper;

	private View main1a_top, main1a_pull;
	private TextView main1a_time, main1a_number;
	
	private List<GameInfo> gameInfos,historyInfos;
	private GameAdapter gameAdapter;
	private GameHistoryAdapter historyAdapter;

	private MyHttpConn httpConn;
	private Gson gson;
	private int page;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		start_temp = getArguments().getInt("start_temp", 0);
		return mainView = inflater.inflate(R.layout.main_fragment1a, null);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		isCreated = true;
		gameInfos = new ArrayList<GameInfo>();
		historyInfos = new ArrayList<GameInfo>();
		httpConn = new MyHttpConn(mContext);
		gson = new Gson();

		initView();
		initPull();

		if(isVisibleToUser&&isFrist){
			page = 1;
			getData();
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		this.isVisibleToUser = isVisibleToUser;
		if(isCreated&&isFrist){
			page = 1;
			getData();
		}
	}

	private void initView() {
		main1a_top = mainView.findViewById(R.id.main1a_top);
		main1a_time = (TextView) mainView.findViewById(R.id.main1a_time);
		main1a_number = (TextView) mainView.findViewById(R.id.main1a_number);
		
		if(start_temp == 0){
			main1a_top.setVisibility(View.VISIBLE);
		}else{
			main1a_top.setVisibility(View.GONE);
		}
		
	}

	private void initPull() {
		main1a_pull = mainView.findViewById(R.id.main1a_pull);
		pullHelper = new PullListViewHelperNew(mContext, main1a_pull) {

			@Override
			protected void pullRefersh() {
				page = 1;
				getData();
			}

			@Override
			protected void pullMore() {
				page++;
				getData();
			}

		};

		pullHelper.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				GameInfo infoG = historyInfos.get(position);
				Intent intentI = new Intent();
				intentI.putExtra("match_id",infoG.getMatch_id());
				intentI.putExtra("season_desc",infoG.getSeason_pre());
				startIntent(intentI, GameDetailActivity.class);
			}
		});

		pullHelper.setDivider(R.color.main_line, 1);
		
	}
	
	private void initAdapter(){
		if(start_temp == 0){
			initGameAdapter();
		}else{
			initHistoryAdapter();
		}
	}
	
	private void initGameAdapter(){
		if(gameAdapter == null){
			gameAdapter = new GameAdapter(mContext, gameInfos);
			pullHelper.setAdapter(gameAdapter);
		}else{
			gameAdapter.notifyDataSetChanged();
		}
	}
	
	private void initHistoryAdapter(){
		if(historyAdapter == null){
			historyAdapter = new GameHistoryAdapter(mContext, historyInfos);
			pullHelper.setAdapter(historyAdapter);
		}else{
			historyAdapter.notifyDataSetChanged();
		}
	}

//	public void scrollToTop(){
//		pullHelper.scrollToTop();
//	}

	private void getData(){

		String url = MyUrl.getHistoryList+ TimeUtils.getNowDate();
		Map<String, String> params = new HashMap<String, String>();
		params.put("page", page + "");
		httpConn.httpGet(url, params, new MyHttpConn.OnCallBack() {
			@Override
			public void Success(JSONObject json) {
				pullHelper.setRefreshComplete();
				if (page == 1){
					historyInfos.clear();
				}
				JSONArray data = json.optJSONArray("data");
				int cLent = 0;
				if(data!=null&&data.length()>0){
					JSONObject dataJson = data.optJSONObject(0);

					JSONArray match_list = dataJson.optJSONArray("match_list");
					if(match_list!=null){
						cLent = match_list.length();
						for (int j = 0; j < cLent; j++) {
							JSONObject matchJson = match_list.optJSONObject(j);
							GameInfo gameInfo = gson.fromJson(matchJson.toString(),GameInfo.class);
							JSONArray spf = matchJson.optJSONArray("spf");
							GameInfo.setSpf(gameInfo,spf);
							historyInfos.add(gameInfo);
						}
					}
				}

				if (historyInfos.size() == 0) {
					pullHelper.setEmptyShow();
				} else {
					pullHelper.setEmptyDismiss();
					if (cLent <= MyStatic.pageSize) {
						pullHelper.setPullMoreEnable(false);
					} else {
						pullHelper.setPullMoreEnable(true);
					}
				}

				initAdapter();

			}

			@Override
			public void onError(int code, String msg) {
				pullHelper.setRefreshComplete();
				if(code == -10){
//					pullHelper.setNOWIFI();
				}
			}
		});
	}

}
