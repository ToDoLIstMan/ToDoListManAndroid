package com.tdl.todolistmanandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.tdl.todolistmanandroid.adapter.MakeFormatAdapter;
import com.tdl.todolistmanandroid.database.format;
import com.tdl.todolistmanandroid.item.MakeFormatItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FormatManageActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.formatList) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    Context mContext;
    RecyclerView.LayoutManager layoutManager;
    List<MakeFormatItem> items;
//    private int lastFormatId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_format_manage);
        ButterKnife.bind(this);
        mContext = this;

        makeToolbar();

        fab.setOnClickListener(this);
    }
    /**
     * Toolbar 생성 메소드
     */
    private void makeToolbar() {
        toolbar.setTitle("포멧관리하기");
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        if(getSupportActionBar() !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }

    /**
     * 리스트 초기화 메소드
     */
    private void initList() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("format");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("df",dataSnapshot.getValue().toString());
                        format format = dataSnapshot.getValue(format.class);
                        MakeFormatItem item = new MakeFormatItem(format.getFormatName(), format.getMasterUid(), format.getFormatId()/*, format.getWork()*/);
                        items.add(item);
                        recyclerView.setAdapter(new MakeFormatAdapter(mContext, items));

                        progressBar.setVisibility(View.GONE);
//                        if (lastFormatId < group.getId())
//                            lastFormatId = group.getId();

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
    public void onClick(View v) {
        if(v.getId()==R.id.fab) {

//            Toast.makeText(mContext, "준비중!", Toast.LENGTH_SHORT).show();
            Intent makeFormat = new Intent(this,MakeFormatActivity.class);
            startActivity(makeFormat);
        }
    }
}