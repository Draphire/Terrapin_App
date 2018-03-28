package com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CarParkDAOpattern;

import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;

import java.util.List;

public interface CarParkDAO {

    public List<HDBCarParkObject> getAllCarParks();
    public HDBCarParkObject getCarParkObject(int id) ;
    public void UpdateCarParkObject(HDBCarParkObject cmObj);
    public void UpdateCarParkObjectList();
    public void DeleteCarParkObject(HDBCarParkObject cmObj);
    public void DeleteCarParkObjectList();



}