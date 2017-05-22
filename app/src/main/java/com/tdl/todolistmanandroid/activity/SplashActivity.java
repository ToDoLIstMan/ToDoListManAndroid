package com.tdl.todolistmanandroid.activity;

/**
 * Created by HyunWook Kim on 2017-03-21.
 */
import android.app.Activity;
import android.content.Intent;
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

public class SplashActivity extends Activity {

    final int SPLASH_TIME = 2000;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent gotoMain = new Intent(SplashActivity.this, MainActivity.class);
                if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
                    database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference().child("user")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                            user curuser = dataSnapshot.getValue(user.class);
                                List<Integer>  a = curuser.getGroups();
                                if(a.size()>0) {
                                    a.removeAll(Collections.singleton(null));
                                    int groupUid = a.get(0);
                                    Log.e("adsf", curuser.getGroups().toString() + "");
                                    final DatabaseReference myRef = database.getReference().child("group").child("" + groupUid);

                                    myRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            gotoMain.putExtra("groupUid", dataSnapshot.getValue(group.class).getId());
                                            gotoMain.putExtra("groupName", dataSnapshot.getValue(group.class).getGroupName());
                                            gotoMain.putExtra("masterUid", dataSnapshot.getValue(group.class).getMasterUid());
                                            startActivity(gotoMain);
                                            finish();
                                            myRef.onDisconnect();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                                else{
                                    gotoMain.putExtra("groupUid",-1);
                                    gotoMain.putExtra("groupName","");

                                    myRef.onDisconnect();
                                    startActivity(gotoMain);finish();
                                }
                            }else {
                                gotoMain.putExtra("groupUid",-1);
                                gotoMain.putExtra("groupName","");

                                myRef.onDisconnect();
                                startActivity(gotoMain);finish();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
                else {
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                    finish();
                }
            }
        },SPLASH_TIME);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
