package com.dooo.android.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;

import com.dooo.android.AppConfig;
import com.dooo.android.R;
import com.dooo.android.Splash;
import com.google.android.material.button.MaterialButton;

public class NoInternetDialog {
    private Context context;
    final Dialog dialog;

    public NoInternetDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.no_internet_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        MaterialButton exitBtn = dialog.findViewById(R.id.exitBtn);
        MaterialButton retryBtn = dialog.findViewById(R.id.retryBtn);
        retryBtn.setBackgroundColor(Color.parseColor(AppConfig.primeryThemeColor));
        exitBtn.setOnClickListener(view->{
            dialog.dismiss();
            System.exit(0);
        });
        retryBtn.setOnClickListener(view->{
            dialog.dismiss();
            context.startActivity(new Intent(context, Splash.class));
            ((Activity)context).finishAffinity();
        });
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }
}
