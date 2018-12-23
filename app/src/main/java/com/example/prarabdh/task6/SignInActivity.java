package com.example.prarabdh.task6;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
   // private String email="w";
    //private String password="e";
    private boolean newUserClicked=false;
   // private String uname="aa";
   // private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //finding all views
        buttonNewUser=findViewById(R.id.buttonNewUser);
        buttonSignIn=findViewById(R.id.buttonSignIn);
        editTextPassword =findViewById(R.id.editTextPassword);
        editTextEmail=findViewById(R.id.editTextEmail);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn(editTextEmail.getText().toString(),editTextPassword.getText().toString());

            }
        });

        buttonNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUserClicked=true;
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(newUserClicked)
        {
            finish();
        }
    }

    // method to check non null entries
    private boolean validateForm() {

        boolean valid = true;
        if (TextUtils.isEmpty( editTextEmail.getText().toString())) {
            editTextEmail.setError("Required.");
            valid = false;
        }
        else
        {
            editTextEmail.setError(null);
        }


        if (TextUtils.isEmpty(editTextEmail.getText().toString())) {
            editTextPassword.setError("Required.");
            valid = false;
        }
        else
        {
            editTextPassword.setError(null);
        }

        return valid;
    }
// method to update ui
    private void updateUI(FirebaseUser user) {
        if (user != null)
        {
            finish();
        }
        else
        {
            editTextPassword.setText("");
            editTextEmail.setText("");
        }
    }

    private void signIn(String email, String password) {


        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
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
                        }

                    }
                });

    }

}

