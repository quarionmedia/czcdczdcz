package com.dooo.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dooo.android.utils.BaseActivity;
import com.dooo.android.utils.HelperUtils;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class FlutterwavePaymentGatway extends BaseActivity {
    View rootView;
    int userID;
    String userName, email;
    String name;
    int subscriptionType, amount, time;
    String currencyCode;

    int flutterwave_status;
    String flutterwave_public_key, flutterwave_secret_key, flutterwave_encryption_key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_flutterwave_payment_gatway);

        rootView = findViewById(R.id.FlutterwavePaymentGatway);

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

        flutterwave_status = jsonObject.get("flutterwave_status").getAsInt();
        flutterwave_public_key = jsonObject.get("flutterwave_public_key").getAsString();
        flutterwave_secret_key = jsonObject.get("flutterwave_secret_key").getAsString();
        flutterwave_encryption_key = jsonObject.get("flutterwave_encryption_key").getAsString();
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

                if(!Objects.equals(flutterwave_public_key, "") && !Objects.equals(flutterwave_encryption_key, "")) {
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


    private String getReceiptString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 40) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    private void startPayment() {
        new RaveUiManager(FlutterwavePaymentGatway.this).setAmount(amount)
                .setCurrency(currencyCode)
                .setEmail(email)
                .setfName(name)
                .setlName(name)
                .setPublicKey(flutterwave_public_key)
                .setEncryptionKey(flutterwave_encryption_key)
                .setTxRef(getReceiptString())
                .acceptAccountPayments(true)
                .acceptCardPayments(true)
                .acceptMpesaPayments(true)
                .acceptAchPayments(true)
                .acceptGHMobileMoneyPayments(true)
                .acceptUgMobileMoneyPayments(true)
                .acceptZmMobileMoneyPayments(true)
                .acceptRwfMobileMoneyPayments(true)
                .acceptSaBankPayments(true)
                .acceptUkPayments(true)
                .acceptBankTransferPayments(true)
                .acceptUssdPayments(true)
                .acceptBarterPayments(true)
                .acceptFrancMobileMoneyPayments(true, "NG")
                .allowSaveCardFeature(true)
                .onStagingEnv(true)
                .shouldDisplayFee(false )
                .showStagingLabel(true)
                .initialize();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
         *  We advise you to do a further verification of transaction's details on your server to be
         *  sure everything checks out before providing service or goods.
         */
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                RequestQueue queue = Volley.newRequestQueue(this);
                StringRequest sr = new StringRequest(Request.Method.POST, AppConfig.url + "dXBncmFkZQ", response -> {
                    if(response.equals("Account Upgraded Succefully")) {

                        Snackbar snackbar = Snackbar.make(rootView, "Account Upgraded Succefully!", Snackbar.LENGTH_SHORT);
                        snackbar.show();

                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(() -> {
                            finishAffinity();
                            startActivity(new Intent(FlutterwavePaymentGatway.this, Splash.class));
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
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR " + message, Toast.LENGTH_SHORT).show();
                finish();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED " + message, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}