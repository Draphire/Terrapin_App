package com.lauden.weeliangng.terrapin.View.MapPresentation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleCameraObject;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.MallObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.UsageOfCameraDAO.CameraDAOUsage;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CarParkDAOpattern.HDBCarParkAPIDAOImplement;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.MapUIDirectionsDataAccess.DirectionFinder;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.MapUIDirectionsDataAccess.DirectionFinderListener;
import com.lauden.weeliangng.terrapin.Model.IncidentFactoryPattern.IncidentObject;
import com.lauden.weeliangng.terrapin.Model.MapElementObjects.Route;
import com.lauden.weeliangng.terrapin.Model.MapUIUtlity.InfoWindowRefresher;
import com.lauden.weeliangng.terrapin.Controller.MapTaskListenerPattern.MapAsyncTaskAllocator;
import com.lauden.weeliangng.terrapin.Controller.MapTaskListenerPattern.MapAsyncTaskListener;
import com.lauden.weeliangng.terrapin.Model.MapUIUtlity.MapMarkerSelection;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CameraSingleton;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CarParkSingletonPattern;
import com.lauden.weeliangng.terrapin.Model.Utility.StringParser;
import com.lauden.weeliangng.terrapin.Model.Utility.TimeStampProcessing;
import com.lauden.weeliangng.terrapin.R;
import com.lauden.weeliangng.terrapin.View.CameraListPresentation.DialogueView.ImageDialogFragment;
import com.lauden.weeliangng.terrapin.View.MainActivityObserver;
import com.lauden.weeliangng.terrapin.View.MainActivityUI;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import com.lauden.weeliangng.terrapin.View.MapPresentation.MapElements.CustomInfoWindowAdapter;


//Subcribed to Observer MainActivityObserver, Observer Pattern
public class MapUI extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks, MainActivityObserver, MapAsyncTaskListener {



    private List<TitleCameraObject> mapCamList = new ArrayList<>();

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().
            getReference("trafficjson").child("data0");
    ValueEventListener incidentlistener;
    final boolean[] navTilt = {true};
    int firsttry = 1;

    private CameraPosition cp;
    private boolean mapsSupported;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private LocationRequest mLocationRequest;
    private final static int MILLISECONDS_PER_SECOND = 1000;
    private final static int miniute = 40 * MILLISECONDS_PER_SECOND;
    private Location myLocation;
    private long LOCATION_UPDATE_MIN_TIME = 1;
    private float LOCATION_UPDATE_MIN_DISTANCE = 10;

  private Location userLocation;
    private Marker userLocationMarker;
    private Marker userAddedMarker;
    private Marker destinationMarker;
    private LocationRequest locationRequest;
    PlaceAutocompleteFragment autocompleteFragment;
    private LatLng destination;
    private ProgressDialog progressDialog;
    private List<Polyline> polylinePaths = new ArrayList<>();
    List<Marker> IncidentListMarker = new ArrayList<>();
    List<Marker> CamMarkerList = new ArrayList<>();


    private LatLngBounds SINGAPORE = new LatLngBounds(
            new LatLng(1.2019992271065199, 103.60885618720204), new LatLng(1.4697179599434476, 104.05448912177235));



    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    private int curMapTypeIndex = 1;

   List<Marker> camListMarker = new ArrayList<>();
    //List<HDBCarParkObject> withLots = CarParkSingletonPattern.getInstance().getArrayList();
    static int x = 1;
    static long DELAY = 200;
    boolean not_first_time_showing_info_window = false;


    List<Marker> carparkListMarker = new ArrayList<>();
   // private ProgressBar spinner;

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;

    private MapView mapView;
    private GoogleMap map;

    FragmentActivity listener;
    AdView mAdView;
    LocationManager manager;
    private static final String TAG = "MapFire";
    MapAsyncTaskAllocator mMapAsyncTaskAllocator = new MapAsyncTaskAllocator();
    List<Marker> carparkListMarker2 = new ArrayList<>();

public List<IncidentObject> incilist2 = new ArrayList<>();

    public List<MallObject> mallcplist = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
         ((MainActivityUI) context).registerDataUpdateListener(this);
        mMapAsyncTaskAllocator.setListener(this);


        if (context instanceof Activity){
            this.listener = (FragmentActivity) context;
        }

      //  final List<IncidentObject> incilist2 = new ArrayList<>();
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().
//                getReference("trafficjson").child("data0");
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d(TAG, "onDataChange() called");
//
//                //clearing the previous artist list
//                // incidentlist.clear();
//                //iterating through all the nodes
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    //getting artist
//                    //    IncidentObject indicident= postSnapshot.getValue(IncidentObject.class);
//                    //adding artist to the list
//                    String displayName = dataSnapshot.getValue().toString();
////                    Log.d("Incident Fire", dataSnapshot.getValue(String.class));
//                    System.out.println(displayName);
//                    Log.d(TAG, " Tried get from Fire");
//                    IncidentObject incident = postSnapshot.getValue(IncidentObject.class);
//                    System.out.println(incident.getLongitude());
//                    incilist2.add(incident);
//                    System.out.println("Inci List Try" +
//                            incilist2.get(0).getType());
//
//                    //    Log.d("incident = ", incident.getMessage());
//                    //  incidentlist.add(indicident);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });

    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivityUI) activity).registerDataUpdateListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivityUI) getActivity()).unregisterDataUpdateListener(this);

    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        MapFragment f = (MapFragment) getFragmentManager()
