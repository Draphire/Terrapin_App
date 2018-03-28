package com.lauden.weeliangng.terrapin.Model.MapElementObjects;

/**
 * Created by Jun Wei on 7/11/2017.
 */

public class Duration {
    private String text;
    private int value;

    public Duration(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public String getText(){
        return text;
    }
}
