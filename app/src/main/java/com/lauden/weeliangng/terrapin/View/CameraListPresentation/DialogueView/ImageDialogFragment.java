package com.lauden.weeliangng.terrapin.View.CameraListPresentation.DialogueView;

import android.Manifest;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern.ListAsyncTaskListener;
import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleCameraObject;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.CameraDAO;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CameraDAOpattern.CameraDAOImplement;
import com.lauden.weeliangng.terrapin.Model.FireBaseDataObject.CameraListFireBaseObject;
import com.lauden.weeliangng.terrapin.Model.IncidentFactoryPattern.IncidentObject;
import com.lauden.weeliangng.terrapin.Model.MapUIUtlity.MapMarkerSelection;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CameraSingleton;
import com.lauden.weeliangng.terrapin.Model.SingletonPattern.CarParkSingletonPattern;
import com.lauden.weeliangng.terrapin.Model.Utility.StringParser;
import com.lauden.weeliangng.terrapin.Model.Utility.TimeStampProcessing;
import com.lauden.weeliangng.terrapin.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.lauden.weeliangng.terrapin.View.MainActivityUI;
import com.lauden.weeliangng.terrapin.View.MapPresentation.MapUI;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ImageDialogFragment extends DialogFragment {

   public static String dialoguri;
    private Activity context;
    public static Object camObjhere;
    ProgressDialog progressDialog;
    public LTACameraObject camObjhere2;
    View v;


    static MyDialogFragment newInstance() {
        MyDialogFragment f = new MyDialogFragment();
        return f;
    }

    public static ImageDialogFragment newInstance(LTACameraObject camObj) {
        camObjhere = camObj;
        dialoguri = camObj.getImage();

        //  MyDialogFragment f = new MyDialogFragment();
        ImageDialogFragment f = new ImageDialogFragment();

        return f;
    }


    public static ImageDialogFragment newInstance(IncidentObject camObj) {
        camObjhere = camObj;
        //dialoguri = camObj.getImage();

        //  MyDialogFragment f = new MyDialogFragment();
        ImageDialogFragment f = new ImageDialogFragment();

        return f;
    }


    public static ImageDialogFragment newInstance(String uri) {
        dialoguri = uri;
        ImageDialogFragment f = new ImageDialogFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.fullscreenfragmentdialog, container, false);

        if(camObjhere instanceof LTACameraObject) {
           camObjhere2 = (LTACameraObject) camObjhere;
            v = inflater.inflate(R.layout.fullscreenfragmentdialog, container, false);

            MobileAds.initialize(getActivity(),
                    "ca-app-pub-7220416147687992~6211443619");
            AdView adMobView = (AdView) v.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adMobView.loadAd(adRequest);
            adMobView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    Log.i("Ads", "onAdLoaded");
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                    Log.i("Ads", "onAdFailedToLoad");
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

        /*
        AdView adMobView2 = (AdView) v.findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        adMobView2.loadAd(adRequest2);
        adMobView2.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("Ads", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
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
        });*/
            context = getActivity();

            ImageLoader imageLoader = ImageLoader.getInstance();

            imageLoader.displayImage(dialoguri, (ImageView) v.findViewById(R.id.iv_preview_image));

            Button btnClose = (Button) v.findViewById(R.id.btnIvClose);






            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


            if (firebaseAuth.getCurrentUser() != null) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("UserCamera").child(user.getUid());


                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                    View v =inflater.inflate(R.layout.cameralistui,container,false);
//                    setHasOptionsMenu(true);

                        Log.d(TAG, "onDataChange() called");


                        DialogAsynTaskTrackUserList dialogAsynTaskTrackUserList = new DialogAsynTaskTrackUserList(dataSnapshot, context);
                        dialogAsynTaskTrackUserList.execute();



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

//                DialogAsynTaskTrackUserList DialogAsynTaskTrackUserList = new DialogAsynTaskTrackUserList(dataSnapshot, context);
//                DialogAsynTaskTrackUserList.execute();


            } else{
                Button btnsavelist = (Button) v.findViewById(R.id.SaveCamToList);
                    btnsavelist.setVisibility(v.GONE);
            }


                if (camObjhere2 != null) {
                TextView dateText = (TextView) v.findViewById(R.id.dateStamp);
                String[] timeStampOut = new TimeStampProcessing(camObjhere2).getDate();
                dateText.setText(timeStampOut[0]);

                TextView timeText = (TextView) v.findViewById(R.id.timeStamp);
                timeText.setText(timeStampOut[1] + " : " + timeStampOut[2] + " : " + timeStampOut[3]);

                TextView addressText = (TextView) v.findViewById(R.id.address);
                addressText.setText(camObjhere2.getName());


            }
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    Fragment prev = ((((Activity) context).getFragmentManager())).findFragmentByTag("my-dialog");
                    if (prev != null) {
                        DialogFragment df = (DialogFragment) prev;
                        df.dismiss();
                    }
                }
