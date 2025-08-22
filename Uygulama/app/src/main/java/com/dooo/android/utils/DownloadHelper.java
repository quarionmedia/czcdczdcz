package com.dooo.android.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.dooo.android.AppConfig;
import com.dooo.android.Downloads;
import com.dooo.android.R;
import com.dooo.android.sharedpreferencesmanager.ConfigManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2core.Downloader;
import com.tonyodev.fetch2okhttp.OkHttpDownloader;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.OkHttpClient;

public class DownloadHelper {

    public static void startDownload(Context context, View view, String name, String mimeType, String url, String referer, String cookie) {

        Log.d("test", name+" "+url+" "+referer);

        int download_manager = 0;
        try {
            JSONObject configObject = ConfigManager.loadConfig(context);
            download_manager = configObject.getInt("download_manager");
        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }

        if(download_manager == 0) {
            Fetch fetch;
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            final FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(context)
                    .setDownloadConcurrentLimit(5)
                    .setHttpDownloader(new OkHttpDownloader(Downloader.FileDownloaderType.PARALLEL))
                    .setNamespace("DownloadList")
                    .build();
            fetch = Fetch.Impl.getInstance(fetchConfiguration);

            File dDirectory = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            File dFile = new File(dDirectory, "/" + context.getResources().getString(R.string.app_name) +
                    "/" + "Downloads");
            String file = dFile+"/"+name+"."+mimeType;

            com.tonyodev.fetch2.Request request = new com.tonyodev.fetch2.Request(url, file);
            request.setPriority(Priority.HIGH);
            request.setNetworkType(NetworkType.ALL);
            request.addHeader("User-Agent", WebSettings.getDefaultUserAgent(context));

            if(!referer.equals("")) {
                request.addHeader("Referer", referer);
            }
            if(!cookie.equals("")) {
                request.addHeader("Cookie", cookie);
            }


            fetch.enqueue(request, updatedRequest -> {
                //Request was successfully enqueued for download.
                Snackbar snackbar = Snackbar.make(view, "Downloading Started!", Snackbar.LENGTH_SHORT);
                snackbar.setAction("Show", v -> {
                    Intent intent = new Intent(context, Downloads.class);
                    context.startActivity(intent);
                });

                snackbar.show();
            }, error -> {
                //An error occurred enqueuing the request.
                Snackbar snackbar = Snackbar.make(view, "Downloading Failed!", Snackbar.LENGTH_SHORT);
                snackbar.setAction("Close", v -> snackbar.dismiss());
                snackbar.show();
            });
        } else if (download_manager == 1) {
            String your_apppackagename="com.dv.adm";
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = null;
            try {
                applicationInfo = packageManager.getApplicationInfo(your_apppackagename, 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (applicationInfo == null) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.missing_dependency_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                TextView subTitle = dialog.findViewById(R.id.subTitle);
                subTitle.setText("You need to install an external downloader (ADM) to be able to download contents.");
                MaterialButton cancelBtn = dialog.findViewById(R.id.cancelBtn);
                cancelBtn.setOnClickListener(v -> dialog.dismiss());
                MaterialButton installBtn = dialog.findViewById(R.id.installBtn);
                installBtn.setBackgroundColor(Color.parseColor(AppConfig.primeryThemeColor));
                installBtn.setOnClickListener(v -> context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + your_apppackagename))));

                dialog.show();
            } else {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.setPackage("com.dv.adm");
                intent.setComponent(new ComponentName("com.dv.adm","com.dv.get.AEditor"));
                intent.putExtra(Intent.EXTRA_TEXT,url);
                intent.putExtra("com.android.extra.filename",name+"."+mimeType);
                context.startActivity(intent);
            }
        } else if (download_manager == 2) {
            try {
                PackageInfo packageInfo = get1DMInstalledPackage((Activity) context);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.setPackage(packageInfo.packageName);
                intent.setComponent(new ComponentName(packageInfo.packageName,"idm.internet.download.manager.Downloader"));
                intent.putExtra("secure_uri", true);
                intent.setData(Uri.parse(url));
                intent.putExtra("extra_filename", name+"."+mimeType);
                context.startActivity(intent);
            } catch (Exception ignored) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.missing_dependency_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                TextView subTitle = dialog.findViewById(R.id.subTitle);
                subTitle.setText("You need to install an external downloader (1DM) to be able to download contents.");
                MaterialButton cancelBtn = dialog.findViewById(R.id.cancelBtn);
                cancelBtn.setOnClickListener(v -> dialog.dismiss());
                MaterialButton installBtn = dialog.findViewById(R.id.installBtn);
                installBtn.setBackgroundColor(Color.parseColor(AppConfig.primeryThemeColor));
                installBtn.setOnClickListener(v -> context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=idm.internet.download.manager"))));

                dialog.show();
            }
        }
    }
    @NonNull
    public static String getMimeType(@NonNull final Context context, @NonNull final Uri uri) {
        final ContentResolver cR = context.getContentResolver();
        final MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = mime.getExtensionFromMimeType(cR.getType(uri));
        if (type == null) {
            type = "*/*";
        }
        return type;
    }

    public static void deleteFileAndContents(@NonNull final File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                final File[] contents = file.listFiles();
                if (contents != null) {
                    for (final File content : contents) {
                        deleteFileAndContents(content);
                    }
                }
            }
            file.delete();
        }
    }

    @NonNull
    public static String getETAString(@NonNull final Context context, final long etaInMilliSeconds) {
        if (etaInMilliSeconds < 0) {
            return "";
        }
        int seconds = (int) (etaInMilliSeconds / 1000);
        long hours = seconds / 3600;
        seconds -= hours * 3600;
        long minutes = seconds / 60;
        seconds -= minutes * 60;
        if (hours > 0) {
            return context.getString(R.string.download_eta_hrs, hours, minutes, seconds);
        } else if (minutes > 0) {
            return context.getString(R.string.download_eta_min, minutes, seconds);
        } else {
            return context.getString(R.string.download_eta_sec, seconds);
        }
    }

    @NonNull
    public static String getDownloadSpeedString(@NonNull final Context context, final long downloadedBytesPerSecond) {
        if (downloadedBytesPerSecond < 0) {
            return "";
        }
        double kb = (double) downloadedBytesPerSecond / (double) 1000;
        double mb = kb / (double) 1000;
        final DecimalFormat decimalFormat = new DecimalFormat(".##");
        if (mb >= 1) {
            return context.getString(R.string.download_speed_mb, decimalFormat.format(mb));
        } else if (kb >= 1) {
            return context.getString(R.string.download_speed_kb, decimalFormat.format(kb));
        } else {
            return context.getString(R.string.download_speed_bytes, downloadedBytesPerSecond);
        }
    }

    @NonNull
    public static File createFile(String filePath) {
        final File file = new File(filePath);
        if (!file.exists()) {
            final File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static int getProgress(long downloaded, long total) {
        if (total < 1) {
            return -1;
        } else if (downloaded < 1) {
            return 0;
        } else if (downloaded >= total) {
            return 100;
        } else {
            return (int) (((double) downloaded / (double) total) * 100);
        }
    }

    public static PackageInfo get1DMInstalledPackage(@NonNull Activity activity) throws Exception {
        final String PACKAGE_NAME_1DM_PLUS = "idm.internet.download.manager.plus";
        final String PACKAGE_NAME_1DM_LITE = "idm.internet.download.manager.adm.lite";
        final String PACKAGE_NAME_1DM_NORMAL = "idm.internet.download.manager";
        PackageManager packageManager = activity.getPackageManager();

        try {
            return packageManager.getPackageInfo(PACKAGE_NAME_1DM_NORMAL, 0);
        } catch (PackageManager.NameNotFoundException ignore) {
            try {
                return packageManager.getPackageInfo(PACKAGE_NAME_1DM_PLUS, 0);
            } catch (PackageManager.NameNotFoundException ignore2) {
                try {
                    return packageManager.getPackageInfo(PACKAGE_NAME_1DM_LITE, 0);
                } catch (PackageManager.NameNotFoundException ignore3) {
                    throw new Exception("No Package Installed");
                }
            }
        }
    }
}
