package com.lauden.weeliangng.terrapin.Controller.MallRecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.lauden.weeliangng.terrapin.Model.CarParkFactoryPattern.MallObject;
import com.lauden.weeliangng.terrapin.Model.Utility.StringParser;
import com.lauden.weeliangng.terrapin.Model.Utility.TimeStampProcessing;
import com.lauden.weeliangng.terrapin.R;
import com.lauden.weeliangng.terrapin.View.MainActivityUI;

/**
 * Created by WeeLiang Ng on 6/3/2018.
 */

public class MallObjectViewHolder extends ChildViewHolder implements View.OnClickListener{

    private TextView mIngredientTextView;
    private TextView mIngredientTextView2;
    private String camI;
    private String imgUri;
    private Context context;

    ImageView camera;
    TextView areaText;
    TextView lotsText;
    TextView devText;
    TextView lottypeText;
    ImageButton dlButton;
    ImageButton rfButton;
    ImageButton mpButton;
    MallObject mMallObject;
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

    public MallObjectViewHolder(@NonNull View itemView) {
        super(itemView);
        // mIngredientTextView = (TextView) itemView.findViewById(R.id.ingredient_textview);

        //mIngredientTextView2 = (TextView) itemView.findViewById(R.id.ingredient_textview2);
        context = itemView.getContext();

        //TextView latitude;
        //TextView longitude;


//        camera = (ImageView) itemView.findViewById(R.id.camera);
//        camera.setOnClickListener(this);




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

        areaText = (TextView) itemView.findViewById(R.id.areamall);
        lotsText = (TextView) itemView.findViewById(R.id.lots);
        lottypeText = (TextView) itemView.findViewById(R.id.lottype);
        devText = (TextView) itemView.findViewById(R.id.development);


        // latitude = (TextView)convertView.findViewById(R.id.latitude);
        //  longitude = (TextView)convertView.findViewById(R.id.longitude);



    }

    public void bind(@NonNull MallObject inMallObject) {
        //mIngredientTextView.setText(mMallObject.getName());
        //  mIngredientTextView2.setText(mMallObject.getName());

        this.mMallObject = inMallObject;
        camI = String.valueOf(inMallObject.getArea());


        areaText.setText(camI);
        //imgUri = mMallObject.getImage();
        //ImageLoader.getInstance().displayImage(mMallObject.getImage(), camera); // Default options will be used

        //  TextView dateText = (TextView) context.findViewById(R.id.dateStamp);
       // String[] timeStampOut =  new TimeStampProcessing(inMallObject).getDate();
        //String date = StringParser.between(inMallObject.getMessage(),"(",")");

        lotsText.setText(String.valueOf(inMallObject.getAvailableLots()));

        // TextView timeText = (TextView) itemView.findViewById(R.id.timeStamp);
       // String time = StringParser.between(inMallObject.getMessage(),")"," ");

        lottypeText.setText(inMallObject.getLotType());

        //String msg = StringParser.between(inMallObject.getMessage()," ",".");

        devText.setText(inMallObject.getDevelopment());




        //   timestamp.setText("Time Stamp: " + mMallObject.getTimestamp());
        //camid1.setText(("Camera ID: " + mMallObject.getCamera_id()));
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
//            case R.id.camera:
//                 /*
//                 final Dialog nagDialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//                 nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                 nagDialog.setCancelable(false);
//                 nagDialog.setContentView(R.layout.fullscreenview);
//                 Button btnClose = (Button)nagDialog.findViewById(R.id.btnIvClose);
//                 //ImageView ivPreview = (ImageView) itemView.findViewById(R.id.iv_preview_image);
//
//               // ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.iv_preview_image);
//                 ImageLoader imageLoader = ImageLoader.getInstance();
//                 //  ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.iv_preview_image);
//                 imageLoader.displayImage(imgUri, (ImageView)nagDialog.findViewById(R.id.iv_preview_image));
//                // ivPreview.setBackgroundDrawable(dd);
//
//                 btnClose.setOnClickListener(new View.OnClickListener() {
//                     @Override
//                     public void onClick(View arg0) {
//
//                         nagDialog.dismiss();
//                     }
//                 });
//                 nagDialog.show();
//                 doKeepDialog(nagDialog);
//                 */
//
//                //  FragmentManager manager = (((AppCompatActivity) context).getFragmentManager();
//
//                // new MyDialogFragment().show(((((AppCompatActivity) context).getFragmentManager())), "tag");
//
//              /*   FragmentManager manager = ((((AppCompatActivity) context).getFragmentManager()));
//                 FragmentTransaction ft = manager.beginTransaction();
//
//                 Fragment prev = manager.findFragmentByTag("yourTag");
//                 if (prev != null) {
//                     ft.remove(prev);
//                 }*/
//
//// Create and show the dialog.
//                // MyDialogFragment newFragment = ImageDialogFragment.newInstance(imgUri);
//                //ImageDialogFragment.newInstance(imgUri).show(((((AppCompatActivity) context).getFragmentManager())), "my-dialog");
//                //newFragment.show();
//                ConnectivityManager cm =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//                boolean isConnected = activeNetwork != null &&
//                        activeNetwork.isConnected();
//
//                if(isConnected) {
//
//
//                    new ImageDialogFragment().newInstance(mMallObject).show(((((AppCompatActivity) context).getFragmentManager())), "my-dialog");
//                }
//                else
//                {
//                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
//
//                }
//                //FullScreenDialog.showDialog();
//                 /*
//                 FragmentTransaction ft = ((MainActivityUI)context).getFragmentManager().beginTransaction();
//                 DialogFragment newFragment = MyDialogFragment.newInstance();
//                 newFragment.show(ft, "dialog");
//
//                 DialogFragment newFragment = MyDialogFragment.newInstance();
//                 newFragment.show(getFragmentManager(), "dialog");
//                 new MyDialogFragment().show(getFragmentManager(), "tag");
//
//                 listener=this;
//                 adapter = new TitleCameraObjectAdapter(camera, MyDialogFragment, context);
//*/
//                break;
//
//
//            //    UrlImageViewHelper.setUrlDrawable(mImageReport, mCurrentReportString.getUrlImages());
//
//
//            // do your code
//            //case R.id.downloadButton:
//            // context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(imgUri)));
//
//            // do your code
//            //break;
//
//            // case R.id.refreshButton:
//            // do your code
//            //    List<TitleCameraObject> items = CameraDAOUsage.CameraDAOgetTitleObjectList();
//            //  TitleCameraObjectAdapter mAdapter = new TitleCameraObjectAdapter(context,items);
//            //mAdapter.updateItems();
//
//
//            // imgUri = CamSingletonImplement.getCurrentLotstaticUpdateForIMG(mMallObject);
//            //ImageLoader.getInstance().displayImage(mMallObject.getImage(), camera); // Default options will be used
//            //((MainActivityUI) context).getPager().setCurrentItem(0);
//
//            //break;

            case R.id.mapButton:


                ((MainActivityUI) context).getPager().setCurrentItem(0);
                //  pager.setCurrentItem(0);
                //  updating.update("button was clicked");
                //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(1.290270, 103.851959), 12);

                ((MainActivityUI) context).dataUpdated(mMallObject);


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

}
