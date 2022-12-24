package com.example.marioavoidsp2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.marioavoidsp2.Enemy;
import com.example.marioavoidsp2.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class MenuActivity extends AppCompatActivity {

    Button menu_BTN_start;
    Button menu_BTN_ldrboard;
    ImageView menu_background;
    TextView name_enter;
    double longitude;
    double latitude;
    String name;
    SwitchMaterial speed_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        menu_background = findViewById(R.id.menu_background);
        Glide
                .with(this)
                .load(R.drawable.menu_background)
                .centerCrop()
                .into(menu_background);
        findViews();
        initButtons();
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        123);
                return;
            }
        }

        getLocation();
    }

    private void initButtons() {
        menu_BTN_start.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = name_enter.getText().toString();
                if (!(name_enter.getText().toString().trim().length() == 0))
                    startGame(v);
                else
                    Toast.makeText(MenuActivity.this, "Please fill name", Toast.LENGTH_SHORT).show();
            }
        }));

        menu_BTN_ldrboard.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLeaderboard(v);
            }
        }));

    }

    private void startGame(View v) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.putExtra(MainActivity.LONGITUDE, longitude);
        mainIntent.putExtra(MainActivity.LATITUDE, latitude);
        mainIntent.putExtra(MainActivity.NAME, name);
        mainIntent.putExtra("Switch", speed_switch.isChecked());
        mainIntent.addFlags(mainIntent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(mainIntent);
        finish();
    }


    private void startLeaderboard(View v) {
        Intent lostIntent = new Intent(this, LostActivity.class);
        lostIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(lostIntent);
        finish();
    }


    private void findViews() {
        // --------------- Find Buttons ---------------

        menu_BTN_start = findViewById(R.id.menu_BTN_start);
        menu_BTN_ldrboard = findViewById(R.id.menu_BTN_ldrboard);
        name_enter = findViewById(R.id.name_enter);
        speed_switch = findViewById(R.id.speed_switch);

        // --------------- --------------- ---------------
    }

    public void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = myLocation.getLongitude();
        latitude = myLocation.getLatitude();
        if (myLocation == null) {
            myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Please enable location permission", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}