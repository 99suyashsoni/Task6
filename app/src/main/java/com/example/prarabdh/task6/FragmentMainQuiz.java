package com.example.prarabdh.task6;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.*;

import java.util.ArrayList;



public class FragmentMainQuiz extends Fragment {

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressBar progressBar;
    TextView question;
    TextView option_1;
    TextView option_2;
    TextView option_3;
    TextView option_4;
    int status=0;
    private Handler handler=new Handler();
    private OnFragmentInteractionListener mListener;
    final int NUMBER_OF_QUESTIONS_TOTAL = 3;     //Stores the total number of questions that are stored in the database for the given category
    int NUMBER_OF_QUESTIONS_PER_ROUND = 2; //Stores the number of Questions the user will play per round of the quiz
    final String CATEGORY = "Cricket";           //Stores the category user has selected for playing
    int askedQuestionIndices[] = new int[NUMBER_OF_QUESTIONS_TOTAL + 1];//Stores the indices of the questions already asked to the user in this round
    int i = 1;                                   //Stores the number of questions asked in this particular round
    TextView questionTextView, option1TextView, option2TextView, option3TextView, option4TextView;
    ArrayList<QuestionModel> arrayList = new ArrayList<>();  //Array to store all questions available in that category in the form of QuestionModel objects
    int currentRandom;                     //Stores the index of the currently generated random Question

    //Function to generate a random number
    public int randomGenerator() {
        int rand = 0;
        do {
            double x = Math.random() * 100;
            rand = (int) x % NUMBER_OF_QUESTIONS_TOTAL;
            for (int j = 0; j < i; j++) {
                if (askedQuestionIndices[i] == rand) {
                    rand = NUMBER_OF_QUESTIONS_TOTAL;
                    break;
                }
            }
        } while (rand == NUMBER_OF_QUESTIONS_TOTAL);
        askedQuestionIndices[i] = rand;
        return rand;
    }


    @Override
    public void onStart() {
        super.onStart();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Categories").child("Cricket").child("Q&A");

        //Gets all the questions stored on the database at that particular instant, and stores them as an array of QuestionModels
        //This is defined outside the for loop, to minimize the read time, and also prevent high data usage
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> contactChildren = dataSnapshot.getChildren();
                for (DataSnapshot contact : contactChildren) {
                    QuestionModel questionModel = new QuestionModel(contact.child("Ques").getValue().toString(), contact.child("Option1").getValue().toString(), contact.child("Option2").getValue().toString(), contact.child("Option3").getValue().toString(), contact.child("Option4").getValue().toString(), contact.child("Answer").getValue().toString());
                    arrayList.add(questionModel);
                }
                newQuestion();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

            option1TextView.setOnClickListener(new onClick(option1TextView));
            option2TextView.setOnClickListener(new onClick(option2TextView));
            option3TextView.setOnClickListener(new onClick(option3TextView));
            option4TextView.setOnClickListener(new onClick(option4TextView));



    }

    public FragmentMainQuiz() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fragment_main_quiz, container, false);
        progressBar=view.findViewById(R.id.ProgressBarMainQuiz);
        question=view.findViewById(R.id.TextViewQuestion);
        option_1=view.findViewById(R.id.Option1);
        option_2=view.findViewById(R.id.Option2);
        option_3=view.findViewById(R.id.Option3);
        option_4=view.findViewById(R.id.Option4);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (status<100){
                    status += 1;
                    // Update the progress bar
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(status);
                        }
        });
                    try {
                        // Sleep for 300 milliseconds.
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        //Initializing all the Views so that they need not be initialized again and again in the code, hence making the code concise
        questionTextView = view.findViewById(R.id.TextViewQuestion);
        option1TextView = view.findViewById(R.id.Option1);
        option2TextView = view.findViewById(R.id.Option2);
        option3TextView = view.findViewById(R.id.Option3);
        option4TextView = view.findViewById(R.id.Option4);
        super.onViewCreated(view, savedInstanceState);
    }

    //This function is called whenever a new question is to be displayed on the screen
    public void newQuestion()
    {
        currentRandom = randomGenerator();
        askedQuestionIndices[i - 1] = currentRandom;
        questionTextView.setText(arrayList.get(currentRandom).getQuestion());
        option1TextView.setText(arrayList.get(currentRandom).getOption1());
        option2TextView.setText(arrayList.get(currentRandom).getOption2());
        option3TextView.setText(arrayList.get(currentRandom).getOption3());
        option4TextView.setText(arrayList.get(currentRandom).getOption4());
    }


    /**Creating a class for onClickListener as the same logic is to be implemented with all the 4 option buttons
     * Didnot crate a new object as I needed a parameter regarding which option was clicked, which was not possible in an object*/
    public class onClick implements View.OnClickListener
    {

        TextView textView;  //Stores which textView called this class

        public onClick(TextView t)
        {
            textView = t;
        }

        @Override
        public void onClick(View v)
        {
            String correct=arrayList.get(currentRandom).getAnswer(); //Stores the correct answer for the current question
                                                                    //Introduced a new variable instead of using the expression at the right again and again to save the time required by the computer to read the arrayList
                                                                    //and execute the getAnswer() function multiple number of times

            if (textView.getText().toString().equals(correct))
            {
                //Setting background colour to green if the answer is correct
                textView.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
            }
            else
            {
                textView.setBackgroundColor(getResources().getColor(R.color.WrongAnswer));
                //Setting green background colour to the textView with the right answer
                if(option1TextView.getText().toString().equals(correct))
                {
                    option1TextView.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                }
                else if(option2TextView.getText().toString().equals(correct))
                {
                    option2TextView.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                }
                else if(option3TextView.getText().toString().equals(correct))
                {
                    option3TextView.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                }
                else
                {
                    option4TextView.setBackgroundColor(getResources().getColor(R.color.CorrectAnswer));
                }
            }

            //Introduces a delay of 3 seconds between the display of result of the current question, and the display of the next question
            new CountDownTimer(3000, 1000)
            {
                public void onFinish()
                {
                    //Resetting every textView before changing the question
                    option1TextView.setBackgroundColor(getResources().getColor(R.color.DefaultBackground));
                    option2TextView.setBackgroundColor(getResources().getColor(R.color.DefaultBackground));
                    option3TextView.setBackgroundColor(getResources().getColor(R.color.DefaultBackground));
                    option4TextView.setBackgroundColor(getResources().getColor(R.color.DefaultBackground));
                    if(i<NUMBER_OF_QUESTIONS_PER_ROUND)
                    {
                        newQuestion();
                        i++;
                    }
                }

                public void onTick(long millisUntilFinished)
                {
                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();

        }
    }

    @Override
    public void onAttach(final Context context)
    {
        super.onAttach(context);
    }
}
