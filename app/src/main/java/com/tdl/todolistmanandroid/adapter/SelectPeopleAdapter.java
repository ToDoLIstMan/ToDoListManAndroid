package com.tdl.todolistmanandroid.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.activity.AddPlanActivity;
import com.tdl.todolistmanandroid.activity.SelectPeopleActivity;
import com.tdl.todolistmanandroid.item.SelectPeopleItem;
import com.tdl.todolistmanandroid.item.SelectSomethingItem;

import java.util.List;

import static com.facebook.FacebookSdk.APPLICATION_ID_PROPERTY;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.tdl.todolistmanandroid.R.id.recyclerView;
import static com.tdl.todolistmanandroid.R.id.selectSomethingItem;
import static com.tdl.todolistmanandroid.R.id.userNameText;

/**
 * Created by HyunWook Kim on 2017-04-20.
 */

public class SelectPeopleAdapter extends RecyclerView.Adapter<SelectPeopleAdapter.ViewHolder> {

    Context context;
    List<SelectPeopleItem> items;
    List<SelectSomethingItem> items2;
    Integer i;
    Intent thisIntent;
    SelectPeopleActivity pActivity;


    int item_layout;
    public SelectPeopleAdapter(Context context, List<SelectPeopleItem> items, int item_layout, SelectPeopleActivity selectPeopleActivity) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
        thisIntent = ((SelectPeopleActivity)this.context).getIntent();
        pActivity= selectPeopleActivity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
//        item_layout = 0;
        if(item_layout==0 || item_layout==4) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_people, null);
        }else if(item_layout==1||item_layout==2){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_something, null);
        }else return null;

        return new ViewHolder(v);
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        item_layout=0;


        if(item_layout==0 || item_layout==4) {
            final SelectPeopleItem item = items.get(position);
                Drawable drawable = context.getResources().getDrawable(item.getImage());
            holder.image.setBackground(drawable);
            holder.userName.setText(item.getUseruId());

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        ((SelectPeopleActivity) context).setList(item.getUseruId(), item.getUserName());

                    else
                        ((SelectPeopleActivity) context).deleteMember(item.getUseruId(), item.getUserName());
                }
            });

        }
        else if (item_layout == 1 || item_layout == 2) {
                final SelectPeopleItem item1 = items.get(position);
                holder.userName.setText(item1.getUserName());
                holder.selectSomethingItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pActivity.sendIntent(item1,item_layout);
                    }
                });

        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView userName;
        Integer itemSelector;
        String workTitle;
        LinearLayout selectSomethingItem;
        AppCompatCheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
                image = (ImageView) itemView.findViewById(R.id.profileImage);
                userName = (TextView) itemView.findViewById(R.id.userNameText);
                selectSomethingItem = (LinearLayout) itemView.findViewById(R.id.selectSomethingItem);
            if(item_layout==0 || item_layout==4)
                checkBox = (AppCompatCheckBox)itemView.findViewById(R.id.checkBox);

        }
    }



}
