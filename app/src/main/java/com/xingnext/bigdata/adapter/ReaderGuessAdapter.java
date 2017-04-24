package com.xingnext.bigdata.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingnext.bigdata.OrderDetailActivity;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.beans.GameInfo;
import com.xingnext.bigdata.beans.ReaderGuessInfo;
import com.xingnext.bigdata.utils.MyImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */
public class ReaderGuessAdapter extends BaseAdapter {

    private Activity context;
    private LayoutInflater inflater;
    private List<ReaderGuessInfo> list;

    public ReaderGuessAdapter(Activity context, List<ReaderGuessInfo> list) {
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
            convertView = inflater.inflate(R.layout.item_record_guess, null);
            initHolder(holder, convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        initData(holder, position);
        return convertView;
    }

    private class ViewHolder {
        TextView item_record_guess_time,item_record_guess_status,item_record_guess_type,item_record_guess_game_time,item_record_guess_detial,
                item_record_guess_team_name1,item_record_guess_team_name2;
        ImageView item_record_guess_team_icon1,item_record_guess_team_icon2,item_record_guess_win_icon,item_record_guess_draw_icon,item_record_guess_failure_icon;
        TextView item_record_guess_win,item_record_guess_draw,item_record_guess_failure;


    }

    private void initHolder(ViewHolder holder, View convertView) {
        holder.item_record_guess_time = (TextView) convertView
                .findViewById(R.id.item_record_guess_time);
        holder.item_record_guess_status = (TextView) convertView
                .findViewById(R.id.item_record_guess_status);
        holder.item_record_guess_type = (TextView) convertView
                .findViewById(R.id.item_record_guess_type);
        holder.item_record_guess_game_time = (TextView) convertView
                .findViewById(R.id.item_record_guess_game_time);
        holder.item_record_guess_detial = (TextView) convertView
                .findViewById(R.id.item_record_guess_detial);
        holder.item_record_guess_team_name1 = (TextView) convertView
                .findViewById(R.id.item_record_guess_team_name1);
        holder.item_record_guess_team_name2 = (TextView) convertView
                .findViewById(R.id.item_record_guess_team_name2);

        holder.item_record_guess_team_icon1 = (ImageView) convertView
                .findViewById(R.id.item_record_guess_team_icon1);
        holder.item_record_guess_team_icon2 = (ImageView) convertView
                .findViewById(R.id.item_record_guess_team_icon2);
        holder.item_record_guess_win_icon = (ImageView) convertView
                .findViewById(R.id.item_record_guess_win_icon);
        holder.item_record_guess_draw_icon = (ImageView) convertView
                .findViewById(R.id.item_record_guess_draw_icon);
        holder.item_record_guess_failure_icon = (ImageView) convertView
                .findViewById(R.id.item_record_guess_failure_icon);

        holder.item_record_guess_win = (TextView) convertView
                .findViewById(R.id.item_record_guess_win);
        holder.item_record_guess_draw = (TextView) convertView
                .findViewById(R.id.item_record_guess_draw);
        holder.item_record_guess_failure = (TextView) convertView
                .findViewById(R.id.item_record_guess_failure);

        convertView.setTag(holder);
    }

    private void initData(ViewHolder holder, int position) {
        final ReaderGuessInfo info = list.get(position);
        holder.item_record_guess_time.setText("订阅时间："+info.getCreate_time());
        holder.item_record_guess_status.setText(info.getHit_status());

        GameInfo gameInfo = info.getGameInfo();
        holder.item_record_guess_game_time.setText(gameInfo.getMatch_sn() + "  " + gameInfo.getSeason_pre() + "  " + gameInfo.getMatch_time());

        MyImageLoader.loader(context, holder.item_record_guess_team_icon1, gameInfo.getHost_team_image());
        MyImageLoader.loader(context, holder.item_record_guess_team_icon2, gameInfo.getAway_team_image());

        holder.item_record_guess_team_name1.setText(gameInfo.getHost_name());
        holder.item_record_guess_team_name2.setText(gameInfo.getAway_name());

        holder.item_record_guess_win.setText("主胜 " + gameInfo.getSpf().get(0));
        holder.item_record_guess_draw.setText("平局 " + gameInfo.getSpf().get(1));
        holder.item_record_guess_failure.setText("客胜 " + gameInfo.getSpf().get(2));

        fillForecast(holder,gameInfo);

        holder.item_record_guess_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentD = new Intent();
                intentD.setClass(context, OrderDetailActivity.class);
                intentD.putExtra("order_id",info.getId());
                context.startActivity(intentD);
                context.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });
    }

    private void fillForecast(ViewHolder holder, GameInfo info) {
        int g0 = R.mipmap.check_middle_icon;
        int g1 = R.mipmap.check_middle_icon;
        int g2 = R.mipmap.check_middle_icon;
        String forecast = info.getForecast();
        if (!forecast.isEmpty()) {
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

        if ("4".equals(info.getMatch_status())) {
            if (info.getHost_score() > info.getAway_score()) {
                g0 = R.mipmap.check_win_icon;
            } else if (info.getHost_score() == info.getAway_score()) {
                g1 = R.mipmap.check_win_icon;
            } else if (info.getHost_score() < info.getAway_score()) {
                g2 = R.mipmap.check_win_icon;
            }
        }
        holder.item_record_guess_win_icon.setImageResource(g0);
        holder.item_record_guess_draw_icon.setImageResource(g1);
        holder.item_record_guess_failure_icon.setImageResource(g2);
    }

}
