package com.example.jari.motiondetection;

import android.util.Log;

/**
 * Created by Jari on 28.5.2017.
 */

public class ImageDataObject {

    public String name;
    public String thumbnailName;
    public String videoId;

    public ImageDataObject(String name, String thumbnailName, String videoId) {
        this.name = name;
        this.thumbnailName = thumbnailName;
        this.videoId = videoId;

    }
}
