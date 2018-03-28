package com.lauden.weeliangng.terrapin.Controller.MapTaskListenerPattern;

import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.MallObject;
import com.lauden.weeliangng.terrapin.Model.IncidentFactoryPattern.IncidentObject;

import java.util.List;

/**
 * Created by WeeLiang Ng on 8/3/2018.
 */

public interface MapAsyncTaskListener {
    void onMapAsyncTaskStart();

    void IncidentMarkPlace(List<IncidentObject> mallLt);
    void MallMarkPlace(List<MallObject> mallLt);
   void HDBnearbyMarkPlace(List<HDBCarParkObject> hdblm);
    void CamMarkPlace(List<LTACameraObject> mallLt);
}
