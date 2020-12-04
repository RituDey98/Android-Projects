package com.ritudey.eggtimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    SeekBar seekTime;
    TextView time;
    Button startStopBtn;
    CountDownTimer myTimer;
    int cuurTime;
    long milliSecondsLeft;

    public void updateTime(long secLeft){
       long  min= (long)secLeft/(60);
       long  sec= (secLeft)-(min*60);

        String secondString = String.valueOf(sec);

        if(sec<=9)
        {
            secondString="0"+secondString;
        }
        time.setText(String.valueOf(min) + ":" + secondString);
    }

    public void timerStart (long milliseconds){
            myTimer = new CountDownTimer(milliseconds,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                milliSecondsLeft = millisUntilFinished;
                updateTime(milliSecondsLeft/1000);
            }

            @Override
            public void onFinish() {

                MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.air_horn);
                mPlayer.start();
                startStopBtn.setText("go");
                seekTime.setEnabled(true);
            }
        }.start();
    }

    public void timerStop()
    {
        myTimer.cancel();
    }
    public void timerResume()
    {
        timerStart(milliSecondsLeft);

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Timer using handler
//        final Handler handler = new Handler();
//
//        Runnable run = new Runnable() {
//            @Override
//            public void run() {
//                Log.i("Running code","every second");
//                handler.postDelayed(this,1000);
//            }
//        };
//        handler.post(run);  //This will run the code immediately



//        new CountDownTimer(10000,1000){
//
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//                Log.i("CountDown",String.valueOf(millisUntilFinished/1000));
//                 //running the code every tick o interval here  1sec
//
//            }
//
//            @Override
//            public void onFinish() {
//
//                Log.i("Finished","countdown");
        //running at 10 sec aftr finish
//            }
//        }.start();

        time = findViewById(R.id.timeTextV);
        startStopBtn =findViewById(R.id.startStopBtn);

        seekTime = findViewById(R.id.timeSeek);
        seekTime.setMax(600);
        seekTime.setProgress(30);
        seekTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cuurTime=progress;
                updateTime(cuurTime);
                seekTime.setProgress(cuurTime);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        startStopBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(startStopBtn.getText().equals("go"))
        {
            timerStart(cuurTime*1000);
            startStopBtn.setText("pause");
            seekTime.setEnabled(false);
        }
        else if(startStopBtn.getText().equals("pause"))
        {
            timerStop();
            startStopBtn.setText("Resume");
        }
        else{
            timerResume();
            startStopBtn.setText("pause");
        }

    }
}