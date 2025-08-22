package com.dooo.android.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dooo.android.AppConfig;
import com.dooo.android.R;
import com.dooo.android.adepter.AllWebSeriesListAdepter;
import com.dooo.android.sharedpreferencesmanager.ConfigManager;
import com.dooo.android.utils.LoadingDialog;
import com.dooo.android.viewmodel.WebSeriesListViewModel;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONObject;

import java.util.ArrayList;

public class AllWebseriesFragment extends Fragment {
    private Context context;
    int shuffleContents;
    RecyclerView webSeriesListRecycleview;
    View seriesShimmerLayout;
    SwipeRefreshLayout webSeriesSwipeRefreshLayout;
    LinearLayout webSeriesFilterTag;
    LoadingDialog loadingAnimation;
    private WebSeriesListViewModel viewModel;
    private AllWebSeriesListAdepter adapter;

    public AllWebseriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(AppConfig.FLAG_SECURE) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
        }

        viewModel = new ViewModelProvider(this).get(WebSeriesListViewModel.class);
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getWebSeriesListLiveData().observe(this, webSeriesList -> {
            if (webSeriesList != null) {
                if (viewModel.currentPage == 1) {
                    adapter.setWebSeriesList(webSeriesList);
                    seriesShimmerLayout.setVisibility(View.GONE);
                    webSeriesListRecycleview.setVisibility(View.VISIBLE);
                    if (webSeriesSwipeRefreshLayout.isRefreshing()) {
                        webSeriesSwipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    adapter.addWebSeriesList(webSeriesList);
                }
            }
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            loadingAnimation.animate(isLoading ? true : false);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        View layoutInflater = inflater.inflate(R.layout.fragment_all_webseries, container, false);
        bindViews(layoutInflater);
        loadingAnimation = new LoadingDialog(context);

        try {
            JSONObject configObject = ConfigManager.loadConfig(context);
            shuffleContents = configObject.getInt("shuffle_contents");
        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }

        webSeriesList();

        webSeriesSwipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refreshWebSeriesList(context);
        });

        webSeriesFilterTag.setOnClickListener(view -> {
            final Dialog dialog = new BottomSheetDialog(context);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.filter_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(true);

            ImageView dialogClose = (ImageView) dialog.findViewById(R.id.dialogClose);
            dialogClose.setOnClickListener(v -> dialog.dismiss());



            dialog.show();
        });

        setColorTheme(Color.parseColor(AppConfig.primeryThemeColor), layoutInflater);

        return layoutInflater;
    }

    private void bindViews(View layoutInflater) {
        webSeriesListRecycleview = layoutInflater.findViewById(R.id.web_series_list_recycleview);
        seriesShimmerLayout = layoutInflater.findViewById(R.id.Series_Shimmer_Layout);
        webSeriesSwipeRefreshLayout = layoutInflater.findViewById(R.id.Web_Series_Swipe_Refresh_Layout);
        webSeriesFilterTag = layoutInflater.findViewById(R.id.webSeriesFilterTag);
    }

    void setColorTheme(int color, View layoutInflater) {
        TextView seriesTitleText = layoutInflater.findViewById(R.id.seriesTitleText);
        seriesTitleText.setTextColor(color);
    }

    void webSeriesList() {
        FlexboxLayoutManager gridLayoutManager = new FlexboxLayoutManager(requireContext());
        gridLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        webSeriesListRecycleview.setLayoutManager(gridLayoutManager);

        adapter = new AllWebSeriesListAdepter(requireContext(), new ArrayList<>());
        webSeriesListRecycleview.setAdapter(adapter);

        webSeriesListRecycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = gridLayoutManager.getChildCount();
                int totalItemCount = gridLayoutManager.getItemCount();
                int firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

                if (!viewModel.getIsLoading().getValue() && !viewModel.isLastPage) {
                    if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + 3)) {
                        viewModel.fetchWebSeriesList(context);
                    }
                }
            }
        });

        viewModel.fetchWebSeriesList(context);
    }
}