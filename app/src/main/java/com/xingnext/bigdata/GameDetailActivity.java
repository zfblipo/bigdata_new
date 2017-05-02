package com.xingnext.bigdata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lipo.utils.DisplayUtil;
import com.lipo.views.RoundedImageView;
import com.xingnext.bigdata.beans.GameInfo;
import com.xingnext.bigdata.factory.TitleBackHelper;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyImageLoader;
import com.xingnext.bigdata.utils.MyPublic;
import com.xingnext.bigdata.utils.MyStatic;
import com.xingnext.bigdata.utils.MyUrl;
import com.xingnext.bigdata.views.MyListenerScrollView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameDetailActivity extends BaseActivity {

    private String match_id;
    private String season_desc;
    private Intent intent;
    private int isFav = 0;

    private TitleBackHelper titleBackHelper;
    private ImageView titleIcon;
    private MyListenerScrollView game_detail_scroll;
    private int dp48 = 0;
    private int scrollTopHeight = 0;
    private int titleMode = 0;

    private View game_detail_content, game_detail_content_top;
    private MyHttpConn httpConn;
    private Gson gson;
    private GameInfo gameInfo;

    private RoundedImageView game_detail_host_icon, game_detail_away_icon;
    private TextView game_detail_host_rank, game_detail_host_name, game_detail_time, game_detail_score, game_detail_away_name, game_detail_away_rank;

    private View game_detail_bottom, game_detail_guess_reslut;
    private TextView game_detail_bottom_button, game_detail_more;
    private TextView game_detail_winrateg, game_detail_winrate, game_detail_ping, game_detail_pinrate, game_detail_failg, game_detail_failrate, game_detail_guess_no;

    private TextView game_detail_odds_ou, game_detail_odds_yp, game_detail_ai_match, game_detail_ai_model, game_detail_ai_rule, game_detail_host_power, game_detail_host_name2, game_detail_away_power, game_detail_away_name2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        dp48 = DisplayUtil.dip2px(mContext, 48);
        intent = getIntent();
        match_id = intent.getStringExtra("match_id");
        season_desc = intent.getStringExtra("season_desc");

        httpConn = new MyHttpConn(mContext);
        gson = new Gson();

        initView();
        initView1();
        initView2();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void initView() {
        titleBackHelper = new TitleBackHelper(mContext, R.id.game_detail_top, season_desc);
        titleIcon = titleBackHelper.getTitleIcon();
        game_detail_scroll = (MyListenerScrollView) findViewById(R.id.game_detail_scroll);
        game_detail_content = findViewById(R.id.game_detail_content);
        game_detail_content_top = findViewById(R.id.game_detail_content_top);

        game_detail_host_icon = (RoundedImageView) findViewById(R.id.game_detail_host_icon);
        game_detail_away_icon = (RoundedImageView) findViewById(R.id.game_detail_away_icon);

        game_detail_host_rank = (TextView) findViewById(R.id.game_detail_host_rank);
        game_detail_host_name = (TextView) findViewById(R.id.game_detail_host_name);
        game_detail_time = (TextView) findViewById(R.id.game_detail_time);
        game_detail_score = (TextView) findViewById(R.id.game_detail_score);
        game_detail_away_name = (TextView) findViewById(R.id.game_detail_away_name);
        game_detail_away_rank = (TextView) findViewById(R.id.game_detail_away_rank);
        game_detail_more = (TextView) findViewById(R.id.game_detail_more);

        game_detail_scroll.setOnScrollListener(new MyListenerScrollView.OnScrollListener() {
            @Override
            public void onListener(int l, int t, int oldl, int oldt) {
                if (scrollTopHeight == 0) {
                    scrollTopHeight = game_detail_content_top.getBottom() - dp48;
                }
                if (t > scrollTopHeight) {
                    if (titleMode != 1) {
                        titleMode = 1;
                        titleBackHelper.setTitleBg(R.color.main_black);
                    }
                } else {
                    if (titleMode != 0) {
                        titleMode = 0;
                        titleBackHelper.setTitleBg(R.color.transparent);
                    }
                }

            }
        });

        game_detail_more.setOnClickListener(onclick);
        titleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toLogin()) {
                    collectBall();
                }
            }
        });
    }

    private void initView1() {
        game_detail_bottom = findViewById(R.id.game_detail_bottom);
        game_detail_guess_reslut = findViewById(R.id.game_detail_guess_reslut);
        game_detail_bottom_button = (TextView) findViewById(R.id.game_detail_bottom_button);
        game_detail_winrateg = (TextView) findViewById(R.id.game_detail_winrateg);
        game_detail_winrate = (TextView) findViewById(R.id.game_detail_winrate);
        game_detail_ping = (TextView) findViewById(R.id.game_detail_ping);
        game_detail_pinrate = (TextView) findViewById(R.id.game_detail_pinrate);
        game_detail_failg = (TextView) findViewById(R.id.game_detail_failg);
        game_detail_failrate = (TextView) findViewById(R.id.game_detail_failrate);
        game_detail_guess_no = (TextView) findViewById(R.id.game_detail_guess_no);

        game_detail_bottom_button.setOnClickListener(onclick);
    }

    private void initView2() {
        game_detail_odds_ou = (TextView) findViewById(R.id.game_detail_odds_ou);
        game_detail_odds_yp = (TextView) findViewById(R.id.game_detail_odds_yp);
        game_detail_ai_match = (TextView) findViewById(R.id.game_detail_ai_match);
        game_detail_ai_model = (TextView) findViewById(R.id.game_detail_ai_model);
        game_detail_ai_rule = (TextView) findViewById(R.id.game_detail_ai_rule);
        game_detail_host_power = (TextView) findViewById(R.id.game_detail_host_power);
        game_detail_host_name2 = (TextView) findViewById(R.id.game_detail_host_name2);
        game_detail_away_power = (TextView) findViewById(R.id.game_detail_away_power);
        game_detail_away_name2 = (TextView) findViewById(R.id.game_detail_away_name2);
    }

    private void getData() {
        String url = MyUrl.getplanDetail + match_id;
        Map<String, String> params = new HashMap<String, String>();
        httpConn.httpGet(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                JSONObject data = json.optJSONObject("data");
                gameInfo = gson.fromJson(data.toString(), GameInfo.class);
                JSONArray spf = data.optJSONArray("spf");
                GameInfo.setSpf(gameInfo, spf);
                fillData();
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    private void isfav() {
        isFav = 1;
        titleIcon.setImageResource(R.mipmap.collected_icon);
    }

    private void isnofav() {
        isFav = 0;
        titleIcon.setImageResource(R.mipmap.collect_no_icon);
    }

    private void fillData() {
        if (gameInfo != null) {
            game_detail_content.setVisibility(View.VISIBLE);
            MyImageLoader.loader(mContext, game_detail_host_icon, gameInfo.getHost_team_image());
            MyImageLoader.loader(mContext, game_detail_away_icon, gameInfo.getAway_team_image());
            game_detail_host_rank.setText("[" + gameInfo.getHost_rank() + "]");
            game_detail_away_rank.setText("[" + gameInfo.getAway_rank() + "]");
            game_detail_host_name.setText(gameInfo.getHost_name());
            game_detail_away_name.setText(gameInfo.getAway_name());
            game_detail_time.setText(gameInfo.getMatch_time());
            game_detail_score.setText(gameInfo.getHost_score() + " : " + gameInfo.getAway_score());

            if ("4".equals(gameInfo.getMatch_status())) {
                game_detail_guess_no.setVisibility(View.GONE);
                game_detail_guess_reslut.setVisibility(View.VISIBLE);

//                if("1".equals(gameInfo.getOrder_status())){
//                    game_detail_bottom_button.setText("参加红单推荐");
//                }else{
                game_detail_bottom.setVisibility(View.GONE);
//                }
            } else {
                if ("1".equals(gameInfo.getOrder_status())) {
                    game_detail_guess_no.setVisibility(View.GONE);
                    game_detail_guess_reslut.setVisibility(View.VISIBLE);
                    game_detail_bottom_button.setText("参加红单推荐");
                    game_detail_bottom.setVisibility(View.GONE);
                } else {
                    game_detail_guess_no.setVisibility(View.VISIBLE);
                    game_detail_guess_reslut.setVisibility(View.GONE);
                    game_detail_bottom_button.setText(gameInfo.getPrice() + "球币查看预测赛果");
                }
            }

            if ("1".equals(gameInfo.getIs_favorite())) {
                isfav();
            } else {
                isnofav();
            }

            game_detail_winrate.setText("主胜 " + gameInfo.getSpf().get(0));
            game_detail_pinrate.setText("平局 " + gameInfo.getSpf().get(1));
            game_detail_failrate.setText("客胜 " + gameInfo.getSpf().get(2));

            List<String> odds_ou = gameInfo.getOdds_ou();
            List<String> odds_ya = gameInfo.getOdds_yp();
            game_detail_odds_ou.setText("欧    " + odds_ou.get(0) + "  " + odds_ou.get(1) + "  " + odds_ou.get(2));
            game_detail_odds_yp.setText("亚    " + odds_ya.get(0) + "  " + odds_ya.get(1) + "  " + odds_ya.get(2));
            game_detail_ai_match.setText(gameInfo.getAi_match_count());
            game_detail_ai_model.setText(gameInfo.getAi_model_count());
            game_detail_ai_rule.setText(gameInfo.getAi_rule_count());
            game_detail_host_power.setText(gameInfo.getHost_power());
            game_detail_host_name2.setText(gameInfo.getHost_name());
            game_detail_away_power.setText(gameInfo.getAway_power());
            game_detail_away_name2.setText(gameInfo.getAway_name());

            game_detail_winrateg.setText(MyPublic.stringToBFB(gameInfo.getForecast_win()));
            game_detail_ping.setText(MyPublic.stringToBFB(gameInfo.getForecast_draw()));
            game_detail_failg.setText(MyPublic.stringToBFB(gameInfo.getForecast_lose()));

            int g0 = R.color.main_text6;
            int g1 = R.color.main_text6;
            int g2 = R.color.main_text6;
            String forecast = gameInfo.getForecast();
            if (forecast != null) {
                String[] fores = forecast.split(",");
                for (int i = 0; i < fores.length; i++) {
                    if ("3".equals(fores[i])) {
                        g0 = R.color.main_color;
                    } else if ("1".equals(fores[i])) {
                        g1 = R.color.main_color;
                    } else if ("0".equals(fores[i])) {
                        g2 = R.color.main_color;
                    }
                }

            }

            game_detail_winrateg.setTextColor(getResources().getColor(g0));
            game_detail_winrate.setTextColor(getResources().getColor(g0));

            game_detail_ping.setTextColor(getResources().getColor(g1));
            game_detail_pinrate.setTextColor(getResources().getColor(g1));

            game_detail_failg.setTextColor(getResources().getColor(g2));
            game_detail_failrate.setTextColor(getResources().getColor(g2));


        }
    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.game_detail_bottom_button:
                    if (toLogin()) {
                        Intent intentp = new Intent();
                        intentp.putExtra("plan_id", gameInfo.getMatch_id());
                        intentp.putExtra("amount", gameInfo.getPrice());
                        intentp.putExtra("order_type", "1");
                        startIntent(intentp, PayWebActivity.class);
                    }
                    break;
                case R.id.game_detail_more:
                    Intent intents = new Intent();
                    intents.putExtra("match_id", match_id);
                    startIntent(intents, GameSampleActivity.class);
                    break;
            }
        }
    };

    private boolean toLogin() {
        if (MyStatic.userData.access_token == null || "".equals(MyStatic.userData.access_token)) {
            startIntent(LoginActivity.class);
            return false;
        }
        return true;
    }

    private void collectBall() {
        if (gameInfo != null) {
            String url = MyUrl.favoriteList;
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", gameInfo.getMatch_id());
            httpConn.httpGet(url, params, new MyHttpConn.OnCallBack() {
                @Override
                public void Success(JSONObject json) {
                    if (isFav == 0) {
                        isfav();
                    } else {
                        isnofav();
                    }
                }

                @Override
                public void onError(int code, String msg) {

                }
            });
        }
    }
}
