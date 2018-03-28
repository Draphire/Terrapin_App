package com.lauden.weeliangng.terrapin.Model.IncidentDAOpattern;

import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.CarParkFactory;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.CarParkStrategy;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.Model.IncidentFactoryPattern.IncidentObject;
import com.lauden.weeliangng.terrapin.Model.MapUIUtlity.MapMarkerSelection;
import com.lauden.weeliangng.terrapin.Model.Utility.CurrentTime;

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
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by WeeLiang Ng on 26/2/2018.
 */

public class IncidentDAOImplement implements IncidentDAO{


    //static List<IncidentObject> incilist2 = new ArrayList<>();

//    public static IncidentObject getIncident() {
//
//        return incident;
//    }
//



    public static List<IncidentObject> getIncidentList() {


      final List<IncidentObject> incilist2 = new ArrayList<>();


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().
                getReference("trafficjson").child("data0");
        //addListenerForSingleValueEvent()
        // addValueEventListener
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange() called");

                List<IncidentObject> incilist2 = new ArrayList<>();


                //clearing the previous artist list
                // incidentlist.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    //    IncidentObject indicident= postSnapshot.getValue(IncidentObject.class);
                    //adding artist to the list
                    String displayName = dataSnapshot.getValue().toString();
//                    Log.d("Incident Fire", dataSnapshot.getValue(String.class));
                    System.out.println(displayName);
                    Log.d(TAG, " Tried get from Fire");
                    IncidentObject incident = postSnapshot.getValue(IncidentObject.class);
                    System.out.println(incident.getLongitude());
                    incilist2.add(incident);
//                    System.out.println("Inci List Try" +
//                            incilist2.get(0).getType());

                    //    Log.d("incident = ", incident.getMessage());
                    //  incidentlist.add(indicident);
                    System.out.println("Test get Data Out" + incilist2.get(0).getMessage());
                    Log.d("Damincident = ", incident.getMessage());
                }


               //return incilist2;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

      //  System.out.println("Test get Data Out" + incilist2.get(0).getMessage());
//

        return incilist2;
    }

    @Override
    public List<IncidentObject> getAllIncidents() {
        return null;
    }

    @Override
    public IncidentObject getIncidentsObject(int id) {
        return null;
    }

    @Override
    public void UpdateIncidentsObject(IncidentObject cmObj) {

    }

    @Override
    public void UpdateIncidentObjectList() {

    }

    @Override
    public void DeleteincidentObject(IncidentObject cmObj) {

    }

    @Override
    public void DeleteIncidentObjectList() {

    }
}
