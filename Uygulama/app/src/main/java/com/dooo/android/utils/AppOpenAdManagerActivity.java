package com.dooo.android.utils;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.dooo.android.AppConfig;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback;

import java.util.Date;

public class AppOpenAdManagerActivity extends App
        implements ActivityLifecycleCallbacks, LifecycleObserver {

    private AppOpenAdManager appOpenAdManager;
    private Activity currentActivity;

    boolean removeAds = false;
    boolean playPremium = false;
    boolean downloadPremium = false;

    com.wortise.ads.appopen.AppOpenAd mAppOpenAd;

    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);

        SharedPreferences sharedPreferences = this.getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        String subscriptionType = sharedPreferences.getString("subscription_type", null);

        String number = String.valueOf(subscriptionType);
        for(int i = 0; i < number.length(); i++) {
            int userSubType = Character.digit(number.charAt(i), 10);
            if(userSubType == 1) {
                removeAds = true;
            } else if(userSubType == 2) {
                playPremium = true;
            } else if(userSubType == 3) {
                downloadPremium = true;
            } else {
                removeAds = false;
                playPremium = false;
                downloadPremium = false;
            }
        }
    }

    @OnLifecycleEvent(Event.ON_START)
    protected void onMoveToForeground() {
        if(!removeAds) {
            if (AppConfig.adType == 1) {
                appOpenAdManager.showAdIfAvailable(currentActivity);
            } else if (AppConfig.adType == 9) {
                if (mAppOpenAd.isAvailable()) {
                    mAppOpenAd.tryToShowAd(currentActivity);
                }
            }
        }
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        if(!removeAds) {
            ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
            if (AppConfig.adType == 1) {
                MobileAds.initialize(
                        this,
                        initializationStatus -> {});
                appOpenAdManager = new AppOpenAdManager();
            } else if (AppConfig.adType == 9) {
                mAppOpenAd = new com.wortise.ads.appopen.AppOpenAd(this, AppConfig.wortise_app_open);
                mAppOpenAd.setAutoReload(true);
                mAppOpenAd.loadAd();
            }
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {}

    @Override
    public void onActivityPaused(@NonNull Activity activity) {}

    @Override
    public void onActivityStopped(@NonNull Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {}

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {}

    public void showAdIfAvailable(
            @NonNull Activity activity,
            @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
        if (appOpenAdManager != null) {
            appOpenAdManager.showAdIfAvailable(activity, onShowAdCompleteListener);
        } else {
            // Handle the case where appOpenAdManager is not initialized
            onShowAdCompleteListener.onShowAdComplete();
        }
    }

    public interface OnShowAdCompleteListener {
        void onShowAdComplete();
    }

    private static class AppOpenAdManager {

        private AppOpenAd appOpenAd = null;
        private boolean isLoadingAd = false;
        private boolean isShowingAd = false;

        private long loadTime = 0;

        public AppOpenAdManager() {}

        private void loadAd(Context context) {
            if (isLoadingAd || isAdAvailable()) {
                return;
            }

            isLoadingAd = true;
            AdRequest request = new AdRequest.Builder().build();
            AppOpenAd.load(context, AppConfig.adMobAppOpenAd,request,0,new AppOpenAdLoadCallback() {
                        @Override
                        public void onAdLoaded(AppOpenAd ad) {
                            appOpenAd = ad;
                            isLoadingAd = false;
                            loadTime = (new Date()).getTime();
                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            isLoadingAd = false;
                        }
                    });
        }

        private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
            long dateDifference = (new Date()).getTime() - loadTime;
            long numMilliSecondsPerHour = 3600000;
            return (dateDifference < (numMilliSecondsPerHour * numHours));
        }

        private boolean isAdAvailable() {
            return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
        }

        private void showAdIfAvailable(@NonNull final Activity activity) {
            showAdIfAvailable(
                    activity,
                    () -> {
                        // Empty because the user will go back to the activity that shows the ad.
                    });
        }

        private void showAdIfAvailable(
                @NonNull final Activity activity,
                @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
            if (isShowingAd) {
                return;
            }

            if (!isAdAvailable()) {
                onShowAdCompleteListener.onShowAdComplete();
                loadAd(activity);
                return;
            }

            appOpenAd.setFullScreenContentCallback(
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            appOpenAd = null;
                            isShowingAd = false;

                            onShowAdCompleteListener.onShowAdComplete();
                            loadAd(activity);
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            appOpenAd = null;
                            isShowingAd = false;

                            onShowAdCompleteListener.onShowAdComplete();
                            loadAd(activity);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                        }
                    });

            isShowingAd = true;
            appOpenAd.show(activity);
        }
    }
}
