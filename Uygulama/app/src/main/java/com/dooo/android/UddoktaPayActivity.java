package com.dooo.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dooo.android.utils.BaseActivity;
import com.dooo.android.utils.HelperUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.help5g.uddoktapaysdk.UddoktaPay;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UddoktaPayActivity extends BaseActivity {
    View rootView;
    int userID;
    String userName, email;
    String name;
    int subscriptionType, amount, time;
    String currencyCode;

    int uddoktapay_status;
    String uddoktapay_api_key, uddoktapay_checkout_url, uddoktapay_verify_payment_url, uddoktapay_base_url, REDIRECT_URL;

    int uddoktapay_exchange_rate;

    private String storedFullName;
    private String storedEmail;
    private String storedAmount;
    private String storedInvoiceId;
    private String storedPaymentMethod;
    private String storedSenderNumber;
    private String storedTransactionId;
    private String storedDate;
    private String storedFee;
    private String storedChargedAmount;

    private String storedMetaKey1;
    private String storedMetaValue1;

    private String storedMetaKey2;
    private String storedMetaValue2;

    private String storedMetaKey3;
    private String storedMetaValue3;
    private static final String CANCEL_URL = "#";

    android.webkit.WebView uddoktaPayWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_uddokta_pay);
        rootView = findViewById(R.id.activity_uddokta_pay);
        uddoktaPayWebView = findViewById(R.id.uddoktaPayWebView);

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

        uddoktapay_status = jsonObject.get("uddoktapay_status").getAsInt();
        uddoktapay_api_key = jsonObject.get("uddoktapay_api_key").getAsString();
        uddoktapay_exchange_rate = jsonObject.get("uddoktapay_exchange_rate").getAsInt();

        if(jsonObject.get("uddoktapay_base_url").getAsString() != null &&
                jsonObject.get("uddoktapay_base_url").getAsString().substring(jsonObject.get("uddoktapay_base_url").getAsString().length() - 1).equals("/")) {
            uddoktapay_base_url = jsonObject.get("uddoktapay_base_url").getAsString().substring(0, jsonObject.get("uddoktapay_base_url").getAsString().length() - 1);
        } else {
            uddoktapay_base_url = jsonObject.get("uddoktapay_base_url").getAsString();
        }
        REDIRECT_URL = uddoktapay_base_url+"/api";
        uddoktapay_checkout_url = uddoktapay_base_url+"/api/checkout-v2";
        uddoktapay_verify_payment_url = uddoktapay_base_url+"/api/verify-payment";
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

                if(uddoktapay_exchange_rate>0) {
                    amount = uddoktapay_exchange_rate*amount;
                }

                int currency = 90; //jsonObject.get("currency").getAsInt();
                String background = jsonObject.get("background").getAsString();
                subscriptionType = jsonObject.get("subscription_type").getAsInt();
                int status = jsonObject.get("status").getAsInt();

                currencyCode = HelperUtils.getCurrencyName(currency);

                if(!Objects.equals(uddoktapay_api_key, "")) {
                    startPayment();
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

    private void startPayment() {
        Map<String, String> metadataMap = new HashMap<>();
        metadataMap.put("CustomMetaData1", "Meta Value 1");
        metadataMap.put("CustomMetaData2", "Meta Value 2");
        metadataMap.put("CustomMetaData3", "Meta Value 3");

        UddoktaPay.PaymentCallback paymentCallback = (status, fullName, email, amount, invoiceId, paymentMethod, senderNumber, transactionId, date, metadataValues, fee, chargeAmount) -> {
            // Callback method triggered when the payment status is received from the payment gateway.
            // It provides information about the payment transaction.
            storedFullName = fullName;
            storedEmail = email;
            storedAmount = amount;
            storedInvoiceId = invoiceId;
            storedPaymentMethod = paymentMethod;
            storedSenderNumber = senderNumber;
            storedTransactionId = transactionId;
            storedDate = date;
            storedFee = fee;
            storedChargedAmount = chargeAmount;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Clear previous metadata values to avoid duplication
                    storedMetaKey1 = null;
                    storedMetaValue1 = null;
                    storedMetaKey2 = null;
                    storedMetaValue2 = null;
                    storedMetaKey3 = null;
                    storedMetaValue3 = null;

                    // Iterate through the metadata map and store the key-value pairs
                    for (Map.Entry<String, String> entry : metadataValues.entrySet()) {
                        String metadataKey = entry.getKey();
                        String metadataValue = entry.getValue();

                        if ("CustomMetaData1".equals(metadataKey)) {
                            storedMetaKey1 = metadataKey;
                            storedMetaValue1 = metadataValue;
                        } else if ("CustomMetaData2".equals(metadataKey)) {
                            storedMetaKey2 = metadataKey;
                            storedMetaValue2 = metadataValue;
                        } else if ("CustomMetaData3".equals(metadataKey)) {
                            storedMetaKey3 = metadataKey;
                            storedMetaValue3 = metadataValue;
                        }
                    }

                    // Update UI based on payment status
                    if ("COMPLETED".equals(status)) {
                        uddoktaPayWebView.setVisibility(View.GONE);
                        RequestQueue queue = Volley.newRequestQueue(UddoktaPayActivity.this);
                        StringRequest sr = new StringRequest(Request.Method.POST, AppConfig.url + "dXBncmFkZQ", response -> {
                            if(response.equals("Account Upgraded Succefully")) {

                                Snackbar snackbar = Snackbar.make(rootView, "Account Upgraded Succefully!", Snackbar.LENGTH_SHORT);
                                snackbar.show();

                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.postDelayed(() -> {
                                    finishAffinity();
                                    startActivity(new Intent(UddoktaPayActivity.this, Splash.class));
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
                    } else if ("PENDING".equals(status)) {
                        uddoktaPayWebView.setVisibility(View.GONE);
                        finish();
                    } else if ("ERROR".equals(status)) {
                        uddoktaPayWebView.setVisibility(View.GONE);
                        finish();
                    }
                }
            });
        };

        uddoktaPayWebView.setVisibility(View.VISIBLE);
        UddoktaPay uddoktapay = new UddoktaPay(uddoktaPayWebView, paymentCallback);
        uddoktapay.loadPaymentForm(uddoktapay_api_key, userName, email, String.valueOf(amount), uddoktapay_checkout_url, uddoktapay_verify_payment_url, REDIRECT_URL, CANCEL_URL, metadataMap);
    }
}