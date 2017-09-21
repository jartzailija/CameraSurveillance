package com.example.jari.motiondetection;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;


/**
 * Created by Jari on 25.5.2017.
 * Structure like a data object
 * All data is in string format due to simplicity
 */

public class VideoDataObject implements Parcelable {

    private String name;
    private String timeStr;
    private String epochTime;
    private String url;
    private String id;

    public VideoDataObject() {
    }

    protected VideoDataObject(Parcel in) {
        String[] data = new String[5];
        in.readStringArray(data);

        setName(data[0]);
        setTimeStr(data[1]);
        setEpochTime(data[2]);
        setUrl(data[3]);
        setId(data[4]);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(String epochTime) {
        this.epochTime = epochTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static final Creator<VideoDataObject> CREATOR = new Creator<VideoDataObject>() {
        @Override
        public VideoDataObject createFromParcel(Parcel in) {
            return new VideoDataObject(in);
        }

        @Override
        public VideoDataObject[] newArray(int size) {
            return new VideoDataObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                getName(),
                getTimeStr(),
                getEpochTime(),
                getUrl(),
                getId()
        });
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
