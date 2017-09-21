package com.example.jari.motiondetection;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Jari on 29.5.2017.
 */

public class SingleImageAdapter extends BaseAdapter {

    private Context mContext;
    private String imageUrl;

    public SingleImageAdapter(Context c, String imageUrl) throws JSONException {
        mContext = c;
        this.imageUrl = imageUrl;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;
        /*if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setTag(Constant.IMAGE_NAME_TAG, singleImageData.name);
        imageView.setTag(Constant.VIDEO_ID_TAG, singleImageData.videoId);

        new ImageDownloader(imageView)
                .execute(Constant.IMAGES_URL_BEGIN + singleImageData.videoId + "/" + singleImageData.thumbnailName);*/
        return imageView;
    }
}
