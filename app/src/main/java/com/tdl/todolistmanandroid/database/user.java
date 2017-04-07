package com.tdl.todolistmanandroid.database;

import java.util.List;

/**
 * Created by songm on 2017-04-07.
 */

public class user {
    String name, belong;
    List<group> groups;
    public String getBelong() {
        return belong;
    }

    public String getName() {
        return name;
    }

    public List<group> getGroups() {
        return groups;
    }

    public user(){}
    public user(String name, String belong,List<group> groups){
        this.name = name;
        this.belong = belong;
        this.groups = groups;

    }

}