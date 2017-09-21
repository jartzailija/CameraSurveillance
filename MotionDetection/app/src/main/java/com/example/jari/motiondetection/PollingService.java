package com.example.jari.motiondetection;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jari on 25.5.2017.
 */

public class PollingService extends Service {

    private Timer timer;
    private TimerTask timerTask;
    //long oldTime = 0;
    private Connection connection;
    private Context context;

    public PollingService(Context applicationContext) {
        this();
        context = applicationContext;
    }

    public PollingService() {
        super();
        connection = new Connection();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent(Constant.RESTART_POLLING_SERVICE);
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 5000, Constant.POLLING_DELAY); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                String data  = connection.getDataOverWeb(Constant.CHEKING_URL);
                sendDataToAlertReceiver(data);
            }
        };
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void sendDataToAlertReceiver(String message) {
        Intent localIntent =
                new Intent(Constant.BROADCAST_ACTION)
                        // Puts the status into the Intent
                        .putExtra(Constant.EXTENDED_DATA_URL, message);
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}
