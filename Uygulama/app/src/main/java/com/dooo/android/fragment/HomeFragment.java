package com.dooo.android.fragment;

import static android.content.Context.MODE_PRIVATE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdSize;
import com.adcolony.sdk.AdColonyAdView;
import com.adcolony.sdk.AdColonyAdViewListener;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.sdk.AppLovinMediationProvider;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.applovin.sdk.AppLovinSdkInitializationConfiguration;
import com.bumptech.glide.Glide;
import com.dooo.android.AllMoviesActivity;
import com.dooo.android.AllWebSeriesActivity;
import com.dooo.android.AppConfig;
import com.dooo.android.Home;
import com.dooo.android.LiveTv;
import com.dooo.android.R;
import com.dooo.android.WebView;
import com.dooo.android.adepter.ContinuePlayingListAdepter;
import com.dooo.android.adepter.GenreListAdepter;
import com.dooo.android.adepter.ImageSliderAdepter;
import com.dooo.android.adepter.LiveTvChannelListAdepter;
import com.dooo.android.adepter.LiveTvGenreListAdepter;
import com.dooo.android.adepter.MostSearchedListAdepter;
import com.dooo.android.adepter.MovieListAdepter;
import com.dooo.android.adepter.NetworksListAdepter;
import com.dooo.android.adepter.TrendingListAdepter;
import com.dooo.android.adepter.WebSeriesListAdepter;
import com.dooo.android.adepter.moviesOnlyForYouListAdepter;
import com.dooo.android.adepter.webSeriesOnlyForYouListAdepter;
import com.dooo.android.db.resume_content.ResumeContent;
import com.dooo.android.db.resume_content.ResumeContentDatabase;
import com.dooo.android.list.ContinuePlayingList;
import com.dooo.android.list.GenreList;
import com.dooo.android.list.ImageSliderItem;
import com.dooo.android.list.LiveTvChannelList;
import com.dooo.android.list.LiveTvGenreList;
import com.dooo.android.list.MostSearchedList;
import com.dooo.android.list.MovieList;
import com.dooo.android.list.NetworksList;
import com.dooo.android.list.TrendingList;
import com.dooo.android.list.WebSeriesList;
import com.dooo.android.sharedpreferencesmanager.ConfigManager;
import com.dooo.android.sharedpreferencesmanager.UserManager;
import com.dooo.android.utils.App;
import com.dooo.android.utils.CustomDialog;
import com.dooo.android.utils.HelperUtils;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.sdk.InitializationListener;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.banner.BannerFormat;
import com.startapp.sdk.ads.banner.BannerListener;
import com.startapp.sdk.ads.banner.BannerRequest;
import com.startapp.sdk.ads.banner.Mrec;
import com.startapp.sdk.ads.nativead.NativeAdDetails;
import com.startapp.sdk.ads.nativead.NativeAdPreferences;
import com.startapp.sdk.ads.nativead.StartAppNativeAd;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;
import com.wortise.ads.AdError;
import com.wortise.ads.banner.BannerAd;
import com.wortise.ads.natives.GoogleNativeAd;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    Context context;
    int userID;
    LinearLayout genreLayout, LivetvgenreLayout, admobNativeadTemplateLayout, homeWebSeriesLayout;
    RecyclerView genre_list_Recycler_View, live_tv_genre_list_Recycler_View, movieListRecycleview, webSeriesListRecycleview,
            continuePlaying_list_Recycler_View;
    LinearLayout recentMoviesLayout;
    RecyclerView home_Recent_Movies_list_Recycler_View;
    LinearLayout recentWebSeriesLayout;
    RecyclerView home_Recent_Series_list_Recycler_View;
    RecyclerView homeLiveTVlistRecyclerView;
    LinearLayout bywMovieLayoutLinearLayout;
    RecyclerView home_bywm_list_Recycler_View;
    LinearLayout bywWebSeriesLayout;
    RecyclerView home_bywws_list_Recycler_View;
    LinearLayout popularMoviesLayout;
    LinearLayout trendingLayout;
    RecyclerView home_popularMovies_list_Recycler_View;
    RecyclerView home_trending_list_Recycler_View;
    LinearLayout popularWebSeriesLayout;
    RecyclerView home_popularWebSeries_list_Recycler_View;
    SwipeRefreshLayout homeSwipeRefreshLayout;
    LinearLayout liveTvLayout;
    private ViewPager2 viewPager2;
    private final Handler sliderHandler = new Handler();

    String imageSliderType;
    List<ImageSliderItem> imageSliderItems;

    int movieImageSliderMaxVisible;
    int webseriesImageSliderMaxVisible;
    int adType;
    int shuffleContents;
    int showMessage;
    String message_animation_url;
    String messageTitle;
    String message;
    private HelperUtils helperUtils;
    int liveTvVisiableInHome, live_tv_genre_visible_in_home, genre_visible_in_home;
    LinearLayout homeMovieLayout;

    boolean removeAds = false;
    boolean playPremium = false;
    boolean downloadPremium = false;
    public static LinearLayout resume_Layout;
    ImageView clearContinuePlaying;

    LinearLayout moreMovies, moreWebSeries, moreLiveTV, moreRecentMovies, moreRecentSeries;

    RelativeLayout bannerViewLayout;
    RelativeLayout adViewLayout;
    RelativeLayout ad_container;
    ImageView custom_footer_banner_ad;
    ImageView custom_banner_ad;
    TemplateView template;
    LinearLayout homeProfile;
    LinearLayout homeTopSearchedLayout;
    RecyclerView home_top_searched_list_Recycler_View;
    PlayerView custom_banner_video_ad, custom_footer_banner_video_ad;
    CardView bottom_floting_menu;
    TextView bottom_floting_menu_movies, bottom_floting_menu_web_series, bottom_floting_menu_live_tv;
    TextView appName;
    ImageView appLogo;
    LinearLayout networkseLayout;
    RecyclerView networks_list_Recycler_View;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(AppConfig.FLAG_SECURE) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        View layoutInflater = inflater.inflate(R.layout.fragment_home, container, false);
        bindViews(layoutInflater);


        NestedScrollView nestedScrollView = layoutInflater.findViewById(R.id.nestedScrollView);
        LinearLayout appBar = layoutInflater.findViewById(R.id.appBar);
        appBar.setBackgroundColor(Color.parseColor("#"+HelperUtils.getAlphaColor(0)+"090911"));
        bottom_floting_menu.animate()
                .translationY(bottom_floting_menu.getHeight())
                .alpha(0.0f)
                .setDuration(100);
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > oldScrollY) {
                //Log.i(TAG, "Scroll DOWN");
                //appBar.getBackground().setAlpha(0);
                //Log.d("test", String.valueOf(scrollY));
                if(scrollY < 303) {
                    appBar.setBackgroundColor(Color.parseColor("#"+HelperUtils.getAlphaColor(scrollY)+"090911"));
                    //bottom_floting_menu.setVisibility(View.GONE);
                    bottom_floting_menu.animate()
                            .translationY(bottom_floting_menu.getHeight())
                            .alpha(0.0f)
                            .setDuration(100);
                } else {
                    appBar.setBackgroundColor(Color.parseColor("#"+HelperUtils.getAlphaColor(303)+"090911"));
                    //bottom_floting_menu.setVisibility(View.VISIBLE);
                    bottom_floting_menu.animate()
                            .translationY(0)
                            .alpha(1.0f)
                            .setDuration(100);
                }
            }
            if (scrollY < oldScrollY) {
                //Log.i(TAG, "Scroll UP");
                //Log.d("test", String.valueOf(scrollY));
                if(scrollY < 303) {
                    appBar.setBackgroundColor(Color.parseColor("#"+HelperUtils.getAlphaColor(scrollY)+"090911"));
                    //bottom_floting_menu.setVisibility(View.GONE);
                    bottom_floting_menu.animate()
                            .translationY(bottom_floting_menu.getHeight())
                            .alpha(0.0f)
                            .setDuration(100);
                } else {
                    appBar.setBackgroundColor(Color.parseColor("#"+HelperUtils.getAlphaColor(303)+"090911"));
                    //bottom_floting_menu.setVisibility(View.VISIBLE);
                    bottom_floting_menu.animate()
                            .translationY(0)
                            .alpha(1.0f)
                            .setDuration(100);
                }
            }

            if (scrollY == 0) {
               // Log.i(TAG, "TOP SCROLL");
                appBar.setBackgroundColor(Color.parseColor("#"+HelperUtils.getAlphaColor(0)+"090911"));

            }

            if (scrollY == ( v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight() )) {
                //Log.i(TAG, "BOTTOM SCROLL");
                appBar.setBackgroundColor(Color.parseColor("#"+HelperUtils.getAlphaColor(303)+"090911"));
            }
        });

        try {
            JSONObject userObject = UserManager.loadUser(context);
            userID = userObject.getInt("ID");
        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }

        try {
            JSONObject configObject = ConfigManager.loadConfig(context);
            appName.setText(configObject.getString("name"));
            if(!configObject.getString("logo").equals("")) {
                if(configObject.getString("name").equals("")) {
                    appLogo.requestLayout();
                    appLogo.getLayoutParams().height=150;
                    appLogo.getLayoutParams().width=150;
                }
                Glide.with(context)
                        .load(configObject.getString("logo"))
                        .placeholder(R.drawable.dooo_logo_no_bg_red)
                        .into(appLogo);
            }

            imageSliderType = configObject.getString("image_slider_type");
            movieImageSliderMaxVisible = configObject.getInt("movie_image_slider_max_visible");
            webseriesImageSliderMaxVisible = configObject.getInt("webseries_image_slider_max_visible");
            adType = configObject.getInt("ad_type");

            shuffleContents = configObject.getInt("shuffle_contents");

            showMessage  = configObject.getInt("Show_Message");
            message_animation_url  = configObject.getString("message_animation_url");
            messageTitle = configObject.getString("Message_Title");
            message = configObject.getString("Message");
            if(showMessage == 1) {
                if(!AppConfig.isCustomMessageShown) {
                    helperUtils = new HelperUtils(getActivity());
                    helperUtils.showMsgDialog(getActivity(), messageTitle, message, message_animation_url);
                    AppConfig.isCustomMessageShown = true;
                }
            }

            liveTvVisiableInHome = configObject.getInt("LiveTV_Visiable_in_Home");

            genre_visible_in_home = configObject.getInt("genre_visible_in_home");

            if(genre_visible_in_home == 0) {
                genreLayout.setVisibility(View.GONE);
            } else if(genre_visible_in_home == 1) {
                genreLayout.setVisibility(View.VISIBLE);
            } else {
                genreLayout.setVisibility(View.VISIBLE);
            }

            live_tv_genre_visible_in_home = configObject.getInt("live_tv_genre_visible_in_home");

            if(live_tv_genre_visible_in_home == 0) {
                LivetvgenreLayout.setVisibility(View.GONE);
            } else if(live_tv_genre_visible_in_home == 1) {
                LivetvgenreLayout.setVisibility(View.VISIBLE);
            } else {
                LivetvgenreLayout.setVisibility(View.VISIBLE);
            }

            if(configObject.getInt("home_bottom_floting_menu_status") == 1) {
                bottom_floting_menu.setVisibility(View.VISIBLE);
            } else {
                bottom_floting_menu.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }

        loadUserSubscriptionDetails();

        imageSliderItems = new ArrayList<>();
        switch (imageSliderType) {
            case "0":
                topMoviesImageSlider();
                break;
            case "1":
                topWebSeriesImageSlider();
                break;
            case "2":
                customImageSlider();
                break;
            case "3":
                viewPager2.setVisibility(View.GONE);
                break;
            default:
                Log.d("Dooo", "Visiable");
        }

        viewPager2.setClipToPadding(true);
        viewPager2.setClipChildren(true);
        //viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

//        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
//        compositePageTransformer.addTransformer((page, position) -> {
//            page.setTranslationX(-position * page.getWidth());
//
//            if (position < -1) {
//                page.setAlpha(0);
//            } else if (position <= 1) {
//                page.setAlpha(1);
//                page.setScaleX(1 - Math.abs(position));
//                page.setScaleY(1 - Math.abs(position));
//                page.setPivotX(page.getWidth() / 2f);
//                page.setPivotY(page.getHeight() / 2f);
//                page.setTranslationZ(position < 0 ? 0 : -1);
//            } else {
//                page.setAlpha(0);
//            }
//        });
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1-Math.abs(position);
            page.setScaleY(0.85f + r * 0.20f);
            page.setScaleX(0.90f + r * 0.20f);
        });

        viewPager2.setPageTransformer(compositePageTransformer);


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 5000);
            }
        });

        if(liveTvVisiableInHome == 0) {
            liveTvLayout.setVisibility(View.GONE);
        } else if(liveTvVisiableInHome == 1) {
            liveTvLayout.setVisibility(View.VISIBLE);
        } else  {
            liveTvLayout.setVisibility(View.VISIBLE);
        }

        loadhomecontentlist();

        homeSwipeRefreshLayout.setOnRefreshListener(() -> {
            loadhomecontentlist();
        });

        clearContinuePlaying.setOnClickListener(view -> {
            CustomDialog mDialog = new CustomDialog(getActivity())
                    .setTitle("Clear Continue Playing List?")
                    .setMessage("You can't Revert this!")
                    .isCancelable(true)
                    .setIcon(R.drawable.clean)
                    .setPositiveButton("Yes", R.drawable.delete, (dialogInterface, which) -> {
                        dialogInterface.dismissDialog();

                        ResumeContentDatabase db = ResumeContentDatabase.getDbInstance(context.getApplicationContext());
                        db.resumeContentDao().clearDB();
                        resume_Layout.setVisibility(View.GONE);
                    })
                    .setNegativeButton("NO", R.drawable.close, (dialogInterface, which) -> dialogInterface.dismissDialog())
                    .build();
            mDialog.showDialog();
        });


        moreMovies.setOnClickListener(view -> {
            Intent intent = new Intent(context, AllMoviesActivity.class);
            startActivity(intent);
        });

        moreWebSeries.setOnClickListener(view -> {
            Intent intent = new Intent(context, AllWebSeriesActivity.class);
            startActivity(intent);
        });

        moreLiveTV.setOnClickListener(view -> {
            Intent intent = new Intent(context, LiveTv.class);
            startActivity(intent);
        });

        moreRecentMovies.setOnClickListener(view -> {
            Intent intent = new Intent(context, AllMoviesActivity.class);
            startActivity(intent);
        });

        moreRecentSeries.setOnClickListener(view -> {
            Intent intent = new Intent(context, AllWebSeriesActivity.class);
            startActivity(intent);
        });

        //Ad Controller
        if(!removeAds) {
            loadAd();
        } else {
            admobNativeadTemplateLayout.setVisibility(View.GONE);
        }

        homeProfile.setOnClickListener(view ->{
            Home.bottomNavigationView.setSelectedItemId(R.id.account);
        });

        bottom_floting_menu_movies.setOnClickListener(view->{
            startActivity(new Intent(getActivity(), AllMoviesActivity.class));
        });
        bottom_floting_menu_web_series.setOnClickListener(view->{
            startActivity(new Intent(getActivity(), AllWebSeriesActivity.class));
        });
        bottom_floting_menu_live_tv.setOnClickListener(view->{
            startActivity(new Intent(getActivity(), LiveTv.class));
        });

        setColorTheme(Color.parseColor(AppConfig.primeryThemeColor), layoutInflater);

        return layoutInflater;
    }

    private void bindViews(View layoutInflater) {
        LivetvgenreLayout = layoutInflater.findViewById(R.id.LivetvgenreLayout);
        live_tv_genre_list_Recycler_View = layoutInflater.findViewById(R.id.live_tv_genre_list_Recycler_View);
        genreLayout = layoutInflater.findViewById(R.id.genreLayout);
        genre_list_Recycler_View = layoutInflater.findViewById(R.id.genre_list_Recycler_View);
        admobNativeadTemplateLayout = layoutInflater.findViewById(R.id.admob_nativead_template_layout);

        viewPager2 = layoutInflater.findViewById(R.id.ViewPagerImageSlider);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if(displayMetrics.widthPixels <= 720) {
            ViewGroup.LayoutParams layoutParams = viewPager2.getLayoutParams();
            layoutParams.height = (int) (400 * context.getResources().getDisplayMetrics().density);
        }


        homeMovieLayout = layoutInflater.findViewById(R.id.homeMovieLayout);
        movieListRecycleview = layoutInflater.findViewById(R.id.home_Movie_list_Recycler_View);
        homeSwipeRefreshLayout = layoutInflater.findViewById(R.id.Home_Swipe_Refresh_Layout);
        webSeriesListRecycleview = layoutInflater.findViewById(R.id.home_Web_Series_list_Recycler_View);
        homeWebSeriesLayout = layoutInflater.findViewById(R.id.homeWebSeriesLayout);
        recentMoviesLayout = layoutInflater.findViewById(R.id.recentMoviesLayout);
        home_Recent_Movies_list_Recycler_View = layoutInflater.findViewById(R.id.home_Recent_Movies_list_Recycler_View);
        recentWebSeriesLayout= layoutInflater.findViewById(R.id.recentWebSeriesLayout);
        home_Recent_Series_list_Recycler_View = layoutInflater.findViewById(R.id.home_Recent_Series_list_Recycler_View);
        homeLiveTVlistRecyclerView = layoutInflater.findViewById(R.id.home_Live_TV_list_Recycler_View);
        bywMovieLayoutLinearLayout= layoutInflater.findViewById(R.id.bywMovieLayout);
        home_bywm_list_Recycler_View = layoutInflater.findViewById(R.id.home_bywm_list_Recycler_View);
        bywWebSeriesLayout= layoutInflater.findViewById(R.id.bywWebSeriesLayout);
        home_bywws_list_Recycler_View = layoutInflater.findViewById(R.id.home_bywws_list_Recycler_View);
        popularMoviesLayout= layoutInflater.findViewById(R.id.popularMoviesLayout);
        trendingLayout= layoutInflater.findViewById(R.id.trendingLayout);
        home_popularMovies_list_Recycler_View = layoutInflater.findViewById(R.id.home_popularMovies_list_Recycler_View);
        home_trending_list_Recycler_View = layoutInflater.findViewById(R.id.home_trending_list_Recycler_View);
        popularWebSeriesLayout= layoutInflater.findViewById(R.id.popularWebSeriesLayout);
        home_popularWebSeries_list_Recycler_View = layoutInflater.findViewById(R.id.home_popularWebSeries_list_Recycler_View);
        liveTvLayout = layoutInflater.findViewById(R.id.LiveTV_Layout);
        continuePlaying_list_Recycler_View = layoutInflater.findViewById(R.id.continuePlaying_list_Recycler_View);
        resume_Layout= layoutInflater.findViewById(R.id.resume_Layout);
        clearContinuePlaying = layoutInflater.findViewById(R.id.clearContinuePlaying);
        moreMovies = layoutInflater.findViewById(R.id.moreMovies);
        moreWebSeries = layoutInflater.findViewById(R.id.moreWebSeries);
        moreLiveTV = layoutInflater.findViewById(R.id.moreLiveTV);
        moreRecentMovies = layoutInflater.findViewById(R.id.moreRecentMovies);
        moreRecentSeries = layoutInflater.findViewById(R.id.moreRecentSeries);
        adViewLayout = layoutInflater.findViewById(R.id.ad_View_Layout);
        bannerViewLayout = layoutInflater.findViewById(R.id.banner_View_Layout);
        ad_container = layoutInflater.findViewById(R.id.ad_container);
        custom_footer_banner_ad = layoutInflater.findViewById(R.id.custom_footer_banner_ad);
        custom_banner_ad = layoutInflater.findViewById(R.id.custom_banner_ad);
        template = layoutInflater.findViewById(R.id.admob_native_template);
        homeProfile = layoutInflater.findViewById(R.id.homeProfile);
        homeTopSearchedLayout = layoutInflater.findViewById(R.id.homeTopSearchedLayout);
        home_top_searched_list_Recycler_View = layoutInflater.findViewById(R.id.home_top_searched_list_Recycler_View);
        custom_banner_video_ad = layoutInflater.findViewById(R.id.custom_banner_video_ad);
        custom_footer_banner_video_ad = layoutInflater.findViewById(R.id.custom_footer_banner_video_ad);
        bottom_floting_menu = layoutInflater.findViewById(R.id.bottom_floting_menu);
        bottom_floting_menu_movies = layoutInflater.findViewById(R.id.bottom_floting_menu_movies);
        bottom_floting_menu_web_series = layoutInflater.findViewById(R.id.bottom_floting_menu_web_series);
        bottom_floting_menu_live_tv = layoutInflater.findViewById(R.id.bottom_floting_menu_live_tv);
        appName = layoutInflater.findViewById(R.id.appName);
        appLogo = layoutInflater.findViewById(R.id.appLogo);
        networkseLayout = layoutInflater.findViewById(R.id.networkseLayout);
        networks_list_Recycler_View = layoutInflater.findViewById(R.id.networks_list_Recycler_View);
    }

    void setColorTheme(int color, View layoutInflater) {

        CardView LiveTVSmallCardPalate = layoutInflater.findViewById(R.id.LiveTVSmallCardPalate);
        LiveTVSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView continuePlayingSmallCardPalate = layoutInflater.findViewById(R.id.continuePlayingSmallCardPalate);
        continuePlayingSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView trendingSmallCardPalate = layoutInflater.findViewById(R.id.trendingSmallCardPalate);
        trendingSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView mostPopularMoviesSmallCardPalate = layoutInflater.findViewById(R.id.mostPopularMoviesSmallCardPalate);
        mostPopularMoviesSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView mostPopularWebseriesSmallCardPalate = layoutInflater.findViewById(R.id.mostPopularWebseriesSmallCardPalate);
        mostPopularWebseriesSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView moviesOnlyForYouSmallCardPalate = layoutInflater.findViewById(R.id.moviesOnlyForYouSmallCardPalate);
        moviesOnlyForYouSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView webseriesOnlyForYouSmallCardPalate = layoutInflater.findViewById(R.id.webseriesOnlyForYouSmallCardPalate);
        webseriesOnlyForYouSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView recentlyAddedMoviesSmallCardPalate = layoutInflater.findViewById(R.id.recentlyAddedMoviesSmallCardPalate);
        recentlyAddedMoviesSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView recentlyAddedWebSeriesSmallCardPalate = layoutInflater.findViewById(R.id.recentlyAddedWebSeriesSmallCardPalate);
        recentlyAddedWebSeriesSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView moviesSmallCardPalate = layoutInflater.findViewById(R.id.moviesSmallCardPalate);
        moviesSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView webSeriesSmallCardPalate = layoutInflater.findViewById(R.id.webSeriesSmallCardPalate);
        webSeriesSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView topSearchedSmallCardPalate = layoutInflater.findViewById(R.id.topSearchedSmallCardPalate);
        topSearchedSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        TextView moreLiveTVViewAll = layoutInflater.findViewById(R.id.moreLiveTVViewAll);
        moreLiveTVViewAll.setTextColor(color);

        TextView moreRecentMoviesViewAll = layoutInflater.findViewById(R.id.moreRecentMoviesViewAll);
        moreRecentMoviesViewAll.setTextColor(color);

        TextView moreRecentSeriesViewAll = layoutInflater.findViewById(R.id.moreRecentSeriesViewAll);
        moreRecentSeriesViewAll.setTextColor(color);

        TextView moreMoviesViewAll = layoutInflater.findViewById(R.id.moreMoviesViewAll);
        moreMoviesViewAll.setTextColor(color);

        TextView moreWebSeriesViewAll = layoutInflater.findViewById(R.id.moreWebSeriesViewAll);
        moreWebSeriesViewAll.setTextColor(color);

    }

    private void loadUserSubscriptionDetails() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPreferences", MODE_PRIVATE);
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


    void customImageSlider() {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.GET, AppConfig.url +"getCustomImageSlider", response -> {
            if (!response.equals("No Data Avaliable")) {
                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();

                    String title = rootObject.get("title").getAsString();
                    String banner = rootObject.get("banner").getAsString();
                    int contentType = rootObject.get("content_type").getAsInt();
                    int contentId = rootObject.get("content_id").getAsInt();
                    String url = rootObject.get("url").getAsString();
                    int status = rootObject.get("status").getAsInt();

                    if (status == 1) {
                        imageSliderItems.add(new ImageSliderItem(banner, title, contentType, contentId, url));
                    }
                }
                viewPager2.setAdapter(new ImageSliderAdepter(imageSliderItems, viewPager2));
            } else {
                viewPager2.setVisibility(View.GONE);
            }
        }, error -> {
                // Do nothing because There is No Error if error It will return 0

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

    void topMoviesImageSlider() {
        if(movieImageSliderMaxVisible > 0) {
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest sr = new StringRequest(Request.Method.GET, AppConfig.url +"getMovieImageSlider", response -> {
                if(!response.equals("No Data Avaliable")) {
                    JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                    int i = 0;
                    int maxVisible = movieImageSliderMaxVisible;
                    for (JsonElement r : jsonArray) {
                        if (i < maxVisible) {
                            JsonObject rootObject = r.getAsJsonObject();
                            int id = rootObject.get("id").getAsInt();
                            String name = rootObject.get("name").getAsString();
                            String banner = rootObject.get("poster").getAsString();
                            int status = rootObject.get("status").getAsInt();

                            if (status == 1) {
                                imageSliderItems.add(new ImageSliderItem(banner, name, 0, id, ""));
                            }
                            i++;
                        }
                    }
                    viewPager2.setVisibility(View.VISIBLE);
                    viewPager2.setAdapter(new ImageSliderAdepter(imageSliderItems, viewPager2));
                } else {
                    viewPager2.setVisibility(View.GONE);
                }
            }, error -> {
                // Do nothing because There is No Error if error It will return 0
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }
            };
            queue.add(sr);
        } else {
            viewPager2.setVisibility(View.GONE);
        }

    }

    void topWebSeriesImageSlider() {
        if(webseriesImageSliderMaxVisible > 0) {
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest sr = new StringRequest(Request.Method.GET, AppConfig.url +"getWebSeriesImageSlider", response -> {
                if(!response.equals("No Data Avaliable")) {
                    JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                    int i = 0;
                    int maxVisible = webseriesImageSliderMaxVisible;
                    for (JsonElement r : jsonArray) {
                        if (i < maxVisible) {
                            JsonObject rootObject = r.getAsJsonObject();
                            int id = rootObject.get("id").getAsInt();
                            String name = rootObject.get("name").getAsString();
                            String banner = rootObject.get("poster").getAsString();
                            int status = rootObject.get("status").getAsInt();

                            if (status == 1) {
                                imageSliderItems.add(new ImageSliderItem(banner, name, 1, id, ""));
                            }
                            i++;
                        }
                    }
                    viewPager2.setVisibility(View.VISIBLE);
                    viewPager2.setAdapter(new ImageSliderAdepter(imageSliderItems, viewPager2));
                } else {
                    viewPager2.setVisibility(View.GONE);
                }
            }, error -> {
                // Do nothing because There is No Error if error It will return 0
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }
            };
            queue.add(sr);
        } else {
            viewPager2.setVisibility(View.GONE);
        }
    }

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    void loadhomecontentlist() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.GET, AppConfig.url + "getRandMovies", response -> {
            if (!response.equals("No Data Avaliable")) {
                homeMovieLayout.setVisibility(View.VISIBLE);

                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                List<MovieList> movieList = new ArrayList<>();
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();
                    int id = rootObject.get("id").getAsInt();
                    String name = rootObject.get("name").getAsString();

                    String year = "";
                    if (!rootObject.get("release_date").getAsString().equals("")) {
                        year = helperUtils.getYearFromDate(rootObject.get("release_date").getAsString());
                    }

                    String poster = rootObject.get("poster").getAsString();
                    int type = rootObject.get("type").getAsInt();
                    int status = rootObject.get("status").getAsInt();


                    String custom_tags_name = "";
                    String custom_tag_background_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_background_color));
                    String custom_tag_text_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_text_color));
                    if(!rootObject.get("custom_tag").isJsonNull() && rootObject.get("custom_tag").isJsonObject()) {
                        JsonObject custom_tagObject = rootObject.get("custom_tag").getAsJsonObject();
                        custom_tags_name = custom_tagObject.get("custom_tags_name").getAsString();
                        custom_tag_background_color = custom_tagObject.get("background_color").getAsString();
                        custom_tag_text_color = custom_tagObject.get("text_color").getAsString();
                    }

                    if (status == 1) {
                        movieList.add(new MovieList(id, type, name, year, poster, custom_tags_name, custom_tag_background_color, custom_tag_text_color));
                    }
                }

                if (shuffleContents == 1) {
                    Collections.shuffle(movieList);
                }

                MovieListAdepter myadepter = new MovieListAdepter(context, movieList);
                movieListRecycleview.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                movieListRecycleview.setAdapter(myadepter);

            } else {
                homeMovieLayout.setVisibility(View.GONE);
                homeSwipeRefreshLayout.setRefreshing(false);
            }
        }, error -> {
            // Do nothing because There is No Error if error It will return 0
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr);

        StringRequest sr2 = new StringRequest(Request.Method.GET, AppConfig.url + "getRandWebSeries", response -> {
            if (!response.equals("No Data Avaliable")) {
                homeWebSeriesLayout.setVisibility(View.VISIBLE);

                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                List<WebSeriesList> webSeriesList = new ArrayList<>();
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();
                    int id = rootObject.get("id").getAsInt();
                    String name = rootObject.get("name").getAsString();

                    String year = "";
                    if (!rootObject.get("release_date").getAsString().equals("")) {
                        year = helperUtils.getYearFromDate(rootObject.get("release_date").getAsString());
                    }

                    String poster = rootObject.get("poster").getAsString();
                    int type = rootObject.get("type").getAsInt();
                    int status = rootObject.get("status").getAsInt();

                    String custom_tags_name = "";
                    String custom_tag_background_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_background_color));
                    String custom_tag_text_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_text_color));
                    if(!rootObject.get("custom_tag").isJsonNull() && rootObject.get("custom_tag").isJsonObject()) {
                        JsonObject custom_tagObject = rootObject.get("custom_tag").getAsJsonObject();
                        custom_tags_name = custom_tagObject.get("custom_tags_name").getAsString();
                        custom_tag_background_color = custom_tagObject.get("background_color").getAsString();
                        custom_tag_text_color = custom_tagObject.get("text_color").getAsString();
                    }

                    if (status == 1) {
                        webSeriesList.add(new WebSeriesList(id, type, name, year, poster, custom_tags_name, custom_tag_background_color, custom_tag_text_color  ));
                    }
                }
                if (shuffleContents == 1) {
                    Collections.shuffle(webSeriesList);
                }

                WebSeriesListAdepter myadepter = new WebSeriesListAdepter(context, webSeriesList);
                webSeriesListRecycleview.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                webSeriesListRecycleview.setAdapter(myadepter);

                homeSwipeRefreshLayout.setRefreshing(false);

            } else {
                homeWebSeriesLayout.setVisibility(View.GONE);
                homeSwipeRefreshLayout.setRefreshing(false);
            }
        }, error -> {
            // Do nothing because There is No Error if error It will return 0
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr2);


        ///////////////////////////////////////////////
        StringRequest sr3 = new StringRequest(Request.Method.GET, AppConfig.url + "getRecentContentList/Movies", response -> {
            if (!response.equals("No Data Avaliable")) {
                recentMoviesLayout.setVisibility(View.VISIBLE);

                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                List<MovieList> recentlyAddedMovieList = new ArrayList<>();
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();
                    int id = rootObject.get("id").getAsInt();
                    String name = rootObject.get("name").getAsString();

                    String year = "";
                    if (!rootObject.get("release_date").getAsString().equals("")) {
                        year = helperUtils.getYearFromDate(rootObject.get("release_date").getAsString());
                    }

                    String poster = rootObject.get("poster").getAsString();
                    int type = rootObject.get("type").getAsInt();
                    int status = rootObject.get("status").getAsInt();

                    String custom_tags_name = "";
                    String custom_tag_background_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_background_color));
                    String custom_tag_text_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_text_color));
                    if(!rootObject.get("custom_tag").isJsonNull() && rootObject.get("custom_tag").isJsonObject()) {
                        JsonObject custom_tagObject = rootObject.get("custom_tag").getAsJsonObject();
                        custom_tags_name = custom_tagObject.get("custom_tags_name").getAsString();
                        custom_tag_background_color = custom_tagObject.get("background_color").getAsString();
                        custom_tag_text_color = custom_tagObject.get("text_color").getAsString();
                    }

                    if (status == 1) {
                        recentlyAddedMovieList.add(new MovieList(id, type, name, year, poster, custom_tags_name, custom_tag_background_color, custom_tag_text_color));
                    }
                }

                if (shuffleContents == 1) {
                    Collections.shuffle(recentlyAddedMovieList);
                }

                MovieListAdepter myadepter = new MovieListAdepter(context, recentlyAddedMovieList);
                home_Recent_Movies_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                home_Recent_Movies_list_Recycler_View.setAdapter(myadepter);

            } else {
                recentMoviesLayout.setVisibility(View.GONE);
                homeSwipeRefreshLayout.setRefreshing(false);
            }
        }, error -> {
            // Do nothing because There is No Error if error It will return 0
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr3);


        StringRequest sr4 = new StringRequest(Request.Method.GET, AppConfig.url + "getRecentContentList/WebSeries", response -> {
            if (!response.equals("No Data Avaliable")) {
                recentWebSeriesLayout.setVisibility(View.VISIBLE);

                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                List<WebSeriesList> recentlyAddedWebSeriesList = new ArrayList<>();
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();
                    int id = rootObject.get("id").getAsInt();
                    String name = rootObject.get("name").getAsString();

                    String year = "";
                    if (!rootObject.get("release_date").getAsString().equals("")) {
                        year = helperUtils.getYearFromDate(rootObject.get("release_date").getAsString());
                    }

                    String poster = rootObject.get("poster").getAsString();
                    int type = rootObject.get("type").getAsInt();
                    int status = rootObject.get("status").getAsInt();

                    String custom_tags_name = "";
                    String custom_tag_background_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_background_color));
                    String custom_tag_text_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_text_color));
                    if(!rootObject.get("custom_tag").isJsonNull() && rootObject.get("custom_tag").isJsonObject()) {
                        JsonObject custom_tagObject = rootObject.get("custom_tag").getAsJsonObject();
                        custom_tags_name = custom_tagObject.get("custom_tags_name").getAsString();
                        custom_tag_background_color = custom_tagObject.get("background_color").getAsString();
                        custom_tag_text_color = custom_tagObject.get("text_color").getAsString();
                    }

                    if (status == 1) {
                        recentlyAddedWebSeriesList.add(new WebSeriesList(id, type, name, year, poster, custom_tags_name, custom_tag_background_color, custom_tag_text_color));
                    }
                }
                if (shuffleContents == 1) {
                    Collections.shuffle(recentlyAddedWebSeriesList);
                }

                WebSeriesListAdepter myadepter = new WebSeriesListAdepter(context, recentlyAddedWebSeriesList);
                home_Recent_Series_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                home_Recent_Series_list_Recycler_View.setAdapter(myadepter);

                homeSwipeRefreshLayout.setRefreshing(false);

            } else {
                recentWebSeriesLayout.setVisibility(View.GONE);
                homeSwipeRefreshLayout.setRefreshing(false);
            }
        }, error -> {
            // Do nothing because There is No Error if error It will return 0
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr4);

        if(liveTvVisiableInHome == 1) {
            StringRequest sr5 = new StringRequest(Request.Method.GET, AppConfig.url + "getFeaturedLiveTV", response -> {
                if (!response.equals("No Data Avaliable")) {
                    liveTvLayout.setVisibility(View.VISIBLE);
                    JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                    List<LiveTvChannelList> liveTVChannelList = new ArrayList<>();
                    for (JsonElement r : jsonArray) {
                        JsonObject rootObject = r.getAsJsonObject();
                        int id = rootObject.get("id").getAsInt();
                        String name = rootObject.get("name").getAsString();
                        String banner = rootObject.get("banner").getAsString();
                        int type = rootObject.get("type").getAsInt();
                        int status = rootObject.get("status").getAsInt();
                        String streamType = rootObject.get("stream_type").getAsString();
                        String url = rootObject.get("url").getAsString();
                        int contentType = rootObject.get("content_type").getAsInt();
                        String drm_uuid = rootObject.get("drm_uuid").isJsonNull() ? "" : rootObject.get("drm_uuid").getAsString();
                        String drm_license_uri = rootObject.get("drm_license_uri").isJsonNull() ? "" : rootObject.get("drm_license_uri").getAsString();

                        if (status == 1) {
                            liveTVChannelList.add(new LiveTvChannelList(id, name, banner, streamType, url, contentType, type, playPremium, drm_uuid, drm_license_uri));
                        }
                    }

                    if (shuffleContents == 1) {
                        Collections.shuffle(liveTVChannelList);
                    }

                    LiveTvChannelListAdepter myadepter = new LiveTvChannelListAdepter(context, liveTVChannelList);
                    homeLiveTVlistRecyclerView.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                    homeLiveTVlistRecyclerView.setAdapter(myadepter);

                } else {
                    liveTvLayout.setVisibility(View.GONE);
                    homeSwipeRefreshLayout.setRefreshing(false);
                }
            }, error -> {
                // Do nothing because There is No Error if error It will return 0
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }
            };
            queue.add(sr5);
        }


        List<LiveTvGenreList> liveTvGenreList = new ArrayList<>();
        StringRequest sr10 = new StringRequest(Request.Method.GET, AppConfig.url + "getLiveTvGenreList", response -> {
            if (!response.equals("No Data Avaliable")) {
                if (live_tv_genre_visible_in_home == 1) {
                    LivetvgenreLayout.setVisibility(View.VISIBLE);
                }
                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();
                    int id = rootObject.get("id").getAsInt();
                    String name = rootObject.get("name").getAsString();
                    int status = rootObject.get("status").getAsInt();

                    if (status == 1) {
                        liveTvGenreList.add(new LiveTvGenreList(id, name, status));
                    }
                }

                LiveTvGenreListAdepter myadepter = new LiveTvGenreListAdepter(context, liveTvGenreList);
                live_tv_genre_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                live_tv_genre_list_Recycler_View.setAdapter(myadepter);
            } else {
                LivetvgenreLayout.setVisibility(View.GONE);
            }
        }, error -> {
            // Do nothing because There is No Error if error It will return 0
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr10);

        List<GenreList> genreList = new ArrayList<>();
        StringRequest sr11 = new StringRequest(Request.Method.GET, AppConfig.url + "getFeaturedGenre", response -> {
            if (!response.equals("No Data Avaliable")) {
                if (genre_visible_in_home == 1) {
                    genreLayout.setVisibility(View.VISIBLE);
                }
                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();
                    int id = rootObject.get("id").getAsInt();
                    String name = rootObject.get("name").getAsString();
                    String icon = rootObject.get("icon").getAsString();
                    String description = rootObject.get("description").getAsString();
                    int featured = rootObject.get("featured").getAsInt();
                    int status = rootObject.get("status").getAsInt();

                    if (status == 1) {
                        genreList.add(new GenreList(id, name, icon, description, featured, status));
                    }
                }

                GenreListAdepter myadepter = new GenreListAdepter(context, genreList);
                genre_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                genre_list_Recycler_View.setAdapter(myadepter);
            } else {
                genreLayout.setVisibility(View.GONE);
            }
        }, error -> {
            // Do nothing because There is No Error if error It will return 0
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr11);


        //----------------------------------//
        String tempUserID = null;
        if (userID != 0) {
            tempUserID = String.valueOf(userID);
        } else {
            tempUserID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        /////=======================////
        StringRequest sr6 = new StringRequest(Request.Method.GET, AppConfig.url + "beacauseYouWatched/Movies/" + tempUserID + "/10", response -> {
            if (!response.equals("No Data Avaliable")) {

                bywMovieLayoutLinearLayout.setVisibility(View.VISIBLE);
                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                List<MovieList> movieList = new ArrayList<>();
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();
                    int id = rootObject.get("id").getAsInt();
                    String name = rootObject.get("name").getAsString();

                    String year = "";
                    if (!rootObject.get("release_date").getAsString().equals("")) {
                        year = helperUtils.getYearFromDate(rootObject.get("release_date").getAsString());
                    }

                    String poster = rootObject.get("poster").getAsString();
                    int type = rootObject.get("type").getAsInt();
                    int status = rootObject.get("status").getAsInt();

                    String custom_tags_name = "";
                    String custom_tag_background_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_background_color));
                    String custom_tag_text_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_text_color));
                    if(!rootObject.get("custom_tag").isJsonNull() && rootObject.get("custom_tag").isJsonObject()) {
                        JsonObject custom_tagObject = rootObject.get("custom_tag").getAsJsonObject();
                        custom_tags_name = custom_tagObject.get("custom_tags_name").getAsString();
                        custom_tag_background_color = custom_tagObject.get("background_color").getAsString();
                        custom_tag_text_color = custom_tagObject.get("text_color").getAsString();
                    }

                    if (status == 1) {
                        movieList.add(new MovieList(id, type, name, year, poster, custom_tags_name, custom_tag_background_color, custom_tag_text_color));
                    }
                }

                Collections.shuffle(movieList);

                moviesOnlyForYouListAdepter myadepter = new moviesOnlyForYouListAdepter(context, movieList);
                home_bywm_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                home_bywm_list_Recycler_View.setAdapter(myadepter);

            } else {
                bywMovieLayoutLinearLayout.setVisibility(View.GONE);
                homeSwipeRefreshLayout.setRefreshing(false);
            }
        }, error -> {
            // Do nothing because There is No Error if error It will return 0
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr6);

        StringRequest sr7 = new StringRequest(Request.Method.GET, AppConfig.url + "beacauseYouWatched/WebSeries/" + tempUserID + "/10", response -> {
            if (!response.equals("No Data Avaliable")) {

                bywWebSeriesLayout.setVisibility(View.VISIBLE);
                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                List<WebSeriesList> webSeriesList = new ArrayList<>();
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();
                    int id = rootObject.get("id").getAsInt();
                    String name = rootObject.get("name").getAsString();

                    String year = "";
                    if (!rootObject.get("release_date").getAsString().equals("")) {
                        year = helperUtils.getYearFromDate(rootObject.get("release_date").getAsString());
                    }

                    String poster = rootObject.get("poster").getAsString();
                    int type = rootObject.get("type").getAsInt();
                    int status = rootObject.get("status").getAsInt();

                    String custom_tags_name = "";
                    String custom_tag_background_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_background_color));
                    String custom_tag_text_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_text_color));
                    if(!rootObject.get("custom_tag").isJsonNull() && rootObject.get("custom_tag").isJsonObject()) {
                        JsonObject custom_tagObject = rootObject.get("custom_tag").getAsJsonObject();
                        custom_tags_name = custom_tagObject.get("custom_tags_name").getAsString();
                        custom_tag_background_color = custom_tagObject.get("background_color").getAsString();
                        custom_tag_text_color = custom_tagObject.get("text_color").getAsString();
                    }

                    if (status == 1) {
                        webSeriesList.add(new WebSeriesList(id, type, name, year, poster, custom_tags_name, custom_tag_background_color, custom_tag_text_color));
                    }
                }

                Collections.shuffle(webSeriesList);

                webSeriesOnlyForYouListAdepter myadepter = new webSeriesOnlyForYouListAdepter(context, webSeriesList);
                home_bywws_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                home_bywws_list_Recycler_View.setAdapter(myadepter);

                homeSwipeRefreshLayout.setRefreshing(false);

            } else {
                bywWebSeriesLayout.setVisibility(View.GONE);
                homeSwipeRefreshLayout.setRefreshing(false);
            }
        }, error -> {
            // Do nothing because There is No Error if error It will return 0
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr7);



        StringRequest sr8 = new StringRequest(Request.Method.GET, AppConfig.url + "getMostWatched/Movies/10", response -> {

            if (!response.equals("No Data Avaliable")) {

                popularMoviesLayout.setVisibility(View.VISIBLE);
                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                List<MovieList> movieList = new ArrayList<>();
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();
                    int id = rootObject.get("id").getAsInt();
                    String name = rootObject.get("name").getAsString();

                    String year = "";
                    if (!rootObject.get("release_date").getAsString().equals("")) {
                        year = helperUtils.getYearFromDate(rootObject.get("release_date").getAsString());
                    }

                    String poster = rootObject.get("poster").getAsString();
                    int type = rootObject.get("type").getAsInt();
                    int status = rootObject.get("status").getAsInt();

                    String custom_tags_name = "";
                    String custom_tag_background_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_background_color));
                    String custom_tag_text_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_text_color));
                    if(!rootObject.get("custom_tag").isJsonNull() && rootObject.get("custom_tag").isJsonObject()) {
                        JsonObject custom_tagObject = rootObject.get("custom_tag").getAsJsonObject();
                        custom_tags_name = custom_tagObject.get("custom_tags_name").getAsString();
                        custom_tag_background_color = custom_tagObject.get("background_color").getAsString();
                        custom_tag_text_color = custom_tagObject.get("text_color").getAsString();
                    }

                    if (status == 1) {
                        movieList.add(new MovieList(id, type, name, year, poster, custom_tags_name, custom_tag_background_color, custom_tag_text_color));
                    }
                }

                Collections.shuffle(movieList);

                moviesOnlyForYouListAdepter myadepter = new moviesOnlyForYouListAdepter(context, movieList);
                home_popularMovies_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                home_popularMovies_list_Recycler_View.setAdapter(myadepter);

                homeSwipeRefreshLayout.setRefreshing(false);

            } else {
                popularMoviesLayout.setVisibility(View.GONE);
                homeSwipeRefreshLayout.setRefreshing(false);
            }
        }, error -> {
            // Do nothing because There is No Error if error It will return 0
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr8);


        StringRequest sr9 = new StringRequest(Request.Method.GET, AppConfig.url + "getMostWatched/WebSeries/10", response -> {
            if (!response.equals("No Data Avaliable")) {

                popularWebSeriesLayout.setVisibility(View.VISIBLE);
                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                List<WebSeriesList> webSeriesList = new ArrayList<>();
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();
                    int id = rootObject.get("id").getAsInt();
                    String name = rootObject.get("name").getAsString();

                    String year = "";
                    if (!rootObject.get("release_date").getAsString().equals("")) {
                        year = helperUtils.getYearFromDate(rootObject.get("release_date").getAsString());
                    }

                    String poster = rootObject.get("poster").getAsString();
                    int type = rootObject.get("type").getAsInt();
                    int status = rootObject.get("status").getAsInt();

                    String custom_tags_name = "";
                    String custom_tag_background_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_background_color));
                    String custom_tag_text_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_text_color));
                    if(!rootObject.get("custom_tag").isJsonNull() && rootObject.get("custom_tag").isJsonObject()) {
                        JsonObject custom_tagObject = rootObject.get("custom_tag").getAsJsonObject();
                        custom_tags_name = custom_tagObject.get("custom_tags_name").getAsString();
                        custom_tag_background_color = custom_tagObject.get("background_color").getAsString();
                        custom_tag_text_color = custom_tagObject.get("text_color").getAsString();
                    }

                    if (status == 1) {
                        webSeriesList.add(new WebSeriesList(id, type, name, year, poster, custom_tags_name, custom_tag_background_color, custom_tag_text_color));
                    }
                }

                Collections.shuffle(webSeriesList);

                webSeriesOnlyForYouListAdepter myadepter = new webSeriesOnlyForYouListAdepter(context, webSeriesList);
                home_popularWebSeries_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                home_popularWebSeries_list_Recycler_View.setAdapter(myadepter);

                homeSwipeRefreshLayout.setRefreshing(false);

            } else {
                popularWebSeriesLayout.setVisibility(View.GONE);
                homeSwipeRefreshLayout.setRefreshing(false);
            }
        }, error -> {
            // Do nothing because There is No Error if error It will return 0
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr9);



        StringRequest sr12 = new StringRequest(Request.Method.GET, AppConfig.url + "getTrending", response -> {
            if (!response.equals("No Data Avaliable")) {

                trendingLayout.setVisibility(View.VISIBLE);
                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                List<TrendingList> trendingList = new ArrayList<>();
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();
                    int id = rootObject.get("id").getAsInt();
                    String poster = rootObject.get("poster").getAsString();
                    int type = rootObject.get("type").getAsInt();
                    int content_type = rootObject.get("content_type").getAsInt();

                    String custom_tags_name = "";
                    String custom_tag_background_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_background_color));
                    String custom_tag_text_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_text_color));
                    if(!rootObject.get("custom_tag").isJsonNull() && rootObject.get("custom_tag").isJsonObject()) {
                        JsonObject custom_tagObject = rootObject.get("custom_tag").getAsJsonObject();
                        custom_tags_name = custom_tagObject.get("custom_tags_name").getAsString();
                        custom_tag_background_color = custom_tagObject.get("background_color").getAsString();
                        custom_tag_text_color = custom_tagObject.get("text_color").getAsString();
                    }

                    trendingList.add(new TrendingList(id, type, content_type, poster, custom_tags_name ,custom_tag_background_color ,custom_tag_text_color));
                }

                TrendingListAdepter myadepter = new TrendingListAdepter(context, trendingList);
                home_trending_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                home_trending_list_Recycler_View.setAdapter(myadepter);

                homeSwipeRefreshLayout.setRefreshing(false);

            } else {
                trendingLayout.setVisibility(View.GONE);
                homeSwipeRefreshLayout.setRefreshing(false);
            }
        }, error -> {
            Log.d("test", error.toString());
            // Do nothing because There is No Error if error It will return 0
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr12);


        StringRequest sr13 = new StringRequest(Request.Method.GET, AppConfig.url + "getMostSearched", response -> {

            if (!response.equals("No Data Avaliable")) {

                homeTopSearchedLayout.setVisibility(View.VISIBLE);
                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                List<MostSearchedList> mostSearchedList = new ArrayList<>();
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();
                    int id = rootObject.get("id").getAsInt();
                    String name = rootObject.get("name").getAsString();

                    String year = "";
                    if (!rootObject.get("release_date").getAsString().equals("")) {
                        year = HelperUtils.getYearFromDate(rootObject.get("release_date").getAsString());
                    }

                    String poster = rootObject.get("poster").getAsString();
                    int type = rootObject.get("type").getAsInt();
                    int status = rootObject.get("status").getAsInt();
                    int content_type = rootObject.get("content_type").getAsInt();

                    String custom_tags_name = "";
                    String custom_tag_background_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_background_color));
                    String custom_tag_text_color = String.valueOf(ContextCompat.getColor(context, R.color.custom_tag_text_color));
                    if(!rootObject.get("custom_tag").isJsonNull() && rootObject.get("custom_tag").isJsonObject()) {
                        JsonObject custom_tagObject = rootObject.get("custom_tag").getAsJsonObject();
                        custom_tags_name = custom_tagObject.get("custom_tags_name").getAsString();
                        custom_tag_background_color = custom_tagObject.get("background_color").getAsString();
                        custom_tag_text_color = custom_tagObject.get("text_color").getAsString();
                    }

                    if (status == 1) {
                        mostSearchedList.add(new MostSearchedList(id, type, name, year, poster, content_type, custom_tags_name, custom_tag_background_color, custom_tag_text_color));
                    }
                }

                MostSearchedListAdepter myadepter = new MostSearchedListAdepter(context, mostSearchedList);
                home_top_searched_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                home_top_searched_list_Recycler_View.setAdapter(myadepter);

                homeSwipeRefreshLayout.setRefreshing(false);

            } else {
                homeTopSearchedLayout.setVisibility(View.GONE);
                homeSwipeRefreshLayout.setRefreshing(false);
            }
        }, error -> {
            // Do nothing because There is No Error if error It will return 0
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr13);

        StringRequest sr14 = new StringRequest(Request.Method.GET, AppConfig.url + "getNetworks", response -> {

            if (!response.equals("No Data Avaliable")) {

                networkseLayout.setVisibility(View.VISIBLE);
                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                List<NetworksList> networksList = new ArrayList<>();
                networksList.add(new NetworksList(0, "", ""));
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();
                    int id = rootObject.get("id").getAsInt();
                    String name = rootObject.get("name").getAsString();
                    String logo = rootObject.get("logo").getAsString();

                    networksList.add(new NetworksList(id, name, logo));
                }

                NetworksListAdepter myadepter = new NetworksListAdepter(context, networksList);
                networks_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                networks_list_Recycler_View.setAdapter(myadepter);

                homeSwipeRefreshLayout.setRefreshing(false);

            } else {
                networkseLayout.setVisibility(View.GONE);
                homeSwipeRefreshLayout.setRefreshing(false);
            }
        }, error -> {
            networkseLayout.setVisibility(View.GONE);
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr14);





        ResumeContentDatabase db = ResumeContentDatabase.getDbInstance(context.getApplicationContext());
        List<ResumeContent> resumeContents = db.resumeContentDao().getResumeContents();

        if (resumeContents.isEmpty()) {
            resume_Layout.setVisibility(View.GONE);
        } else {
            resume_Layout.setVisibility(View.VISIBLE);
        }

        List<ResumeContent> mData = resumeContents;
        List<ContinuePlayingList> continuePlayingList = new ArrayList<>();

        for (int i = 0; i < mData.size(); i++) {

            int id = mData.get(i).getId();
            int contentID = mData.get(i).getContent_id();

            String contentType = mData.get(i).getContent_type();

            String name = mData.get(i).getName();

            String year = "";
            if (!mData.get(i).getYear().equals("")) {
                year = HelperUtils.getYearFromDate(mData.get(i).getYear());
            }
            String poster = mData.get(i).getPoster();
            String sourceType = mData.get(i).getSource_type();
            String sourceUrl = mData.get(i).getSource_url();
            int type = mData.get(i).getType();
            long position = mData.get(i).getPosition();
            long duration = mData.get(i).getDuration();

            continuePlayingList.add(new ContinuePlayingList(id, contentID, name, year, poster, sourceType, sourceUrl, type, contentType, position, duration));

            ContinuePlayingListAdepter myadepter = new ContinuePlayingListAdepter(context, continuePlayingList);
            continuePlaying_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
            continuePlaying_list_Recycler_View.setAdapter(myadepter);

        }

    }

    private void loadAd() {

        if (adType == 1) {   //AdMob
            admobNativeadTemplateLayout.setVisibility(View.VISIBLE);
            adViewLayout.setVisibility(View.GONE);

            //Home Header Native Ad
            MobileAds.initialize(context);
            AdLoader adLoader = new AdLoader.Builder(context, AppConfig.adMobNative)
                    .forNativeAd(nativeAd -> {
                        template.setNativeAd(nativeAd);
                    })
                    .build();

            adLoader.loadAd(new AdRequest.Builder().build());

            //Home Footer Banner Ad
            AdView mAdView = new AdView(context);
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(AppConfig.adMobBanner);
            (bannerViewLayout).addView(mAdView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

        } else if (adType == 2) { // StartApp
            admobNativeadTemplateLayout.setVisibility(View.GONE);
            // Define StartApp Banner
            new BannerRequest(context)
                    .setAdFormat(BannerFormat.BANNER)
                    .load((creator, error) -> {
                        if (creator != null) {
                            View adView = creator.create(context, null);
                            adViewLayout.addView(adView);
                        } else {
                            adViewLayout.setVisibility(View.GONE);
                        }
                    });




            // Define StartApp Native
            RelativeLayout.LayoutParams mrecParameters =
                    new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            mrecParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mrecParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            Mrec startAppMrec = new Mrec((Activity) context, new BannerListener() {
                @Override
                public void onReceiveAd(View view) {

                }

                @Override
                public void onFailedToReceiveAd(View view) {

                }

                @Override
                public void onImpression(View view) {

                }

                @Override
                public void onClick(View view) {

                }
            });
            // Add to main Layout
            bannerViewLayout.addView(startAppMrec, mrecParameters);

        } else if (adType == 3) { //Facebook
            admobNativeadTemplateLayout.setVisibility(View.GONE);

            AudienceNetworkAds.initialize(context);

            //Home Header Banner Ad
            com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, AppConfig.facebook_banner_ads_placement_id, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            adViewLayout.addView(adView);
            adView.loadAd();

            //Home Footer Banner Ad
            com.facebook.ads.AdView adViewFooter = new com.facebook.ads.AdView(context, AppConfig.facebook_banner_ads_placement_id, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            bannerViewLayout.addView(adViewFooter);
            adViewFooter.loadAd();

        } else if(adType == 4) { //AdColony
            admobNativeadTemplateLayout.setVisibility(View.GONE);

            String[] AdColony_AD_UNIT_Zone_Ids = new String[] {AppConfig.AdColony_BANNER_ZONE_ID,AppConfig.AdColony_INTERSTITIAL_ZONE_ID};
            AdColony.configure(getActivity(), AppConfig.AdColony_APP_ID, AdColony_AD_UNIT_Zone_Ids);

            AdColonyAdViewListener listener = new AdColonyAdViewListener() {
                @Override
                public void onRequestFilled(AdColonyAdView adColonyAdView) {
                    adViewLayout.addView(adColonyAdView);
                }
            };
            AdColony.requestAdView(AppConfig.AdColony_BANNER_ZONE_ID, listener, AdColonyAdSize.BANNER);
        } else if(adType == 5) { //Unityads
            admobNativeadTemplateLayout.setVisibility(View.GONE);

            UnityAds.initialize (getActivity(), AppConfig.Unity_Game_ID, false);

            BannerView bannerView = new BannerView(getActivity(), AppConfig.Unity_Banner_ID, new UnityBannerSize(320, 50));
            bannerView.load();
            adViewLayout.addView(bannerView);

            BannerView bannerView1 = new BannerView(getActivity(), AppConfig.Unity_Banner_ID, new UnityBannerSize(320, 50));
            bannerView1.load();
            bannerViewLayout.addView(bannerView1);
        } else if(adType == 6) { //Custom Ads
            admobNativeadTemplateLayout.setVisibility(View.GONE);
            adViewLayout.setVisibility(View.GONE);
            bannerViewLayout.setVisibility(View.GONE);

            if(!AppConfig.Custom_Banner_url.equals("")) {
                if(AppConfig.Custom_Banner_url.toLowerCase().contains(".mp4")
                        || AppConfig.Custom_Banner_url.toLowerCase().contains(".mkv")) {

                    custom_banner_video_ad.setVisibility(View.VISIBLE);
                    custom_banner_video_ad.setUseController(false);
                    custom_banner_video_ad.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                    ExoPlayer player = new ExoPlayer.Builder(context).build();
                    custom_banner_video_ad.setPlayer(player);
                    MediaItem mediaItem = MediaItem.fromUri(AppConfig.Custom_Banner_url);
                    player.setMediaItem(mediaItem);
                    player.setVolume(0);
                    player.setRepeatMode(Player.REPEAT_MODE_ONE);
                    player.prepare();
                    player.play();
                } else {
                    custom_banner_ad.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(AppConfig.Custom_Banner_url)
                            .into(custom_banner_ad);
                }

                custom_banner_ad.setOnClickListener(view -> {
                    if(!AppConfig.Custom_Banner_click_url.equals("")) {
                        switch (AppConfig.Custom_Banner_click_url_type) {
                            case 1:
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AppConfig.Custom_Banner_click_url)));
                                break;
                            case 2:
                                Intent intent = new Intent(context, WebView.class);
                                intent.putExtra("URL", AppConfig.Custom_Banner_click_url);
                                startActivity(intent);
                                break;
                            default:
                        }
                    }
                });
            }

            if(!AppConfig.Custom_Banner_url.equals("")) {
                if(AppConfig.Custom_Banner_url.toLowerCase().contains(".mp4")
                        || AppConfig.Custom_Banner_url.toLowerCase().contains(".mkv")) {

                    custom_footer_banner_video_ad.setVisibility(View.VISIBLE);
                    custom_footer_banner_video_ad.setUseController(false);
                    custom_footer_banner_video_ad.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                    ExoPlayer player = new ExoPlayer.Builder(context).build();
                    custom_footer_banner_video_ad.setPlayer(player);
                    MediaItem mediaItem = MediaItem.fromUri(AppConfig.Custom_Banner_url);
                    player.setMediaItem(mediaItem);
                    player.setVolume(0);
                    player.setRepeatMode(Player.REPEAT_MODE_ONE);
                    player.prepare();
                    player.play();
                } else {
                    custom_footer_banner_ad.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(AppConfig.Custom_Banner_url)
                            .into(custom_footer_banner_ad);
                }


                custom_footer_banner_ad.setOnClickListener(view -> {
                    if(!AppConfig.Custom_Banner_click_url.equals("")) {
                        switch (AppConfig.Custom_Banner_click_url_type) {
                            case 1:
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AppConfig.Custom_Banner_click_url)));
                                break;
                            case 2:
                                Intent intent = new Intent(context, WebView.class);
                                intent.putExtra("URL", AppConfig.Custom_Banner_click_url);
                                startActivity(intent);
                                break;
                            default:
                        }
                    }
                });
            }
        } else if(adType == 7) { // AppLovin Ads
            admobNativeadTemplateLayout.setVisibility(View.GONE);
            AppLovinSdkInitializationConfiguration initConfig = AppLovinSdkInitializationConfiguration.builder( AppConfig.applovin_sdk_key )
                    .setMediationProvider( AppLovinMediationProvider.MAX )
                    .build();
            AppLovinSdk.getInstance( context ).initialize( initConfig, sdkConfig -> {
                MaxAdView adView = new MaxAdView( AppConfig.applovin_Banner_ID);
                adViewLayout.addView(adView);
                adView.loadAd();

                MaxAdView adView1 = new MaxAdView( AppConfig.applovin_Banner_ID);
                bannerViewLayout.addView(adView1);
                adView1.loadAd();
            });
        } else if(adType == 8) { // IronSource Ads
            IronSource.init(getActivity(), AppConfig.ironSource_app_key, IronSource.AD_UNIT.INTERSTITIAL, IronSource.AD_UNIT.REWARDED_VIDEO, IronSource.AD_UNIT.BANNER);
            IronSource.init(getActivity(), AppConfig.ironSource_app_key, new InitializationListener() {
                @Override
                public void onInitializationComplete() {
                    IronSourceBannerLayout banner = IronSource.createBanner(getActivity(), ISBannerSize.BANNER);
                    adViewLayout.addView(banner);
                    IronSource.loadBanner(banner);

                    IronSourceBannerLayout banner1 = IronSource.createBanner(getActivity(), ISBannerSize.BANNER);
                    bannerViewLayout.addView(banner1);
                    IronSource.loadBanner(banner1);
                }
            });

        } else if(adType == 9) { // Wortise Ads
            admobNativeadTemplateLayout.setVisibility(View.VISIBLE);
            adViewLayout.setVisibility(View.GONE);

            GoogleNativeAd mGoogleNativeAd = new GoogleNativeAd(
                    context, AppConfig.wortise_native, new GoogleNativeAd.Listener() {
                @Override
                public void onNativeClicked(@NonNull GoogleNativeAd googleNativeAd) {

                }

                @Override
                public void onNativeFailedToLoad(@NonNull GoogleNativeAd googleNativeAd, @NonNull AdError adError) {

                }

                @Override
                public void onNativeImpression(@NonNull GoogleNativeAd googleNativeAd) {

                }

                @Override
                public void onNativeLoaded(@NonNull GoogleNativeAd googleNativeAd, @NonNull NativeAd nativeAd) {
                    template.setNativeAd(nativeAd);
                }
            });
            mGoogleNativeAd.load();


            BannerAd mBannerAd = new BannerAd(context);
            mBannerAd.setAdSize(com.wortise.ads.AdSize.HEIGHT_50);
            mBannerAd.setAdUnitId(AppConfig.wortise_banner);
            bannerViewLayout.addView(mBannerAd);
            mBannerAd.loadAd();
        } else {
            ad_container.setVisibility(View.GONE);
            admobNativeadTemplateLayout.setVisibility(View.GONE);
            bannerViewLayout.setVisibility(View.GONE);
        }

    }

}