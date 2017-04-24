package com.xingnext.bigdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xingnext.bigdata.beans.OrderDetailInfo;
import com.xingnext.bigdata.factory.TitleHelper;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OrderDetailActivity extends BaseActivity {

    private String orderId;
    private Intent intent;

    private TitleHelper titleHelper;
    private TextView order_detail_number,order_detail_price,order_detail_price_quan,order_detail_price_to,order_detail_sn
            ,order_detail_state,order_detail_time;

    private MyHttpConn httpConn;
    private Gson gson;

    private OrderDetailInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        httpConn = new MyHttpConn(mContext);
        gson = new Gson();

        intent = getIntent();
        orderId = intent.getStringExtra("order_id");

        initView();
        getData();

    }
    private void initView(){
        titleHelper = new TitleHelper(mContext,R.id.order_detail_top,"购买详情");

        order_detail_number = (TextView) findViewById(R.id.order_detail_number);
        order_detail_price = (TextView) findViewById(R.id.order_detail_price);
        order_detail_price_quan = (TextView) findViewById(R.id.order_detail_price_quan);
        order_detail_price_to = (TextView) findViewById(R.id.order_detail_price_to);
        order_detail_sn = (TextView) findViewById(R.id.order_detail_sn);
        order_detail_state = (TextView) findViewById(R.id.order_detail_state);
        order_detail_time = (TextView) findViewById(R.id.order_detail_time);
    }

    private void getData() {
        String url = MyUrl.orderDetailUrl + orderId;
        Map<String, String> params = new HashMap<String, String>();
        httpConn.httpGet(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                String data = json.optString("data");
                info = gson.fromJson(data,OrderDetailInfo.class);
                fillData();
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    private void fillData(){
        order_detail_number.setText(info.getOrder_count()+"场");
        order_detail_price.setText(info.getAmount()+"元");
        order_detail_price_quan.setText("0元");
        order_detail_price_to.setText(info.getAmount()+"元");
        order_detail_price.setText(info.getOrder_sn());
        order_detail_state.setText(info.getStatus_desc());
        order_detail_time.setText(info.getCreate_time());
    }


}
