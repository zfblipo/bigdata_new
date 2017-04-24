package com.xingnext.bigdata;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lipo.views.MyProgreeDialog;
import com.xingnext.bigdata.factory.TitleHelper;
import com.xingnext.bigdata.utils.MyUrl;

public class AboutWebActivity extends BaseActivity {

    private TitleHelper titleHelper;
    private WebView about_web_view;
    private MyProgreeDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_web);

        myDialog = new MyProgreeDialog(this);

        initView();

    }

    private void initView() {
        titleHelper = new TitleHelper(mContext, R.id.about_web_top, "帮助中心");

        about_web_view = (WebView) findViewById(R.id.about_web_view);

        initWeb();
        myDialog.show();

        String url = MyUrl.aboutWebUrl;
        about_web_view.loadUrl(url);

//        String url = "https://api.shaobing.jikegouwu.com/order/check?token=" + MyStatic.userData.access_token;
//        String postDate = "plan_id=" + plan_id + "&amount=" + amount + "&order_type=" + order_type;
//        about_web_view.postUrl(url, postDate.getBytes());

        about_web_view.setWebViewClient(new WebViewClient() {
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
//        about_web_view.addJavascriptInterface(new JsInteration(), "xingnext");
    }

    private void initWeb() {
        WebSettings webSettings = about_web_view.getSettings();
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
        public void orderPage() {

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
