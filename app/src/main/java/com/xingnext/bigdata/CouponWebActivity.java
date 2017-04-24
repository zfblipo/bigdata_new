package com.xingnext.bigdata;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lipo.views.MyProgreeDialog;
import com.xingnext.bigdata.factory.TitleHelper;
import com.xingnext.bigdata.utils.MyStatic;
import com.xingnext.bigdata.utils.MyUrl;

public class CouponWebActivity extends BaseActivity {

    private WebView coupon_web;
    private TitleHelper titleHelper;
    private MyProgreeDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        initView();
    }

    private void initView() {
        titleHelper = new TitleHelper(mContext,R.id.coupon_top,"优惠券");
        coupon_web = (WebView) findViewById(R.id.coupon_web);

        initWeb();
        myDialog = new MyProgreeDialog(this);
        coupon_web.loadUrl(MyUrl.couponWebUrl+"?token="+ MyStatic.userData.access_token+"&status=1");
        coupon_web.setWebViewClient(new WebViewClient() {
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
        coupon_web.addJavascriptInterface(new JsInteration(), "xingnext");
    }

    private void initWeb() {
        WebSettings webSettings = coupon_web.getSettings();
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
        public void useCoupon() {
            Intent intentT = new Intent();
            intentT.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startIntent(intentT,MainActivity.class);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (myDialog != null && myDialog.isShowing()) {
            myDialog.dismiss();
        }
    }


}
