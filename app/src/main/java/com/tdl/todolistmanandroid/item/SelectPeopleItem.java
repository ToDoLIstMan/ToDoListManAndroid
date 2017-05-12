package com.tdl.todolistmanandroid.item;

/**
 * Created by HyunWook Kim on 2017-04-20.
 */

public class SelectPeopleItem {
    int image;
    String userName;
    String useruId;

    public String getUseruId() {
        return useruId;
    }

    public int getImage(){
        return this.image;
    }

    public String getUserName(){
        return this.userName;
    }

    public SelectPeopleItem(int image, String userName, String useruId) {
        this.image = image;
        this.userName = userName;
        this.useruId = useruId;
    }
}
