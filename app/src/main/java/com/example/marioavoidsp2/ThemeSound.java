package com.example.marioavoidsp2;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;

public class ThemeSound extends AsyncTask<Void, Void, Void> {
    private Context context;

    public ThemeSound(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        MediaPlayer player = MediaPlayer.create(this.context, R.raw.audio_theme);
        player.setLooping(true); // Set looping
        player.setVolume(1.0f, 1.0f);
        player.start();
        return null;
    }

}