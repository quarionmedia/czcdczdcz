package com.dooo.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dooo.android.utils.BaseActivity;
import com.dooo.android.utils.HelperUtils;
import com.dooo.android.utils.bKashUtils.Checkout;
import com.dooo.android.utils.bKashUtils.PaymentRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class bKashActivity extends BaseActivity {
    View rootView;
    android.webkit.WebView webView;
    private String request = "";

    int userID;
    String userName, email;

    String name;
    int subscriptionType, amount, time;
    String currencyCode;

    int bKash_status, bKash_payment_type;
    String bKash_app_key, bKash_app_secret, bKash_username, bKash_password;

    int bkash_exchange_rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bkash);
        rootView = findViewById(R.id.activity_bkash);
        loadConfig();
        loadData();

        Intent intent = getIntent();
        int id = intent.getExtras().getInt("id");
        loadSubscriptionDetails(id);


    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        if (sharedPreferences.getString("UserData", null) != null) {
            String userData = sharedPreferences.getString("UserData", null);
            JsonObject jsonObject = new Gson().fromJson(userData, JsonObject.class);

            userID = jsonObject.get("ID").getAsInt();
            userName = jsonObject.get("Name").getAsString();
            email = jsonObject.get("Email").getAsString();
        }
    }

    private void loadConfig() {
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        String config = sharedPreferences.getString("Config", null);

        JsonObject jsonObject = new Gson().fromJson(config, JsonObject.class);

        bKash_status = jsonObject.get("bKash_status").getAsInt();
        bKash_app_key = jsonObject.get("bKash_app_key").getAsString();
        bKash_app_secret = jsonObject.get("bKash_app_secret").getAsString();
        bKash_username = jsonObject.get("bKash_username").getAsString();
        bKash_password = jsonObject.get("bKash_password").getAsString();
        bKash_payment_type = jsonObject.get("bKash_payment_type").getAsInt();
        bkash_exchange_rate = jsonObject.get("bkash_exchange_rate").getAsInt();
    }

    void loadSubscriptionDetails(int id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.GET, AppConfig.url +"getSubscriptionDetails/"+id, response -> {
            if(!response.equals("No Data Avaliable")) {
                JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
                int id1 = jsonObject.get("id").getAsInt();
                name = jsonObject.get("name").getAsString();
                time = jsonObject.get("time").getAsInt();
                amount = jsonObject.get("amount").getAsInt();

                if(bkash_exchange_rate>0) {
                    amount = bkash_exchange_rate*amount;
                }

                int currency = 90; //jsonObject.get("currency").getAsInt();
                String background = jsonObject.get("background").getAsString();
                subscriptionType = jsonObject.get("subscription_type").getAsInt();
                int status = jsonObject.get("status").getAsInt();

                currencyCode = HelperUtils.getCurrencyName(currency);

                if(!Objects.equals(bKash_app_key, "") && !Objects.equals(bKash_app_secret, "") &&
                        !Objects.equals(bKash_username, "") && !Objects.equals(bKash_password, "")) {

                    Checkout checkout = new Checkout();
                    checkout.setAmount(String.valueOf(amount));
                    checkout.setVersion("two");
                    checkout.setIntent("sale");

                    PaymentRequest paymentRequest = new PaymentRequest();
                    paymentRequest.setAmount(checkout.getAmount());
                    paymentRequest.setIntent(checkout.getIntent());

                    Gson gson = new Gson();
                    request = gson.toJson(paymentRequest);

                    webView = findViewById(R.id.webView);

                    WebSettings webSettings = webView.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webView.setClickable(true);
                    webView.getSettings().setDomStorageEnabled(true);
                    webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                    webView.clearCache(true);
                    webView.getSettings().setAllowFileAccessFromFileURLs(true);
                    webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

                    webView.addJavascriptInterface(new JavaScriptInterface(this), "AndroidNative");

                    webView.loadUrl("https://cloud.team-dooo.com/Dooo/bKash/payment_"+getbKashPaymentType(bKash_payment_type)+".php");

                    webView.setWebViewClient(new CheckoutWebViewClient());
                } else {
                    finish();
                }
            }
        }, error -> {
            // Do nothing
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr);
    }

    public String getbKashPaymentType(int bKashPaymentType) {
        if(bKashPaymentType==0) {
            return "sandbox";
        } else {
            return "live";
        }
    }

    public class JavaScriptInterface {
        Context mContext;

        public JavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void onPaymentSuccess(String bKashpaymentID, String bKashtransactionID, String bKashamount) {
            Log.d("test", bKashpaymentID+" : "+bKashtransactionID+" : "+bKashamount);
            webView.setVisibility(View.GONE);
            RequestQueue queue = Volley.newRequestQueue(bKashActivity.this);
            StringRequest sr = new StringRequest(Request.Method.POST, AppConfig.url + "dXBncmFkZQ", response -> {
                if(response.equals("Account Upgraded Succefully")) {

                    Snackbar snackbar = Snackbar.make(rootView, "Account Upgraded Succefully!", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(() -> {
                        finishAffinity();
                        startActivity(new Intent(bKashActivity.this, Splash.class));
                    }, 2000);
                } else {
                    finish();
                }
            }, error -> {
                finish();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("User_ID", String.valueOf(userID));
                    params.put("name", name);
                    params.put("subscription_type", String.valueOf(subscriptionType));
                    params.put("time", String.valueOf(time));
                    params.put("amount", String.valueOf(amount));
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }
            };
            queue.add(sr);
        }

    }

    private class CheckoutWebViewClient extends WebViewClient {
//        @Override
//        public void onReceivedSslError(android.webkit.WebView view, SslErrorHandler handler, SslError error) {
//            handler.proceed();
//        }

        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, WebResourceRequest request) {
            if (request.getUrl().toString().equals("https://www.bkash.com/terms-and-conditions")) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(request.getUrl().toString()));
                startActivity(myIntent);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
            webView.setVisibility(View.GONE);
        }

        @Override
        public void onPageFinished(android.webkit.WebView view, String url) {
            String paymentCredentials = "?app_key="+bKash_app_key+"&app_secret="+bKash_app_secret+"&username="+bKash_username+"&password="+bKash_password;
            webView.loadUrl("javascript:callReconfigure2('"+paymentCredentials+"',  '"+bKash_payment_type+"')");

            String paymentRequest = "{paymentRequest:" + request + "}";
            webView.loadUrl("javascript:callReconfigure(" + paymentRequest + " )");

            webView.loadUrl("javascript:clickPayButton()");
            webView.setVisibility(View.VISIBLE);

        }

    }

}