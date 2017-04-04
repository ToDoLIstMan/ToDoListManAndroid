package com.tdl.todolistmanandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tdl.todolistmanandroid.R;

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

    private Intent getIntent;
    private boolean isFinished;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getIntent = getIntent();
        isFinished = getIntent.getBooleanExtra("isFinished",false);

        makeToolbar();

        txtTitle.setText(getIntent.getStringExtra("title"));
        txtTime.setText(getIntent.getStringExtra("startTime")+" ~ "+getIntent.getStringExtra("endTime"));
        txtDetail.setText(getIntent.getStringExtra("detail"));
        txtPeople.setText(getIntent.getStringExtra("people"));
        btDone.setOnClickListener(this);

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
        if(getSupportActionBar() !=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    }
}
