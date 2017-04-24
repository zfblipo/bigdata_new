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
import com.xingnext.bigdata.beans.GameTypeInfo;

import java.util.List;

/**
 * Created by lipo on 2017/4/23.
 */
public class TextChoiceAdapter extends BaseAdapter {

    private Activity context;
    private LayoutInflater inflater;
    private List<GameTypeInfo> list;
    private Resources resources;
    private int blackColor;
    private int redColor;

    public TextChoiceAdapter(Activity context, List<GameTypeInfo> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        resources = context.getResources();
        blackColor = resources.getColor(R.color.main_text3);
        redColor = resources.getColor(R.color.main_color);
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
            convertView = inflater.inflate(R.layout.item_choice_text, null);
            initHolder(holder, convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        initData(holder, position);
        return convertView;
    }

    private class ViewHolder {
        View mainView;
        TextView item_choice_text_name;
        ImageView item_choice_text_icon;

    }

    private void initHolder(ViewHolder holder, View convertView) {
        holder.mainView = convertView;
        holder.item_choice_text_name = (TextView) convertView
                .findViewById(R.id.item_choice_text_name);
        holder.item_choice_text_icon = (ImageView) convertView
                .findViewById(R.id.item_choice_text_icon);

        convertView.setTag(holder);
    }

    private void initData(ViewHolder holder, int position) {
        GameTypeInfo info = list.get(position);

        holder.item_choice_text_name.setText(info.getTitle());

        if(info.isChoiced()){
            holder.mainView.setBackgroundResource(R.drawable.main_red_border);
            holder.item_choice_text_name.setTextColor(redColor);
            holder.item_choice_text_icon.setVisibility(View.VISIBLE);
        }else{
            holder.mainView.setBackgroundResource(R.drawable.main_buy_border);
            holder.item_choice_text_name.setTextColor(blackColor);
            holder.item_choice_text_icon.setVisibility(View.GONE);
        }

    }

}
