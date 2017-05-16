package com.tdl.todolistmanandroid.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HyunWook Kim on 2017-04-28.
 */

public class format {

    String formatName, masterUid, formatId, detail, startTime, endTime, planNumber, planName;
    List<String> work = new ArrayList<>();

    public String getFormatName() {
        return formatName;
    }

    public String getMasterUid() {
        return masterUid;
    }

    public String getFormatId() {
        return formatId;
    }

    public List<String> getWork() {
        return work;
    }

    public String getPlanNumber() {
        return planNumber;
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


    public format(){}
    public format(String formatName, String masterUid, String formatId){
        this.formatName = formatName;
        this.masterUid = masterUid;
        this.formatId = formatId;
    }

}
