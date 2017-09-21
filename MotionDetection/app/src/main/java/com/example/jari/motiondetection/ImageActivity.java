package com.example.jari.motiondetection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jari on 28.5.2017.
 */

public class ImageActivity extends AppCompatActivity {

    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);

        imageUrl = this.getIntent().getStringExtra(Constant.IMAGE_URL);


    }
}
