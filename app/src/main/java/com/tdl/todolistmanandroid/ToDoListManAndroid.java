package com.tdl.todolistmanandroid;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.kakao.auth.*;
import com.tdl.todolistmanandroid.adapter.KakaoSDKAdapter;

/**
 * Created by songmho on 2017. 4. 14..
 */

public class ToDoListManAndroid extends Application{
    private static volatile ToDoListManAndroid instance = null;
     @Override
    public void onCreate() {
        super.onCreate();
         instance = this;

        FacebookSdk.sdkInitialize(getApplicationContext());
        KakaoSDK.init(new KakaoSDKAdapter());

    }
    public static ToDoListManAndroid getGlobalApplicationContext() {
        if(instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return instance;
    }
}
