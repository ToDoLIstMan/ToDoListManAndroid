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
import com.tdl.todolistmanandroid.database.format;
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
    int lastGroupId = 0, lastPlanNumber = 0;
    List<MakeFormatItem> items;

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

        items = new ArrayList<>();

        recyclerView.setAdapter(new MakeFormatAdapter(mContext,items));

        lastGroupId = getIntent().getIntExtra("lastGrpId",-1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_format,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if(item.getItemId()==R.id.action_add) {
//            Log.e("adfadf",""+items.size());
            if (((MakeFormatAdapter) recyclerView.getAdapter()).getItemCount() == 1)
                Toast.makeText(this, "일을 추가하세요", Toast.LENGTH_SHORT).show();
            else{

//            // edittext_dialogue layout의 폼을 가진 AlertDialogue 생성 후 이름을 포멧 추가라고 지정
                LayoutInflater inflater = LayoutInflater.from(this);
            View promptView = inflater.inflate(R.layout.edittext_single_dialog, null);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("포멧 추가");
            alert.setView(promptView);

            // 내용 지정
            final EditText input = (EditText) promptView.findViewById(R.id.editGroup);
            input.requestFocus();
            input.setHint("포멧명을 입력하세요.");
            alert.setView(promptView);

            // 확인을 누를 시
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference().child("format").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(input.getText().toString());
                    for (int i = 0; i < items.size(); i++)
                        myRef.child("" + i).setValue(items.get(i));
                    finish();

//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference myRef = database.getReference().child("format").child(String.valueOf(lastGroupId+1));
//                    DatabaseReference myRefPlan = database.getReference().child("format").child(String.valueOf(lastPlanNumber+1));

//                    format format = new format(input.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getUid(),lastGroupId+1+"");
//                    //format.plan(editFormatDetail.getText().toString(),startTime.getText().toString(),endTime.getText().toString(),
//                     //       lastPlanNumber+1+"", planName.getText().toString());
//                    myRef.setValue(format);
//                    myRefPlan.setValue(format);

//                    myRef = database.getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {

//                            Map<String,Object> f = new HashMap<>();
////                            List<Integer> masterGroups = dataSnapshot.getValue(user.class).getMasterGroups();
////                            List<String> masterGroupName =dataSnapshot.getValue(user.class).getMasterGroupName();
////
////                            masterGroups.add(lastGroupId+1);
////                            masterGroupName.add(input.getText().toString());
////
////                            f.put("masterGroups",masterGroups);
////                            f.put("masterGroupName",masterGroupName);
////                            f.put("groups",masterGroups);
////                            f.put("groupName",masterGroupName);
//
//                            dataSnapshot.getRef().updateChildren(f);
//                            Toast.makeText(mContext, "추가되었습니다.", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
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
        return super.onOptionsItemSelected(item);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0) {
            Log.e("dddd",data.getStringExtra("itemTitle"));
         //   ((AddPlanAdapter)recyclerView.getAdapter()).setWorker(data.getStringExtra("itemTitle"));
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
