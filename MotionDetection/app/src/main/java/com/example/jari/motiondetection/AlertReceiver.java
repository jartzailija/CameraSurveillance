package com.example.jari.motiondetection;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Jari on 19.5.2017.
 */

public class AlertReceiver extends BroadcastReceiver {

    private NotificationCompat.Builder mBuilder;


    public AlertReceiver() {

    }

    //Set notifaction, if got alert
    @Override
    public void onReceive(Context context, Intent intent) {

        ArrayList<VideoDataObject> videoData = getVideoData(intent.getStringExtra(Constant.EXTENDED_DATA_URL));
        if(videoData != null){
            setNotification(context, videoData);
            sendNotification(context);
        }
    }

    private void sendNotification(Context context) {
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(001, mBuilder.build());
    }

    private void setNotification(Context context, ArrayList<VideoDataObject> videoData) {
        //Get a normal notification sound
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Set a notification builder
        mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("MotionDetection")
                .setContentText("Motion detected, watch a new video")
                .setPriority(2)
                .setSound(alarmSound);

        Intent notificationIntent = new Intent(context, UnseenVideoListActivity.class);
        notificationIntent.putExtra(Constant.NOTIFICATION_EXTRA, videoData);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
    }

    /**
     * Parse incoming data and find out if there is an alert
     * @param json
     */
    private ArrayList<VideoDataObject> getVideoData(String json) {
        JSONObject parsedJson = null;
        ArrayList<VideoDataObject> videoData = null;
        try {

            parsedJson = new JSONObject(json);
            if(parsedJson.getJSONArray("results").length() > 0) {

                videoData = new ArrayList<VideoDataObject>();

                JSONArray videoObjs = parsedJson.getJSONArray("results");

                for(int i = 0; i < videoObjs.length(); i++) {

                    Object rawVideoObj = videoObjs.get(i);
                    JSONObject videoObj = (JSONObject)rawVideoObj;
                    VideoDataObject tmpObj = new VideoDataObject();

                    tmpObj.setUrl(videoObj.get("url").toString());
                    tmpObj.setTimeStr(videoObj.get("time").toString());
                    tmpObj.setEpochTime(videoObj.get("epochTime").toString());
                    tmpObj.setName(videoObj.get("name").toString());
                    tmpObj.setId(videoObj.get("id").toString());

                    videoData.add(tmpObj);

                }
                return videoData;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } /*catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
        Log.d("return", "null");
        return null;
    }
}