//                    int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
//                    } else {
//                        //TODO
//                       // downloadurl(camObjhere2.getImage());
//                    }
//
//                 downloadurl(camObjhere2.getImage());
//                }
          });

//            Button btnsavelist = (Button) v.findViewById(R.id.SaveCamToList);
//
//            btnsavelist.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View arg0) {
//
//                    //((MainActivityUI) context).dataUpdated(camObjhere2,"savetolist");
//
//
//                  // ((MainActivityUI) context).ListSaver(camObjhere2);
//
//                    ((MainActivityUI) context).ListDeleter(camObjhere2);
//
//                    Toast.makeText(context, "Added to you List", Toast.LENGTH_SHORT).show();
//
//
//
////                    Fragment prev = ((((Activity) context).getFragmentManager())).findFragmentByTag("my-dialog");
////                    if (prev != null) {
////                        DialogFragment df = (DialogFragment) prev;
////                        df.dismiss();
////                    }
//                }
////                    int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
////
////                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
////                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
////                    } else {
////                        //TODO
////                       // downloadurl(camObjhere2.getImage());
////                    }
////
////                 downloadurl(camObjhere2.getImage());
////                }
//            });


        }else if(camObjhere instanceof IncidentObject) {
            final IncidentObject camObjhere2 = (IncidentObject) camObjhere;
            v = inflater.inflate(R.layout.incidentfullscreenfragmentdialog, container, false);

            MobileAds.initialize(getActivity(),
                    "ca-app-pub-7220416147687992~6211443619");
            AdView adMobView = (AdView) v.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adMobView.loadAd(adRequest);
            adMobView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    Log.i("Ads", "onAdLoaded");
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                    Log.i("Ads", "onAdFailedToLoad");
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

        /*
        AdView adMobView2 = (AdView) v.findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        adMobView2.loadAd(adRequest2);
        adMobView2.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("Ads", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
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
        });*/
            context = getActivity();

           // ImageLoader imageLoader = ImageLoader.getInstance();

            //imageLoader.displayImage(dialoguri, (ImageView) v.findViewById(R.id.iv_preview_image));
            ImageView dialogimg = v.findViewById(R.id.iv_preview_image);

            int typeicon = MapMarkerSelection.IncidentChoice(camObjhere2.getType());

            dialogimg.setImageResource(typeicon);

            Button btnClose = (Button) v.findViewById(R.id.btnIvClose);
            Button btnnaerbycam = (Button) v.findViewById(R.id.nearbycam);
          //  Button btnarchive = (Button) v.findViewById(R.id.Archivebtn);

            if (camObjhere2 != null) {


                TextView dateText = (TextView) v.findViewById(R.id.dateStamp);
                String[] timeStampOut = new TimeStampProcessing(camObjhere2).getDate();
                dateText.setText(StringParser.between(camObjhere2.getMessage(),"(",")"));

                TextView timeText = (TextView) v.findViewById(R.id.timeStamp);
                timeText.setText(StringParser.between(camObjhere2.getMessage(),")"," "));

                String markermsg = StringParser.between(camObjhere2.getMessage(),"on",".");
                if(markermsg.isEmpty()){
                    markermsg = StringParser.between(camObjhere2.getMessage(),"in",".");

                }

                TextView addressText = (TextView) v.findViewById(R.id.address);
                addressText.setText(markermsg);

                TextView typediatext = (TextView) v.findViewById(R.id.incidiatype);
               typediatext.setText(camObjhere2.getType());

                TextView advicediatext = (TextView) v.findViewById(R.id.advicedialog);
                advicediatext.setText(StringParser.after(camObjhere2.getMessage(),"."));

             //   advicediatext.setText(camObjhere2.getType());




            }

            btnnaerbycam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

//                    CameraDAO camDAO = new CameraDAOImplement(context);
//                    List<LTACameraObject> camArray = camDAO.getAllCameras(context);

                    List<LTACameraObject> camArray = CameraSingleton.getInstance().getArrayList();
                    int retryCount = 0;

                    while(camArray == null){
                        camArray = CameraSingleton.getNewInstance(context).getArrayList();
                        retryCount++;
                        Log.d("Retry get CameraList ", String.valueOf(retryCount));

                    }

                    //CameraDAO camdao
                   // List<LTACameraObject> nearlist = CameraDAO.CameraDAOImplement.;
//                    List<HDBCarParkObject> withLots = CarParkSingletonPattern.getInstance().getArrayList();
//                    while(withLots == null){
//                        withLots = CarParkSingletonPattern.getNewInstance().getArrayList();
//                    }

                    // MapUI.rangeCheckLatlonCamera(camObjhere2,nearlist);
                    ((MainActivityUI) context).dataUpdated(camObjhere2,"camera");


                    Fragment prev = ((((Activity) context).getFragmentManager())).findFragmentByTag("my-dialog");
                    if (prev != null) {
                        DialogFragment df = (DialogFragment) prev;
                        df.dismiss();
                    }
                }
            });

