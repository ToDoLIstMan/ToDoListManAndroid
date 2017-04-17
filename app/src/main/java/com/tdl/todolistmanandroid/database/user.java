package com.tdl.todolistmanandroid.database;

import java.util.List;

/**
 * Created by songm on 2017-04-07.
 */

public class user {
    String name, rank;
    String[] groups;
    public String getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public String[] getGroups() {
        return groups;
    }

    public user(){}

    public user(String name, String rank,String[] groups){
        this.name = name;
        this.rank = rank;
        this.groups = groups;

    }

}