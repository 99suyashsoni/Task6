package com.example.prarabdh.task6;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class ScoreFragment extends Fragment {
    public ArrayList<String > nImages=new ArrayList<>();
    public ArrayList<String> ncategories=new ArrayList<>();
    TextView head,middle,score;
    Button replay;
    RecyclerView recyclerView;
    ImageView avtar;
    final String CATEGORY = "Cricket";
     public ScoreFragment(){
         //required empty public constructor
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_score, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        //Initializing all the Views so that they need not be initialized again and again in the code, hence making the code concise
        head=view.findViewById(R.id.textView8);
        middle=view.findViewById(R.id.textView10);
        score=view.findViewById(R.id.textView9);
        replay=view.findViewById(R.id.button2);
        recyclerView=view.findViewById(R.id.achieveRecycler);
        avtar=view.findViewById(R.id.imageView4);
        super.onViewCreated(view, savedInstanceState);
    }
    public void initiate(){
         AchievementsAdapter adapter=new AchievementsAdapter(getContext(),ncategories,nImages);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
    }
    @Override
    public void onAttach(final Context context)
    {
        super.onAttach(context);
    }
}
