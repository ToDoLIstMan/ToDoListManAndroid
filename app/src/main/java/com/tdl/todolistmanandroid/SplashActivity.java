package com.tdl.todolistmanandroid;

/**
 * Created by HyunWook Kim on 2017-03-21.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends Activity {

    final int SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new Intent(SplashActivity.this,MainActivity.class);
            }
        },SPLASH_TIME);


    }
}
