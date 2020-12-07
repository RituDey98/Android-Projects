package com.ritudey.basicphrases;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer phrase1,phrase2,phrase3,phrase4,phrase5,phrase6,phrase7,phrase8;

    public void onClickPhrase(View v)
    {
        switch (v.getId()){

            case R.id.btn1:
                phrase1.start();
                break;
            case R.id.btn2:
                phrase2.start();
                break;
            case R.id.btn3:
                phrase3.start();
                break;
            case R.id.btn4:
                phrase4.start();
                break;
            case R.id.btn5:
                phrase5.start();
                break;
            case R.id.btn6:
                phrase6.start();
                break;
            case R.id.btn7:
                phrase7.start();
                break;
            case  R.id.btn8:
                phrase8.start();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phrase1=MediaPlayer.create(this,R.raw.doyouspeakenglish);
        phrase2=MediaPlayer.create(this,R.raw.goodevening);
        phrase3=MediaPlayer.create(this,R.raw.howareyou);
        phrase4=MediaPlayer.create(this,R.raw.hello);
        phrase5=MediaPlayer.create(this,R.raw.ilivein);
        phrase6=MediaPlayer.create(this,R.raw.please);
        phrase7=MediaPlayer.create(this,R.raw.mynameis);
        phrase8=MediaPlayer.create(this,R.raw.welcome);

    }

}