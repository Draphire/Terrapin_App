package com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern;

import android.app.Activity;

import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;

import java.util.List;

public interface CameraDAO {
    public List<LTACameraObject> getAllCameras();
    public List<LTACameraObject> getAllCameras(Activity act);
    public LTACameraObject getCameraObject(int id) ;
    public void UpdateCameraObject(LTACameraObject cmObj);
    public void UpdateCameraObjectList();
    public void DeleteCameraObject(LTACameraObject cmObj);
    public void DeleteCameraObjectList();

}