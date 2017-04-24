package com.xingnext.bigdata.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xingnext.bigdata.R;
import com.xingnext.bigdata.beans.AccountInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */
public class AccountAdapter extends BaseAdapter {

    private Activity context;
    private LayoutInflater inflater;
    private List<AccountInfo> list;
    private Resources resources;
    private int blackColor;
    private int redColor;

    public AccountAdapter(Activity context, List<AccountInfo> list) {
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
            convertView = inflater.inflate(R.layout.item_account, null);
            initHolder(holder, convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        initData(holder, position);
        return convertView;
    }

    private class ViewHolder {
        TextView item_account_orderid,item_account_time,item_account_money;

    }

    private void initHolder(ViewHolder holder, View convertView) {
        holder.item_account_orderid = (TextView) convertView
                .findViewById(R.id.item_account_orderid);
        holder.item_account_time = (TextView) convertView
                .findViewById(R.id.item_account_time);
        holder.item_account_money = (TextView) convertView
                .findViewById(R.id.item_account_money);

        convertView.setTag(holder);
    }

    private void initData(ViewHolder holder, int position) {
        AccountInfo info = list.get(position);

        holder.item_account_orderid.setText(info.getChange_desc());
        holder.item_account_time.setText(info.getChange_time());

        if("3".equals(info.getChange_type())||"5".equals(info.getChange_type())){
            holder.item_account_money.setTextColor(redColor);
            holder.item_account_money.setText("+"+info.getMoney());
        }else{
            holder.item_account_money.setTextColor(blackColor);
            holder.item_account_money.setText(info.getMoney());
        }

    }

}