//                .findFragmentById(R.id.);
//        if (f != null)
//            getFragmentManager().beginTransaction().remove(f).commit();
//    }


    @Override
    public void onDataUpdate() {

        map.addMarker(new MarkerOptions().position(new LatLng(1.290270, 103.851959)));


    }

    @Override
    public void onDataUpdate(Object o , String choice) {
        if (choice.equals("camera") && o instanceof IncidentObject) {

            List<LTACameraObject> camerafornearby = CameraSingleton.getInstance().getArrayList();
            rangeCheckLatlonCamera(o,camerafornearby);
//            currentMarker.hideInfoWindow();
        }

        }

    ProgressDialog progressDialogMall;

    @Override
    public void onMapAsyncTaskStart() {
        if (progressDialogMall != null && progressDialogMall.isShowing()) {
            progressDialogMall.dismiss();
        }
//        if(parentAct != null) {
//            progressDialogMall = ProgressDialog.show(parentAct, "Please wait.",
//                    "Updating your Map...");
//        }else
        if (getContext() != null) {

            progressDialogMall = ProgressDialog.show(getContext(), "Please wait.",
                    "Updating your Map...");
        }

       // progressDialogMall.dismiss();

    }

    @Override
    public void MallMarkPlace(List<MallObject> mallLt) {

       // progressDialogMall.

        for (MallObject camObj  : mallLt) {

            // Log.d("Try Add incident map = ", camObj.getMessage());
            // int markerimg = MapMarkerSelection.IncidentChoice(camObj.getType());

//            IncidentObject camObj = incilist2.get(0);
            //map.addMarker(new MarkerOptions().position(new LatLng(camObj.getLat(), camObj.getLon())));
            //  if(!camObj.getDevelopment().contains(" ROAD")||!camObj.getDevelopment().contains(" STREET")) {
            if(camObj.getAgency().equals("LTA") && !camObj.getLat().equals(0)){
                Marker marker = map.addMarker(new MarkerOptions()
                        .position((new LatLng(camObj.getLat(), camObj.getLong())))
                        // .position((new LatLng(1.2782213982700898, 103.87105633785305)))
                        .snippet(String.valueOf(camObj.getAvailableLots()))
                        .title(String.valueOf(camObj.getDevelopment()))

                        .icon(BitmapDescriptorFactory.fromBitmap(
                                BitmapFactory.decodeResource(res, R.drawable.mallicon))));
                marker.setTag(camObj);
            }
        }
        progressDialogMall.dismiss();


//        if (progressDialogMall != null && progressDialogMall.isShowing()) {
//            progressDialogMall.dismiss();
//        }

    }

    @Override
    public void IncidentMarkPlace(List<IncidentObject> incidentObjectList) {
        for (IncidentObject camObj : incidentObjectList) {
            Log.d("Try Add incident map = ", camObj.getMessage());
            int markerimg = MapMarkerSelection.IncidentChoice(camObj.getType());

//            IncidentObject camObj = incilist2.get(0);
            //map.addMarker(new MarkerOptions().position(new LatLng(camObj.getLat(), camObj.getLon())));
            Marker marker = map.addMarker(new MarkerOptions()
                    .position((new LatLng(camObj.getLatitude(), camObj.getLongitude())))
                    // .position((new LatLng(1.2782213982700898, 103.87105633785305)))
                    .snippet(camObj.getMessage())
                    .title(camObj.getType())

                    .icon(BitmapDescriptorFactory.fromBitmap(
                            BitmapFactory.decodeResource(res, markerimg))));
            marker.setTag(camObj);

            IncidentListMarker.add(marker);

        }
        progressDialogMall.dismiss();
    }

    @Override
    public void CamMarkPlace(List<LTACameraObject> camlistUp) {
        for (LTACameraObject camObj : camlistUp) {

            //map.addMarker(new MarkerOptions().position(new LatLng(camObj.getLat(), camObj.getLon())));
           Marker marker = map.addMarker(new MarkerOptions()
                    .position((new LatLng(camObj.getLat(), camObj.getLon())))

                    .icon(BitmapDescriptorFactory.fromBitmap(
                            BitmapFactory.decodeResource(res, R.drawable.carmark6))));
            marker.setTag(camObj);

            CamMarkerList.add(marker);

        }
        progressDialogMall.dismiss();

    }

    @Override
    public void HDBnearbyMarkPlace(List<HDBCarParkObject> cpList) {

        for (final HDBCarParkObject cpItem : cpList) {


            Marker marker = map.addMarker(new MarkerOptions()

                    //  carparkListMarker2.add(map.addMarker(new MarkerOptions()
                    .position(new LatLng(cpItem.getLatitude(),
                            cpItem.getLongitude()))
                    .title(cpItem.getAddress())
                    .snippet(getCurrentLot(cpItem))
                    .icon(BitmapDescriptorFactory.fromBitmap(
                            BitmapFactory.decodeResource(res, hdbIcon)))


            );
            marker.setTag(cpItem);

            carparkListMarker2.add(marker);


        }
        progressDialogMall.dismiss();


    }
    @Override
    public void onDataUpdate(String choice ) {



        if (choice.equals("incident")) {
          //  map.clear();
//            CameraPosition cameraPosition = new CameraPosition.Builder().
//                    target(new LatLng(userLocation.getLatitude(), userLocation.getLongitude())).
//                    tilt(80).
//                    zoom(17).
//                    bearing(userLocation.getBearing()).
//                    build();
//
//
//
//            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


//            DatabaseReference mDatabase = FirebaseDatabase.getInstance().
//                    getReference("trafficjson").child("data0");
//            //addListenerForSingleValueEvent()
           // addValueEventListener
//            mDatabase.removeEventListener(incidentlistener);

            incidentlistener = mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    incilist2.clear();
                    map.clear();

                    for (Marker marker: IncidentListMarker) {
                        marker.remove();
                    }
                    if(CamMarkerList != null){
                    for (Marker marker: CamMarkerList) {
                        marker.remove();
                    }}
//
                    Log.d(TAG, "onDataChange() called");

                    mMapAsyncTaskAllocator.setDataSnapshot(dataSnapshot);
                    //newmark.execute();
                    if(parentAct != null) {
                        mMapAsyncTaskAllocator.AsyncTasking("asyncincident", parentAct);
                    }else if (getContext() != null){
                        mMapAsyncTaskAllocator.AsyncTasking("asyncincident", getContext());

                    }
//                    //clearing the previous artist list
//                    // incidentlist.clear();
//                    //iterating through all the nodes
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        //getting artist
//                        //    IncidentObject indicident= postSnapshot.getValue(IncidentObject.class);
//                        //adding artist to the list
//                        String displayName = dataSnapshot.getValue().toString();
////                    Log.d("Incident Fire", dataSnapshot.getValue(String.class));
//                        System.out.println(displayName);
//                        Log.d(TAG, " Tried get from Fire");
//                        IncidentObject incident = postSnapshot.getValue(IncidentObject.class);
//                        System.out.println(incident.getLongitude());
//
//                        if(incident.getType().equalsIgnoreCase("Roadwork")){
//
//                        }else {
//                            incilist2.add(incident);
//                        }
////                        System.out.println("Inci List Try" +
////                                incilist2.get(0).getType());
//
//                        //    Log.d("incident = ", incident.getMessage());
//                        //  incidentlist.add(indicident);
//                    }
//
//                    for (IncidentObject camObj : incilist2) {
//                        Log.d("Try Add incident map = ", camObj.getMessage());
//                        int markerimg = MapMarkerSelection.IncidentChoice(camObj.getType());
//
////            IncidentObject camObj = incilist2.get(0);
//                        //map.addMarker(new MarkerOptions().position(new LatLng(camObj.getLat(), camObj.getLon())));
//                        Marker marker = map.addMarker(new MarkerOptions()
//                                .position((new LatLng(camObj.getLatitude(), camObj.getLongitude())))
//                                // .position((new LatLng(1.2782213982700898, 103.87105633785305)))
//                                .snippet(camObj.getMessage())
//                                .title(camObj.getType())
//
//                                .icon(BitmapDescriptorFactory.fromBitmap(
//                                        BitmapFactory.decodeResource(res, markerimg))));
//                        marker.setTag(camObj);
//
//                        IncidentListMarker.add(marker);
//
//                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                  //  map.clear();
//
                }
            });
        }if (choice.equals("roadwork")) {

               // incilist2.clear();
               // map.clear();


            //  map.clear();
//            CameraPosition cameraPosition = new CameraPosition.Builder().
//                    target(new LatLng(userLocation.getLatitude(), userLocation.getLongitude())).
//                    tilt(80).
//                    zoom(17).
//                    bearing(userLocation.getBearing()).
//                    build();
//
//
//
//            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


//            DatabaseReference mDatabase = FirebaseDatabase.getInstance().
//                    getReference("trafficjson").child("data0");
//            //addListenerForSingleValueEvent()
            // addValueEventListener
            if(mDatabase!=null && incidentlistener!= null)
                mDatabase.removeEventListener(incidentlistener);

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    incilist2.clear();
                    map.clear();

                    if(IncidentListMarker != null ) {
                    for (Marker marker : IncidentListMarker) {
                        marker.remove();
                    }
                }
                if(CamMarkerList != null){
                    for (Marker marker: CamMarkerList) {
                        marker.remove();
                    }}
                    Log.d(TAG, "onDataChange() called");

                    mMapAsyncTaskAllocator.setDataSnapshot(dataSnapshot);
                    //newmark.execute();
                    mMapAsyncTaskAllocator.AsyncTasking("asyncroadwork", getContext());

                    //clearing the previous artist list
                    // incidentlist.clear();
                    //iterating through all the nodes
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        //getting artist
//                        //    IncidentObject indicident= postSnapshot.getValue(IncidentObject.class);
//                        //adding artist to the list
//                        String displayName = dataSnapshot.getValue().toString();
////                    Log.d("Incident Fire", dataSnapshot.getValue(String.class));
//                        System.out.println(displayName);
//                        Log.d(TAG, " Tried get from Fire");
//                        IncidentObject incident = postSnapshot.getValue(IncidentObject.class);
//                        System.out.println(incident.getLongitude());
//
//                        if(incident.getType().equalsIgnoreCase("Roadwork")){
//
//
//                            incilist2.add(incident);
//                        }
////                        System.out.println("Inci List Try" +
////                                incilist2.get(0).getType());
//
//                        //    Log.d("incident = ", incident.getMessage());
//                        //  incidentlist.add(indicident);
//                    }
//
//                    for (IncidentObject camObj : incilist2) {
//                        Log.d("Try Add incident map = ", camObj.getMessage());
//                        int markerimg = MapMarkerSelection.IncidentChoice(camObj.getType());
//
////            IncidentObject camObj = incilist2.get(0);
//                        //map.addMarker(new MarkerOptions().position(new LatLng(camObj.getLat(), camObj.getLon())));
//                        Marker marker = map.addMarker(new MarkerOptions()
//                                .position((new LatLng(camObj.getLatitude(), camObj.getLongitude())))
//                                // .position((new LatLng(1.2782213982700898, 103.87105633785305)))
//                                .snippet(camObj.getMessage())
//                                .title(camObj.getType())
//
//                                .icon(BitmapDescriptorFactory.fromBitmap(
//                                        BitmapFactory.decodeResource(res, markerimg))));
//                        marker.setTag(camObj);
//                        IncidentListMarker.add(marker);
//                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //  map.clear();
//
                }
            });
        } else if (choice.equals("camera")) {

            map.clear();
                if(CamMarkerList != null){
                    for (Marker marker: CamMarkerList) {
                        marker.remove();
                    }}
            if(mDatabase!=null && incidentlistener!= null)
                mDatabase.removeEventListener(incidentlistener);
            mMapAsyncTaskAllocator.AsyncTasking("asynccam",getContext());





        } else if (choice.equals("hdbcp")) {
            incilist2.clear();
            map.clear();

            if(IncidentListMarker != null ) {
                for (Marker marker : IncidentListMarker) {
                    marker.remove();
                }
            }
            if(CamMarkerList != null){
                for (Marker marker: CamMarkerList) {
                    marker.remove();
                }}
            if(mDatabase!=null && incidentlistener!= null)
                mDatabase.removeEventListener(incidentlistener);


            getCurrentLocation();

            } else if (choice.equals("mallcp")) {


            incilist2.clear();
            map.clear();

            if(IncidentListMarker != null ) {
                for (Marker marker : IncidentListMarker) {
                    marker.remove();
                }
            }
            if(CamMarkerList != null){
                for (Marker marker: CamMarkerList) {
                    marker.remove();
                }}
            if(mDatabase!=null && incidentlistener!= null)
                mDatabase.removeEventListener(incidentlistener);


               // AsyncTask<>
//                DirectionFinder directionFinder = new DirectionFinder
//                        (this, mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), destination.latitude, destination.longitude);
//                directionFinder.execute();


                DatabaseReference mDatabase = FirebaseDatabase.getInstance().
                    getReference("datamallcarparkjson").child("data0");
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    // MapAsyncTaskAllocator(this,dataSnapshot);
                    Log.d(TAG, "onDataChange() called");

                  // MapAsyncTaskAllocator malltyr = new MapAsyncTaskAllocator(dataSnapshot);

                   // MapAsyncTaskAllocator newmark = new MapAsyncTaskAllocator(dataSnapshot);
                    mMapAsyncTaskAllocator.setDataSnapshot(dataSnapshot);
                  //newmark.execute();
                    mMapAsyncTaskAllocator.AsyncTasking("asyncmall", getContext());



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
//                while (progressDialogMall != null && progressDialogMall.isShowing()) {
//                    progressDialogMall.dismiss();
//                }

        }

        }


    List<Marker> selectMarkers = new ArrayList<>();

    List<Marker> selectCpMarkers = new ArrayList<>();
    int markerTracker = 0;
    int cpmarkerTracker = 0;
    @Override
    public void onDataUpdate(Object o) {

        if(map!=null) {
            if (o instanceof HDBCarParkObject) {
                if (selectCpMarkers.size() > 0) {
                    selectCpMarkers.remove(0);
                    cpmarkerTracker = 0;
                }

                HDBCarParkObject carObj = (HDBCarParkObject) o;
             /*   selectCpMarkers.add(map.addMarker(new MarkerOptions()
                        .position((new LatLng(carObj.getLatitude(), carObj.getLongitude())))
                        .title(carObj.getAddress() + "  -  " + getCurrentLot(carObj))

                        .icon(BitmapDescriptorFactory.fromBitmap(
                                BitmapFactory.decodeResource(res,cpIcon)))));
            */
                Marker marker=map.addMarker(new MarkerOptions()

                        //  carparkListMarker2.add(map.addMarker(new MarkerOptions()
                        .position(new LatLng(carObj.getLatitude(),
                                carObj.getLongitude()))
                        .title(carObj.getAddress())
                        .snippet(getCurrentLot(carObj))
                        .icon(BitmapDescriptorFactory.fromBitmap(
                                BitmapFactory.decodeResource(res,hdbIcon)))


                );
                marker.setTag(carObj);

                selectCpMarkers.add(marker);



                selectCpMarkers.get(cpmarkerTracker).showInfoWindow();
                cpmarkerTracker++;
                trackCurrent = false;

                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom((new LatLng(carObj.getLatitude(), carObj.getLongitude())), 15);
                map.animateCamera(yourLocation);

            }


            if (o instanceof LTACameraObject) {
                LTACameraObject camObj = (LTACameraObject) o;


                //map.addMarker(new MarkerOptions().position(new LatLng(camObj.getLat(), camObj.getLon())));
                selectMarkers.add(map.addMarker(new MarkerOptions()
                        .position((new LatLng(camObj.getLat(), camObj.getLon())))

                        .icon(BitmapDescriptorFactory.fromBitmap(
                                BitmapFactory.decodeResource(res,camIcon)))));
                selectMarkers.get(markerTracker).setTag(camObj);
                selectMarkers.get(markerTracker).showInfoWindow();

                selectMarkers.get(markerTracker).hideInfoWindow();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                selectMarkers.get(markerTracker).showInfoWindow();
                markerTracker++;
                trackCurrent = false;


                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom((new LatLng(camObj.getLat(), camObj.getLon())), 14);
                map.animateCamera(yourLocation);
            }

            if (o instanceof IncidentObject) {
                IncidentObject camObj = (IncidentObject) o;


                int markchoiceinci = MapMarkerSelection.IncidentChoice(camObj.getType());
                //map.addMarker(new MarkerOptions().position(new LatLng(camObj.getLat(), camObj.getLon())));
                selectMarkers.add(map.addMarker(new MarkerOptions()
                        .position((new LatLng(camObj.getLatitude(), camObj.getLongitude())))

                        .icon(BitmapDescriptorFactory.fromBitmap(
                                BitmapFactory.decodeResource(res,markchoiceinci)))));
                selectMarkers.get(markerTracker).setTag(camObj);
                selectMarkers.get(markerTracker).showInfoWindow();

                selectMarkers.get(markerTracker).hideInfoWindow();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                selectMarkers.get(markerTracker).showInfoWindow();
                markerTracker++;
                trackCurrent = false;


                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom((new LatLng(camObj.getLatitude(), camObj.getLongitude())), 14);
                map.animateCamera(yourLocation);
            }

            if (o instanceof MallObject) {
                MallObject camObj = (MallObject) o;


                //map.addMarker(new MarkerOptions().position(new LatLng(camObj.getLat(), camObj.getLon())));
                selectMarkers.add(map.addMarker(new MarkerOptions()
                        .position((new LatLng(camObj.getLat(), camObj.getLong())))

                        .icon(BitmapDescriptorFactory.fromBitmap(
                                BitmapFactory.decodeResource(res,R.drawable.mallicon)))));
                selectMarkers.get(markerTracker).setTag(camObj);
                selectMarkers.get(markerTracker).showInfoWindow();

                selectMarkers.get(markerTracker).hideInfoWindow();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                selectMarkers.get(markerTracker).showInfoWindow();
                markerTracker++;
                trackCurrent = false;


                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom((new LatLng(camObj.getLat(), camObj.getLong())), 14);
                map.animateCamera(yourLocation);
            }

        }

    }

    SearchView searchView;
    private String mSearchString;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();

        inflater.inflate(R.menu.mapmenu, menu);
       // menu.clear();
        //inflater.inflate(R.menu.signin, menu);
        super.onCreateOptionsMenu(menu, inflater);

       // final MenuItem item = menu.findItem(R.id.action_search);

        final MenuItem itemcamrefresh = menu.findItem(R.id.action_addMarkers);
        //refresh = (item) MenuItemCompat.getActionView(itemrefresh);

        itemcamrefresh.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                cm =
                        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnected();

                if(isConnected) {


//                if (map != null) {
//                    map.clear();
//                    // addMapPointsCamTemp();
//
//                }
                    map.clear();
                    if(IncidentListMarker != null) {

                        for (Marker cmarker : IncidentListMarker) {
                            cmarker.remove();
                        }
                    }
                    if(CamMarkerList != null){
                        for (Marker marker: CamMarkerList) {
                            marker.remove();
                        }}
                    if(mDatabase!=null && incidentlistener!= null)
                        mDatabase.removeEventListener(incidentlistener);

                    mMapAsyncTaskAllocator.AsyncTasking("asynccam",getContext());




                }  else{
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        final MenuItem itemhdbnearby = menu.findItem(R.id.locbutton);
        //refresh = (item) MenuItemCompat.getActionView(itemrefresh);

        itemhdbnearby.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                cm =
                        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnected();

                if(isConnected) {


//                if (map != null) {
//                    map.clear();
//                    // addMapPointsCamTemp();
//
//                }
                    map.clear();
                    if(IncidentListMarker != null) {

                        for (Marker cmarker : IncidentListMarker) {
                            cmarker.remove();
                        }
                    }
                    if(CamMarkerList != null){
                        for (Marker marker: CamMarkerList) {
                            marker.remove();
                        }}
                      if(mDatabase!=null && incidentlistener!= null)
                          mDatabase.removeEventListener(incidentlistener);

                    getCurrentLocation();




                }  else{
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

//        if (id == R.id.action_addMarkers) {
////            progressDialog = ProgressDialog.show(getContext(), "Please wait.",
////                    "Updating Traffic Cameras...", true);
//
//            cm =
//                    (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//
//            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//            boolean isConnected = activeNetwork != null &&
//                    activeNetwork.isConnected();
//
//            if(isConnected) {
//
//
////                if (map != null) {
////                    map.clear();
////                    // addMapPointsCamTemp();
////
////                }
//                map.clear();
//                if(IncidentListMarker != null) {
//
//                    for (Marker cmarker : IncidentListMarker) {
//                        cmarker.remove();
//                    }
//                }
//                if(CamMarkerList != null){
//                    for (Marker marker: CamMarkerList) {
//                        marker.remove();
//                    }}
//                mDatabase.removeEventListener(incidentlistener);
//
//                mMapAsyncTaskAllocator.AsyncTasking("asynccam",getContext());
//
//
//
//
//            }  else{
//                progressDialog.dismiss();
//            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//                   }
//
//
//
//
//
//
//
//
//
//
//            return true;
//        }
       // else
//            if (id == R.id.locbutton) {
//
//          //  dataUpdated("hdbcp");
//         //   ((MainActivityUI) getActivity()).dataUpdated("hdbcp");
//
//
//            getCurrentLocation();
//           // List<IncidentObject> incilist2 = IncidentSingletonPattern.getIncidentList();
//            //addincidentpoints(incilist2);
//
//
//
//
//            return true;
//        }
        /*else if(id == R.id.action_search){
           *//* FragmentManager fm = ((MainActivityUI) getActivity()).getSupportFragmentManager();
            SearchActivity fragment = new SearchActivity();
            fm.beginTransaction().add((R.id.mainContain,fragment).commit();
*//*

            SearchActivity nextFrag= new SearchActivity();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainContain, nextFrag,"findThisFragment")
                    .addToBackStack(null)
                    .commit();
            return  true;
        }
*/



        // else if (id == R.id.action_addCPMarkers)
           // addMapPointsCP();
           // rangeCheckLatlon(mCurrentLocation);

        return super.onOptionsItemSelected(item);
    }
    boolean trackCurrent = false;
    Activity parentAct;
    Resources res;
    int camIcon,cpIcon,destIcon,hdbIcon;
    View v;
    String[] mapinfo;
    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
           // view = inflater.inflate(R.layout.map, container, false);
            v = inflater.inflate(R.layout.some_layout, container, false);

        } catch (InflateException e) {
        /* map is already there, just return view as it is */
   //    return v;
            v = inflater.inflate(R.layout.some_layout, container, false);

        }
        //MapAsyncTaskAllocator.setListener(this);


        parentAct = getActivity();

        res = getContext().getResources();
        camIcon = R.drawable.carmark6;
        cpIcon = R.drawable.mallicon;
        hdbIcon = R.drawable.hdbicon;
        destIcon = R.drawable.desmark;

        manager = (LocationManager) getActivity().getSystemService( Context.LOCATION_SERVICE );


        /*
        mapinfo = new String[] {(getResources().
                getString(R.drawable.carmark5))};*/

       /* if (!checkLocationPermission())
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        setHasOptionsMenu(true);
*/
       /* if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
*/
        if (!checkLocationPermission())
           // ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);

        setHasOptionsMenu(true);



        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        ImageButton ib = (ImageButton) v.findViewById(R.id.imageButton);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(map.isTrafficEnabled()) {
                    map.setTrafficEnabled(false);
                    Toast.makeText(getActivity(), "Traffic Status Off", Toast.LENGTH_SHORT).show();

                }
                else if(!map.isTrafficEnabled()){
                    map.setTrafficEnabled(true);
                    Toast.makeText(getActivity(), "Traffic Status On", Toast.LENGTH_SHORT).show();

                }
            }
        });

        final boolean[] tiltCheck = {false};
        final boolean[] infoShown = {false};
        ImageButton ib2 = (ImageButton) v.findViewById(R.id.imageButton2);
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();
                } else if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && userLocation != null) {

                    if (!trackCurrent) {
                        infoShown[0] = false;

                        if (!tiltCheck[0]) {
                            /*if (cmp != null) {
                                CameraPosition cameraPosition = new CameraPosition.Builder().
                                        target(new LatLng(cmp.getLat(), cmp.getLon())).
                                        tilt(90).
                                        zoom(15).
                                        // bearing(userLocation.getBearing()).
                                                build();
                                Toast.makeText(getActivity(), "Inspection View", Toast.LENGTH_SHORT).show();


                                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                tiltCheck[0] = true;
                            } else*/
                            for (Marker marker : camListMarker) {
                                if (marker.isInfoWindowShown()) {
                                    CameraPosition cameraPosition = new CameraPosition.Builder().
                                            target(marker.getPosition()).
                                            tilt(90).
                                            zoom(15).
                                            // bearing(userLocation.getBearing()).
                                                    build();
                                    Toast.makeText(getActivity(), "Inspection View", Toast.LENGTH_SHORT).show();


                                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                    tiltCheck[0] = true;
                                    infoShown[0] = true;
                                }
                            }

                            if (!infoShown[0]) {
                                CameraPosition cameraPosition = new CameraPosition.Builder().
                                        target(new LatLng(userLocation.getLatitude(), userLocation.getLongitude())).
                                        tilt(90).
                                        zoom(15).
                                        bearing(userLocation.getBearing()).
                                        build();
                                Toast.makeText(getActivity(), "Inspection View", Toast.LENGTH_SHORT).show();


                                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                tiltCheck[0] = true;
                                //infoShown = false;
                            }
                        } else if (tiltCheck[0]) {
                            CameraPosition cameraPosition = new CameraPosition.Builder().
                                    target(new LatLng(userLocation.getLatitude(), userLocation.getLongitude())).
                                    tilt(10).
                                    zoom(12).
                                    bearing(0).
                                    build();

                            Toast.makeText(getActivity(), "Standard View", Toast.LENGTH_SHORT).show();


                            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            tiltCheck[0] = false;

                        }
                    } else if (navTilt[0]) {
                        CameraPosition cameraPosition = new CameraPosition.Builder().
                                target(new LatLng(userLocation.getLatitude(), userLocation.getLongitude())).
                                tilt(0).
                                zoom(17).
                                bearing(userLocation.getBearing()).
                                build();

                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        Toast.makeText(getActivity(), "3D View Off", Toast.LENGTH_SHORT).show();



                        navTilt[0] = false;

                        tiltCheck[0] = false;

                    } else if (!navTilt[0]) {




                        Toast.makeText(getActivity(), "3D View On", Toast.LENGTH_SHORT).show();


                        CameraPosition cameraPosition = new CameraPosition.Builder().
                                target(new LatLng(userLocation.getLatitude(), userLocation.getLongitude())).
                                tilt(80).
                                zoom(17).
                                bearing(userLocation.getBearing()).
                                build();

                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        navTilt[0] = true;
                        tiltCheck[0] = true;
                    }
                }
            }




        });

        ImageButton ib3 = (ImageButton) v.findViewById(R.id.imageButton3);
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(curMapTypeIndex == 1) {
                    curMapTypeIndex = 2;
                    map.setMapType(MAP_TYPES[curMapTypeIndex]);
                    Toast.makeText(getActivity(), "Satellite Mode", Toast.LENGTH_SHORT).show();

                }else if(curMapTypeIndex == 2){

                    curMapTypeIndex = 1;
                    map.setMapType(MAP_TYPES[curMapTypeIndex]);
                    Toast.makeText(getActivity(), "Map Mode", Toast.LENGTH_SHORT).show();


                }

            }
        });



     //   autocompleteFragment = (PlaceAutocompleteFragment)  getActivity().getFragmentManager().findFragmentById(R.id.toDestinationAutoComplete);

     //   if (autocompleteFragment == null) {

        SupportPlaceAutocompleteFragment autocompleteFragment = new SupportPlaceAutocompleteFragment();
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.toDestinationAutoComplete, autocompleteFragment);
        ft.commit();

