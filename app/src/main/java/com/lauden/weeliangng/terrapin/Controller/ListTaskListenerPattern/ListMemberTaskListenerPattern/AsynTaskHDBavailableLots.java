package com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern.ListMemberTaskListenerPattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.lauden.weeliangng.terrapin.Controller.CarParkRecyclerView.CarParkObjectViewHolder;
import com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern.ListAsyncTaskListener;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleCameraObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.MallObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.UsageOfCameraDAO.CameraDAOUsage;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CarParkDAOpattern.HDBCarParkAPIDAOImplement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeeLiang Ng on 8/3/2018.
 */

public class AsynTaskHDBavailableLots extends AsyncTask<Void,Void,String> {

    ListMemberAsyncTaskListener listener;
    Activity mActivity;
    DataSnapshot dataSnapshot;
    ValueEventListener mValueEventListener;
    Context mContext;
    List<MallObject> mMallObjectList = new ArrayList<>();
    List<Marker> CamMarkerList = new ArrayList<>();
    HDBCarParkObject mHDBCarParkObject;

    public AsynTaskHDBavailableLots(ListMemberAsyncTaskListener listener,  HDBCarParkObject hdbCarParkObject,Context context) {
this.listener = listener;
this.mContext= context;
this.mHDBCarParkObject = hdbCarParkObject;
    }


    @Override
    protected String doInBackground(Void... voids) {

       // List<TitleCameraObject> TitleCameraObject = CameraDAOUsage.CameraDAOgetTitleObjectList(mActivity);
        String tobeset = HDBCarParkAPIDAOImplement.getCurrentLotStaticUpdate(mHDBCarParkObject, mContext);

        return tobeset;
    }

    ProgressDialog progressDialogMall;

    @Override
    protected void onPostExecute(String CamObjects) {
        listener.onListMemberAsyncTaskStartComplete(CamObjects);

           // progressDialogMall.dismiss();


    }

    @Override
    protected void onPreExecute() {
      listener.onListMemberAsyncTaskStart();

//        progressDialogMall = ProgressDialog.show(getContext(), "Please wait.",
//                "Locating Malls...", true);

    }
}
