package com.xingnext.bigdata.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingnext.bigdata.R;
import com.xingnext.bigdata.beans.GameSampleInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */
public class GameSampleAdapter extends BaseAdapter {

    private Activity context;
    private LayoutInflater inflater;
    private List<GameSampleInfo> list;

    public GameSampleAdapter(Activity context, List<GameSampleInfo> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int count = 0;
        count = list.size();
        return count;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_game_sample, null);
            initHolder(holder, convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        initData(holder, position);
        return convertView;
    }

    private class ViewHolder {
        TextView item_game_sample_time, item_game_sample_name, item_game_sample_team,
                item_game_sample_result;
    }

    private void initHolder(ViewHolder holder, View convertView) {
        holder.item_game_sample_time = (TextView) convertView
                .findViewById(R.id.item_game_sample_time);
        holder.item_game_sample_name = (TextView) convertView
                .findViewById(R.id.item_game_sample_name);
        holder.item_game_sample_team = (TextView) convertView
                .findViewById(R.id.item_game_sample_team);
        holder.item_game_sample_result = (TextView) convertView
                .findViewById(R.id.item_game_sample_result);

        convertView.setTag(holder);
    }

    private void initData(ViewHolder holder, int position) {
        GameSampleInfo info = list.get(position);

        holder.item_game_sample_time.setText(info.getMatch_time());
        holder.item_game_sample_name.setText(info.getLeague_name());
        holder.item_game_sample_team.setText(info.getHost_name() + " " + info.getHost_score() + "-" + info.getAway_score() + " " + info.getAway_name());
        if ("3".equals(info.getMatch_result())) {
            holder.item_game_sample_result.setText("胜");
            holder.item_game_sample_result.setBackgroundResource(R.color.main_color);
        } else if ("1".equals(info.getMatch_result())) {
            holder.item_game_sample_result.setText("平");
            holder.item_game_sample_result.setBackgroundResource(R.color.sample_pin);
        } else if ("0".equals(info.getMatch_result())) {
            holder.item_game_sample_result.setText("负");
            holder.item_game_sample_result.setBackgroundResource(R.color.sample_fail);
        }


    }

}
