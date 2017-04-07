package com.tdl.todolistmanandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.tdl.todolistmanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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


                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

             /*   FirebaseAuth mAuth  = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(txtId.getText().toString(),txtPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()){
                            Toast.makeText(SignInActivity.this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(SignInActivity.this, "다시 확인하시기 바랍니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                */
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
