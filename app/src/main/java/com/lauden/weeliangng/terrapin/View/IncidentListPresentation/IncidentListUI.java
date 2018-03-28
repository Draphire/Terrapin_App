package com.lauden.weeliangng.terrapin.View.IncidentListPresentation;

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
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lauden.weeliangng.terrapin.Controller.CameraListUIRecyclerView.TitleCameraObjectAdapter;
import com.lauden.weeliangng.terrapin.Controller.IncidentRecyclerView.TitleIncidentObjectAdapter;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleIncidentObject;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleIncidentObject;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.UsageOfCameraDAO.CameraDAOUsage;
import com.lauden.weeliangng.terrapin.Model.IncidentDAOpattern.IncidentDAOImplement;
import com.lauden.weeliangng.terrapin.Model.IncidentDAOpattern.UsageOfIncidentDAO.IncidentDAOUsage;
import com.lauden.weeliangng.terrapin.Model.IncidentFactoryPattern.IncidentObject;
import com.lauden.weeliangng.terrapin.Model.Utility.ConnectionCheck;
import com.lauden.weeliangng.terrapin.Model.Utility.StringParser;
import com.lauden.weeliangng.terrapin.R;
import com.lauden.weeliangng.terrapin.View.MainActivityObserver;
import com.lauden.weeliangng.terrapin.View.MainActivityUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by WeeLiang Ng on 6/3/2018.
 */

public class IncidentListUI extends Fragment implements MainActivityObserver {

    /*
        @Override
        public void update(UpdateData xyzData) {
            // this method will be called for every fragment in viewpager
            // so check if update is for this fragment
         //  if(forMe(xyzData)) {
                // do whatever you want to update your UI
          //  }

        }



    @Override
    public void update(ViewPagerAdapter.UpdateData xyzData) {

    }

    public class UpdateData {
        //whatever you want here
    }
*/

    FragmentActivity listener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivityUI) context).registerDataUpdateListener(this);

        if (context instanceof Activity){
            this.listener = (FragmentActivity) context;
        }

    }


    /*

    public interface CameraListObserver {

        public void update(String data);

    }

*/

    //private ListView lvCamera;
    // private static final List<LTACameraObject> keeplist = new ArrayList<>();
    List<LTACameraObject> mLTACameraObjectList = new ArrayList<>();
    List<TitleIncidentObject> items;
    Boolean _areLecturesLoaded =false;
    private int loadCount = 5;

    //public UpdateData updateData;


    // SwipeRefreshLayout swipeRefreshLayout;
    SwipeRefreshLayout mSwipeRefreshLayout;
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


    private TitleIncidentObjectAdapter mAdapter;

    ConnectionCheck lfyl = new ConnectionCheck(getActivity()); //Here the context is passing
    private String mSearchString;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivityUI) activity).registerDataUpdateListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivityUI) getActivity()).unregisterDataUpdateListener(this);
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


    public static interface DataProxy {
        void sendData(Object... args);
    }
/*
    @NonNull
    public static Intent newIntent(Context context) {
        return new Intent(context, VerticalLinearRecyclerViewSampleActivity.class);
    }
*/



