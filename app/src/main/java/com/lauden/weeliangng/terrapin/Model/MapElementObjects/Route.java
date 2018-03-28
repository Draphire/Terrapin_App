package com.lauden.weeliangng.terrapin.Model.MapElementObjects;


import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Jun Wei on 7/11/2017.
 */

public class Route {
    private Distance distance;
    private Duration duration;
    private String startAddress;
    private String endAddress;
    private LatLng startLocation;
    private LatLng endLocation;
    private List<LatLng> points;

    public Route() {

    }

    public Route(Distance distance, Duration duration, String startAddress, String endAddress, LatLng startLocation, LatLng endLocation, List<LatLng> points) {
        this.distance = distance;
        this.duration = duration;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.points = points;
    }
    public List<LatLng> getPoints(){
        return points;
    }

    public Distance getDistance(){
        return distance;
    }

    public Duration getDuration(){
        return duration;
    }
}
