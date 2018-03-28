package com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern;

import android.app.Activity;
import android.os.StrictMode;
import android.util.Log;

import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.CameraFactory;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.CameraStrategy;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.MapUIUtlity.ExtractAddress;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CameraSingleton;
import com.lauden.weeliangng.terrapin.Model.Utility.CurrentTime;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CameraDAOImplement implements CameraDAO{
    Activity mActivity;
    List<LTACameraObject> cameraList;

    //String name[] = new String[86];
    static String[] name = {"KPE: KPE/ECP", "KPE: Kallang Bahru" , "KPE: KPE/PIE", "KPE: Kallang Way Flyover", "KPE: Defu Flyover", "KPE: KPE 8.5km",
            "MCE: View from Maxwell Rd", "MCE: Marina Boulevard/Marina Coastal Drive", "MCE: 1.02km", "MCE: MCE/ECP", "MCE: Marina Boulevard",
            "CTE: Moulmain Flyover (Towards AYE)", "CTE: Braddell Flyover (Towards SLE)", "CTE: St George Rd (Towards SLE)", "CTE: Entrance from Chin Swee Rd", "CTE: AMK Ave 5 Flyover (Towards City)", "CTE: Bukit Merah Flyover", "CTE: Exit 6 to Bukit Timah Rd", "CTE: Ang Mo Kio Ave 1 Flyover","NA",
            "Woodlands Causeway (Towards Johor)", "Near Woodlands Checkpoint (Towards BKE)",
            "BKE: Chantek Flyover", "BKE: Woodlands Flyover", "BKE: Dairy Farm Flyover", "BKE: After KJE Exit", "BKE: Mandai Rd Entrance", "BKE: Exit 5 to KJE (Towards Checkpoint)", "BKE: Woodlands South Flyover (Towards BKE)",
            "ECP: Entrance from PIE (Changi)", "ECP: Entrance from MCE", "ECP: Exit 2A to Changi Coast Rd", "ECP: Laguna Flyover (Towards Changi)", "ECP: Marine ParadeFlyover (Towards AYE)", "ECP: Tanjung Katong Flyover (Towards Changi)", "ECP: Tanjung Rhu (Towards AYE)", "ECP: Benjamin Sheares Bridge",
            "AYE: Alexandar Road (Towards ECP)", "AYE: Keppel Viaduct", "AYE: Lower Delta Road (Towards Tuas)", "AYE: Entrance from Yuan Ching Rd", "AYE: Near NUS (Towards Tuas)", "AYE: Entrance from Jin Ahmad Ibrahim", "AYE: Near Dover ITE (Towards ECP)","NA", "AYE: Towards Pandan Gardens", "AYE: After Tuas West Road", "AYE: Near West Coast Walk", "AYE: Entrance from Benoi Rd",
            "Second Link at Tuas", "Tuas Checkpoint",
            "Sentosa Gateway: Towards HabourFont", "Sentosa Gateway: Towards Sentosa",
            "PIE: Bedok North (Towards Jurong)", "PIE: Eunos Flyover (Towards Jurong)", "PIE: Paya Lebar Flyover (Towards Jurong)", "PIE: Kallang", "PIE: Woodsvilie Flyover (Towards Changi)", "PIE: Kim Keat (Towards Changi)", "PIE: Thomson Rd", "PIE: Mount Pleasant (Towards Changi)", "PIE: Adam Rd (Towards Changi)", "PIE: BKE (Towards Changi)", "PIE: Jurong West St 81 (Towards Jurrong)", "PIE: Entrance from Jalan Anak Bukit", "PIE: Entrance to PIE from ECP Changi", "PIE: Exit 27 to Clementi Ave 6", "PIE: Entrance from Simei Aue", "PIE: Exit 35 to KJE", "PIE: Hong Kah Flyover", "PIE: Tuas Flyover",
            "TPE: Upper Changi Flyover", "TPE: Rivervale Drive (Towards SLE)", "NA","NA","NA","NA",
            "KJE: Choa Chu Kang(Towards PIE)", "KJE: Exit to BKE", "KJE: Entrance From Choa Chu Kang Dr", "KJE: Tengah Flyover",
            "SLE: Selatar Flyover (Towards BKE)", "Lentor Flyover (Towards TPE)","NA","NA","NA","NA"
    };

    static String[][] name2 = {{"1001","KPE: KPE/ECP"}, {"1002","KPE: Kallang Bahru"} , {"1003","KPE: KPE/PIE"}, {"1004","KPE: Kallang Way Flyover"}, {"1005","KPE: Defu Flyover"}, {"1006","KPE: KPE 8.5km"},
            {"1501","MCE: View from Maxwell Rd"}, {"1502","MCE: Marina Boulevard/Marina Coastal Drive"}, {"1503","MCE: 1.02km"}, {"1504","MCE: MCE/ECP"}, {"1505","MCE: Marina Boulevard"},
            {"1701","CTE: Moulmain Flyover (Towards AYE)"}, {"1702","CTE: Braddell Flyover (Towards SLE)"}, {"1703","CTE: St George Rd (Towards SLE)"}, {"1704","CTE: Entrance from Chin Swee Rd"}, {"1705","CTE: AMK Ave 5 Flyover (Towards City)"}, {"1706","CTE: Bukit Merah Flyover"}, {"1707","CTE: Exit 6 to Bukit Timah Rd"}, {"1709","CTE: Ang Mo Kio Ave 1 Flyover"},
            {"2701","Woodlands Causeway (Towards Johor)"}, {"2702","Near Woodlands Checkpoint (Towards BKE)"},
            {"2703","BKE: Chantek Flyover"}, {"2704","BKE: Woodlands Flyover"}, {"2705","BKE: Dairy Farm Flyover"}, {"2706","BKE: After KJE Exit"}, {"2707","BKE: Mandai Rd Entrance"}, {"2708","BKE: Exit 5 to KJE (Towards Checkpoint)"}, {"9703","BKE: Woodlands South Flyover (Towards BKE)"},
            {"3702","ECP: Entrance from PIE (Changi)"}, {"3704","ECP: Entrance from MCE"}, {"3705","ECP: Exit 2A to Changi Coast Rd"},
            {"3793","ECP: Laguna Flyover (Towards Changi)"}, {"3795","ECP: Marine ParadeFlyover (Towards AYE)"}, {"3796","ECP: Tanjung Katong Flyover (Towards Changi)"}, {"3797","ECP: Tanjung Rhu (Towards AYE)"}, {"3798","ECP: Benjamin Sheares Bridge"},
            {"4701","AYE: Alexandar Road (Towards ECP)"}, {"4702","AYE: Keppel Viaduct"}, {"4704","AYE: Lower Delta Road (Towards Tuas)"}, {"4705","AYE: Entrance from Yuan Ching Rd"}, {"4706","AYE: Near NUS (Towards Tuas)"}, {"4707","AYE: Entrance from Jin Ahmad Ibrahim"}, {"4708","AYE: Near Dover ITE (Towards ECP)"}, {"4710","AYE: Towards Pandan Gardens"}, {"4712","AYE: After Tuas West Road"}, {"4714","AYE: Near West Coast Walk"}, {"4716","AYE: Entrance from Benoi Rd"},
            {"4703","Second Link at Tuas"}, {"4713","Tuas Checkpoint"},
            {"4798","Sentosa Gateway: Towards HabourFont"}, {"4799","Sentosa Gateway: Towards Sentosa"},
            {"5794","PIE: Bedok North (Towards Jurong)"}, {"5795","PIE: Eunos Flyover (Towards Jurong)"}, {"5797","PIE: Paya Lebar Flyover (Towards Jurong)"}, {"5798","PIE: Kallang"}, {"5799","PIE: Woodsvilie Flyover (Towards Changi)"},
            {"6701","PIE: Kim Keat (Towards Changi)"}, {"6703","PIE: Thomson Rd"}, {"6704","PIE: Mount Pleasant (Towards Changi)"}, {"6705","PIE: Adam Rd (Towards Changi)"}, {"6706","PIE: BKE (Towards Changi)"}, {"6708","PIE: Jurong West St 81 (Towards Jurrong)"}, {"6710","PIE: Entrance from Jalan Anak Bukit"}, {"6711","PIE: Entrance to PIE from ECP Changi"}, {"6712","PIE: Exit 27 to Clementi Ave 6"}, {"6713","PIE: Entrance from Simei Aue"},
            {"6714","PIE: Exit 35 to KJE"}, {"6715","PIE: Hong Kah Flyover"}, {"6716","PIE: Tuas Flyover"},
            {"7791","TPE: Upper Changi Flyover", "TPE: Rivervale Drive (Towards SLE)"}, {"8701","KJE: Choa Chu Kang(Towards PIE)"}, {"8702","KJE: Exit to BKE"}, {"8704","KJE: Entrance From Choa Chu Kang Dr"}, {"8706","KJE: Tengah Flyover"},
            {"9704", "SLE: Selatar Flyover (Towards BKE)"}, {"9703","Lentor Flyover (Towards TPE)"}
    };

   /* String[][] newArray = {{"France", "Blue"}, {"Ireland", "Green"}};
    int columns = 2;
    int rows = 2;

    String[][] newArray = new String[columns][rows];
    newArray[0][0] = "France";
    newArray[0][1] = "Blue";

    newArray[1][0] = "Ireland";
    newArray[1][1] = "Green";*/

   /*
    for(int i = 0; i < rows; i++){
        for(int j = 0; j < columns; j++){
            System.out.println(newArray[i][j]);
        }
    }*/


    public CameraDAOImplement(){

        cameraList = new ArrayList<LTACameraObject>();
        cameraList = getCameraObjectList();

    }


    public CameraDAOImplement(Activity act) {
        this.mActivity = act;
        if(cameraList == null) {
            cameraList = new ArrayList<LTACameraObject>();
            cameraList = getCameraObjectList(act);
        }
    }


    @Override
    public List<LTACameraObject> getAllCameras() {

        if (cameraList == null){
            cameraList = getCameraObjectList();
        }
        return cameraList;
    }

    @Override
    public List<LTACameraObject> getAllCameras(Activity act) {

        if (cameraList == null){
            cameraList = getCameraObjectList(act);
        }
        return cameraList;
    }

    @Override
    public LTACameraObject getCameraObject(int id) {
        return cameraList.get(id);
    }

    @Override
    public void UpdateCameraObject(LTACameraObject cmObj) {
    for(int y = 0 ; y<cameraList.size();y++){
        if(cameraList.get(y).getCamera_id().equals(cmObj.getCamera_id())){
            cameraList.get(y).setName(cmObj.getName());
            cameraList.get(y).setCamera_id(cmObj.getCamera_id());
            cameraList.get(y).setTimestamp(cmObj.getTimestamp());
            cameraList.get(y).setLat(cmObj.getLat());
            cameraList.get(y).setLon(cmObj.getLon());
        }
    }
    }

    @Override
    public void UpdateCameraObjectList() {
        cameraList = getCameraObjectList(mActivity);
    }

    @Override
    public void DeleteCameraObject(LTACameraObject cmObj) {
        for(int y = 0 ; y<cameraList.size();y++){
            if(cameraList.get(y).getCamera_id().equals(cmObj.getCamera_id())){
               cameraList.remove(y);
            }
        }
    }

    @Override
    public void DeleteCameraObjectList() {
        cameraList.clear();
        cameraList = new ArrayList<>();
    }


    public static List<LTACameraObject> getCameraObjectList(){

        String storedate;
        CameraFactory camFactory = new CameraFactory();
        CameraStrategy camStrat = null;


        List<LTACameraObject> mLTACameraObjectList = new ArrayList<>();



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        storedate = CurrentTime.getCurrentTimeStamp();

        try {
            URL url = new URL("https://api.data.gov.sg/v1/transport/traffic-images?date_time=" + storedate);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("api-key", "SkfDAyH1OAYncI9PmA4drLGFlwF6HgzA");

            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String finalJson = buffer.toString();

            JSONObject parentObject = new JSONObject(finalJson);
            JSONArray parentArray = parentObject.getJSONArray("items");
             JSONObject semiFinalObject = parentArray.getJSONObject(0);

            JSONArray sonArray = semiFinalObject.getJSONArray("cameras");

            StringBuffer finalBufferedData = new StringBuffer();


            for (int i = 0; i < sonArray.length(); i++) {

                //Log.d(TAG, "Updating Traffic camera #" + i,
                //  new Exception());
            JSONObject sonObject = sonArray.getJSONObject(i);


                //LTACameraObject LTACameraObject = new LTACameraObject();
                camStrat = camFactory.getCameraType("LTA");
                LTACameraObject LTACameraObject = (LTACameraObject) camStrat;

                LTACameraObject.setTimestamp(sonObject.getString("timestamp"));
                LTACameraObject.setCamera_id(sonObject.getString("camera_id"));
                LTACameraObject.setImage(sonObject.getString("image"));

                JSONObject grandson = sonObject.getJSONObject("location");

                //location is an object
                LTACameraObject.setLon(grandson.getDouble("longitude"));

                LTACameraObject.setLat(grandson.getDouble("latitude"));

                LTACameraObject.setName(name[i]);
                Log.d("Checking Name Into LTACameraObject "+ i , LTACameraObject.getName());

                //  Log.d("Find latlong ", String.valueOf(LTACameraObject.getLat())+" "+String.valueOf(LTACameraObject.getLon()) );


                mLTACameraObjectList.add(LTACameraObject);
          }
            Collections.sort(mLTACameraObjectList, new CamIdComparator());

            for(int y = 0; y < mLTACameraObjectList.size(); y++){
                //   mLTACameraObjectList.get(y).setName("NA");

                for(int u = 0; u < name2.length;u++){
                    //  Log.i("try 2DMarix ", String.valueOf(name2[u][0]));
                    if (mLTACameraObjectList.get(y).getCamera_id().equals(name2[u][0])){
                        mLTACameraObjectList.get(y).setName(name2[u][1]);

                    }
                }

                   //    for(int j = 0; j < columns; j++){
                //      System.out.println(newArray[i][j]);
            }

            return mLTACameraObjectList;

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



    public static List<LTACameraObject> getCameraObjectList(Activity act){

        String storedate;

        //Factory Pattern Usage
        CameraFactory camFactory = new CameraFactory();
        CameraStrategy camStrat = null;


        List<LTACameraObject> mLTACameraObjectList = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        storedate = CurrentTime.getCurrentTimeStamp();

        try {
            URL url = new URL("https://api.data.gov.sg/v1/transport/traffic-images?date_time=" + storedate);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("api-key", "SkfDAyH1OAYncI9PmA4drLGFlwF6HgzA");

            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String finalJson = buffer.toString();

            JSONObject parentObject = new JSONObject(finalJson);
            JSONArray parentArray = parentObject.getJSONArray("items");
            JSONObject semiFinalObject = parentArray.getJSONObject(0);

            JSONArray sonArray = semiFinalObject.getJSONArray("cameras");

            StringBuffer finalBufferedData = new StringBuffer();


            for (int i = 0; i < sonArray.length(); i++) {

                //Log.d(TAG, "Updating Traffic camera #" + i,
                //  new Exception());
                JSONObject sonObject = sonArray.getJSONObject(i);


               //Factory Pattern Usage
                camStrat = camFactory.getCameraType("LTA");
                LTACameraObject LTACameraObject = (LTACameraObject) camStrat;


                LTACameraObject.setTimestamp(sonObject.getString("timestamp"));
                LTACameraObject.setCamera_id(sonObject.getString("camera_id"));
                LTACameraObject.setImage(sonObject.getString("image"));

                JSONObject grandson = sonObject.getJSONObject("location");

                //location is an object
                LTACameraObject.setLon(grandson.getDouble("longitude"));

                LTACameraObject.setLat(grandson.getDouble("latitude"));

                mLTACameraObjectList.add(LTACameraObject);
            }
           Collections.sort(mLTACameraObjectList, new CamIdComparator());

        /*    for(int y = 0; y < mLTACameraObjectList.size(); y++){
                if (name[y].equals("NA")) {
                    mLTACameraObjectList.get(y).setName( ExtractAddress.getAddressFromLatLng(new LatLng(mLTACameraObjectList.get(y).getLat(), mLTACameraObjectList.get(y).getLon()),act));
                } else{
                   mLTACameraObjectList.get(y).setName(name[y]);
                }
                Log.d("Checking Name Into LTACameraObject "+ y , mLTACameraObjectList.get(y).getName()+" ID: "+ mLTACameraObjectList.get(y).getCamera_id());
            }
*/

            for(int y = 0; y < mLTACameraObjectList.size(); y++){
             //   mLTACameraObjectList.get(y).setName("NA");

                for(int u = 0; u < name2.length;u++){
                  //  Log.i("try 2DMarix ", String.valueOf(name2[u][0]));
                    if (mLTACameraObjectList.get(y).getCamera_id().equals(name2[u][0])){
                        mLTACameraObjectList.get(y).setName(name2[u][1]);

                    }
            }

                if (mLTACameraObjectList.get(y).getName()== null) {
                    mLTACameraObjectList.get(y).setName( ExtractAddress.getAddressFromLatLng(new LatLng(mLTACameraObjectList.get(y).getLat(), mLTACameraObjectList.get(y).getLon()),act));
                }
            //    for(int j = 0; j < columns; j++){
              //      System.out.println(newArray[i][j]);
                }



            return mLTACameraObjectList;

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


    static class CamIdComparator implements Comparator<LTACameraObject> {

        public int compare(LTACameraObject cam1, LTACameraObject cam2) {
        return cam1.getCamera_id().compareTo(cam2.getCamera_id());
          }
    }


}