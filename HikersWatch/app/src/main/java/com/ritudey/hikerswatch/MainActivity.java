package com.ritudey.hikerswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    TextView addressTV,latitudeValue,longitudeValue,altitude,accuracy;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
            }
        }
    }

    public void updateLocation(Location myLocation)
    {
        Log.i("userLocation", String.valueOf(myLocation));

        latitudeValue.setText(String.valueOf((int) myLocation.getLatitude()));
        longitudeValue.setText(String.valueOf((int) myLocation.getLongitude()));
        accuracy.setText(String.valueOf((int) myLocation.getAccuracy()));
        altitude.setText(String.valueOf((int) myLocation.getAltitude()));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressTV=findViewById(R.id.addressTV);
        latitudeValue=findViewById(R.id.latitudeValueTV);
        longitudeValue=findViewById(R.id.longitudeValueTv);
        altitude=findViewById(R.id.altitudeValueTV);
        accuracy=findViewById(R.id.accuracyValueTV);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                updateLocation(location);

                //getting user address
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {

                    List<Address> userAddress = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    if(userAddress !=null && userAddress.size()> 0) {

                        Log.i("userLocation", String.valueOf(userAddress));
                        String address = "";
                        if (userAddress.get(0).getAddressLine(0) != null) {
                            address += userAddress.get(0).getAddressLine(0);
                        }
                        addressTV.setText(address);
                    }

                } catch (IOException e) {

                    e.printStackTrace();
                }

            }
        };


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            //ask for permission
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

        }else {

            //we have permission
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        }


    }
}