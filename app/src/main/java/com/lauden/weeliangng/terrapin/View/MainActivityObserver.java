package com.lauden.weeliangng.terrapin.View;

/**
 * Created by WeeLiang Ng on 2/11/2017.
 */

public interface MainActivityObserver {
    void onDataUpdate();
    void onDataUpdate(String s);

    void onDataUpdate(Object o);

    void onDataUpdate(Object o,String s);

}
