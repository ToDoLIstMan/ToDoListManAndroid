package com.tdl.todolistmanandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.item.TimeListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songmho on 2017. 4. 14..
 */

public class ListFragment extends Fragment {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<TimeListItem> lists;
    HashMap<Integer,String> timeLists;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout v = (FrameLayout) inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this,v);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        lists = new ArrayList<>();
        timeLists = new HashMap<>();

        final List<String> doPeople = new ArrayList<>();
        final List<Boolean> isDone = new ArrayList<>();
        doPeople.add("test #1");
        doPeople.add("test #2");
        doPeople.add("test #3");
        isDone.add(true);
        isDone.add(false);
        isDone.add(false);

        lists.add(new TimeListItem("11:00", "12:00", "Work #1", "detail is detail.", "Supervisor #1", doPeople, isDone));
        lists.add(new TimeListItem("17:00", "18:00", "Work #1", "detail is detail.", "Supervisor #1", doPeople, isDone));
        lists.add(new TimeListItem("17:00", "18:00", "Work #1", "detail is detail.", "Supervisor #1", doPeople, isDone));
        lists.add(new TimeListItem("17:00", "18:00", "Work #1", "detail is detail.", "Supervisor #1", doPeople, isDone));
        lists.add(new TimeListItem("18:00", "20:00", "Work #1", "detail is detail.", "Supervisor #1", doPeople, isDone));



        return v;
    }
}
