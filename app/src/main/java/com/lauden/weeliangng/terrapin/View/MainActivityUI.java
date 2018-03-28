package com.lauden.weeliangng.terrapin.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lauden.weeliangng.terrapin.Controller.Adapter.SlidingTabLayout;
import com.lauden.weeliangng.terrapin.Controller.Adapter.ViewPagerAdapter;
import com.lauden.weeliangng.terrapin.Controller.CameraListUIRecyclerView.TitleCameraObjectAdapter;
import com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern.ListMemberTaskListenerPattern.ListMemberAsyncTaskListener;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.Parent;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CarParkDAOpattern.HDBCarParkAPIDAOImplement;
import com.lauden.weeliangng.terrapin.Model.FireBaseDataObject.CameraListFireBaseObject;
import com.lauden.weeliangng.terrapin.Model.IncidentFactoryPattern.IncidentObject;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CameraSingleton;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CarParkSingletonPattern;
import com.lauden.weeliangng.terrapin.Model.Utility.ConnectionCheck;
import com.lauden.weeliangng.terrapin.R;
import com.lauden.weeliangng.terrapin.View.CameraListPresentation.DialogueView.ImageDialogFragment;
import com.lauden.weeliangng.terrapin.View.CameraListPresentation.DialogueView.ListDialogFragment;
import com.lauden.weeliangng.terrapin.View.CameraListPresentation.DialogueView.MyDialogFragment;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MainActivityUI extends AppCompatActivity implements MyDialogFragment.YesNoListener, PlaceAutocompleteAdapter.PlaceAutoCompleteInterface, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {



    private static final String TAG = "GovAnalysis";

    private Bundle savedState;
    private String storedate;
    private static final String SAVED_BUNDLE_TAG = "saved_bundle";

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Map","Camera","Incident", "Works","HDB","Mall"};
    int Numboftabs = 6;

    private static final String STR_CHECKED = " has Checked!";
    private static final String STR_UNCHECKED = " has unChecked!";
    private int ParentClickStatus=-1;
    private int ChildClickStatus=-1;
    private ArrayList<Parent> parents;
    private Fragment mContent;
    private TitleCameraObjectAdapter mAdapter;
    private static Context mContext;
   // public static ProgressBar spinner;
    private List<MainActivityObserver> mListeners = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    DrawerLayout drawer;

    private TextView textViewSignin;
    private Button signoutin;
    private Button mylist;
    initiatorAsync startapp;

   // private ProgressDialog progressDialog;



    //Registering/Subscribing Fragments into Observer Pattern
    public synchronized void registerDataUpdateListener(MainActivityObserver listener) {
        mListeners.add(listener);
    }

    public synchronized void unregisterDataUpdateListener(MainActivityObserver listener) {
        mListeners.remove(listener);
    }


    //Passes Update to all Subcriber Fragments using Observer Pattern without passing object (future use)
    public synchronized void dataUpdated() {
        for (MainActivityObserver listener : mListeners) {
            listener.onDataUpdate();

             }
    }

    //Passes Update to all Subcriber Fragments using Observer Pattern
    public synchronized void dataUpdated(String s) {
        for (MainActivityObserver listener : mListeners) {
            listener.onDataUpdate(s);

        }
    }


    //Passes Update to all Subcriber Fragments using Observer Pattern
    public synchronized void dataUpdated(Object o) {
        for (MainActivityObserver listener : mListeners) {
            listener.onDataUpdate(o);

        }
    }

    public synchronized void dataUpdated(Object o,String s) {
        for (MainActivityObserver listener : mListeners) {
            listener.onDataUpdate(o,s);

        }
    }


    final ConnectionCheck checkConnection = new ConnectionCheck(this);

     Activity act = this;


    public static Context getContext(){
        return mContext;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //firebaseAuth = FirebaseAuth.getInstance();

        if (savedInstanceState != null) {

        }

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            //Extra bundle is null


            // checkConnection.checkNetworkConnection();
            //  checkConnection.isNetworkConnectionAvailable();

            firebaseAuth = FirebaseAuth.getInstance();

            act = this;


            if (!checkConnection.isNetworkConnectionAvailableOnly()) {
                // checkConnection.checkNetworkConnection();
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
                //final AlertDialog alertDialog = builder.create();
                builder.setTitle("Connection Failed");
                builder.setMessage("No Internet Connection Detected");
                builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (!checkConnection.isNetworkConnectionAvailableOnly()) {
                            checkNetworkConnection(act);
                            //alertDialog.show();
                        }
                        //   dialog.show();
                        else {
                            //dialog.dismiss();
                            if (firebaseAuth.getCurrentUser() != null) {
                              //  initiator(act);
//                                initiatorAsync startapp = new  initiatorAsync();
//                                startapp.execute();

                                if(startapp == null)
                                    startapp = new  initiatorAsync();
                                startapp.execute();

                            } else {
                                LoginScreen(act);
                            }

                        }


                    }
                });
                AlertDialog alertDialog = builder.create();
                builder.create();
                alertDialog.show();

            } else {
                if (firebaseAuth.getCurrentUser() != null) {
              //      initiator(act);

                    if(startapp == null)
                    startapp = new  initiatorAsync();
                    startapp.execute();

                } else {
                    LoginScreen(act);
                }

                //  initiator(act);
            }


        } else {
            String method = extras.getString("methodName");

            if (method != null && method.equals("myMethod")) {
                //Call method here!
              //  extras.clear();
                firebaseAuth = FirebaseAuth.getInstance();

                progressDialog = ProgressDialog.show(this, "Please wait.",   "Skipping Log In...");

                       // initiator(this);
//
//                initiatorAsync startapp = new  initiatorAsync();
//                startapp.execute();

                if(startapp == null)
                    startapp = new  initiatorAsync();
                startapp.execute();

            }
        }
    }

    public void checkNetworkConnection(final Activity act) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.Theme_AppCompat_Dialog_Alert);
        builder.setTitle("Connection Failed");
        builder.setMessage("No Internet Connection Detected");
        builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(!checkConnection.isNetworkConnectionAvailableOnly()){
                    checkNetworkConnection(act);
                }
                //   dialog.show();
                else
                {
                   //initiator(act);
                    if(firebaseAuth.getCurrentUser() != null) {
                      //  initiator(act);
//                        initiatorAsync startapp = new  initiatorAsync();
//                        startapp.execute();
                        if(startapp == null)
                            startapp = new  initiatorAsync();
                        startapp.execute();

                    }else{
                        LoginScreen(act);
                    }
                }




            }
        });
        AlertDialog alertDialog = builder.create();
        builder.create();
        alertDialog.show();

        // new MyDialogFragment().show(((((AppCompatActivity) mContext).getFragmentManager())), "tag");

    }



    public void initiator(Activity act){
        setTheme(R.style.AppTheme);


        MobileAds.initialize(act, "ca-app-pub-7220416147687992~6211443619");


           Log.d(TAG, "onCreate(Bundle) called");

           setContentView(R.layout.activity_main);

           //spinner = (ProgressBar) findViewById(R.id.progressBar1);

           //spinner.setVisibility(View.GONE);

           // Creating The Toolbar and setting it as the Toolbar for the activity

           toolbar = (Toolbar) findViewById(R.id.tool_bar);
           setSupportActionBar(toolbar);
      //  getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        //getSupportActionBar().setLogo(R.drawable.terrapinpath);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

       NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    //  menu navigationVie = (menu) navigationView.findViewById(R.id.mylist) ;
       // NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //View hView =  navigationView.getHeaderView(0);
        //TextView nav_user = (TextView)hView.findViewById(R.id.nav_name);
        //nav_user.setText(user);
        navigationView.inflateMenu(R.menu.activity_main_drawer);
        View hView =  navigationView.inflateHeaderView(R.layout.nav_header_main);
       // View hView = navigationView.getHeaderView(0);

        ImageView imgvw = (ImageView)hView.findViewById(R.id.imageView);
        TextView tv = (TextView)hView.findViewById(R.id.textView);
      //  Button mylist = (Button) hView.findViewById(R.id.mylist);
//        Button signoutin = (Button) hView.findViewById(R.id.signoutin);

          //  signoutin.setOnClickListener(this);
       // LinearLayout header = (LinearLayout) hView.findViewById(R.id.signbuttonlayout);

        //  LinearLayout header = (LinearLayout) hView.findViewById(R.id.);
//        header.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivityUI.this, "clicked", Toast.LENGTH_SHORT).show();
//               // drawer.closeDrawer(GravityCompat.START);
//                firebaseAuth.signOut();
//                //closing activity
//                finish();
//                //starting login activity
//                startActivity(new Intent(getContext(), LoginActivity.class));
//
//            }
//        });


        if(firebaseAuth.getCurrentUser() != null) {
            // imgvw.setImageResource(R.drawable.terrapin);
            FirebaseUser user = firebaseAuth.getCurrentUser();
         //   signoutin.setText("Sign Out");
            tv.setText("Welcome Back "+ user.getEmail());



        }
      //  signoutin.setOnClickListener(this);

        imgvw.setImageResource(R.drawable.terrapin);

       // View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        //headerLayout.findViewById(R.layout.nav_header_main);
        //NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
      // View drawerHeader = navigationView.inflateHeaderView(R.layout.nav_header_main);
       // ImageView headimg = (ImageView) navigationView.findViewById(R.id.imageView);
        //TextView username = (TextView) drawerHeader.findViewById(R.id.textView);


           // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
           adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs, act);

           // Assigning ViewPager View and setting the adapt er
        if(pager ==null) {
            pager = (ViewPager) findViewById(R.id.pager);
            pager.setAdapter(adapter);
            pager.setOffscreenPageLimit(7);
            pager.canScrollHorizontally(3);
        }

           // Assiging the Sliding Tab Layout View
           tabs = (SlidingTabLayout) findViewById(R.id.tabs);
           tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

           // Setting Custom Color for the Scroll bar indicator of the Tab View
           tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
               @Override
               public int getIndicatorColor(int position) {
                   return getResources().getColor(R.color.tabsScrollColor);
               }
           });

           // Setting the ViewPager For the SlidingTabsLayout
           tabs.setViewPager(pager);

        if (progressDialog != null && progressDialog.isShowing()) {

            progressDialog.dismiss();
        }


           DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()

                   .cacheInMemory(true)
                   .cacheOnDisk(true)
                   .build();
           ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                   .defaultDisplayImageOptions(defaultOptions)
                   .build();
           ImageLoader.getInstance().init(config); // Do it on Application start

       }


    public ViewPager getPager(){
        return pager;
    }

    GoogleApiClient mGoogleApiClient;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_roadincident) {
            // Handle the camera action
            getPager().setCurrentItem(0);
            dataUpdated("incident");


        } else if (id == R.id.nav_trafficcam) {
            // Handle the camera action
            getPager().setCurrentItem(0);
            dataUpdated("camera");


        }else if (id == R.id.nav_roadwork) {
            // Handle the camera action
            getPager().setCurrentItem(0);
            dataUpdated("roadwork");


        } else if (id == R.id.nav_mallcp) {
            getPager().setCurrentItem(0);
            dataUpdated("mallcp");

        } else if (id == R.id.nav_nearbyhdb) {
            getPager().setCurrentItem(0);
            dataUpdated("hdbcp");

        } else if (id == R.id.cameralist) {
            getPager().setCurrentItem(1);
        } else if (id == R.id.incidentlist) {
            getPager().setCurrentItem(2);

        }else if (id == R.id.roadworklist) {
            getPager().setCurrentItem(3);

        }else if (id == R.id.hdblist) {
            getPager().setCurrentItem(4);

        }
        else if (id == R.id.mylist) {
            if(firebaseAuth.getCurrentUser() !=null){

                new ListDialogFragment().newInstance().show(((getFragmentManager())), "my-dialog");

//                LTACameraObject ltaCameraObject = new LTACameraObject();
//                new ImageDialogFragment().newInstance(ltaCameraObject).show(((getFragmentManager())), "my-dialog");



            }else{
                Toast.makeText(MainActivityUI.this, "Log In for My List Features", Toast.LENGTH_SHORT).show();
//
            }
          //  getPager().setCurrentItem(4);

        }
        else if (id == R.id.signinout) {
            if(firebaseAuth.getCurrentUser() !=null){
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(this, LoginActivity.class));

            }else {
                //starting login activity
                finish();

                startActivity(new Intent(this, LoginActivity.class));

            }
            //  getPager().setCurrentItem(4);

        }
        else if (id == R.id.malllist) {
           getPager().setCurrentItem(5);

//            firebaseAuth.signOut();
//            //closing activity
//            finish();
//            //starting login activity
//            startActivity(new Intent(this, LoginActivity.class));


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
      //  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }




    @Override
    public void onPlaceClick(ArrayList<PlaceAutocompleteAdapter.PlaceAutocomplete> mResultList, int position) {
      //  Toast.makeText(getApplicationContext(),"Result press ",Toast.LENGTH_SHORT).show();

        if(mResultList!=null){
            final String placeId = String.valueOf(mResultList.get(position).placeId);

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            Toast.makeText(getApplicationContext(),"Result press "+placeResult, Toast.LENGTH_SHORT).show();


            try {
            //    final String placeId = String.valueOf(mResultList.get(position).placeId);
                        /*
                             Issue a request to the Places Geo Data API to retrieve a Place object with additional details about the place.
                         */

                //PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                  //      .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if(places.getCount()==1){
                            //Do the things here on Click.....
                            Intent data = new Intent();
                            data.putExtra("lat", String.valueOf(places.get(0).getLatLng().latitude));
                            data.putExtra("lng", String.valueOf(places.get(0).getLatLng().longitude));
                            Toast.makeText(getApplicationContext(),"Result press ", Toast.LENGTH_SHORT).show();

                            //setResult(SearchActivity.RESULT_OK, data);
                            // rootV.finish();
                        }else {

                            Toast.makeText(getApplicationContext(),"something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            catch (Exception e){

            }

        }
    }

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private ProgressDialog progressDialog;
    private TextView skipsignin;
   // private TextView textViewSignin;


    public void LoginScreen(Activity act){
        setTheme(R.style.AppTheme);


        MobileAds.initialize(act, "ca-app-pub-7220416147687992~6211443619");


        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.registerscreen);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);


        buttonSignup = (Button) findViewById(R.id.buttonSignup);


        progressDialog = new ProgressDialog(this);
        skipsignin = (TextView) findViewById(R.id.textViewSkipSignin);

        skipsignin.setOnClickListener(this);
        //attaching listener to button
        buttonSignup.setOnClickListener(this);

        textViewSignin.setOnClickListener(this);

    }


    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(act,"Successfully registered",Toast.LENGTH_LONG).show();



