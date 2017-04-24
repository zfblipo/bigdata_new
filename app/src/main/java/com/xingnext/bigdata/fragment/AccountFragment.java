package com.xingnext.bigdata.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.adapter.AccountAdapter;
import com.xingnext.bigdata.beans.AccountInfo;
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
 * Created by Administrator on 2017/4/10.
 */
public class AccountFragment extends BaseFragment {

    private View mainView,fragment_account_list;
    private boolean isCreated = false;
    private boolean isVisibleToUser = false;
    private boolean isFrist = true;

    private MyHttpConn httpConn;

    private int page;
    private Gson gson;

    private int startTemp;

    private PullListViewHelperNew pullHelper;
    private List<AccountInfo> accountInfos;
    private AccountAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mainView = inflater.inflate(R.layout.fragment_account, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isCreated = true;
        startTemp = getArguments().getInt("start_temp",0);

        httpConn = new MyHttpConn(mContext);
        gson = new Gson();

        accountInfos = new ArrayList<AccountInfo>();
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
        this.isVisibleToUser = isVisibleToUser;
        if(isCreated&&isFrist){
            page = 1;
            getData();
        }
    }

    private void initPull(){
        fragment_account_list = mainView.findViewById(R.id.fragment_account_list);
        pullHelper = new PullListViewHelperNew(mContext, fragment_account_list) {

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

        pullHelper.setDivider(R.color.main_line, 1);
    }

    private void initAdapter(){
        if(adapter == null){
            adapter = new AccountAdapter(mContext, accountInfos);
            pullHelper.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    private void getData(){
        String url = MyUrl.billUrl;
        final Map<String, String> params = new HashMap<String, String>();
        params.put("page", page + "");
        if(startTemp == 0){
            params.put("type", "0");
        }else if(startTemp == 1){
            params.put("type", "3");
        }else if(startTemp == 2){
            params.put("type", "2");
        }

        httpConn.httpGet(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                isFrist = false;
                pullHelper.setRefreshComplete();
                if (page == 1){
                    accountInfos.clear();
                }
                JSONArray data = json.optJSONArray("data");
                int lent = 0;
                if(data!=null){
                    lent = data.length();
                    for (int i = 0; i < lent; i++) {
                        String dataJson = data.optString(i);
                        accountInfos.add(gson.fromJson(dataJson,AccountInfo.class));
                    }
                }

                if (accountInfos.size() == 0) {
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
