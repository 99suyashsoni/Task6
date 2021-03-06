package com.example.prarabdh.task6.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prarabdh.task6.R;

import java.util.ArrayList;

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> categories;
    private ArrayList<String> images;

    public AchievementsAdapter(Context ncontext, ArrayList<String> ncategories, ArrayList<String> nimages) {
        context = ncontext;
        categories = ncategories;
        images = nimages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int f) {
        View view;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unlocked_achievements, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d("binded", "called");

        Glide.with(context)
                .asBitmap()
                .load(images.get(i))
                .into(viewHolder.imageView);
        viewHolder.textView.setText(categories.get(i));
    }

    @Override
    public int getItemCount() {
        Log.d("item_count", String.valueOf(categories.size()));
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView3);
            textView = itemView.findViewById(R.id.textView11);
        }
    }

    /*class ViewHolderText extends RecyclerView.ViewHolder {

        TextView textView;
        ViewHolderText(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.TextViewDefault);
        }
    }*/

}
