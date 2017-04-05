package com.tdl.todolistmanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public AddPlanAdapter(Context mContext, List<AddPlanItem> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_plan,parent,false);
        return new AddPlanAdapter.AddPlanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final AddPlanItem curItem = items.get(position);
        ((AddPlanAdapter.AddPlanViewHolder)holder).addPlanTxtTitle.setText(curItem.getTitle());

/*        ((AddPlanAdapter.AddPlanViewHolder)holder).addPlanCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoToDo = new Intent(mContext,AddPlanActivity.class);
                gotoToDo.putExtra("title",curItem.getTitle());
                mContext.startActivity(gotoToDo);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return items.size();
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

}
