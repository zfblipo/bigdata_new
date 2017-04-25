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
import com.xingnext.bigdata.GameRecomDetailActivity;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.adapter.GameRecomAdapter;
import com.xingnext.bigdata.beans.GameRecomInfo;
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
 * Created by Administrator on 2017/4/11.
 */
public class MainFragment2a extends BaseFragment {

    private View mainView;
    private int startTemp;
    private boolean isVisibleToUser;
    private boolean isCreated = false;
    private boolean isFrist = true;

    private PullListViewHelperNew pullHelper;
    private List<GameRecomInfo> recomInfos;
    private GameRecomAdapter adapter;

    private Gson gson;
    private MyHttpConn httpConn;
    private int page;
    private LayoutInflater inflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        return mainView = inflater.inflate(R.layout.cell_pull_list,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isCreated = true;
        startTemp = getArguments().getInt("start_temp",0);
        gson = new Gson();
        httpConn = new MyHttpConn(mContext);

        recomInfos = new ArrayList<GameRecomInfo>();

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

    private void initPull(){
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
                if(position>0){
                    position--;
                    Intent intentI = new Intent();
                    intentI.putExtra("recom_id",recomInfos.get(position).getId());
                    startIntent(intentI, GameRecomDetailActivity.class);
                }

            }
        });

        pullHelper.setDivider(R.color.main_bg, DisplayUtil.dip2px(getActivity(),8));
        pullHelper.addHeaderView(inflater.inflate(R.layout.cell_line8,null));
    }

    private void initAdapter(){
        if(adapter == null){
            adapter = new GameRecomAdapter(mContext, recomInfos);
            pullHelper.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    private void getData(){
        String url = MyUrl.getHotList;
        Map<String, String> params = new HashMap<String, String>();
        params.put("type", startTemp + "");
        params.put("page", page + "");
        httpConn.httpGet(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                isFrist = false;
                pullHelper.setRefreshComplete();
                if (page == 1){
                    recomInfos.clear();
                }
                int lent = 0;
                JSONArray data = json.optJSONArray("data");
                if(data!=null){
                    lent = data.length();
                    for (int i = 0; i < lent; i++) {
                        String dataJson = data.optString(i);
                        recomInfos.add(gson.fromJson(dataJson,GameRecomInfo.class));
                    }
                }

                if (recomInfos.size() == 0) {
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

            @Override
            public void onError(int code, String msg) {
                pullHelper.setRefreshComplete();
            }
        });
    }

}
