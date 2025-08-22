package com.dooo.android;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
import static com.google.android.youtube.player.YouTubePlayer.PlayerStyle.DEFAULT;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.dooo.android.utils.BaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class YoutubeLivePlayer extends BaseActivity {
    Context context = this;
    YouTubePlayerFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        if(AppConfig.FLAG_SECURE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));

        View decorView = getWindow().getDecorView();
        int uiOptions =  View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_youtube_live_player);

        fragment = YouTubePlayerFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.player, fragment);
        transaction.commit();

        Intent intent = getIntent();
        String url = intent.getExtras().getString("url");
        initYoutubePlayer(url);
    }

    private void initYoutubePlayer(String url) {
        Log.d("test", "youtube_live: " + url);
        Uri uri = Uri.parse(url);
        String vID = uri.getQueryParameter("v");

        fragment.initialize("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                if (!wasRestored) {
                    youTubePlayer.setFullscreen(true);
                    youTubePlayer.setShowFullscreenButton(false);
                    youTubePlayer.setPlayerStyle(DEFAULT);
                    youTubePlayer.loadVideo(vID);
                    youTubePlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("test", String.valueOf(youTubeInitializationResult));
            }
        });

    }
}