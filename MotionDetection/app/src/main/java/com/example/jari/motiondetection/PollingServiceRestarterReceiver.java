package com.example.jari.motiondetection;

/**
 * The only purpose for this class is to restart the backgroundService
 * Inspired by: http://fabcirablog.weebly.com/blog/creating-a-never-ending-background-service-in-android
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Jari on 25.5.2017.
 */

public class PollingServiceRestarterReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, PollingService.class));
    }
}
