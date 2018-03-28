package com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects;

import com.bignerdranch.expandablerecyclerview.model.*;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.MallObject;

import java.util.List;

/**
 * Created by WeeLiang Ng on 6/3/2018.
 */

public class TitleMallObject implements com.bignerdranch.expandablerecyclerview.model.Parent<MallObject> {
    private String mName;
    private List<MallObject> mMallObjects;

    public TitleMallObject(String name, List<MallObject> MallObjects) {
        mName = name;
        mMallObjects = MallObjects;
    }

    public String getName() {
        return mName;
    }

    @Override
    public List<MallObject> getChildList() {
        return mMallObjects;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public MallObject getIngredient(int position) {
        return mMallObjects.get(position);
    }
/*
    public boolean isVegetarian() {
        for (MallObject MallObject : mMallObjects) {
            if (!MallObject.isVegetarian()) {
                return false;
            }
        }
        return true;
    }
*/

}



