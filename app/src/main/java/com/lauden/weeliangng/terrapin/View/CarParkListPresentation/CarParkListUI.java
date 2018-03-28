package com.lauden.weeliangng.terrapin.View.CarParkListPresentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.lauden.weeliangng.terrapin.Controller.CarParkRecyclerView.TitleCarParkObjectAdapter;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleCarParkObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CarParkDAOpattern.UsageOfCarparkDAO.CarParkDAOUsage;
import com.lauden.weeliangng.terrapin.Model.Utility.ConnectionCheck;
import com.lauden.weeliangng.terrapin.R;
import com.lauden.weeliangng.terrapin.View.MainActivityObserver;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by WeeLiang Ng on 2/11/2017.
 */


//Subcribed to Observer MainActivityObserver, Observer Pattern
public class CarParkListUI extends Fragment implements MainActivityObserver{

/*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivityUI) activity).registerDataUpdateListener(this);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivityUI) context).registerDataUpdateListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivityUI) getActivity()).unregisterDataUpdateListener(this);
    }

    @Override
    public void onDataUpdate() {
        // put your UI update logic here
    }
*/
    //private ListView lvCamera;
    // private static final List<HDBCarParkObject> keeplist = new ArrayList<>();
    List<HDBCarParkObject> mHDBCarParkObjectList = new ArrayList<>();
    List<TitleCarParkObject> items;
    Boolean _areLecturesLoaded = false;
    private int loadCount = 5;

   // String storedate = CurrentTime.getCurrentTimeStamp();

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Bundle savedInstanceState;
    RecyclerView recyclerView;
    SearchView searchBar;
    SearchView searchView;



    private final String KEY_RECYCLER_STATE = "recycler_state";
    // private CameraListUIRecyclerView mRecyclerView;
    private static Bundle mBundleRecyclerViewState;

    private TitleCarParkObjectAdapter mAdapter;

    ConnectionCheck lfyl = new ConnectionCheck(getActivity()); //Here the context is passing
    private String mSearchString;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.carparklistui, container, false);
        setHasOptionsMenu(true);

        super.onActivityCreated(savedInstanceState);

        if (!isConnected(getActivity())) buildDialog(getActivity()).show();
        else {
            // List<HDBCarParkObject> testlist2 = HDBCarParkAPIDAOImplement.getComboCarParkList(testlist);
           //List<HDBCarParkObject> testlist2 = HDBCarParkAPIDAOImplement.getCarParkList(testlist);


            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewcam);
            Log.d("CmeraList", " Revived");



            MobileAds.initialize(getActivity(),
                    "ca-app-pub-7220416147687992~6211443619");

            AdView mAdView = (AdView) v.findViewById(R.id.adView);

            final View rootV = v;


            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    Log.i("Ads", "onAdLoaded");
                    //  AdView mAdView = (AdView) v.findViewById(R.id.adView2);
                    LinearLayout adContainer = (LinearLayout) rootV.findViewById(R.id.adCarSizing);

                    LinearLayout listContainer = (LinearLayout) rootV.findViewById(R.id.listContain);


                    int heightPixels = AdSize.SMART_BANNER.getHeightInPixels(getActivity());
                    LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) adContainer.getLayoutParams();

// Changes the height and width to the specified *pixels*
                    params.height = heightPixels;

                    LinearLayout.LayoutParams paramsTop = (android.widget.LinearLayout.LayoutParams) listContainer.getLayoutParams();

// Changes the height and width to the specified *pixels*
                    //  params.height = heightPixels;
                    paramsTop.topMargin=heightPixels;
                    // params.width = 100;
                    listContainer.setLayoutParams(paramsTop);

                    // params.width = 100;
                    adContainer.setLayoutParams(params);

                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                    Log.i("Ads", "onAdFailedToLoad");

                    LinearLayout adContainer = (LinearLayout) rootV.findViewById(R.id.adCarSizing);

                    LinearLayout listContainer = (LinearLayout) rootV.findViewById(R.id.listContain);


                    int heightPixels = AdSize.SMART_BANNER.getHeightInPixels(getActivity());
                    LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) adContainer.getLayoutParams();

// Changes the height and width to the specified *pixels*
                    params.height = 0;

                    LinearLayout.LayoutParams paramsTop = (android.widget.LinearLayout.LayoutParams) listContainer.getLayoutParams();

