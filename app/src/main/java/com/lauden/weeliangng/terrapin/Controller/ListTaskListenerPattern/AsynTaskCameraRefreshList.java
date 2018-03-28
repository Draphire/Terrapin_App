package com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.lauden.weeliangng.terrapin.Controller.MapTaskListenerPattern.MapAsyncTaskListener;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleCameraObject;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.MallObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.UsageOfCameraDAO.CameraDAOUsage;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CameraSingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeeLiang Ng on 8/3/2018.
 */

public class AsynTaskCameraRefreshList extends AsyncTask<Void,Void,List<TitleCameraObject>> {

    ListAsyncTaskListener listener;
    Activity mActivity;

    public AsynTaskCameraRefreshList(ListAsyncTaskListener listener, Activity activity) {
this.listener = listener;
this.mActivity = activity;
    }


    @Override
    protected List<TitleCameraObject> doInBackground(Void... voids) {

        List<TitleCameraObject> TitleCameraObject = CameraDAOUsage.CameraDAOgetTitleObjectList(mActivity);


        return TitleCameraObject;
    }

    ProgressDialog progressDialogMall;

    @Override
    protected void onPostExecute(List<TitleCameraObject> CamObjects) {
        listener.CameraObjectList(CamObjects);
           // progressDialogMall.dismiss();


    }

    @Override
    protected void onPreExecute() {
      listener.onListAsyncTaskStart();
//        progressDialogMall = ProgressDialog.show(getContext(), "Please wait.",
//                "Locating Malls...", true);

    }
}
