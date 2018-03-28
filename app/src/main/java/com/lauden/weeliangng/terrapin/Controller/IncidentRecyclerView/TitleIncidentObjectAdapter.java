package com.lauden.weeliangng.terrapin.Controller.IncidentRecyclerView;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleIncidentObject;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleIncidentObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.UsageOfCameraDAO.CameraDAOUsage;
import com.lauden.weeliangng.terrapin.Model.IncidentDAOpattern.IncidentDAO;
import com.lauden.weeliangng.terrapin.Model.IncidentDAOpattern.UsageOfIncidentDAO.IncidentDAOUsage;
import com.lauden.weeliangng.terrapin.Model.IncidentFactoryPattern.IncidentObject;
import com.lauden.weeliangng.terrapin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeeLiang Ng on 6/3/2018.
 */

public class TitleIncidentObjectAdapter extends ExpandableRecyclerAdapter<TitleIncidentObject, IncidentObject, TitleIncidentObjectViewHolder, IncidentObjectViewHolder> {

    private static final int PARENT_VEGETARIAN = 0;
    private static final int PARENT_NORMAL = 1;
    private static final int CHILD_VEGETARIAN = 2;
    private static final int CHILD_NORMAL = 3;
    private static final int ACCIDENT = 4;
    private static final int ROADWORK = 5;
    private static final int VEHICLE_BREAKDOWN = 6;
    private static final int ABANDONED_VEHICLE = 7;
    private static final int HEAVY_TRAFFIC = 8;
    private static final int OBSTACLE = 9;
    private static final int DIVERSION = 10;

    private static final int WEATHER = 11;
    private static final int ROADBLOCK = 12;


    private LayoutInflater mInflater;
    private List<TitleIncidentObject> mTitleIncidentListObject = new ArrayList<>();
    private List<TitleIncidentObject> itemsCopy = new ArrayList<>();


    public TitleIncidentObjectAdapter(Context context, @NonNull List<TitleIncidentObject> titleIncidentListObject) {
        super(titleIncidentListObject);
        mTitleIncidentListObject = titleIncidentListObject;
        mInflater = LayoutInflater.from(context);
        itemsCopy.addAll(titleIncidentListObject);
    }


