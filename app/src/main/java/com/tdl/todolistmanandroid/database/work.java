package com.tdl.todolistmanandroid.database;

/**
 * Created by songmho on 2017. 4. 5..
 */

public class work {
    String title, detail, startTime, endTime;
    int id;
    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getId() {
        return id;
    }

    public work(){}
    public work(int id, String title, String detail, String startTime, String endTime){
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.startTime = startTime;
        this.endTime = endTime;

    }
}
