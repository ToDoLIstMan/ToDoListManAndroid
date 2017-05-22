package com.tdl.todolistmanandroid.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HyunWook Kim on 2017-04-28.
 */

public class format {
    int id;

    String formatName, detail, startTime, endTime, planName;

    public int getId() {
        return id;
    }

    public String getFormatName() {
        return formatName;
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

    public String getPlanName() {
        return planName;
    }

    public  format(){}
    public format(int id, String planName, String detail, String startTime, String endTime){
        this.id = id;
        this.planName = planName;
        this.detail = detail;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
