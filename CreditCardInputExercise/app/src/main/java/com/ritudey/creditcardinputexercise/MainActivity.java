package com.ritudey.creditcardinputexercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.health.SystemHealthManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ConstraintLayout mainLayout;
    TextInputEditText cardNumET,expDateET,cvvET,fNameET,lNameET;
    TextInputLayout cardNumTL,expDateTL,cvvTL,fNameTL,lNameTL;

    String cardNum,expiryDate,cvv,fName,lName;

    boolean validityCardNum=false;
    boolean validityLName=false,validityFName=false;


    //pressing the submit button calls this method
    public void onClickSubmit(View v)
    {
        cardNum = String.valueOf(cardNumTL.getEditText().getText());
        expiryDate = String.valueOf(expDateTL.getEditText().getText());
        cvv = String.valueOf(cvvTL.getEditText().getText());
        fName = String.valueOf(fNameTL.getEditText().getText());
        lName = String.valueOf(lNameTL.getEditText().getText());



            Log.i(TAG, cardNum);
            Log.i(TAG, expiryDate);
            Log.i(TAG, cvv);
            Log.i(TAG, fName);

            //hiding the keyboard after submit
            mainLayout = findViewById(R.id.main_layout);
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

            if (cardNum != null) {

                validityCardNum = validityCheckCardNum(cardNum);
                if (!validityCardNum) {
                    Log.i("validityCheckCard", "invalid");
                    setErrorMessage("Please enter a valid card number", cardNumTL);


                } else {
                    Log.i("validityCheckcard", "valid");
                    resetError(cardNumTL);

                }

            } else {
                validityCardNum = false;
                Log.i("validityCard", "invalid");
                setErrorMessage("Please enter a valid card number", cardNumTL);
            }


            //checking the validity of cvv
            if (!validityCheckCvv(cvv)) {
                Log.i("validityCheckCvv", "invalid");
                setErrorMessage("Please enter a valid cvv", cvvTL);

            } else {
                Log.i("validityCheckCvv", "valid");
                resetError(cvvTL);
            }


            //checking validity of the expiry date
            if (!validityCheckExpiryDate(expiryDate)) {
                Log.i("validityCheckExp", "invalid");
                setErrorMessage("Please enter a valid date", expDateTL);

            } else {
                Log.i("validityCheckExp", "valid");
                resetError(expDateTL);
            }


            //checking validity for name
            if (fName != null && lName != null) {

                if (!fName.matches("[\\p{L} .'-]+")) {
                    Log.i("validityCheckName", "invalid");
                    setErrorMessage("Please enter a valid first name", fNameTL);
                    validityFName = false;

                } else {
                    Log.i("validityCheckName", "valid");
                    resetError(fNameTL);
                    validityFName = true;

                }

                if (!lName.matches("[\\p{L} .'-]+")) {
                    Log.i("validityCheckName", "invalid");
                    setErrorMessage("Please enter a valid last name", lNameTL);
                    validityLName = false;

                } else {
                    Log.i("validityCheckName", "valid");
                    resetError(lNameTL);
                    validityLName = true;
                }

            } else {
                Log.i("validityCheckName", "invalid");
                setErrorMessage("Please enter a valid first name", fNameTL);
                setErrorMessage("Please enter a valid last name", lNameTL);
                validityFName = false;
                validityLName = false;

            }


            // showing alert dialog box
            if (validityCardNum && validityCheckCvv(cvv) && validityCheckExpiryDate(expiryDate) && validityFName && validityLName) {
                new AlertDialog.Builder(this)
                        .setTitle("Payment Successful")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("Done", "done");
                            }
                        }).setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }else{

                Toast.makeText(this, "Please provide valid inputs", Toast.LENGTH_SHORT).show();
            }



}





    public  int getSize(String num){
        //Log.i("validitySize", String.valueOf((num.length())));
        return num.length();
    }



    // return this cardNumber if it is a single digit, otherwise,
    // return the sum of the two digits
    public int getDigit(int num){

        if(num < 9)
            return num;
        return num/10 + num % 10;

    }


    public long getPrefix(String num,int prefixSize){

        //getting the prefix
        if(getSize(num) > (prefixSize)){
            return Long.parseLong(num.substring(0,  prefixSize));
        }
        return Long.parseLong(num);

    }



    public boolean prefixMatch(String num,int myPrefix)
    {
        //checking if the prefix returned is equal the required prefix
        String prefix = String.valueOf(myPrefix);
        Log.i("validityPrefix", (prefix));
        return getPrefix(num,getSize(prefix)) == myPrefix;

    }




    // sumOdd and sumDouble shows the implementation of Luhn's algorithm
    public int sumOdd(String num)
    {
        int sum =0;
        for(int i = getSize(num) - 1; i >=0 ; i-=2){

            sum += Integer.parseInt(String.valueOf(num.charAt(i))); //adding the alternative numbers from extreme right that were not doubled
            Log.i("validitySum", String.valueOf(sum));
        }
        return sum;
    }



    public int sumDouble(String num){

        int sum = 0;
        for(int i = getSize(num)-2 ; i>=0; i-=2){

            sum+=getDigit(Integer.parseInt(String.valueOf(num.charAt(i))) * 2); //adding the doubled numbers
            Log.i("validitySumDouble", String.valueOf(sum));
        }

        return  sum;
    }





    //validating the card number
    public boolean validityCheckCardNum(String num) {

        //returns true if valid
                return ( (getSize(num)>=13 && getSize(num) <= 16)   &&
                (prefixMatch(num,4) || prefixMatch(num,5) || prefixMatch(num,6) || prefixMatch(num,37))
                && (sumOdd(num) + sumDouble(num)) % 10 == 0);

    }

    //validating the cvv number
    public boolean validityCheckCvv(String num)
    {
        if(num!=null || !num.equals("")) {

            if (prefixMatch(cardNum, 37)) {

                if (getSize(num) == 4) //Checking validity for American express card
                {
                    return true;
                } else if (getSize(num) != 4) {

                    return false;
                }
            } else if (prefixMatch(cardNum, 4) || prefixMatch(cardNum, 5) || prefixMatch(cardNum, 6)) {

                if (getSize(num) == 3) //for the other cards
                {

                    return true;

                } else if (getSize(cvv) != 3) {


                    return false;
                }
            }
        }else {
            return false;
        }
        return false;
    }



    //checking expiry date validity
    public boolean validityCheckExpiryDate(String num){


        if(num!=null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
            if(!num.matches("[01][0-9]/[0-9][0-9]")){
                return false;
            }

            sdf.setLenient(false);
            try{
               sdf.parse(num);
                return true;
            }catch (ParseException e)
            {
                return false;
            }

        }else {
            return false;
        }

    }




    public void setErrorMessage(String msg,TextInputLayout v)
    {
       // Log.i("validityCheck","Invalid");
        v.setErrorEnabled(true);
        v.setError(msg);
    }



    public void resetError(TextInputLayout v)
    {
        Log.i("validityCheck","valid");
        v.setErrorEnabled(false);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the edit text views
        cardNumET=findViewById(R.id.cardNumberEditText);
        expDateET =findViewById(R.id.expiryDateEditText);
        cvvET=findViewById(R.id.cvvEditText);
        fNameET=findViewById(R.id.fNameEditText);
        lNameET=findViewById(R.id.lNameEditText);

        //getting the text input layouts
        cardNumTL=findViewById(R.id.cardNumberTL);
        expDateTL=findViewById(R.id.expireDateTL);
        cvvTL=findViewById(R.id.cvvTL);
        fNameTL=findViewById(R.id.firstNameTL);
        lNameTL=findViewById(R.id.lastNameTL);


    }

}