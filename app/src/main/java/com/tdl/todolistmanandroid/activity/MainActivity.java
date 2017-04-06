package com.tdl.todolistmanandroid.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.MainAdapter;
import com.tdl.todolistmanandroid.database.group;
import com.tdl.todolistmanandroid.item.MainItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.navView) NavigationView navView;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    Context mContext;
    RecyclerView.LayoutManager layoutManager;
    List<MainItem> items;
    private int lastGroupId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;

        makeToolbar();


        fab.setOnClickListener(this);
        navView.setNavigationItemSelectedListener(this);
    }
    /**
     * Toolbar 생성 메소드
     */
    private void makeToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        if(getSupportActionBar() !=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            drawerToggle.syncState();
        }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(navView);
                }
            });
    }

    @Override
    protected void onStart() {
        super.onStart();
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
                DatabaseReference myRef = database.getReference().child("group");

                myRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                group group = dataSnapshot.getValue(group.class);
                                MainItem item = new MainItem(group.getId(), group.getGroupName());
                                items.add(item);
                                recyclerView.setAdapter(new MainAdapter(mContext, items));

                                progressBar.setVisibility(View.GONE);
                                if (lastGroupId < group.getId())
                                    lastGroupId = group.getId();

                                Log.e("" + group.getId(), group.getGroupName());
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.send:
                startActivity(new Intent(MainActivity.this,AddPlanActivity.class));
                drawerLayout.closeDrawer(navView);
                break;
            case R.id.mypage:
                drawerLayout.closeDrawer(navView);
                break;
            case R.id.setup:
                drawerLayout.closeDrawer(navView);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fab){
            LayoutInflater inflater = LayoutInflater.from(this);
            View promptView = inflater.inflate(R.layout.edittext_dialog,null);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("그룹 추가");
            alert.setView(promptView);

            final EditText input = (EditText)promptView.findViewById(R.id.editGroup);
            input.requestFocus();
            input.setHint("그룹명을 입력하세요.");
            alert.setView(promptView);

            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference().child("group").child(String.valueOf(lastGroupId+1));
                    group group = new group(lastGroupId+1,input.getText().toString());
                    myRef.setValue(group);
                }
            }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = alert.create();
            dialog.show();

        }
    }
}