    @UiThread
    @NonNull
    @Override
    public TitleIncidentObjectViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView;
        switch (viewType) {

            case ROADWORK:
                recipeView = mInflater.inflate(R.layout.roadworktitleincidentobject_view, parentViewGroup, false);
                break;
            default:

            case ACCIDENT:
                recipeView = mInflater.inflate(R.layout.accidenttitleincidentobject_view, parentViewGroup, false);
                break;
            case VEHICLE_BREAKDOWN:
                recipeView = mInflater.inflate(R.layout.vehiclebreakdowntitleincidentobject_view, parentViewGroup, false);
                break;
            case ABANDONED_VEHICLE:
                recipeView = mInflater.inflate(R.layout.abandonedtitleincidentobject_view, parentViewGroup, false);
                break;
            case HEAVY_TRAFFIC:
                recipeView = mInflater.inflate(R.layout.heavytraffictitleincidentobject_view, parentViewGroup, false);
                break;
            case WEATHER:
                recipeView = mInflater.inflate(R.layout.weatherincidentobject_view, parentViewGroup, false);
                break;
              case OBSTACLE:
                recipeView = mInflater.inflate(R.layout.obstacletitleincidentobject_view, parentViewGroup, false);
                break;
            case DIVERSION:
                recipeView = mInflater.inflate(R.layout.diversionincidentobject_view, parentViewGroup, false);
                break;
            case ROADBLOCK:
                recipeView = mInflater.inflate(R.layout.obstacletitleincidentobject_view, parentViewGroup, false);
                break;


        }
        return new TitleIncidentObjectViewHolder(recipeView);
    }

    @UiThread
    @NonNull
    @Override
    public IncidentObjectViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView;
        switch (viewType) {
            default:
            case CHILD_NORMAL:
                ingredientView = mInflater.inflate(R.layout.incidentobject_view, childViewGroup, false);

                break;
            case CHILD_VEGETARIAN:
                ingredientView = mInflater.inflate(R.layout.altcarparkobject_view, childViewGroup, false);
                break;
        }
        return new IncidentObjectViewHolder(ingredientView);
    }

    @UiThread
    @Override
    public void onBindParentViewHolder(@NonNull TitleIncidentObjectViewHolder titleIncidentObjectViewHolder, int parentPosition, @NonNull TitleIncidentObject titleCarParkObject) {
        titleIncidentObjectViewHolder.bind(titleCarParkObject);
    }

    @UiThread
    @Override
    public void onBindChildViewHolder(@NonNull IncidentObjectViewHolder IncidentObjectViewHolder, int parentPosition, int childPosition, @NonNull IncidentObject IncidentObject) {
        IncidentObjectViewHolder.bind(IncidentObject);
    }

    @Override
    public int getParentViewType(int parentPosition) {
//        if (mTitleIncidentListObject.get(parentPosition).isVegetarian()) {
        //          return PARENT_VEGETARIAN;
        //    } else {
        if (mTitleIncidentListObject.get(parentPosition).parentCategory().equalsIgnoreCase("accident")){
            return ACCIDENT;
        }else  if (mTitleIncidentListObject.get(parentPosition).parentCategory().equalsIgnoreCase("roadwork")){
            return ROADWORK;
        }else  if (mTitleIncidentListObject.get(parentPosition).parentCategory().equalsIgnoreCase("vehiclebreakdown")){
            return VEHICLE_BREAKDOWN;
        }else  if (mTitleIncidentListObject.get(parentPosition).parentCategory().equalsIgnoreCase("heavytraffic")){
            return HEAVY_TRAFFIC;
        }else  if (mTitleIncidentListObject.get(parentPosition).parentCategory().equalsIgnoreCase("abandonedvehicle")){
            return ABANDONED_VEHICLE;
        }else  if (mTitleIncidentListObject.get(parentPosition).parentCategory().equalsIgnoreCase("obstacle")){
            return OBSTACLE;
        }else  if (mTitleIncidentListObject.get(parentPosition).parentCategory().equalsIgnoreCase("diversion")){
            return DIVERSION;
        }else  if (mTitleIncidentListObject.get(parentPosition).parentCategory().equalsIgnoreCase("weather")){
            return WEATHER;
        }else  if (mTitleIncidentListObject.get(parentPosition).parentCategory().equalsIgnoreCase("road block")){
            return ROADBLOCK;
        }
        return PARENT_NORMAL;
        //modify to more options
        //  }
    }

    @Override
    public int getChildViewType(int parentPosition, int childPosition) {
        // IncidentObject IncidentObject = mTitleIncidentListObject.get(parentPosition).getIngredient(childPosition);
        // if (IncidentObject.isVegetarian()) {
        //  return CHILD_VEGETARIAN;
        //} else {
        return CHILD_NORMAL;
        //}
    }

    @Override
    public boolean isParentViewType(int viewType) {
        return viewType == PARENT_VEGETARIAN || viewType == PARENT_NORMAL|| viewType == ACCIDENT
                || viewType == VEHICLE_BREAKDOWN|| viewType == ABANDONED_VEHICLE|| viewType == DIVERSION
                || viewType == WEATHER|| viewType == OBSTACLE|| viewType == ROADWORK
                || viewType == HEAVY_TRAFFIC|| viewType == ROADBLOCK;
    }

    public void swapItems(List<TitleIncidentObject> TitleIncidentObject) {
        //mTitleIncidentListObject = new ArrayList<>();
        //  mTitleIncidentListObject.clear();
        //setParentList(CameraDAOUsage.CameraDAOgetTitleObjectList(),true);
        //   this.mTitleIncidentListObject = TitleIncidentObject;
        //List<TitleIncidentObject> titleCarParkObjects
        // this.mTitleIncidentListObject.addAll(CameraDAOUsage.CameraDAOgetTitleObjectList()); //reload the items from database
        mTitleIncidentListObject.clear();
        mTitleIncidentListObject.addAll(TitleIncidentObject);
//        Log.d("Refresh requested", mTitleIncidentListObject.get(0).getChildList().get(0).getTimestamp());
        // notifyDataSetChanged();
        // notifyParentDataSetChanged(true);
    }



    public void updateItems(Activity mAct) {
        List<TitleIncidentObject> TitleIncidentObject = IncidentDAOUsage.IncidentDAOgetTitleObjectList();

        // Get full updated list of grandfather objects
        if(!mTitleIncidentListObject.isEmpty()){
            List<TitleIncidentObject> tempCamObj = new ArrayList<>();
            tempCamObj.clear();
            tempCamObj.addAll(mTitleIncidentListObject);
            mTitleIncidentListObject.clear();// clear existing to empty
            int tempObjSize = tempCamObj.size();

            for(int i =0;i<TitleIncidentObject.size();i++) {
                for(int j =0;j<TitleIncidentObject.size();j++) {
                    // the full list input for update
                    if(  i < tempObjSize && tempCamObj.get(i).getChildList().get(0)
                            .getMessage().matches(TitleIncidentObject.get(j)
                                    .getChildList().get(0).getMessage())
                            ) //compare to find extract needed items for update
                    {
                        mTitleIncidentListObject.add(TitleIncidentObject.get(j));
                        // add matching items update to the empty existing list
                    }
                }
            }
            // After getting the new updated Camera Object array, update the expandable items in list
            for (int j = 0; j < getParentList().size(); j++) {
                notifyChildRemoved(j, 0);
                notifyChildInserted(j, 0);
            }
        }}



    public void loadItemsUpdate(List<TitleIncidentObject> newList) {

        mTitleIncidentListObject.clear();

        mTitleIncidentListObject.addAll(newList);
        notifyParentDataSetChanged(true);

    }


    public void filter(String text) {
        mTitleIncidentListObject.clear();
        if (text.isEmpty()) {
            mTitleIncidentListObject.addAll(itemsCopy);
        } else {
            text = text.toLowerCase();
            for (TitleIncidentObject item : itemsCopy) {
                if (item.getName().toLowerCase().contains(text) || item.getChildList().get(0).getMessage().toLowerCase().contains(text)) {
                    mTitleIncidentListObject.add(item);
                }
            }
        }
        // notifyDataSetChanged();
        notifyParentDataSetChanged(true);
//        Log.d("Search Parent Updated", (mTitleIncidentListObject.get(0).getName()));
    }

}


