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
import com.ritudey.petcare.Models.NewProModel;
import com.ritudey.petcare.R;

import java.util.List;

public class NewProRecyclerViewAdapter extends RecyclerView.Adapter<NewProRecyclerViewAdapter.ViewHOlder> {

    private Context context;
    private List<NewProModel> myList;

    public NewProRecyclerViewAdapter(Context context, List<NewProModel> myList) {
        this.context = context;
        this.myList = myList;
    }

    @NonNull
    @Override
    public ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHOlder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_product_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHOlder holder, int position) {

        Glide.with(context).load(myList.get(position).getImg_url()).into(holder.newProImage);
        holder.newProName.setText(myList.get(position).getName());
        holder.newProAmount.setText(String.valueOf(myList.get(position).getAmount()));

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

    public class ViewHOlder extends RecyclerView.ViewHolder {

        ImageView newProImage;
        TextView newProName,newProAmount;

        public ViewHOlder(@NonNull View itemView) {
            super(itemView);

            newProImage=itemView.findViewById(R.id.new_pro_img);
            newProName=itemView.findViewById(R.id.new_pro_name_TV);
            newProAmount=itemView.findViewById(R.id.new_amount_TV);
        }
    }
}
