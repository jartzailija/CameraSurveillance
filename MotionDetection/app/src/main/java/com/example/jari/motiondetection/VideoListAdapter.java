package com.example.jari.motiondetection;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by Jari on 26.5.2017.
 */

public class VideoListAdapter extends ArrayAdapter<VideoDataObject> {

    Context context;

    public VideoListAdapter(Context context, ArrayList<VideoDataObject> videos) {
        super(context, 0, videos);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        VideoDataObject videoData = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.video_list_item, parent, false);
        }

        //Set a time to as a name
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        tvTime.setText(dateFormat.format(Long.parseLong(videoData.getEpochTime())).toString());

        //This is for video downloading
        tvTime.setTag(R.id.url, videoData.getUrl());
        //This is for image gallery
        tvTime.setTag(R.id.videoId, videoData.getId());

        //Set a click listener
        tvTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String url = (String) view.getTag(R.id.url);
                String id = (String) view.getTag(R.id.videoId);

                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra(Constant.VIDEO_URL, url);
                intent.putExtra(Constant.VIDEO_ID, id);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

}
