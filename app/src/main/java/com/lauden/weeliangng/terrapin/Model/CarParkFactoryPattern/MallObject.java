package com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern;

import android.location.Location;

/**
 * Created by WeeLiang Ng on 5/3/2018.
 */

public class MallObject extends CarParkStrategy{

   private String Agency;

   private String Area;
          //  "Marina"
   private int AvailableLots;
            //2791
   private String CarParkID;
            //"1"
    private String Development;
            //"Suntec City"
    private String Location;
            //"1.29375 103.85718"
   private String  LotType;
            //"C"
    private Double Latitude;
    private Double Longitude;


    public MallObject() {

    }

    public MallObject(String agency, String area, int availableLots, String carParkID, String development, String location, String lotType) {
        Agency = agency;
        Area = area;
        AvailableLots = availableLots;
        CarParkID = carParkID;
        Development = development;
        Location = location;
        LotType = lotType;
    }


    public Double getLat() {
        return Latitude;
    }

    public void setLat(Double latitude) {
        Latitude = latitude;
    }


    public Double getLong() {
        return Longitude;
    }

    public void setLong(Double longitude) {
        Longitude = longitude;
    }

    public String getAgency() {
        return Agency;
    }

    public void setAgency(String agency) {
        Agency = agency;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public int getAvailableLots() {
        return AvailableLots;
    }

    public void setAvailableLots(int availableLots) {
        AvailableLots = availableLots;
    }

    public String getCarParkID() {
        return CarParkID;
    }

    public void setCarParkID(String carParkID) {
        CarParkID = carParkID;
    }

    public String getDevelopment() {
        return Development;
    }

    public void setDevelopment(String development) {
        Development = development;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    @Override
    public String getLotType() {
        return LotType;
    }

    @Override
    public void setLotType(String lotType) {
        LotType = lotType;
    }
}
