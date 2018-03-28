package com.lauden.weeliangng.terrapin.Controller.MallRecyclerView;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.lauden.weeliangng.terrapin.Controller.MallRecyclerView.MallObjectViewHolder;
import com.lauden.weeliangng.terrapin.Controller.MallRecyclerView.TitleMallObjectViewHolder;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleMallObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CarParkDAOpattern.UsageOfCarparkDAO.CarParkDAOUsage;
//import com.lauden.weeliangng.terrapin.Model.CarParkDAOpattern.UsageOfMallDAO.MallDAOUsage;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.MallObject;
import com.lauden.weeliangng.terrapin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeeLiang Ng on 6/3/2018.
 */

public class TitleMallObjectAdapter  extends ExpandableRecyclerAdapter<TitleMallObject, MallObject, TitleMallObjectViewHolder, MallObjectViewHolder> {

    private static final int PARENT_VEGETARIAN = 0;
    private static final int PARENT_NORMAL = 1;
    private static final int CHILD_VEGETARIAN = 2;
    private static final int CHILD_NORMAL = 3;

    private LayoutInflater mInflater;
    private List<TitleMallObject> mTitleMallListObject = new ArrayList<>();
    private List<TitleMallObject> itemsCopy = new ArrayList<>();


    public TitleMallObjectAdapter(Context context, @NonNull List<TitleMallObject> titleMallListObject) {
        super(titleMallListObject);
        mTitleMallListObject = titleMallListObject;
        mInflater = LayoutInflater.from(context);
        itemsCopy.addAll(titleMallListObject);
    }


    @UiThread
    @NonNull
    @Override
    public TitleMallObjectViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView;
        switch (viewType) {
            default:
            case PARENT_NORMAL:
                recipeView = mInflater.inflate(R.layout.titlemallobject_view, parentViewGroup, false);
                break;
            case PARENT_VEGETARIAN:
                recipeView = mInflater.inflate(R.layout.alttitlecarparkobject_view, parentViewGroup, false);
                break;
        }
        return new TitleMallObjectViewHolder(recipeView);
    }

    @UiThread
    @NonNull
    @Override
    public MallObjectViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView;
        switch (viewType) {
            default:
            case CHILD_NORMAL:
                ingredientView = mInflater.inflate(R.layout.mallobject_view, childViewGroup, false);

                break;
            case CHILD_VEGETARIAN:
                ingredientView = mInflater.inflate(R.layout.altcarparkobject_view, childViewGroup, false);
                break;
        }
        return new MallObjectViewHolder(ingredientView);
    }

    @UiThread
    @Override
    public void onBindParentViewHolder(@NonNull TitleMallObjectViewHolder titleMallObjectViewHolder, int parentPosition, @NonNull TitleMallObject titleCarParkObject) {
        titleMallObjectViewHolder.bind(titleCarParkObject);
    }

    @UiThread
    @Override
    public void onBindChildViewHolder(@NonNull MallObjectViewHolder MallObjectViewHolder, int parentPosition, int childPosition, @NonNull MallObject MallObject) {
        MallObjectViewHolder.bind(MallObject);
    }

    @Override
    public int getParentViewType(int parentPosition) {
//        if (mTitleMallListObject.get(parentPosition).isVegetarian()) {
        //          return PARENT_VEGETARIAN;
        //    } else {
        return PARENT_NORMAL;
        //  }
    }

    @Override
    public int getChildViewType(int parentPosition, int childPosition) {
        // MallObject MallObject = mTitleMallListObject.get(parentPosition).getIngredient(childPosition);
        // if (MallObject.isVegetarian()) {
        //  return CHILD_VEGETARIAN;
        //} else {
        return CHILD_NORMAL;
        //}
    }

    @Override
    public boolean isParentViewType(int viewType) {
        return viewType == PARENT_VEGETARIAN || viewType == PARENT_NORMAL;
    }

    public void swapItems(List<TitleMallObject> TitleMallObject) {
        //mTitleMallListObject = new ArrayList<>();
        //  mTitleMallListObject.clear();
        //setParentList(CameraDAOUsage.CameraDAOgetTitleObjectList(),true);
        //   this.mTitleMallListObject = TitleMallObject;
        //List<TitleMallObject> titleCarParkObjects
        // this.mTitleMallListObject.addAll(CameraDAOUsage.CameraDAOgetTitleObjectList()); //reload the items from database
        mTitleMallListObject.clear();
        mTitleMallListObject.addAll(TitleMallObject);
//        Log.d("Refresh requested", mTitleMallListObject.get(0).getChildList().get(0).getTimestamp());
        // notifyDataSetChanged();
        // notifyParentDataSetChanged(true);
    }



    public void updateItems(Activity mAct) {
       // List<TitleMallObject> TitleMallObject = CarParkDAOUsage.MallDAOgetTitleObjectList();
        List<TitleMallObject> TitleMallObject = new ArrayList<>();

        // Get full updated list of grandfather objects
        if(!mTitleMallListObject.isEmpty()){
            List<TitleMallObject> tempCamObj = new ArrayList<>();
            tempCamObj.clear();
            tempCamObj.addAll(mTitleMallListObject);
            mTitleMallListObject.clear();// clear existing to empty
            int tempObjSize = tempCamObj.size();

            for(int i =0;i<TitleMallObject.size();i++) {
                for(int j =0;j<TitleMallObject.size();j++) {
                    // the full list input for update
                    if(  i < tempObjSize && tempCamObj.get(i).getChildList().get(0)
                            .getCarParkID().matches(TitleMallObject.get(j)
                                    .getChildList().get(0).getCarParkID())
                            ) //compare to find extract needed items for update
                    {
                        mTitleMallListObject.add(TitleMallObject.get(j));
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



    public void loadItemsUpdate(List<TitleMallObject> newList) {

        mTitleMallListObject.clear();

        mTitleMallListObject.addAll(newList);
        notifyParentDataSetChanged(true);

    }


    public void filter(String text) {
        mTitleMallListObject.clear();
        if (text.isEmpty()) {
            mTitleMallListObject.addAll(itemsCopy);
        } else {
            text = text.toLowerCase();
            for (TitleMallObject item : itemsCopy) {
                if (item.getName().toLowerCase().contains(text) || item.getChildList().get(0).getDevelopment().toLowerCase().contains(text)) {
                    mTitleMallListObject.add(item);
                }
            }
        }
        // notifyDataSetChanged();
        notifyParentDataSetChanged(true);
//        Log.d("Search Parent Updated", (mTitleMallListObject.get(0).getName()));
    }

}


