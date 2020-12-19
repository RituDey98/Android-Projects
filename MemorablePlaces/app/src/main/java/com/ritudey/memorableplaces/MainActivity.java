package com.ritudey.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
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


        SharedPreferences sharedPreferences = this.getSharedPreferences("com.ritudey.memorableplaces",MODE_PRIVATE); // for this app only
            ArrayList<String> latitudes = new ArrayList<>();
            ArrayList<String> longitudes = new ArrayList<>();
            try {

            myFavPlaces = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("myFavPlaces",ObjectSerializer.serialize(new ArrayList<String>())));
            latitudes=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latitudes",ObjectSerializer.serialize(new ArrayList<String>())));
            longitudes=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longitudes",ObjectSerializer.serialize(new ArrayList<String>())));

            } catch (IOException e) {

            e.printStackTrace();
        }

            if(myFavPlaces.size() > 0 && latitudes.size()>0 && longitudes.size() >0){

                if(myFavPlaces.size() == latitudes.size() && latitudes.size() == longitudes.size()){

                    for(int i=0;i<latitudes.size();i++){

                        myPlaceLatLng.add(new LatLng(Double.parseDouble(latitudes.get(i)),Double.parseDouble(longitudes.get(i))));
                    }

                }

            }else {

                myFavPlaces.add("+ Add a new place");
                myPlaceLatLng.add(new LatLng(0,0));
            }


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