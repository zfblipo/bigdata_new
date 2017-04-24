package com.xingnext.bigdata.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xingnext.bigdata.R;
import com.xingnext.bigdata.beans.GameInfo;
import com.xingnext.bigdata.utils.MyPublic;

import java.util.List;

/**
 * Created by lipo on 2017/4/15.
 */
public class ForecastAdapter  extends BaseAdapter {

    private Activity context;
    private LayoutInflater inflater;
    private List<GameInfo> list;
    private Resources resources;

    public ForecastAdapter(Activity context, List<GameInfo> list) {
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
            convertView = inflater.inflate(R.layout.item_forecast, null);
            initHolder(holder, convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        initData(holder, position);
        return convertView;
    }

    private class ViewHolder {
        TextView item_forecast_g1,item_forecast_g2,item_forecast_g3,item_forecast_grate1,item_forecast_grate2,item_forecast_grate3;

    }

    private void initHolder(ViewHolder holder, View convertView) {
        holder.item_forecast_g1 = (TextView) convertView
                .findViewById(R.id.item_forecast_g1);
        holder.item_forecast_g2 = (TextView) convertView
                .findViewById(R.id.item_forecast_g2);
        holder.item_forecast_g3 = (TextView) convertView
                .findViewById(R.id.item_forecast_g3);

        holder.item_forecast_grate1 = (TextView) convertView
                .findViewById(R.id.item_forecast_grate1);
        holder.item_forecast_grate2 = (TextView) convertView
                .findViewById(R.id.item_forecast_grate2);
        holder.item_forecast_grate3 = (TextView) convertView
                .findViewById(R.id.item_forecast_grate3);


        convertView.setTag(holder);
    }

    private void initData(ViewHolder holder, int position) {
        GameInfo gameInfo = list.get(position);


        holder.item_forecast_grate1.setText("主胜 " + gameInfo.getSpf().get(0));
        holder.item_forecast_grate2.setText("平局 " + gameInfo.getSpf().get(1));
        holder.item_forecast_grate3.setText("客胜 " + gameInfo.getSpf().get(2));

        holder.item_forecast_g1.setText(MyPublic.stringToBFB(gameInfo.getForecast_win()));
        holder.item_forecast_g2.setText(MyPublic.stringToBFB(gameInfo.getForecast_draw()));
        holder.item_forecast_g3.setText(MyPublic.stringToBFB(gameInfo.getForecast_lose()));

        int g0 = R.color.main_text6;
        int g1 = R.color.main_text6;
        int g2 = R.color.main_text6;
        String forecast = gameInfo.getForecast();
        if(forecast!=null){
            String[] fores = forecast.split(",");
            for (int i = 0; i < fores.length; i++) {
                if ("3".equals(fores[i])) {
                    g0 = R.color.main_color;
                } else if ("1".equals(fores[i])) {
                    g1 = R.color.main_color;
                } else if ("0".equals(fores[i])) {
                    g2 = R.color.main_color;
                }
            }

        }

        holder.item_forecast_g1.setTextColor(resources.getColor(g0));
        holder.item_forecast_grate1.setTextColor(resources.getColor(g0));

        holder.item_forecast_g2.setTextColor(resources.getColor(g1));
        holder.item_forecast_grate2.setTextColor(resources.getColor(g1));

        holder.item_forecast_g3.setTextColor(resources.getColor(g2));
        holder.item_forecast_grate3.setTextColor(resources.getColor(g2));
    }

}
