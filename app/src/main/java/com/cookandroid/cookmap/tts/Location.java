package com.cookandroid.cookmap.tts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Location extends AppCompatActivity {

    protected double latitude, longitude, altitude; //위도,경도,해발고도
    protected Button btcheck,btPlace,button2;
    protected TextView tvLatitude, tvLongitude, tvAltitude;
    protected LocationManager locationManager;
    protected MyLocationListener myLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        tvAltitude = (TextView) findViewById(R.id.tvAltitude);
        tvLongitude = (TextView) findViewById(R.id.tvLongtitude);
        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        button2 = (Button)findViewById(R.id.Button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Location.this,SMS.class);
                startActivity(intent);

            }
        });
        btcheck = (Button) findViewById(R.id.btCheck);
        btcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitude = myLocationListener.latitude;
                longitude = myLocationListener.longitude;
                altitude = myLocationListener.altitude;
                tvLatitude.setText(String.format("%g", latitude));
                tvLongitude.setText(String.format("%g", longitude));
                tvAltitude.setText(String.format("%g", altitude));
                String str =String.format("geo:%g,%g?z=15",latitude,longitude);//위도,경도,20까지만 확대가능

                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(str));
                startActivity(intent);

            }
        });
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        long nMinTime = 1000;
        float minDistance = 0;
        myLocationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, nMinTime, minDistance, myLocationListener);

        btPlace = (Button)findViewById(R.id.btPlace);
        btPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geocoder geocoder; //지오는 땅
                geocoder = new Geocoder(getApplicationContext(), Locale.KOREAN); //코리아가아닌 코리안으로하기
                try {
                    List<Address> isAddress;
                    isAddress =geocoder.getFromLocation(latitude,longitude,1);
                    Address address = isAddress.get(0);
                    String placeName = address.getFeatureName();
                    Toast.makeText(getApplicationContext(),placeName,Toast.LENGTH_SHORT).show(); //토스트메세지주기
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });




    }
}


