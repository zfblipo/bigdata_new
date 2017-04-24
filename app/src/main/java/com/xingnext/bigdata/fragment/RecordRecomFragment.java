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
import com.xingnext.bigdata.adapter.ReaderRecomAdapter;
import com.xingnext.bigdata.beans.ReaderRecomInfo;
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
 * Created by Administrator on 2017/4/12.
 */
public class RecordRecomFragment extends BaseFragment {

    private View mainView;
    private boolean isVisibleToUser;
    private boolean isCreated = false;
    private boolean isFrist = true;

    private PullListViewHelperNew pullHelper;
    private List<ReaderRecomInfo> recomInfos;
    private ReaderRecomAdapter adapter;

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
        recomInfos = new ArrayList<ReaderRecomInfo>();
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
                Intent intentI = new Intent();
                intentI.putExtra("recom_id",recomInfos.get(position).getGoods_id());
                startIntent(intentI, GameRecomDetailActivity.class);
            }
        });

        pullHelper.setDivider(R.color.main_bg, DisplayUtil.dip2px(mContext,8));
    }

    private void initAdapter(){
        if(adapter == null){
            adapter = new ReaderRecomAdapter(mContext, recomInfos);
            pullHelper.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    private void getData() {
        String url = MyUrl.orderUrl;
        Map<String, String> params = new HashMap<String, String>();
        params.put("page", page + "");
        params.put("order_type", "2");
        httpConn.httpPost(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                pullHelper.setRefreshComplete();
                if (page == 1) {
                    recomInfos.clear();
                }
                JSONArray data = json.optJSONArray("data");

                int dataLent = 0;

                if (data != null) {
                    dataLent = data.length();
                    if (dataLent > 0) {
                        for (int i = 0; i < dataLent; i++) {
                            String dataJson = data.optString(i);
                            ReaderRecomInfo recomInfo = gson.fromJson(dataJson,ReaderRecomInfo.class);
                            recomInfos.add(recomInfo);
                        }
                    }
                }

                if (recomInfos.size() == 0) {
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
