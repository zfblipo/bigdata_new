package com.xingnext.bigdata.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.xingnext.bigdata.GameDetailActivity;
import com.xingnext.bigdata.R;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lipo on 2017/3/13.
 */
public class MainFragment3 extends BaseFragment {

    private View mainView, mainc_status;
    private boolean isFrist = true;

    private TextView mainc_title_name, mainc_title_button,
            mainc_score_num, mainc_score_rate;
    private Button mainc_time1, mainc_time2;
    private View mainc_pull;

    private List<GameInfo> historyInfos;
    private GameHistoryAdapter historyAdapter;

    private PullListViewHelperNew pullHelper;

    private View mianc_mask;
    private MaterialCalendarView mianc_calendar;
    private boolean isShowing;

    private MyHttpConn httpConn;
    private Gson gson;
    private int page;
    private String dayStr;
    private Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mainView = inflater.inflate(R.layout.main_fragment3, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isShowing = false;
        historyInfos = new ArrayList<GameInfo>();

        httpConn = new MyHttpConn(mContext);
        gson = new Gson();
        page = 1;
        calendar = Calendar.getInstance();

        initView();
        initPull();
        initCalendar();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden & isFrist ) {
            getData();
        }

    }

    private void initView() {
        mainc_title_name = (TextView) findViewById(R.id.mainc_title_name);
        mainc_title_button = (TextView) findViewById(R.id.mainc_title_button);
        mainc_time1 = (Button) findViewById(R.id.mainc_time1);
        mainc_time2 = (Button) findViewById(R.id.mainc_time2);
        mainc_score_num = (TextView) findViewById(R.id.mainc_score_num);
        mainc_score_rate = (TextView) findViewById(R.id.mainc_score_rate);
        mainc_title_button.setOnClickListener(onclick);

        initButtonSelector();
        initTitleDate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mainc_status = mainView.findViewById(R.id.mainc_status);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 55);
            mainc_status.setLayoutParams(params);
        }
    }

    private void initTitleDate() {
        mainc_title_button.setText(TimeUtils.getYMDFromCalender(calendar));
    }

    private void initButtonSelector() {
        mainc_time1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mainc_time1.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case MotionEvent.ACTION_UP:
                        mainc_time1.setTextColor(getResources().getColor(R.color.main_color));
                        break;
                }

                return false;
            }
        });

        mainc_time2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mainc_time2.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case MotionEvent.ACTION_UP:
                        mainc_time2.setTextColor(getResources().getColor(R.color.main_color));
                        break;
                }

                return false;
            }
        });
        mainc_time1.setOnClickListener(onclick);
        mainc_time2.setOnClickListener(onclick);
    }

    private void initPull() {
        mainc_pull = findViewById(R.id.mainc_pull);
        pullHelper = new PullListViewHelperNew(mContext, mainc_pull) {

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
                intentI.putExtra("match_id", infoG.getMatch_id());
                intentI.putExtra("season_desc", infoG.getSeason_pre());
                startIntent(intentI, GameDetailActivity.class);
            }
        });

        pullHelper.setDivider(R.color.main_line, 1);

    }

    private void initCalendar() {
        mianc_mask = findViewById(R.id.mianc_mask);
        mianc_calendar = (MaterialCalendarView) findViewById(R.id.mianc_calendar);

        mianc_calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                calendar = calendarDay.getCalendar();
                page = 1;
                initTitleDate();
                hiddenCalendar();
                getData();
            }
        });
        mianc_mask.setOnClickListener(onclick);
    }

    private void showCalendar() {
        isShowing = true;
        mianc_calendar.setCurrentDate(calendar);
        mianc_mask.setVisibility(View.VISIBLE);
        mianc_calendar.setVisibility(View.VISIBLE);
    }

    private void hiddenCalendar() {
        isShowing = false;
        mianc_mask.setVisibility(View.GONE);
        mianc_calendar.setVisibility(View.GONE);
    }

    private View findViewById(int id) {
        return mainView.findViewById(id);
    }

    private void initAdapter() {
        if (historyAdapter == null) {
            historyAdapter = new GameHistoryAdapter(mContext, historyInfos);
            pullHelper.setAdapter(historyAdapter);
        } else {
            historyAdapter.notifyDataSetChanged();
        }
    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.mainc_title_button:
                    if (isShowing) {
                        hiddenCalendar();
                    } else {
                        showCalendar();
                    }
                    break;
                case R.id.mianc_mask:
                    hiddenCalendar();
                    break;
                case R.id.mainc_time1:
                    page = 1;
                    calendar.add(Calendar.DATE, -1);
                    initTitleDate();
                    getData();
                    break;
                case R.id.mainc_time2:
                    page = 1;
                    calendar.add(Calendar.DATE, 1);
                    initTitleDate();
                    getData();
                    break;
            }
        }
    };

    String match_count = "0";
    String period_time = "";
    String hit = "";
    String rate = "";

    private void getData() {

        dayStr = TimeUtils.getYMDFromCalender(calendar);

        String url = MyUrl.getHistoryList + dayStr;
        Map<String, String> params = new HashMap<String, String>();
        params.put("page", page + "");
        httpConn.httpGet(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                isFrist = false;
                pullHelper.setRefreshComplete();
                if (page == 1) {
                    historyInfos.clear();
                }
                int cLent = 0;
                JSONArray data = json.optJSONArray("data");
                if (data != null && data.length() > 0) {
                    JSONObject dataJson = data.optJSONObject(0);
                    match_count = dataJson.optString("match_count");
                    period_time = dataJson.optString("period_time");
                    hit = dataJson.optString("hit");
                    rate = dataJson.optString("rate");

                    JSONArray match_list = dataJson.optJSONArray("match_list");
                    if (match_list != null) {
                        cLent = match_list.length();
                        for (int j = 0; j < cLent; j++) {
                            JSONObject matchJson = match_list.optJSONObject(j);
                            GameInfo gameInfo = gson.fromJson(matchJson.toString(), GameInfo.class);
                            JSONArray spf = matchJson.optJSONArray("spf");
                            GameInfo.setSpf(gameInfo, spf);
                            historyInfos.add(gameInfo);
                        }
                    }
                }

                calendar = TimeUtils.stringToCalendar(period_time);
                mainc_title_button.setText(period_time);
                mainc_score_num.setText("共" + match_count + "场，预测命中" + hit + "场，");
                mainc_score_rate.setText("命中率"+rate+"%");
                initAdapter();

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

            }

            @Override
            public void onError(int code, String msg) {
                pullHelper.setRefreshComplete();
            }
        });
    }

}
