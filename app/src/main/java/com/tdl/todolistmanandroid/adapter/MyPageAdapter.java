package com.tdl.todolistmanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.activity.MyPageActivity;
import com.tdl.todolistmanandroid.item.MyPageItem;

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
    private ArrayList<MyPageItem> mItems;

    public MyPageAdapter(ArrayList<MyPageItem> mItems, Context mContext){
        this.mItems = mItems;
        this.mContext = mContext;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mypage,parent,false);
        return new MyPageAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyPageItem curItem = mItems.get(position);
        ((MyPageAdapter.ViewHolder) holder).txtName.setText(curItem.getName());
        ((MyPageAdapter.ViewHolder) holder).txtDetail.setText(curItem.getDetail());

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtName) TextView txtName;
        @BindView(R.id.txtDetail) TextView txtDetail;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