//        autocompleteFragment = (PlaceAutocompleteFragment)
//                    getActivity().getFragmentManager().findFragmentById(R.id.toDestinationAutoComplete);

//        if(autocompleteFragment==null){
//            //    rootView = inflater.inflate(R.layout.MapFragment, container, false);
//          //  v = inflater.inflate(R.layout.some_layout, container, false);
//            autocompleteFragment = (PlaceAutocompleteFragment)
//                    getActivity().getFragmentManager().findFragmentById(R.id.toDestinationAutoComplete);
//
//        }


//        autocompleteFragment = (PlaceAutocompleteFragment)
//                getActivity().getFragmentManager().findFragmentById(R.id.toDestinationAutoComplete);

            AutocompleteFilter filter =
                    new AutocompleteFilter.Builder().setCountry("SG").build();

          //  if (filter != null) {
                autocompleteFragment.setFilter(filter);
          //  }
         //   autocompleteFragment.setHint("To Destination");

            autocompleteFragment.setOnPlaceSelectedListener(new DirectionFinderListener() {

                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.
                    Toast.makeText(getActivity(), place.getName(), Toast.LENGTH_SHORT).show();
                    if (destinationMarker != null) {
                        destinationMarker.remove();
                    }
                    destination = place.getLatLng();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(destination);
                    markerOptions.title("Destination");
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
                            BitmapFactory.decodeResource(res, destIcon)));
                    destinationMarker = map.addMarker(markerOptions);
                    destinationMarker.showInfoWindow();
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 12));


                    mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                    //}

                    if (mCurrentLocation != null) {

                        DirectionFinder directionFinder = new DirectionFinder
                                (this, mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), destination.latitude, destination.longitude);
                        directionFinder.execute();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Dialog_Alert);
                        //final AlertDialog alertDialog = builder.create();
                        builder.setTitle("Location Error");
                        builder.setMessage("Unable to Obatin current location");
                        builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                dialog.dismiss();


                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        builder.create();
                        alertDialog.show();
                        //  Toast.makeText(getActivity(), "Unable to Obatin current location, please try again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.

                    Toast.makeText(getActivity(), "An error occurred: " + status, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onDirectionFinderStart() {
                    progressDialog = ProgressDialog.show(getContext(), "Please wait.",
                            "Finding direction...", true);
                    if (polylinePaths != null) {
                        for (int i = 0; i < polylinePaths.size(); i++) {
                            polylinePaths.get(i).remove();
                        }
                    }
                }

                @Override
                public void onDirectionFinderSuccess(List<Route> routes) {
                    progressDialog.dismiss();
                    polylinePaths = new ArrayList<>();
                    map.setTrafficEnabled(false);
                    TextView modeText = (TextView) getActivity().findViewById(R.id.modeText);
                    TextView distanceText = (TextView) getActivity().findViewById(R.id.distanceText);
                    TextView durationText = (TextView) getActivity().findViewById(R.id.durationText);

                    for (int i = 0; i < routes.size(); i++) {
                        Route route = routes.get(i);
                        modeText.setText("Mode: Driving");
                        distanceText.setText("Distance: " + route.getDistance().getText());
                        durationText.setText("Duration: " + route.getDuration().getText());
                        PolylineOptions polylineOptions = new PolylineOptions().
                                geodesic(true).
                                color(Color.RED).
                                width(15);
                        for (int j = 0; j < route.getPoints().size(); j++) {
                            polylineOptions.add(route.getPoints().get(j));
                        }

                        polylinePaths.add(map.addPolyline(polylineOptions));
                    }
                }
            });
      //  }

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        //addMapPointsCam();

        return v;


    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(isAdded()) {
            map = googleMap;
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.getUiSettings().isCompassEnabled();
            map.setLatLngBoundsForCameraTarget(SINGAPORE);

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            map.setMyLocationEnabled(true);
            googleMap.setOnMarkerClickListener(this);
            //googleMap.setOnMapLongClickListener(this);
            googleMap.setOnInfoWindowClickListener(this);
            googleMap.setOnMapClickListener(this);
            googleMap.setTrafficEnabled(true);
            googleMap.setBuildingsEnabled(true);
            // Enable / Disable zooming controls
            googleMap.getUiSettings().setZoomControlsEnabled(true);

            // Enable / Disable my location button
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            // Enable / Disable Compass icon
            googleMap.getUiSettings().setCompassEnabled(true);

            // Enable / Disable Rotate gesture
            googleMap.getUiSettings().setRotateGesturesEnabled(true);

            // Enable / Disable zooming functionality
            googleMap.getUiSettings().setZoomGesturesEnabled(true);

            onDataUpdate("incident");

            map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {


                    View v = null;
                    // LTACameraObject cmp = (LTACameraObject) marker.getTag();
                    Object markObject = (Object) marker.getTag();
                     if (markObject instanceof LTACameraObject) {
                        LTACameraObject cmp = (LTACameraObject) markObject;
                         Log.d("Check Marker Obj ", cmp.getName());

                         v = getActivity().getLayoutInflater().inflate(R.layout.custom_info_window, null);

                        // Getting reference to the TextView to set latitude
                        //  TextView addressTxt = (TextView) v.findViewById(R.id.addressTxt);
                        TextView addressTxt = (TextView) v.findViewById(R.id.txt1);


                        ImageView mainImg = (ImageView) v.findViewById(R.id.clientPic);
                        String imgUri = cmp.getImage();

                         TextView dateText = (TextView) v.findViewById(R.id.dateStamp);
                         String[] timeStampOut =  new TimeStampProcessing(cmp).getDate();
                         dateText.setText(timeStampOut[0]);

                         TextView timeText = (TextView) v.findViewById(R.id.timeStamp);
                         timeText.setText(timeStampOut[1]+" : "+timeStampOut[2]+" : "+timeStampOut[3]);


                         if (imgUri != null) {


                            addressTxt.setText(((LTACameraObject) marker.getTag()).getName());

                            Picasso.with(getActivity()).load(imgUri).into(mainImg, new InfoWindowRefresher(marker));

                        }
                    } else if (markObject instanceof HDBCarParkObject) {
                         HDBCarParkObject cpmp = (HDBCarParkObject) markObject;
                         v = getActivity().getLayoutInflater().inflate(R.layout.custom_cpinfo_window, null);
                         TextView addressTxt = (TextView) v.findViewById(R.id.txt1);
                         addressTxt.setText(((HDBCarParkObject) marker.getTag()).getAddress());

                         TextView cpTypeTxt = (TextView) v.findViewById(R.id.txt0);
                         cpTypeTxt.setText(((HDBCarParkObject) marker.getTag()).getCar_park_type());

                         TextView stTypeTxt = (TextView) v.findViewById(R.id.txt2);
                         stTypeTxt.setText("Short-Term Parking: "+((HDBCarParkObject) marker.getTag()).getShort_term_parking());

                         TextView fpTypeTxt = (TextView) v.findViewById(R.id.txt3);
                         fpTypeTxt.setText("Free-Parking: "+((HDBCarParkObject) marker.getTag()).getFree_parking());

                         TextView npTypeTxt = (TextView) v.findViewById(R.id.txt4);
                         npTypeTxt.setText("Night-Parking: "+((HDBCarParkObject) marker.getTag()).isNight_parking());


                         TextView timeText = (TextView) v.findViewById(R.id.timeStamp);
                          timeText.setText(marker.getSnippet());


                     }else if (markObject instanceof IncidentObject) {
                         IncidentObject cpmp = (IncidentObject) markObject;
                         v = getActivity().getLayoutInflater().inflate(R.layout.custom_inciinfo_window, null);

                         TextView dataTxt = (TextView) v.findViewById(R.id.incidateStamp);
                         dataTxt.setText(StringParser.between(((IncidentObject) marker.getTag()).getMessage(),"(",")"));

                         TextView timeTxt = (TextView) v.findViewById(R.id.incitimeStamp);
                         timeTxt.setText(StringParser.between(((IncidentObject) marker.getTag()).getMessage(),")"," "));

                         TextView msgTxt = (TextView) v.findViewById(R.id.incidetail);
                         String markermsg = StringParser.between(((IncidentObject) marker.getTag()).getMessage(),"on",".");
                         if(markermsg.isEmpty()){
                             markermsg = StringParser.between(((IncidentObject) marker.getTag()).getMessage(),"in",".");

                         }
                         if(markermsg.isEmpty()){
                             markermsg = ((IncidentObject) marker.getTag()).getMessage();
                         }
                         msgTxt.setText(markermsg);

                         TextView typeTxt = (TextView) v.findViewById(R.id.intypeTitle);
                         typeTxt.setText(((IncidentObject) marker.getTag()).getType());

                         TextView adviceTxt = (TextView) v.findViewById(R.id.inciadvice);
                         adviceTxt.setText(StringParser.after(((IncidentObject) marker.getTag()).getMessage(),"."));

                         //  TextView timeText = (TextView) v.findViewById(R.id.timeStamp);
                         //timeText.setText(marker.getSnippet());


                     }else if (markObject instanceof MallObject) {
                         //IncidentObject cpmp = (IncidentObject) markObject;
                         v = getActivity().getLayoutInflater().inflate(R.layout.custom_mallinfo_window, null);

                         TextView dataTxt = (TextView) v.findViewById(R.id.developmentmark);
                         dataTxt.setText(((MallObject) marker.getTag()).getDevelopment());

                         TextView lotmallTxt = (TextView) v.findViewById(R.id.malllotmark);
                         lotmallTxt.setText(String.valueOf(   ((MallObject) marker.getTag()).getAvailableLots()));


                         TextView areamallTxt = (TextView) v.findViewById(R.id.areamark);
                         areamallTxt.setText(((MallObject) marker.getTag()).getArea());

                         //  TextView timeText = (TextView) v.findViewById(R.id.timeStamp);
                         //timeText.setText(marker.getSnippet());


                     }
                     else if (marker != null) {
                        if (marker.getTitle().equals("Current Location") || marker.getTitle().equals("Destination")) {


                            String uri = null;
                            List<TitleCameraObject> mapCamList = CameraDAOUsage.CameraDAOgetTitleObjectList();
                            for (int i = 0; i < mapCamList.size(); i++) {
                                if (mapCamList.get(i).getChildList().get(0).getLat() == marker.getPosition().latitude && mapCamList.get(i).getChildList().get(0).getLon() == marker.getPosition().longitude) {
                                    uri = mapCamList.get(i).getChildList().get(0).getImage();
                                }
                            }
                            LatLng latlng = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                            Address address = getAddressFromLatLng(latlng);


                            v = getActivity().getLayoutInflater().inflate(R.layout.infowindow_layout, null);

                            TextView markerTitle = (TextView) v.findViewById(R.id.markerTitle);
                            ImageView imageView = (ImageView) v.findViewById(R.id.mapcamera);
                            TextView cameraAddress = (TextView) v.findViewById(R.id.cameraAddress);
                            TextView cameraCity = (TextView) v.findViewById(R.id.cameraCity);
                            TextView cameraState = (TextView) v.findViewById(R.id.cameraState);
                            TextView cameraCountry = (TextView) v.findViewById(R.id.cameraCountry);
                            TextView cameraPostalCode = (TextView) v.findViewById(R.id.cameraPostalCode);
                            TextView cameraKnownName = (TextView) v.findViewById(R.id.cameraKnownName);

                            if (uri == null) {
                                imageView.setVisibility(View.GONE);
                            } else {
                                ImageLoader imageLoader = ImageLoader.getInstance();
                                imageLoader.displayImage(uri, imageView);
                                Log.d("IMAGE", uri);
                            }
                            if (marker.getTitle().equals("Current Location")) {
                                markerTitle.setTextColor(Color.BLUE);
                                markerTitle.setText("Current Location");
                            } else if (marker.getTitle().equals("Destination")) {
                                markerTitle.setTextColor(Color.RED);
                                markerTitle.setText("Destination");
                            } else if (marker.getTitle().equals("Traffic Camera")) {
                                markerTitle.setTextColor(Color.GREEN);
                                markerTitle.setText("Traffic Camera");
                            } else {
                                markerTitle.setTextColor(Color.MAGENTA);
                                markerTitle.setText("Placed Marker");
                            }

                            Log.d("Address:", address.getAddressLine(0));
                            if (address.getAddressLine(0) == null) {
                                cameraAddress.setText("Address:\t-");
                            } else {
                                cameraAddress.setText("Address:\t" + address.getAddressLine(0));
                            }

                            if (address.getLocality() == null) {
                                cameraCity.setText("City:\t-");
                            } else {
                                cameraCity.setText("City:\t" + address.getLocality());
                            }
                            //  Log.d("Address:",address.getLocality());

                            if (address.getAdminArea() == null) {
                                cameraState.setText("State:\t-");
                            } else {
                                cameraState.setText("State:\t" + address.getAdminArea());
                            }

                            if (address.getCountryName() == null) {
                                cameraCountry.setText("Country:\t-");
                            } else {
                                cameraCountry.setText("Country:\t" + address.getCountryName());
                            }

                            if (address.getPostalCode() == null) {
                                cameraPostalCode.setText("Postal Code:\t-");
                            } else {
                                cameraPostalCode.setText("Postal Code:\t" + address.getPostalCode());
                            }

                            if (address.getFeatureName() == null) {
                                cameraKnownName.setText("Known Name:\t-");
                            } else {
                                cameraKnownName.setText("Known Name:\t" + address.getFeatureName());
                            }
                        }


                    }


                    return v;
                }
            });



            googleMap.setOnMarkerClickListener(this);
            googleMap.setOnInfoWindowClickListener(this);
            googleMap.setOnMapClickListener(this);

            //googleMap.setTrafficEnabled(true);
            // Enable / Disable zooming controls
            googleMap.getUiSettings().setZoomControlsEnabled(true);

            // Enable / Disable my location button
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            // Enable / Disable Compass icon
            googleMap.getUiSettings().setCompassEnabled(true);

            // Enable / Disable Rotate gesture
            googleMap.getUiSettings().setRotateGesturesEnabled(true);

            // Enable / Disable zooming functionality
            googleMap.getUiSettings().setZoomGesturesEnabled(true);


            // map.moveCamera(CameraUpdateFactory.newLatLng(43.1, -87.9));

           try {
                // Customise the styling of the base map using a JSON object defined
                // in a raw resource file.
                boolean success = map.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.mapstyle));

                if (!success) {
                    Log.e("Map", "Style parsing failed.");
                }
            } catch (Resources.NotFoundException e) {
                Log.e("Map", "Can't find style.", e);
            }
        }

        //add location button click listener

      //  map.getUiSettings().setMyLocationButtonEnabled(true);

        if(map.getUiSettings().isMyLocationButtonEnabled()) {
            map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {

                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        buildAlertMessageNoGps();
                    } else {

                        if (trackCurrent == false) {
                            Toast.makeText(getActivity(), "Navigation Mode On", Toast.LENGTH_SHORT).show();

                            trackCurrent = true;
                            navTilt[0] = true;

                        } else if (trackCurrent == true) {
                            Toast.makeText(getActivity(), "Navigation Mode Off", Toast.LENGTH_SHORT).show();
                            navTilt[0] = false;

                            trackCurrent = false;
                        }
                        userLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && userLocation != null) {

                            CameraPosition cameraPosition = new CameraPosition.Builder().
                                    target(new LatLng(userLocation.getLatitude(), userLocation.getLongitude())).
                                    tilt(80).
                                    zoom(17).
                                    bearing(userLocation.getBearing()).
                                    build();

                            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
                                @Override
                                public void onFinish() {
                                    // Code to execute when the animateCamera task has finished
                                }

                                @Override
                                public void onCancel() {
                                    // Code to execute when the user has canceled the animateCamera task
                                }
                            });
                        }




                    }
                    return false;
                }
            });
        }






    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//
