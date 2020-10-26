package com.andjdk.library_base.base;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.andjdk.library_base.R;
import com.andjdk.library_base.constants.Constants;
import com.andjdk.library_base.widget.TitleBar;


public class BaseWebActivity extends BaseActivity {

    private static final String TAG = "BaseWebActivity";

    ProgressBar progressBar;
    WebView mWebView;


    protected String title;
    protected String url;

    private static final boolean enableProgressBar = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.base_web_activity;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView(Bundle savedInstanceState) {
        onClickToolBarBack(findViewById(R.id.title_bar));

        mWebView = findViewById(R.id.base_web_webview);
        progressBar = findViewById(R.id.base_web_progress_bar);

        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setLoadsImagesAutomatically(true);

        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient);





    }

    @Override
    protected void initData() {
        super.initData();

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            url = bundle.getString(Constants.WEB_URL);

            mWebView.loadUrl(url);
        }



    }

    WebViewClient webViewClient = new WebViewClient() {

        /**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    };


    WebChromeClient webChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView webView, int newProgress) {
            super.onProgressChanged(webView, newProgress);

            if (enableProgressBar) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }
            }

        }
    };
}
