package com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CarParkDAOpattern.UsageOfCarparkDAO;

import android.content.Context;
import android.util.Log;

import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleCarParkObject;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CarParkDAOpattern.CarParkDAO;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CarParkDAOpattern.HDBCarParkAPIDAOImplement;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CameraSingleton;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CarParkSingletonPattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by WeeLiang Ng on 3/11/2017.
 */

public class CarParkDAOUsage {


    public static List<TitleCarParkObject> CarparkDAOgetTitleObjectList(Context mCon) {
     //  List<HDBCarParkObject> carArray = HDBCarParkAPIDAOImplement.readCarparkObjectRawData(mCon);

//        CarParkDAO carDAO = new HDBCarParkAPIDAOImplement(mCon);
//        List<HDBCarParkObject> carArray = carDAO.getAllCarParks();
        List<HDBCarParkObject> carArray = CarParkSingletonPattern.getInstance(mCon).getArrayList();
        // List<LTACameraObject> camArray = CameraSingleton.getNewInstance(act).getArrayList();



        // My loop to retry get Camera Object List in case null was returned
        int retryCount = 0;

        while(carArray == null) {
            // camArray = CameraDAOImplement.getCameraObjectList(act);
//            carDAO = new HDBCarParkAPIDAOImplement(mCon);
//
//            carArray = carDAO.getAllCarParks();

           carArray = CarParkSingletonPattern.getNewInstance(mCon).getArrayList();

            retryCount++;
            Log.d("Retry get CameraList ", String.valueOf(retryCount));

        }
        // my loop for populating entire Expandable Camera List View with relevant information and image
        List<TitleCarParkObject> titleCarParkObjects = new ArrayList<>();
        int j = 0;

        for (int i = 0; i < carArray.size(); i++) {

            TitleCarParkObject kpe1 = new TitleCarParkObject(carArray.get(i).getAddress(), Arrays.asList(carArray.get(j)));
            j++;
            // Log.d("CarPark LatLon", carparkObject.getLatitude() +" " +carparkObject.getLongitude());


            titleCarParkObjects.add(kpe1);
        }


        return titleCarParkObjects;
    }


}
