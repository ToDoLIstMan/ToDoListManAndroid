package com.tdl.todolistmanandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.TimeListAdapter;
import com.tdl.todolistmanandroid.item.TimeListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songmho on 2017. 4. 5..
 */

public class PreviewListAcitivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    RecyclerView.LayoutManager layoutManager;
    Context mContext;

    List<TimeListItem> lists;
    HashMap<Integer,String> timeLists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timelist);
        ButterKnife.bind(this);
        mContext = this;

        makeToolbar();

        initList();
    }


    /**
     * Toolbar 생성 메소드
     */
    private void makeToolbar() {
        toolbar.setTitle(getIntent().getStringExtra("date"));
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
        lists = new ArrayList<>();
        timeLists = new HashMap<>();

        List<String> doPeople = new ArrayList<>();
        List<Boolean> isDone = new ArrayList<>();
        doPeople.add("test #1");
        doPeople.add("test #2");
        doPeople.add("test #3");
        isDone.add(true);
        isDone.add(false);
        isDone.add(false);

        lists.add(new TimeListItem("16:00", "17:00", "Work #1", "detail is detail.", "Supervisor #1", doPeople, isDone));
        lists.add(new TimeListItem("17:00", "18:00", "Work #1", "detail is detail.", "Supervisor #1", doPeople, isDone));
        lists.add(new TimeListItem("17:00", "18:00", "Work #1", "detail is detail.", "Supervisor #1", doPeople, isDone));
        lists.add(new TimeListItem("17:00", "18:00", "Work #1", "detail is detail.", "Supervisor #1", doPeople, isDone));
        lists.add(new TimeListItem("18:00", "20:00", "Work #1", "detail is detail.", "Supervisor #1", doPeople, isDone));


        for(int i = 0;i<lists.size();i++) {
            if (i > 0) {
                if(!lists.get(i-1).getStartTime().equals(lists.get(i).getStartTime()))
                    timeLists.put(timeLists.size()+(i),lists.get(i).getStartTime());
            }
            else if(i==0)
                timeLists.put(0,lists.get(i).getStartTime());
        }


        recyclerView.setAdapter(new TimeListAdapter(mContext,lists,timeLists));
    }
}

