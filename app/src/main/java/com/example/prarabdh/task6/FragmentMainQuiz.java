package com.example.prarabdh.task6;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.CountDownTimer;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.Random;

@SuppressLint("ValidFragment")
public class FragmentMainQuiz extends Fragment
{

    ProgressBar progressBar;
    TextView question;
    TextView option_1;
    TextView option_2;
    TextView option_3;
    TextView option_4;

    int status=100;
    private Handler handler=new Handler();

    //Will have to extract this from firebase
    final int NUMBER_OF_QUESTIONS_TOTAL = 5;     //Stores the total number of questions that are stored in the database for the given category

    MediaPlayer mediaPlayerBackground;
    MediaPlayer mediaPlayerCorrect;
    MediaPlayer mediaPlayerWrong;
    int NUMBER_OF_QUESTIONS_PER_ROUND ; //Stores the number of Questions the user will play per round of the quiz
    String CATEGORY;           //Stores the category user has selected for playing
    int askedQuestionIndices[] = new int[NUMBER_OF_QUESTIONS_TOTAL];//Stores the indices of the questions already asked to the user in this round
    int i = 1;                                   //Stores the number of questions asked in this particular round
    ArrayList<QuestionModel> arrayList;  //Array to store all questions available in that category in the form of QuestionModel objects
    int currentRandom=0;                     //Stores the index of the currently generated random Question
    int points=1000,wrong=0,corect=0;       //Points variable stores the points of current quiz, wrong variable stores number of incorrect answers, and correct variable stores number of correct answers
    private boolean val=true;

    //Function to generate a random number
    public int Random()
    {
        int rand = 1;
        do {
            double d=Math.random()*1000+rand;
            rand = (int) d % NUMBER_OF_QUESTIONS_TOTAL;
            for (int j = 0; j < i; j++) {
                if (askedQuestionIndices[i] == rand) {
                    rand = NUMBER_OF_QUESTIONS_TOTAL;
                    break;
                }
            }
        } while (rand == NUMBER_OF_QUESTIONS_TOTAL);
        //askedQuestionIndices[i] = rand;
        return rand;
    }

    //This functions resets all properties of textViews to prepare it for the next question
    public void resetButtons()
    {
        option_1.setBackgroundColor(getResources().getColor(R.color.DefaultBackground));
        option_2.setBackgroundColor(getResources().getColor(R.color.DefaultBackground));
        option_3.setBackgroundColor(getResources().getColor(R.color.DefaultBackground));
        option_4.setBackgroundColor(getResources().getColor(R.color.DefaultBackground));

        option_1.setClickable(true);
        option_2.setClickable(true);
        option_3.setClickable(true);
        option_4.setClickable(true);

        question.setText("");
        option_4.setText("");
        option_3.setText("");
        option_2.setText("");
        option_1.setText("");
    }

    @Override
    public void onStart()
    {
        super.onStart();

        points = Integer.parseInt(PlayerData.udrPoints);

        //Assigning songs to the various mediaPlayer objects so that they can be played as and when required
        mediaPlayerBackground=MediaPlayer.create(getContext(),R.raw.main_quiz_background);
        mediaPlayerWrong=MediaPlayer.create(getContext(),R.raw.wrong_answer );
        mediaPlayerCorrect=MediaPlayer.create(getContext(), R.raw.correct_answer);

        //Initializing all elements of array to avoid errors due to garbage values
        for (int b=0;b<NUMBER_OF_QUESTIONS_TOTAL;b++)
        {
            askedQuestionIndices[b]=Integer.MAX_VALUE;
        }

        NUMBER_OF_QUESTIONS_PER_ROUND= getResources().getInteger(R.integer.Number_Of_Rounds_Per_Match);

        //Starting the progressBar and display of the first question

        newQuestion();

        //Setting onClickListeners to all the TextViews to know which option is selected by the user
        option_1.setOnClickListener(new onClickButton(option_1));
        option_2.setOnClickListener(new onClickButton(option_2));
        option_3.setOnClickListener(new onClickButton(option_3));
        option_4.setOnClickListener(new onClickButton(option_4));

    }

