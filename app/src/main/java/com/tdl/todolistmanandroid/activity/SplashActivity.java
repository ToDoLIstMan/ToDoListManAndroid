package com.tdl.todolistmanandroid.activity;

/**
 * Created by HyunWook Kim on 2017-03-21.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.tdl.todolistmanandroid.R;

public class SplashActivity extends Activity {

    final int SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,SignInActivity.class));
                finish();
            }
        },SPLASH_TIME);



    }
}
