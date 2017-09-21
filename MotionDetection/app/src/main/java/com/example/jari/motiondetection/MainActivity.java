package com.example.jari.motiondetection;

//Inspired by: http://fabcirablog.weebly.com/blog/creating-a-never-ending-background-service-in-android
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //private Intent pollingService;
    private Intent backgroundServiceIntent;
    private PollingService backgroundService;
    private ListView listview;
    private ArrayAdapter<Object> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Set up a background server polling service
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Receive intent from pollingservice with an alert receiver
        IntentFilter statusIntentFilter = new IntentFilter(
                Constant.BROADCAST_ACTION);

        AlertReceiver alertReceiver = new AlertReceiver();

        LocalBroadcastManager.getInstance(this).registerReceiver(
                alertReceiver,
                statusIntentFilter);

        Context context = this.getBaseContext();

        backgroundService = new PollingService(context);
        backgroundServiceIntent = new Intent(context, PollingService.class);

        if (!isMyServiceRunning(PollingService.class)) {
            startService(backgroundServiceIntent);
        }

        //Get all videos and set them to the list
        listview = (ListView) findViewById(R.id.fullvideolistview);

        VideoListLoader videoListLoader = new VideoListLoader();

        ///BAAAAD WAY TO UPDATE THE VIEW, IT MIGHT FREEZE
        try {
            ArrayList<VideoDataObject> videos = videoListLoader.execute(Constant.ALL_VIDEOS).get();
            if(videos != null) {
                setListContent(videos);
            }
            else {
                Log.d("videoDataList", "is null");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    private void setListContent(ArrayList<VideoDataObject> videos) {

        VideoListAdapter vlAdapter = new VideoListAdapter(this, videos);
        listview.setAdapter(vlAdapter);
    }





    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        //stopService(pollingService);
        stopService(backgroundServiceIntent);
        super.onDestroy();

    }


    private class VideoListLoader extends AsyncTask<String, Void, ArrayList<VideoDataObject>> {

        @Override
        protected ArrayList<VideoDataObject> doInBackground(String... url) {
            ArrayList<VideoDataObject> videos;

            Connection connection = new Connection();

            String imageListJson = connection.getDataOverWeb(url[0]);
            videos = getVideoData(imageListJson);
            return videos;

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
}
