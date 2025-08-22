package com.dooo.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dooo.android.utils.BaseActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.stripe.android.PaymentConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CoingatePaymentGatewayActivity extends BaseActivity {
    View rootView;
    int userID;
    String userName, email;
    String name;
    int subscriptionType, amount, time;
    android.webkit.WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coingate_payment_gateway);
        rootView = findViewById(R.id.CoingatePaymentGatewayActivity);

        Intent intent = getIntent();
        int subscriptionID = Objects.requireNonNull(intent.getExtras()).getInt("id");

        loadData(subscriptionID);
        loadSubscriptionDetails(subscriptionID);
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
                subscriptionType = jsonObject.get("subscription_type").getAsInt();

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

    private void loadData(int subscriptionID) {
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        if (sharedPreferences.getString("UserData", null) != null) {
            String userData = sharedPreferences.getString("UserData", null);
            JsonObject jsonObject = new Gson().fromJson(userData, JsonObject.class);

            userID = jsonObject.get("ID").getAsInt();
            userName = jsonObject.get("Name").getAsString();
            email = jsonObject.get("Email").getAsString();

            fetchPaymentIntent(userID, subscriptionID);
        }
    }

    private void fetchPaymentIntent(int userID, int subscriptionID) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, AppConfig.url +"getCoingatePaymentIntent", response -> {
            JsonObject jsonArray = new Gson().fromJson(response, JsonObject.class);
            String error = jsonArray.has("error") ? jsonArray.get("error").getAsString() : null;

            if (error != null && !error.isEmpty()) {
                Toast.makeText(CoingatePaymentGatewayActivity.this, error, Toast.LENGTH_LONG).show();
                finish();
            } else {
                String payment_url = jsonArray.get("payment_url").getAsString();

                if (payment_url != null) {
                    presentPaymentView(payment_url);
                }
            }

        }, error -> {
            Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
            finish();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("subscriptionID", Integer.toString(subscriptionID));
                params.put("customerID", Integer.toString(userID));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr);

    }

    private void presentPaymentView(String payment_url) {
        webView = findViewById(R.id.webView);

        webView.setVisibility(View.VISIBLE);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setClickable(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.clearCache(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        webView.loadUrl(payment_url);

        webView.setWebViewClient(new CoingatePaymentGatewayActivity.CheckoutWebViewClient());
    }


    private class CheckoutWebViewClient extends WebViewClient {
//        @Override
//        public void onReceivedSslError(android.webkit.WebView view, SslErrorHandler handler, SslError error) {
//            handler.proceed();
//        }

        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, WebResourceRequest request) {
            Uri uri = request.getUrl();
            String scheme = uri.getScheme();

            if (scheme != null && !scheme.equals("http") && !scheme.equals("https")) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    return true;
                } catch (Exception e) {
                    return true;
                }
            } else {
                if(uri.toString().equals("https://status/test")) {
                    webView.setVisibility(View.GONE);
                    Toast.makeText(CoingatePaymentGatewayActivity.this, "Payment Test", Toast.LENGTH_SHORT).show();
                    finish();
                } else if(uri.toString().equals("https://status/success")) {
                    webView.setVisibility(View.GONE);
                    RequestQueue queue = Volley.newRequestQueue(CoingatePaymentGatewayActivity.this);
                    StringRequest sr = new StringRequest(Request.Method.POST, AppConfig.url + "dXBncmFkZQ", response -> {
                        if(response.equals("Account Upgraded Succefully")) {

                            Snackbar snackbar = Snackbar.make(rootView, "Account Upgraded Succefully!", Snackbar.LENGTH_SHORT);
                            snackbar.show();

                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(() -> {
                                finishAffinity();
                                startActivity(new Intent(CoingatePaymentGatewayActivity.this, Splash.class));
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
                } else if(uri.toString().equals("https://status/cancel")) {
                    webView.setVisibility(View.GONE);
                    Toast.makeText(CoingatePaymentGatewayActivity.this, "Payment Canceled", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
            //webView.setVisibility(View.GONE);
        }

        @Override
        public void onPageFinished(android.webkit.WebView view, String url) {
//            webView.setVisibility(View.VISIBLE);

        }

    }


}