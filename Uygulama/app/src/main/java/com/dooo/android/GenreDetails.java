package com.dooo.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dooo.android.adepter.SearchListAdepter;
import com.dooo.android.list.SearchList;
import com.dooo.android.utils.BaseActivity;
import com.dooo.android.utils.HelperUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreDetails extends BaseActivity {
    Context context = this;
    int id;
    String name;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView genreName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_details);

        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        name = intent.getStringExtra("Name");

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        genreName = findViewById(R.id.genreName);

        genreName.setText(name);

        loadContents(name);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadContents(name);
        });

        setColorTheme(Color.parseColor(AppConfig.primeryThemeColor));
    }

    void setColorTheme(int color) {
        TextView genreName = findViewById(R.id.genreName);
        genreName.setTextColor(color);
    }

    private void loadContents(String name) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.GET, AppConfig.url +"getContentsReletedToGenre/"+name, response -> {
            if(!response.equals("No Data Avaliable")) {
                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                List<SearchList> searchList = new ArrayList<>();
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();
                    int id = rootObject.get("id").getAsInt();
                    String contentName = rootObject.get("name").getAsString();

                    String year = "";
                    if(!rootObject.get("release_date").getAsString().equals("")) {
                        year = HelperUtils.getYearFromDate(rootObject.get("release_date").getAsString());
                    }

                    String poster = rootObject.get("poster").getAsString();
                    int type = rootObject.get("type").getAsInt();
                    int status = rootObject.get("status").getAsInt();
                    int contentType = rootObject.get("content_type").getAsInt();

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
                        searchList.add(new SearchList(id, type, contentName, year, poster, contentType, custom_tags_name, custom_tag_background_color, custom_tag_text_color));
                    }
                }


                RecyclerView genreContentsRecylerView = findViewById(R.id.genreContentsRecylerView);
                SearchListAdepter myadepter = new SearchListAdepter(context, searchList);
                genreContentsRecylerView.setLayoutManager(new GridLayoutManager(context, 3));
                genreContentsRecylerView.setAdapter(myadepter);

            }
            swipeRefreshLayout.setRefreshing(false);
        }, error -> {
            swipeRefreshLayout.setRefreshing(false);
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

    @Override
    public void onBackPressed() {
        finish();
    }
}