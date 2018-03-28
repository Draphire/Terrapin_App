package com.lauden.weeliangng.terrapin.Controller.MapTaskListenerPattern;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
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

public class MapAsyncTaskAllocator {

   // Context mContext;
    private MapAsyncTaskListener listener;
    DataSnapshot dataSnapshot;
    ValueEventListener mValueEventListener;
    Context mContext;
    List<MallObject> mMallObjectList = new ArrayList<>();
    LatLng mLatLng;

    public MapAsyncTaskAllocator(){

    }


    public void AsyncTasking(String s, Context context){

       if(s.equals("asyncincident")){
           AsynTaskIncident incidentorigin = new AsynTaskIncident(listener, dataSnapshot);
           incidentorigin.execute();
       }else  if(s.equals("asyncroadwork")){
           AsynTaskIncident incidentorigin = new AsynTaskIncident(listener, dataSnapshot,"roadwork");
           incidentorigin.execute();
       }
        else if(s.equals("asyncmall")) {
            AsynTaskMall origin = new AsynTaskMall(listener, dataSnapshot);
            origin.execute();
        }else if(s.equals("asynccam")){
            Activity activity = (Activity) context;
            AsynTaskCamera camorigin = new AsynTaskCamera(listener,activity);
            camorigin.execute();

        }


    }

    public void AsyncTasking(String s, Context context, LatLng latLng) {


        if (s.equals("asynchdbnearby")) {
            // Activity activity = (Activity) context;
            AsynTaskHDBnearby hdborigin = new AsynTaskHDBnearby(listener, context, latLng);
            hdborigin.execute();


        }
    }


    public MapAsyncTaskAllocator(Context context , DataSnapshot dataSnapshotin){
        this.mContext = context;
        this.dataSnapshot = dataSnapshotin;
        // this.listener = listenerin;


    }

    public MapAsyncTaskAllocator(MapAsyncTaskListener listenerin, DataSnapshot dataSnapshotin){
        this.dataSnapshot = dataSnapshotin;
        this.listener = listenerin;

    }

    public MapAsyncTaskAllocator(ValueEventListener valueEventListener, DataSnapshot dataSnapshotin){
        this.dataSnapshot = dataSnapshotin;
        this.mValueEventListener = valueEventListener;

    }

    public MapAsyncTaskAllocator(DataSnapshot dataSnapshotin){
        this.dataSnapshot = dataSnapshotin;
       // this.listener = listenerin;

    }

    public MapAsyncTaskListener getListener() {
        return listener;
    }

    public synchronized void setListener(MapAsyncTaskListener listener) {
        this.listener = listener;
    }

//        public List<MallObject> MallOutsource() {
//
//
//            //Factory Pattern Usage
//            CarParkFactory carFactory = new CarParkFactory();
//            CarParkStrategy carStrat = null;
//            //Factory Pattern Usage
//            // carStrat = carFactory.getCarParkType("MALL");
//            // MallObject carparkObjectMALL = (MallObject) carStrat;
//
//            //clearing the previous artist list
//            // incidentlist.clear();
//            //iterating through all the nodes
//            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                //getting artist
//                //    IncidentObject indicident= postSnapshot.getValue(IncidentObject.class);
//                //adding artist to the list
//                String displayName = dataSnapshot.getValue().toString();
////                    Log.d("Incident Fire", dataSnapshot.getValue(String.class));
//                System.out.println(displayName);
//              //  Log.d(TAG, " Tried get from Fire");
//
//
//                //IncidentObject incident = postSnapshot.getValue(IncidentObject.class);
//                //Factory Pattern Usage
//                carStrat = carFactory.getCarParkType("MALL");
//                MallObject carparkObjectMALL = (MallObject) carStrat;
//                carparkObjectMALL = postSnapshot.getValue(MallObject.class);
//
//                String[] latlong =  carparkObjectMALL.getLocation().split(" ");
//                double latitude = Double.parseDouble(latlong[0]);
//                double longitude = Double.parseDouble(latlong[1]);
//
//                carparkObjectMALL.setLat(latitude);
//                carparkObjectMALL.setLong(longitude);
//
//                System.out.println("SPlit Mall LatLon " +
//                        String.valueOf(carparkObjectMALL.getLat())+" and "+ String.valueOf(carparkObjectMALL.getLong()));
//
//
//                //System.out.println(incident.getLongitude());
//                mMallObjectList.add(carparkObjectMALL);
////                System.out.println("Mallcp List Try" +
////                        mallcplist.get(0).getLocation());
//
//
//                //    Log.d("incident = ", incident.getMessage());
//                //  incidentlist.add(indicident);
//            }
//
//            //((MainActivityUI) mContext).dataUpdated();
//            return mMallObjectList;
//
//
//    }

