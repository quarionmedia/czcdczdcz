package com.dooo.android.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.dooo.android.AppConfig;
import com.dooo.android.R;
import com.google.android.material.button.MaterialButton;

public class CustomDialog extends Dialog {

    private Context context;
    private String title;
    private String message;
    private String positiveButtonText;
    private int positiveButtonIconResourceId;
    private OnClickListener positiveButtonClickListener;
    private String negativeButtonText;
    private int negativeButtonIconResourceId;
    private OnClickListener negativeButtonClickListener;
    private boolean isCancelable = true;
    private int iconResourceId;
    public static final int BUTTON_POSITIVE = 1;
    public static final int BUTTON_NEGATIVE = -1;

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_layout);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(isCancelable);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        setCancelable(isCancelable);

        CardView cardView = findViewById(R.id.cardView);
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.grayBG));

        // Set icon
        ImageView iconImageView = findViewById(R.id.iconImageView);
        if (iconResourceId != 0) {
            iconImageView.setImageResource(iconResourceId);
            iconImageView.setVisibility(View.VISIBLE);
        } else {
            iconImageView.setVisibility(View.GONE);
        }

        // Set title
        TextView titleTextView = findViewById(R.id.titleTextView);
        if (title != null) {
            titleTextView.setText(title);
        }

        // Set message
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        if (message != null) {
            descriptionTextView.setText(message);
        }

        // Set positive button
        MaterialButton positiveButton = findViewById(R.id.retryBtn);
        positiveButton.setBackgroundColor(Color.parseColor(AppConfig.primeryThemeColor));
        if (positiveButtonText != null && positiveButtonClickListener != null) {
            positiveButton.setText(positiveButtonText);
            if(positiveButtonIconResourceId != 0) {
                positiveButton.setIcon(ContextCompat.getDrawable(context, positiveButtonIconResourceId));
            }
            positiveButton.setVisibility(View.VISIBLE);
            positiveButton.setOnClickListener(view -> {
                positiveButtonClickListener.onClick(new DialogInterface() {
                    @Override
                    public void cancelDialog() {
                        cancel();
                    }

                    @Override
                    public void dismissDialog() {
                        dismiss();
                    }
                }, BUTTON_POSITIVE);
            });
        }

        // Set negative button
        MaterialButton negativeButton = findViewById(R.id.exitBtn);
        if (negativeButtonText != null && negativeButtonClickListener != null) {
            negativeButton.setText(negativeButtonText);
            if(negativeButtonIconResourceId!=0) {
                negativeButton.setIcon(ContextCompat.getDrawable(context, negativeButtonIconResourceId));
            }
            negativeButton.setVisibility(View.VISIBLE);
            negativeButton.setOnClickListener(view -> {
                negativeButtonClickListener.onClick(new DialogInterface() {
                    @Override
                    public void cancelDialog() {
                        cancel();
                    }

                    @Override
                    public void dismissDialog() {
                        dismiss();
                    }
                }, BUTTON_NEGATIVE);
            });
        }


    }

    public CustomDialog setIcon(int iconResourceId) {
        this.iconResourceId = iconResourceId;
        return this;
    }

    public CustomDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CustomDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public CustomDialog setPositiveButton(String positiveButtonText, int positiveButtonIconResourceId, OnClickListener listener) {
        this.positiveButtonText = positiveButtonText;
        this.positiveButtonIconResourceId = positiveButtonIconResourceId;
        this.positiveButtonClickListener = listener;
        return this;
    }

    public CustomDialog setPositiveButton(String positiveButtonText, OnClickListener listener) {
        this.positiveButtonText = positiveButtonText;
        this.positiveButtonClickListener = listener;
        return this;
    }

    public CustomDialog setNegativeButton(String negativeButtonText, int negativeButtonIconResourceId, OnClickListener listener) {
        this.negativeButtonText = negativeButtonText;
        this.negativeButtonIconResourceId = negativeButtonIconResourceId;
        this.negativeButtonClickListener = listener;
        return this;
    }

    public CustomDialog setNegativeButton(String negativeButtonText, OnClickListener listener) {
        this.negativeButtonText = negativeButtonText;
        this.negativeButtonClickListener = listener;
        return this;
    }

    public CustomDialog isCancelable(boolean cancelable) {
        isCancelable = cancelable;
        return this;
    }
    public CustomDialog build() {
        return this;
    }

    public void showDialog() {
        show();
    }

    public interface OnClickListener {
        void onClick(DialogInterface dialogInterface, int which);
    }
    public interface DialogInterface {
        void cancelDialog();
        void dismissDialog();
    }
}