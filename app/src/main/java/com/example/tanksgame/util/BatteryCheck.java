package com.example.tanksgame.util;

import static android.R.drawable.ic_lock_idle_low_battery;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AlertDialog;

public class BatteryCheck extends BroadcastReceiver {
    private static final int LOW_BATTERY = 30;

    @Override
    public void onReceive(Context context, Intent intent) {
        int batteryPercent = intent.getIntExtra("level", 0);

        if (batteryPercent < LOW_BATTERY) {
            timerDelayRemoveDialog(
                    3000,
                    new AlertDialog.Builder(context).setTitle("LowButtery").setIcon(ic_lock_idle_low_battery).show()
            );
        }
    }

    public void timerDelayRemoveDialog(long time, final Dialog dialog) {
        new Handler(Looper.getMainLooper()).postDelayed(dialog::dismiss, time);
    }
}
