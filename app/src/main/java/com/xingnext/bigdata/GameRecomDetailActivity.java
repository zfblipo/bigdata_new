package com.xingnext.bigdata;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lipo.utils.DisplayUtil;
import com.lipo.views.NoScrollListview;
import com.lipo.views.RoundedImageView;
import com.xingnext.bigdata.adapter.PlanAdapter;
import com.xingnext.bigdata.adapter.RaceAdapter;
import com.xingnext.bigdata.beans.GameRecomInfo;
import com.xingnext.bigdata.beans.UserInfo;
import com.xingnext.bigdata.factory.TitleBackHelper;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyImageLoader;
import com.xingnext.bigdata.utils.MyPublic;
import com.xingnext.bigdata.utils.MyStatic;
import com.xingnext.bigdata.utils.MyUrl;
import com.xingnext.bigdata.views.MyListenerScrollView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GameRecomDetailActivity extends BaseActivity {

    private Intent intent;
    private String recom_id;

    private View game_detail_recom_main, game_detail_recom_top, game_detail_recom_top_head;
    private MyListenerScrollView game_detail_recom_scroll;
    private TitleBackHelper titleBackHelper;
    private int dp48 = 0;
    private int scrollTopHeight = 0;
    private int titleMode = 0;


    private TextView game_detail_recom_pay_money, game_detail_recom_topay, game_detail_recom_name, game_detail_recom_nicket, game_detail_recom_winnum,
            game_detail_recom_total, game_detail_recom_share, game_detail_recom_winrate;
    private NoScrollListview game_recom_detail_recelist, game_recom_detail_pay_detail;
    private RoundedImageView game_detail_recom_head;

    private View game_recom_detail_gif;
    private TextView game_recom_detail_gif_time, game_recom_detail_gif_name1, game_recom_detail_gif_name2, game_recom_detail_gif_probability,
            game_recom_detail_gif_win, game_recom_detail_gif_draw, game_recom_detail_gif_failure;
    private ImageView game_recom_detail_gif_icon1, game_recom_detail_gif_icon2, game_recom_detail_gif_win_icon, game_recom_detail_gif_draw_icon, game_recom_detail_gif_failure_icon;

    private TextView game_recom_detail_ordernum, game_recom_detail_time, game_recom_detail_price;
    private View game_recom_detail_nopay, game_detail_recom_bottom,game_recom_detail_pay;

    private TextView game_recom_detail_title,game_recom_detail_state,game_recom_detail_time0,game_recom_detail_status,game_recom_detail_content;

    private RaceAdapter raceAdapter;

    private MyHttpConn httpConn;
    private Gson gson;
    private GameRecomInfo info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_recom_detail);

        intent = getIntent();
        recom_id = intent.getStringExtra("recom_id");

        dp48 = DisplayUtil.dip2px(mContext, 48);
        httpConn = new MyHttpConn(mContext);
        gson = new Gson();

        initView();
        initGifView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void initGifView() {
        game_recom_detail_gif = findViewById(R.id.game_recom_detail_gif);
        game_recom_detail_gif_time = (TextView) findViewById(R.id.game_recom_detail_gif_time);
        game_recom_detail_gif_name1 = (TextView) findViewById(R.id.game_recom_detail_gif_name1);
        game_recom_detail_gif_name2 = (TextView) findViewById(R.id.game_recom_detail_gif_name2);
        game_recom_detail_gif_probability = (TextView) findViewById(R.id.game_recom_detail_gif_probability);
        game_recom_detail_gif_win = (TextView) findViewById(R.id.game_recom_detail_gif_win);
        game_recom_detail_gif_draw = (TextView) findViewById(R.id.game_recom_detail_gif_draw);
        game_recom_detail_gif_failure = (TextView) findViewById(R.id.game_recom_detail_gif_failure);
        game_recom_detail_gif_icon1 = (ImageView) findViewById(R.id.game_recom_detail_gif_icon1);
        game_recom_detail_gif_icon2 = (ImageView) findViewById(R.id.game_recom_detail_gif_icon2);
        game_recom_detail_gif_win_icon = (ImageView) findViewById(R.id.game_recom_detail_gif_win_icon);
        game_recom_detail_gif_draw_icon = (ImageView) findViewById(R.id.game_recom_detail_gif_draw_icon);
        game_recom_detail_gif_failure_icon = (ImageView) findViewById(R.id.game_recom_detail_gif_failure_icon);
    }

    private void initView() {
        titleBackHelper = new TitleBackHelper(mContext, R.id.game_detail_recom_top, "方案详情");
        game_detail_recom_main = findViewById(R.id.game_detail_recom_main);
        game_detail_recom_top_head = findViewById(R.id.game_detail_recom_top_head);
        game_detail_recom_scroll = (MyListenerScrollView) findViewById(R.id.game_detail_recom_scroll);
        game_detail_recom_pay_money = (TextView) findViewById(R.id.game_detail_recom_pay_money);
        game_detail_recom_topay = (TextView) findViewById(R.id.game_detail_recom_topay);
        game_detail_recom_name = (TextView) findViewById(R.id.game_detail_recom_name);
        game_detail_recom_winnum = (TextView) findViewById(R.id.game_detail_recom_winnum);
        game_detail_recom_nicket = (TextView) findViewById(R.id.game_detail_recom_nicket);
        game_detail_recom_total = (TextView) findViewById(R.id.game_detail_recom_total);
        game_detail_recom_share = (TextView) findViewById(R.id.game_detail_recom_share);
        game_detail_recom_winrate = (TextView) findViewById(R.id.game_detail_recom_winrate);

        game_recom_detail_ordernum = (TextView) findViewById(R.id.game_recom_detail_ordernum);
        game_recom_detail_time = (TextView) findViewById(R.id.game_recom_detail_time);
        game_recom_detail_price = (TextView) findViewById(R.id.game_recom_detail_price);

        game_recom_detail_recelist = (NoScrollListview) findViewById(R.id.game_recom_detail_recelist);
        game_detail_recom_head = (RoundedImageView) findViewById(R.id.game_detail_recom_head);

        game_recom_detail_nopay = findViewById(R.id.game_recom_detail_nopay);
        game_recom_detail_pay = findViewById(R.id.game_recom_detail_pay);
        game_recom_detail_pay_detail = (NoScrollListview) findViewById(R.id.game_recom_detail_pay_detail);
        game_detail_recom_bottom = findViewById(R.id.game_detail_recom_bottom);

        game_recom_detail_title = (TextView) findViewById(R.id.game_recom_detail_title);
        game_recom_detail_state = (TextView) findViewById(R.id.game_recom_detail_state);
        game_recom_detail_time0 = (TextView) findViewById(R.id.game_recom_detail_time0);
        game_recom_detail_status = (TextView) findViewById(R.id.game_recom_detail_status);
        game_recom_detail_content = (TextView) findViewById(R.id.game_recom_detail_content);

        game_detail_recom_scroll.setOnScrollListener(new MyListenerScrollView.OnScrollListener() {
            @Override
            public void onListener(int l, int t, int oldl, int oldt) {
                if (scrollTopHeight == 0) {
                    scrollTopHeight = game_detail_recom_top_head.getBottom() - dp48;
                }

                if (t > scrollTopHeight) {
                    if (titleMode != 1) {
                        titleMode = 1;
                        titleBackHelper.setTitleBg(R.color.main_black);
                    }
                } else {
                    if (titleMode != 0) {
                        titleMode = 0;
                        titleBackHelper.setTitleBg(0);
                    }
                }

            }
        });

        game_detail_recom_topay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLogin();
                Intent intentp = new Intent();
                intentp.putExtra("plan_id", info.getId());
                intentp.putExtra("amount", info.getPrice());
                intentp.putExtra("order_type", "2");
                startIntent(intentp, PayWebActivity.class);
            }
        });

    }

    private void getData() {
        String url = MyUrl.getHotDetail + recom_id;
        Map<String, String> params = new HashMap<String, String>();
        httpConn.httpGet(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                String data = json.optString("data");
                info = gson.fromJson(data, GameRecomInfo.class);

                fillData();
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    private void fillData() {
        UserInfo userInfo = info.getUser();
        MyImageLoader.loader(mContext, game_detail_recom_head, userInfo.getPic());
        game_detail_recom_name.setText(userInfo.getNickname());
        game_detail_recom_nicket.setText(userInfo.getSignature());
        game_detail_recom_total.setText(userInfo.getStat_count());
        game_detail_recom_share.setText(userInfo.getStat_count_7() + "发" + userInfo.getStat_count_7_hit() + "中");
        game_detail_recom_winrate.setText(MyPublic.stringToBFB(userInfo.getStat_rate()));
        game_detail_recom_winnum.setText(userInfo.getStat_rate_count() + "场胜率");

        ColorDrawable divider = new ColorDrawable(getResources().getColor(R.color.main_bg));
        game_recom_detail_recelist.setDivider(divider);
        game_recom_detail_recelist.setDividerHeight(1);

        game_recom_detail_recelist.setAdapter(new RaceAdapter(mContext, info.getMatch_list()));

        game_recom_detail_pay_detail.setDivider(divider);
        game_recom_detail_pay_detail.setDividerHeight(1);


        game_recom_detail_ordernum.setText(info.getOrder_count() + "人订阅");
        game_recom_detail_time.setText(info.getCreate_time());
        game_recom_detail_price.setText(info.getPrice() + "元");

        game_detail_recom_pay_money.setText("查看推荐需支付：" + info.getPrice() + "元");

        game_detail_recom_main.setVisibility(View.VISIBLE);

        if ("1".equals(info.getOrder_status())) {
            game_recom_detail_nopay.setVisibility(View.GONE);
            game_recom_detail_pay.setVisibility(View.VISIBLE);
            game_detail_recom_bottom.setVisibility(View.GONE);
            game_recom_detail_pay_detail.setAdapter(new PlanAdapter(mContext, info.getMatch_list()));
        } else {
            game_recom_detail_nopay.setVisibility(View.VISIBLE);
            game_recom_detail_pay.setVisibility(View.GONE);
            game_detail_recom_bottom.setVisibility(View.VISIBLE);
        }

        game_recom_detail_title.setText(info.getTitle());
        if(info.getMatch_list().size() == 2){
            game_recom_detail_state.setText("串关");
        }else{
            game_recom_detail_state.setText("单关");
        }
        game_recom_detail_time0.setText(info.getLatest_time()+" 截止");
        game_recom_detail_content.setText("                    "+info.getRemark());

    }

    private void toLogin() {
        if (MyStatic.userData.access_token.isEmpty()) {
            startIntent(LoginActivity.class);
            return;
        }
    }

}
