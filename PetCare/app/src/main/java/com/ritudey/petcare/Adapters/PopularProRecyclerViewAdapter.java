package com.ritudey.petcare.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ritudey.petcare.Activities.DetailActivity;
import com.ritudey.petcare.Models.PopularProModel;
import com.ritudey.petcare.R;


import java.util.List;

public class PopularProRecyclerViewAdapter extends RecyclerView.Adapter<PopularProRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<PopularProModel> myList;

    public PopularProRecyclerViewAdapter(Context context, List<PopularProModel> myList) {
        this.context = context;
        this.myList = myList;
    }

    @NonNull
    @Override
    public PopularProRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_pro_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularProRecyclerViewAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(myList.get(position).getImg_url()).into(holder.popularProImg);
        holder.popularProName.setText(myList.get(position).getName());
        holder.popularProAmount.setText(String.valueOf(myList.get(position).getAmount()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail",myList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popularProImg;
        TextView popularProName,popularProAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popularProImg = itemView.findViewById(R.id.popular_pro_img);
            popularProName=itemView.findViewById(R.id.popular_pro_name_TV);
            popularProAmount=itemView.findViewById(R.id.popular_amount_TV);
        }
    }
}
