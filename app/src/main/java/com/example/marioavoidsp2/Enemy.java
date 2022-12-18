package com.example.marioavoidsp2;

import android.widget.ImageView;

public class Enemy {
    private int col;
    private ImageView place;
    private boolean is_coin;

    public boolean getIs_coin() {
        return is_coin;
    }

    public void setIs_coin(boolean is_coin) {
        this.is_coin = is_coin;
    }

    public ImageView getLoc() {
        return place;
    }

    public int getCol() {
        return col;
    }

    public void setLoc(ImageView loc) {
        this.place = place;
    }

    public Enemy(int col, ImageView place, int is_coin) {
        this.col = col;
        this.place = place;
        if(is_coin>3)
            setIs_coin(true);
        else
            setIs_coin(false);
    }
}
