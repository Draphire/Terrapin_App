package com.lauden.weeliangng.terrapin.Model.SingletonPattern;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lauden.weeliangng.terrapin.MainActivityUI;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.CameraDAOImplement;
import com.lauden.weeliangng.terrapin.Model.IncidentFactoryPattern.IncidentObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeeLiang Ng on 4/3/2018.
 */


public class IncidentSingletonPattern {

    private static final String TAG = "FireBase";


    public static List<IncidentObject> getIncidentList(){


        final List<IncidentObject> incidentlist = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().
                getReference("trafficjson").child("data0");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange() called");

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
                    incidentlist.add(incident);

                    //    Log.d("incident = ", incident.getMessage());
                    //  incidentlist.add(indicident);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return incidentlist;
    }

    /**
     * Created by WeeLiang Ng on 6/11/2017.
     */

/*
    Activity mActivity;
    private List<IncidentObject> cpList;

    private static IncidentSingletonPattern instance;
    private IncidentSingletonPattern(){
        cpList = ;
    }

    private IncidentSingletonPattern(Activity activity){
        this.mActivity = activity;
        cpList = View.Main
    }


    public static CameraSingleton getInstance(){
        if (instance == null){
            instance = new CameraSingleton();
        }
        return instance;
    }


    public static CameraSingleton getInstance(Activity act){
        if (instance == null){
            instance = new CameraSingleton(act);
        }
        return instance;

    }

    public static CameraSingleton getNewInstance(){


        instance = new CameraSingleton();

        if(instance == null){
            instance = new CameraSingleton();
        }
        return instance;
    }

    public static CameraSingleton getNewInstance(Activity mact){


        instance = new CameraSingleton();

        if(instance == null){
            instance = new CameraSingleton(mact);
        }
        return instance;
    }


    public List<LTACameraObject> getArrayList() {
        return cpList;
    }

*/
}
