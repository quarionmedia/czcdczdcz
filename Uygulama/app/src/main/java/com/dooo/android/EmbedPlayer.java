package com.dooo.android;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.core.content.ContextCompat;

import com.dooo.android.sharedpreferencesmanager.ConfigManager;
import com.dooo.android.utils.BaseActivity;
import com.dooo.android.utils.HelperUtils;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class EmbedPlayer extends BaseActivity {
    private HelperUtils helperUtils;

    StringBuilder adservers;

    String embed_error_code = "";

    android.webkit.WebView webView;
    String streamUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(AppConfig.FLAG_SECURE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));

        setContentView(R.layout.activity_embed_player);

        Intent intent = getIntent();
        streamUrl = intent.getExtras().getString("url");

        try {
            JSONObject configObject = ConfigManager.loadConfig(this);
            embed_error_code = StringEscapeUtils.unescapeHtml4(configObject.getString("embed_error_code"));
        } catch (Exception e) {}

        adservers();

        webView = findViewById(R.id.webview);
        //webView.setWebChromeClient(new WebChromeClient());
        //webView.setWebViewClient(new WebViewClient());
        webView.setWebViewClient(new MyWebViewClient());
        String ua = System.getProperty("http.agent");
        webView.getSettings().setUserAgentString(ua);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl(streamUrl);
    }

    public class MyWebViewClient extends WebViewClient {

        private Map<String, Boolean> loadedUrls = new HashMap<>();

        @Override
        public WebResourceResponse shouldInterceptRequest(android.webkit.WebView view, WebResourceRequest request) {
            ByteArrayInputStream EMPTY = new ByteArrayInputStream("".getBytes());
            String kk5 = String.valueOf(adservers);

            if(request.getUrl().getHost() != null) {
                if (kk5.contains(":::::" + request.getUrl().getHost())) {
                    return new WebResourceResponse("text/plain", "utf-8", EMPTY);
                }
            }
            return super.shouldInterceptRequest(view, request);
        }

         @Override
         public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
             return false;
         }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.d("test", request.getUrl()+"::"+error.getDescription());

            if(request.getUrl().toString().equals(streamUrl)) {
                if(!embed_error_code.equals("")) {
                    view.loadDataWithBaseURL(null, embed_error_code, "text/html", "utf-8", null);
                }
            }
            super.onReceivedError(view,request,error);
        }
    }

    private void adservers(){
        String strLine2="";
        adservers = new StringBuilder();

        InputStream fis2 = this.getResources().openRawResource(R.raw.adblockserverlist);
        BufferedReader br2 = new BufferedReader(new InputStreamReader(fis2));
        if(fis2 != null) {
            try {
                while ((strLine2 = br2.readLine()) != null) {
                    adservers.append(strLine2);
                    adservers.append("\n");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}