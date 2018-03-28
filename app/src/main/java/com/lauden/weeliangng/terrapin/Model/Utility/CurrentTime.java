package com.lauden.weeliangng.terrapin.Model.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentTime {
    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH'%3A'mm'%3A'ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}