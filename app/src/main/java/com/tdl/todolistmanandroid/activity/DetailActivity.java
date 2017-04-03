package com.tdl.todolistmanandroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.tdl.todolistmanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songm on 2017-03-29.
 */

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        makeToolbar();
    }


    /**
     * Toolbar 생성 메소드
     */
    private void makeToolbar() {
        toolbar.setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(toolbar);
        if(getSupportActionBar() !=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