//                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                            == PackageManager.PERMISSION_GRANTED) {
//
//                        // mapView.getMapAsync(this);

                    map.setMyLocationEnabled(true);

                    map.setOnMarkerClickListener(this);
                    //googleMap.setOnMapLongClickListener(this);
                    map.setOnInfoWindowClickListener(this);
                    map.setOnMapClickListener(this);
                    map.setTrafficEnabled(true);
                    map.setBuildingsEnabled(true);
                    // Enable / Disable zooming controls
                    map.getUiSettings().setZoomControlsEnabled(true);

                    // Enable / Disable my location button
                    map.getUiSettings().setMyLocationButtonEnabled(true);

                    // Enable / Disable Compass icon
                    map.getUiSettings().setCompassEnabled(true);

                    // Enable / Disable Rotate gesture
                    map.getUiSettings().setRotateGesturesEnabled(true);

                    // Enable / Disable zooming functionality
                    map.getUiSettings().setZoomGesturesEnabled(true);

                    onDataUpdate("incident");

                    map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {


                        @Override
                        public View getInfoWindow(Marker marker) {
                            return null;
                        }

                        @Override
                        public View getInfoContents(Marker marker) {


                            View v = null;
                            // LTACameraObject cmp = (LTACameraObject) marker.getTag();
                            Object markObject = (Object) marker.getTag();
                            if (markObject instanceof LTACameraObject) {
                                LTACameraObject cmp = (LTACameraObject) markObject;
                                Log.d("Check Marker Obj ", cmp.getName());

                                v = getActivity().getLayoutInflater().inflate(R.layout.custom_info_window, null);

                                // Getting reference to the TextView to set latitude
                                //  TextView addressTxt = (TextView) v.findViewById(R.id.addressTxt);
                                TextView addressTxt = (TextView) v.findViewById(R.id.txt1);


                                ImageView mainImg = (ImageView) v.findViewById(R.id.clientPic);
                                String imgUri = cmp.getImage();

                                TextView dateText = (TextView) v.findViewById(R.id.dateStamp);
                                String[] timeStampOut =  new TimeStampProcessing(cmp).getDate();
                                dateText.setText(timeStampOut[0]);

                                TextView timeText = (TextView) v.findViewById(R.id.timeStamp);
                                timeText.setText(timeStampOut[1]+" : "+timeStampOut[2]+" : "+timeStampOut[3]);


                                if (imgUri != null) {


                                    addressTxt.setText(((LTACameraObject) marker.getTag()).getName());

                                    Picasso.with(getActivity()).load(imgUri).into(mainImg, new InfoWindowRefresher(marker));

                                }
                            } else if (markObject instanceof HDBCarParkObject) {
                                HDBCarParkObject cpmp = (HDBCarParkObject) markObject;
                                v = getActivity().getLayoutInflater().inflate(R.layout.custom_cpinfo_window, null);
                                TextView addressTxt = (TextView) v.findViewById(R.id.txt1);
                                addressTxt.setText(((HDBCarParkObject) marker.getTag()).getAddress());

                                TextView cpTypeTxt = (TextView) v.findViewById(R.id.txt0);
                                cpTypeTxt.setText(((HDBCarParkObject) marker.getTag()).getCar_park_type());

                                TextView stTypeTxt = (TextView) v.findViewById(R.id.txt2);
                                stTypeTxt.setText("Short-Term Parking: "+((HDBCarParkObject) marker.getTag()).getShort_term_parking());

                                TextView fpTypeTxt = (TextView) v.findViewById(R.id.txt3);
                                fpTypeTxt.setText("Free-Parking: "+((HDBCarParkObject) marker.getTag()).getFree_parking());

                                TextView npTypeTxt = (TextView) v.findViewById(R.id.txt4);
                                npTypeTxt.setText("Night-Parking: "+((HDBCarParkObject) marker.getTag()).isNight_parking());


                                TextView timeText = (TextView) v.findViewById(R.id.timeStamp);
                                timeText.setText(marker.getSnippet());


                            }else if (markObject instanceof IncidentObject) {
                                IncidentObject cpmp = (IncidentObject) markObject;
                                v = getActivity().getLayoutInflater().inflate(R.layout.custom_inciinfo_window, null);

                                TextView dataTxt = (TextView) v.findViewById(R.id.incidateStamp);
                                dataTxt.setText(StringParser.between(((IncidentObject) marker.getTag()).getMessage(),"(",")"));

                                TextView timeTxt = (TextView) v.findViewById(R.id.incitimeStamp);
                                timeTxt.setText(StringParser.between(((IncidentObject) marker.getTag()).getMessage(),")"," "));

                                TextView msgTxt = (TextView) v.findViewById(R.id.incidetail);
                                String markermsg = StringParser.between(((IncidentObject) marker.getTag()).getMessage(),"on",".");
                                if(markermsg.isEmpty()){
                                    markermsg = StringParser.between(((IncidentObject) marker.getTag()).getMessage(),"in",".");

                                }
                                if(markermsg.isEmpty()){
                                    markermsg = ((IncidentObject) marker.getTag()).getMessage();
                                }
                                msgTxt.setText(markermsg);

                                TextView typeTxt = (TextView) v.findViewById(R.id.intypeTitle);
                                typeTxt.setText(((IncidentObject) marker.getTag()).getType());

                                TextView adviceTxt = (TextView) v.findViewById(R.id.inciadvice);
                                adviceTxt.setText(StringParser.after(((IncidentObject) marker.getTag()).getMessage(),"."));

                                //  TextView timeText = (TextView) v.findViewById(R.id.timeStamp);
                                //timeText.setText(marker.getSnippet());


                            }else if (markObject instanceof MallObject) {
                                //IncidentObject cpmp = (IncidentObject) markObject;
                                v = getActivity().getLayoutInflater().inflate(R.layout.custom_mallinfo_window, null);

                                TextView dataTxt = (TextView) v.findViewById(R.id.developmentmark);
                                dataTxt.setText(((MallObject) marker.getTag()).getDevelopment());

                                TextView lotmallTxt = (TextView) v.findViewById(R.id.malllotmark);
                                lotmallTxt.setText(String.valueOf(   ((MallObject) marker.getTag()).getAvailableLots()));


                                TextView areamallTxt = (TextView) v.findViewById(R.id.areamark);
                                areamallTxt.setText(((MallObject) marker.getTag()).getArea());

                                //  TextView timeText = (TextView) v.findViewById(R.id.timeStamp);
                                //timeText.setText(marker.getSnippet());


                            }
                            else if (marker != null) {
                                if (marker.getTitle().equals("Current Location") || marker.getTitle().equals("Destination")) {


                                    String uri = null;
                                    List<TitleCameraObject> mapCamList = CameraDAOUsage.CameraDAOgetTitleObjectList();
                                    for (int i = 0; i < mapCamList.size(); i++) {
                                        if (mapCamList.get(i).getChildList().get(0).getLat() == marker.getPosition().latitude && mapCamList.get(i).getChildList().get(0).getLon() == marker.getPosition().longitude) {
                                            uri = mapCamList.get(i).getChildList().get(0).getImage();
                                        }
                                    }
                                    LatLng latlng = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                                    Address address = getAddressFromLatLng(latlng);


                                    v = getActivity().getLayoutInflater().inflate(R.layout.infowindow_layout, null);

                                    TextView markerTitle = (TextView) v.findViewById(R.id.markerTitle);
                                    ImageView imageView = (ImageView) v.findViewById(R.id.mapcamera);
                                    TextView cameraAddress = (TextView) v.findViewById(R.id.cameraAddress);
                                    TextView cameraCity = (TextView) v.findViewById(R.id.cameraCity);
                                    TextView cameraState = (TextView) v.findViewById(R.id.cameraState);
                                    TextView cameraCountry = (TextView) v.findViewById(R.id.cameraCountry);
                                    TextView cameraPostalCode = (TextView) v.findViewById(R.id.cameraPostalCode);
                                    TextView cameraKnownName = (TextView) v.findViewById(R.id.cameraKnownName);

                                    if (uri == null) {
                                        imageView.setVisibility(View.GONE);
                                    } else {
                                        ImageLoader imageLoader = ImageLoader.getInstance();
                                        imageLoader.displayImage(uri, imageView);
                                        Log.d("IMAGE", uri);
                                    }
                                    if (marker.getTitle().equals("Current Location")) {
                                        markerTitle.setTextColor(Color.BLUE);
                                        markerTitle.setText("Current Location");
                                    } else if (marker.getTitle().equals("Destination")) {
                                        markerTitle.setTextColor(Color.RED);
                                        markerTitle.setText("Destination");
                                    } else if (marker.getTitle().equals("Traffic Camera")) {
                                        markerTitle.setTextColor(Color.GREEN);
                                        markerTitle.setText("Traffic Camera");
                                    } else {
                                        markerTitle.setTextColor(Color.MAGENTA);
                                        markerTitle.setText("Placed Marker");
                                    }

                                    Log.d("Address:", address.getAddressLine(0));
                                    if (address.getAddressLine(0) == null) {
                                        cameraAddress.setText("Address:\t-");
                                    } else {
                                        cameraAddress.setText("Address:\t" + address.getAddressLine(0));
                                    }

                                    if (address.getLocality() == null) {
                                        cameraCity.setText("City:\t-");
                                    } else {
                                        cameraCity.setText("City:\t" + address.getLocality());
                                    }
                                    //  Log.d("Address:",address.getLocality());

                                    if (address.getAdminArea() == null) {
                                        cameraState.setText("State:\t-");
                                    } else {
                                        cameraState.setText("State:\t" + address.getAdminArea());
                                    }

                                    if (address.getCountryName() == null) {
                                        cameraCountry.setText("Country:\t-");
                                    } else {
                                        cameraCountry.setText("Country:\t" + address.getCountryName());
                                    }

                                    if (address.getPostalCode() == null) {
                                        cameraPostalCode.setText("Postal Code:\t-");
                                    } else {
                                        cameraPostalCode.setText("Postal Code:\t" + address.getPostalCode());
                                    }

                                    if (address.getFeatureName() == null) {
                                        cameraKnownName.setText("Known Name:\t-");
                                    } else {
                                        cameraKnownName.setText("Known Name:\t" + address.getFeatureName());
                                    }
                                }


                            }


                            return v;
                        }
                    });



