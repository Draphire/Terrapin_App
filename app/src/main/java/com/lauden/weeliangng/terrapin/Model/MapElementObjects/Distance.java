package com.lauden.weeliangng.terrapin.Model.MapElementObjects;

/**
 * Created by Jun Wei on 7/11/2017.
 */

public class Distance {
    private String text;
    private int value;

    public Distance(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public String getText(){
        return text;
    }
}
