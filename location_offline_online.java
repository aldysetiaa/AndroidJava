package com.atharvakale.facerecognition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.atharvakale.facerecognition.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class locationAct extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int RESQUEST_CODE=100;
    TextView latitude,longitude,address,city,country;
    Button getLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

//        getSupportActionBar().hide();

        latitude = findViewById(R.id.text_latitude);
        longitude = findViewById(R.id.text_longitude);
        address = findViewById(R.id.text_address);
        city = findViewById(R.id.text_city);
        country = findViewById(R.id.text_country);
        getLocation = findViewById(R.id.btn_getLocation);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
                        Log.d("Latitude", String.valueOf(location.getLatitude()));
                        Log.d("Longitude", String.valueOf(location.getLongitude()));
                        latitude.setText("LATITUDE: " + String.valueOf(location.getLatitude()));
                        longitude.setText("LONGITUDE: " + location.getLongitude());
                        Geocoder geocoder = new Geocoder(locationAct.this, Locale.getDefault());
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
                            latitude.setText("LATITUDE: " + location.getLatitude());
                            longitude.setText("LONGITUDE: " + location.getLongitude());
                            address.setText("ADDRESS: " + addresses.get(0).getAddressLine(0));
                            city.setText("CITY: " + addresses.get(0).getLocality());
                            country.setText("COUNTRY: " + addresses.get(0).getCountryName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }else{
            //  ActivityCompat.requestPermissions(locationAct.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},RESQUEST_CODE);
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(locationAct.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},RESQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == RESQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else {
                Toast.makeText(this, "Please provide required permissions", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
