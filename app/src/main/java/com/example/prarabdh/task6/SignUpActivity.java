package com.example.prarabdh.task6;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private Button buttonRegister;
    private Button buttonSignInInstead;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextUserName;
    private RecyclerView recyclerView;
    private SignUpAdapter mAdapter;
    private ProgressBar progressBar;
    private boolean progressBarPresent=false;

 //    private ImageButton imageButton1;
//    private ImageButton imageButton2;
//    private ImageButton imageButton3;
//    private ImageButton imageButton4;
    private ImageView imageViewAvatar;
//    private  String email="w";
//    private  String password="e";
    private String imageChosen="1";
    private String uid="hh";
//    private String url1="https://vignette.wikia.nocookie.net/angry-birds-epic-fanon/images/f/fd/212px-AB_Epic_Avatar_Image_1.png/revision/latest?cb=20150110074525";
//    private String url2="https://vignette.wikia.nocookie.net/angry-birds-epic-fanon/images/4/45/177px-AB_Epic_Avatar_Image_3.png/revision/latest?cb=20150314093014";
//    private String url3="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQVCdo0Ck-v38cziOSMRVVhpXb2AUsF_EBSXPTYv3W2Zyv8mfJi";
//    private String url4="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQSVB6DvVjeTQxtDHtBAVQ6_wG4qA7_JPcC3sxJz25f3tWNo6r";
    private String imageChosenUrl=Integer.toString(R.string.avatar_url_1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
         ArrayList<String> datasetAvatar= new ArrayList<>();
        datasetAvatar.add(Integer.toString(R.string.avatar_url_1));
        datasetAvatar.add(Integer.toString(R.string.avatar_url_2));
        datasetAvatar.add(Integer.toString(R.string.avatar_url_3));
        datasetAvatar.add(Integer.toString(R.string.avatar_url_4));
        datasetAvatar.add(Integer.toString(R.string.avatar_url_1));
        datasetAvatar.add(Integer.toString(R.string.avatar_url_2));
        datasetAvatar.add(Integer.toString(R.string.avatar_url_3));
        datasetAvatar.add(Integer.toString(R.string.avatar_url_4));



        //finding all views
        buttonRegister=findViewById(R.id.buttonRegister);
        buttonSignInInstead=findViewById(R.id.buttonSignInInstead);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword =findViewById(R.id.editTextPassword);
        editTextUserName=findViewById(R.id.editTextUserName);
        recyclerView=findViewById(R.id.recyclerView);
        mAdapter=new SignUpAdapter(getApplicationContext(),datasetAvatar);
        recyclerView.setAdapter(mAdapter);
      //  mAdapter.notifyDataSetChanged();
//        imageButton1=findViewById(R.id.imageButton1);
//        imageButton2=findViewById(R.id.imageButton2);
//        imageButton3=findViewById(R.id.imageButton3);
//        imageButton4=findViewById(R.id.imageButton4);
        imageViewAvatar=findViewById(R.id.imageViewAvatar);
       Glide.with(this).load(Integer.toString(R.string.avatar_url_1)).into(imageViewAvatar);
//        Glide.with(this).load(url1).into(imageButton1);
//        Glide.with(this).load(url2).into(imageButton2);
//        Glide.with(this).load(url3).into(imageButton3);
//        Glide.with(this).load(url4).into(imageButton4);

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();
   recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

       @Override
       public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
           View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());

         //  editTextUserName.setText(Integer.toString(recyclerView.getChildAdapterPosition(child))+1);
         //  Toast.makeText(SignUpActivity.this,"pos"+recyclerView.getChildAdapterPosition(child),Toast.LENGTH_LONG).show();
 //Glide.with(SignUpActivity.this).load(url2).into(imageViewAvatar);
    updateImage(recyclerView.getChildAdapterPosition(child));
           return false;
       }

       @Override
       public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

//           View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());
//
//editTextUserName.setText(recyclerView.getChildAdapterPosition(child));
//           Toast.makeText(SignUpActivity.this,"pos"+recyclerView.getChildAdapterPosition(child),Toast.LENGTH_LONG).show();

       }

       @Override
       public void onRequestDisallowInterceptTouchEvent(boolean b) {

       }
   });

        //setting onClick Listeners
