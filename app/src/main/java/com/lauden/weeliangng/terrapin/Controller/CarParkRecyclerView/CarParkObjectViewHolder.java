package com.lauden.weeliangng.terrapin.Controller.CarParkRecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern.ListAsyncTaskAllocator;
import com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern.ListAsyncTaskListener;
import com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern.ListMemberTaskListenerPattern.ListMemberAsyncTaskAllocator;
import com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern.ListMemberTaskListenerPattern.ListMemberAsyncTaskListener;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.HDBCarParkObject;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.MallObject;
import com.lauden.weeliangng.terrapin.Model.DataAccessObjects.CarParkDAOpattern.HDBCarParkAPIDAOImplement;
import com.lauden.weeliangng.terrapin.R;
import com.lauden.weeliangng.terrapin.View.MainActivityUI;

import java.util.ArrayList;
import java.util.List;
//import com.ntu.weeliangng.expandablerecylerlistview.ChildViewHolder;

/**
 * Created by WeeLiang Ng on 3/11/2017.
 */

public class CarParkObjectViewHolder extends ChildViewHolder implements View.OnClickListener,ListMemberAsyncTaskListener{

    private TextView mIngredientTextView;
    private TextView mIngredientTextView2;
    private String camI;
    private String imgUri;
    private Context context;

    /*
    private String totalLots;
    private String lotType;
    private String lotsAvailable;
    private String carParkNo;
    private String updateDateTime;
*/
    private TextView totalLots,lotsAvailable,cpNo, car_park_type, type_of_parking_system, short_term_parking, free_parking, night_parking;
    private TextView cpLatitude;
    private TextView cpLongitude;
    HDBCarParkObject CpObject;
    ImageView mpButton;
    ImageView rfButton;
    ListMemberAsyncTaskAllocator listMemberAsyncTaskAllocator = new ListMemberAsyncTaskAllocator();

    //  private double x_coord, y_coord;

/*
    ImageView camera;
    TextView timestamp;
    TextView camid1;
*/
    /*
    TextView title;

    public MyChildViewHolder(View v) {
        super(v);
        title = (TextView) v.findViewById(R.id.btn);
        title.setOnClickListener(new View.onClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, title.getText(),
                        Toast.LENGTH_SHORT).show();
            }
        });
*/

    public CarParkObjectViewHolder(@NonNull View itemView) {
        super(itemView);
        // mIngredientTextView = (TextView) itemView.findViewById(R.id.ingredient_textview);

        //mIngredientTextView2 = (TextView) itemView.findViewById(R.id.ingredient_textview2);
        context = itemView.getContext();

       // listMemberAsyncTaskAllocator.setListener(itemView.get);


        //TextView latitude;
        //TextView longitude;


       cpNo = (TextView) itemView.findViewById(R.id.cpNo);
       // camera.setOnClickListener(this);
        // address, car_park_type, type_of_parking_system, short_term_parking, free_parking, night_parking;


        car_park_type = (TextView) itemView.findViewById(R.id.CarParkType);
       // timestamp.setOnClickListener(this);

        short_term_parking = (TextView) itemView.findViewById(R.id.CarParkST);


        free_parking= (TextView) itemView.findViewById(R.id.CarParkFP);


        night_parking= (TextView) itemView.findViewById(R.id.CarParkNP);

        lotsAvailable= (TextView) itemView.findViewById(R.id.LotsAvailable);
        lotsAvailable.setOnClickListener(this);

        mpButton = (ImageView) itemView.findViewById(R.id.mapButton);
        mpButton.setOnClickListener(this);
        rfButton = (ImageView) itemView.findViewById(R.id.refreshButton);
        rfButton.setOnClickListener(this);
        // cpLatitude = (TextView) itemView.findViewById(R.id.cpLat);

        //cpLongitude = (TextView) itemView.findViewById(R.id.cpLon);



        //camid1.setOnClickListener(this);
        // latitude = (TextView)convertView.findViewById(R.id.latitude);
        //  longitude = (TextView)convertView.findViewById(R.id.longitude);



    }

