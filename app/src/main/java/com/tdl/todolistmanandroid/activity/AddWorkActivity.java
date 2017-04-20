package com.tdl.todolistmanandroid.activity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tdl.todolistmanandroid.R;

import butterknife.ButterKnife;

/**
 * Created by HyunWook Kim on 2017-04-18.
 */

public class AddWorkActivity extends AppCompatActivity{

    TextView workStartTimeHourText;
    TextView workStartTimeMinText;
    TextView workEndTimeHourText;
    TextView workEndTimeMinText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footer_plan);
        ButterKnife.bind(this);
        final EditText workTitleEditText = (EditText) findViewById(R.id.workTitleEditText);
        final EditText workDetailText = (EditText) findViewById(R.id.workDetailText);
        workStartTimeHourText = (TextView) findViewById(R.id.workStartTimeHourText);
        workEndTimeHourText = (TextView) findViewById(R.id.workEndTimeHourText);
        Button btAddWork = (Button) findViewById(R.id.btAddWork);
        final TimePickerDialog startTimePickDialog =  new TimePickerDialog(this, startListener, 0, 0, false);
        final TimePickerDialog endTimePickDialog =  new TimePickerDialog(this, endListener, 0, 0, false);


        /*workStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimePickDialog.show();
            }
        });
        workEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTimePickDialog.show();
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
*/
    }

    private TimePickerDialog.OnTimeSetListener startListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            workStartTimeHourText.setText(hourOfDay+"");
            workStartTimeMinText.setText(minute+"");
        }
    };

    private TimePickerDialog.OnTimeSetListener endListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            workEndTimeHourText.setText(hourOfDay+"");
            workEndTimeMinText.setText(minute+"");
        }
    };


}
