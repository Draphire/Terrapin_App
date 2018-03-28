package com.lauden.weeliangng.terrapin.Model.SingletonPattern;

import android.app.Activity;

import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.CameraDAOImplement;

import java.util.List;

/**
 * Created by WeeLiang Ng on 6/11/2017.
 */

public class CameraSingleton {


    /**
     * Created by WeeLiang Ng on 6/11/2017.
     */

    Activity mActivity;
        private List<LTACameraObject> cpList;

        private static CameraSingleton instance;
        private CameraSingleton(){
            cpList = CameraDAOImplement.getCameraObjectList();
        }

    private CameraSingleton(Activity activity){
            this.mActivity = activity;
        cpList = CameraDAOImplement.getCameraObjectList(activity);
    }

        public static CameraSingleton getInstance(){
            if (instance == null){
                instance = new CameraSingleton();
            }
            return instance;
        }


    public static CameraSingleton getInstance(Activity act){
        if (instance == null){
            instance = new CameraSingleton(act);
        }
        return instance;

    }

    public static CameraSingleton getNewInstance(){


            instance = new CameraSingleton();

            if(instance == null){
                instance = new CameraSingleton();
            }
            return instance;
        }

    public static CameraSingleton getNewInstance(Activity mact){


        instance = new CameraSingleton(mact);

        if(instance == null){
            instance = new CameraSingleton(mact);
        }
        return instance;
    }


    public List<LTACameraObject> getArrayList() {
            return cpList;
        }


}
