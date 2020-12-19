package com.ritudey.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView addPlaces;
    Intent intent;
    ListView myListView;
    static ArrayList<String> myFavPlaces= new ArrayList<>();
    static ArrayList<LatLng> myPlaceLatLng =new ArrayList<>();
    static ArrayAdapter arrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myListView=findViewById(R.id.listView);

        myFavPlaces.add("+ Add a new place");
        myPlaceLatLng.add(new LatLng(0,0));

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,myFavPlaces);
        myListView.setAdapter(arrayAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //take to the next activity here

                intent = new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("favPlaceNumber",position);
                startActivity(intent);
            }
        });

    }
}