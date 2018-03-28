package com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern.ListMemberTaskListenerPattern;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern.AsynTaskCameraRefreshList;
import com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern.ListAsyncTaskListener;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.MallObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeeLiang Ng on 8/3/2018.
 */

public class ListMemberAsyncTaskAllocator {

   // Context mContext;
    private ListMemberAsyncTaskListener listener;
    DataSnapshot dataSnapshot;
    ValueEventListener mValueEventListener;
    Context mContext;
    List<MallObject> mMallObjectList = new ArrayList<>();
    LatLng mLatLng;

    public ListMemberAsyncTaskAllocator(){

    }


    public void AsyncTasking(String s, Context context){



    }

    public void AsyncTasking(String s, HDBCarParkObject hdbCarParkObject, Context context){
    if(s.equals("getLotAvailable")){

        AsynTaskHDBavailableLots refreshcam = new AsynTaskHDBavailableLots(listener,hdbCarParkObject,context);
        refreshcam.execute();


    }



    }



    public void AsyncTasking(String s, Context context, LatLng latLng) {


    }


    public ListMemberAsyncTaskAllocator(Context context , DataSnapshot dataSnapshotin){
        this.mContext = context;
        this.dataSnapshot = dataSnapshotin;
        // this.listener = listenerin;


    }


    public ListMemberAsyncTaskAllocator(DataSnapshot dataSnapshotin){
        this.dataSnapshot = dataSnapshotin;
       // this.listener = listenerin;

    }

    public ListMemberAsyncTaskListener getListener() {
        return listener;
    }

    public synchronized void setListener(ListMemberAsyncTaskListener listener) {
        this.listener = listener;
    }

    public void setDataSnapshot(DataSnapshot dataSnapshot){
        this.dataSnapshot = dataSnapshot;
    }


}
