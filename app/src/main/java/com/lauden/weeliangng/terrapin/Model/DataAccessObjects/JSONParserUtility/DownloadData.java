package com.lauden.weeliangng.terrapin.Model.DataAccessObjects.JSONParserUtility;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jun Wei on 4/11/2017.
 */

public class DownloadData {
    public String readUrl(String url){
        String data = null;
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        URL urlObject = null;
        try {
            urlObject = new URL(url);
            urlConnection = (HttpURLConnection) urlObject.openConnection();
            urlConnection.connect();

            inputStream  = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while((line = br.readLine())!= null){
                sb.append(line);
            }

            data = sb.toString();
            Log.d("DownloadData",data);

            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if(inputStream !=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            urlConnection.disconnect();
        }

        return data;
    }
}
