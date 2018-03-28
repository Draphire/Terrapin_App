package com.lauden.weeliangng.terrapin.Model.SingletonPattern;

import android.content.Context;

import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CarParkDAOpattern.HDBCarParkAPIDAOImplement;

import java.util.List;

/**
 * Created by WeeLiang Ng on 6/11/2017.
 */
public class CarParkSingletonPattern {

    private List<HDBCarParkObject> cpList;

    private List<HDBCarParkObject> cplotList;

    private static CarParkSingletonPattern instance;

    private CarParkSingletonPattern(){
        cplotList = HDBCarParkAPIDAOImplement.getCarParkList();
    }

    public static CarParkSingletonPattern getInstance(){
        while (instance == null){
            instance = new CarParkSingletonPattern();
        }
        return instance;
    }

    public static CarParkSingletonPattern getNewInstance(){
            instance = new CarParkSingletonPattern();

        return instance;
    }


    private CarParkSingletonPattern(Context context){
        cpList = HDBCarParkAPIDAOImplement.readCarparkObjectRawData(context);
    }

    public static CarParkSingletonPattern getInstance(Context context){
        while (instance == null){
            instance = new CarParkSingletonPattern(context);
        }
        return instance;
    }

    public static CarParkSingletonPattern getNewInstance(Context context){
        instance = new CarParkSingletonPattern(context);

        return instance;
    }


    public List<HDBCarParkObject> getArrayList() {
        return cpList;
    }

    public List<HDBCarParkObject> getArrayLotList() {
        return cplotList;
    }

}