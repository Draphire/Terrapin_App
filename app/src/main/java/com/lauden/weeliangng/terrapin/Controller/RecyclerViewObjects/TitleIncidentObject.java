package com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects;

import com.lauden.weeliangng.terrapin.Model.IncidentFactoryPattern.IncidentObject;

        import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.lauden.weeliangng.terrapin.Model.IncidentFactoryPattern.IncidentObject;

import java.util.List;

/**
 * Created by WeeLiang Ng on 6/3/2018.
 */

public class TitleIncidentObject implements Parent<IncidentObject> {
    private String mName;
    private List<IncidentObject> mIncidentObjects;

    public TitleIncidentObject(String name, List<IncidentObject> IncidentObjects) {
        mName = name;
        mIncidentObjects = IncidentObjects;
    }

    public String getName() {
        return mName;
    }

    @Override
    public List<IncidentObject> getChildList() {
        return mIncidentObjects;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public IncidentObject getIngredient(int position) {
        return mIncidentObjects.get(position);
    }


    public String parentCategory(){
        if(mIncidentObjects.get(0).getType().equalsIgnoreCase("Roadwork")){
            return "roadwork";
        }else if(mIncidentObjects.get(0).getType().equals("accident")){
            return "accident";
        }else if(mIncidentObjects.get(0).getType().equalsIgnoreCase("Heavy traffic")){
            return "heavytraffic";
        }else if(mIncidentObjects.get(0).getType().equalsIgnoreCase("vehicle breakdown")){
            return "vehiclebreakdown";
        }else if(mIncidentObjects.get(0).getType().equalsIgnoreCase("Abandoned Vehicle")){
            return "abandonedvehicle";
        }else if(mIncidentObjects.get(0).getType().equalsIgnoreCase("Obstacle")){
            return "obstacle";
        }else if(mIncidentObjects.get(0).getType().equalsIgnoreCase("weather")){
            return "weather";
        }else if(mIncidentObjects.get(0).getType().equalsIgnoreCase("Diversion")){
            return "diversion";
        }else if(mIncidentObjects.get(0).getType().equalsIgnoreCase("Road Block")){
            return "Road Block";
        }else if(mIncidentObjects.get(0).getType().equalsIgnoreCase("Weather")){
            return "weather";
        }

        return "normal";
    }
/*
    public boolean isVegetarian() {
        for (IncidentObject IncidentObject : mIncidentObjects) {
            if (!IncidentObject.isVegetarian()) {
                return false;
            }
        }
        return true;
    }
*/

}



