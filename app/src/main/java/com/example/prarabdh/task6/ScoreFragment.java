package com.example.prarabdh.task6;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.prarabdh.task6.R;
import com.example.prarabdh.task6.adapters.AchievementsAdapter;
import com.example.prarabdh.task6.dataModels.PlayerData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

@SuppressLint("ValidFragment")
public class    ScoreFragment extends Fragment {
    public ArrayList<String> nImages = new ArrayList<>();
    public ArrayList<String> ncategories = new ArrayList<>();
    TextView head, middle, score;
    Button replay;
    RecyclerView recyclerView;
    private ImageView avtar;
    String CATEGORY = "";
    int FinalScore, unlock;

    @SuppressLint("ValidFragment")
    public ScoreFragment(int x, String y) {
        FinalScore = x;
        CATEGORY = y;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Initializing all the Views so that they need not be initialized again and again in the code, hence making the code concise
        head = view.findViewById(R.id.textView8);
        middle = view.findViewById(R.id.textView10);
        score = view.findViewById(R.id.textView9);
        replay = view.findViewById(R.id.button2);
        recyclerView = view.findViewById(R.id.achieveRecycler);
        avtar = view.findViewById(R.id.imageView4);
        super.onViewCreated(view, savedInstanceState);
    }

    public void onStart() {

        super.onStart();

        Glide.with(Objects.requireNonNull(getContext()))
                .asBitmap()
                .load(PlayerData.udrAvatar)
                .into(avtar);
        ncategories.add("NO results to show as of now");
        nImages.add(PlayerData.udrAvatar);

        final AchievementsAdapter adapter = new AchievementsAdapter(getContext(), ncategories, nImages);
        // score.setText(FinalScore);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Categories");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> allCategory = dataSnapshot.getChildren();
                int j=0;
                for (DataSnapshot Category : allCategory) {

                    Log.d("Score", "Datasnapshot Called "+allCategory.toString());

                    unlock = PlayerData.pointsToUnlock[j];
                    int val = FinalScore - unlock;
                    int val2 = unlock - PlayerData.udrPoints;
                    //String x = Category.child("Images").getValue().toString();
                    String y = Category.getKey();
                    if (val >= 0 && val2>=0 ) {
                        Log.d("Score", "If condition called Called");
                        //nImages.add(x);
                        ncategories.add(y);
                        adapter.notifyDataSetChanged();
                    }
                    j++;
                }

                if(ncategories.size()==0)
                {
                    ncategories.add("No new achivements unlocked");
                    nImages.add(PlayerData.udrAvatar);
                    adapter.notifyDataSetChanged();
                }

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Database Error", Toast.LENGTH_LONG).show();
            }
        });
        score.setText(Integer.toString(FinalScore));
        firebaseDatabase.getReference("Users").child(PlayerData.udrUserId).child("Points").setValue(Integer.toString(FinalScore));
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Score", "On Click Listener Called");
                Countdown fragmentMainQuiz = new Countdown(CATEGORY);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.homeFragment, fragmentMainQuiz);
                fragmentTransaction.commit();
            }
        });

    }


    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
    }
}
