package com.lauden.weeliangng.terrapin.Model.MapUIUtlity;

import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by WeeLiang Ng on 4/11/2017.
 */
public class InfoWindowRefresher implements com.squareup.picasso.Callback{
    private Marker markerToRefresh;
    String uri;
    ImageView iView;

    public InfoWindowRefresher(Marker markerToRefresh) {
        this.markerToRefresh = markerToRefresh;
        if (markerToRefresh.isInfoWindowShown()) {
            markerToRefresh.hideInfoWindow();

            markerToRefresh.showInfoWindow();

        }

        }
    public InfoWindowRefresher(Marker markerToRefresh, String URL, ImageView iview) {
        this.markerToRefresh = markerToRefresh;
        this.uri = URL;
        this.iView = iview;

    }

    @Override
    public void onSuccess() {
       // markerToRefresh.showInfoWindow();
        Log.d("CallBack Picasso ","On success executed");
        if (markerToRefresh != null && markerToRefresh.isInfoWindowShown()) {
            markerToRefresh.hideInfoWindow();

            markerToRefresh.showInfoWindow();
           // markerToRefresh.hideInfoWindow();
            //markerToRefresh.showInfoWindow();

        }
      //  markerToRefresh.hideInfoWindow();
        //markerToRefresh.showInfoWindow();
    }


    @Override
    public void onError() {
        Log.e(getClass().getSimpleName(), "Error loading thumbnail!");

    }
}
