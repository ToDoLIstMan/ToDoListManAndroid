package com.tdl.todolistmanandroid.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songm on 2017-03-21.
 */


public class MainItem {
    private String title, masterUid;
    private int groupId;
    List<String> memberUid =new ArrayList<>();
    List<String> memberName =new ArrayList<>();


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

    public List<String> getMemberName() {
        return memberName;
    }

    public List<String> getMemberUid() {
        return memberUid;
    }

    public MainItem(int groupId, String masterUid, String title, List<String> memberUid, List<String> memberName){
        this.title = title;
        this.masterUid = masterUid;
        this.groupId = groupId;
        this.memberUid.addAll(memberUid);
        this.memberName.addAll(memberName);
    }
}
