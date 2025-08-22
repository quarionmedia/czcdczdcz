package com.dooo.android.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dooo.android.AllGenre;
import com.dooo.android.AppConfig;
import com.dooo.android.Downloads;
import com.dooo.android.Favorites;
import com.dooo.android.LoginSignup;
import com.dooo.android.MovieDetails;
import com.dooo.android.PrivecyPolicy;
import com.dooo.android.R;
import com.dooo.android.Splash;
import com.dooo.android.Subscription;
import com.dooo.android.TermsAndConditions;
import com.dooo.android.UpcomingActivity;
import com.dooo.android.WebSeriesDetails;
import com.dooo.android.sharedpreferencesmanager.UserManager;
import com.dooo.android.utils.Constants;
import com.dooo.android.utils.CustomDialog;
import com.dooo.android.utils.HelperUtils;
import com.dooo.android.utils.LoadingDialog;
import com.dooo.android.utils.TinyDB;
import com.dooo.android.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.makeramen.roundedimageview.RoundedImageView;
import com.onesignal.OneSignal;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.NetworkType;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class MoreFragment extends Fragment {
    Context context;
    ConstraintLayout settingBtn;
    ConstraintLayout genre_list_btn;
    ConstraintLayout downloadBtn;
    ConstraintLayout termsCondition;
    ConstraintLayout privecyPolicy;
    ConstraintLayout languageBtn;
    ConstraintLayout report_btn;
    ConstraintLayout shareBtn;
    SwitchCompat networkSwitch, preferExtensionRenderer, softwareCodec, blockNotifications, autoPlay;
    TinyDB tinyDB;
    private Fetch fetch;
    private String userData;
    ConstraintLayout favouriteListBtn;
    String tempLanguage = "";
    ConstraintLayout sufflePlay;
    ConstraintLayout upcommingContents;
    LoadingDialog loadingAnimation;
    TextView profileName;
    TextView profileEmail;
    LinearLayout loginBtnLayout;
    MaterialButton Logout_btn;
    CardView logout;
    RoundedImageView profileImage;
    JSONObject userObject;
    View forgotPasswordLayout;
    MaterialButton editAccount;
    CardView myProfile;
    LinearLayout vipUserTag;
    ConstraintLayout profileImageLayout;
    CardView Subscription_Btn;
    ConstraintLayout requestConstrainLayout;
    LinearLayout accountItems;
    ConstraintLayout accountDetails;
    MaterialButton loginBtn;

    public MoreFragment() {
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
        View layoutInflater = inflater.inflate(R.layout.fragment_more, container, false);
        bindViews(layoutInflater);
        tinyDB = new TinyDB(context);
        fetch = Fetch.Impl.getDefaultInstance();
        loadingAnimation = new LoadingDialog(context);

        try {
            userObject = UserManager.loadUser(context);
            userData = userObject.toString();
        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }

        settingBtn.setOnClickListener(view ->{
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
            bottomSheetDialog.setContentView(R.layout.settings_dialog);

            LinearLayout DownloadStoragelinearLayout = bottomSheetDialog.findViewById(R.id.DownloadStoragelinearLayout);
            DownloadStoragelinearLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(AppConfig.primeryThemeColor)));

            LinearProgressIndicator storageIndicator = bottomSheetDialog.findViewById(R.id.storageIndicator);
            storageIndicator.setIndicatorColor(Color.parseColor(AppConfig.primeryThemeColor));

            blockNotifications = bottomSheetDialog.findViewById(R.id.blockNotifications);
            blockNotifications.setChecked(tinyDB.getBoolean(Constants.NOTIFICATIONS));
            blockNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
                tinyDB.putBoolean(Constants.NOTIFICATIONS, isChecked);
                OneSignal.disablePush(isChecked);
            });

            networkSwitch = bottomSheetDialog.findViewById(R.id.networkSwitch);
            networkSwitch.setChecked(tinyDB.getBoolean("networkSwitch"));
            networkSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                tinyDB.putBoolean("networkSwitch", isChecked);
                if (isChecked) {
                    fetch.setGlobalNetworkType(NetworkType.WIFI_ONLY);
                } else {
                    fetch.setGlobalNetworkType(NetworkType.ALL);
                }
            });

            autoPlay = bottomSheetDialog.findViewById(R.id.autoPlay);
            autoPlay.setChecked(tinyDB.getBoolean(Constants.AUTOPLAY));
            autoPlay.setOnCheckedChangeListener((buttonView, isChecked) -> {
                tinyDB.putBoolean(Constants.AUTOPLAY, isChecked);
            });

            preferExtensionRenderer = bottomSheetDialog.findViewById(R.id.preferExtensionRenderer);
            preferExtensionRenderer.setChecked(tinyDB.getBoolean(Constants.EXTENTIONS));
            preferExtensionRenderer.setOnCheckedChangeListener((buttonView, isChecked) -> {
                tinyDB.putBoolean(Constants.EXTENTIONS, isChecked);
            });

