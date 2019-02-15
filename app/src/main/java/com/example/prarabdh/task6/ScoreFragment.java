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
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.prarabdh.task6.dataModels.QuizData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

@SuppressLint("ValidFragment")
public class ScoreFragment extends Fragment {

    public ArrayList<String> nImages = new ArrayList<>();
    public ArrayList<String> ncategories = new ArrayList<>();
    TextView head, middle, score;
    Button replay;
    AchievementsAdapter adapter;
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
        Glide.with(Objects.requireNonNull(getContext()))
                .asBitmap()
                .load(PlayerData.udrAvatar)
                .into(avtar);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new AchievementsAdapter(getContext(), ncategories, nImages);
        recyclerView.setAdapter(adapter);
        super.onViewCreated(view, savedInstanceState);
    }

    public void onStart() {

        super.onStart();

        String previousUnlocked = PlayerData.udrAchievementsUnlocked; //Stroes the String that contains information of achivements unlocked till now
        checkAchivementsUnlocked();
        for(int i = 0; i<PlayerData.udrAchievementsUnlocked.length();i++)
        {
            if(previousUnlocked.charAt(i) == '0' && PlayerData.udrAchievementsUnlocked.charAt(i) == '1')
            {
                ncategories.add(QuizData.udrAchievements.get(i).getValue());
                nImages.add(PlayerData.udrAvatar);
            }
        }
        adapter.notifyDataSetChanged();

        score.setText(Integer.toString(FinalScore));

        replay.setOnClickListener(v -> {
            Countdown fragmentMainQuiz = new Countdown(CATEGORY);
            FragmentManager fragmentManager = getFragmentManager();
            assert fragmentManager != null;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.homeFragment, fragmentMainQuiz);
            fragmentTransaction.commit();
        });

    }


    /**
     * This function is used to check if the user has unlocked some new achivement or not
     * The function first classify the categories based on the first 3 letters
     * Then based on the first three letters, the number is then extracted from its position, and compared to the user's present stats
     * In cases where the number has lower number of digits, blank spaces are left in the achivements, which are trimmed before converting the String to Integer
     * If a new achivement is unlocked, the player stats are updated through the PlayerData.udrAchivements variable
     */

    public void checkAchivementsUnlocked()
    {
        String p;
        int j;
        for(int i = 0;i<QuizData.udrAchievements.size();i++) {
            String x = QuizData.udrAchievements.get(i).getValue();
            switch (x.substring(0, 3))
            {

                case "Win" :
                    p = x.substring(4,8).trim();
                    Log.d("ScoreFragment",p);
                    j = Integer.parseInt(p);
                    if(PlayerData.udrAchievementsUnlocked.charAt(i) == '0' && Integer.parseInt(PlayerData.udrWin) >= j)
                    {
                        Log.d("ScoreFragment","If Entered");
                        PlayerData.udrAchievementsUnlocked = PlayerData.udrAchievementsUnlocked.substring(0,i) + "1" + PlayerData.udrAchievementsUnlocked.substring(i+1);
                        Log.d("ScoreFragment",PlayerData.udrAchievementsUnlocked);
                    }
                    break;

                case "Loo" :
                    p = x.substring(6,10).trim();
                    Log.d("ScoreFragment",p);
                    j = Integer.parseInt(p);
                    if(PlayerData.udrAchievementsUnlocked.charAt(i) == '0' && Integer.parseInt(PlayerData.udrLoss) >= j)
                    {
                        Log.d("ScoreFragment","If Entered");
                        PlayerData.udrAchievementsUnlocked = PlayerData.udrAchievementsUnlocked.substring(0,i) + "1" + PlayerData.udrAchievementsUnlocked.substring(i+1);
                        Log.d("ScoreFragment",PlayerData.udrAchievementsUnlocked);
                    }
                    break;

                case "Unl" :
                    p = x.substring(7,9).trim();
                    Log.d("ScoreFragment",p);
                    j = Integer.parseInt(p);
                    @SuppressLint({"NewApi", "LocalSuppress"}) long t = PlayerData.udrCategoriesUnlocked.chars().filter(c -> c == '1').count();
                    if(PlayerData.udrAchievementsUnlocked.charAt(i) == '0' && (int)t >= j)
                    {
                        Log.d("ScoreFragment","If Entered");
                        PlayerData.udrAchievementsUnlocked = PlayerData.udrAchievementsUnlocked.substring(0,i) + "1" + PlayerData.udrAchievementsUnlocked.substring(i+1);
                        Log.d("ScoreFragment",PlayerData.udrAchievementsUnlocked);
                    }
                    break;
            }
        }

    }


}
