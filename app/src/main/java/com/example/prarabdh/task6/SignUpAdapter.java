package com.example.prarabdh.task6;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class SignUpAdapter extends RecyclerView.Adapter<SignUpAdapter.MyViewHolder> {

    ArrayList<String> datasetAvatar;
    //    private ArrayList datasetUname;
//    private ArrayList datasetPoints;
//    private ArrayList datasetAvatar;
    private Context context ;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public ImageButton imageButton;
        public MyViewHolder(View v) {
            super(v);

            imageButton = v.findViewById(R.id.imageButton);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SignUpAdapter(Context context ,ArrayList datasetAvatar) {


        this.datasetAvatar=datasetAvatar;
        this.context =context;


    }



    // Create new views (invoked by the layout manager)
    @Override
    public SignUpAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.signup_avatar, parent, false);

        // ...
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Glide.with(context).load(datasetAvatar.get(position)).into(holder.imageButton);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return datasetAvatar.size();
    }


}