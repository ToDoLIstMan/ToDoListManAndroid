package com.tdl.todolistmanandroid.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
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
import com.tdl.todolistmanandroid.activity.FormatManageActivity;
import com.tdl.todolistmanandroid.activity.SelectPeopleActivity;
import com.tdl.todolistmanandroid.item.MakeFormatItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HyunWook Kim on 2017-04-28.
 */

public class MakeFormatAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MakeFormatItem> items;

    private final int BODY = 0;
    private final int FOOTER = 1;

    private String worker ="", format ="", group ="";
    public MakeFormatAdapter(Context mContext, List<MakeFormatItem> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {

        if(position<items.size())
            return BODY;
        else if(position==(items.size()))
            return FOOTER;
        else
            return -100;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==BODY) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_plan, parent, false);
            return new FormatViewHolder(v);
        }
        else if(viewType == FOOTER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_format, parent, false);
            return new FormatFooter(v);
        }
        else
            return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        //body(holder)
        if(holder instanceof FormatViewHolder) {
            final MakeFormatItem curItem = items.get(position);
//            ((FormatViewHolder)holder).txtEndTime.setText(curItem.getEndTime());
//            ((FormatViewHolder)holder).txtStartTime.setText(curItem.getStartTime());
            ((FormatViewHolder)holder).addPlanTxtTitle.setText(curItem.getPlanName());


//            ((FormatViewHolder)holder).addPlanCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent gotoToDo = new Intent(mContext,FormatManageActivity.class);
//                gotoToDo.putExtra("PlanName",curItem.getPlanName());
//                mContext.startActivity(gotoToDo);
//            }
//        });


        }


        //footer
        else if(holder instanceof FormatFooter){
            ((FormatFooter)holder).btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((FormatFooter)holder).bckAdd.setVisibility(View.GONE);
                    ((FormatFooter)holder).bckInput.setVisibility(View.VISIBLE);
                }
            });

            ((FormatFooter)holder).btAddWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((FormatFooter)holder).bckAdd.setVisibility(View.VISIBLE);
                    ((FormatFooter)holder).bckInput.setVisibility(View.GONE);

                    ((FormatFooter)holder).txtStartTime.getText().toString();
                    ((FormatFooter)holder).txtEndTime.getText().toString();
                    ((FormatFooter)holder).editFormatDetail.getText().toString();

                    items.add(new MakeFormatItem(items.size(),
                            ((FormatFooter)holder).editTitle.getText().toString(),
                            ((FormatFooter)holder).txtStartTime.getText().toString(),
                            ((FormatFooter)holder).txtEndTime.getText().toString(),
                            ((FormatFooter)holder).editFormatDetail.getText().toString()
                            ));


                    notifyDataSetChanged();     //리스트 추가한 것 띄어주는 코드

                    ((FormatFooter)holder).editTitle.setText("");
                    Toast.makeText(mContext, "추가되었습니다", Toast.LENGTH_SHORT).show();
                }
            });

            ((FormatFooter)holder).btnStartTime.setOnClickListener(new View.OnClickListener() {
                int hour, minute;
                @Override
                public void onClick(View v) {
                    TimePickerDialog t = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            ((FormatFooter)holder).txtStartTime.setText(hourOfDay+":"+minute);
                        }
                    }, hour, minute, true);
                    t.show();
                }
            });
            ((FormatFooter)holder).btnEndTime.setOnClickListener(new View.OnClickListener() {
                int hour, minute;
                @Override
                public void onClick(View v) {
                    TimePickerDialog t = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            ((FormatFooter)holder).txtEndTime.setText(hourOfDay+":"+minute);
                        }
                    }, hour, minute, true);
                    t.show();
                }
            });

        }



        else{
/*
            // Header
            ((AddPlanAdapter.AddPlanViewHeader)holder).txtWorker.setText(worker);
            ((AddPlanAdapter.AddPlanViewHeader)holder).btAddGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent  gotoA = new Intent(mContext,SelectPeopleActivity.class);
                    gotoA.putExtra("status",2);
                    gotoA.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ((AddPlanActivity)mContext).startActivityForResult(gotoA,0);
                }
            });


            ((AddPlanAdapter.AddPlanViewHeader)holder).btAddFormat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent  gotoA = new Intent(mContext,SelectPeopleActivity.class);
                    gotoA.putExtra("status",1);
                    gotoA.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(gotoA);
                }
            });

            ((AddPlanAdapter.AddPlanViewHeader)holder).btAddWorker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent  gotoA = new Intent(mContext,SelectPeopleActivity.class);
                    gotoA.putExtra("status",0);
                    gotoA.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(gotoA);
                }
            });*/
        }
    }




    @Override
    public int getItemCount() {
        return items.size() +1;
    }

    class FormatViewHolder extends RecyclerView.ViewHolder{
        @Nullable@BindView(R.id.addPlanTxtTitle) TextView addPlanTxtTitle;
        @Nullable@BindView(R.id.addPlanCardView) CardView addPlanCardView;
        FormatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class FormatFooter extends RecyclerView.ViewHolder{
        @BindView(R.id.bckInput) LinearLayout bckInput;
        @BindView(R.id.bckAdd) FrameLayout bckAdd;
        @BindView(R.id.btAddWork) Button btAddWork;
        @BindView(R.id.btnAdd) FloatingActionButton btnAdd;


        @BindView(R.id.editTitle) EditText editTitle;
        @BindView(R.id.txtStartTime) TextView txtStartTime;
        @BindView(R.id.txtEndTime) TextView txtEndTime;
        @BindView(R.id.editFormatDetail) EditText editFormatDetail;

        @BindView(R.id.btnStartTime) Button btnStartTime;
        @BindView(R.id.btnEndTime) Button btnEndTime;


        public FormatFooter(View itemView) {
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

}
