package com.xingnext.bigdata.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xingnext.bigdata.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 */
public class UpgradeTextAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<String> list;
    private Resources resources;

    public UpgradeTextAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        resources = context.getResources();
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
            convertView = inflater.inflate(R.layout.item_upgrade_text, null);
            initHolder(holder, convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        initData(holder, position);
        return convertView;
    }

    private class ViewHolder {
        TextView contentView;
    }

    private void initHolder(ViewHolder holder, View convertView) {
        holder.contentView = (TextView) convertView;

        convertView.setTag(holder);
    }

    private void initData(ViewHolder holder, int position) {
        String info = list.get(position);
        holder.contentView.setText((position+1)+"."+info);
    }

}
