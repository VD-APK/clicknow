package com.example.clicknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MyCurrentLocation extends AppCompatActivity implements LocationListener {
    Button button_location;
    TextView textView_location;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_current_location);
        textView_location = findViewById(R.id.text_location);
        button_location = findViewById(R.id.button_location);
        if (ContextCompat.checkSelfPermission(MyCurrentLocation.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MyCurrentLocation.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);


        }


        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, MyCurrentLocation.this);
            Toast.makeText(this,"load gps ",Toast.LENGTH_SHORT);
            } catch (Exception e)
        {
            e.printStackTrace();
        }


}
        @Override
    public void onLocationChanged(Location location) {
            Toast.makeText(this, " "+location.getLatitude()+"  ," +location.getLongitude(),Toast.LENGTH_SHORT).show();
            try {
                Geocoder geocoder = new Geocoder(MyCurrentLocation.this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                String address = addresses.get(0).getAddressLine(0);
                textView_location.setText(address);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}