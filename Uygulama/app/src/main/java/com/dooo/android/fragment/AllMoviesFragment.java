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
import com.dooo.android.adepter.AllMovieListAdepter;
import com.dooo.android.sharedpreferencesmanager.ConfigManager;
import com.dooo.android.utils.LoadingDialog;
import com.dooo.android.viewmodel.MovieListViewModel;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONObject;

import java.util.ArrayList;

public class AllMoviesFragment extends Fragment {
    private Context context;
    int shuffleContents;
    RecyclerView movieListRecycleview;
    View moviesShimmerLayout;
    SwipeRefreshLayout movieSwipeRefreshLayout;
    LinearLayout movieFilterTag;
    LoadingDialog loadingAnimation;
    private MovieListViewModel viewModel;
    private AllMovieListAdepter adapter;

    public AllMoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(AppConfig.FLAG_SECURE) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
        }

        viewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getMovieListLiveData().observe(this, movieList -> {
            if (movieList != null) {
                if (viewModel.currentPage == 1) {
                    adapter.setMovieList(movieList);
                    moviesShimmerLayout.setVisibility(View.GONE);
                    movieListRecycleview.setVisibility(View.VISIBLE);
                    if(movieSwipeRefreshLayout.isRefreshing()) {
                        movieSwipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    adapter.addMovieList(movieList);
                }
            }
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            loadingAnimation.animate(isLoading?true:false);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        View layoutInflater =  inflater.inflate(R.layout.fragment_all_movies, container, false);
        bindViews(layoutInflater);
        loadingAnimation = new LoadingDialog(context);
        try {
            JSONObject configObject = ConfigManager.loadConfig(context);
            shuffleContents = configObject.getInt("shuffle_contents");
        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }

        movieList();

        movieSwipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refreshMovieList(context);
        });

        movieFilterTag.setOnClickListener(view -> {
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
        movieListRecycleview = layoutInflater.findViewById(R.id.movie_list_recycleview);
        moviesShimmerLayout = layoutInflater.findViewById(R.id.Movies_Shimmer_Layout);
        movieSwipeRefreshLayout = layoutInflater.findViewById(R.id.Movie_Swipe_Refresh_Layout);
        movieFilterTag = layoutInflater.findViewById(R.id.movieFilterTag);
    }

    void setColorTheme(int color, View layoutInflater) {
        TextView moviesTitleText = layoutInflater.findViewById(R.id.moviesTitleText);
        moviesTitleText.setTextColor(color);
    }

    void movieList() {
        FlexboxLayoutManager gridLayoutManager = new FlexboxLayoutManager(requireContext());
        gridLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        movieListRecycleview.setLayoutManager(gridLayoutManager);

        adapter = new AllMovieListAdepter(requireContext(), new ArrayList<>());
        movieListRecycleview.setAdapter(adapter);

        movieListRecycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = gridLayoutManager.getChildCount();
                int totalItemCount = gridLayoutManager.getItemCount();
                int firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

                if (!viewModel.getIsLoading().getValue() && !viewModel.isLastPage) {
                    if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + 3)) {
                        viewModel.fetchMovieList(context);
                    }
                }
            }
        });

        viewModel.fetchMovieList(context);
    }
}