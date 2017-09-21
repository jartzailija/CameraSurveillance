//Stolen from http://android-er.blogspot.fi/2012/03/play-media-on-surfaceview-using.html
package com.example.jari.motiondetection;
import java.io.IOException;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class VideoActivity extends AppCompatActivity
        implements SurfaceHolder.Callback {

    Uri targetUri;

    //This is for image gallery
    String videoId;

    MediaPlayer mediaPlayer;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean pausing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.video_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        targetUri = Uri.parse(this.getIntent().getStringExtra(Constant.VIDEO_URL));
        videoId = this.getIntent().getStringExtra(Constant.VIDEO_ID);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView)findViewById(R.id.surfaceview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFixedSize(176, 144);
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(getApplicationContext(), targetUri);
            mediaPlayer.prepare();
        } catch (IOException e) {
            //TODO: error -toast
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.play:
                playVideo();
                return true;
            case R.id.pause:
                pauseVideo();
                return true;
            case R.id.open_gallery:
                openGallery();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void playVideo() {
        pausing = false;

        if(mediaPlayer.isPlaying()){
            mediaPlayer.reset();
        }

        //This is important to do after the view rendering
        //because otherwise surfaceview will prevent menu rendering
        mediaPlayer.setDisplay(surfaceHolder);

        try {
            mediaPlayer.setDataSource(getApplicationContext(), targetUri);
            mediaPlayer.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();
    }

    private void pauseVideo() {
        if(pausing){
            pausing = false;
            mediaPlayer.start();
        }
        else{
            pausing = true;
            mediaPlayer.pause();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(this, ImageGalleryActivity.class);
        galleryIntent.putExtra(Constant.VIDEO_ID, videoId);

        this.startActivity(galleryIntent);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mediaPlayer.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("onCreateOptionsMenu", "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.video_player, menu);
        return true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        mediaPlayer.release();
    }

}