package com.tdl.todolistmanandroid.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.tdl.todolistmanandroid.activity.FormatDetailActivity;
import com.tdl.todolistmanandroid.activity.FormatManageActivity;
import com.tdl.todolistmanandroid.item.FormatDetailItem;
import com.tdl.todolistmanandroid.item.FormatManageItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HyunWook Kim on 2017-05-20.
 */

public class FormatDetailAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<FormatDetailItem> items;

    private final int BODY = 0;
    private final int FOOTER = 1;
    private final int HEADER = -1;

    private String format="",name="", formatName="", title="", detail="", startTime="", endTime="";


    private int groupId = -1;
    public FormatDetailAdapter(Context mContext, List<FormatDetailItem> items, String formatName) {
        this.mContext = mContext;
        this.items = items;
        this.formatName = formatName;
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
        if(viewType==BODY) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_plan,parent, false);
            return new FormatDetailAdapter.FormatDetailViewHolder(v);
        } else if(viewType==FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_format,parent, false);
            return new FormatDetailAdapter.FormatDetailFooter(v);
        } else if(viewType==HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_format_detail,parent, false);
            return new FormatDetailAdapter.FormatDetailViewHeader(v);
        } else
            return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        //body(holder)
        if(holder instanceof FormatDetailViewHolder) {
            final FormatDetailItem curItem = items.get(position-1);
            ((FormatDetailViewHolder)holder).addPlanTxtTitle.setText(curItem.getTitle());
            ((FormatDetailViewHolder)holder).addPlanTxtStartTime.setText(curItem.getStartTime());
            ((FormatDetailViewHolder)holder).addPlanTxtEndTime.setText(curItem.getEndTime());
            ((FormatDetailViewHolder)holder).addPlanTxtDetail.setText(curItem.getDetail());
            ((FormatDetailViewHolder)holder).addPlanCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    items.remove(position-1);
                    Toast.makeText(mContext, "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    return false;
                }
            });


    }


        //footer
        else if(holder instanceof FormatDetailFooter){

            ((FormatDetailFooter)holder).btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((FormatDetailFooter)holder).bckAdd.setVisibility(View.GONE);
                    ((FormatDetailFooter)holder).bckInput.setVisibility(View.VISIBLE);
                }
            });


            ((FormatDetailFooter)holder).btnStartTime.setOnClickListener(new View.OnClickListener() {
                int hour, minute;
                @Override
                public void onClick(View v) {
                    TimePickerDialog t = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            ((FormatDetailFooter)holder).btnStartTime.setText(hourOfDay+":"+minute);
                        }
                    }, hour, minute, true);
                    t.show();
                }
            });
            ((FormatDetailFooter)holder).btnEndTime.setOnClickListener(new View.OnClickListener() {
                int hour, minute;
                @Override
                public void onClick(View v) {
                    TimePickerDialog t = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            ((FormatDetailFooter)holder).btnEndTime.setText(hourOfDay+":"+minute);
                        }
                    }, hour, minute, true);
                    t.show();
                }
            });

            ((FormatDetailFooter)holder).btAddWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(((FormatDetailFooter)holder).editTitle.getText().toString().equals("")||
                            ((FormatDetailFooter)holder).btnStartTime.getText().toString().equals("")||
                            ((FormatDetailFooter)holder).btnEndTime.getText().toString().equals("")) {
                        Toast.makeText(mContext, "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        ((FormatDetailFooter)holder).bckAdd.setVisibility(View.VISIBLE);
                        ((FormatDetailFooter)holder).editTitle.getText().toString();
                        ((FormatDetailFooter)holder).btnStartTime.getText().toString();
                        ((FormatDetailFooter)holder).btnEndTime.getText().toString();
                        ((FormatDetailFooter)holder).editFormatDetail.getText().toString();
                        ((FormatDetailFooter)holder).bckInput.setVisibility(View.GONE);

                        items.add(new FormatDetailItem(items.size(),
                                ((FormatDetailAdapter.FormatDetailFooter)holder).editTitle.getText().toString(),
                                ((FormatDetailAdapter.FormatDetailFooter)holder).editFormatDetail.getText().toString(),
                                ((FormatDetailAdapter.FormatDetailFooter)holder).btnStartTime.getText().toString(),
                                ((FormatDetailAdapter.FormatDetailFooter)holder).btnEndTime.getText().toString()));



                        // 추가까지 다 되고 난 후의 코드
                        notifyDataSetChanged();     //리스트 추가한 것 띄어주는 코드
                        ((FormatDetailAdapter.FormatDetailFooter)holder).editTitle.setText("");
                        ((FormatDetailAdapter.FormatDetailFooter)holder).editFormatDetail.setText("");
                        ((FormatDetailAdapter.FormatDetailFooter)holder).btnStartTime.setText("00:00");
                        ((FormatDetailAdapter.FormatDetailFooter)holder).btnEndTime.setText("00:00");
                        Toast.makeText(mContext, "추가되었습니다", Toast.LENGTH_SHORT).show();}
                }});


        }



        else{

            // Header
            name = formatName;
            ((FormatDetailViewHeader)holder).txtTitle.setText(name);
    }

        }

    @Override
    public int getItemCount() {
        return items.size()+2;
    }



    class FormatDetailViewHeader extends RecyclerView.ViewHolder{
        @BindView(R.id.txtTitle) TextView txtTitle;
        public FormatDetailViewHeader(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class FormatDetailViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.addPlanTxtTitle)
        TextView addPlanTxtTitle;
        @BindView(R.id.addPlanTxtStartTime) TextView addPlanTxtStartTime;
        @BindView(R.id.addPlanTxtEndTime) TextView addPlanTxtEndTime;
        @BindView(R.id.addPlanTxtDetail) TextView addPlanTxtDetail;
        @BindView(R.id.addPlanCardView) CardView addPlanCardView;
        FormatDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FormatDetailFooter extends RecyclerView.ViewHolder{
        @BindView(R.id.bckInput) LinearLayout bckInput;
        @BindView(R.id.bckAdd) FrameLayout bckAdd;
        @BindView(R.id.btAddWork) Button btAddWork;
        @BindView(R.id.btnAdd) FloatingActionButton btnAdd;

        @BindView(R.id.editTitle) EditText editTitle;
        @BindView(R.id.editFormatDetail) EditText editFormatDetail;

        @BindView(R.id.btnStartTime) Button btnStartTime;
        @BindView(R.id.btnEndTime) Button btnEndTime;

        FormatDetailFooter(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setFormat(String format) {this.format = format;    }
    public void setTitle(String title) {this.title = title;}
    public void setDetail(String detail) {this.detail = detail; }
    public void setStartTime(String startTime){this.startTime = startTime;}
    public void setEndTime(String endTime) {this.endTime = endTime;}

    public String getFormat(){return format;}
    public String getTitle() { return title; }
    public String getDetail() { return detail;}
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime;}

}
