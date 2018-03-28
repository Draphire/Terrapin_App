package com.lauden.weeliangng.terrapin.Model.MapUIUtlity;

import android.app.Activity;
import android.content.Context;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

/**
 * Created by WeeLiang Ng on 6/11/2017.
 */

public class ExtractAddress {


    public static String getAddressFromLatLng(LatLng latLng, Activity act) {
        Geocoder geocoder = new Geocoder( act );


        String address = "";
        try {

            if (geocoder!=null)
            address = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get( 0 ).getAddressLine( 0 );
            Log.d("Attempt get Address", address);

        } catch (IOException e ) {

        }

        return address;
    }
    public static String getAddressFromLatLng(LatLng latLng, Context context) {
        Geocoder geocoder = new Geocoder( context );


        String address = "";
        try {

            if (geocoder!=null)
                address = geocoder
                        .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                        .get( 0 ).getAddressLine( 0 );
            Log.d("Attempt get Address", address);

        } catch (IOException e ) {

        }

        return address;
    }
}