//            softwareCodec = bottomSheetDialog.findViewById(R.id.softwareCodec);
//            softwareCodec.setChecked(tinyDB.getBoolean(Constants.SOFTWARE_EXTENTIONS));
//            softwareCodec.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                tinyDB.putBoolean(Constants.SOFTWARE_EXTENTIONS, isChecked);
//            });

            long totalSize = new File(context.getFilesDir().getAbsoluteFile().toString()).getTotalSpace();
            long totalSizetotMb = totalSize / (1024 * 1024);
            storageIndicator.setMax((int) totalSizetotMb);

            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long bytesAvailable;
            if (android.os.Build.VERSION.SDK_INT >=
                    android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
            } else {
                bytesAvailable = (long)stat.getBlockSize() * (long)stat.getAvailableBlocks();
            }
            long megAvailable = bytesAvailable / (1024 * 1024);
            storageIndicator.setProgress((int) megAvailable);

            TextView totalTextView = bottomSheetDialog.findViewById(R.id.totalTextView);
            totalTextView.setText(HelperUtils.formatFileSize(totalSize));

            TextView freeTextView = bottomSheetDialog.findViewById(R.id.freeTextView);
            freeTextView.setText(HelperUtils.formatFileSize(bytesAvailable));

            bottomSheetDialog.show();
        });

        favouriteListBtn.setOnClickListener(view -> {
            Intent favoriteContentsActivity = new Intent(getActivity(), Favorites.class);
            startActivity(favoriteContentsActivity);

            /*if(userData != null) {

            } else {
                Snackbar snackbar = Snackbar.make(rootView, "Login to See Favourite Contents!", Snackbar.LENGTH_SHORT);
                snackbar.setAction("Login", v -> {
                    Intent lsIntent = new Intent(Home.this, LoginSignup.class);
                    startActivity(lsIntent);
                });
                snackbar.show();
            }*/
        });


        genre_list_btn.setOnClickListener(view->{
            startActivity(new Intent(getActivity(), AllGenre.class));
        });

        editAccount.setOnClickListener(v->{
            editAccount();
        });
        myProfile.setOnClickListener(view->{
            editAccount();
        });

        Subscription_Btn.setOnClickListener(view -> {
            if(userData != null) {
                Intent favoriteContentsActivity = new Intent(getActivity(), Subscription.class);
                startActivity(favoriteContentsActivity);
            } else {
                Snackbar snackbar = Snackbar.make(getView().getRootView(), "Login to See Subscription Details!", Snackbar.LENGTH_SHORT);
                snackbar.setAction("Login", v -> {
                    Intent lsIntent = new Intent(getActivity(), LoginSignup.class);
                    startActivity(lsIntent);
                });
                snackbar.show();
            }
        });

        downloadBtn.setOnClickListener(view -> {
            Intent downloads = new Intent(getActivity(), Downloads.class);
            startActivity(downloads);
        });

        termsCondition.setOnClickListener(view -> {
            Intent termsAndConditionsIntent = new Intent(getActivity(), TermsAndConditions.class);
            startActivity(termsAndConditionsIntent);
        });

        privecyPolicy.setOnClickListener(view -> {
            Intent privecyPolicyIntent = new Intent(getActivity(), PrivecyPolicy.class);
            startActivity(privecyPolicyIntent);
        });

        languageBtn.setOnClickListener(view->{
            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.language_change_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(true);

            ImageView Close = (ImageView) dialog.findViewById(R.id.Close);
            Close.setOnClickListener(v -> dialog.dismiss());

            TextView languageDialogHeader = dialog.findViewById(R.id.languageDialogHeader);
            TextView languageDialogSubHeader = dialog.findViewById(R.id.languageDialogSubHeader);

            CardView cardView_english = dialog.findViewById(R.id.cardView_english);
            CardView cardView_hindi = dialog.findViewById(R.id.cardView_hindi);
            CardView cardView_bengali = dialog.findViewById(R.id.cardView_bengali);
            CardView cardView_spanish = dialog.findViewById(R.id.cardView_spanish);
            CardView cardView_russian = dialog.findViewById(R.id.cardView_russian);
            CardView cardView_turkish = dialog.findViewById(R.id.cardView_turkish);
            CardView cardView_chaines = dialog.findViewById(R.id.cardView_chaines);

            LinearLayout linearlayout_english = dialog.findViewById(R.id.linearlayout_english);
            LinearLayout linearlayout_hindi = dialog.findViewById(R.id.linearlayout_hindi);
            LinearLayout linearlayout_bengali = dialog.findViewById(R.id.linearlayout_bengali);
            LinearLayout linearlayout_spanish = dialog.findViewById(R.id.linearlayout_spanish);
            LinearLayout linearlayout_russian = dialog.findViewById(R.id.linearlayout_russian);
            LinearLayout linearlayout_turkish = dialog.findViewById(R.id.linearlayout_turkish);
            LinearLayout linearlayout_chaines = dialog.findViewById(R.id.linearlayout_chaines);

            TextView textview_english = dialog.findViewById(R.id.textview_english);
            TextView textview_hindi = dialog.findViewById(R.id.textview_hindi);
            TextView textview_bengali = dialog.findViewById(R.id.textview_bengali);
            TextView textview_spanish = dialog.findViewById(R.id.textview_spanish);
            TextView textview_russian = dialog.findViewById(R.id.textview_russian);
            TextView textview_turkish = dialog.findViewById(R.id.textview_turkish);
            TextView textview_chaines = dialog.findViewById(R.id.textview_chaines);

            tinyDB = new TinyDB(context);
            String appLanguage = tinyDB.getString("appLanguage");


            if(appLanguage.equals("en") || appLanguage.equals("")) {
                linearlayout_english.setBackground(AppCompatResources.getDrawable(context, R.color.white));
                linearlayout_hindi.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_bengali.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_spanish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_russian.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_turkish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_chaines.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                textview_english.setTextColor(ContextCompat.getColor(context, R.color.Red_Smooth));
                textview_hindi.setTextColor(Color.WHITE);
                textview_bengali.setTextColor(Color.WHITE);
                textview_spanish.setTextColor(Color.WHITE);
                textview_russian.setTextColor(Color.WHITE);
                textview_turkish.setTextColor(Color.WHITE);
                textview_chaines.setTextColor(Color.WHITE);
                languageDialogHeader.setText("Choose Your \nDisplay Language");
                languageDialogSubHeader.setText("Please select one");
                tempLanguage = "en";
            }
            else if(appLanguage.equals("hi")) {
                linearlayout_english.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_hindi.setBackground(AppCompatResources.getDrawable(context, R.color.white));
                linearlayout_bengali.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_spanish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_russian.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_turkish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_chaines.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                textview_english.setTextColor(Color.WHITE);
                textview_hindi.setTextColor(ContextCompat.getColor(context, R.color.Red_Smooth));
                textview_bengali.setTextColor(Color.WHITE);
                textview_spanish.setTextColor(Color.WHITE);
                textview_russian.setTextColor(Color.WHITE);
                textview_turkish.setTextColor(Color.WHITE);
                textview_chaines.setTextColor(Color.WHITE);
                languageDialogHeader.setText("अपनी प्रदर्शन भाषा चुनें");
                languageDialogSubHeader.setText("कृपया एक का चयन करें");
                tempLanguage = "hi";
            }
            else if(appLanguage.equals("bn")) {
                linearlayout_english.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_hindi.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_bengali.setBackground(AppCompatResources.getDrawable(context, R.color.white));
                linearlayout_spanish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_russian.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_turkish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_chaines.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                textview_english.setTextColor(Color.WHITE);
                textview_hindi.setTextColor(Color.WHITE);
                textview_bengali.setTextColor(ContextCompat.getColor(context, R.color.Red_Smooth));
                textview_spanish.setTextColor(Color.WHITE);
                textview_russian.setTextColor(Color.WHITE);
                textview_turkish.setTextColor(Color.WHITE);
                textview_chaines.setTextColor(Color.WHITE);
                languageDialogHeader.setText("আপনার প্রদর্শন ভাষা \nচয়ন করুন");
                languageDialogSubHeader.setText("অনুগ্রহপূর্বক একটা নির্বাচন করুন");
                tempLanguage = "bn";
            }
            else if(appLanguage.equals("es")) {
                linearlayout_english.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_hindi.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_bengali.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_spanish.setBackground(AppCompatResources.getDrawable(context, R.color.white));
                linearlayout_russian.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_turkish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_chaines.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                textview_english.setTextColor(Color.WHITE);
                textview_hindi.setTextColor(Color.WHITE);
                textview_bengali.setTextColor(Color.WHITE);
                textview_spanish.setTextColor(ContextCompat.getColor(context, R.color.Red_Smooth));
                textview_russian.setTextColor(Color.WHITE);
                textview_turkish.setTextColor(Color.WHITE);
                textview_chaines.setTextColor(Color.WHITE);
                languageDialogHeader.setText("Elija su idioma \nde visualización");
                languageDialogSubHeader.setText("Por favor, seleccione uno");
                tempLanguage = "es";
            }
            else if(appLanguage.equals("ru")) {
                linearlayout_english.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_hindi.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_bengali.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_spanish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_russian.setBackground(AppCompatResources.getDrawable(context, R.color.white));
                linearlayout_turkish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_chaines.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                textview_english.setTextColor(Color.WHITE);
                textview_hindi.setTextColor(Color.WHITE);
                textview_bengali.setTextColor(Color.WHITE);
                textview_spanish.setTextColor(Color.WHITE);
                textview_russian.setTextColor(ContextCompat.getColor(context, R.color.Red_Smooth));
                textview_turkish.setTextColor(Color.WHITE);
                textview_chaines.setTextColor(Color.WHITE);
                languageDialogHeader.setText("Выберите язык \nотображения");
                languageDialogSubHeader.setText("Пожалуйста, выберите один");
                tempLanguage = "ru";
            }
            else if(appLanguage.equals("tr")) {
                linearlayout_english.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_hindi.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_bengali.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_spanish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_russian.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_turkish.setBackground(AppCompatResources.getDrawable(context, R.color.white));
                linearlayout_chaines.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                textview_english.setTextColor(Color.WHITE);
                textview_hindi.setTextColor(Color.WHITE);
                textview_bengali.setTextColor(Color.WHITE);
                textview_spanish.setTextColor(Color.WHITE);
                textview_russian.setTextColor(Color.WHITE);
                textview_turkish.setTextColor(ContextCompat.getColor(context, R.color.Red_Smooth));
                textview_chaines.setTextColor(Color.WHITE);
                languageDialogHeader.setText("Görüntüleme Dilinizi \nSeçin");
                languageDialogSubHeader.setText("Lütfen birini seçin");
                tempLanguage = "tr";
            }
            else if(appLanguage.equals("zh")) {
                linearlayout_english.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_hindi.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_bengali.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_spanish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_russian.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_turkish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_chaines.setBackground(AppCompatResources.getDrawable(context, R.color.white));
                textview_english.setTextColor(Color.WHITE);
                textview_hindi.setTextColor(Color.WHITE);
                textview_bengali.setTextColor(Color.WHITE);
                textview_spanish.setTextColor(Color.WHITE);
                textview_russian.setTextColor(Color.WHITE);
                textview_turkish.setTextColor(Color.WHITE);
                textview_chaines.setTextColor(ContextCompat.getColor(context, R.color.Red_Smooth));
                languageDialogHeader.setText("选择您的显示语言");
                languageDialogSubHeader.setText("请选择一项");
                tempLanguage = "zh";
            }



            cardView_english.setOnClickListener(v1->{
                linearlayout_english.setBackground(AppCompatResources.getDrawable(context, R.color.white));
                linearlayout_hindi.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_bengali.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_spanish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_russian.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_turkish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_chaines.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                textview_english.setTextColor(ContextCompat.getColor(context, R.color.Red_Smooth));
                textview_hindi.setTextColor(Color.WHITE);
                textview_bengali.setTextColor(Color.WHITE);
                textview_spanish.setTextColor(Color.WHITE);
                textview_russian.setTextColor(Color.WHITE);
                textview_turkish.setTextColor(Color.WHITE);
                textview_chaines.setTextColor(Color.WHITE);
                languageDialogHeader.setText("Choose Your \nDisplay Language");
                languageDialogSubHeader.setText("Please select one");
                tempLanguage = "en";
            });
            cardView_hindi.setOnClickListener(v2->{
                linearlayout_english.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_hindi.setBackground(AppCompatResources.getDrawable(context, R.color.white));
                linearlayout_bengali.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_spanish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_russian.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_turkish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_chaines.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                textview_english.setTextColor(Color.WHITE);
                textview_hindi.setTextColor(ContextCompat.getColor(context, R.color.Red_Smooth));
                textview_bengali.setTextColor(Color.WHITE);
                textview_spanish.setTextColor(Color.WHITE);
                textview_russian.setTextColor(Color.WHITE);
                textview_turkish.setTextColor(Color.WHITE);
                textview_chaines.setTextColor(Color.WHITE);
                languageDialogHeader.setText("अपनी प्रदर्शन भाषा चुनें");
                languageDialogSubHeader.setText("कृपया एक का चयन करें");
                tempLanguage = "hi";
            });
            cardView_bengali.setOnClickListener(v3->{
                linearlayout_english.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_hindi.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_bengali.setBackground(AppCompatResources.getDrawable(context, R.color.white));
                linearlayout_spanish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_russian.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_turkish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_chaines.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                textview_english.setTextColor(Color.WHITE);
                textview_hindi.setTextColor(Color.WHITE);
                textview_bengali.setTextColor(ContextCompat.getColor(context, R.color.Red_Smooth));
                textview_spanish.setTextColor(Color.WHITE);
                textview_russian.setTextColor(Color.WHITE);
                textview_turkish.setTextColor(Color.WHITE);
                textview_chaines.setTextColor(Color.WHITE);
                languageDialogHeader.setText("আপনার প্রদর্শন ভাষা \nচয়ন করুন");
                languageDialogSubHeader.setText("অনুগ্রহপূর্বক একটা নির্বাচন করুন");
                tempLanguage = "bn";
            });
            cardView_spanish.setOnClickListener(v4->{
                linearlayout_english.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_hindi.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_bengali.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_spanish.setBackground(AppCompatResources.getDrawable(context, R.color.white));
                linearlayout_russian.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_turkish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_chaines.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                textview_english.setTextColor(Color.WHITE);
                textview_hindi.setTextColor(Color.WHITE);
                textview_bengali.setTextColor(Color.WHITE);
                textview_spanish.setTextColor(ContextCompat.getColor(context, R.color.Red_Smooth));
                textview_russian.setTextColor(Color.WHITE);
                textview_turkish.setTextColor(Color.WHITE);
                textview_chaines.setTextColor(Color.WHITE);
                languageDialogHeader.setText("Elija su idioma \nde visualización");
                languageDialogSubHeader.setText("Por favor, seleccione uno");
                tempLanguage = "es";
            });
            cardView_russian.setOnClickListener(v5->{
                linearlayout_english.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_hindi.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_bengali.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_spanish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_russian.setBackground(AppCompatResources.getDrawable(context, R.color.white));
                linearlayout_turkish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_chaines.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                textview_english.setTextColor(Color.WHITE);
                textview_hindi.setTextColor(Color.WHITE);
                textview_bengali.setTextColor(Color.WHITE);
                textview_spanish.setTextColor(Color.WHITE);
                textview_russian.setTextColor(ContextCompat.getColor(context, R.color.Red_Smooth));
                textview_turkish.setTextColor(Color.WHITE);
                textview_chaines.setTextColor(Color.WHITE);
                languageDialogHeader.setText("Выберите язык \nотображения");
                languageDialogSubHeader.setText("Пожалуйста, выберите один");
                tempLanguage = "ru";
            });
            cardView_turkish.setOnClickListener(v6->{
                linearlayout_english.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_hindi.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_bengali.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_spanish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_russian.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_turkish.setBackground(AppCompatResources.getDrawable(context, R.color.white));
                linearlayout_chaines.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                textview_english.setTextColor(Color.WHITE);
                textview_hindi.setTextColor(Color.WHITE);
                textview_bengali.setTextColor(Color.WHITE);
                textview_spanish.setTextColor(Color.WHITE);
                textview_russian.setTextColor(Color.WHITE);
                textview_turkish.setTextColor(ContextCompat.getColor(context, R.color.Red_Smooth));
                textview_chaines.setTextColor(Color.WHITE);
                languageDialogHeader.setText("Görüntüleme Dilinizi \nSeçin");
                languageDialogSubHeader.setText("Lütfen birini seçin");
                tempLanguage = "tr";
            });
            cardView_chaines.setOnClickListener(v7->{
                linearlayout_english.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_hindi.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_bengali.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_spanish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_russian.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_turkish.setBackground(AppCompatResources.getDrawable(context, R.drawable.language_dialog_bg));
                linearlayout_chaines.setBackground(AppCompatResources.getDrawable(context, R.color.white));
                textview_english.setTextColor(Color.WHITE);
                textview_hindi.setTextColor(Color.WHITE);
                textview_bengali.setTextColor(Color.WHITE);
                textview_spanish.setTextColor(Color.WHITE);
                textview_russian.setTextColor(Color.WHITE);
                textview_turkish.setTextColor(Color.WHITE);
                textview_chaines.setTextColor(ContextCompat.getColor(context, R.color.Red_Smooth));
                languageDialogHeader.setText("选择您的显示语言");
                languageDialogSubHeader.setText("请选择一项");
                tempLanguage = "zh";
            });
            ImageView Coupan_Dialog_save = dialog.findViewById(R.id.Coupan_Dialog_save);
            Coupan_Dialog_save.setOnClickListener(vSave->{
                if(tempLanguage.equals("en")) {
                    tinyDB.putString("appLanguage", "en");
                } else if(tempLanguage.equals("hi")) {
                    tinyDB.putString("appLanguage", "hi");
                }
                else if(tempLanguage.equals("bn")) {
                    tinyDB.putString("appLanguage", "bn");
                }
                else if(tempLanguage.equals("es")) {
                    tinyDB.putString("appLanguage", "es");
                }
                else if(tempLanguage.equals("ru")) {
                    tinyDB.putString("appLanguage", "ru");
                } else if(tempLanguage.equals("tr")) {
                    tinyDB.putString("appLanguage", "tr");
                }
                else if(tempLanguage.equals("zh")) {
                    tinyDB.putString("appLanguage", "zh");
                }
                dialog.dismiss();
                Intent languageIntent = new Intent(getActivity(), Splash.class);
                startActivity(languageIntent);
            });


            dialog.show();
        });

        report_btn.setOnClickListener(v->{
            if(userData != null) {
                JsonObject jsonObject = new Gson().fromJson(userData, JsonObject.class);
                String cUserID = String.valueOf(jsonObject.get("ID").getAsInt());

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.custom_report_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(true);

                ImageView dialogClose = (ImageView) dialog.findViewById(R.id.Coupan_Dialog_Close);
                dialogClose.setOnClickListener(v1 -> dialog.dismiss());

                EditText titleEditText = dialog.findViewById(R.id.titleEditText);

                MaterialSpinner typeSpinner = (MaterialSpinner) dialog.findViewById(R.id.typeSpinner);
                typeSpinner.setItems("Custom", "Movie", "Web Series", "Live TV");


                EditText descriptionEditText = dialog.findViewById(R.id.descriptionEditText);

                Button submitBtnButton = dialog.findViewById(R.id.submitBtn);
                submitBtnButton.setBackgroundColor(Color.parseColor(AppConfig.primeryThemeColor));
                submitBtnButton.setOnClickListener(btnView -> {
                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest sr = new StringRequest(Request.Method.POST, AppConfig.url + "createReport", response -> {
                        if (response != "") {
                            dialog.dismiss();
                            Snackbar snackbar = Snackbar.make(getView().getRootView(), "Report Successfully Submited!", Snackbar.LENGTH_SHORT);
                            snackbar.setAction("Close", v12 -> snackbar.dismiss());
                            snackbar.show();
                        } else {
                            dialog.dismiss();
                            Snackbar snackbar = Snackbar.make(getView().getRootView(), "Error: Something went Wrong!", Snackbar.LENGTH_SHORT);
                            snackbar.setAction("Close", v13 -> snackbar.dismiss());
                            snackbar.show();
                        }
                    }, error -> {
                        // Do nothing because There is No Error if error It will return 0
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("user_id", String.valueOf(cUserID));
                            params.put("title", titleEditText.getText().toString());
                            params.put("description", descriptionEditText.getText().toString());
                            params.put("report_type", String.valueOf(typeSpinner.getSelectedIndex()));
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
                });
                dialog.show();
            } else {
                Snackbar snackbar = Snackbar.make(getView().getRootView(), "Login to Report Content!", Snackbar.LENGTH_SHORT);
                snackbar.setAction("Login", v14 -> {
                    Intent lsIntent = new Intent(getActivity(), LoginSignup.class);
                    startActivity(lsIntent);
                });
                snackbar.show();
            }
        });

        requestConstrainLayout.setOnClickListener(v->{
            if(userData != null) {
                JsonObject jsonObject = new Gson().fromJson(userData, JsonObject.class);
                String cUserID = String.valueOf(jsonObject.get("ID").getAsInt());

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.request_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(true);

                ImageView dialogClose = (ImageView) dialog.findViewById(R.id.Coupan_Dialog_Close);
                dialogClose.setOnClickListener(v1 -> dialog.dismiss());

                EditText titleEditText = dialog.findViewById(R.id.titleEditText);

                MaterialSpinner typeSpinner = (MaterialSpinner) dialog.findViewById(R.id.typeSpinner);
                typeSpinner.setItems("Custom", "Movie", "Web Series", "Live TV");


                EditText descriptionEditText = dialog.findViewById(R.id.descriptionEditText);

                Button submitBtnButton = dialog.findViewById(R.id.submitBtn);
                submitBtnButton.setBackgroundColor(Color.parseColor(AppConfig.primeryThemeColor));
                submitBtnButton.setOnClickListener(btnView -> {
                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest sr = new StringRequest(Request.Method.POST, AppConfig.url + "addRequest", response -> {
                        if (response != "") {
                            dialog.dismiss();
                            Snackbar snackbar = Snackbar.make(getView().getRootView(), "Request Successfully Submited!", Snackbar.LENGTH_SHORT);
                            snackbar.setAction("Close", v12 -> snackbar.dismiss());
                            snackbar.show();
                        } else {
                            dialog.dismiss();
                            Snackbar snackbar = Snackbar.make(getView().getRootView(), "Error: Something went Wrong!", Snackbar.LENGTH_SHORT);
                            snackbar.setAction("Close", v13 -> snackbar.dismiss());
                            snackbar.show();
                        }
                    }, error -> {
                        // Do nothing because There is No Error if error It will return 0
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("user_id", String.valueOf(cUserID));
                            params.put("title", titleEditText.getText().toString());
                            params.put("description", descriptionEditText.getText().toString());
                            params.put("type", String.valueOf(typeSpinner.getSelectedIndex()));
                            params.put("status", String.valueOf(0));
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
                });
                dialog.show();
            } else {
                Snackbar snackbar = Snackbar.make(getView().getRootView(), "Login to Report Content!", Snackbar.LENGTH_SHORT);
                snackbar.setAction("Login", v14 -> {
                    Intent lsIntent = new Intent(getActivity(), LoginSignup.class);
                    startActivity(lsIntent);
                });
                snackbar.show();
            }
        });

        Logout_btn.setOnClickListener(view -> {
            logout();
        });
        logout.setOnClickListener(view->{
            logout();
        });

        shareBtn.setOnClickListener(view -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            sharingIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_app_text));
            startActivity(Intent.createChooser(sharingIntent, "Share app via"));
        });


        sufflePlay.setOnClickListener(view->{
            loadingAnimation.animate(true);
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest sr = new StringRequest(Request.Method.GET, AppConfig.url +"sufflePlay", response -> {
                JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
                switch (jsonObject.get("content_type").getAsInt()) {
                    case 1:
                        Intent sufflePlayMovieIntent = new Intent(context, MovieDetails.class);
                        sufflePlayMovieIntent.putExtra("ID", jsonObject.get("id").getAsInt());
                        startActivity(sufflePlayMovieIntent);
                        break;
                    case 2:
                        Intent sufflePlaySeriesIntent = new Intent(context, WebSeriesDetails.class);
                        sufflePlaySeriesIntent.putExtra("ID", jsonObject.get("id").getAsInt());
                        startActivity(sufflePlaySeriesIntent);
                        break;
                }

                loadingAnimation.animate(false);

            }, error -> {
                loadingAnimation.animate(false);
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }
            };
            queue.add(sr);
        });

        upcommingContents.setOnClickListener(view->{
            Intent upcomingActivity =  new Intent(context, UpcomingActivity.class);
            startActivity(upcomingActivity);
        });


        JsonObject jsonObject = new Gson().fromJson(userData, JsonObject.class);
        if(userData == null) {
            accountDetails.setVisibility(View.GONE);
            accountItems.setVisibility(View.GONE);

            loginBtnLayout.setVisibility(View.VISIBLE);

            loginBtn.setOnClickListener(view->{
                Intent loginSignupActivity = new Intent(getActivity(), LoginSignup.class);
                startActivity(loginSignupActivity);
            });


            Logout_btn.setVisibility(View.GONE);
        } else {
            String name = jsonObject.get("Name").getAsString();
            String email = jsonObject.get("Email").getAsString();
            int subscriptionType = jsonObject.get("subscription_type").getAsInt();

            profileName.setText(name);
            profileEmail.setText(email);

            accountDetails.setVisibility(View.VISIBLE);
            accountItems.setVisibility(View.VISIBLE);

            loginBtnLayout.setVisibility(View.GONE);

            Glide.with(context)
                    .load("https://www.gravatar.com/avatar/"+Utils.getMd5(email)+"?&d=404")
                    .placeholder(R.drawable.user)
                    .into(profileImage);

            if(subscriptionType == 0) {
                vipUserTag.setVisibility(View.GONE);
            } else {

                vipUserTag.setVisibility(View.VISIBLE);
            }

        }

        setColorTheme(Color.parseColor(AppConfig.primeryThemeColor), layoutInflater);

        return layoutInflater;
    }

    private void editAccount() {
        if(userData != null) {
            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.account_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(true);

            ImageView close = (ImageView) dialog.findViewById(R.id.Close);
            close.setOnClickListener(view -> dialog.dismiss());

            JsonObject jsonObject = new Gson().fromJson(userData, JsonObject.class);
            int userID = jsonObject.get("ID").getAsInt();
            String name = jsonObject.get("Name").getAsString();
            String email = jsonObject.get("Email").getAsString();

            TextView profileName = dialog.findViewById(R.id.profileName);
            profileName.setTextColor(Color.parseColor(AppConfig.primeryThemeColor));
            profileName.setText(name);

            EditText nameEditText = dialog.findViewById(R.id.nameEditText);
            nameEditText.setText(name);

            EditText emailEditText = dialog.findViewById(R.id.emailEditText);
            emailEditText.setText(email);

            EditText passwordEditText = dialog.findViewById(R.id.passwordEditText);

            Button saveBtn = dialog.findViewById(R.id.saveBtn);
            saveBtn.setBackgroundColor(Color.parseColor(AppConfig.primeryThemeColor));
            ProgressBar saveLoadingProgress = dialog.findViewById(R.id.saveLoadingProgress);
            saveBtn.setOnClickListener(view -> {
                if (view.getVisibility() == View.VISIBLE) {
                    view.setVisibility(View.GONE);
                    saveLoadingProgress.setVisibility(View.VISIBLE);
                }

                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest sr = new StringRequest(Request.Method.POST, AppConfig.url + "updateAccount", response -> {
                    if(response.equals("Account Updated Successfully")) {
                        Toasty.success(context, "Account Updated Successfully!", Toast.LENGTH_SHORT, true).show();
                        Intent restartIntent = new Intent(getActivity(), Splash.class);
                        startActivity(restartIntent);
                        getActivity().finish();
                    } else {
                        Toasty.warning(context, "Invalid Password!.", Toast.LENGTH_SHORT, true).show();
                    }

                    if (view.getVisibility() == View.GONE) {
                        saveLoadingProgress.setVisibility(View.GONE);
                        view.setVisibility(View.VISIBLE);
                    }
                }, error -> {
                    if (view.getVisibility() == View.GONE) {
                        saveLoadingProgress.setVisibility(View.GONE);
                        view.setVisibility(View.VISIBLE);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("UserID", String.valueOf(userID));
                        params.put("UserName", nameEditText.getText().toString());
                        params.put("Email", emailEditText.getText().toString());
                        params.put("Password", Utils.getMd5(passwordEditText.getText().toString()));
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

            });


            dialog.show();
        } else {
            Snackbar snackbar = Snackbar.make(getView().getRootView(), "You are not Logged in!", Snackbar.LENGTH_SHORT);
            snackbar.setAction("Login", view -> {
                Intent lsIntent = new Intent(getActivity(), LoginSignup.class);
                startActivity(lsIntent);
            });
            snackbar.show();
        }
    }

    private void logout() {
        CustomDialog mDialog = new CustomDialog(getActivity())
                .setTitle("Confirm Logout!")
                .setMessage("Are you sure you want to log out?")
                .isCancelable(true)
                .setIcon(R.drawable.logout)
                .setNegativeButton("Cancel", R.drawable.close, (dialogInterface, which) -> dialogInterface.dismissDialog())
                .setPositiveButton("Logout", R.drawable.out, (dialogInterface, which) -> {
                    SharedPreferences settings = context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
                    settings.edit().clear().apply();

                    Intent splashActivity = new Intent(getActivity(), Splash.class);
                    splashActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(splashActivity);
                    getActivity().finish();
                })
                .build();
        mDialog.show();
    }

    private void bindViews(View layoutInflater) {
        settingBtn = layoutInflater.findViewById(R.id.settingBtn);
        genre_list_btn = layoutInflater.findViewById(R.id.genre_list_btn);
        Subscription_Btn = layoutInflater.findViewById(R.id.Subscription_Btn);
        downloadBtn = layoutInflater.findViewById(R.id.Download_Btn);
        termsCondition = layoutInflater.findViewById(R.id.termsCondition);
        privecyPolicy = layoutInflater.findViewById(R.id.privecyPolicy);
        languageBtn = layoutInflater.findViewById(R.id.languageBtn);
        report_btn = layoutInflater.findViewById(R.id.report_btn);
        requestConstrainLayout = layoutInflater.findViewById(R.id.requestConstrainLayout);
        Logout_btn = layoutInflater.findViewById(R.id.Logout_btn);
        logout = layoutInflater.findViewById(R.id.logout);
        shareBtn = layoutInflater.findViewById(R.id.Share_btn);
        favouriteListBtn = layoutInflater.findViewById(R.id.favourite_list_btn);
        sufflePlay = layoutInflater.findViewById(R.id.sufflePlay);
        upcommingContents = layoutInflater.findViewById(R.id.upcommingContents);
        profileName = layoutInflater.findViewById(R.id.Profile_Name);
        profileEmail = layoutInflater.findViewById(R.id.Profile_Email);
        loginBtnLayout = layoutInflater.findViewById(R.id.loginBtnLayout);
        profileImage = layoutInflater.findViewById(R.id.profileImage);
        forgotPasswordLayout = layoutInflater.findViewById(R.id.Forgot_Password_Layout);
        editAccount = layoutInflater.findViewById(R.id.editAccount);
        myProfile = layoutInflater.findViewById(R.id.myProfile);
        vipUserTag = layoutInflater.findViewById(R.id.vipUserTag);
        profileImageLayout = layoutInflater.findViewById(R.id.profileImageLayout);
        accountItems = layoutInflater.findViewById(R.id.accountItems);
        accountDetails = layoutInflater.findViewById(R.id.accountDetails);
        loginBtn = layoutInflater.findViewById(R.id.loginBtn);
    }

    void setColorTheme(int color, View layoutInflater) {
        profileImage.setBorderColor(color);

        TextView profileTitleText = layoutInflater.findViewById(R.id.profileTitleText);
        profileTitleText.setTextColor(color);

        profileName.setTextColor(color);
        loginBtn.setBackgroundColor(color);

        Logout_btn.setTextColor(ColorStateList.valueOf(color));
    }
}