package com.dooo.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dooo.android.utils.BaseActivity;
import com.dooo.android.utils.HelperUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class SubscriptionDetails extends BaseActivity {
    LinearLayout payNow;
    View payment_dialog;


    int id;

    String name;
    int subscriptionType, amount, time;
    String strCurrency;

    int userID;
    String userName, email;

    int payment_gateway_type, razorpay_status, paypal_status, flutterwave_status, uddoktapay_status, bKash_status, google_play_billing_status, stripe_status, coingate_status;
    String razorpay_key_id, razorpay_key_secret, paypal_clint_id, flutterwave_public_key, flutterwave_secret_key, flutterwave_encryption_key, uddoktapay_api_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(AppConfig.FLAG_SECURE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
        }

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.Home_TitleBar_BG));

        setContentView(R.layout.activity_subscription_details);

        loadData();
        loadConfig();

        Intent intent = getIntent();
        id = intent.getExtras().getInt("ID");
        loadSubscriptionDetails(id);

        payNow = findViewById(R.id.Pay_Now);
        payNow.setOnClickListener(view -> {
            if(payment_gateway_type == 0) {
                paymentDialog();
            } else {
                Intent intent1 = new Intent(SubscriptionDetails.this, CustomPaymentActivity.class);
                intent1.putExtra("ID", id);
                startActivity(intent1);
            }
        });

        setColorTheme(Color.parseColor(AppConfig.primeryThemeColor));
    }

    void setColorTheme(int color) {
        TextView titleText = findViewById(R.id.titleText);
        titleText.setTextColor(color);

        LinearLayout Pay_Now = findViewById(R.id.Pay_Now);
        Pay_Now.setBackgroundColor(color);
    }

    private void paymentDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.payment_dialog);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        CardView razorPay = bottomSheetDialog.findViewById(R.id.razorPay);
        switch (razorpay_status) {
            case 0:
                razorPay.setVisibility(View.GONE);
                break;
            case 1:
                razorPay.setVisibility(View.VISIBLE);
                break;
            default:
                razorPay.setVisibility(View.GONE);
        }
        razorPay.setOnClickListener(view->{
            Intent intent00 = new Intent(SubscriptionDetails.this, Razorpay_Payment_gatway.class);
            intent00.putExtra("id", id);
            startActivity(intent00);
        });

        CardView paypalPayment = bottomSheetDialog.findViewById(R.id.paypalPayment);
        switch (paypal_status) {
            case 0:
                paypalPayment.setVisibility(View.GONE);
                break;
            case 1:
                paypalPayment.setVisibility(View.VISIBLE);
                break;
            default:
                paypalPayment.setVisibility(View.GONE);
        }
        paypalPayment.setOnClickListener(view->{
            Intent intent00 = new Intent(SubscriptionDetails.this, PaypalPaymentGatway.class);
            intent00.putExtra("id", id);
            startActivity(intent00);
        });

        CardView upi = bottomSheetDialog.findViewById(R.id.upi);
        upi.setOnClickListener(view -> {
            Intent intent00 = new Intent(SubscriptionDetails.this, UPI.class);
            intent00.putExtra("id", id);
            startActivity(intent00);
        });

        CardView paytm = bottomSheetDialog.findViewById(R.id.paytm);
        paytm.setOnClickListener(view->{
            Intent intent00 = new Intent(SubscriptionDetails.this, PaytmPaymentGatway.class);
            intent00.putExtra("id", id);
            startActivity(intent00);
        });

        CardView flutterwave = bottomSheetDialog.findViewById(R.id.flutterwave);
        switch (flutterwave_status) {
            case 0:
                flutterwave.setVisibility(View.GONE);
                break;
            case 1:
                flutterwave.setVisibility(View.VISIBLE);
                break;
            default:
                flutterwave.setVisibility(View.GONE);
        }
        flutterwave.setOnClickListener(view->{
            Intent intent00 = new Intent(SubscriptionDetails.this, FlutterwavePaymentGatway.class);
            intent00.putExtra("id", id);
            startActivity(intent00);
        });

        CardView uddoktaPay = bottomSheetDialog.findViewById(R.id.uddoktaPay);
        switch (uddoktapay_status) {
            case 0:
                uddoktaPay.setVisibility(View.GONE);
                break;
            case 1:
                uddoktaPay.setVisibility(View.VISIBLE);
                break;
            default:
                uddoktaPay.setVisibility(View.GONE);
        }
        uddoktaPay.setOnClickListener(view->{
            Intent intent00 = new Intent(SubscriptionDetails.this, UddoktaPayActivity.class);
            intent00.putExtra("id", id);
            startActivity(intent00);
        });

        CardView bKash = bottomSheetDialog.findViewById(R.id.bKash);
        switch (bKash_status) {
            case 0:
                bKash.setVisibility(View.GONE);
                break;
            case 1:
                bKash.setVisibility(View.VISIBLE);
                break;
            default:
                bKash.setVisibility(View.GONE);
        }
        bKash.setOnClickListener(view->{
            Intent intent00 = new Intent(SubscriptionDetails.this, bKashActivity.class);
            intent00.putExtra("id", id);
            startActivity(intent00);
        });

        CardView gpBilling = bottomSheetDialog.findViewById(R.id.gpBilling);
        switch (google_play_billing_status) {
            case 0:
                gpBilling.setVisibility(View.GONE);
                break;
            case 1:
                gpBilling.setVisibility(View.VISIBLE);
                break;
            default:
                gpBilling.setVisibility(View.GONE);
        }
        gpBilling.setOnClickListener(view->{
            Intent intent00 = new Intent(SubscriptionDetails.this, GooglePlayBillingActivity.class);
            intent00.putExtra("id", id);
            startActivity(intent00);
        });


        CardView stripe = bottomSheetDialog.findViewById(R.id.stripe);
        switch (stripe_status) {
            case 0:
                stripe.setVisibility(View.GONE);
                break;
            case 1:
                stripe.setVisibility(View.VISIBLE);
                break;
            default:
                stripe.setVisibility(View.GONE);
        }
        stripe.setOnClickListener(view->{
            Intent intent00 = new Intent(SubscriptionDetails.this, StripePaymentGatwayActivity.class);
            intent00.putExtra("id", id);
            startActivity(intent00);
        });


        CardView coingate = bottomSheetDialog.findViewById(R.id.coingate);
        switch (coingate_status) {
            case 0:
                coingate.setVisibility(View.GONE);
                break;
            case 1:
                coingate.setVisibility(View.VISIBLE);
                break;
            default:
                coingate.setVisibility(View.GONE);
        }
        coingate.setOnClickListener(view->{
            Intent intent00 = new Intent(SubscriptionDetails.this, CoingatePaymentGatewayActivity.class);
            intent00.putExtra("id", id);
            startActivity(intent00);
        });

        bottomSheetDialog.show();
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

                TextView nameTextView = findViewById(R.id.Name_TextView);
                nameTextView.setText(name);

                TextView timeTextView = findViewById(R.id.Time_TextView);
                timeTextView.setText(String.valueOf(time+"Days"));

                TextView amountTextView= findViewById(R.id.Amount_TextView);
                amountTextView.setText(HelperUtils.getCurrencyNameWithSymbol(currency) +" - "+ amount);

                ImageView subscriptionTtemBg= findViewById(R.id.Subscription_item_bg);
                Glide.with(SubscriptionDetails.this)
                        .load(background)
                        .apply(RequestOptions.bitmapTransform(new BlurTransformation(5, 2)))
                        .placeholder(R.drawable.thumbnail_placeholder)
                        .into(subscriptionTtemBg);

                LinearLayout GATAC = findViewById(R.id.GATAC);
                LinearLayout RAA = findViewById(R.id.RAA);
                LinearLayout WPC = findViewById(R.id.WPC);
                LinearLayout DPC = findViewById(R.id.DPC);


                String number = String.valueOf(subscriptionType);
                for(int i = 0; i < number.length(); i++) {
                    int userSubType = Character.digit(number.charAt(i), 10);
                    if(userSubType == 1) {
                        RAA.setVisibility(View.VISIBLE);
                    } else if(userSubType == 2) {
                        GATAC.setVisibility(View.VISIBLE);
                        WPC.setVisibility(View.VISIBLE);
                    } else if(userSubType == 3) {
                        DPC.setVisibility(View.VISIBLE);
                    } else {
                        GATAC.setVisibility(View.GONE);
                        RAA.setVisibility(View.GONE);
                        WPC.setVisibility(View.GONE);
                        DPC.setVisibility(View.GONE);
                    }
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

        payment_gateway_type = jsonObject.has("payment_gateway_type") && !jsonObject.get("payment_gateway_type").isJsonNull() ? jsonObject.get("payment_gateway_type").getAsInt() : 0;

        razorpay_status = jsonObject.has("razorpay_status") && !jsonObject.get("razorpay_status").isJsonNull() ? jsonObject.get("razorpay_status").getAsInt() : 0;
        razorpay_key_id = jsonObject.has("razorpay_key_id") && !jsonObject.get("razorpay_key_id").isJsonNull() ? jsonObject.get("razorpay_key_id").getAsString() : "";
        razorpay_key_secret = jsonObject.has("razorpay_key_secret") && !jsonObject.get("razorpay_key_secret").isJsonNull() ? jsonObject.get("razorpay_key_secret").getAsString() : "";

        paypal_status = jsonObject.has("paypal_status") && !jsonObject.get("paypal_status").isJsonNull() ? jsonObject.get("paypal_status").getAsInt() : 0;
        paypal_clint_id = jsonObject.has("paypal_clint_id") && !jsonObject.get("paypal_clint_id").isJsonNull() ? jsonObject.get("paypal_clint_id").getAsString() : "";

        flutterwave_status = jsonObject.has("flutterwave_status") && !jsonObject.get("flutterwave_status").isJsonNull() ? jsonObject.get("flutterwave_status").getAsInt() : 0;
        flutterwave_public_key = jsonObject.has("flutterwave_public_key") && !jsonObject.get("flutterwave_public_key").isJsonNull() ? jsonObject.get("flutterwave_public_key").getAsString() : "";
        flutterwave_secret_key = jsonObject.has("flutterwave_secret_key") && !jsonObject.get("flutterwave_secret_key").isJsonNull() ? jsonObject.get("flutterwave_secret_key").getAsString() : "";
        flutterwave_encryption_key = jsonObject.has("flutterwave_encryption_key") && !jsonObject.get("flutterwave_encryption_key").isJsonNull() ? jsonObject.get("flutterwave_encryption_key").getAsString() : "";

        uddoktapay_status = jsonObject.has("uddoktapay_status") && !jsonObject.get("uddoktapay_status").isJsonNull() ? jsonObject.get("uddoktapay_status").getAsInt() : 0;
        uddoktapay_api_key = jsonObject.has("uddoktapay_api_key") && !jsonObject.get("uddoktapay_api_key").isJsonNull() ? jsonObject.get("uddoktapay_api_key").getAsString() : "";

        bKash_status = jsonObject.has("bKash_status") && !jsonObject.get("bKash_status").isJsonNull() ? jsonObject.get("bKash_status").getAsInt() : 0;
        google_play_billing_status = jsonObject.has("google_play_billing_status") && !jsonObject.get("google_play_billing_status").isJsonNull() ? jsonObject.get("google_play_billing_status").getAsInt() : 0;
        stripe_status = jsonObject.has("stripe_status") && !jsonObject.get("stripe_status").isJsonNull() ? jsonObject.get("stripe_status").getAsInt() : 0;
        coingate_status = jsonObject.has("coingate_status") && !jsonObject.get("coingate_status").isJsonNull() ? jsonObject.get("coingate_status").getAsInt() : 0;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}