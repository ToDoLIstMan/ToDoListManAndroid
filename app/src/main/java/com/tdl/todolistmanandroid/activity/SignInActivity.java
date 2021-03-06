package com.tdl.todolistmanandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.auth.ISessionCallback;
import com.kakao.util.helper.log.Logger;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.database.group;
import com.tdl.todolistmanandroid.database.user;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

import static android.content.ContentValues.TAG;

/**
 * Created by HyunWook Kim on 2017-03-21.
 */
public class SignInActivity extends Activity {

    @BindView(R.id.facebook_login) LoginButton login_button;
    @BindView(R.id.kakao_login) com.kakao.usermgmt.LoginButton kakaoLogin;
    @BindView(R.id.container_delay) FrameLayout container_delay;

    private Intent intent;
    CallbackManager mFacebookCallbackManager;
    FirebaseAuth mAuth;
    ProfileTracker pt;


    private SessionCallback callback;

    String userName, userEmail;
    String rank;

    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        mContext = this;

        facebookLogin();

        kakaoLogin();

    }

    /**
     * 카카오톡 로그인 구동 메소드
     *
     */
    private void kakaoLogin() {
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    /**
     * 카카오톡 로그인 관련 session callback 클래스
     */
    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            container_delay.setVisibility(View.VISIBLE);
            Log.e("asdfasdf","asdfasdf");

            Toast.makeText(getApplicationContext(), "카카오톡에 로그인 됐습니다", Toast.LENGTH_LONG).show();
            String accessToken = Session.getCurrentSession().getAccessToken();
            getFirebaseJwt(accessToken).continueWithTask(new Continuation<String, Task<AuthResult>>() {
                    @Override
                    public Task<AuthResult> then(@NonNull Task<String> task) throws Exception {
                        String firebaseToken = task.getResult();
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        return auth.signInWithCustomToken(firebaseToken);
                    }
                }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference myRef = database.getReference().child("user").child(""+FirebaseAuth.getInstance().getCurrentUser().getUid());

                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Log.e("data",FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    try{
                                        Intent gotoMain = new Intent(SignInActivity.this,MainActivity.class);
                                        List<Integer>  a = dataSnapshot.getValue(user.class).getGroups();
                                        if(a.size()>0) {
                                            a.removeAll(Collections.singleton(null));

                                            gotoMain.putExtra("groupUid", dataSnapshot.getValue(user.class).getGroups().get(0));
                                            gotoMain.putExtra("groupName", dataSnapshot.getValue(user.class).getGroupName().get(0));
                                        }else{
                                            gotoMain.putExtra("groupUid", -1);
                                            gotoMain.putExtra("groupName", -1);
                                        }
                                        container_delay.setVisibility(View.GONE);
                                        startActivity(gotoMain);
                                        finish();   myRef.removeEventListener(this);

                                    }catch (Exception e){
                                        container_delay.setVisibility(View.GONE);
                                        getRank();   myRef.removeEventListener(this);

                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                    container_delay.setVisibility(View.GONE);
                                }
                            });

                        } else {
                            container_delay.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "파이어베이스 유저를 만들지 못했습니다", Toast.LENGTH_LONG).show();
                            if (task.getException() != null) {
                                Log.e(TAG, task.getException().toString());
                            }
                        }
                    }
                });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
            Toast.makeText(getApplicationContext(), "카카오톡으로 회원가입이 실패했습니다", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 페이스북 로그인 구동 메소드
     */
    private void facebookLogin() {
        mFacebookCallbackManager  = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();

        try{
            login_button.setReadPermissions("email", "public_profile");
            login_button.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(final LoginResult loginResult) {
                    container_delay.setVisibility(View.VISIBLE);
                    AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                    mAuth.signInWithCredential(credential)
                            .addOnCompleteListener((SignInActivity) mContext, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Log.e("ddd",FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        final DatabaseReference myRef = database.getReference().child("user").child(""+FirebaseAuth.getInstance().getCurrentUser().getUid());

                                        myRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Log.e("data",dataSnapshot.toString());
                                                try {
                                                    Intent gotoMain = new Intent(SignInActivity.this,MainActivity.class);
                                                    List<Integer>  a = dataSnapshot.getValue(user.class).getGroups();
                                                    if(a.size()>0) {
                                                        a.removeAll(Collections.singleton(null));

                                                        gotoMain.putExtra("groupUid", dataSnapshot.getValue(user.class).getGroups().get(0));
                                                        gotoMain.putExtra("groupName", dataSnapshot.getValue(user.class).getGroupName().get(0));
                                                    }else{
                                                        gotoMain.putExtra("groupUid", -1);
                                                        gotoMain.putExtra("groupName", -1);
                                                    }

                                                    container_delay.setVisibility(View.GONE);
                                                    startActivity(gotoMain);
                                                    myRef.removeEventListener(this);
                                                    finish();
                                                }catch(Exception e) {

                                                    myRef.removeEventListener(this);
                                                    //현재 유저 이름 찾기 위한 메소드 실행.
                                                    pt = new ProfileTracker() {
                                                        @Override
                                                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                                                            container_delay.setVisibility(View.GONE);
                                                            userName = currentProfile.getName();
                                                            getRank();

                                                        }
                                                    };
                                                }

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });



                                    }else{


                                    }
                                }
                            });


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
    }

    /**
     * 추가정보를 받고 파이어베이스로 전달하는 메소드
     */
    private void getRank() {
        if(!SignInActivity.this.isFinishing()) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View promptView = inflater.inflate(R.layout.edittext_dialog, null);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("계급 추가");
            alert.setView(promptView);

            final EditText inputGroup = (EditText) promptView.findViewById(R.id.editGroup);
            final EditText inputName = (EditText) promptView.findViewById(R.id.editName);
            inputGroup.requestFocus();
            inputGroup.setHint("계급을 입력하세요.");

            inputName.setHint("이름을 입력하세요.");

            alert.setView(promptView);

            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    rank = inputGroup.getText().toString();
                    userName = inputName.getText().toString();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference().child("user");
                    List<Integer> group = new ArrayList<>();

                    List<String> groupName = new ArrayList<>();
                    user user = new user(userName, rank, group, groupName, group, groupName);
                    myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                    Intent gotoMain = new Intent(SignInActivity.this, MainActivity.class);

                    startActivity(gotoMain);
                    finish();
                }
            });

            AlertDialog dialog = alert.create();
            dialog.show();
        }
    }
    /**
     *
     * @param kakaoAccessToken Access token retrieved after successful Kakao Login
     * @return Task object that will call validation server and retrieve firebase token
     */
    private Task<String> getFirebaseJwt(final String kakaoAccessToken) {
        final TaskCompletionSource<String> source = new TaskCompletionSource<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getResources().getString(R.string.validation_server_domain) + "/verifyToken";
        HashMap<String, String> validationObject = new HashMap<>();
        validationObject.put("token", kakaoAccessToken);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(validationObject), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String firebaseToken = response.getString("firebase_token");
                    source.setResult(firebaseToken);
                } catch (Exception e) {
                    source.setException(e);
                    Log.e(TAG, e.toString());
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
                source.setException(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("token", kakaoAccessToken);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
        return source.getTask();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }catch (Exception error){
            Log.e("error : ",error.toString());
        }

        Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }
}
