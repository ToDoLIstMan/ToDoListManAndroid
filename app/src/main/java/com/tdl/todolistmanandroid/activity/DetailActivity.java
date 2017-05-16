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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tdl.todolistmanandroid.R;

import java.util.Calendar;
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
    int curGrpUid;
    String title;
    Context mContext;
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

        Log.e("dfdf",""+curGrpUid);

        txtTitle.setText(getIntent.getStringExtra("title"));
        txtTime.setText(getIntent.getStringExtra("startTime")+" ~ "+getIntent.getStringExtra("endTime"));
        txtDetail.setText(getIntent.getStringExtra("detail"));
        txtPeople.setText(getIntent.getStringExtra("people"));
        btDone.setOnClickListener(this);
        btPostpone.setOnClickListener(this);

        setBtStatue();
    }

    private void setBtStatue() {
        if(isFinished){
            btDone.setBackgroundResource(R.color.colorNoeDone);
            btDone.setText("취소");
        }else{
            btDone.setBackgroundResource(R.color.colorPrimary);
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
        }

        if(v.getId() == R.id.btPostpone){
            final int curYear = Calendar.getInstance().get(Calendar.YEAR);
            final int curMon = Calendar.getInstance().get(Calendar.MONTH);
            final int curDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

            Dialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    if(year<curYear){
                        Toast.makeText(mContext, "잘못 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        if(month<curMon){
                            Toast.makeText(mContext, "잘못 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                        }else if(month>curMon){
                            FirebaseDatabase database =FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference().child("work").child(curGrpUid+"");
                            DatabaseReference myRef2 = database.getReference().child("work").child(curGrpUid+"");
                            dayToString(month ,dayOfMonth);
                            myRef2.child(year+"-"+nMonth+"-"+nDay).child(title).getDatabase();
                            myRef.child(year+"-"+nMonth+"-"+nDay).child(title).setValue(myRef2);
                            dayToString(curMon,curDay);
                            myRef.child(curYear+"-"+nMonth+"-"+nDay).child(title).removeValue();
                            finish();

//                                Toast.makeText(mContext, ""+year+" . "+(month+1)+" . "+dayOfMonth, Toast.LENGTH_SHORT).show();
                            // 지우고 이 날짜로 보내기
                        }
                        else{
                            if(dayOfMonth>curDay) {
//                                Toast.makeText(mContext, "" + year + " . " + (month + 1) + " . " + dayOfMonth, Toast.LENGTH_SHORT).show();
                                FirebaseDatabase database =FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference().child("work").child(curGrpUid+"");
                                DatabaseReference myRef2 = database.getReference().child("work").child(curGrpUid+"");
                                dayToString(month ,dayOfMonth);
                                myRef2.child(year+"-"+month+"-"+dayOfMonth).child(title).getDatabase();
                                myRef.child(year+"-"+month+"-"+dayOfMonth).child(title).setValue(myRef2);
                                dayToString(curMon,curDay);
                                myRef.child(curYear+"-"+curMon+"-"+curDay).child(title).removeValue();
                                finish();
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

    public void dayToString(int month, int day){

        if(month<9){
            nMonth = "0"+(month+1);
        } else  nMonth = (month+1)+"";

        if(day<10){
            nDay = "0" + day;
        } else nDay = day+"";


    }

}
