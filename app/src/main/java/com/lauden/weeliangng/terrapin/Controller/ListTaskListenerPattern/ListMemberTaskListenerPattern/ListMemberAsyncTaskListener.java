package com.lauden.weeliangng.terrapin.Controller.ListTaskListenerPattern.ListMemberTaskListenerPattern;

import com.lauden.weeliangng.terrapin.Controller.RecyclerViewObjects.TitleCameraObject;

import java.util.List;

/**
 * Created by WeeLiang Ng on 8/3/2018.
 */

public interface ListMemberAsyncTaskListener {
    void onListMemberAsyncTaskStart();

    void onListMemberAsyncTaskStartComplete(String s);

}
