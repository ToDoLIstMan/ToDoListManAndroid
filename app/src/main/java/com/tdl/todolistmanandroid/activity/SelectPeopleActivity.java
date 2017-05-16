package com.tdl.todolistmanandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.FormatManageAdapter;
import com.tdl.todolistmanandroid.adapter.SelectPeopleAdapter;
import com.tdl.todolistmanandroid.database.format;
import com.tdl.todolistmanandroid.database.user;
import com.tdl.todolistmanandroid.item.FormatManageItem;
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

    List<String> memberName = new ArrayList<>();
    List<String> memberUid = new ArrayList<>();

    Intent getintent;

    FirebaseAuth mAuth;
    String curuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_people);
        mContext = this;
        mAuth = FirebaseAuth.getInstance();
        curuId = mAuth.getCurrentUser().getUid();
        getintent = getIntent();

        Toolbar searchBar = (Toolbar) findViewById(R.id.searchToolbar);
        Log.e("ddd",""+getintent.getIntExtra("status",-1));
        switch (getintent.getIntExtra("status",-1)) {
            case 0:     //당번 선택
            case 4:
                searchBar.setTitle("당번선택");
                break;
            case 1:
                searchBar.setTitle("포맷선택");
                break;
            case 2:             //그룹 선택
                searchBar.setTitle("그룹선택");
                break;
        }
        setSupportActionBar(searchBar);
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
            case 4:
                myRef = database.getReference().child("group").child(""+getintent.getIntExtra("groupId",-1));
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        group group = dataSnapshot.getValue(group.class);
                        for(int i = 0; i<group.getMemberName().size();i++)
                            items.add(new SelectPeopleItem(R.drawable.kakao_default_profile_image, group.getMemberName().get(i),
                                    group.getMemberUid().get(i)));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                break;


            case 1:
                myRef = database.getReference().child("format").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getChildren();
                        int i = 0;
                        for(DataSnapshot a : dataSnapshot.getChildren()){
                            items.add(new SelectPeopleItem(i,a.getKey(),""));
                            i++;
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                break;


            case 2:
                myRef = database.getReference().child("user").child(curuId);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user user = dataSnapshot.getValue(user.class);
                        for(int i = 0; i<user.getGroupName().size();i++)
                            items.add(new SelectPeopleItem(user.getGroups().get(i), user.getGroupName().get(i), null));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;

        }

        recyclerView.setAdapter(new SelectPeopleAdapter(mContext, items,getIntent().getIntExtra("status",-1000),this));
    }

    public void setList(String uId, String name){
        memberName.add(name);
        memberUid.add(uId);
        Log.e("dd",memberName.toString());
    }
    public void deleteMember(String uId, String name){
        memberUid.remove(uId);
        memberName.remove(name);
        Log.e("dd",memberName.toString());
    }

    public void sendIntent(SelectPeopleItem s, int status){
        Intent i = getIntent();

        if(status==0 || status==4) {
            String[] names = new String[memberName.size()];
            String[] uIds = new String[memberUid.size()];


            for(int j = 0;j<memberName.size();j++)
                names[j] = memberName.get(j);
            for(int j = 0;j<memberUid.size();j++)
                uIds[j] = memberUid.get(j);


            i.putExtra("memberName", names);
            i.putExtra("memberUid", uIds);

            if(status==0) {
                i.putExtra("curView","header");
                setResult(778, i);
            } else if(status==4) {
                i.putExtra("curView","footer");
                setResult(781, i);
            }
        }
        else {
            i.putExtra("itemTitle", s.getUserName());
            if (status == 1)
                setResult(779, i);
            else if (status == 2) {
                i.putExtra("groupId", s.getImage());
                setResult(780, i);
            }
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(getintent.getIntExtra("status",-1)==0 || getintent.getIntExtra("status",-1)==4)
        inflater.inflate(R.menu.menu_seclectstn,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_done){
            if(getintent.getIntExtra("status",-1)==0)
                sendIntent(null,0);
            else if(getintent.getIntExtra("status",-1)==4)
                sendIntent(null,4);
        }
        return super.onOptionsItemSelected(item);
    }
}
