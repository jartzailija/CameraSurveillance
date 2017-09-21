package com.example.jari.motiondetection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Jari on 27.5.2017.
 * Edited from https://developer.android.com/guide/topics/ui/layout/gridview.html
 */

public class ImageGalleryAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ImageDataObject> imageData;

    public ImageGalleryAdapter(Context c, ArrayList<ImageDataObject> imageData) throws JSONException {
        mContext = c;
        this.imageData = imageData;
    }


    public int getCount() {
        return imageData.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }


    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        ImageDataObject singleImageData = imageData.get(position);

        //TODO: menee näistä rikki
        //imageView.setTag(Constant.IMAGE_NAME_TAG, singleImageData.name.toString());
        //imageView.setTag(Constant.VIDEO_ID_TAG, singleImageData.videoId);

        new ImageDownloader(imageView)
                .execute(Constant.IMAGES_URL_BEGIN + singleImageData.videoId + "/" + singleImageData.thumbnailName);
        return imageView;
    }

}
