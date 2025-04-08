package com.example.tanksgame.util;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.tanksgame.R;

public class MusicManager {
    private static MediaPlayer mediaPlayer;
    private static boolean isPlaying = false;

    public static void startMusic(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.default_music);
            mediaPlayer.setLooping(true); // Loop the music
        }

        if (!isPlaying) {
            mediaPlayer.start();
            isPlaying = true;
        }
    }

    public static void stopMusic() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
        }
    }
}

