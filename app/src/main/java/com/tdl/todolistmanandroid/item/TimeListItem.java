package com.tdl.todolistmanandroid.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songm on 2017-03-21.
 */

public class TimeListItem {
    private int id;
    private String startTime, endTime, title, detail;
    private List<String> doPeople =new ArrayList<>(), peopleUid =new ArrayList<>();
    private List<Boolean> isDone =new ArrayList<>();

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

    public List<String> getDoPeople() {
        return doPeople;
    }

    public List<String> getPeopleUid() {
        return peopleUid;
    }

    public List<Boolean> getIsDone() {
        return isDone;
    }
    public TimeListItem(String startTime, String endTime, String title, String detail, int id, List<String> doPeople, List<String> peopleUid, List<Boolean> isDone){
        this.startTime = startTime;
        this.endTime =endTime;
        this.title = title;
        this.detail =detail;
        this.id = id;

        this.doPeople = new ArrayList<>();
        this.peopleUid = new ArrayList<>();
        this.isDone = new ArrayList<>();

        if(doPeople!=null && peopleUid!=null && isDone!=null ) {
            this.doPeople.addAll(doPeople);
            this.peopleUid.addAll(peopleUid);
            this.isDone.addAll(isDone);
        }

    }
}
