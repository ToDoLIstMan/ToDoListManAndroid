package com.tdl.todolistmanandroid.item;

/**
 * Created by HyunWook Kim on 2017-04-05.
 */

public class AddPlanItem {
    private String title;

    /**
     *
     * @return title을 반환
     */
    public String getTitle() {
        return title;
    }
    public AddPlanItem(String title){
        this.title = title;
    }
}
