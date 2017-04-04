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
import com.tdl.todolistmanandroid.item.TimeListItem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songm on 2017-03-21.
 */

public class TimeListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<TimeListItem> lists;
    private HashMap<Integer,String> timeLists;

    private final int HOLDER = 1;
    private  final int HEADER = 0;

    private int headCount = 0;
    public TimeListAdapter(Context mContext, List<TimeListItem> lists, HashMap<Integer,String> timeLists) {
        this.mContext=mContext;
        this.lists = lists;
        this.timeLists=timeLists;

        Iterator<Integer> iterator = timeLists.keySet().iterator();
        while (iterator.hasNext()) {
            int key = iterator.next();
            Log.e(""+key,""+timeLists.get(key));
        }
    }
    @Override
    public int getItemViewType(int position) {
       if(timeLists.containsKey(position))
           return HEADER;
        else
            return HOLDER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == HEADER){
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time,parent,false);
            return new Header(v);}
        else if(viewType == HOLDER){
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timelist,parent,false);
            return new Holder(v);
        }
        else
            return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof Holder){
            String done = "", undone = "";
            final boolean[] isFinished = {false};
            final TimeListItem item = lists.get(position-headCount);
            ((Holder)holder).txtTitle.setText(item.getTitle());
            for(int i = 0; i<item.getDoPeople().size();i++) {
                if(item.getIsDone().get(i))
                    done += item.getDoPeople().get(i);
                else
                    undone += item.getDoPeople().get(i);
            }
            ((Holder) holder).txtDone.setText(done);
            ((Holder) holder).txtUndone.setText(undone);
            ((Holder)holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gotoDetail = new Intent(mContext,DetailActivity.class);
                    gotoDetail.putExtra("title",item.getTitle());
                    gotoDetail.putExtra("startTime",item.getStartTime());
                    gotoDetail.putExtra("endTime",item.getEndTime());
                    gotoDetail.putExtra("detail",item.getDetail());
                    gotoDetail.putExtra("supervisior",item.getSupervisor());
                    gotoDetail.putExtra("people",item.getDoPeople().toString());
                    gotoDetail.putExtra("isFinished", isFinished[0]);
                    mContext.startActivity(gotoDetail);
                }
            });
            ((Holder)holder).checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        ((Holder)holder).txtTitle.setPaintFlags(((Holder)holder).txtTitle.getPaintFlags()  | Paint.STRIKE_THRU_TEXT_FLAG);
                        isFinished[0] = true;
                    }else{
                        ((Holder)holder).txtTitle.setPaintFlags(((Holder)holder).txtTitle.getPaintFlags()  ^ Paint.STRIKE_THRU_TEXT_FLAG);
                        isFinished[0] = false;
                    }
                }
            });

        } else if(holder instanceof Header){
            headCount++;
            Log.e("ddd",""+headCount);
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
        @BindView(R.id.checkBox) AppCompatCheckBox checkBox;
        @BindView(R.id.cardView) CardView cardView;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class Header extends RecyclerView.ViewHolder{
        @BindView(R.id.txtTitle) TextView txtTime;
        public Header(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


