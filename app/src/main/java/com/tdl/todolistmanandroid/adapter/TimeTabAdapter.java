package com.tdl.todolistmanandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;

import com.tdl.todolistmanandroid.item.TimeListItem;

import java.util.List;

/**
 * Created by songmho on 2017. 4. 14..
 */

public class TimeTabAdapter extends FragmentStatePagerAdapter {
    FragmentManager fm;
    int tabCount;
    List<TimeListItem> lists;

    public TimeTabAdapter(FragmentManager fm, int tabCount, List<TimeListItem> lists) {
        super(fm);
        this.fm = fm;
        this.tabCount = tabCount;
        this.lists = lists;
    }

    @Override
    public Fragment getItem(int position) {
        ListFragment listFragment;
        switch (position){
            case 0:
                listFragment = new ListFragment();

                return listFragment;
            case 1:
                listFragment = new ListFragment();
                return listFragment;
            case 2:
                listFragment = new ListFragment();
                return listFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
