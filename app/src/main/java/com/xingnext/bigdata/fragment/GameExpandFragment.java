package com.xingnext.bigdata.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.adapter.GameExpandAdapter;
import com.xingnext.bigdata.beans.GameInfo;
import com.xingnext.bigdata.beans.TimeInfo;
import com.xingnext.bigdata.factory.PullScrollViewHelper;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyUrl;
import com.xingnext.bigdata.views.NoScrollExpandListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6.
 */
public class GameExpandFragment extends BaseFragment {

    private View mainView;
    private boolean isVisibleToUser;
    private boolean isCreated = false;
    private boolean isFrist = true;

    private List<TimeInfo> parentList;
    private List<List<GameInfo>> childList;
    private GameExpandAdapter expandAdapter;

    private NoScrollExpandListView expandView;
    private PullScrollViewHelper scrollViewHelper;

    private MyHttpConn httpConn;

    private int page;
    private Gson gson;
    private LayoutInflater inflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mainView = inflater.inflate(R.layout.cell_pull_scroll, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        inflater= LayoutInflater.from(mContext);
        parentList = new ArrayList<TimeInfo>();
        childList = new ArrayList<List<GameInfo>>();
        httpConn = new MyHttpConn(mContext);

        gson = new Gson();

        initView();
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
        expandView = (NoScrollExpandListView) inflater.inflate(R.layout.cell_noscroll_expand,null);

        scrollViewHelper = new PullScrollViewHelper(mContext,mainView,expandView) {
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

        ColorDrawable divider = new ColorDrawable(mContext.getResources()
                .getColor(R.color.main_line));
        expandView.setChildDivider(divider);
        expandView.setDividerHeight(1);

        //取消前头箭头
        expandView.setGroupIndicator(null);

        // 点击组失效
        expandView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // TODO Auto-generated method stub
                return true;
            }
        });

    }

    private void initAdapter() {
        if (expandAdapter == null) {
            expandAdapter = new GameExpandAdapter(mContext, parentList, childList);
            expandView.setAdapter(expandAdapter);
        } else {
            expandAdapter.notifyDataSetChanged();
        }

        //默认全展开
        for (int i = 0; i < parentList.size(); i++) {
            expandView.expandGroup(i);
        }

    }

    private void getData() {
        String url = MyUrl.getMatchList;
        Map<String, String> params = new HashMap<String, String>();
        params.put("page", page + "");
        httpConn.httpGet(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                scrollViewHelper.onRefreshComplete();
                int pSize = 0;
                if (page == 1) {
                    parentList.clear();
                    childList.clear();
                } else {
                    pSize = parentList.size() - 1;
                }

                JSONArray data = json.optJSONArray("data");
                int plent = 0;
                if (data != null) {
                    plent = data.length();
                    for (int i = 0; i < plent; i++) {
                        JSONObject dataJson = data.optJSONObject(i);
                        TimeInfo timeInfo = TimeInfo.fromJson(dataJson);
                        List<GameInfo> gameInfos = null;
                        if (i == 0 && page > 1 && timeInfo.getPeriod_time().equals(parentList.get(pSize).getPeriod_time())) {
                            gameInfos = childList.get(pSize);
                        }else{
                            gameInfos = new ArrayList<GameInfo>();
                            parentList.add(timeInfo);
                        }

                        JSONArray match_list = dataJson.optJSONArray("match_list");
                        if(match_list!=null){
                            int cLent = match_list.length();
                            for (int j = 0; j < cLent; j++) {
                                JSONObject matchJson = match_list.optJSONObject(j);
                                GameInfo gameInfo = gson.fromJson(matchJson.toString(),GameInfo.class);
                                JSONArray spf = matchJson.optJSONArray("spf");
                                GameInfo.setSpf(gameInfo,spf);
                                gameInfos.add(gameInfo);
                            }
                        }

                        childList.add(gameInfos);
                    }
                }

                if (parentList.size() == 0) {
                    scrollViewHelper.setEmptyShow();
                    initAdapter();
                } else {
                    scrollViewHelper.setEmptyDismiss();
                    if (plent == 0) {
                        scrollViewHelper.setPullMoreEnable(false);
                    } else {
                        scrollViewHelper.setPullMoreEnable(true);
                        initAdapter();
                    }
                }

            }

            @Override
            public void onError(int code, String msg) {
                scrollViewHelper.onRefreshComplete();
            }
        });
    }


}
