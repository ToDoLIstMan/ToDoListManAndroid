package com.tdl.todolistmanandroid.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.usermgmt.response.model.User;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.activity.MyPageActivity;
import com.tdl.todolistmanandroid.database.user;
import com.tdl.todolistmanandroid.item.MyPageItem;
import com.tdl.todolistmanandroid.item.MypageHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by HyunWook Kim on 2017-05-19.
 */

public class MyPageAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private MypageHeader mypageHeader;
    private ArrayList<MyPageItem> groupItems;
    private ArrayList<MyPageItem> masterItems;

    private final int HEADER = -1;
    private final int BODY = 0;


    public MyPageAdapter(Context mContext, MypageHeader mypageHeader, ArrayList<MyPageItem> groupItems, ArrayList<MyPageItem> masterItems) {
//        Log.e("",groupItems.get(0).getName());
        this.groupItems = new ArrayList<>();
        this.masterItems = new ArrayList<>();
        this.groupItems.addAll(groupItems);
        this.mContext = mContext;
        this.mypageHeader =  mypageHeader;
        this.masterItems.addAll(masterItems);
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return HEADER;
        }else{
            return BODY;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_mypage, parent, false);
            return new MyPageAdapter.ViewHeader(v);
        }else{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mypage, parent, false);
            return new MyPageAdapter.ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHeader) {
            Log.e("dd",mypageHeader.getName());
            ((ViewHeader)holder).txtName.setText(mypageHeader.getName());
            ((ViewHeader)holder).txtRank.setText(mypageHeader.getRank());
            if(mypageHeader.getuId().contains("kakao"))
                ((ViewHeader)holder).txtLogin.setText("카카오");
            else
                ((ViewHeader)holder).txtLogin.setText("Facebook");

            ((ViewHeader)holder).btnMoti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((ViewHeader)holder).btnMoti.getText().equals("수정")){

                        ((ViewHeader)holder).editRank.setVisibility(View.VISIBLE);
                        ((ViewHeader)holder).editRank.setText(mypageHeader.getRank());
                        ((ViewHeader)holder).txtRank.setVisibility(View.GONE);
                        ((ViewHeader)holder).btnMoti.setText("완료");
                    } else if(((ViewHeader)holder).btnMoti.getText().equals("완료")){
                        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                        alert.setTitle("정보 수정");
                        alert.setMessage("입력한 정보로 수정하시겠습니까?");
                        alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                                            .child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        ref.removeEventListener(this);
                                        Log.e("asdf",dataSnapshot.toString());
                                        user u = dataSnapshot.getValue(user.class);
                                        u.setRank(((ViewHeader)holder).editRank.getText().toString());
                                        ref.setValue(u);

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                                ((ViewHeader)holder).btnMoti.setText("수정");
                                ((ViewHeader)holder).txtRank.setVisibility(View.VISIBLE);
                                ((ViewHeader)holder).editRank.setVisibility(View.GONE);


                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((ViewHeader)holder).btnMoti.setText("수정");
                                ((ViewHeader)holder).txtRank.setVisibility(View.VISIBLE);
                                ((ViewHeader)holder).editRank.setVisibility(View.GONE);
                              }
                        });
                        alert.show();

                    }
                }
            });
        } else {
            if(position>0 && position<=groupItems.size())
                ((ViewHolder)holder).txtName.setText(groupItems.get(position-1).getName());
            else {
                ((ViewHolder) holder).txtName.setText(masterItems.get(position - (groupItems.size() + 1)).getName());
                ((ViewHolder) holder).isMaster.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return groupItems.size()+masterItems.size()+1; // +header (+1)
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtName) TextView txtName;
        @BindView(R.id.isMaster) ImageView isMaster;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class ViewHeader extends RecyclerView.ViewHolder{
        @BindView(R.id.txtName) TextView txtName;
        @BindView(R.id.txtRank) TextView txtRank;
        @BindView(R.id.txtLogin) TextView txtLogin;
        @BindView(R.id.btnMoti) Button btnMoti;
        @BindView(R.id.editRank) EditText editRank;

        public ViewHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
