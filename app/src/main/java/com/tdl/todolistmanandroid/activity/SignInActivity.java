package com.tdl.todolistmanandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.tdl.todolistmanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HyunWook Kim on 2017-03-21.
 */
public class SignInActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.login_button) LoginButton login_button;
    @BindView(R.id.kakaoLogin) ImageButton kakaoLogin;

    private Intent intent;
    CallbackManager mFacebookCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        try{
            login_button.setReadPermissions("email", "public_profile");
            login_button.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());

                }

                @Override
                public void onCancel() {

                    Log.d("CANCEL : ", "Facebook login canceled.");
                }

                @Override
                public void onError(FacebookException error) {

                    Log.d("ERROR : ", error.toString());
                }
            });

        } catch (Exception error){
            Log.e("error : ",error.toString());
        }

        kakaoLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:      //로그인 버튼 눌렀을 때


                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

                break;
            case R.id.kakaoLogin:       //카카오로그인 버튼 눌렀을 때
                intent = new Intent(
                        getApplicationContext(), KakaoLoginActivity.class);
                startActivity(intent);
                break;



        }
    }
}
