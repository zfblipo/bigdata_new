package com.xingnext.bigdata.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xingnext.bigdata.R;
import com.xingnext.bigdata.beans.MoneyInfo;

import java.util.List;

/**
 * Created by lipo on 2017/3/21.
 */
public class BuyMoneyAdapter extends BaseAdapter {

    private Activity context;
    private LayoutInflater inflater;
    private List<MoneyInfo> list;
    private Resources resources;

    public BuyMoneyAdapter(Activity context, List<MoneyInfo> list) {
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
            convertView = inflater.inflate(R.layout.item_buy_money, null);
            initHolder(holder, convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        initData(holder, position);
        return convertView;
    }

    private class ViewHolder {
        View mainView;
        TextView buy_money_shaobing, buy_money_price;
    }

    private void initHolder(ViewHolder holder, View convertView) {
        holder.mainView = convertView;
        holder.buy_money_shaobing = (TextView) convertView
                .findViewById(R.id.buy_money_shaobing);
        holder.buy_money_price = (TextView) convertView
                .findViewById(R.id.buy_money_price);

        convertView.setTag(holder);
    }

    private void initData(ViewHolder holder, int position) {
        MoneyInfo info = list.get(position);
        if(info.isChoiced){
            holder.mainView.setBackgroundResource(R.drawable.main_red_bg);
            holder.buy_money_shaobing.setTextColor(resources.getColor(R.color.white));
        }else{
            holder.mainView.setBackgroundResource(R.drawable.main_red_border);
            holder.buy_money_shaobing.setTextColor(resources.getColor(R.color.main_red_clock));
        }

        holder.buy_money_shaobing.setText(info.title);
        holder.buy_money_price.setText(info.price+"元");
    }

}
