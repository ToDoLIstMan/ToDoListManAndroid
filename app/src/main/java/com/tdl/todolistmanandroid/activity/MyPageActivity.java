package com.tdl.todolistmanandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.AddPlanAdapter;
import com.tdl.todolistmanandroid.adapter.MyPageAdapter;
import com.tdl.todolistmanandroid.database.user;
import com.tdl.todolistmanandroid.item.MyPageItem;
import com.tdl.todolistmanandroid.item.MypageHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

/**
 * Created by songm on 2017-05-15.
 */

public class MyPageActivity extends AppCompatActivity{

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    Context mContext;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_mypage);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        final ArrayList<MyPageItem> groupItems = new ArrayList<>();
        final ArrayList<MyPageItem> masterItems = new ArrayList<>();

        mContext = this;
        makeToolbar();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user u = dataSnapshot.getValue(user.class);
                Log.e("ddd",u.getName());
                MypageHeader mypageHeader = new MypageHeader(u.getName(),FirebaseAuth.getInstance().getCurrentUser().getUid(),u.getRank());

                for(int i = 0; i<u.getMasterGroups().size(); i++)
                    masterItems.add(new MyPageItem(u.getMasterGroups().get(i),u.getMasterGroupName().get(i)));

                for(int i = 0; i<u.getGroups().size(); i++) {
                    if(!u.getMasterGroups().contains(u.getGroups().get(i)))
                        groupItems.add(new MyPageItem(u.getGroups().get(i), u.getGroupName().get(i)));
                }

//                Log.e("",groupItems.get(0).getName());

                layoutManager = new LinearLayoutManager(mContext);
                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setAdapter(new MyPageAdapter(mContext,mypageHeader,groupItems,masterItems));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }



    /**
     * User 데이터를 불러오는 메소드
     */

    /**
     * Toolbar 생성 메소드
     */
    private void makeToolbar() {
        toolbar.setTitle("마이페이지");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(getSupportActionBar() !=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_mypage,menu);
//        return super.onCreateOptionsMenu(menu);
//    }



}
