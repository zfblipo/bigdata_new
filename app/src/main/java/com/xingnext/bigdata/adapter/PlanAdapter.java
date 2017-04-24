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
 * Created by lipo on 2017/4/16.
 */
public class PlanAdapter extends BaseAdapter {

    private Activity context;
    private LayoutInflater inflater;
    private List<GameInfo> list;
    private Resources resources;
    private int blackColor;
    private int whiteColor;
    private int text9Color;

    public PlanAdapter(Activity context, List<GameInfo> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        resources = context.getResources();
        blackColor = resources.getColor(R.color.black);
        text9Color = resources.getColor(R.color.main_text9);
        whiteColor = resources.getColor(R.color.white);
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
            convertView = inflater.inflate(R.layout.item_plan, null);
            initHolder(holder, convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        initData(holder, position);
        return convertView;
    }

    private class ViewHolder {
        TextView item_plan_guess,item_plan_result,item_plan_game_sn,item_plan_game_name,item_plan_game_score,item_plan_host_name,
                item_plan_race1,item_plan_race2,item_plan_race3,item_plan_away_name,item_plan_textvs;
        private ImageView item_plan_result_icon,item_plan_host_icon,item_plan_away_icon;
        private View item_plan_text1,item_plan_text2,item_plan_text3;

        private View[] layouts = new View[3];
        private TextView[] cell_text1s = new TextView[3];
        private TextView[] cell_text2s = new TextView[3];
    }

    private void initHolder(ViewHolder holder, View convertView) {

        holder.item_plan_result_icon = (ImageView) convertView
                .findViewById(R.id.item_plan_result_icon);
        holder.item_plan_host_icon = (ImageView) convertView
                .findViewById(R.id.item_plan_host_icon);
        holder.item_plan_away_icon = (ImageView) convertView
                .findViewById(R.id.item_plan_away_icon);

        holder.item_plan_text1 = convertView
                .findViewById(R.id.item_plan_text1);
        holder.item_plan_text2 = convertView
                .findViewById(R.id.item_plan_text2);
        holder.item_plan_text3 = convertView
                .findViewById(R.id.item_plan_text3);
        holder.layouts[0] = holder.item_plan_text1;
        holder.layouts[1] = holder.item_plan_text2;
        holder.layouts[2] = holder.item_plan_text3;

        holder.item_plan_guess = (TextView) convertView
                .findViewById(R.id.item_plan_guess);
        holder.item_plan_result = (TextView) convertView
                .findViewById(R.id.item_plan_result);
        holder.item_plan_game_sn = (TextView) convertView
                .findViewById(R.id.item_plan_game_sn);
        holder.item_plan_game_name = (TextView) convertView
                .findViewById(R.id.item_plan_game_name);
        holder.item_plan_game_score = (TextView) convertView
                .findViewById(R.id.item_plan_game_score);
        holder.item_plan_host_name = (TextView) convertView
                .findViewById(R.id.item_plan_host_name);
        holder.item_plan_race1 = (TextView) convertView
                .findViewById(R.id.item_plan_race1);
        holder.item_plan_race2 = (TextView) convertView
                .findViewById(R.id.item_plan_race2);
        holder.item_plan_race3 = (TextView) convertView
                .findViewById(R.id.item_plan_race3);
        holder.item_plan_away_name = (TextView) convertView
                .findViewById(R.id.item_plan_away_name);
        holder.item_plan_textvs = (TextView) convertView
                .findViewById(R.id.item_plan_textvs);

        holder.cell_text1s[0] = holder.item_plan_host_name;
        holder.cell_text1s[1] = holder.item_plan_textvs;
        holder.cell_text1s[2] = holder.item_plan_away_name;

        holder.cell_text2s[0] = holder.item_plan_race1;
        holder.cell_text2s[1] = holder.item_plan_race2;
        holder.cell_text2s[2] = holder.item_plan_race3;

        convertView.setTag(holder);
    }

    private void initData(ViewHolder holder, int position) {
        GameInfo info = list.get(position);

        MyImageLoader.loader(context,holder.item_plan_host_icon,info.getHost_team_image());
        MyImageLoader.loader(context,holder.item_plan_away_icon,info.getAway_team_image());

        holder.item_plan_game_sn.setText(info.getMatch_sn());
        holder.item_plan_game_name.setText(info.getSeason_pre()+"  "+info.getMatch_status_desc());
        holder.item_plan_game_score.setText(info.getHost_score()+":"+info.getAway_score());
        holder.item_plan_host_name.setText(info.getHost_name());
        holder.item_plan_away_name.setText(info.getAway_name());
        holder.item_plan_race1.setText("主胜  "+info.getSpf().get(0));
        holder.item_plan_race2.setText("平  "+info.getSpf().get(1));
        holder.item_plan_race3.setText("客胜  "+info.getSpf().get(2));

        int winNum = -1;
        String resultStr = "";

        if("4".equals(info.getMatch_status())){
            if (info.getHost_score() > info.getAway_score()) {
                winNum = 3;
                resultStr = "主胜";
            } else if (info.getHost_score() == info.getAway_score()) {
                winNum = 1;
                resultStr = "平局";
            } else if (info.getHost_score() < info.getAway_score()) {
                winNum = 0;
                resultStr = "客胜";
            }
        }else{
            resultStr = "进行中";
        }

        holder.item_plan_result.setText(resultStr);

        String planStr = "";
        boolean isRight = false;

        int g[] = new int[3];

        String forecast = info.getForecast();
        if(forecast!=null){
            String[] fores = forecast.split(",");
            for (int i = 0; i < fores.length; i++) {
                if ("3".equals(fores[i])) {
                    planStr = "主胜 ";
                    g[0] = 1;
                    if(winNum == 3){
                        isRight = true;
                        g[0] = 2;
                    }
                } else if ("1".equals(fores[i])) {
                    planStr = "平局 ";
                    g[1] = 1;
                    if(winNum == 1){
                        isRight = true;
                        g[1] = 2;
                    }
                } else if ("0".equals(fores[i])) {
                    planStr = "客胜 ";
                    g[2] = 1;
                    if(winNum == 0){
                        isRight = true;
                        g[2] = 2;
                    }
                }
            }
        }

        holder.item_plan_guess.setText("推荐选项："+planStr);
        if(isRight){
            holder.item_plan_result_icon.setImageResource(R.mipmap.game_state_win);
        }else{
            holder.item_plan_result_icon.setImageResource(R.mipmap.game_state_miss);
        }

        for (int i = 0; i < 3; i++) {
           if(g[i] == 0){
               holder.cell_text1s[i].setTextColor(whiteColor);
               holder.cell_text2s[i].setTextColor(text9Color);
               holder.layouts[i].setBackgroundResource(R.drawable.white_lborder);
           }else if(g[i] == 1){
               holder.cell_text1s[i].setTextColor(blackColor);
               holder.cell_text2s[i].setTextColor(blackColor);
               holder.layouts[i].setBackgroundResource(R.drawable.main_text9_lbg);
           }else if(g[i] == 2){
               holder.cell_text1s[i].setTextColor(blackColor);
               holder.cell_text2s[i].setTextColor(blackColor);
               holder.layouts[i].setBackgroundResource(R.drawable.recomm_orange_lbg);
           }
        }
    }

}
