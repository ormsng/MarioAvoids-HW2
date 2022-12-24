package com.example.marioavoidsp2;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;

public class CoinSound extends AsyncTask<Void, Void, Void> {
    private Context context;

    public CoinSound(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        MediaPlayer player = MediaPlayer.create(this.context, R.raw.audio_coin);
        player.setLooping(false); // Set looping
        player.setVolume(1.0f, 1.0f);
        player.start();

        return null;
    }

}