package com.lauden.weeliangng.terrapin.Model.SingletonPattern;

import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;

import java.util.List;

/**
 * Created by WeeLiang Ng on 6/11/2017.
 */

public class CamSingletonImplement {


    public static String getCurrentLotstaticForIMG(LTACameraObject cpitem){

        List<LTACameraObject> withLots = CameraSingleton.getInstance().getArrayList();
        for(int o = 0; o<withLots.size();o++){
           // Log.d("Comparing CarPark No for Lots ", withLots.get(o).getCamera_id() + " + " + cpitem.getCarParkNo());

            if(withLots.get(o).getCamera_id().equals(cpitem.getCamera_id())){
                return (withLots.get(o).getImage());
            }
        }
        return cpitem.getImage();
    }


    public static String getCurrentLotstaticUpdateForIMG(LTACameraObject cpitem){

        List<LTACameraObject> withLots = CameraSingleton.getNewInstance().getArrayList();
        for(int o = 0; o<withLots.size();o++){
         //   Log.d("Comparing CarPark No for Lots ", withLots.get(o).getCamera_id() + " + " + cpitem.getCarParkNo());

            if(withLots.get(o).getCamera_id().equals(cpitem.getCamera_id())){
                return (withLots.get(o).getImage());
            }
        }
        return cpitem.getImage();
    }
}
