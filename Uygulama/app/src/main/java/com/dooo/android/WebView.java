package com.dooo.android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import androidx.core.content.ContextCompat;

import com.dooo.android.utils.BaseActivity;

public class WebView extends BaseActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        View decor = getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));

        Intent intent = getIntent();
        String url = intent.getExtras().getString("URL");

        android.webkit.WebView webView = findViewById(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        String ua = System.getProperty("http.agent");
        webView.getSettings().setUserAgentString(ua);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}