//ViewPager pager;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.incidentlistui,container,false);
        setHasOptionsMenu(true);

      //  mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);


        super.onActivityCreated(savedInstanceState);


        if(!isConnected(getActivity())) buildDialog(getActivity()).show();
        else {

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().
                    getReference("trafficjson").child("data0");
            //addListenerForSingleValueEvent()
            // addValueEventListener
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    View v =inflater.inflate(R.layout.cameralistui,container,false);
//                    setHasOptionsMenu(true);


                    Log.d(TAG, "onDataChange() called");

                    List<IncidentObject> incilist2 = new ArrayList<>();


                    //clearing the previous artist list
                    // incidentlist.clear();
                    //iterating through all the nodes
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //getting artist
                        //    IncidentObject indicident= postSnapshot.getValue(IncidentObject.class);
                        //adding artist to the list
                        String displayName = dataSnapshot.getValue().toString();
//                    Log.d("Incident Fire", dataSnapshot.getValue(String.class));
                        System.out.println(displayName);
                        Log.d(TAG, " Tried get from Fire");
                        IncidentObject incident = postSnapshot.getValue(IncidentObject.class);
                     //   System.out.println(incident.getLongitude());

                        if (incident.getType() != null) {
                            if (incident.getType().equalsIgnoreCase("Roadwork")) {

                            } else {
                                incilist2.add(incident);
                            }
                            //  incilist2.add(incident);

//                        System.out.println("Inci List Try" +
//                                incilist2.get(0).getType());

                            //    Log.d("incident = ", incident.getMessage());
                            //  incidentlist.add(indicident);
                            //     System.out.println("Test get Data Out" + incilist2.get(0).getMessage());
                            Log.d("Damincident = ", incident.getMessage());
                        }
                    }

                    /////////////////////////////////////////////////////////////////////////////////////


                  //  List<IncidentObject> incilist2 = IncidentDAOImplement.getIncidentList();



                    // my loop for populating entire Expandable Camera List View with relevant information and image
                    final List<TitleIncidentObject> items = new ArrayList<>();
                    int j = 0;

                    for(int i=0; i<incilist2.size();i++){

                        //String example = "/abc/def/ghfj.doc";
                      //  String Incidenttitle = incilist2.get(i).getMessage().substring(example.lastIndexOf("on") + 1);
//                        String Incidenttitle = StringParser.between(incilist2.get(i).getMessage()," ",")")+")";
//                        if(Incidenttitle.equals(")")){
//                            Incidenttitle = StringParser.between(incilist2.get(i).getMessage()," ",".");
//
//                        }
                        String Incidenttitle = StringParser.between(incilist2.get(i).getMessage(),"on",".");
                        if(Incidenttitle.isEmpty()){
                           Incidenttitle = StringParser.between(incilist2.get(i).getMessage(),"in",".");

                        }
                        if(Incidenttitle.isEmpty()){
                            Incidenttitle = incilist2.get(i).getMessage();
                        }

                        System.out.println(Incidenttitle);

                        TitleIncidentObject kpe1 = new TitleIncidentObject(Incidenttitle, Arrays.asList(incilist2.get(j)));
                        j++;

                    //    if(incilist2.get(j).getType()!="Road Works")
                       items.add(kpe1);
                    }


                    ///////////////////////////////////////////////////////////////

                    //  items = IncidentDAOUsage.IncidentDAOgetTitleObjectList();
                    //CameraDAOUsage.CameraDAOgetTitleObjectList(getActivity());

                    if (getContext() != null) {
// Code goes here.

                        mAdapter = new TitleIncidentObjectAdapter(getContext(), items);
                    }

                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


                                mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {

                @UiThread
                @Override
                public void onParentExpanded(int parentPosition) {

                    TitleIncidentObject expandedRecipe = items.get(parentPosition);
                    for (int i = 0; i < mAdapter.getParentList().size(); i++) {
                        if (i != parentPosition) {
                            mAdapter.collapseParent(i);
                        }

                    }
                    // String toastMsg = getResources().getString(R.string.expanded, expandedRecipe.getName());
                  /*  Toast.makeText(getActivity(),
                            toastMsg,
                            Toast.LENGTH_SHORT)
                            .show();
                */
                }

                @UiThread
                @Override
                public void onParentCollapsed(int parentPosition) {
                    TitleIncidentObject collapsedRecipe = items.get(parentPosition);

                    // String toastMsg = getResources().getString(R.string.collapsed, collapsedRecipe.getName());
                   /* Toast.makeText(getActivity(),
                            toastMsg,
                            Toast.LENGTH_SHORT)
                            .show();
              */
                }
            });

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });



