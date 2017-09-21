package com.example.jari.motiondetection;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Jari on 27.5.2017.
 */

public class ImageGalleryActivity extends AppCompatActivity {

    private String videoId;
    private ArrayList<ImageDataObject> imageObjList;
    private GridView gridview;
    private Context context;

    public ImageGalleryActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        context = getApplicationContext();

        videoId = this.getIntent().getStringExtra(Constant.VIDEO_ID);
        gridview = (GridView) findViewById(R.id.gallerygrid);

        ImgNameLoader nameLoader = new ImgNameLoader();

        try {
            //This is very very bad habit to handle asyc tasks...
            //It downloads image names which are connected to the video
            //TODO: add a progress bar or something
            //TODO: toast if an error happens

            imageObjList = nameLoader.execute(videoId).get();
            gridview.setAdapter(new ImageGalleryAdapter(this, imageObjList));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        /*gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = gridview.getChildAt(position).getTag(Constant.IMAGE_NAME_TAG).toString();
                String videoId = gridview.getChildAt(position).getTag(Constant.VIDEO_ID_TAG).toString();

                Intent intent = new Intent(context, ImageActivity.class);
                intent.putExtra(Constant.IMAGE_URL, Constant.IMAGES_URL_BEGIN + videoId + "/" + name);
                context.startActivity(intent);
            }
        });

        //Pressing listener
        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });*/
    }

    private class ImgNameLoader extends AsyncTask<String, Void, ArrayList<ImageDataObject>> {

        private String videoId;

        @Override
        protected ArrayList<ImageDataObject> doInBackground(String... pVideoId) {
            ArrayList<ImageDataObject> nameList = null;
            videoId = pVideoId[0];
            try {
                nameList = getImageDataFromWeb(videoId);

            } catch (JSONException e) {
                e.printStackTrace();
                //TODO: throw error
            }

            return nameList;
        }

        public ArrayList<ImageDataObject> getImageDataFromWeb(String videoId) throws JSONException {
            ArrayList<ImageDataObject> imgData = new ArrayList<ImageDataObject>();
            Connection connection = new Connection();

            String imageListJson = connection.getDataOverWeb(Constant.VIDEO_IMAGES_URL_BEGIN + videoId);
            imgData = parseImageData(imageListJson);
            return imgData;
        }

        public ArrayList<ImageDataObject> parseImageData(String imageListJson) throws JSONException {
            ArrayList<ImageDataObject> imgDataArr = new ArrayList<ImageDataObject>();

            JSONObject parsedJson = new JSONObject(imageListJson);
            JSONArray results = parsedJson.getJSONArray("results");

            for(int i = 0; i < results.length(); i++) {
                Object rawVideoObj = results.get(i);
                JSONObject videoObj = (JSONObject)rawVideoObj;


                ImageDataObject tmpObj = new ImageDataObject(
                        videoObj.get("name").toString(),
                        videoObj.get("thumbnail").toString(),
                        videoId.toString()
                );

                imgDataArr.add(tmpObj);
            }

            return imgDataArr;
        }
    }

}
