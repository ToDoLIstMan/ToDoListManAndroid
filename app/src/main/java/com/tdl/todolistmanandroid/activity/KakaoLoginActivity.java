package com.tdl.todolistmanandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tdl.todolistmanandroid.R;

/**
 * Created by HyunWook Kim on 2017-03-29.
 */

public class KakaoLoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_login);


        final EditText kakaoLoginBeloging = (EditText) findViewById(R.id.kakaoLoginBelonging);
        Button btKakaoLoginSignup = (Button) findViewById(R.id.btKakaoLoginSignup);

        btKakaoLoginSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kakaoLoginBeloging.getText().toString().equals(""))
                    Toast.makeText(KakaoLoginActivity.this, "소속을 입력해 주세요", Toast.LENGTH_SHORT).show();
                else
                    finish();
            }
        });

    }
}