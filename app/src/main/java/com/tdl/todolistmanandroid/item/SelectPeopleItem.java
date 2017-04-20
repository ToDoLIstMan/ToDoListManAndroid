package com.tdl.todolistmanandroid.item;

/**
 * Created by HyunWook Kim on 2017-04-20.
 */

public class SelectPeopleItem {
    int image;
    String userName;

    public int getImage(){
        return this.image;
    }

    public String getUserName(){
        return this.userName;
    }

    public SelectPeopleItem(int image, String userName) {
        this.image = image;
        this.userName = userName;
    }
}