    //Default Constructor for the fragment that receives array list of questionModels from the Countdown Fragment
    @SuppressLint("ValidFragment")
    public FragmentMainQuiz(ArrayList<QuestionModel> list, String CATEGORY) {
        arrayList=list;
        this.CATEGORY=CATEGORY;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment_main_quiz, container, false);
        Progress();
        return view;
    }

    /**This function controls the functioning of the progress bar
     * The progress bar is reset at the start of every question
     * It also stops once the user has selected an option
     * Also, if the time of the Progress Bar is over, It starts a new question if the questions for that round are not over*/
    public void Progress()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (status>0){
                    status -= 1;
                    // Update the progress bar
                    handler.post(new Runnable() {
                        public void run()
                        {
                            if(val)
                            {
                                progressBar.setProgress(status);
                            }
                            else
                                {
                                new CountDownTimer(800,1000)
                                {          //pauses the progress bar if the user clicks an option

                                    @Override
                                    public void onFinish()
                                    {
                                        val=true;
                                        status=100;
                                        progressBar.setProgress(status);
                                    }
                                    @Override
                                    public void onTick(long l) { }
                                }.start();
                            }
                        }
                    });
                    try {
                        // Sleep for 300 milliseconds.
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(status<=0)
                {          //resets the progress bar
                    status=100;
                    if(i<NUMBER_OF_QUESTIONS_PER_ROUND)
                    {
                        i++;
                        newQuestion();
                        Progress();
                    }
                    else
                    {
                        endQuestions();
                        //resetButtons();
                    }

                }
            }

        }).start();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        //Initializing all the Views so that they need not be initialized again and again in the code, hence making the code concise
        question = view.findViewById(R.id.TextViewQuestion);
        progressBar = view.findViewById(R.id.ProgressBarMainQuiz);
        option_1 = view.findViewById(R.id.Option1);
        option_2= view.findViewById(R.id.Option2);
        option_3 = view.findViewById(R.id.Option3);
        option_4 = view.findViewById(R.id.Option4);
        super.onViewCreated(view, savedInstanceState);
    }

    //This function is called whenever a new question is to be displayed on the screen
    public void newQuestion()
    {
        mediaPlayerBackground.start();
        currentRandom = Random();
        askedQuestionIndices[i-1] = currentRandom;
        question.setText(arrayList.get(currentRandom).getQuestion());
        option_1.setText(arrayList.get(currentRandom).getOption1());
        option_2.setText(arrayList.get(currentRandom).getOption2());
        option_3.setText(arrayList.get(currentRandom).getOption3());
        option_4.setText(arrayList.get(currentRandom).getOption4());
    }

    /**Creating a class for onClickListener as the same logic is to be implemented with all the 4 option buttons
     * Didnot crate a new object as I needed a parameter regarding which option was clicked, which was not possible in an object*/
    public class onClickButton implements View.OnClickListener
    {

        TextView textView;  //Stores which textView called this class

        public onClickButton(TextView t)
        {
            textView = t;
        }

        @Override
        public void onClick(View v)
        {
            String correct=arrayList.get(currentRandom).getAnswer(); //Stores the correct answer for the current question
             val=false;                                                       //Introduced a new variable instead of using the expression at the right again and again to save the time required by the computer to read the arrayList
                                                                    //and execute the getAnswer() function multiple number of times

            //Disable other textViews so that multiple answers cannot be selected
            option_1.setClickable(false);
            option_2.setClickable(false);
            option_3.setClickable(false);
            option_4.setClickable(false);

            //Stopping the background sound, and resetting it for the next question
            mediaPlayerBackground.pause();
            mediaPlayerBackground.seekTo(0);

            if (textView.getText().toString().equals(correct))   //The answer is correct
            {
                //Setting background colour to green if the answer is correct
                textView.setBackgroundColor(0xFF00FF00);
                mediaPlayerCorrect.seekTo(2000);
                mediaPlayerCorrect.start();
                points++;
                corect+=1;
            }
            else    //The answer is wrong
            {
                textView.setBackgroundColor(0xFFFF0000);
                wrong+=1;
                mediaPlayerWrong.seekTo(1000);
                mediaPlayerWrong.start();
                //Setting green background colour to the textView with the right answer
                if(option_1.getText().toString().equals(correct))
                {
                    option_1.setBackgroundColor(0xFF00FF00);
                }
                else if(option_2.getText().toString().equals(correct))
                {
                    option_2.setBackgroundColor(0xFF00FF00);
                }
                else if(option_3.getText().toString().equals(correct))
                {
                    option_3.setBackgroundColor(0xFF00FF00);
                }
                else
                {
                    option_4.setBackgroundColor(0xFF00FF00);
                }
            }

            //Introduces a delay of 2 seconds between the display of result of the current question, and the display of the next question
            new CountDownTimer(2000, 1000)
            {
                public void onFinish()
                {
                    //Resetting every textView before changing the question
                    resetButtons();
                    //Stopping and resetting the Correct or Wrong Answer sound that was being played in the background
                    if(mediaPlayerCorrect.isPlaying())
                    {
                        mediaPlayerCorrect.pause();
                        mediaPlayerCorrect.seekTo(0);
                    }
                    if(mediaPlayerWrong.isPlaying())
                    {
                        mediaPlayerWrong.pause();
                        mediaPlayerWrong.seekTo(0);
                    }

                    if(i<NUMBER_OF_QUESTIONS_PER_ROUND)
                    {
                        mediaPlayerBackground.start();
                        newQuestion();
                        i++;
                    }
                    else
                    {
                        endQuestions();
                        //Toast.makeText(getContext(),"Game Over\nScore"+points ,Toast.LENGTH_LONG ).show();
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
    public void onPause()
    {
        super.onPause();
        mediaPlayerBackground.release();
        mediaPlayerCorrect.release();
        mediaPlayerWrong.release();
    }


    public void endQuestions()
    {
        Log.d("points", String.valueOf(points));
        ScoreFragment fragmentMainQuiz=new ScoreFragment(points);
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeFragment, fragmentMainQuiz);
        fragmentTransaction.commit();
    }


}
