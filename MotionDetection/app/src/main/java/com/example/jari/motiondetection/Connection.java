package com.example.jari.motiondetection;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Jari on 19.5.2017.
 * TODO: THROW ERROR!
 */

public class Connection {
    public String getDataOverWeb(String pUrl) {

        String content = "false";
        String line = "";
        HttpURLConnection connection = null;
        try {

            URL url = new URL(pUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            connection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            content = "";
            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }


        return content;
    }
}
