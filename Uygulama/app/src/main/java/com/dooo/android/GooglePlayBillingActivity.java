package com.dooo.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.PendingPurchasesParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.ProductDetails;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dooo.android.utils.HelperUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GooglePlayBillingActivity extends AppCompatActivity implements PurchasesUpdatedListener {

    private BillingClient billingClient;
    private Context context = this;
    String name;
    int subscriptionType, amount, time;
    String currencyCode;
    String play_store_billing_product_id;

    int userID;
    String userName, email;

    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_play_billing);

        rootView = findViewById(R.id.GooglePlayBillingActivity);

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

    void loadSubscriptionDetails(int id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.GET, AppConfig.url +"getSubscriptionDetails/"+id, response -> {
            if(!response.equals("No Data Avaliable")) {
                JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
                int id1 = jsonObject.get("id").getAsInt();
                name = jsonObject.get("name").getAsString();
                time = jsonObject.get("time").getAsInt();
                amount = jsonObject.get("amount").getAsInt();
                play_store_billing_product_id = jsonObject.get("play_store_billing_product_id").getAsString();
                int currency = jsonObject.get("currency").getAsInt();
                String background = jsonObject.get("background").getAsString();
                subscriptionType = jsonObject.get("subscription_type").getAsInt();
                int status = jsonObject.get("status").getAsInt();
                currencyCode = HelperUtils.getCurrencyName(currency);

                PendingPurchasesParams pendingPurchasesParams = PendingPurchasesParams.newBuilder()
                        .enableOneTimeProducts()
                        .build();

                billingClient = BillingClient.newBuilder(this)
                        .setListener(this)
                        .enablePendingPurchases(pendingPurchasesParams)
                        .build();

                if(!Objects.equals(play_store_billing_product_id, "")) {
                    startBillingConnection(play_store_billing_product_id);
                } else {
                    handleFailure("Product not found");
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

    private void startBillingConnection(String productId) {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    initiatePurchase(productId);
                } else {
                    handleFailure("Billing setup failed");
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                handleFailure("Billing service disconnected");
            }
        });
    }

    private void initiatePurchase(String productId) {
        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
            .setProductList(List.of(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(productId)
                    .setProductType(BillingClient.ProductType.INAPP)
                    .build()
            ))
            .build();

        billingClient.queryProductDetailsAsync(params, (billingResult, productDetailsList) -> {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && !productDetailsList.isEmpty()) {
            ProductDetails productDetails = productDetailsList.get(0);
            BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(
                    List.of(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                            .setProductDetails(productDetails)
                            .build()
                    )
                )
                .build();
            billingClient.launchBillingFlow(this, billingFlowParams);
        } else {
            handleFailure("Product not found or billing error");
        }
    });
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest sr = new StringRequest(Request.Method.POST, AppConfig.url + "dXBncmFkZQ", response -> {
                if(response.equals("Account Upgraded Succefully")) {

                    Snackbar snackbar = Snackbar.make(rootView, "Account Upgraded Successfully!", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(() -> {
                        finishAffinity();
                        startActivity(new Intent(GooglePlayBillingActivity.this, Splash.class));
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
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            handleFailure("Purchase canceled by user");
        } else {
            handleFailure("Purchase failed with error code: " + billingResult.getResponseCode());
        }
    }

    private void handleFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (billingClient != null) {
            billingClient.endConnection();
        }
    }
}
