package com.xingnext.bigdata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.xingnext.bigdata.adapter.GameSampleAdapter;
import com.xingnext.bigdata.beans.GameSampleInfo;
import com.xingnext.bigdata.factory.PullListViewHelper;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSampleActivity extends BaseActivity {

    private String match_id;
    private Intent intent;

    private View game_sample_list;
    private PullListViewHelper listHelper;

    private GameSampleAdapter adapter;
    private List<GameSampleInfo> infos;

    private MyHttpConn httpConn;
    private Gson gson;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_sample);

        infos = new ArrayList<GameSampleInfo>();
        intent = getIntent();
        match_id = intent.getStringExtra("match_id");

        httpConn = new MyHttpConn(mContext);
        gson = new Gson();


        initView();
        getData();
    }

    private void initView(){
        game_sample_list = findViewById(R.id.game_sample_list);
        listHelper = new PullListViewHelper(mContext,game_sample_list) {
            @Override
            public void updateData(int page) {

            }

            @Override
            public void OnItemClick(int position) {

            }
        };

        listHelper.setDivider(R.color.main_bg,0);
        listHelper.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    private void getData() {
        String url = MyUrl.getSampleList + match_id;
        Map<String, String> params = new HashMap<String, String>();
        params.put("page",page+"");
        if(page == 1){
            listHelper.setPage(1);
        }
        httpConn.httpGet(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                JSONArray array = json.optJSONArray("data");
                if(page == 1){
                    infos.clear();
                }
                if(array!=null){
                    int lent = array.length();
                    for (int i = 0; i < lent; i++) {
                        String dataStr = array.optString(i);
                        infos.add(gson.fromJson(dataStr,GameSampleInfo.class));
                    }
                }
                initAdapter();
            }

            @Override
            public void onError(int code, String msg) {
            }
        });
    }

    private void initAdapter(){
        if(adapter == null){
            adapter = new GameSampleAdapter(mContext,infos);
            listHelper.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

}
