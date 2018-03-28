package com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.lauden.weeliangng.terrapin.Controller.MapTaskListenerPattern.AsynTaskCamera;
import com.lauden.weeliangng.terrapin.Controller.MapTaskListenerPattern.AsynTaskHDBnearby;
import com.lauden.weeliangng.terrapin.Controller.MapTaskListenerPattern.AsynTaskIncident;
import com.lauden.weeliangng.terrapin.Controller.MapTaskListenerPattern.AsynTaskMall;
import com.lauden.weeliangng.terrapin.Controller.MapTaskListenerPattern.MapAsyncTaskListener;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleCameraObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.CarParkFactory;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.CarParkStrategy;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.MallObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.UsageOfCameraDAO.CameraDAOUsage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeeLiang Ng on 8/3/2018.
 */

public class ListAsyncTaskAllocator {

   // Context mContext;
    private ListAsyncTaskListener listener;
    DataSnapshot dataSnapshot;
    ValueEventListener mValueEventListener;
    Context mContext;
    List<MallObject> mMallObjectList = new ArrayList<>();
    LatLng mLatLng;

    public ListAsyncTaskAllocator(){

    }


    public void AsyncTasking(String s, Context context){



    }


    public void AsyncTasking(String s, Activity activity){

        if(s.equals("getTitleListAndUpdate")){

            AsynTaskCameraRefreshList refreshcam = new AsynTaskCameraRefreshList(listener,activity);
            refreshcam.execute();


        }



    }

    public void AsyncTasking(String s, Context context, LatLng latLng) {


    }


    public ListAsyncTaskAllocator(Context context , DataSnapshot dataSnapshotin){
        this.mContext = context;
        this.dataSnapshot = dataSnapshotin;
        // this.listener = listenerin;


    }

    public ListAsyncTaskAllocator(ListAsyncTaskListener listenerin, DataSnapshot dataSnapshotin){
        this.dataSnapshot = dataSnapshotin;
        this.listener = listenerin;

    }

    public ListAsyncTaskAllocator(ValueEventListener valueEventListener, DataSnapshot dataSnapshotin){
        this.dataSnapshot = dataSnapshotin;
        this.mValueEventListener = valueEventListener;

    }

    public ListAsyncTaskAllocator(DataSnapshot dataSnapshotin){
        this.dataSnapshot = dataSnapshotin;
       // this.listener = listenerin;

    }

    public ListAsyncTaskListener getListener() {
        return listener;
    }

    public synchronized void setListener(ListAsyncTaskListener listener) {
        this.listener = listener;
    }

    public void setDataSnapshot(DataSnapshot dataSnapshot){
        this.dataSnapshot = dataSnapshot;
    }


}
