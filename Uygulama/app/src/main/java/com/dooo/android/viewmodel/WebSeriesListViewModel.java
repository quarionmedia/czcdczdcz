package com.dooo.android.viewmodel;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dooo.android.AppConfig;
import com.dooo.android.R;
import com.dooo.android.list.WebSeriesList;
import com.dooo.android.utils.HelperUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebSeriesListViewModel extends ViewModel {
    private MutableLiveData<List<WebSeriesList>> webSeriesListLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public int currentPage = 1;
    public boolean isLastPage = false;

    public LiveData<List<WebSeriesList>> getWebSeriesListLiveData() {
        return webSeriesListLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void refreshWebSeriesList(Context context) {
        currentPage = 1;
        isLastPage = false;
        fetchWebSeriesList(context);
    }

    public void fetchWebSeriesList(Context context) {
        if (!isLastPage) {
            isLoading.setValue(true);
            String url = AppConfig.url + "getAllWebSeries/" + currentPage;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
                        isLoading.setValue(false);

                        if (!response.equals("No Data Avaliable")) {
                            JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                            List<WebSeriesList> webSeriesList = new ArrayList<>();

                            for (JsonElement r : jsonArray) {
                                JsonObject rootObject = r.getAsJsonObject();
                                int id = rootObject.get("id").getAsInt();
                                String name = rootObject.get("name").getAsString();
                                String year = "";
                                if(!rootObject.get("release_date").getAsString().equals("")) {
                                    year = HelperUtils.getYearFromDate(rootObject.get("release_date").getAsString());
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

                                webSeriesList.add(new WebSeriesList(id, type, name, year, poster, custom_tags_name, custom_tag_background_color, custom_tag_text_color));
                            }

                            webSeriesListLiveData.setValue(webSeriesList);

                            currentPage++;
                        } else {
                            isLastPage = true;
                        }
                    },
                    error -> {
                        isLoading.setValue(false);
                        // Handle error
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }
    }
}
