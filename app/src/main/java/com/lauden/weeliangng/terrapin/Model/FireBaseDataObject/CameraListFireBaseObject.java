package com.lauden.weeliangng.terrapin.Model.FireBaseDataObject;

/**
 * Created by WeeLiang Ng on 11/3/2018.
 */

public class CameraListFireBaseObject {
    private String camerakey;
    private String cameraid;
    private String cameraname;

    public CameraListFireBaseObject(){

    }

    public CameraListFireBaseObject(String cameraid, String cameraname) {
        this.cameraid = cameraid;
        this.cameraname = cameraname;
    }


    public String getCamerakey() {
        return camerakey;
    }

    public void setCamerakey(String camerakey) {
        this.camerakey = camerakey;
    }

    public String getCameraid() {
        return cameraid;
    }

    public void setCameraid(String cameraid) {
        this.cameraid = cameraid;
    }

    public String getCameraname() {
        return cameraname;
    }

    public void setCameraname(String cameraname) {
        this.cameraname = cameraname;
    }
}
