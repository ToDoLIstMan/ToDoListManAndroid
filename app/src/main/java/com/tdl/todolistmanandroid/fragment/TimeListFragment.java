package com.tdl.todolistmanandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import com.tdl.todolistmanandroid.activity.TimeListActivity;
import com.tdl.todolistmanandroid.adapter.ListAdapter;
import com.tdl.todolistmanandroid.adapter.TimeTabAdapter;
import com.tdl.todolistmanandroid.database.work;
import com.tdl.todolistmanandroid.item.TimeListItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songmho on 2017. 4. 14..
 */

public class TimeListFragment extends Fragment {
    public TimeListFragment(){}

    @BindView(R.id.noneWork) FrameLayout noneWork;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    RecyclerView.LayoutManager layoutManager;
    List<TimeListItem> lists;
    HashMap<Integer,String> timeLists;

    Date today;
    SimpleDateFormat sDF;

    FirebaseDatabase database ;
    DatabaseReference myRef;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout v = (FrameLayout) inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this,v);

        today = new Date();
        sDF = new SimpleDateFormat("yyyy-M-dd");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("work").child(String.valueOf(getActivity().getIntent().getIntExtra("groupId",-1))).child(sDF.format(today).toString());

        hasData();

        return v;
    }




    private void hasData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("work").child(String.valueOf(getActivity().getIntent().getIntExtra("groupId",-1))).child(sDF.format(today).toString());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    initList(getArguments().getString("status"));
                }else {
                    noneWork.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void initList(final String status) {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        lists = new ArrayList<>();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Toast.makeText(getActivity(), "adsfdsaf", Toast.LENGTH_SHORT).show();
                    Log.e("asdf","asdfadsf");
                }else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                work work = dataSnapshot.getValue(work.class);
                lists.add(new TimeListItem(work.getStartTime(),work.getEndTime(),work.getTitle(),work.getDetail(),work.getId(),work.getName(),work.getuId(),work.getIsDone()));

                switch (status){
                    case "done":
                        for(int i = 0;i<work.getuId().size();i++){
                            if(work.getuId().get(i).equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && work.getIsDone().get(i)) {
                                lists.add(new TimeListItem(work.getStartTime(), work.getEndTime(), work.getTitle(), work.getDetail(), work.getId(), work.getName(), work.getuId(), work.getIsDone()));
                                break;
                            }
                        }
                        break;
                    case "doing":
                        for(int i = 0;i<work.getuId().size();i++){
                        if(work.getuId().get(i).equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && !work.getIsDone().get(i)) {
                            lists.add(new TimeListItem(work.getStartTime(), work.getEndTime(), work.getTitle(), work.getDetail(), work.getId(), work.getName(), work.getuId(), work.getIsDone()));
                            break;
                        }
                    }
                        break;
                    case "whole":
                        lists.add(new TimeListItem(work.getStartTime(), work.getEndTime(), work.getTitle(), work.getDetail(), work.getId(), work.getName(), work.getuId(), work.getIsDone()));
                        break;
                }

                recyclerView.setAdapter(new ListAdapter(getActivity(),lists));
                progressBar.setVisibility(View.GONE);
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
