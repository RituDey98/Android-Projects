package com.ritudey.petcare.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ritudey.petcare.Models.CategoryModel;
import com.ritudey.petcare.R;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<CategoryModel> myList;

    public CategoryRecyclerViewAdapter(Context context, List<CategoryModel> myList) {
        this.context = context;
        this.myList = myList;
    }

    @NonNull
    @Override
    public CategoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  CategoryRecyclerViewAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(myList.get(position).getImg_url()).into(holder.categoryImg);
        holder.categoryName.setText(myList.get(position).getName());

    }

    @Override
    public int getItemCount() {


        return myList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryImg;
        TextView categoryName;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            categoryImg = itemView.findViewById(R.id.catg_img);
            categoryName =itemView.findViewById(R.id.catg_name);
        }
    }
}
