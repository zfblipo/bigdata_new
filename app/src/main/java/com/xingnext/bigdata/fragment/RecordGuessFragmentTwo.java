package com.xingnext.bigdata.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.lipo.utils.DisplayUtil;
import com.xingnext.bigdata.GameDetailActivity;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.adapter.ReaderGuessAdapter;
import com.xingnext.bigdata.beans.GameInfo;
import com.xingnext.bigdata.beans.ReaderGuessInfo;
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
 * Created by lipo on 2017/4/18.
 */
public class RecordGuessFragmentTwo extends BaseFragment {

    private View mainView;
    private boolean isVisibleToUser;
    private boolean isCreated = false;
    private boolean isFrist = true;

    private PullListViewHelperNew pullHelper;
    private List<ReaderGuessInfo> infos;
    private ReaderGuessAdapter adapter;

    private MyHttpConn httpConn;
    private Gson gson;
    private int page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mainView = inflater.inflate(R.layout.cell_pull_list, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isCreated = true;
        infos = new ArrayList<ReaderGuessInfo>();
        httpConn = new MyHttpConn(mContext);
        gson = new Gson();

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

    private void initPull() {
        pullHelper = new PullListViewHelperNew(mContext, mainView) {

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
                GameInfo gameInfo = infos.get(position).getGameInfo();
                Intent intentI = new Intent();
                intentI.putExtra("match_id",gameInfo.getMatch_id());
                intentI.putExtra("season_desc",gameInfo.getSeason_pre());
                startIntent(intentI, GameDetailActivity.class);
            }
        });
        pullHelper.setDivider(R.color.main_bg, DisplayUtil.dip2px(mContext,8));
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new ReaderGuessAdapter(mContext, infos);
            pullHelper.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void getData() {
        String url = MyUrl.orderUrl;
        Map<String, String> params = new HashMap<String, String>();
        params.put("page", page + "");
        params.put("order_type", "1");
        httpConn.httpPost(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                pullHelper.setRefreshComplete();
                if (page == 1) {
                    infos.clear();
                }
                JSONArray data = json.optJSONArray("data");
                int dataLent = 0;
                if (data != null) {
                    dataLent = data.length();
                    if (dataLent > 0) {
                        for (int i = 0; i < dataLent; i++) {
                            JSONObject dataJson = data.optJSONObject(i);
                            ReaderGuessInfo guessInfo = gson.fromJson(dataJson.toString(), ReaderGuessInfo.class);
                            JSONObject detail = dataJson.optJSONObject("detail");

                            JSONArray match_list = detail.optJSONArray("match_list");
                            if (match_list != null) {
                                int cLent = match_list.length();
                                if (cLent > 0) {
                                    String gameDetail = match_list.optString(0);
                                    guessInfo.setGameInfo(gson.fromJson(gameDetail, GameInfo.class));
                                }
                            }
                            infos.add(guessInfo);
                        }
                    }
                }

                if (infos.size() == 0) {
                    pullHelper.setEmptyShow();
                } else {
                    pullHelper.setEmptyDismiss();
                    if (dataLent <= MyStatic.pageSize) {
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
            }
        });
    }

}

