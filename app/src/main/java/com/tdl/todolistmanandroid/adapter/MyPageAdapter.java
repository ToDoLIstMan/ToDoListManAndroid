package com.tdl.todolistmanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.activity.MyPageActivity;
import com.tdl.todolistmanandroid.item.MyPageItem;
import com.tdl.todolistmanandroid.item.MypageHeader;

import java.util.ArrayList;
import java.util.List;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHeader) {
            Log.e("dd",mypageHeader.getName());
            ((ViewHeader)holder).txtName.setText(mypageHeader.getName());
            ((ViewHeader)holder).txtRank.setText(mypageHeader.getRank());
            if(mypageHeader.getuId().contains("kakao"))
                ((ViewHeader)holder).txtLogin.setText("카카오");
            else
                ((ViewHeader)holder).txtLogin.setText("Facebook");
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

        public ViewHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
