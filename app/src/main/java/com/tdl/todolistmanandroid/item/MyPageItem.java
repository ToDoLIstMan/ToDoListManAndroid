package com.tdl.todolistmanandroid.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HyunWook Kim on 2017-05-19.
 */

public class MyPageItem {

    String name;
    int id;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public MyPageItem(int id, String name){
        this.name = name;
        this.id = id;
    }

}
