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
import com.ritudey.petcare.Models.PetModel;
import com.ritudey.petcare.R;

import java.util.List;

public class PetRecyclerViewAdapter extends RecyclerView.Adapter<PetRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<PetModel> myList;

    public PetRecyclerViewAdapter(Context context, List<PetModel> myList) {
        this.context = context;
        this.myList = myList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pets_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(myList.get(position).getImg_url()).into(holder.petImg);
        holder.petName.setText(myList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView petImg;
        TextView petName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            petImg = itemView.findViewById(R.id.pet_img);
            petName =itemView.findViewById(R.id.pet_name);
        }
    }
}
