package com.lauden.weeliangng.terrapin.Controller.MapTaskListenerPattern;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.CarParkFactory;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.CarParkStrategy;
import com.lauden.weeliangng.terrapin.Model.IncidentFactoryPattern.IncidentObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeeLiang Ng on 8/3/2018.
 */

public class AsynTaskIncident extends AsyncTask<Void,Void,List<IncidentObject>> {

    MapAsyncTaskListener listener;
    DataSnapshot dataSnapshot;
    ValueEventListener mValueEventListener;
    Context mContext;
    List<IncidentObject> mIncidentObjectList = new ArrayList<>();
    String type = "incident";

    public AsynTaskIncident(MapAsyncTaskListener listener, DataSnapshot dataSnapshot) {
this.listener = listener;
this.dataSnapshot = dataSnapshot;
       // type = "incident";
    }

    public AsynTaskIncident(MapAsyncTaskListener listener, DataSnapshot dataSnapshot, String s) {
        this.listener = listener;
        this.dataSnapshot = dataSnapshot;
        this.type = s;
    }


    @Override
    protected List<IncidentObject> doInBackground(Void... voids) {

        //Factory Pattern Usage
        CarParkFactory carFactory = new CarParkFactory();
        CarParkStrategy carStrat = null;
        //Factory Pattern Usage
        // carStrat = carFactory.getCarParkType("MALL");
        // MallObject carparkObjectMALL = (MallObject) carStrat;

        //clearing the previous artist list
        // incidentlist.clear();
        //iterating through all the nodes
        //clearing the previous artist list
        // incidentlist.clear();
        //iterating through all the nodes
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            //getting artist
            //    IncidentObject indicident= postSnapshot.getValue(IncidentObject.class);
            //adding artist to the list
          //  String displayName = dataSnapshot.getValue().toString();
//                    Log.d("Incident Fire", dataSnapshot.getValue(String.class));
           // System.out.println(displayName);
          // Log.d(TAG, " Tried get from Fire");
            IncidentObject incident = postSnapshot.getValue(IncidentObject.class);
           // System.out.println(incident.getLongitude());

            if(type.equals("roadwork")) {
                if (incident.getType().equalsIgnoreCase("Roadwork")) {
                    mIncidentObjectList.add(incident);

                }
            }else {

                if (incident.getType().equalsIgnoreCase("Roadwork")) {
                   // mIncidentObjectList.add(incident);

                }else{
                    mIncidentObjectList.add(incident);
            }
            }

//                        System.out.println("Inci List Try" +
//                                incilist2.get(0).getType());

            //    Log.d("incident = ", incident.getMessage());
            //  incidentlist.add(indicident);
        }
        type = "incident";

//        for (IncidentObject camObj : incilist2) {
//            Log.d("Try Add incident map = ", camObj.getMessage());
//            int markerimg = MapMarkerSelection.IncidentChoice(camObj.getType());
//
////            IncidentObject camObj = incilist2.get(0);
//            //map.addMarker(new MarkerOptions().position(new LatLng(camObj.getLat(), camObj.getLon())));
//            Marker marker = map.addMarker(new MarkerOptions()
//                    .position((new LatLng(camObj.getLatitude(), camObj.getLongitude())))
//                    // .position((new LatLng(1.2782213982700898, 103.87105633785305)))
//                    .snippet(camObj.getMessage())
//                    .title(camObj.getType())
//
//                    .icon(BitmapDescriptorFactory.fromBitmap(
//                            BitmapFactory.decodeResource(res, markerimg))));
//            marker.setTag(camObj);
//
//            IncidentListMarker.add(marker);
//
//        }
        return mIncidentObjectList;
    }

    ProgressDialog progressDialogMall;

    @Override
    protected void onPostExecute(List<IncidentObject> incidentObjects) {
        listener.IncidentMarkPlace(incidentObjects);
           // progressDialogMall.dismiss();


    }

    @Override
    protected void onPreExecute() {
        listener.onMapAsyncTaskStart();
//        progressDialogMall = ProgressDialog.show(getContext(), "Please wait.",
//                "Locating Malls...", true);

    }
}
