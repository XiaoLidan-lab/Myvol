package com.example.myvol.ui.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myvol.R;
import com.example.myvol.bean.NewsBean;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        NewsBean newsBean = (NewsBean) intent.getSerializableExtra("data");
        setContentView(R.layout.activity_news_detail);

        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setDomStorageEnabled(true);

        // 启用WebView的JavaScript执行功能
        webView.getSettings().setJavaScriptEnabled(true);
        // 允许WebView加载图片资源
        webView.getSettings().setBlockNetworkImage(false);

        webView.setWebViewClient(new WebViewClient() {
            // 在页面加载过程中，需要处理加载错误的情况
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 在出现加载错误时，可以进行相应的处理操作
            }

            // 在页面开始加载时，可以做一些加载前的准备工作
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // 显示加载进度条或其他提示信息
            }

            // 在页面加载完成后，可以进行一些处理操作
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 隐藏加载进度条或其他提示信息
            }
        });
        String url =newsBean.getContent();
        webView.loadUrl(url);
    }
}