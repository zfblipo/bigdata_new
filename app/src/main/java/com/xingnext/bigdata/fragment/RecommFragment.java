package com.xingnext.bigdata.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xingnext.bigdata.R;
import com.xingnext.bigdata.adapter.GameRecomAdapter;
import com.xingnext.bigdata.beans.GameRecomInfo;
import com.xingnext.bigdata.factory.PullListViewHelperNew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */
public class RecommFragment extends BaseFragment {

    private View mainView;

    private PullListViewHelperNew pullHelper;
    private List<GameRecomInfo> recomInfos;
    private GameRecomAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mainView = inflater.inflate(R.layout.cell_pull_list, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recomInfos = new ArrayList<GameRecomInfo>();

        initPull();

    }

    private void initPull(){
        pullHelper = new PullListViewHelperNew(mContext, mainView) {

            @Override
            protected void pullRefersh() {
//                page = 1;
//                getData();
            }

            @Override
            protected void pullMore() {
//                page++;
//                getData();
            }

        };

        pullHelper.setDivider(R.color.main_line, 1);

        for (int i = 0; i < 6; i++) {
            recomInfos.add(new GameRecomInfo());
        }
        initAdapter();
    }

    private void initAdapter(){
        if(adapter == null){
            adapter = new GameRecomAdapter(mContext, recomInfos);
            pullHelper.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

}
