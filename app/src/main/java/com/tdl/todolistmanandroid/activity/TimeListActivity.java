package com.tdl.todolistmanandroid.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.Toast;

import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.TimeListAdapter;
import com.tdl.todolistmanandroid.item.TimeListItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songm on 2017-03-21.
 */

public class TimeListActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    RecyclerView.LayoutManager layoutManager;
    Context mContext;

    List<TimeListItem> lists;
    HashMap<Integer,String> timeLists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timelist);
        ButterKnife.bind(this);
        mContext=this;

        makeToolbar();

        initList();
    }

    /**
     * Toolbar 생성 메소드
     */
    private void makeToolbar() {
        toolbar.setTitle(getIntent().getStringExtra("title"));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_time,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_date){
            final int curYear = Calendar.getInstance().get(Calendar.YEAR);
            final int curMon = Calendar.getInstance().get(Calendar.MONTH);
            final int curDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

            Dialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    if(year>curYear){
                        Toast.makeText(mContext, "잘못 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        if(month>curMon){
                            Toast.makeText(mContext, "잘못 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                        }else if(month<curMon){
                            Toast.makeText(mContext, ""+year+" . "+(month+1)+" . "+dayOfMonth, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(dayOfMonth<=curDay) {
                                Toast.makeText(mContext, "" + year + " . " + (month + 1) + " . " + dayOfMonth, Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(mContext, "잘못 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            },curYear,curMon,curDay);
            datePicker.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
