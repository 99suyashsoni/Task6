package com.example.prarabdh.task6;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.motion.MotionLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


public class Countdown extends Fragment 
{

    MotionLayout motionLayout;
    TextView textView1;
    TextView textView2;
    TextView textView3;

    
    public Countdown() {
        // Required empty public constructor
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_countdown, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        motionLayout=view.findViewById(R.id.motionContainer);
        textView1=view.findViewById(R.id.TextViewCountdown1);
        textView2=view.findViewById(R.id.TextViewCountdown2);
        textView3=view.findViewById(R.id.TextViewCountdown3);
    }

    @Override
    public void onAttach(Context context) 
    {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onStart() 
    {
        final Animation animation=AnimationUtils.loadAnimation(getContext(),R.anim.animation_countdown_new );
        textView3.startAnimation(animation);
        final MediaPlayer mediaPlayer;
        mediaPlayer=MediaPlayer.create(getContext(),R.raw.countdown );
        mediaPlayer.seekTo(3000);
        mediaPlayer.start();
        new CountDownTimer(1000, 100)
        {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish()
            {
                textView3.setTextColor(getResources().getColor(R.color.DefaultBackground ));
                textView2.startAnimation(animation);


                new CountDownTimer(1000,100 )
                {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish()
                    {
                        textView2.setTextColor(getResources().getColor(R.color.DefaultBackground));
                        textView1.startAnimation(animation);

                        new CountDownTimer(1000,100 )
                        {

                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish()
                            {

                                mediaPlayer.stop();
                                textView1.setTextColor(getResources().getColor(R.color.DefaultBackground));
                                FragmentMainQuiz fragmentMainQuiz=new FragmentMainQuiz();
                                FragmentManager fragmentManager=getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.add(R.id.homeFragment, fragmentMainQuiz);
                                fragmentTransaction.commit();
                                //supportFragmentManager.beginTransaction().replace(R.id.homeFragment, FragmentMainQuiz()).commit()

                            }
                        }.start();

                    }
                }.start();

            }
        }.start();



        super.onStart();
    }
}
