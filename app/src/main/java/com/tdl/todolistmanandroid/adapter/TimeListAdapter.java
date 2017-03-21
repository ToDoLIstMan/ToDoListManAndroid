package com.tdl.todolistmanandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.item.TimeListItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songm on 2017-03-21.
 */

public class TimeListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<TimeListItem> lists;
    private List<String> timeLists;

    public TimeListAdapter(Context mContext, List<TimeListItem> lists, List<String> timeLists) {
        this.mContext=mContext;
        this.lists = lists;
        this.timeLists = timeLists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return lists.size()+timeLists.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        @BindView(R.id.txtTitle) TextView txtTitle;
        @BindView(R.id.txtUndone) TextView txtUndone;
        @BindView(R.id.txtDone) TextView txtDone;
        @BindView(R.id.checkBox) TextView checkBox;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }

    class Header extends RecyclerView.ViewHolder{
        @BindView(R.id.title) TextView txtTime;
        public Header(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


