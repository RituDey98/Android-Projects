package com.ritudey.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView timer,equation,score,option1,option2,option3,option4,result,play;
    ConstraintLayout game;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    Random rand;
    int correctAnsRandomLocation,myScore=0,numOfQuestions=0,flag=0;
    Button playAgain;

    CountDownTimer myTimer;

    public void playAgainFun()
    {
        flag=0;
        myScore=0;
        numOfQuestions=0;
        myTimer.start();
        if(flag==0) //flag=0 means time is not up
        {
            generateEquation();
        }
        playAgain.setVisibility(View.GONE);
        result.setText("");
        score.setText(String.format("%s/%s","0", "0"));

    }


    public void generateEquation()
    {
        rand=new Random();
        int a=rand.nextInt(41);
        int b=rand.nextInt(41);
        int x=rand.nextInt(21);
        int y=rand.nextInt(16);

        int operatorNumber= rand.nextInt(3);
        String operator = "";

        switch (operatorNumber)
        {
            case 0:
                operator="+";
                equation.setText(Integer.toString(a)+operator+Integer.toString(b));
                break;
            case 1:
                operator="-";
                if(a>b){
                    equation.setText(Integer.toString(a)+operator+Integer.toString(b));
                }
                else {
                    equation.setText(Integer.toString(b)+operator+Integer.toString(a));
                }
                break;
            case 2:
                operator="*";
                equation.setText(Integer.toString(x)+operator+Integer.toString(y));
                break;

        }


        correctAnsRandomLocation=rand.nextInt(4);

        answers.clear();

        for(int i=0;i<4;i++)
        {
            if(i==correctAnsRandomLocation){
                switch (operatorNumber)
                {
                    case 0:
                        answers.add(a+b);
                        break;
                    case 1:
                        if(a>b)
                        {
                            answers.add(a-b);
                        }else {
                            answers.add(b-a);
                        }
                        break;
                    case 2:
                       answers.add(x*y);
                        break;
                }
            }
            else {
                int incorrectProduct= rand.nextInt(301);
                int  incorrectAnswer=rand.nextInt(81);
                while (incorrectAnswer==a+b||incorrectProduct==x*y)
                {
                    incorrectProduct=rand.nextInt(301);
                    incorrectAnswer= rand.nextInt(81);
                }
                if(operatorNumber==3){
                    answers.add(incorrectProduct);
                }
                else {
                    answers.add(incorrectAnswer);
                }
            }
        }

        option1.setText(Integer.toString(answers.get(0)));
        option2.setText(Integer.toString(answers.get(1)));
        option3.setText(Integer.toString(answers.get(2)));
        option4.setText(Integer.toString(answers.get(3)));

    }

    public void resultUpdate(TextView v)
    {
        if(flag==0){
            if(Integer.toString(correctAnsRandomLocation).equals(String.valueOf(v.getTag()))){
                result.setText("Correct!");
                myScore++;
            }else{
                result.setText("Incorrect!");
            }
            numOfQuestions++;
            score.setText(String.format("%s/%s", Integer.toString(myScore), Integer.toString(numOfQuestions)));
            generateEquation();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.playLayout);
        game = findViewById(R.id.gameLayout);

        timer = findViewById(R.id.timeTextView);
        equation=findViewById(R.id.equationTextView);
        score =findViewById(R.id.scoreTextView);
        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);
        result=findViewById(R.id.resultTextView);
        playAgain=findViewById(R.id.playAgainBtn);



        play.setOnClickListener(this);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        playAgain.setOnClickListener(this);



        if(flag==0) //flag=0 means time is not up
        {
            generateEquation();
        }




        myTimer = new CountDownTimer(36000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.format("%sS", String.valueOf(millisUntilFinished / 1000)));
            }

            @Override
            public void onFinish() {
                result.setText("Time is up!");
                flag++;
                result.setText("Your Score : "+Integer.toString(myScore)+"/"+Integer.toString(numOfQuestions));
                playAgain.setVisibility(View.VISIBLE);
            }
        };


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.playLayout:
                game.setVisibility(View.VISIBLE);
                play.setVisibility(View.GONE);

                myTimer.start();
                break;

            case R.id.option1:
                resultUpdate(option1);
                break;
            case R.id.option2:
                resultUpdate(option2);
                break;
            case R.id.option3:
                resultUpdate(option3);
                break;
            case R.id.option4:
                resultUpdate(option4);
                break;
            case R.id.playAgainBtn:
                playAgainFun();
                break;
        }

    }
}