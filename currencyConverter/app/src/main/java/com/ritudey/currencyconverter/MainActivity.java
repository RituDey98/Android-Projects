package com.ritudey.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public void onClickConverter(View view){
        Log.i("check","clicked");
        EditText currency = findViewById(R.id.editTextCurrency);
        Double c = Double.parseDouble(currency.getText().toString());
        Double inr = c * 73.4331;

        Toast.makeText(this,String.format("%.2f",inr),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}