package com.tdl.todolistmanandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tdl.todolistmanandroid.R;

/**
 * Created by HyunWook Kim on 2017-04-18.
 */

public class AddWorkActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);

        final EditText workTitleEditText = (EditText) findViewById(R.id.workTitleEditText);
        final EditText workStartTimeHourText = (EditText) findViewById(R.id.workStartTimeHourText);
        final EditText workStartTimeMinText = (EditText) findViewById(R.id.workStartTimeMinText);
        final EditText workEndTimeHourText = (EditText) findViewById(R.id.workEndTimeHourText);
        final EditText workEndTimeMinText = (EditText) findViewById(R.id.workEndTimeMinText);
        final EditText workDetailText = (EditText) findViewById(R.id.workDetailText);
        Button btAddWork = (Button) findViewById(R.id.btAddWork);

        workStartTimeHourText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btAddWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(workTitleEditText.getText().toString().equals("")||workStartTimeHourText.getText().toString().equals("")||
                        workStartTimeMinText.getText().toString().equals("")||workEndTimeHourText.getText().toString().equals("")||
                        workEndTimeMinText.getText().toString().equals(""))
                Toast.makeText(AddWorkActivity.this, "빈칸을 채워 주세요!", Toast.LENGTH_SHORT).show();

            }

        });

    }


}
