package com.xingnext.bigdata.factory;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xingnext.bigdata.R;

public abstract class PullListViewHelper {

	private Activity context;
	private View mainView;
	private LayoutInflater inflater;

	private boolean isEndFinish = false;
	private int page = 1;
	private int modeId = -1;// 1.both

	private PullToRefreshListView cell_list_view_list;
	private TextView cell_list_view_empty;
	private ImageView cell_list_view_icon;
	private ListView listView;
	private View cell_list_view_ll;

	private View footer_finish;

	private OnLongItemClick onLongItemClick;

	public PullListViewHelper(Activity context, View mainView) {
		this.context = context;
		this.mainView = mainView;
		inflater = LayoutInflater.from(context);

		initView();

	}

	private void initView() {
		cell_list_view_list = (PullToRefreshListView) mainView
				.findViewById(R.id.cell_list_view_list);
		cell_list_view_ll = mainView.findViewById(R.id.cell_list_view_ll);
		cell_list_view_empty = (TextView) mainView
				.findViewById(R.id.cell_list_view_empty);
		cell_list_view_icon = (ImageView) mainView
				.findViewById(R.id.cell_list_view_icon);

		listView = cell_list_view_list.getRefreshableView();

		footer_finish = inflater.inflate(R.layout.cell_finish_end, null);
		cell_list_view_list
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						page = 1;
						updateData(page);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						page++;
						updateData(page);
					}
				});

		cell_list_view_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int count = arg0.getAdapter().getCount();
				if (isEndFinish) {
					count -= 2;
				}

				if (modeId == 1) {
					count--;
					arg2--;
				}

				if (arg2 >= 0 && arg2 < count) {
					OnItemClick(arg2);
				}
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				int count = arg0.getAdapter().getCount();
				if (isEndFinish) {
					count -= 2;
				}

				if (modeId == 1) {
					count--;
					arg2--;
				}

				if (arg2 >= 0 && arg2 < count) {
					
					if(onLongItemClick!=null){
						onLongItemClick.onLongClick(arg2);
					}
					
				}

				return true;
			}
		});

		// cell_list_view_empty.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// page = 1;
		// updateData(page);
		// }
		// });

		cell_list_view_ll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cell_list_view_empty.getVisibility() == View.VISIBLE) {
					page = 1;
					updateData(page);
				}
			}
		});

		listView.setFooterDividersEnabled(false);

	}

	public void setDivider(int color_id, int height, int selector_id) {
		ColorDrawable divider = new ColorDrawable(context.getResources()
				.getColor(color_id));
		listView.setDivider(divider);
		listView.setDividerHeight(height);

		if (selector_id != 0) {
			listView.setSelector(selector_id);
		}
	}
	
	public void setDivider(int color_id, int height){
		setDivider(color_id,height,0);
	}

	public void setMode(Mode mode) {
		if (mode == Mode.BOTH) {
			modeId = 1;
		}
		cell_list_view_list.setMode(mode);
	}

	public void setAdapter(BaseAdapter adapter) {
		cell_list_view_list.setAdapter(adapter);
	}

	public void setFinishShow() {
		if (!isEndFinish) {
			listView.addFooterView(footer_finish);
			if (modeId == 1) {
				footer_finish.setVisibility(View.VISIBLE);
				cell_list_view_list.setMode(Mode.PULL_FROM_START);
			} else {
				cell_list_view_list.setMode(Mode.DISABLED);
			}
			isEndFinish = true;
		}
	}

	public void setFinishDismiss() {
		if (isEndFinish) {
			if (footer_finish != null) {
				footer_finish.setVisibility(View.GONE);
				listView.removeFooterView(footer_finish);
			}
			if (modeId == 1) {
				cell_list_view_list.setMode(Mode.BOTH);
			} else {
				cell_list_view_list.setMode(Mode.PULL_FROM_END);
			}
			isEndFinish = false;
		}
	}

	public void setEmptyShow() {
		// cell_list_view_list.setVisibility(View.GONE);
		toEmpty();
		cell_list_view_icon.setImageResource(R.mipmap.ic_no_data);
		cell_list_view_empty.setText("暂无数据");
	}

	public void setNOWIFI(){
		cell_list_view_icon.setImageResource(R.mipmap.tabbar_icon_normal_3);
		cell_list_view_empty.setText("无法连接到网络，加载失败");
	}

	public void toEmpty(){
		cell_list_view_ll.setVisibility(View.VISIBLE);

		if (footer_finish != null) {
			footer_finish.setVisibility(View.GONE);
			listView.removeFooterView(footer_finish);
		}
		if (modeId == 1) {
			cell_list_view_list.setMode(Mode.PULL_FROM_START);
		} else {
			cell_list_view_list.setMode(Mode.DISABLED);
		}
		isEndFinish = false;
	}

	public void setEmptyDismiss() {
		// cell_list_view_list.setVisibility(View.VISIBLE);
		cell_list_view_ll.setVisibility(View.GONE);
	}

	public void setFooterDividersEnabled(boolean footerDividersEnabled) {
		listView.setFooterDividersEnabled(footerDividersEnabled);
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setRefreshComplete() {
		cell_list_view_list.onRefreshComplete();
	}

	public void setOnLongItemClick(OnLongItemClick onLongItemClick) {
		this.onLongItemClick = onLongItemClick;
	}

	public abstract void updateData(int page);

	public abstract void OnItemClick(int position);

	public interface OnLongItemClick {
		public void onLongClick(int position);
	}

	public void setImageIcon(int resId) {
		cell_list_view_icon.setImageResource(resId);
	}

	public void setEmptyText(String text) {
		cell_list_view_empty.setText(text);
	}

	public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener){
		listView.setOnScrollListener(onScrollListener);
	}

	public void scrollToTop(){
		listView.smoothScrollToPosition(0);
	}

}
