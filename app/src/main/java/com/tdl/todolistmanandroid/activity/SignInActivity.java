package com.tdl.todolistmanandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tdl.todolistmanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tdl.todolistmanandroid.R.id.textSignUp;
import static com.tdl.todolistmanandroid.R.id.txtSignUp;

/**
 * Created by HyunWook Kim on 2017-03-21.
 */
public class SignInActivity extends Activity implements View.OnClickListener {

    @BindView (R.id.txtId)  EditText txtId;
    @BindView(R.id.txtPassword) EditText txtPassword;
    @BindView(R.id.btLogin) Button btLogin;
    @BindView(R.id.txtSignUp) TextView txtSignUp;
    @BindView(R.id.kakaoLogin) ImageButton kakaoLogin;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        btLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        kakaoLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btLogin:      //로그인 버튼 눌렀을 때
                //   if((textId.getText().toString()).equals("master") && textPassword.getText().toString().equals("password")){
                Intent intent = new Intent(
                        getApplicationContext(),MainActivity.class);
                startActivity(intent);
                //}
                // else{
                //     Toast.makeText(SignInActivity.this, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                // }
                break;
            case R.id.txtSignUp:        //회원가입 버튼 눌렀을 때
                intent = new Intent(
                        getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.kakaoLogin:       //카카오로그인 버튼 눌렀을 때
                intent = new Intent(
                        getApplicationContext(), KakaoLoginActivity.class);
                startActivity(intent);
                break;



        }
    }
}
