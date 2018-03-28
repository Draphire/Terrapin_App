package com.lauden.weeliangng.terrapin.Model.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;

public class ConnectionCheck {
        Context mContext;
        Activity mActivity;

        public ConnectionCheck(Activity activity) {
            this.mActivity = activity;
        }

        public void checkNetworkConnection() {

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle("No internet Connection");
            builder.setMessage("Please turn on internet connection to continue");
            builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                        if(!isNetworkConnectionAvailableOnly()){
                            checkNetworkConnection();
                        }
                         //   dialog.show();
                        else
                        {
                            dialog.dismiss();
                        }




                }
            });
            AlertDialog alertDialog = builder.create();
            builder.create();
            alertDialog.show();

           // new MyDialogFragment().show(((((AppCompatActivity) mContext).getFragmentManager())), "tag");

        }

    public boolean checkNetworkConnectionState() {
        final boolean[] result = new boolean[1];
       AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(!isNetworkConnectionAvailableOnly()) {
                    checkNetworkConnection();
                    //dialog.show();
                }

                    result[0] = true;





            }
        });

            AlertDialog alertDialog = builder.create();
                    builder.create();
                    alertDialog.show();


        return result[0];

        // new MyDialogFragment().show(((((AppCompatActivity) mContext).getFragmentManager())), "tag");

    }

        public boolean isNetworkConnectionAvailable() {
            ConnectivityManager cm =
                    (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnected();
            if (isConnected) {
                Log.d("Network", "Connected");
                return true;
            } else {
                checkNetworkConnection();
                Log.d("Network", "Not Connected");
                return false;
            }
        }

    public boolean isNetworkConnectionAvailableOnly() {
        ConnectivityManager cm =
                (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if (isConnected) {
            Log.d("Network", "Connected");
            return true;
        } else {
           // checkNetworkConnection();
            Log.d("Network", "Not Connected");
           // checkNetworkConnection();

             return false;
        }
    }

    }