//                    map.setOnMarkerClickListener(this);
//                    map.setOnInfoWindowClickListener(this);
//                    map.setOnMapClickListener(this);

                    //map.setTrafficEnabled(true);
                    // Enable / Disable zooming controls
                    map.getUiSettings().setZoomControlsEnabled(true);

                    // Enable / Disable my location button
                    map.getUiSettings().setMyLocationButtonEnabled(true);

                    // Enable / Disable Compass icon
                    map.getUiSettings().setCompassEnabled(true);

                    // Enable / Disable Rotate gesture
                    map.getUiSettings().setRotateGesturesEnabled(true);

                    // Enable / Disable zooming functionality
                    map.getUiSettings().setZoomGesturesEnabled(true);


                    // map.moveCamera(CameraUpdateFactory.newLatLng(43.1, -87.9));

                    try {
                        // Customise the styling of the base map using a JSON object defined
                        // in a raw resource file.
                        boolean success = map.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.mapstyle));

                        if (!success) {
                            Log.e("Map", "Style parsing failed.");
                        }
                    } catch (Resources.NotFoundException e) {
                        Log.e("Map", "Can't find style.", e);
                    }
                }

                //add location button click listener

                //  map.getUiSettings().setMyLocationButtonEnabled(true);

                if(map.getUiSettings().isMyLocationButtonEnabled()) {
                    map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                        @Override
                        public boolean onMyLocationButtonClick() {

                            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                buildAlertMessageNoGps();
                            } else {

                                if (trackCurrent == false) {
                                    Toast.makeText(getActivity(), "Navigation Mode On", Toast.LENGTH_SHORT).show();

                                    trackCurrent = true;
                                    navTilt[0] = true;

                                } else if (trackCurrent == true) {
                                    Toast.makeText(getActivity(), "Navigation Mode Off", Toast.LENGTH_SHORT).show();
                                    navTilt[0] = false;

                                    trackCurrent = false;
                                }
                                userLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && userLocation != null) {

                                    CameraPosition cameraPosition = new CameraPosition.Builder().
                                            target(new LatLng(userLocation.getLatitude(), userLocation.getLongitude())).
                                            tilt(80).
                                            zoom(17).
                                            bearing(userLocation.getBearing()).
                                            build();

                                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
                                        @Override
                                        public void onFinish() {
                                            // Code to execute when the animateCamera task has finished
                                        }

                                        @Override
                                        public void onCancel() {
                                            // Code to execute when the user has canceled the animateCamera task
                                        }
                                    });
                                }




                            }
                            return false;
                        }
                    });





                    Log.d("Permission Accepted", "int Onrequest");
                  //  onMapReady();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = getActivity().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }




    private void addMapPointsCam() {
        if(isAdded()) {

        /*    progressDialog = ProgressDialog.show(getContext(), "Please wait.",
                    "Finding direction...", true);
*/

            cm =(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);


            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnected();

            if(isConnected) {


           List<TitleCameraObject> mapCamListinit = CameraDAOUsage.CameraDAOgetTitleObjectList(getActivity());

            while (mapCamListinit == null) {

                mapCamListinit = CameraDAOUsage.CameraDAOgetTitleObjectList(getActivity());

            }


            final List<TitleCameraObject> mapCamList = mapCamListinit;

            final LatLng[] point_new = new LatLng[mapCamList.size()];

            int i = 0;
//        map.clear();
            Handler handler = new Handler(Looper.getMainLooper());


            int loadedCheck;
            for (i = 0; i < mapCamList.size(); i++) {
                Log.d("AddMap Called ", String.valueOf(i));
                loadedCheck = 0;
                point_new[i] = new LatLng(mapCamList.get(i).getChildList().get(0)
                        .getLat(), mapCamList.get(i).getChildList().get(0).getLon());
                Log.d("Camera Lat ", String.valueOf(mapCamList.get(i).getChildList().get(0)
                        .getLat()));
                // Marker marker = map.addMarker(new MarkerOptions().position(point_new[i]));
                final int finalI = i;
                handler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                camListMarker.add(map.addMarker(new MarkerOptions()
                                                .position(point_new[finalI])
                                                .title("Traffic Information")
                                                .icon(BitmapDescriptorFactory.fromBitmap(
                                                        BitmapFactory.decodeResource(res,camIcon))

                                                ))
                                );
                                camListMarker.get(finalI).setTag(mapCamList.get(finalI).getChildList().get(0));
                               // LTACameraObject testObj = (LTACameraObject) camListMarker.get(finalI).getTag();
                             //   Log.d("Adding Cam Marker", testObj.getName());
                            }
                        }, (DELAY * (long) x++));
                //}


            }
        }
        }
        else{
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();

        }




        /*
        for(Marker marker: camListMarker) {
            marker.showInfoWindow();
            marker.hideInfoWindow();

        }*/
       /* progressDialog.dismiss();
*/
    }


    private void addMapPointsCamTemp() {
        if(isAdded()) {

            List<TitleCameraObject> mapCamListinit = CameraDAOUsage.CameraDAOgetTitleObjectList(getActivity());

            while (mapCamListinit == null) {

                mapCamListinit = CameraDAOUsage.CameraDAOgetTitleObjectList(getActivity());

            }


            final List<TitleCameraObject> mapCamList = mapCamListinit;

            final LatLng[] point_new = new LatLng[mapCamList.size()];

            int i = 0;
            final Marker[] marker = {null};
//        map.clear();
            Handler handler = new Handler(Looper.getMainLooper());


            int loadedCheck;
            for (i = 0; i < mapCamList.size(); i++) {
                Log.d("AddMap Called ", String.valueOf(i));
                loadedCheck = 0;
                point_new[i] = new LatLng(mapCamList.get(i).getChildList().get(0)
                        .getLat(), mapCamList.get(i).getChildList().get(0).getLon());
                // Marker marker = map.addMarker(new MarkerOptions().position(point_new[i]));
                final int finalI = i;
                handler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {

                                marker[0] = map.addMarker(new MarkerOptions()
                                        .position(point_new[finalI])

                                        .icon(BitmapDescriptorFactory.fromBitmap(
                                                BitmapFactory.decodeResource(res, camIcon))));
                                marker[0].setTag(mapCamList.get(finalI).getChildList().get(0));


                                LTACameraObject testObj = (LTACameraObject) marker[0].getTag();
                                Log.d("Adding Cam Marker", testObj.getName());
                            }
                        }, (DELAY * (long) x++));
                //}


            }
        }

    }





    private void addMapPointsCP() {


        Handler handler = new Handler(Looper.getMainLooper());

        int k = 0;
        if (carparkListMarker.size() == 0) {
            //    final List<TitleCarParkObject> mapCPList = CarParkDAOUsage.CarparkDAOgetTitleObjectList(getActivity());
            final List<HDBCarParkObject> mapCPList = HDBCarParkAPIDAOImplement.readCarparkObjectRawData(getContext());
            final LatLng[] point_cp_new = new LatLng[mapCPList.size()];

            int loadedCheck;
            for (k = 0; k < mapCPList.size(); k++) {

                loadedCheck = 0;
                point_cp_new[k] = new LatLng(mapCPList.get(k).getLatitude(), mapCPList.get(k).getLongitude());
                // Marker marker = map.addMarker(new MarkerOptions().position(point_new[i]));
                final int finalK = k;
                handler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {

                                carparkListMarker.add(map.addMarker(new MarkerOptions()
                                                .position(point_cp_new[finalK])
                                                .title(mapCPList.get(finalK).getAddress())
                                           /*     .icon(BitmapDescriptorFactory.fromBitmap(
                                                        BitmapFactory.decodeResource(getResources(),
                                                                R.drawable.cpicon)))*/


                                        )
                                );

                            }
                        }, (DELAY * (long) x++));
            }


        } else if (carparkListMarker.size() > 0) {
            if (carparkListMarker.get(k).isVisible()) {
                for (Marker m : carparkListMarker) {
                    m.setVisible(false);
                }
            } else {
                for (Marker m : carparkListMarker) {
                    m.setVisible(true);

                }
            }

        }

    }


    private void addincidentpoints(List<IncidentObject> incidentlist){


        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();

        if(isConnected) {
            if (map != null) {
                map.clear();
                // addMapPointsCamTemp();

            }


            Marker marker = null;
            //List<LTACameraObject> listUp = CameraDAOImplement.getCameraObjectList(getActivity());
            // LTACameraObject camObj = listUp.get(0);

            for (IncidentObject camObj : incidentlist) {
                //map.addMarker(new MarkerOptions().position(new LatLng(camObj.getLat(), camObj.getLon())));
                marker = map.addMarker(new MarkerOptions()
                        .position((new LatLng(camObj.getLatitude(), camObj.getLongitude())))

                        .icon(BitmapDescriptorFactory.fromBitmap(
                                BitmapFactory.decodeResource(res, camIcon))));
                marker.setTag(camObj);
            }
        }  else{
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

//    @Override
//    public void onResume() {
//        // if(mapView!=null)
//        super.onResume();
//
//        if(v!=null) {
//            if (mapView != null) {
//                mapView.onResume();
//            }
//            //  mapView = (MapView) v.findViewById(R.id.mapview);
//            //mapView.onCreate(savedInstanceState);
//
//            // mapView.getMapAsync(this);
//
//
//            if (map == null) {
//                //autocompleteFragment = (PlaceAutocompleteFragment)  getActivity().getFragmentManager().findFragmentById(R.id.toDestinationAutoComplete);
////                mapView = (MapView) getActivity().gfindFragmentById(R.id.mapview);
////                MapView.
////                mapView = (MapView) v.findViewById(R.id.mapview);
////                mapView.onCreate(savedInstanceState);
//
//                mapView.getMapAsync(this);
//            }
//        }
//
////        if(camListMarker==null){
////            addMapPointsCam();
////        }
//
//    }

    @Override
    public void onResume() {
        // if(mapView!=null)
        mapView.onResume();
        if(map==null)
            mapView.getMapAsync(this);
//        if(camListMarker==null){
//            addMapPointsCam();
//        }
        super.onResume();
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        mapView = (MapView) view.findViewById(R.id.mapview);
//        mapView.onCreate(savedInstanceState);
//        mapView.onResume();
//        mapView.getMapAsync(this);//when you already implement OnMapReadyCallback in your fragment
//    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();

//        autocompleteFragment = (PlaceAutocompleteFragment)  getActivity().getFragmentManager().findFragmentById(R.id.toDestinationAutoComplete);
//
//        if (autocompleteFragment != null)
//            getActivity().getFragmentManager().beginTransaction().remove(autocompleteFragment).commit();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onStart() {
        super.onStart();
        if( mGoogleApiClient != null)
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(500);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(1.290270, 103.851959), 12);


        map.animateCamera(cameraUpdate);

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
        }
            myLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (myLocation != null) {
                // showCurrentLocation(myLocation);
                initCamera(myLocation);
                // rangeCheck(myLocation);
                return;
            }
        /*
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if (location == null)
                            return;
                        myLocation = location;
                        initCamera(location);
                    }
                });
        */

    }



    @Override
    public void onInfoWindowClick(Marker marker) {
        //getInfoContents(marker);

            marker.hideInfoWindow();
        marker.showInfoWindow();


       // LTACameraObject cmp = (LTACameraObject) marker.getTag();

        Object markObject = (Object) marker.getTag();

      //  if (markObject != null && markObject instanceof LTACameraObject && trackCurrent == false) {

            if (markObject != null && markObject instanceof LTACameraObject) {
                LTACameraObject cmp = (LTACameraObject) markObject;

                cm =(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnected();

            if(isConnected) {


                new ImageDialogFragment().newInstance(cmp)
                        .show(((((AppCompatActivity) getContext())
                                .getFragmentManager())), "my-dialog");
            }
            else{
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();

            }
        } else if (markObject != null && markObject instanceof IncidentObject) {
            IncidentObject cmp = (IncidentObject) markObject;

            cm =(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnected();

            if(isConnected) {


                new ImageDialogFragment().newInstance(cmp)
                        .show(((((AppCompatActivity) getContext())
                                .getFragmentManager())), "my-dialog");

            }
            else{
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();

            }
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        userLocation = location;
        mCurrentLocation = location;
        /*
        if(userLocationMarker !=null){
            userLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        markerOptions.visible(false);
        userLocationMarker = map.addMarker(markerOptions);*/
        //userLocationMarker.showInfoWindow();
        if(trackCurrent != false) {

//            float bearing = map.getMyLocation().getBearing();
            if( navTilt[0]) {

                CameraPosition cameraPosition = new CameraPosition.Builder().
                        target(new LatLng(userLocation.getLatitude(), userLocation.getLongitude())).
                        tilt(80).
                        zoom(17).
                        bearing(userLocation.getBearing()).
                        build();

                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }else if(!navTilt[0]){
                CameraPosition cameraPosition = new CameraPosition.Builder().
                        target(new LatLng(userLocation.getLatitude(), userLocation.getLongitude())).
                        tilt(0).
                        zoom(17).
                        bearing(userLocation.getBearing()).
                        build();

                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            }

          //  map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 14));
        }
    //rangeCheckLatlon(location,false);
    }



    private void initCamera( Location location ) {
        CameraPosition position = CameraPosition.builder()
                .target( new LatLng( location.getLatitude(),
                        location.getLongitude() ) )
                .zoom( 12f )
                .bearing( 0.0f )
                .tilt( 10.0f )
                .build();

        map.animateCamera( CameraUpdateFactory
                .newCameraPosition( position ), null );

        map.setMapType( MAP_TYPES[curMapTypeIndex] );
        map.setTrafficEnabled( true );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }
        map.setMyLocationEnabled( true );
        map.getUiSettings().setZoomControlsEnabled( true );
    }




    @Override
    public void onConnectionSuspended(int i) {

    }


    private Address getAddressFromLatLng(LatLng latLng ) {
        Geocoder geocoder = new Geocoder( getActivity() );

        Address address = null;
        try {
            address = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get(0);
        } catch (IOException e ) {
        }

        return address;
    }

    Marker currentMarker;
    ConnectivityManager cm;
    LTACameraObject cmp;

    @Override
    public boolean onMarkerClick(Marker marker) {

        currentMarker = marker;
         marker.showInfoWindow();
        CameraUpdate yourLocation;

      //  Object cmpObj = marker;
         //cmp =  (LTACameraObject) marker.getTag();
        Object markObject = (Object) marker.getTag();
       // if (markObject instanceof LTACameraObject) {

            if (markObject != null && markObject instanceof LTACameraObject && trackCurrent == false) {
             cm =
                    (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnected();

            if(!isConnected) {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();

            }


                //  LTACameraObject cmp = (LTACameraObject) cmpObj;
            yourLocation = CameraUpdateFactory.newLatLng(marker.getPosition());
            map.animateCamera(yourLocation);

           // map.moveCamera(marker.getPosition());

            marker.showInfoWindow();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            marker.hideInfoWindow();

            marker.showInfoWindow();

           // cmp = null;


        }else if (markObject != null && markObject instanceof HDBCarParkObject && trackCurrent == false){
           // if(marker.getSnippet().equals("CarPark Location")) {
         /*       yourLocation = CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 16);
                map.animateCamera(yourLocation);
        */
                yourLocation = CameraUpdateFactory.newLatLng(marker.getPosition());
                map.animateCamera(yourLocation);

         //   }

        }


        return true;
    }


    @Override
    public void onMapClick(LatLng latLng) {
        if(currentMarker!=null) {
            currentMarker.hideInfoWindow();
        }
   /*     if(userAddedMarker!=null){
            userAddedMarker.remove();
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Placed Marker");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        userAddedMarker = map.addMarker(markerOptions);
  */  }




    private void getCurrentLocation() {
       // boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location = null;
        //if (!(isGPSEnabled || isNetworkEnabled))
          //  Snackbar.make(mapView, "GPS is not enabled", Snackbar.LENGTH_INDEFINITE).show();
        //else {

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        } else{

            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        //}
        if (location != null) {
            Log.d("getCurrentLocation", String.valueOf(location.getLatitude())+" "+
                    String.valueOf(location.getLongitude()));
            //drawMarker(location);
            rangeCheckLatlon(location);
            // rangeCheckLatlonCam(location);
        }
    }}


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.Theme_AppCompat_Dialog_Alert);
        builder.setMessage("Your GPS seems to be disabled, Only location features will be limited, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void drawMarker(Location location) {
        if (map!= null) {
            // map.clear();
            LatLng gps = new LatLng(location.getLatitude(), location.getLongitude());
            map.addMarker(new MarkerOptions()
                    .position(gps)
                    .title("Current Position"));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 12));
        }

    }


