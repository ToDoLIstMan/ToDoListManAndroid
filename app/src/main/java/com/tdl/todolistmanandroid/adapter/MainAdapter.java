package com.tdl.todolistmanandroid.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.activity.TimeListActivity;
import com.tdl.todolistmanandroid.item.MainItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songm on 2017-03-21.
 */

public class MainAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MainItem> items;
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

                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("Alert");
                ab.setMessage(curItem.getTitle()+"에 참여하시겠습니까?");
                // 확인버튼
                ab.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent gotoToDo = new Intent(mContext,TimeListActivity.class);
                        gotoToDo.putExtra("title",curItem.getTitle());
                        gotoToDo.putExtra("groupId",curItem.getGroupId());
                        mContext.startActivity(gotoToDo);
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
        });
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