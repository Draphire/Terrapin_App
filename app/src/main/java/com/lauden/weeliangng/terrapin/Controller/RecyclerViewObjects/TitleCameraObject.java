package com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects;

//import com.ntu.weeliangng.expandablerecylerlistview.model.Parent;

import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;

import java.util.List;

public class TitleCameraObject implements Parent<LTACameraObject> {

    private String mName;
    private List<LTACameraObject> mLTACameraObjects;

    public TitleCameraObject(String name, List<LTACameraObject> LTACameraObjects) {
        mName = name;
        mLTACameraObjects = LTACameraObjects;
    }

    public String getName() {
        return mName;
    }

    @Override
    public List<LTACameraObject> getChildList() {
        return mLTACameraObjects;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public LTACameraObject getIngredient(int position) {
        return mLTACameraObjects.get(position);
    }
/*
    public boolean isVegetarian() {
        for (LTACameraObject cameraObject : mLTACameraObjects) {
            if (!cameraObject.isVegetarian()) {
                return false;
            }
        }
        return true;
    }
*/

}

