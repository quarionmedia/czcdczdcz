package com.dooo.android;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dooo.android.fragment.AllWebseriesFragment;
import com.dooo.android.utils.BaseActivity;

public class AllWebSeriesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_all_web_series);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameContainer, new AllWebseriesFragment());
        fragmentTransaction.commit();
    }
}