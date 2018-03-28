package com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.UsageOfCameraDAO;

import android.app.Activity;
import android.util.Log;

import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleCameraObject;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.CameraDAO;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.CameraDAOImplement;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CameraSingleton;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CarParkSingletonPattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CameraDAOUsage {





    public static List<TitleCameraObject> CameraDAOgetTitleObjectList() {




        // DAO Pattern Usage from API
         CameraDAO camDAO = new CameraDAOImplement();
        List<LTACameraObject> camArray = camDAO.getAllCameras();


        // My loop to retry get Camera Object List in case null was returned
        int retryCount = 0;

        while(camArray == null) {
          //  camArray = CameraDAOImplement.getCameraObjectList();
             camDAO = new CameraDAOImplement();
            camArray = camDAO.getAllCameras();

            retryCount++;
            Log.d("Retry get CameraList ", String.valueOf(retryCount));

        }

         // my loop for populating entire Expandable Camera List View with relevant information and image
        List<TitleCameraObject> titleCameraObjects = new ArrayList<>();
        int j = 0;

        for(int i=0; i<camArray.size();i++){

            TitleCameraObject kpe1 = new TitleCameraObject(camArray.get(i).getName(), Arrays.asList(camArray.get(j)));
            j++;


            titleCameraObjects.add(kpe1);
        }


            return titleCameraObjects;
        }


    public static List<TitleCameraObject> CameraDAOgetTitleObjectList(Activity act) {

        // DAO Pattern Usage from API
        //CameraDAO camDAO = new CameraDAOImplement(act);
        //List<LTACameraObject> camArray = camDAO.getAllCameras();
      //  List<LTACameraObject> camArray = CameraSingleton.getInstance().getArrayList();
        List<LTACameraObject> camArray = CameraSingleton.getNewInstance(act).getArrayList();

        int retryCount = 0;

        while(camArray == null){
                        camArray = CameraSingleton.getNewInstance(act).getArrayList();
                        retryCount++;
                        Log.d("Retry get CameraList ", String.valueOf(retryCount));

                    }

        // My loop to retry get Camera Object List in case null was returned

//        while(camArray == null) {
//           // camArray = CameraDAOImplement.getCameraObjectList(act);
//            camDAO = new CameraDAOImplement(act);
//
//            camArray = camDAO.getAllCameras();
//
//            retryCount++;
//            Log.d("Retry get CameraList ", String.valueOf(retryCount));
//
//        }

        // my loop for populating entire Expandable Camera List View with relevant information and image
        List<TitleCameraObject> titleCameraObjects = new ArrayList<>();
        int j = 0;

        for(int i=0; i<camArray.size();i++){

            TitleCameraObject kpe1 = new TitleCameraObject(camArray.get(i).getName(), Arrays.asList(camArray.get(j)));
            j++;


            titleCameraObjects.add(kpe1);
        }


        return titleCameraObjects;
    }


    public static List<TitleCameraObject> CameraDAOgetTitleObjectListFirst(Activity act) {

        // DAO Pattern Usage from API
        //CameraDAO camDAO = new CameraDAOImplement(act);
        //List<LTACameraObject> camArray = camDAO.getAllCameras();
          List<LTACameraObject> camArray = CameraSingleton.getInstance(act).getArrayList();
       // List<LTACameraObject> camArray = CameraSingleton.getNewInstance(act).getArrayList();

        int retryCount = 0;

        while(camArray == null){
            camArray = CameraSingleton.getNewInstance(act).getArrayList();
            retryCount++;
            Log.d("Retry get CameraList ", String.valueOf(retryCount));

        }

        // My loop to retry get Camera Object List in case null was returned

//        while(camArray == null) {
//           // camArray = CameraDAOImplement.getCameraObjectList(act);
//            camDAO = new CameraDAOImplement(act);
//
//            camArray = camDAO.getAllCameras();
//
//            retryCount++;
//            Log.d("Retry get CameraList ", String.valueOf(retryCount));
//
//        }

        // my loop for populating entire Expandable Camera List View with relevant information and image
        List<TitleCameraObject> titleCameraObjects = new ArrayList<>();
        int j = 0;

        for(int i=0; i<camArray.size();i++){

            TitleCameraObject kpe1 = new TitleCameraObject(camArray.get(i).getName(), Arrays.asList(camArray.get(j)));
            j++;


            titleCameraObjects.add(kpe1);
        }


        return titleCameraObjects;
    }

}