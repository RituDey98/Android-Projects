package com.ritudey.petcare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ritudey.petcare.R;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";


    TextInputEditText email,pwd;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();


        auth= FirebaseAuth.getInstance();
        email=findViewById(R.id.emailET);
        pwd=findViewById(R.id.passwordET);
    }

    public void onClickSignIn(View view) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);


        String userEmail = (email.getText()).toString();
        String userPwd = (pwd.getText()).toString();


        Log.d(TAG, "onClickSignUp: "+userEmail);
        Log.d(TAG, "onClickSignUp: "+userPwd);

        if(TextUtils.isEmpty(userEmail)){

            //emailTIL.setError("Email is required!");
            Toast.makeText(this, "email is required!", Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(userPwd)){

            //pwdTIL.setError("Password is required!");
            Toast.makeText(this, "pwd is required!", Toast.LENGTH_SHORT).show();
            return;

        }
        if(userPwd.length()<8){
            // pwdTIL.setError("Password must be of 8 characters");
            Toast.makeText(this, "Password must be of 8 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(userEmail,userPwd).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull  Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(SignInActivity.this, "Successfully Logged in!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInActivity.this,MainActivity.class));

                }else{
                    Toast.makeText(SignInActivity.this, "Login failed!"+task.getException(), Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    public void onClickSignUp(View view) {
        startActivity(new Intent(SignInActivity.this,SignUpActivity.class));

    }
}