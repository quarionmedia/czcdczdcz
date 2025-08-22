package com.dooo.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dooo.android.utils.BaseActivity;
import com.dooo.android.utils.HelperUtils;
import com.dooo.android.utils.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.paypal.android.corepayments.CoreConfig;
import com.paypal.android.corepayments.Environment;
import com.paypal.android.corepayments.PayPalSDKError;
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutClient;
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutFundingSource;
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutListener;
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutRequest;
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PaypalPaymentGatway extends BaseActivity {

    private Context context = this;
    String name;
    int subscriptionType, amount, time;
    String currencyCode;

    int userID;
    String userName, email;

    int paypal_status;
    int paypal_type;
    String paypal_clint_id = "";
    String paypal_secret_key = "";

    View rootView;
    String paypalOrderID = "";
    String access_token = "";
    public static String getPaypalBaseURL(int type) {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "https://api-m.sandbox.paypal.com");
        map.put(1, "https://api-m.paypal.com");

        if(map.get(type)!=null) {
            return map.get(type);
        } else {
            return map.get("");
        }
    }

    @Override
    protected void onNewIntent(Intent newIntent) {
        super.onNewIntent(newIntent);
        setIntent(newIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setContentView(R.layout.activity_paypal_payment_gatway);

        rootView = findViewById(R.id.PaypalPaymentGatway);

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

        paypal_status = jsonObject.get("paypal_status").getAsInt();
        paypal_type = jsonObject.get("paypal_type").getAsInt();
        paypal_clint_id = jsonObject.get("paypal_clint_id").getAsString();
        paypal_secret_key = jsonObject.get("paypal_secret_key").getAsString();
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
                int currency = jsonObject.get("currency").getAsInt();
                String background = jsonObject.get("background").getAsString();
                subscriptionType = jsonObject.get("subscription_type").getAsInt();
                int status = jsonObject.get("status").getAsInt();

                currencyCode = HelperUtils.getCurrencyName(currency);

                if(!Objects.equals(paypal_clint_id, "")) {
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
        CoreConfig config = new CoreConfig(paypal_clint_id, (paypal_type == 0) ? Environment.SANDBOX : Environment.LIVE);
        PayPalWebCheckoutClient payPalWebCheckoutClient = new PayPalWebCheckoutClient(
                this,
                config,
                "paypalpay"
        );
        payPalWebCheckoutClient.setListener(new PayPalWebCheckoutListener() {

            @Override
            public void onPayPalWebSuccess(@NonNull PayPalWebCheckoutResult payPalWebCheckoutResult) {
                //Order successfully captured

                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest sr = new StringRequest(Request.Method.POST, getPaypalBaseURL(paypal_type) +"/v2/checkout/orders/"+paypalOrderID+"/capture", response0 -> {
                    StringRequest sr100 = new StringRequest(Request.Method.POST, AppConfig.url + "dXBncmFkZQ", response -> {
                        if(response.equals("Account Upgraded Succefully")) {

                            Snackbar snackbar = Snackbar.make(rootView, "Account Upgraded Succefully!", Snackbar.LENGTH_SHORT);
                            snackbar.show();

                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(() -> {
                                finishAffinity();
                                startActivity(new Intent(PaypalPaymentGatway.this, Splash.class));
                            }, 2000);
                        } else {
                            Toast.makeText(context, "Something went wrongQ", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }, error -> {
                        Toast.makeText(context, "Something went wrongQ", Toast.LENGTH_SHORT).show();
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
                    queue.add(sr100);

                }, error -> {
                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    finish();
                }) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String,String> params = new HashMap<>();
                        params.put("Content-Type", "application/json");
                        params.put("Authorization", "Bearer "+access_token);
                        return params;
                    }
                };
                queue.add(sr);
            }

            @Override
            public void onPayPalWebFailure(@NonNull PayPalSDKError payPalSDKError) {
                Toast.makeText(context, "ERROR :" + payPalSDKError.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onPayPalWebCanceled() {
                Toast.makeText(context, "Payment Cancelled!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, getPaypalBaseURL(paypal_type) +"/v1/oauth2/token", response -> {
            JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
            access_token = jsonObject.get("access_token").getAsString();


            // Create the JSON object for the request body
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("intent", "CAPTURE");

                // Create the "amount" object
                JSONObject amountObj = new JSONObject();
                amountObj.put("currency_code", "USD");
                amountObj.put("value", String.valueOf(amount));

                // Create the "purchase_units" array
                JSONArray purchaseUnitsArray = new JSONArray();

                // Create the "purchase_unit" object and add the "amount" object
                JSONObject purchaseUnit = new JSONObject();
                purchaseUnit.put("amount", amountObj);
                purchaseUnitsArray.put(purchaseUnit);

                jsonBody.put("purchase_units", purchaseUnitsArray);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Create the request
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getPaypalBaseURL(paypal_type) +"/v2/checkout/orders/", jsonBody,
                    response2 -> {
                        JsonObject jsonObject2 = new Gson().fromJson(String.valueOf(response2), JsonObject.class);
                        paypalOrderID = jsonObject2.get("id").getAsString();

                        PayPalWebCheckoutRequest payPalWebCheckoutRequest = new PayPalWebCheckoutRequest(paypalOrderID, PayPalWebCheckoutFundingSource.PAYPAL);
                        payPalWebCheckoutClient.start(payPalWebCheckoutRequest);

                    }, error -> {
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public byte[] getBody() {
                    return jsonBody.toString().getBytes();
                }

                @Override
                public java.util.Map<String, String> getHeaders() {
                    java.util.Map<String, String> headers = new java.util.HashMap<>();
                    headers.put("Authorization", "Bearer "+access_token);
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest);

        }, error -> {
            // Do nothing
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("grant_type", "client_credentials");
                return map;
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Authorization", "Basic "+ Utils.toBase64(paypal_clint_id+":"+paypal_secret_key));
                return params;
            }
        };
        queue.add(sr);

    }

}