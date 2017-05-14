package com.tdl.todolistmanandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.database.user;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songm on 2017-05-15.
 */

public class MyPageActivity extends AppCompatActivity{

    @BindView(R.id.toolbar) Toolbar toolbar;

    @BindView(R.id.txtName) TextView txtName;
    @BindView(R.id.txtRank) TextView txtRank;
    @BindView(R.id.txtMasterGroup) TextView txtMasterGroup;
    @BindView(R.id.txtGroup) TextView txtGroup;

    @BindView(R.id.editName) EditText editName;
    @BindView(R.id.editRank) EditText editRank;
    @BindView(R.id.editMasterGroup) EditText editMasterGroup;
    @BindView(R.id.editGroup) EditText editGroup;

    @BindView(R.id.container_inform)
    LinearLayout container_inform;
    @BindView(R.id.container_edit)
    LinearLayout container_edit;


    boolean isEdit = false;
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        ButterKnife.bind(this);

        mContext = this;
        makeToolbar();
        setData();
    }

    /**
     * User 데이터를 불러오는 메소드
     */
    private void setData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref =database.getReference();
        ref.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user u = dataSnapshot.getValue(user.class);
                txtName.setText(u.getName());
                txtRank.setText(u.getRank());
                txtMasterGroup.setText(u.getMasterGroupName().toString());
                txtGroup.setText(u.getGroupName().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void editData() {
        editName.setHint(txtName.getText());
        editRank.setHint(txtRank.getText());
        editMasterGroup.setHint(txtMasterGroup.getText());
        editGroup.setHint(editGroup.getText());
    }

    /**
     * Toolbar 생성 메소드
     */
    private void makeToolbar() {
        toolbar.setTitle("마이페이지");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(getSupportActionBar() !=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mypage,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(isEdit){
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
        }else{
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);

        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                isEdit = true;
                container_edit.setVisibility(View.VISIBLE);
                container_inform.setVisibility(View.GONE);

                supportInvalidateOptionsMenu();
                editData();
                break;

            case R.id.action_done:
                isEdit = true;

                container_edit.setVisibility(View.GONE);
                container_inform.setVisibility(View.VISIBLE);

                supportInvalidateOptionsMenu();
                setData();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
