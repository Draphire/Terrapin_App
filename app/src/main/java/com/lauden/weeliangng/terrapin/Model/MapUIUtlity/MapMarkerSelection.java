package com.lauden.weeliangng.terrapin.Model.MapUIUtlity;

import com.lauden.weeliangng.terrapin.R;

/**
 * Created by WeeLiang Ng on 5/3/2018.
 */

public class MapMarkerSelection {
    public static int IncidentChoice(String type){

        switch(type) {
            case "Roadwork" :
                // Statements
                return R.drawable.roadconstruction;


            case "Accident" :
                // Statements
                return R.drawable.accident;

            case "Heavy Traffic" :
                // Statements
                return R.drawable.heavytraffic;

            case "Weather" :
                // Statements
                return R.drawable.heavyweather;

            case "Vehicle breakdown" :
                // Statements
                return R.drawable.breakdownmark;

            case "Diversion" :
                // Statements
                return R.drawable.diversionmark;

            case "Unattended Vehicle" :
                // Statements
                return R.drawable.abandonedvehicle;
            case "Obstacle" :
                // Statements
                return R.drawable.obstruction;
            case "SOS" :
                // Statements
                return R.drawable.sosicon;
            case "Road Block" :
                // Statements
                return R.drawable.obstruction;



            // You can have any number of case statements.
            default : // Optional
                // Statements

        }
        return R.drawable.roadconstruction;

    }
}
