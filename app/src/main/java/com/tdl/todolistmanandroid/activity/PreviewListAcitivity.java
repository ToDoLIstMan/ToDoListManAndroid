package com.tdl.todolistmanandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.TimeListAdapter;
import com.tdl.todolistmanandroid.database.work;
import com.tdl.todolistmanandroid.item.TimeListItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songmho on 2017. 4. 5..
 */

public class PreviewListAcitivity extends AppCompatActivity {
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    RecyclerView.LayoutManager layoutManager;
    Context mContext;

    List<TimeListItem> lists;
    HashMap<Integer,String> timeLists;
    String pickday;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timelist);
        ButterKnife.bind(this);
        mContext = this;

        pickday = getIntent().getStringExtra("date");

        makeToolbar();

        initList();
    }


    /**
     * Toolbar 생성 메소드
     */
    private void makeToolbar() {
        toolbar.setTitle(pickday);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() !=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 데이터 불어와 리스트 초기화하는 메소드
     */
    private void initList() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        lists = new ArrayList<>();
        timeLists = new HashMap<>();

        final List<String> doPeople = new ArrayList<>();
        final List<Boolean> isDone = new ArrayList<>();
        doPeople.add("test #1");
        doPeople.add("test #2");
        doPeople.add("test #3");
        isDone.add(true);
        isDone.add(false);
        isDone.add(false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("work").child(String.valueOf(getIntent().getIntExtra("groupId",-1))).child(pickday);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                work work = dataSnapshot.getValue(work.class);
                Log.e("asdf",work.getTitle());
                lists.add(new TimeListItem(work.getStartTime(),work.getEndTime(),work.getTitle(),work.getDetail(),"adf",doPeople,null,isDone));

                recyclerView.setAdapter(new TimeListAdapter(mContext,lists,timeLists));
                progressBar.setVisibility(View.GONE);
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
}

