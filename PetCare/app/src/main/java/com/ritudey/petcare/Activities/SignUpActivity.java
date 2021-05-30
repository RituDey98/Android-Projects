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


public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    TextInputEditText name,email,pwd;
    //TextInputLayout nameTIL,emailTIL,pwdTIL;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);

        auth= FirebaseAuth.getInstance();
        name=findViewById(R.id.nameET);
        email=findViewById(R.id.emailET);
        pwd=findViewById(R.id.passwordET);

    }

    public void onClickSignUp(View view) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);


        String userName = (name.getText()).toString();
        String userEmail = (email.getText()).toString();
        String userPwd = (pwd.getText()).toString();

        Log.d(TAG, "onClickSignUp: "+userName);
        Log.d(TAG, "onClickSignUp: "+userEmail);
        Log.d(TAG, "onClickSignUp: "+userPwd);

        if(TextUtils.isEmpty(userName)){

           // nameTIL.setError("Username is required!");
            Log.d(TAG, "onClickSignUp: "+userName);
            Toast.makeText(this, "Username is required!", Toast.LENGTH_SHORT).show();
            return;

        }
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

        auth.createUserWithEmailAndPassword(userEmail,userPwd).addOnCompleteListener(SignUpActivity.this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull  Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                }else{
                    Toast.makeText(SignUpActivity.this, "Registration failed!"+task.getException(), Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void onClickSignIn(View view) {
        startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
    }
}