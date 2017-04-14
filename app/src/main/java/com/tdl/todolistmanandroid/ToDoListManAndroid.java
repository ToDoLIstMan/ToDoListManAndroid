package com.tdl.todolistmanandroid;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by songmho on 2017. 4. 14..
 */

public class ToDoListManAndroid extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
