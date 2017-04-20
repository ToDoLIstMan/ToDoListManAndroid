package com.tdl.todolistmanandroid.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songm on 2017-04-07.
 */

public class user {
    String name, rank, email;
    List<Integer> groups = new ArrayList<>();
    List<String> groupName = new ArrayList<>();
    public String getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Integer> getGroups() {
        return groups;
    }

    public List<String> getGroupName() {
        return groupName;
    }

    public user(){}

    public user(String name, String rank, List<Integer> groups, List<String> groupName){
        this.name = name;
        this.rank = rank;
        this.groups.addAll(groups);
        this.groupName.addAll(groupName);

    }

}