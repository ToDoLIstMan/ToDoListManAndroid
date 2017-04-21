package com.tdl.todolistmanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.activity.AddPlanActivity;
import com.tdl.todolistmanandroid.item.AddPlanItem;

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
    public AddPlanAdapter(Context mContext, List<AddPlanItem> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {

         if(position==0)
             return HEADER;
         else if(position<items.size())
            return BODY;
        else if(position==items.size())
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof AddPlanViewHolder) {
            final AddPlanItem curItem = items.get(position);
            ((AddPlanAdapter.AddPlanViewHolder) holder).addPlanTxtTitle.setText(curItem.getTitle());

/*        ((AddPlanAdapter.AddPlanViewHolder)holder).addPlanCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoToDo = new Intent(mContext,AddPlanActivity.class);
                gotoToDo.putExtra("title",curItem.getTitle());
                mContext.startActivity(gotoToDo);
            }
        });*/
        }else if(holder instanceof  AddPlanFooter){
            ((AddPlanFooter)holder).btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AddPlanFooter)holder).bckAdd.setVisibility(View.GONE);
                    ((AddPlanFooter)holder).bckInput.setVisibility(View.VISIBLE);
                }
            });

        }
        else{

        }
    }

    @Override
    public int getItemCount() {
        return items.size() +1;
    }


    class AddPlanViewHeader extends RecyclerView.ViewHolder{

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
        @BindView(R.id.btnAdd)
        FloatingActionButton btnAdd;

        public AddPlanFooter(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
