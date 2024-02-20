package com.example.clicknow;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.clicknow.databinding.ActivityUserDashBoardBinding;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class UserDashBoard extends AppCompatActivity implements LocationListener {


    ImageView saloonImage;
    ImageView acMechanicImage;
    ImageView BikeMechanicImage;
    ImageView CarMechanicImage;

    TextView textView_location;
    LocationManager locationManager;
    ImageView HomeCleaningImage;
    ImageView PaintingImage;
    ImageView PestControlImage;
    ImageView BathroomCleaningImage;
    ImageView ElectricianImage;
    ImageView AppliancesRepairImage;
    ImageView PlumberandCarpenterImage;
    ImageView HomeRepairImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);
        textView_location = findViewById(R.id.text_location);
        if (ContextCompat.checkSelfPermission(UserDashBoard.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UserDashBoard.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);


        }
        getLocation();
        saloonImage = findViewById(R.id.saloonImage);
        acMechanicImage = findViewById(R.id.acMechanicImage);
        BikeMechanicImage = findViewById(R.id.bikeMechanicImage);
        CarMechanicImage = findViewById(R.id.carmechanicImage);
        HomeCleaningImage = findViewById(R.id.fullhomecleaningservicemage);
        PaintingImage = findViewById(R.id.paintingImage);
        PestControlImage = findViewById(R.id.pestcontrolImage);
        BathroomCleaningImage = findViewById(R.id.bathroomcleanigImage);
        ElectricianImage = findViewById(R.id.electricianImage);
        AppliancesRepairImage = findViewById(R.id.appliancesrepairImage);
        PlumberandCarpenterImage = findViewById(R.id.plumberandcarpenterImage);
        HomeRepairImage = findViewById(R.id.homerepairImage);

        saloonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserDashBoard.this, SaloonActivity.class);

                UserDashBoard.this.startActivity(myIntent);
            }
        });

        PaintingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserDashBoard.this, MyCurrentLocation.class);

                UserDashBoard.this.startActivity(myIntent);
            }
        });
        acMechanicImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserDashBoard.this, AcMechanic.class);

                UserDashBoard.this.startActivity(myIntent);
            }
        });


        BikeMechanicImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserDashBoard.this, BikeMechanic.class);

                UserDashBoard.this.startActivity(myIntent);

            }
        });
        CarMechanicImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserDashBoard.this, CarMechanic.class);

                UserDashBoard.this.startActivity(myIntent);

            }
        });
        HomeCleaningImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserDashBoard.this, HomeCleaning.class);

                UserDashBoard.this.startActivity(myIntent);

            }
        });
//        PaintingImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myIntent = new Intent(UserDashBoard.this, Painting.class);
//
//                UserDashBoard.this.startActivity(myIntent);
//
//            }
//        });
        PestControlImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserDashBoard.this, PestControl.class);

                UserDashBoard.this.startActivity(myIntent);

            }
        });
        BathroomCleaningImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserDashBoard.this, BathroomCleaning.class);

                UserDashBoard.this.startActivity(myIntent);

            }
        });
        ElectricianImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserDashBoard.this, Electrician.class);

                UserDashBoard.this.startActivity(myIntent);

            }
        });
        AppliancesRepairImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserDashBoard.this, AppliancesRepair.class);

                UserDashBoard.this.startActivity(myIntent);

            }
        });
        PlumberandCarpenterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserDashBoard.this, PlumberandCarpenter.class);

                UserDashBoard.this.startActivity(myIntent);

            }
        });
        HomeRepairImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserDashBoard.this, HomeRepair.class);

                UserDashBoard.this.startActivity(myIntent);

            }
        });

    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, UserDashBoard.this);

            }
            else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, UserDashBoard.this);
                Toast.makeText(this,"Enable  gps from settings to load location ",Toast.LENGTH_SHORT);
            }

            return;
        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, " "+location.getLatitude()+"  ," +location.getLongitude(),Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(UserDashBoard.this, Locale.getDefault());
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