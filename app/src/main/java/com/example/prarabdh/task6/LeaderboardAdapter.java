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

    // Provide a suitable constructor (depends on the kind of dataset)
    public LeaderboardAdapter(Context context ,ArrayList datasetLeaderboard) {

//        for (int i = 0; i < (datasetPoints.size() - 1 ); i++) {
//            for (int j = 0; j < datasetPoints.size() - i - 1; j++) {
//                if (Integer.parseInt(datasetPoints.get(j).toString()) < Integer.parseInt(datasetPoints.get(j+1).toString()))
//                {
//                    String temp = datasetPoints.get(j).toString();
//                    datasetPoints.set(j,datasetPoints.get(j+1));
//                    datasetPoints.set(j+1,temp);
//
//                    temp = datasetAvatar.get(j).toString();
//                    datasetAvatar.set(j,datasetAvatar.get(j+1));
//                    datasetAvatar.set(j+1,temp);
//
//                    temp = datasetUname.get(j).toString();
//                    datasetUname.set(j,datasetUname.get(j+1));
//                    datasetUname.set(j+1,temp);
//                }
//            }
//        }


this.datasetLeaderboard=datasetLeaderboard;

//        this.datasetAvatar=datasetAvatar;
//        this.datasetPoints=datasetPoints;
//        this.datasetUname=datasetUname;
        this.context =context;

      // sort();

//                for (pass in 0 until (datasetPoints.size - 1)) {
//                    // A single pass of bubble sort
//                    for (currentPosition in 0 until (datasetPoints.size - pass - 1)) {
//                        // This is a single step
//
//                        if (datasetPoints.get(currentPosition) > datasetPoints.get(currentPosition + 1)) {
//                            var tmp = datasetPoints.get(currentPosition)
//                            datasetPoints.set(currentPosition, datasetPoints.get(currentPosition + 1))
//                            datasetPoints.set(currentPosition + 1, tmp)
//                           // mleaderboardAdapter.notifyDataSetChanged()
////                    var tmp1 = datasetUname[currentPosition]
////                    datasetUname[currentPosition] = datasetUname[currentPosition + 1]
////                    datasetUname[currentPosition + 1] = tmp1
////
////                    var tmp2 = datasetAvatar[currentPosition]
////                    datasetAvatar[currentPosition] = datasetAvatar[currentPosition + 1]
////                    datasetAvatar[currentPosition + 1] = tmp2
//                        }
//                    }
//                }
    }

    private void sort() {

//        for (int i = 0; i < (datasetPoints.size() - 1 ); i++) {
//            for (int j = 0; j < datasetPoints.size() - i - 1; j++) {
//                if (Integer.parseInt(datasetPoints.get(j).toString()) < Integer.parseInt(datasetPoints.get(j+1).toString()))
//                {
//                    String temp = datasetPoints.get(j).toString();
//                    datasetPoints.set(j,datasetPoints.get(j+1));
//                    datasetPoints.set(j+1,temp);
//
//                    temp = datasetAvatar.get(j).toString();
//                    datasetAvatar.set(j,datasetAvatar.get(j+1));
//                    datasetAvatar.set(j+1,temp);
//
//                    temp = datasetUname.get(j).toString();
//                    datasetUname.set(j,datasetUname.get(j+1));
//                    datasetUname.set(j+1,temp);
//                }
//            }
//        }
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

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return datasetLeaderboard.size();
    }


}