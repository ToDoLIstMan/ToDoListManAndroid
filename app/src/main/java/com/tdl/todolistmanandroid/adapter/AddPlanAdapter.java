package com.tdl.todolistmanandroid.adapter;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.activity.AddPlanActivity;
import com.tdl.todolistmanandroid.activity.MainActivity;
import com.tdl.todolistmanandroid.activity.PickGroupActivity;
import com.tdl.todolistmanandroid.activity.SelectPeopleActivity;
import com.tdl.todolistmanandroid.item.AddPlanItem;
import com.tdl.todolistmanandroid.item.SelectPeopleItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by HyunWook Kim on 2017-04-05.
 */

public class AddPlanAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<AddPlanItem> items;

    private final int BODY = 0;
    private final int FOOTER = 1;
    private final int HEADER  = -1;

    private String worker ="", format ="", group ="";
    private int groupId= -1;
    public AddPlanAdapter(Context mContext, List<AddPlanItem> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {

         if(position==0)
             return HEADER;
         else if(position<=items.size())
            return BODY;
        else if(position==(items.size()+1))
            return FOOTER;
        else
            return -100;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.e("gggg",""+groupId);
        if(viewType==BODY) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_plan, parent, false);
            return new AddPlanAdapter.AddPlanViewHolder(v);
        }
        else if(viewType == FOOTER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_plan, parent, false);
            return new AddPlanAdapter.AddPlanFooter(v);
        }
        else if(viewType==HEADER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_plan, parent, false);

            return new AddPlanAdapter.AddPlanViewHeader(v);
        }
        else
            return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        Log.e("gggg",""+groupId);
        //body(holder)
        if(holder instanceof AddPlanViewHolder) {
            final AddPlanItem curItem = items.get(position-1);
            ((AddPlanAdapter.AddPlanViewHolder) holder).addPlanTxtTitle.setText(curItem.getTitle());

/*        ((AddPlanAdapter.AddPlanViewHolder)holder).addPlanCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoToDo = new Intent(mContext,AddPlanActivity.class);
                gotoToDo.putExtra("title",curItem.getTitle());
                mContext.startActivity(gotoToDo);
            }
        });*/


        }


        //footer
        else if(holder instanceof  AddPlanFooter){
            ((AddPlanFooter)holder).btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AddPlanFooter)holder).bckAdd.setVisibility(View.GONE);
                    ((AddPlanFooter)holder).bckInput.setVisibility(View.VISIBLE);
                }
            });

            ((AddPlanFooter)holder).btAddWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AddPlanFooter)holder).bckAdd.setVisibility(View.VISIBLE);
                    ((AddPlanFooter)holder).bckInput.setVisibility(View.GONE);

                    ((AddPlanFooter)holder).txtStartTime.getText().toString();
                    ((AddPlanFooter)holder).txtEndTime.getText().toString();
                    ((AddPlanFooter)holder).editWorkDetail.getText().toString();

                    items.add(new AddPlanItem( ((AddPlanFooter)holder).editTitle.getText().toString()));
                    notifyDataSetChanged();     //리스트 추가한 것 띄어주는 코드

                    ((AddPlanFooter)holder).editTitle.setText("");
                    Toast.makeText(mContext, "추가되었습니다", Toast.LENGTH_SHORT).show();
                }
            });

            ((AddPlanFooter)holder).btnStartTime.setOnClickListener(new View.OnClickListener() {
                int hour, minute;
                @Override
                public void onClick(View v) {
                   TimePickerDialog t = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                       @Override
                       public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                           ((AddPlanFooter)holder).txtStartTime.setText(hourOfDay+":"+minute);
                       }
                   }, hour, minute, true);
                    t.show();
                }
            });
            ((AddPlanFooter)holder).btnEndTime.setOnClickListener(new View.OnClickListener() {
                int hour, minute;
                @Override
                public void onClick(View v) {
                    TimePickerDialog t = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            ((AddPlanFooter)holder).txtEndTime.setText(hourOfDay+":"+minute);
                        }
                    }, hour, minute, true);
                    t.show();
                }
            });

            ((AddPlanFooter)holder).btnAddPeople.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gotoB = new Intent(mContext,SelectPeopleActivity.class);
                    Log.e("adsf",""+groupId);
                    gotoB.putExtra("status",groupId);
                    gotoB.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(gotoB);}
            });
        }



        else{

            // Header
            ((AddPlanViewHeader)holder).txtWorker.setText(worker);
            ((AddPlanViewHeader)holder).txtFormat.setText(format);
            ((AddPlanViewHeader)holder).txtGroup.setText(group);
            ((AddPlanViewHeader)holder).btAddGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((AddPlanActivity)mContext).sendIntent(position,2);
                }
            });


            ((AddPlanViewHeader)holder).btAddFormat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((AddPlanActivity)mContext).sendIntent(position,1);
                }
            });

            ((AddPlanViewHeader)holder).btAddWorker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("gggg",""+groupId);
                    if(groupId!=-1) {
                        ((AddPlanActivity) mContext).sendIntent(groupId, 0);
                    }
                    else
                        Toast.makeText(mContext, "그룹을 먼저 선택해 주세요.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }




    @Override
    public int getItemCount() {
        return items.size() +2;
    }


    class AddPlanViewHeader extends RecyclerView.ViewHolder{
        @BindView(R.id.btAddGroup) Button btAddGroup;
        @BindView(R.id.btAddFormat) Button btAddFormat;
        @BindView(R.id.btAddWorker) Button btAddWorker;
        @BindView(R.id.txtGroup) TextView txtGroup;
        @BindView(R.id.txtFormat) TextView txtFormat;
        @BindView(R.id.txtWorker) TextView txtWorker;
        public AddPlanViewHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class AddPlanViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.addPlanTxtTitle)
        TextView addPlanTxtTitle;
        @BindView(R.id.addPlanCardView)
        CardView addPlanCardView;
        AddPlanViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class AddPlanFooter extends RecyclerView.ViewHolder{
        @BindView(R.id.bckInput)
        LinearLayout bckInput;
        @BindView(R.id.bckAdd)
        FrameLayout bckAdd;
        @BindView(R.id.btAddWork) Button btAddWork;
        @BindView(R.id.btnAdd)
        FloatingActionButton btnAdd;

        @BindView(R.id.editTitle)
        EditText editTitle;
        @BindView(R.id.txtStartTime) TextView txtStartTime;
        @BindView(R.id.txtEndTime) TextView txtEndTime;
        @BindView(R.id.txtPeople) TextView txtPeople;
        @BindView(R.id.editWorkDetail) EditText editWorkDetail;

        @BindView(R.id.btnStartTime) Button btnStartTime;
        @BindView(R.id.btnEndTime) Button btnEndTime;
        @BindView(R.id.btnAddPeople) Button btnAddPeople;


        public AddPlanFooter(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroup() {
        return group;
    }
}
