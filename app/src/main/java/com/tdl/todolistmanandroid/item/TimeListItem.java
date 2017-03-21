package com.tdl.todolistmanandroid.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songm on 2017-03-21.
 */

public class TimeListItem {
    private String startTime, endTime, title, detail, supervisor;
    private List<String> doPeople = new ArrayList<>();
    private List<Boolean> isDone = new ArrayList<>();

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

    public String getSupervisor() {
        return supervisor;
    }

    public List<String> getDoPeople() {
        return doPeople;
    }

    public List<Boolean> getIsDone() {
        return isDone;
    }
    public TimeListItem(String startTime, String endTime, String title, String detail, String supervisor, List<String> doPeople,  List<Boolean> isDone){
        this.startTime = startTime;
        this.endTime =endTime;
        this.title = title;
        this.detail =detail;
        this.supervisor = supervisor;
        this.doPeople = doPeople;
        this.isDone = isDone;

    }
}
