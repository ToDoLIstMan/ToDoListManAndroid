package com.tdl.todolistmanandroid.item;

/**
 * Created by songm on 2017-03-21.
 */


public class MainItem {
    private String title;
    private int groupId;

    /**
     *
     * @return title을 반환
     */
    public String getTitle() {
        return title;
    }

    public int getGroupId() {
        return groupId;
    }

    public MainItem(int groupId,String title){
        this.title = title;
        this.groupId = groupId;
    }
}
