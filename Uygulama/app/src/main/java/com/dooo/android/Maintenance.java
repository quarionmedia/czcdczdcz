package com.dooo.android;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.dooo.android.utils.BaseActivity;
import com.dooo.android.utils.HelperUtils;

public class Maintenance extends BaseActivity {
    private HelperUtils helperUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.Orange_Smooth));


        setContentView(R.layout.activity_maintenance);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}