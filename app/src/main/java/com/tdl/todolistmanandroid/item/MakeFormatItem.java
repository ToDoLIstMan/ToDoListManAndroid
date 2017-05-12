package com.tdl.todolistmanandroid.item;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HyunWook Kim on 2017-04-28.
 */

public class MakeFormatItem {

    private String formatName;
    private String detail;
    private String endTime;
    private String startTime;
    private String planName;
    private int id;


    public int getId() {
        return id;
    }

    @Nullable
    public String getFormatName() {
        return formatName;
    }
    @Nullable
    public String getDetail() {
        return detail;
    }
    @Nullable
    public String getEndTime() {
        return endTime;
    }
    @Nullable
    public String getStartTime() {
        return startTime;
    }
    @Nullable
    public String getPlanName() {
        return planName;
    }

    public MakeFormatItem(int id, String planName, String startTime, String endTime, String detail){
        this.id = id;
        this.detail = detail;
        this.endTime = endTime;
        this.startTime = startTime;
        this.planName = planName;
    }

}
