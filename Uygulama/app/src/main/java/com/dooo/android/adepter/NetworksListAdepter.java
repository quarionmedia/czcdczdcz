package com.dooo.android.adepter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dooo.android.AppConfig;
import com.dooo.android.R;
import com.dooo.android.list.NetworksList;
import com.dooo.android.networkDetailsActivity;

import java.util.List;

public class NetworksListAdepter extends RecyclerView.Adapter<NetworksListAdepter.MyViewHolder> {
    private Context mContext;
    private List<NetworksList> mData;

    public NetworksListAdepter(Context mContext, List<NetworksList> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? R.layout.network_first_text_item : R.layout.network_item;
    }

    @NonNull
    @Override
    public NetworksListAdepter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == R.layout.network_item){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.network_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.network_first_text_item, parent, false);
        }
        return new NetworksListAdepter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NetworksListAdepter.MyViewHolder holder, int position) {
        if(position != 0) {
            holder.setImage(mData.get(position));
            holder.setTitle(mData.get(position));

            holder.network_item_LinearLayout.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, networkDetailsActivity.class);
                intent.putExtra("ID", mData.get(position).getId());
                intent.putExtra("Name", mData.get(position).getName());
                mContext.startActivity(intent);
            });

        } else { }


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView logo;
        TextView name;
        LinearLayout network_item_LinearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            logo = itemView.findViewById(R.id.logo);
            name = itemView.findViewById(R.id.name);
            network_item_LinearLayout = itemView.findViewById(R.id.network_item_LinearLayout);
        }

        void setImage(NetworksList networksList) {
            if(AppConfig.safeMode) {
                Glide.with(mContext)
                        .load(R.drawable.thumbnail_placeholder)
                        .placeholder(R.drawable.thumbnail_placeholder)
                        .into(logo);
            } else {
                Glide.with(mContext)
                        .load(networksList.getLogo())
                        .placeholder(R.drawable.thumbnail_placeholder)
                        .into(logo);
            }
        }

        void setTitle(NetworksList networksList) {
            name.setText(networksList.getName());
        }
    }
}
