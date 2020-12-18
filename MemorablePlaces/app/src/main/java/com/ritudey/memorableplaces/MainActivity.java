package com.ritudey.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView addPlaces;
    Intent intent;
    ListView myListView;
    ArrayList<String> myFavPlaces;


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
        myListView=findViewById(R.id.listView);

        myFavPlaces = new ArrayList<String>();
        myFavPlaces.add("Rik");
        myFavPlaces.add("Ritu");
        myFavPlaces.add("Rik");
        myFavPlaces.add("Ritu");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,myFavPlaces);
        myListView.setAdapter(arrayAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //take to the next activity here
            }
        });

    }
}