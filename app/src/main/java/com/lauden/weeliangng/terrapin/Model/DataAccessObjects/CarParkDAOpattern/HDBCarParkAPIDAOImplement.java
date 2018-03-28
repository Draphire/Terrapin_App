package com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CarParkDAOpattern;


import android.content.Context;
import android.os.StrictMode;

import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.CarParkFactory;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.CarParkStrategy;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CarParkSingletonPattern;
import com.lauden.weeliangng.terrapin.Model.Utility.CurrentTime;
import com.lauden.weeliangng.terrapin.R;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class HDBCarParkAPIDAOImplement implements CarParkDAO {
    Context mContext;

    List<HDBCarParkObject> carparkList = new ArrayList<>();

    public HDBCarParkAPIDAOImplement(Context mCon) {
        this.mContext = mCon;
        carparkList = readCarparkObjectRawData(mCon);

    }


    @Override
    public List<HDBCarParkObject> getAllCarParks() {
        return carparkList;
    }

    @Override
    public HDBCarParkObject getCarParkObject(int id) {

        return carparkList.get(id);
    }

    @Override
    public void UpdateCarParkObject(HDBCarParkObject cmObj) {
        /*for(int y = 0 ; y<carparkList.size();y++){
            if(carparkList.get(y).getCamera_id().equals(cmObj.getCamera_id())){
                carparkList.get(y).setName(cmObj.getName());
                carparkList.get(y).setCamera_id(cmObj.getCamera_id());
                carparkList.get(y).setTimestamp(cmObj.getTimestamp());
                carparkList.get(y).setLat(cmObj.getLat());
                carparkList.get(y).setLon(cmObj.getLon());
            }
        }*/
    }

    @Override
    public void UpdateCarParkObjectList() {
        carparkList = readCarparkObjectRawData(mContext);
    }

    @Override
    public void DeleteCarParkObject(HDBCarParkObject cmObj) {

    }

    @Override
    public void DeleteCarParkObjectList() {
        carparkList.clear();
        carparkList = new ArrayList<>();

    }


    public static List<HDBCarParkObject> getCarParkList() {

        String storedate;

        //Factory Pattern Usage
        CarParkFactory carFactory = new CarParkFactory();
        CarParkStrategy carStrat = null;


        List<HDBCarParkObject> mHDBCarParkObjectList = new ArrayList<>();


        // Strict mode is necessary
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        storedate = CurrentTime.getCurrentTimeStamp(); // Method for getting current time in relevant format

        try {

            // Connection to the relevant API

            URL url = new URL("https://api.data.gov.sg/v1/transport/carpark-availability?date_time=" + storedate);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("api-key", "SkfDAyH1OAYncI9PmA4drLGFlwF6HgzA");
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

            JSONObject parentObject = new JSONObject(RawJson); // Objectify Raw JSON String
            JSONArray parentArray = parentObject.getJSONArray("items"); // Search String 'items' then convert into Array
            JSONObject semiFinalObject = parentArray.getJSONObject(0); // Objectify first item (only first Necessary) in the Array

            JSONArray sonArray = semiFinalObject.getJSONArray("carpark_data");
            // Search String 'carpark data' then convert into Array we need all items in this array

            for (int i = 0; i < sonArray.length(); i++) { //Traverse the Array


                JSONObject sonObject = sonArray.getJSONObject(i); // Objectify first Array Item


                // accessing nested items in JSON
                JSONArray grandSonArray = sonObject.getJSONArray("carpark_info"); // Search String 'carpark info' then convert into Array
                JSONObject grandSonObject = grandSonArray.getJSONObject(0); // Objectify first item (only first Necessary) in the Array


                //Factory Pattern Usage
                carStrat = carFactory.getCarParkType("HDB");
                HDBCarParkObject carparkObjectHDB = (HDBCarParkObject) carStrat;

                // HDBCarParkObject carparkObjectHDB = new HDBCarParkObject(); // Create new CarPark Object to Set and add in CarPark Object Array List

                carparkObjectHDB.setTotalLots(grandSonObject.getString("total_lots")); // Search String 'total_lots' and set attribute into object
                carparkObjectHDB.setLotType(grandSonObject.getString("lot_type")); // Search String 'lot_type' and set attribute into object
                carparkObjectHDB.setLotsAvailable(grandSonObject.getString("lots_available")); // Search String 'lots_available' and set attribute into object
                // end of accessing nested items

                carparkObjectHDB.setCarParkNo(sonObject.getString("carpark_number")); // Search String 'carpark_number' and set attribute into object
                carparkObjectHDB.setUpdateDateTime(sonObject.getString("update_datetime")); // Search String 'update_datetime' and set attribute into object


                mHDBCarParkObjectList.add(carparkObjectHDB);
            }


            return mHDBCarParkObjectList; // return the completed CarPark Object Array List

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



    //This method gets all Main Carpark info using Factory Pattern
    public static List<HDBCarParkObject> readCarparkObjectRawData(Context mContext) {
        InputStream is = mContext.getResources().openRawResource(R.raw.hdb_carpark_information_fin);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        //Factory Pattern Usage
        CarParkFactory carFactory = new CarParkFactory();
        CarParkStrategy carStrat = null;


        List<HDBCarParkObject> mCarparkInfo = new ArrayList<>();


        String line = "";
        try {
            // Step over headers
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                // Log.d("CarparkAvailability", "Line: " + line);
                // Split by ','
                String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                // Read the data
                //Factory Pattern Usage
                carStrat = carFactory.getCarParkType("HDB");
                HDBCarParkObject CPInfo = (HDBCarParkObject) carStrat;


            //    com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject CPInfo = new com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject();

                CPInfo.setCarParkNo(tokens[0]);
                CPInfo.setAddress(tokens[1]);
                CPInfo.setX_coord(Double.parseDouble(tokens[2]));
                CPInfo.setY_coord(Double.parseDouble(tokens[3]));

                CPInfo.setCar_park_type(tokens[4]);
                CPInfo.setType_of_parking_system(tokens[5]);
                CPInfo.setShort_term_parking(tokens[6]);
                CPInfo.setFree_parking(tokens[7]);
                CPInfo.setNight_parking(tokens[8]);
                CPInfo.setLatitude(Double.parseDouble(tokens[9]));
           //     Log.d("Test CarPark", String.valueOf(Double.parseDouble(tokens[9])));
             CPInfo.setLongitude(Double.parseDouble(tokens[10]));

                mCarparkInfo.add(CPInfo);
                //Log.d("Test CarPark",CPInfo.getAddress());
            }
        } catch (IOException e) {
            //Log.wtf("CarparkAvailability", "Error reading info file" + line, e);
        }
        return mCarparkInfo;
    }


    //This method gets existing Lots info array contained in singleton Pattern
    public static String getCurrentLotstatic(HDBCarParkObject cpitem, Context mCon){

        // Uses Singleton Pattern to get Lots, DAO in readCarparkObjectRawData to get all info on carpark
        List<HDBCarParkObject> withLots = CarParkSingletonPattern.getInstance(mCon).getArrayList();

        while(withLots == null){
            withLots = CarParkSingletonPattern.getNewInstance(mCon).getArrayList();

        }
        for(int o = 0; o<withLots.size();o++){

            if(withLots.get(o).getCarParkNo().equals(cpitem.getCarParkNo())){

                return (withLots.get(o).getLotsAvailable());
            }
        }
        return "N/A";
    }


    //This method gets New Lots info array contained in singleton Pattern
    public static String getCurrentLotStaticUpdate(HDBCarParkObject cpitem, Context mCon){

        // Uses Singleton Pattern to get Lots, DAO in readCarparkObjectRawData to get all info on carpark

        List<HDBCarParkObject> withLots = CarParkSingletonPattern.getNewInstance().getArrayLotList();

        while(withLots == null){
            withLots = CarParkSingletonPattern.getNewInstance().getArrayList();

            //    carDAO = new HDBCarParkAPIDAOImplement(mCon);
            //   withLots = carDAO.getAllCarParks();
        }
       for(int o = 0; o<withLots.size();o++){
          //  Log.d("Comparing CarPark No for Lots ", withLots.get(o).getCarParkNo() + " + " + cpitem.getCarParkNo());
//           System.out.print(String.valueOf(withLots.get(o).getCarParkNo()));
//
//           // if(withLots.get(o).getCarParkNo() == null) {
//            withLots.get(o).setCarParkNo("NA");
//           System.out.print(String.valueOf(withLots.get(o).getCarParkNo()));

           //}
            if(withLots.get(o).getCarParkNo().equals(cpitem.getCarParkNo())){
                return (withLots.get(o).getLotsAvailable());
            }
        }
        return "N/A";
    }


}