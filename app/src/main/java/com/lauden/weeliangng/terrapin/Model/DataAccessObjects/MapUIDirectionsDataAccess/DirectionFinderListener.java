package com.lauden.weeliangng.terrapin.Model.DataAccessObjects.MapUIDirectionsDataAccess;

import com.lauden.weeliangng.terrapin.Model.MapElementObjects.Route;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.List;

/**
 * Created by Jun Wei on 7/11/2017.
 */

public interface DirectionFinderListener extends PlaceSelectionListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> routes);
}
