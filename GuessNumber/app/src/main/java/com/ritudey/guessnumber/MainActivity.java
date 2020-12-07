package com.ritudey.guessnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int randomNum;
    int min=1;
    int max=20;

    public void makeToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void onClickCheck(View view)
    {
        EditText numEntered = findViewById(R.id.editTextNumber);
        int num = Integer.parseInt(numEntered.getText().toString());
        Log.i("check",numEntered.getText().toString());


        if(num>20||num<1){
           makeToast("Enter a number between 1-20!!");
        }
        else if (num > randomNum)
        {
            makeToast("Lower!");
        }
        else if (num < randomNum) {
           makeToast("Higher!");
        }
        else{
            makeToast("Correct! Play again I m ready !");
            randomNum = new Random().nextInt((max-min)+1) + min ; // [0,19]+ 1
        }
        numEntered.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        randomNum = new Random().nextInt((max-min)+1) + min ; // [0,19]+ 1
    }
}