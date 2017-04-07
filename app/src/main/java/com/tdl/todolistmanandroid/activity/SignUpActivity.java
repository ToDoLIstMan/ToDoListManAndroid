package com.tdl.todolistmanandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.database.user;

/**
 * Created by HyunWook Kim on 2017-03-29.
 */

public class SignUpActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

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

                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(signUpId.getText().toString(),signUpPw.getText().toString()).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isComplete())
                                Toast.makeText(SignUpActivity.this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            else{
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference().child("user");

                                user user = new user(signUpName.getText().toString(),signUpBelonging.getText().toString(),null);
                                myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);           //db에 데이터 넣는 코드

                                Toast.makeText(SignUpActivity.this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        }
                    });

                }
            }
        });

    }
}