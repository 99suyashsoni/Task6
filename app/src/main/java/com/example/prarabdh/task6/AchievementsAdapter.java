package com.example.prarabdh.task6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.ViewHolder>{
     private Context context;
     private ArrayList<String> categories;
     private ArrayList<String> images;
     AchievementsAdapter(Context ncontext, ArrayList<String> ncategories, ArrayList<String> nimages){
         context=ncontext;
         categories=ncategories;
         images=nimages;
     }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unlocked_achievements,viewGroup,false);
         ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context)
             .asBitmap()
             .load(images.get(i))
             .into(viewHolder.imageView);
         viewHolder.textView.setText(categories.get(i));

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        CardView parent;
        ImageView imageView;
        TextView textView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.cardView);
            imageView=itemView.findViewById(R.id.imageView3);
            textView=itemView.findViewById(R.id.textView11);
        }
    }
}
