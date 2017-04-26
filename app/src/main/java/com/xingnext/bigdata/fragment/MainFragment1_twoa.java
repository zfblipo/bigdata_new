package com.xingnext.bigdata.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xingnext.bigdata.GameDetailActivity;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.adapter.MatchAdapter;
import com.xingnext.bigdata.adapter.TextChoiceAdapter;
import com.xingnext.bigdata.beans.GameInfo;
import com.xingnext.bigdata.beans.GameTypeInfo;
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
public class MainFragment1_twoa extends BaseFragment {

    public static String type_id = "1";
    private int start_temp;

    private View mainView;
    private boolean isVisibleToUser;
    private boolean isCreated = false;
    private boolean isFrist = true;

    private MyHttpConn httpConn;
    private Gson gson;
    private int page;

    private TextView maina_twoa_up, maina_twoa_next, maina_twoa_content;
    private View maina_twoa_middle, maina_twoa_pull, maina_twoa_remark;
    private ImageView maina_twoa_icon;

    private GridView maina_twoa_grid;
    private TextChoiceAdapter textChoiceAdapter;
    private List<GameTypeInfo> gameTypeInfos;
    private boolean isShowType = false;

    private String period_sn = "";
    private int typePosition = 0;
    private boolean isRefreshSn = true;

    private PullListViewHelperNew pullHelper;
    private MatchAdapter adapter;
    private List<GameInfo> gameInfos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        start_temp = getArguments().getInt("start_temp", 1);
        return mainView = inflater.inflate(R.layout.main_fragment1_twoa, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isCreated = true;
        httpConn = new MyHttpConn(mContext);
        gson = new Gson();

        gameInfos = new ArrayList<GameInfo>();
        gameTypeInfos = new ArrayList<GameTypeInfo>();

        initView();
        initPull();

        if (isVisibleToUser && isFrist) {
            page = 1;
            getData();
        }
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

    private void initView() {
        maina_twoa_up = (TextView) findViewById(R.id.maina_twoa_up);
        maina_twoa_next = (TextView) findViewById(R.id.maina_twoa_next);
        maina_twoa_content = (TextView) findViewById(R.id.maina_twoa_content);
        maina_twoa_middle = findViewById(R.id.maina_twoa_middle);
        maina_twoa_pull = findViewById(R.id.maina_twoa_pull);
        maina_twoa_remark = findViewById(R.id.maina_twoa_remark);
        maina_twoa_icon = (ImageView) findViewById(R.id.maina_twoa_icon);
        maina_twoa_grid = (GridView) findViewById(R.id.maina_twoa_grid);

        maina_twoa_up.setOnClickListener(onclick);
        maina_twoa_next.setOnClickListener(onclick);
        maina_twoa_middle.setOnClickListener(onclick);
        maina_twoa_remark.setOnClickListener(onclick);

        maina_twoa_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != typePosition) {
                    refreshGrid(position);
                    dismissLabel();
                }
            }
        });
    }

    private void initPull() {
        maina_twoa_pull = mainView.findViewById(R.id.maina_twoa_pull);
        pullHelper = new PullListViewHelperNew(mContext, maina_twoa_pull) {

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


    private View findViewById(int id) {
        return mainView.findViewById(id);
    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.maina_twoa_up:
                    int pnew = typePosition + 1;
                    if (pnew < gameTypeInfos.size()) {
                        refreshGrid(pnew);
                    }
                    dismissLabel();
                    break;
                case R.id.maina_twoa_next:
                    int ppnew = typePosition - 1;
                    if (ppnew >= 0) {
                        refreshGrid(ppnew);
                    }
                    dismissLabel();
                    break;
                case R.id.maina_twoa_middle:

                    if (isShowType) {
                        dismissLabel();
                    } else {
                        showLabel();
                    }
                    break;
                case R.id.maina_twoa_remark:
                    dismissLabel();
                    break;
            }
        }
    };

    private void refreshGrid(int pnew) {
        GameTypeInfo gameTypeInfo = gameTypeInfos.get(pnew);
        gameTypeInfo.setChoiced(true);
        gameTypeInfos.get(typePosition).setChoiced(false);
        period_sn = gameTypeInfo.getTitle();
        maina_twoa_content.setText(gameTypeInfo.getTitle());
        MainFragment1_twoa.type_id = gameTypeInfo.getId();
        typePosition = pnew;
        textChoiceAdapter.notifyDataSetChanged();

        page = 1;
        getData();
    }

    private void showLabel() {
        if (!isShowType) {
            isShowType = true;
            maina_twoa_remark.setVisibility(View.VISIBLE);
            maina_twoa_grid.setVisibility(View.VISIBLE);
            maina_twoa_icon.setImageResource(R.mipmap.hui_zhi_shang_icon);
        }
    }

    public void dismissLabel() {
        if (isShowType) {
            isShowType = false;
            maina_twoa_remark.setVisibility(View.GONE);
            maina_twoa_grid.setVisibility(View.GONE);
            maina_twoa_icon.setImageResource(R.mipmap.hui_zhi_xia_icon);
        }
    }

    public void refreshSn() {
        isRefreshSn = true;
        page = 1;
        typePosition = 0;
        getData();
    }
    private void getData() {
        String url = MyUrl.getMatchList;

        Map<String, String> params = new HashMap<String, String>();
        params.put("page", page + "");
        params.put("type_id", type_id);
        params.put("period_sn", period_sn);
        if(start_temp == 1){
            params.put("tab", "best");
        }

        httpConn.httpPost(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                pullHelper.setRefreshComplete();
                fillPull(json);
                fillGrid(json);
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
        if (page == 1) {
            gameInfos.clear();
        }
        if (data != null && data.length() > 0) {
            JSONObject dataJson = data.optJSONObject(0);
            period_sn = dataJson.optString("period_sn");
            JSONArray match_list = dataJson.optJSONArray("match_list");

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


    private void fillGrid(JSONObject json) {
        JSONObject extra = json.optJSONObject("extra");
        if (extra != null) {
            JSONArray period_sn_list = extra.optJSONArray("period_sn_list");
            if (period_sn_list != null) {
                int lentsn = period_sn_list.length();
                gameTypeInfos.clear();
                for (int i = 0; i < lentsn; i++) {
                    String jsonObj = period_sn_list.optString(i);
                    GameTypeInfo gameTypeInfo = new GameTypeInfo();
                    gameTypeInfo.setTitle(jsonObj);
                    if (gameTypeInfo.getTitle().equals(period_sn)) {
                        gameTypeInfo.setChoiced(true);
                        maina_twoa_content.setText(jsonObj);
                        typePosition = i;
                    } else {
                        gameTypeInfo.setChoiced(false);
                    }
                    gameTypeInfos.add(gameTypeInfo);
                }
                if (lentsn > 0) {
                    isRefreshSn = false;
                }
            }
        }

        if(typePosition == 0){
            gameTypeInfos.get(0).setChoiced(true);
            maina_twoa_content.setText(gameTypeInfos.get(0).getTitle());
        }

        textChoiceAdapter = new TextChoiceAdapter(mContext, gameTypeInfos);
        maina_twoa_grid.setAdapter(textChoiceAdapter);
    }

}
