package com.tdl.todolistmanandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.SelectPeopleAdapter;
import com.tdl.todolistmanandroid.item.SelectPeopleItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by HyunWook Kim on 2017-04-20.
 */

public class SelectPeopleActivity extends AppCompatActivity{

    @BindView(R.id.toolbar) Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_people);

        Toolbar searchBar = (Toolbar) findViewById(R.id.searchToolbar);
        searchBar.setTitle("그룹선택");
        setSupportActionBar((Toolbar) findViewById(R.id.searchToolbar));
        if(getSupportActionBar() !=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            searchBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selectPeopleRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<SelectPeopleItem> items = new ArrayList<>();
        SelectPeopleItem[] item = new SelectPeopleItem[500];
        for(int i=0;i<5;i++){
             item[i] = new SelectPeopleItem(R.drawable.kakao_default_profile_image,i+"");
            items.add(item[i]);
        }

        recyclerView.setAdapter(new SelectPeopleAdapter(getApplicationContext(), items,getIntent().getIntExtra("status",-1000)));
    }
}
