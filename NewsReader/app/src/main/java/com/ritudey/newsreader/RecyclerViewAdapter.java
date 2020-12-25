package com.ritudey.newsreader;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mArticleTitles = new ArrayList<>();
    private ArrayList<String> mArticleContent = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> mArticleTitles, ArrayList<String> mArticleContent, Context mContext) {
        this.mArticleTitles = mArticleTitles;
        this.mArticleContent = mArticleContent;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.titleTV.setText(mArticleTitles.get(position));
        holder.contentTV.setText(mArticleContent.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: "+mArticleTitles.get(position));

                Toast.makeText(mContext, "clicked: "+mArticleTitles.get(position), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mArticleContent.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder{

       MaterialTextView titleTV;
       MaterialTextView contentTV;
       ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV=itemView.findViewById(R.id.title_tv);
            contentTV=itemView.findViewById(R.id.content_tv);
            parentLayout=itemView.findViewById(R.id.list_item_parent_layout);


        }
    }

}









