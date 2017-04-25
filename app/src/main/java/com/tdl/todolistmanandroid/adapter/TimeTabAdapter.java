package com.tdl.todolistmanandroid.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.util.Log;

import com.tdl.todolistmanandroid.fragment.TimeListFragment;

/**
 * Created by songmho on 2017. 4. 14..
 */

public class TimeTabAdapter extends FragmentStatePagerAdapter {
    FragmentManager fm;
    int tabCount, curGroupUid;

    public TimeTabAdapter(FragmentManager fm, int tabCount, int curGroupId) {
        super(fm);
        this.fm = fm;
        this.tabCount = tabCount;
        this.curGroupUid = curGroupId;
    }

    @Override
    public Fragment getItem(int position) {
        TimeListFragment listFragment;
        Bundle bundle= new Bundle();
        bundle.putInt("uid",curGroupUid);
        switch (position){
            case 0:
                Log.e("curPosition",""+position);
                listFragment = new TimeListFragment();
                bundle.putString("status","whole");
                listFragment.setArguments(bundle);
                return listFragment;
            case 1:
                Log.e("curPosition",""+position);
                listFragment = new TimeListFragment();
                bundle.putString("status","done");
                listFragment.setArguments(bundle);
                return listFragment;
            case 2:
                Log.e("curPosition",""+position);
                listFragment = new TimeListFragment();
                bundle.putString("status","doing");
                listFragment.setArguments(bundle);
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