//    static int y = 3;
//    static long DELAYING = 100;
//
//    private List<Marker> carparkListMarker2 = new ArrayList<>();
//    List<MarkerOptions> markers = new ArrayList<MarkerOptions>();
//
//    Circle circle;
    public void rangeCheckLatlon(Location mCurrentLocation) {
        int y = 3;
       long DELAYING = 100;

       // final List<Marker> carparkListMarker2 = new ArrayList<>();
       // List<MarkerOptions> markers = new ArrayList<MarkerOptions>();

        Circle circle = null;

        if(circle != null){

            circle.remove();
        }

        if(carparkListMarker2 != null) {
            for (Marker marker : carparkListMarker2) {
                marker.remove();
            }
        }



        //List<HDBCarParkObject> cpList = HDBCarParkAPIDAOImplement.readCarparkObjectRawData(getContext());


        if (mCurrentLocation != null) {
            circle = map.addCircle(new CircleOptions()
                    .center(new LatLng(mCurrentLocation.getLatitude(),
                            mCurrentLocation.getLongitude()))
                    .radius(500)
                    .strokeColor(Color.rgb(0, 137, 123))
                    .fillColor(Color.argb(20,0, 137, 123)));
            Handler handler = new Handler(Looper.getMainLooper());



            LatLng currentforhdb = new LatLng(mCurrentLocation.getLatitude(),
                    mCurrentLocation.getLongitude());
            mMapAsyncTaskAllocator.AsyncTasking("asynchdbnearby",getContext(),currentforhdb);

//            for (final HDBCarParkObject cpItem : cpList) {
//                if (SphericalUtil.computeDistanceBetween(new LatLng(mCurrentLocation.getLatitude(),
//                        mCurrentLocation.getLongitude()), new LatLng(cpItem.getLatitude(),
//                        cpItem.getLongitude())) < 500) {
////                    Log.d("Comparing circle range ", (new LatLng(mCurrentLocation.getLatitude(),
////                            mCurrentLocation.getLongitude())) + " VS " + ( new LatLng(cpItem.getLatitude(),
////                            cpItem.getLongitude())));
////                    //  markerCP.setVisible(true);
//                    handler.postDelayed(
//                            new Runnable() {
//                                @Override
//                                public void run() {
//                                    Marker marker=map.addMarker(new MarkerOptions()
//
//                                  //  carparkListMarker2.add(map.addMarker(new MarkerOptions()
//                                            .position(new LatLng(cpItem.getLatitude(),
//                                                    cpItem.getLongitude()))
//                                            .title(cpItem.getAddress())
//                                            .snippet(getCurrentLot(cpItem))
//                                            .icon(BitmapDescriptorFactory.fromBitmap(
//                                                    BitmapFactory.decodeResource(res,cpIcon)))
//
//
//                                    );
//                                    marker.setTag(cpItem);
//
//                                    carparkListMarker2.add(marker);
//
//
//
//
//
//                                }
//                            }, (DELAYING * (long) y++));
//
//                }
//
//            }

            //TODO: Any custom actions


            if(!trackCurrent) {
                CameraPosition cameraPosition = new CameraPosition.Builder().
                        target(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude())).
                        tilt(80).
                        zoom(16).
                        bearing(mCurrentLocation.getBearing()).
                        build();

                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        // Code to execute when the animateCamera task has finished
                    }

                    @Override
                    public void onCancel() {
                        // Code to execute when the user has canceled the animateCamera task
                    }
                });
            }
           

        }

    }
    Circle circle = null;
    List<Marker> ListMarker2 = new ArrayList<>();


    public void rangeCheckLatlonCamera(Object origin, List<LTACameraObject> cpList) {
        int y = 3;
        long DELAYING = 100;

       // final List<Marker> ListMarker2 = new ArrayList<>();
        List<MarkerOptions> markers = new ArrayList<MarkerOptions>();


        if(circle != null){

            circle.remove();
        }

        for (Marker marker: CamMarkerList) {
            marker.remove();
        }


        //List<HDBCarParkObject> cpList = HDBCarParkAPIDAOImplement.readCarparkObjectRawData(getContext());

        if(origin instanceof IncidentObject) {
            IncidentObject center = (IncidentObject) origin;
        //    List<LTACameraObject> cpList2 = (List<LTACameraObject>) nearbylist;


            if (center != null) {
                circle = map.addCircle(new CircleOptions()
                        .center(new LatLng(center.getLatitude(),
                                center.getLongitude()))
                        .radius(1000)
                        .strokeColor(Color.rgb(0, 137, 123))
                        .fillColor(Color.argb(20, 0, 137, 123)));
                Handler handler = new Handler(Looper.getMainLooper());


                for (final LTACameraObject cpItem : cpList) {
                    if (SphericalUtil.computeDistanceBetween(new LatLng(center.getLatitude(),
                            center.getLongitude()), new LatLng(cpItem.getLat(),
                            cpItem.getLon())) < 1000) {
                        Log.d("Comparing circle range ", (new LatLng(center.getLatitude(),
                                center.getLongitude())) + " VS " + (new LatLng(cpItem.getLat(),
                                cpItem.getLon())));
                        //  markerCP.setVisible(true);
                        handler.postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        Marker marker = map.addMarker(new MarkerOptions()

                                                //  carparkListMarker2.add(map.addMarker(new MarkerOptions()
                                                .position(new LatLng(cpItem.getLat(),
                                                        cpItem.getLon()))
                                                .title(cpItem.getName())
                                                .snippet(cpItem.getCamera_id())
                                                .icon(BitmapDescriptorFactory.fromBitmap(
                                                        BitmapFactory.decodeResource(res, camIcon)))


                                        );
                                        marker.setTag(cpItem);

                                        CamMarkerList.add(marker);


                                    }
                                }, (DELAYING * (long) y++));

                    }

                }

                //TODO: Any custom actions


                if (!trackCurrent) {
                    CameraPosition cameraPosition = new CameraPosition.Builder().
                            target(new LatLng(center.getLatitude(), center.getLongitude())).
                            tilt(80).
                            zoom(15).
                           // bearing(center.getBearing()).
                            build();

                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            // Code to execute when the animateCamera task has finished
                        }

                        @Override
                        public void onCancel() {
                            // Code to execute when the user has canceled the animateCamera task
                        }
                    });
                }


            }
        }

    }

    /*Circle circle;
    public void rangeCheckLatlon(Location mCurrentLocation) {
        if(circle != null){

            circle.remove();
        }

        for (Marker marker: carparkListMarker2) {
            marker.remove();
        }



        List<HDBCarParkObject> cpList = HDBCarParkAPIDAOImplement.readCarparkObjectRawData(getContext());
        //make marker list first?

        if (mCurrentLocation != null) {
            circle = map.addCircle(new CircleOptions()
                    .center(new LatLng(mCurrentLocation.getLatitude(),
                            mCurrentLocation.getLongitude()))
                    .radius(500)
                    .strokeColor(Color.rgb(0, 137, 123))
                    .fillColor(Color.argb(20,0, 137, 123)));
            Handler handler = new Handler(Looper.getMainLooper());


            for (final HDBCarParkObject cpItem : cpList) {
                if (SphericalUtil.computeDistanceBetween(new LatLng(mCurrentLocation.getLatitude(),
                        mCurrentLocation.getLongitude()), new LatLng(cpItem.getLatitude(),
                        cpItem.getLongitude())) < 500) {
                    Log.d("Comparing circle range ", (new LatLng(mCurrentLocation.getLatitude(),
                            mCurrentLocation.getLongitude())) + " VS " + ( new LatLng(cpItem.getLatitude(),
                            cpItem.getLongitude())));
                    //  markerCP.setVisible(true);
                    handler.postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {

                                    carparkListMarker2.add(map.addMarker(new MarkerOptions()
                                            .position(new LatLng(cpItem.getLatitude(),
                                                    cpItem.getLongitude()))
                                            .title(cpItem.getAddress()+ "  -  " + getCurrentLot(cpItem))
                                            .snippet("CarPark Location")
                                            .icon(BitmapDescriptorFactory.fromBitmap(
                                                    BitmapFactory.decodeResource(res,cpIcon)))



                                    ));




                                }
                            }, (DELAYING * (long) y++));

                }

            }

            //TODO: Any custom actions


            if(!trackCurrent) {
                CameraPosition cameraPosition = new CameraPosition.Builder().
                        target(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude())).
                        tilt(80).
                        zoom(16).
                        bearing(mCurrentLocation.getBearing()).
                        build();

                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        // Code to execute when the animateCamera task has finished
                    }

                    @Override
                    public void onCancel() {
                        // Code to execute when the user has canceled the animateCamera task
                    }
                });
            }

                  *//*  CameraPosition cameraPosition = new CameraPosition.Builder().
                            target(new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude())).
                            tilt(80).
                            zoom(16).
                            bearing(mCurrentLocation.getBearing()).
                            build();

                    //    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition)); }
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), cancelableCallback);

*//*


           *//* CameraPosition cameraPosition = new CameraPosition.Builder().
                    target(new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude())).
                    tilt(80).
                    zoom(16).
                    bearing(mCurrentLocation.getBearing()).
                    build();

            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
         *//* //  map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude()), 16));

            //  initCamera( mCurrentLocation );
        }

    }*/

    private List<Marker> carparkListMarkerTrack = new ArrayList<>();



    Circle circleTrack;
