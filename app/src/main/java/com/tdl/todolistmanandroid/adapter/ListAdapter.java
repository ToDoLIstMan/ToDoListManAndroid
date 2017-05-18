package com.tdl.todolistmanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.activity.DetailActivity;
import com.tdl.todolistmanandroid.item.TimeListItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songm on 2017-03-21.
 */

public class ListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<TimeListItem> lists;
    int curGrpUid;
    String pickDay;
    public ListAdapter(Context mContext, List<TimeListItem> lists, int curGrpUid, String pickday) {
        this.mContext=mContext;
        this.lists = lists;
        this.curGrpUid = curGrpUid;
        this.pickDay = pickday;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timelist,parent,false);
            return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String done = "", undone = "";
            final boolean[] isFinished = {false};
            final TimeListItem item = lists.get(position);
            ((Holder)holder).txtTitle.setText(item.getTitle());

            for(int i = 0; i<item.getDoPeople().size();i++) {
                if(item.getIsDone().get(i)) {
                    done += item.getDoPeople().get(i);
                    if(item.getPeopleUid().get(i).equals(uid)) {
                        ((Holder) holder).checkBox.setChecked(item.getIsDone().get(i));
                        if(item.getIsDone().get(i))
                            ((Holder)holder).txtTitle.setPaintFlags(((Holder)holder).txtTitle.getPaintFlags()  | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                }
                else
                    undone += item.getDoPeople().get(i);

            }
            if(!item.getPeopleUid().contains(uid))
                ((Holder)holder).checkBox.setClickable(false);

            ((Holder) holder).txtDone.setText(done);
            ((Holder) holder).txtUndone.setText(undone);
            ((Holder)holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gotoDetail = new Intent(mContext,DetailActivity.class);
                    gotoDetail.putExtra("curGrpUid", curGrpUid);
                    gotoDetail.putExtra("id",item.getId());
                    gotoDetail.putExtra("title",item.getTitle());
                    gotoDetail.putExtra("startTime",item.getStartTime());
                    gotoDetail.putExtra("endTime",item.getEndTime());
                    gotoDetail.putExtra("detail",item.getDetail());
                    gotoDetail.putExtra("supervisior",item.getId());
                    gotoDetail.putExtra("people",item.getDoPeople().toString());
                    gotoDetail.putExtra("isFinished", isFinished[0]);
                    gotoDetail.putExtra("pickDay",pickDay);
                    gotoDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(gotoDetail);
                }
            });
            ((Holder)holder).checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                     if(isChecked){
                        ((Holder)holder).txtTitle.setPaintFlags(((Holder)holder).txtTitle.getPaintFlags()  | Paint.STRIKE_THRU_TEXT_FLAG);
                        isFinished[0] = true;

                         sendChgData(isChecked, item, uid);

                     }else{
                        ((Holder)holder).txtTitle.setPaintFlags(((Holder)holder).txtTitle.getPaintFlags()  ^ Paint.STRIKE_THRU_TEXT_FLAG);
                        isFinished[0] = false;

                         sendChgData(isChecked, item, uid);

                    }


                }
            });

    }

    private void sendChgData(boolean isChecked, TimeListItem item, String uid) {
        int userPos = -1;
        for(int i = 0; i<item.getPeopleUid().size();i++){
            Log.e("Asdf",""+i);
            if(item.getPeopleUid().get(i).equals(uid)){
                userPos = i;
                break;
            }
        }


        item.getIsDone().set(userPos,isChecked);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("work")
                .child(String.valueOf(curGrpUid)).child(pickDay).child(""+item.getId()).child("isDone");
        myRef.setValue(item.getIsDone());
    }

    @Override
    public int getItemCount() {
        return lists.size();
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


