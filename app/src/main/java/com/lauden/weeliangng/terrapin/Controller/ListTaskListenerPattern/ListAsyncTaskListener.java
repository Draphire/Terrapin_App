package com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern;

import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleCameraObject;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.MallObject;
import com.lauden.weeliangng.terrapin.Model.IncidentFactoryPattern.IncidentObject;

import java.util.List;

/**
 * Created by WeeLiang Ng on 8/3/2018.
 */

public interface ListAsyncTaskListener {
    void onListAsyncTaskStart();

    void CameraObjectList(List<TitleCameraObject> camLt);

}
