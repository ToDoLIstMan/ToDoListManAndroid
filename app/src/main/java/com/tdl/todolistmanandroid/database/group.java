package com.tdl.todolistmanandroid.database;

/**
 * Created by songmho on 2017. 4. 5..
 */

public class group {
    String groupName;
    String masterUid;
    int id;

    public int getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getMasterUid() {
        return masterUid;
    }

    public group(){}

    public group(int id,String masterUid, String groupName){
        this.id= id;
        this.masterUid = masterUid;
        this.groupName = groupName;
    }
}
