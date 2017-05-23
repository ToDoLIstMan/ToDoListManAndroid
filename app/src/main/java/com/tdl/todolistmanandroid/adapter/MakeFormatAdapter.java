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

import com.tdl.todolistmanandroid.ChangeDate;
import com.tdl.todolistmanandroid.ChangeTime;
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
    ChangeDate cd1 = new ChangeDate("00:00");
    ChangeDate cd2 = new ChangeDate("00:00");
    ChangeTime tc = new ChangeTime(0,0);

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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        //body(holder)
        if(holder instanceof FormatViewHolder) {
            final MakeFormatItem curItem = items.get(position);
            ((FormatViewHolder)holder).addPlanTxtTitle.setText(curItem.getPlanName());
            ((FormatViewHolder)holder).addPlanTxtStartTime.setText(curItem.getStartTime());
            ((FormatViewHolder)holder).addPlanTxtEndTime.setText(curItem.getEndTime());
            ((FormatViewHolder)holder).addPlanTxtDetail.setText(curItem.getDetail());


            ((FormatViewHolder)holder).addPlanCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    items.remove(position);
                    Toast.makeText(mContext, "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    return false;
                }
            });


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
                    ChangeDate cd1 = new ChangeDate(((FormatFooter)holder).btnStartTime.getText().toString());
                    ChangeDate cd2 = new ChangeDate(((FormatFooter)holder).btnEndTime.getText().toString());
                    if(((FormatFooter)holder).editTitle.getText().toString().equals("")||
                            (cd1.getOut()-cd2.getOut()>=0))
                    {
                        Toast.makeText(mContext, "빈칸을 마저 채우세요", Toast.LENGTH_SHORT).show();}
                    else{
                    ((FormatFooter)holder).bckAdd.setVisibility(View.VISIBLE);
                    ((FormatFooter)holder).bckInput.setVisibility(View.GONE);

                    ((FormatFooter)holder).btnStartTime.getText().toString();
                    ((FormatFooter)holder).btnEndTime.getText().toString();
                    ((FormatFooter)holder).editFormatDetail.getText().toString();

                    items.add(new MakeFormatItem(items.size(),
                            ((FormatFooter)holder).editTitle.getText().toString(),
                            ((FormatFooter)holder).btnStartTime.getText().toString(),
                            ((FormatFooter)holder).btnEndTime.getText().toString(),
                            ((FormatFooter)holder).editFormatDetail.getText().toString()
                            ));


                    notifyDataSetChanged();     //리스트 추가한 것 띄어주는 코드

                    ((FormatFooter)holder).editTitle.setText("");
                    Toast.makeText(mContext, "추가되었습니다", Toast.LENGTH_SHORT).show();}
                }
            });

            ((FormatFooter)holder).btnStartTime.setOnClickListener(new View.OnClickListener() {
                int hour, minute;
                @Override
                public void onClick(View v) {
                    TimePickerDialog t = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            tc = new ChangeTime(hourOfDay,minute);
                            ((FormatFooter)holder).btnStartTime.setText(tc.getFullTime());
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
                            tc = new ChangeTime(hourOfDay,minute);
                            ((FormatFooter)holder).btnEndTime.setText(tc.getFullTime());
                        }
                    }, hour, minute, true);
                    t.show();
                }
            });

        } else{}

    }




    @Override
    public int getItemCount() {
        return items.size() +1;
    }

    class FormatViewHolder extends RecyclerView.ViewHolder{
        @Nullable@BindView(R.id.addPlanTxtTitle) TextView addPlanTxtTitle;
        @Nullable@BindView(R.id.addPlanCardView) CardView addPlanCardView;
        @Nullable@BindView(R.id.addPlanTxtStartTime) TextView addPlanTxtStartTime;
        @Nullable@BindView(R.id.addPlanTxtEndTime) TextView addPlanTxtEndTime;
        @Nullable@BindView(R.id.addPlanTxtDetail) TextView addPlanTxtDetail;
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