//            btnarchive.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View arg0) {
//                    ((MainActivityUI) context).ListSaver(camObjhere2);
//
//                    Toast.makeText(context, "Added to your Archive", Toast.LENGTH_SHORT).show();
//
//                }
//            });

            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    Fragment prev = ((((Activity) context).getFragmentManager())).findFragmentByTag("my-dialog");
                    if (prev != null) {
                        DialogFragment df = (DialogFragment) prev;
                        df.dismiss();
                    }
                }
            });
        }
        return v;
    }
    public void downloadurl(String url){

        DownloadManager.Request request = null;

        try {
            request = new DownloadManager.Request(Uri.parse(url));
        } catch (IllegalArgumentException e) {
        }
                /* allow mobile and WiFi downloads */
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("DM Example");
        request.setDescription("Downloading file");

                /* we let the user see the download in a notification */
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                /* Try to determine the file extension from the url. Only allow image types. You
                 * can skip this check if you only plan to handle the downloaded file manually and
                 * don't care about file managers not recognizing the file as a known type */
        String[] allowedTypes = {"png", "jpg", "jpeg", "gif", "webp"};
        String suffix = url.substring(url.lastIndexOf(".") + 1).toLowerCase();
        if (!Arrays.asList(allowedTypes).contains(suffix)) {
            for (String s : allowedTypes) {
            }
        }

                /* set the destination path for this download */
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS +
                File.separator + "image_test", "Terrapin" + "." + suffix);
                /* allow the MediaScanner to scan the downloaded file */
        request.allowScanningByMediaScanner();
        final DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                /* this is our unique download id */
        final long DL_ID = dm.enqueue(request);

                /* get notified when the download is complete */
        BroadcastReceiver mDLCompleteReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                        /* our download */
                if (DL_ID == intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)) {

                             /* get the path of the downloaded file */
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(DL_ID);
                    Cursor cursor = dm.query(query);
                    if (!cursor.moveToFirst()) {
                        return;
                    }

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                            != DownloadManager.STATUS_SUCCESSFUL) {
                        return;
                    }

                    String path = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                }
            }
        };
                /* register receiver to listen for ACTION_DOWNLOAD_COMPLETE action */
        context.registerReceiver(mDLCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    downloadurl(camObjhere2.getImage());
                }
                else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }



            // other 'case' lines to check for other
            // permissions this app might request
        }



    public class DialogAsynTaskTrackUserList extends AsyncTask<Void, Void, Boolean> {

        ListAsyncTaskListener listener;
        Activity mActivity;
        DataSnapshot dataSnapshot;

//        public DialogAsynTaskTrackUserList(ListAsyncTaskListener listener, Activity activity) {
//            this.listener = listener;
//            this.mActivity = activity;
//        }

        public DialogAsynTaskTrackUserList(DataSnapshot dataSnapshot, Activity activity) {
            this.dataSnapshot = dataSnapshot;
            this.mActivity = activity;
        }


        @Override
        protected Boolean doInBackground(Void... voids) {


            List<CameraListFireBaseObject> camlistfirebase = new ArrayList<>();

            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                String displayName = dataSnapshot.getValue().toString();
//                System.out.println(displayName);
//                Log.d(TAG, " Tried get from Fire");
                CameraListFireBaseObject firecamera = postSnapshot.getValue(CameraListFireBaseObject.class);
              //  System.out.println(firecamera.getCameraid());


                camlistfirebase.add(firecamera);
            }

            /////////////////////////////////////////////////////////////////////////////////////

            List<LTACameraObject> camlistforcomparison = CameraSingleton.getNewInstance(context).getArrayList();

            for (CameraListFireBaseObject firecamcomp : camlistfirebase) {

             //   for (LTACameraObject camcomp : camlistforcomparison) {

                    if (firecamcomp.getCameraid().equals(camObjhere2.getCamera_id())) {
                      return true;

                  //  }

                }
            }





            return false;
        }


        @Override
        protected void onPostExecute(final Boolean saveditem) {

          //  setUpDialogList(CamObjects);
            // listener.CameraObjectList(CamObjects);
            // progressDialogMall.dismiss();

            Button btnsavelist = (Button) v.findViewById(R.id.SaveCamToList);

            if(saveditem) {
            btnsavelist.setText("Remove from List");
            }

            btnsavelist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    //((MainActivityUI) context).dataUpdated(camObjhere2,"savetolist");


                    // ((MainActivityUI) context).ListSaver(camObjhere2);

                    if (saveditem) {
                        ((MainActivityUI) context).ListDeleter(camObjhere2);
                        Toast.makeText(context, "Removed from your List", Toast.LENGTH_SHORT).show();

                    }else {
                        ((MainActivityUI) context).ListSaver(camObjhere2);
                        Toast.makeText(context, "Added to you List", Toast.LENGTH_SHORT).show();

                    }



//                    Fragment prev = ((((Activity) context).getFragmentManager())).findFragmentByTag("my-dialog");
//                    if (prev != null) {
//                        DialogFragment df = (DialogFragment) prev;
//                        df.dismiss();
//                    }
                }
//                    int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
//                    } else {
//                        //TODO
//                       // downloadurl(camObjhere2.getImage());
//                    }
//
//                 downloadurl(camObjhere2.getImage());
//                }
            });

            if (progressDialog != null && progressDialog.isShowing()) {

                progressDialog.dismiss();
            }

        }

        @Override
        protected void onPreExecute() {

            //  listener.onListAsyncTaskStart();
            progressDialog = ProgressDialog.show(getContext(), "Please wait.",
                    "Checking your List...", true);

        }
    }

    public void setsavebuttontext(Boolean savedinlist){

    }



}
