package com.dooo.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dooo.android.adepter.AllGenreListAdepter;
import com.dooo.android.list.GenreList;
import com.dooo.android.utils.BaseActivity;
import com.dooo.android.utils.HelperUtils;
import com.dooo.android.utils.bKashUtils.Checkout;
import com.dooo.android.utils.bKashUtils.PaymentRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StripePaymentGatwayActivity extends BaseActivity {

    View rootView;
    int userID;
    String userName, email;
    private PaymentSheet paymentSheet;
    String name;
    int subscriptionType, amount, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_stripe_payment_gatway);
        rootView = findViewById(R.id.StripePaymentGatwayActivity);

        paymentSheet = new PaymentSheet(StripePaymentGatwayActivity.this, StripePaymentGatwayActivity.this::onPaymentSheetResult);

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
        StringRequest sr = new StringRequest(Request.Method.POST, AppConfig.url +"getStripePaymentIntent", response -> {
            JsonObject jsonArray = new Gson().fromJson(response, JsonObject.class);
            String error = jsonArray.has("error") ? jsonArray.get("error").getAsString() : null;

            if (error != null && !error.isEmpty()) {
                Toast.makeText(StripePaymentGatwayActivity.this, error, Toast.LENGTH_LONG).show();
                finish();
            } else {
                String clientSecret = jsonArray.get("clientSecret").getAsString();
                String publishableKey = jsonArray.get("publishableKey").getAsString();

                PaymentConfiguration.init(StripePaymentGatwayActivity.this, publishableKey);

                if (clientSecret != null) {
                    presentPaymentSheet(clientSecret);
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

    private void presentPaymentSheet(String clientSecret) {
        PaymentSheet.Configuration configuration = new PaymentSheet.Configuration("Example, Inc.");
        paymentSheet.presentWithPaymentIntent(clientSecret, configuration);
    }

    private void onPaymentSheetResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            RequestQueue queue = Volley.newRequestQueue(StripePaymentGatwayActivity.this);
            StringRequest sr = new StringRequest(Request.Method.POST, AppConfig.url + "dXBncmFkZQ", response -> {
                if(response.equals("Account Upgraded Succefully")) {

                    Snackbar snackbar = Snackbar.make(rootView, "Account Upgraded Succefully!", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(() -> {
                        finishAffinity();
                        startActivity(new Intent(StripePaymentGatwayActivity.this, Splash.class));
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

        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
            finish();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(this, "Payment Canceled", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}