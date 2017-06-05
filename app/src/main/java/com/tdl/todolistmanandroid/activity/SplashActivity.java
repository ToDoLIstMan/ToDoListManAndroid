package com.tdl.todolistmanandroid.activity;

/**
 * Created by HyunWook Kim on 2017-03-21.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.database.group;
import com.tdl.todolistmanandroid.database.user;

import java.util.Collections;
import java.util.List;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends Activity {

    final int SPLASH_TIME = 2000;

    FirebaseDatabase database;

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!isNetworkConnected()) {
            Toast.makeText(this, "인터넷 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
            finish();
        } else{

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent gotoMain = new Intent(SplashActivity.this, MainActivity.class);
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference().child("user")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    final ValueEventListener v1 = myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                user curuser = dataSnapshot.getValue(user.class);
                                List<Integer> a = curuser.getGroups();
                                if (a.size() > 0) {
                                    a.removeAll(Collections.singleton(null));
                                    int groupUid = a.get(0);
                                    Log.e("adsf", curuser.getGroups().toString() + "");
                                    final DatabaseReference myRef1 = database.getReference().child("group").child("" + groupUid);
                                    myRef1.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            gotoMain.putExtra("groupUid", dataSnapshot.getValue(group.class).getId());
                                            gotoMain.putExtra("groupName", dataSnapshot.getValue(group.class).getGroupName());
                                            gotoMain.putExtra("masterUid", dataSnapshot.getValue(group.class).getMasterUid());
                                            startActivity(gotoMain);
                                            finish();

                                            myRef1.removeEventListener(this);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                } else {
                                    gotoMain.putExtra("groupUid", -1);
                                    gotoMain.putExtra("groupName", "");
                                    startActivity(gotoMain);
                                    myRef.removeEventListener(this);
                                    finish();
                                }


                                myRef.removeEventListener(this);
                            } else {
                                gotoMain.putExtra("groupUid", -1);
                                gotoMain.putExtra("groupName", "");
                                startActivity(gotoMain);

                                myRef.removeEventListener(this);
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME-1000);


    }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
