package com.tdl.todolistmanandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.adapter.MainAdapter;
import com.tdl.todolistmanandroid.item.MainItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HyunWook Kim on 2017-03-21.
 */
public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final EditText textId = (EditText)findViewById(R.id.textId);
        final EditText textPassword = (EditText)findViewById(R.id.textPassword);
        Button btLogin = (Button)findViewById(R.id.btLogin);
        final TextView textSignUp = (TextView)findViewById(R.id.textSignUp);
        ImageButton kakaoLogin = (ImageButton)findViewById(R.id.kakaoLogin);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((textId.getText().toString()).equals("master") && textPassword.getText().toString().equals("password")){
                Intent intent = new Intent(
                        getApplicationContext(),MainActivity.class);
                startActivity(intent);}
                else{
                    Toast.makeText(SignInActivity.this, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        kakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(), KakaoLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
