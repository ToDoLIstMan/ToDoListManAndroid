package com.tdl.todolistmanandroid.database;

/**
 * Created by songmho on 2017. 4. 5..
 */

public class group {
    String groupName;
    int id;

    public int getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }
    public group(){}

    public group(int id, String groupName){
        this.id= id;
        this.groupName = groupName;
    }
}
