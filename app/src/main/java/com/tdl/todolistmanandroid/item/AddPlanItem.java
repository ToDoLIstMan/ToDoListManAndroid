package com.tdl.todolistmanandroid.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HyunWook Kim on 2017-04-05.
 */

public class AddPlanItem {
    String title, detail, startTime, endTime;
    int id;
    List<String> name, uId;
    List<Boolean> isDone;

    /**
     *
     * @return title을 반환
     */
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

    public List<String> getName() {
        return name;
    }

    public List<String> getuId() {
        return uId;
    }

    public List<Boolean> getIsDone() {
        return isDone;
    }
    public AddPlanItem(int id, String title, String detail, String startTime, String endTime,List<String> name,List<String> uId,List<Boolean> isDone){
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.startTime = startTime;
        this.endTime = endTime;


        this.name = new ArrayList<>();
        this.uId = new ArrayList<>();
        this.isDone = new ArrayList<>();

        this.name.addAll(name);
        this.uId.addAll(uId);
        this.isDone.addAll(isDone);
    }
}
