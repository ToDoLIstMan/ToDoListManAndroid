package com.tdl.todolistmanandroid.item;

/**
 * Created by songm on 2017-03-21.
 */


public class MainItem {
    private String title, masterUid;
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

    public String getMasterUid() {
        return masterUid;
    }

    public MainItem(int groupId, String masterUid, String title){
        this.title = title;
        this.masterUid = masterUid;
        this.groupId = groupId;
    }
}
