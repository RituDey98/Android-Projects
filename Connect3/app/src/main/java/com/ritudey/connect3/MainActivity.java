package com.ritudey.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 2 is when none is selected
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    //the indices at which the game could be won
    int [][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    boolean gameActive = true;

    int f=0;
    public void onClickDropIn(View v)
    {
        ImageView counter = (ImageView) v;

        // f=0 for red and f=1 for yellow
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameActive)
        {
            counter.setTranslationY(-1000f);
            gameState[tappedCounter] = f;

            if(f==0)
            {
                counter.setImageResource(R.drawable.red_coin);

                f=1;
            }
            else
            {
                counter.setImageResource(R.drawable.yellow_coin);
                f=0;
            }

            counter.animate().translationYBy(1000f).setDuration(500);

            for(int [] winningPosition : winningPositions)
            {
                if((gameState[winningPosition[0]]==gameState[winningPosition[1]] )             // checking if the winning positions are same
                        && (gameState[winningPosition[1]]==gameState[winningPosition[2]])
                        &&(gameState[winningPosition[0]]!=2))
                {
                    gameActive=false;
                    System.out.println("win"+gameState[winningPosition[0]]); // testing is getting 0 or 1 at 0th index

                    if(gameState[winningPosition[0]]==0)
                    {
                        winMsg("Red Wins!!");
                    }
                    else {
                        winMsg("Yellow wins!!");
                    }

                }
                else {

                    boolean gameOver=true;
                    for (int counterState : gameState)
                    {
                        if(counterState==2)
                        {
                            gameOver=false;
                        }
                    }
                    if(gameOver)
                    {
                        winMsg("It's a Draw!!!");
                    }
                }

            }
        }

    }

    public void playAgain(View v)
    {
        LinearLayout playAgain = findViewById(R.id.playAgainLayout);
        playAgain.clearAnimation();
        playAgain.setVisibility(View.INVISIBLE);

         gameActive = true;
         f=0;
        for (int i=0;i<gameState.length;i++)
        {
            gameState[i]=2;
        }

        GridLayout layout = findViewById(R.id.gridLayout);
        for (int i = 0; i < layout.getChildCount(); i++ )
        {
            ((ImageView) layout.getChildAt(i)).setImageResource(0);
        }
    }
    public void winMsg(String msg)
    {

        LinearLayout playAgain = findViewById(R.id.playAgainLayout) ;
        playAgain.animate().rotation(720).setDuration(2000); // the animation is running only first time
        playAgain.setVisibility(View.VISIBLE);

        TextView winMsg = findViewById(R.id.winTextView);
        winMsg.setText(msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}