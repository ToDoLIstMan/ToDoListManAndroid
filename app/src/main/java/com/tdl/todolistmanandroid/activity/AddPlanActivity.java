package com.tdl.todolistmanandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.TimeListAdapter;
import com.tdl.todolistmanandroid.item.TimeListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by songm on 2017-04-03.
 */

public class AddPlanActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        ButterKnife.bind(this);
        mContext = this;

        makeToolbar();
        initList();

    }

    /**
     * Toolbar 생성 메소드
     */
    private void makeToolbar() {
        toolbar.setTitle("일정전송하기");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() !=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 데이터 불어와 리스트 초기화하는 메소드
     */
    private void initList() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);


  //      recyclerView.setAdapter(new TimeListAdapter(mContext,lists));
    }

    @OnClick(R.id.fab)
    void fabClick(){
        Toast.makeText(mContext, "준비중...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_plan,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_send){
            Toast.makeText(mContext, "준비중...", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
