package com.tdl.todolistmanandroid.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HyunWook Kim on 2017-04-28.
 */

public class FormatManageItem {

    private String formatName, masterUid, formatId;
    List<String> work =new ArrayList<>();


    /**
     *
     * @return title을 반환
     */
    public String getFormatName() {
        return formatName;
    }

    public String getFormatId() {
        return formatId;
    }

    public String getMasterUid() {
        return masterUid;
    }

//    public List<String> getWork() {
//        return work;
//    }

    public FormatManageItem(String formatName, String formatId, String masterUid/*, List<String> work*/){
        this.formatName = formatName;
        this.masterUid = masterUid;
//        this.formatId = formatId;
//        this.work.addAll(work);
    }

}
