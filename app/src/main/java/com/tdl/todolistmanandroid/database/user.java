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
    List<Integer> masterGroups = new ArrayList<>();
    List<String> masterGroupName = new ArrayList<>();

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

    public List<Integer> getMasterGroups() {
        return masterGroups;
    }

    public List<String> getMasterGroupName() {
        return masterGroupName;
    }

    public user(){}

    public void setName(String name) {
        this.name = name;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public user(String name, String rank, List<Integer> groups, List<String> groupName, List<Integer> masterGroups, List<String> masterGroupName){
        this.name = name;
        this.rank = rank;
        this.groups.addAll(groups);
        this.groupName.addAll(groupName);
        this.masterGroupName.addAll(masterGroupName);
        this.masterGroups.addAll(masterGroups);

    }

}