//            AdView mAdView = (AdView) v.findViewById(R.id.adView2);
//
//            Log.d("CameraList", " Revived");
//            final View rootV = v;
//
//            MobileAds.initialize(getActivity(),
//                    "ca-app-pub-7220416147687992~6211443619");
//            AdRequest adRequest = new AdRequest.Builder().build();
//            mAdView.loadAd(adRequest);
//            mAdView.setAdListener(new AdListener() {
//                @Override
//                public void onAdLoaded() {
//                    // Code to be executed when an ad finishes loading.
//                    Log.i("Ads", "onAdLoaded");
//                    LinearLayout adContainer = (LinearLayout) rootV.findViewById(R.id.adCamSizing);
//                    LinearLayout listContainer = (LinearLayout) rootV.findViewById(R.id.listContain);
//
//
//                    int heightPixels = AdSize.SMART_BANNER.getHeightInPixels(getActivity());
//                    Log.i("adsSize", String.valueOf(heightPixels));
//                    LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) adContainer.getLayoutParams();
//                    LinearLayout.LayoutParams paramsTop = (android.widget.LinearLayout.LayoutParams) listContainer.getLayoutParams();
//
//// Changes the height and width to the specified *pixels*
//                    params.height = heightPixels;
//                    //  params.height = heightPixels;
//                    paramsTop.topMargin=heightPixels;
//                    // params.width = 100;
//                    listContainer.setLayoutParams(paramsTop);
//                    adContainer.setLayoutParams(params);
//
//                }
//
//
//                @Override
//                public void onAdFailedToLoad(int errorCode) {
//                    // Code to be executed when an ad request fails.
//                    Log.i("Ads", "onAdFailedToLoad");
//
//                    LinearLayout adContainer = (LinearLayout) rootV.findViewById(R.id.adCamSizing);
//                    LinearLayout listContainer = (LinearLayout) rootV.findViewById(R.id.listContain);
//
//
//                    LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) adContainer.getLayoutParams();
//                    LinearLayout.LayoutParams paramsTop = (android.widget.LinearLayout.LayoutParams) listContainer.getLayoutParams();
//
//                    params.height = 0;
//                    //  params.height = heightPixels;
//                    paramsTop.topMargin=0;
//                    listContainer.setLayoutParams(paramsTop);
//                    adContainer.setLayoutParams(params);
//
//
//
//                }
//
//                @Override
//                public void onAdOpened() {
//                    // Code to be executed when an ad opens an overlay that
//                    // covers the screen.
//                    Log.i("Ads", "onAdOpened");
//                }
//
//                @Override
//                public void onAdLeftApplication() {
//                    // Code to be executed when the user has left the app.
//                    Log.i("Ads", "onAdLeftApplication");
//                }
//
//                @Override
//                public void onAdClosed() {
//                    // Code to be executed when when the user is about to return
//                    // to the app after tapping on an ad.
//                    Log.i("Ads", "onAdClosed");
//                }
//
//            });

           // recyclerView.setAdapter(mAdapter);
           // recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


            if (savedInstanceState == null) {

                //Restore the fragment's state here
                //super.onRestoreInstanceState(savedInstanceState);
                //   recyclerView=(CameraListUIRecyclerView) getActivity().findViewById(R.id.recyclerview);


            } else {


              //  mAdapter.onRestoreInstanceState(savedInstanceState);

//not for now real time firebase will change list dynamic
                Log.d("Adapter", " Restored");
                if(mSearchString!=null)
                    mSearchString = savedInstanceState.getString("SEARCH_KEY");

                //  Log.d("RestoreInstance ListUI","Restored");


            }

