package com.example.marioavoidsp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ImageView game_BTN_right;
    private ImageView game_BTN_left;
    private ImageView game_IMG_mario;
    private ImageView[] game_IMG_hearts;
    private TextView game_TXT_score;
    private ImageView game_background;
    private CrashSound mCrashSound;
    private CoinSound mCoinSound;
    private ThemeSound mThemeSound;
    private Detector stepDetector;
    private StepCallBack stepCallBack;

    public ImageView enemy_COL_1;
    ImageView enemy_COL_2;
    ImageView enemy_COL_3;
    ImageView enemy_COL_4;
    ImageView enemy_COL_5;

    private Timer theTimer;
    private ArrayList<Enemy> enemies;
    private int[] enemies_locations = {0, 0, 0, 0, 0, 0};
    int random_number;
    int curr_hero_x = 2;
    float curr_hero_x_spec = 410;
    int lives = 3;
    int score = 0;
    boolean stopped = false;
    boolean isSpeedChecked = false;

    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";
    public static final String NAME = "NAME";

    String name;
    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game_background = findViewById(R.id.game_background);
        Glide
                .with(this)
                .load(R.drawable.mario_background)
                .centerCrop()
                .into(game_background);
        findViews();
        initButtons();
        enemies = new ArrayList<Enemy>();
        Intent prevIntent = getIntent();
        latitude = prevIntent.getExtras().getDouble(LATITUDE);
        longitude = prevIntent.getExtras().getDouble(LONGITUDE);
        name = prevIntent.getExtras().getString(NAME);
        isSpeedChecked = prevIntent.getBooleanExtra("Switch", false);
    }


    @Override
    protected void onStop() {
        super.onStop();
        stopped = true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        theTimer = new Timer();
        theTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            newEnemy();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            shiftEnemies();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //checkCollision();
                    }
                });
            }
        }, 0, 60);
    }


    private void findViews() {
        // --------------- Find Buttons ---------------

        game_BTN_right = findViewById(R.id.game_BTN_right);
        game_BTN_left = findViewById(R.id.game_BTN_left);

        // --------------- --------------- ---------------

        // --------------- Find Hero ---------------

        game_IMG_mario = findViewById(R.id.game_IMG_mario);

        // --------------- --------------- ---------------
        // --------------- Find Enemies ---------------

        enemy_COL_1 = findViewById(R.id.enemy_COL_1);
        enemy_COL_2 = findViewById(R.id.enemy_COL_2);
        enemy_COL_3 = findViewById(R.id.enemy_COL_3);
        enemy_COL_4 = findViewById(R.id.enemy_COL_4);
        enemy_COL_5 = findViewById(R.id.enemy_COL_5);


        // --------------- --------------- ---------------

        game_IMG_hearts = new ImageView[]{
                findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3),
        };

        game_TXT_score = findViewById(R.id.game_TXT_score);

        // --------------- --------------- ---------------
    }

    private void move(boolean dir) {
        if (dir && curr_hero_x_spec < 810) {
            curr_hero_x_spec += 202;
            game_IMG_mario.setX(curr_hero_x_spec);
            curr_hero_x++;
        } else if (curr_hero_x_spec > 6 && !dir) {
            curr_hero_x_spec -= 202;
            game_IMG_mario.setX(curr_hero_x_spec);
            curr_hero_x--;
        }
    }

    private void initButtons() {
        game_BTN_left.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(false);
            }
        }));

        game_BTN_right.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(true);
            }
        }));
    }


    public void newEnemy() throws InterruptedException {
        random_number = (int) Math.floor(Math.random() * 5);
        int is_coin = (int) Math.floor(Math.random() * 5);

        // Random to reduce overload
        if (enemies_locations[random_number] != 1 && (int) Math.floor(Math.random() * 5) > 2 ? true : false) {
            switch (random_number) {
                case 0: {
                    if (is_coin > 3)
                        enemy_COL_1.setImageResource(R.drawable.ic_coin);
                    Enemy generatedEnemy = new Enemy(random_number, enemy_COL_1, is_coin);
                    enemies.add(generatedEnemy);
                    break;
                }
                case 1: {
                    if (is_coin > 3)
                        enemy_COL_2.setImageResource(R.drawable.ic_coin);
                    Enemy generatedEnemy = new Enemy(random_number, enemy_COL_2, is_coin);
                    enemies.add(generatedEnemy);
                    break;
                }
                case 2: {
                    if (is_coin > 3)
                        enemy_COL_3.setImageResource(R.drawable.ic_coin);
                    Enemy generatedEnemy = new Enemy(random_number, enemy_COL_3, is_coin);
                    enemies.add(generatedEnemy);
                    break;
                }
                case 3: {
                    if (is_coin > 3)
                        enemy_COL_4.setImageResource(R.drawable.ic_coin);
                    Enemy generatedEnemy = new Enemy(random_number, enemy_COL_4, is_coin);
                    enemies.add(generatedEnemy);
                    break;
                }
                case 4: {
                    if (is_coin > 3)
                        enemy_COL_5.setImageResource(R.drawable.ic_coin);
                    Enemy generatedEnemy = new Enemy(random_number, enemy_COL_5, is_coin);
                    enemies.add(generatedEnemy);
                    break;
                }
            }
            enemies_locations[random_number] = 1;
        } else {

        }
    }

    public void checkCollision(Enemy en) {

        if (en.getCol() == curr_hero_x && stopped == false) {
            if (en.getIs_coin() == true) {
                score += 10;
                game_TXT_score.setText(Integer.toString(score));
                mCoinSound = new CoinSound(this);
                mCoinSound.execute();
                //coin_audio.cancel(true);
            } else {
                lives--;
                Toast.makeText(this, "\uD83D\uDCA5 " + lives + " lives left \uD83D\uDCA5", Toast.LENGTH_SHORT)
                        .show();
                game_IMG_hearts[lives].setVisibility(View.INVISIBLE);
                mCrashSound = new CrashSound(this);
                mCrashSound.execute();

                if (lives <= 0) {
                    saveSession();
                    Intent lostIntent = new Intent(this, LostActivity.class);
                    lostIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(lostIntent);
                    finish();
                }

            }
        } else {
            score += 5;
            game_TXT_score.setText(Integer.toString(score));
        }


    }

    public void shiftEnemies() throws InterruptedException {
        for (Enemy en : enemies) {
            en.getLoc().setVisibility(View.VISIBLE);
            for (int i = 0; i < 9; i++) {
                en.getLoc().setY(en.getY() + (isSpeedChecked ? 8 : 4));
                en.setY(en.getY() + (isSpeedChecked ? 8 : 4));
                if (en.getY() >= 1380) {
                    break;
                }
            }
            if (en.getY() >= 1380) {
                checkCollision(en);
                en.getLoc().setY(0);
                en.setY(0);
                en.getLoc().setVisibility(View.INVISIBLE);
                en.getLoc().setImageResource(R.drawable.ic_mushroom);
                enemies.remove(en);
                enemies_locations[en.getCol()] = 0;
                break;
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initStepDetector() {
        stepDetector = new Detector(this, new StepCallBack() {
            @Override
            public void sensorMove(boolean dir) {
                move(dir);
            }

        });
        stepDetector.start();
    }

    public void saveSession() {
        MyDB myDB;
        String json = MSPv3.getInstance(this).getStringSP("MY_DB", "");
        myDB = new Gson().fromJson(json, MyDB.class);
        if (myDB == null)
            myDB = new MyDB();
        myDB.getRecords().add(
                new Record()
                        .setName(name)
                        .setTime(System.currentTimeMillis() / (1000 * 60 * 60 * 24))
                        .setScore(score)
                        .setLat(latitude)
                        .setLon(longitude)
        );
        myDB.sortRecords();
        json = new Gson().toJson(myDB);
        MSPv3.getInstance(this).putStringSP("MY_DB", json);
        String fromJSON = MSPv3.getInstance(this).getStringSP("MY_DB", "");
        MyDB newDb = new Gson().fromJson(fromJSON, MyDB.class);
    }

}



