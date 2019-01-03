package com.example.prarabdh.task6;

import android.content.Context;
import android.os.Bundle;
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
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ScoreFragment extends Fragment {
    public ArrayList<String > nImages=new ArrayList<>();
    public ArrayList<String> ncategories=new ArrayList<>();
    TextView head,middle,score;
    Button replay;
    RecyclerView recyclerView;
    private ImageView avtar;
    String FinalScore,unlock;
     public ScoreFragment(){
         Glide.with(Objects.requireNonNull(getContext()))
              .asBitmap()
              .load(PlayerData.udrAvtar)
              .into(avtar);
     }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false);
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
    public void onStart() {

        super.onStart();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Categories");
         databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> allCategory = dataSnapshot.getChildren();
                for (DataSnapshot Category : allCategory) {
                    unlock = Objects.requireNonNull(Category.child("Unlock points").getValue()).toString();
                    int val=FinalScore.compareTo(unlock);
                    int val2=unlock.compareTo(PlayerData.udrPoints);
                    if((val>=0)&&(val2>0)){
                        nImages.add(Objects.requireNonNull(Category.child("Images").getValue()).toString());
                        ncategories.add(Objects.requireNonNull(Category.getValue()).toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
         initiate();
    }

    public void initiate(){
         AchievementsAdapter adapter=new AchievementsAdapter(getContext(),ncategories,nImages);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onAttach(final Context context)
    {
        super.onAttach(context);
    }
}
