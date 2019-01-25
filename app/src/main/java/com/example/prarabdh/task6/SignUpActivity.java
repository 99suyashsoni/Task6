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
    private boolean progressBarPresent = false;

    private ImageView imageViewAvatar;
    private String imageChosen = "1";
    private String uid = "hh";

    private String url_1="https://vignette.wikia.nocookie.net/angry-birds-epic-fanon/images/f/fd/212px-AB_Epic_Avatar_Image_1.png/revision/latest?cb=20150110074525";
    private String url_2="https://vignette.wikia.nocookie.net/angry-birds-epic-fanon/images/4/45/177px-AB_Epic_Avatar_Image_3.png/revision/latest?cb=20150314093014";
    private String url_3="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQVCdo0Ck-v38cziOSMRVVhpXb2AUsF_EBSXPTYv3W2Zyv8mfJi";
    private String url_4="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQSVB6DvVjeTQxtDHtBAVQ6_wG4qA7_JPcC3sxJz25f3tWNo6r";
    private String imageChosenUrl =url_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ArrayList<String> datasetAvatar = new ArrayList<>();

        datasetAvatar.add(url_1);
        datasetAvatar.add(url_2);
        datasetAvatar.add(url_3);
        datasetAvatar.add(url_4);
        datasetAvatar.add(url_1);
        datasetAvatar.add(url_2);
        datasetAvatar.add(url_3);
        datasetAvatar.add(url_4);

        //finding all views
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonSignInInstead = findViewById(R.id.buttonSignInInstead);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUserName = findViewById(R.id.editTextUserName);
        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new SignUpAdapter(getApplicationContext(), datasetAvatar);
        recyclerView.setAdapter(mAdapter);

        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        //Glide.with(this).load(Integer.toString(R.string.avatar_url_1)).into(imageViewAvatar);
        Glide.with(this).load(url_1).into(imageViewAvatar);

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {


            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                updateImage(recyclerView.getChildAdapterPosition(child));
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });

        buttonSignInInstead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadProgressBar();

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int i = 0;
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            String uname = dsp.child("Username").getValue().toString();
                            // UserR userR = dsp.getValue(UserR.class);
                            if (editTextUserName.getText().toString().equals(uname)) {

                                Toast.makeText(SignUpActivity.this, "Username already taken", Toast.LENGTH_LONG).show();

                                editTextUserName.setText("");

                                editTextUserName.setHint("Choose different username");
                                removeProgressBar();
                                i = 1;
                                break;
                            }

                        }
                        if (i == 0) {
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
        if (progressBarPresent) {
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBarPresent = false;
        }
    }

    private void loadProgressBar() {
        if (!progressBarPresent) {
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBarPresent = true;
        }
    }

    private void updateImage(int childAdapterPosition) {

        switch (childAdapterPosition) {
            case 0:

                imageChosenUrl = url_1;
                Glide.with(SignUpActivity.this).load(url_1).into(imageViewAvatar);
                break;

            case 1:
                imageChosenUrl = url_2;
                Glide.with(SignUpActivity.this).load(url_2).into(imageViewAvatar);
                break;
            case 2:
                imageChosenUrl = url_3;
                Glide.with(SignUpActivity.this).load(url_3).into(imageViewAvatar);
                break;
            case 3:
                imageChosenUrl = url_4;
                Glide.with(SignUpActivity.this).load(url_4).into(imageViewAvatar);
                break;
            case 4:
                imageChosenUrl = url_1;
                Glide.with(SignUpActivity.this).load(url_1).into(imageViewAvatar);
                break;
            case 5:
                imageChosenUrl = url_2;
                Glide.with(SignUpActivity.this).load(url_2).into(imageViewAvatar);
                break;
            case 6:
                imageChosenUrl = url_3;
                Glide.with(SignUpActivity.this).load(url_3).into(imageViewAvatar);
                break;
            case 7:
                imageChosenUrl = url_4;
                Glide.with(SignUpActivity.this).load(url_1).into(imageViewAvatar);
                break;
            default:

                imageChosenUrl = url_1;
                Glide.with(SignUpActivity.this).load(url_1).into(imageViewAvatar);
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

            uid = user.getUid();

            writeNewUser(editTextEmail.getText().toString(), imageChosenUrl, editTextUserName.getText().toString());
            removeProgressBar();
            finish();
        } else {
            editTextPassword.setText("");
            editTextUserName.setText("");
            editTextEmail.setText("");
            removeProgressBar();
        }
    }

    //method to  save data of new user in database
    private void writeNewUser(String email, String imageChosenUrl, String userName) {

        mDatabase.child(uid).child("AchievementsUnlocked").setValue("000000000000000000000");//21 0's
        mDatabase.child(uid).child("Avatar").setValue(imageChosenUrl);
        mDatabase.child(uid).child("CategoriesUnlocked").setValue("111000000000"); //3 1's 9 0's
        mDatabase.child(uid).child("Email").setValue(email);
        mDatabase.child(uid).child("Loss").setValue("0");
        mDatabase.child(uid).child("Points").setValue("0");
        //to store questions attempted by user
        mDatabase.child(uid).child("Questions").child("Anime").setValue("0000000000");//10 0's
        mDatabase.child(uid).child("Questions").child("Culture").setValue("0000000000");//10 0's
        mDatabase.child(uid).child("Questions").child("DC+Marvel").setValue("0000000000");//10 0's
        mDatabase.child(uid).child("Questions").child("Food").setValue("0000000000");//10 0's
        mDatabase.child(uid).child("Questions").child("Gaming").setValue("0000000000");//10 0's
        mDatabase.child(uid).child("Questions").child("Movies").setValue("0000000000");//10 0's
        mDatabase.child(uid).child("Questions").child("Politics").setValue("0000000000");//10 0's
        mDatabase.child(uid).child("Questions").child("Riddles").setValue("0000000000");//10 0's
        mDatabase.child(uid).child("Questions").child("Sports").setValue("0000000000");//10 0's
        mDatabase.child(uid).child("Questions").child("TVSeries").setValue("0000000000");//10 0's
        mDatabase.child(uid).child("Questions").child("Travel").setValue("0000000000");//10 0's
        mDatabase.child(uid).child("Questions").child("War").setValue("0000000000");//10 0's
        //
        mDatabase.child(uid).child("Username").setValue(userName);
        mDatabase.child(uid).child("Win").setValue("0");
    }

}
