package com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects;

import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;

import java.util.List;

//import com.ntu.weeliangng.expandablerecylerlistview.model.Parent;

/**
 * Created by WeeLiang Ng on 3/11/2017.
 */

public class TitleCarParkObject implements Parent<HDBCarParkObject> {

    private String mName;
    private List<HDBCarParkObject> mHDBCarParkObjects;

    public TitleCarParkObject(String name, List<HDBCarParkObject> HDBCarParkObjects) {
        mName = name;
        mHDBCarParkObjects = HDBCarParkObjects;
    }

    public String getName() {
        return mName;
    }

    @Override
    public List<HDBCarParkObject> getChildList() {
        return mHDBCarParkObjects;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public HDBCarParkObject getIngredient(int position) {
        return mHDBCarParkObjects.get(position);
    }
/*
    public boolean isVegetarian() {
        for (HDBCarParkObject HDBCarParkObject : mHDBCarParkObjects) {
            if (!HDBCarParkObject.isVegetarian()) {
                return false;
            }
        }
        return true;
    }
*/

}

