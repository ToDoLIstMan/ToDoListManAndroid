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
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.ListAdapter;
import com.tdl.todolistmanandroid.database.work;
import com.tdl.todolistmanandroid.item.TimeListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

/**
 * Created by songmho on 2017. 4. 5..
 */

public class PreviewListAcitivity extends AppCompatActivity {
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.noneWork)
    FrameLayout noneWork;
    RecyclerView.LayoutManager layoutManager;
    Context mContext;

    List<TimeListItem> lists;
    HashMap<Integer,String> timeLists;
    String pickday;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_preview);
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

        Log.e("asdf",""+getIntent().getIntExtra("groupId",-1));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("work").child(String.valueOf(getIntent().getIntExtra("groupId",-1))).child(pickday);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Log.e("asdf","qoeoiwiwiewiew");
                    lists.clear();

                    myRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Log.e("asdfasd","asdf");
                            if(dataSnapshot.exists()){
                                work work = dataSnapshot.getValue(work.class);
                                lists.add(new TimeListItem(work.getStartTime(),work.getEndTime(),work.getTitle(),work.getDetail(),work.getId(),work.getName(),work.getuId(),work.getIsDone()));

                                recyclerView.setAdapter(new ListAdapter(mContext,lists,getIntent().getIntExtra("groupId",-1),pickday));

                                progressBar.setVisibility(View.GONE);
                            }
                            else{
                                Log.e("asdf","-00");
                                noneWork.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            Log.e("asdf","1");

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            Log.e("asdf","2");

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                            Log.e("asdf","3");

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("asdf","4");

                        }
                    });
                }else{

                    Log.e("asdf","-1");
                    noneWork.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}

