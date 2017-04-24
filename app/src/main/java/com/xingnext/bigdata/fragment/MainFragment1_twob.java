package com.xingnext.bigdata.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.xingnext.bigdata.GameDetailActivity;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.adapter.MatchAdapter;
import com.xingnext.bigdata.beans.GameInfo;
import com.xingnext.bigdata.factory.PullListViewHelperNew;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyStatic;
import com.xingnext.bigdata.utils.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lipo on 2017/4/23.
 */
public class MainFragment1_twob extends BaseFragment {

    private int start_temp;
    private View mainView;
    private boolean isVisibleToUser;
    private boolean isCreated = false;
    private boolean isFrist = true;

    private MyHttpConn httpConn;
    private Gson gson;
    private int page;

    private View maintwob_title,maintwob_pull;

    private PullListViewHelperNew pullHelper;
    private MatchAdapter adapter;
    private List<GameInfo> gameInfos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        start_temp = getArguments().getInt("start_temp", 1);
        return mainView = inflater.inflate(R.layout.main_fragment1_twob, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isCreated = true;
        httpConn = new MyHttpConn(mContext);
        gson = new Gson();

        gameInfos = new ArrayList<GameInfo>();
        initView();
        initPull();

        if (isVisibleToUser && isFrist) {
            page = 1;
            getData();
        }
    }

    private void initView() {
        maintwob_title = findViewById(R.id.maintwob_title);


        if (start_temp == 2) {
            maintwob_title.setVisibility(View.VISIBLE);
        }else {
            maintwob_title.setVisibility(View.GONE);
        }
    }
    private View findViewById(int id) {
        return mainView.findViewById(id);
    }

    private void initPull() {
        maintwob_pull = findViewById(R.id.maintwob_pull);
        pullHelper = new PullListViewHelperNew(mContext, maintwob_pull) {

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
                GameInfo infoG = gameInfos.get(position);
                Intent intentI = new Intent();
                intentI.putExtra("match_id", infoG.getMatch_id());
                intentI.putExtra("season_desc", infoG.getSeason_pre());
                startIntent(intentI, GameDetailActivity.class);
            }
        });

        pullHelper.setDivider(R.color.main_line, 1);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isCreated && isFrist) {
            page = 1;
            getData();
        }
    }


    public void getData() {
        String url = MyUrl.getMatchList;
        Map<String, String> params = new HashMap<String, String>();
        params.put("page", page + "");
        params.put("type_id", "1");
        params.put("period_sn", "");

        httpConn.httpGet(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                pullHelper.setRefreshComplete();
                fillPull(json);
            }

            @Override
            public void onError(int code, String msg) {
                pullHelper.setRefreshComplete();

            }
        });
    }

    private void fillPull(JSONObject json) {
        JSONArray data = json.optJSONArray("data");
        int lent = 0;
        if (data != null && data.length() > 0) {
            JSONObject dataJson = data.optJSONObject(0);

            JSONArray match_list = dataJson.optJSONArray("match_list");

            if (page == 1) {
                gameInfos.clear();
            }
            if (match_list != null) {
                lent = match_list.length();
                for (int i = 0; i < lent; i++) {
                    JSONObject matchJson = match_list.optJSONObject(i);
                    GameInfo gameInfo = gson.fromJson(matchJson.toString(), GameInfo.class);
                    gameInfos.add(gameInfo);
                }
            }
        }

        if (gameInfos.size() == 0) {
            pullHelper.setEmptyShow();
        } else {
            pullHelper.setEmptyDismiss();
            if (lent <= MyStatic.pageSize) {
                pullHelper.setPullMoreEnable(false);
            } else {
                pullHelper.setPullMoreEnable(true);
            }
        }

        initAdapter();
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new MatchAdapter(mContext, gameInfos);
            pullHelper.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

}
