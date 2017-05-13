package com.tdl.todolistmanandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.AddPlanAdapter;
import com.tdl.todolistmanandroid.database.work;
import com.tdl.todolistmanandroid.item.AddPlanItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    String[] memberNames;
    String[] memberUids;
    List<AddPlanItem> items;
    int groupId;

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


        items = new ArrayList<>();

        recyclerView.setAdapter(new AddPlanAdapter(mContext,items));
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
            Log.e("dfdf","입력한다!");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child("work");
            for(int i =0; i<items.size();i++) {
                Log.e("몇번째?", ""+items.get(i).getIsDone().size());
                myRef.child("" + groupId).child(sdf.format(date)).child(""+i).setValue(
                        new work(i, items.get(i).getTitle(), items.get(i).getDetail(),
                                items.get(i).getStartTime(), items.get(i).getEndTime(),
                                items.get(i).getName(), items.get(i).getuId(), items.get(i).getIsDone()));
            }
            Toast.makeText(mContext, "일정이 전송되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void sendIntent(int position, int status){
        Intent  gotoA = new Intent(AddPlanActivity.this,SelectPeopleActivity.class);
        gotoA.putExtra("status",status);
        if(status==0 || status==4){
            gotoA.putExtra("groupId",position);
        }
        else
            gotoA.putExtra("position",position);
        startActivityForResult(gotoA,777);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 777) {
            if (resultCode == 778) {
                memberNames = new String[data.getStringArrayExtra("memberName").length];
                memberUids = new String[data.getStringArrayExtra("memberUid").length];
                memberNames = data.getStringArrayExtra("memberName");
                memberUids = data.getStringArrayExtra("memberUid");

                ((AddPlanAdapter) recyclerView.getAdapter()).setWorker(memberNames,memberUids);
            } else if (resultCode == 779)
                ((AddPlanAdapter) recyclerView.getAdapter()).setFormat(data.getStringExtra("itemTitle"));
            else if (resultCode == 780) {
                ((AddPlanAdapter) recyclerView.getAdapter()).setGroup(data.getStringExtra("itemTitle"));
                groupId = Integer.valueOf(data.getIntExtra("groupId", -1));
                ((AddPlanAdapter) recyclerView.getAdapter()).setGroupId(Integer.valueOf(data.getIntExtra("groupId", -1)));
            }
            else if(resultCode==781){
                memberNames = new String[data.getStringArrayExtra("memberName").length];
                memberUids = new String[data.getStringArrayExtra("memberUid").length];
                memberNames = data.getStringArrayExtra("memberName");
                memberUids = data.getStringArrayExtra("memberUid");

                ((AddPlanAdapter) recyclerView.getAdapter()).setTodayWorker(memberNames,memberUids);

            }

            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
