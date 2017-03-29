package com.tdl.todolistmanandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tdl.todolistmanandroid.R;

/**
 * Created by HyunWook Kim on 2017-03-29.
 */

public class SignUpActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText signUpId = (EditText) findViewById(R.id.signUpId);
        final EditText signUpPw = (EditText) findViewById(R.id.signUpPw);
        final EditText signUpPwChck = (EditText) findViewById(R.id.signUpPwChck);
        final EditText signUpName = (EditText) findViewById(R.id.signUpName);
        final EditText signUpBelonging = (EditText) findViewById(R.id.signUpBelonging);
        Button btSignUpSignUp = (Button) findViewById(R.id.btSignUpSignUp);

        btSignUpSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(signUpId.getText().toString().equals("")||signUpPw.getText().toString().equals("")||
                        signUpPwChck.getText().toString().equals("")||signUpName.getText().toString().equals("")||
                        signUpBelonging.getText().toString().equals(""))
                    Toast.makeText(SignUpActivity.this, "빈칸을 채워 주세요!", Toast.LENGTH_SHORT).show();
                else if(signUpPw.getText().toString().equals(signUpPwChck.getText().toString())==false)
                    Toast.makeText(SignUpActivity.this, "비밀번호를 확인 해 주세요!", Toast.LENGTH_SHORT).show();
                else{
                    finish();
                }
            }
        });

    }
}