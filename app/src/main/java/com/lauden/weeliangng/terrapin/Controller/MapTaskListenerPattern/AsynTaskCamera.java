package com.lauden.weeliangng.terrapin.Controller.MapTaskListenerPattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.MallObject;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CameraSingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeeLiang Ng on 8/3/2018.
 */

public class AsynTaskCamera extends AsyncTask<Void,Void,List<LTACameraObject>> {

    MapAsyncTaskListener listener;
    Activity mActivity;


    public AsynTaskCamera(MapAsyncTaskListener listener, Activity activity) {
this.listener = listener;
this.mActivity = activity;
    }


    @Override
    protected List<LTACameraObject> doInBackground(Void... voids) {

        Marker marker = null;
        // List<LTACameraObject> listUp = CameraDAOImplement.getCameraObjectList(getActivity());
        int retryCount = 0;

        List<LTACameraObject> listUp = new ArrayList<>();

//            if(firsttry > 0) {
//                listUp = CameraSingleton.getInstance().getArrayList();
//            firsttry = 0;
//            }else{
//                listUp = CameraSingleton.getNewInstance(getActivity()).getArrayList();
//
//            }
       // listUp = CameraSingleton.getInstance().getArrayList();


       // while(listUp == null){
            listUp = CameraSingleton.getNewInstance(mActivity).getArrayList();
            retryCount++;
            Log.d("Retry get CameraList ", String.valueOf(retryCount));

        //}
        // if(listUp.get(0).getTimestamp())
        // LTACameraObject camObj = listUp.get(0);

//        for (LTACameraObject camObj : listUp) {
//            //map.addMarker(new MarkerOptions().position(new LatLng(camObj.getLat(), camObj.getLon())));
//            marker = map.addMarker(new MarkerOptions()
//                    .position((new LatLng(camObj.getLat(), camObj.getLon())))
//
//                    .icon(BitmapDescriptorFactory.fromBitmap(
//                            BitmapFactory.decodeResource(res, R.drawable.carmark6))));
//            marker.setTag(camObj);
//
//            CamMarkerList.add(marker);
//
//        }
        return listUp;
    }

    ProgressDialog progressDialogMall;

    @Override
    protected void onPostExecute(List<LTACameraObject> CamObjects) {
        listener.CamMarkPlace(CamObjects);
           // progressDialogMall.dismiss();


    }

    @Override
    protected void onPreExecute() {
        listener.onMapAsyncTaskStart();
//        progressDialogMall = ProgressDialog.show(getContext(), "Please wait.",
//                "Locating Malls...", true);

    }
}
