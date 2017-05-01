package com.tdl.todolistmanandroid.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HyunWook Kim on 2017-04-28.
 */

public class format {

    String formatName, masterUid, formatId;
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

    public format(){}

    public format(String formatName, String masterUid, String formatId, List<String> work){
        this.formatName = formatName;
        this.masterUid = masterUid;
        this.formatId = formatId;
        this.work.addAll(work);

    }

}
