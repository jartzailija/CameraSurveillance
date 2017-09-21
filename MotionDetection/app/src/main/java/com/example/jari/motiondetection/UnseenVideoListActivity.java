package com.example.jari.motiondetection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Jari on 25.5.2017.
 */

public class UnseenVideoListActivity extends AppCompatActivity {

    private ListView listview;
    private ArrayAdapter<Object> adapter;

    public UnseenVideoListActivity() {

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_list_activity);
        Log.d("onCreate", "onCreate");

        listview = (ListView) findViewById(R.id.videolistview);

        //When this activity is woken up with Intent by notification
        ArrayList<VideoDataObject> videoDataList =
                this.getIntent().getParcelableArrayListExtra(Constant.NOTIFICATION_EXTRA);

        if(videoDataList != null) {
            setListContent(videoDataList);
        }
        else {
            Log.d("videoDataList", "is null");
        }

    }

    void setListContent(ArrayList<VideoDataObject> videos) {

        VideoListAdapter vlAdapter = new VideoListAdapter(this, videos);
        listview.setAdapter(vlAdapter);
    }
}
