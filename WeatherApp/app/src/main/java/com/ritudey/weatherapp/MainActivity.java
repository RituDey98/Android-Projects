package com.ritudey.weatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.PersistableBundle;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {

    TextView temp,main,descriptionV,humidityV,pressV,windV,city;
   Group mainGroup;
    EditText cityEditText;
   ImageView iconWeather;
   String iconName,cityName,myResult="";


    public void onSearchClick(View v){


      cityName= cityEditText.getText().toString();
       Log.i("name",cityName);

       //closing the keyboard after taking the input
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityEditText.getWindowToken(),0);

        if(cityName.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Oops,no input! Please provide an input :)", Toast.LENGTH_SHORT).show();
        }
        else{

            DownloadTask task = new DownloadTask();
            try {
                Log.i("name",cityName);

                String encodedString = URLEncoder.encode(cityName, "UTF-8"); //encoding special characters  for url
                task.execute("https://api.openweathermap.org/data/2.5/weather?q="+encodedString+"&appid=899bfe16917fd6d4ebcda376ca3d2b4c");


            }catch (Exception e)
            {
                e.printStackTrace();
            }


        }


    }






    public void changeIcon(String icon)
    {
        switch (icon){
            case "01d":
                iconWeather.setImageResource(R.drawable.img01d);
                break;
            case "01n":
                iconWeather.setImageResource(R.drawable.img01n);
                break;
            case "02d":
                iconWeather.setImageResource(R.drawable.img02d);
                break;
            case "02n":
                iconWeather.setImageResource(R.drawable.img02n);
                break;
            case "03d":
            case "03n":
                iconWeather.setImageResource(R.drawable.img03d);
                break;
              case "04d":
            case "04n":
                iconWeather.setImageResource(R.drawable.img04d);
                break;
            case "09d":
            case "09n":
                iconWeather.setImageResource(R.drawable.img09d);
                break;
            case "10d":
                iconWeather.setImageResource(R.drawable.img10d);
                break;
            case "10n":
                iconWeather.setImageResource(R.drawable.img10n);
                break;
            case "11d":
            case "11n":
                iconWeather.setImageResource(R.drawable.img11d);
                break;
            case "13d":
            case "13n":
                iconWeather.setImageResource(R.drawable.snow);
                break;
            case "50d":
            case "50n":
                iconWeather.setImageResource(R.drawable.mist);
                break;

            default:
                iconWeather.setImageResource(R.drawable.ic_baseline_cloud_24);

        }
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        temp = findViewById(R.id.tempTV);
        main = findViewById(R.id.mainTV);
        descriptionV =findViewById(R.id.descValue);
        humidityV=findViewById(R.id.humidityValue);
        pressV=findViewById(R.id.pressureValue);
        windV=findViewById(R.id.windValue);
        cityEditText = findViewById(R.id.searchBarET);
        iconWeather =findViewById(R.id.iconWeather);
        city =findViewById(R.id.cityNameTV);
        mainGroup=findViewById(R.id.group1);

    }








    public class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {

            URL url;
            HttpsURLConnection myConnection =null;


            try {
                url = new URL(urls[0]);
                myConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = myConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();
                while (data!=-1)
                {
                    char current  = (char) data;
                    myResult+=current;
                    data =reader.read();
                }

                return myResult;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);



                if(result!=null){

                    try {
                        Log.i("weather", result);
                        JSONObject jsonObject = new JSONObject(result);
                        String weatherInfo = jsonObject.getString("weather");
                        String tempIno = jsonObject.getString("main");
                        String name = jsonObject.getString("name");
                        String sysInfo = jsonObject.getString("sys");
                        String windInfo = jsonObject.getString("wind");

                        Log.i("infoRitu", weatherInfo);
                        Log.i("infoRitu", tempIno);
                        Log.i("city",name);

                        //for temperature
                        JSONObject tempJsonObj = new JSONObject(tempIno);
                        JSONObject sysInfoObj = new JSONObject(sysInfo);
                        JSONObject windInfoObj = new JSONObject(windInfo);
                        double kelvin = Double.parseDouble(tempJsonObj.getString("temp"));
                        double cel = Math.round((kelvin - 273.15) * 100.0) / 100.0;
                        temp.setText(Double.toString(cel));
                        humidityV.setText(tempJsonObj.getString("humidity"));
                        pressV.setText(tempJsonObj.getString("pressure"));
                        city.setText(String.format("%s , %s", name, sysInfoObj.getString("country")));
                        windV.setText(windInfoObj.getString("speed"));

                        //for weather
                        JSONArray arrWeather = new JSONArray(weatherInfo);
                        for (int i = 0; i < arrWeather.length(); i++) {


                            JSONObject jsonObjectPart = arrWeather.getJSONObject(i);

                            main.setText(jsonObjectPart.getString("main"));
                            descriptionV.setText(jsonObjectPart.getString("description"));
                            iconName = jsonObjectPart.getString("icon");
                            Log.i("icon", iconName);

                        }
                        changeIcon(iconName);

                        myResult="";

                        temp.setVisibility(View.VISIBLE);
                        main.setVisibility(View.VISIBLE);
                        descriptionV.setVisibility(View.VISIBLE);
                        humidityV.setVisibility(View.VISIBLE);
                        pressV.setVisibility(View.VISIBLE);
                        windV.setVisibility(View.VISIBLE);
                        city.setVisibility(View.VISIBLE);
                        mainGroup.setVisibility(View.VISIBLE);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Couldn't find the weather for this location  :(", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(), "Couldn't find the weather for this location  :(", Toast.LENGTH_SHORT).show();
                }


        }




    }

}