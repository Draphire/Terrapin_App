package com.lauden.weeliangng.terrapin.Model.IncidentFactoryPattern;

/**
 * Created by WeeLiang Ng on 4/3/2018.
 */

public class IncidentObject {
    Double Latitude;
    Double Longitude;
    String Message;
    String Type;
    String Key;


    public IncidentObject() {

    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public IncidentObject(String key, String type, String message,  Double latitude, Double longitude) {
        Latitude = latitude;
        Longitude = longitude;
        Message = message;
        Type = type;
        Key = key;
    }

    public IncidentObject(Double longitude, Double latitude, String type, String message) {
        Latitude = latitude;
        Longitude = longitude;
        Message = message;

        Type = type;
    }

    public IncidentObject(Double longitude, Double latitude, String message) {
        Latitude = latitude;
        Longitude = longitude;
        Message = message;

    }





    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getType() {

        return Type;
    }

    public void setType(String type) {
        Type = type;
    }







}
