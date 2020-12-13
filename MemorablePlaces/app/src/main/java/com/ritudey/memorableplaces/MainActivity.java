package com.ritudey.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView addPlaces;
    Intent intent;



    public  void OnClickAddPlaces(View v)
    {
        intent = new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addPlaces = findViewById(R.id.addTv);

    }
}