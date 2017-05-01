package com.xingnext.bigdata.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingnext.bigdata.GameDetailActivity;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.beans.GameInfo;
import com.xingnext.bigdata.beans.TimeInfo;
import com.xingnext.bigdata.utils.MyImageLoader;
import com.xingnext.bigdata.utils.MyPublic;

import java.util.List;

/**
 * Created by Administrator on 2017/4/6.
 */
public class GameExpandAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private LayoutInflater inflater;

    private List<TimeInfo> parentList;
    private List<List<GameInfo>> childList;

    private Resources resources;

    public GameExpandAdapter(Activity context, List<TimeInfo> parentList, List<List<GameInfo>> childList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.parentList = parentList;
        this.childList = childList;

        resources = context.getResources();

    }

    @Override
    public int getGroupCount() {
        return parentList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childList.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return parentList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childList.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ParentViewHolder holder = null;
        if (view == null) {
            holder = new ParentViewHolder();
            view = inflater.inflate(R.layout.item_text_time, null);
            initParentHolder(holder, view);
        } else {
            holder = (ParentViewHolder) view.getTag();
        }

        TimeInfo timeInfo = parentList.get(i);
        holder.item_text_time_date.setText(timeInfo.getPeriod_week() + "  "
                + timeInfo.getPeriod_time());
        holder.item_text_time_num.setText("（共" + timeInfo.getMatch_count() + "场）");
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_game, null);
            initHolder(holder, view);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        initData(holder, i, i1);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    private class ParentViewHolder {
        TextView item_text_time_date, item_text_time_num;
    }

    private void initParentHolder(ParentViewHolder holder, View convertView) {
        holder.item_text_time_date = (TextView) convertView
                .findViewById(R.id.item_text_time_date);
        holder.item_text_time_num = (TextView) convertView
                .findViewById(R.id.item_text_time_num);
        convertView.setTag(holder);
    }

    private class ViewHolder {
        View mainView;
        TextView item_game_time, item_game_state, item_game_name1,
                item_game_name2, item_game_probability, item_game_price,
                item_game_persons;
        ImageView item_game_icon1, item_game_icon2;
        TextView item_game_win, item_game_draw, item_game_failure;
        ImageView item_game_win_icon, item_game_draw_icon,
                item_game_failure_icon;
    }

    private void initHolder(ViewHolder holder, View convertView) {
        holder.mainView = convertView;
        holder.item_game_time = (TextView) convertView
                .findViewById(R.id.item_game_time);
        holder.item_game_state = (TextView) convertView
                .findViewById(R.id.item_game_state);
        holder.item_game_name1 = (TextView) convertView
                .findViewById(R.id.item_game_name1);
        holder.item_game_name2 = (TextView) convertView
                .findViewById(R.id.item_game_name2);
        holder.item_game_probability = (TextView) convertView
                .findViewById(R.id.item_game_probability);
        holder.item_game_price = (TextView) convertView
                .findViewById(R.id.item_game_price);
        holder.item_game_persons = (TextView) convertView
                .findViewById(R.id.item_game_persons);
        holder.item_game_win = (TextView) convertView
                .findViewById(R.id.item_game_win);
        holder.item_game_draw = (TextView) convertView
                .findViewById(R.id.item_game_draw);
        holder.item_game_failure = (TextView) convertView
                .findViewById(R.id.item_game_failure);

        holder.item_game_icon1 = (ImageView) convertView
                .findViewById(R.id.item_game_icon1);
        holder.item_game_icon2 = (ImageView) convertView
                .findViewById(R.id.item_game_icon2);
        holder.item_game_win_icon = (ImageView) convertView
                .findViewById(R.id.item_game_win_icon);
        holder.item_game_draw_icon = (ImageView) convertView
                .findViewById(R.id.item_game_draw_icon);
        holder.item_game_failure_icon = (ImageView) convertView
                .findViewById(R.id.item_game_failure_icon);

        convertView.setTag(holder);
    }

    private void initData(ViewHolder holder, int i, int i1) {
        final GameInfo info = childList.get(i).get(i1);

        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, GameDetailActivity.class);
                intent.putExtra("match_id", info.getMatch_id());
                intent.putExtra("season_desc", info.getSeason_pre());
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        });

        holder.item_game_time.setText(info.getMatch_sn() + "  " + info.getSeason_pre() + "  " + info.getMatch_time());

        if ("0".equals(info.getOrder_status())){
            holder.item_game_state.setText("未订阅");
            holder.item_game_state.setTextColor(resources.getColor(R.color.main_text6));
            holder.item_game_state.setBackgroundResource(R.color.main_line);
        }else{
            holder.item_game_state.setText("已订阅");
            holder.item_game_state.setTextColor(resources.getColor(R.color.white));
            holder.item_game_state.setBackgroundResource(R.color.main_color);
        }

        MyImageLoader.loader(context, holder.item_game_icon1, info.getHost_team_image());
        MyImageLoader.loader(context, holder.item_game_icon2, info.getAway_team_image());

        holder.item_game_name1.setText(info.getHost_name());
        holder.item_game_name2.setText(info.getAway_name());

        holder.item_game_probability.setText(info.getPrecision());
        holder.item_game_price.setText(info.getPrice() + "球币");
        holder.item_game_persons.setText(info.getOrder_count() + "人订阅");

        holder.item_game_win.setText("主胜 " + info.getSpf0());
        holder.item_game_draw.setText("平局 " + info.getSpf1());
        holder.item_game_failure.setText("客胜 " + info.getSpf2());

        fillForecast(holder, info);

    }

    private void fillForecast(ViewHolder holder, GameInfo info) {
        int g0 = R.mipmap.check_middle_icon;
        int g1 = R.mipmap.check_middle_icon;
        int g2 = R.mipmap.check_middle_icon;
        String forecast = info.getForecast();
        if (!MyPublic.isEmpty(forecast)) {
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

        if ("10".equals(info.getMatch_status())) {
            if (info.getHost_score() > info.getAway_score()) {
                g0 = R.mipmap.check_win_icon;
            } else if (info.getHost_score() == info.getAway_score()) {
                g1 = R.mipmap.check_win_icon;
            } else if (info.getHost_score() < info.getAway_score()) {
                g2 = R.mipmap.check_win_icon;
            }
        }
        holder.item_game_win_icon.setImageResource(g0);
        holder.item_game_draw_icon.setImageResource(g1);
        holder.item_game_failure_icon.setImageResource(g2);
    }

}
