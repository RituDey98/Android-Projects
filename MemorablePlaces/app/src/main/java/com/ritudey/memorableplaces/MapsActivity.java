package com.ritudey.memorableplaces;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback ,GoogleMap.OnMapLongClickListener  {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;




    public void centerOnMapLocation(Location myLocation, String title) {
        LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

        mMap.clear();        //clearing previous markers

        if(!title.equals("Your location"))
        {
            mMap.addMarker(new MarkerOptions().position(userLocation).title(title));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }

        }
    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }







    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);

        Intent intent = getIntent();
        int placeNumber = intent.getIntExtra("favPlaceNumber",0);

        if(placeNumber == 0){


            //zooming on user location
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {

                    centerOnMapLocation(location,"Your location");
                    Log.i("userLocation", String.valueOf(location));
                }
            };



            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                //we have permission
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,35000,0,locationListener);

            }else{

                //asking for permission
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }



        }else{

            Location placeLocation = new Location(LocationManager.GPS_PROVIDER);
            placeLocation.setLatitude(MainActivity.myPlaceLatLng.get(placeNumber).latitude);
            placeLocation.setLongitude(MainActivity.myPlaceLatLng.get(placeNumber).longitude);

            centerOnMapLocation(placeLocation,MainActivity.myFavPlaces.get(placeNumber)); //zoom in on fav place
        }




    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        String address = "";

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> myAddress = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            Log.i("myAddress", String.valueOf(myAddress));


            if(myAddress!=null && myAddress.size()>0){

                if(myAddress.get(0).getThoroughfare() != null) {


                    if(myAddress.get(0).getSubAdminArea() != null){


                        if(myAddress.get(0).getSubThoroughfare() != null){


                            address += myAddress.get(0).getSubThoroughfare() + " ";
                            address+=myAddress.get(0).getThoroughfare()+ ",";

                        }else{

                            if(myAddress.get(0).getLocality()!=null && myAddress.get(0).getThoroughfare().equals("Unnamed Road") ){
                                address+=myAddress.get(0).getLocality()+",";
                            }else {
                                address+= myAddress.get(0).getThoroughfare()+",";
                            }

                        }
                        address+=myAddress.get(0).getSubAdminArea();


                    }


                }

            }




        } catch (IOException e) {
            e.printStackTrace();
        }

        if(address.equals("")){

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
              SimpleDateFormat mySdf = new SimpleDateFormat("HH:mm  dd-MM-yyyy");
             address+=mySdf.format(new Date());
            }

        }

        mMap.addMarker(new MarkerOptions().position(latLng).title(address));

        MainActivity.myFavPlaces.add(address);
        MainActivity.myPlaceLatLng.add(latLng);
        MainActivity.arrayAdapter.notifyDataSetChanged();

        Toast.makeText(this, "Location saved!!", Toast.LENGTH_SHORT).show();


    }



}