// Changes the height and width to the specified *pixels*
                    //  params.height = heightPixels;
                    paramsTop.topMargin=0;
                    // params.width = 100;
                    listContainer.setLayoutParams(paramsTop);

                    // params.width = 100;
                    adContainer.setLayoutParams(params);
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                    Log.i("Ads", "onAdOpened");
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                    Log.i("Ads", "onAdLeftApplication");
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when when the user is about to return
                    // to the app after tapping on an ad.
                    Log.i("Ads", "onAdClosed");
                }
            });

            items = CarParkDAOUsage.CarparkDAOgetTitleObjectList(getContext());


            mAdapter = new TitleCarParkObjectAdapter(getActivity(), items);


            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


            if (savedInstanceState == null) {

                //Restore the fragment's state here
                //super.onRestoreInstanceState(savedInstanceState);
                //   recyclerView=(CameraListUIRecyclerView) getActivity().findViewById(R.id.recyclerview);


            } else {

                if(mAdapter!=null)
                mAdapter.onRestoreInstanceState(savedInstanceState);
                Log.d("Adapter", " Restored");
                mSearchString = savedInstanceState.getString("SEARCH_KEY");

            }



            mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {

                @UiThread
                @Override
                public void onParentExpanded(int parentPosition) {

                    TitleCarParkObject expandedRecipe = items.get(parentPosition);
                    for (int i = 0; i < mAdapter.getParentList().size(); i++) {
                        if (i != parentPosition) {
                            mAdapter.collapseParent(i);
                        }

                    }
                   /* String toastMsg = getResources().getString(R.string.expanded, expandedRecipe.getName());
                    Toast.makeText(getActivity(),
                            toastMsg,
                            Toast.LENGTH_SHORT)
                            .show();*/
                }

                @UiThread
                @Override
                public void onParentCollapsed(int parentPosition) {
                    TitleCarParkObject collapsedRecipe = items.get(parentPosition);

                  /*  String toastMsg = getResources().getString(R.string.collapsed, collapsedRecipe.getName());
                    Toast.makeText(getActivity(),
                            toastMsg,
                            Toast.LENGTH_SHORT)
                            .show();*/
                }
            });




        }
        return v;
    }



    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // super.onRestoreInstanceState(savedInstanceState);
        mAdapter.onRestoreInstanceState(savedInstanceState);
    }


    public boolean isInternet()
    {
        boolean isInternet;
        ConnectionCheck cd = new ConnectionCheck(getActivity());
        isInternet = cd.isNetworkConnectionAvailable();
        return isInternet;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.cpmenu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);

        //focus the SearchView
        if (mSearchString != null && !mSearchString.isEmpty()) {
            item.expandActionView();

            searchView.setQuery(mSearchString, true);
            mAdapter.filter(mSearchString);

            searchView.clearFocus();
        }

        searchView.setQueryHint("Search For");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try{
                    Toast.makeText(getActivity(),query, Toast.LENGTH_LONG).show();
                    mAdapter.filter(query);
                    searchView.clearFocus();
                    //searchView.onActionViewCollapsed();
                    //   MenuItemCompat.collapseActionView(item);
                    return true;
                }
                catch(NumberFormatException ex) {
                    //They didn't enter a number.  Pop up a toast or warn them in some other way
                    Toast.makeText(getActivity(), "Please Enter A Valid input!", Toast.LENGTH_SHORT).show();

                    return true;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(mAdapter != null)
                    mAdapter.filter(newText);

                return false;
            }
        });




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_refresh) {

          //  List<TitleCarParkObject> tempItem = items;
            //List<CarparkInfo> testlist = new HDBCarParkAPIDAOImplement(getActivity()).readCarparkRawData();

            //items = CarParkDAOUsage.CameraDAOgetTitleObjectList(getActivity(), testlist);
       //     items = CarParkDAOUsage.CarparkDAOgetTitleObjectList(getContext());

            //   items = CameraDAOUsage.CameraDAOgetTitleObjectList();

            //  mAdapter.updateItems(items,tempItem);
        //    items = CarParkDAOUsage.GenExampleLiteRefresh(getContext());

          // mAdapter.updateItems(items);

           // items = CameraDAOUsage.CameraDAOgetTitleObjectList();

            //  mAdapter.updateItems(items,tempItem);

       //     mAdapter.updateItems(items);


            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        //recyclerView=(CameraListUIRecyclerView) getActivity().findViewById(R.id.recyclerview);
        // restore CameraListUIRecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            if(recyclerView!= null)
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);

        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        mAdapter.onRestoreInstanceState(savedInstanceState);


    }


    Parcelable listState;



    @Override
    public void onPause()
    {
        super.onPause();

        // save CameraListUIRecyclerView state
        mBundleRecyclerViewState = new Bundle();
        if(recyclerView != null) {
            listState = recyclerView.getLayoutManager().onSaveInstanceState();
        }

        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }





    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {

        super.onStop();

    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {

            super.onSaveInstanceState(outState);
            if(mAdapter!=null)
            mAdapter.onSaveInstanceState(outState);
            Log.d("SaveInstance ListUI", "Saved");
            if (searchView != null)
                mSearchString = searchView.getQuery().toString();
            outState.putString("SEARCH_KEY", mSearchString);
        }
        // getSupportFragmentManager().putFragment(outState, "myFragmentName", getActivity());

    }

    public boolean isConnected(Activity context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else return false;
    }

    public AlertDialog.Builder buildDialog(Activity c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet connection.");
        builder.setMessage("You have no internet connection");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        return builder;
    }


    @Override
    public void onDataUpdate() {

    }

    @Override
    public void onDataUpdate(String choice ) {



    }

    @Override
    public void onDataUpdate(Object o) {

    }
    @Override
    public void onDataUpdate(Object o , String s) {
    }
}
