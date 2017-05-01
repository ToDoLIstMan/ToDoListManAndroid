package com.tdl.todolistmanandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.SelectPeopleAdapter;
import com.tdl.todolistmanandroid.database.user;
import com.tdl.todolistmanandroid.item.SelectPeopleItem;

import com.tdl.todolistmanandroid.database.group;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by HyunWook Kim on 2017-04-20.
 */

public class SelectPeopleActivity extends AppCompatActivity{

    @BindView(R.id.toolbar) Toolbar toolbar;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_people);
        mContext = this;

        Intent getintent = getIntent();

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

        final List<SelectPeopleItem> items = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        switch (getintent.getIntExtra("status",-1)){

            case 0:
                Log.e("hghghghg", ""+getintent.getIntExtra("groupId",-1));
                myRef = database.getReference().child("group").child(""+getintent.getIntExtra("groupId",-1));
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        group group = dataSnapshot.getValue(group.class);
                        for(String s1 : group.getMemberName()) {
                            SelectPeopleItem item = new SelectPeopleItem(R.drawable.kakao_default_profile_image, s1);
                            items.add(item);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                break;


            case 1:
                break;


            case 2:

                myRef = database.getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user user = dataSnapshot.getValue(user.class);
                        for(int i = 0; i<user.getGroupName().size();i++) {
                            SelectPeopleItem item = new SelectPeopleItem(user.getGroups().get(i), user.getGroupName().get(i));
                            items.add(item);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;





        }

        recyclerView.setAdapter(new SelectPeopleAdapter(mContext, items,getIntent().getIntExtra("status",-1000),this));
    }

    public void sendIntent(SelectPeopleItem s, int status){
        Intent i = getIntent();
        i.putExtra("itemTitle", s.getUserName());
        if(status==0)
            setResult(778,i);
        else if(status==1)
            setResult(779,i);
        else if(status==2) {
            i.putExtra("groupId", s.getImage());
            setResult(780, i);
        }
        finish();
    }
}
