package com.tdl.todolistmanandroid.item;

import java.util.List;

/**
 * Created by HyunWook Kim on 2017-05-20.
 */

public class FormatDetailItem {
    String title, detail, startTime, endTime;
    int id;

    public String getTitle(){return title;}

    public String getDetail(){return detail;}

    public String getStartTime() {return startTime; }

    public String getEndTime() { return endTime; }

    public int getId() { return id;}

    public FormatDetailItem(int id, String title, String detail, String startTime, String endTime){
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