//
//            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                    refreshItems();
//                }
//            });
//
//
//            mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
//
//                @UiThread
//                @Override
//                public void onParentExpanded(int parentPosition) {
//
//                    TitleIncidentObject expandedRecipe = items.get(parentPosition);
//                    for (int i = 0; i < mAdapter.getParentList().size(); i++) {
//                        if (i != parentPosition) {
//                            mAdapter.collapseParent(i);
//                        }
//
//                    }
//                    // String toastMsg = getResources().getString(R.string.expanded, expandedRecipe.getName());
//                  /*  Toast.makeText(getActivity(),
//                            toastMsg,
//                            Toast.LENGTH_SHORT)
//                            .show();
//                */
//                }
//
//                @UiThread
//                @Override
//                public void onParentCollapsed(int parentPosition) {
//                    TitleIncidentObject collapsedRecipe = items.get(parentPosition);
//
//                    // String toastMsg = getResources().getString(R.string.collapsed, collapsedRecipe.getName());
//                   /* Toast.makeText(getActivity(),
//                            toastMsg,
//                            Toast.LENGTH_SHORT)
//                            .show();
//              */
//                }
//            });


            // Inflate the layout for this fragment
        }
        return v;


    }



    /*@Override
    public void onResume() {
        super.onResume();
       // mAdapter.swapItems(CameraDAOUsage.CameraDAOgetTitleObjectList());
       // Log.d("onResume","Tried Swap");
    }*/



    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // super.onRestoreInstanceState(savedInstanceState);
     //   mAdapter.onRestoreInstanceState(savedInstanceState);

//not for now real time firebase will change list dynamic
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



         /*
        searchBar = (SearchView) v.findViewById(R.id.searchbar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
              mAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return true;
            }
        });*/



    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

//        if (id == R.id.action_refresh) {
//
//            //List<TitleIncidentObject> tempItem = items;
//            //items = CameraDAOUsage.CameraDAOgetTitleObjectList();
//
//            //  mAdapter.updateItems(items,tempItem);
//            //updating.update("button was clicked");
//            // mAdapter.updateItems(items);
//
//            ConnectivityManager cm =
//                    (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//
//            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//            boolean isConnected = activeNetwork != null &&
//                    activeNetwork.isConnected();
//
//            if(isConnected) {
//                mAdapter.updateItems(getActivity());
//            }else{
//                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//
//            }
//
//            /*
//            for (int j = 0; j < mAdapter.getParentList().size(); j++) {
//                mAdapter.notifyChildRemoved(j, 0);
//                mAdapter.notifyChildInserted(j, 0);
//            }*/
//
//            return true;
//        }
//


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        //recyclerView=(CameraListUIRecyclerView) getActivity().findViewById(R.id.recyclerview);
        // restore CameraListUIRecyclerView state
//        if (mBundleRecyclerViewState != null) {
//            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
//            if(recyclerView!= null)
//                recyclerView.getLayoutManager().onRestoreInstanceState(listState);
//
//not for now real time firebase will change list dynamic
//        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //ConnectionCheck checkConnetion =  new ConnectionCheck(getActivity());

        //  checkConnetion.isNetworkConnectionAvailable();
//        if(mAdapter!=null)
//            mAdapter.onRestoreInstanceState(savedInstanceState);
//not for now real time firebase will change list dynamic

    }

    Parcelable listState;

    @Override
    public void onPause()
    {
        super.onPause();

        // save CameraListUIRecyclerView state
        mBundleRecyclerViewState = new Bundle();

//        if(recyclerView != null) {
//            listState = recyclerView.getLayoutManager().onSaveInstanceState();
//        }

        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }



//    void refreshItems() {
//        // mAdapter.swapItems(items);
//       /* for(int j =0; j<items.size();j++) {
//            mAdapter.notifyChildRemoved(j, 0);
//            mAdapter.notifyChildInserted(j, 0);
//        }*/
//        // List<TitleIncidentObject> tempItem = items;
//        //items = CameraDAOUsage.CameraDAOgetTitleObjectList();
//
//        //  mAdapter.updateItems(items,tempItem);
//
//        mAdapter.updateItems(getActivity());
//
//        onItemsLoadComplete();
//    }

//    void refreshItems(int loadCount) {
//        // mAdapter.swapItems(items);
//
//        for(int j =0; j<loadCount;j++) {
//            mAdapter.notifyChildRemoved(j, j);
//            mAdapter.notifyChildInserted(j, j);
//        }
//
//        onItemsLoadComplete();
//
//    }


//    void onItemsLoadComplete() {
//        // Update the adapter and notify data set changed
//        // ...
//
//
//        // Stop refresh animation
//        mSwipeRefreshLayout.setRefreshing(false);
//
//    }

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






}
