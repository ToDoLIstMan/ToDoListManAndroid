package com.tdl.todolistmanandroid.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.Receiver.AlarmReceiver;
import com.tdl.todolistmanandroid.adapter.TimeTabAdapter;
import com.tdl.todolistmanandroid.database.user;
import com.tdl.todolistmanandroid.database.work;
import com.tdl.todolistmanandroid.item.TimeListItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songm on 2017-03-21.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
  @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.navView) NavigationView navView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    Context mContext;
    TimeTabAdapter timeTabAdapter;

    List<TimeListItem> lists;
    FirebaseDatabase database;

    List<Integer> navGroupId = new ArrayList<>();
    List<String> navGroupName = new ArrayList<>();

    int curGroupId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext=this;

        try {
            new AlarmHATT(mContext).Alarm(1, 4, 39, "hi!");
        }catch (Exception e){
            Log.e("error : ",e.toString());
        }

        makeToolbar();
        makeViewPager();

        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        makeDrawer();
    }

    private void makeDrawer() {
        navView.getMenu().clear();
        final SubMenu itema = navView.getMenu().addSubMenu("내그룹");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("user");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if((FirebaseAuth.getInstance().getCurrentUser().getUid()).equals(dataSnapshot.getKey())) {
                    user u = dataSnapshot.getValue(user.class);
           //         Toast.makeText(mContext, "추가되었습니다", Toast.LENGTH_SHORT).show();
                    List<String> a = u.getGroupName();
                    List<Integer> b = u.getGroups();

                    a.removeAll(Collections.singleton(null));
                    b.removeAll(Collections.singleton(null));
                    navGroupId.addAll(b);
                    navGroupName.addAll(a);
                    for (String i : a)
                        itema.add(i).setTitle(i);
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
        navView.inflateMenu(R.menu.main_drawer);
    }


    /**
     * Toolbar 생성 메소드
     */
    private void makeToolbar() {
        Log.e("asdf",getIntent().getIntExtra("groupUid",-1)+"");
        if(getIntent().getIntExtra("groupUid",-1)>-1) {
            curGroupId = getIntent().getIntExtra("groupUid",-1);
            toolbar.setTitle(getIntent().getStringExtra("groupName"));
        }
        else{
            Toast.makeText(mContext, "들어가있는 그룹이 없습니다. 그룹추가 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                startActivity(new Intent(MainActivity.this,PickGroupActivity.class));
                }
            },500);
        }
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

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



    private void makeViewPager(){
        tabLayout.addTab(tabLayout.newTab().setText("전체"));
        tabLayout.addTab(tabLayout.newTab().setText("실시"));
        tabLayout.addTab(tabLayout.newTab().setText("미실시"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        addViewPagerAdapter();
    }

    private void addViewPagerAdapter() {
        timeTabAdapter = new TimeTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),curGroupId);
        viewPager.setAdapter(timeTabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("curPosition aa: ",""+tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    public class AlarmHATT {
        private Context context;
        public AlarmHATT(Context context) {
            this.context=context;
            Log.e("asdfadsf","asdf");
        }
        public void Alarm(int count,int hour,int min, String title) {
            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
            intent.putExtra("userUid",FirebaseAuth.getInstance().getCurrentUser().getUid());
            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, count, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            //알람시간 calendar에 set해주기

            if(calendar.get(Calendar.HOUR)<4)
                calendar.set(calendar.get(Calendar.YEAR),(calendar.get(Calendar.MONTH)),calendar.get(Calendar.DATE),4,0,0);
            else
                calendar.set(calendar.get(Calendar.YEAR),(calendar.get(Calendar.MONTH))+1,calendar.get(Calendar.DATE),4,0,0);
            Log.e("month",""+calendar.get(Calendar.MONTH));


            Log.e("month",""+calendar.get(Calendar.MONTH));
            calendar.add(Calendar.SECOND,10);

            if(Build.VERSION.SDK_INT >= 23 ){
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
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
        Log.e("Asdf","Asdf");
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

                            Intent gotoPreview = new Intent(MainActivity.this,PreviewListAcitivity.class);
                            gotoPreview.putExtra("groupId",curGroupId);
                            if (0<=month && month<9)
                                gotoPreview.putExtra("date",""+year+"-0"+(month+1)+"-"+dayOfMonth);
                            else
                                gotoPreview.putExtra("date",""+year+"-"+(month+1)+"-"+dayOfMonth);
                            startActivity(gotoPreview);
                        }
                        else{
                            if(dayOfMonth<=curDay) {
                            //    Toast.makeText(mContext, "" + year + " . " + (month + 1) + " . " + dayOfMonth, Toast.LENGTH_SHORT).show();


                                Intent gotoPreview = new Intent(MainActivity.this,PreviewListAcitivity.class);
                                gotoPreview.putExtra("groupId",curGroupId);
                                if (0<=month && month<9)
                                    gotoPreview.putExtra("date",""+year+"-0"+(month+1)+"-"+dayOfMonth);
                                else
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        for(int i = 0;i<navGroupName.size();i++){
            if(navGroupName.get(i).equals(item.getTitle())){
                curGroupId = navGroupId.get(i);
                addViewPagerAdapter();
                toolbar.setTitle(navGroupName.get(i));
                drawerLayout.closeDrawer(navView);
            }
        }

        switch (item.getItemId()){
            case R.id.send:
                startActivity(new Intent(MainActivity.this,AddPlanActivity.class));
                drawerLayout.closeDrawer(navView);
                break;
            case R.id.format:
                startActivity(new Intent(MainActivity.this,FormatManageActivity.class));
                drawerLayout.closeDrawer(navView);
                break;
            case R.id.group:
                startActivity(new Intent(MainActivity.this,PickGroupActivity.class));
                drawerLayout.closeDrawer(navView);
                break;
            case R.id.mypage:
                startActivity(new Intent(MainActivity.this,MyPageActivity.class));
                drawerLayout.closeDrawer(navView);
                break;
            case R.id.setup:
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                drawerLayout.closeDrawer(navView);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
