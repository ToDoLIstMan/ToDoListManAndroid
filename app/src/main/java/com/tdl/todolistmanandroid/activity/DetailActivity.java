package com.tdl.todolistmanandroid.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdl.todolistmanandroid.ChangeDate;
import com.tdl.todolistmanandroid.ChangeTime;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.database.work;
import com.tdl.todolistmanandroid.item.TimeListItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by songm on 2017-03-29.
 */

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.txtTitle) TextView txtTitle;
    @BindView(R.id.txtTime) TextView txtTime;
    @BindView(R.id.txtPeople) TextView txtPeople;
    @BindView(R.id.txtDetail) TextView txtDetail;
    @BindView(R.id.btDone) Button btDone;
    @BindView(R.id.btPostpone) Button btPostpone;


    String nMonth, nDay;

    private Intent getIntent;
    private boolean isFinished;
    int curGrpUid, id;
    String title, pickDay;
    Context mContext;

    ChangeTime ct1 = new ChangeTime(0,0);
    ChangeTime ct2 = new ChangeTime(0,0);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getIntent = getIntent();
        isFinished = getIntent.getBooleanExtra("isFinished",false);
        mContext = this;

        makeToolbar();

        curGrpUid =getIntent.getIntExtra("curGrpUid",-1);
        title = getIntent.getStringExtra("title");
        pickDay = getIntent.getStringExtra("pickDay");
        id = getIntent.getIntExtra("id",-1);
        Log.e("dfdf",""+curGrpUid);

        ct1 = new ChangeTime(getIntent.getStringExtra("startTime"));
        ct2 = new ChangeTime(getIntent.getStringExtra("endTime"));

        txtTitle.setText(getIntent.getStringExtra("title"));
        txtTime.setText(ct1.getStoi()+"~"+ct2.getStoi());
        txtDetail.setText(getIntent.getStringExtra("detail"));
        txtPeople.setText(getIntent.getStringExtra("people"));
        btDone.setOnClickListener(this);
        FirebaseDatabase.getInstance().getReference().
                child("group").child(""+curGrpUid).child("masterUid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("adsf",dataSnapshot.getValue().toString());
                if(!dataSnapshot.getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                    btPostpone.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btPostpone.setOnClickListener(this);

        setBtStatue();


    }


    private void sendChgData(final boolean isChecked) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("work")
                .child(String.valueOf(curGrpUid)).child(pickDay).child(""+id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                work work = dataSnapshot.getValue(work.class);
                for(int i = 0; i<work.getuId().size();i++){
                    if(work.getuId().get(i).equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        List<Boolean> isDone = work.getIsDone();
                        isDone.set(i,isChecked);
                        myRef.child("isDone").setValue(isDone);
                    }
                }

                myRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void setBtStatue() {
        if(isFinished){
            btDone.setBackgroundResource(R.color.colorNoeDone);
          //  sendChgData(true);
            btDone.setText("취소");
        }else{
            btDone.setBackgroundResource(R.color.colorPrimary);
          //  sendChgData(false);
            btDone.setText("완료");
        }
    }

    /**
     * Toolbar 생성 메소드
     */
    private void makeToolbar() {
        toolbar.setTitle(getIntent.getStringExtra("title"));
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
    public void onClick(View v) {
        if(v.getId() == R.id.btDone){
            if(isFinished)
                isFinished=false;
            else
                isFinished=true;
            setBtStatue();
            sendChgData(isFinished);
        }

        if(v.getId() == R.id.btPostpone){
            final int curYear = Calendar.getInstance().get(Calendar.YEAR);
            final int curMon = Calendar.getInstance().get(Calendar.MONTH);
            final int curDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            Dialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                    if(year<curYear){
                        Toast.makeText(mContext, "잘못 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        if(month<curMon){
                            Toast.makeText(mContext, "잘못 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                        }else if(month>curMon){

                            delayWork(year, month, dayOfMonth);
                        }
                        else{
                            if(dayOfMonth>curDay) {

                                delayWork(year, month, dayOfMonth);
                            }
                            else
                                Toast.makeText(mContext, "잘못 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            },curYear,curMon,curDay);
            datePicker.show();
        }

    }


    private void delayWork(final int year, final int month, final int dayOfMonth) {
        final Date dd = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        ChangeDate cD = new ChangeDate(year, month,dayOfMonth);
        cD.getDate();   //2017-05-06

        final FirebaseDatabase database =FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("work").child(""+curGrpUid)
                .child(sdf.format(dd)).child(""+getIntent.getIntExtra("id",-1));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final work curWork = dataSnapshot.getValue(work.class);
                myRef.removeValue();
                final DatabaseReference myr = database.getReference().child("work").child(""+curGrpUid)
                        .child(""+year+"-"+(month+1)+"-"+dayOfMonth);
                myr.addValueEventListener(new ValueEventListener() {        //item 갯수 찾기 위해 돌림
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null)
                            myr.child(""+dataSnapshot.getChildrenCount()).setValue(curWork);
                        else
                            myr.child("0").setValue(curWork);
                        myr.removeEventListener(this);
                        Toast.makeText(mContext, "2.", Toast.LENGTH_SHORT).show();

                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                myRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
