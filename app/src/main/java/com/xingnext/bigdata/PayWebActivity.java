package com.xingnext.bigdata;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.lipo.views.MyProgreeDialog;
import com.xingnext.bigdata.beans.PayInfo;
import com.xingnext.bigdata.factory.PayBaseHelper;
import com.xingnext.bigdata.factory.TitleHelper;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyStatic;
import com.xingnext.bigdata.utils.MyUrl;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PayWebActivity extends BaseActivity {

    private TitleHelper titleHelper;
    private WebView pay_web_view;
    private Intent intent;
    private String plan_id;
    private String amount;
    private String order_type;//订单类型，1智能预测，2红单推荐
    private MyProgreeDialog myDialog;

    private MyHttpConn httpConn;
    private Gson gson;
    private PayBaseHelper payBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_web);

        intent = getIntent();
        plan_id = intent.getStringExtra("plan_id");
        amount = intent.getStringExtra("amount");
        order_type = intent.getStringExtra("order_type");
        myDialog = new MyProgreeDialog(this);

        httpConn = new MyHttpConn(mContext,false);
        gson = new Gson();
        payBaseHelper = new PayBaseHelper(mContext);

        initView();
    }

    private void initView() {
        titleHelper = new TitleHelper(mContext, R.id.pay_web_top, "立即支付");

        pay_web_view = (WebView) findViewById(R.id.pay_web_view);

        initWeb();
        myDialog.show();

        String url = MyUrl.checkWebUrl+"?token=" + MyStatic.userData.access_token
                +"&goods_id=" + plan_id + "&amount=" + amount + "&order_type=" + order_type;
        pay_web_view.loadUrl(url);

//        String url = "https://api.shaobing.jikegouwu.com/order/check?token=" + MyStatic.userData.access_token;
//        String postDate = "plan_id=" + plan_id + "&amount=" + amount + "&order_type=" + order_type;
//        pay_web_view.postUrl(url, postDate.getBytes());

        pay_web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (myDialog != null && myDialog.isShowing()) {
                    myDialog.dismiss();
                }
            }

        });
        pay_web_view.addJavascriptInterface(new JsInteration(), "xingnext");
    }

    private void initWeb() {
        WebSettings webSettings = pay_web_view.getSettings();
        webSettings.setJavaScriptEnabled(true); // 支持js
        webSettings.setUseWideViewPort(false); // 将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setLoadsImagesAutomatically(true); // 支持自动加载图片
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setBuiltInZoomControls(true); // 设置支持缩放
        webSettings.setDisplayZoomControls(false);
    }

    public class JsInteration {

        @JavascriptInterface
        public void orderPay(String data) {
            submitData(gson.fromJson(data,PayInfo.class));
        }
    }

    private void submitData(PayInfo info){
        String url = MyUrl.orderPay;
        Map<String, String> params = new HashMap<String, String>();
        params.put("goods_id",info.getGoods_id());
        params.put("order_sn",info.getOrder_sn());
        params.put("amount",info.getAmount());
        params.put("order_type",info.getOrder_type());
        params.put("coupon_sn",info.getCoupon_sn());
        params.put("payment",info.getPayment());
        httpConn.httpPost(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                JSONObject data = json.optJSONObject("data");
                JSONObject payment_request = data.optJSONObject("payment_request");
                payBaseHelper.toAlipay(payment_request.optString("alipay"));
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (myDialog != null && myDialog.isShowing()) {
            myDialog.dismiss();
        }
    }

}
