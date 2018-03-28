package com.lauden.weeliangng.terrapin.Controller.CameraListUIRecyclerView;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleCameraObject;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.UsageOfCameraDAO.CameraDAOUsage;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CameraSingleton;
import com.lauden.weeliangng.terrapin.R;

import java.util.ArrayList;
import java.util.List;

public class TitleCameraObjectAdapter extends ExpandableRecyclerAdapter<TitleCameraObject, LTACameraObject, TitleCameraObjectViewHolder, CameraObjectViewHolder> {

    private static final int PARENT_VEGETARIAN = 0;
    private static final int PARENT_NORMAL = 1;
    private static final int CHILD_VEGETARIAN = 2;
    private static final int CHILD_NORMAL = 3;

    private LayoutInflater mInflater;
    private List<TitleCameraObject> mTitleCameraListObject = new ArrayList<>();
    private List<TitleCameraObject> itemsCopy = new ArrayList<>();


    public TitleCameraObjectAdapter(Context context, @NonNull List<TitleCameraObject> titleCameraListObject) {
        super(titleCameraListObject);
        mTitleCameraListObject = titleCameraListObject;
        mInflater = LayoutInflater.from(context);
        itemsCopy.addAll(titleCameraListObject);
    }


    @UiThread
    @NonNull
    @Override
    public TitleCameraObjectViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView;
        switch (viewType) {
            default:
            case PARENT_NORMAL:
                recipeView = mInflater.inflate(R.layout.icontraffictitlecameraobject_view, parentViewGroup, false);
                break;
            case PARENT_VEGETARIAN:
                recipeView = mInflater.inflate(R.layout.alttitlecameraobject_view, parentViewGroup, false);
                break;
        }
        return new TitleCameraObjectViewHolder(recipeView);
    }

    @UiThread
    @NonNull
    @Override
    public CameraObjectViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView;
        switch (viewType) {
            default:
            case CHILD_NORMAL:
                ingredientView = mInflater.inflate(R.layout.cameraobject_view, childViewGroup, false);

                break;
            case CHILD_VEGETARIAN:
                ingredientView = mInflater.inflate(R.layout.altcameraobject_view, childViewGroup, false);
                break;
        }
        return new CameraObjectViewHolder(ingredientView);
    }

    @UiThread
    @Override
    public void onBindParentViewHolder(@NonNull TitleCameraObjectViewHolder titleCameraObjectViewHolder, int parentPosition, @NonNull TitleCameraObject titleCameraObject) {
        titleCameraObjectViewHolder.bind(titleCameraObject);
    }

    @UiThread
    @Override
    public void onBindChildViewHolder(@NonNull CameraObjectViewHolder cameraObjectViewHolder, int parentPosition, int childPosition, @NonNull LTACameraObject LTACameraObject) {
        cameraObjectViewHolder.bind(LTACameraObject);
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
       // LTACameraObject mLTACameraObject = mTitleCameraListObject.get(parentPosition).getIngredient(childPosition);
      // if (mLTACameraObject.isVegetarian()) {
          //  return CHILD_VEGETARIAN;
        //} else {
            return CHILD_NORMAL;
        //}
    }

    @Override
    public boolean isParentViewType(int viewType) {
        return viewType == PARENT_VEGETARIAN || viewType == PARENT_NORMAL;
    }

    public void swapItems(List<TitleCameraObject> TitleCameraObject) {
        //mTitleCameraListObject = new ArrayList<>();
      //  mTitleCameraListObject.clear();
        //setParentList(CameraDAOUsage.CameraDAOgetTitleObjectList(),true);
     //   this.mTitleCameraListObject = TitleCameraObject;
        //List<TitleCameraObject> titleCameraObjects
       // this.mTitleCameraListObject.addAll(CameraDAOUsage.CameraDAOgetTitleObjectList()); //reload the items from database
        mTitleCameraListObject.clear();
        mTitleCameraListObject.addAll(TitleCameraObject);
//        Log.d("Refresh requested", mTitleCameraListObject.get(0).getChildList().get(0).getTimestamp());
       // notifyDataSetChanged();
      // notifyParentDataSetChanged(true);
    }


    public void updateItems(Activity mAct) {
        List<TitleCameraObject> TitleCameraObject = CameraDAOUsage.CameraDAOgetTitleObjectList(mAct);

       // List<TitleCameraObject> TitleCameraObject = CameraSingleton.getInstance(mAct).getArrayList();

        // Get full updated list of grandfather objects
    if(!mTitleCameraListObject.isEmpty()){
        List<TitleCameraObject> tempCamObj = new ArrayList<>();
        tempCamObj.clear();
        tempCamObj.addAll(mTitleCameraListObject);
        mTitleCameraListObject.clear();// clear existing to empty
        int tempObjSize = tempCamObj.size();

        for(int i =0;i<TitleCameraObject.size();i++) {
            for(int j =0;j<TitleCameraObject.size();j++) {
              // the full list input for update
                if(  i < tempObjSize && tempCamObj.get(i).getChildList().get(0)
                        .getCamera_id().matches(TitleCameraObject.get(j)
                                .getChildList().get(0).getCamera_id())
                         ) //compare to find extract needed items for update
                {
                    mTitleCameraListObject.add(TitleCameraObject.get(j));
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

    public void updateItems(List<TitleCameraObject> TitleCameraObject) {
       // List<TitleCameraObject> TitleCameraObject = CameraDAOUsage.CameraDAOgetTitleObjectList(mAct);

        // List<TitleCameraObject> TitleCameraObject = CameraSingleton.getInstance(mAct).getArrayList();

        // Get full updated list of grandfather objects
        if(!mTitleCameraListObject.isEmpty()){
            List<TitleCameraObject> tempCamObj = new ArrayList<>();
            tempCamObj.clear();
            tempCamObj.addAll(mTitleCameraListObject);
            mTitleCameraListObject.clear();// clear existing to empty
            int tempObjSize = tempCamObj.size();

            for(int i =0;i<TitleCameraObject.size();i++) {
                for(int j =0;j<TitleCameraObject.size();j++) {
                    // the full list input for update
                    if(  i < tempObjSize && tempCamObj.get(i).getChildList().get(0)
                            .getCamera_id().matches(TitleCameraObject.get(j)
                                    .getChildList().get(0).getCamera_id())
                            ) //compare to find extract needed items for update
                    {
                        mTitleCameraListObject.add(TitleCameraObject.get(j));
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


    public void updateItems(List<TitleCameraObject> TitleCameraObject, List<TitleCameraObject> TempTitleCameraObject) {

        if(mTitleCameraListObject != null){
            // List<TitleCameraObject> tempCamObj = getParentList();
            mTitleCameraListObject.clear();// clear existing to empty

            // List<TitleCameraObject> outerListObj = TitleCameraObject;

            // List<TitleCameraObject> innerListObj;
            for(int i =0;i<TitleCameraObject.size();i++) {
                //tempCamObj.get(i);// the current list in the adapter
//            Log.d("The Current List = ", (tempCamObj.get(i).getName()));
                for(int j =0;j<TitleCameraObject.size();j++) {
                    //TitleCameraObject.get(j); // the full list input for update
                    Log.d("The Update List = ", (TitleCameraObject.get(i).getName()));

                    if(TempTitleCameraObject.get(i)
                            == TitleCameraObject.get(j)) //compare to find extract needed items for update
                    {
                        mTitleCameraListObject.add(TitleCameraObject.get(j));// add equal items update to the empty existing list

                    }


                }

            }


        }}

    public void filter(String text) {
        mTitleCameraListObject.clear();
        if(text.isEmpty()){
            mTitleCameraListObject.addAll(itemsCopy);
        } else{
            text = text.toLowerCase();
            for(TitleCameraObject item: itemsCopy){
                if(item.getName().toLowerCase().contains(text) || item.getChildList().get(0).getCamera_id().toLowerCase().contains(text)){
                    mTitleCameraListObject.add(item);
                }
            }
        }
       // notifyDataSetChanged();
    notifyParentDataSetChanged(true);
//        Log.d("Search Parent Updated", (mTitleCameraListObject.get(0).getName()));
    }


}
