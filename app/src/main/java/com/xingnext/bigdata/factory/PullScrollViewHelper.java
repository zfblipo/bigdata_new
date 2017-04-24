package com.xingnext.bigdata.factory;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingnext.bigdata.R;
import com.xingnext.bigdata.views.MyListenerScrollView;

public abstract class PullScrollViewHelper {

	private Activity mContext;
	private View mainView,footerView,contentView;
	private SwipeRefreshLayout swipeLayout;
	private LayoutInflater inflater;
	private boolean isRefreshing = false;
	private boolean isMoreLoading = false;
	private boolean isPullMoreEnable = true;
	private ObjectAnimator anim;
	
	private MyListenerScrollView cell_pull_scroll_view;
	private LinearLayout cell_pull_scroll_content;
	private View cell_pull_scroll_empty;
	
	private ImageView pull_footer_progress;
	private TextView pull_footer_text;
	private View pull_footer_line,pull_footer_more;
	private int bottomHeight = 0;
	
	public PullScrollViewHelper(Activity mContext,View mainView,View contentView){
		this.mContext = mContext;
		this.mainView = mainView;
		this.contentView = contentView;
		isMoreLoading = false;
		inflater = LayoutInflater.from(mContext);
		
		initView();
	}
	
	private void initView(){
		swipeLayout = (SwipeRefreshLayout) mainView.findViewById(R.id.cell_pull_scroll_swipe);
		cell_pull_scroll_view = (MyListenerScrollView) mainView.findViewById(R.id.cell_pull_scroll_view);
		cell_pull_scroll_content = (LinearLayout) mainView.findViewById(R.id.cell_pull_scroll_content);
		footerView = inflater.inflate(R.layout.pull_footer, null);
		cell_pull_scroll_content.addView(contentView);
		cell_pull_scroll_content.addView(footerView);
		
		pull_footer_progress = (ImageView) footerView.findViewById(R.id.pull_footer_progress);
		pull_footer_text = (TextView) footerView.findViewById(R.id.pull_footer_text);
		pull_footer_line = footerView.findViewById(R.id.pull_footer_line);
		pull_footer_more = footerView.findViewById(R.id.pull_footer_more);
		
		anim = ObjectAnimator.ofFloat(pull_footer_progress, "rotation", 0F,360F);
		anim.setRepeatMode(ObjectAnimator.RESTART);
		anim.setRepeatCount(-1);
		anim.setDuration(800);
		anim.setInterpolator(new LinearInterpolator());
		anim.start();
		
		cell_pull_scroll_view.setOnScrollListener(new MyListenerScrollView.OnScrollListener() {
			
			@Override
			public void onListener(int l, int t, int oldl, int oldt) {
				// TODO Auto-generated method stub
				if(bottomHeight == 0){
					bottomHeight = contentView.getBottom();
				}
				if(t>=bottomHeight){
					if(!isMoreLoading&&isPullMoreEnable){
						isMoreLoading = true;
						swipeLayout.setEnabled(false);
						pullMore();
					}
				}
			}
		});
		
		swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				isRefreshing = true;
				isPullMoreEnable = false;
				footerView.setVisibility(View.GONE);
				pullRefersh();
			}
		});

		setEmptyShow();
	}
	
	public void onRefreshComplete(){
		if(isRefreshing){
			isRefreshing = false;
			isPullMoreEnable = true;
			footerView.setVisibility(View.VISIBLE);
		}
		if(isMoreLoading){
			isMoreLoading = false;
			swipeLayout.setEnabled(true);
		}
		swipeLayout.setRefreshing(false);
	}
	
	public void setPullMoreEnable(boolean isPullMoreEnable){
		this.isPullMoreEnable = isPullMoreEnable;
		if(isPullMoreEnable){
			pull_footer_progress.setVisibility(View.VISIBLE);
			pull_footer_line.setVisibility(View.GONE);
			pull_footer_text.setText("正在加载...");
		}else{
			pull_footer_progress.setVisibility(View.GONE);
			pull_footer_line.setVisibility(View.VISIBLE);
			pull_footer_text.setText("  数据已加载完  ");
		}
	}

	public void setEmptyShow(){
		isPullMoreEnable = false;
		footerView.setVisibility(View.GONE);
	}

	public void setEmptyDismiss(){
		isPullMoreEnable = true;
		footerView.setVisibility(View.VISIBLE);
	}

	protected abstract void pullRefersh();
	protected abstract void pullMore();
}
