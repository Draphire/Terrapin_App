package com.lauden.weeliangng.terrapin.Controller.CameraListUIRecyclerView;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.Utility.TimeStampProcessing;
import com.lauden.weeliangng.terrapin.R;
import com.lauden.weeliangng.terrapin.View.CameraListPresentation.DialogueView.ImageDialogFragment;
import com.lauden.weeliangng.terrapin.View.MainActivityUI;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.Arrays;


public class CameraObjectViewHolder extends ChildViewHolder implements View.OnClickListener{

    private TextView mIngredientTextView;
    private TextView mIngredientTextView2;
    private String camI;
    private String imgUri;
    private Context context;

    ImageView camera;
    TextView timestamp;
    TextView camid1;
    ImageButton dlButton;
    ImageButton rfButton;
    ImageButton mpButton;
    LTACameraObject mLTACameraObject;
    TextView dateText;
    TextView timeText;

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

    public CameraObjectViewHolder(@NonNull View itemView) {
        super(itemView);
       // mIngredientTextView = (TextView) itemView.findViewById(R.id.ingredient_textview);

        //mIngredientTextView2 = (TextView) itemView.findViewById(R.id.ingredient_textview2);
        context = itemView.getContext();

        //TextView latitude;
        //TextView longitude;


        camera = (ImageView) itemView.findViewById(R.id.camera);
        camera.setOnClickListener(this);




      // timestamp = (TextView) itemView.findViewById(R.id.timestamp);
        //timestamp.setOnClickListener(this);

     //   camid1 = (TextView) itemView.findViewById(R.id.camid);
       // camid1.setOnClickListener(this);

       /* dlButton = (ImageButton) itemView.findViewById(R.id.downloadButton);
        dlButton.setOnClickListener(this);

        rfButton = (ImageButton) itemView.findViewById(R.id.refreshButton);
        rfButton.setOnClickListener(this);*/

        mpButton = (ImageButton) itemView.findViewById(R.id.mapButton);
        mpButton.setOnClickListener(this);

        dateText = (TextView) itemView.findViewById(R.id.dateStamp);
        timeText = (TextView) itemView.findViewById(R.id.timeStamp);


        // latitude = (TextView)convertView.findViewById(R.id.latitude);
        //  longitude = (TextView)convertView.findViewById(R.id.longitude);



    }

    public void bind(@NonNull LTACameraObject inLTACameraObject) {
        //mIngredientTextView.setText(mLTACameraObject.getName());
      //  mIngredientTextView2.setText(mLTACameraObject.getName());

        this.mLTACameraObject = inLTACameraObject;
        camI = String.valueOf(mLTACameraObject.getCamera_id());

        imgUri = mLTACameraObject.getImage();
        ImageLoader.getInstance().displayImage(mLTACameraObject.getImage(), camera); // Default options will be used

      //  TextView dateText = (TextView) context.findViewById(R.id.dateStamp);
        String[] timeStampOut =  new TimeStampProcessing(inLTACameraObject).getDate();
        dateText.setText(timeStampOut[0]);

       // TextView timeText = (TextView) itemView.findViewById(R.id.timeStamp);
        timeText.setText(timeStampOut[1]+" : "+timeStampOut[2]+" : "+timeStampOut[3]);



    //   timestamp.setText("Time Stamp: " + mLTACameraObject.getTimestamp());
       //camid1.setText(("Camera ID: " + mLTACameraObject.getCamera_id()));
        //   mIngredientTextView2 = (TextView) itemView.findViewById(R.id.ingredient_textview2);


    }

