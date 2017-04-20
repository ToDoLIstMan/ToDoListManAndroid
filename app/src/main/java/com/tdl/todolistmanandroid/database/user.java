package com.tdl.todolistmanandroid.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songm on 2017-04-07.
 */

public class user {
    String name, rank, email;
    List<String> groups = new ArrayList<>();
    public String getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getGroups() {
        return groups;
    }

    public user(){}

    public user(String name, String email, String rank, List<String> groups){
        this.name = name;
        this.rank = rank;
        this.groups = groups;

    }

}