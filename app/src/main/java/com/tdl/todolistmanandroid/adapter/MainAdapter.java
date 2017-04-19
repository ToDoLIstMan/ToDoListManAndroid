package com.tdl.todolistmanandroid.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.activity.TimeListActivity;
import com.tdl.todolistmanandroid.database.user;
import com.tdl.todolistmanandroid.item.MainItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songm on 2017-03-21.
 */

public class MainAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MainItem> items;

    private FirebaseDatabase mDatabase;

    public MainAdapter(Context mContext, List<MainItem> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent,false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MainItem curItem = items.get(position);
        ((MainViewHolder)holder).txtTitle.setText(curItem.getTitle());

        ((MainViewHolder)holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!curItem.getMemberUid().contains(FirebaseAuth.getInstance().getCurrentUser().getUid())) {               //그룹에 포함되어 있지 않을 경우 (코드 간략화 좀 필요할 듯)
                    AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                    ab.setTitle("Alert");
                    ab.setMessage(curItem.getTitle() + "에 참여하시겠습니까?");
                    // 확인버튼
                    ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = mDatabase.getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    final String curName = dataSnapshot.getValue(user.class).getName();
                                    DatabaseReference myRef = mDatabase.getReference().child("group").child("" + curItem.getGroupId());
                                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            List<String> uids = new ArrayList<>();
                                            List<String> names = new ArrayList<>();
                                            uids.addAll(curItem.getMemberUid());
                                            names.addAll(curItem.getMemberName());
                                            uids.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                            names.add(curName);

                                            dataSnapshot.getRef().child("memberUid").setValue(uids);
                                            dataSnapshot.getRef().child("memberName").setValue(names);

                                            timeIntent(curItem);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                    });

                    // 취소버튼
                    ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    // 메인 다이얼로그 생성
                    AlertDialog alert = ab.create();
                    // 다이얼로그 보기
                    alert.show();
                }

                else                //그룹에 현재 유저가 포함되어 있을 경우
                    timeIntent(curItem);
            }
        });
    }

    /**
     * timeListActivity로 보내는 intent포함된 메소드
     * @param curItem
     */
    private void timeIntent(MainItem curItem) {
        Intent gotoToDo = new Intent(mContext, TimeListActivity.class);
        gotoToDo.putExtra("title", curItem.getTitle());
        gotoToDo.putExtra("groupId", curItem.getGroupId());
        gotoToDo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(gotoToDo);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class MainViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtTitle) TextView txtTitle;
        @BindView(R.id.cardView) CardView cardView;
        MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}