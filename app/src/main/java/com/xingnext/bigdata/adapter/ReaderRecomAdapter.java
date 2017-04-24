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
import com.xingnext.bigdata.beans.GameRecomInfo;
import com.xingnext.bigdata.beans.ReaderRecomInfo;
import com.xingnext.bigdata.utils.MyImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */
public class ReaderRecomAdapter extends BaseAdapter {

    private Activity context;
    private LayoutInflater inflater;
    private List<ReaderRecomInfo> list;

    public ReaderRecomAdapter(Activity context, List<ReaderRecomInfo> list) {
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
            convertView = inflater.inflate(R.layout.item_record_recom, null);
            initHolder(holder, convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        initData(holder, position);
        return convertView;
    }

    private class ViewHolder {
        TextView item_record_recom_time,item_record_recom_status,item_record_recom_type,item_record_recom_game_time,item_record_recom_price,
                item_record_recom_team_name1,item_record_recom_team_name2,item_record_recom_content,item_record_recom_infor,item_record_recom_count;
        ImageView item_record_recom_team_icon1,item_record_recom_team_icon2;

        View item_record_recom_content2;

    }

    private void initHolder(ViewHolder holder, View convertView) {
        holder.item_record_recom_time = (TextView) convertView
                .findViewById(R.id.item_record_recom_time);
        holder.item_record_recom_status = (TextView) convertView
                .findViewById(R.id.item_record_recom_status);
        holder.item_record_recom_type = (TextView) convertView
                .findViewById(R.id.item_record_recom_type);
        holder.item_record_recom_game_time = (TextView) convertView
                .findViewById(R.id.item_record_recom_game_time);
        holder.item_record_recom_price = (TextView) convertView
                .findViewById(R.id.item_record_recom_price);
        holder.item_record_recom_team_name1 = (TextView) convertView
                .findViewById(R.id.item_record_recom_team_name1);
        holder.item_record_recom_team_name2 = (TextView) convertView
                .findViewById(R.id.item_record_recom_team_name2);
        holder.item_record_recom_infor = (TextView) convertView
                .findViewById(R.id.item_record_recom_infor);
        holder.item_record_recom_count = (TextView) convertView
                .findViewById(R.id.item_record_recom_count);


        holder.item_record_recom_team_icon1 = (ImageView) convertView
                .findViewById(R.id.item_record_recom_team_icon1);
        holder.item_record_recom_team_icon2 = (ImageView) convertView
                .findViewById(R.id.item_record_recom_team_icon2);

        holder.item_record_recom_content = (TextView) convertView
                .findViewById(R.id.item_record_recom_content);
        holder.item_record_recom_content2 = convertView
                .findViewById(R.id.item_record_recom_content2);

        convertView.setTag(holder);
    }

    private void initData(ViewHolder holder, int position) {
        final ReaderRecomInfo info = list.get(position);
        holder.item_record_recom_time.setText("订阅时间："+info.getCreate_time());
        holder.item_record_recom_status.setText(info.getHit_status());

        GameRecomInfo recomInfo = info.getDetail();

        int lent = recomInfo.getMatch_list().size();
        if(lent>0){
            if(lent == 1){
                holder.item_record_recom_type.setText("单场");
                GameInfo gameInfo = recomInfo.getMatch_list().get(0);
                holder.item_record_recom_game_time.setText(gameInfo.getMatch_sn()+"  "+gameInfo.getSeason_pre()+"  "+gameInfo.getMatch_time()+"开赛");
                MyImageLoader.loader(context,holder.item_record_recom_team_icon1,gameInfo.getHost_team_image());
                MyImageLoader.loader(context,holder.item_record_recom_team_icon2,gameInfo.getAway_team_image());
                holder.item_record_recom_team_name1.setText(gameInfo.getHost_name());
                holder.item_record_recom_team_name2.setText(gameInfo.getAway_name());
                holder.item_record_recom_content.setVisibility(View.GONE);
                holder.item_record_recom_content2.setVisibility(View.VISIBLE);
            }else{
                holder.item_record_recom_type.setText("串关");
                holder.item_record_recom_content.setVisibility(View.VISIBLE);
                holder.item_record_recom_content2.setVisibility(View.GONE);
                if(!recomInfo.getTitle().isEmpty()){
                    holder.item_record_recom_content.setText(recomInfo.getTitle());
                }
            }
        }
        holder.item_record_recom_price.setText(recomInfo.getPrice()+"球币");
        holder.item_record_recom_infor.setText(recomInfo.getRemark());
        holder.item_record_recom_count.setText(recomInfo.getOrder_count()+"人订阅");

        holder.item_record_recom_status.setOnClickListener(new View.OnClickListener() {
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
}
