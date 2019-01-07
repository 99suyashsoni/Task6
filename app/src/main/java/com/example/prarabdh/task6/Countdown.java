package com.example.prarabdh.task6;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Countdown extends Fragment
{

    ConstraintLayout motionLayout;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    ArrayList<QuestionModel> arrayList=new ArrayList<>();
    String CATEGORY;
    CountDownTimer c1,c2,c3;

    
    @SuppressLint("ValidFragment")
    public Countdown(String CATEGORY)
    {
        this.CATEGORY=CATEGORY;
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
    public void onPause()
    {
        super.onPause();
    }

    public void startCountdown()
    {
        final Animation animation=AnimationUtils.loadAnimation(getContext(),R.anim.animation_countdown_new );
        textView3.startAnimation(animation);
        final MediaPlayer mediaPlayer;
        mediaPlayer=MediaPlayer.create(getContext(),R.raw.countdown );
        mediaPlayer.seekTo(3000);
        mediaPlayer.start();
        //CountDownTimer c1=null;
        c1=new CountDownTimer(1000, 100)
        {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish()
            {
                textView3.setTextColor(getResources().getColor(R.color.DefaultBackground ));
                textView2.startAnimation(animation);
                //CountDownTimer c2=null;
                c2=new CountDownTimer(1000,100 )
                {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish()
                    {
                        textView2.setTextColor(getResources().getColor(R.color.DefaultBackground));
                        textView1.startAnimation(animation);
                        //CountDownTimer c3=null;
                        c3=new CountDownTimer(1000,100 )
                        {

                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish()
                            {

                                mediaPlayer.stop();
                                textView1.setTextColor(getResources().getColor(R.color.DefaultBackground));
                                FragmentMainQuiz fragmentMainQuiz=new FragmentMainQuiz(arrayList,CATEGORY);
                                FragmentManager fragmentManager=getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.add(R.id.homeFragment, fragmentMainQuiz);
                                fragmentTransaction.commit();
                                //supportFragmentManager.beginTransaction().replace(R.id.homeFragment, FragmentMainQuiz()).commit()

                            }
                        };
                        c3.start();

                    }
                };
                c2.start();

            }
        };
        c1.start();
    }

    @Override
    public void onStart() 
    {
        new dataDownload().execute();

        /*final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Categories").child(CATEGORY).child("Q&A");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Iterable<DataSnapshot> contactChildren = dataSnapshot.getChildren();
                for (DataSnapshot contact : contactChildren) {
                    QuestionModel questionModel = new QuestionModel(contact.child("Ques").getValue().toString(), contact.child("Option1").getValue().toString(), contact.child("Option2").getValue().toString(), contact.child("Option3").getValue().toString(), contact.child("Option4").getValue().toString(), contact.child("Answer").getValue().toString());
                    arrayList.add(questionModel);
                }

                startCountdown();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        super.onStart();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        c1.cancel();
        c2.cancel();
        c3.cancel();
        super.onDetach();
    }

    private class dataDownload extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            progressDialog=new ProgressDialog(getContext());
            progressDialog.setMessage("Please Wait while we download your questions");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Categories").child(CATEGORY).child("Q&A");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    Iterable<DataSnapshot> contactChildren = dataSnapshot.getChildren();
                    for (DataSnapshot contact : contactChildren) {
                        QuestionModel questionModel = new QuestionModel(contact.child("Ques").getValue().toString(), contact.child("Option1").getValue().toString(), contact.child("Option2").getValue().toString(), contact.child("Option3").getValue().toString(), contact.child("Option4").getValue().toString(), contact.child("Answer").getValue().toString());
                        arrayList.add(questionModel);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            progressDialog.dismiss();
            startCountdown();
            super.onPostExecute(aVoid);
        }
    }
}
