package com.example.prarabdh.task6;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private Button buttonNewUser;
    private Button buttonSignIn;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private boolean newUserClicked = false;
    private ProgressBar progressBar;
    private boolean progressBarPresent = false;
    private boolean onBackPressedTwice=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //finding all views
        buttonNewUser = findViewById(R.id.buttonNewUser);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProgressBar();


                signIn(editTextEmail.getText().toString(),editTextPassword.getText().toString());

            }
        });

        buttonNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUserClicked = true;
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (newUserClicked) {
            finish();
        }
    }

    // method to check non null entries
    private boolean validateForm() {

        boolean valid = true;
        if (TextUtils.isEmpty(editTextEmail.getText().toString())) {
            editTextEmail.setError("Required.");

            valid = false;
        } else {
            editTextEmail.setError(null);
        }


        if (TextUtils.isEmpty(editTextEmail.getText().toString())) {
            editTextPassword.setError("Required.");

            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        return valid;
    }

    // method to update ui
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            removeProgressBar();
            finish();
        } else {
            removeProgressBar();
            editTextPassword.setText("");
            editTextEmail.setText("");
        }
    }


    private void signIn(String email, String password) {


        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            removeProgressBar();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            editTextPassword.setHint("Enter Valid Password");
                            editTextEmail.setText("Enter Valid Email-ID");
                            updateUI(null);
                        }


                        if (!task.isSuccessful()) {
                            removeProgressBar();

                        }

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

    @Override
    public void onBackPressed() {

        if(onBackPressedTwice){
            finishAffinity();
            super.onBackPressed();

        }else{
            onBackPressedTwice=true;
            Toast.makeText(this,"Press again to exit",Toast.LENGTH_LONG).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBackPressedTwice=false;
            }
        },2000);

    }
}

