package com.xingnext.bigdata;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.lipo.views.NoScrollGirdView;
import com.lipo.views.ToastView;
import com.xingnext.bigdata.adapter.BuyMoneyAdapter;
import com.xingnext.bigdata.beans.MoneyInfo;
import com.xingnext.bigdata.factory.PayHelper;
import com.xingnext.bigdata.factory.TitleHelper;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyShaobingActivity extends BaseActivity {

    private TextView buy_shaobing_bottom;
    private TextView buy_shaobing_total;
    private NoScrollGirdView buy_shaobing_grid;

    private TitleHelper titleHelper;
    private PayHelper payHelper;

    private List<MoneyInfo> moneyInfos;
    private BuyMoneyAdapter adapter;
    private int position_choiced;

    private MyHttpConn httpConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_shaobing);

        moneyInfos = new ArrayList<MoneyInfo>();
        httpConn = new MyHttpConn(mContext);

        initView();
        getData();
    }

    private void initView(){
        buy_shaobing_bottom = (TextView) findViewById(R.id.buy_shaobing_bottom);
        buy_shaobing_total = (TextView) findViewById(R.id.buy_shaobing_total);
        buy_shaobing_grid = (NoScrollGirdView) findViewById(R.id.buy_shaobing_grid);

        titleHelper = new TitleHelper(mContext,R.id.buy_shaobing_top,"购买球币");
        payHelper = new PayHelper(mContext,R.id.buy_shaobing_pay);

        position_choiced = -1;

        buy_shaobing_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=position_choiced){
                    MoneyInfo info = moneyInfos.get(i);
                    info.isChoiced = true;
                    if(position_choiced>=0){
                        moneyInfos.get(position_choiced).isChoiced = false;
                    }
                    initAdapter();
                    position_choiced = i;

                    buy_shaobing_total.setText("购买数量："+info.price);
                    buy_shaobing_bottom.setText("立即支付  "+info.price+"元");
                }
            }
        });

        buy_shaobing_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });

    }

    private void initAdapter(){
        if(adapter == null){
            adapter = new BuyMoneyAdapter(mContext,moneyInfos);
            buy_shaobing_grid.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    private void getData(){
        String url = MyUrl.chargeUrl;
        Map<String, String> params = new HashMap<String, String>();
        httpConn.httpGet(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                JSONArray data = json.optJSONArray("data");
                int lent = 0;
                if(data!=null){
                    lent = data.length();
                    for (int i = 0; i < lent; i++) {
                        JSONObject jsonObj = data.optJSONObject(i);
                        moneyInfos.add(MoneyInfo.fromJson(jsonObj));
                    }
                }

                initAdapter();
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    private void submitData(){
        if(position_choiced == -1){
            ToastView.setToasd(mContext,"请选择支付金额");
            return;
        }
        String url = MyUrl.orderPay;
        Map<String, String> params = new HashMap<String, String>();
        params.put("goods_id",moneyInfos.get(position_choiced).id);
        params.put("order_type","9");
        params.put("payment","alipay");
        httpConn.httpPost(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                JSONObject data = json.optJSONObject("data");
                payHelper.toAlipay(data.optString("alipay"));
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

}