//                            DatabaseReference databaseUserCameraInit = FirebaseDatabase.getInstance().getReference("UserCamera");
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                            String key = databaseUserCameraInit.push().getKey();
//                            CameraListFireBaseObject firebaseobjcam = new CameraListFireBaseObject(key,"FirstEntry","FirstEntry");
//
//
//
//                            databaseUserCameraInit.child(user.getUid()).child(key).setValue(firebaseobjcam);


                            progressDialog.dismiss();
                          //  initiator(act);
//                            initiatorAsync startapp = new  initiatorAsync();
//                            startapp.execute();
                            if(startapp == null)
                                startapp = new  initiatorAsync();
                            startapp.execute();

                        }else{
                            //display some message here
                            Toast.makeText(act,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {

        if(view == buttonSignup){
            // Check if no view has focus:
            View viewfocus = this.getCurrentFocus();
            if (viewfocus != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(viewfocus.getWindowToken(), 0);
            }
            registerUser();
        }

        if(view == textViewSignin){
            //open login activity when user taps on the already registered textview
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(view == skipsignin){
            //open login activity when user taps on the already registered textview
           // startActivity(new Intent(this, LoginActivity.class));
//            initiatorAsync startapp = new  initiatorAsync();
//           startapp.execute();

            if(startapp == null)
                startapp = new  initiatorAsync();
            startapp.execute();

           // initiator(this);
        }



//        if(view == signoutin){
//            //logging out the user
//            firebaseAuth.signOut();
//            //closing activity
//            finish();
//            //starting login activity
//            startActivity(new Intent(this, LoginActivity.class));
//
//            if(firebaseAuth.getCurrentUser() != null) {
//                firebaseAuth.signOut();
//                //closing activity
//                finish();
//                //starting login activity
//                startActivity(new Intent(this, LoginActivity.class));
//            }else{
//                finish();
//                startActivity(new Intent(this, LoginActivity.class));
//            }
//        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getStringExtra("methodName").equals("myMethod")){
           // initiator(act);
//            initiatorAsync startapp = new  initiatorAsync();
//            startapp.execute();

            if(startapp == null)
                startapp = new  initiatorAsync();
            startapp.execute();

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public class UpdateData {
        //whatever you want here
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
      //  mAdapter.onSaveInstanceState(outState);
       // mContent = getSupportFragmentManager().getFragment(savedInstanceState, "CameraListUI");
       // getSupportFragmentManager().putFragment(outState, "cameraListView", adapter.getItem(0));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
       //mAdapter.onRestoreInstanceState(savedInstanceState);
    }


    LTACameraObject camob = new LTACameraObject();




   // DatabaseReference mDatabasechild = mDatabase.child("trafficjson").child("value").child("Latitude"); // this gives you object that has long,lat,message,type traffic
     //  mDatabasechild.getDatabase();


    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart() called");

       // IncidentObject incident = new IncidentObject();

        //String lat = incident.getLatitude();
/*
        final List<IncidentObject> incidentlist = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().
                getReference("trafficjson").child("data");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange() called");
                //clearing the previous artist list
               incidentlist.clear();
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
                   System.out.println(incident.getMessage() );
                  incidentlist.add(incident);

                    //    Log.d("incident = ", incident.getMessage());
                  //  incidentlist.add(indicident);
                }
                //creating adapter
              //  ArtistList artistAdapter = new ArtistList(MainActivity.this, artists);
                //attaching adapter to the listview
               // listViewArtists.setAdapter(artistAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        */
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop() {

        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


    @Override
    public void onYes() {

    }

    @Override
    public void onNo() {

    }



    public class initiatorAsync extends AsyncTask<Void,Void,Void> {

        ListMemberAsyncTaskListener listener;

        HDBCarParkObject mHDBCarParkObject;



        ProgressDialog progressDialogMall;

        @Override
        protected void onPostExecute(Void aVoid) {

            initiator(act);

            progressDialogMall.dismiss();


        }


        @Override
        protected Void doInBackground(Void... voids) {
            CameraSingleton.getNewInstance(act).getArrayList();
            CarParkSingletonPattern.getInstance(act).getArrayList();


            return null;
        }

        @Override
        protected void onPreExecute() {
            // listener.onListMemberAsyncTaskStart();

            progressDialogMall = ProgressDialog.show(act, "Please wait.",
                    "Initiating App...", true);

        }
    }

    public void ListSaver(Object o){



//        Artist artist = new Artist(id, name, genre);
//        databaseReference.child(id).setValue(artist);


        if(o instanceof LTACameraObject){
            LTACameraObject camobj = (LTACameraObject) o;
            FirebaseUser user = firebaseAuth.getCurrentUser();


//           DatabaseReference databaseUserCamera = FirebaseDatabase.getInstance().getReference();
//            String usercam = "UserCamera";
            DatabaseReference databaseUserCamera = FirebaseDatabase.getInstance().getReference("UserCamera").child(user.getUid());


         // String key = databaseUserCamera.push().getKey();
            CameraListFireBaseObject firebaseobjcam = new CameraListFireBaseObject(camobj.getCamera_id(),camobj.getName());


            databaseUserCamera.child(camobj.getCamera_id()).setValue(firebaseobjcam);


//            DatabaseReference databaseUserCamera = FirebaseDatabase.getInstance().getReference("UserCamera");
//            databaseUserCamera.setValue(null);


//
//
//            DatabaseReference databaseUserIncident = FirebaseDatabase.getInstance().getReference("UserCamera").child(user.getUid());
//
//
//           // String key = databaseUserIncident.push().getKey();
//            CameraListFireBaseObject firebaseobjcam = new CameraListFireBaseObject(key,camobj.getCamera_id(),camobj.getName());
//
//
//            databaseUserCamera.child(camobj.getCamera_id()).setValue(firebaseobjcam);


        } else if(o instanceof IncidentObject){
            IncidentObject camobj = (IncidentObject) o;
            FirebaseUser user = firebaseAuth.getCurrentUser();


//           DatabaseReference databaseUserCamera = FirebaseDatabase.getInstance().getReference();
//            String usercam = "UserCamera";
            DatabaseReference databaseUserCamera = FirebaseDatabase.getInstance().getReference("UserIncidentArchive").child(user.getUid());


             String key = databaseUserCamera.push().getKey();
           IncidentObject firebaseobjincident = new IncidentObject(key,camobj.getMessage(),camobj.getType(),camobj.getLatitude(),camobj.getLongitude());


            databaseUserCamera.child(key).setValue(firebaseobjincident);


//
//
//            DatabaseReference databaseUserIncident = FirebaseDatabase.getInstance().getReference("UserCamera").child(user.getUid());
//
//
//           // String key = databaseUserIncident.push().getKey();
//            CameraListFireBaseObject firebaseobjcam = new CameraListFireBaseObject(key,camobj.getCamera_id(),camobj.getName());
//
//
//            databaseUserCamera.child(camobj.getCamera_id()).setValue(firebaseobjcam);



        }


    }

    public void ListDeleter(Object o){



        if(o instanceof LTACameraObject){
            LTACameraObject camobj = (LTACameraObject) o;
            FirebaseUser user = firebaseAuth.getCurrentUser();


            DatabaseReference databaseUserCamera = FirebaseDatabase.getInstance().getReference("UserCamera").child(user.getUid());


//            CameraListFireBaseObject firebaseobjcam = new CameraListFireBaseObject(camobj.getCamera_id(),camobj.getName());
//
//
         databaseUserCamera.child(camobj.getCamera_id()).setValue(null);

//            CameraListFireBaseObject firebaseobjcam = new CameraListFireBaseObject(camobj.getCamera_id(),camobj.getName());
//
//
//            databaseUserCamera.child(camobj.getCamera_id()).removeValue(firebaseobjcam);



        } else if(o instanceof IncidentObject){
            IncidentObject camobj = (IncidentObject) o;
            FirebaseUser user = firebaseAuth.getCurrentUser();


//           DatabaseReference databaseUserCamera = FirebaseDatabase.getInstance().getReference();
//            String usercam = "UserCamera";
            DatabaseReference databaseUserCamera = FirebaseDatabase.getInstance().getReference("UserIncidentArchive").child(user.getUid());


            String key = databaseUserCamera.push().getKey();
            IncidentObject firebaseobjincident = new IncidentObject(key,camobj.getMessage(),camobj.getType(),camobj.getLatitude(),camobj.getLongitude());


            databaseUserCamera.child(key).setValue(firebaseobjincident);


//
//
//            DatabaseReference databaseUserIncident = FirebaseDatabase.getInstance().getReference("UserCamera").child(user.getUid());
//
//
//           // String key = databaseUserIncident.push().getKey();
//            CameraListFireBaseObject firebaseobjcam = new CameraListFireBaseObject(key,camobj.getCamera_id(),camobj.getName());
//
//
//            databaseUserCamera.child(camobj.getCamera_id()).setValue(firebaseobjcam);



        }


    }

}
