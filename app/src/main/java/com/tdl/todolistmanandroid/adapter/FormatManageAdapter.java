package com.tdl.todolistmanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.activity.DetailActivity;
import com.tdl.todolistmanandroid.item.FormatManageItem;
import com.tdl.todolistmanandroid.item.TimeListItem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songm on 2017-03-21.
 */

public class FormatManageAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<FormatManageItem> lists;
    private int headCount = 0;
    public FormatManageAdapter(Context mContext, List<FormatManageItem> lists) {
        this.mContext=mContext;
        this.lists = lists;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final FormatManageItem item = lists.get(position-headCount);
        ((Holder)holder).txtTitle.setText(item.getFormatName());
        ((Holder)holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoDetail = new Intent(mContext,DetailActivity.class);
                gotoDetail.putExtra("formatName",item.getFormatName());
                gotoDetail.putExtra("formatId",item.getFormatId());
                gotoDetail.putExtra("masterUid",item.getMasterUid());
                gotoDetail.putExtra("work",item.getWork().toString());
                gotoDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(gotoDetail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        @BindView(R.id.txtTitle) TextView txtTitle;
        @BindView(R.id.cardView) CardView cardView;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

//    class Header extends RecyclerView.ViewHolder{
//        @BindView(R.id.txtTitle) TextView txtTime;
//        public Header(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
}


