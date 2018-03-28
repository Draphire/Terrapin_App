package com.lauden.weeliangng.terrapin.Controller.MapTaskListenerPattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.SphericalUtil;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CarParkDAOpattern.HDBCarParkAPIDAOImplement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeeLiang Ng on 8/3/2018.
 */

public class AsynTaskHDBnearby extends AsyncTask<Void,Void,List<HDBCarParkObject>> {

    MapAsyncTaskListener listener;
    Activity mActivity;
    DataSnapshot dataSnapshot;
    ValueEventListener mValueEventListener;
    Context mContext;
     List<HDBCarParkObject> mHDBCarParkObjectList = new ArrayList<>();
    LatLng mLatLng;

    public AsynTaskHDBnearby(MapAsyncTaskListener listener, Activity activity) {
this.listener = listener;
this.mActivity = activity;
    }

    public AsynTaskHDBnearby(MapAsyncTaskListener listener, Context context, LatLng latLng) {
        this.listener = listener;
        this.mContext = context;
        this.mLatLng = latLng;
    }



    @Override
    protected List<HDBCarParkObject> doInBackground(Void... voids) {

        List<HDBCarParkObject> cpList = HDBCarParkAPIDAOImplement.readCarparkObjectRawData(mContext);



            for ( HDBCarParkObject cpItem : cpList) {
                if (SphericalUtil.computeDistanceBetween(mLatLng, new LatLng(cpItem.getLatitude(),
                        cpItem.getLongitude())) < 500) {
                    mHDBCarParkObjectList.add(cpItem);
                }
            }
        return mHDBCarParkObjectList;
    }

    ProgressDialog progressDialogMall;

    @Override
    protected void onPostExecute(List<HDBCarParkObject> HDBObjects) {
        listener.HDBnearbyMarkPlace(HDBObjects);
           // progressDialogMall.dismiss();


    }

    @Override
    protected void onPreExecute() {
        listener.onMapAsyncTaskStart();
//        progressDialogMall = ProgressDialog.show(getContext(), "Please wait.",
//                "Locating Malls...", true);

    }
}