//        imageButton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageChosen="1";
//                Glide.with(SignUpActivity.this).load(url1).into(imageViewAvatar);
//
//            }
//        });
//
//        imageButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageChosen="2";
//                Glide.with(SignUpActivity.this).load(url2).into(imageViewAvatar);
//            }
//        });
//
//        imageButton3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageChosen="3";
//                Glide.with(SignUpActivity.this).load(url3).into(imageViewAvatar);
//
//            }
//        });
//
//        imageButton4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageChosen="girl";
//                Glide.with(SignUpActivity.this).load(url4).into(imageViewAvatar);
//            }
//        });



        buttonSignInInstead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // progressDialog=ProgressDialog.show(SignUpActivity.this,)
               loadProgressBar();

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        int i = 0;
                        for(DataSnapshot dsp:dataSnapshot.getChildren()){
                            String uname=dsp.child("Username").getValue().toString();
                            // UserR userR = dsp.getValue(UserR.class);
                            if(editTextUserName.getText().toString().equals(uname)){

                                Toast.makeText(SignUpActivity.this,"Username already taken",Toast.LENGTH_LONG).show();

                                editTextUserName.setText("");

                                editTextUserName.setHint("Choose different username");
                                removeProgressBar();
                                i = 1;
                                break;
                            }

                        }
                        if(i==0)
                        {
                            createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                       removeProgressBar();
                    }
                });


            }
        });
    }

    private void removeProgressBar() {
        if(progressBarPresent)
        {
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBarPresent=false;
        }
    }

    private void loadProgressBar() {
        if(!progressBarPresent)
        {
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBarPresent=true;
        }
    }

    private void updateImage(int childAdapterPosition) {

        switch (childAdapterPosition) {
            case 0:
                imageChosenUrl=Integer.toString(R.string.avatar_url_1);
                Glide.with(SignUpActivity.this).load(Integer.toString(R.string.avatar_url_1)).into(imageViewAvatar);
                break;

            case 1:
                imageChosenUrl=Integer.toString(R.string.avatar_url_2);
                Glide.with(SignUpActivity.this).load(Integer.toString(R.string.avatar_url_2)).into(imageViewAvatar);
                break;
            case 2:
                imageChosenUrl=Integer.toString(R.string.avatar_url_3);
                Glide.with(SignUpActivity.this).load(Integer.toString(R.string.avatar_url_3)).into(imageViewAvatar);
                break;
            case 3:
                imageChosenUrl=Integer.toString(R.string.avatar_url_4);
                Glide.with(SignUpActivity.this).load(Integer.toString(R.string.avatar_url_4)).into(imageViewAvatar);
                break;
            case 4:
                imageChosenUrl=Integer.toString(R.string.avatar_url_1);
                Glide.with(SignUpActivity.this).load(Integer.toString(R.string.avatar_url_1)).into(imageViewAvatar);
                break;
            case 5:
                imageChosenUrl=Integer.toString(R.string.avatar_url_2);
                Glide.with(SignUpActivity.this).load(Integer.toString(R.string.avatar_url_2)).into(imageViewAvatar);
                break;
            case 6:
                imageChosenUrl=Integer.toString(R.string.avatar_url_3);
                Glide.with(SignUpActivity.this).load(Integer.toString(R.string.avatar_url_3)).into(imageViewAvatar);
                break;
            case 7:
                imageChosenUrl=Integer.toString(R.string.avatar_url_4);
                Glide.with(SignUpActivity.this).load(Integer.toString(R.string.avatar_url_4)).into(imageViewAvatar);
                break;
        default:

            imageChosenUrl=Integer.toString(R.string.avatar_url_1);
            Glide.with(SignUpActivity.this).load(Integer.toString(R.string.avatar_url_1)).into(imageViewAvatar);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


//Method to create a new account
    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            removeProgressBar();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            editTextEmail.setHint("Enter Valid Email");
                            editTextPassword.setHint("Enter Valid Password");
                            Toast.makeText(SignUpActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }



//method to check non null entries
    private boolean validateForm() {
        boolean valid = true;

        String email1 = editTextEmail.getText().toString();
        if (TextUtils.isEmpty(email1)) {
            editTextEmail.setError("Required.");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        String password1 = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(password1)) {
            editTextPassword.setError("Required.");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        String username1 = editTextUserName.getText().toString();
        if (TextUtils.isEmpty(username1)) {
            editTextUserName.setError("Required.");
            valid = false;
        } else {
            editTextUserName.setError(null);
        }

        return valid;
    }

    //method to update ui
    private void updateUI(FirebaseUser user) {
        if (user != null) {

//            if(imageChosen.equals("1")){
//                imageChosenUrl=url1;
//            }
//            else if(imageChosen.equals("2")){
//                imageChosenUrl=url2;
//            }
//            else if(imageChosen.equals("3")){
//                imageChosenUrl=url3;
//            }
//            else if(imageChosen.equals("4")){
//                imageChosenUrl=url4;
//            }


            uid=user.getUid();

            writeNewUser(editTextEmail.getText().toString(),imageChosenUrl,editTextUserName.getText().toString());
            removeProgressBar();
            finish();
        }
        else
        {
            editTextPassword.setText("");
            editTextUserName.setText("");
            editTextEmail.setText("");
            removeProgressBar();
        }
    }

    //method to  save data of new user in database
    private void writeNewUser(String email,String imageChosenUrl,String userName) {
        mDatabase.child(uid).child("Email-id").setValue(email);
        mDatabase.child(uid).child("Total Points").setValue("0");
        mDatabase.child(uid).child("Avtar Img").setValue(imageChosenUrl);
        mDatabase.child(uid).child("Username").setValue(userName);
        mDatabase.child(uid).child("Wins").setValue("0");
        mDatabase.child(uid).child("Losses").setValue("0");
        mDatabase.child(uid).child("Achievements").child("A1").setValue("0");
        mDatabase.child(uid).child("Achievements").child("A2").setValue("0");
        mDatabase.child(uid).child("Achievements").child("A3").setValue("0");
    }

}
