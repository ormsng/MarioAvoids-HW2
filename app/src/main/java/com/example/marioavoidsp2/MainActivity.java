package com.example.marioavoidsp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ImageView game_BTN_right;
    private ImageView game_BTN_left;
    private ImageView game_IMG_mario;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initButtons();
        enemies = new ArrayList<Enemy>();
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
        }, 0, 140);
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
    }

    private void move(boolean dir) {
        if (dir && game_IMG_mario.getX() < 810) {
            game_IMG_mario.setX(game_IMG_mario.getX() + 202);
            curr_hero_x++;
        } else if (game_IMG_mario.getX() > 6 && !dir) {
            game_IMG_mario.setX(game_IMG_mario.getX() - 202);
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

        if (enemies_locations[random_number] != 1) {
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
        } else {
            Thread.sleep(12);
        }
        enemies_locations[random_number] = 1;
    }

    public void checkCollision(Enemy en) {
        if (en.getCol() == curr_hero_x) {
            if (en.getIs_coin() == true)
                System.out.println("Money!");
            else
                System.out.println("Bad!");
        }


    }

    public void shiftEnemies() throws InterruptedException {
        for (Enemy en : enemies) {
            en.getLoc().setVisibility(View.VISIBLE);
            en.getLoc().setY(en.getLoc().getY() + 23);
            Thread.sleep(8);
            en.getLoc().setY(en.getLoc().getY() + 23);
            Thread.sleep(8);
            en.getLoc().setY(en.getLoc().getY() + 23);
            if (en.getLoc().getY() >= 1445) {
                checkCollision(en);
                en.getLoc().setY(0);
                en.getLoc().setVisibility(View.INVISIBLE);
                en.getLoc().setImageResource(R.drawable.ic_mushroom);
                enemies.remove(en);
                enemies_locations[en.getCol()] = 0;
                break;
            }
        }

    }

}