    public void setDataSnapshot(DataSnapshot dataSnapshot){
        this.dataSnapshot = dataSnapshot;
    }

    public void MallOutsource(DataSnapshot dataSnapshot) {


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
            String displayName = dataSnapshot.getValue().toString();
//                    Log.d("Incident Fire", dataSnapshot.getValue(String.class));
            System.out.println(displayName);
            //  Log.d(TAG, " Tried get from Fire");


            //IncidentObject incident = postSnapshot.getValue(IncidentObject.class);
            //Factory Pattern Usage
            carStrat = carFactory.getCarParkType("MALL");
            MallObject carparkObjectMALL = (MallObject) carStrat;
            carparkObjectMALL = postSnapshot.getValue(MallObject.class);

            String[] latlong =  carparkObjectMALL.getLocation().split(" ");
            double latitude = Double.parseDouble(latlong[0]);
            double longitude = Double.parseDouble(latlong[1]);

            carparkObjectMALL.setLat(latitude);
            carparkObjectMALL.setLong(longitude);

            System.out.println("SPlit Mall LatLon " +
                    String.valueOf(carparkObjectMALL.getLat())+" and "+ String.valueOf(carparkObjectMALL.getLong()));


            //System.out.println(incident.getLongitude());
            mMallObjectList.add(carparkObjectMALL);
//                System.out.println("Mallcp List Try" +
//                        mallcplist.get(0).getLocation());


            //    Log.d("incident = ", incident.getMessage());
            //  incidentlist.add(indicident);
        }

        //((MainActivityUI) mContext).dataUpdated();
       listener.MallMarkPlace(mMallObjectList);
        // return mMallObjectList;


    }


    public void MallOutsource() {


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
            String displayName = dataSnapshot.getValue().toString();
//                    Log.d("Incident Fire", dataSnapshot.getValue(String.class));
            System.out.println(displayName);
            //  Log.d(TAG, " Tried get from Fire");


            //IncidentObject incident = postSnapshot.getValue(IncidentObject.class);
            //Factory Pattern Usage
            carStrat = carFactory.getCarParkType("MALL");
            MallObject carparkObjectMALL = (MallObject) carStrat;
            carparkObjectMALL = postSnapshot.getValue(MallObject.class);

            String[] latlong =  carparkObjectMALL.getLocation().split(" ");
            double latitude = Double.parseDouble(latlong[0]);
            double longitude = Double.parseDouble(latlong[1]);

            carparkObjectMALL.setLat(latitude);
            carparkObjectMALL.setLong(longitude);

            System.out.println("SPlit Mall LatLon " +
                    String.valueOf(carparkObjectMALL.getLat())+" and "+ String.valueOf(carparkObjectMALL.getLong()));


            //System.out.println(incident.getLongitude());
            mMallObjectList.add(carparkObjectMALL);
//                System.out.println("Mallcp List Try" +
//                        mallcplist.get(0).getLocation());


            //    Log.d("incident = ", incident.getMessage());
            //  incidentlist.add(indicident);
        }

        //((MainActivityUI) mContext).dataUpdated();
        listener.MallMarkPlace(mMallObjectList);
        // return mMallObjectList;


    }

}
