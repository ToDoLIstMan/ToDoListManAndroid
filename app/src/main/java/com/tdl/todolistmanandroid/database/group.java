package com.tdl.todolistmanandroid.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songmho on 2017. 4. 5..
 */

public class group {
    String groupName;
    String masterUid;
    int id;
    List<String> memberUid =new ArrayList<>();
    List<String> memberName =new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getMasterUid() {
        return masterUid;
    }

    public List<String> getMemberUid() {
        return memberUid;
    }

    public List<String> getMemberName() {
        return memberName;
    }

    public group(){}
    public group(int id, String masterUid, String groupName, List<String> memberUid, List<String> memberName){
        this.id= id;
        this.masterUid = masterUid;
        this.groupName = groupName;
        this.memberUid.addAll(memberUid);
        this.memberName.addAll(memberName);
    }
}
