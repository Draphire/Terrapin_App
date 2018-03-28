package com.lauden.weeliangng.terrapin.Controller.CarParkRecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleCarParkObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeeLiang Ng on 3/11/2017.
 */

public class TitleCarParkObjectAdapter extends ExpandableRecyclerAdapter<TitleCarParkObject, HDBCarParkObject, TitleCarParkObjectViewHolder, CarParkObjectViewHolder> {

    private static final int PARENT_VEGETARIAN = 0;
    private static final int PARENT_NORMAL = 1;
    private static final int CHILD_VEGETARIAN = 2;
    private static final int CHILD_NORMAL = 3;

    private LayoutInflater mInflater;
    private List<TitleCarParkObject> mTitleCameraListObject = new ArrayList<>();
    private List<TitleCarParkObject> itemsCopy = new ArrayList<>();


    public TitleCarParkObjectAdapter(Context context, @NonNull List<TitleCarParkObject> titleCameraListObject) {
        super(titleCameraListObject);
        mTitleCameraListObject = titleCameraListObject;
        mInflater = LayoutInflater.from(context);
        itemsCopy.addAll(titleCameraListObject);
    }


    @UiThread
    @NonNull
    @Override
    public TitleCarParkObjectViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView;
        switch (viewType) {
            default:
            case PARENT_NORMAL:
                recipeView = mInflater.inflate(R.layout.titlecarparkobject_view, parentViewGroup, false);
                break;
            case PARENT_VEGETARIAN:
                recipeView = mInflater.inflate(R.layout.alttitlecarparkobject_view, parentViewGroup, false);
                break;
        }
        return new TitleCarParkObjectViewHolder(recipeView);
    }

    @UiThread
    @NonNull
    @Override
    public CarParkObjectViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView;
        switch (viewType) {
            default:
            case CHILD_NORMAL:
                ingredientView = mInflater.inflate(R.layout.carparkobject_view, childViewGroup, false);

                break;
            case CHILD_VEGETARIAN:
                ingredientView = mInflater.inflate(R.layout.altcarparkobject_view, childViewGroup, false);
                break;
        }
        return new CarParkObjectViewHolder(ingredientView);
    }

    @UiThread
    @Override
    public void onBindParentViewHolder(@NonNull TitleCarParkObjectViewHolder titleCarParkObjectViewHolder, int parentPosition, @NonNull TitleCarParkObject titleCarParkObject) {
        titleCarParkObjectViewHolder.bind(titleCarParkObject);
    }

    @UiThread
    @Override
    public void onBindChildViewHolder(@NonNull CarParkObjectViewHolder CarParkObjectViewHolder, int parentPosition, int childPosition, @NonNull HDBCarParkObject HDBCarParkObject) {
        CarParkObjectViewHolder.bind(HDBCarParkObject);
    }

    @Override
    public int getParentViewType(int parentPosition) {
//        if (mTitleCameraListObject.get(parentPosition).isVegetarian()) {
        //          return PARENT_VEGETARIAN;
        //    } else {
        return PARENT_NORMAL;
        //  }
    }

    @Override
    public int getChildViewType(int parentPosition, int childPosition) {
        // HDBCarParkObject HDBCarParkObject = mTitleCameraListObject.get(parentPosition).getIngredient(childPosition);
        // if (HDBCarParkObject.isVegetarian()) {
        //  return CHILD_VEGETARIAN;
        //} else {
        return CHILD_NORMAL;
        //}
    }

    @Override
    public boolean isParentViewType(int viewType) {
        return viewType == PARENT_VEGETARIAN || viewType == PARENT_NORMAL;
    }

    public void swapItems(List<TitleCarParkObject> TitleCarParkObject) {
        //mTitleCameraListObject = new ArrayList<>();
        //  mTitleCameraListObject.clear();
        //setParentList(CameraDAOUsage.CameraDAOgetTitleObjectList(),true);
        //   this.mTitleCameraListObject = TitleCarParkObject;
        //List<TitleCarParkObject> titleCarParkObjects
        // this.mTitleCameraListObject.addAll(CameraDAOUsage.CameraDAOgetTitleObjectList()); //reload the items from database
        mTitleCameraListObject.clear();
        mTitleCameraListObject.addAll(TitleCarParkObject);
//        Log.d("Refresh requested", mTitleCameraListObject.get(0).getChildList().get(0).getTimestamp());
        // notifyDataSetChanged();
        // notifyParentDataSetChanged(true);
    }



    public void updateItems(List<TitleCarParkObject> TitleCarParkObject) {

        if (!mTitleCameraListObject.isEmpty()) {
            List<TitleCarParkObject> tempCamObj = new ArrayList<>();
            tempCamObj.clear();
            tempCamObj.addAll(mTitleCameraListObject);
            mTitleCameraListObject.clear();// clear existing to empty
            int tempObjSize = tempCamObj.size();
            // List<TitleCarParkObject> outerListObj = TitleCarParkObject;

            // List<TitleCarParkObject> innerListObj;
            for (int i = 0; i < TitleCarParkObject.size(); i++) {
                //tempCamObj.get(i);// the current list in the adapter
//            Log.d("The Current List = ", (tempCamObj.get(i).getName()));
                for (int j = 0; j < TitleCarParkObject.size(); j++) {
                    //TitleCarParkObject.get(j); // the full list input for update


                    if (i < tempObjSize && tempCamObj.get(i).getChildList().get(0).getCarParkNo().matches(TitleCarParkObject.get(j).getChildList().get(0).getCarParkNo())
                            ) //compare to find extract needed items for update
                    {
                        Log.d("The Update List = ", (TitleCarParkObject.get(i).getName()));

                        mTitleCameraListObject.add(TitleCarParkObject.get(j));// add equal items update to the empty existing list


                    }


                }

            }
            for (int j = 0; j < getParentList().size(); j++) {
                notifyChildRemoved(j, 0);
                notifyChildInserted(j, 0);
            }

        }
    }


    public void updateItems(List<TitleCarParkObject> TitleCarParkObject, List<TitleCarParkObject> TempTitleCarParkObject) {

        if (mTitleCameraListObject != null) {
            // List<TitleCarParkObject> tempCamObj = getParentList();
            mTitleCameraListObject.clear();// clear existing to empty

            // List<TitleCarParkObject> outerListObj = TitleCarParkObject;

            // List<TitleCarParkObject> innerListObj;
            for (int i = 0; i < TitleCarParkObject.size(); i++) {
                //tempCamObj.get(i);// the current list in the adapter
//            Log.d("The Current List = ", (tempCamObj.get(i).getName()));
                for (int j = 0; j < TitleCarParkObject.size(); j++) {
                    //TitleCarParkObject.get(j); // the full list input for update
                    Log.d("The Update List = ", (TitleCarParkObject.get(i).getName()));

                    if (TempTitleCarParkObject.get(i)
                            == TitleCarParkObject.get(j)) //compare to find extract needed items for update
                    {
                        mTitleCameraListObject.add(TitleCarParkObject.get(j));// add equal items update to the empty existing list

                    }


                }

            }


        }
    }

    /*
    public void loadMoreItems(int loadCount) {

        mTitleCameraListObject.clear();

        mTitleCameraListObject.addAll(CameraDAOUsage.GenMoreExample(loadCount));

    }*/


    public void loadItemsUpdate(List<TitleCarParkObject> newList) {

        mTitleCameraListObject.clear();

        mTitleCameraListObject.addAll(newList);
        notifyParentDataSetChanged(true);

    }


    public void filter(String text) {
        mTitleCameraListObject.clear();
        if (text.isEmpty()) {
            mTitleCameraListObject.addAll(itemsCopy);
        } else {
            text = text.toLowerCase();
            for (TitleCarParkObject item : itemsCopy) {
                if (item.getName().toLowerCase().contains(text) || item.getChildList().get(0).getCarParkNo().toLowerCase().contains(text)) {
                    mTitleCameraListObject.add(item);
                }
            }
        }
        // notifyDataSetChanged();
        notifyParentDataSetChanged(true);
//        Log.d("Search Parent Updated", (mTitleCameraListObject.get(0).getName()));
    }

}
