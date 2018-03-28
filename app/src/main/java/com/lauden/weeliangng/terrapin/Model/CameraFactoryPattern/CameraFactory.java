package com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern;

public class CameraFactory {
public CameraStrategy getCameraType(String cameraType){



    if(cameraType.equals("LTA")){
        return new LTACameraObject();
    }else{
        return null;
    }

}

}