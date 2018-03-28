package com.lauden.weeliangng.terrapin.Model.Utility;

import com.lauden.weeliangng.terrapin.Model.CameraFactoryPattern.LTACameraObject;
import com.lauden.weeliangng.terrapin.Model.IncidentFactoryPattern.IncidentObject;

import java.util.StringTokenizer;

/**
 * Created by WeeLiang Ng on 16/11/2017.
 */

public class TimeStampProcessing {

    LTACameraObject camObjIn;
    IncidentObject inciobjin;
    String timeStamp;
    String inciStamp;

    public TimeStampProcessing(LTACameraObject camObj){
        this.camObjIn = camObj;

        this.timeStamp = camObj.getTimestamp();


    }

    public TimeStampProcessing(IncidentObject camObj){
        this.inciobjin = camObj;

        this.inciStamp = camObj.getMessage();


    }


    public String[] getDate() {

        if (timeStamp==null) {
            //String[] incidateTimeArray = {"incident", "test"};

        } else {

            StringTokenizer tokens = new StringTokenizer(timeStamp, "T");
            String date = tokens.nextToken();
            String timeGst = tokens.nextToken();
            StringTokenizer tokensChild = new StringTokenizer(timeGst, ":");
            String hour = tokensChild.nextToken();
            String minute = tokensChild.nextToken();
            String secondPlus = tokensChild.nextToken();

            StringTokenizer tokensGrandChild = new StringTokenizer(secondPlus, "+");
            String second = tokensGrandChild.nextToken();


            //    String gst = tokens.nextToken();// this will contain "Fruit"

            String[] dateTimeArray = {date, hour, minute, second};

            return dateTimeArray;

// in the case above I assumed the string has always that syntax (foo: bar)
// but you may want to check if there are tokens or not using the hasMoreTokens method

        }

       String[] incidateTimeArray = {"incident", "test"};

        return incidateTimeArray;
    }





}