    public void bind(@NonNull HDBCarParkObject inCpObject) {
        //mIngredientTextView.setText(cameraObject.getName());
        //  mIngredientTextView2.setText(cameraObject.getName());

        //TextView latitude;
        //TextView longitude;
        this.CpObject = inCpObject;

        cpNo.setText("Car-Park No. " + CpObject.getCarParkNo());
        // camera.setOnClickListener(this);
        // address, car_park_type, type_of_parking_system, short_term_parking, free_parking, night_parking;


        car_park_type.setText(CpObject.getCar_park_type());
        // timestamp.setOnClickListener(this);

        short_term_parking.setText("Short Term Parking: " + CpObject.getShort_term_parking());


        free_parking.setText("Free-Parking: " + CpObject.getFree_parking());


        night_parking.setText("Night-Parking: " + CpObject.isNight_parking());

        //lotsAvailable.setText(HDBCarParkAPIDAOImplement.getCurrentLotstatic(CpObject, context));
        lotsAvailable.setText("Refresh Now");


       // cpLatitude.setText(String.valueOf( CpObject.getLatitude()));

        //cpLongitude.setText(String.valueOf( CpObject.getLongitude()));


        /*
        camI = cameraObject.getCamera_id();

        imgUri = cameraObject.getImage();
        ImageLoader.getInstance().displayImage(cameraObject.getImage(), camera); // Default options will be used


        timestamp.setText("Time Stamp: " + cameraObject.getTimestamp());
        camid1.setText(("Camera ID: " + cameraObject.getCamera_id()));
        //   mIngredientTextView2 = (TextView) itemView.findViewById(R.id.ingredient_textview2);

*/
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.LotsAvailable:





                break;


            //    UrlImageViewHelper.setUrlDrawable(mImageReport, mCurrentReportString.getUrlImages());


            // do your code
            case R.id.mapButton:
               // context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(imgUri))); /** replace with your own uri */

                ((MainActivityUI) context).getPager().setCurrentItem(0);
                //  pager.setCurrentItem(0);
                //  updating.update("button was clicked");
                //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(1.290270, 103.851959), 12);

                ((MainActivityUI) context).dataUpdated(CpObject);

                // do your code
                break;

            case R.id.refreshButton:
                // do your code
                ConnectivityManager cm =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnected();

                if(isConnected) {

                  //  listMemberAsyncTaskAllocator.AsyncTasking("getTitleListAndUpdate",CpObject,context);
                  AsynTaskHDBavailableLots asynTaskHDBavailableLots = new AsynTaskHDBavailableLots();
                    asynTaskHDBavailableLots.execute();





                }
                else{
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();

                }
                break;

            default:
                break;
        }



    }

    ProgressDialog progressDialog;
    @Override
    public void onListMemberAsyncTaskStart() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = ProgressDialog.show(context, "Please wait.",
                "Updating your List...");

    }
    public void setLotsAvailable(String s){
        lotsAvailable.setText(s);

    }

    @Override
    public void onListMemberAsyncTaskStartComplete(String s) {

        lotsAvailable.setText(s);
        progressDialog.dismiss();

    }

    public class AsynTaskHDBavailableLots extends AsyncTask<Void,Void,String> {

        ListMemberAsyncTaskListener listener;

        HDBCarParkObject mHDBCarParkObject;


        @Override
        protected String doInBackground(Void... voids) {

            // List<TitleCameraObject> TitleCameraObject = CameraDAOUsage.CameraDAOgetTitleObjectList(mActivity);
            String tobeset = HDBCarParkAPIDAOImplement.getCurrentLotStaticUpdate(CpObject, context);

            return tobeset;
        }

        ProgressDialog progressDialogMall;

        @Override
        protected void onPostExecute(String CamObjects) {

            // listener.onListMemberAsyncTaskStartComplete(CamObjects);
            lotsAvailable.setText(CamObjects);

            progressDialogMall.dismiss();


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            // listener.onListMemberAsyncTaskStart();

            progressDialogMall = ProgressDialog.show(context, "Please wait.",
                    "Updating your List...", true);

        }
    }
}
