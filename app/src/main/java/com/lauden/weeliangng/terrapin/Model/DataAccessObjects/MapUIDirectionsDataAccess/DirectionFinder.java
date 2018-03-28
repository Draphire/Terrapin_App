package com.lauden.weeliangng.terrapin.Model.DataAccessObjects.MapUIDirectionsDataAccess;

import android.os.AsyncTask;

import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.JSONParserUtility.DownloadData;
import com.lauden.weeliangng.terrapin.Model.MapElementObjects.Distance;
import com.lauden.weeliangng.terrapin.Model.MapElementObjects.Duration;
import com.lauden.weeliangng.terrapin.Model.MapElementObjects.Route;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jun Wei on 4/11/2017.
 */

public class DirectionFinder extends AsyncTask<Void, Void, String> {
    private static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?";
   // private static final String GOOGLE_API_KEY = "AIzaSyBtjpcrGgRAs_-FfmdT1qddCzRgPOJmduw";
   private static final String GOOGLE_API_KEY = "AIzaSyDT6bKACiuNRlDN9OfoCBqKzGBpx3Xp79Q";

    private DirectionFinderListener listener;
    private double originLat, originLng;
    private double destinationLat, destinationLng;

    public DirectionFinder(DirectionFinderListener listener, double originLat, double originLng, double destinationLat, double destinationLng) {
        this.listener = listener;
        this.originLat = originLat;
        this.originLng = originLng;
        this.destinationLat = destinationLat;
        this.destinationLng = destinationLng;
    }


    @Override
    protected String doInBackground(Void... voids) {
        String url = null;
        String data = null;

        url = createUrl();
        DownloadData downloader = new DownloadData();
        data = downloader.readUrl(url);

        return data;
    }

    @Override
    protected void onPreExecute() {
        listener.onDirectionFinderStart();
    }

    private String createUrl() {
        return DIRECTION_URL_API + "origin=" + originLat + "," + originLng
                                 + "&destination=" + destinationLat + "," + destinationLng
                                 + "&key=" + GOOGLE_API_KEY;
    }

    @Override
    protected void onPostExecute(String s) {
        List<Route> routes = null;
        try {
            routes = parseDirections(s);
            listener.onDirectionFinderSuccess(routes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Route> parseDirections(String data) throws JSONException {
        if (data == null)
            return null;

        List<Route> routes = new ArrayList<Route>();
        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonRoutes = jsonData.getJSONArray("routes");
        for (int i = 0; i < jsonRoutes.length(); i++) {
            JSONObject jsonRoute = jsonRoutes.getJSONObject(i);

            JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
            JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
            JSONObject jsonLeg = jsonLegs.getJSONObject(0);
            JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
            JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
            JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
            JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");

            Distance distance = new Distance(jsonDistance.getString("text"), jsonDistance.getInt("value"));
            Duration duration = new Duration(jsonDuration.getString("text"), jsonDuration.getInt("value"));
            String startAddress = jsonLeg.getString("start_address");
            String endAddress = jsonLeg.getString("end_address");
            LatLng startLocation = new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
            LatLng endLocation = new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"));
            List<LatLng> points = PolyUtil.decode(overview_polylineJson.getString("points"));

            Route route = new Route(distance, duration, startAddress, endAddress, startLocation, endLocation, points);
            routes.add(route);
        }
        return routes;
    }

}
