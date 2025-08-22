package com.dooo.android;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dooo.android.adepter.CastAdepter;
import com.dooo.android.list.CastList;
import com.dooo.android.list.YTStreamList;
import com.dooo.android.utils.BaseActivity;
import com.dooo.android.utils.Yts;
import com.dooo.android.utils.ytExtractor.VideoInfoCallback;
import com.dooo.android.utils.ytExtractor.YouTubeExtractor;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import es.dmoral.toasty.Toasty;

public class TrailerPlayer extends BaseActivity {

    private PlayerView playerView;
    private ExoPlayer simpleExoPlayer;

    static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

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

        setContentView(R.layout.activity_trailer_player);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait!");
        progressDialog.setCancelable(false);

        playerView = findViewById(R.id.player_view);

        Intent intent = getIntent();
        String trailerUrl = Objects.requireNonNull(intent.getExtras()).getString("Trailer_URL");


        progressDialog.show();
        YouTubeExtractor.getVideoInfo(trailerUrl, new VideoInfoCallback() {
            @Override
            public void onVideoUrlReceived(final List<org.schabi.newpipe.extractor.stream.VideoStream> videoStreams) {
                runOnUiThread(() -> {
                    progressDialog.hide();
                    if(videoStreams.isEmpty()) {
                        progressDialog.hide();
                        isBackPressed = true;
                        releasePlayer();
                        finish();
                    } else if(videoStreams.size() == 1) {
                        initializePlayer(videoStreams.get(0).getUrl());
                        progressDialog.hide();
                    } else {
                        CharSequence[] name = new CharSequence[videoStreams.size()];
                        for (int i = 0; i < videoStreams.size(); i++) {
                            name[i] = videoStreams.get(i).getQuality();
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(TrailerPlayer.this)
                                .setTitle("Quality!")
                                .setCancelable(false)
                                .setItems(name, (dialog, which) -> {
                                    initializePlayer(videoStreams.get(which).getUrl());
                                })
                                .setPositiveButton("Close", (dialog, which) -> {
                                    progressDialog.hide();
                                    isBackPressed = true;
                                    releasePlayer();
                                    finish();
                                });
                        progressDialog.hide();
                        builder.show();
                    }
                });
            }

            @Override
            public void onError(final Exception e) {
                runOnUiThread(() -> {
                    progressDialog.hide();
                    isBackPressed = true;
                    releasePlayer();
                    finish();
                });
            }
        });


//        progressDialog.show();
//        RequestQueue queue = Volley.newRequestQueue(this);
//        StringRequest sr = new StringRequest(Request.Method.POST, AppConfig.url+"getYoutubeStream", response -> {
//            if(!response.isEmpty()) {
//                progressDialog.hide();
//                Log.d("test", response);
//            }
//        }, error -> {
//            Log.d("test", error.toString());
//            progressDialog.hide();
//            isBackPressed = true;
//            releasePlayer();
//            finish();
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("videoURL", trailerUrl);
//                return params;
//            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("x-api-key", AppConfig.apiKey);
//                return params;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        queue.add(sr);
//        Yts.getlinks(this, trailerUrl, new Yts.VolleyCallback(){
//            @Override
//            public void onSuccess(List<YTStreamList> result) {
//                ytMultipleQualityDialog(TrailerPlayer.this, result);
//            }
//
//            @Override
//            public void onError(VolleyError error) {
//                Toasty.warning(TrailerPlayer.this, "Trailer not available.", Toasty.LENGTH_LONG, true).show();
//                isBackPressed = true;
//                releasePlayer();
//                finish();
//            }
//        });

        ImageView imgFullScr = playerView.findViewById(R.id.img_full_scr);
        imgFullScr.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                if(playerView.getResizeMode() == AspectRatioFrameLayout.RESIZE_MODE_ZOOM) {
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                    imgFullScr.setImageDrawable(getDrawable(R.drawable.ic_baseline_fullscreen_24));
                } else if(playerView.getResizeMode() == AspectRatioFrameLayout.RESIZE_MODE_FIT) {
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
                    imgFullScr.setImageDrawable(getDrawable(R.drawable.ic_baseline_fullscreen_exit_24));
                } else {
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                    imgFullScr.setImageDrawable(getDrawable(R.drawable.ic_baseline_fullscreen_24));
                }

            }
        });

        fullScreenall();
    }

    public void fullScreenall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    void ytMultipleQualityDialog(Context context, List<YTStreamList> list) {
        progressDialog.dismiss();
        Collections.reverse(list);
        CharSequence[] name = new CharSequence[list.size()];
        CharSequence[] vid = new CharSequence[list.size()];
        CharSequence[] token = new CharSequence[list.size()];
        for (int i = 0; i < list.size(); i++) {
            name[i] = list.get(i).getName();
            vid[i] = list.get(i).getVid();
            token[i] = list.get(i).getToken();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Quality!")
                .setCancelable(false)
                .setItems(name, (dialog, which) -> Yts.getStreamLinks(context, (String) token[which], (String) vid[which], new Yts.VolleyCallback2(){

                    @Override
                    public void onSuccess(String result) {
                        TrustManager[] trustAllCerts = new TrustManager[] {
                                new X509TrustManager() {
                                    public X509Certificate[] getAcceptedIssuers()
                                    {
                                        return null;
                                    }
                                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                                }
                        };
                        try {
                            SSLContext sc = SSLContext.getInstance("TLS");
                            sc.init(null, trustAllCerts, new java.security.SecureRandom());
                            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                        } catch (KeyManagementException | NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }

                        initializePlayer(result);
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Toasty.warning(TrailerPlayer.this, "Selected source is currently not available.", Toast.LENGTH_LONG, true).show();
                        isBackPressed = true;
                        releasePlayer();
                        finish();
                    }
                }))
                .setPositiveButton("Close", (dialog, which) -> {
                    isBackPressed = true;
                    releasePlayer();
                    finish();
                });
        builder.show();
    }

    void initializePlayer(String URL) {
        simpleExoPlayer=new ExoPlayer.Builder(this)
                .setSeekForwardIncrementMs(10000)
                .setSeekBackIncrementMs(10000)
                .build();

        DataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory()
                .setUserAgent(WebSettings.getDefaultUserAgent(this))
                .setKeepPostFor302Redirects(true)
                .setAllowCrossProtocolRedirects(true)
                .setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
                .setReadTimeoutMs(DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS);
        DataSource.Factory dataSourceFactory = new DefaultDataSource.Factory(this, httpDataSourceFactory);

        MediaItem mediaItem = new MediaItem.Builder()
                .setMimeType(MimeTypes.APPLICATION_MP4)
                .setUri(URL)
                .build();
        MediaSource progressiveMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaItem);



        playerView.setPlayer(simpleExoPlayer);
        playerView.setKeepScreenOn(true);
        simpleExoPlayer.prepare(progressiveMediaSource);
        simpleExoPlayer.setPlayWhenReady(true);

    }

    private void pausePlayer(){
        if(simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(false);
            simpleExoPlayer.getPlaybackState();
        }
    }
    private void startPlayer(){
        if(simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(true);
            simpleExoPlayer.getPlaybackState();
        }
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer.clearVideoSurface();
            simpleExoPlayer = null;
        }
    }

    Boolean isBackPressed = false;
    @Override
    public void onBackPressed() {
        isBackPressed = true;
        releasePlayer();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!isBackPressed) {
            pausePlayer();
        }

    }
}