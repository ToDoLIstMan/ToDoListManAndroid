package com.tdl.todolistmanandroid.item;

/**
 * Created by songmho on 2017. 5. 23..
 */

public class MypageHeader {
    String name;
    String uId;
    String rank;

    public String getName() {
        return name;
    }

    public String getRank() {
        return rank;
    }

    public String getuId() {
        return uId;
    }

    public MypageHeader(String name, String uId, String rank){
        this.name = name;
        this.uId = uId;
        this.rank = rank;

    }
}
