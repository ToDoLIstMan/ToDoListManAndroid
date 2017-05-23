package com.tdl.todolistmanandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.AddPlanAdapter;
import com.tdl.todolistmanandroid.adapter.FormatDetailAdapter;
import com.tdl.todolistmanandroid.adapter.FormatManageAdapter;
import com.tdl.todolistmanandroid.adapter.MakeFormatAdapter;
import com.tdl.todolistmanandroid.database.format;
import com.tdl.todolistmanandroid.database.work;
import com.tdl.todolistmanandroid.item.AddPlanItem;
import com.tdl.todolistmanandroid.item.FormatDetailItem;
import com.tdl.todolistmanandroid.item.FormatManageItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

/**
 * Created by HyunWook Kim on 2017-05-20.
 */

public class FormatDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.formatPlanList) RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
     Context mContext;
    List<FormatDetailItem> items;
    List<FormatDetailItem> formatItems;
    int groupId;
    String excDate;
    int formatId;
    private Intent getIntent;
    String formatName = "";
    int lastGrpId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_format_detail);
        ButterKnife.bind(this);
        mContext = this;
        getIntent = getIntent();



        makeToolbar();
        initList();
    }

    private void makeToolbar() {
        toolbar.setTitle(getIntent.getStringExtra("formatName"));
        setSupportActionBar(toolbar);
        if(getSupportActionBar() !=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initList() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        items = new ArrayList<>();
        lastGrpId = getIntent().getIntExtra("lastGrpId",1);
        Log.e("lastGrpId",""+lastGrpId);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference myRef = database.getReference().child("format").child(FirebaseAuth.getInstance().
                getCurrentUser().getUid()).child(getIntent.getStringExtra("formatName"))
                /*.child(getIntent.getStringExtra("formatId"))*/;
        Log.e("adf",getIntent().getStringExtra("formatName"));
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("dddd","");
                format f = dataSnapshot.getValue(format.class);
                items.add(new FormatDetailItem(f.getId(), f.getPlanName(),f.getDetail(),f.getStartTime(),f.getEndTime()));

                lastGrpId=items.size();
                recyclerView.setAdapter(new FormatDetailAdapter(mContext, items, getIntent.getStringExtra("formatName")));

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {




                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit,menu);
        return true;
    }



//    public void sendIntent(int position, int status){
//        Intent gotoA = new Intent(FormatDetailActivity.this,DetailActivity.class);
//        gotoA.putExtra("status",status);
//        if(status==0 || status==4){
//            gotoA.putExtra("groupId",position);
//        }
//        else
//            gotoA.putExtra("position",position);
//        startActivityForResult(gotoA,777);
//
//    }

}
