package com.lauden.weeliangng.terrapin.Model.IncidentDAOpattern.UsageOfIncidentDAO;

import android.util.Log;

import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleIncidentObject;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleIncidentObject;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.CameraDAO;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.CameraDAOImplement;
import com.lauden.weeliangng.terrapin.Model.IncidentDAOpattern.IncidentDAO;
import com.lauden.weeliangng.terrapin.Model.IncidentDAOpattern.IncidentDAOImplement;
import com.lauden.weeliangng.terrapin.Model.IncidentFactoryPattern.IncidentObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by WeeLiang Ng on 26/2/2018.
 */

public class IncidentDAOUsage {


    public static List<TitleIncidentObject> IncidentDAOgetTitleObjectListindi() {




        // DAO Pattern Usage from API
        IncidentDAO camDAO = new IncidentDAOImplement();
        List<IncidentObject> camArray = camDAO.getAllIncidents();


        // My loop to retry get Camera Object List in case null was returned
        int retryCount = 0;

//        while(camArray == null) {
//            //  camArray = CameraDAOImplement.getCameraObjectList();
//            camDAO = new CameraDAOImplement();
//            camArray = camDAO.getAllCameras();
//
//            retryCount++;
//            Log.d("Retry get CameraList ", String.valueOf(retryCount));
//
//        }

        // my loop for populating entire Expandable Camera List View with relevant information and image
        List<TitleIncidentObject> titleIncidentObject = new ArrayList<>();
        int j = 0;

        for(int i=0; i<camArray.size();i++){

            String example = "/abc/def/ghfj.doc";
            String Incidenttitle = camArray.get(i).getMessage().substring(example.lastIndexOf("on") + 1);
            System.out.println(Incidenttitle);

            TitleIncidentObject kpe1 = new TitleIncidentObject(Incidenttitle, Arrays.asList(camArray.get(j)));
            j++;


            titleIncidentObject.add(kpe1);
        }


        return titleIncidentObject;
    }


    public static List<TitleIncidentObject> IncidentDAOgetTitleObjectList() {




        // DAO Pattern Usage from API
          //     IncidentDAO camDAO = new IncidentDAOImplement();
        //List<IncidentObject> camArray = camDAO.getIncidentList();
     //   IncidentDAO camDAO = new IncidentDAOImplement();
        List<IncidentObject> camArray = IncidentDAOImplement.getIncidentList();



        // My loop to retry get Camera Object List in case null was returned
        int retryCount = 0;

//        while(camArray == null) {
//            //  camArray = CameraDAOImplement.getCameraObjectList();
//            camDAO = new CameraDAOImplement();
//            camArray = camDAO.getAllCameras();
//
//            retryCount++;
//            Log.d("Retry get CameraList ", String.valueOf(retryCount));
//
//        }

        // my loop for populating entire Expandable Camera List View with relevant information and image
        List<TitleIncidentObject> titleIncidentObject = new ArrayList<>();
        int j = 0;

        for(int i=0; i<camArray.size();i++){

            String example = "/abc/def/ghfj.doc";
            String Incidenttitle = camArray.get(i).getMessage().substring(example.lastIndexOf("on") + 1);
            System.out.println(Incidenttitle);

            TitleIncidentObject kpe1 = new TitleIncidentObject(Incidenttitle, Arrays.asList(camArray.get(j)));
            j++;


            titleIncidentObject.add(kpe1);
        }


        return titleIncidentObject;
    }

}
