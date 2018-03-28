package com.lauden.weeliangng.terrapin.View.CameraListPresentation.DialogueView;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.UiThread;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lauden.weeliangng.terrapin.Controller.Adapter.SlidingTabLayout;
import com.lauden.weeliangng.terrapin.Controller.Adapter.ViewPagerAdapter;
import com.lauden.weeliangng.terrapin.Controller.CameraListUIRecyclerView.TitleCameraObjectAdapter;
import com.lauden.weeliangng.terrapin.Controller.CameraListUIRecyclerView.TitleCameraObjectAdapter;
import com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern.ListAsyncTaskListener;
import com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern.ListMemberTaskListenerPattern.ListMemberAsyncTaskListener;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleCameraObject;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleCameraObject;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.CameraDAOImplement;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.UsageOfCameraDAO.CameraDAOUsage;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.FireBaseDataObject.CameraListFireBaseObject;
import com.lauden.weeliangng.terrapin.Model.MapUIUtlity.MapMarkerSelection;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CameraSingleton;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CarParkSingletonPattern;
import com.lauden.weeliangng.terrapin.Model.Utility.ConnectionCheck;
import com.lauden.weeliangng.terrapin.Model.Utility.StringParser;
import com.lauden.weeliangng.terrapin.Model.Utility.TimeStampProcessing;
import com.lauden.weeliangng.terrapin.R;
import com.lauden.weeliangng.terrapin.View.MainActivityUI;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ListDialogFragment extends DialogFragment {

    public static String dialoguri;
    private Activity context;
    public static Object camObjhere;
    public List<LTACameraObject> camlist = new ArrayList<>();

    private TitleCameraObjectAdapter mAdapter;

    RecyclerView recyclerView;
    SearchView searchBar;
    SearchView searchView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    ProgressDialog progressDialog;


    List<TitleCameraObject> items = new ArrayList<>();


    //    ConnectionCheck lfyl = new ConnectionCheck(getActivity()); //Here the context is passing
    private String mSearchString;

    CharSequence Titles[] = {"Map", "Camera", "Camera", "Works", "HDB", "Mall"};
    int Numboftabs = 6;


    public LTACameraObject camObjhere2;


    public static ListDialogFragment newInstance() {
        ListDialogFragment f = new ListDialogFragment();
        return f;
    }

//    public static ListDialogFragment newInstance(LTACameraObject camObj) {
//        camObjhere = camObj;
//        dialoguri = camObj.getImage();
//
//        //  MyDialogFragment f = new MyDialogFragment();
//        ListDialogFragment f = new ListDialogFragment();
//
//        return f;
//    }


    public static ListDialogFragment newInstance(LTACameraObject camObj) {
        camObjhere = camObj;
        //dialoguri = camObj.getImage();

        //  MyDialogFragment f = new MyDialogFragment();
        ListDialogFragment f = new ListDialogFragment();

        return f;
    }


    public static ListDialogFragment newInstance(String uri) {
        dialoguri = uri;
        ListDialogFragment f = new ListDialogFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Window window = getDialog().getWindow();
        // window.setBackgroundDrawableResource(android.R.color.transparent);
        Dialog d = getDialog();

        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            d.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialogincidentlistui, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);

        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
     


        //setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Translucent);

//
//        initiator(getActivity());
        context = getActivity();
//        ViewPagerAdapter adapter = new ViewPagerAdapter(((AppCompatActivity) actcontext).getSupportFragmentManager(), Titles, Numboftabs, actcontext);
//
//        // Assigning ViewPager View and setting the adapter
//        ViewPager pager = (ViewPager) context.findViewById(R.id.pager);
//        pager.setAdapter(adapter);
//        pager.setOffscreenPageLimit(5);
//        pager.canScrollHorizontally(3);
//
//        // Assiging the Sliding Tab Layout View
//        SlidingTabLayout tabs = (SlidingTabLayout) context.findViewById(R.id.tabs);
//        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
//
//        // Setting Custom Color for the Scroll bar indicator of the Tab View
//        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//            @Override
//            public int getIndicatorColor(int position) {
//                return getResources().getColor(R.color.tabsScrollColor);
//            }
//        });
//
//        // Setting the ViewPager For the SlidingTabsLayout
//        tabs.setViewPager(pager);


        // FirebaseAuth firebaseAuth = null;
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


        if (firebaseAuth.getCurrentUser() != null) {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("UserCamera").child(user.getUid());
            //addListenerForSingleValueEvent()
            // addValueEventListener
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    View v =inflater.inflate(R.layout.cameralistui,container,false);
//                    setHasOptionsMenu(true);

                    Log.d(TAG, "onDataChange() called");


                    DialogAsynTaskCameraList dialogAsynTaskCameraList = new DialogAsynTaskCameraList(dataSnapshot, context);
                    dialogAsynTaskCameraList.execute();


                    
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }


//
//      SwipeRefreshLayout  mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
//        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
//
//       final List<TitleCameraObject> items = CameraDAOUsage.CameraDAOgetTitleObjectListFirst(getActivity());
//
//
//
//
//
//        final TitleCameraObjectAdapter mAdapter = new TitleCameraObjectAdapter(getActivity(), items);
//
//
//
//        Log.d("CameraList", " Revived");
//        final View rootV = v;
//
//
//        recyclerView.setAdapter(mAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//
//
//
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                //refreshItems();
//            }
//        });
//
//
//        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
//
//            @UiThread
//            @Override
//            public void onParentExpanded(int parentPosition) {
//
//                TitleCameraObject expandedRecipe = items.get(parentPosition);
//                for (int i = 0; i < mAdapter.getParentList().size(); i++) {
//                    if (i != parentPosition) {
//                        mAdapter.collapseParent(i);
//                    }
//
//                }
//                // String toastMsg = getResources().getString(R.string.expanded, expandedRecipe.getName());
//                  /*  Toast.makeText(getActivity(),
//                            toastMsg,
//                            Toast.LENGTH_SHORT)
//                            .show();
//                */
//            }
//
//            @UiThread
//            @Override
//            public void onParentCollapsed(int parentPosition) {
//                TitleCameraObject collapsedRecipe = items.get(parentPosition);
//
//                // String toastMsg = getResources().getString(R.string.collapsed, collapsedRecipe.getName());
//                   /* Toast.makeText(getActivity(),
//                            toastMsg,
//                            Toast.LENGTH_SHORT)
//                            .show();
//              */
//            }
//        });


        // Inflate the layout for this fragment

        return v;

        //return context;
    }


