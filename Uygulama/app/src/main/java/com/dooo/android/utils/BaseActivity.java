package com.dooo.android.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dooo.android.AppConfig;

import java.util.Timer;
import java.util.TimerTask;

public class BaseActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        NoInternetDialog noInternetDialog = new NoInternetDialog(context);
        VPNDialog VPNDialog = new VPNDialog(context);
        NoRootDialog noRootDialog = new NoRootDialog(context);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(!((Activity) context).isFinishing()) {
                    if(!HelperUtils.isInternetAvailable(context)) {
                        runOnUiThread(noInternetDialog::show);
                    } else {
                        runOnUiThread(() -> {
                            if(noInternetDialog.isShowing()) {
                                noInternetDialog.dismiss();
                            }
                        });

                    }

                    if (HelperUtils.isVpnConnected(context, AppConfig.allowVPN)) {
                        runOnUiThread(VPNDialog::show);
                    } else {
                        runOnUiThread(() -> {
                            if (VPNDialog.isShowing()) {
                                VPNDialog.dismiss();
                            }
                        });

                    }

                    if (HelperUtils.cr((Activity) context, AppConfig.allowRoot)) {
                        runOnUiThread(noRootDialog::show);
                    } else {
                        if (noRootDialog.isShowing()) {
                            noRootDialog.dismiss();
                        }
                    }
                }
            }
        }, 0, 1000);


        if(!AppConfig.allowPrivateDNS) {
            DNSDialog dnsDialog = new DNSDialog(context);
            ConnectivityManager cMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cMgr != null) {
                cMgr.registerNetworkCallback(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
                    @Override
                    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
                        super.onLinkPropertiesChanged(network, linkProperties);
                        if(!((Activity) context).isFinishing()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                if(linkProperties.isPrivateDnsActive()) {
                                    runOnUiThread(dnsDialog::show);
                                } else {
                                    runOnUiThread(() -> {
                                        if(dnsDialog.isShowing()) {
                                            dnsDialog.dismiss();
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
            }
        }

    }

}
