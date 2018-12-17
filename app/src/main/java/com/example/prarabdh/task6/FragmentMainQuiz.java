package com.example.prarabdh.task6;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.*;

import java.util.ArrayList;


public class FragmentMainQuiz extends Fragment
{

    final int NUMBER_OF_QUESTIONS_TOTAL=3;     //Stores the total number of questions that are stored in the database for the given category
    int NUMBER_OF_QUESTIONS_PER_ROUND=2; //Stores the number of Questions the user will play per round of the quiz
    final String CATEGORY="Cricket";           //Stores the category user has selected for playing
    int askedQuestionIndices[]=new int[NUMBER_OF_QUESTIONS_TOTAL+1];//Stores the indices of the questions already asked to the user in this round
    int i;                                   //Stores the number of questions asked in this particular round
    TextView questionTextView, option1TextView, option2TextView, option3TextView, option4TextView;
    ArrayList<QuestionModel> arrayList= new ArrayList<>();  //Array to store all questions available in that category in the form of QuestionModel objects
    boolean flag=true;

    //Function to generate a random number

    public int randomGenerator()
    {
        int rand=0;
        do {
            double x=Math.random()*100;
            rand=(int)x%NUMBER_OF_QUESTIONS_TOTAL;
            for(int j=0;j<i;j++)
            {
                if(askedQuestionIndices[i]==rand)
                {
                    rand=NUMBER_OF_QUESTIONS_TOTAL;
                    break;
                }
            }
        }while(rand==NUMBER_OF_QUESTIONS_TOTAL);
        askedQuestionIndices[i]=rand;
        return rand+1;
    }


    public FragmentMainQuiz()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_main_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        questionTextView=view.findViewById(R.id.TextViewQuestion);
        option1TextView=view.findViewById(R.id.Option1);
        option2TextView=view.findViewById(R.id.Option2);
        option3TextView=view.findViewById(R.id.Option3);
        option4TextView=view.findViewById(R.id.Option4);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(final Context context)
    {
        super.onAttach(context);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Categories").child("Cricket").child("Q&A");

        //Gets all the questions stored on the database at that particular instant, and stores them as an array of QuestionModels
        //This is defined outside the for loop, to minimize the read time, and also prevent high data usage

        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> contactChildren = dataSnapshot.getChildren();
                for (DataSnapshot contact : contactChildren)
                {
                    QuestionModel questionModel=new QuestionModel(contact.child("Ques").getValue().toString(), contact.child("Option1").getValue().toString(),contact.child("Option2").getValue().toString() ,contact.child("Option3").getValue().toString() ,contact.child("Option4").getValue().toString() , contact.child("Answer").getValue().toString());
                    arrayList.add(questionModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        for(i=0;i<NUMBER_OF_QUESTIONS_PER_ROUND && flag==true;i++)
        {
            flag=false;
            final int randomQuestionIndex=randomGenerator();
            askedQuestionIndices[randomQuestionIndex]=randomQuestionIndex;
            questionTextView.setText(arrayList.get(randomQuestionIndex).getQuestion());
            option1TextView.setText(arrayList.get(randomQuestionIndex).getOption1());
            option2TextView.setText(arrayList.get(randomQuestionIndex).getOption2());
            option3TextView.setText(arrayList.get(randomQuestionIndex).getOption3());
            option4TextView.setText(arrayList.get(randomQuestionIndex).getOption4());
            String correctAnswer=arrayList.get(randomQuestionIndex).getAnswer();


        }

    }

}