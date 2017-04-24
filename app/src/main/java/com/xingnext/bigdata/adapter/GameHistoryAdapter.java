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

public class GameHistoryAdapter extends BaseAdapter {

    private Activity context;
    private LayoutInflater inflater;
    private List<GameInfo> list;
    private Resources resources;
    private int whiteColor;
    private int mainColor;
    private int mainLineColor;

    public GameHistoryAdapter(Activity context, List<GameInfo> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        resources = context.getResources();
        whiteColor = resources.getColor(R.color.white);
        mainColor = resources.getColor(R.color.main_color);
        mainLineColor = resources.getColor(R.color.main_line2);
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
            convertView = inflater.inflate(R.layout.item_game_history, null);
            initHolder(holder, convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        initData(holder, position);
        return convertView;
    }

    private class ViewHolder {
        TextView item_game_history_historytime, item_game_history_team_name1,
                item_game_history_team_name2, item_game_history_history_rate,
                item_game_history_history_winstate, item_game_history_score1,
                item_game_history_score2, item_game_history_history_rateh,
                item_game_history_history_rateg;
        ImageView item_game_history_team_icon1, item_game_history_team_icon2;
        TextView item_game_history_win, item_game_history_draw,
                item_game_history_failure;
        ImageView item_game_history_win_icon, item_game_history_draw_icon,
                item_game_history_failure_icon;
        View item_game_history_history_pro;
    }

    private void initHolder(ViewHolder holder, View convertView) {
        holder.item_game_history_historytime = (TextView) convertView
                .findViewById(R.id.item_game_history_historytime);
        holder.item_game_history_team_name1 = (TextView) convertView
                .findViewById(R.id.item_game_history_team_name1);
        holder.item_game_history_team_name2 = (TextView) convertView
                .findViewById(R.id.item_game_history_team_name2);
        holder.item_game_history_history_rate = (TextView) convertView
                .findViewById(R.id.item_game_history_history_rate);
        holder.item_game_history_history_winstate = (TextView) convertView
                .findViewById(R.id.item_game_history_history_winstate);
        holder.item_game_history_score1 = (TextView) convertView
                .findViewById(R.id.item_game_history_score1);
        holder.item_game_history_score2 = (TextView) convertView
                .findViewById(R.id.item_game_history_score2);
        holder.item_game_history_win = (TextView) convertView
                .findViewById(R.id.item_game_history_win);
        holder.item_game_history_draw = (TextView) convertView
                .findViewById(R.id.item_game_history_draw);
        holder.item_game_history_failure = (TextView) convertView
                .findViewById(R.id.item_game_history_failure);
        holder.item_game_history_history_rateh = (TextView) convertView
                .findViewById(R.id.item_game_history_history_rateh);
        holder.item_game_history_history_rateg = (TextView) convertView
                .findViewById(R.id.item_game_history_history_rateg);

        holder.item_game_history_team_icon1 = (ImageView) convertView
                .findViewById(R.id.item_game_history_team_icon1);
        holder.item_game_history_team_icon2 = (ImageView) convertView
                .findViewById(R.id.item_game_history_team_icon2);
        holder.item_game_history_win_icon = (ImageView) convertView
                .findViewById(R.id.item_game_history_win_icon);
        holder.item_game_history_draw_icon = (ImageView) convertView
                .findViewById(R.id.item_game_history_draw_icon);
        holder.item_game_history_failure_icon = (ImageView) convertView
                .findViewById(R.id.item_game_history_failure_icon);

        holder.item_game_history_history_pro = convertView
                .findViewById(R.id.item_game_history_history_pro);

        convertView.setTag(holder);
    }

    private void initData(ViewHolder holder, int position) {
        GameInfo info = list.get(position);

        holder.item_game_history_historytime.setText(info.getMatch_sn() + "  " + info.getSeason_pre() + "  " + info.getMatch_time());

        MyImageLoader.loader(context, holder.item_game_history_team_icon1, info.getHost_team_image());
        MyImageLoader.loader(context, holder.item_game_history_team_icon2, info.getAway_team_image());

        holder.item_game_history_team_name1.setText(info.getHost_name());
        holder.item_game_history_team_name2.setText(info.getAway_name());

        holder.item_game_history_history_rate.setText(info.getPrecision());
        holder.item_game_history_score1.setText(info.getHost_score() + "");
        holder.item_game_history_score2.setText(info.getAway_score() + "");

        holder.item_game_history_win.setText("主胜 " + info.getSpf0());
        holder.item_game_history_draw.setText("平局 " + info.getSpf1());
        holder.item_game_history_failure.setText("客胜 " + info.getSpf2());

        fillForecast(holder, info);

    }

    private void fillForecast(ViewHolder holder, GameInfo info) {
        boolean isRight = false;
        int g0 = R.mipmap.check_middle_icon;
        int g1 = R.mipmap.check_middle_icon;
        int g2 = R.mipmap.check_middle_icon;
        String forecast = info.getForecast();
        if(forecast!=null){
            String[] fores = forecast.split(",");
            for (int i = 0; i < fores.length; i++) {
                if ("3".equals(fores[i])) {
                    g0 = R.mipmap.check_fail_icon;
                } else if ("1".equals(fores[i])) {
                    g1 = R.mipmap.check_fail_icon;
                } else if ("0".equals(fores[i])) {
                    g2 = R.mipmap.check_fail_icon;
                }
            }
        }

        if (info.getHost_score() > info.getAway_score()) {
            if (g0 == R.mipmap.check_fail_icon) {
                isRight = true;
            }
            g0 = R.mipmap.check_win_icon;
        } else if (info.getHost_score() == info.getAway_score()) {
            if (g1 == R.mipmap.check_fail_icon) {
                isRight = true;
            }
            g1 = R.mipmap.check_win_icon;
        } else if (info.getHost_score() < info.getAway_score()) {
            if (g1 == R.mipmap.check_fail_icon) {
                isRight = true;
            }
            g2 = R.mipmap.check_win_icon;
        }

        holder.item_game_history_win_icon.setImageResource(g0);
        holder.item_game_history_draw_icon.setImageResource(g1);
        holder.item_game_history_failure_icon.setImageResource(g2);

        if (isRight) {
            holder.item_game_history_history_winstate.setText("命中");
            holder.item_game_history_history_winstate.setBackgroundResource(R.color.main_color);
            holder.item_game_history_history_pro.setBackgroundResource(R.drawable.main_color_borderl);
            holder.item_game_history_history_rate.setTextColor(mainColor);
            holder.item_game_history_history_rateh.setTextColor(mainColor);
            holder.item_game_history_history_rateg.setTextColor(mainColor);
        }else{
            holder.item_game_history_history_winstate.setText("未中");
            holder.item_game_history_history_winstate.setBackgroundResource(R.color.main_line2);
            holder.item_game_history_history_pro.setBackgroundResource(R.drawable.main_line2_borderl);
            holder.item_game_history_history_rate.setTextColor(mainLineColor);
            holder.item_game_history_history_rateh.setTextColor(mainLineColor);
            holder.item_game_history_history_rateg.setTextColor(mainLineColor);
        }

    }

}
