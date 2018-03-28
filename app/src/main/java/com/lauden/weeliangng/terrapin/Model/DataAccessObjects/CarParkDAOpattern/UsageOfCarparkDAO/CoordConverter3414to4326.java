package com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CarParkDAOpattern.UsageOfCarparkDAO;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by XWSOON on 10/28/2017.
 */

public class CoordConverter3414to4326 {
    public static String[] getConvertedValue(String x_coord, String y_coord){

        // Strict mode is necessary
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {

            // Connection to the relevant API

            URL url = new URL("https://developers.onemap.sg/commonapi/convert/3414to4326?X=" + x_coord + "&Y=" + y_coord);

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();


            // Code responsible for retrieving Raw JSON to be parsed

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String RawJson = buffer.toString(); // Assign Raw Json String into RawJson String Variable

            JSONObject jsonObj = new JSONObject(RawJson);

            String x_lat = jsonObj.getString("latitude");
            String y_lng = jsonObj.getString("longitude");

            Log.d("Coordinates Converter ", x_lat + "," + y_lng);


            return new String[] {x_lat, y_lng}; // return the completed CarPark Object Array List

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally

        {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
