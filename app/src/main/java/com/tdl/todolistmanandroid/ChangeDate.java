package com.tdl.todolistmanandroid;

/**
 * Created by songmho on 2017. 5. 16..
 */

public class ChangeDate {
    int year, month, day;
    String sYear, sMonth, sDay;



    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getDate(){
        return sYear+"-"+sMonth+"-"+sDay;
    }


    public ChangeDate(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;

        sYear = ""+year;
        if(month<9){
            sMonth = "0"+(month+1);
        } else  sMonth = (month+1)+"";

        if(day<10){
            sDay = "0" + day;
        } else sDay = day+"";

    }
}
