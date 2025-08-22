package com.dooo.android.adepter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dooo.android.AppConfig;
import com.dooo.android.R;
import com.dooo.android.WebSeriesDetails;
import com.dooo.android.list.MovieList;
import com.dooo.android.list.WebSeriesList;
import com.dooo.android.utils.Utils;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;

public class AllWebSeriesListAdepter extends RecyclerView.Adapter<AllWebSeriesListAdepter.MyViewHolder> {
    private Context mContext;
    private List<WebSeriesList> mdata;

    Context context;

    public AllWebSeriesListAdepter(Context mContext, List<WebSeriesList> mdata) {
        this.mContext = mContext;
        this.mdata = mdata;
    }

    @Override
    public int getItemViewType(int position) {
        return AppConfig.contentItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(AppConfig.contentItem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setTitle(mdata.get(position));
        holder.setYear(mdata.get(position));
        holder.setImage(mdata.get(position));
        holder.IsPremium(mdata.get(position));
        holder.setCustomTag(mdata.get(position));

        holder.Movie_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WebSeriesDetails.class);
                intent.putExtra("ID", mdata.get(position).getID());
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public void setWebSeriesList(List<WebSeriesList> newWebSeriesList) {
        mdata.clear();
        mdata.addAll(newWebSeriesList);
        notifyDataSetChanged();
    }

    public void addWebSeriesList(List<WebSeriesList> additionalWebSeriesList) {
        int startPosition = mdata.size();
        mdata.addAll(additionalWebSeriesList);
        this.notifyItemRangeInserted(startPosition, additionalWebSeriesList.size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        TextView Year;
        ImageView Thumbnail;
        View Premium_Tag;
        CardView Movie_Item;
        CardView tag_card;
        TextView tag_text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = (TextView) itemView.findViewById(R.id.Movie_list_Title);
            Year = (TextView) itemView.findViewById(R.id.Movie_list_Year);
            Thumbnail = (ImageView) itemView.findViewById(R.id.Movie_Item_thumbnail);
            Premium_Tag = (View) itemView.findViewById(R.id.Premium_Tag);
            Movie_Item = itemView.findViewById(R.id.Movie_Item);
            tag_card = itemView.findViewById(R.id.tag_card);
            tag_text = itemView.findViewById(R.id.tag_text);
        }

        void IsPremium(WebSeriesList type) {
            if(AppConfig.all_series_type == 0) {
                if(type.getType() == 2) {
                    Premium_Tag.setVisibility(View.VISIBLE);
                } else {
                    Premium_Tag.setVisibility(View.GONE);
                }
            } else if(AppConfig.all_series_type == 1) {
                Premium_Tag.setVisibility(View.GONE);
            } else if(AppConfig.all_series_type == 2) {
                Premium_Tag.setVisibility(View.VISIBLE);
            }
        }

        void setTitle(WebSeriesList title_text) {
            Title.setText(title_text.getTitle());
        }

        void setYear(WebSeriesList year_text) {
            Year.setText(year_text.getYear());
        }

        void setImage(WebSeriesList Thumbnail_Image) {
            if(AppConfig.safeMode) {
                Glide.with(context)
                        .load(R.drawable.thumbnail_placeholder)
                        .placeholder(R.drawable.thumbnail_placeholder)
                        .into(Thumbnail);
            } else {
                if(AppConfig.isProxyImages) {
                    Glide.with(context)
                            .load(AppConfig.url+"/imageProxy/"+ Utils.urlEncode(Utils.toBase64(Thumbnail_Image.getThumbnail())))
                            .placeholder(R.drawable.thumbnail_placeholder)
                            .into(Thumbnail);
                } else {
                    Glide.with(context)
                            .load(Thumbnail_Image.getThumbnail())
                            .placeholder(R.drawable.thumbnail_placeholder)
                            .into(Thumbnail);
                }
            }
        }

        void setCustomTag(WebSeriesList movieList) {
            if (!movieList.getCustom_tag().isEmpty()) {
                tag_text.setText(movieList.getCustom_tag());
                tag_card.setVisibility(View.VISIBLE);
                tag_text.setTextColor(Color.parseColor(movieList.getCustom_tag_text_color()));
                tag_card.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(movieList.getCustom_tag_background_color())));
            } else {
                tag_card.setVisibility(View.GONE);
            }
        }
    }
}