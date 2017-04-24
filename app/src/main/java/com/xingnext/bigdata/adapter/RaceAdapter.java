package com.xingnext.bigdata.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingnext.bigdata.R;
import com.xingnext.bigdata.beans.GameInfo;
import com.xingnext.bigdata.utils.MyImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */
public class RaceAdapter extends BaseAdapter {

    private Activity context;
    private LayoutInflater inflater;
    private List<GameInfo> list;

    public RaceAdapter(Activity context, List<GameInfo> list) {
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
            convertView = inflater.inflate(R.layout.item_race, null);
            initHolder(holder, convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        initData(holder, position);
        return convertView;
    }

    private class ViewHolder {
        ImageView item_race_host_icon,item_race_away_icon;
        TextView item_race_time,item_race_host_name,item_race_away_name;

    }

    private void initHolder(ViewHolder holder, View convertView) {
        holder.item_race_time = (TextView) convertView
                .findViewById(R.id.item_race_time);
        holder.item_race_host_name = (TextView) convertView
                .findViewById(R.id.item_race_host_name);
        holder.item_race_away_name = (TextView) convertView
                .findViewById(R.id.item_race_away_name);

        holder.item_race_host_icon = (ImageView) convertView
                .findViewById(R.id.item_race_host_icon);
        holder.item_race_away_icon = (ImageView) convertView
                .findViewById(R.id.item_race_away_icon);

        convertView.setTag(holder);
    }

    private void initData(ViewHolder holder, int position) {
        GameInfo info = list.get(position);

        MyImageLoader.loader(context,holder.item_race_host_icon,info.getHost_team_image());
        MyImageLoader.loader(context,holder.item_race_away_icon,info.getAway_team_image());

        holder.item_race_time.setText(info.getMatch_sn()+"\n"+info.getSeason_pre()+"\n"+info.getMatch_time());
        holder.item_race_host_name.setText(info.getHost_name());
        holder.item_race_away_name.setText(info.getAway_name());
    }

}
