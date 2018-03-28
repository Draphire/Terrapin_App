package com.lauden.weeliangng.terrapin.Controller.MapTaskListenerPattern;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.CarParkFactory;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.CarParkStrategy;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.MallObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeeLiang Ng on 8/3/2018.
 */

public class AsynTaskMall extends AsyncTask<Void,Void,List<MallObject>> {

    MapAsyncTaskListener listener;
    DataSnapshot dataSnapshot;
    ValueEventListener mValueEventListener;
    Context mContext;
    List<MallObject> mMallObjectList = new ArrayList<>();

    public AsynTaskMall(MapAsyncTaskListener listener, DataSnapshot dataSnapshot) {
this.listener = listener;
this.dataSnapshot = dataSnapshot;
    }


    @Override
    protected List<MallObject> doInBackground(Void... voids) {

        //Factory Pattern Usage
        CarParkFactory carFactory = new CarParkFactory();
        CarParkStrategy carStrat = null;
        //Factory Pattern Usage
        // carStrat = carFactory.getCarParkType("MALL");
        // MallObject carparkObjectMALL = (MallObject) carStrat;

        //clearing the previous artist list
        // incidentlist.clear();
        //iterating through all the nodes
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            //getting artist
            //    IncidentObject indicident= postSnapshot.getValue(IncidentObject.class);
            //adding artist to the list
          //  String displayName = dataSnapshot.getValue().toString();
//                    Log.d("Incident Fire", dataSnapshot.getValue(String.class));
            //System.out.println(displayName);
            //  Log.d(TAG, " Tried get from Fire");


            //IncidentObject incident = postSnapshot.getValue(IncidentObject.class);
            //Factory Pattern Usage
            carStrat = carFactory.getCarParkType("MALL");
            MallObject carparkObjectMALL = (MallObject) carStrat;
            carparkObjectMALL = postSnapshot.getValue(MallObject.class);


            String[] latlong = carparkObjectMALL.getLocation().split(" ");
            double latitude = Double.parseDouble(latlong[0]);
            double longitude = Double.parseDouble(latlong[1]);

            carparkObjectMALL.setLat(latitude);
            carparkObjectMALL.setLong(longitude);

//            System.out.println("SPlit Mall LatLon " +
//                    String.valueOf(carparkObjectMALL.getLat()) + " and " + String.valueOf(carparkObjectMALL.getLong()));


            //System.out.println(incident.getLongitude());
            mMallObjectList.add(carparkObjectMALL);


        }
        return mMallObjectList;
    }

    ProgressDialog progressDialogMall;

    @Override
    protected void onPostExecute(List<MallObject> mallObjects) {
        listener.MallMarkPlace(mMallObjectList);
           // progressDialogMall.dismiss();


    }

    @Override
    protected void onPreExecute() {
        listener.onMapAsyncTaskStart();
//        progressDialogMall = ProgressDialog.show(getContext(), "Please wait.",
//                "Locating Malls...", true);

    }
}
