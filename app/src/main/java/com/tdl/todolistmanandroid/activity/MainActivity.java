package com.tdl.todolistmanandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.MainAdapter;
import com.tdl.todolistmanandroid.item.MainItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.navView) NavigationView navView;

    Context mContext;
    RecyclerView.LayoutManager layoutManager;
    List<MainItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;

        makeToolbar();
        initList();

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



    /**
     * 리스트 초기화 메소드
     */
    private void initList() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();
        for(int i = 0; i< 3;i++)
            items.add(new MainItem("test"));

        recyclerView.setAdapter(new MainAdapter(mContext,items));
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
}
