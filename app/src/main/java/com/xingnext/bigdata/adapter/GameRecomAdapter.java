package com.xingnext.bigdata.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lipo.views.RoundedImageView;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.beans.GameInfo;
import com.xingnext.bigdata.beans.GameRecomInfo;
import com.xingnext.bigdata.beans.UserInfo;
import com.xingnext.bigdata.utils.MyImageLoader;
import com.xingnext.bigdata.utils.MyPublic;

import java.util.List;

public class GameRecomAdapter extends BaseAdapter {

	private Activity context;
	private LayoutInflater inflater;
	private List<GameRecomInfo> list;

	public GameRecomAdapter(Activity context, List<GameRecomInfo> list) {
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
			convertView = inflater.inflate(R.layout.item_game_recom, null);
			initHolder(holder, convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		initData(holder, position);
		return convertView;
	}

	private class ViewHolder {
		RoundedImageView item_game_recom_head;
		TextView item_game_recom_name, item_game_recom_lab1,
				item_game_recom_lab2, item_game_recom_winrate,
				item_game_recom_num, item_game_recom_type,
				item_game_recom_time, item_game_recom_price,
				item_game_recom_team_name1, item_game_recom_team_name2,
				item_game_recom_infor, item_game_recom_count,item_game_recom_content;
		ImageView item_game_recom_team_icon1, item_game_recom_team_icon2;
	}

	private void initHolder(ViewHolder holder, View convertView) {
		holder.item_game_recom_head = (RoundedImageView) convertView
				.findViewById(R.id.item_game_recom_head);
		holder.item_game_recom_name = (TextView) convertView
				.findViewById(R.id.item_game_recom_name);
		holder.item_game_recom_lab1 = (TextView) convertView
				.findViewById(R.id.item_game_recom_lab1);
		holder.item_game_recom_lab2 = (TextView) convertView
				.findViewById(R.id.item_game_recom_lab2);
		holder.item_game_recom_winrate = (TextView) convertView
				.findViewById(R.id.item_game_recom_winrate);
		holder.item_game_recom_num = (TextView) convertView
				.findViewById(R.id.item_game_recom_num);
		holder.item_game_recom_type = (TextView) convertView
				.findViewById(R.id.item_game_recom_type);
		holder.item_game_recom_time = (TextView) convertView
				.findViewById(R.id.item_game_recom_time);
		holder.item_game_recom_price = (TextView) convertView
				.findViewById(R.id.item_game_recom_price);
		holder.item_game_recom_team_name1 = (TextView) convertView
				.findViewById(R.id.item_game_recom_team_name1);
		holder.item_game_recom_team_name2 = (TextView) convertView
				.findViewById(R.id.item_game_recom_team_name2);
		holder.item_game_recom_infor = (TextView) convertView
				.findViewById(R.id.item_game_recom_infor);
		holder.item_game_recom_count = (TextView) convertView
				.findViewById(R.id.item_game_recom_count);
		holder.item_game_recom_content = (TextView) convertView
				.findViewById(R.id.item_game_recom_content);

		holder.item_game_recom_team_icon1 = (ImageView) convertView
				.findViewById(R.id.item_game_recom_team_icon1);
		holder.item_game_recom_team_icon2 = (ImageView) convertView
				.findViewById(R.id.item_game_recom_team_icon2);

		convertView.setTag(holder);
	}

	private void initData(ViewHolder holder, int position) {
		GameRecomInfo info = list.get(position);
		UserInfo userInfo = info.getUser();
		MyImageLoader.loaderHead(context,holder.item_game_recom_head,userInfo.getPic());
		holder.item_game_recom_name.setText(userInfo.getNickname());
		holder.item_game_recom_lab1.setText(userInfo.getStat_win_count()+"连中");
		holder.item_game_recom_lab2.setText(userInfo.getStat_count_7()+"发"+userInfo.getStat_count_7_hit()+"中");
		holder.item_game_recom_num.setText("近"+userInfo.getStat_rate_count()+"场胜率：");
		holder.item_game_recom_winrate.setText(MyPublic.stringToBFB(userInfo.getStat_rate()));
		int lent = info.getMatch_list().size();
		if(lent>0){
			if(lent == 1){
				holder.item_game_recom_type.setText("单场");
				GameInfo gameInfo = info.getMatch_list().get(0);
				holder.item_game_recom_time.setText(gameInfo.getSeason_pre()+"  "+gameInfo.getMatch_time()+"开赛");
				MyImageLoader.loader(context,holder.item_game_recom_team_icon1,gameInfo.getHost_team_image());
				MyImageLoader.loader(context,holder.item_game_recom_team_icon2,gameInfo.getAway_team_image());
				holder.item_game_recom_team_name1.setText(gameInfo.getHost_name());
				holder.item_game_recom_team_name2.setText(gameInfo.getAway_name());
				holder.item_game_recom_content.setVisibility(View.GONE);
			}else{
				holder.item_game_recom_type.setText("串关");
				holder.item_game_recom_content.setVisibility(View.VISIBLE);
				if(!info.getTitle().isEmpty()){
					holder.item_game_recom_content.setText(info.getTitle());
				}
				holder.item_game_recom_time.setText(info.getCreate_time());
			}

		}

		holder.item_game_recom_price.setText(info.getPrice()+"球币");
		holder.item_game_recom_infor.setText(info.getRemark());
		holder.item_game_recom_count.setText(info.getOrder_count()+"人订阅");
	}

}
