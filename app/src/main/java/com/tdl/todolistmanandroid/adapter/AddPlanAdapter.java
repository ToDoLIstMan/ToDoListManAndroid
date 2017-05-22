package com.tdl.todolistmanandroid.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tdl.todolistmanandroid.ChangeDate;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.activity.AddPlanActivity;
import com.tdl.todolistmanandroid.activity.MainActivity;
import com.tdl.todolistmanandroid.activity.PickGroupActivity;
import com.tdl.todolistmanandroid.activity.PreviewListAcitivity;
import com.tdl.todolistmanandroid.activity.SelectPeopleActivity;
import com.tdl.todolistmanandroid.item.AddPlanItem;
import com.tdl.todolistmanandroid.item.SelectPeopleItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

    private String worker ="", format ="", group ="",todayWorker="",excTime = "";

    private String[] workerNames, workerUids, curWkNames, curWkUids;

//    private String a1 = new ChangeDate(0,0,0).getToday();

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

        //body(holder)
        if(holder instanceof AddPlanViewHolder) {
            final AddPlanItem curItem = items.get(position-1);
            ((AddPlanAdapter.AddPlanViewHolder)holder).addPlanTxtTitle.setText(curItem.getTitle());
            ((AddPlanViewHolder)holder).addPlanTxtStartTime.setText(curItem.getStartTime());
            ((AddPlanViewHolder)holder).addPlanTxtEndTime.setText(curItem.getEndTime());
            ((AddPlanViewHolder)holder).addPlanTxtDetail.setText(curItem.getDetail());
            ((AddPlanAdapter.AddPlanViewHolder)holder).addPlanCardView.setOnLongClickListener(new View.OnLongClickListener() {
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
        else if(holder instanceof  AddPlanFooter){

            ((AddPlanFooter)holder).txtPeople.setText(todayWorker);
            ((AddPlanFooter)holder).btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AddPlanFooter)holder).bckAdd.setVisibility(View.GONE);
                    ((AddPlanFooter)holder).bckInput.setVisibility(View.VISIBLE);
                }
            });


            ((AddPlanFooter)holder).btnStartTime.setOnClickListener(new View.OnClickListener() {
                int hour, minute;
                @Override
                public void onClick(View v) {
                    TimePickerDialog t = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            ((AddPlanFooter)holder).btnStartTime.setText(hourOfDay+":"+minute);
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
                            ((AddPlanFooter)holder).btnEndTime.setText(hourOfDay+":"+minute);
                        }
                    }, hour, minute, true);
                    t.show();
                }
            });
            ((AddPlanFooter)holder).btnAddPeople.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(groupId!=-1) {
                        ((AddPlanActivity) mContext).sendIntent(groupId, 4);
                        Log.e("asdf","asdfsws");
                    }
                    else
                        Toast.makeText(mContext, "그룹을 먼저 선택해 주세요.", Toast.LENGTH_SHORT).show();
                }
            });

            ((AddPlanFooter)holder).btAddWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(((AddPlanFooter)holder).editTitle.getText().toString().equals("")||
                            ((AddPlanFooter)holder).btnStartTime.getText().toString().equals("")||
                            ((AddPlanFooter)holder).btnEndTime.getText().toString().equals("")||
                            todayWorker.equals("")) {
                        Toast.makeText(mContext, "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(mContext, a1, Toast.LENGTH_SHORT).show();
                    }
                    else{
                    ((AddPlanFooter)holder).bckAdd.setVisibility(View.VISIBLE);
                    ((AddPlanFooter)holder).btnStartTime.getText().toString();
                    ((AddPlanFooter)holder).btnEndTime.getText().toString();
                    ((AddPlanFooter)holder).editWorkDetail.getText().toString();
                    ((AddPlanFooter)holder).bckInput.setVisibility(View.GONE);

                    todayWorker="";
                    List<String> a = new ArrayList<>(Arrays.asList(curWkNames));
                    List<String> b = new ArrayList<>(Arrays.asList(curWkUids));
                    List<Boolean> isDone =new ArrayList<>();
                    for(int j =0;j<a.size();j++)
                        isDone.add(false);

                    items.add(new AddPlanItem(items.size(),
                            ((AddPlanFooter)holder).editTitle.getText().toString(),
                            ((AddPlanFooter)holder).editWorkDetail.getText().toString(),
                            ((AddPlanFooter)holder).btnStartTime.getText().toString(),
                            ((AddPlanFooter)holder).btnEndTime.getText().toString(),
                            a, b,isDone));



                    // 추가까지 다 되고 난 후의 코드
                    notifyDataSetChanged();     //리스트 추가한 것 띄어주는 코드
                    ((AddPlanFooter)holder).editTitle.setText("");
                    ((AddPlanFooter)holder).editWorkDetail.setText("");
                    ((AddPlanFooter)holder).btnStartTime.setText("");
                    ((AddPlanFooter)holder).btnEndTime.setText("");
                    Toast.makeText(mContext, "추가되었습니다", Toast.LENGTH_SHORT).show();}
                }});


        }



        else{

            // Header
            ((AddPlanViewHeader)holder).txtWorker.setText(worker);
            ((AddPlanViewHeader)holder).txtFormat.setText(format);
            ((AddPlanViewHeader)holder).txtGroup.setText(group);

            ((AddPlanViewHeader)holder).addGroupLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AddPlanActivity)mContext).sendIntent(position,2);
                }
            });


            ((AddPlanViewHeader)holder).addFormatLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AddPlanActivity)mContext).sendIntent(position,1);
                }
            });

            ((AddPlanViewHeader)holder).addWorkerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("gggg",""+groupId);
                    if(groupId!=-1)
                        ((AddPlanActivity) mContext).sendIntent(groupId, 0);
                    else
                        Toast.makeText(mContext, "그룹을 먼저 선택해 주세요.", Toast.LENGTH_SHORT).show();
                }
            });

            ((AddPlanViewHeader)holder).addTimeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int curYear = Calendar.getInstance().get(Calendar.YEAR);
                    final int curMon = Calendar.getInstance().get(Calendar.MONTH);
                    final int curDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                    Dialog datePicker = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month+1;
                            if(1<=month && month<10)
                                ((AddPlanViewHeader)holder).txtTime.setText(year+"-0"+month+"-"+dayOfMonth);
                            else
                                ((AddPlanViewHeader)holder).txtTime.setText(year+"-"+month+"-"+dayOfMonth);

                            excTime = ((AddPlanViewHeader)holder).txtTime.getText().toString();
                        }
                    },curYear,curMon,curDay);
                    datePicker.show();
                }
            });

        }
    }




    @Override
    public int getItemCount() {
        return items.size() +2;
    }



    class AddPlanViewHeader extends RecyclerView.ViewHolder{
        @BindView(R.id.addGroupLayout) LinearLayout addGroupLayout;
        @BindView(R.id.addFormatLayout) LinearLayout addFormatLayout;
        @BindView(R.id.addWorkerLayout) LinearLayout addWorkerLayout;
        @BindView(R.id.txtGroup) TextView txtGroup;
        @BindView(R.id.txtFormat) TextView txtFormat;
        @BindView(R.id.txtWorker) TextView txtWorker;
        @BindView(R.id.txtTime) TextView txtTime;
        @BindView(R.id.addTimeLayout) LinearLayout addTimeLayout;
        public AddPlanViewHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class AddPlanViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.addPlanTxtTitle) TextView addPlanTxtTitle;
        @BindView(R.id.addPlanTxtStartTime) TextView addPlanTxtStartTime;
        @BindView(R.id.addPlanTxtEndTime) TextView addPlanTxtEndTime;
        @BindView(R.id.addPlanTxtDetail) TextView addPlanTxtDetail;
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

        @BindView(R.id.editTitle) EditText editTitle;
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

    public void setTodayWorker(String[] todayWorker,String[] todayWkUid) {
        curWkNames = todayWorker;
        curWkUids = todayWkUid;
        for(String a : curWkNames)
            this.todayWorker = this.todayWorker+" "+a;
    }

    public void setWorker(String[] worker,String[] uId) {
        workerNames = worker;
        workerUids = uId;
        for(String a : workerNames)
            this.worker = this.worker+" "+a;

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

    public void setExcTime(String excTime) {
        this.excTime = excTime;
    }

    public void clearWorker() {
        this.worker = "";
    }
    public  void clearTodayWorker(){
        this.todayWorker = "";
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroup() {
        return group;
    }

    public String getExcTime() {
        return excTime;
    }

    public String getFormat() {
        return format;
    }
}
