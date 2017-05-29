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

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.AddPlanAdapter;
import com.tdl.todolistmanandroid.database.format;
import com.tdl.todolistmanandroid.database.work;
import com.tdl.todolistmanandroid.item.AddPlanItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

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

    String[] curMemberNames;
    String[] curMemberUids;
    List<AddPlanItem> items;
    List<AddPlanItem> formatItems;
    int groupId;
    String excDate;
    int formatId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
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


            formatItems = new ArrayList<>();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference[] myRef = new DatabaseReference[1];

            if(((AddPlanAdapter)recyclerView.getAdapter()).getExcTime().equals("")||
                    ((AddPlanAdapter)recyclerView.getAdapter()).getGroup().equals("")||
                    (((recyclerView.getAdapter()).getItemCount()==2)&&
                            (((AddPlanAdapter)recyclerView.getAdapter()).getFormat().equals("")))){
                Toast.makeText(this, "내용을 마저 채우세요", Toast.LENGTH_SHORT).show();
            } else {

                myRef[0] = database.getReference().child("format").
                        child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                        child(((AddPlanAdapter) recyclerView.getAdapter()).getFormat());
                myRef[0].addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        if (!((AddPlanAdapter) recyclerView.getAdapter()).getFormat().equals("")) {
                            format format = dataSnapshot.getValue(format.class);


                            List<String> a = new ArrayList<>(Arrays.asList(memberNames));
                            List<String> b = new ArrayList<>(Arrays.asList(memberUids));

                            Log.e("asdasdf", "" + a.size());
                            List<Boolean> isDone = new ArrayList<>();
                            for (int j = 0; j < a.size(); j++)
                                isDone.add(false);
                            Log.e("asdasdf", "" + format.getId());

                            Log.e("asdasdf", "" + format.getPlanName());
                            Log.e("asdasdf", "" + format.getDetail());
                            Log.e("asdasdf", "" + format.getStartTime());
                            Log.e("asdasdf", "" + format.getEndTime());

                            formatItems.add(new AddPlanItem(format.getId(), format.getPlanName(), format.getDetail(),
                                    format.getStartTime(), format.getEndTime(), a, b, isDone));


                        }

                        items.addAll(0, formatItems);

                        myRef[0] = database.getReference().child("work");
                        for (int i = 0; i < items.size(); i++) {
                            Log.e("몇번째?", "" + items.get(i).getIsDone().size());
                            myRef[0].child("" + groupId).child(((AddPlanAdapter) recyclerView.getAdapter()).getExcTime()).child("" + i).setValue(
                                    new work(i, items.get(i).getTitle(), items.get(i).getDetail(),
                                            items.get(i).getStartTime(), items.get(i).getEndTime(),
                                            items.get(i).getName(), items.get(i).getuId(), items.get(i).getIsDone()));
                        }

                        Toast.makeText(mContext, "일정이 전송되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
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


                ((AddPlanAdapter) recyclerView.getAdapter()).clearWorker();
                ((AddPlanAdapter) recyclerView.getAdapter()).setWorker(memberNames,memberUids);
            } else if (resultCode == 779) {
                ((AddPlanAdapter) recyclerView.getAdapter()).setFormat(data.getStringExtra("itemTitle"));
            } else if (resultCode == 780) {
                ((AddPlanAdapter) recyclerView.getAdapter()).setGroup(data.getStringExtra("itemTitle"));
                ((AddPlanAdapter) recyclerView.getAdapter()).setGroupId(data.getIntExtra("groupId",-1));
                groupId = Integer.valueOf(data.getIntExtra("groupId", -1));
            }
            else if(resultCode==781){
                curMemberNames = new String[data.getStringArrayExtra("memberName").length];
                curMemberUids = new String[data.getStringArrayExtra("memberUid").length];
                curMemberNames = data.getStringArrayExtra("memberName");
                curMemberUids = data.getStringArrayExtra("memberUid");

                ((AddPlanAdapter) recyclerView.getAdapter()).clearTodayWorker();
                ((AddPlanAdapter) recyclerView.getAdapter()).setTodayWorker(curMemberNames,curMemberUids);

            }

            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
