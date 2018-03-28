package com.lauden.weeliangng.terrapin.Controller.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lauden.weeliangng.terrapin.View.CameraListPresentation.CameraListUI;
import com.lauden.weeliangng.terrapin.View.CarParkListPresentation.CarParkListUI;
import com.lauden.weeliangng.terrapin.View.CarParkListPresentation.MallListUI;
import com.lauden.weeliangng.terrapin.View.IncidentListPresentation.IncidentListUI;
import com.lauden.weeliangng.terrapin.View.MainActivityUI;
import com.lauden.weeliangng.terrapin.View.MapPresentation.MapUI;
import com.lauden.weeliangng.terrapin.View.RoadWorkListPresentation.RoadWorkListUI;

//import com.lauden.weeliangng.terrapin.Controller.Adapter.FragmentStatePagerAdapter;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
        Context mContext;
 FragmentManager cm;
    //  public String tableName1;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, Context context) {
        super(fm);
        cm = fm;
        this.mContext = context;
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }


    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {


        if (position == 0) // if the position is 0 we are returning the First tab
        {
            MapUI mapUI = new MapUI();
            Bundle args = new Bundle();

            args.putString("tableName", "map");
            mapUI.setArguments(args);
            return mapUI;
        } else if (position == 1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {

            CameraListUI cameraListView = new CameraListUI();
            Bundle args = new Bundle();

            args.putString("tableName", "camera");
            cameraListView.setArguments(args);
            return cameraListView;
        } else if (position == 2) {
            IncidentListUI cameraListView = new IncidentListUI();
            Bundle args = new Bundle();

            args.putString("tableName", "camera");
            cameraListView.setArguments(args);
            return cameraListView;
        }else if (position == 3) {
            RoadWorkListUI RoadWorkListView = new RoadWorkListUI();
            Bundle args = new Bundle();

            args.putString("tableName", "RoadWork");
            RoadWorkListView.setArguments(args);
            return RoadWorkListView;
        }else if (position == 4) {
            CarParkListUI carUI= new CarParkListUI();
            Bundle args = new Bundle();

            args.putString("tableName", "carpark");
            carUI.setArguments(args);
            return carUI;
        }else if (position == 5) {
            MallListUI mallUI= new MallListUI();
            Bundle args = new Bundle();

            args.putString("tableName", "mallcarpark");
            mallUI.setArguments(args);
            return mallUI;
        }


        return null;
    }

    //call this method to update fragments in ViewPager dynamically
    public void update(MainActivityUI.UpdateData xyzData) {
        //this.updateData = xyzData;
        notifyDataSetChanged();
    }
//
//    @Override
//    public int getItemPosition(Object object) {
//        if (object instanceof UpdateableFragment) {
//            ((UpdateableFragment) object).update(updateData);
//        }
//        //don't return POSITION_NONE, avoid fragment recreation.
//        return super.getItemPosition(object);
//    }



    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}