package com.tdl.todolistmanandroid.activity;


import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.AddPlanAdapter;
import com.tdl.todolistmanandroid.adapter.MakeFormatAdapter;
import com.tdl.todolistmanandroid.database.group;
import com.tdl.todolistmanandroid.database.user;
import com.tdl.todolistmanandroid.item.AddPlanItem;
import com.tdl.todolistmanandroid.item.MakeFormatItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HyunWook Kim on 2017-04-27.
 */

public class MakeFormatActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Context mContext;
    int lastGroupId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_format);
        ButterKnife.bind(this);
        mContext = this;

        makeToolbar();
        initList();


    }

    /**
     * Toolbar 생성 메소드
     */
    private void makeToolbar() {
        toolbar.setTitle("포맷 추가");
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
        List<MakeFormatItem> items;

        items = new ArrayList<>();

        recyclerView.setAdapter(new MakeFormatAdapter(mContext,items));
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
            Toast.makeText(this, "준비중", Toast.LENGTH_SHORT).show();
//            LayoutInflater inflater = LayoutInflater.from(this);
//            View promptView = inflater.inflate(R.layout.edittext_dialog,null);
//            AlertDialog.Builder alert = new AlertDialog.Builder(this);
//            alert.setTitle("포멧 추가");
//            alert.setView(promptView);
//
//            final EditText input = (EditText)promptView.findViewById(R.id.editGroup);
//            input.requestFocus();
//            input.setHint("포멧명을 입력하세요.");
//            alert.setView(promptView);
//
//            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference myRef = database.getReference().child("format").child(String.valueOf(lastGroupId+1));
//                    group group = new group(lastGroupId+1, FirebaseAuth.getInstance().getCurrentUser().getUid(),input.getText().toString(),new ArrayList<String>(),new ArrayList<String>());
//                    myRef.setValue(group);
//
//                    myRef = database.getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//                            Map<String,Object> g = new HashMap<>();
//                            List<Integer> masterGroups = dataSnapshot.getValue(user.class).getMasterGroups();
//                            List<String> masterGroupName =dataSnapshot.getValue(user.class).getMasterGroupName();
//
//                            masterGroups.add(lastGroupId+1);
//                            masterGroupName.add(input.getText().toString());
//
//                            g.put("masterGroups",masterGroups);
//                            g.put("masterGroupName",masterGroupName);
//                            g.put("groups",masterGroups);
//                            g.put("groupName",masterGroupName);
//
//                            dataSnapshot.getRef().updateChildren(g);
//                            Toast.makeText(mContext, "추가되었습니다.", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            });
//
//            AlertDialog dialog = alert.create();
//            dialog.show();
//
        }
        return super.onOptionsItemSelected(item);
    }





//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode==0) {
//            Log.e("dddd",data.getStringExtra("itemTitle"));
//            ((AddPlanAdapter)recyclerView.getAdapter()).setWorker(data.getStringExtra("itemTitle"));
//            recyclerView.getAdapter().notifyDataSetChanged();
//        }
//    }
}
