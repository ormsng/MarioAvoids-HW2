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

public class MenuActivity extends AppCompatActivity {

    Button menu_BTN_start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        initButtons();
    }

    private void initButtons() {
        menu_BTN_start.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        }));

    }

    private void startGame(){
        Intent mainIntent = new Intent(MenuActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(mainIntent);
        finish();
    }


    private void findViews() {
        // --------------- Find Buttons ---------------

        menu_BTN_start = findViewById(R.id.menu_BTN_start);


        // --------------- --------------- ---------------
    }


}