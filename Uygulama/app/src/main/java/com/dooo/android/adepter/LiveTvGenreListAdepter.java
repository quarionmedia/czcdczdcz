package com.dooo.android.adepter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dooo.android.LiveTvGenreDetailsActivity;
import com.dooo.android.R;
import com.dooo.android.list.LiveTvGenreList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiveTvGenreListAdepter extends RecyclerView.Adapter<LiveTvGenreListAdepter.MyViewHolder> {
    private Context context;
    private List<LiveTvGenreList> genreData;

    public LiveTvGenreListAdepter(Context context, List<LiveTvGenreList> genreData) {
        this.context = context;
        this.genreData = genreData;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.live_tv_genre_item;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_tv_genre_item, parent, false);
        return new LiveTvGenreListAdepter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        if(position != genreData.size()) {
            holder.setText(genreData.get(position));
            //holder.genreItem.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(AppConfig.primeryThemeColor)));

            holder.genreItem.setOnClickListener(view->{
                Intent intent = new Intent(context, LiveTvGenreDetailsActivity.class);
                intent.putExtra("ID", genreData.get(position).getId());
                intent.putExtra("Name", genreData.get(position).getName());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return genreData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView genreItem;
        TextView genreTextView;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            genreItem = itemView.findViewById(R.id.genreItem);
            genreTextView = itemView.findViewById(R.id.genreTextView);
        }

        void setText(LiveTvGenreList text) {
            genreTextView.setText(text.getName());
        }
    }
}
