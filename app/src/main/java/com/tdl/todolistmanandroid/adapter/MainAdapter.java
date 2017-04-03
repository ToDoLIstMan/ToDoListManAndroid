package com.tdl.todolistmanandroid.adapter;

import android.content.Context;
import android.content.Intent;
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
                Intent gotoToDo = new Intent(mContext,TimeListActivity.class);
                gotoToDo.putExtra("title",curItem.getTitle());
                mContext.startActivity(gotoToDo);
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