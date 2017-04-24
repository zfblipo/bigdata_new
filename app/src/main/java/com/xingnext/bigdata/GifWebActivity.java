package com.xingnext.bigdata;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lipo.views.MyProgreeDialog;
import com.xingnext.bigdata.factory.TitleHelper;
import com.xingnext.bigdata.utils.MyStatic;
import com.xingnext.bigdata.utils.MyUrl;

public class GifWebActivity extends BaseActivity {

    private WebView gif_web_view;
    private TitleHelper titleHelper;
    private MyProgreeDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_web);
        initView();
    }

    private void initView() {
        titleHelper = new TitleHelper(mContext,R.id.gif_web_top,"幸运抽奖");
        gif_web_view = (WebView) findViewById(R.id.gif_web_view);

        initWeb();
        myDialog = new MyProgreeDialog(mContext);
        gif_web_view.loadUrl(MyUrl.gifWebUrl+"?token="+ MyStatic.userData.access_token);
        gif_web_view.setWebViewClient(new WebViewClient() {
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
//        gif_web_view.addJavascriptInterface(new JsInteration(), "xingnext");
    }

    private void initWeb() {
        WebSettings webSettings = gif_web_view.getSettings();
        webSettings.setJavaScriptEnabled(true); // 支持js
        webSettings.setUseWideViewPort(false); // 将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setLoadsImagesAutomatically(true); // 支持自动加载图片
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setBuiltInZoomControls(true); // 设置支持缩放
        webSettings.setDisplayZoomControls(false);
    }

    public class JsInteration {
//        @JavascriptInterface
//        public void useCoupon() {
//            Intent intentT = new Intent();
//            intentT.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startIntent(intentT,MainActivity.class);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (myDialog != null && myDialog.isShowing()) {
            myDialog.dismiss();
        }
    }

}
