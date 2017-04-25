package com.xingnext.bigdata.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lipo.views.RoundedImageView;
import com.xingnext.bigdata.AboutWebActivity;
import com.xingnext.bigdata.BaseApplication;
import com.xingnext.bigdata.CouponWebActivity;
import com.xingnext.bigdata.GifWebActivity;
import com.xingnext.bigdata.LoginActivity;
import com.xingnext.bigdata.MyAccountActivity;
import com.xingnext.bigdata.MyreadRecordActivity;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.RechareWebActivity;
import com.xingnext.bigdata.beans.UserInfo;
import com.xingnext.bigdata.factory.ItemNextHelper;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyImageLoader;
import com.xingnext.bigdata.utils.MyStatic;
import com.xingnext.bigdata.utils.MyUrl;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lipo on 2017/3/13.
 */
public class MainFragment4 extends BaseFragment {

    private View mainView, maind_status, maind_login_head;

    private RoundedImageView maind_head;
    private TextView maind_name, maind_nicket, maind_buy, maind_money;
    private View maind_have, maind_account, maind_gif, maind_coupon,
            maind_phone, maind_help, maind_quit;
    private ItemNextHelper haveHelper, accountHelper, gifHelper, couponHelper,
            phoneHelper, helpHelper;//vipHelper,

    //    private View maind_vip;
    private boolean isLogin = false;

    private MyHttpConn httpConn;
    private Gson gson;
    private UserInfo userInfo;

    private String headUrl = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mainView = inflater.inflate(R.layout.main_fragment4, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        httpConn = new MyHttpConn(mContext, false);
        gson = new Gson();

        initView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (!MyStatic.userData.access_token.isEmpty()) {
                getData();
            }
        }
    }

    private void initView() {
        maind_login_head = mainView.findViewById(R.id.maind_login_head);
        maind_head = (RoundedImageView) mainView.findViewById(R.id.maind_head);
        maind_name = (TextView) mainView.findViewById(R.id.maind_name);
        maind_nicket = (TextView) mainView.findViewById(R.id.maind_nicket);
        maind_buy = (TextView) mainView.findViewById(R.id.maind_buy);
        maind_money = (TextView) mainView.findViewById(R.id.maind_money);

//        maind_vip = mainView.findViewById(R.id.maind_vip);
        maind_have = mainView.findViewById(R.id.maind_have);
        maind_account = mainView.findViewById(R.id.maind_account);
        maind_coupon = mainView.findViewById(R.id.maind_coupon);
        maind_phone = mainView.findViewById(R.id.maind_phone);
        maind_help = mainView.findViewById(R.id.maind_help);
        maind_quit = mainView.findViewById(R.id.maind_quit);
        maind_gif = mainView.findViewById(R.id.maind_gif);

//        vipHelper = new ItemNextHelper(mContext, R.id.maind_vip);
        haveHelper = new ItemNextHelper(mContext, R.id.maind_have);
        accountHelper = new ItemNextHelper(mContext, R.id.maind_account);
        couponHelper = new ItemNextHelper(mContext, R.id.maind_coupon);
        phoneHelper = new ItemNextHelper(mContext, R.id.maind_phone);
        helpHelper = new ItemNextHelper(mContext, R.id.maind_help);
        gifHelper = new ItemNextHelper(mContext, R.id.maind_gif);


        maind_login_head.setOnClickListener(onclick);
        maind_buy.setOnClickListener(onclick);
//        maind_vip.setOnClickListener(onclick);
        maind_have.setOnClickListener(onclick);
        maind_account.setOnClickListener(onclick);
        maind_coupon.setOnClickListener(onclick);
        maind_phone.setOnClickListener(onclick);
        maind_help.setOnClickListener(onclick);
        maind_quit.setOnClickListener(onclick);
        maind_gif.setOnClickListener(onclick);

//        vipHelper.fillData(R.mipmap.my_rss_icon, "聚米VIP会员", "超多特权尊享");
        haveHelper.fillData(R.mipmap.ic_user_center_my_order, "我的订阅");
        accountHelper.fillData(R.mipmap.user_money_icon, "账户明细");
        couponHelper.fillData(R.mipmap.ticket_icon, "我的优惠券");
        helpHelper.fillData(R.mipmap.phone_icon, "帮助中心");
        phoneHelper.fillData(R.mipmap.band_id_icon, "绑定手机", "绑定有奖");
        gifHelper.fillData(R.mipmap.account_icon, "幸运抽奖");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            maind_status = mainView.findViewById(R.id.maind_status);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 55);
            maind_status.setLayoutParams(params);
        }

        if (!MyStatic.userData.access_token.isEmpty()) {
            maind_money.setVisibility(View.VISIBLE);
        }

    }

    private OnClickListener onclick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.maind_buy:
                    if (toLogin()) {
                        startIntent(RechareWebActivity.class);
                    }
//                    startIntent(BuyShaobingActivity.class);
                    break;
//                case R.id.maind_vip:
//
//                    break;
                case R.id.maind_have:
                    if (toLogin()) {
                        startIntent(MyreadRecordActivity.class);
                    }
                    break;
                case R.id.maind_account:
                    if (toLogin()) {
                        startIntent(MyAccountActivity.class);
                    }
                    break;
                case R.id.maind_gif:
                    if (toLogin()) {
                        startIntent(GifWebActivity.class);
                    }
                    break;
                case R.id.maind_coupon:
                    if (toLogin()) {
                        startIntent(CouponWebActivity.class);
                    }
                    break;
                case R.id.maind_phone:

                    break;
                case R.id.maind_help:
                    startIntent(AboutWebActivity.class);
                    break;
                case R.id.maind_quit:
                    BaseApplication.quitPreferences();
                    Intent intentL = new Intent();
                    intentL.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getActivity().finish();
                    startIntent(intentL, LoginActivity.class);
                    break;
                case R.id.maind_login_head:
                    if (toLogin()) {
                    }

                    break;
            }
        }
    };

    private void getData() {
        String url = MyUrl.userUrl;
        Map<String, String> params = new HashMap<String, String>();
        httpConn.httpPost(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                String data = json.optString("data");
                userInfo = gson.fromJson(data, UserInfo.class);
                fillData();
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    private void fillData() {
        if (!headUrl.equals(userInfo.getPic())) {
            MyImageLoader.loaderHead(mContext, maind_head, userInfo.getPic());
            headUrl = userInfo.getPic();
        }

        maind_name.setText(userInfo.getNickname());
        maind_nicket.setText(userInfo.getSignature());
        maind_money.setText(userInfo.getMoney());
    }

    private boolean toLogin() {
        if (MyStatic.userData.access_token == null || "".equals(MyStatic.userData.access_token)) {
            startIntent(LoginActivity.class);
            return false;
        }

        return true;
    }

}