     @Override
    public void onClick(View v) {
       /*  List<TitleCameraObject> items = CameraDAOUsage.CameraDAOgetTitleObjectList();

         CameraListUI.mAdapter.updateItems(items);

         for (int j = 0; j < items.size(); j++) {
             mAdapter.notifyChildRemoved(j, 0);
             mAdapter.notifyChildInserted(j, 0);
         }*/

         Log.d("Child Image CamID: "+ camI," was clicked");
       /*
         Toast.makeText(context, "NORMAL SIZE!", Toast.LENGTH_LONG).show();
         imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
         imageView.setAdjustViewBounds(true);
         zoomOut =false;
         */

         switch (v.getId()) {
             case R.id.camera:
                 /*
                 final Dialog nagDialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                 nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                 nagDialog.setCancelable(false);
                 nagDialog.setContentView(R.layout.fullscreenview);
                 Button btnClose = (Button)nagDialog.findViewById(R.id.btnIvClose);
                 //ImageView ivPreview = (ImageView) itemView.findViewById(R.id.iv_preview_image);

               // ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.iv_preview_image);
                 ImageLoader imageLoader = ImageLoader.getInstance();
                 //  ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.iv_preview_image);
                 imageLoader.displayImage(imgUri, (ImageView)nagDialog.findViewById(R.id.iv_preview_image));
                // ivPreview.setBackgroundDrawable(dd);

                 btnClose.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View arg0) {

                         nagDialog.dismiss();
                     }
                 });
                 nagDialog.show();
                 doKeepDialog(nagDialog);
                 */

               //  FragmentManager manager = (((AppCompatActivity) context).getFragmentManager();

               // new MyDialogFragment().show(((((AppCompatActivity) context).getFragmentManager())), "tag");

              /*   FragmentManager manager = ((((AppCompatActivity) context).getFragmentManager()));
                 FragmentTransaction ft = manager.beginTransaction();

                 Fragment prev = manager.findFragmentByTag("yourTag");
                 if (prev != null) {
                     ft.remove(prev);
                 }*/

// Create and show the dialog.
                // MyDialogFragment newFragment = ImageDialogFragment.newInstance(imgUri);
                 //ImageDialogFragment.newInstance(imgUri).show(((((AppCompatActivity) context).getFragmentManager())), "my-dialog");
                 //newFragment.show();
                 ConnectivityManager cm =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                 NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                 boolean isConnected = activeNetwork != null &&
                         activeNetwork.isConnected();

                 if(isConnected) {


                     new ImageDialogFragment().newInstance(mLTACameraObject).show(((((AppCompatActivity) context).getFragmentManager())), "my-dialog");
                 }
                 else
                 {
                     Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();

                 }
                 //FullScreenDialog.showDialog();
                 /*
                 FragmentTransaction ft = ((MainActivityUI)context).getFragmentManager().beginTransaction();
                 DialogFragment newFragment = MyDialogFragment.newInstance();
                 newFragment.show(ft, "dialog");

                 DialogFragment newFragment = MyDialogFragment.newInstance();
                 newFragment.show(getFragmentManager(), "dialog");
                 new MyDialogFragment().show(getFragmentManager(), "tag");

                 listener=this;
                 adapter = new TitleCameraObjectAdapter(camera, MyDialogFragment, context);
*/
                 break;


         //    UrlImageViewHelper.setUrlDrawable(mImageReport, mCurrentReportString.getUrlImages());


                 // do your code
             //case R.id.downloadButton:
               // context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(imgUri)));

                   // do your code
                 //break;

           // case R.id.refreshButton:
                 // do your code
             //    List<TitleCameraObject> items = CameraDAOUsage.CameraDAOgetTitleObjectList();
               //  TitleCameraObjectAdapter mAdapter = new TitleCameraObjectAdapter(context,items);
                //mAdapter.updateItems();


                // imgUri = CamSingletonImplement.getCurrentLotstaticUpdateForIMG(mLTACameraObject);
                 //ImageLoader.getInstance().displayImage(mLTACameraObject.getImage(), camera); // Default options will be used
                 //((MainActivityUI) context).getPager().setCurrentItem(0);

                 //break;

             case R.id.mapButton:


                 ((MainActivityUI) context).getPager().setCurrentItem(0);
                 //  pager.setCurrentItem(0);
                 //  updating.update("button was clicked");
                 //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(1.290270, 103.851959), 12);

                 ((MainActivityUI) context).dataUpdated(mLTACameraObject);


                 break;

             default:
                 break;
         }



     }
    private static void doKeepDialog(Dialog dialog){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
    }


//    public void downloadurl(String url){
//
//         DownloadManager.Request request = null;
//
//        try {
//            request = new DownloadManager.Request(Uri.parse(url));
//        } catch (IllegalArgumentException e) {
//         }
//                /* allow mobile and WiFi downloads */
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
//        request.setTitle("DM Example");
//        request.setDescription("Downloading file");
//
//                /* we let the user see the download in a notification */
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
//                /* Try to determine the file extension from the url. Only allow image types. You
//                 * can skip this check if you only plan to handle the downloaded file manually and
//                 * don't care about file managers not recognizing the file as a known type */
//        String[] allowedTypes = {"png", "jpg", "jpeg", "gif", "webp"};
//        String suffix = url.substring(url.lastIndexOf(".") + 1).toLowerCase();
//        if (!Arrays.asList(allowedTypes).contains(suffix)) {
//                for (String s : allowedTypes) {
//            }
//        }
//
//                /* set the destination path for this download */
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS +
//                File.separator + "image_test", "Terrapin" + "." + suffix);
//                /* allow the MediaScanner to scan the downloaded file */
//        request.allowScanningByMediaScanner();
//        final DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//                /* this is our unique download id */
//        final long DL_ID = dm.enqueue(request);
//
//                /* get notified when the download is complete */
//        BroadcastReceiver mDLCompleteReceiver = new BroadcastReceiver() {
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                        /* our download */
//                if (DL_ID == intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)) {
//
//                             /* get the path of the downloaded file */
//                    DownloadManager.Query query = new DownloadManager.Query();
//                    query.setFilterById(DL_ID);
//                    Cursor cursor = dm.query(query);
//                    if (!cursor.moveToFirst()) {
//                         return;
//                    }
//
//                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
//                            != DownloadManager.STATUS_SUCCESSFUL) {
//                         return;
//                    }
//
//                    String path = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
//                  }
//            }
//        };
//                /* register receiver to listen for ACTION_DOWNLOAD_COMPLETE action */
//        context.registerReceiver(mDLCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
//    }

}