/*

    public void rangeCheckLatlon(Location mCurrentLocation,boolean track) {

        if(track == false) {
            if (circleTrack != null) {

                circleTrack.remove();

            }


            for (Marker marker : carparkListMarkerTrack) {
                marker.remove();
            }


            List<HDBCarParkObject> cpList = HDBCarParkAPIDAOImplement.readCarparkObjectRawData(getContext());
            if (mCurrentLocation != null) {
                circleTrack = map.addCircle(new CircleOptions()
                        .center(new LatLng(mCurrentLocation.getLatitude(),
                                mCurrentLocation.getLongitude()))
                        .radius(400)
                        .strokeColor(Color.rgb(0, 137, 123))
                        .fillColor(Color.argb(20, 0, 137, 123)));
                Handler handler = new Handler(Looper.getMainLooper());


                for (final HDBCarParkObject cpItem : cpList) {
                    if (SphericalUtil.computeDistanceBetween(new LatLng(mCurrentLocation.getLatitude(),
                            mCurrentLocation.getLongitude()), new LatLng(cpItem.getLatitude(),
                            cpItem.getLongitude())) < 400) {
                        Log.d("Comparing circle range ", (new LatLng(mCurrentLocation.getLatitude(),
                                mCurrentLocation.getLongitude())) + " VS " + (new LatLng(cpItem.getLatitude(),
                                cpItem.getLongitude())));

                        //  markerCP.setVisible(true);
                        handler.postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {

                                        carparkListMarkerTrack.add(map.addMarker(new MarkerOptions()
                                                .position(new LatLng(cpItem.getLatitude(),
                                                        cpItem.getLongitude()))
                                                .title(cpItem.getAddress() + "  -  " + getCurrentLot(cpItem))
                                                .snippet("CarPark Location")
                                            */
