package com.xingnext.bigdata;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.lipo.views.MyProgreeDialog;
import com.lipo.views.ToastView;
import com.xingnext.bigdata.beans.PayInfo;
import com.xingnext.bigdata.factory.PayBaseHelper;
import com.xingnext.bigdata.factory.TitleHelper;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyLog;
import com.xingnext.bigdata.utils.MyStatic;
import com.xingnext.bigdata.utils.MyUrl;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RechareWebActivity extends BaseActivity {

    private WebView rechare_web_view;
    private TitleHelper titleHelper;
    private MyProgreeDialog myDialog;

    private MyHttpConn httpConn;
    private Gson gson;
    private PayBaseHelper payBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechare_web);

        httpConn = new MyHttpConn(mContext,false);
        gson = new Gson();
        payBaseHelper = new PayBaseHelper(mContext);

        initView();
    }

    private void initView() {
        titleHelper = new TitleHelper(mContext,R.id.rechare_web_top,"购买球币");
        rechare_web_view = (WebView) findViewById(R.id.rechare_web_view);

        initWeb();
        myDialog = new MyProgreeDialog(mContext);
        rechare_web_view.loadUrl(MyUrl.chargeWebUrl+"?token="+ MyStatic.userData.access_token);
        rechare_web_view.setWebViewClient(new WebViewClient() {
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
        rechare_web_view.addJavascriptInterface(new JsInteration(), "xingnext");
    }

    private void initWeb() {
        WebSettings webSettings = rechare_web_view.getSettings();
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
            MyLog.i(data);
            submitData(gson.fromJson(data,PayInfo.class));
        }
    }

    private void submitData(final PayInfo info) {
        String url = MyUrl.orderheckPay;
        Map<String, String> params = new HashMap<String, String>();
        params.put("order_sn", info.getOrder_sn());
        params.put("goods_id",info.getGoods_id());
        params.put("amount",info.getAmount());
        params.put("order_type",info.getOrder_type());
        params.put("coupon_sn",info.getCoupon_sn());
        params.put("payment",info.getPayment());

        httpConn.httpPost(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                final JSONObject data = json.optJSONObject("data");
                if("balance".equals(info.getPayment())){
                    submitDataTwo(data.optString("buyer_id"),data.optString("order_sn"));
                }else if("alipay".equals(info.getPayment())){
                    JSONObject payment_request = data.optJSONObject("payment_request");
                    payBaseHelper.toAlipay(payment_request.optString("alipay"));
                    payBaseHelper.setOnPaySuccessListener(new PayBaseHelper.OnPaySuccessListener() {
                        @Override
                        public void paySuccess() {
                            submitDataTwo(data.optString("buyer_id"),data.optString("order_sn"));
                        }
                    });
                }
            }

            @Override
            public void onError(int code, String msg) {
                ToastView.setToasd(mContext,"支付失败");
            }
        });
    }

    private void submitDataTwo(String buyer_id,String order_sn) {
        String url = MyUrl.orderPay;
        Map<String, String> params = new HashMap<String, String>();
        params.put("order_sn", order_sn);
        params.put("user_id",buyer_id);
        MyLog.i("order_sn:"+order_sn);
        MyLog.i("user_id:"+buyer_id);
        httpConn.httpPost(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                finish();
                startIntent(PaySuccessActivity.class);
            }

            @Override
            public void onError(int code, String msg) {
                ToastView.setToasd(mContext,"支付失败");
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
