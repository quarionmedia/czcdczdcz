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
import com.dooo.android.MovieDetails;
import com.dooo.android.R;
import com.dooo.android.WebSeriesDetails;
import com.dooo.android.list.MovieList;
import com.dooo.android.list.SearchList;
import com.dooo.android.utils.Utils;

import java.util.List;

public class SearchListAdepter extends RecyclerView.Adapter<SearchListAdepter.MyViewHolder> {
    private Context mContext;
    private List<SearchList> mData;

    Context context;

    public SearchListAdepter(Context mContext, List<SearchList> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public SearchListAdepter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(AppConfig.contentItem,parent,false);
        return new SearchListAdepter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListAdepter.MyViewHolder holder, int position) {
        holder.setTitle(mData.get(position));
        holder.setYear(mData.get(position));
        holder.setImage(mData.get(position));
        holder.IsPremium(mData.get(position));
        holder.setCustomTag(mData.get(position));

        holder.Movie_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mData.get(position).getContent_Type() == 1) {
                    Intent intent = new Intent(mContext, MovieDetails.class);
                    intent.putExtra("ID", mData.get(position).getID());
                    mContext.startActivity(intent);
                } else if(mData.get(position).getContent_Type() == 2) {
                    Intent intent = new Intent(mContext, WebSeriesDetails.class);
                    intent.putExtra("ID", mData.get(position).getID());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
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

        void IsPremium(SearchList type) {
            if(type.getType() == 1) {
                Premium_Tag.setVisibility(View.VISIBLE);
            } else {
                Premium_Tag.setVisibility(View.GONE);
            }
        }

        void setTitle(SearchList title_text) {
            Title.setText(title_text.getTitle());
        }

        void setYear(SearchList year_text) {
            Year.setText(year_text.getYear());
        }

        void setImage(SearchList Thumbnail_Image) {
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

        void setCustomTag(SearchList searchList) {
            if (!searchList.getCustom_tag().isEmpty()) {
                tag_text.setText(searchList.getCustom_tag());
                tag_card.setVisibility(View.VISIBLE);
                tag_text.setTextColor(Color.parseColor(searchList.getCustom_tag_text_color()));
                tag_card.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(searchList.getCustom_tag_background_color())));
            } else {
                tag_card.setVisibility(View.GONE);
            }
        }
    }

}
