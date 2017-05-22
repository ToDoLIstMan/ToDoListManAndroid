package com.tdl.todolistmanandroid.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.MainAdapter;
import com.tdl.todolistmanandroid.database.group;
import com.tdl.todolistmanandroid.database.user;
import com.tdl.todolistmanandroid.item.MainItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickGroupActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    Context mContext;
    RecyclerView.LayoutManager layoutManager;
    List<MainItem> items;
    private int lastGroupId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickgroup);
        ButterKnife.bind(this);
        mContext = this;

        makeToolbar();

        fab.setOnClickListener(this);
    }
    /**
     * Toolbar 생성 메소드
     */
    private void makeToolbar() {
        toolbar.setTitle("전체그룹보기");
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        if(getSupportActionBar() !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
     }

    @Override
    protected void onResume() {
        super.onResume();
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
                                Log.e("df",dataSnapshot.getValue().toString());
                                group group = dataSnapshot.getValue(group.class);
                                MainItem item = new MainItem(group.getId(), group.getMasterUid(),group.getGroupName(),group.getMemberUid(),group.getMemberName());
                                items.add(item);
                                recyclerView.setAdapter(new MainAdapter(mContext, items));

                                progressBar.setVisibility(View.GONE);
                                if (lastGroupId < group.getId())
                                    lastGroupId = group.getId();

                            }
                        });
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        recyclerView.getAdapter().notifyDataSetChanged();
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
    public void onClick(View v) {
        if(v.getId()==R.id.fab){
            LayoutInflater inflater = LayoutInflater.from(this);
            View promptView = inflater.inflate(R.layout.edittext_dialog,null);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("그룹 추가");
            alert.setView(promptView);

            final EditText input = (EditText)promptView.findViewById(R.id.editGroup);

            promptView.findViewById(R.id.editName).setVisibility(View.GONE);
            input.requestFocus();
            input.setHint("그룹명을 입력하세요.");
            alert.setView(promptView);

            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference[] myRef = {database.getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid())};
                    myRef[0].addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Map<String,Object> g = new HashMap<>();
                            List<Integer> masterGroups = dataSnapshot.getValue(user.class).getMasterGroups();
                            List<String> masterGroupName =dataSnapshot.getValue(user.class).getMasterGroupName();

                            masterGroups.add(lastGroupId+1);
                            masterGroupName.add(input.getText().toString());

                            g.put("masterGroups",masterGroups);
                            g.put("masterGroupName",masterGroupName);
                            g.put("groups",masterGroups);
                            g.put("groupName",masterGroupName);

                            dataSnapshot.getRef().updateChildren(g);

                            myRef[0] = database.getReference().child("group").child(String.valueOf(lastGroupId+1));
                            List<String> memberName = new ArrayList<>();
                            memberName.add(dataSnapshot.getValue(user.class).getName());
                            List<String> memberUid = new ArrayList<>();
                            memberUid.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            group group = new group(lastGroupId+1, FirebaseAuth.getInstance().getCurrentUser().getUid(),input.getText().toString(),memberName,memberUid);
                            myRef[0].setValue(group);
                            Toast.makeText(mContext, "추가되었습니다.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
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
