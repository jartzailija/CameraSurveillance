package com.example.jari.motiondetection;

/**
 * Created by Jari on 26.5.2017.
 */

public class Constant {

    //Message keys
    public static final String VIDEO_DATA_BUNDLE = "com.example.jari.motiondetection.VIDEO_DATA_BUNDLE";
    public static final String RESTART_POLLING_SERVICE = "com.example.jari.motiondetection.RestartPollingService";
    public static final String BROADCAST_ACTION = "com.example.jari.motiondetection.BROADCAST";
    public static final String EXTENDED_DATA_URL = "com.example.jari.motiondetection.URL";
    public static final String NOTIFICATION_EXTRA = "com.example.jari.motiondetection.NOTIFICATION_EXTRA";
    public static final String VIDEO_URL = "com.example.jari.motiondetection.VIDEO_URL";
    public static final String VIDEO_ID = "com.example.jari.motiondetection.VIDEO_ID";
    public static final String IMAGE_URL = "com.example.jari.motiondetection.IMAGE_URL";
    public static final int IMAGE_NAME_TAG = 1;
    public static final int VIDEO_ID_TAG = 2;

    //Urls
    public static final String BASIC_URL = "http://tolkki.eu:3000";
    //public static final String BASIC_URL = "http://146.148.116.10:3000";
    public static final String CHEKING_URL = BASIC_URL + "/videos/unseen";
    public static final String VIDEO_IMAGES_URL_BEGIN = BASIC_URL + "/videoid/";
    public static final String IMAGES_URL_BEGIN = BASIC_URL + "/images/";
    public static final String ALL_VIDEOS = BASIC_URL + "/videos/";

    //Other settings
    public static final int POLLING_DELAY = 6000;
}
