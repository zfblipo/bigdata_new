package com.xingnext.bigdata.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingnext.bigdata.R;
import com.xingnext.bigdata.beans.GameInfo;

import java.util.List;

public class GameAdapter extends BaseAdapter {

	private Activity context;
	private LayoutInflater inflater;
	private List<GameInfo> list;

	public GameAdapter(Activity context, List<GameInfo> list) {
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
			convertView = inflater.inflate(R.layout.item_game, null);
			initHolder(holder, convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		initData(holder, position);
		return convertView;
	}

	private class ViewHolder {
		TextView item_game_time, item_game_state, item_game_name1,
				item_game_name2, item_game_probability, item_game_price,
				item_game_persons;
		ImageView item_game_icon1, item_game_icon2;
		TextView item_game_win, item_game_draw, item_game_failure;
		ImageView item_game_win_icon, item_game_draw_icon,
				item_game_failure_icon;
	}

	private void initHolder(ViewHolder holder, View convertView) {
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

	private void initData(ViewHolder holder, int position) {
		GameInfo info = list.get(position);

	}

}
