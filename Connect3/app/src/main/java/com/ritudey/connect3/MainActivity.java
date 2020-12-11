package com.ritudey.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 2 is when none is selected
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    //the indices at which the game could be won if the game states of the winning position are equal
    int [][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    boolean gameActive = true;

    int counterColor=0; //0 is red, 1 is yellow



    public void onClickDropIn(View v)
    {
        ImageView counter = (ImageView) v;

        // f=0 for red and f=1 for yellow
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameActive) // if game is active and the tappedCounter is not selected already i.e tappedCounter==2
        {
            gameState[tappedCounter] = counterColor;    //setting the game state to the colour of selected counter

            if(counterColor==0)
            {
                counter.setImageResource(R.drawable.red_coin);
                counterColor=1;  //next counter would b yellow
            }
            else
            {
                counter.setImageResource(R.drawable.yellow_coin);
                counterColor=0; //next counter would be red
            }

            for(int [] myWinningPosition : winningPositions) // going through all the 8 sets of winning positions
            {

                // checking if the winning positions have same colour of counter,i.e the game states are equal at the winning positions
                if((gameState[myWinningPosition[0]]==gameState[myWinningPosition[1]] )
                        && (gameState[myWinningPosition[1]]==gameState[myWinningPosition[2]])
                        &&(gameState[myWinningPosition[0]]!=2))
                {
                    gameActive=false;
                    System.out.println("win:"+gameState[myWinningPosition[0]]); // testing if getting 0 or 1 at 0th index

                    if(gameState[myWinningPosition[0]]==0)
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
        Log.i("clicked","play again");
        LinearLayout playAgain = findViewById(R.id.playAgainLayout);
        playAgain.setVisibility(View.GONE);

         gameActive = true;
         counterColor=0;
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