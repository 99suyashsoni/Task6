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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder> {

    ArrayList<LeaderboardDataModel> datasetLeaderboard;
//    private ArrayList datasetUname;
//    private ArrayList datasetPoints;
//    private ArrayList datasetAvatar;
    private Context context ;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textViewTotalPoints;
        public TextView textViewUname;
        public TextView textViewPosition;
        public ImageView imageViewAvatar;
        public MyViewHolder(View v) {
            super(v);
            textViewTotalPoints = v.findViewById(R.id.textViewTotalPoints);
            textViewUname = v.findViewById(R.id.textViewUname);
            textViewPosition = v.findViewById(R.id.textViewPosition);
            imageViewAvatar = v.findViewById(R.id.imageViewAvatar);
        }
    }

    public LeaderboardAdapter(Context context ,ArrayList datasetLeaderboard) {

        this.datasetLeaderboard=datasetLeaderboard;
        this.context =context;

    }



    // Create new views (invoked by the layout manager)
    @Override
    public LeaderboardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leaderboard_position, parent, false);

       // ...
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        LeaderboardDataModel model=datasetLeaderboard.get(position);
        holder.textViewPosition.setText(Integer.toString(position+1));
//        holder.textViewPosition.setText("kbhc");
       holder.textViewUname.setText(model.getUname());
       holder.textViewTotalPoints.setText(Integer.toString(model.getPoints()));
       // holder.textViewUname.setText("kx");
      Glide.with(context).load(model.getAvatar()).into(holder.imageViewAvatar);


    }

        @Override
    public int getItemCount() {
        return datasetLeaderboard.size();
    }


}