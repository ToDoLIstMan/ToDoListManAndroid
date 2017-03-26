package com.tdl.todolistmanandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

    private final int HOLDER = 1;
    private  final int HEADER = 0;

    public TimeListAdapter(Context mContext, List<TimeListItem> lists, List<String> timeLists) {
        this.mContext=mContext;
        this.lists = lists;
        this.timeLists = timeLists;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return HEADER;
        else if(position>0){
            if(!lists.get(position).getStartTime().equals(lists.get(position-1).getStartTime()))
                return HEADER;
            else
                return HOLDER;
        }
        else
            return HEADER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == HEADER){
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent,false);
            return new Header(v);}
        else if(viewType == HOLDER){
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timelist,parent,false);
            return new Holder(v);
        }
        else
            return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof Holder){
            String done = "", undone = "";
            TimeListItem item = lists.get(position);
            ((Holder)holder).txtTitle.setText(item.getTitle());
            for(int i = 0; i<item.getDoPeople().size();i++) {
                if(item.getIsDone().get(i))
                    done += item.getDoPeople().get(i);
                else
                    undone += item.getDoPeople().get(i);
            }
            ((Holder) holder).txtDone.setText(done);
            ((Holder) holder).txtUndone.setText(undone);

        } else if(holder instanceof Header){
            String timeItem = timeLists.get(position);
            ((Header)holder).txtTime.setText(timeItem);
        }
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