/*    .icon(BitmapDescriptorFactory.fromBitmap(
                                                        BitmapFactory.decodeResource(getResources(),
                                                                R.drawable.parkingline)))));
*//*


                                    }
                                }, (DELAYING * (long) y++));

                    }

                }
              //  map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 16));

                //  initCamera( mCurrentLocation );
            }
        }
    }
*/
@Override
public void onDestroyView() {
    super.onDestroyView();
//            MapFragment f = (MapFragment) getFragmentManager()
//                    .findFragmentById(R.id.mymap);
    autocompleteFragment = (PlaceAutocompleteFragment)  getActivity().getFragmentManager().findFragmentById(R.id.toDestinationAutoComplete);

    if (autocompleteFragment != null)
        getActivity().getFragmentManager().beginTransaction().remove(autocompleteFragment).commit();
}



    public static String getCurrentLot(HDBCarParkObject cpitem){
        List<HDBCarParkObject> withLots = CarParkSingletonPattern.getInstance().getArrayLotList();
     //   List<HDBCarParkObject> withLots = CarParkSingletonPattern.getNewInstance().getArrayLotList();

        while(withLots == null){
            withLots = CarParkSingletonPattern.getNewInstance().getArrayLotList();
        }
        for(int o = 0; o<withLots.size();o++){
          //  Log.d("Comparing CarPark No for Lots ", withLots.get(o).getCarParkNo() + " + " + cpitem.getCarParkNo());

            if(withLots.get(o).getCarParkNo().equals(cpitem.getCarParkNo())){
                return (withLots.get(o).getLotsAvailable());
            }
        }
        return "N/A";
    }



}