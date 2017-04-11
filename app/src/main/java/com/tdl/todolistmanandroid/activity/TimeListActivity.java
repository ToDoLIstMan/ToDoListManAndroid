package com.tdl.todolistmanandroid.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.Receiver.AlarmReceiver;
import com.tdl.todolistmanandroid.adapter.TimeListAdapter;
import com.tdl.todolistmanandroid.database.group;
import com.tdl.todolistmanandroid.database.work;
import com.tdl.todolistmanandroid.item.TimeListItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    @BindView(R.id.progressBar) ProgressBar progressBar;
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


    }

    @Override
    protected void onStart() {
        super.onStart();
        initList();
    }

    /**
     * Toolbar 생성 메소드
     */
    private void makeToolbar() {
        toolbar.setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(toolbar);
        if(getSupportActionBar() !=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
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
/*
        lists.add(new TimeListItem("11:00", "12:00", "Work #1", "detail is detail.", "Supervisor #1", doPeople, isDone));
        lists.add(new TimeListItem("17:00", "18:00", "Work #1", "detail is detail.", "Supervisor #1", doPeople, isDone));
        lists.add(new TimeListItem("17:00", "18:00", "Work #1", "detail is detail.", "Supervisor #1", doPeople, isDone));
        lists.add(new TimeListItem("17:00", "18:00", "Work #1", "detail is detail.", "Supervisor #1", doPeople, isDone));
        lists.add(new TimeListItem("18:00", "20:00", "Work #1", "detail is detail.", "Supervisor #1", doPeople, isDone));
*/

        Date today = new Date();
        SimpleDateFormat sDF = new SimpleDateFormat("yyyy-M-dd");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("work").child(String.valueOf(getIntent().getIntExtra("groupId",-1))).child("2017-4-5");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                work work = dataSnapshot.getValue(work.class);
                Log.e("asdf",work.getTitle());
                lists.add(new TimeListItem(work.getStartTime(),work.getEndTime(),work.getTitle(),work.getDetail(),"adf",doPeople,isDone));
                recyclerView.setAdapter(new TimeListAdapter(mContext,lists,timeLists));
                progressBar.setVisibility(View.GONE);

                String[] arr;
                for(int i= 0;i<lists.size();i++) {
                    arr = lists.get(i).getStartTime().split(":");
                    new AlarmHATT(mContext).Alarm(i,Integer.valueOf(arr[0]),Integer.valueOf(arr[1]),lists.get(i).getTitle());
                }

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


    public class AlarmHATT {
        private Context context;
        public AlarmHATT(Context context) {
            this.context=context;
        }
        public void Alarm(int count,int hour,int min, String title) {
            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(TimeListActivity.this, AlarmReceiver.class);
            intent.putExtra("num",count);
            intent.putExtra("title",title);
            Log.e("adsf","nkjkjk");
            PendingIntent sender = PendingIntent.getBroadcast(TimeListActivity.this, count, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            //알람시간 calendar에 set해주기
            calendar.set(calendar.get(Calendar.YEAR),(calendar.get(Calendar.MONTH)),calendar.get(Calendar.DATE),hour,min,0);
            Log.e("month",""+calendar.get(Calendar.MONTH));
            calendar.add(Calendar.SECOND,10);

            if(Build.VERSION.SDK_INT >= 23 ){
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                Log.e("adsf","11111");
        }
            else {
                if (Build.VERSION.SDK_INT >= 19) {
                    am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);Log.e("adsf","22222");

                } else {
                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);Log.e("adsf","33333");

                }
            }

        }
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
                            //Toast.makeText(mContext, ""+year+" . "+(month+1)+" . "+dayOfMonth, Toast.LENGTH_SHORT).show();

                            Intent gotoPreview = new Intent(TimeListActivity.this,PreviewListAcitivity.class);
                            gotoPreview.putExtra("groupId",getIntent().getIntExtra("groupId",-1));
                            gotoPreview.putExtra("date",""+year+"-"+(month+1)+"-"+dayOfMonth);
                            startActivity(gotoPreview);
                        }
                        else{
                            if(dayOfMonth<=curDay) {
                            //    Toast.makeText(mContext, "" + year + " . " + (month + 1) + " . " + dayOfMonth, Toast.LENGTH_SHORT).show();


                                Intent gotoPreview = new Intent(TimeListActivity.this,PreviewListAcitivity.class);
                                gotoPreview.putExtra("date",""+year+"-"+(month+1)+"-"+dayOfMonth);
                                startActivity(gotoPreview);
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
