package com.tdl.todolistmanandroid;

/**
 * Created by HyunWook Kim on 2017-05-23.
 */


public class ChangeTime {
    int hour, min, out;
    String sHour, sMin, timeData, stoi;
    String today;



    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public String getFullTime(){
        return sHour+":"+sMin;
    }

    public String getStoi() { return stoi; }

    public int getOut(){ return out;}

    public ChangeTime(int hour, int min){
        this.hour = hour;
        this.min = min;

        if(hour<10){
            sHour = "0"+hour;
        } else  sHour = hour+"";

        if(min<10){
            sMin = "0" + min;
        } else sMin = min+"";

    }

    public ChangeTime(String timeData){
        this.timeData = timeData;
        ChangeTime ct;

        String[] times = timeData.split(":");
        out = Integer.parseInt(times[0])*60 + Integer.parseInt(times[1]);

        hour = Integer.parseInt(times[0]);
        min = Integer.parseInt(times[1]);
        ct = new ChangeTime(hour,min);
        stoi = ct.getFullTime();

    }


}
