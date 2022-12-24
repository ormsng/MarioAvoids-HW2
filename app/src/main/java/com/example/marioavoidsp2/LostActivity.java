package com.example.marioavoidsp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.marioavoidsp2.Enemy;
import com.example.marioavoidsp2.R;

import java.util.ArrayList;
import java.util.Timer;

public class LostActivity extends AppCompatActivity {
    private BoardFragment boardFragment;
    private MapFragment mapFragment;
    private Button menu_BTN_playagain;

    CallBack_userProtocol callBack_userProtocol = new CallBack_userProtocol() {
        @Override
        public void setLocation(String name, double lat, double lon) {
            mapFragment.setLocation(name, lat, lon);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        boardFragment = new BoardFragment();
        boardFragment.setCallback(callBack_userProtocol);
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.lost_board, boardFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.lost_map, mapFragment).commit();
        findViews();
        initButtons();
    }

    private void initButtons() {

        menu_BTN_playagain.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(LostActivity.this, MenuActivity.class);
                mainIntent.addFlags(mainIntent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(mainIntent);
            }
        }));
    }
    private void findViews() {
        // --------------- Find Score Layout ---------------

        menu_BTN_playagain = findViewById(R.id.menu_BTN_playagain);


        // --------------- --------------- ---------------
    }


}