//    public void initiator(Activity act){
//       // setTheme(R.style.AppTheme);
//
//
////
////        if(firebaseAuth.getCurrentUser() != null) {
////            // imgvw.setImageResource(R.drawable.terrapin);
////            FirebaseUser user = firebaseAuth.getCurrentUser();
////            //   signoutin.setText("Sign Out");
////            tv.setText("Welcome Back "+ user.getEmail());
////
////
////
////        }
////        //  signoutin.setOnClickListener(this);
////
////        imgvw.setImageResource(R.drawable.terrapin);
//
//        // View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
//        //headerLayout.findViewById(R.layout.nav_header_main);
//        //NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
//        // View drawerHeader = navigationView.inflateHeaderView(R.layout.nav_header_main);
//        // ImageView headimg = (ImageView) navigationView.findViewById(R.id.imageView);
//        //TextView username = (TextView) drawerHeader.findViewById(R.id.textView);
//
//
//        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
//       ViewPagerAdapter adapter = new ViewPagerAdapter(((AppCompatActivity)context).getSupportFragmentManager(), Titles, Numboftabs, act);
//
//        // Assigning ViewPager View and setting the adapter
//        ViewPager pager = (ViewPager) context.findViewById(R.id.pager);
//        pager.setAdapter(adapter);
//        pager.setOffscreenPageLimit(5);
//        pager.canScrollHorizontally(3);
//
//        // Assiging the Sliding Tab Layout View
//        SlidingTabLayout tabs = (SlidingTabLayout) context.findViewById(R.id.tabs);
//        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
//
//        // Setting Custom Color for the Scroll bar indicator of the Tab View
//        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//            @Override
//            public int getIndicatorColor(int position) {
//                return getResources().getColor(R.color.tabsScrollColor);
//            }
//        });
//
//        // Setting the ViewPager For the SlidingTabsLayout
//        tabs.setViewPager(pager);
//
////        if (progressDialog != null && progressDialog.isShowing()) {
////
////            progressDialog.dismiss();
////        }
//
//
//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .build();
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(((AppCompatActivity)context).getApplicationContext())
//                .defaultDisplayImageOptions(defaultOptions)
//                .build();
//        ImageLoader.getInstance().init(config); // Do it on Application start
//
//    }
//
//
//
//
////
////
////    public class initiatorAsync extends AsyncTask<Void,Void,Void> {
////
////        ListMemberAsyncTaskListener listener;
////
////        HDBCarParkObject mHDBCarParkObject;
////
////
////
////        ProgressDialog progressDialogMall;
////
////        @Override
////        protected void onPostExecute(Void aVoid) {
////            initiator(act);
////
////            progressDialogMall.dismiss();
////
////
////        }
////
////
////        @Override
////        protected Void doInBackground(Void... voids) {
////            CameraSingleton.getNewInstance(act).getArrayList();
////            CarParkSingletonPattern.getInstance(act).getArrayList();
////
////
////            return null;
////        }
////
////        @Override
////        protected void onPreExecute() {
////            // listener.onListMemberAsyncTaskStart();
////
////            progressDialogMall = ProgressDialog.show(act, "Please wait.",
////                    "Initiating App...", true);
////
////        }
////    }


    public class DialogAsynTaskCameraList extends AsyncTask<Void, Void, List<TitleCameraObject>> {

        ListAsyncTaskListener listener;
        Activity mActivity;
        DataSnapshot dataSnapshot;

//        public DialogAsynTaskCameraList(ListAsyncTaskListener listener, Activity activity) {
//            this.listener = listener;
//            this.mActivity = activity;
//        }

        public DialogAsynTaskCameraList(DataSnapshot dataSnapshot, Activity activity) {
            this.dataSnapshot = dataSnapshot;
            this.mActivity = activity;
        }


        @Override
        protected List<TitleCameraObject> doInBackground(Void... voids) {


            List<CameraListFireBaseObject> camlistfirebase = new ArrayList<>();

            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                  String displayName = dataSnapshot.getValue().toString();
                System.out.println(displayName);
                Log.d(TAG, " Tried get from Fire");
                CameraListFireBaseObject firecamera = postSnapshot.getValue(CameraListFireBaseObject.class);
                System.out.println(firecamera.getCameraid());




                camlistfirebase.add(firecamera);
            }

            /////////////////////////////////////////////////////////////////////////////////////

            List<LTACameraObject> camlistforcomparison = CameraSingleton.getNewInstance(context).getArrayList();

            for (CameraListFireBaseObject firecamcomp : camlistfirebase) {

                for (LTACameraObject camcomp : camlistforcomparison) {

                    if (firecamcomp.getCameraid().equals(camcomp.getCamera_id())) {
                        camlist.add(camcomp);
                        System.out.println("fire base for user found " + firecamcomp.getCameraid());


                    }

                }
            }


               final List<TitleCameraObject> items = new ArrayList<>();
            int j = 0;

            for (int i = 0; i < camlist.size(); i++) {

                String Cameratitle = camlist.get(i).getName();


                System.out.println(Cameratitle);
                camlist.get(j).getCamera_id();
                TitleCameraObject kpe1 = new TitleCameraObject(Cameratitle, Arrays.asList(camlist.get(j)));
                j++;

                //    if(camlist.get(j).getType()!="Road Works")
                items.add(kpe1);

            }


            return items;
        }


        @Override
        protected void onPostExecute(List<TitleCameraObject> CamObjects) {
            setUpDialogList(CamObjects);
            // listener.CameraObjectList(CamObjects);
            // progressDialogMall.dismiss();


        }

        @Override
        protected void onPreExecute() {

            //  listener.onListAsyncTaskStart();
            progressDialog = ProgressDialog.show(getContext(), "Please wait.",
                    "Retrieving your List...", true);

        }
    }


    public void setUpDialogList(final List<TitleCameraObject> camObjhere) {

        items = camObjhere;

        ///////////////////////////////////////////////////////////////

        //  items = CameraDAOUsage.CameraDAOgetTitleObjectList();
        //CameraDAOUsage.CameraDAOgetTitleObjectList(getActivity());


        //if(mAdapter != null)

        if( mSwipeRefreshLayout.isRefreshing()){
            List<TitleCameraObject> TitleCameraObject = CameraDAOUsage.CameraDAOgetTitleObjectList(context);

          //  mAdapter = new TitleCameraObjectAdapter(context, items);

            mAdapter.updateItems(TitleCameraObject);

//            mSwipeRefreshLayout.setRefreshing(false);
        }else {
            mAdapter = new TitleCameraObjectAdapter(context, items);


            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


       }


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DialogAsynTaskRefreshCameraList dialogAsynTaskRefreshCameraList = new DialogAsynTaskRefreshCameraList(camObjhere, context);
                dialogAsynTaskRefreshCameraList.execute();
            }
        });


        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {

            @UiThread
            @Override
            public void onParentExpanded(int parentPosition) {

                TitleCameraObject expandedRecipe = items.get(parentPosition);
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
                TitleCameraObject collapsedRecipe = items.get(parentPosition);

                // String toastMsg = getResources().getString(R.string.collapsed, collapsedRecipe.getName());
                   /* Toast.makeText(getActivity(),
                            toastMsg,
                            Toast.LENGTH_SHORT)
                            .show();
              */
            }
        });

        if( mSwipeRefreshLayout.isRefreshing()){

            mSwipeRefreshLayout.setRefreshing(false);
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        }


    public class DialogAsynTaskRefreshCameraList extends AsyncTask<Void, Void, List<TitleCameraObject>> {

        //  ListAsyncTaskListener listener;
        Activity mActivity;
        DataSnapshot dataSnapshot;
        List<TitleCameraObject> mlist;


        public DialogAsynTaskRefreshCameraList(List<TitleCameraObject> list, Activity activity) {
            this.mlist = list;
            this.mActivity = activity;
        }

//        public DialogAsynTaskRefreshCameraList(DataSnapshot dataSnapshot, Activity activity) {
//            this.dataSnapshot = dataSnapshot;
//            this.mActivity = activity;
//        }


        @Override
        protected List<TitleCameraObject> doInBackground(Void... voids) {


            List<CameraListFireBaseObject> camlistfirebase = new ArrayList<>();


            /////////////////////////////////////////////////////////////////////////////////////

            List<LTACameraObject> camlistforcomparison = CameraSingleton.getNewInstance(context).getArrayList();

            for (TitleCameraObject firecamcomp : mlist) {

                for (LTACameraObject camcomp : camlistforcomparison) {

                    if (firecamcomp.getChildList().get(0).getCamera_id().equals(camcomp.getCamera_id())) {
                        camlist.add(camcomp);
                        // System.out.println("fire base for user found " + firecamcomp.getCameraid());


                    }

                }
            }


            final List<TitleCameraObject> items = new ArrayList<>();
            int j = 0;

            for (int i = 0; i < camlist.size(); i++) {

                String Cameratitle = camlist.get(i).getName();


                System.out.println(Cameratitle);
                camlist.get(j).getCamera_id();
                TitleCameraObject kpe1 = new TitleCameraObject(Cameratitle, Arrays.asList(camlist.get(j)));
                j++;

                //    if(camlist.get(j).getType()!="Road Works")
                items.add(kpe1);

            }


            return items;
        }


        @Override
        protected void onPostExecute(List<TitleCameraObject> CamObjects) {
            setUpDialogList(CamObjects);
            // listener.CameraObjectList(CamObjects);
            // progressDialogMall.dismiss();


        }

        @Override
        protected void onPreExecute() {

            //  listener.onListAsyncTaskStart();
//            progressDialog = ProgressDialog.show(getContext(), "Please wait.",
//                    "Updating your List...", true);

        }
    }



//
//    public void onDialogListAsyncTaskStart() {
//        if( mSwipeRefreshLayout.isRefreshing()){
//
//        }else{
//            if (progressDialog != null && progressDialog.isShowing()) {
//                progressDialog.dismiss();
//            }
//            progressDialog = ProgressDialog.show(getContext(), "Please wait.",
//                    "Updating your List...");
//        }
//
//    }


    }

